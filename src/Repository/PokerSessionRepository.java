package Repository;

import Models.DatabaseConnection;
import Models.Estimation;
import Models.PokerSession;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Caterina on 5/15/2016.
 */
public class PokerSessionRepository {
    private DatabaseConnection databaseConnection = new DatabaseConnection();
    private UserStoryRepository userStoryRepository = new UserStoryRepository();

    public PokerSessionRepository(){

    }

    public int createPokerSession(String pokerSessionDate, String pokerSessionStartTime, int userStoryId, String storyPointType){
        int result = 0;
        int storyPointTypeId = getStoryPointTypeId(storyPointType);
        Connection connection = databaseConnection.connectToDatabase();
        PreparedStatement preparedStatement = null;
        String query = "insert into pokersession(pokerSessionDate, pokerSessionStartTime, userstoryId,storyPointTypeId) values (?,?,?,?)";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,pokerSessionDate);
            preparedStatement.setString(2,pokerSessionStartTime);
            preparedStatement.setInt(3, userStoryId);
            preparedStatement.setInt(4,storyPointTypeId);
            if(preparedStatement.executeUpdate() > 0){
                result = 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            databaseConnection.closeStatementAndConnection(preparedStatement, connection);
        }
        return result;
    }

    public int getStoryPointTypeId(String storyPointType){
        int storyPointTypeId = 0;
        Connection connection = databaseConnection.connectToDatabase();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String query = "select storyPointTypeId from storypointtype where storyPointType = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, storyPointType);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                storyPointTypeId = resultSet.getInt("storyPointTypeId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnection.closeConnection(resultSet, preparedStatement, connection);
        }

        return storyPointTypeId;
    }

    public ArrayList<String> getStoryPointValuesByType(String storyPointType){
        ArrayList<String> storyPointValues = new ArrayList<String>();
        int storyPointId = getStoryPointTypeId(storyPointType);

        Connection connection = databaseConnection.connectToDatabase();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select pv.pokerPlanningValue from pokerplanning.pokerplanningvalues pv\n" +
                "INNER JOIN storypointtype sp on sp.storyPointTypeId = pv.storyPointTypeId and sp.storyPointTypeId = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,storyPointId);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                storyPointValues.add(resultSet.getString("pokerPlanningValue"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return storyPointValues;
    }

    public PokerSession getPokerSessionForUserStory(String userStoryName){
        int userStoryId = userStoryRepository.getUserStoryId(userStoryName);
        PokerSession pokerSession = new PokerSession();
        
        Connection connection = databaseConnection.connectToDatabase();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String query = "select * from pokersession where userstoryId = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,userStoryId);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                pokerSession.setPokerSessionId(resultSet.getInt("pokerSessionId"));
                pokerSession.setPokerSessionDate(resultSet.getString("pokerSessionDate"));
                pokerSession.setPokerSessionStartTime(resultSet.getString("pokerSessionStartTime"));
                pokerSession.setUserStoryId(userStoryId);
                pokerSession.setStoryPointTypeId(resultSet.getInt("storyPointTypeId"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pokerSession;
    }

    public ArrayList<String> getStoryPointValuesForUserStory(int userStoryId){
        ArrayList<String> storyPointValues = new ArrayList<String>();

        Connection connection = databaseConnection.connectToDatabase();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "\n" +
                "select pv.pokerPlanningValue from pokerplanning.pokerplanningvalues pv\n" +
                "inner join pokersession ps on ps.storyPointTypeId = pv.storyPointTypeId and ps.userstoryId = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,userStoryId);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                storyPointValues.add(resultSet.getString("pokerPlanningValue"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return storyPointValues;
    }

    public String getStoryPointTypeForUserStory(int userStoryId){
        String storyPointType = "";

        Connection connection = databaseConnection.connectToDatabase();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "\n" +
                "select sp.storyPointType from pokerplanning.storypointtype sp\n" +
                "inner join pokersession ps on ps.storyPointTypeId = sp.storyPointTypeId and ps.userstoryId = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,userStoryId);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                storyPointType = resultSet.getString("storyPointType");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return storyPointType;
    }

    public int addEstimation(int userId, int pokerSessionId, String pokerPlanningValue, String storyPointType){
        int result = 0;
        int storyPointTypeId = getStoryPointTypeId(storyPointType);
        int pokerPlanningValueId = getPokerValueIdByName(pokerPlanningValue, storyPointTypeId);
        Connection connection = databaseConnection.connectToDatabase();
        PreparedStatement preparedStatement = null;
        boolean alreadyExists = checkIfEstimationExists(userId,pokerSessionId);

        //if user with userId has already made his estimation, then he should not be able to add another one.
        if(alreadyExists == true){
            result = -1;
        } else {
            String query = "insert into estimation(userId,pokerSessionId,pokerPlanningValueId) values (?,?,?)";

            try {
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, userId);
                preparedStatement.setInt(2, pokerSessionId);
                preparedStatement.setInt(3, pokerPlanningValueId);
                if (preparedStatement.executeUpdate() > 0) {
                    result = 1;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                databaseConnection.closeStatementAndConnection(preparedStatement, connection);
            }
        }
        return result;
    }

    private int getPokerValueIdByName(String pokerPlanningValue, int storyPointTypeId) {
        int result = 0;
        Connection connection = databaseConnection.connectToDatabase();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select pokerPlanningValueId from pokerplanningvalues where pokerPlanningValue = ? and storyPointTypeId = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,pokerPlanningValue);
            preparedStatement.setInt(2,storyPointTypeId);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                result = resultSet.getInt("pokerPlanningValueId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public int getPokerSessionIdForUserStory(int userStoryId){
        int pokerSessionId = 0;
        Connection connection = databaseConnection.connectToDatabase();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String query = "select pokerSessionId from pokersession where userstoryId = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userStoryId);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                pokerSessionId = resultSet.getInt("pokerSessionId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            databaseConnection.closeConnection(resultSet,preparedStatement,connection);
        }

        return pokerSessionId;
    }

    public ArrayList<Estimation> getEstimationsForPokerSession(int pokerSessionId){
        ArrayList<Estimation> estimations = new ArrayList<Estimation>();
        Connection connection = databaseConnection.connectToDatabase();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT u.userName, pv.pokerPlanningValue, estimation.pokerSessionId from users u\n" +
                "inner join estimation on u.userID = estimation.userId\n" +
                "inner join pokerplanningvalues pv on pv.pokerPlanningValueId = estimation.pokerPlanningValueId where estimation.pokerSessionId = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,pokerSessionId);

            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Estimation currentEstimation = new Estimation();
                currentEstimation.setUserName(resultSet.getString("userName"));
                currentEstimation.setPokerSessionId(resultSet.getInt("pokerSessionId"));
                currentEstimation.setPokerPlanningValue(resultSet.getString("pokerPlanningValue"));
                estimations.add(currentEstimation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return estimations;
    }

    private boolean checkIfEstimationExists(int userId, int pokerSessionId){
        boolean exists = false;
        Connection connection = databaseConnection.connectToDatabase();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select * from estimation where userId = ? and pokerSessionId = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,userId);
            preparedStatement.setInt(2,pokerSessionId);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                exists = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exists;
    }

    public int getPokerSessionIdByUserStory(int userStoryId){
        int result = 0;
        Connection connection = databaseConnection.connectToDatabase();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select pokerSessionId from pokersession where userstoryId = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,userStoryId);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                result = resultSet.getInt("pokerSessionId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
