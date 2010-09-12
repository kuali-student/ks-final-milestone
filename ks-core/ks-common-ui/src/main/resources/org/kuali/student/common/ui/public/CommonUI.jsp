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

<%@page import="org.kuali.student.common.ui.server.messages.MessageRPCPreloader"%>
<%@page	import="org.kuali.student.common.ui.server.dictionary.DictionaryRPCPreloader"%>
<%@page	import="org.kuali.student.common.ui.client.dictionary.DictionaryHelper"%>
<!-- <%@page	import="org.kuali.student.common.ui.server.dictionary.CommonDictionaryServiceImpl"%> -->
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.kuali.student.core.messages.service.impl.MessageServiceMock"%>
<%@page import="java.util.ArrayList"%>
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


try {
//MessageRPCPreloader messageRPCPreloader = new MessageRPCPreloader();
//   String messageData = messageRPCPreloader.getMessagesByGroupsEncodingString("en", new String[]{"common"});

DictionaryRPCPreloader preloader = new DictionaryRPCPreloader("luClient");
//preloader.setDictionaryService(new CommonDictionaryServiceImpl());
Map structures = new HashMap();

String objectTypes = preloader.getObjectTypesEncodedString();

String[] keys = preloader.getObjectTypes();

for (int i = 0; i < keys.length; i++) {
    String key = keys[i];
    String s = preloader.getObjectStructureEncodedString(key);
    structures.put(key, s);

}
MessageRPCPreloader mesageRPCPreloader = new MessageRPCPreloader();

//This method of setting message service is only here for testing common-ui, normally this would be spring
//injected or obtained from a service bus.
MessageServiceMock messageService = new MessageServiceMock();
ArrayList fileList = new ArrayList();
fileList.add("classpath:org\\kuali\\student\\common\\ui\\gwt-messages.xml");
messageService.setMessageFiles(fileList);
mesageRPCPreloader.setMessageService(messageService);

String messageData = mesageRPCPreloader.getMessagesByGroupsEncodingString("en",new String[]{"common"});

%>

<script type="text/javascript"> 
<!--    var i18nMessages = '<%=messageData%>'; -->
   var objectTypes = '<%=objectTypes%>';

   <%
   for(int j=0; j<keys.length; j++) {           
       String newKey = DictionaryHelper.buildJavaScriptKey(keys[j]);
   %>
       var <%=newKey.toString()%> = '<%=structures.get(keys[j])%>';
    <% 
  } 
%>
</script>
<%
} catch (Exception e) {

   e.printStackTrace();
}
%>
<%
 MessageRPCPreloader mesageRPCPreloader = new MessageRPCPreloader();
 
 //This method of setting message service is only here for testing common-ui, normally this would be spring
 //injected or obtained from a service bus.
 MessageServiceMock messageService = new MessageServiceMock();
 ArrayList fileList = new ArrayList();
 fileList.add("classpath:org\\kuali\\student\\common\\ui\\gwt-messages.xml");
 messageService.setMessageFiles(fileList);
 mesageRPCPreloader.setMessageService(messageService);
  
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
   		<div id="applicationPanel" style="height: 100%; width: 100%; overflow: auto">
		</div>
   		
	</body>
</html>
