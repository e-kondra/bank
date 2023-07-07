package user;

import java.util.UUID;

public class User {
    private UUID id;
    private String name;
    private String username;
    private String password;
    private Gender gender;
    private Account account;
    public User(){}

    public User(UUID id, String name, String username, String password, Gender gender) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.gender = gender;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", gender='" + gender + '\'' +
                ", account='" + getAccount().getAccountNumber() + '\'' +
                '}';
    }
}
