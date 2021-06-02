package com.epam.rd.fp.servlets;

import com.epam.rd.fp.dao.MeetingDao;
import com.epam.rd.fp.model.Meeting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(name = "GetFutureMeetingsServlet", value = "/getFutureMeetings")
public class GetFutureMeetingsServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(GetFutureMeetingsServlet.class);
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/meetings?createDatabaseIfNotExist=true&user=root&password=myrootpass";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean exceptionCaught = false;
        MeetingDao meetingDao = new MeetingDao();
        List<Meeting> futureMeetings = new ArrayList<>();
        List<Meeting> allMeetings;
        DateFormat df = new SimpleDateFormat("dd.MM.yy");

        try {
            allMeetings = meetingDao.getAllMeetings(CONNECTION_URL);
            for (Meeting meeting : allMeetings) {
                String dateStr = meeting.getDate();
                Date date = df.parse(dateStr);
                if (new Date().getTime() < (date.getTime())) {
                    futureMeetings.add(meeting);
                }
            }
            request.setAttribute("futureMeetings", futureMeetings);
        }catch (IllegalArgumentException | ParseException e ){
            log.error(e.getMessage());
            exceptionCaught = true;
            request.getSession().setAttribute("errorMessage", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
        }
        if (!exceptionCaught) {
            request.getRequestDispatcher("futureMeetingsPage.jsp").forward(request, response);
        }
    }
}
