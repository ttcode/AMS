/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flight.controller;

import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import system.core.AppCore;
import system.entity.FlightType;
import system.entity.Airport;
import system.entity.Flightplan;

/**
 *
 * @author chatri.pi
 */
@WebServlet(name = "AddFlight", urlPatterns = {"/addFlight"})
public class AddFlight extends HttpServlet
{  
    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        if (!AppCore.initPage(request, response))
            return ;
       
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("AMSPU");
        EntityManager em = emf.createEntityManager();
       
        List<FlightType> flightTypes = null;
        List<Airport> airports = null;
        List<Flightplan> flights = null;
        
       try
       {
            flightTypes = em.createNamedQuery("FlightType.findAll").getResultList();
            airports = em.createNamedQuery("Airport.findAll").getResultList();
            flights = em.createNamedQuery("Flightplan.findAll").getResultList();
       }
       catch (Exception ex) {}
       if (flightTypes == null || airports == null)
           return ;
       
       request.setAttribute("flightTypes", flightTypes);
       request.setAttribute("airports", airports);
       request.setAttribute("flights", flights);
       
       request.setAttribute("headtitle", "Add Flight");
       request.getRequestDispatcher("jsp/flight/addFlight.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
