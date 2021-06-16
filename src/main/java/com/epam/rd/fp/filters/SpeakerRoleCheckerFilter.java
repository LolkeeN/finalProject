package com.epam.rd.fp.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/speakerPage.jsp", "/chooseFreeTopic.jsp", "/speakerMeetings.jsp", "/chooseFreeTopic.jsp",
        "/suggestATopic.jsp"})
public class SpeakerRoleCheckerFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpReq =
                (HttpServletRequest) req;

        HttpSession session = httpReq.getSession();

        int role = (int) session.getAttribute("role");

        if (role != 3) {
            session.setAttribute("errorMessage", "Access denied");
            ((HttpServletResponse) resp).sendRedirect("errorPage.jsp");
            return;
        }

        chain.doFilter(req, resp);
    }

}
