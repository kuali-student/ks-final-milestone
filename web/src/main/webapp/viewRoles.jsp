<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<A href="index.jsp">Back</A><br/>
Roles: <br/>
<table border="1" cellpadding="2" cellspacing="0" style="border-collapse:collapse">
	<tr>
		<th>Name</th>
		<th>Type</th>
		<th>Permissions</th>
		<th>Hierarchy</th>
	</tr>

<c:forEach var="role" items="${roles}">
	<tr>
		<td>${role.name}</td>
		<td>
			${role.qualifierType.name}
			<ul>
			<c:forEach var="pkQualifierType" items="${role.qualifierType.pkQualifierTypes}">
				<li>${pkQualifierType.name}</li>
			</c:forEach>
			</ul>
		</td>
		<td>
			<ul>
			<c:forEach var="permission" items="${role.permissions}">
				<li>${permission.name}</li>
			</c:forEach>
			</ul>
		</td>
		<td>${role.qualifierHierarchy.name}</td>
	</tr>
</c:forEach>

</table>

<form action="TreeViewServlet?action=createNewRole" METHOD="POST">
<input type="submit" value="Create New Role">
</form>