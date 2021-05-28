package com.epam.rd.fp.servlets;

import com.epam.rd.fp.model.Meeting;
import com.epam.rd.fp.model.Topic;
import com.epam.rd.fp.model.enums.Language;
import com.epam.rd.fp.service.DBManager;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "CreateMeetingServlet", value = "/createMeeting")
public class CreateMeetingServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String date = request.getParameter("date");
        String location_id = request.getParameter("location_id");
        Language language;
        if ("EN".equals(request.getParameter("language"))){
            language = Language.EN;
        }else{
            language = Language.RU;
        }
        DBManager dbManager = DBManager.getInstance();
        Meeting meeting = new Meeting();
        meeting.setName(name);
        meeting.setDate(date);
        meeting.setLanguage(language);
        try {
            dbManager.insertMeeting(meeting);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.sendRedirect(request.getContextPath() + "/adminPage.jsp");
   }
}
