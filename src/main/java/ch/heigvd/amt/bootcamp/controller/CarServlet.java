package ch.heigvd.amt.bootcamp.controller;

import ch.heigvd.amt.bootcamp.model.CarModel;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Alain
 */
public class CarServlet extends HttpServlet {
   
   CarModel cm = new CarModel();
   
   /**
    * Handles the HTTP <code>GET</code> method.
    *
    * @param request servlet request
    * @param response servlet response
    * @throws ServletException if a servlet-specific error occurs
    * @throws IOException if an I/O error occurs
    */
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
      // If this page is used to edit a car, load and display the info about the car ...
      if(request.getSession().getAttribute("edit") != null) {
         List car = cm.getCar(new Integer((String)request.getSession().getAttribute("id")));
         request.setAttribute("edit", 1);
         request.setAttribute("brand", car.get(1));
         request.setAttribute("color", car.get(2));
         request.setAttribute("horsePower", car.get(3));
         request.setAttribute("price", car.get(4));
         request.setAttribute("edit", 1);
         request.getSession().setAttribute("id", car.get(0));
         // Set the session attribute of edit to null, to avoid blocking this page on 
         //  edit mode.
         request.getSession().setAttribute("edit", null);
      }
      // ... otherwise simply display the form page
      request.getRequestDispatcher("/WEB-INF/pages/car.jsp").forward(request, response);
   }

   /**
    * Handles the HTTP <code>POST</code> method.
    *
    * @param request servlet request
    * @param response servlet response
    * @throws ServletException if a servlet-specific error occurs
    * @throws IOException if an I/O error occurs
    */
   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
      String brand = null;
      String color = null;
      int horsePower = 0;
      int price = 0;
      
      String errorMessage = "";
      
      // Check if the brand is not empty
      if (!request.getParameter("brand").equals("")) {
         brand = request.getParameter("brand");
         request.setAttribute("brand", brand);
      } else {
         errorMessage = errorMessage.concat("Please input a brand");
      }
      
       // Check if the color is not empty
      if (!request.getParameter("color").equals("")) {
         color = request.getParameter("color");
         request.setAttribute("color", color);
      } else {
         // Add a break line  for a better display if there already is an error
         if (!errorMessage.equals("")) {
            errorMessage += "<br/>";
         }
         
         errorMessage = errorMessage.concat("Please input a color");
      }
      
      // Check if the number of horse is a positive integer
      if (request.getParameter("horsePower").matches("[+]?\\d+")) {
         horsePower = new Integer(request.getParameter("horsePower"));
         request.setAttribute("horsePower", horsePower);
      } else {
         // Add a break line  for a better display if there already is an error
         if (!errorMessage.equals("")) {
            errorMessage += "<br/>";
         }
         
         errorMessage = errorMessage.concat("Please input a positive integer for the horsepower");
      }
      
      // Check if the price is a positive int
      if (request.getParameter("price").matches("[+]?\\d+")) {
         price = new Integer(request.getParameter("price"));
         request.setAttribute("price", price);
      } else {
         // Add a break line  for a better display if there already is an error
         if (!errorMessage.equals("")) {
            errorMessage += "<br/>";
         }
         
         errorMessage = errorMessage.concat("Please input a positive number for the price");
      }
      
      if(request.getParameter("edit") != null) {
         request.setAttribute("edit", 1);
      }
      
      // In case of error, return to the page and display the error to the user
      if(!errorMessage.equals("")) {
         request.setAttribute("errorMessage", errorMessage);
         request.getRequestDispatcher("/WEB-INF/pages/car.jsp").forward(request, response);
         return;
      }
      
      // Edit an existing car or insert a new one
      if(request.getParameter("edit") != null) {
         cm.editCar((Integer)request.getSession().getAttribute("id"),
                 request.getParameter("brand"),
                 request.getParameter("color"),
                 new Integer(request.getParameter("horsePower")),
                 new Integer(request.getParameter("price")));
         String page = request.getSession().getAttribute("page") != null ?
                 "?page=" + (String)request.getSession().getAttribute("page") :
                 "";
         response.sendRedirect("list"+page);
         
      } else {
         cm.insertCar(brand, color, horsePower, price);
         response.sendRedirect("car");
      }
   }

}
