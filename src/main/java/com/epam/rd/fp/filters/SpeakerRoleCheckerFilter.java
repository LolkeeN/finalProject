//package com.epam.rd.fp.filters;
//
//import com.epam.rd.fp.servlets.BindSuggestedTopicWithMeetingServlet;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import javax.servlet.*;
//import javax.servlet.annotation.*;
//import java.io.IOException;
//
//@WebFilter(servletNames = "/speakerPage")
//public class SpeakerRoleCheckerFilter implements Filter {
//    private static final Logger log = LogManager.getLogger(SpeakerRoleCheckerFilter.class);
//    private FilterConfig config = null;
//    private boolean active = false;
//
//    public void init(FilterConfig config) throws ServletException {
//        this.config = config;
//        String act = config.getInitParameter("active");
//        if (act != null)
//            active = (act.toUpperCase().equals("TRUE"));
//    }
//
//    public void destroy() {
//        config = null;
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
//        if (active){
//            if((int)request.getAttribute("role") != 3){
//                log.info("You are not a speaker");
//                request.getRequestDispatcher("accessDeniedPage.jsp").forward(request, response);
//            }
//        }
//        chain.doFilter(request, response);
//    }
//}
