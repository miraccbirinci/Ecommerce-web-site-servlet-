package edu.aydin.web_final.Controller;

import edu.aydin.web_final.Database.Database;
import edu.aydin.web_final.Model.Box;
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

@WebServlet(name = "boxServlet", urlPatterns = "/box")
public class BoxController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // getting the session
        HttpSession session = req.getSession();
        User loggedUser = null;
        // Checking the which user has logged in
        if(session.getAttribute("username") != null) {
            loggedUser = Database.getInstance().getUser(session.getAttribute("username").toString());
        }else{
            resp.sendRedirect("home");
            return;
        }

        try{
            // get the product for user has added to box.
            ArrayList<Product> boxList = Database.getInstance().getBoxByUserId(loggedUser.getId());
            int totalPrice = 0;
            for (Product product: boxList){
                totalPrice += product.getProductPrice();
            }
            // adding the boxList to the request.
            req.setAttribute("boxList", boxList);
            req.setAttribute("totalPrice", totalPrice);
            // get the boxId's
            ArrayList<Box> boxIdList = Database.getInstance().getBoxIds(loggedUser.getId());
            // adding the boxIdList, to the request.
            req.setAttribute("boxIdList", boxIdList);
        }catch (NullPointerException e){

        }

        // render the box page, with user boxes.
        RequestDispatcher rd = req.getRequestDispatcher("box.jsp");
        rd.forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId");
        String productId = req.getParameter("productId");
        // adding the product to the user.
        Database.getInstance().addToBox(userId, productId);
        // render the home page, for being able to shopping more.
        resp.sendRedirect("home");
    }
}
