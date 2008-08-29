<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<A href="index.jsp">Back</A><br/>
Create New Role<br/>

<form action="TreeViewServlet?action=createNewRole" METHOD="POST">
Role Name: <input type="text" name="rName" value="${rName}"><br/>
Permissions: 
		<select MULTIPLE name="permIds" size="10">
			<c:forEach var="permission" items="${permissions}">

				<option value="${permission.id}">${permission.name}</option>
			</c:forEach>
		</select><br/>
Qualifier Hierarchy:
	<select name="qhId" onChange="form.submit()">
		<option>Choose One:</option>
		<c:forEach var="qualifierHierarchy" items="${qualifierHierarchies}">
			<c:set var="selectedFlag" scope="page" value=""/>
			<c:if test="${qhId eq qualifierHierarchy.id}">
				<c:set var="selectedQualifierHierarchy" scope="page" value="${qualifierHierarchy}"/>
				<c:set var="selectedFlag" scope="page" value="selected"/>
			</c:if>
			<option value="${qualifierHierarchy.id}" ${selectedFlag}>${qualifierHierarchy.name}</option>
		</c:forEach>
	</select><br/>
<c:if test="${!empty selectedQualifierHierarchy}">	
	Qualifier Type:
	<select name="qtId">
		<c:forEach var="qualifierType" items="${selectedQualifierHierarchy.qualifierTypes}">
			<c:set var="selectedFlag" scope="page" value=""/>
			<c:if test="${qtId eq qualifierType.id}">
				<c:set var="selectedFlag" scope="page" value="selected"/>
			</c:if>
			<option value="${qualifierType.id}" ${selectedFlag}>${qualifierType.name}</option>
		</c:forEach>
	</select>
	<br/>
</c:if>
<c:if test="${empty selectedQualifierHierarchy}">	
	Qualifier Type:
	<select name="qtId">
		<c:forEach var="qualifierType" items="${qualifierTypes}">
			<c:set var="selectedFlag" scope="page" value=""/>
			<c:if test="${qtId eq qualifierType.id}">
				<c:set var="selectedFlag" scope="page" value="selected"/>
			</c:if>
			<option value="${qualifierType.id}" ${selectedFlag}>${qualifierType.name}</option>
		</c:forEach>
	</select>
	<br/>
</c:if>

<input type="submit" name="create" value="Create">
</form>