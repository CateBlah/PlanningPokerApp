package Models;

/**
 * Created by Caterina on 5/3/2016.
 */
public class Estimation {
    private String userName;
    private int pokerSessionId;
    private String pokerPlanningValue;

    public Estimation(){

    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPokerPlanningValue() {
        return pokerPlanningValue;
    }

    public void setPokerPlanningValue(String pokerPlanningValue) {
        this.pokerPlanningValue = pokerPlanningValue;
    }


    public int getPokerSessionId() {
        return pokerSessionId;
    }

    public void setPokerSessionId(int pokerSessionId) {
        this.pokerSessionId = pokerSessionId;
    }

    @Override
    public String toString(){
        String result = "";
        result += userName + " " + pokerSessionId + " " + pokerPlanningValue + "\n";
        return result;
    }
}
