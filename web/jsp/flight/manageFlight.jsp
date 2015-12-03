<%-- 
    Document   : manageFlight
    Created on : Jun 14, 2013, 10:35:01 PM
    Author     : chatri.pi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="/WEB-INF/jspf/page_begin.jspf" %>

<link href="css/employee.css" type="text/css" rel="stylesheet" />

<div style="padding-top: 20px; padding-left: 20px;"><form method="post" action="addFlight"><button type="submit">Add Flight</button></form></div>
<div id="div_table" style="width: 760px; margin: 0 auto; margin-top: 20px; margin-bottom: 30px; margin-left: 0px">
        <table class="body_table" style="width: 756px;">
            <thead>
                <tr>
                    <th colspan="2">Action</th>
                    <th>Flight Number</th>
                    <th>Flight Type</th>
                    <th>From</th>
                    <th>To</th>
                    <th>Departure</th>
                    <th>Duration</th>
                    <!--<th>Route</th>-->
                    <th>Next Flight</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="flight" items="${flights}">
                <tr>
                    <td width="55">
                        <form method="post" action="updateFlight">
                            <input type="hidden" name="flightNumber" value="${flight.flightNumber}" />
                            <%--         <input type="hidden" name="flightType" value="${flight.flightType.id}" />
                            <input type="hidden" name="dAirport" value="${flight.departureAirport.icao}" />
                            <input type="hidden" name="aAirport" value="${flight.arrivalAirport.icao}" />
                            <input type="hidden" name="departureTime" value="${flight.departureTime}" />
                            <input type="hidden" name="flightDuration" value="${flight.flightDuration}" />
                            <input type="hidden" name="flightRoute" value="${flight.flightRoute}" />
                            <input type="hidden" name="repeatRule" value="${flight.repeatRule}" />--%>
                            <button type="submit">Edit</button>
                        </form>
                    </td>
                    <td style="width: 50px;">
                        <form method="post" action="deleteFlight">
                                <input type="hidden" name="flightNumber" value="${flight.flightNumber}" />
                                <button type="submit" onclick="return(confirm('Are you sure to delete this Flight ?'))">Delete</button>
                        </form>
                    </td>
                    <td style="text-align: center;">${flight.flightNumber}</td>
                    <td style="text-align: center;">${flight.flightType.typeName}</td>
                    <td style="text-align: center;">${flight.departureAirport.icao}</td>
                    <td style="text-align: center;">${flight.arrivalAirport.icao}</td>
                    <td style="text-align: center;">${flight.departureTime}</td>
                    <td style="text-align: center;">${flight.flightDuration}</td>
                    <!--<td style="text-align: center;">${flight.flightRoute}<td>-->
                    <td style="text-align: center;">${flight.repeatRule}<td>
                </tr>
                </c:forEach>
            </tbody>
        </table>

<%@include file="/WEB-INF/jspf/page_end.jspf" %>