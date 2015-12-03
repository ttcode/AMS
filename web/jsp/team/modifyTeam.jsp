<%-- 
    Document   : addTeam
    Created on : 13 juin 2013, 17:00:44
    Author     : Loyd
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="/WEB-INF/jspf/page_begin.jspf" %>

<link href="css/team.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="js/editTeam_script.js"></script>

<div id="team_alert"></div>
<form method="POST">
        <table style="width: 600px; margin: 0 auto; margin-top: 30px; margin-bottom: 30px;">
            <tr>
                    <td>
                            <label for="team_name">Team Name :</label>
                    </td>
                    <td>
                            <input id="team_name" type="text" maxlength="254" value="${team.getTeamName()}"/>
                    </td>
            </tr>
            <tr><td><br /></td></tr>
            <tr>
                    <td>
                            <label for="aircraft">Aircraft :</label>
                    </td>
                    <td>
                            <select id="aircraft" style="width:200px">
                                    <option value="">Not assigned</option>
                                    <c:forEach var="aircraft" items="${aircrafts}">
                                        <c:choose>
                                            <c:when test="${aircraft.id == team.getAircraftId().getId()}">
                                                <option selected value="${aircraft.id}">${aircraft.getAircraftRegistration()} - ${aircraft.getModelId().getAircraftModel()}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${aircraft.id}">${aircraft.getAircraftRegistration()} - ${aircraft.getModelId().getAircraftModel()}</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                            </select>
                    </td>
            </tr>
            <tr><td><br /></td></tr>
            <tr>
                    <td colspan="5" style="text-align: center; padding-top: 30px;">
                            <div id="once">
                                    <button type="submit" id="edit_team">Edit Team</button>
                                    <input type="hidden" id="team_id" value="${team.id}" />
                            </div>
                    </td>
            </tr>
</table>
</form>

<%@include file="/WEB-INF/jspf/page_end.jspf" %>