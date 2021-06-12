package com.epam.rd.fp.servlets;

import com.epam.rd.fp.factory.ServiceFactory;
import com.epam.rd.fp.factory.impl.ServiceFactoryImpl;
import com.epam.rd.fp.model.User;
import com.epam.rd.fp.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    private final ServiceFactory serviceFactory = new ServiceFactoryImpl();
    private final UserService userService = serviceFactory.getUserService();
    private static final Logger log = LogManager.getLogger(LoginServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        User user;
        try {
            user = userService.getUser(email, password);
            request.getSession().setAttribute("firstName", user.getFirstName());
            request.getSession().setAttribute("lastName", user.getLastName());
            request.getSession().setAttribute("role", user.getRole().getValue());
            request.getSession().setAttribute("email", user.getEmail());
            request.getSession().setAttribute("password", user.getPassword());
            request.getSession().setAttribute("id", user.getId());
            request.setAttribute("id", user.getId());
        } catch (IllegalArgumentException | IllegalStateException e) {
            log.error(e.getMessage());
            request.getSession().setAttribute("errorMessage", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
            return;
        } catch (NullPointerException e) {
            log.error(e.getMessage());
            request.getSession().setAttribute("errorMessage", "You are not registered");
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
            return;
        }
        response.sendRedirect(request.getContextPath() + "/CheckRoleAndRedirectServlet");
    }
}
