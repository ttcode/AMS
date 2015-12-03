/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aircraft.controller;

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
import system.entity.AircraftModel;
import system.entity.AircraftType;

/**
 *
 * @author Administrator
 */
@WebServlet(name = "UpdateModelProcess", urlPatterns = {"/updateModelProcess"})
public class UpdateModelProcess extends HttpServlet 
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
        try
        {
            EntityManager em = emf.createEntityManager();
            Query           query;           
            UserTransaction transaction;
            
            if (formCheck(request, response) == false)
                return ;
            
            transaction = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
            transaction.begin();
            query = em.createNamedQuery("AircraftModel.findById");
            query.setParameter("id",Integer.parseInt(request.getParameter("id")));
            AircraftModel   aircraftModel= (AircraftModel)query.getSingleResult();
            query = em.createNamedQuery("AircraftType.findById");
            query.setParameter("id", Integer.parseInt(request.getParameter("model_type")));
            AircraftType aircraftType = (AircraftType)query.getSingleResult();
            
            aircraftModel.setAircraftType(aircraftType);
            aircraftModel.setAircraftModel(request.getParameter("model_name"));
            aircraftModel.setMaxCargo(Integer.parseInt(request.getParameter("max_cargo")));
            aircraftModel.setMaxPassenger(Integer.parseInt(request.getParameter("max_passenger")));
            aircraftModel.setRequiredCrew(Integer.parseInt(request.getParameter("required_crew")));
            aircraftModel.setRequiredPilot(Integer.parseInt(request.getParameter("required_pilot")));

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
        PrintWriter out = response.getWriter();
        JSONObject json = new JSONObject();
        String error = "";
        
        if (request.getParameter("id") == null)
            return (false);
        
        EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery("AircraftModel.findById");
        query.setParameter("id", Integer.parseInt(request.getParameter("id")));
        AircraftModel initialModel = (AircraftModel)query.getSingleResult();
        
        String model_name = request.getParameter("model_name");
        String model_type = request.getParameter("model_type");
        String required_pilot = request.getParameter("required_pilot");
        String required_crew = request.getParameter("required_crew");
        String max_passenger = request.getParameter("max_passenger");
        String max_cargo = request.getParameter("max_cargo");

        if (model_name.length() <= 0)
        {
            error += "- Empty model name<br />";
            result = false;
        } 
        if (model_type.length() <= 0)
        {
            error += "- Empty model type<br />";
            result = false;
        }
        if (required_pilot.length()<=0)
        {
            error += "- Empty number of required pilot<br />";
            result=false;
        }
        if (isNumeric(required_pilot) == false)
        {
            error += "- Number of required pilot must be numeric<br />";
            result=false;
        }
        if (required_crew.length()<=0)
        {
            error += "- Empty number of required crew<br />";
            result=false;
        }
        if (isNumeric(required_crew) == false)
        {
            error += "- Number of required crew must be numeric<br />";
            result=false;
        }
        if (max_passenger.length()<=0)
        {
            error += "- Empty number of max passenger<br />";
            result=false;
        }
        if (isNumeric(max_passenger) == false)
        {
            error += "- Number of max passenger must be numeric<br />";
            result=false;
        }
        if (max_cargo.length()<=0)
        {
            error += "- Empty number of max cargo<br />";
            result=false;
        }
        if (isNumeric(max_cargo) == false)
        {
            error += "- Number of max cargo must be numeric<br />";
            result=false;
        }
        if (initialModel.getAircraftModel().equals(model_name) == false)
        {
            query = em.createNamedQuery("AircraftModel.findByAircraftModel");
            query.setParameter("aircraftModel", model_name);
            List<AircraftModel> aircraftModel = (List<AircraftModel>)query.getResultList();
            if (aircraftModel.isEmpty() == false)
            {
                error += "- Model Name already exist<br />";
                result = false;
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
    
    private boolean isNumeric(String str)
    {
        float f;
        try
        {
            f = Float.parseFloat(str);
            return (true);
        }
        catch (Exception ex) {}
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
