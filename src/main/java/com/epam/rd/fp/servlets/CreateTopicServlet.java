package com.epam.rd.fp.servlets;

import com.epam.rd.fp.dao.MeetingDao;
import com.epam.rd.fp.dao.MeetingTopicDao;
import com.epam.rd.fp.dao.TopicDao;
import com.epam.rd.fp.dao.UserDao;
import com.epam.rd.fp.model.Meeting;
import com.epam.rd.fp.model.Topic;
import com.epam.rd.fp.model.enums.Language;
import com.epam.rd.fp.service.DBManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(name = "CreateTopicServlet", value = "/createTopic")
public class CreateTopicServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(CreateTopicServlet.class);
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/meetings?createDatabaseIfNotExist=true&user=root&password=myrootpass";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean exceptionCaught = false;
        TopicDao topicDao = new TopicDao();
        DateFormat df = new SimpleDateFormat("dd.MM.yy");
        MeetingDao meetingDao = new MeetingDao();
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

        Topic topic = new Topic();
        topic.setName(name);
        topic.setDate(date);
        topic.setDescription(description);
        topic.setLanguage(language);
        try {
            Meeting meeting = meetingDao.getMeeting(CONNECTION_URL, request.getParameter("meeting_name"));
            String dateStr = meeting.getDate();
            String topicDateStr = topic.getDate();
            Date meetingDate = df.parse(dateStr);
            Date topicDate = df.parse(topicDateStr);
            if (topicDate.getTime() == (meetingDate.getTime())) {
                topicDao.insertTopic(CONNECTION_URL, topic);
            }else{
                throw new IllegalArgumentException("Topic date must be equals to meeting date");
            }

            List<Topic> topics = new ArrayList<>();
            topics.add(topic);
            meeting.setTopics(topics);
            meetingTopicDao.bindTopicIdWithMeetingId(CONNECTION_URL, topic.getId(), meeting.getId());
        }catch (IllegalArgumentException | ParseException e){
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
