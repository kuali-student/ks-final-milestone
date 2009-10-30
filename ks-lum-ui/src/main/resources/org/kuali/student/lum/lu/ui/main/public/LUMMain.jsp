<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page	import="org.kuali.student.common.ui.server.messages.MessageRPCPreloader"%>
<%@page	import="org.kuali.student.common.ui.server.dictionary.DictionaryRPCPreloader"%>
<%@page	import="org.kuali.student.common.ui.client.dictionary.DictionaryHelper"%>
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
        String commonMessageData = messageRPCPreloader.getMessagesByGroupsEncodingString("en", new String[]{"common", "validation"});
        String lumMessageData = messageRPCPreloader.getMessagesByGroupsEncodingString("en", new String[]{"course"});

        DictionaryRPCPreloader preloader = new DictionaryRPCPreloader("{http://student.kuali.org/wsdl/lu}LuService");
        Map structures = new HashMap();

        String objectTypes = preloader.getObjectTypesEncodedString();

        String[] keys = preloader.getObjectTypes();

        for (int i = 0; i < keys.length; i++) {
            String key = keys[i];
            String s = preloader.getObjectStructureEncodedString(key);
            structures.put(key, s);

        }
%>
<script type="text/javascript"> 
	    var commonMessages = '<%=commonMessageData%>';
	    var lumMessages = '<%=lumMessageData%>';
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

<!-- OPTIONAL: include this if you want history support -->
<iframe src="javascript:''" id="__gwt_historyFrame"
	style="width: 0; height: 0; border: 0"> </iframe>

<script type="text/javascript" language="javascript"
	src="org.kuali.student.lum.lu.ui.main.LUMMain.nocache.js">
		</script>
</body>
</html>