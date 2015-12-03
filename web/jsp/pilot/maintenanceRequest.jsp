<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jspf/page_begin.jspf" %>
<script type="text/javascript" src="js/submitMaintenanceRequest_script.js"></script>

<h1>Maintenance Request</h1><br />
<div id="alert_box" class="alert">
    
</div>
<br />
<c:choose>
    <c:when test="${aircraft == '0'}">
        <p style="text-align: center">No aircraft assigned.</p>
    </c:when>
    <c:otherwise>
        <c:if test="${aircraftEnt.aircraftStatus == '1'}">
           <form method="POST" onSubmit="return(submitMaintenanceRequest());">
                <table style="width: 600px; margin: 0 auto;">
                    <tr>
                        <td>
                            <label for="comment">Comments on technical issues:</label>
                        </td>
                        <td>
                            <textarea id="comment" name="comment" cols="40" rows="10"></textarea>
                            <input type="hidden" id="aircraft_id" name="aircraft_id" value="${aircraftEnt.id}" />
                        </td>
                    </tr>
                    <tr><td>&nbsp;</td></tr>
                    <tr>
                        <td colspan="2" style="text-align: center;">
                            <button type="submit">Submit to maintenance</button>
                        </td>
                    </tr>
                </table>
            </form> 
        </c:if>
        <c:if test="${aircraftEnt.aircraftStatus == '2'}">
            <p>
                Aircraft Status: <strong>Maintenance Requested</strong><br /><br />
                Maintenance Comments: <span style="font-style: italic;">${aircraftEnt.maintenanceComment}</span>
            </p>
        </c:if>
        <c:if test="${aircraftEnt.aircraftStatus == '3'}">
            <p>
                Aircraft Status: <strong>Maintenance in Progress</strong><br /><br />
                Maintenance Comments: <span style="font-style: italic;">${aircraftEnt.maintenanceComment}</span>
            </p>
        </c:if>
    </c:otherwise>
</c:choose>
<br />

<%@include file="/WEB-INF/jspf/page_end.jspf" %>