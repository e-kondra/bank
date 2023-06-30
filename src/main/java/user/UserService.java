package user;

import java.util.ArrayList;

public class UserService {

    public UserService() {
    }

    public String create(User user, ArrayList<User> userList) {
        for (User u : userList){
            if(u.getUsername().equals(user.getUsername())){
                return "User with username " + user.getUsername() + " already exist";
            }
        }
        return null;
    }

    public Boolean login(User user) {
        if (user == null){
            System.out.println("Incorrect login or password!");
            return false;
        }
        return true;
    }
}
