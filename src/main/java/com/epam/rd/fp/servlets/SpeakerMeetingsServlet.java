package com.epam.rd.fp.servlets;

import com.epam.rd.fp.factory.ServiceFactory;
import com.epam.rd.fp.factory.impl.ServiceFactoryImpl;
import com.epam.rd.fp.model.Topic;
import com.epam.rd.fp.model.User;
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

@WebServlet(name = "SpeakerMeetingsServlet", value = "/speakerMeetings")
public class SpeakerMeetingsServlet extends HttpServlet {
    private final ServiceFactory serviceFactory = new ServiceFactoryImpl();
    private final UserService userService = serviceFactory.getUserService();
    private final TopicService topicService = serviceFactory.getTopicService();
    private static final Logger log = LogManager.getLogger(SpeakerMeetingsServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Topic> topics;
        String speakerEmail = (String) request.getSession().getAttribute("email");
        String speakerPassword = (String) request.getSession().getAttribute("password");

        try {
            User speaker = userService.getUser(speakerEmail, speakerPassword);
            topics = topicService.getTopicIdBySpeakerId(speaker.getId());
            request.getSession().setAttribute("first_name", speaker.getFirstName());
            request.getSession().setAttribute("last_name", speaker.getLastName());
            request.setAttribute("topics", topics);
        }catch (IllegalArgumentException e){
            log.error(e.getMessage(), e);
            request.getSession().setAttribute("errorMessage", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
            return;
        }
            request.getRequestDispatcher("speakerMeetings.jsp").forward(request, response);
    }
}
