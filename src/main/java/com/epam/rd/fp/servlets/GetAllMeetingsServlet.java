package com.epam.rd.fp.servlets;

import com.epam.rd.fp.dao.MeetingDao;
import com.epam.rd.fp.model.Meeting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "GetAllMeetingsServlet", value = "/getAllMeetings")
public class GetAllMeetingsServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(GetAllMeetingsServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MeetingDao meetingDao = new MeetingDao();
        List<Meeting> meetings;
        meetings = meetingDao.getAllMeetings();
        request.setAttribute("meetings", meetings);
        request.getRequestDispatcher("registrationForAMeeting.jsp").forward(request, response);
    }
}
