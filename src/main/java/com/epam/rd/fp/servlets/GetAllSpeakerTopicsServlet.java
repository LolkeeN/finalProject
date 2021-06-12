package com.epam.rd.fp.servlets;

import com.epam.rd.fp.factory.ServiceFactory;
import com.epam.rd.fp.factory.impl.ServiceFactoryImpl;
import com.epam.rd.fp.model.Topic;
import com.epam.rd.fp.service.TopicService;
import com.epam.rd.fp.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "GetAllSpeakerTopicsServlet", value = "/getAllSpeakerTopics")
public class GetAllSpeakerTopicsServlet extends HttpServlet {
    private final ServiceFactory serviceFactory = new ServiceFactoryImpl();
    private final TopicService topicService = serviceFactory.getTopicService();
    private final UserService userService = serviceFactory.getUserService();
    private static final Logger log = LogManager.getLogger(GetFreeTopicsServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int topicId = Integer.parseInt(request.getParameter("topic_id"));
        request.getSession().setAttribute("old_topic_id", topicId);
        List<Topic> topics;
        try {
            int speakerId = userService.getSpeakerIdByTopicId(topicId);
            topics = topicService.getTopicIdBySpeakerId(speakerId);
            request.setAttribute("topics", topics);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            request.getSession().setAttribute("errorMessage", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
        }

        request.getRequestDispatcher("changeTopicBySpeakerPage.jsp").forward(request, response);
    }
}
