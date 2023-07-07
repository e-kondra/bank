package user;

import javax.swing.*;
import static javax.swing.JOptionPane.*;
import java.util.UUID;

public class UserController {
    private UserService userService;
    private UserRepository userRepository;
    private User user;

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
            if (this.userService.create(user, userRepository.getUserList())){
                showInfoMessage("User " + user.getUsername() + " was created successfully");
                displayConsole("User " + user.getUsername() + " was created successfully");
                return user;
            }
        } catch (Exception e){
            showErrorMessage("User wasn't created, please try again");
            displayConsole("createUser Exception: \n" + e.getMessage());
            return null;
        }
        return null;
    }

    private User collectUserInfo() {
        JTextField fieldName= new JTextField();
        JTextField fieldUserName = new JTextField();
        JTextField fieldPassword = new JTextField();
        JTextField fieldGender = new JTextField();

        Object[] message = {
                "Name:", fieldName,
                "Username:", fieldUserName,
                "Password:", fieldPassword,
                "gender (1 - MALE, 2 - FEMALE, 3 - OTHER)", fieldGender,

        };
        int option = JOptionPane.showConfirmDialog(null, message,
                "Please, fill out all fields", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION)
        {
            User user = new User();

            user.setId(UUID.randomUUID());
            user.setName(fieldName.getText());
            user.setUsername(fieldUserName.getText());
            user.setPassword(fieldPassword.getText());

            String genderChoice = fieldGender.getText();
            Gender gender = (genderChoice.equals("1"))? Gender.MALE
                    : (genderChoice.equals("2")) ? Gender.FEMALE : Gender.OTHER;
            user.setGender(gender);

            return user;
        } else {
            showInfoMessage("You canceled user creation");
            return null;
        }
    }

    private void showErrorMessage(String message){
        showMessageDialog(null, message, "Attention!", ERROR_MESSAGE);
    }

    private void showInfoMessage(String message){
        showMessageDialog(null, message, "Information: ", PLAIN_MESSAGE);
    }

    public void displayConsole(String message){
        System.out.println(message);
    }

    public User registerUser(){
        try {
            this.setUser(this.createUser());  // creating new user
            if (this.getUser() != null){
                this.createAccount();    // creating account
                userRepository.addUser(this.user);  // add user in ArrayList
            }
            return this.user;

        } catch (NullPointerException e){
            showErrorMessage("Unfortunately, user's creation wasn't successful, please try again");
            displayConsole("registerUser NullPointerException \n" + e.getMessage());
        } catch (Exception e) {
            showErrorMessage("Unfortunately, user's creation wasn't successful, please try again");
            displayConsole("registerUser :\n" + e.getMessage());
        }
        return null;
    }

    public void logout(){
        String userName = this.user.getUsername();
        this.user = null;
        showInfoMessage("You successfully logout!");
        displayConsole( userName + " was logout");
    }

    public User loginUser() {
        try{
            JTextField field1 = new JTextField();
            JTextField field2 = new JTextField();

            Object[] message = {
                    "Username:", field1,
                    "Password:", field2,
            };
            int option = JOptionPane.showConfirmDialog(null, message,
                    "Please, fill out all fields", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                String username = field1.getText();
                String password = field2.getText();
                User user = userRepository.getUserFromUserList(username, password);
                if (this.userService.login(user)){
                    showInfoMessage("Welcome, " + user.getName() + "!");
                    displayConsole("User " + user.getUsername() + " login successfully");
                    this.user = user;
                    return user;
                } else {
                    return null;
                }
            } else{
                showInfoMessage("You canceled user login");
                return null;
            }
        }catch (Exception e){
            showErrorMessage("Something was wrong with login, let's try again");
            displayConsole("loginUser :\n" + e.getMessage() );
            return null;
        }
    }

    private void createAccount() {
        Account account = new Account();  //  generate account number + 0 balance
        user.setAccount(account);
    }

    public void showAccount() {
        this.showInfoMessage("Account number: " + this.user.getAccount().getAccountNumber() +
                "\nYour balance: " + this.user.getAccount().getBalanceStr());
    }


    public void exit(){
        this.userRepository.writeToCsvFile();
        this.showInfoMessage("Bue!\nGlad to see you again");
        System.exit(0);
    }

    public void toDeposit() {
        try {
            double amount = Double.parseDouble(showInputDialog("How much do you want to deposit?")
                    .replace(",", "."));
            Account account = user.getAccount();
            account.toCredit(amount);
            showInfoMessage("You have successfully deposited your account\n" +
                    "Your balance " + account.getBalanceStr());
        }catch (Exception e){
            showErrorMessage("Something was wrong, please, try again");
            displayConsole("makeCredit :\n" + e.getMessage());
        }
    }

    public void toWithdraw() {
        try{
            Account account = user.getAccount();
            double amount = Double.parseDouble(showInputDialog("How much do you want to withdraw?\n" +
                    "available amount " + account.getBalanceStr()).replace(",","."));
            if (account.checkDebitAmount(amount)) { // check amount
                account.toDebit(amount);
                showInfoMessage("You have successfully withdrawn your account\n" +
                        "Your balance " + account.getBalanceStr());
            } else {
                showErrorMessage("Sorry, You don't have enough funds");
            }
        }catch (Exception e){
            showErrorMessage("Something was wrong, please, try again");
            displayConsole("makeCredit :\n" + e.getMessage());
        }

    }
}
