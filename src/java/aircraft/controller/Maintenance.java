/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aircraft.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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


@WebServlet(name = "Maintenance", urlPatterns = {"/Maintenance"})
public class Maintenance extends HttpServlet {
    @PersistenceUnit(unitName="AMSPU")
    EntityManagerFactory emf;

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
        request.setAttribute("headtitle", "Aircraft Maintenance");
        EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery("Aircraft.findAll");
        List<Aircraft> allAircraft = query.getResultList();
        List<Aircraft> aircraftList = new ArrayList();
        
        Aircraft aircraft;
        Iterator<Aircraft> it = allAircraft.iterator();
        while (it.hasNext())
        {
            aircraft = it.next();
            em.refresh(aircraft);
            if (aircraft.getAircraftStatus() != '1')
                aircraftList.add(aircraft);
        }
        
        if (aircraftList.isEmpty() == false)
            request.setAttribute("avail", "1");
        else
            request.setAttribute("avail", "0");
        request.setAttribute("aircraftList", aircraftList);
        request.getRequestDispatcher("jsp/aircraft/Maintenance.jsp").forward(request, response);
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
