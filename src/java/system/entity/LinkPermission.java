/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jean
 */
@Entity
@Table(name = "link_permission")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LinkPermission.findAll", query = "SELECT l FROM LinkPermission l"),
    @NamedQuery(name = "LinkPermission.findById", query = "SELECT l FROM LinkPermission l WHERE l.id = :id"),
    @NamedQuery(name = "LinkPermission.findByName", query = "SELECT l FROM LinkPermission l WHERE l.name = :name"),
    @NamedQuery(name = "LinkPermission.findByUrl", query = "SELECT l FROM LinkPermission l WHERE l.url = :url"),
    @NamedQuery(name = "LinkPermission.findByMenuOrder", query = "SELECT l FROM LinkPermission l WHERE l.menuOrder = :menuOrder"),
    @NamedQuery(name = "LinkPermission.findByAuthorization", query = "SELECT l FROM LinkPermission l WHERE l.authorization = :authorization"),
    @NamedQuery(name = "LinkPermission.findByBarLink", query = "SELECT l FROM LinkPermission l WHERE l.barLink = :barLink"),
    @NamedQuery(name = "LinkPermission.findByAuthorizationAndOrdered", query = "SELECT l FROM LinkPermission l WHERE l.authorization = '*' OR l.authorization LIKE :authorization ORDER BY l.menuOrder ASC")})
public class LinkPermission implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "url")
    private String url;
    @Basic(optional = false)
    @NotNull
    @Column(name = "menu_order")
    private int menuOrder;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "authorization")
    private String authorization;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "bar_link")
    private String barLink;

    public LinkPermission() {
    }

    public LinkPermission(Integer id) {
        this.id = id;
    }

    public LinkPermission(Integer id, String name, String url, int menuOrder, String authorization, String barLink) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.menuOrder = menuOrder;
        this.authorization = authorization;
        this.barLink = barLink;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getMenuOrder() {
        return menuOrder;
    }

    public void setMenuOrder(int menuOrder) {
        this.menuOrder = menuOrder;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getBarLink() {
        return barLink;
    }

    public void setBarLink(String barLink) {
        this.barLink = barLink;
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
        if (!(object instanceof LinkPermission)) {
            return false;
        }
        LinkPermission other = (LinkPermission) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "system.entity.LinkPermission[ id=" + id + " ]";
    }
    
}
