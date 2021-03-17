package edu.aydin.web_final.Controller;

import edu.aydin.web_final.Database.Database;
import edu.aydin.web_final.Model.Product;
import edu.aydin.web_final.Model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "homeServlet", urlPatterns = "/home")
public class HomeController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Get the whole products, for listing
        ArrayList<Product> productList = Database.getInstance().getProducts();
        // add the productList in the request for being able to use in the jsp file.
        req.setAttribute("productList", productList);

        String username = "";
        // get the session, and the user that has been logged.
        HttpSession session = req.getSession();
        try{
            username = session.getAttribute("username").toString();
            // To be able to set the userId related the logged User.
            User loggedUser = Database.getInstance().getUser(username);
            // adding the userId attribute to the request
            req.setAttribute("userID", loggedUser.getId());
        }catch (NullPointerException e){
            session.setAttribute("username", "Not logged in");
        }

        // Render the home page with products.
        RequestDispatcher rd = req.getRequestDispatcher("home.jsp");
        rd.forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
