package user;

import java.util.ArrayList;

import static javax.swing.JOptionPane.*;

public class UserService {

    public UserService() {
    }

    public String create(User user, ArrayList<User> userList) {
        for (User u : userList){
            if(u.getUsername().equals(user.getUsername())){
                showMessageDialog(null, "User with username " + user.getUsername() + " already exist", "Error: ", ERROR_MESSAGE);
                return "User with username " + user.getUsername() + " already exist";
            }
        }
        return null;
    }

    public Boolean login(User user) {
        if (user == null){
            showMessageDialog(null, "Incorrect login or password!", "Information: ", PLAIN_MESSAGE);
            return false;
        }
        return true;
    }
}
