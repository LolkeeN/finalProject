package com.epam.rd.fp.servlets;

import com.epam.rd.fp.exceptions.InvalidTopicException;
import com.epam.rd.fp.factory.ServiceFactory;
import com.epam.rd.fp.factory.impl.ServiceFactoryImpl;
import com.epam.rd.fp.model.Meeting;
import com.epam.rd.fp.model.Topic;
import com.epam.rd.fp.model.enums.Language;
import com.epam.rd.fp.service.MeetingService;
import com.epam.rd.fp.service.TopicService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "CreateSuggestedTopicServlet", value = "/createSuggestedTopic")
public class CreateSuggestedTopicServlet extends HttpServlet {
    private final ServiceFactory serviceFactory = new ServiceFactoryImpl();
    private final MeetingService meetingService = serviceFactory.getMeetingService();
    private final TopicService topicService = serviceFactory.getTopicService();
    private static final Logger log = LogManager.getLogger(CreateSuggestedTopicServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try {
            Topic topic = getTopic(request);
            validateTopic(topic);
            topicService.insertTopic(topic);
            topicService.updateTopicAvailability(topic, false);

            Meeting meeting = meetingService.getMeetingByName(request.getParameter("meeting_name"));

            List<Topic> topics = new ArrayList<>();
            topics.add(topic);
            meeting.setTopics(topics);
            meetingService.bindTopicIdWithMeetingId(topic.getId(), meeting.getId());
        } catch (IllegalArgumentException | InvalidTopicException e) {
            log.error(e.getMessage(), e);
            request.getSession().setAttribute("errorMessage", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
            return;
        }
        response.sendRedirect(request.getContextPath() + "/speakerPage.jsp");
    }

    private Topic getTopic(HttpServletRequest request) {
        String name = request.getParameter("name");
        String date = request.getParameter("date");
        String description = request.getParameter("description");
        Language language;
        if ("EN".equalsIgnoreCase(request.getParameter("language"))) {
            language = Language.EN;
        } else {
            language = Language.RU;
        }

        Topic topic = new Topic();
        topic.setName(name);
        topic.setDate(date);
        topic.setDescription(description);
        topic.setLanguage(language);
        return topic;
    }

    private void validateTopic(Topic topic) {
        if (topic.getDate().isEmpty()) {
            throw new InvalidTopicException("Topic date is invalid");
        }
        if (topic.getDescription().isEmpty()) {
            throw new InvalidTopicException("Topic description is invalid");
        }
        if (topic.getName().isEmpty()) {
            throw new InvalidTopicException("Topic name is invalid");
        }
        if (topic.getLanguage().getValue().isEmpty()) {
            throw new InvalidTopicException("Topic language is invalid");
        }
    }
}
