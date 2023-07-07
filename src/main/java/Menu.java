import user.User;
import user.UserController;
import user.UserRepository;

import javax.swing.*;
import static javax.swing.JOptionPane.*;

public class Menu {
    private final UserRepository userRepository;
    private final UserController userController;
    private User user;
    private final JRadioButton buttonRegister = new JRadioButton("register user");
    private final JRadioButton buttonLogin = new JRadioButton("login");
    private final JRadioButton buttonExit = new JRadioButton("exit");
    private final JRadioButton buttonShowAccount = new JRadioButton("look at my account");
    private final JRadioButton buttonDeposit = new JRadioButton("to deposit");
    private final JRadioButton buttonWithdraw = new JRadioButton("to withdraw");
    private final JRadioButton buttonLogout = new JRadioButton("logout");


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
                    "What would you like to do?", YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
        } else {

            setMode = new ButtonGroup();
            buttonShowAccount.setSelected(true);
            setMode.add(buttonShowAccount);
            setMode.add(buttonDeposit);
            setMode.add(buttonWithdraw);
            setMode.add(buttonLogout);
            setMode.add(buttonExit);

            Object[] array = {buttonShowAccount, buttonDeposit, buttonWithdraw, buttonLogout, buttonExit};

            choice = showConfirmDialog(null, array,
                    "What would you like to do?", YES_NO_OPTION,
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
                this.userController.toDeposit();

            } else if (this.buttonWithdraw.isSelected()) {
                this.userController.toWithdraw();

            } else if (this.buttonLogout.isSelected()) {
                this.userController.logout();
                this.user = null;

            } else if (this.buttonExit.isSelected()) {
                this.userController.exit();
            }

            setMode.clearSelection();
        }
        this.start();
    }

}
