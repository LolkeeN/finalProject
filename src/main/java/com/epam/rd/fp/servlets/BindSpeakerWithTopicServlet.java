package com.epam.rd.fp.servlets;

import com.epam.rd.fp.dao.TopicDao;
import com.epam.rd.fp.dao.UserDao;
import com.epam.rd.fp.factory.DaoFactory;
import com.epam.rd.fp.factory.ServiceFactory;
import com.epam.rd.fp.factory.impl.DaoFactoryImpl;
import com.epam.rd.fp.factory.impl.ServiceFactoryImpl;
import com.epam.rd.fp.service.DBManager;
import com.epam.rd.fp.service.TopicService;
import com.epam.rd.fp.service.impl.TopicServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "BindSpeakerWithTopicServlet", value = "/bindSpeakerWithTopic")
public class BindSpeakerWithTopicServlet extends HttpServlet {
    private final ServiceFactory serviceFactory = new ServiceFactoryImpl();
    private final TopicService topicService = serviceFactory.getTopicService();
    private static final Logger log = LogManager.getLogger(BindSpeakerWithTopicServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try {
            topicService.bindSpeakerToTopic(Integer.parseInt(request.getParameter("speaker_id")), Integer.parseInt(request.getParameter("topic_id")));
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            request.getSession().setAttribute("errorMessage", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
            return;
        }
            response.sendRedirect(request.getContextPath() + "/adminPage.jsp");
    }
}
