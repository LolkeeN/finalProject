package com.epam.rd.fp.servlets;

import com.epam.rd.fp.dao.MeetingDao;
import com.epam.rd.fp.dao.MeetingTopicDao;
import com.epam.rd.fp.dao.TopicDao;
import com.epam.rd.fp.model.Meeting;
import com.epam.rd.fp.model.Topic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "BindSuggestedTopicWithMeetingServlet", value = "/bindSuggestedTopicWithMeeting")
public class BindSuggestedTopicWithMeetingServlet extends HttpServlet {
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/meetings?createDatabaseIfNotExist=true&user=root&password=myrootpass";
    private static final Logger log = LogManager.getLogger(BindSuggestedTopicWithMeetingServlet.class);
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean exceptionCaught = false;
        MeetingDao meetingDao = new MeetingDao();
        MeetingTopicDao meetingTopicDao = new MeetingTopicDao();

        int topicId = Integer.parseInt(request.getParameter("topic_id"));
        String meetingName = request.getParameter("meeting_name");

        try {
            Meeting meeting = meetingDao.getMeeting(CONNECTION_URL, meetingName);
            meetingTopicDao.bindTopicIdWithMeetingId(CONNECTION_URL, topicId, meeting.getId());
            meetingTopicDao.deleteMeetingAndTopicConnectivityById(CONNECTION_URL, topicId, 1);
        }catch (IllegalArgumentException e){
            log.error(e.getMessage());
            exceptionCaught = true;
            request.getSession().setAttribute("errorMessage", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
        }
        if (!exceptionCaught) {
            response.sendRedirect(request.getContextPath() + "/adminPage.jsp");
        }
    }
}
