import user.User;
import user.UserController;
import user.UserRepository;

import javax.swing.*;
import java.util.Scanner;

import static javax.swing.JOptionPane.*;

public class Menu {
    private final UserRepository userRepository;
    private UserController userController;
    Scanner scanner = new Scanner(System.in);
    private User user;
    private JRadioButton buttonRegister = new JRadioButton("register user");;
    private JRadioButton buttonLogin = new JRadioButton("login");
    private JRadioButton buttonExit = new JRadioButton("exit");;
    private JRadioButton buttonShowAccount = new JRadioButton("look at my account");
    private JRadioButton buttonDeposit = new JRadioButton("make a deposit");
    private JRadioButton buttonTransfer = new JRadioButton("make a transfer");
    private JRadioButton buttonLogout = new JRadioButton("logout");


    public Menu() {
        this.userRepository = new UserRepository();
        this.userController = new UserController(userRepository);
        this.user = null;
    }

    public void start(){
        ButtonGroup setMode;
        int choice;

        if (this.user == null){
            setMode = new ButtonGroup();
            buttonRegister.setSelected(true);

            setMode.add(buttonRegister);
            setMode.add(buttonLogin);
            setMode.add(buttonExit);

            Object[] array = {buttonRegister, buttonLogin, buttonExit};

            choice = showConfirmDialog(null, array,
                    "What would you like to do?", YES_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
        } else {

            setMode = new ButtonGroup();
            buttonShowAccount.setSelected(true);
            setMode.add(buttonShowAccount);
            setMode.add(buttonDeposit);
            setMode.add(buttonTransfer);
            setMode.add(buttonLogout);
            setMode.add(buttonExit);

            Object[] array = {buttonShowAccount, buttonDeposit, buttonTransfer, buttonLogout, buttonExit};

            choice = showConfirmDialog(null, array,
                    "What would you like to do?", YES_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
        }
        if (choice == 0) {
            if (this.buttonRegister.isSelected()) {
                this.user = userController.registerUser();

            } else if (this.buttonShowAccount.isSelected()) {
                this.userController.showAccount();

            } else if (this.buttonLogin.isSelected()) {
                this.user = this.userController.loginUser();

            } else if (this.buttonDeposit.isSelected()) {
                System.out.println("Please, enter a number of funds");
                double summa = scanner.nextFloat();
                this.userController.makeDeposit(summa);

            } else if (this.buttonTransfer.isSelected()) {
                System.out.println("Please, enter a number of funds");
                double summa = scanner.nextFloat();
                this.userController.makeTransfer(summa);

            } else if (this.buttonLogout.isSelected()) {
                this.userController.logout();
                this.user = null;

            } else if (buttonExit.isSelected()) {
                this.userController.exit();
            }

            setMode.clearSelection();
        }
        this.start();
    }


}
