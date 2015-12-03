/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flight.controller;

import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import system.core.AppCore;
import system.entity.Airport;
import system.entity.FlightType;
import system.entity.Flightplan;

/**
 *
 * @author chatri.pi
 */
@WebServlet(name = "UpdateFlight", urlPatterns = {"/updateFlight"})
public class UpdateFlight extends HttpServlet
{
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
        String flightNumber = request.getParameter("flightNumber");
        String departure ="";
        String duration ="";
        String dhour ="";
        String dminute="";
        String hour ="";
        String minute="";
        String type = "";
        if (flightNumber == null || flightNumber.isEmpty())
            return ;
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("AMSPU");
        EntityManager em = emf.createEntityManager();
        
        List<FlightType> flightTypes = null;
        List<Airport> fromAirports = null;
        List<Airport> toAirports = null;
        List<Flightplan> flights = null;
        Flightplan flightplan = null;
        try
        {   
            flightplan = (Flightplan) em.createNamedQuery("Flightplan.findByFlightNumber").setParameter("flightNumber", Integer.parseInt(flightNumber)).getSingleResult();
            flights = em.createNamedQuery("Flightplan.findAll").getResultList();
            fromAirports = em.createNamedQuery("Airport.findAll").getResultList();
            toAirports = em.createNamedQuery("Airport.findAll").getResultList();
            flightTypes = em.createNamedQuery("FlightType.findAll").getResultList();  
            type = flightplan.getFlightType().getId().toString();
            departure = flightplan.getDepartureTime();
            duration = flightplan.getFlightDuration();
            dhour = departure.substring(0, 2);
            dminute = departure.substring(2);
            hour = duration.substring(0, 2);
            minute = duration.substring(2);
        }
        catch (Exception ex) {
            
        }
        if (flightNumber == null)
        {System.out.println("null at servlet flightNumber" + flightNumber);
            return ;
        }else if (flights == null)
        {System.out.println("null at servlet flights" );
            return ;
        }else if (fromAirports == null)
        {System.out.println("null at servlet fromAirports");
            return ;
        }else if (toAirports == null)
        {System.out.println("null at servlet toAirports");
            return ;
        }else if (flightTypes == null)
        {System.out.println("null at servlet flightTypes");
            return ;
        }
        
        request.setAttribute("flightplan", flightplan);
        request.setAttribute("flightTypes", flightTypes);
        request.setAttribute("type", type);
        request.setAttribute("flights", flights);
        request.setAttribute("fromAirports", fromAirports);
        request.setAttribute("toAirports", toAirports);
        request.setAttribute("dhour",dhour);
        request.setAttribute("dminute",dminute);
        request.setAttribute("hour",hour);
        request.setAttribute("minute",minute);
        
        request.setAttribute("headtitle", "Update Flight");
        request.getRequestDispatcher("jsp/flight/updateFlight.jsp").forward(request, response);
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
