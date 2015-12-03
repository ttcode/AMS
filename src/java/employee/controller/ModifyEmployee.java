/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package employee.controller;

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
import system.entity.Airport;
import system.entity.Job;
import system.entity.Roles;
import system.entity.SystemAccount;

/**
 *
 * @author Loyd
 */
@WebServlet(name = "ModifyEmployee", urlPatterns = {"/modifyEmployee"})
public class ModifyEmployee extends HttpServlet
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
        
        String id = request.getParameter("id");
        if (id == null || id.isEmpty())
            return ;
        
        EntityManager em = emf.createEntityManager();
        
        SystemAccount myAccount = (SystemAccount) request.getSession().getAttribute("_systemAccount");
        int jobId = myAccount.getEmployee().getJobId().getId();

        List<Roles> accountTypes = null;
        List<Job> jobs = null;
        List<Airport> airports = null;
        SystemAccount account = null;

        try
        {
            accountTypes = em.createNamedQuery("Roles.findAll").getResultList();
            jobs = em.createNamedQuery("Job.findAll").getResultList();
            airports = em.createNamedQuery("Airport.findAll").getResultList();
            account = (SystemAccount) em.createNamedQuery("SystemAccount.findById").setParameter("id", Integer.parseInt(id)).getSingleResult();
        }
        catch (Exception ex) {}
        if (accountTypes == null || jobs == null || airports == null || account == null)
            return ;

        request.setAttribute("accountTypes", accountTypes);
        request.setAttribute("jobId", jobId);
        request.setAttribute("jobs", jobs);
        request.setAttribute("airports", airports);
        request.setAttribute("account", account);
        
        request.setAttribute("headtitle", "Edit Employee");
        request.getRequestDispatcher("jsp/employee/modifyEmployee.jsp").forward(request, response);
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
