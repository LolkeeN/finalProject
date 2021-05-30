package com.epam.rd.fp.servlets;

import com.epam.rd.fp.dao.MeetingTopicDao;
import com.epam.rd.fp.dao.TopicDao;
import com.epam.rd.fp.model.Topic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "GetSuggestedTopicsServlet", value = "/getSuggestedTopics")
public class GetSuggestedTopicsServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(GetSuggestedTopicsServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MeetingTopicDao  meetingTopicDao = new MeetingTopicDao();
        List<Topic> topics = meetingTopicDao.getSuggestedTopics();
        request.setAttribute("suggestedTopics", topics);
        request.getRequestDispatcher("suggestedTopicsPage.jsp").forward(request, response);
    }
}
