package com.epam.rd.fp.servlets;

import com.epam.rd.fp.dao.LocationDao;
import com.epam.rd.fp.dao.MeetingDao;
import com.epam.rd.fp.dao.MeetingLocationDao;
import com.epam.rd.fp.model.Meeting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ChangeMeetingTimeAndPlaceServlet", value = "/changeMeetingTimeAndPlace")
public class ChangeMeetingTimeAndPlaceServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(ChangeMeetingTimeAndPlaceServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean exceptionCaught = false;
        MeetingDao meetingDao = new MeetingDao();
        MeetingLocationDao meetingLocationDao = new MeetingLocationDao();

        String meetingName = request.getParameter("name");
        String date = request.getParameter("date");

        try {
            Meeting meeting = meetingDao.getMeeting(meetingName);
            int locationId = Integer.parseInt(request.getParameter("location_id"));
            meetingDao.setMeetingDate(meetingName, date);
            meetingLocationDao.setMeetingLocation(meeting.getId(), locationId);
        }catch (IllegalArgumentException e){
            log.error(e.getMessage(), e);
            exceptionCaught = true;
            request.getSession().setAttribute("errorMessage", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
        }
        if (!exceptionCaught) {
            response.sendRedirect(request.getContextPath() + "/adminPage.jsp");
        }
    }
}
