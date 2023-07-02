package user;

import javax.swing.*;
import static javax.swing.JOptionPane.*;\
import java.util.Scanner;
import java.util.UUID;

public class UserController {
    private UserService userService;
    private UserRepository userRepository;
    private User user;
    Scanner scanner = new Scanner(System.in);

    public UserController(UserRepository userRepository) {
        this.userService = new UserService();
        this.userRepository = userRepository;
        this.user = null;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    private User createUser(){
        try {
            User user = this.collectUserInfo();
            String result = this.userService.create(user, userRepository.getUserList());
            if (result != null && result.length() != 0) {
                System.out.println(result);
                return null;
            } else {
                System.out.println("User " + user.getName() + " - " + user.getGender()+ " was created successfully");
                return user;
            }
        } catch (Exception e){
            System.out.println("Something was wrong: \n" + e);
            return null;
        }
    }

    private User collectUserInfo() {
        JTextField field1 = new JTextField();
        JTextField field2 = new JTextField();
        JTextField field3 = new JTextField();
        JTextField field4 = new JTextField();

        Object[] message = {
                "Name:", field1,
                "Username:", field2,
                "Password:", field3,
                "gender (1 - MALE, 2 - FEMALE, 3 - OTHER)", field4,

        };
        int option = JOptionPane.showConfirmDialog(null, message, "Please, fill out all fields", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION)
        {
            String value1 = field1.getText();
            String value2 = field2.getText();
            String value3 = field3.getText();
            String value4 = field4.getText();

            User user = new User();
            user.setId(UUID.randomUUID());
            user.setName(value1);
            user.setUsername(value2);
            user.setPassword(value3);
            String genderChoice = value4;
            Gender gender = (genderChoice.equals("1"))? Gender.MALE
                    : (genderChoice.equals("2")) ? Gender.FEMALE : Gender.OTHER;
            user.setGender(gender);
            return user;
        } else {
            this.showInfoMessage("You canceled user creation");
            return null;
        }
    };

    private void showErrorMessage(String message){
        showMessageDialog(null, message, "Attention!", ERROR_MESSAGE);
    }

    private void showInfoMessage(String message){
        showMessageDialog(null, message, "Information: ", PLAIN_MESSAGE);
    }

    public User registerUser(){
        try {
            this.setUser(this.createUser());
            if (this.getUser() != null){
                this.createAccount();
                userRepository.addUser(this.user);
            }
            return this.user;

        } catch (NullPointerException e){
            this.showErrorMessage("Unfortunately, user creation wasn't successful");
        } catch (Exception e) {
            this.showErrorMessage("User register failed: \n " + e);
        }
        return null;
    }

    public void logout(){
        this.user = null;
        this.showInfoMessage("You successfully logout!");
    }

    public User loginUser() {
        try{
            JTextField field1 = new JTextField();
            JTextField field2 = new JTextField();

            Object[] message = {
                    "Username:", field1,
                    "Password:", field2,
            };
            int option = JOptionPane.showConfirmDialog(null, message, "Please, fill out all fields", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                String username = field1.getText();
                String password = field2.getText();
                User user = userRepository.getUserFromUserList(username, password);
                if (this.userService.login(user)){
                    this.showInfoMessage("User " + user.getName() + " login successfully");
                    System.out.println("User " + user.getName() + " login successfully");
                    this.user = user;
                    return user;
                } else {
                    return null;
                }
            } else{
                this.showInfoMessage("You canceled user login");
                return null;
            }
        }catch (Exception e){
            this.showErrorMessage("Something was wrong with login, let's try again: \n" + e);
            return null;
        }
    }

    private void createAccount() {
        //generate account number + 0 balance
        Account account = new Account();
        user.setAccount(account);
    }

    public void showAccount() {
        this.showInfoMessage("Account number: " + this.user.getAccount().getAccountNumber() +
                "\nYour balance: " + this.user.getAccount().getBalance());
    }

    public void makeDeposit(double summa) {
        Account account = user.getAccount();
        account.makeDeposit(summa);
    }

    public void makeTransfer(double summa) {
        this.user.getAccount().makeTransfer(summa);
    }

    public void exit(){
        this.userRepository.writeToCsvFile();
        this.showInfoMessage("Bue!\nGlad to see you again");
        System.exit(0);
    }

}
