<%-- 
    Document   : manageEmployee
    Created on : 15 mai 2013, 01:20:59
    Author     : Loyd
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="/WEB-INF/jspf/page_begin.jspf" %>

<div style="padding-top: 20px; padding-left: 20px;"><form method="post" action="addEmployee"><button type="submit">Add Employee</button></form></div>
<div id="div_table" style="width: 600px; margin: 0 auto; margin-top: 20px; margin-bottom: 30px;">
        <table class="body_table" style="width: 600px;">
                <tr>
                        <th colspan="2">Actions</th><th>Registration Date</th><th>Employee Name</th><th>Employee Job</th>
                <tr>
                <c:forEach var="employee" items="${employees}">
                        <tr>
                                <td style="width: 55px;">
                                        <form method="post" action="modifyEmployee">
                                                <input type="hidden" name="id" id="id" value="${employee.id}" />
                                                <button type="submit">Edit</button>
                                        </form>
                                </td>
                                <td style="width: 50px;">
                                        <form method="post" action="removeEmployee">
                                                <input type="hidden" name="id" id="id" value="${employee.id}" />
                                                <button type="submit" onclick="return(confirm('Are you sure to delete this employee ?'))">Delete</button>
                                        </form>
                                </td>
                                <td style="text-align: center;">${employee.registrationDate}</td>
                                <td style="text-align: center;">${employee.name}</td>
                                <td style="text-align: center;">${employee.jobId.jobName}</td>
                        </tr>
                </c:forEach>
        </table>
</div>

<%@include file="/WEB-INF/jspf/page_end.jspf" %>