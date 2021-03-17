package edu.aydin.web_final.Controller;

import edu.aydin.web_final.Database.Database;
import edu.aydin.web_final.Model.Box;
import edu.aydin.web_final.Model.Product;
import edu.aydin.web_final.Model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "buyServlet", urlPatterns = "/buy")
public class BuyController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        // Checking the which user has logged in
        String username = session.getAttribute("username").toString();
        User loggedUser = Database.getInstance().getUser(username);

        ArrayList<Box> boxIdList = Database.getInstance().getBoxIds(loggedUser.getId());
        for (Box box: boxIdList){

            // Update the stock
            Database.getInstance().updateStock(box.getProductId());
            // Clear the box
            Database.getInstance().clearBox(box.getBoxId());
        }


        // render the home page.
        resp.sendRedirect("home");
    }
}
