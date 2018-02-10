package Controllers;

import Models.UserStory;
import Models.User;
import Repository.NotificationRepository;
import Repository.ProjectRepository;
import Repository.UserStoryRepository;
import Repository.UserProjectRepository;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by Caterina on 4/20/2016.
 */
@WebServlet(name = "UserHomeController")
public class UserHomeController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String projectName, projectType, deadline, selectedProject, currentUsername, currentUserrole;
        UserStoryRepository userStoryRepository = new UserStoryRepository();
        projectName = request.getParameter("projectName");
        projectType = request.getParameter("projectType");
        deadline = request.getParameter("projectDeadline");
        currentUsername = request.getParameter("currentUsername");
        currentUserrole = request.getParameter("currentUserrole");

        ProjectRepository projectRepository = new ProjectRepository();
        UserProjectRepository userProjectRepository = new UserProjectRepository();
        RequestDispatcher requestDispatcher = null;
        ArrayList<String> projects = new ArrayList<String>();

        request.getSession().setAttribute("sessionProjectName", projectName);
        request.getSession().setAttribute("projectType", projectType);
        request.getSession().setAttribute("deadline", deadline);
        request.getSession().setAttribute("currentUsername",currentUsername);
        //System.out.println(currentUsername + " " + currentUserrole);
        request.getSession().setAttribute("currentUserrole", currentUserrole);
        //System.out.println("in doPost");
        int result = projectRepository.createNewProject(projectName, projectType, deadline);
        projects = userProjectRepository.getUserProjects(String.valueOf(request.getSession().getAttribute("projectName")));
        if (result == -3) {
            request.setAttribute("emptyProjectNameAndType", "Project name and type cannot be empty!");
            request.getRequestDispatcher("/programManagerHome.jsp").forward(request, response);
        } else if (result == -2) {
            request.setAttribute("emptyProjectType", "Project type cannot be empty!");
            request.getRequestDispatcher("/programManagerHome.jsp").forward(request, response);
        } else if (result == -1) {
            request.setAttribute("emptyProjectName", "Project name cannot be empty!");
            request.getRequestDispatcher("/programManagerHome.jsp").forward(request, response);
        } else if (result == 1) {
            //user that created the project will automatically become the admin(project manager) of the project
            int addAdminResult = userProjectRepository.addAdminToProject(projectRepository.getProjectId(projectName), String.valueOf(request.getSession().getAttribute("userName")));
          //  System.out.println(projectName + "<-- this is the prject name");
            request.getSession().setAttribute("projectManager", String.valueOf(request.getSession().getAttribute("userName")));
            request.getSession().setAttribute("projectName", projectName);
            projects.add(projectName);
            request.getSession().setAttribute("projectNames", projects);
            ArrayList<UserStory> userStories = userStoryRepository.getUserStoriesForAProject(projectName);
            ArrayList<User> users = userProjectRepository.getMembersOfProject(projectName);
            request.getSession().setAttribute("projectTasks", userStories);
            request.getSession().setAttribute("projectName", projectName);
            request.getSession().setAttribute("addedUsers", users);
            requestDispatcher = request.getRequestDispatcher("/projectManagerHome.jsp");
            requestDispatcher.forward(request, response);
        }

        requestDispatcher.forward(request, response);

    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UserStoryRepository userStoryRepository = new UserStoryRepository();
        UserProjectRepository userProjectRepository = new UserProjectRepository();
        //HttpSession session = request.getSession();

        String currentUsername = request.getParameter("user");
        String currentUserRole = request.getParameter("role");
        //System.out.println(currentUsername + "  " + currentUserRole + " " + request.getSession().getAttribute("currentUsername") + request.getSession().getAttribute("currentuserrole"));
        //System.out.println((request.getSession().getAttribute("currentUsername") == null) + "<-currentUserNAme is null");
        //System.out.println(currentUsername + " " + currentUserRole);

            request.getSession().setAttribute("currentUsername", currentUsername);
            request.getSession().setAttribute("currentUserrole", currentUserRole);
            String projectName = request.getParameter("projectName");
            //System.out.println(projectName + "<--- projectName from doPost");
            ArrayList<UserStory> userStories = userStoryRepository.getUserStoriesForAProject(projectName);
            ArrayList<User> users = userProjectRepository.getMembersOfProject(projectName);
            request.getSession().setAttribute("addedUsers", users);
            String parameter = "/projectManagerHome.jsp";
            request.getSession().setAttribute("projectTasks", userStories);
            request.getSession().setAttribute("projectName", projectName);
          //  System.out.println(request.getParameter("action") + " " + request.getParameter("userName") + "<-- user that I want to delete");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(parameter);
            dispatcher.forward(request, response);


    }

}
