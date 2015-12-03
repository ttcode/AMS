/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import system.core.AppCore;
import system.entity.News;
import system.entity.SystemAccount;

/**
 *
 * @author Jean
 */
@WebServlet(name = "Index", urlPatterns = {"/home"})
public class Index extends HttpServlet
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
        
        SystemAccount myAccount = (SystemAccount) request.getSession().getAttribute("_systemAccount");
        int jobId = myAccount.getEmployee().getJobId().getId();
        
        Date now = new Date();
        DateFormat fullDateFormatEN = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, new Locale("EN","en"));
        SimpleDateFormat day = new SimpleDateFormat("dd");

        List<News> news = null;
        List<News> motd = null;
        try
        {
            news = em.createNamedQuery("News.findByMotd").setParameter("motd", false).getResultList();
            motd = em.createNamedQuery("News.findByMotd").setParameter("motd", true).getResultList();
        }
        catch (Exception ex) {}

        if (news != null)
            request.setAttribute("news", news);
    
        if (motd != null)
            request.setAttribute("motd", motd.get(0));
        request.setAttribute("date", fullDateFormatEN.format(now));
        request.setAttribute("jobId", jobId);
        request.setAttribute("headtitle", "Dashboard");
        request.getRequestDispatcher("jsp/index.jsp").forward(request, response);
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
