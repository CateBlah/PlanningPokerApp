package Models;

/**
 * Created by Caterina on 4/19/2016.
 */
public class User {
    private int userId;
    private String userName;
    private String password;
    private String email;
    private int roleId;
    public User(String userName, String password, String email, int roleId){
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.roleId = roleId;
    }

    public User(int userId, String userName, String email){
        this.userId = userId;
        this.userName = userName;
        this.email = email;
    }

    public User(){

    }
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString(){
        String user = "";
        user += userId + " " + userName + " " + password + " " + email + " " + roleId + "\n";
        return user;
    }


}
