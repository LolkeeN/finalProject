package com.epam.rd.fp.servlets;

import com.epam.rd.fp.dao.MeetingTopicDao;
import com.epam.rd.fp.dao.TopicDao;
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
import java.util.List;

@WebServlet(name = "GetSuggestedTopicsServlet", value = "/getSuggestedTopics")
public class GetSuggestedTopicsServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(GetSuggestedTopicsServlet.class);
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/meetings?createDatabaseIfNotExist=true&user=root&password=myrootpass";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean exceptionCaught = false;
        MeetingTopicDao  meetingTopicDao = new MeetingTopicDao();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(CONNECTION_URL);
            List<Topic> topics = meetingTopicDao.getSuggestedTopics(connection);
            request.setAttribute("suggestedTopics", topics);
        }catch (IllegalArgumentException | ClassNotFoundException | SQLException e){
            log.error(e.getMessage());
            exceptionCaught = true;
            request.getSession().setAttribute("errorMessage", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
        }
        if (!exceptionCaught) {
            request.getRequestDispatcher("suggestedTopicsPage.jsp").forward(request, response);
        }
    }
}
