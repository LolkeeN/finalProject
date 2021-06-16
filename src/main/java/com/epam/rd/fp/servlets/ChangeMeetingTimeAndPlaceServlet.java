package com.epam.rd.fp.servlets;

import com.epam.rd.fp.factory.ServiceFactory;
import com.epam.rd.fp.factory.impl.ServiceFactoryImpl;
import com.epam.rd.fp.service.MeetingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ChangeMeetingTimeAndPlaceServlet", value = "/changeMeetingTimeAndPlace")
public class ChangeMeetingTimeAndPlaceServlet extends HttpServlet {
    private final ServiceFactory serviceFactory = new ServiceFactoryImpl();
    private final MeetingService meetingService = serviceFactory.getMeetingService();

    private static final Logger log = LogManager.getLogger(ChangeMeetingTimeAndPlaceServlet.class);

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String date = request.getParameter("date");

        try {
            int meetingId = Integer.parseInt(request.getParameter("meeting_id"));
            int locationId = Integer.parseInt(request.getParameter("location_id"));
            meetingService.changeMeetingTimeAndPlace(meetingId, date, locationId);
        } catch (NumberFormatException e) {
            log.error(e.getMessage(), e);
            request.getSession().setAttribute("errorMessage", "Some fields are empty or have invalid format");
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
            return;
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
            request.getSession().setAttribute("errorMessage", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
            return;
        }
        response.sendRedirect(request.getContextPath() + "/adminPage.jsp");
    }
}
