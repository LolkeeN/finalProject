package com.epam.rd.fp.servlets;

import com.epam.rd.fp.dao.MeetingDao;
import com.epam.rd.fp.dao.MeetingParticipantsDao;
import com.epam.rd.fp.dao.RegisteredUsersDao;
import com.epam.rd.fp.model.Meeting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@WebServlet(name = "SortMeetingsByParticipantsCountServlet", value = "/sortMeetingsByParticipantsCount")
public class SortMeetingsByParticipantsCountServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(SortMeetingsByParticipantsCountServlet.class);
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/meetings?createDatabaseIfNotExist=true&user=root&password=myrootpass";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean exceptionCaught = false;
        MeetingDao meetingDao = new MeetingDao();
        MeetingParticipantsDao meetingParticipantsDao = new MeetingParticipantsDao();
        RegisteredUsersDao registeredUsersDao = new RegisteredUsersDao();

        List<Meeting> meetings;
        try {
            meetings = meetingDao.getAllMeetings(CONNECTION_URL);
            request.setAttribute("meetings", meetings);
            for (Meeting meeting:meetings) {
                meeting.setParticipantsCount(meetingParticipantsDao.countMeetingParticipants(CONNECTION_URL, meeting.getId()));
                meeting.setRegisteredUsers(registeredUsersDao.countMeetingRegisteredUsers(CONNECTION_URL, meeting.getId()));
            }
            meetings.sort(new MeetingParticipantsCountComparator());

        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            exceptionCaught = true;
            request.getSession().setAttribute("errorMessage", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
        }
        if (!exceptionCaught) {
            request.getRequestDispatcher("allMeetingsPage.jsp").forward(request, response);
        }
    }

    static class MeetingParticipantsCountComparator  implements Comparator<Meeting> {
        public int compare(Meeting a, Meeting b) {

            return Integer.compare(a.getParticipantsCount(), b.getParticipantsCount());
        }
    }
}
