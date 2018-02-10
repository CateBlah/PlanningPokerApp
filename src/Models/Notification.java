package Models;

/**
 * Created by Caterina on 5/3/2016.
 */
public class Notification {
    private int notificationId, userId;
    private String notificationMessage;

    public Notification(){

    }

    public Notification(int notificationId, String notificationMessage, int userId){
        this.notificationId = notificationId;
        this.notificationMessage = notificationMessage;
        this.userId = userId;
    }
    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public String getNotificationMessage() {
        return notificationMessage;
    }

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    @Override
    public String toString(){
        String result="";
        result+= notificationId + " " + notificationMessage + "\n";
        return result;
    }


}
