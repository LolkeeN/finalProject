package com.epam.rd.fp.servlets;

import com.epam.rd.fp.dao.MeetingDao;
import com.epam.rd.fp.factory.ServiceFactory;
import com.epam.rd.fp.factory.impl.ServiceFactoryImpl;
import com.epam.rd.fp.model.Meeting;
import com.epam.rd.fp.service.MeetingService;
import com.epam.rd.fp.service.TopicService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(name = "GetFutureMeetingsServlet", value = "/getFutureMeetings")
public class GetFutureMeetingsServlet extends HttpServlet {
    private final ServiceFactory serviceFactory = new ServiceFactoryImpl();
    private final MeetingService meetingService = serviceFactory.getMeetingService();
    private static final Logger log = LogManager.getLogger(GetFutureMeetingsServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Meeting> futureMeetings = new ArrayList<>();
        DateFormat df = new SimpleDateFormat("dd.MM.yy");

        try {
            request.setAttribute("futureMeetings", getFutureMeetings(futureMeetings, df));
        }catch (IllegalArgumentException | ParseException e ){
            log.error(e.getMessage());
            request.getSession().setAttribute("errorMessage", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
            return;
        }
            request.getRequestDispatcher("futureMeetingsPage.jsp").forward(request, response);
    }

    private List<Meeting> getFutureMeetings(List<Meeting> futureMeetings, DateFormat df) throws ParseException {
        List<Meeting> allMeetings;
        allMeetings = meetingService.getAllMeetings();
        for (Meeting meeting : allMeetings) {
            String dateStr = meeting.getDate();
            Date date = df.parse(dateStr);
            if (new Date().getTime() < (date.getTime())) {
                futureMeetings.add(meeting);
            }
        }
        return futureMeetings;
    }
}
