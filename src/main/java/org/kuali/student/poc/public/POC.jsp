<%@ page language="java" import="java.util.*, org.kuali.student.poc.server.login.gwt.*, org.kuali.student.poc.client.*" contentType="text/html" %>
<%@page import="org.kuali.student.poc.client.login.LoginCredentials"%>
<%
	// horrible hack to work around spring initialization error
	synchronized (this) {
		Boolean b = (Boolean) super.getServletContext().getAttribute("FIRST_HIT_FLAG");
		if (b != null && b.booleanValue()) {
		    // do nothing, continue on
		} else {
		    super.getServletContext().setAttribute("FIRST_HIT_FLAG", Boolean.TRUE);
		    try {
		        Thread.sleep(5000);
		    } catch (InterruptedException ie) {
		        Thread.interrupted();
		    }
		}
	}
%>
<html>
	<head>
	
		<!--                                           -->
		<!-- Any title is fine                         -->
		<!--                                           -->
		<title>Kuali Student: Proof of Concept</title>
 				 
		<!--                                           -->
		<!-- Use normal html, such as style            -->
		<!--                                           -->
		<style>
			body,td,a,div,.p{font-family:arial,sans-serif}
			div,td{color:#000000}
			a:link,.w,.w a:link{color:#0000cc}
			a:visited{color:#551a8b}
			a:active{color:#ff0000}
		</style>
	
<%
	String locale = request.getParameter("kslocale");
	locale = (locale == null) ? "" : locale.trim();
	if (locale.equals("")) {
	    LoginCredentials credentials = (LoginCredentials) session.getAttribute(LoginServiceImpl.CURRENT_LOGIN_SESSION_KEY);
	    if (credentials != null) {
	        locale = credentials.getLocale();
	    } else {
		    locale = Locale.getDefault().toString().substring(0,2);
		    System.out.println("USING DEFAULT LOCALE: " + locale);
		}
	}
%>
		<meta name="gwt:property" content="locale=<%= locale %>">

		<script type="text/javascript">
			var kualiStudentSettings = {
				locale: "<%= locale %>"
			};
		</script>
	
		<!--                                           -->
		<!-- This script loads your compiled module.   -->
		<!-- If you add any GWT meta tags, they must   -->
		<!-- be added before this line.                -->
		<!--                                           -->
		<script language='javascript' src='org.kuali.student.poc.POC.nocache.js'></script>
	</head>

	<!--                                           -->
	<!-- The body can have arbitrary html, or      -->
	<!-- you can leave the body empty if you want  -->
	<!-- to create a completely dynamic ui         -->
	<!--                                           -->
	<body>

		<!-- OPTIONAL: include this if you want history support -->
		<iframe src="javascript:''" id="__gwt_historyFrame" style="position:absolute;width:0;height:0;border:0"></iframe>


		<div id="loadingSpinner">
			<img src="images/loading.gif" />
			<b>Loading...</b>
		</div>
	</body>
</html>
