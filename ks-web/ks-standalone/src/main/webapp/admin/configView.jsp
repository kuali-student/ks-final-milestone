<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Collections" %>
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
<style>
#box-table-a{font-family:"Lucida Sans Unicode", "Lucida Grande", Sans-Serif;font-size:12px;width:480px;text-align:left;border-collapse:collapse;margin:20px;}
#box-table-a th{font-size:13px;font-weight:normal;background:#404039;border-top:4px solid #840000;border-bottom:1px solid #fff;color:#fff;padding:8px;}
#box-table-a td{background:#F5F5EB;border-bottom:1px solid #fff;color:#000000;border-top:1px solid transparent;padding:8px;}
#box-table-a tr:hover td{background:#d0dafd;color:#339;}
</style>

<body>

<table id="box-table-a" summary="KS Configurations">
<thead>
	<tr>
		<th scope="col">Config Key</th>
		<th scope="col">Config Value</th>
	</tr>
</thead>
<tbody>
<% 

List<KeyValuePair> activeConfigList = new ArrayList<KeyValuePair>();
Properties p = ConfigContext.getCurrentContextConfig().getProperties();
ArrayList al = new ArrayList(p.keySet());
Collections.sort(al);
for(Object o: al){
	String key = (String)o;
	String value = ConfigLogger.getDisplaySafeValue(key, p.getProperty(key));
	%>
	<tr>
		<td><%=key%></td> 
		<td><%=value%></td>
	</tr>
	
	<%			
}

%>
</tbody>
</table>

</body>