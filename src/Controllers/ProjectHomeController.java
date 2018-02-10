package Controllers;

import Models.UserStory;
import Models.User;
import Repository.PokerSessionRepository;
import Repository.UserStoryRepository;
import Repository.UserProjectRepository;
import Repository.UserRepository;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Caterina on 4/23/2016.
 */
@WebServlet(name = "ProjectHomeController")
public class ProjectHomeController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String email = request.getParameter("addedUserEmail");
        String taskName,taskDescription;
        String projectName = String.valueOf(session.getAttribute("projectName"));
        String currentUsername = String.valueOf(request.getSession().getAttribute("userName"));
        String currentUserrole = String.valueOf(request.getSession().getAttribute("userRole"));

        request.getSession().setAttribute("currentUsername", currentUsername);
        request.getSession().setAttribute("currentUserrole",currentUserrole);
      //  System.out.println(projectName + "this is the project's name");
        String priority = request.getParameter("userStoryPriority");
       // System.out.println(priority + " priority");

        UserProjectRepository userProjectRepository = new UserProjectRepository();
        ArrayList<User> addedUsers = userProjectRepository.getMembersOfProject(projectName);


        UserStoryRepository userStoryRepository = new UserStoryRepository();
        ArrayList<UserStory> userStories = userStoryRepository.getUserStoriesForAProject(projectName);

        UserRepository userRepository = new UserRepository();

        if(request.getParameter("addMemberButton") != null)
        {
            if(userProjectRepository.addMemberToProject(projectName,email) > 0) {
                //System.out.println("Member added!");
                addedUsers.add(userRepository.getUserByEmail(email));
                //mut-o afara din if daca nu iti apare.
                request.getSession().setAttribute("addedUsers",addedUsers);
                request.getRequestDispatcher("/projectManagerHome.jsp").forward(request,response);
            } else {
                request.setAttribute("noEmail","No user has such e-mail address.");
                request.getRequestDispatcher("/projectManagerHome.jsp").forward(request,response);
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        //System.out.println("I get here");
        String redirect = "/userStory.jsp";
        UserRepository userRepository = new UserRepository();
        UserStoryRepository userStoryRepository = new UserStoryRepository();
        String userStoryId = request.getParameter("userStoryId");
        String userStoryDescription = request.getParameter("userStoryDescription");
        String username = request.getParameter("username");
        String role = userRepository.getUserRole(username);
        String userStoryName = userStoryRepository.getUserStoryNameAndDescriptionById(Integer.parseInt(userStoryId)).getUserStoryName();
        System.out.println(userStoryName);
        int assigneeId = userStoryRepository.getAssigneeId(userStoryName);
        String assigneeUsername = userRepository.getUsernameById(assigneeId);


        //System.out.println(role);
        PokerSessionRepository pokerSessionRepository = new PokerSessionRepository();
        ArrayList<String> storyPointValues = pokerSessionRepository.getStoryPointValuesForUserStory(Integer.valueOf(userStoryId));
        String storyPointType = pokerSessionRepository.getStoryPointTypeForUserStory(Integer.valueOf(userStoryId));
        int pokerSessionId = pokerSessionRepository.getPokerSessionIdForUserStory(Integer.valueOf(userStoryId));
       // System.out.println(storyPointValues);
        //System.out.println(storyPointType);
        boolean isCurrentUserAssignee = userStoryRepository.checkIfUserIsAssignee(username,Integer.valueOf(userStoryId));
        if(isCurrentUserAssignee == true){
            session.setAttribute("currentUserIsAssignee","true");
            System.out.println(session.getAttribute("currentUserIsAssignee") + " <---- current user is assignee.");
        } else {
            session.setAttribute("currentUserIsAssignee","false");
            System.out.println(session.getAttribute("currentUserIsAssignee") + " <---- current user is not assignee");
        }

        session.setAttribute("assignee",assigneeUsername);
        session.setAttribute("cusername", username);
        session.setAttribute("crole",role);
        session.setAttribute("userStoryId", userStoryId);
        session.setAttribute("pokerSessionId",pokerSessionId);
        session.setAttribute("storyPointValues",storyPointValues);
        session.setAttribute("storyPointType", storyPointType);
        //System.out.println(session.getAttribute("userStoryId") + " " + pokerSessionId);
        session.setAttribute("taskDescription", userStoryDescription);
        session.setAttribute("userStoryName",userStoryRepository.getUserStoryNameAndDescriptionById(Integer.valueOf(userStoryId)).getUserStoryName());
        System.out.println(session.getAttribute("assignee") + " assignee");
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(redirect);
        dispatcher.forward(request, response);
    }
}
