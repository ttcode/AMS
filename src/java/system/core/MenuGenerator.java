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
import javax.servlet.http.HttpSession;
import system.entity.LinkPermission;
import system.entity.SystemAccount;

/**
 *
 * @author Jean
 */
public class                            MenuGenerator implements Serializable
{
    private String content = null;
    
    public String                       generate(HttpSession session)
    {
        if (content == null)
        {
            SystemAccount account = (SystemAccount) session.getAttribute("_systemAccount");
            fillContent(account);
        }

        return (content);
    }
    
    private void                        fillContent(SystemAccount account)
    {
        String rank = "" + account.getEmployee().getJobId().getId();
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("AMSPU");
        EntityManager em = emf.createEntityManager();
        
        List<LinkPermission> results = null;
        
        try
        {
            if (account.getRole().getId() == 1)
                results = em.createNamedQuery("LinkPermission.findByAuthorizationAndOrdered").setParameter("authorization", "%1%").getResultList();
            else
                results = em.createNamedQuery("LinkPermission.findByAuthorizationAndOrdered").setParameter("authorization", "%" + rank + "%").getResultList();
        }
        catch (Exception ex) {}
        
        if (results == null)
            return ;
        
        Iterator<LinkPermission> it = results.iterator();
        LinkPermission elem;
        
        content = "	<ul>\n";
        
        while (it.hasNext())
        {
            elem = it.next();
            if (elem.getMenuOrder() != 0)
                content += "		<li><a href=\"" + elem.getUrl() + "\" onmouseover=\"$(this).parent().css('background-color', '#9a0000');\" onmouseout=\"$(this).parent().css('background-color', 'white');\">> " + elem.getName() + "</a></li>\n";
        }
        content += "	</ul>\n";
    }
}
