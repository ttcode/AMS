<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jspf/page_begin.jspf" %>
<script type="text/javascript" src="js/maintenance_script.js"></script>

<div class="alert" id="alert_box">
    
</div>
    <br />
    <c:choose>
        <c:when test="${avail == '0'}">
            <p style="text-align: center;">No aircraft in maintenance</p>
        </c:when>
        <c:otherwise>
            <form method="POST" onSubmit="return(submitMaintenance());">
                <table style="width: 300px; margin: 0 auto;">
                    <tr>
                        <td>
                            <label for="aircraft_registration">Aircraft registration:</label>
                        </td>
                        <td>
                             <select id="aircraft_registration" name="aircraft_registration" onChange="loadData();">
                                <option value=""></option>
                                <c:forEach var="aircraft" items="${aircraftList}">
                                    <option value="${aircraft.id}">${aircraft.aircraftRegistration}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                </table>
                <br />
                <div id="info" style="display: none; width: 450px; margin: 0 auto;">
                    <p>
                        Aircraft Registration: <span id="registration"></span>
                    </p>
                    <p>
                        Aircraft Status: <span id="actual_status"></span>
                    </p>
                    <p>
                        Maintenance Comments: <span id="actual_comment" style="font-style: italic;"></span>
                    </p>
                    <br />
                    <table style="width: 450px; margin: 0 auto;">
                        <tr>
                            <td>
                                <label for="aircraft_status">New Status:</label>
                            </td>
                            <td>
                                <select id="aircraft_status" name="aircraft_status">
                                    <option value=""></option>
                                    <option value="1">Operational</option>
                                    <option value="2">Maintenance Requested</option>
                                    <option value="3">Maintenance in Progress</option>
                                </select>    
                            </td>
                        </tr>
                        <tr><td>&nbsp;</td></tr>
                        <tr>
                            <td><label for="maintenance_comment">Maintenance Comments:</label></td>
                            <td><textarea id="maintenance_comment" name="maintenance_comment" cols="40"></textarea></td>
                        </tr>
                        <tr><td>&nbsp;</td></tr>
                        </tr>
                            <td colspan="2" align="center">
                                <button type="submit">Maintenance</button>
                            </td>
                        </tr>
                    </table>
                </div>
            </form>
        </c:otherwise>
    </c:choose>
        <br />

<%@include file="/WEB-INF/jspf/page_end.jspf" %>
