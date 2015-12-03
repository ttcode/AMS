/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pilot.controller;

import de.grobmeier.jjson.JSONObject;
import de.grobmeier.jjson.JSONString;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
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
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import javax.transaction.UserTransaction;
import system.core.AppCore;
import system.entity.Aircraft;
import system.entity.Airport;
import system.entity.Employee;
import system.entity.Flightplan;
import system.entity.SystemAccount;
import system.entity.Team;

/**
 *
 * @author Jean
 */
@WebServlet(name = "SendPirepProcess", urlPatterns = {"/sendPirepProcess"})
public class SendPirepProcess extends HttpServlet
{
    @Resource(name = "jdbc/ams")
    private DataSource _dataSource;
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
        if (formCheck(request, response) == false)
                return ;
        
        HttpSession session = request.getSession();
        SystemAccount systemAccount = (SystemAccount)session.getAttribute("_systemAccount");
        
        try
        {
            String                  query;
            Connection connection = this._dataSource.getConnection("c2_airline", "SFAXQjMQwNa29n8K");

            query = "SELECT * FROM employee WHERE id = ?";
            PreparedStatement employee_query = connection.prepareStatement(query);
            employee_query.setInt(1, systemAccount.getId());
            ResultSet employee = employee_query.executeQuery();
            employee.next();
            
            query = "SELECT * FROM team WHERE id = ?";
            PreparedStatement team_query = connection.prepareStatement(query);
            team_query.setInt(1, employee.getInt("team_id"));
            ResultSet team = team_query.executeQuery();
            team.next();
            
            query = "SELECT * FROM aircraft WHERE id = ?";
            PreparedStatement aircraft_query = connection.prepareStatement(query);
            aircraft_query.setInt(1, team.getInt("aircraft_id"));
            ResultSet aircraft = aircraft_query.executeQuery();
            aircraft.next();
            
            query = "SELECT * FROM flightplan WHERE flight_number = ?";
            PreparedStatement flightplan_query = connection.prepareStatement(query);
            flightplan_query.setInt(1, aircraft.getInt("assigned_flight"));
            ResultSet flightplan = flightplan_query.executeQuery();
            flightplan.next();
            
            query = "UPDATE aircraft SET position = ?, flight_hour = ?, assigned_flight = ? WHERE id = ?";
            PreparedStatement aircraft_update = connection.prepareStatement(query);
            aircraft_update.setString(1, flightplan.getString("arrival_airport"));
            aircraft_update.setFloat(2, aircraft.getFloat("flight_hour") + (Float.parseFloat(request.getParameter("flight_hour")) / 100));
            if (flightplan.getString("repeat_rule").equals("NULL") == false)
                aircraft_update.setInt(3, Integer.parseInt(flightplan.getString("repeat_rule")));
            else
                aircraft_update.setString(3, "NULL");
            aircraft_update.setInt(4, aircraft.getInt("id"));
            aircraft_update.executeUpdate();
            
            query = "SELECT id FROM employee WHERE team_id = ?";
            PreparedStatement employeelist_query = connection.prepareStatement(query);
            employeelist_query.setInt(1, team.getInt("id"));
            ResultSet employeelist = employeelist_query.executeQuery();
            query = "UPDATE employee SET position = ? WHERE id = ?";
            PreparedStatement employee_update = connection.prepareStatement(query);
            while (employeelist.next())
            {
                employee_update.setString(1, flightplan.getString("arrival_airport"));
                employee_update.setInt(2, employeelist.getInt("id"));
                employee_update.executeUpdate();
            }
            connection.close();
            
            /*transaction = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
            transaction.begin();
            query = em.createNamedQuery("Employee.findById");
            query.setParameter("id", systemAccount.getEmployee().getId());
            Employee employee = (Employee)query.getSingleResult();
            em.refresh(employee);
            Team team = employee.getTeamId();
            em.refresh(team);
            Aircraft aircraft = team.getAircraftId();
            em.refresh(aircraft);
            Flightplan flightplan = aircraft.getAssignedFlight();
            em.refresh(flightplan);
            
            query = em.createNamedQuery("Airport.findByIcao");
            query.setParameter("icao", flightplan.getArrivalAirport().getIcao());
            Airport arrival_airport = (Airport)query.getSingleResult();*/
            
            /*employee.setPosition(arrival_airport);
            aircraft.setPosition(arrival_airport);*/
            //aircraft.setFlightHour(aircraft.getFlightHour() + (Integer.parseInt(request.getParameter("flight_hour")) / 100));
            
            /*if (flightplan.getRepeatRule().equals("0") == false)
            {
                query = em.createNamedQuery("Flightplan.findByFlightNumber");
                System.out.println("REPEAT : " + flightplan.getRepeatRule());
                query.setParameter("flightNumber", Integer.parseInt(flightplan.getRepeatRule()));
                Flightplan nextflight = (Flightplan)query.getSingleResult();
                System.out.println("NEXT : " + nextflight.getFlightNumber());
                aircraft.setAssignedFlight(nextflight);
            }*/
            
           /* Iterator<Employee> it = team.getEmployeeList().iterator();
            while (it.hasNext())
            {
                Employee emp = it.next();
                emp.setPosition(flightplan.getArrivalAirport());
            }*/
            //transaction.commit();
        }
        catch (Exception ex)
        {
            Logger.getLogger(SendPirepProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private boolean formCheck(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String error = "";
        JSONObject json = new JSONObject();
        PrintWriter out = response.getWriter();
        
        if (request.getParameter("dep_gate").isEmpty())
            error += "- Departure Gate must be filled.<br />";
        if (request.getParameter("dep_time").length() != 4)
            error += "- Departure Time must be formatted as Zulu Time HHMM.<br />";
        if (isNumeric(request.getParameter("dep_time")) == false)
            error += "- Departure Time must be numeric.<br />";
        if (request.getParameter("arr_gate").isEmpty())
            error += "- Arrival Gate must be filled.<br />";
        if (request.getParameter("arr_time").length() != 4)
            error += "- Arrival Time must be formatted as Zulu Time HHMM.<br />";
        if (request.getParameter("flight_hour").length() != 4)
            error += "- Total Flight Hour must be formatted as HHMM.<br />";
        if (request.getParameter("flight_hour").length() != 4)
            error += "- Total Flight Hour must be numeric.<br />";
        if (isNumeric(request.getParameter("fuel")) == false)
            error += "- Fuel must be numeric.<br />";
        if (error.isEmpty())
            json.put("result", new JSONString("1"));
        else
        {
            json.put("result", new JSONString("0"));
            json.put("error", new JSONString(error));
        }
        try
        {
            out.print(json.toJSON());
        }
        finally
        {
            out.close();
        }
        if (error.isEmpty())
            return (true);
        return (false);
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
