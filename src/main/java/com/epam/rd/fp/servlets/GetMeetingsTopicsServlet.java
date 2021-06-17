package com.epam.rd.fp.servlets;

import com.epam.rd.fp.factory.ServiceFactory;
import com.epam.rd.fp.factory.impl.ServiceFactoryImpl;
import com.epam.rd.fp.model.Topic;
import com.epam.rd.fp.service.MeetingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "GetMeetingsTopicsServlet", value = "/getMeetingsTopics")
public class GetMeetingsTopicsServlet extends HttpServlet {
    private final ServiceFactory serviceFactory = new ServiceFactoryImpl();
    private final MeetingService meetingService = serviceFactory.getMeetingService();
    private static final Logger log = LogManager.getLogger(GetMeetingsTopicsServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Topic> topics;

        try {
            int meetingId = Integer.parseInt(request.getParameter("meeting_id"));
            request.getSession().setAttribute("meeting_id", meetingId);
            topics = meetingService.getMeetingsTopics(meetingId);
            request.setAttribute("topics", topics);
        } catch (NumberFormatException e) {
            log.error(e.getMessage(), e);
            request.getSession().setAttribute("errorMessage", "Meeting id  field is empty or has invalid format");
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
            return;
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
            request.getSession().setAttribute("errorMessage", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
            return;
        }
        request.getRequestDispatcher("meetingTopicsPage.jsp").forward(request, response);
    }
}
