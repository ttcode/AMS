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

/**
 *
 * @author Administrator
 */
@WebServlet(name = "DeleteAircraftProcess", urlPatterns = {"/DeleteAircraftProcess"})
public class DeleteAircraftProcess extends HttpServlet 
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException 
    {
        if (!AppCore.initPage(request, response))
            return ;
        try
        {
            EntityManager em = emf.createEntityManager();
            Query query;
            Aircraft aircraft;
            UserTransaction transaction;
          
            if (formCheck(request, response) == false)
                return ;
            String checked[] = request.getParameterValues("AircraftManagement");
            
            for (int i = 0; i < checked.length; ++i)
            {
                query = em.createNamedQuery("Aircraft.findById");
                query.setParameter("id", Integer.parseInt(checked[i]));
                aircraft = (Aircraft)query.getSingleResult();  
                em.remove(aircraft);
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
        JSONObject json = new JSONObject();
        String error = "";
        PrintWriter out = response.getWriter();
        
        String[] deleteAircraft=request.getParameterValues("AircraftManagement");
        if (deleteAircraft == null)
        {
            error += "No aircraft to delete";
            result=false;
        }
        else
        {
            EntityManager em = emf.createEntityManager();
            Query query = em.createNamedQuery("Aircraft.findById");
            Aircraft aircraft;
            for (int i = 0; i < deleteAircraft.length; ++i)
            {
                query.setParameter("id", Integer.parseInt(deleteAircraft[i]));
                aircraft = (Aircraft)query.getSingleResult();
                em.refresh(aircraft);
                if (aircraft.getTeamList().isEmpty() == false || aircraft.getAssignedFlight() != null)
                {
                    result = false;
                    error += "- Cannot delete \'" + aircraft.getAircraftRegistration()+ "\' : Aircraft in use.<br />";
                }
            }
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
