<%-- 
    Document   : airportManagement
    Created on : 15 juin 2013, 10:03:26
    Author     : Jean
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jspf/page_begin.jspf" %>
<script type="text/javascript" src="js/deleteAirport_script.js"></script>

<div class="alert" id="alert_box">
    
</div>
<div style="padding-top: 20px; padding-left: 20px;"><form method="post" action="addAirport"><button type="submit">Add Airport</button></form></div>
<div id="div_table" style="width: 600px; margin: 0 auto; margin-top: 20px; margin-bottom: 30px;">
    <table class="body_table" style="text-align: center; width:600px;">
        <thead>
            <tr>
                <th colspan="2">Actions</th>
                <th>ICAO Code</th>
                <th>Airport Name</th>
                <th>HUB</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="airport" items="${airportList}">
                <tr>
                    <td width="55">
                        <form method="post" action="editAirport?icao=${airport.icao}"><button type="submit">Edit</button></form>
                    </td>
                    <td width="50">
                        <form method="post" onSubmit="return(submitDeleteAirport('${airport.icao}'));"><button type="submit">Delete</button></form>
                    </td>
                    <td>${airport.icao}</td>
                    <td>${airport.name}</td>
                    <td>${airport.isHub}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

<%@include file="/WEB-INF/jspf/page_end.jspf" %>