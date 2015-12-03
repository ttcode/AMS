<%-- 
    Document   : add_employee
    Created on : 13 mai 2013, 16:11:46
    Author     : Loyd
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="/WEB-INF/jspf/page_begin.jspf" %>

<link href="css/employee.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="js/addEmployee_script.js"></script>

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
                                                <option value="${accountType.id}">${accountType.roleName}</option>
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
                            <input id="login" type="text" maxlength="49"/>
                    </td>
                    <td>&nbsp;</td>
                    <td>
                            <label for="password">Password :</label>
                    </td>
                    <td>
                            <input id="password" type="password" />
                    </td>
            </tr>
            <tr><td><br /></td></tr>
            <tr>
                    <td>
                            <label for="name">Name :</label>
                    </td>
                    <td>
                            <input id="name" type="text" />
                    </td>
                    <td>&nbsp;</td>
                    <td>
                            <label for="phone_number">Phone Number :</label>
                    </td>
                    <td>
                            <input id="phone_number" type="text" maxlength="20" />
                    </td>
            </tr>
            <tr><td><br /></td></tr>
            <tr>
                    <td>
                            <label for="address">Address :</label>
                    </td>
                    <td colspan="4">
                            <textarea id="address" cols="70"></textarea>
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
                                        <option value="${job.id}">${job.jobName}</option>
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
                                            <option value="${airport.icao}">${airport.icao}/${airport.name}</option>
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
                            <input id="salary" type="text" />
                    </td>
                    <td>&nbsp;</td>
                    <td>
                            <label for="localisation">Localisation :</label>
                    </td>
                    <td>
                            <select id="localisation" style="width:120px">
                                    <option></option>
                                    <c:forEach var="airport" items="${airports}">
                                        <option value="${airport.icao}">${airport.icao}/${airport.name}</option>
                                    </c:forEach>
                            </select>
                    </td>
            </tr>
            <tr><td><br /></td></tr>
            <tr>
                    <td colspan="5" style="text-align: center; padding-top: 30px;">
                            <div id="once">
                                    <button type="submit" id="add_employee">Add Employee</button>
                            </div>
                    </td>
            </tr>
</table>
</form>

<%@include file="/WEB-INF/jspf/page_end.jspf" %>