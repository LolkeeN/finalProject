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
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "CreateTopicServlet", value = "/createTopic")
public class CreateTopicServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(CreateTopicServlet.class);
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TopicDao topicDao = new TopicDao();
        MeetingDao meetingDao = new MeetingDao();
        MeetingTopicDao meetingTopicDao = new MeetingTopicDao();

        String name = request.getParameter("name");
        String date = request.getParameter("date");
        String description = request.getParameter("description");
        Language language;
        if ("EN".equalsIgnoreCase(request.getParameter("language"))){
            language = Language.EN;
        }else{
            language = Language.RU;
        }

        Topic topic = new Topic();
        topic.setName(name);
        topic.setDate(date);
        topic.setDescription(description);
        topic.setLanguage(language);
        topicDao.insertTopic(topic);

        Meeting meeting = meetingDao.getMeeting(request.getParameter("meeting_name"));
        System.out.println("meeting name " + meeting.getName());
        System.out.println("meeting id " + meeting.getId());
        List<Topic> topics = new ArrayList<>();
        topics.add(topic);
        meeting.setTopics(topics);
        meetingTopicDao.bindTopicIdWithMeetingId(topic.getId(), meeting.getId());
        response.sendRedirect(request.getContextPath() + "/adminPage.jsp");
    }

}