<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>

<A href="index.jsp">Back</A><br/>
Qualifier Hierarchies<br/>
<ul>
<c:forEach var="qualifierHierarchy" items="${qualifierHierarchies}">
	<li>${qualifierHierarchy.name}
		<ul>
			<c:forEach var="qualifierType" items="${qualifierHierarchy.qualifierTypes}">
				<li>${qualifierType.name}
					<ul>
						<c:forEach var="pkQualifierType" items="${qualifierType.pkQualifierTypes}">
							<li>${pkQualifierType.name}</li>
						</c:forEach>
					</ul>
				</li>
			</c:forEach>
		</ul>		
	</li>
</c:forEach>
</ul>
<form action="TreeViewServlet?action=createNewQualifierHierarchy" METHOD="POST">
Create New QualifierHierarchy <br/>
Name:<input type="text" name="qhName"> <input type="submit" id="submit">
</form>