/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.core;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import system.entity.LinkPermission;
import system.entity.SystemAccount;

/**
 *
 * @author Jean
 */
public class                            LinkBarGenerator implements Serializable
{
    private List<LinkPermission> results = null;
    private String content = "";
    
    public String                       generate(HttpSession session, HttpServletRequest request)
    {
        if (results == null)
        {
            SystemAccount account = (SystemAccount) session.getAttribute("_systemAccount");
            fillResults(account);
        }
        if (results == null)
            return ("");
        
        // Get current path here. Dunno if it is request.getServletPath or request.getContextPath or request.getRequestURI
        String currentPage = request.getServletPath();
        currentPage = currentPage.replace("/", "");
        
        content = "";
        Iterator<LinkPermission> it = results.iterator();
        LinkPermission elem;
        
        while (it.hasNext())
        {
            elem = it.next();
            if (currentPage.equals(elem.getUrl()))
            {
                barSeeker(elem.getBarLink());
                break ;
            }
        }
        
        return (content);
    }
    
    private void                barSeeker(String barLink)
    {
        String[] links = barLink.split(";");
        
        for (int i = 0; i < links.length; ++i)
        {
            barDiv(links[i], i);
        }
    }
    
    private void              barDiv(String id, int count)
    {
        Iterator<LinkPermission> it = results.iterator();
        LinkPermission elem;
        
        while (it.hasNext())
        {
            elem = it.next();
            if (id.equals("" + elem.getId()))
            {
                if (count == 0)
                    content += "		<div class=\"first\"><p>> <a href=\"" + elem.getUrl() + "\">" + elem.getName() + "</a></p></div>\n";             
                else    
                    content += "		<div class=\"second\"><p>> <a href=\"" + elem.getUrl() + "\">" + elem.getName() + "</a></p></div>\n";
            }
        }
    }
    
    private void                fillResults(SystemAccount account)
    {
        String rank = "" + account.getEmployee().getJobId().getId();
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("AMSPU");
        EntityManager em = emf.createEntityManager();
        
        try
        {
            if (account.getRole().getId() == 1)
                results = em.createNamedQuery("LinkPermission.findByAuthorizationAndOrdered").setParameter("authorization", "%1%").getResultList();
            else
                results = em.createNamedQuery("LinkPermission.findByAuthorizationAndOrdered").setParameter("authorization", "%" + rank + "%").getResultList();
        }
        catch (Exception ex) {}
    }
}
