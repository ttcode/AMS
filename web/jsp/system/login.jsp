<%-- 
    Document   : login
    Created on : 4 mai 2013, 08:40:54
    Author     : Jean
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en" dir="ltr">
    <head>
        <meta http-equiv="Content-Type" content="application/xhtml+xml; charset=UTF-8" />
	<%@include file="/WEB-INF/jspf/style.jspf" %>
	 <%@include file="/WEB-INF/jspf/js_inc.jspf" %>
	<link href="css/login.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript" src="js/login_script.js"></script>
	<title>Airline Management System</title>
    </head>
	<body>
		<%@include file="/WEB-INF/jspf/header.jspf" %>
		<%@include file="/WEB-INF/jspf/page_bar.jspf" %>
		<div id="body">
			<div class="col_center_450">
				<div class="header"><p>Authentication</p></div>
				<div id="login_alert"></div>
				<form method="post" >
					<table>
						<tr>
							<td>
								<label for="login">Login :</label>
							</td>
							<td>
								<input id="login" type="text" />
							</td>
						</tr>
						<tr>
							<td>
								<label for="password">Password :</label>
							</td>
							<td>
								<input id="password" type="password" />
							</td>
						</tr>
						<tr>
							<td colspan="2" style="text-align: center; padding-top: 30px;">
								<button type="submit" id="login-button">Log in</button>
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div class="clear"></div>
		</div>
		<%@include file="/WEB-INF/jspf/footer.jspf" %>
	</body>
</html>
