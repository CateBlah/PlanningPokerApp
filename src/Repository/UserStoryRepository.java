package Repository;

import Models.DatabaseConnection;
import Models.UserStory;
import Validators.Validator;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Caterina on 4/23/2016.
 */
public class UserStoryRepository {
    private Connection connection;
    private Validator validator = new Validator();
    private ArrayList<UserStory> userStories = new ArrayList<UserStory>();
    private DatabaseConnection databaseConnection = new DatabaseConnection();
    public UserStoryRepository(){
        connect();
    }

    private void connect(){
        try {
            Class.forName("org.gjt.mm.mysql.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/pokerplanning", "root", "eminem_8");
            // System.out.println(connection.toString());

            // statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int addUserStory(String userStoryName, String userStoryDescription, String projectName, String priority, int assigneeId){
        int result = 0;
        ProjectRepository projectRepository = new ProjectRepository();
        int projectId = projectRepository.getProjectId(projectName);
        int priorityId = getPriorityID(priority);
        //System.out.println(priorityId + "<---- priority ID");
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement("insert into userstories(projectId, userStoryName, userStoryDescription,priorityId,assigneeId) values(?,?,?,?,?)");
            preparedStatement.setInt(1, projectId);
            preparedStatement.setString(2, userStoryName);
            preparedStatement.setString(3, userStoryDescription);
            preparedStatement.setInt(4, priorityId);
            preparedStatement.setInt(5,assigneeId);
            if (preparedStatement.executeUpdate() > 0) {
                result = 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public int getPriorityID(String priority) {
        int priorityId = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select priorityId from priority where priorityName = ?");
            preparedStatement.setString(1, priority);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                priorityId = resultSet.getInt("priorityId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return priorityId;
    }

    public ArrayList<UserStory> getUserStoriesForAProject(String projectName){
        ProjectRepository projectRepository = new ProjectRepository();
        int projectID = projectRepository.getProjectId(projectName);
        String query = "select * from userstories where projectId = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, projectID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int userStoryId = resultSet.getInt("userStoryId");
                int projectId = projectID;
                String userStoryName = resultSet.getString("userStoryName");
                int estimatedDifficulty = resultSet.getInt("estimatedDifficulty");
                String userStoryDescription = resultSet.getString("userStoryDescription");
                UserStory currentUserStory = new UserStory(userStoryId,projectId,userStoryName,estimatedDifficulty,userStoryDescription);
                currentUserStory.setPriority(resultSet.getInt("priorityId"));
                userStories.add(currentUserStory);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userStories;
    }

    public UserStory getUserStoryNameAndDescriptionById(int userStoryId){
        UserStory userStory = new UserStory();
        String query = "select userStoryName,userStoryDescription from userstories where userStoryId = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,userStoryId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                userStory.setUserStoryName(resultSet.getString("userStoryName"));
                userStory.setUserStoryDescription(resultSet.getString("userStoryDescription"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userStory;
    }


    public int getUserStoryId(String userStoryName) {
        Connection connection = databaseConnection.connectToDatabase();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        int userStoryId = 0;
        String query = "select userStoryId from userstories where userStoryName = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userStoryName);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                userStoryId = resultSet.getInt("userStoryId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userStoryId;
    }

    public int getProjectForUserStory(int userStoryId){
        int result = 0;
        Connection connection = databaseConnection.connectToDatabase();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select projectId from userstories where userStoryId = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,userStoryId);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                result = resultSet.getInt("projectId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnection.closeConnection(resultSet,preparedStatement,connection);
        }

        return result;
    }


    public int addAssignee(String userName, String userStoryName){
        UserRepository userRepository = new UserRepository();
        UserProjectRepository userProjectRepository = new UserProjectRepository();
        int userId = userRepository.getUserId(userName);
        int userStoryId = getUserStoryId(userStoryName);
        int projectId = getProjectForUserStory(userStoryId);

        int result = 0;

        Connection connection = databaseConnection.connectToDatabase();
        PreparedStatement preparedStatement = null;

        if(userProjectRepository.checkIfMember(userName,projectId)) {
            String query = "update userstories set assigneeId = ? WHERE userStoryId = ?";

            try {
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, userId);
                preparedStatement.setInt(2, userStoryId);
                if (preparedStatement.executeUpdate() > 0) {
                    result = 1;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public int getAssigneeId(String userStoryName){
        int userStoryId = getUserStoryId(userStoryName);
        int assigneeId = 0;
        Connection connection = databaseConnection.connectToDatabase();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select assigneeId from userstories WHERE userStoryId = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,userStoryId);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                if(!resultSet.wasNull()){
                    assigneeId = resultSet.getInt("assigneeId");

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnection.closeConnection(resultSet,preparedStatement,connection);
        }

       // System.out.println(assigneeId + " this is the assigneeID.");
        return assigneeId;
    }

    public boolean checkIfUserIsAssignee(String userName, int userStoryId){
        UserRepository userRepository = new UserRepository();
        int userId = userRepository.getUserId(userName);
        boolean isAssignee = false;
        Connection connection = databaseConnection.connectToDatabase();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT assigneeId FROM userstories WHERE userStoryId = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,userStoryId);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                if(userId == resultSet.getInt("assigneeID")){
                    isAssignee = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isAssignee;
    }
}
