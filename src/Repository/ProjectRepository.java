package Repository;

import Models.DatabaseConnection;
import Models.Project;
import Validators.Validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Caterina on 5/4/2016.
 */
public class ProjectRepository {
    private DatabaseConnection databaseConnection = new DatabaseConnection();
    private Validator validator = new Validator();
    public ProjectRepository(){

    }

    public int createNewProject(String projectName, String projectType, String deadline){
        Connection connection = databaseConnection.connectToDatabase();
        PreparedStatement preparedStatement = null;

        int result = 0;
        if(validator.invalidProjectType(projectType) == true && validator.invalidProjectName(projectName) == true) {
            result = -3;
        } else if(validator.invalidProjectName(projectName)){
            result = -1;
        } else if(validator.invalidProjectType(projectType)){
            result = -2;
        } else{


            String query = "insert into projects(projectName, projectType, deadline) " + "values(?,?,?)";
            try {
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, projectName);
                preparedStatement.setString(2, projectType);
                preparedStatement.setString(3, deadline);

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

    private ArrayList<Project> getAllProjects(){
        Connection connection = databaseConnection.connectToDatabase();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ArrayList<Project> allProjects = new ArrayList<Project>();
        try {
            statement = connection.prepareStatement("select * from projects");
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                int projectId = resultSet.getInt("projectId");
                String projectName = resultSet.getString("projectName");
                String projectType = resultSet.getString("projectType");
                Date deadline = resultSet.getDate("deadline");

                Project currentProject = new Project(projectId, projectName,projectType,deadline);
                allProjects.add(currentProject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnection.closeConnection(resultSet,statement,connection);
        }



        return allProjects;
    }

    public int addUserToProject(int userId, int projectId, String role){
        Connection connection = databaseConnection.connectToDatabase();
        PreparedStatement preparedStatement = null;
        int result = 0;
        String query = "insert into userproject(userID, projectId, role)" + "values(?,?,?)";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,userId);
            preparedStatement.setInt(2,projectId);
            preparedStatement.setString(3,role);
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

    public int getProjectId(String projectName){
        int projectID = -1;
        Connection connection = databaseConnection.connectToDatabase();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        String query = "select projectId from projects where projectName= ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, projectName);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
                projectID = resultSet.getInt("projectId");
            //System.out.println(projectID + " <-- this is the projectID");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnection.closeConnection(resultSet,preparedStatement,connection);
        }

        return projectID;
    }

    public ArrayList<String> getProjectNames(){
        ArrayList<Project> projects = getAllProjects();
        ArrayList<String> projectNames = new ArrayList<String>();
        for(Project project: projects){
            projectNames.add(project.getProjectName());
        }

        return projectNames;
    }
}
