<%-- 
    Document   : addTeamMember
    Created on : 14 juin 2013, 01:23:07
    Author     : Loyd
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="/WEB-INF/jspf/page_begin.jspf" %>

<link href="css/team.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="js/addTeamMember_script.js"></script>

<div id="team_alert"></div>
<form method="POST">
        <table style="width: 600px; margin: 0 auto; margin-top: 30px; margin-bottom: 30px;">
            <tr>
                    <td>
                            <label for="employee_id">Team member :</label>
                    </td>
                    <td>
                            <select id="employee_id" style="width:250px">
                                    <option value=""></option>
                                    <c:forEach var="employee" items="${employees}">
                                        <c:choose>
                                            <c:when test="${empty employee.getTeamId()}">
                                                <option value="${employee.id}">${employee.name} - ${employee.getJobId().getJobName()} - No Team Assigned</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${employee.id}">${employee.name} - ${employee.getJobId().getJobName()} - ${employee.getTeamId().getTeamName()}</option>
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
                                    <input type="hidden" name="team_id" id="team_id" value="${team.id}" />
                                    <button type="submit" id="add_team_member">Add Team</button>
                            </div>
                    </td>
            </tr>
</table>
</form>

<%@include file="/WEB-INF/jspf/page_end.jspf" %>