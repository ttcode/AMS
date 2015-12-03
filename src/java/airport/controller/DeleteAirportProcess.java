/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package airport.controller;

import de.grobmeier.jjson.JSONObject;
import de.grobmeier.jjson.JSONString;
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
import system.entity.Airport;

/**
 *
 * @author Jean
 */
@WebServlet(name = "DeleteAirportProcess", urlPatterns = {"/deleteAirportProcess"})
public class DeleteAirportProcess extends HttpServlet
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
        if (request.getParameter("icao") == null)
            return ;
        EntityManager em = emf.createEntityManager();
        Query query;
        Airport airport;
        UserTransaction transaction;
        JSONObject json = new JSONObject();
        PrintWriter out = response.getWriter();
        String error = "";
        
        try
        {
            System.out.println("ICAO : " + request.getParameter("icao"));
            query = em.createNamedQuery("Airport.findByIcao");
            query.setParameter("icao", request.getParameter("icao"));
            airport = (Airport)query.getSingleResult();
            em.refresh(airport);
            if (airport.getAircraftList().isEmpty() == false
                    || airport.getEmployeeList().isEmpty() == false
                    || airport.getEmployeeList1().isEmpty() == false
                    || airport.getFlightplanList().isEmpty() == false
                    || airport.getFlightplanList1().isEmpty() == false)
                error += "Cannot delete '" + airport.getName() + "': Airport already in use";
            else
            {
                em.remove(airport);
                transaction = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
                transaction.begin();
                em.flush();
                transaction.commit();
            }
        }
        catch(Exception ex)
        {
            Logger.getLogger(DeleteAirportProcess.class.getName()).log(Level.SEVERE, null, ex);
            return ;
        }
        if (error.isEmpty())
            json.put("result", new JSONString("1"));
        else
        {
            json.put("result", new JSONString("0"));
            json.put("error", new JSONString(error));
        }
        try
        {
            out.print(json.toJSON());
        }
        finally
        {
            out.close();
        }
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
