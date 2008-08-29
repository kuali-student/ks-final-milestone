<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<A href="index.jsp">Back</A><br/>
Search for Principals with Permissions and Qualifier:


<br/><a href="TreeViewServlet?action=viewQualifiers">Select Qualifier</a>

<c:if test="${!empty qualifier}">
<form action="TreeViewServlet?action=viewPrincipalsWithAuth" method="POST">
<input type="hidden" name="qid" value="${qid}">
<br/>Qualifier: ${qualifier.name}
<br/>Permissions:
<select name="permId">
<c:forEach var="permission" items="${permissions}">
	<option value="${permission.id}">${permission.name}</option>
</c:forEach>
</select>
<input type="submit" value="Search">
</form>
</c:if>