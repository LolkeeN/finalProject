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
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "GetAllMeetingsServlet", value = "/getAllMeetings")
public class GetAllMeetingsServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(GetAllMeetingsServlet.class);
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
            for (Meeting meeting:meetings) {
                meeting.setParticipantsCount(meetingParticipantsDao.countMeetingParticipants(connection, meeting.getId()));
                meeting.setRegisteredUsers(registeredUsersDao.countMeetingRegisteredUsers(connection, meeting.getId()));
            }
            request.setAttribute("meetings", meetings);
        }catch (IllegalArgumentException | ClassNotFoundException | SQLException e){
            log.error(e.getMessage());
            exceptionCaught = true;
            request.getSession().setAttribute("errorMessage", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
        }
        if (!exceptionCaught) {
            if ((int)request.getSession().getAttribute("role") == 1) {
                request.getRequestDispatcher("registrationForAMeeting.jsp").forward(request, response);
            }else{
                request.getRequestDispatcher("allMeetingsPage.jsp").forward(request, response);
            }
        }
    }
}
