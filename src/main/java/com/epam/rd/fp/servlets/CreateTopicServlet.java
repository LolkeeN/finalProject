package com.epam.rd.fp.servlets;

import com.epam.rd.fp.factory.ServiceFactory;
import com.epam.rd.fp.factory.impl.ServiceFactoryImpl;
import com.epam.rd.fp.model.Meeting;
import com.epam.rd.fp.model.Topic;
import com.epam.rd.fp.model.enums.Language;
import com.epam.rd.fp.service.MeetingService;
import com.epam.rd.fp.service.TopicService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(name = "CreateTopicServlet", value = "/createTopic")
public class CreateTopicServlet extends HttpServlet {
    private final ServiceFactory serviceFactory = new ServiceFactoryImpl();
    MeetingService meetingService = serviceFactory.getMeetingService();
    TopicService topicService = serviceFactory.getTopicService();
    private static final Logger log = LogManager.getLogger(CreateTopicServlet.class);

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        DateFormat df = new SimpleDateFormat("dd.MM.yy");

        Topic topic = getTopic(request);
        try {
            Meeting meeting = meetingService.getMeetingById(Integer.parseInt(request.getParameter("meeting_id")));
            String topicDateStr = topic.getDate();
            String dateStr = meeting.getDate();
            Date meetingDate = df.parse(dateStr);
            Date topicDate = df.parse(topicDateStr);
            if (topicDate.getTime() == (meetingDate.getTime())) {
                topicService.insertTopic(topic);
            } else {
                throw new IllegalArgumentException("Topic date must be equals to meeting date");
            }

            List<Topic> topics = new ArrayList<>();
            topics.add(topic);
            meeting.setTopics(topics);
            meetingService.bindTopicIdWithMeetingId(topic.getId(), meeting.getId());
        } catch (NumberFormatException e) {
            log.error(e.getMessage(),e);
            request.getSession().setAttribute("errorMessage", "Wrong data input format");
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
            return;
        } catch (IllegalArgumentException | ParseException e) {
            log.error(e.getMessage(),e);
            request.getSession().setAttribute("errorMessage", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
            return;
        }catch (NullPointerException e){
            log.error(e.getMessage(), e);
            request.getSession().setAttribute("errorMessage", "Some input field are null");
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
            return;
        }
            response.sendRedirect(request.getContextPath() + "/adminPage.jsp");
    }

    private Topic getTopic(HttpServletRequest request) {
        String name = request.getParameter("name");
        String date = request.getParameter("date");
        String description = request.getParameter("description");
        Language language;
        if ("EN".equalsIgnoreCase(request.getParameter("language"))) {
            language = Language.EN;
        } else {
            language = Language.RU;
        }

        Topic topic = new Topic();
        topic.setName(name);
        topic.setDate(date);
        topic.setDescription(description);
        topic.setLanguage(language);
        return topic;
    }

}
