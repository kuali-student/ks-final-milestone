

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="org.kuali.student.common.ui.server.messages.MessageRPCPreloader"%>
<html>
	<head>
	
		<!--                                           -->
		<!-- Any title is fine                         -->
		<!--                                           -->
		<title>Wrapper HTML for CoreUI</title>

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

		<!--                                           -->
		<!-- This script loads your compiled module.   -->
		<!-- If you add any GWT meta tags, they must   -->
		<!-- be added before this line.                -->
		<!--  	
		                                        -->
<%
 MessageRPCPreloader mesageRPCPreloader = new MessageRPCPreloader();
 String messageData = mesageRPCPreloader.getMessagesByGroupsEncodingString("en",new String[]{"common"});
%>

<script type="text/javascript">
 var i18nMessages = '<%= messageData %>';
</script>	
	</head>


	<!--                                           -->
	<!-- The body can have arbitrary html, or      -->
	<!-- you can leave the body empty if you want  -->
	<!-- to create a completely dynamic ui         -->
	<!--                                           -->
	<body>


		<!-- OPTIONAL: include this if you want history support -->
		<iframe src="javascript:''" id="__gwt_historyFrame" style="width:0;height:0;border:0"></iframe>
		
   		<script type="text/javascript" language="javascript" src="org.kuali.student.common.ui.CommonUITest.nocache.js"></script>
   		
   		
	</body>
</html>
