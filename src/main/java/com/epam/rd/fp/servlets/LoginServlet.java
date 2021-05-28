package com.epam.rd.fp.servlets;

import com.epam.rd.fp.dao.UserDao;
import com.epam.rd.fp.model.User;
import com.epam.rd.fp.service.DBManager;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String email = request.getParameter("email");
    String password = request.getParameter("password");
        UserDao userDao = new UserDao();
        User user = userDao.getUser(email, password);
        request.setAttribute("firstName", user.getFirstName());
        request.setAttribute("lastName", user.getLastName());
        request.setAttribute("role", user.getRole());
        RegistrationServlet.checkRoleAndForward(request, response, user);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
