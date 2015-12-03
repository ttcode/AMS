<%-- 
    Document   : addNews
    Created on : 16 juin 2013, 21:20:22
    Author     : Loyd
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="/WEB-INF/jspf/page_begin.jspf" %>

<link href="css/home.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="js/addAdvice_script.js"></script>

<div id="home_alert"></div>
<form method="POST">
        <table style="width: 600px; margin: 0 auto; margin-top: 30px; margin-bottom: 30px;">
            <tr>
                    <td>
                            <label for="advice">Advice: </label>
                    </td>
                    <td>
                            <textarea id="advice" rows="2" cols="65"></textarea>
                    </td>
            </tr>
            <tr><td><br /></td></tr>
            <tr>
                    <td colspan="5" style="text-align: center; padding-top: 30px;">
                            <div id="once">
                                    <button type="submit" id="add_advice">Add Advice</button>
                            </div>
                    </td>
            </tr>
</table>
</form>

<%@include file="/WEB-INF/jspf/page_end.jspf" %>