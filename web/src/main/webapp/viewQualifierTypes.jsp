<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>

<A href="index.jsp">Back</A><br/>
Qualifier Types<br/>
<ul>
<c:forEach var="qualifierType" items="${qualifierTypes}">
	<li>${qualifierType.name}
		<ul>
			<c:forEach var="pkQualifierType" items="${qualifierType.pkQualifierTypes}">
				<li>${pkQualifierType.name}</li>
			</c:forEach>
		</ul>		
	</li>
</c:forEach>
</ul>
<form action="TreeViewServlet?action=createNewQualifierType" METHOD="POST">
Create New QualifierType <br/>
Name:<input type="text" name="qtName"> <input type="submit" id="submit">
</form>