package edu.aydin.web_final.Filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class AuthenticationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        // Getting the session if exist, if does not exits, don't create a new one.
        HttpSession session = request.getSession(false);

        // checking the session does not null, and includes the username attribute.
        boolean isLoggedIn = (session != null && session.getAttribute("username") != null);

        String boxURI = request.getContextPath() + "/box";
        boolean isBoxRequest = request.getRequestURI().equals(boxURI); // checking if the user is in box endpoint.
        System.out.println(isBoxRequest);
        System.out.println(isLoggedIn);

        if (isLoggedIn && isBoxRequest ) {
            // the user is already logged in and he's trying to see the box
            RequestDispatcher dispatcher = request.getRequestDispatcher("/box");
            dispatcher.forward(request, response);

        } else if (isLoggedIn || isBoxRequest) {
            // continues the filter chain
            // allows the request to reach the destination
            filterChain.doFilter(request, response);

        } else {
            // the user is not logged in, so authentication is required
            // forwards to the Login page
            RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
            dispatcher.forward(request, response);

        }

    }
}
