<%-- 
    Document   : manageTeam
    Created on : 22 mai 2013, 15:13:00
    Author     : Loyd
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="/WEB-INF/jspf/page_begin.jspf" %>

<div style="padding-top: 20px; padding-left: 20px;"><form method="post" action="addTeam"><button type="submit">Add Team</button></form></div>
<div id="div_table" style="width: 600px; margin: 0 auto; margin-top: 20px; margin-bottom: 30px;">
        <table class="body_table" style="width: 600px;">
                <c:forEach var="team" items="${teams}">
                        <tr>
                                <c:choose>
                                    <c:when test="${empty team.getAircraftId()}">
                                        <td colspan="4" class="identify" >Team Name : ${team.teamName} - Not assigned to an Aircraft yet</td>
                                    </c:when>
                                    <c:otherwise>
                                        <td colspan="4" class="identify" >Team Name : ${team.teamName} - Aircraft Registration : ${team.getAircraftId().getAircraftRegistration()} - Model : ${team.getAircraftId().getModelId().getAircraftModel()}</td>
                                    </c:otherwise>
                                </c:choose>
                        </tr>
                        <tr>
                                <th>Actions</th><th>Registration Date</th><th>Employee Name</th><th>Employee Job</th>
                        <tr>
                        <c:forEach var="employee" items="${team.employeeList}">
                        <tr>
                                <td style="text-align: center;">
                                        <form method="post" action="removeTeamMember">
                                                <input type="hidden" name="employee_id" id="employee_id" value="${employee.id}" />
                                                <button type="submit" onclick="return(confirm('Are you sure to delete this employee from this team ?'))">Delete</button>
                                        </form>
                                </td>
                                <td style="text-align: center;">${employee.registrationDate}</td>
                                <td style="text-align: center;">${employee.name}</td>
                                <td style="text-align: center;">${employee.jobId.jobName}</td>
                        </tr>
                        </c:forEach>
                        <tr>
                                <td style="text-align: center;">
                                        <form method="post" action="removeTeam">
                                                <input type="hidden" name="team_id" id="team_id" value="${team.id}" />
                                                <button type="submit" onclick="return(confirm('Are you sure to delete this team?'))">Delete Team</button>
                                        </form>
                                </td>
                                <td style="text-align: center;">
                                        <form method="post" action="modifyTeam">
                                                <input type="hidden" name="team_id" id="team_id" value="${team.id}" />
                                                <button type="submit">Edit Team</button>
                                        </form>
                                </td>
                                <td colspan="2" style="text-align: center;">
                                        <form method="post" action="addTeamMember">
                                                <input type="hidden" name="team_id" id="team_id" value="${team.id}" />
                                                <button type="submit">Add Team Member</button>
                                        </form>
                                </td>
                        </tr>
                </c:forEach>
        </table>
</div>

<%@include file="/WEB-INF/jspf/page_end.jspf" %>