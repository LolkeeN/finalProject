package com.epam.rd.fp.servlets;

import com.epam.rd.fp.dao.UserDao;
import com.epam.rd.fp.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(LoginServlet.class);
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/meetings?createDatabaseIfNotExist=true&user=root&password=myrootpass";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean exceptionCaught = false;
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        UserDao userDao = new UserDao();
        User user = new User();
        try {
            user = userDao.getUser(CONNECTION_URL, email, password);
            request.getSession().setAttribute("firstName", user.getFirstName());
            request.getSession().setAttribute("lastName", user.getLastName());
            request.getSession().setAttribute("role", user.getRole().getValue());
            request.getSession().setAttribute("email", user.getEmail());
            request.getSession().setAttribute("password", user.getPassword());
            request.getSession().setAttribute("id", user.getId());
            request.setAttribute("id", user.getId());
        }catch (IllegalArgumentException e){
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
