package com.epam.rd.fp.servlets;

import com.epam.rd.fp.factory.ServiceFactory;
import com.epam.rd.fp.factory.impl.ServiceFactoryImpl;
import com.epam.rd.fp.service.TopicService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "BindFreeTopicServlet", value = "/bindFreeTopic")
public class BindFreeTopicServlet extends HttpServlet {
    private final ServiceFactory serviceFactory = new ServiceFactoryImpl();
    private final TopicService topicService = serviceFactory.getTopicService();
    private static final Logger log = LogManager.getLogger(BindFreeTopicServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try {
           topicService.bindSpeakerWithFreeTopic((int)request.getSession().getAttribute("id"), Integer.parseInt(request.getParameter("topic_id")));
        }catch (IllegalArgumentException e){
            request.getSession().setAttribute("errorMessage", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
            return;
        }
            request.getRequestDispatcher("speakerPage.jsp").forward(request, response);
    }
}
