package com.epam.rd.fp.servlets;

import com.epam.rd.fp.dao.TopicSpeakerDao;
import com.epam.rd.fp.model.Topic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "GetAllSpeakerTopicsServlet", value = "/getAllSpeakerTopics")
public class GetAllSpeakerTopicsServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(GetFreeTopicsServlet.class);
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/meetings?createDatabaseIfNotExist=true&user=root&password=myrootpass";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean exceptionCaught = false;
        int topicId = Integer.parseInt(request.getParameter("topic_id"));
        request.getSession().setAttribute("old_topic_id", topicId);
        TopicSpeakerDao topicSpeakerDao = new TopicSpeakerDao();
        List<Topic> topics;
        try {
            int speakerId = topicSpeakerDao.getSpeakerIdByTopicId(CONNECTION_URL, topicId);
            topics = topicSpeakerDao.getTopicIdBySpeakerId(CONNECTION_URL, speakerId);
            request.setAttribute("topics", topics);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            exceptionCaught = true;
            request.getSession().setAttribute("errorMessage", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
        }

        if (!exceptionCaught) {
            request.getRequestDispatcher("changeTopicBySpeakerPage.jsp").forward(request, response);
        }
    }
}
