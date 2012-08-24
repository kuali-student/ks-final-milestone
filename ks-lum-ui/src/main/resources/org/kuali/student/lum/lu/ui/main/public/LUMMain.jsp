<%--

    Copyright 2010 The Kuali Foundation Licensed under the
    Educational Community License, Version 2.0 (the "License"); you may
    not use this file except in compliance with the License. You may
    obtain a copy of the License at

    http://www.osedu.org/licenses/ECL-2.0

    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on an "AS IS"
    BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
    or implied. See the License for the specific language governing
    permissions and limitations under the License.

--%>

<%@page import="org.kuali.rice.core.api.config.property.ConfigContext"%>
<%
// We need to detect the browser in order to populate the correct DOCTYPE
String browser = request.getHeader("User-Agent");
if(browser.indexOf("MSIE") > 0) {
%>
	<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%}
else if(browser.indexOf("Chrome")> 0) {
%>
	<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
} else {
%>
	<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
}
%>

<%@page	import="org.kuali.student.common.ui.server.messages.MessageRPCPreloader"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>


<html>
<head>
<% if(browser.indexOf("MSIE") > 0) { %>
	<meta http-equiv="X-UA-Compatible" content="IE=8" />
<% } %>

<%
	// When running in a multi-server system, it's important to know
	// who you ar, your session id, and what machine you're using. 
	String user = null;
	String sessionId = null;
	String hostName = java.net.InetAddress.getLocalHost().getHostName();
	String hostIp = request.getLocalAddr();
	try{
		user = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getName();		
	}catch(NullPointerException ex){
		user = "null";
	}
	
	try{
		sessionId = request.getSession(false).getId();	
	}catch(NullPointerException ex){
		sessionId = "null";
	}
%>

<!-- 
Server Info:
 User Name:  <%= user %>
 Host Name:  <%= hostName %>
 Host IP:    <%= hostIp %>
 Session ID: <%= sessionId %>
 -->
 
 
</head>

<body>
<script type="text/javascript">
	var val = unescape(self.document.location.hash.substring(1));
	document.write('<input type="hidden" id="locationHash" value="'+val+'" >');
</script>

<%
    try {
        MessageRPCPreloader messageRPCPreloader = new MessageRPCPreloader();
        String commonMessageData = messageRPCPreloader.getMessagesByGroupsEncodingString("en", new String[]{"common", "validation"});
        String luMessageData = messageRPCPreloader.getMessagesByGroupsEncodingString("en", new String[]{"course", "program","clusetmanagement"});
		
%>
<script type="text/javascript"> 
	    var commonMessages = '<%=commonMessageData%>';
	    var luMessages = '<%=luMessageData%>';
	 
     	</script>
<%
    } catch (Exception e) {

        e.printStackTrace();
    }
%>

	<!-- OPTIONAL: include this if you want history support -->
	<iframe src="javascript:''" id="__gwt_historyFrame"
		style="width: 0; height: 0; border: 0"> </iframe>
		
		<% if("true".equalsIgnoreCase(ConfigContext.getCurrentContextConfig().getProperty("enableKSBackdoorLogin"))){ %>
			<!-- needs css switch included -->
			<div id="switchuser" style="display: block">
				<form action="../j_spring_security_switch_user" method="get">
					<input type="text" name="j_username" id="j_username"/> <input type="submit" value="Change User">
				</form>
			</div>
			<div id="switchback" style="display: none">
				<input type="button" value="go" onclick="document.location = "/j_spring_security_exit_user">
			</div>
		<% }%>
	<div id="applicationPanel" style="height: 100%; width: 100%; overflow: auto"></div>

	<script type="text/javascript" language="javascript"
		src="org.kuali.student.lum.lu.ui.main.LUMMain.nocache.js">
			</script>

</body>
</html>