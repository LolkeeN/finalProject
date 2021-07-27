package com.epam.rd.fp.servlets;

import com.epam.rd.fp.factory.ServiceFactory;
import com.epam.rd.fp.factory.impl.ServiceFactoryImpl;
import com.epam.rd.fp.model.User;
import com.epam.rd.fp.model.enums.Role;
import com.epam.rd.fp.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "registrationServlet", value = "/registration")
public class RegistrationServlet extends HttpServlet {
    private final ServiceFactory serviceFactory = new ServiceFactoryImpl();
    private final UserService userService = serviceFactory.getUserService();
    private static final Logger log = LogManager.getLogger(RegistrationServlet.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
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

        for (String elem : parameterList) {
            if (elem.equals("")) {
                request.getSession().setAttribute("errorMessage", "Some fields are empty");
                response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
                return;
            }
        }


        Role role = null;
        try {
            if (!email.contains("@") && !email.contains(".")) {
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
        } catch (IllegalArgumentException | IllegalStateException e) {
            log.error(e.getMessage(), e);
            request.getSession().setAttribute("errorMessage", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
            return;
        }
        User user = new User(firstName, lastName, password, email, role);
        try {
            boolean isRegistered = userService.isAlreadyRegistered(user.getEmail());
            if (isRegistered) {
                log.error("user's already registered");
                request.getSession().setAttribute("errorMessage", "User's already registered");
                response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
                return;
            } else {
                userService.insertUser(user);
                request.getSession().setAttribute("first_name", user.getFirstName());
                request.getSession().setAttribute("last_name", user.getLastName());
                request.getSession().setAttribute("id", user.getId());
                request.getSession().setAttribute("email", user.getEmail());
                request.getSession().setAttribute("role", user.getRole().getValue());
            }
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
            request.getSession().setAttribute("errorMessage", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
            return;
        }
        checkRoleAndRedirect(request, response, user);
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