/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package system.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Jean
 */
public class                            AppCore
{
    public static boolean               initPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession                     session = request.getSession();
        Authorizer                      authorizer = (Authorizer)objectFactory("Authorizer", session);
        MenuGenerator                   menuGenerator = (MenuGenerator)objectFactory("MenuGenerator", session);
        LinkBarGenerator                linkBarGenerator = (LinkBarGenerator)objectFactory("LinkBarGenerator", session);
        List<Object>                    objectList = new ArrayList<Object>();
        
        if (SessionManager.checkSession(session) == false)
        {
            request.getRequestDispatcher("jsp/system/login.jsp").forward(request, response);
            return (false);
        }
        if (authorizer.checkAuthorization(session, request) == false)
        {
            request.getRequestDispatcher("jsp/system/access_denied.jsp").forward(request, response);
            return (false);
        }
        request.setAttribute("generatedMenu", menuGenerator.generate(session));
        request.setAttribute("generatedPageBar", linkBarGenerator.generate(session, request));
        objectList.add(authorizer);
        objectList.add(menuGenerator);
        objectList.add(linkBarGenerator);
        saveObjectToSession(objectList, session);
        return (true);
    }
    
    private static Object               objectFactory(String className, HttpSession session)
    {
        String                          tempName = className;
        
        if (className.length() > 0 && className.charAt(0) >= 'A' && className.charAt(0) <= 'Z')
        {
            tempName = className.substring(1);
            tempName = className.substring(0, 1).toLowerCase().concat(tempName);
        }
        Object obj = session.getAttribute("_" + tempName);
        if (obj == null)
        {
            try
            {
                obj = Class.forName("system.core." + className).newInstance();
            }
            catch (Exception ex)
            {
                Logger.getLogger(AppCore.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return (obj);
    }
    
    private static void                 saveObjectToSession(List<Object> objectList, HttpSession session)
    {
        Iterator<Object>                it = objectList.iterator();
        Object                          elem;
        String                          className;
        String                          tempName;
        
        while (it.hasNext())
        {
            elem = it.next();
            className = elem.getClass().getName().substring(12);
            tempName = className;
            
            if (className.length() > 0 && className.charAt(0) >= 'A' && className.charAt(0) <= 'Z')
            {
                tempName = className.substring(1);
                tempName = className.substring(0, 1).toLowerCase().concat(tempName);
            }
            session.setAttribute("_" + tempName, elem);
        }
    }
}
