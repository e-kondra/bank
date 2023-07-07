package user;

import java.util.ArrayList;

import static javax.swing.JOptionPane.*;

public class UserService {

    public UserService() {
    }

    public Boolean create(User user, ArrayList<User> userList) {
        String result = "";
        if (user.getName().isEmpty()) result =  "Name couldn't be empty!\n";
        if (user.getUsername().isEmpty()) result = result.concat("Username couldn't be empty!\n");
        if (user.getPassword().isEmpty()) result = result.concat("Password couldn't be empty!\n");
        if (user.getPassword().length() < 5) result = result.concat("Password must have at least 5 characters\n") ;
        for (User u : userList){
            if(u.getUsername().equals(user.getUsername())){
                result = result.concat("User with username " + user.getUsername() + " already exist");
            }
        }
        if (result.length() > 0) {
            showMessageDialog(null, result, "Error: ", ERROR_MESSAGE);
        }
        return result.isEmpty();
    }

    public Boolean login(User user) {
        if (user == null){
            showMessageDialog(null, "Incorrect login or password!", "Error: ", ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}
