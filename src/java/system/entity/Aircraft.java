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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "aircraft")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Aircraft.findAll", query = "SELECT a FROM Aircraft a"),
    @NamedQuery(name = "Aircraft.findById", query = "SELECT a FROM Aircraft a WHERE a.id = :id"),
    @NamedQuery(name = "Aircraft.findByAircraftRegistration", query = "SELECT a FROM Aircraft a WHERE a.aircraftRegistration = :aircraftRegistration"),
    @NamedQuery(name = "Aircraft.findByFlightHour", query = "SELECT a FROM Aircraft a WHERE a.flightHour = :flightHour"),
    @NamedQuery(name = "Aircraft.findByFlightNumber", query = "SELECT a FROM Aircraft a WHERE a.flightNumber = :flightNumber"),
    @NamedQuery(name = "Aircraft.findByAircraftStatus", query = "SELECT a FROM Aircraft a WHERE a.aircraftStatus = :aircraftStatus")})
public class Aircraft implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "maintenance_comment")
    private String maintenanceComment;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "aircraft_registration")
    private String aircraftRegistration;
    @Basic(optional = false)
    @NotNull
    @Column(name = "flight_hour")
    private float flightHour;
    @Column(name = "flight_number")
    private Integer flightNumber;
    @Basic(optional = false)
    @NotNull
    @Column(name = "aircraft_status")
    private char aircraftStatus;
    @OneToMany(mappedBy = "aircraftId")
    private List<Team> teamList;
    @JoinColumn(name = "model_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private AircraftModel modelId;
    @JoinColumn(name = "position", referencedColumnName = "icao")
    @ManyToOne(optional = false)
    private Airport position;
    @JoinColumn(name = "assigned_flight", referencedColumnName = "flight_number")
    @ManyToOne
    private Flightplan assignedFlight;

    public Aircraft() {
    }

    public Aircraft(Integer id) {
        this.id = id;
    }

    public Aircraft(Integer id, String aircraftRegistration, float flightHour, char aircraftStatus) {
        this.id = id;
        this.aircraftRegistration = aircraftRegistration;
        this.flightHour = flightHour;
        this.aircraftStatus = aircraftStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAircraftRegistration() {
        return aircraftRegistration;
    }

    public void setAircraftRegistration(String aircraftRegistration) {
        this.aircraftRegistration = aircraftRegistration;
    }

    public float getFlightHour() {
        return flightHour;
    }

    public void setFlightHour(float flightHour) {
        this.flightHour = flightHour;
    }

    public Integer getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(Integer flightNumber) {
        this.flightNumber = flightNumber;
    }

    public char getAircraftStatus() {
        return aircraftStatus;
    }

    public void setAircraftStatus(char aircraftStatus) {
        this.aircraftStatus = aircraftStatus;
    }

    @XmlTransient
    public List<Team> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<Team> teamList) {
        this.teamList = teamList;
    }

    public AircraftModel getModelId() {
        return modelId;
    }

    public void setModelId(AircraftModel modelId) {
        this.modelId = modelId;
    }

    public Airport getPosition() {
        return position;
    }

    public void setPosition(Airport position) {
        this.position = position;
    }

    public Flightplan getAssignedFlight() {
        return assignedFlight;
    }

    public void setAssignedFlight(Flightplan assignedFlight) {
        this.assignedFlight = assignedFlight;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Aircraft)) {
            return false;
        }
        Aircraft other = (Aircraft) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "system.entity.Aircraft[ id=" + id + " ]";
    }

    public String getMaintenanceComment() {
        return maintenanceComment;
    }

    public void setMaintenanceComment(String maintenanceComment) {
        this.maintenanceComment = maintenanceComment;
    }
    
}
