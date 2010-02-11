<%@ page import="org.kuali.student.web.RecordedSession" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
RecordedSession rs = (RecordedSession) session.getAttribute("recordedSession");
pageContext.setAttribute("rs",rs);
%>
<h4>Request Log for HttpSession: <%=session.getId()%></h4>
<table border=1>
<th>Sequence</th>
<th>Request</th>
<th>Params</th>
<c:forEach var="rr" items="${rs.recordedRequests}">
 <tr>
   <td>${rr.sequence}</td>
   <td>${rr.path}</td>
   <td>
     <table border=0>
       <c:forEach var="parameter" items="${rr.parameters}">
		 <tr>
		   <td align=right>${parameter.name}</td>
		   <td>=</td>
		   <td>${parameter.value}</td>
		 </tr>
       </c:forEach>
     </table>
   </td>
 </tr>
</c:forEach>
</table>

