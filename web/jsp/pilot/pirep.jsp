<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jspf/page_begin.jspf" %>
<script type="text/javascript" src="js/sendPirep_script.js"></script>

<div id="alert_box" class="alert">
    
</div>
<c:choose>
    <c:when test="${dispatch == '0'}">
        <p style="text-align: center;">No flight plan.</p>
    </c:when>
    <c:otherwise>
        <c:choose>
            <c:when test="${msg != ''}">
                <p style="text-align: center; font-weight: bold; color: #87030e;">${msg}</p>
            </c:when>
            <c:otherwise>
                <br />
                <form method="POST" onSubmit="return(submitPirep());">
                    <table style="width: 600px; margin: 0 auto;">
                        <tr>
                            <td>Pilot ID:</td>
                            <td>${employee.name}</td>
                        </tr>
                        <tr><td>&nbsp;</td></tr>
                        <tr>
                            <td>Flight Number:</td>
                            <td>${flightplan.flightNumber}</td>
                        </tr>
                        <tr><td>&nbsp;</td></tr>
                        <tr>
                            <td>Equipment:</td>
                            <td>${aircraft.aircraftRegistration}</td>
                        </tr>
                        <tr><td>&nbsp;</td></tr>
                        <tr>
                            <td>Departure Airport:</td>
                            <td>${flightplan.departureAirport.icao} - ${flightplan.departureAirport.name}</td>
                        </tr>
                        <tr><td>&nbsp;</td></tr>
                        <tr>
                            <td><label for="dep_gate">Departure Gate (terminal gate):</label></td>
                            <td><input type="text" id="dep_gate" name="dep_gate"/></td>
                        </tr>
                        <tr><td>&nbsp;</td></tr>
                        <tr>
                            <td><label for="dep_time">Departure Time (Zulu HHMM):</label></td>
                            <td><input type="text" id="dep_time" name="dep_time"/></td>
                        </tr>
                        <tr><td>&nbsp;</td></tr>
                        <tr>
                            <td>Arrival Airport:</td>
                            <td>${flightplan.arrivalAirport.icao} - ${flightplan.arrivalAirport.name}</td>
                        </tr>
                        <tr><td>&nbsp;</td></tr>
                        <tr>
                            <td><label for="arr_time">Arrival Time (Zulu HHMM):</label></td>
                            <td><input type="text" id="arr_time" name="arr_time"/></td>
                        </tr>
                        <tr><td>&nbsp;</td></tr>
                        <tr>
                            <td><label for="arr_gate">Arrival Gate (terminal gate):</label></td>
                            <td><input type="text" id="arr_gate" name="arr_gate"/></td>
                        </tr>
                        <tr><td>&nbsp;</td></tr>
                        <tr>
                            <td><label for="flight_hour">Total Flight Hour (HHMM):</label></td>
                            <td><input type="text" id="flight_hour" name="flight_hour"/></td>
                        </tr>
                        <tr><td>&nbsp;</td></tr>
                        <tr>
                            <td><label for="fuel">Fuel Consumption (lbs.):</label></td>
                            <td><input type="text" id="fuel" name="fuel"/></td>
                        </tr>
                        <tr><td>&nbsp;</td></tr>
                        <tr>
                            <td><label for="comments">Comments:</label></td>
                            <td><textarea id="comments" name="comments"></textarea></td>
                        </tr>
                        <tr><td>&nbsp;</td></tr>
                        <tr><td colspan="2" style="text-align: center;"><button type="submit">Submit PIREP</button></td></tr>
                    </table>
                    <br /><br />
                </form>
            </c:otherwise>
        </c:choose>
    </c:otherwise>
</c:choose>

<%@include file="/WEB-INF/jspf/page_end.jspf" %>
