/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pilot.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import system.core.AppCore;
import system.entity.Aircraft;
import system.entity.Employee;
import system.entity.Flightplan;
import system.entity.SystemAccount;
import system.entity.Team;

/**
 *
 * @author Jean
 */
@WebServlet(name = "FlightAssignment", urlPatterns = {"/flightAssignment"})
public class FlightAssignment extends HttpServlet
{
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
        request.setAttribute("headtitle", "Flight Assignment");
        
        HttpSession session = request.getSession();
        SystemAccount systemAccount = (SystemAccount)session.getAttribute("_systemAccount");
        
        boolean status = true;
        EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery("Employee.findById");
        query.setParameter("id", systemAccount.getEmployee().getId());
        Employee employee = (Employee)query.getSingleResult();
        em.refresh(employee);
        Team team = employee.getTeamId();
        Aircraft aircraft;
        Flightplan flightplan;
        if (team == null)
            status = false;
        else
        {
            em.refresh(team);
            aircraft = team.getAircraftId();
            if (aircraft == null)
                status = false;
            else
            {
                em.refresh(aircraft);
                request.setAttribute("aircraft", aircraft);
                flightplan = aircraft.getAssignedFlight();
                if (flightplan == null)
                    status = false;
                else
                {
                    em.refresh(flightplan);
                    request.setAttribute("flightplan", flightplan);
                    if (flightplan.getDepartureAirport().getIcao().equals(aircraft.getPosition().getIcao()) == false)
                        status = false;
                }
            }
        }

        if (status == false)
            request.setAttribute("dispatch", "0");
        else
        {
            
            if (employee.getTeamId().getAircraftId().getAircraftStatus() != '1')
                request.setAttribute("msg", "Aircraft in maintenance");
            else
                request.setAttribute("msg", "");
            request.setAttribute("dispatch", "1");
        }
        request.getRequestDispatcher("jsp/pilot/flightAssignment.jsp").forward(request, response);
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
