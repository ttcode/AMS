/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Jean
 */
@Entity
@Table(name = "flightplan")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Flightplan.findAll", query = "SELECT f FROM Flightplan f"),
    @NamedQuery(name = "Flightplan.findByFlightNumber", query = "SELECT f FROM Flightplan f WHERE f.flightNumber = :flightNumber"),
    @NamedQuery(name = "Flightplan.findByDepartureTime", query = "SELECT f FROM Flightplan f WHERE f.departureTime = :departureTime"),
    @NamedQuery(name = "Flightplan.findByFlightDuration", query = "SELECT f FROM Flightplan f WHERE f.flightDuration = :flightDuration")})
public class Flightplan implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "flight_number")
    private Integer flightNumber;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "departure_time")
    private String departureTime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "flight_duration")
    private String flightDuration;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "flight_route")
    private String flightRoute;
    @Lob
    @Size(max = 65535)
    @Column(name = "repeat_rule")
    private String repeatRule;
    @JoinColumn(name = "flight_type", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private FlightType flightType;
    @JoinColumn(name = "arrival_airport", referencedColumnName = "icao")
    @ManyToOne(optional = false)
    private Airport arrivalAirport;
    @JoinColumn(name = "departure_airport", referencedColumnName = "icao")
    @ManyToOne(optional = false)
    private Airport departureAirport;
    @OneToMany(mappedBy = "assignedFlight")
    private List<Aircraft> aircraftList;

    public Flightplan() {
    }

    public Flightplan(Integer flightNumber) {
        this.flightNumber = flightNumber;
    }

    public Flightplan(Integer flightNumber, String departureTime, String flightDuration, String flightRoute) {
        this.flightNumber = flightNumber;
        this.departureTime = departureTime;
        this.flightDuration = flightDuration;
        this.flightRoute = flightRoute;
    }

    public Integer getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(Integer flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getFlightDuration() {
        return flightDuration;
    }

    public void setFlightDuration(String flightDuration) {
        this.flightDuration = flightDuration;
    }

    public String getFlightRoute() {
        return flightRoute;
    }

    public void setFlightRoute(String flightRoute) {
        this.flightRoute = flightRoute;
    }

    public String getRepeatRule() {
        return repeatRule;
    }

    public void setRepeatRule(String repeatRule) {
        this.repeatRule = repeatRule;
    }

    public FlightType getFlightType() {
        return flightType;
    }

    public void setFlightType(FlightType flightType) {
        this.flightType = flightType;
    }

    public Airport getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(Airport arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public Airport getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(Airport departureAirport) {
        this.departureAirport = departureAirport;
    }

    @XmlTransient
    public List<Aircraft> getAircraftList() {
        return aircraftList;
    }

    public void setAircraftList(List<Aircraft> aircraftList) {
        this.aircraftList = aircraftList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (flightNumber != null ? flightNumber.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Flightplan)) {
            return false;
        }
        Flightplan other = (Flightplan) object;
        if ((this.flightNumber == null && other.flightNumber != null) || (this.flightNumber != null && !this.flightNumber.equals(other.flightNumber))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "system.entity.Flightplan[ flightNumber=" + flightNumber + " ]";
    }
    
}
