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
import system.core.AppCore;
import system.entity.Aircraft;
import system.entity.AircraftModel;
import system.entity.Airport;
import system.entity.Flightplan;

@WebServlet(name = "CreateAircraftProcess", urlPatterns = {"/CreateAircraftProcess"})
public class CreateAircraftProcess extends HttpServlet 
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
            if(formCheck(request,response)==false)
                return;
           EntityManager em=emf.createEntityManager();
           Query query = em.createNamedQuery("AircraftModel.findById");
           query.setParameter("id", Integer.parseInt(request.getParameter("model_id")));
           AircraftModel aircraftModel = (AircraftModel)query.getSingleResult();
           Flightplan   flightplan = null;
           
           /*if (!request.getParameter("assignedFlight").equals(""))
           {
                query=em.createNamedQuery("Flightplan.findByFlightNumber");
                query.setParameter("flight_number", request.getParameter("assignedFlight"));
                flightplan=(Flightplan)query.getSingleResult();
           }*/
           
           query=em.createNamedQuery("Airport.findByIcao");
           query.setParameter("icao", request.getParameter("position"));
           Airport airport=(Airport)query.getSingleResult();
           
           Aircraft aircraft = new Aircraft();
           Character status=request.getParameter("aircraft_status").charAt(0);

           aircraft.setModelId(aircraftModel);
           aircraft.setAircraftRegistration(request.getParameter("aircraft_registration"));
           aircraft.setFlightHour(Float.parseFloat(request.getParameter("flight_hour")));
           /*if (request.getParameter("flight_number").equals(""))
                aircraft.setFlightNumber(null);
           else
               aircraft.setFlightNumber(Integer.parseInt(request.getParameter("flight_number")));*/
           aircraft.setAircraftStatus(status);
           //aircraft.setAssignedFlight(flightplan);
           aircraft.setPosition(airport);
           aircraft.setMaintenanceComment(" ");
           

           UserTransaction transaction;

           transaction = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
           transaction.begin();
           em.persist(aircraft);
           em.flush();
           transaction.commit();

        }
        catch (Exception ex)
        {
            Logger.getLogger(CreateAircraftProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
        private boolean formCheck(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        PrintWriter out = response.getWriter();
        boolean result = true;
        JSONObject json = new JSONObject();
        String error = "";
        String model_name = request.getParameter("model_id");
        String aircraft_registration = request.getParameter("aircraft_registration");
        String flight_hour = request.getParameter("flight_hour");
        String aircraft_status = request.getParameter("aircraft_status");
        String aircraft_position = request.getParameter("position");
        
        if (model_name.length() <= 0)
        {
            error += "- Aircraft Model must be filled.<br />";
            result = false;
        }
        if (flight_hour.length() <= 0)
        {
            error += "- Flight hour field must be filled.<br />";
            result = false;
        }
        if (isNumeric(flight_hour) == false)
        {
            error += "- Flight hour field must be numeric.<br />";
            result = false;
        }
        if (aircraft_registration.length() <= 0)
        {
            error += "- Aircraft Registration field must be filled.<br />";
            result = false;
        }
        EntityManager em=emf.createEntityManager();
        Query query=em.createNamedQuery("Aircraft.findByAircraftRegistration");
        query.setParameter("aircraftRegistration", aircraft_registration);
        List<Aircraft> aircraft = (List<Aircraft>)query.getResultList();
        if (aircraft.isEmpty() == false)
        {
            error += "- Aircraft registration already used.<br />";
            result = false;
        }
        if (request.getParameter("position").isEmpty())
        {
            error += "- Aircraft position field must be filled.<br />";
            result = false;
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
