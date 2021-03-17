package edu.aydin.web_final.Controller;

import edu.aydin.web_final.Database.Database;
import edu.aydin.web_final.Model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "indexServlet", urlPatterns = "/login")
public class IndexController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String error = (String) req.getAttribute("error");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path="";
        // get the username and the password from request.
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if(checkUser(username,password)){
            // if user exists in the database
            HttpSession session = req.getSession();
            // getting the session and adding the username attribute with given username.
            session.setAttribute("username",username);
            // render the home page where the products are listed.
            resp.sendRedirect("home");
        }else{
            // user does not exist
            path ="index.jsp"; // initialize the path value with login page which is index.jsp and render.
            RequestDispatcher requestDispatcher = req.getRequestDispatcher(path);
            requestDispatcher.forward(req,resp);
        }
    }

    /**
     * @param username that will be checking in the database
     * @param password that will be checking in the database
     * @return true if user exist, otherwise false.
     */
    private boolean checkUser(String username, String password){
        // Trying the get the user credentials in the database
        User dbUser = Database.getInstance().getUser(username);
        if(dbUser != null){
            // user exist
            if(username.equals(dbUser.getName()) && password.equals(dbUser.getPassword())){
                // username and password are correct
                return true;
            }
        }
        return false; // user does not exist.
    }
}
