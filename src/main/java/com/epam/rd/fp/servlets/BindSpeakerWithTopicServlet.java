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

@WebServlet(name = "BindSpeakerWithTopicServlet", value = "/bindSpeakerWithTopic")
public class BindSpeakerWithTopicServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(BindSpeakerWithTopicServlet.class);
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/meetings?createDatabaseIfNotExist=true&user=root&password=myrootpass";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        boolean exceptionCaught = false;
        TopicDao topicDao = new TopicDao();
        UserDao userDao = new UserDao();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(CONNECTION_URL);
            Topic topic = topicDao.getTopicById(connection, Integer.parseInt(request.getParameter("topic_id")));
            User user = userDao.getUser(connection, Integer.parseInt(request.getParameter("speaker_id")));
            System.out.println(user.getId());
            if (user.getRole().getValue() != 3){
                log.info("Cannot set a topic to non-speaker, user role is not a speaker");
                throw new IllegalArgumentException("Cannot set a topic to non-speaker");
            }
            TopicSpeakerDao topicSpeakerDao = new TopicSpeakerDao();
            topicSpeakerDao.bindTopicWithSpeakerId(connection, topic.getId(), user.getId());
            topicDao.updateTopicAvailability(connection, topic, false);
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
