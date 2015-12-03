/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flight.controller;

import aircraft.controller.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;
import system.core.AppCore;
import system.entity.Flightplan;

/**
 *
 * @author chatri.pi
 */
@WebServlet(name = "DeleteFlight", urlPatterns = {"/deleteFlight"})
public class DeleteFlight extends HttpServlet 
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
        try
        {
            EntityManager em = emf.createEntityManager();
            Query           query;
            Flightplan   flightPlan;
            UserTransaction transaction;
            
            if (formCheck(request, response) == false)
                return ;
            String checked[] = request.getParameterValues("flightNumber");
            
            for (int i = 0; i < checked.length; ++i)
            {
                query = em.createNamedQuery("Flightplan.findByFlightNumber");
                query.setParameter("flightNumber", Integer.parseInt(checked[i]));
                flightPlan = (Flightplan)query.getSingleResult();  
                em.remove(flightPlan);
            }
            
            transaction = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
            transaction.begin();
            em.flush();
            transaction.commit(); 
        }
        catch(Exception ex)
        {
            Logger.getLogger(CreateModelProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private boolean formCheck(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        boolean result=true;
        PrintWriter out = response.getWriter();
        
        String[] deleteFlight=request.getParameterValues("flightNumber");
        if (deleteFlight == null)
        {
            out.println("No Flight to delete");
            result=false;
        }
        else
            response.sendRedirect("manageFlight");
        out.close();
        return (result);
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
