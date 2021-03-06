<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/WEB-INF/jspf/page_begin.jspf" %>
<script type="text/javascript" src="js/editAirport_script.js"></script>

        <div class="alert" id="alert_box">
            
        </div>
        <form method="POST" onSubmit="return(submitEditAirport());">
            <table style="width: 450px; margin: 0 auto; margin-top: 30px; margin-bottom: 30px;">
                <tr>
                    <td>
                        <label for="icao">ICAO Code:</label>
                    </td>
                    <td>
                        ${airport.icao}
                        <input type="hidden" id="icao" name="icao" value="${airport.icao}" />
                    </td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td>
                        <label for="airport_name">Airport Name:</label>
                    </td>
                    <td>
                        <input type="text" id="airport_name" name="airport_name" value="${airport.name}" />
                    </td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td>
                        <label for="hub">HUB:</label>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${airport.isHub == true}">
                                <input type="checkbox" id="hub" name="hub" value="hub" checked="checked" />
                            </c:when>
                            <c:otherwise>
                                <input type="checkbox" id="hub" name="hub" value="hub" />
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        <button type="submit">Edit Airport</button>
                    </td>
                </tr>
            </table>
        </form>
<%@include file="/WEB-INF/jspf/page_end.jspf" %>

