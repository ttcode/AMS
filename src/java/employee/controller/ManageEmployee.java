/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package employee.controller;

import java.io.IOException;
import java.util.Iterator;
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
import system.entity.Employee;

/**
 *
 * @author Loyd
 */
@WebServlet(name = "ManageEmployee", urlPatterns = {"/manageEmployee"})
public class ManageEmployee extends HttpServlet
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
        
        //EntityManagerFactory emf = Persistence.createEntityManagerFactory("AMSPU");
        EntityManager em = emf.createEntityManager();
        List<Employee> employees = null;
        try
        {
            employees = em.createNamedQuery("Employee.findAllOrderedByRegistrationDateDesc").getResultList();
            Iterator<Employee> it = employees.iterator();
            while (it.hasNext())
            {
                Employee emp = it.next();
                em.refresh(emp);
            }
        }
        catch (Exception ex) {}
        if (employees == null)
            return ;
        
        request.setAttribute("employees", employees);
        request.setAttribute("headtitle", "Employee Management");
        request.getRequestDispatcher("jsp/employee/manageEmployee.jsp").forward(request, response);
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
