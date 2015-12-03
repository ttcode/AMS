/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team.controller;

import de.grobmeier.jjson.JSONObject;
import de.grobmeier.jjson.JSONString;
import java.io.IOException;
import java.io.PrintWriter;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;
import system.core.AppCore;
import system.entity.Employee;
import system.entity.Team;

/**
 *
 * @author Loyd
 */
@WebServlet(name = "AddTeamMemberFormCheck", urlPatterns = {"/addTeamMemberFormCheck"})
public class AddTeamMemberFormCheck extends HttpServlet
{
    @PersistenceUnit(unitName="AMSPU")
    private EntityManagerFactory emf;
    private boolean status = true;
    private String errorMessage = "";
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
        
        String employeeId = request.getParameter("employee_id");
        String teamId = request.getParameter("team_id");
        
        status = true;
        errorMessage = "";
        
        EntityManager em = emf.createEntityManager();

        UserTransaction transaction;
        try
        {
            Employee employee = (Employee) em.createNamedQuery("Employee.findById").setParameter("id", Integer.parseInt(employeeId)).getSingleResult();
            Team team = (Team) em.createNamedQuery("Team.findById").setParameter("id", Integer.parseInt(teamId)).getSingleResult();

            transaction = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
            transaction.begin();
            
            employee.setTeamId(team);
            
            em.flush();
            transaction.commit();
        }
        catch (Exception ex)
        {
            errorMessage = "Problem encountered : please retry later";
            status = false;
        }
        
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        jsonWriting(out, status);
    }
    
    private void jsonWriting(PrintWriter out, boolean status)
    {
        JSONObject json = new JSONObject();
        if (status == false)
        {
            json.put("result", new JSONString("0"));
            json.put("error", new JSONString(errorMessage));
        }
        else
        {
            json.put("result", new JSONString("1"));
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
