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
import java.util.List;

@WebServlet(name = "GetAllSpeakerTopicsServlet", value = "/getAllSpeakerTopics")
public class GetAllSpeakerTopicsServlet extends HttpServlet {
    private final ServiceFactory serviceFactory = new ServiceFactoryImpl();
    private final TopicService topicService = serviceFactory.getTopicService();
    private final UserService userService = serviceFactory.getUserService();
    private static final Logger log = LogManager.getLogger(GetFreeTopicsServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Topic> topics;
        try {
            int topicId = Integer.parseInt(request.getParameter("topic_id"));
            request.getSession().setAttribute("old_topic_id", topicId);
            int speakerId = userService.getSpeakerIdByTopicId(topicId);
            topics = topicService.getTopicIdBySpeakerId(speakerId);
            request.setAttribute("topics", topics);
        } catch (NumberFormatException e) {
            log.error(e.getMessage(), e);
            request.getSession().setAttribute("errorMessage", "Topic id field is empty or has invalid format");
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
            request.getSession().setAttribute("errorMessage", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
        }

        request.getRequestDispatcher("changeTopicBySpeakerPage.jsp").forward(request, response);
    }
}
