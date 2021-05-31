package com.epam.rd.fp.servlets;

import com.epam.rd.fp.dao.LocationDao;
import com.epam.rd.fp.dao.MeetingDao;
import com.epam.rd.fp.dao.MeetingLocationDao;
import com.epam.rd.fp.model.Meeting;
import com.epam.rd.fp.model.Topic;
import com.epam.rd.fp.model.enums.Language;
import com.epam.rd.fp.service.DBManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "CreateMeetingServlet", value = "/createMeeting")
public class CreateMeetingServlet extends HttpServlet {
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/meetings?createDatabaseIfNotExist=true&user=root&password=myrootpass";
    private static final Logger log = LogManager.getLogger(CreateMeetingServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean exceptionCaught = false;
        String name = request.getParameter("meeting_name");
        String date = request.getParameter("date");
        String location_id = request.getParameter("location_id");
        Language language;
        if ("EN".equalsIgnoreCase(request.getParameter("language"))){
            language = Language.EN;
        }else{
            language = Language.RU;
        }

        LocationDao locationDao = new LocationDao();
        MeetingDao meetingDao = new MeetingDao();
        MeetingLocationDao meetingLocationDao = new MeetingLocationDao();

        Meeting meeting = new Meeting();
        meeting.setName(name);
        meeting.setDate(date);
        meeting.setLanguage(language);
        try {
            meeting.setLocation(locationDao.getLocation(CONNECTION_URL, Integer.parseInt(location_id)));
            meetingDao.insertMeeting(CONNECTION_URL, meeting);
            meetingLocationDao.bindLocationIdWithMeetingId(CONNECTION_URL, Integer.parseInt(location_id), meeting.getId());
            request.setAttribute("meeting_name", meeting.getName());
        }catch (IllegalArgumentException e){
            log.error(e.getMessage());
            exceptionCaught = true;
            request.getSession().setAttribute("errorMessage", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
        }
        if (!exceptionCaught) {
            request.getRequestDispatcher("createTopicPage.jsp").forward(request, response);
        }
    }
}
