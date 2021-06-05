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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

@WebServlet(name = "SortMeetingsByRegisteredCountServlet", value = "/sortMeetingsByRegisteredCount")
public class SortMeetingsByRegisteredCountServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(SortMeetingsByRegisteredCountServlet.class);
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/meetings?createDatabaseIfNotExist=true&user=root&password=myrootpass";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean exceptionCaught = false;
        MeetingDao meetingDao = new MeetingDao();
        MeetingParticipantsDao meetingParticipantsDao = new MeetingParticipantsDao();
        RegisteredUsersDao registeredUsersDao = new RegisteredUsersDao();

        List<Meeting> meetings;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(CONNECTION_URL);
            meetings = meetingDao.getAllMeetings(connection);
            request.setAttribute("meetings", meetings);
            for (Meeting meeting:meetings) {
                meeting.setParticipantsCount(meetingParticipantsDao.countMeetingParticipants(connection, meeting.getId()));
                meeting.setRegisteredUsers(registeredUsersDao.countMeetingRegisteredUsers(connection, meeting.getId()));
            }
            meetings.sort(new MeetingRegisteredUsersComparator());

        } catch (IllegalArgumentException | ClassNotFoundException | SQLException e) {
            log.error(e.getMessage());
            exceptionCaught = true;
            request.getSession().setAttribute("errorMessage", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
        }
        if (!exceptionCaught) {
            request.getRequestDispatcher("allMeetingsPage.jsp").forward(request, response);
        }
    }

    static class MeetingRegisteredUsersComparator  implements Comparator<Meeting> {
        public int compare(Meeting a, Meeting b) {

            return Integer.compare(a.getRegisteredUsers(), b.getRegisteredUsers());
        }
    }
}
