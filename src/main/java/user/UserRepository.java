package user;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class UserRepository {
    private ArrayList<User> userList;

    public UserRepository() {
        this.userList = new ArrayList<>();
        this.readFromCsvFile();
    }

    public UserRepository(ArrayList<User> userList) {
        this.userList = userList;
    }

    public ArrayList<User> getUserList() {
        return userList;
    }

    public void setUserList(ArrayList<User> userList) {
        this.userList = userList;
    }

    public void addUser(User user) {
        userList.add(user);
    }

    public User getUserFromUserList(String username, String password) {
        for (User user : userList) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    private void createCsvFile() {
        try {
            File file = new File("users.csv");
            file.createNewFile();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void writeToCsvFile() {
        this.createCsvFile();
        StringBuilder stringBuilder = new StringBuilder();
        for (User user : this.userList) {
            stringBuilder.append(user.getName()).append(";").append(user.getUsername()).append(";")
                    .append(user.getPassword()).append(";").append(user.getGender()).append(";")
                    .append(user.getAccount().getAccountNumber()).append(";")
                    .append(user.getAccount().getBalance()).append("\n");
        }
        try (FileWriter fileWriter = new FileWriter("users.csv")) {
            fileWriter.write(stringBuilder.toString());
            System.out.println("Successfully wrote to the file.");
            fileWriter.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void readFromCsvFile() {
        try {
            File file = new File("users.csv");
            file.createNewFile();
            if (file != null){
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    String data = scanner.nextLine();
                    User user = this.getUserFromStringData(data);
                    userList.add(user);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private User getUserFromStringData(String data) {
        try {
            String[] arr = data.split(";");
            User user = new User(UUID.randomUUID(), arr[0], arr[1], arr[2], Gender.valueOf(arr[3]));
            Account account = new Account(arr[4], Double.valueOf(arr[5]));
            user.setAccount(account);
            return user;
        } catch (Exception e){
            System.out.println(e);
            return null;
        }
    }
}