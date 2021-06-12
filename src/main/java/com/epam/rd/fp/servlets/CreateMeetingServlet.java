package com.epam.rd.fp.servlets;

import com.epam.rd.fp.dao.LocationDao;
import com.epam.rd.fp.dao.MeetingDao;
import com.epam.rd.fp.dao.TopicDao;
import com.epam.rd.fp.factory.ServiceFactory;
import com.epam.rd.fp.factory.impl.ServiceFactoryImpl;
import com.epam.rd.fp.model.Meeting;
import com.epam.rd.fp.model.enums.Language;
import com.epam.rd.fp.service.DBManager;
import com.epam.rd.fp.service.LocationService;
import com.epam.rd.fp.service.MeetingService;
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

@WebServlet(name = "CreateMeetingServlet", value = "/createMeeting")
public class CreateMeetingServlet extends HttpServlet {
    private final ServiceFactory serviceFactory = new ServiceFactoryImpl();
    private final MeetingService meetingService = serviceFactory.getMeetingService();
    private final LocationService locationService = serviceFactory.getLocationService();

    private static final Logger log = LogManager.getLogger(CreateMeetingServlet.class);

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Meeting meeting = getMeeting(request);
        try {
            meeting.setLocation(locationService.getLocation(Integer.parseInt(request.getParameter("location_id"))));
            meetingService.createMeeting(meeting);
            request.setAttribute("meeting_name", meeting.getName());
        } catch (NumberFormatException e) {
            log.error(e.getMessage());
            request.getSession().setAttribute("errorMessage", "Wrong data input format");
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
            return;
        } catch (IllegalArgumentException | NullPointerException e) {
            log.error(e.getMessage());
            request.getSession().setAttribute("errorMessage", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
            return;
        }
            request.getRequestDispatcher("createTopicPage.jsp").forward(request, response);
    }

    private Meeting getMeeting(HttpServletRequest request) {
        Meeting meeting = new Meeting();
        meeting.setName(request.getParameter("meeting_name"));
        meeting.setDate(request.getParameter("date"));
        meeting.setLanguage(Language.fromString(request.getParameter("language")));
        return meeting;
    }
}
