package Models;

/**
 * Created by Caterina on 4/23/2016.
 */
public class UserStory {
    private String userStoryName, userStoryDescription;
    private int userStoryId,projectId, estimatedDifficulty, priorityId;
    private int assigneeId;
    public UserStory(){

    }

    public UserStory(int userStoryId, String userStoryName, String userStoryDescription, int priorityId){
        this.userStoryId = userStoryId;
        this.userStoryName = userStoryName;
        this.userStoryDescription = userStoryDescription;
        this.priorityId = priorityId;
    }

    public UserStory(int userStoryId, int projectId, String userStoryName, int estimatedDifficulty, String userStoryDescription){
        this.userStoryId = userStoryId;
        this.projectId = projectId;
        this.userStoryName = userStoryName;
        this.estimatedDifficulty = estimatedDifficulty;
        this.userStoryDescription = userStoryDescription;
    }
    public String getUserStoryName() {
        return userStoryName;
    }

    public void setUserStoryName(String userStoryName) {
        this.userStoryName = userStoryName;
    }

    public String getUserStoryDescription() {
        return userStoryDescription;
    }

    public void setUserStoryDescription(String userStoryDescription) {
        this.userStoryDescription = userStoryDescription;
    }

    public int getUserStoryId() {
        return userStoryId;
    }

    public void setUserStoryId(int userStoryId) {
        this.userStoryId = userStoryId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }


    public int getEstimatedDifficulty() {
        return estimatedDifficulty;
    }

    public void setEstimatedDifficulty(int estimatedDifficulty) {
        this.estimatedDifficulty = estimatedDifficulty;
    }

    public int getPriority() {
        return priorityId;
    }

    public void setPriority(int priority) {
        this.priorityId = priority;
    }

    public int getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(int assigneeId) {
        this.assigneeId = assigneeId;
    }

    @Override
    public String toString(){
        String task = "";
        task += userStoryId + " "  + projectId +" " + userStoryName + " " + userStoryDescription + " " + estimatedDifficulty + " " +  assigneeId + "\n";
        return task;
    }
}
