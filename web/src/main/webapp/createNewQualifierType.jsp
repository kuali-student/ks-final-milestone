<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@page import="org.kuali.student.authz.dto.QualifierTypeDTO"%>
<A href="index.jsp">Back</A>
Create Qualifier Type<br/>

<form action="TreeViewServlet" METHOD="POST">
Name:<input type="text" name="qtName" value="${qtName}">
<br/>
Qualifier Composite Keys:
<ul>
<c:forEach var="selectedQualifierType" items="${selectedQualifierTypes}">
	<li>
		${selectedQualifierType.name}<input type="hidden" name="qtPkIds" value="${selectedQualifierType.id}">
	</li>
</c:forEach>
</ul>
<%java.util.List<QualifierTypeDTO> qualifierTypes = (java.util.List<QualifierTypeDTO>)request.getAttribute("qualifierTypes");
if(qualifierTypes!=null && qualifierTypes.size()>0) {%>
Add Composite Key Qualifier Type: <br/>
<select name="qtPkId">
	<c:forEach var="qualifierType" items="${qualifierTypes}">
		<option value="${qualifierType.id}">${qualifierType.name}</option>
	</c:forEach>	
</select>
<input type="submit" name="action" value="Add Key"><br/>
<%} %>
<input type="submit" name="action" value="Create Qualifier Type">
</form>