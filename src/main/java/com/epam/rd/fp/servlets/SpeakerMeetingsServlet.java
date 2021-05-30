package com.epam.rd.fp.servlets;

import com.epam.rd.fp.dao.TopicSpeakerDao;
import com.epam.rd.fp.dao.UserDao;
import com.epam.rd.fp.model.Topic;
import com.epam.rd.fp.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "SpeakerMeetingsServlet", value = "/speakerMeetings")
public class SpeakerMeetingsServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(SpeakerMeetingsServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TopicSpeakerDao topicSpeakerDao = new TopicSpeakerDao();
        UserDao userDao = new UserDao();

        List<Topic> topics;
        String speakerEmail = (String) request.getSession().getAttribute("email");
        String speakerPassword = (String) request.getSession().getAttribute("password");
        User speaker = userDao.getUser(speakerEmail, speakerPassword);
        topics = topicSpeakerDao.getTopicIdBySpeakerId(speaker.getId());
        request.getSession().setAttribute("first_name", speaker.getFirstName());
        request.getSession().setAttribute("last_name", speaker.getLastName());
        request.setAttribute("topics", topics);
        request.getRequestDispatcher("speakerMeetings.jsp").forward(request, response);
    }
}
