package user;


import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class UserController {
    private UserService userService;
    private UserRepository userRepository;
    User user;
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

        System.out.println("Please, enter your name");
        String name = scanner.nextLine();

        System.out.println("Please, enter your username");
        String username = scanner.nextLine();

        System.out.println("Please, enter your password");
        String password = scanner.nextLine();

        System.out.println("Please, enter your gender (1 - MALE, 2 - FEMALE, 3 - OTHER)");
        String genderChoice = scanner.nextLine();
        System.out.println(genderChoice);
        Gender gender = (genderChoice.equals("1"))? Gender.MALE
                : (genderChoice.equals("2")) ? Gender.FEMALE : Gender.OTHER;
        User user = new User(UUID.randomUUID(), name, username, password, gender);
        return user;
    };
    public User registerUser(){
        try {
            this.setUser(this.createUser());
            if (this.getUser() != null){
                this.createAccount();
                userRepository.addUser(this.user);
            }
            return this.user;
        } catch (Exception e){
            System.out.println("User register failed" + e);
            return null;
        }

    }

    public void logout(){
        this.user = null;
    }

    public User loginUser() {
        try {
            System.out.println("Please, enter your username");
            String username = scanner.nextLine();
            System.out.println("Please, enter your password");
            String password = scanner.nextLine();
            User user = userRepository.getUserFromUserList(username, password);
            if (this.userService.login(user)){
                System.out.println("User " + user.getName() + " login successfully");
                this.user = user;
                return user;
            } else {
                return null;
            }
        } catch (Exception e){
            System.out.println("Something was wrong with login, let's try again: \n" + e);
            return null;
        }
    }

    public void createAccount() {
        //generate account number + 0 balance
        Account account = new Account();
        user.setAccount(account);
    }

    public void showAccount() {
        System.out.println("Your account number: " + user.getAccount().getAccountNumber());
        System.out.println("Your balance : " + user.getAccount().getBalance());
    }

    public void makeDeposit(double summa) {
        Account account = user.getAccount();
        account.makeDeposit(summa);
    }

    public void makeTransfer(double summa) {
        Account account = user.getAccount();
        account.makeTransfer(summa);
    }
}
