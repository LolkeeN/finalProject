package com.epam.rd.fp.servlets;

import com.epam.rd.fp.dao.MeetingParticipantsDao;
import com.epam.rd.fp.dao.RegisteredUsersDao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "MeetingParticipationServlet", value = "/meetingParticipation")
public class MeetingParticipationServlet extends HttpServlet {
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/meetings?createDatabaseIfNotExist=true&user=root&password=myrootpass";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean exceptionCaught = false;
        MeetingParticipantsDao meetingParticipantsDao = new MeetingParticipantsDao();
        int userId = (int) request.getSession().getAttribute("id");
        int meetingId = Integer.parseInt(request.getParameter("meeting_id"));
        try {
            meetingParticipantsDao.addMeetingParticipant(CONNECTION_URL, userId, meetingId);
        }catch (IllegalArgumentException e){
            exceptionCaught = true;
            request.getSession().setAttribute("errorMessage", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
        }
        if (!exceptionCaught){
            response.sendRedirect(request.getContextPath() + "/mainPage.jsp");
        }
    }
}
