<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jspf/page_begin.jspf" %>

<h1 style="font-family: Courier New, serif">Flight Dispatch</h1>
<br />
<c:choose>
    <c:when test="${dispatch == '1'}">
        <p style="font-family: Courier New, serif">
            <c:if test="${msg != ''}">
                <span style="font-weight: bold; font-family: Courier New, serif; color: #87030e;">${msg}</span><br />
            </c:if>
            Aircraft Model: ${aircraft.modelId.aircraftModel}<br />
            Aircraft Registration: ${aircraft.aircraftRegistration}<br /><br />
            Flight Number: ${flightplan.flightNumber}<br />
            Departure Time: ${flightplan.departureTime}<br />
            Estimated Flight Duration: ${flightplan.flightDuration}<br /><br />
            <span style="font-weight: bold; font-family: Courier New, serif;">Company Flight Route:</span><br /><br />
            ${flightplan.flightRoute}
        </p>
    </c:when>
    <c:otherwise>
        <p style="font-family: Courier New, serif">
            No flight plan assigned.
        </p>
    </c:otherwise>
</c:choose>


<%@include file="/WEB-INF/jspf/page_end.jspf" %>