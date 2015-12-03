/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "airport")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Airport.findAll", query = "SELECT a FROM Airport a"),
    @NamedQuery(name = "Airport.findByIcao", query = "SELECT a FROM Airport a WHERE a.icao = :icao"),
    @NamedQuery(name = "Airport.findByName", query = "SELECT a FROM Airport a WHERE a.name = :name"),
    @NamedQuery(name = "Airport.findByIsHub", query = "SELECT a FROM Airport a WHERE a.isHub = :isHub")})
public class Airport implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "icao")
    private String icao;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "is_hub")
    private boolean isHub;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "arrivalAirport")
    private List<Flightplan> flightplanList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "departureAirport")
    private List<Flightplan> flightplanList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "position")
    private List<Aircraft> aircraftList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "position")
    private List<Employee> employeeList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hub")
    private List<Employee> employeeList1;

    public Airport() {
    }

    public Airport(String icao) {
        this.icao = icao;
    }

    public Airport(String icao, String name, boolean isHub) {
        this.icao = icao;
        this.name = name;
        this.isHub = isHub;
    }

    public String getIcao() {
        return icao;
    }

    public void setIcao(String icao) {
        this.icao = icao;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getIsHub() {
        return isHub;
    }

    public void setIsHub(boolean isHub) {
        this.isHub = isHub;
    }

    @XmlTransient
    public List<Flightplan> getFlightplanList() {
        return flightplanList;
    }

    public void setFlightplanList(List<Flightplan> flightplanList) {
        this.flightplanList = flightplanList;
    }

    @XmlTransient
    public List<Flightplan> getFlightplanList1() {
        return flightplanList1;
    }

    public void setFlightplanList1(List<Flightplan> flightplanList1) {
        this.flightplanList1 = flightplanList1;
    }

    @XmlTransient
    public List<Aircraft> getAircraftList() {
        return aircraftList;
    }

    public void setAircraftList(List<Aircraft> aircraftList) {
        this.aircraftList = aircraftList;
    }

    @XmlTransient
    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    @XmlTransient
    public List<Employee> getEmployeeList1() {
        return employeeList1;
    }

    public void setEmployeeList1(List<Employee> employeeList1) {
        this.employeeList1 = employeeList1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (icao != null ? icao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Airport)) {
            return false;
        }
        Airport other = (Airport) object;
        if ((this.icao == null && other.icao != null) || (this.icao != null && !this.icao.equals(other.icao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return icao + "";
    }
    
}
