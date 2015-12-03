/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aircraft.controller;

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
import system.entity.AircraftModel;

@WebServlet(name = "MaintenanceProcess", urlPatterns = {"/MaintenanceProcess"})
public class MaintenanceProcess extends HttpServlet {
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
           if(formCheck(request,response)==false)
                return;
           EntityManager em=emf.createEntityManager();
           UserTransaction transaction;
           transaction = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
           transaction.begin();
           Query query = em.createNamedQuery("Aircraft.findById");
           query.setParameter("id", Integer.parseInt(request.getParameter("aircraft_registration")));
           Aircraft aircraft = (Aircraft)query.getSingleResult();
 
           Character status=request.getParameter("aircraft_status").charAt(0);
           aircraft.setAircraftStatus(status);
           aircraft.setMaintenanceComment(request.getParameter("maintenance_comment"));

           transaction.commit();

        }
        catch (Exception ex)
        {
            Logger.getLogger(MaintenanceProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
        private boolean formCheck(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        PrintWriter out = response.getWriter();
        JSONObject json = new JSONObject();
        boolean result = true;
        String error = "";
        String aircraft_registration = request.getParameter("aircraft_registration");
        String aircraft_status = request.getParameter("aircraft_status");
        String comment = request.getParameter("maintenance_comment");
        
        if (aircraft_registration.length() <= 0)
        {
            error += "- Aircraft no selected<br />";
            result = false;
        }
        if (aircraft_status.length() <= 0)
        {
            error += "- Aircraft Status no selected<br />";
            result = false;
        }
        if (comment.length() <= 0)
        {
            error += "- Maintenance comments must be filled<br />";
            result = false;
        }
        if (result == false)
        {
            json.put("result", new JSONString("0"));
            json.put("error", new JSONString(error));
        }
        else
            json.put("result", new JSONString("1"));
        try
        {
            out.print(json.toJSON());
        }
        finally
        {
            out.close();
        }
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