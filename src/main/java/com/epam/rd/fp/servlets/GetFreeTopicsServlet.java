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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TopicDao topicDao = new TopicDao();
        List<Topic> topics = topicDao.getFreeTopics();
        request.setAttribute("freeTopics", topics);
        request.getRequestDispatcher("chooseFreeTopic.jsp").forward(request, response);
    }
}
