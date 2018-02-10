package Controllers;

import Repository.NewsFeedRepository;
import Repository.UserRepository;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Caterina on 5/6/2016.
 */
@WebServlet(name = "AccountSettingsController")
public class AccountSettingsController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");
        UserRepository userRepository = new UserRepository();
        int result;
        int userID = userRepository.getUserId(String.valueOf(request.getSession().getAttribute("userName")));
        //System.out.println(userID);
        //System.out.println(action);

        if (action.equals("edit")) {
            String userName = request.getParameter("userName");
            String password = request.getParameter("password");
            //System.out.println(userName.isEmpty() + " " + password.isEmpty());

            if((userName != null && !userName.isEmpty()) && (password != null && !password.isEmpty())){
                result = userRepository.updateUserNameAndPassword(userName, password, userID);
                if(result == 1){
                    out.println("Username and password updated successfully!");
                    request.getSession().setAttribute("userName", userName);
                } else {
                    //System.out.println("An error occurred.");
                }
            }
            else if(password != null && !password.isEmpty()){
                result = userRepository.updatePassword(password, userID);
                if(result == 1){
                    out.println("Password updated successfully");
                } else {
                    out.println("An error occurred");
                }
            } else if (userName != null && !userName.isEmpty()){
               result = userRepository.updateUsername(userName, userID);
                if(result == 1){
                    out.println("Username updated successfully");
                }
            }


        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        NewsFeedRepository newsFeedRepository = new NewsFeedRepository();
        String username = request.getParameter("currentUsername");
        String action = request.getParameter("action");

        List<String> newsFeed = newsFeedRepository.getUserNews(username);

        //System.out.println(username + " " + action);
        if(action.equals("loadNewsFeed")) {
            String myJson = new Gson().toJson(newsFeed);
            //System.out.println(myJson);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(myJson);
        }
    }
}
