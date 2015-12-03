/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.controller;

import de.grobmeier.jjson.JSONObject;
import de.grobmeier.jjson.JSONString;
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
import system.core.Encoder;
import system.entity.SystemAccount;



/**
 *
 * @author Jean
 */
@WebServlet(name = "LoginCheck", urlPatterns = {"/loginCheck"})
public class                            LoginCheck extends HttpServlet
{
    @PersistenceUnit(unitName="AMSPU")
    EntityManagerFactory                emf;
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
    protected void                      processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String                          login = request.getParameter("login");
        String                          password = request.getParameter("password");
        boolean                         status = true;
        SystemAccount                   systemAccount = null;
        
        if (login == null || login.isEmpty() || password == null || password.isEmpty())
            status = false;
        EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery("SystemAccount.findByLogin");
        query.setParameter("login", login);
        try
        {
            systemAccount = (SystemAccount)query.getSingleResult();
        }
        catch (Exception ex) { }
        if (systemAccount != null)
        {
            String encodedPass = Encoder.encode("SHA-512", login.toLowerCase() + systemAccount.getSalt() + password);
            if (systemAccount.getPassword().equals(encodedPass))
            {
                HttpSession session = request.getSession(true);
                session.setAttribute("_systemAccount", systemAccount);
            }
            else
                status = false;
        }
        else
            status = false;
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JSONObject json = new JSONObject();
        if (status == false)
        {
            json.put("result", new JSONString("0"));
            json.put("error", new JSONString("Incorrect user/password"));
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
