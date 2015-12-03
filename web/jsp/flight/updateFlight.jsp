<%-- 
    Document   : addFlight
    Created on : Jun 15, 2013, 1:09:51 AM
    Author     : chatri.pi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="/WEB-INF/jspf/page_begin.jspf" %>


<link href="css/employee.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="js/updateFlight_script.js"></script>

<div id="flight_alert"></div>
<!DOCTYPE html>

        <form method="POST">
            <table style="width:760px; margin: 0 auto;">
                <tbody>
                    <tr>
                        <td>Flight Number : ${flightplan.flightNumber}<input type="hidden" id="flight_number" name="flightNumber" value="${flightplan.flightNumber}" /></td>
                    </tr>
                    <tr><td><br /></td></tr>
                    <tr>
                        <td>Flight Type :<select id="flight_type" name="Flight Type">
                             <c:forEach var="flightType" items="${flightTypes}">
                                 <c:choose>
                                    <c:when test="${flightType.id == type}"> 
                                        <option selected >${flightType.typeName}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option>${flightType.typeName}</option>
                                    </c:otherwise>
                                </c:choose>
                             </c:forEach>
                        </select></td>

                    </tr>
                    <tr><td><br /></td></tr>
                    <tr>
                        <td>From :<select id="from" name="Airports">
                                 <c:forEach var="fromAirport" items="${fromAirports}">
                                    <c:choose>
                                    <c:when test="${fromAirport.icao == flightplan.departureAirport}">
                                        <option selected value="${fromAirport.icao}">${fromAirport.icao}/${fromAirport.name}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${fromAirport.icao}">${fromAirport.icao}/${fromAirport.name}</option>
                                    </c:otherwise>
                                </c:choose>
                                 </c:forEach>
                            </select>
                        </td>
                        <td>&nbsp;</td>
                        <td>To :<select id="to" name="Airports">
                                 <option></option>
                                 <c:forEach var="toAirport" items="${toAirports}">
                                    <c:choose>
                                    <c:when test="${toAirport.icao == flightplan.arrivalAirport}">
                                        <option selected value="${toAirport.icao}">${toAirport.icao}/${toAirport.name}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${toAirport.icao}">${toAirport.icao}/${toAirport.name}</option>
                                    </c:otherwise>
                                </c:choose>
                                 </c:forEach>
                            </select></td>
                    </tr>
                    <tr><td><br /></td></tr>
                    <tr>
                        <td>Departure :<input type="text" id="dhour" name="dhour" value="${dhour}" size="2" width="2"/>HH
                            <input type="text" id="dminute" name="dminute" value="${dminute}" size="2" width="2"/>MM</td>
                        <td>&nbsp;</td>
                        <td>Duration :<input type="text" id="hour" name="hour" value="${hour}" size="2" width="2"/>HH
                            <input type="text" id="minute" name="minute" value="${minute}" size="2" width="2"/>MM</td>
   
                    </tr>
                    <tr><td><br /></td></tr>
                    <tr>
                        <td>Flight Route :<input type="text" id="flight_route" name="route" value="${flightplan.flightRoute}" /></td>
                        <td>&nbsp;</td>
                        <td>Repeat Rule :<select id="repeat_rule" name="repeatRule">
                                 <option>0</option>
                                 <c:forEach var="flight" items="${flights}">
                                    <c:choose>
                                    <c:when test="${flight.flightNumber == flightplan.repeatRule}">
                                        <option selected value="${flight.flightNumber}">${flight.flightNumber}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${flight.flightNumber}">${flight.flightNumber}</option>
                                    </c:otherwise>
                                </c:choose>
                                 </c:forEach>
                            </select></td>
                    </tr>
                    <tr>
                    <td colspan="5" style="text-align: center; padding-top: 30px;">
                            <div id="once">
                                    <button type="submit" id="update_flight">Update Flight</button>
                                    <input type="hidden" id="flightNumber" value="${flightplan.flightNumber}" />
                            </div>
                    </td>
            </tr>
                </tbody>
            </table>
        </form>

<%@include file="/WEB-INF/jspf/page_end.jspf" %>