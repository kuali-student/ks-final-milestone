<%@ page import="org.kuali.student.web.RecordedSession" %>
<%
RecordedSession rs = (RecordedSession) session.getAttribute("org.kuali.student.RECORDED_SESSTION");
out.println(rs);
%>

<table border=1>
<c:forEach var="rr" items="${rs.recordedRequests}">
 <tr><td>${rr.path}</td></tr>
</c:forEach>
</table>