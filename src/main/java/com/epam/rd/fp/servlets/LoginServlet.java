package com.epam.rd.fp.servlets;

import com.epam.rd.fp.dao.UserDao;
import com.epam.rd.fp.model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String email = request.getParameter("email");
    String password = request.getParameter("password");
        UserDao userDao = new UserDao();
        User user = userDao.getUser(email, password);
        request.getSession().setAttribute("firstName", user.getFirstName());
        request.getSession().setAttribute("lastName", user.getLastName());
        request.getSession().setAttribute("role", user.getRole().getValue());
        request.getSession().setAttribute("email", user.getEmail());
        request.getSession().setAttribute("password", user.getPassword());
        request.getSession().setAttribute("id", user.getId());
        RegistrationServlet.checkRoleAndRedirect(request, response, user);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
