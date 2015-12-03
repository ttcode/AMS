<%-- 
    Document   : addFlight
    Created on : Jun 15, 2013, 1:09:51 AM
    Author     : chatri.pi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="/WEB-INF/jspf/page_begin.jspf" %>


<link href="css/employee.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="js/addFlight_script.js"></script>

<div id="flight_alert"></div>
<!DOCTYPE html>
        <form method="POST">
            <table style="width: 760px; margin: 0 auto;">
                <tbody>
                    <tr>
                        <td>Flight Number :<input type="text" id="flight_number" name="flightNumber" /></td>
                    </tr>
                    <tr><td><br /></td></tr>
                    <tr>
                        <td>Flight Type :<select id="flight_type" name="Flight Type">
                             <option></option>
                             <c:forEach var="flightType" items="${flightTypes}">
                                <option>${flightType.typeName}</option>
                             </c:forEach>
                        </select></td>

                    </tr>
                    <tr><td><br /></td></tr>
                    <tr>
                        <td>From :<select id="from" name="Airports">
                                 <option></option>
                                 <c:forEach var="airport" items="${airports}">
                                    <option value="${airport.icao}">${airport.icao}/${airport.name}</option>
                                 </c:forEach>
                            </select>
                        </td>
                        <td>&nbsp;</td>
                        <td>To :<select id="to" name="Airports">
                                 <option></option>
                                 <c:forEach var="airport" items="${airports}">
                                    <option value="${airport.icao}">${airport.icao}/${airport.name}</option>
                                 </c:forEach>
                            </select></td>
                    </tr>
                    <tr><td><br /></td></tr>
                    <tr>
                        <td>Departure :<input type="text" id="dhour" name="dhour" value="" size="2" width="2"/>HH
                            <input type="text" id="dminute" name="dminute" value="" size="2" width="2"/>MM</td>
                        <td>&nbsp;</td>
                        <td>Duration :<input type="text" id="hour" name="hour" value="" size="2" width="2"/>HH
                            <input type="text" id="minute" name="minute" value="" size="2" width="2"/>MM</td>
                    </tr>
                    <tr><td><br /></td></tr>
                    <tr>
                        <td>Flight Route :<input type="text" id="flight_route" name="route" value="" /></td>
                        <td>&nbsp;</td>
                        <td>Repeat Rule :<select id="repeat_rule" name="repeatRule">
                                 <option>0</option>
                                 <c:forEach var="flight" items="${flights}">
                                    <option>${flight.flightNumber}</option>
                                 </c:forEach>
                            </select></td>
                    </tr>
                    <tr>
                    <td colspan="5" style="text-align: center; padding-top: 30px;">
                            <div id="once">
                                    <button type="submit" id="add_flight">Add Flight</button>
                            </div>
                    </td>
            </tr>
                </tbody>
            </table>

        </form>

<%@include file="/WEB-INF/jspf/page_end.jspf" %>