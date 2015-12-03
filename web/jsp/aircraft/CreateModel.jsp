<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/WEB-INF/jspf/page_begin.jspf" %>
<script type="text/javascript" src="js/createModel_script.js"></script>

        <div class="alert" id="alert_box">
            
        </div>
        <form method="POST" onSubmit="return(submitCreateModel());">
            <table style="width: 600px; margin: 0 auto; margin-top: 30px; margin-bottom: 30px;">
                <tr>
                    <td>
                        <label for="model_name">Aircraft model name:</label>
                    </td>
                    <td>
                        <input type="text" id="model_name" name="model_name" />                    
                    </td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td>
                        <label for="model_type">Aircraft model type:</label>
                    </td>
                    <td>
                        <select id="model_type" name="model_type">
                            <option value=""></option>
                            <c:forEach var="aircraftType" items="${aircraftTypeList}">
                                <option value="${aircraftType.id}">${aircraftType.typeName}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td>
                        <label for="required_pilot">Number of pilot required:</label>
                    </td>
                    <td>
                        <input type="text" id="required_pilot" name="required_pilot" />
                    </td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td>
                        <label for="required_crew">Number of crew required:</label>
                    </td>
                    <td>
                        <input type="text" id="required_crew" name="required_crew"/>
                    </td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td>
                        <label for="max_passenger">Maximum passenger capacity:</label>
                    </td>
                    <td>
                        <input type="text" id="max_passenger" name="max_passenger"/>
                    </td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td>
                        <label for="max_cargo">Maximum cargo capacity:</label>
                    </td>
                    <td>
                        <input type="text" id="max_cargo" name="max_cargo"/>
                    </td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        <button type="submit">Create Model</button>
                    </td>
                </tr>
            </table>
        </form>
<%@include file="/WEB-INF/jspf/page_end.jspf" %>
