package com.epam.rd.fp.servlets;

import com.epam.rd.fp.dao.MeetingParticipantsDao;
import com.epam.rd.fp.dao.RegisteredUsersDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebServlet(name = "GetMeetingRegisteredAndParticipantsCountServlet", value = "/getMeetingRegisteredAndParticipantsCount")
public class GetMeetingRegisteredAndParticipantsCountServlet extends HttpServlet {
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/meetings?createDatabaseIfNotExist=true&user=root&password=myrootpass";
    private static final Logger log = LogManager.getLogger(GetFreeTopicsServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean exceptionCaught = false;
        MeetingParticipantsDao meetingParticipantsDao = new MeetingParticipantsDao();
        RegisteredUsersDao registeredUsersDao = new RegisteredUsersDao();
        int participantsCount;
        int registeredCount;
        int meetingId = Integer.parseInt(request.getParameter("meeting_id"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(CONNECTION_URL);
            participantsCount = meetingParticipantsDao.countMeetingParticipants(connection, meetingId);
            registeredCount = registeredUsersDao.countMeetingRegisteredUsers(connection, meetingId);
            request.getSession().setAttribute("participants_count", participantsCount);
            request.getSession().setAttribute("registered_count", registeredCount);
        }catch (IllegalArgumentException | ClassNotFoundException | SQLException e){
            log.error(e.getMessage());
            exceptionCaught = true;
            request.getSession().setAttribute("errorMessage", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
        }
        if (!exceptionCaught) {
            request.getRequestDispatcher("meetingParticipantsAndRegisteredCount.jsp").forward(request, response);
        }
    }
}
