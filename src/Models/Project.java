package Models;

import java.util.Date;

/**
 * Created by Caterina on 4/20/2016.
 */
public class Project {
    private int projectId;
    private String projectName;
    private String projectType;
    private Date deadline;

    public Project(){

    }

    public Project(int projectId, String projectName, String projectType, Date deadline){
        this.projectId = projectId;
        this.projectName = projectName;
        this.projectType = projectType;
        this.deadline = deadline;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Override
    public String toString(){
        String project = "";
        project += projectId + " " + projectName + " " + projectType + " " + deadline.toString() + " " + "\n";
        return project;
    }
}
