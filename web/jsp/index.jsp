<%-- 
    Document   : index
    Created on : 3 mai 2013, 20:32:06
    Author     : Jean
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="/WEB-INF/jspf/page_begin.jspf" %>

<link href="css/home.css" type="text/css" rel="stylesheet" />

<div id="date">Welcome ! We are now <b>${date}</b></div>
<div id="today_message">
    <c:choose>
        <c:when test="${empty motd.getContent()}">
            Special advice: Yahallo~ !!
        </c:when>
        <c:otherwise>
            Today's advice: ${motd.getContent()}
        </c:otherwise>
    </c:choose>
</div>
<c:if test="${jobId == 1}">
    <div style="padding-top: 20px; padding-left: 20px;">
        <table>
            <tr>
                <td>
                    <form method="post" action="addNews"><button type="submit">Add Message</button></form>
                </td>
                <td>
                    <form method="post" action="addAdvice"><button type="submit">Add Advice</button></form>
                </td>
            </tr>
        </table>
    </div>
</c:if>
<div id="div_table" style="width: 760px; margin: 0 auto; margin-top: 20px; margin-bottom: 30px;">
    <table class="body_table" style="width: 760px;">
        <c:forEach var="message" items="${news}">
            <c:choose>
                <c:when test="${jobId == 1}">
                <tr>
                    <th class="title">${message.getTitle()} - ${message.getDate()}</th>
                    <th class="delete">
                        <form method="post" action="removeNews">
                            <input type="hidden" name="news_id" id="news_id" value="${message.id}" />
                            <button type="submit" onclick="return(confirm('Are you sure to delete this news ?'))">Delete</button>
                        </form>
                    </th>
                </tr>
                <tr>
                    <td colspan="2" class="news">${message.getContent()}</td>
                </tr>
                </c:when>
                <c:otherwise>
                <tr>
                    <th class="title">${message.getTitle()} - ${message.getDate()}</th>
                </tr>
                <tr>
                    <td class="news">${message.getContent()}</td>
                </tr>
                </c:otherwise>
            </c:choose>
            <tr><td class="empty"></td></tr>
        </c:forEach>
    </table>
</div>
<%@include file="/WEB-INF/jspf/page_end.jspf" %>