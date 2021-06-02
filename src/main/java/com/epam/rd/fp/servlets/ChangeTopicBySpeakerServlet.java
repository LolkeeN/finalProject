package com.epam.rd.fp.servlets;

import com.epam.rd.fp.dao.MeetingTopicDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ChangeTopicBySpeakerServlet", value = "/changeTopicBySpeaker")
public class ChangeTopicBySpeakerServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(ChangeTopicBySpeakerServlet.class);
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/meetings?createDatabaseIfNotExist=true&user=root&password=myrootpass";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean exceptionCaught = false;
        MeetingTopicDao meetingTopicDao = new MeetingTopicDao();
        int meetingId = (int)request.getSession().getAttribute("meeting_id");
        int oldTopicId = (int)request.getSession().getAttribute("old_topic_id");
        int newTopicId = Integer.parseInt(request.getParameter("topic_id"));

        try {
            meetingTopicDao.updateMeetingTopic(CONNECTION_URL, oldTopicId, newTopicId, meetingId);
        }catch (IllegalArgumentException e){
            log.error(e.getMessage());
            exceptionCaught = true;
            request.getSession().setAttribute("errorMessage", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
        }
        if (!exceptionCaught){
            request.getRequestDispatcher("adminPage.jsp").forward(request, response);
        }
    }
}
