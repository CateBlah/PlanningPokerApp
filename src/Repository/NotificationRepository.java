package Repository;

import Models.DatabaseConnection;
import Models.Notification;

import java.sql.*;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by Caterina on 5/3/2016.
 */
public class NotificationRepository {
    private DatabaseConnection databaseConnection = new DatabaseConnection();

    public NotificationRepository(){

    }

    public int createNotificationMessage(String notificationMessage){
        Connection connection = databaseConnection.connectToDatabase();
        PreparedStatement preparedStatement = null;
        int result = 0;
        String query = "INSERT INTO notificationmessage(notificationMessage) values ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,notificationMessage);
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

