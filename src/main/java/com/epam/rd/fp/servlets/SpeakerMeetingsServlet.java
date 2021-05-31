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
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/meetings?createDatabaseIfNotExist=true&user=root&password=myrootpass";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean exceptionCaught = false;
        TopicSpeakerDao topicSpeakerDao = new TopicSpeakerDao();
        UserDao userDao = new UserDao();

        List<Topic> topics;
        String speakerEmail = (String) request.getSession().getAttribute("email");
        String speakerPassword = (String) request.getSession().getAttribute("password");
        try {
            User speaker = userDao.getUser(CONNECTION_URL, speakerEmail, speakerPassword);
            topics = topicSpeakerDao.getTopicIdBySpeakerId(CONNECTION_URL, speaker.getId());
            request.getSession().setAttribute("first_name", speaker.getFirstName());
            request.getSession().setAttribute("last_name", speaker.getLastName());
            request.setAttribute("topics", topics);
        }catch (IllegalArgumentException e){
            log.error(e.getMessage());
            exceptionCaught = true;
            request.getSession().setAttribute("errorMessage", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
        }
        if (!exceptionCaught) {
            request.getRequestDispatcher("speakerMeetings.jsp").forward(request, response);
        }
    }
}
