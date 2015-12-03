<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jspf/page_begin.jspf" %>
<script type="text/javascript" src="js/updateAircraft_script.js"></script>

<div class="alert" id="alert_box">
    
</div>
<br />
        <form method="POST" onSubmit="return(submitUpdateAircraft());">
            <input type="hidden" id="id" name="id" value="${aircraft.id}" />
            <table style="width: 550px; margin: 0 auto;">
                <tr>
                    <td>
                        <label for="model_id">Model id of aircraft:</label>
                    </td>
                    <td>
                        <select id="model_id" name="model_id">
                            <option value=""></option>
                            <c:forEach var="aircraftModel" items="${aircraftModelList}">
                                <c:choose>
                                    <c:when test="${aircraft.modelId.id == aircraftModel.id}">
                                        <option value="${aircraftModel.id}" selected="selected">${aircraftModel.aircraftModel}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${aircraftModel.id}">${aircraftModel.aircraftModel}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr><td>&nbsp;</td></tr>
                <tr>
                    <td>
                        <label for="aircraft_registration">Aircraft registration:</label>
                    </td>
                    <td>
                        <input type="hidden" id="old_aircraft_registration" name="old_aircraft_registration" value="${aircraft.aircraftRegistration}" />
                        <input type="text" id="aircraft_registration" name="aircraft_registration" value="${aircraft.aircraftRegistration}">
                    </td>
                </tr>
                <tr><td>&nbsp;</td></tr>
                <tr>
                    <td>
                        <label for="flight_hour">Flight hour:</label>
                    </td>
                    <td>
                        <input type="text" id="flight_hour" name="flight_hour" value="${aircraft.flightHour}"/>
                    </td>
                </tr>
                <tr><td>&nbsp;</td></tr>
                <tr>
                    <td>
                        <label for="aircraft_status">Status of aircraft:</label>
                    </td>
                    <td>
                        <select id="aircraft_status" name="aircraft_status">
                            <c:choose>
                                <c:when test="${aircraft.aircraftStatus == '1'}">
                                    <option value="1" selected="selected">Operational</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="1">Operational</option>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${aircraft.aircraftStatus == '2'}">
                                    <option value="2" selected="selected">Maintenance Requested</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="2">Maintenance Requested</option>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${aircraft.aircraftStatus == '3'}">
                                    <option value="3" selected="selected">Maintenance in Progress</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="3">Maintenance in Progress</option>
                                </c:otherwise>
                            </c:choose>
                        </select>
                    </td>
                </tr>
                <tr><td>&nbsp;</td></tr>
                <tr>
                    <td>
                        <label for="position">Position of aircraft:</label>
                    </td>
                    <td>
                        <select id="position" name="position">
                            <option value=""></option>
                            <c:forEach var="airport" items="${airportList}">
                                <c:choose>
                                    <c:when test="${airport.icao == aircraft.position}">
                                        <option value="${airport.icao}" selected="selected">${airport.icao}: ${airport.name}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${airport.icao}">${airport.icao}: ${airport.name}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </td>
                </tr> 
                <tr><td>&nbsp;</td></tr>
                <tr>
                    <td>
                        <label for="assignedFlight">Assigned flight:</label>
                    </td>
                    <td>
                        <select id="assignedFlight" name="assignedFlight">
                            <option value=""></option>
                            <c:forEach var="flightplan" items="${flightplanList}">
                                <c:choose>
                                    <c:when test="${aircraft.assignedFlight.flightNumber == flightplan.flightNumber}">
                                        <option value="${flightplan.flightNumber}" selected="selected">${flightplan.flightNumber}: ${flightplan.departureAirport}->${flightplan.arrivalAirport}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${flightplan.flightNumber}">${flightplan.flightNumber}: ${flightplan.departureAirport}->${flightplan.arrivalAirport}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr><td>&nbsp;</td></tr>
                <tr>
                    <td colspan="2" align="center">
                        <button type="submit">Update Aircraft</button>
                    </td>
                </tr>
            </table>
        </form>
        <br />

<%@include file="/WEB-INF/jspf/page_end.jspf" %>