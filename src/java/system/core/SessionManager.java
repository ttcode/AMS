package system.core;

import javax.servlet.http.HttpSession;

/**
 *
 * @author Jean
 * 
 * SessionManager class abstract the creation and desctruction of sessions
 * 
 */
public class                    SessionManager
{
    /*
     * checkSession is used for verifying actual session state. User is
     * automatically redirected to login page if not logged to the system.
     */
    public static boolean       checkSession(HttpSession session)
    {
        if (session == null || session.getAttribute("_systemAccount") == null)
            return (false);
        return (true);
    }
    
    /*
     * destroySession is used during user logout, destroying the session.
     */
    public static void          destroySession(HttpSession session)
    {   
        if (session != null)
        {
            if (session.getAttribute("_systemAccount") != null)
                session.setAttribute("_systemAccount", null);
            if (session.getAttribute("_sessionManager") != null)
                session.setAttribute("_sessionManager", null);
            if (session.getAttribute("_authorizer") != null)
                session.setAttribute("_authorizer", null);
            if (session.getAttribute("_menuGenerator") != null)
                session.setAttribute("_menuGenerator", null);
            if (session.getAttribute("_linkBarGenerator") != null)
                session.setAttribute("_linkBarGenerator", null);
            session.invalidate();
        }
    }
}
