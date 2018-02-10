package Controllers;

import Models.Estimation;
import Models.PokerSession;
import Repository.PokerSessionRepository;
import Repository.UserRepository;
import Repository.UserStoryRepository;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Caterina on 5/5/2016.
 */
@WebServlet(name = "UserStoryController")
public class UserStoryController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = (HttpSession) request.getSession();
        PokerSessionRepository pokerSessionRepository = new PokerSessionRepository();

        if(action.equals("invite")){
            UserStoryRepository userStoryRepository = new UserStoryRepository();
            String pokerSessionDate = request.getParameter("pokerSessionDate");
            String pokerSessionStartTime = request.getParameter("pokerSessionStartTime");
            String storyPointType = request.getParameter("storyPointType");
            String userStoryName = request.getParameter("userStoryName");
            int userStoryId = userStoryRepository.getUserStoryId(userStoryName);
            session.setAttribute("currentStoryPointType",storyPointType);
            //System.out.println(session.getAttribute("currentStoryPointType"));
            int createInvite = pokerSessionRepository.createPokerSession(pokerSessionDate, pokerSessionStartTime, userStoryId, storyPointType);
            //session.setAttribute("pokerSessionId",pokerSessionRepository.getPokerSessionForUserStory(userStoryName).getPokerSessionId());
            //System.out.println(session.getAttribute("pokerSessionId") + " poker session id");
            //System.out.println(session.getAttribute("currentStoryPointValues"));
            if(createInvite == 1){
                String responseJson = new Gson().toJson("Invitation sent!");
              //  System.out.println(responseJson);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(responseJson);
            }
        } else if(action.equals("addEstimation")){
            String cUsername = request.getParameter("cUsername");
            UserRepository userRepository = new UserRepository();
            int userId = userRepository.getUserId(cUsername);
            String userEstimation = request.getParameter("userEstimation");
            String storyPointType = String.valueOf(request.getSession().getAttribute("storyPointType"));
            int pokerSessionId = Integer.valueOf(String.valueOf(request.getSession().getAttribute("pokerSessionId")));
            //System.out.println(pokerSessionId);
            int addEstimation = pokerSessionRepository.addEstimation(userId,pokerSessionId,userEstimation,storyPointType);
            if(addEstimation > 0){
                /*String responseJson = new Gson().toJson("");
                System.out.println(responseJson);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(responseJson);*/
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        PokerSessionRepository pokerSessionRepository = new PokerSessionRepository();

        if(action.equals("getEstimations")){
            String userStoryId = String.valueOf(request.getSession().getAttribute("userStoryId"));
            int pokerSessionId = pokerSessionRepository.getPokerSessionIdForUserStory(Integer.valueOf(userStoryId));
            ArrayList<Estimation> currentEstimations = pokerSessionRepository.getEstimationsForPokerSession(pokerSessionId);
            String jsonResponse = new Gson().toJson(currentEstimations);
            //System.out.println(currentEstimations);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonResponse);

        }
    }
}
