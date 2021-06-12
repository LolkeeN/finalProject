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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebServlet(name = "BindFreeTopicServlet", value = "/bindFreeTopic")
public class BindFreeTopicServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(BindFreeTopicServlet.class);
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/meetings?createDatabaseIfNotExist=true&user=root&password=myrootpass";

    static void bindTopicToSpeaker(HttpServletRequest request, Topic topic, User user, Connection conn) {
        user.setId((Integer) request.getSession().getAttribute("id"));
        if (user.getRole().getValue() != 3){
            BindFreeTopicServlet.log.info("Cannot set a topic to non-speaker, user role is not a speaker");
            throw new IllegalArgumentException("Cannot set a topic to non-speaker");
        }
        TopicSpeakerDao topicSpeakerDao = new TopicSpeakerDao();
        topicSpeakerDao.bindTopicWithSpeakerId(conn, topic.getId(), user.getId());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        boolean exceptionCaught = false;
        TopicDao topicDao = new TopicDao();
        TopicSpeakerDao topicSpeakerDao = new TopicSpeakerDao();
        UserDao userDao = new UserDao();
        Topic topic;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(CONNECTION_URL);
            topic = topicDao.getTopicById(connection, Integer.parseInt(request.getParameter("topic_id")));
            if (topic.isAvailability()) {
                User user = userDao.getUser(connection, (Integer) request.getSession().getAttribute("id"));
                bindTopicToSpeaker(request, topic, user, connection);
                topicDao.updateTopicAvailability(connection, topic, false);
            }else{
                throw new IllegalArgumentException("Topic with id " + topic.getId() + " is not available");
            }
        }catch (IllegalArgumentException | ClassNotFoundException | SQLException e){
            exceptionCaught = true;
            request.getSession().setAttribute("errorMessage", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
        }
        if (!exceptionCaught) {
            request.getRequestDispatcher("speakerPage.jsp").forward(request, response);
        }
    }
}
