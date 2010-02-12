<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="org.kuali.student.web.RecordedSession" %>

<%
RecordedSession rs = (RecordedSession) session.getAttribute("recordedSession");
pageContext.setAttribute("rs",rs);
%>
<h4>Request Log for HttpSession: <%=session.getId()%></h4>
<table border=1>
<th>Seq</th>
<th>Request</th>
<th>Params</th>
<c:forEach var="rr" items="${rs.recordedRequests}">
 <tr>
   <td align=center>${rr.sequence}</td>
   <td>${rr.path}</td>
   <td>
     <table border=0>
       <c:forEach var="parameter" items="${rr.parameters}">
		 <tr valign=top>
		   <td align=right><pre>${parameter.name}</pre></td>
		   <td><pre>=</pre></td>
		   <td><pre><c:out value="${parameter.value}" escapeXml="true"/></pre></td>
		 </tr>
       </c:forEach>
     </table>
   </td>
 </tr>
</c:forEach>
</table>

