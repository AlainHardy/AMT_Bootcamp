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
public class ListServlet extends HttpServlet {

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
      int currentPage = 1;
      int limit = 10;
      int nbOfCar = cm.getNumberOfCars();
      if(nbOfCar > 0) {
         int lastPageNb = (int)Math.ceil((double)nbOfCar / limit);

         // Check if page is set and if it is not too big
         if (request.getParameter("page") != null && !request.getParameter("page").equals("0") && request.getParameter("page").matches("\\d+") ) {
            currentPage = new Integer(request.getParameter("page"));
         }
         // Set the number of page to the maximum of pages possible to create if too high
         if (currentPage > lastPageNb) {
            currentPage = lastPageNb;         
         }

         // If there is less pages in total than the maximum allowed, then the number
         //  of pages to display is set to the available amount (lastPageNb). 
         //  Otherwise to the maximum possible (maxPages).
         // The maximum number of pages to show should be an odd number, so there is the
         //  same amount of number on both side of the active page. 
         // The first and last pages are always visible in the pagination.
         int maxPages = 13;
         int nbOfPages = lastPageNb <= maxPages ? lastPageNb : maxPages;

         
         // If the number of number of pages is less or equal than the maximum of
         //  of pages to display, store number from 1 to nbOfPages. Look at the 
         //  initialization of nbOfPages for a better understanding.
         int[] pages = new int[nbOfPages];
         if(nbOfPages == lastPageNb) {
            for(int i = 0; i < nbOfPages; i++) {
               pages[i] = i+1;
            }
         }
         // Otherwise display 'maxPages' pages. The active page is on the center of 
         //  the group, except when to close to the first and last pages.
         else {
            pages[0] = 1; // first page always the first one ...
            // shit is the shift than need to be applied for :
            //  - Normal case: the active page to be centered
            //  - Close to the first page: not shifting the centered page while still
            //                             close enough from the first page.
            //  - Close to the last page: same as the first page, but the other way
            //                            around.
            int shift = (maxPages-1)/2;
            // When the current page is lesser than the Xth closest page from the
            //  first one, compensate the shift to not make any changes.
            // Ex. for 13; X : (13+1)/2 = 7
            if(currentPage < (maxPages+1)/2) {
               shift = 0 + currentPage - 1;
            }
            // When the current page is greather than Xth closest page from the last
            //  one, compensate the shift to not make any changes.
            // Ex. for 13; X : 100-(13+1)/2 = 100-7 = 93
            if(currentPage > lastPageNb-(maxPages+1)/2) {
               shift = currentPage - lastPageNb+maxPages-1;
            }
            for(int i = 1; i < nbOfPages-1; i++) {
               pages[i] = currentPage-shift + i;
            }
            pages[nbOfPages-1] = lastPageNb; // ... and last page the last one.
         }

         List<List> cars = cm.getCars(limit, currentPage);

         request.setAttribute("cars", cars);
         request.setAttribute("page", currentPage);
         request.setAttribute("pages", pages);
         request.setAttribute("nbOfPages", nbOfPages);
      }      
      
      request.getRequestDispatcher("/WEB-INF/pages/list.jsp").forward(request, response);
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
      
      // Prepare the data to be sent to the car servlet for the edition part
      if(request.getParameter("edit") != null) {
         request.getSession().setAttribute("edit", true);
         request.getSession().setAttribute("id", request.getParameter("edit"));
         request.getSession().setAttribute("page", request.getParameter("page"));
         response.sendRedirect("car");
      } 
      // Delete the car and send back the user to the list of car
      else if(request.getParameter("delete") != null) {
         cm.deleteCar(new Integer(request.getParameter("delete")));
         int page = 1;
         if (request.getParameter("page") != null) {
            page = new Integer(request.getParameter("page"));
         }
         response.sendRedirect("list?page="+page);
      }
   }
}
