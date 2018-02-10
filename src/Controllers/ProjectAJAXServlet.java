package Controllers;

import Models.User;
import Models.UserStory;
import Repository.*;
import com.google.gson.Gson;

import javax.portlet.ResourceResponse;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by Caterina on 5/6/2016.
 */
@WebServlet(name = "ProjectAJAXServlet")
public class ProjectAJAXServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*response.setContentType("text/plain");
        PrintWriter out = response.getWriter();*/
        String action = request.getParameter("action");
        UserStoryRepository userStoryRepository = new UserStoryRepository();
        UserRepository userRepository = new UserRepository();
        ProjectRepository projectRepository = new ProjectRepository();
        NewsFeedRepository newsFeedRepository = new NewsFeedRepository();

        String userStoryName = request.getParameter("userStoryName");
        String userStoryDescription = request.getParameter("userStoryDescription");
        String userStoryPriority = request.getParameter("userStoryPriority");
        String assigneeName = request.getParameter("assigneeName");
        int assigneeId = userRepository.getUserId(assigneeName);

        String projectName = String.valueOf(request.getSession().getAttribute("projectName"));
        int projectId = projectRepository.getProjectId(projectName);
        int result;
        if(action.equals("addUserStory")){
            result = userStoryRepository.addUserStory(userStoryName,userStoryDescription,projectName,userStoryPriority,assigneeId);
            newsFeedRepository.addMessageForUsers(request.getSession().getAttribute("currentUsername") + " has added a new user story: " + userStoryName + "for the project " + projectName + ". Assignee: " + assigneeName,projectName);
            int userStoryId = userStoryRepository.getUserStoryId(userStoryName);
            UserStory userStory = new UserStory();
            userStory.setUserStoryId(userStoryId);
            userStory.setUserStoryDescription(userStoryDescription);
            userStory.setUserStoryName(userStoryName);
            userStory.setPriority(userStoryRepository.getPriorityID(userStoryPriority));
            userStory.setProjectId(projectId);
            userStory.setAssigneeId(assigneeId);
            String myJson = new Gson().toJson(userStory);
            //System.out.println(myJson);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(myJson);
        } else if(action.equals("addProjectMember")){
            //System.out.println("I am in addProjectMember");
            UserProjectRepository userProjectRepository = new UserProjectRepository();
            String email = request.getParameter("addedUserEmail");
            int addMemberResult = userProjectRepository.addMemberToProject(projectName, email);
            if(addMemberResult == 0) {
                String errorMessage = "There is no user associated with this e-mail address!";
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                String errorJson = new Gson().toJson(errorMessage);
              //  System.out.println(errorJson);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(errorJson);
            } else {
                newsFeedRepository.addMessageForUsers(request.getSession().getAttribute("currentUsername") + " has added " + userRepository.getUserByEmail(email).getUserName() + " to the project " + projectName, projectName);
                //System.out.println(email);
                String responseJson = new Gson().toJson(userRepository.getUserNameByEmail(email));
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(responseJson);
            }
        }

        if(action.equals("removeMember")){
            String memberToDelete = request.getParameter("memberToDelete");

            //System.out.println(memberToDelete + "<--- i want to delete this member " + request.getSession().getAttribute("projectName"));
            UserProjectRepository userProjectRepository = new UserProjectRepository();
            int myResult = userProjectRepository.removeMember(memberToDelete, String.valueOf(request.getSession().getAttribute("projectName")));
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserStoryRepository userStoryRepository = new UserStoryRepository();
        List<UserStory> userStories = userStoryRepository.getUserStoriesForAProject(String.valueOf(request.getSession().getAttribute("projectName")));
        String crole=request.getParameter("currentUserrole");
        String cUsername = (String) request.getSession().getAttribute("currentUsername");
        //request.getSession().setAttribute("cRole",crole);
       // request.getSession().setAttribute("cUsername",cUsername);
        String json = new Gson().toJson(userStories);
        String action = request.getParameter("action");
        //System.out.println(json);
        if(action.equals("getUserStories")) {
          //  System.out.println("I am here");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        }

        if(action.equals("getMembers")){
            String projectName = String.valueOf(request.getSession().getAttribute("projectName"));
            UserProjectRepository userProjectRepository = new UserProjectRepository();
            List<User> projectMembers = userProjectRepository.getMembersExceptCurrentUser(projectName,cUsername);
            //System.out.println(cUsername);
            String myJson = new Gson().toJson(projectMembers);
            //System.out.println(myJson);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(myJson);
        }

    }
}
