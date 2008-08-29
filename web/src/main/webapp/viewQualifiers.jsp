<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@page import="org.kuali.student.authz.dto.QualifierDTO"%>
<%@page import="org.kuali.student.authz.dto.RoleDTO"%>
<A href="index.jsp">Back</A><br/>
<a href="TreeViewServlet?action=viewQualifiers">All</a>  

<%
int i = 0;
java.util.List<QualifierDTO> breadcrumbs = (java.util.List<QualifierDTO>)session.getAttribute("breadcrumbs");
for(QualifierDTO q:breadcrumbs){
	if(i<breadcrumbs.size()-1){
		out.print("<a href=\"TreeViewServlet?action=viewQualifiers&qid=");
		out.print(java.net.URLEncoder.encode(q.getId(),"UTF-8"));
		out.print("&qname="+java.net.URLEncoder.encode(q.getName(),"UTF-8"));
		out.print("&level="+i);
		out.print("\">"+q.getName()+"</a>  ");
	}
	i++;
}
%>
<UL>
<%
java.util.List<QualifierDTO> qualifiers = (java.util.List<QualifierDTO>)request.getAttribute("qualifiers");

if(session.getAttribute("pid")==null||"".equals(session.getAttribute("pid"))){
	//add the create new qualifier here
	out.print("<a href=\"TreeViewServlet?action=createNewQualifier");
	if(breadcrumbs.size()>0){
		QualifierDTO q = breadcrumbs.get(breadcrumbs.size()-1);
		if(q!=null){
			out.print("&qParentId="+q.getId());
		}
	}
	out.print("\">Add New Qualifier </a>\n");
	//Add the search for Principals with this qualifier
	if(breadcrumbs.size()>0){
		QualifierDTO q = breadcrumbs.get(breadcrumbs.size()-1);
		if(q!=null){
			out.print("<br/><a href=\"TreeViewServlet?action=searchPrincipalsByAuth");
			out.print("&qid="+q.getId()+"\">Search for Principals with Authorization to this Qualifier</a>");
		}
	}
	
}

else if(breadcrumbs.size()>0){
	//Add the "add to Role button"
	if(request.getAttribute("role")!=null){
		RoleDTO role = (RoleDTO)request.getAttribute("role");
		out.print("Role: "+role.getName()+"Role QT:"+role.getQualifierType().getName()+"</br>\n");

		QualifierDTO q = breadcrumbs.get(breadcrumbs.size()-1);
		
		if(q!=null/*&&q.getQualifierType().getId().equals(role.getQualifierType().getId())*/){
			out.print("<a href=\"TreeViewServlet?action=addQualifierToAuth");
			out.print("&qid="+q.getId());
			//out.print("&qtype="+java.net.URLEncoder.encode(q.getQualifierType(),"UTF-8"));
			out.print("&qname="+java.net.URLEncoder.encode(q.getName(),"UTF-8"));
			out.print("\">Add "+q.getName()+" to Role</a>\n");
			//out.print("<br/>QT: "+(q.getQualifierType()==null?"null":q.getQualifierType().getName())+"\n");
		}
	}
}


for(QualifierDTO q:qualifiers){
	out.print("<li><a href=\"TreeViewServlet?action=viewQualifiers&qid=");
	out.print(java.net.URLEncoder.encode(q.getId(),"UTF-8"));
	out.print("&qname="+java.net.URLEncoder.encode(q.getName(),"UTF-8"));
	
	String qhName = "null QH";
	if(q.getQualifierHierarchy()!=null){
		qhName=q.getQualifierHierarchy().getName();
	}

	String qtName = "null QT";
	if(q.getQualifierType()!=null){
		qtName=q.getQualifierType().getName();
	}
	
	out.print("\">"+q.getName()+"</a> ("+qhName+" - "+qtName+") ["+q.getLeftVisit()+","+q.getRightVisit()+"] <ul>");
	
	for(java.util.Map.Entry<String,String> e:q.getPkQualifiers().entrySet()){
		out.print("<li>"+e.getValue()+"</li>");
	}
	
	out.print("</ul></li>\n");
}


%>
</UL>


<%--
<UL>
<c:forEach var="qualifier" items="${qualifiers}">
		<LI>
		<a href="TreeViewServlet?qname=${qualifier.name}&qtype=${qualifier.qualifierType}">${qualifier.name}</a>
		${qualifier.qualifiers}
		</LI>
	</c:forEach>
</UL>
--%>
