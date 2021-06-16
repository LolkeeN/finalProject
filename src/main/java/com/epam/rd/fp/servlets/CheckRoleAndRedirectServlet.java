package com.epam.rd.fp.servlets;

import com.epam.rd.fp.model.enums.Role;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CheckRoleAndRedirectServlet", value = "/CheckRoleAndRedirectServlet")
public class CheckRoleAndRedirectServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userRole = 0;
        try {
            userRole = (int) request.getSession().getAttribute("role");
        } catch (NullPointerException e) {
            response.sendRedirect(request.getContextPath() + "/loginPage.jsp");
        }

        if (userRole == Role.USER.getValue()) {
            response.sendRedirect(request.getContextPath() + "/mainPage.jsp");
        } else if (userRole == Role.MODERATOR.getValue()) {
            response.sendRedirect(request.getContextPath() + "/adminPage.jsp");
        } else if (userRole == Role.SPEAKER.getValue()) {
            response.sendRedirect(request.getContextPath() + "/speakerPage.jsp");
        }
    }
}
