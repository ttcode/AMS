/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pilot.controller;

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
import system.entity.Aircraft;

/**
 *
 * @author Jean
 */
@WebServlet(name = "MaintenanceRequestProcess", urlPatterns = {"/maintenanceRequestProcess"})
public class MaintenanceRequestProcess extends HttpServlet
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
        if (formCheck(request, response) == false)
                return ;
        
        EntityManager em = emf.createEntityManager();
        Query           query;           
        UserTransaction transaction;
        try
        {
            transaction = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
            transaction.begin();
            query = em.createNamedQuery("Aircraft.findById");
            query.setParameter("id", Integer.parseInt(request.getParameter("aircraft_id")));
            Aircraft aircraft = (Aircraft)query.getSingleResult();
            aircraft.setAircraftStatus('2');
            aircraft.setMaintenanceComment(request.getParameter("comment"));
            transaction.commit();
        }
        catch(Exception ex)
        {
            Logger.getLogger(MaintenanceRequestProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private boolean formCheck(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String error = "";
        JSONObject json = new JSONObject();
        PrintWriter out = response.getWriter();

        if (request.getParameter("aircraft_id") == null)
            return (false);
        if (request.getParameter("comment").isEmpty())
            error += "Comment field must be filled.";
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
        if (error.isEmpty())
            return (true);
        return (false);
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
