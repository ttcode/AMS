<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jspf/page_begin.jspf" %>
<script type="text/javascript" src="js/createAircraft_script.js"></script>

    <br />
    <div class="alert" id="alert_box">
        
    </div>
    <br />
        <form method="POST" onSubmit="return(submitCreateAircraft());">
            <table style="width: 450px; margin: 0 auto;">
                <tr>
                    <td>
                        <label for="model_id">Aircraft model:</label>
                    </td>
                    <td>
                        <select id="model_id" name="model_id">
                            <option value=""></option>
                            <c:forEach var="aircraftModel" items="${aircraftModelList}">
                                <option value="${aircraftModel.id}">${aircraftModel.aircraftModel}</option>
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
                        <input type="text" id="aircraft_registration" name="aircraft_registration">
                    </td>
                </tr>
                <tr><td>&nbsp;</td></tr>
                <tr>
                    <td>
                        <label for="flight_hour">Flight hour:</label>
                    </td>
                    <td>
                        <input type="text" id="flight_hour" name="flight_hour" />
                    </td>
                </tr>
                <tr><td>&nbsp;</td></tr>
                <tr>
                    <td>
                        <label for="aircraft_status">Status of aircraft:</label>
                    </td>
                    <td>
                        <select id="aircraft_status" name="aircraft_status">
                            <option value="1">Operational</option>
                            <option value="2">Maintenance Requested</option>
                            <option value="3">Maintenance in Progress</option>
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
                                <option>${airport.icao}</option>
                            </c:forEach>
                        </select>  
                    </td>
                </tr>
                <tr><td>&nbsp;</td></tr>
                <tr>
                    <td colspan="2" align="center">
                        <button type="submit">Create Aircraft</button>
                    </td>
                </tr>
            </table>
        </form>
    <br />

<%@include file="/WEB-INF/jspf/page_end.jspf" %>