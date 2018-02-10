package Repository;
import Models.DatabaseConnection;
import Models.User;
import Validators.Validator;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Caterina on 4/18/2016.
 */
public class UserRepository {
    private Validator validator = new Validator();
    //private Statement statement;

    private DatabaseConnection databaseConnection = new DatabaseConnection();
    public UserRepository(){

    }

    public String getUsernameById(int userId){
        String username = "";
        Connection connection = databaseConnection.connectToDatabase();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select userName FROM users WHERE userID = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,userId);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                username = resultSet.getString("userName");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return username;
    }
    public boolean authenticateUsername(String username){
        Connection connection = databaseConnection.connectToDatabase();
        ResultSet resultSet = null;
        Statement statement = null;
        boolean result = false;
        try {
            statement  = connection.createStatement();
            resultSet = statement.executeQuery("select * from users where BINARY userName='" + username + "'");
            if(resultSet.next()){
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnection.close(resultSet,statement,connection);
        }

        return result;
    }

    public boolean authenticatePassword(String password){
        Connection connection = databaseConnection.connectToDatabase();
        ResultSet resultSet = null;
        Statement statement = null;
        boolean result = false;
        try {
            statement  = connection.createStatement();
            resultSet = statement.executeQuery("select * from users where BINARY userpassword='" + password + "'");
            if(resultSet.next()){
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnection.close(resultSet,statement,connection);
        }

        return result;
    }

    public boolean authenticateUsernameAndPassword(String username, String password) {
        Connection connection = databaseConnection.connectToDatabase();
        ResultSet resultSet = null;
        Statement statement = null;
        boolean result = false;
        //System.out.println(username + " " + password);
        try{
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from users where BINARY userName = '" + username + "' and BINARY userPassword = '" + password + "'");
            if(resultSet.next()){
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnection.close(resultSet,statement,connection);
        }

        return result;
    }

    public int authenticate(String username, String password){
        int result = 0;
        if(authenticateUsername(username) == true && authenticatePassword(password) == true)
            result = 1;
        else if(authenticateUsername(username) == true && authenticatePassword(password) == false)
            result = -1;
        else if(authenticatePassword(password) == true && authenticateUsername(username) == false)
            result = -2;
        return result;

    }
    public int createUser(String userName, String password, String email, String roleName)  {
        Connection connection = databaseConnection.connectToDatabase();
        int resultSet;
        int result = 0;
        int roleId = getUserRoleId(roleName);
        PreparedStatement preparedStatement = null;
        ArrayList<User> users = getAllUsers();
        if(validator.userNameExists(users,userName) && validator.userEmailExists(users,email)){
            result = -3;
        } else if(validator.userEmailExists(users,email)) {
            result = -1;
        } else if(validator.userNameExists(users, userName)) {
            result = -2;
        } else {

            String query = " insert into users (userName, userPassword,email,roleId)"
                    + " values (?, ?, ?,?)";
            try {
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, userName);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, email);
                preparedStatement.setInt(4,roleId);
                if (preparedStatement.executeUpdate() > 0) {
                    result = 1;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                databaseConnection.closeStatementAndConnection(preparedStatement,connection);
            }
        }
        return result;
    }

    protected ArrayList<User> getAllUsers(){
        Connection connection = databaseConnection.connectToDatabase();
        ResultSet resultSet = null;
        Statement statement = null;
        ArrayList<User> users = new ArrayList<User>();
        try {
            statement = connection.createStatement();
            String query = ("SELECT * FROM users");
            resultSet = statement.executeQuery(query);
            while(resultSet.next()) {
                int userID = resultSet.getInt("userID");
                String userName = resultSet.getString("userName");
                String password = resultSet.getString("userpassword");
                String email = resultSet.getString("email");
                int roleId = resultSet.getInt("roleId");
                User newUser = new User(userName,password,email, roleId);
                newUser.setUserId(userID);
                users.add(newUser);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnection.close(resultSet,statement,connection);
        }

        return users;
    }

    public int getUserId(String userName){
        Connection connection = databaseConnection.connectToDatabase();
        Statement statement = null;
        ResultSet resultSet = null;
        int userID = -1;
        String query = "select userID from users where BINARY userName='" + userName + "'";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            if(resultSet.next()){
                userID = resultSet.getInt("userID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnection.close(resultSet, statement, connection);
        }

        return userID;
    }

    protected int getUserIdByEmail(String email){
        Connection connection = databaseConnection.connectToDatabase();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        int result = 0;
        String query = "select userID from users where BINARY email = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,email);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                result = resultSet.getInt("userID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            databaseConnection.closeConnection(resultSet, preparedStatement, connection);
        }
        return result;
    }

    public String getUserNameByEmail(String email){
        Connection connection = databaseConnection.connectToDatabase();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String result = "";
        String query = "select userName from users where BINARY email = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,email);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                result = resultSet.getString("userName");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnection.closeConnection(resultSet,preparedStatement,connection);
        }
        return result;
    }

    public User getUserByEmail(String email){
        Connection connection = databaseConnection.connectToDatabase();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select * from users where BINARY email = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,email);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                int userId = resultSet.getInt("userID");
                String userName= resultSet.getString("userName");
                int roleId = resultSet.getInt("roleId");
                User user = new User();
                user.setUserId(userId);
                user.setUserName(userName);
                user.setEmail(email);
                user.setRoleId(roleId);
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnection.closeConnection(resultSet, preparedStatement, connection);
        }
        return null;
    }

    public int getUserRoleId(String roleName){
        Connection connection = databaseConnection.connectToDatabase();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        int userRoleId = 0;
        String query = "select roleId from userroles where roleName = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,roleName);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                userRoleId = resultSet.getInt("roleId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnection.closeConnection(resultSet,preparedStatement,connection);
        }

        return userRoleId;
    }

    public boolean checkIfManager(String userName){
        ArrayList<User> users = getAllUsers();
        for(User user: users){
            if(user.getUserName().equals(userName) && user.getRoleId() == getUserRoleId("Program Manager")){
                return true;
            }
        }

        return false;
    }

    public String getUserRole(String userName) {
        Connection connection = databaseConnection.connectToDatabase();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String role = "";
        String query = "select roleName from userroles\n" +
                "inner join users on userroles.roleId = users.roleId and users.userName = ?;";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,userName);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                role = resultSet.getString("roleName");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return role;
    }

    public int updateUsername(String userName, int userId){
        Connection connection = databaseConnection.connectToDatabase();
        PreparedStatement preparedStatement = null;
        int result = 0;
        String query = "update users set userName = ? where userID = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,userName);
            preparedStatement.setInt(2,userId);
            if(preparedStatement.executeUpdate() > 0){
                result = 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnection.closeStatementAndConnection(preparedStatement,connection);
        }

        return result;
    }

    public int updatePassword(String password, int userId){
        Connection connection = databaseConnection.connectToDatabase();
        PreparedStatement preparedStatement = null;
        int result = 0;
        String query = "update users set userpassword = ? where userID = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, password);
            preparedStatement.setInt(2,userId);
            if(preparedStatement.executeUpdate() > 0){
                result = 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnection.closeStatementAndConnection(preparedStatement,connection);
        }

        return result;
    }

    public int updateUserNameAndPassword(String userName, String password, int userId){
        Connection connection = databaseConnection.connectToDatabase();
        PreparedStatement preparedStatement = null;
        int result = 0;
        String query = "update users set userpassword = ?, userName = ? where userID = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, password);
            preparedStatement.setString(2,userName);
            preparedStatement.setInt(3,userId);
            if(preparedStatement.executeUpdate() > 0){
                result = 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnection.closeStatementAndConnection(preparedStatement,connection);
        }

        return result;
    }
}
