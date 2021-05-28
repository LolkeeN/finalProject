package com.epam.rd.fp.servlets;

import com.epam.rd.fp.dao.LocationDao;
import com.epam.rd.fp.dao.TopicDao;
import com.epam.rd.fp.model.Location;
import com.epam.rd.fp.model.Topic;
import com.epam.rd.fp.model.enums.Language;
import com.epam.rd.fp.service.DBManager;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "CreateLocationServlet", value = "/createLocation")
public class CreateLocationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LocationDao locationDao = new LocationDao();
        String country = request.getParameter("country");
        String city = request.getParameter("city");
        String street = request.getParameter("street");
        String house = request.getParameter("house");
        String room = request.getParameter("room");
        Language language;
        if ("EN".equalsIgnoreCase(request.getParameter("language"))) {
            language = Language.EN;
        } else {
            language = Language.RU;
        }
        Location location = new Location();
        location.setCity(city);
        location.setCountry(country);
        location.setHouse(house);
        location.setRoom(room);
        location.setStreet(street);
        location.setLanguage(language);
        locationDao.insertLocation(location);
        response.sendRedirect(request.getContextPath() + "/adminPage.jsp");
    }
}

