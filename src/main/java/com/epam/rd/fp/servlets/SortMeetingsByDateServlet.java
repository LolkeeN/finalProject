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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@WebServlet(name = "SortMeetingsByDateServlet", value = "/sortMeetingsByDate")
public class SortMeetingsByDateServlet extends HttpServlet {
    private final ServiceFactory serviceFactory = new ServiceFactoryImpl();
    private final MeetingService meetingService = serviceFactory.getMeetingService();
    private static final Logger log = LogManager.getLogger(SortMeetingsByDateServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Meeting> meetings;
        try {
            meetings = meetingService.getAllMeetings();
            for (Meeting meeting : meetings) {
                meeting.setParticipantsCount(meetingService.countMeetingParticipants(meeting.getId()));
                meeting.setRegisteredUsers(meetingService.countMeetingRegisteredUsers(meeting.getId()));
            }
            request.setAttribute("meetings", meetings);
            meetings.sort(new MeetingDateComparator());
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            request.getSession().setAttribute("errorMessage", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
            return;
        }
        request.getRequestDispatcher("allMeetingsPage.jsp").forward(request, response);
    }

    static class MeetingDateComparator implements Comparator<Meeting> {
        public int compare(Meeting a, Meeting b) {
            DateFormat df = new SimpleDateFormat("dd.MM.yy");
            String firstMeetingDateStr = a.getDate();
            String secondMeetingDateStr = b.getDate();
            Date secondMeetingDate = new Date();
            Date firstMeetingDate = new Date();
            try {
                firstMeetingDate = df.parse(firstMeetingDateStr);
                secondMeetingDate = df.parse(secondMeetingDateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return Long.compare(firstMeetingDate.getTime(), secondMeetingDate.getTime());
        }
    }
}
