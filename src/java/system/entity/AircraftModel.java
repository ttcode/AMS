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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "aircraft_model")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AircraftModel.findAll", query = "SELECT a FROM AircraftModel a"),
    @NamedQuery(name = "AircraftModel.findById", query = "SELECT a FROM AircraftModel a WHERE a.id = :id"),
    @NamedQuery(name = "AircraftModel.findByAircraftModel", query = "SELECT a FROM AircraftModel a WHERE a.aircraftModel = :aircraftModel"),
    @NamedQuery(name = "AircraftModel.findByRequiredPilot", query = "SELECT a FROM AircraftModel a WHERE a.requiredPilot = :requiredPilot"),
    @NamedQuery(name = "AircraftModel.findByRequiredCrew", query = "SELECT a FROM AircraftModel a WHERE a.requiredCrew = :requiredCrew"),
    @NamedQuery(name = "AircraftModel.findByMaxPassenger", query = "SELECT a FROM AircraftModel a WHERE a.maxPassenger = :maxPassenger"),
    @NamedQuery(name = "AircraftModel.findByMaxCargo", query = "SELECT a FROM AircraftModel a WHERE a.maxCargo = :maxCargo")})
public class AircraftModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "aircraft_model")
    private String aircraftModel;
    @Basic(optional = false)
    @NotNull
    @Column(name = "required_pilot")
    private int requiredPilot;
    @Basic(optional = false)
    @NotNull
    @Column(name = "required_crew")
    private int requiredCrew;
    @Basic(optional = false)
    @NotNull
    @Column(name = "max_passenger")
    private int maxPassenger;
    @Basic(optional = false)
    @NotNull
    @Column(name = "max_cargo")
    private int maxCargo;
    @JoinColumn(name = "aircraft_type", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private AircraftType aircraftType;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "modelId")
    private List<Aircraft> aircraftList;

    public AircraftModel() {
    }

    public AircraftModel(Integer id) {
        this.id = id;
    }

    public AircraftModel(Integer id, String aircraftModel, int requiredPilot, int requiredCrew, int maxPassenger, int maxCargo) {
        this.id = id;
        this.aircraftModel = aircraftModel;
        this.requiredPilot = requiredPilot;
        this.requiredCrew = requiredCrew;
        this.maxPassenger = maxPassenger;
        this.maxCargo = maxCargo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAircraftModel() {
        return aircraftModel;
    }

    public void setAircraftModel(String aircraftModel) {
        this.aircraftModel = aircraftModel;
    }

    public int getRequiredPilot() {
        return requiredPilot;
    }

    public void setRequiredPilot(int requiredPilot) {
        this.requiredPilot = requiredPilot;
    }

    public int getRequiredCrew() {
        return requiredCrew;
    }

    public void setRequiredCrew(int requiredCrew) {
        this.requiredCrew = requiredCrew;
    }

    public int getMaxPassenger() {
        return maxPassenger;
    }

    public void setMaxPassenger(int maxPassenger) {
        this.maxPassenger = maxPassenger;
    }

    public int getMaxCargo() {
        return maxCargo;
    }

    public void setMaxCargo(int maxCargo) {
        this.maxCargo = maxCargo;
    }

    public AircraftType getAircraftType() {
        return aircraftType;
    }

    public void setAircraftType(AircraftType aircraftType) {
        this.aircraftType = aircraftType;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AircraftModel)) {
            return false;
        }
        AircraftModel other = (AircraftModel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "system.entity.AircraftModel[ id=" + id + " ]";
    }
    
}
