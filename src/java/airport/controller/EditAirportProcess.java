/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package airport.controller;

import de.grobmeier.jjson.JSONObject;
import de.grobmeier.jjson.JSONString;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
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
@WebServlet(name = "EditAirportProcess", urlPatterns = {"/editAirportProcess"})
public class EditAirportProcess extends HttpServlet
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
            query = em.createNamedQuery("Airport.findByIcao");
            query.setParameter("icao", request.getParameter("icao").toUpperCase());
            Airport airport = (Airport)query.getSingleResult();
            airport.setIcao(request.getParameter("icao").toUpperCase());
            airport.setName(request.getParameter("airport_name"));
            String[] hub = request.getParameterValues("hub");
            if (hub == null)
                airport.setIsHub(false);
            else
                airport.setIsHub(true);
            transaction.commit();
        }
        catch(Exception ex)
        {
            Logger.getLogger(EditAirportProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private boolean formCheck(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String error = "";
        JSONObject json = new JSONObject();
        PrintWriter out = response.getWriter();
        String old_icao = request.getParameter("old_icao");
        String icao = request.getParameter("icao");
        String airport_name = request.getParameter("airport_name");
        
        if (icao.length() <= 0)
            error += "- ICAO must be filled<br />";
        if (icao.length() != 4)
            error += "- ICAO field must be 4 characters<br />";
        if (airport_name.length() <= 0)
            error += "- Airport name must be filled<br />";
        
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
