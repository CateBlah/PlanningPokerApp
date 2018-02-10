package Repository;

import Models.DatabaseConnection;
import Models.User;
import Validators.Validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Caterina on 5/4/2016.
 */
public class UserProjectRepository {
    private DatabaseConnection databaseConnection = new DatabaseConnection();
    private Validator validator = new Validator();
    private UserRepository userRepository = new UserRepository();
    private ProjectRepository projectRepository = new ProjectRepository();
    public UserProjectRepository(){

    }

    public int addAdminToProject(int projectId, String userName){
        Connection connection = databaseConnection.connectToDatabase();
        PreparedStatement preparedStatement = null;
        int result = 0;
        int userID = userRepository.getUserId(userName);
        //System.out.println(projectId + "-projectID " +userID + "-userID");
        if(checkIfAdminWasSet(projectId,userName) == false){
          //  System.out.println("admin was not set");
            if(projectId > 0 && userID > 0){
                String query = "insert into userproject(projectId,userID) values(?,?)";
                try {
                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setInt(1,projectId);
                    preparedStatement.setInt(2, userID);
                    if(preparedStatement.executeUpdate() > 0);
                    result = 1;
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    databaseConnection.closeStatementAndConnection(preparedStatement,connection);
                }
            }
        }
        return result;
    }

    public ArrayList<User> getMembersExceptCurrentUser(String projectName, String userName){
        Connection connection = databaseConnection.connectToDatabase();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        ArrayList<User> users = new ArrayList<User>();
        int projectId = projectRepository.getProjectId(projectName);

        if(projectId > 0){
            String query = "select u.userName,u.userID,u.email, u.roleId from users u inner join userproject up on u.userID = up.userID and projectId = ? and u.userName != ?";
            try {
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1,projectId);
                preparedStatement.setString(2,userName);
                resultSet = preparedStatement.executeQuery();
                while(resultSet.next()){
                    User user = new User();
                    user.setUserId(resultSet.getInt("userID"));
                    user.setUserName(resultSet.getString("userName"));
                    user.setEmail(resultSet.getString("email"));
                    user.setRoleId(resultSet.getInt("roleId"));
                    users.add(user);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                databaseConnection.closeConnection(resultSet,preparedStatement,connection);
            }
        }
        return users;
    }
    public int addMemberToProject(String projectName, String userEmail){
        Connection connection = databaseConnection.connectToDatabase();
        PreparedStatement preparedStatement = null;
        int result = 0;
        int projectID = projectRepository.getProjectId(projectName);
        int userID = userRepository.getUserIdByEmail(userEmail);
        //System.out.println(userID + "<--this is the id of the inserted user");
        if(projectID > 0 && userID > 0){
            String query = "insert into userproject(userID,projectId) values(?,?)";
            try {
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, userID);
                preparedStatement.setInt(2, projectID);
                if(preparedStatement.executeUpdate() > 0){
                    result = 1;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
    public ArrayList<String> getUserProjects(String username){
        Connection connection = databaseConnection.connectToDatabase();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        ArrayList<String> projectNames = new ArrayList<String>();
        int userID = userRepository.getUserId(username);

        if(userID > 0){
            String query = "select projectName from projects p " +
                    "inner join userproject u on p.projectId = u.projectId and userID = ?";
            try {
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1,userID);
                resultSet = preparedStatement.executeQuery();
                while(resultSet.next()){
                    projectNames.add(resultSet.getString("projectName"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                databaseConnection.closeConnection(resultSet, preparedStatement, connection);
            }
        }

        return projectNames;

    }

    public ArrayList<User> getMembersOfProject(String projectName){
        Connection connection = databaseConnection.connectToDatabase();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        ArrayList<User> users = new ArrayList<User>();
        int projectId = projectRepository.getProjectId(projectName);

        if(projectId > 0){
            String query = "select u.userName,u.userID,u.email from users u inner join userproject up on u.userID = up.userID and projectId = ?";
            try {
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1,projectId);
                resultSet = preparedStatement.executeQuery();
                while(resultSet.next()){
                    User user = new User();
                    user.setUserId(resultSet.getInt("userID"));
                    user.setUserName(resultSet.getString("userName"));
                    user.setEmail(resultSet.getString("email"));
                    users.add(user);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                databaseConnection.closeConnection(resultSet,preparedStatement,connection);
            }
        }
        return users;
    }

    public boolean checkIfAdminWasSet(int projectId, String userName){
        Connection connection = databaseConnection.connectToDatabase();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int userId = userRepository.getUserId(userName);
        String query = "select * from userproject where projectId = ? and userID = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,projectId);
            preparedStatement.setInt(2,userId);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnection.closeConnection(resultSet,preparedStatement,connection);
        }

        return false;
    }

    public int removeMember(String userName, String projectName){
        Connection connection = databaseConnection.connectToDatabase();
        PreparedStatement preparedStatement = null;

        int userId = userRepository.getUserId(userName);
        int projectId = projectRepository.getProjectId(projectName);
        int result = 0;

        String query = "DELETE FROM userproject where userID = ? and projectId = ?";
        //System.out.println("I want to delete:  " + projectId + " " + userId);
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,userId);
            preparedStatement.setInt(2,projectId);
            if(preparedStatement.executeUpdate() > 0){
                result = 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnection.closeStatementAndConnection(preparedStatement, connection);
        }
        return result;
    }

    public boolean checkIfMember(String userName, int projectId){
        Connection connection = databaseConnection.connectToDatabase();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        boolean isMember = false;
        int userId = userRepository.getUserId(userName);
        String query = "select * from userproject where userID = ? and projectId = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,userId);
            preparedStatement.setInt(2,projectId);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                isMember = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnection.closeConnection(resultSet,preparedStatement,connection);
        }

        return isMember;
    }
}
