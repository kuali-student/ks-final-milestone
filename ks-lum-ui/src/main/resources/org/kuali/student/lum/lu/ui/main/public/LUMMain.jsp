<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page	import="org.kuali.student.common.ui.server.messages.MessageRPCPreloader"%>
<%@page	import="org.kuali.student.common.ui.server.dictionary.DictionaryRPCPreloader"%>
<%@page	import="org.kuali.student.lum.lu.ui.course.client.configuration.LUConstants"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>


<html>
<head>
<title>LUM Application</title>

</head>

<body>

<%
    try {
        MessageRPCPreloader messageRPCPreloader = new MessageRPCPreloader();
        String messageData = messageRPCPreloader.getMessagesByGroupsEncodingString("en", new String[]{"common"});

        DictionaryRPCPreloader preloader = new DictionaryRPCPreloader("luClient");
        Map structures = new HashMap();

        //TODO: Nice to be able to read this from dictionary config instead of a constant
        
        for(int i=0; i<LUConstants.DICTIONARY_OBJECT_KEYS.length; i++) {
            String key = LUConstants.DICTIONARY_OBJECT_KEYS[i];
            String s = preloader.getObjectStructureEncodedString(key);
            structures.put(key, s);
        }
%>
	<script type="text/javascript"> 
	 var i18nMessages = '<%=messageData%>';
	 
	 <%
     for(int j=0; j<LUConstants.DICTIONARY_OBJECT_KEYS.length; j++) {
         String key = LUConstants.DICTIONARY_OBJECT_KEYS[j];        
     %>
         var <%=key%> = '<%=structures.get(key)%>';
     <% 
       } 
     %>
	</script>
<%
    } catch (Exception e) {

        e.printStackTrace();
    }
%>

<!-- OPTIONAL: include this if you want history support -->
<iframe src="javascript:''" id="__gwt_historyFrame"
	style="width: 0; height: 0; border: 0"> </iframe>

<script type="text/javascript" language="javascript"
	src="org.kuali.student.lum.lu.ui.main.LUMMain.nocache.js">
		</script>
</body>
</html>