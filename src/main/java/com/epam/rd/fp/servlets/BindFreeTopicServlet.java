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

@WebServlet(name = "BindFreeTopicServlet", value = "/bindFreeTopic")
public class BindFreeTopicServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(BindFreeTopicServlet.class);
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean exceptionCaught = false;
        TopicDao topicDao = new TopicDao();
        UserDao userDao = new UserDao();
        Topic topic = topicDao.getTopicById(Integer.parseInt(request.getParameter("topic_id")));
        User user = userDao.getUser((Integer) request.getSession().getAttribute("id"));
        try {
            bindTopicToSpeaker(request, topic, user, log);
        }catch (IllegalArgumentException e){
            exceptionCaught = true;
            request.getSession().setAttribute("errorMessage", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
        }
        topicDao.updateTopicAvailability(topic, false);
        if (!exceptionCaught) {
            request.getRequestDispatcher("speakerPage.jsp").forward(request, response);
        }
    }

    static void bindTopicToSpeaker(HttpServletRequest request, Topic topic, User user, Logger log) {
        user.setId((Integer) request.getSession().getAttribute("id"));
        if (user.getRole().getValue() != 3){
            log.info("Cannot set a topic to non-speaker, user role is not a speaker");
            throw new IllegalArgumentException("Cannot set a topic to non-speaker");
        }
        TopicSpeakerDao topicSpeakerDao = new TopicSpeakerDao();
        topicSpeakerDao.bindTopicWithSpeakerId(topic.getId(), user.getId());
    }
}
