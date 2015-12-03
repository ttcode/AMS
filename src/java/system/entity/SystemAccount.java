/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jean
 */
@Entity
@Table(name = "system_account")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SystemAccount.findAll", query = "SELECT s FROM SystemAccount s"),
    @NamedQuery(name = "SystemAccount.findById", query = "SELECT s FROM SystemAccount s WHERE s.id = :id"),
    @NamedQuery(name = "SystemAccount.findByLogin", query = "SELECT s FROM SystemAccount s WHERE s.login = :login"),
    @NamedQuery(name = "SystemAccount.findBySalt", query = "SELECT s FROM SystemAccount s WHERE s.salt = :salt"),
    @NamedQuery(name = "SystemAccount.findByPassword", query = "SELECT s FROM SystemAccount s WHERE s.password = :password"),
    @NamedQuery(name = "SystemAccount.findByLastLogin", query = "SELECT s FROM SystemAccount s WHERE s.lastLogin = :lastLogin")})
public class SystemAccount implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "login")
    private String login;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "salt")
    private String salt;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "password")
    private String password;
    @Column(name = "last_login")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLogin;
    @JoinColumn(name = "account_type", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private AccountType accountType;
    @JoinColumn(name = "role", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Roles role;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "systemAccount")
    private Employee employee;

    public SystemAccount() {
    }

    public SystemAccount(Integer id) {
        this.id = id;
    }

    public SystemAccount(Integer id, String login, String salt, String password) {
        this.id = id;
        this.login = login;
        this.salt = salt;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
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
        if (!(object instanceof SystemAccount)) {
            return false;
        }
        SystemAccount other = (SystemAccount) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "system.entity.SystemAccount[ id=" + id + " ]";
    }
    
}
