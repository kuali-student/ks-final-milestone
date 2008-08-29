<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>

<A href="index.jsp">Back</A>
Create Qualifier Hierarchy<br/>

<form action="TreeViewServlet" METHOD="POST">
Name:<input type="text" name="qhName" value="${qhName}">
<br/>
Qualifier Types:
<ul>
<c:forEach var="selectedQualifierType" items="${selectedQualifierTypes}">
	<li>
		${selectedQualifierType.name}<input type="hidden" name="qtIds" value="${selectedQualifierType.id}">
		<ul>
			<c:forEach var="pkQualifierType" items="${selectedQualifierType.pkQualifierTypes}">
				<li>${pkQualifierType.name}</li>
			</c:forEach>
		</ul>
	</li>
</c:forEach>
</ul>
Add Qualifier Type: <br/>
<select name="qtId">
	<c:forEach var="qualifierType" items="${qualifierTypes}">
		<option value="${qualifierType.id}">${qualifierType.name}</option>
	</c:forEach>	
</select>
<input type="submit" name="action" value="Add Qualifier To Hierarchy"><br/>
<input type="submit" name="action" value="Create Qualifier Hierarchy">
</form>