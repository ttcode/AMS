<%-- 
    Document   : editEmployee
    Created on : 15 mai 2013, 02:39:51
    Author     : Loyd
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="/WEB-INF/jspf/page_begin.jspf" %>

<link href="css/employee.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="js/modifyEmployee_script.js"></script>

<div id="employee_alert"></div>
<form method="POST">
        <table style="width: 600px; margin: 0 auto; margin-top: 30px; margin-bottom: 30px;">
            <c:choose>
                <c:when test="${jobId == 1}">
                    <tr>
                            <td>
                                    <label for="account_type">Account type :</label>
                            </td>
                            <td>
                                    <select id="account_type">
                                            <option></option>
                                            <c:forEach var="accountType" items="${accountTypes}">
                                                <c:choose>
                                                    <c:when test="${accountType.id == account.role.id}">
                                                        <option selected value="${accountType.id}">${accountType.roleName}</option>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <option value="${accountType.id}">${accountType.roleName}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                    </select>
                            </td>
                    </tr>
                    <tr><td><br /></td></tr>
                </c:when>
                <c:otherwise>
                    <input type="hidden" id="account_type" value="2" />
                </c:otherwise>
            </c:choose>
            <tr>
                    <td>
                            <label for="login">Login :</label>
                    </td>
                    <td>
                            <input id="login" type="text" maxlength="49" value="${account.login}"/>
                    </td>
                    <td>&nbsp;</td>
                    <td>
                            <label for="password">Password :</label>
                    </td>
                    <td>
                            <input id="password" type="password"/>
                    </td>
            </tr>
            <tr><td><br /></td></tr>
            <tr>
                    <td>
                            <label for="name">Name :</label>
                    </td>
                    <td>
                            <input id="name" type="text" value="${account.employee.name}"/>
                    </td>
                    <td>&nbsp;</td>
                    <td>
                            <label for="phone_number">Phone Number :</label>
                    </td>
                    <td>
                            <input id="phone_number" type="text" maxlength="20" value="${account.employee.phone}"/>
                    </td>
            </tr>
            <tr><td><br /></td></tr>
            <tr>
                    <td>
                            <label for="address">Address :</label>
                    </td>
                    <td colspan="4">
                            <textarea id="address" cols="70">${account.employee.address}</textarea>
                    </td>
            </tr>
            <tr><td><br /></td></tr>
            <tr>
                    <td>
                            <label for="job">Job :</label>
                    </td>
                    <td>
                            <select id="job" >
                                    <option value=""></option>
                                    <c:forEach var="job" items="${jobs}">
                                        <c:choose>
                                            <c:when test="${job.id == account.employee.jobId.id}">
                                                <option selected value="${job.id}">${job.jobName}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${job.id}">${job.jobName}</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                            </select>
                    </td>
                    <td>&nbsp;</td>
                    <td>
                            <label for="hub">HUB :</label>
                    </td>
                    <td>
                            <select id="hub" style="width:120px">
                                    <option></option>
                                    <c:forEach var="airport" items="${airports}">
                                        <c:if test="${airport.isHub == true}">
                                            <c:choose>
                                                <c:when test="${airport.icao == account.employee.hub.icao}">
                                                    <option selected value="${airport.icao}">${airport.icao}/${airport.name}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${airport.icao}">${airport.icao}/${airport.name}</option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:if>
                                    </c:forEach>
                            </select>
                    </td>
            </tr>
            <tr>
                    <td>&nbsp;</td>
            </tr>
            <tr>
                    <td>
                            <label for="salary">Salary :</label>
                    </td>
                    <td>
                            <input id="salary" type="text" value="${account.employee.salary}"/>
                    </td>
                    <td>&nbsp;</td>
                    <td>
                            <label for="localisation">Localisation :</label>
                    </td>
                    <td>
                            <select id="localisation" style="width:120px">
                                    <option></option>
                                    <c:forEach var="airport" items="${airports}">
                                        <c:choose>
                                            <c:when test="${airport.icao == account.employee.position.icao}">
                                                <option selected value="${airport.icao}">${airport.icao}/${airport.name}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${airport.icao}">${airport.icao}/${airport.name}</option>
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
                                    <button type="submit" id="edit_employee">Edit Employee</button>
                                    <input type="hidden" id="id" value="${account.id}" />
                            </div>
                    </td>
            </tr>
</table>
</form>

<%@include file="/WEB-INF/jspf/page_end.jspf" %>