package Validators;

import Models.User;

import java.util.ArrayList;

/**
 * Created by Caterina on 4/19/2016.
 */
public class Validator {

    public Validator(){

    }
    public boolean userEmailExists(ArrayList<User> users, String email){
        for(User u: users){
            if(email.equals(u.getEmail())){
                return true;
            }
        }

        return false;
    }

    public boolean userNameExists(ArrayList<User> users, String userName){
        for(User u: users){
            if(userName.equals(u.getUserName())){
                return true;
            }
        }

        return false;
    }

    public boolean invalidProjectName(String projectName){
        if(projectName.equals("") || projectName.equals("Enter project name"))
            return true;
        return false;
    }

    public boolean invalidProjectType(String projectType){
        if(projectType.equals("") || projectType.equals("Enter project type"))
            return true;
        return false;
    }

    public boolean invalidTaskName(String taskName){
        if(taskName == null){
            return true;
        }

        return false;
    }

    public boolean invalidPriority(String priorityName){
        if(priorityName == null){
            return true;
        }
        else return false;
    }


}
