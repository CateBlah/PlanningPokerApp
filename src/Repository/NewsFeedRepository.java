package Repository;

import Models.DatabaseConnection;
import Models.NewsFeedItem;
import Models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Caterina on 5/7/2016.
 */
public class NewsFeedRepository {
    private ArrayList<NewsFeedItem> newsFeed = new ArrayList<NewsFeedItem>();
    private DatabaseConnection databaseConnection = new DatabaseConnection();
    private UserRepository userRepository = new UserRepository();
    private UserProjectRepository userProjectRepository = new UserProjectRepository();

    public NewsFeedRepository(){

    }

    public int createNewsFeedMessage(String message){
        int result = 0;
        Connection connection = databaseConnection.connectToDatabase();
        PreparedStatement preparedStatement = null;
        String query = "insert into newsfeedmessage(newsfeedMessage) values (?)";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,message);
            //System.out.println(message);
            if(preparedStatement.executeUpdate() > 0) {
                result = 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            databaseConnection.closeStatementAndConnection(preparedStatement,connection);
        }

        return result;
    }

    public ArrayList<NewsFeedItem> getNewsFeed(){
        ArrayList<NewsFeedItem> news = new ArrayList<NewsFeedItem>();
        Connection connection = databaseConnection.connectToDatabase();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select * from newsfeedmessage";
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                NewsFeedItem newsFeedItem = new NewsFeedItem();
                newsFeedItem.setNewsFeedId(resultSet.getInt("newsfeedMessageId"));
                newsFeedItem.setNewsFeedMessage(resultSet.getString("newsfeedMessage"));
                news.add(newsFeedItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnection.closeConnection(resultSet,preparedStatement,connection);
        }
        return news;
    }

    public int getNewsFeedId(String message){
        int result = 0;
        for(NewsFeedItem newsFeedItem: getNewsFeed()){
            if(newsFeedItem.getNewsFeedMessage().equals(message)){
                result = newsFeedItem.getNewsFeedId();
            }
        }

        return result;
    }

    protected int addUserNewsFeedMessage(String message,int userId){
        int result = 0;
        Connection connection = databaseConnection.connectToDatabase();
        PreparedStatement preparedStatement = null;
        int messageId = getNewsFeedId(message);
        //System.out.println("This is the message id:" + messageId);
        String query = "insert into usernewsfeed(newsfeedMessageId,userID) values(?,?)";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,messageId);
            preparedStatement.setInt(2,userId);
            if(preparedStatement.executeUpdate() > 0){
                result = 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            databaseConnection.closeStatementAndConnection(preparedStatement,connection);
        }
        return result;
    }

    public void addMessageForUsers(String message,String projectName){
        ArrayList<User> usersForProject = userProjectRepository.getMembersOfProject(projectName);
        int result = createNewsFeedMessage(message);
        if(result == 1){
            for(User user: usersForProject){
                int result2 = addUserNewsFeedMessage(message,user.getUserId());
            }
        }
    }

    public ArrayList<String> getUserNews(String username){
        ArrayList<String> userNews = new ArrayList<String>();
        Connection connection = databaseConnection.connectToDatabase();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select newsfeedMessage.newsfeedMessage from newsfeedmessage\n" +
                "INNER join usernewsfeed on newsfeedmessage.newsfeedMessageId = userNewsFeed.newsfeedMessageId \n" +
                "INNER join users on userNewsFeed.userID = users.userID and users.userName = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,username);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                userNews.add(resultSet.getString("newsfeedMessage"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnection.closeConnection(resultSet,preparedStatement,connection);
        }

        return userNews;
    }
}
