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
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

@WebServlet(name = "SortMeetingsByRegisteredCountServlet", value = "/sortMeetingsByRegisteredCount")
public class SortMeetingsByRegisteredCountServlet extends HttpServlet {
    private final ServiceFactory serviceFactory = new ServiceFactoryImpl();
    private final MeetingService meetingService = serviceFactory.getMeetingService();
    private static final Logger log = LogManager.getLogger(SortMeetingsByRegisteredCountServlet.class);
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/meetings?createDatabaseIfNotExist=true&user=root&password=myrootpass";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Meeting> meetings;
        try {
            meetings = meetingService.getAllMeetings();
            request.setAttribute("meetings", meetings);
            for (Meeting meeting : meetings) {
                meeting.setParticipantsCount(meetingService.countMeetingParticipants(meeting.getId()));
                meeting.setRegisteredUsers(meetingService.countMeetingRegisteredUsers(meeting.getId()));
            }
            meetings.sort(new MeetingRegisteredUsersComparator());

        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            request.getSession().setAttribute("errorMessage", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
            return;
        }
        request.getRequestDispatcher("allMeetingsPage.jsp").forward(request, response);
    }

    static class MeetingRegisteredUsersComparator implements Comparator<Meeting> {
        public int compare(Meeting a, Meeting b) {

            return Integer.compare(a.getRegisteredUsers(), b.getRegisteredUsers());
        }
    }
}
