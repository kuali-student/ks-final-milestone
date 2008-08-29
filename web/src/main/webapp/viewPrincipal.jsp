<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<a href="TreeViewServlet?action=viewPrincipals">Return to Principals</a><br/>
Principal:  ${principal.name}<br/>
Authorizations:
<UL>
<c:forEach var="qualifiedRole" items="${qualifiedRoles}">
	<LI>
	Role: ${qualifiedRole.name}<br/>
	Qualifier:${qualifiedRole.qualifier.name} Descends Tree:${qualifiedRole.descendTree}
	</LI>
</c:forEach>
</UL>

Add Authorization with Role:
<UL>
<c:forEach var="role" items="${roles}">
<LI><a href="TreeViewServlet?action=viewNewAuth&pid=${principal.id}&rid=${role.id}"/>${role.name}</a></LI>
</c:forEach>
</UL>