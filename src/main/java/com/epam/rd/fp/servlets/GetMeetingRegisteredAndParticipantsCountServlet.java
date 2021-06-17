package com.epam.rd.fp.servlets;

import com.epam.rd.fp.factory.ServiceFactory;
import com.epam.rd.fp.factory.impl.ServiceFactoryImpl;
import com.epam.rd.fp.service.MeetingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "GetMeetingRegisteredAndParticipantsCountServlet", value = "/getMeetingRegisteredAndParticipantsCount")
public class GetMeetingRegisteredAndParticipantsCountServlet extends HttpServlet {
    private final ServiceFactory serviceFactory = new ServiceFactoryImpl();
    private final MeetingService meetingService = serviceFactory.getMeetingService();
    private static final Logger log = LogManager.getLogger(GetFreeTopicsServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int participantsCount;
        int registeredCount;

        try {
            int meetingId = Integer.parseInt(request.getParameter("meeting_id"));
            participantsCount = meetingService.countMeetingParticipants(meetingId);
            registeredCount = meetingService.countMeetingRegisteredUsers(meetingId);
            request.getSession().setAttribute("participants_count", participantsCount);
            request.getSession().setAttribute("registered_count", registeredCount);
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
        request.getRequestDispatcher("meetingParticipantsAndRegisteredCount.jsp").forward(request, response);
    }
}
