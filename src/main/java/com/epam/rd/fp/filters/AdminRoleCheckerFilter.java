package com.epam.rd.fp.filters;

import org.apache.commons.lang3.ObjectUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(urlPatterns = {"/adminPage.jsp", "/createLocation.jsp", "/createMeetingPage.jsp", "/createTopicPage.jsp",
        "/changeMeetingTimeAndPlacePage.jsp", "/changeTopicBySpeakerPage.jsp", "/futureMeetingsPage",
        "/meetingParticipantsAndRegisteredCount.jsp", "/pastMeetingsPage.jsp", "/setTopicSpeaker.jsp"})
public class AdminRoleCheckerFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        int role;

        HttpServletRequest httpReq =
                (HttpServletRequest) req;

        HttpSession session = httpReq.getSession();
        try {
            role = (int) session.getAttribute("role");
        }catch (NullPointerException e){
            session.setAttribute("errorMessage", "Access denied");
            ((HttpServletResponse) resp).sendRedirect("errorPage.jsp");
            return;
        }

        if (role != 2) {
            session.setAttribute("errorMessage", "Access denied");
            ((HttpServletResponse) resp).sendRedirect("errorPage.jsp");
            return;
        }

        chain.doFilter(req, resp);
    }

}
