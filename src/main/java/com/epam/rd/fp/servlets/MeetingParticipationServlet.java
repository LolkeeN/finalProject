package com.epam.rd.fp.servlets;

import com.epam.rd.fp.factory.ServiceFactory;
import com.epam.rd.fp.factory.impl.ServiceFactoryImpl;
import com.epam.rd.fp.service.MeetingService;
import com.epam.rd.fp.service.UserService;
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

@WebServlet(name = "MeetingParticipationServlet", value = "/meetingParticipation")
public class MeetingParticipationServlet extends HttpServlet {
    private final ServiceFactory serviceFactory = new ServiceFactoryImpl();
    private final UserService userService = serviceFactory.getUserService();
    private final MeetingService meetingService = serviceFactory.getMeetingService();
    private static final Logger log = LogManager.getLogger(LoginServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int userId = (int) request.getSession().getAttribute("id");
        try {
            int meetingId = Integer.parseInt(request.getParameter("meeting_id"));
             if (userService.isRegistered(userId, meetingId)) {
                meetingService.addMeetingParticipant(userId, meetingId);
            }else{
                throw new IllegalArgumentException("You are not registered for this meeting. You have to register first!");
            }
        }catch (IllegalArgumentException e){
            log.error(e.getMessage());
            request.getSession().setAttribute("errorMessage", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
            return;
        }
            response.sendRedirect(request.getContextPath() + "/mainPage.jsp");
    }
}
