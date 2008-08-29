<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<A href="index.jsp">Back</A><br/>
Available Principals: <br/>
<UL>
<c:forEach var="principal" items="${principals}">
	<LI>
	<A href="TreeViewServlet?action=viewPrincipal&pid=${principal.id}">${principal.name}</A>
	</LI>
</c:forEach>
</UL>
<form action="TreeViewServlet?action=addPrincipal" METHOD="POST">
Create Principal: <input type="text" name="prName"> <input type="submit" name="AddPrincipal">
</form>