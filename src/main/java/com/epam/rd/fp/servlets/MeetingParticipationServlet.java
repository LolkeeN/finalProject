package com.epam.rd.fp.servlets;

import com.epam.rd.fp.dao.MeetingParticipantsDao;
import com.epam.rd.fp.dao.RegisteredUsersDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "MeetingParticipationServlet", value = "/meetingParticipation")
public class MeetingParticipationServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(LoginServlet.class);
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/meetings?createDatabaseIfNotExist=true&user=root&password=myrootpass";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean exceptionCaught = false;
        RegisteredUsersDao registeredUsersDao = new RegisteredUsersDao();
        MeetingParticipantsDao meetingParticipantsDao = new MeetingParticipantsDao();
        int userId = (int) request.getSession().getAttribute("id");
        int meetingId = Integer.parseInt(request.getParameter("meeting_id"));
        try {
            if (registeredUsersDao.isRegistered(CONNECTION_URL, userId, meetingId)) {
                meetingParticipantsDao.addMeetingParticipant(CONNECTION_URL, userId, meetingId);
            }else{
                throw new IllegalArgumentException("You are not registered for this meeting. You have to register first!");
            }
        }catch (IllegalArgumentException e){
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
