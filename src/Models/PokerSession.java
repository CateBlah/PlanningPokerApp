package Models;

/**
 * Created by Caterina on 5/15/2016.
 */
public class PokerSession {
    private String pokerSessionDate;
    private int pokerSessionId;
    private String pokerSessionStartTime;
    private int userStoryId;
    private int storyPointTypeId;

    public PokerSession(){

    }


    public String getPokerSessionStartTime() {
        return pokerSessionStartTime;
    }

    public void setPokerSessionStartTime(String pokerSessionStartTime) {
        this.pokerSessionStartTime = pokerSessionStartTime;
    }

    public int getUserStoryId() {
        return userStoryId;
    }

    public void setUserStoryId(int userStoryId) {
        this.userStoryId = userStoryId;
    }

    public int getPokerSessionId() {
        return pokerSessionId;
    }

    public void setPokerSessionId(int pokerSessionId) {
        this.pokerSessionId = pokerSessionId;
    }

    public String getPokerSessionDate() {
        return pokerSessionDate;
    }

    public void setPokerSessionDate(String pokerSessionDate) {
        this.pokerSessionDate = pokerSessionDate;
    }

    public int getStoryPointTypeId() {
        return storyPointTypeId;
    }

    public void setStoryPointTypeId(int storyPointTypeId) {
        this.storyPointTypeId = storyPointTypeId;
    }

    @Override
    public String toString(){
        return pokerSessionId + " " + pokerSessionDate + " " + pokerSessionStartTime + " " + userStoryId + " " + storyPointTypeId + "\n";
    }
}
