package Repository;

import Models.DatabaseConnection;
import Models.Progress;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Caterina on 5/17/2016.
 */
public class ProgressRepository {
    private DatabaseConnection databaseConnection = new DatabaseConnection();

    public ProgressRepository(){

    }

    public ArrayList<Progress> getProgressListForUserStory(int userStoryId){
        Connection connection = databaseConnection.connectToDatabase();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT dayNumber,completedUserStoryPercentage from userstoryprogress where userStoryId = ?";
        ArrayList<Progress> progresses = new ArrayList<Progress>();
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,userStoryId);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Progress currentProgress = new Progress();
                currentProgress.setDayNumber(resultSet.getInt("dayNumber"));
                currentProgress.setCompletedPercentage(resultSet.getInt("completedUserStoryPercentage"));
                currentProgress.setUserStoryId(userStoryId);
                progresses.add(currentProgress);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return progresses;
    }

    public int addProgress(int dayNumber, int completed, int userStoryId){
        int result = 0;
        Connection connection = databaseConnection.connectToDatabase();
        PreparedStatement preparedStatement = null;
        String query = "INSERT into userstoryprogress VALUES (?,?,?)";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,dayNumber);
            preparedStatement.setInt(2,completed);
            preparedStatement.setInt(3,userStoryId);
            if(preparedStatement.executeUpdate() > 0) result = 1;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnection.closeStatementAndConnection(preparedStatement,connection);
        }

        return result;
    }
}
