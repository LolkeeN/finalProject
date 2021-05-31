package com.epam.rd.fp.servlets;

import com.epam.rd.fp.dao.TopicDao;
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

@WebServlet(name = "BindSpeakerWithTopicServlet", value = "/bindSpeakerWithTopic")
public class BindSpeakerWithTopicServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(BindSpeakerWithTopicServlet.class);
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/meetings?createDatabaseIfNotExist=true&user=root&password=myrootpass";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean exceptionCaught = false;
        TopicDao topicDao = new TopicDao();
        UserDao userDao = new UserDao();
        Topic topic = topicDao.getTopicById(CONNECTION_URL, Integer.parseInt(request.getParameter("topic_id")));
        User user = userDao.getUser(CONNECTION_URL, Integer.parseInt(request.getParameter("speaker_id")));
        try {
            BindFreeTopicServlet.bindTopicToSpeaker(request, topic, user, log);
            topicDao.updateTopicAvailability(CONNECTION_URL, topic, false);
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
