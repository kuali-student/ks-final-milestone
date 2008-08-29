package org.kuali.student.authz.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.kuali.student.authz.entity.Authorization;
import org.kuali.student.authz.entity.Permission;
import org.kuali.student.authz.entity.Principal;
import org.kuali.student.authz.entity.Qualifier;
import org.kuali.student.authz.entity.QualifierHierarchy;
import org.kuali.student.authz.entity.QualifierType;
import org.kuali.student.authz.entity.Role;

public class AuthorizationDAOImpl implements AuthorizationDAO {

    private static final Logger LOG = Logger.getLogger(AuthorizationDAOImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public String createAuthorization(Authorization authorization) {
        entityManager.persist(authorization);
        return authorization.getId();
    }

    @Override
    public void deleteAuthorization(String id) {
        entityManager.remove(findAuthorizationById(id));
    }

    @Override
    public Authorization findAuthorizationById(String id) {
        return entityManager.find(Authorization.class, id);
    }

    @Override
    public List<Authorization> findAuthorizationByPrincipalId(String principalId) {
        Query query = entityManager.createQuery(
                "select a from Authorization a " + 
                "where a.principal.id = ?1");
        query.setParameter(1, principalId);
        
        return query.getResultList();
    }

    @Override
    public Authorization updateAuthorization(Authorization authorization) {
        return entityManager.merge(authorization);
    }

    @Override
    public boolean hasPermission(String principalId, String permissionId){
        // Find authorizations for principal for given permission
        Query query = entityManager.createQuery(
            "select a from Authorization a join a.qualifiers q " + 
            "where a.principal.id = ?1 and " + 
            "a.role in (select r from Role r join r.permissions p where p.id = ?2)");
        query.setParameter(1, principalId);
        query.setParameter(2, permissionId);

        return query.getResultList().size() > 0;
    }
       
    @Override
    public boolean hasPermission(Qualifier qualifier, String principalId, String permissionId) {
        
        // Find authorizations for principal for given permission
        Query query = entityManager.createQuery(
            "select a from Authorization a join a.qualifier q " + 
            "where a.principal.id = ?1 and " + 
            "a.role in (select r from Role r join r.permissions p where p.id = ?2)");
        query.setParameter(1, principalId);
        query.setParameter(2, permissionId);
                   
        List <Authorization> authorizations = query.getResultList();
               
        // Find any parent qualifiers that would inherit this qualifier (including this qualifier)
        // Took this query2 out from the for-loop below, if it breaks move it back.
        Query query2 = entityManager.createQuery(
            "select q from Qualifier q where q.leftVisit <= ?1 and q.rightVisit >= ?2 and q.qualifierHierarchy.id = ?3");
        
        // Some qualifiers may not belong to a hierarchy, just be rebels, loners
        List <Qualifier> qualifiers = new ArrayList<Qualifier>();
        if(qualifier.getQualifierHierarchy()!= null){
            query2.setParameter(1, qualifier.getLeftVisit());
            query2.setParameter(2, qualifier.getRightVisit());
            query2.setParameter(3, qualifier.getQualifierHierarchy().getId());
            
            qualifiers = query2.getResultList();
        } else {
            // Added this for the IF (descend tree) below. Without this else a qualifier with a null qualifierHierarchy
            // would return false even if it matches the authz qualifier. However this is redundant since a qualifier 
            // without a hierarchy should not have descend tree set.
            qualifiers.add(qualifier);
        }
        
        
        // Check if user authorization has required qualifiers
        for (Authorization authz:authorizations){
            
            if (authz.getDescendTree()){
                //User inherits all child qualifiers
                
                // Find any parent qualifiers that would inherit this qualifier (including this qualifier)
                // query2 was here
                if (qualifiers.contains(authz.getQualifier())){                         
                    return true;
                }
                            
            } else {
                //User doesn't inherit any qualifiers
                String givenAuthQualifierId = authz.getQualifier().getId();
                if (givenAuthQualifierId.equals(qualifier.getId())){
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public List search(String jpql, Object... args) {
        Query query = entityManager.createQuery(jpql);
        for (int i = 0; i < args.length; i++) {
            query.setParameter(i + 1, args[i]);
        }
        return query.getResultList();
    }

    private int walkTree(Qualifier node, QualifierHierarchy qualifierHierarchy, int count) {
        node.setLeftVisit(count++);
        if (node.getQualifiers() != null) {
            for (Qualifier qualifier : node.getQualifiers()) {
                count = walkTree(qualifier, qualifierHierarchy, count);
            }
        }
        node.setRightVisit(count++);
        node.setQualifierHierarchy(qualifierHierarchy); // this is a pseudo-namespace so we can store multiple trees in the same table, just restrict
        // based on the root id

        try {
            // okay, this is weird. hibernate seems to allow a persist operation even when the object already exists
            // therefore this try/catch is unnecessary, but i'm putting it here, in case we switch jpas to one that does
            // throw
            entityManager.persist(node);
        } catch (EntityExistsException e) {
            entityManager.merge(node);
        }
        return count;
    }

    public String createQualifierType(QualifierType qualfierType) {

        try {
            entityManager.persist(qualfierType);
        } catch (EntityExistsException e) {
            entityManager.merge(qualfierType);
        }

        return qualfierType.getId();
    }

    @Override
    public String createQualifierTree(Qualifier root) {
        entityManager.persist(root); // do this to create id for root
        //String id = root.getId();
        QualifierHierarchy qualifierHierarchy = root.getQualifierHierarchy();
        
        walkTree(root, qualifierHierarchy, 0);
        
        String id = root.getId();
        return id;
    }

    @Override
    public Qualifier deleteQualifierNode(Qualifier node) {
        // if(node.getRightVisit()-node.getLeftVisit() != 1)
        // throw new UnsupportedOperationException("yeah, i don't do non-leaves currently");

        Qualifier root = node;
        if (root.getParent() == null) {
            if (root.getQualifiers() != null && root.getQualifiers().size() != 9)
                throw new UnsupportedOperationException("not sure how to remove a root node with children");
            entityManager.remove(node);
            return null;
        }
        for (root = node; node.getParent() != null; node.getParent())
            ;

        List<Qualifier> nodes = node.getParent().getQualifiers();
        if (nodes != null)
            nodes.remove(node); // just to make sure

        // take the node's children and add them to the parent. maybe this operation should be unsupported?
        List<Qualifier> qualifiers = node.getQualifiers();
        if (qualifiers != null && qualifiers.size() != 0) {
            for (Qualifier qualifier : qualifiers) {
                qualifier.setParent(node.getParent());
                node.getParent().getQualifiers().add(qualifier);
            }
            node.getQualifiers().clear();
        }

        entityManager.remove(node); // remove

        walkTree(root, root.getQualifierHierarchy(), 0); // reindex

        return root;
    }

    @Override
    public void deleteQualifierTree(Qualifier root) {
        if (root.getParent() != null)
            throw new RuntimeException("Not the root element, genius");

        delTree(root);
    }

    private void delTree(Qualifier node) {
        if (node.getQualifiers() != null) {
            for (Qualifier qualifier : node.getQualifiers()) {
                delTree(qualifier);
            }
        }

        entityManager.remove(node);
    }

    @Override
    public Qualifier updateQualifierTree(Qualifier root) {
        QualifierHierarchy qualifierHierarchy = root.getQualifierHierarchy();
        if (qualifierHierarchy == null)
            throw new RuntimeException("bad monkey, no banana. this shuold've been a createTree() operation");

        walkTree(root, qualifierHierarchy, 0);

        return root;
    }

    @Override
	public List<Principal> findPrincipalsAtOrAboveQualifierWithPermissions(
			String qualifierId, String permissionId){
    	Permission permission = fetchPermission(permissionId);
    	Qualifier qualifier = fetchQualifier(qualifierId);
    	
    	Query query = entityManager.createQuery("SELECT DISTINCT p FROM Principal p, IN(p.authorizations) a, IN(a.role.permissions) perm " +
    			                                "  WHERE perm = :permission " +
    			                                "    AND" +
    			                                "     ( " +
    			                                "      ( " +
    			                                "        a.descendTree = TRUE " +
    			                                "        AND a.qualifier.leftVisit <= :leftVisit " +
    			                                "        AND a.qualifier.rightVisit >= :rightVisit" +
    			                                "      ) " +
    			                                "      OR " +
    			                                "      ( " +
    			                                "        a.descendTree = FALSE " +
    			                                "        AND a.qualifier.id = :qid " +
    			                                "      ) " +
    			                                "    ) " +
    			                                "    AND a.qualifier.qualifierHierarchy = :qualifierHierarchy");
        query.setParameter("permission", permission);
        query.setParameter("leftVisit", qualifier.getLeftVisit());
        query.setParameter("rightVisit", qualifier.getRightVisit());
        query.setParameter("qualifierHierarchy", qualifier.getQualifierHierarchy());
        query.setParameter("qid", qualifier.getId());
        return query.getResultList();
    }
    
    @Override
    public List<Principal> findPrincipalsAtOrAboveQualifier(Qualifier qualifier, Role role) {
        Query query = entityManager.createQuery("select p from Principal p join p.authorizations a join a.qualifier q where a.role = ?1 and q in (select t from Qualifier t where t.leftVisit <= ?2 and t.rightVisit >= ?3 and t.qualifierHierarchy.id = ?4)");
        query.setParameter(1, role);
        query.setParameter(2, qualifier.getLeftVisit());
        query.setParameter(3, qualifier.getRightVisit());
        query.setParameter(4, qualifier.getQualifierHierarchy().getId());
        return query.getResultList();
    }

    @Override
    public List<Principal> findPrincipalsAtQualifier(Qualifier qualifier, Role role) {
        Query query = entityManager.createQuery("select p from Principal p join p.authorizations a join a.qualifier q where a.role = ?1 and q = ?2");
        query.setParameter(1, role);
        query.setParameter(2, qualifier);
        return query.getResultList();
    }

    public Qualifier findQualifierByType(String qualifierType, String qualifierName) {
        Query query = entityManager.createQuery("select q from Qualifier q join q.qualifierType qt where qt.name = ?1 and q.name=?2");
        query.setParameter(1, qualifierType);
        query.setParameter(2, qualifierName);

        try {
            return (Qualifier) query.getSingleResult();
        } catch (NoResultException nre) {
            // TODO: Handle this exception
            return null;
        }
    }
    
    public Qualifier findCompositeQualifierByType(Map<String, String> qualifications) {
        int counter = 1;
        String qualifierFromClause = "";
        String qualifierWhereClause = "";
        String qualifierNames = "";
        String qualifierTypes = "";
        
        for (String qType : qualifications.keySet()) {
            String qValue = qualifications.get(qType);
            qualifierFromClause += ", Qualifier q" + counter;
            qualifierWhereClause += "q.id = q" + counter + ".compositeQualifier and ";
            qualifierNames += " and q" + counter + ".name = '" + qValue + "'";
            qualifierTypes += ",'" + qType + "'";
            counter++;
        }
        
        // leaving this here in case we need to change it again - this took long.
        /*String jpql = "SELECT q FROM Qualifier q" + qualifierFromClause + " join q.qualifierType qt " +
        		"WHERE " + qualifierWhereClause + 
        		"q.qualifierType=qt.id" + qualifierNames + " and q.qualifierType = " +
        		"(select distinct qt.compositeQualifierType from QualifierType qt where composite=1 and name in (" + qualifierTypes.substring(1) + "))";
        System.err.println("MY STRING " + jpql);*/
        
        Query query = entityManager.createQuery(
                "SELECT q FROM Qualifier q" + qualifierFromClause + " join q.qualifierType qt " +
                "WHERE " +  qualifierWhereClause +
                "q.qualifierType=qt.id" + qualifierNames + " and q.qualifierType = " +
                "(select distinct qt.compositeQualifierType from QualifierType qt where composite=1 and name in (" + qualifierTypes.substring(1) + "))" );
        
        return (Qualifier) query.getSingleResult();
    }
    
    @Override
    public List<Qualifier> findRootQualifiers(String qhId) {
    	Query query; 
    	if(qhId==null||"".equals(qhId)){
			query = entityManager.createQuery("SELECT q FROM Qualifier q where q.parent.id IS NULL and q.compositeQualifier IS NULL");
		}else{
			query = entityManager.createQuery("SELECT q FROM Qualifier q where q.parent.id IS NULL and q.compositeQualifier IS NULL AND q.qualifierHierarchy.id = :qhId");
			query.setParameter("qhId", qhId);
		}
        try {
            return query.getResultList();
        } catch (NoResultException nre) {
            // TODO: Handle this exception
            return null;
        }
    }
    
    @Override
    public Qualifier fetchQualifier(String qualifierId) {
        return entityManager.find(Qualifier.class, qualifierId);
    }

    @Override
    public Permission fetchPermission(String permissionId) {
        return entityManager.find(Permission.class, permissionId);
    }

    @Override
    public Principal fetchPrincipal(String principalId) {
        return entityManager.find(Principal.class, principalId);
    }

    @Override
    public Role fetchRole(String roleId) {
        return entityManager.find(Role.class, roleId);
    }
    
    public List<String> getPrincipalIdsWithQualifiedRole(String roleId, Map<String, String> qualifications) {
        Role role = entityManager.find(Role.class, roleId);
        
        List<Qualifier> qualifiers = new ArrayList<Qualifier>();
        int counter = 2;
        String in = "";

        for (String qType : qualifications.keySet()) {
            try {
                String qValue = qualifications.get(qType);
                qualifiers.add(findQualifierByType(qType, qValue));
                
                in += " or q = ?"+(counter++);
            } catch (NoResultException nre) {
                // TODO: Handle this exception
            }
        }
        
        Query query = entityManager.createQuery("select p.id from Principal p join p.authorizations a join a.qualifiers q where a.role = ?1 and ("+in.substring(4)+")");
        query.setParameter(1, role);
        for(int i = 0; i < qualifiers.size(); i++) {
            query.setParameter(i+2, qualifiers.get(i));
        }
        
        @SuppressWarnings("unchecked")
        List<String> resultList = query.getResultList();
        
        return resultList;
    }

	@Override
	public List<Qualifier> findQualifierDirectDescendents(String qid, String qhId) {
		
		Query query;
		if(qhId==null||"".equals(qhId)){
			query = entityManager.createQuery("SELECT q FROM Qualifier q where q.parent.id = :qid");
		}else{
			query = entityManager.createQuery("SELECT q FROM Qualifier q where q.parent.id = :qid AND q.qualifierHierarchy.id = :qhId");
			query.setParameter("qhId", qhId);
		}
		query.setParameter("qid", qid);
        try {
            return query.getResultList();
        } catch (NoResultException nre) {
            // TODO: Handle this exception
            return null;
        }
	}

	@Override
	public List<Principal> findPrinciplals() {
		Query query = entityManager.createQuery("SELECT p FROM Principal p");
        try {
            return query.getResultList();
        } catch (NoResultException nre) {
            // TODO: Handle this exception
            return null;
        }
	}

	@Override
	public List<Role> findRoles() {
		Query query = entityManager.createQuery("SELECT r FROM Role r");
        try {
            return query.getResultList();
        } catch (NoResultException nre) {
            // TODO: Handle this exception
            return null;
        }
	}
	
	public List<Permission> findPermissionsByPrincipalId(String principalId){
	    List<Authorization> authorizations = findAuthorizationByPrincipalId(principalId);
	    
	    List<Permission> permissions = new ArrayList<Permission>();
	    
	    for(Authorization auth : authorizations){
	        Role role = auth.getRole();
	        permissions.addAll(role.getPermissions());
	    }
	    return permissions;
	}

	@Override
	public List<Permission> findPermissions() {
		Query query = entityManager.createQuery("SELECT perm FROM Permission perm");
        try {
            return query.getResultList();
        } catch (NoResultException nre) {
            // TODO: Handle this exception
            return null;
        }
	}

	@Override
	public QualifierType fetchQualifierType(String id) {
		return entityManager.find(QualifierType.class, id);
	}

	@Override
	public List<QualifierType> findQualifierTypes() {
		Query query = entityManager.createQuery("SELECT qt FROM QualifierType qt");
        try {
            return query.getResultList();
        } catch (NoResultException nre) {
            // TODO: Handle this exception
            return null;
        }
	}

	@Override
	public void createPermission(Permission permission) {
		entityManager.persist(permission);
	}
	
	@Override
    public String createRole(Role role) {
        entityManager.persist(role);
        return role.getId();
    }
	
	@Override
	public String createPrincipal(Principal principal) {
	    entityManager.persist(principal);
	    return principal.getId();
	}
	
	@Override
	public String createQualifierHierarchy(QualifierHierarchy qualifierHierarchy){
	    entityManager.persist(qualifierHierarchy);
	    return qualifierHierarchy.getId();
	}
	@Override
	public List<QualifierHierarchy> findQualifierHierarchies() {
		Query query = entityManager.createQuery("SELECT qh FROM QualifierHierarchy qh");
        try {
            return query.getResultList();
        } catch (NoResultException nre) {
            // TODO: Handle this exception
            return null;
        }
	}

	@Override
	public QualifierHierarchy fetchQualifierHierarchy(String id) {
        return entityManager.find(QualifierHierarchy.class, id);
	}

	
	
	/* (non-Javadoc)
	 * @see org.kuali.student.authz.dao.AuthorizationDAO#updateNestingForQualifier(org.kuali.student.authz.entity.Qualifier)
	 * 
	 */
	/**
	 * This method will update the nesting data for the given qualifier.  It assumes that the qualifier has already been persisted
	 * with a q.leftVisit = q.parent.rightVisit and q.rightVisit = q.leftVisit+1.
	 * It will add 2 to the nesting data qualifiers to the right of the qualifier.
	 */
	@Override
	public void updateNestingForQualifier(Qualifier q){
		if(q.getParent()==null){
			return;
		}
		String qhId = q.getQualifierHierarchy().getId();
		int parentRightVisit = q.getParent().getRightVisit();
		
		Query updateRightVisit = entityManager.createQuery("UPDATE Qualifier q " +
														   "   SET q.rightVisit = q.rightVisit + 2 " +
														   " WHERE q.qualifierHierarchy.id = :qhId " +
														   "   AND q.rightVisit >= :parentRightVisit " +
														   "   AND q.id != :qid");
		updateRightVisit.setParameter("qhId", qhId);
		updateRightVisit.setParameter("parentRightVisit", parentRightVisit);
		updateRightVisit.setParameter("qid", q.getId());
		
		Query updateLeftVisit = entityManager.createQuery("UPDATE Qualifier q " +
														  "   SET q.leftVisit = q.leftVisit + 2 " +
														  " WHERE q.qualifierHierarchy.id = :qhId " +
														  "   AND q.leftVisit > :parentRightVisit " +
														  "   AND q.id != :qid");
		updateLeftVisit.setParameter("qhId", qhId);
		updateLeftVisit.setParameter("parentRightVisit", parentRightVisit);
		updateLeftVisit.setParameter("qid", q.getId());
		
		updateRightVisit.executeUpdate();
		updateLeftVisit.executeUpdate();
		entityManager.flush();
	}
	
	/* (non-Javadoc)
	 * @see org.kuali.student.authz.dao.AuthorizationDAO#createQualifier(org.kuali.student.authz.entity.Qualifier)
	 */
	/**
	 * Inserts a Qualifier and sets it's nested set data
	 */
	@Override
	public String createQualifier(Qualifier q) {
		if(q.getParent()==null){
			q.setLeftVisit(1);
			q.setRightVisit(2);
		   
		} else {
			q.setLeftVisit(q.getParent().getRightVisit());
			q.setRightVisit(q.getParent().getRightVisit()+1);
		}
		entityManager.persist(q);
		entityManager.flush();    
	    return q.getId();
	}

    // This is only here for quick testing, so I don't have to deal with the web service test
    @Override
    public boolean isAuthorized(Principal principal, Permission permission, Map<String, String> qualifications) {

        boolean isAuthorized = false;

        Set<String> keysQT = qualifications.keySet();
        // More than one key represents a Composite Key
        if (keysQT.size() == 1){
            String qType = keysQT.iterator().next();
            String qValue = qualifications.get(qType);
            Qualifier qualifier = findQualifierByType(qType, qValue);
            isAuthorized = hasPermission(qualifier, principal.getId(), permission.getId()); 
            
            /*for (String qType : keysQT) {
                String qValue = qualifications.get(qType);
                Qualifier qualifier = findQualifierByType(qType, qValue);
                isAuthorized = hasPermission(qualifier, principal, permission);                
            }*/
            
        } else {
            Qualifier qualifier = findCompositeQualifierByType(qualifications);
            isAuthorized = hasPermission(qualifier, principal.getId(), permission.getId());
        }

        return isAuthorized;
    }
}
