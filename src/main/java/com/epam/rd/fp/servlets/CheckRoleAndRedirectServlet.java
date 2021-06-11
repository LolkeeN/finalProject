package com.epam.rd.fp.servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CheckRoleAndRedirectServlet", value = "/CheckRoleAndRedirectServlet")
public class CheckRoleAndRedirectServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userRole = 0;
        try {
            userRole = (int) request.getSession().getAttribute("role");
        }catch (NullPointerException e){
            response.sendRedirect(request.getContextPath() + "/loginPage.jsp");
        }

        if (userRole == 1){
            response.sendRedirect(request.getContextPath() + "/mainPage.jsp");
        }else if(userRole == 2){
            response.sendRedirect(request.getContextPath() + "/adminPage.jsp");
        }else if(userRole == 3) {
            response.sendRedirect(request.getContextPath() + "/speakerPage.jsp");
        }
    }
}
