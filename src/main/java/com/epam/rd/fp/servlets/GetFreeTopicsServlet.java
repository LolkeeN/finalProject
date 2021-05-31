package com.epam.rd.fp.servlets;

import com.epam.rd.fp.dao.TopicDao;
import com.epam.rd.fp.model.Topic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "GetFreeTopicsServlet", value = "/getFreeTopics")
public class GetFreeTopicsServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(GetFreeTopicsServlet.class);
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/meetings?createDatabaseIfNotExist=true&user=root&password=myrootpass";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean exceptionCaught = false;
        TopicDao topicDao = new TopicDao();
        try {
            List<Topic> topics = topicDao.getFreeTopics(CONNECTION_URL);
            request.setAttribute("freeTopics", topics);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            exceptionCaught = true;
            request.getSession().setAttribute("errorMessage", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
        }
        if (!exceptionCaught) {
            request.getRequestDispatcher("chooseFreeTopic.jsp").forward(request, response);
        }
    }
}
