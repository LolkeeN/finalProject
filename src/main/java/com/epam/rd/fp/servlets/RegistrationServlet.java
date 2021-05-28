package com.epam.rd.fp.servlets;

import com.epam.rd.fp.dao.UserDao;
import com.epam.rd.fp.model.User;
import com.epam.rd.fp.model.enums.Role;
import com.epam.rd.fp.service.DBManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "registrationServlet", value = "/registration")
public class RegistrationServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(RegistrationServlet.class);
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
          case "SPECIALKEYFORADMINROLE":
              role = Role.MODERATOR;

          default:
              log.info("Role haven't been chosen");
              throw new IllegalArgumentException("You've't chosen a role");
      }
        User user = new User(firstName, lastName, password, email, role);
        UserDao userDao = new UserDao();
        userDao.insertUser(user);
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