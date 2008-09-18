<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>

<html>
<head>
<title>Document Loaded</title>
<body>
<h2>Document Loaded</h2>
<p>
Document ${document.routeHeaderId} was loaded.  You can perform the following actions:
</p>

<form action="CourseClientServlet" method="post">

<input type="hidden" name="documentId" value="${documentId}"/>
<input type="hidden" name="userName" value="${userName}"/>

<table>
<tr><td>Course: </td><td><c:out value="${course}"/> </td></tr>
<tr><td>Course Name: </td><td><c:out value="${name}"/> </td></tr>
<tr><td>Course Description: </td><td><c:out value="${description}"/></td></tr> 
<tr><td>Start Date: </td><td><c:out value="${startDate}"/></td></tr>
<tr><td>End Date: </td><td><c:out value="${endDate}"/></td></tr>
<tr><td>Grading Method: </td><td><c:out value="${gradeMethod}"/></td></tr>
<tr><td>Credits: </td><td><c:out value="${credits}"/></td></tr>
</table>

<c:if test="${document.approvalRequested}">
	<input type="submit" value="Approve" name="approve"/>
</c:if>

<c:if test="${document.acknowledgeRequested}">
	<input type="submit" value="Acknowledge" name="ack"/>
</c:if>

<c:if test="${document.approvalRequested}">
	<input type="submit" value="Disapprove" name="disapprove"/>
</c:if>

<c:if test="${document.approvalRequested}">
<p>
	<%
	String[] previousNodes = (String[])request.getAttribute("previousNodes");
	
	if (previousNodes.length > 0){
	%>
		Send back to a previous point in the workflow:<br>
		
		Re-route message:<br>
		<textarea rows="4" cols="50" name="message"/></textarea><br>
		
		<select name="previousNode">
		<% 
			for (String s : previousNodes) {
		%>
		    <option value="<%=s%>}"><%=s%>
		<% 
		  } 
		%>
		</select>
		<input type="submit" value="Reroute" name="reroute"/>
		<%
	}
	%>
</p>
</c:if> 
</form>
</body>
</html>