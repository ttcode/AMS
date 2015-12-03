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
import system.entity.AircraftModel;

/**
 *
 * @author Administrator
 */
@WebServlet(name = "DeleteModelProcess", urlPatterns = {"/deleteModelProcess"})
public class DeleteModelProcess extends HttpServlet 
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
        try
        {
            EntityManager em = emf.createEntityManager();
            Query           query;
            AircraftModel   aircraftModel;
            UserTransaction transaction;
            
            if (formCheck(request, response) == false)
                return ;
            String checked[] = request.getParameterValues("ModelManagement");
            
            for (int i = 0; i < checked.length; ++i)
            {
                query = em.createNamedQuery("AircraftModel.findById");
                query.setParameter("id", Integer.parseInt(checked[i]));
                aircraftModel = (AircraftModel)query.getSingleResult();  
                em.remove(aircraftModel);
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
        String error = "";
        EntityManager em = emf.createEntityManager();
        Query query;
        AircraftModel aircraftModel;
        JSONObject json = new JSONObject();
        PrintWriter out = response.getWriter();
        
        String[] deleteModel=request.getParameterValues("ModelManagement");
        if (deleteModel == null)
        {
            error = "No model to delete";
            result=false;
        }
        else
        {
            query = em.createNamedQuery("AircraftModel.findById");
            for (int i = 0; i < deleteModel.length; ++i)
            {
                query.setParameter("id", Integer.parseInt(deleteModel[i]));
                aircraftModel = (AircraftModel)query.getSingleResult();
                em.refresh(aircraftModel);
                if (aircraftModel.getAircraftList().isEmpty() == false)
                {
                    result = false;
                    error += "- Cannot delete \'" + aircraftModel.getAircraftModel() + "\' : Model in use.<br />";
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
