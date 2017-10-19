package ch.heigvd.amt.bootcamp.controller;

import ch.heigvd.amt.bootcamp.model.CarModel;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Alain
 */
public class ConfigurationServlet extends HttpServlet {

   CarModel carModel = new CarModel();

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
      request.getRequestDispatcher("/WEB-INF/pages/configuration.jsp").forward(request, response);
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
      String fieldValue = request.getParameter("quantity");
      Integer qty = 0;
      
      // Check if the value inputed is a numeric
      if (fieldValue != null && fieldValue.matches("[+]?\\d+")) {
         qty = new Integer(fieldValue);
      } else {
         request.setAttribute("errorMessage", "Please input a positive integer value");
         request.getRequestDispatcher("/WEB-INF/pages/configuration.jsp").forward(request, response);
         return;
      }      
      
      
      // Insert cars and redirect to the list of cars
      carModel.insertRandomCar(qty);
      
      response.sendRedirect("list");      
   }

}
