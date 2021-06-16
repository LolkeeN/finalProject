package com.epam.rd.fp.servlets;

import com.epam.rd.fp.factory.ServiceFactory;
import com.epam.rd.fp.factory.impl.ServiceFactoryImpl;
import com.epam.rd.fp.model.Meeting;
import com.epam.rd.fp.service.MeetingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
        } catch (IllegalArgumentException | ParseException e) {
            log.error(e.getMessage(), e);
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
