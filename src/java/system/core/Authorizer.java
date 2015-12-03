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
public class                    Authorizer implements Serializable
{
    private List<LinkPermission> results = null;
            
    public boolean              checkAuthorization(HttpSession session, HttpServletRequest request)
    {
        if (results == null)
        {
            SystemAccount account = (SystemAccount) session.getAttribute("_systemAccount");
            fillResults(account);
        }
        if (results == null)
            return (false);

        // Get current path here. Dunno if it is request.getServletPath or request.getContextPath or request.getRequestURI
        String currentPage = request.getServletPath();
        currentPage = currentPage.replace("/", "");
        
        Iterator<LinkPermission> it = results.iterator();
        LinkPermission elem;
        boolean check = false;
        
        while (it.hasNext())
        {
            elem = it.next();
            if (currentPage.equals(elem.getUrl()))
                check |= true;
        }
        return (check);
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
