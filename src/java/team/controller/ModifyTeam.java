/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team.controller;

import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import system.core.AppCore;
import system.entity.Aircraft;
import system.entity.Team;

/**
 *
 * @author Loyd
 */
@WebServlet(name = "ModifyTeam", urlPatterns = {"/modifyTeam"})
public class ModifyTeam extends HttpServlet
{
    @PersistenceUnit(unitName="AMSPU")
    private EntityManagerFactory emf;
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
       
        EntityManager em = emf.createEntityManager();
       
        String teamId = request.getParameter("team_id");

        Team team = null;
        List<Aircraft> aircraft = null;

        try
        {
            team = (Team) em.createNamedQuery("Team.findById").setParameter("id", Integer.parseInt(teamId)).getSingleResult();
            aircraft = em.createNamedQuery("Aircraft.findAll").getResultList();
            List<Team> teams = em.createNamedQuery("Team.findAll").getResultList();
            for (Team tmp : teams)
            {
                if (tmp != team)
                {
                    Aircraft item = tmp.getAircraftId();
                    if (item != null)
                    {
                        if (aircraft.contains(item))
                        {
                            aircraft.remove(item);
                        }
                    }
                }
            }
        }
        catch (Exception ex) {}
        if (aircraft == null)
            return ;

        request.setAttribute("aircrafts", aircraft);
        request.setAttribute("team", team);

        request.setAttribute("headtitle", "Edit Team");
        request.getRequestDispatcher("jsp/team/modifyTeam.jsp").forward(request, response);
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
