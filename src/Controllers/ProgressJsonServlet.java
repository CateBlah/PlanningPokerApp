package Controllers;

import Models.Progress;
import Repository.ProgressRepository;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Caterina on 5/17/2016.
 */
@WebServlet(name = "ProgressJsonServlet")
public class ProgressJsonServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String dayNumber = request.getParameter("dayNumber");
        String effortPercent = request.getParameter("effortPercent");
        String action = request.getParameter("action");
        ProgressRepository progressRepository = new ProgressRepository();
        System.out.println(request.getSession().getAttribute("userStoryId"));
        List<Progress> progressList = progressRepository.getProgressListForUserStory(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userStoryId"))));
        if(action.equals("addProgress")){
            //System.out.println("in addProgress");
            Progress progress = new Progress();
            progress.setDayNumber(Integer.valueOf(dayNumber));
            progress.setCompletedPercentage(Integer.valueOf(effortPercent));
            progress.setUserStoryId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userStoryId"))));
            progressList.add(progress);
            progressRepository.addProgress(Integer.valueOf(dayNumber),Integer.valueOf(effortPercent),Integer.valueOf(String.valueOf(request.getSession().getAttribute("userStoryId"))));
            String jsonResponse = new Gson().toJson(progress);
            //System.out.println(jsonResponse);
            response.setContentType("application/json");
            response.getWriter().write(jsonResponse);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProgressRepository progressRepository = new ProgressRepository();
        String userStoryId = String.valueOf(request.getSession().getAttribute("userStoryId"));
        List<Progress> progressList = progressRepository.getProgressListForUserStory(Integer.valueOf(userStoryId));
        //System.out.println(progressList.toString());
        Gson gson = new Gson();
        String jsonProgress = gson.toJson(progressList);
        response.setContentType("application/json");
        response.getWriter().write(jsonProgress);
    }
}
