package com.epam.rd.fp.servlets;

import com.epam.rd.fp.exceptions.InvalidLocationException;
import com.epam.rd.fp.factory.ServiceFactory;
import com.epam.rd.fp.factory.impl.ServiceFactoryImpl;
import com.epam.rd.fp.model.Location;
import com.epam.rd.fp.model.enums.Language;
import com.epam.rd.fp.service.LocationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CreateLocationServlet", value = "/createLocation")
public class CreateLocationServlet extends HttpServlet {
    private final ServiceFactory serviceFactory = new ServiceFactoryImpl();
    private static final Logger log = LogManager.getLogger(CreateLocationServlet.class);
    private final LocationService locationService = serviceFactory.getLocationService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        Location location = getLocation(request);
        try {
            validateLocation(location);
        } catch (InvalidLocationException e) {
            request.getSession().setAttribute("errorMessage", "Some fields are empty");
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
            return;
        }

        try {
            locationService.createLocation(location);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
            request.getSession().setAttribute("errorMessage", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
            return;
        }
        response.sendRedirect(request.getContextPath() + "/adminPage.jsp");
    }

    private Location getLocation(HttpServletRequest request) {
        Location location = new Location();
        location.setCity(request.getParameter("city"));
        location.setCountry(request.getParameter("country"));
        location.setHouse(request.getParameter("house"));
        location.setRoom(request.getParameter("room"));
        location.setStreet(request.getParameter("street"));
        location.setLanguage(Language.fromString(request.getParameter("language")));
        return location;
    }

    private void validateLocation(Location location) {
        if (location.getCity().isEmpty()) {
            throw new InvalidLocationException("City is empty!");
        }
        if (location.getCountry().isEmpty()) {
            throw new InvalidLocationException("City is empty!");
        }
        if (location.getHouse().isEmpty()) {
            throw new InvalidLocationException("City is empty!");
        }
        if (location.getRoom().isEmpty()) {
            throw new InvalidLocationException("City is empty!");
        }
        if (location.getStreet().isEmpty()) {
            throw new InvalidLocationException("City is empty!");
        }
    }
}

