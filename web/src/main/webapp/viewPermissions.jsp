<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>

<A href="index.jsp">Back</A><br/>
Permissions<br/>
<ul>
<c:forEach var="permission" items="${permissions}">
	<li>${permission.name}		
	</li>
</c:forEach>
</ul>
<form action="TreeViewServlet?action=addPermission" METHOD="POST">
Create Permission: <input type="text" name="permName"> <input type="submit" id="submit">
</form>