package org.kuali.student.authz.dao;

import java.util.List;
import java.util.Map;

import org.kuali.student.authz.entity.Authorization;
import org.kuali.student.authz.entity.Permission;
import org.kuali.student.authz.entity.Principal;
import org.kuali.student.authz.entity.Qualifier;
import org.kuali.student.authz.entity.QualifierType;
import org.kuali.student.authz.entity.QualifierHierarchy;
import org.kuali.student.authz.entity.Role;

public interface AuthorizationDAO {

    public String createAuthorization(Authorization authorization);
    
    public Authorization updateAuthorization(Authorization authorization);
    
    public Authorization findAuthorizationById(String id);
    
    public List<Authorization> findAuthorizationByPrincipalId(String id);
    
    public void deleteAuthorization(String id);
    
    public boolean hasPermission(String principalId, String permissionId);
        
    public boolean hasPermission(Qualifier qualifier, String principalId, String permissionId);
    
    public List<Object> search(String jpql, Object... args);
    
    public String createQualifierType(QualifierType qualifierType);
    
    public String createQualifierTree(Qualifier root);
    
    public Qualifier updateQualifierTree(Qualifier root);
    
    /**
     * This method deletes a node from a tree and returns the tree minus the node.
     * 
     * @param node
     * @return the tree minus the node, or null if the tree is a standalone
     */
    public Qualifier deleteQualifierNode(Qualifier node);
    
    public void deleteQualifierTree(Qualifier root);
    
    public List<Principal> findPrincipalsAtQualifier(Qualifier qualifier, Role role);
    
    public List<Principal> findPrincipalsAtOrAboveQualifier(Qualifier qualifier, Role role);
    
    public Qualifier findQualifierByType(String qualifierType, String qualifierName);
    
    public Qualifier findCompositeQualifierByType(Map<String, String> qualifications);
    
    public Qualifier fetchQualifier(String qualifierId);
    
    public Permission fetchPermission(String permissionId);
    
    public Principal fetchPrincipal(String principalId);
    
    public Role fetchRole(String roleId);
    
    public List<String> getPrincipalIdsWithQualifiedRole(String roleId, Map<String, String> qualifications);
    
    public List<Qualifier> findRootQualifiers(String qhId);

    public List<Qualifier> findQualifierDirectDescendents(String qid, String qhId);

	public List<Role> findRoles();

	public List<Principal> findPrinciplals();
	
	public List<Permission> findPermissionsByPrincipalId(String principalId);

	public List<Permission> findPermissions();

	public QualifierType fetchQualifierType(String id);

	public List<QualifierType> findQualifierTypes();

	public void createPermission(Permission permission);

	public List<QualifierHierarchy> findQualifierHierarchies();
	
	public String createRole(Role role);
	
	public String createPrincipal(Principal principal);
	
	public String createQualifierHierarchy(QualifierHierarchy qualifierHierarchy);

	public QualifierHierarchy fetchQualifierHierarchy(String id);

	public String createQualifier(Qualifier q);

	public List<Principal> findPrincipalsAtOrAboveQualifierWithPermissions(
			String qualifierId, String permissionId);

	void updateNestingForQualifier(Qualifier q);

	//The following methods should not be part of the interface

    public boolean isAuthorized(Principal principal, Permission permission, Map <String, String> qualifications);
}
