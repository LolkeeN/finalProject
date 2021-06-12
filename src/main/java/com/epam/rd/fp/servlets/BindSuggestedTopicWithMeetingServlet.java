package com.epam.rd.fp.servlets;

import com.epam.rd.fp.dao.MeetingDao;
import com.epam.rd.fp.dao.TopicDao;
import com.epam.rd.fp.factory.ServiceFactory;
import com.epam.rd.fp.factory.impl.ServiceFactoryImpl;
import com.epam.rd.fp.service.DBManager;
import com.epam.rd.fp.service.MeetingService;
import com.epam.rd.fp.service.TopicService;
import com.epam.rd.fp.service.impl.MeetingServiceImpl;
import com.epam.rd.fp.service.impl.TopicServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "BindSuggestedTopicWithMeetingServlet", value = "/bindSuggestedTopicWithMeeting")
public class BindSuggestedTopicWithMeetingServlet extends HttpServlet {
    private final ServiceFactory serviceFactory = new ServiceFactoryImpl();
    private final MeetingService meetingService = serviceFactory.getMeetingService();


    private static final Logger log = LogManager.getLogger(BindSuggestedTopicWithMeetingServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try {
            meetingService.bindSuggestedTopicWithMeeting(Integer.parseInt(request.getParameter("topic_id")), Integer.parseInt(request.getParameter("meeting_id")));
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            request.getSession().setAttribute("errorMessage", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
            return;
        }
        response.sendRedirect(request.getContextPath() + "/adminPage.jsp");
    }
}
