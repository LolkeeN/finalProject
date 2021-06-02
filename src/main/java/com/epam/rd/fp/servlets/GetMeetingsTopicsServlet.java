package com.epam.rd.fp.servlets;

import com.epam.rd.fp.dao.MeetingTopicDao;
import com.epam.rd.fp.model.Topic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "GetMeetingsTopicsServlet", value = "/getMeetingsTopics")
public class GetMeetingsTopicsServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(GetMeetingsTopicsServlet.class);
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/meetings?createDatabaseIfNotExist=true&user=root&password=myrootpass";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean exceptionCaught = false;
        int meetingId = Integer.parseInt(request.getParameter("meeting_id"));
        request.getSession().setAttribute("meeting_id", meetingId);
        MeetingTopicDao meetingTopicDao = new MeetingTopicDao();
        List<Topic> topics;

        try {
            topics = meetingTopicDao.getMeetingsTopics(CONNECTION_URL, meetingId);
            request.setAttribute("topics", topics);
        }catch (IllegalArgumentException e){
            log.error(e.getMessage());
            exceptionCaught = true;
            request.getSession().setAttribute("errorMessage", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
        }
        if (!exceptionCaught) {
            request.getRequestDispatcher("meetingTopicsPage.jsp").forward(request, response);
        }
    }
}
