import user.User;
import user.UserController;
import user.UserRepository;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserRepository userRepository = new UserRepository(); // add data from file
        UserController userController = new UserController(userRepository);

        User user = null;
        int userChoice = 0;
        while(userChoice != 5) {
            System.out.println("What would you like to do?");
            if (user == null){
                System.out.println("1 - register new user");
                System.out.println("2 - login");
                System.out.println("3 - exit");

                userChoice = scanner.nextInt();

                if (userChoice == 1){
                    user = userController.registerUser();
                } else if (userChoice == 2) {
                    user = userController.loginUser();
                } else {
                    userChoice = 5;
                    userRepository.writeToCsvFile();
                }

            } else {
                System.out.println("1 - look at your account");
                System.out.println("2 - make a deposit");
                System.out.println("3 - make a transfer");
                System.out.println("4 - logout");
                System.out.println("5 - exit");
                userChoice = scanner.nextInt();

                if(userChoice == 1){
                    userController.showAccount();
                } else if (userChoice == 2) {
                    System.out.println("Please, enter a number of funds");
                    double summa = scanner.nextFloat();
                    userController.makeDeposit(summa);
                } else if (userChoice == 3 ) {
                    System.out.println("Please, enter a number of funds");
                    double summa = scanner.nextFloat();
                    userController.makeTransfer(summa);
                } else if (userChoice == 4) {
                    userController.logout();
                    user = null;
                } else if (userChoice == 5) {
                    userRepository.writeToCsvFile();
                }
            }

        }
    }
}
