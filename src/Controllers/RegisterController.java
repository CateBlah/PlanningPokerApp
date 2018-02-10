package Controllers;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import Repository.UserProjectRepository;
import Repository.UserRepository;

/**
 * Created by Caterina on 5/4/2016.
 */
@WebServlet(name = "RegisterController")
public class RegisterController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName, userPassword, email, roleName;
        HttpSession session = request.getSession();

        userName = request.getParameter("name");
        userPassword = request.getParameter("passwordCreate");
        email = request.getParameter("email");
        roleName = request.getParameter("userRole");
        //System.out.println(roleName);
        UserRepository userRepository = new UserRepository();
        UserProjectRepository userProjectRepository = new UserProjectRepository();
        RequestDispatcher requestDispatcher = null;



        if(userName.equals("") || email.equals("") || userPassword.equals("") || roleName == null) {
            request.setAttribute("noEmpty", "Empty fields are not allowed!");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } else {
            int result = userRepository.createUser(userName, userPassword, email, roleName);
            if (result == 1) {

                if (roleName.equals("Program Manager")) {
                    session.setAttribute("userName", userName);
                    session.setAttribute("userRole", roleName);
                    requestDispatcher = request.getRequestDispatcher("/programManagerHome.jsp");
                    requestDispatcher.forward(request, response);
                } else {
                    session.setAttribute("userName", userName);
                    session.setAttribute("userRole", roleName);
                    requestDispatcher = request.getRequestDispatcher("/programManagerHome.jsp");
                    requestDispatcher.forward(request, response);
                }
            } else {
                if (result == 0) {
          //          System.out.println("user was not added! :(");
                } else if (result == -1) {
                    //e-mail is already taken
                    request.setAttribute("erroremail", "E-Mail Already Exists.");

                } else if (result == -2) {
                    request.setAttribute("errorusername", "User Name Already Exists.");

                } else if (result == -3) {
                    request.setAttribute("errorusernameandemail", "User Name and email already exist.");

                } else if (result == -4) {
                    request.setAttribute("errorRole", "Please select a role.");
                }
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
