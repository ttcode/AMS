/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package home.controller;

import de.grobmeier.jjson.JSONObject;
import de.grobmeier.jjson.JSONString;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
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
import system.entity.News;

/**
 *
 * @author Loyd
 */
@WebServlet(name = "AddNewsFormCheck", urlPatterns = {"/addNewsFormCheck"})
public class AddNewsFormCheck extends HttpServlet
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
        
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        
        status = true;
        errorMessage = "";
        
        EntityManager em = emf.createEntityManager();
        
        if (status) checkTitle(title);
        if (status) checkContent(content);
        
        content = content.replace("\n", "<br />");
        
        if (status)
        {
            UserTransaction transaction;
            try
            {
                transaction = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
                transaction.begin();

                News news = new News();
                news.setDate(new Date());
                news.setTitle(title);
                news.setContent(content);
                news.setMotd(false);
                
                em.persist(news);
                em.flush();
                transaction.commit();
            }
            catch (Exception ex) {}
        }
        
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        jsonWriting(out, status);
    }

    private void checkContent(String content)
    {
        if (content == null || content.isEmpty())
        {
            errorMessage = "Content field must be filled";
            status &= false;
        }
        status &= true;
    }    
    
    private void checkTitle(String title)
    {
        if (title == null || title.isEmpty())
        {
            errorMessage = "Title field must be filled";
            status &= false;
        }
        else if (title.length() > 254)
        {
            errorMessage = "Title field too long";
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
