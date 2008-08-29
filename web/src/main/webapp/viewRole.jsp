<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
Role: ${role.name}<br/>

Permissions<br/>
<ul>
<c:forEach var="permission" items="${role.permissions}">
	<li>${permission.name}		
	</li>
</c:forEach>
</ul>

<c:if test="${empty qualifiers}">
	<a href="TreeViewServlet?action=viewQualifiers"/>Add Qualifier</a><br/>
</c:if>
<c:if test="${!empty qualifiers}">
Qualifier:
<UL>
<c:forEach var="qualifier" items="${qualifiers}">
	<LI>
	${qualifier.name}
	</LI>
</c:forEach>
</UL>
<form action="TreeViewServlet?action=assignAuth" method="POST">
<input type="hidden" name="pid" value="${pid}">
<input type="hidden" name="rid" value="${rid}">
<input type="checkbox" name="descendTree" value="true">Descends Tree<br/>
<input type="submit" value="Assign Authorization">
</form>
</c:if>
 