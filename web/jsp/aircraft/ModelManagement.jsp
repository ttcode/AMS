<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/WEB-INF/jspf/page_begin.jspf" %>
<script type="text/javascript" src="js/deleteModel_script.js"></script>

    <div class="alert" id="alert_box">

    </div>
    <div id="div_table" style="width: 750px; margin: 0 auto; margin-top: 20px; margin-bottom: 30px;">
        <form method="POST" onSubmit="return(submitDeleteModel());">
            <table class="body_table" style="text-align: center;">
                <thead>
                    <tr>
                        <th></th>
                        <th>Aircraft model name</th>
                        <th>Aircraft model type</th>
                        <th>Number of pilot required</th>
                        <th>Number of crew required</th>
                        <th>Maximum passenger capacity</th>
                        <th>Maximum cargo capacity</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="aircraftModel"items="${aircraftModelList}" >
                        <tr>
                            <td><input name="ModelManagement" type="checkbox" value="${aircraftModel.id}"/></td>
                            <td>${aircraftModel.aircraftModel}</td>
                            <td>${aircraftModel.aircraftType.typeName}</td>
                            <td>${aircraftModel.requiredPilot}</td>
                            <td>${aircraftModel.requiredCrew}</td>
                            <td>${aircraftModel.maxPassenger}</td>
                            <td>${aircraftModel.maxCargo}</td>
                            <td><a href="updateModel?id=${aircraftModel.id}"><img src="img/edit.png" alt="Update" /></a></td>
                        </tr>
                    </c:forEach>
                </tbody>
                <tfoot>
                    <tr>
                        <td colspan="5" align="center">
                            <button type="submit">Delete</button>
                        </td>
                    </form>
                        <td colspan="3" align="center">
                            <form method="POST" action="createModel">
                                <button type="submit">Create</button>
                            </form>
                        </td>
                    </tr>
                </tfoot>                                
            </table>
    </div>
<%@include file="/WEB-INF/jspf/page_end.jspf" %>