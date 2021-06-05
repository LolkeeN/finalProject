package com.epam.rd.fp.servlets;

import com.epam.rd.fp.dao.LocationDao;
import com.epam.rd.fp.dao.MeetingLocationDao;
import com.epam.rd.fp.dao.TopicDao;
import com.epam.rd.fp.model.Location;
import com.epam.rd.fp.model.Topic;
import com.epam.rd.fp.model.enums.Language;
import com.epam.rd.fp.service.DBManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebServlet(name = "CreateLocationServlet", value = "/createLocation")
public class CreateLocationServlet extends HttpServlet {
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/meetings?createDatabaseIfNotExist=true&user=root&password=myrootpass";
    private static final Logger log = LogManager.getLogger(CreateLocationServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean exceptionCaught = false;
        request.setCharacterEncoding("UTF-8");
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
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(CONNECTION_URL);
            locationDao.insertLocation(connection, location);
        }catch (IllegalArgumentException | ClassNotFoundException | SQLException e){
            log.error(e.getMessage());
            exceptionCaught = true;
            request.getSession().setAttribute("errorMessage", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
        }
        if (!exceptionCaught) {
            response.sendRedirect(request.getContextPath() + "/adminPage.jsp");
        }
    }
}

