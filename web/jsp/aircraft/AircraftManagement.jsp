<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jspf/page_begin.jspf" %>
<script type="text/javascript" src="js/deleteAircraft_script.js"></script>

<div class="alert" id="alert_box">
    
</div>
<div id="div_table" style="width: 750px; margin: 0 auto; margin-top: 20px; margin-bottom: 30px;">
    <form method="POST" onSubmit="return(submitDeleteAircraft());">
        <table class="body_table" style="text-align: center; width: 100%">
            <thead>
                <tr>
                    <th></th>
                    <th>Aircraft registration</th>
                    <th>Flight hour</th>
                    <th>Status of aircraft</th>
                    <th>Position of aircraft</th>
                    <th>Assigned flight</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="aircraft"items="${aircraftList}" >
                    <tr>
                        <td><input name="AircraftManagement" type="checkbox" value="${aircraft.id}"/></td>
                        <td>${aircraft.aircraftRegistration}</td>
                        <td>${aircraft.flightHour}</td>
                        <td>
                            <c:if test="${aircraft.aircraftStatus == '1'}">
                                <span style="color: green;">Operational</span>
                            </c:if>
                            <c:if test="${aircraft.aircraftStatus == '2'}">
                                <span style="color: red;">Maintenance requested</span>
                            </c:if>
                            <c:if test="${aircraft.aircraftStatus == '3'}">
                                <span style="color: orange;">Maintenance in progress</span>
                            </c:if>
                        </td>
                        <td>${aircraft.position.icao}</td>
                        <td>${aircraft.assignedFlight.flightNumber}</td>
                        <td><a href="UpdateAircraft?id=${aircraft.id}"><img src="img/edit.png" alt="Update" /></a>
                    </tr>
                </c:forEach>
            </tbody>
            <tfoot>
                <tr>
                    <td colspan="3" align="center">
                        <button type="submit">Delete</button>
                    </td>
                    </form>
                    <td colspan="4" align="center">
                        <form method="POST" action="CreateAircraft">
                            <button type="submit">Create</button>
                        </form>
                    </td>
                </tr>
            </tfoot>                                
        </table>
</div>

<%@include file="/WEB-INF/jspf/page_end.jspf" %>
