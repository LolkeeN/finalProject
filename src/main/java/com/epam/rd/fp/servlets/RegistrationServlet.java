package com.epam.rd.fp.servlets;

import com.epam.rd.fp.dao.UserDao;
import com.epam.rd.fp.model.User;
import com.epam.rd.fp.model.enums.Role;
import liquibase.pro.packaged.C;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "registrationServlet", value = "/registration")
public class RegistrationServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(RegistrationServlet.class);
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/meetings?createDatabaseIfNotExist=true&user=root&password=myrootpass";

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDao userDao = new UserDao();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            log.error("No suitable driver found", e);
        }
        request.setCharacterEncoding("UTF-8");
        boolean exceptionCaught = false;
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String roleValue = request.getParameter("role");
        roleValue = roleValue.toLowerCase();

        List<String> parameterList = new ArrayList<>();
        parameterList.add(email);
        parameterList.add(password);
        parameterList.add(firstName);
        parameterList.add(lastName);
        parameterList.add(roleValue);
        exceptionCaught = checkForEmptyFields(request, response, exceptionCaught, parameterList);


        Role role = null;
        try {
            if (!email.contains("@") && !email.contains(".")){
                throw new IllegalArgumentException("Wrong email format");
            }
            switch (roleValue) {
                case "user":
                    role = Role.USER;
                    break;
                case "speaker":
                    role = Role.SPEAKER;
                    break;
                case "specialkeyforadminrole":
                    role = Role.MODERATOR;
                    break;
                case "":
                    log.info("Role hasn't been chosen");
                    throw new IllegalArgumentException("You've't chosen a role");
                default:
                    throw new IllegalStateException("Unexpected value: " + roleValue);
            }
        }catch (IllegalArgumentException | IllegalStateException e){
            log.error(e.getMessage());
            exceptionCaught = true;
            request.getSession().setAttribute("errorMessage", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
        }
        if (!exceptionCaught) {
            User user = new User(firstName, lastName, password, email, role);
            try {
                Connection connection = DriverManager.getConnection(CONNECTION_URL);
                boolean isRegistered = userDao.isAlreadyRegistered(connection, user.getEmail());
                if (isRegistered){
                    log.error("user's already registered");
                    exceptionCaught = true;
                    request.getSession().setAttribute("errorMessage", "User's already registered");
                    response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
                }else {
                    userDao.insertUser(connection, user);
                    request.getSession().setAttribute("first_name", user.getFirstName());
                    request.getSession().setAttribute("last_name", user.getLastName());
                    request.getSession().setAttribute("id", user.getId());
                    request.getSession().setAttribute("email", user.getEmail());
                    request.getSession().setAttribute("role", user.getRole().getValue());
                }
            } catch (IllegalArgumentException | SQLException e) {
                log.error(e.getMessage());
                exceptionCaught = true;
                request.getSession().setAttribute("errorMessage", e.getMessage());
                response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
            }
            if (!exceptionCaught) {
                checkRoleAndRedirect(request, response, user);
            }
        }
    }

    private boolean checkForEmptyFields(HttpServletRequest request, HttpServletResponse response, boolean exceptionCaught, List<String> parameterList) throws IOException {
        for (String elem: parameterList) {
            if (elem.equals("")){
                if (!exceptionCaught) {
                    exceptionCaught = true;
                    request.getSession().setAttribute("errorMessage", "Some fields are empty");
                    response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
                }
            }
        }
        return exceptionCaught;
    }

    static void checkRoleAndRedirect(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException {
        if (user.getRole().getValue() == 1) {
            request.getRequestDispatcher("mainPage.jsp").forward(request, response);
        } else if (user.getRole().getValue() == 2) {
            response.sendRedirect(request.getContextPath() + "/adminPage.jsp");
        } else if (user.getRole().getValue() == 3) {
            request.getRequestDispatcher("speakerPage.jsp").forward(request, response);

        }
    }
}