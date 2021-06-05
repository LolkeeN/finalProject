package com.epam.rd.fp.servlets;

import com.epam.rd.fp.dao.RegisteredUsersDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebServlet(name = "MeetingRegistrationServlet", value = "/meetingRegistration")
public class MeetingRegistrationServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(LoginServlet.class);
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/meetings?createDatabaseIfNotExist=true&user=root&password=myrootpass";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        boolean exceptionCaught = false;
        RegisteredUsersDao registeredUsersDao = new RegisteredUsersDao();
        int userId = (int) request.getSession().getAttribute("id");
        int meetingId = Integer.parseInt(request.getParameter("meeting_id"));
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(CONNECTION_URL);
            registeredUsersDao.registerUserForAMeeting(connection, userId, meetingId);
        }catch (IllegalArgumentException | ClassNotFoundException | SQLException e){
            log.error(e.getMessage());
            exceptionCaught = true;
            request.getSession().setAttribute("errorMessage", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
        }
        if (!exceptionCaught){
            response.sendRedirect(request.getContextPath() + "/mainPage.jsp");
        }
    }
}
