package com.epam.rd.fp.servlets;

import com.epam.rd.fp.dao.MeetingDao;
import com.epam.rd.fp.dao.TopicDao;
import com.epam.rd.fp.factory.ServiceFactory;
import com.epam.rd.fp.factory.impl.ServiceFactoryImpl;
import com.epam.rd.fp.service.DBManager;
import com.epam.rd.fp.service.MeetingService;
import com.epam.rd.fp.service.impl.MeetingServiceImpl;
import liquibase.pro.packaged.N;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebServlet(name = "ChangeTopicBySpeakerServlet", value = "/changeTopicBySpeaker")
public class ChangeTopicBySpeakerServlet extends HttpServlet {
    private final ServiceFactory serviceFactory = new ServiceFactoryImpl();
    private final MeetingService meetingService = serviceFactory.getMeetingService();
    private static final Logger log = LogManager.getLogger(ChangeTopicBySpeakerServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int meetingId = (int)request.getSession().getAttribute("meeting_id");
        int oldTopicId = (int)request.getSession().getAttribute("old_topic_id");
        int newTopicId = Integer.parseInt(request.getParameter("topic_id"));

        try {
            meetingService.changeTopicBySpeaker(oldTopicId, newTopicId, meetingId);
        }catch (IllegalArgumentException e){
            log.error(e.getMessage());
            request.getSession().setAttribute("errorMessage", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
            return;
        }
            request.getRequestDispatcher("adminPage.jsp").forward(request, response);
    }
}
