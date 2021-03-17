package edu.aydin.web_final.Controller;

import edu.aydin.web_final.Database.Database;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "userServlet", urlPatterns = "/addUser")
public class UserController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // render the addUser page
        RequestDispatcher rd = req.getRequestDispatcher("addUser.jsp");
        rd.forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // get the username and the password from form.
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String pw_again = req.getParameter("pw_again");

        // Checking if the both passwords are equal
        if(password.equals(pw_again)){
            // create the user with given credentials.
            Database.getInstance().registerUser(username,password);
            // render the login page to be able to login.
            RequestDispatcher rd = req.getRequestDispatcher("index.jsp");
            rd.forward(req,resp);
        }else{
            // if the credentials are not acceptable, render the register page.
            resp.sendRedirect("addUser");
        }

    }
}
