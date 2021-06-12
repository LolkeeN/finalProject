package com.epam.rd.fp.servlets;

import com.epam.rd.fp.factory.ServiceFactory;
import com.epam.rd.fp.factory.impl.ServiceFactoryImpl;
import com.epam.rd.fp.model.Meeting;
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

@WebServlet(name = "GetAllMeetingsServlet", value = "/getAllMeetings")
public class GetAllMeetingsServlet extends HttpServlet {
    private final ServiceFactory serviceFactory = new ServiceFactoryImpl();
    private final MeetingService meetingService = serviceFactory.getMeetingService();
    private static final Logger log = LogManager.getLogger(GetAllMeetingsServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Meeting> meetings;
        try {
           meetings = meetingService.getAllMeetings();
            for (Meeting meeting:meetings) {
                meeting.setParticipantsCount(meetingService.countMeetingParticipants(meeting.getId()));
                meeting.setRegisteredUsers(meetingService.countMeetingRegisteredUsers(meeting.getId()));
            }
            request.setAttribute("meetings", meetings);
        }catch (IllegalArgumentException e){
            log.error(e.getMessage());
            request.getSession().setAttribute("errorMessage", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
            return;
        }
            if ((int)request.getSession().getAttribute("role") == 1) {
                request.getRequestDispatcher("registrationForAMeeting.jsp").forward(request, response);
            }else{
                request.getRequestDispatcher("allMeetingsPage.jsp").forward(request, response);
            }
    }
}
