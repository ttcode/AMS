/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flight.controller;

import de.grobmeier.jjson.JSONObject;
import de.grobmeier.jjson.JSONString;
import java.io.IOException;
import java.io.PrintWriter;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;
import system.entity.Airport;
import system.entity.FlightType;
import system.entity.Flightplan;

/**
 *
 * @author chatri.pi
 */
@WebServlet(name = "UpdateFlightFormCheck", urlPatterns = {"/updateFlightFormCheck"})
public class UpdateFlightFormCheck extends HttpServlet
{
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
//        if (!AppCore.initPage(request, response))
//            return ;

        String flight_number = request.getParameter("flight_number");
        String flight_type = request.getParameter("flight_type");
        
        System.out.println("flight_type : "+flight_type);
        
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        String dhour = request.getParameter("dhour");
        String dminute = request.getParameter("dminute");
        String hour = request.getParameter("hour");
        String minute = request.getParameter("minute");
        String flight_route = request.getParameter("flight_route");
        String repeat_rule = request.getParameter("repeat_rule");
        
        status = true;
        errorMessage = "";
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("AMSPU");
        EntityManager em = emf.createEntityManager();
        
        FlightType flightTypeEntity;
        Airport departureAirport;
        Airport arrivalAirport;
        

        checkFlightNumber(flight_number, em);
        if (status) checkFlightType(flight_type);
        if (status) checkFrom(from,em);  
        if (status) checkTo(to,em);
        if (status) checkDepartureHour(dhour);
        if (status) checkDepartureMinute(dminute);
        if (status) checkDurationHour(hour);
        if (status) checkDurationMinute(minute);
        if (status)
        {

                try
                {   
                    UserTransaction transaction;
                    Flightplan flightplan;
                    transaction = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
                    transaction.begin();
                    
                    flightTypeEntity = (FlightType) em.createNamedQuery("FlightType.findByTypeName").setParameter("typeName", flight_type).getSingleResult();
                    departureAirport = (Airport) em.createNamedQuery("Airport.findByIcao").setParameter("icao", from).getSingleResult();                  
                    arrivalAirport = (Airport) em.createNamedQuery("Airport.findByIcao").setParameter("icao", to).getSingleResult();   

                    flightplan = (Flightplan) em.createNamedQuery("Flightplan.findByFlightNumber").setParameter("flightNumber", Integer.parseInt(flight_number)).getSingleResult();
                    flightplan.setFlightNumber(Integer.parseInt(flight_number));
                    flightplan.setFlightType(flightTypeEntity);
                    flightplan.setDepartureAirport(departureAirport);
                    flightplan.setArrivalAirport(arrivalAirport);
                    flightplan.setDepartureTime(dhour+dminute);
                    flightplan.setFlightDuration(hour+minute);
                    flightplan.setFlightRoute(flight_route);
                    flightplan.setRepeatRule(repeat_rule);

                    transaction.commit();
                }   
                catch (Exception ex)
                {
                    errorMessage = "Problem encountered : please retry later : "+ex.getMessage();
                    status = false;
                }
        }
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        jsonWriting(out, status);
    }
    
    private void checkFlightNumber(String flightNumber, EntityManager em)
    {
        if (flightNumber == null || flightNumber.isEmpty())
        {
            errorMessage = "Flight number field must be filled";
            status &= false;
        }     
        else if (isNumeric(flightNumber) == false)
        {
            errorMessage = "Flight number must be numeric";
            status &= false;
        }
        else if (flightNumber.length() > 4)
        {
            errorMessage = "The maximun flight number are 4 digits";
            status &= false;
        }
        Flightplan flight = null;
        try
        {        
             flight = (Flightplan) em.createNamedQuery("Flightplan.findByFlightNumbers").setParameter("flightNumber", flightNumber).getSingleResult();
        }
        catch (Exception ex) {}
        if (flight != null)
        {
            errorMessage = "Flight number already exists, please specify another flight number";
            status &= false;
        }
        status &= true;
    }
    private void checkFlightType(String flightType){
        System.out.println("Type: "+flightType);
        if (flightType == null || flightType.isEmpty())
        {
            errorMessage = "Please select flight type";
            status &= false;
        }
        status &= true;
    }
    private void checkName(String name)
    {
        if (name == null || name.isEmpty())
        {
            errorMessage = "Name field must be filled";
            status &= false;
        }
        else if (name.length() > 254)
        {
            errorMessage = "Name field too long";
            status &= false;
        }
        status &= true;
    }
    private void checkFrom(String from, EntityManager em)
    {
        System.out.println("From: "+from);
        
        if (from == null || from.isEmpty())
        {
            errorMessage = "From field must be filled";
            status &= false;
        }
        else
        {
            status &= true;
        }
    }   
     private void checkTo(String to, EntityManager em)
    {
        System.out.println("To: "+to);
        if (to == null || to.isEmpty())
        {
            errorMessage = "To field must be filled";
            status &= false;
        }
        else
        {
            status &= true;
        }
    }   
    private void checkDepartureHour(String dhour){
        if (dhour == null || dhour.isEmpty())
        {
            errorMessage = "Departure time: hour field must be filled";
            status &= false;
        }     
        else if (isNumeric(dhour) == false)
        {
            errorMessage = "Departure time: hour must be numeric";
            status &= false;
        } 
        else if (dhour.length() != 2)
        {
            errorMessage = "Departure time hour must be 2 digits";
            status &= false;
        }
        else if (Integer.parseInt(dhour) < 0 || Integer.parseInt(dhour) > 23)
        {
            errorMessage = "Departure time valid hour are between 0-23";
            status &= false;
        }else{
            System.out.println("dhour : "+dhour);
        }
    }
    private void checkDepartureMinute(String dminute){
            
        if (dminute == null || dminute.isEmpty())
        {
            errorMessage = "Departure time: minute field must be filled";
            status &= false;
        }     
        else if (isNumeric(dminute) == false)
        {
            errorMessage = "Departure time: minute must be numeric";
            status &= false;
        }
        else if (dminute.length() != 2)
        {
            errorMessage = "Departure time: minute must be 2 digits";
            status &= false;
        }
        else if (Integer.parseInt(dminute) < 0 || Integer.parseInt(dminute) > 59)
        {
            errorMessage = "Departure time: valid minute are 0-59";
            status &= false;
        }else{
            System.out.println("dminute : "+dminute);
        }
    }
    
    private void checkDurationHour(String hour){
        if (hour == null || hour.isEmpty())
        {
            errorMessage = "Duration time: hour field must be filled";
            status &= false;
        }     
        else if (isNumeric(hour) == false)
        {
            errorMessage = "Duration time: hour must be numeric";
            status &= false;
        } 
        else if (hour.length() != 2)
        {
            errorMessage = "Duration time: hour are must be 2 digits";
            status &= false;
        }
        else if (Integer.parseInt(hour) < 0 || Integer.parseInt(hour) > 23)
        {
            errorMessage = "Duration time: valid hour are between 0-23";
            status &= false;
        }else{
            System.out.println("hour : "+hour);
        }
    }
    
     private void checkDurationMinute(String minute){
            
        if (minute == null || minute.isEmpty())
        {
            errorMessage = "Duration time: minute field must be filled";
            status &= false;
        }     
        else if (isNumeric(minute) == false)
        {
            errorMessage = "Duration time: minute must be numeric";
            status &= false;
        }
        else if (minute.length() != 2)
        {
            errorMessage = "Duration time: minute must be 2 digits";
            status &= false;
        }
        else if (Integer.parseInt(minute) < 0 || Integer.parseInt(minute) > 59)
        {
            errorMessage = "Duration time: valid minute are 0-59";
            status &= false;
        }else{
            System.out.println("minute : "+minute);
        }
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
