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
import system.entity.Aircraft;
import system.entity.Team;

/**
 *
 * @author Loyd
 */
@WebServlet(name = "ModifyTeamFormCheck", urlPatterns = {"/modifyTeamFormCheck"})
public class ModifyTeamFormCheck extends HttpServlet
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
        
        String teamId = request.getParameter("team_id");
        String teamName = request.getParameter("team_name");
        String aircraft = request.getParameter("aircraft");
        
        status = true;
        errorMessage = "";
        
        EntityManager em = emf.createEntityManager();
        
        if (status) checkTeamName(teamName, teamId, em);
        
        if (status)
        {
            UserTransaction transaction;
            try
            {
                Team team = (Team) em.createNamedQuery("Team.findById").setParameter("id", Integer.parseInt(teamId)).getSingleResult();
                team.setTeamName(teamName);

                transaction = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
                transaction.begin();

                if (checkAircraft(aircraft))
                    team.setAircraftId((Aircraft) em.createNamedQuery("Aircraft.findById").setParameter("id", Integer.parseInt(aircraft)).getSingleResult());
                else
                    team.setAircraftId(null);
                
                em.flush();
                transaction.commit();
            }
            catch (Exception ex)
            {
                errorMessage = "Problem encountered : please retry later";
                status = false;
            }
        }
        
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        jsonWriting(out, status);
    }
    
    private boolean checkAircraft(String aircraft)
    {
        System.out.println(aircraft);
        if (aircraft == null || aircraft.isEmpty())
        {
            return false;
        }
        return true;
    }
    
    private void checkTeamName(String teamName, String teamId, EntityManager em)
    {
        if (teamName == null || teamName.isEmpty())
        {
            errorMessage = "Team Name field must be filled";
            status &= false;
        }
        else if (teamName.length() > 254)
        {
            errorMessage = "Team Name field too long";
            status &= false;
        }
        Team tmp = null;
        try
        {
            tmp = (Team) em.createNamedQuery("Team.findByTeamName").setParameter("teamName", teamName).getSingleResult();
        }
        catch (Exception ex) { }
        if (tmp != null && tmp.getId() != Integer.parseInt(teamId))
        {
            errorMessage = "Team Name already exists";
            status &= false;
        }
        status &= true;
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
