package com.epam.rd.fp.servlets;

import com.epam.rd.fp.dao.MeetingDao;
import com.epam.rd.fp.dao.MeetingTopicDao;
import com.epam.rd.fp.dao.TopicDao;
import com.epam.rd.fp.model.Meeting;
import com.epam.rd.fp.model.Topic;
import com.epam.rd.fp.model.enums.Language;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "CreateSuggestedTopicServlet", value = "/createSuggestedTopic")
public class CreateSuggestedTopicServlet extends HttpServlet {
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/meetings?createDatabaseIfNotExist=true&user=root&password=myrootpass";
    private static final Logger log = LogManager.getLogger(CreateSuggestedTopicServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        boolean exceptionCaught = false;
        try {
            TopicDao topicDao = new TopicDao();
            MeetingDao meetingDao = new MeetingDao();
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(CONNECTION_URL);
            List<String> parameterList = new ArrayList<>();
            MeetingTopicDao meetingTopicDao = new MeetingTopicDao();

            String name = request.getParameter("name");
            String date = request.getParameter("date");
            String description = request.getParameter("description");
            Language language;
            if ("EN".equalsIgnoreCase(request.getParameter("language"))) {
                language = Language.EN;
            } else {
                language = Language.RU;
            }

            parameterList.add(name);
            parameterList.add(date);
            parameterList.add(description);
            parameterList.add(language.toString());

            for (String elem:parameterList) {
                if (elem.equals("")){
                    throw new IllegalArgumentException("Some fields are null");
                }
            }
            Topic topic = new Topic();
            topic.setName(name);
            topic.setDate(date);
            topic.setDescription(description);
            topic.setLanguage(language);
            topicDao.insertTopic(connection, topic);
            topicDao.updateTopicAvailability(connection, topic, false);


            Meeting meeting = meetingDao.getMeeting(connection, request.getParameter("meeting_name"));
            List<Topic> topics = new ArrayList<>();
            topics.add(topic);
            meeting.setTopics(topics);
            meetingTopicDao.bindTopicIdWithMeetingId(connection, topic.getId(), meeting.getId());
        }catch (IllegalArgumentException | ClassNotFoundException | SQLException e){
            log.error(e.getMessage());
            exceptionCaught = true;
            request.getSession().setAttribute("errorMessage", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
        }
        if (!exceptionCaught) {
            response.sendRedirect(request.getContextPath() + "/speakerPage.jsp");
        }
    }

//    static void createTopicAndBindWithMeeting(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, IOException {
//
//    }
}
