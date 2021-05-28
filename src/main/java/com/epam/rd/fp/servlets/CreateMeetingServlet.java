package com.epam.rd.fp.servlets;

import com.epam.rd.fp.dao.LocationDao;
import com.epam.rd.fp.dao.MeetingDao;
import com.epam.rd.fp.dao.MeetingLocationDao;
import com.epam.rd.fp.model.Meeting;
import com.epam.rd.fp.model.Topic;
import com.epam.rd.fp.model.enums.Language;
import com.epam.rd.fp.service.DBManager;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "CreateMeetingServlet", value = "/createMeeting")
public class CreateMeetingServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        meeting.setLocation(locationDao.getLocation(Integer.parseInt(location_id)));
        meetingDao.insertMeeting(meeting);
        meetingLocationDao.bindLocationIdWithMeetingId(Integer.parseInt(location_id), meeting.getId());
        request.setAttribute("meeting_id", meeting.getId());
        request.getRequestDispatcher("createTopicPage.jsp").forward(request, response);

    }
}
