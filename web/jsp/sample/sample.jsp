<%-- 
    Document   : sample
    Created on : 9 mai 2013, 20:15:01
    Author     : Jean
--%>

<!-- You must include this part -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jspf/page_begin.jspf" %>
<!-- End of inclusion -->

<!-- Put the content of the page here -->
By default a new created page is not accessible as the system don't know who can access this URL (which type of account).
You must add an entry in the database, on "permission_link" table in order to indicate to the system the permission level of this page.
For further information, please contact Loyd or Jean.
Thank you !

<!-- You must include this part -->
<%@include file="/WEB-INF/jspf/page_end.jspf" %>
<!-- End of inclusion -->