package com.epam.rd.fp.servlets;

import com.epam.rd.fp.dao.UserDao;
import com.epam.rd.fp.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(LoginServlet.class);
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/meetings?createDatabaseIfNotExist=true&user=root&password=myrootpass";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        boolean exceptionCaught = false;
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        UserDao userDao = new UserDao();
        User user = new User();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(CONNECTION_URL);
            user = userDao.getUser(connection, email, password);
            request.getSession().setAttribute("firstName", user.getFirstName());
            request.getSession().setAttribute("lastName", user.getLastName());
            request.getSession().setAttribute("role", user.getRole().getValue());
            request.getSession().setAttribute("email", user.getEmail());
            request.getSession().setAttribute("password", user.getPassword());
            request.getSession().setAttribute("id", user.getId());
            request.setAttribute("id", user.getId());
        }catch (IllegalArgumentException | ClassNotFoundException | SQLException | IllegalStateException e){
            log.error(e.getMessage());
            exceptionCaught = true;
            request.getSession().setAttribute("errorMessage", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
        }catch (NullPointerException e){
            log.error(e.getMessage());
            exceptionCaught = true;
            request.getSession().setAttribute("errorMessage", "You are not registered");
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
        }
        if(!exceptionCaught) {
            RegistrationServlet.checkRoleAndRedirect(request, response, user);
        }
    }
}
