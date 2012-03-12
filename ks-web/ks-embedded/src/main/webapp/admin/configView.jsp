<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Properties" %>

<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page import="javax.servlet.http.HttpServletResponse" %>

<%@ page import="org.kuali.rice.core.config.ConfigContext" %>
<%@ page import="org.kuali.rice.core.config.ConfigLogger" %>
<%@ page import="org.kuali.rice.kew.engine.node.KeyValuePair" %>

<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tld/displaytag.tld" prefix="display"%>

<html:html>
<head>
<title>Config View</title>

<link href="../css/screen.css" rel="stylesheet" type="text/css">
</head>

<body>
<% 
List<KeyValuePair> activeConfigList = new ArrayList<KeyValuePair>();
Properties p = ConfigContext.getCurrentContextConfig().getProperties();

for(Object o: p.keySet()){
	String key = (String)o;
    activeConfigList.add(new KeyValuePair(key,ConfigLogger.getDisplaySafeValue(key, p.getProperty(key))));
} 
pageContext.setAttribute("properties",activeConfigList);
%>

	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="contentBlock-title">
	<tr>
		<td width="20" height="20">&nbsp;</td>
		<td>
		<b>Configured Properties:</b> <%-- Table layout of the search results --%>
		<display:table excludedParams="*" class="bord-r-t"
			style="width:100%" cellspacing="0" cellpadding="0" name="${properties}" id="result" 
			defaultsort="1" defaultorder="ascending">
			<display:setProperty name="css.th.sorted" value="cellTableSortedHeader" />
			<display:setProperty name="css.th.sortable" value="cellTableSortableHeader" />
			<display:setProperty name="css.tr.even" value="cellTableEvenRow" />
			<display:setProperty name="css.tr.odd" value="cellTableOddRow" />
			<display:setProperty name="paging.banner.placement" value="both" />
			<display:setProperty name="paging.banner.all_items_found" value="" />
			<display:setProperty name="basic.msg.empty_list">No Configuration Found</display:setProperty>
			 <display:column class="cellTableCell contentBlock-desc" sortable="true" 
				title="<div>Config Key</div>">
				<c:out value="${result.key}" />&nbsp;
		     </display:column>
			 <display:column class="cellTableCell contentBlock-desc" sortable="true" 
				title="<div>Config Value</div>">
				<c:out value="${result.value}" />&nbsp;
		     </display:column>
		</display:table>
		</td>
		<td width="20" height="20">&nbsp;</td>
	</tr>
</table>

</body>
</html:html>