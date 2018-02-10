package Models;

/**
 * Created by Caterina on 5/7/2016.
 */
public class NewsFeedItem {
    private int newsFeedItemId;
    private String newsFeedMessage;
    private int userId;

    public NewsFeedItem(){

    }
    public NewsFeedItem(int newsFeedId, String newsFeedMessage){
        this.newsFeedItemId = newsFeedId;
        this.newsFeedMessage = newsFeedMessage;
    }


    public int getNewsFeedId() {
        return newsFeedItemId;
    }

    public void setNewsFeedId(int newsFeedId) {
        this.newsFeedItemId = newsFeedId;
    }

    public String getNewsFeedMessage() {
        return newsFeedMessage;
    }

    public void setNewsFeedMessage(String newsFeedMessage) {
        this.newsFeedMessage = newsFeedMessage;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString(){
        String result = this.newsFeedItemId + " " + this.newsFeedMessage + " ";
        return result;
    }
}
