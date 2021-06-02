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
//@WebFilter(servletNames = "/adminPage")
//public class AdminRoleCheckerFilter implements Filter {
//    private static final Logger log = LogManager.getLogger(AdminRoleCheckerFilter.class);
//    private FilterConfig config = null;
//    private boolean active = true;
//
//    public void init(FilterConfig config) throws ServletException {
//    }
//
//    public void destroy() {
//        config = null;
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
//        if (active){
//            if((int)request.getAttribute("role") != 2){
//                System.out.println(request.getAttribute("role"));
//                log.info("You are not an admin");
//                request.getRequestDispatcher("accessDeniedPage.jsp").forward(request, response);
//            }
//        }
//        chain.doFilter(request, response);
//    }
//}
