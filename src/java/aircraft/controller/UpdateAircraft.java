/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aircraft.controller;

import java.io.IOException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import system.core.AppCore;
import system.entity.Aircraft;

@WebServlet(name = "UpdateAircraft", urlPatterns = {"/UpdateAircraft"})
public class UpdateAircraft extends HttpServlet 
{
    @PersistenceUnit(unitName="AMSPU")
    EntityManagerFactory emf;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        if (!AppCore.initPage(request, response))
            return ;
        request.setAttribute("headtitle", "Update Aircraft");
        EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery("AircraftModel.findAll");
        request.setAttribute("aircraftModelList", query.getResultList());
        
        query=em.createNamedQuery("Airport.findAll");
        request.setAttribute("airportList", query.getResultList());
        
        query = em.createNamedQuery("Flightplan.findAll");
        request.setAttribute("flightplanList", query.getResultList());
        
        query = em.createNamedQuery("Aircraft.findById");
        if (request.getParameter("id") != null)
            query.setParameter("id", Integer.parseInt(request.getParameter("id")));
        else
            return ;
        request.setAttribute("aircraft", (Aircraft)query.getSingleResult());
        
        request.getRequestDispatcher("jsp/aircraft/UpdateAircraft.jsp").forward(request, response);
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
