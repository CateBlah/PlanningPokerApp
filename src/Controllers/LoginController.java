package Controllers;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

import Repository.NewsFeedRepository;
import Repository.UserProjectRepository;
import Repository.UserRepository;
/**
 * Created by Caterina on 5/4/2016.
 */
@WebServlet(name = "LoginController")
public class LoginController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName, password;
        NewsFeedRepository newsFeedRepository = new NewsFeedRepository();
        HttpSession session = request.getSession();
        userName = request.getParameter("username");
        password = request.getParameter("password");
        RequestDispatcher requestDispatcher;


        UserProjectRepository userProjectRepository = new UserProjectRepository();
        UserRepository userRepository = new UserRepository();
        //System.out.println(request.getSession().getId().toString() + "<--- this is the session id");


        int result = userRepository.authenticate(userName, password);
        //success
        if (result == 1) {
            //System.out.println(userName + " " + password);
            ArrayList<String> projectNames = userProjectRepository.getUserProjects(userName);

            request.setAttribute("projectNames",projectNames);
            if (userRepository.checkIfManager(userName)) {
                //System.out.println("Good: " + newsFeedRepository.getUserNews("Bono"));
                //System.out.println(userProjectRepository.getMembersOfProject("BonoTest"));
                session.setAttribute("userName", userName);
                session.setAttribute("userRole","Program Manager");
                //response.sendRedirect("/programManagerHome.jsp");
                request.getRequestDispatcher("/programManagerHome.jsp").forward(request,response);
            }
            else {
                session.setAttribute("userName", userName);
                session.setAttribute("userRole",userRepository.getUserRole(userName));
                request.getRequestDispatcher("/programManagerHome.jsp").forward(request,response);
                // response.sendRedirect("/userHome.jsp");
            }
        } else {
            if (result == -1) {
                //requestDispatcher = request.getRequestDispatcher("/error.jsp");
                request.setAttribute("invalidPassword", "Invalid password");
            } else if (result == -2) {
                request.setAttribute("invalidUsername", "Invalid username");
            } else if (result == 0) {
                request.setAttribute("invalidUsernameAndPassword", "Invalid username and password");
            }

            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
