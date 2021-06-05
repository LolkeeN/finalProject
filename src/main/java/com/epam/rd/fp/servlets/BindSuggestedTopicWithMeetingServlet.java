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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebServlet(name = "BindSuggestedTopicWithMeetingServlet", value = "/bindSuggestedTopicWithMeeting")
public class BindSuggestedTopicWithMeetingServlet extends HttpServlet {
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/meetings?createDatabaseIfNotExist=true&user=root&password=myrootpass";
    private static final Logger log = LogManager.getLogger(BindSuggestedTopicWithMeetingServlet.class);
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        boolean exceptionCaught = false;
        MeetingDao meetingDao = new MeetingDao();
        TopicDao topicDao = new TopicDao();
        MeetingTopicDao meetingTopicDao = new MeetingTopicDao();

        int topicId = Integer.parseInt(request.getParameter("topic_id"));
        String meetingName = request.getParameter("meeting_name");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(CONNECTION_URL);
            Topic topic = topicDao.getTopicById(connection, topicId);
            Meeting meeting = meetingDao.getMeeting(connection, meetingName);
            topicDao.updateTopicDate(connection, topic, meeting.getDate());
            meetingTopicDao.bindTopicIdWithMeetingId(connection, topicId, meeting.getId());
            meetingTopicDao.deleteMeetingAndTopicConnectivityById(connection, topicId, 1);
        }catch (IllegalArgumentException | ClassNotFoundException | SQLException e){
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
