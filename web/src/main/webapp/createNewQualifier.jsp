<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@page import="org.kuali.student.authz.dto.QualifierHierarchyDTO"%>
<A href="index.jsp">Back</A><br/>
Create New Qualifier<br/>

<form action="TreeViewServlet?action=createNewQualifier" METHOD="POST">
<input type="hidden" name="qParentId" value="${qParentId}">
Name: <input type="text" name="qName" value="${qName}"><br/>
<%java.util.List<QualifierHierarchyDTO> qualifierHierarchies = (java.util.List<QualifierHierarchyDTO>)request.getAttribute("qualifierHierarchies");
if(qualifierHierarchies.size()==1){ %>

<c:forEach var="qualifierHierarchy" items="${qualifierHierarchies}">
	Hierarchy: ${qualifierHierarchy.name}
	<input type="hidden" name="qhId" value="${qualifierHierarchy.id}"><br/>
	Qualifier Type:
	<select name="qtId" onChange="form.submit()">
	<%--<c:if test="${empty qtId}">--%>
		<option>Choose Qualifier Type</option>
	<%--</c:if>--%>
	<c:forEach var="qualifierType" items="${qualifierHierarchy.qualifierTypes}">
		<c:set var="selectedFlag" scope="page" value=""/>
		<c:if test="${qtId eq qualifierType.id}">
			<c:set var="selectedQualifierType" scope="page" value="${qualifierType}"/>
			<c:set var="selectedFlag" scope="page" value="selected"/>
		</c:if>
		<option value="${qualifierType.id}" ${selectedFlag}>${qualifierType.name}</option>
	</c:forEach>
	</select><br/>

	<%-- If the selected qualifier is a composite key add in the key value pairs:--%>
	<c:forEach var="pkQualifierType" items="${selectedQualifierType.pkQualifierTypes}">
		<input type="hidden" name="pkIds" value="${pkQualifierType.id}">
		${pkQualifierType.name}
		<input type="text" name="pkValues"><br/>
	</c:forEach>
</c:forEach>

<%}else{%>


	Hierarchy: 
	<select name="qhId" onChange="this.form.submit()">
	<c:if test="${empty qhId}">
		<option>Choose Qualifier Hierarchy</option>
	</c:if>
	<c:forEach var="qualifierHierarchy" items="${qualifierHierarchies}">
		<c:set var="selectedFlag" scope="page" value=""/>
		<c:if test="${qhId eq qualifierHierarchy.id}">
			<c:set var="selectedQualifierHierarchy" scope="page" value="${qualifierHierarchy}"/>
			<c:set var="selectedFlag" scope="page" value="selected"/>
		</c:if>
		<option value="${qualifierHierarchy.id}" ${selectedFlag}>${qualifierHierarchy.name}</option>
	</c:forEach>
	</select><br/>

	Qualifier Type:
	<select name="qtId" onChange="this.form.submit()">
	<%--<c:if test="${empty qtId}">--%>
		<option>Choose Qualifier Type</option>
	<%--</c:if>--%>
	<c:forEach var="qualifierType" items="${selectedQualifierHierarchy.qualifierTypes}">
		<c:set var="selectedFlag" scope="page" value=""/>
		<c:if test="${qtId eq qualifierType.id}">
			<c:set var="selectedQualifierType" scope="page" value="${qualifierType}"/>
			<c:set var="selectedFlag" scope="page" value="selected"/>
		</c:if>
		<option value="${qualifierType.id}" ${selectedFlag}>${qualifierType.name}</option>
	</c:forEach>
	</select><br/>
	<%-- If the selected qualifier is a composite key add in the key value pairs:--%>
	<c:forEach var="pkQualifierType" items="${selectedQualifierType.pkQualifierTypes}">
		<input type="hidden" name="pkIds" value="${pkQualifierType.id}">
		${pkQualifierType.name}
		<input type="text" name="pkValues"><br/>
	</c:forEach>

<%} %>
<br/>
<input type="submit" name="create" value="Create">
</form>