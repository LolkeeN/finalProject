package com.epam.rd.fp.servlets;

import com.epam.rd.fp.model.User;
import com.epam.rd.fp.model.enums.Role;
import com.epam.rd.fp.service.DBManager;

import java.io.*;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "registrationServlet", value = "/registration")
public class RegistrationServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
      String email = request.getParameter("email");
      String password = request.getParameter("password");
      String firstName = request.getParameter("firstName");
      String lastName = request.getParameter("lastName");
      String roleValue = request.getParameter("role");
      roleValue = roleValue.toLowerCase();
      Role role;
      switch (roleValue){
          case "user":
              role = Role.USER;
              break;
          case "speaker":
              role = Role.SPEAKER;
              break;
          default:
              role = Role.MODERATOR;
      }
        User user = new User(firstName, lastName, password, email, role);
        DBManager dbManager = DBManager.getInstance();
        try {
            dbManager.insertUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        checkRoleAndForward(request, response, user);
    }

    static void checkRoleAndForward(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException {
        if (user.getRole().getValue() == 1) {
            request.getRequestDispatcher("mainPage.jsp").forward(request, response);
        }else if (user.getRole().getValue() == 2){
            request.getRequestDispatcher("adminPage.jsp").forward(request, response);
        }else if (user.getRole().getValue() == 3){
            request.getRequestDispatcher("speakerPage.jsp").forward(request, response);
        }
    }

    public void destroy() {
    }
}