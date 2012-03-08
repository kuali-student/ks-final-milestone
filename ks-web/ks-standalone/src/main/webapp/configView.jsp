<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Properties" %>

<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page import="javax.servlet.http.HttpServletResponse" %>

<%@ page import="org.kuali.rice.core.config.ConfigContext" %>
<%@ page import="org.kuali.rice.core.config.ConfigLogger" %>
<%@ page import="org.kuali.rice.kew.engine.node.KeyValuePair" %>


<head>
<title>Config View</title>
</head>

<body>


<% 

List<KeyValuePair> activeConfigList = new ArrayList<KeyValuePair>();
Properties p = ConfigContext.getCurrentContextConfig().getProperties();
for(Object o: p.keySet()){
	String key = (String)o;
	String value = ConfigLogger.getDisplaySafeValue(key, p.getProperty(key));
	%>
	[<%=key%>] : <%=value%>}<br>
	
	<%			
}

%>
</body>