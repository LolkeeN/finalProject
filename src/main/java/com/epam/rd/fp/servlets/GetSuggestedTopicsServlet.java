package com.epam.rd.fp.servlets;

import com.epam.rd.fp.factory.ServiceFactory;
import com.epam.rd.fp.factory.impl.ServiceFactoryImpl;
import com.epam.rd.fp.model.Topic;
import com.epam.rd.fp.service.TopicService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "GetSuggestedTopicsServlet", value = "/getSuggestedTopics")
public class GetSuggestedTopicsServlet extends HttpServlet {
    private final ServiceFactory serviceFactory = new ServiceFactoryImpl();
    private final TopicService topicService = serviceFactory.getTopicService();
    private static final Logger log = LogManager.getLogger(GetSuggestedTopicsServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Topic> topics = topicService.getSuggestedTopics();
            request.setAttribute("suggestedTopics", topics);
        }catch (IllegalArgumentException e){
            log.error(e.getMessage());
            request.getSession().setAttribute("errorMessage", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
            return;
        }
            request.getRequestDispatcher("suggestedTopicsPage.jsp").forward(request, response);
    }
}
