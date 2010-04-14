/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.core.role.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.kuali.student.core.role.dao.QualifierHierarchyDAO;
import org.kuali.student.core.role.entity.Qualifier;
import org.kuali.student.core.role.entity.QualifierHierarchy;
import org.kuali.student.core.role.entity.QualifierType;

public class QualifierHierarchyDAOImpl implements QualifierHierarchyDAO {

    //private static final Logger LOG = Logger.getLogger(QualifierHierarchyDAOImpl.class);

    @PersistenceContext(unitName = "Role")
    private EntityManager entityManager;


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

    public String createQualifierType(QualifierType qualfierType) {

        try {
            entityManager.persist(qualfierType);
        } catch (EntityExistsException e) {
            entityManager.merge(qualfierType);
        }

        return qualfierType.getId();
    }

    @Override
    public String createQualifierHierarchy(QualifierHierarchy qualifierHierarchy){
        entityManager.persist(qualifierHierarchy);
        return qualifierHierarchy.getId();
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
    public Qualifier updateQualifierTree(Qualifier root) {
        QualifierHierarchy qualifierHierarchy = root.getQualifierHierarchy();
        if (qualifierHierarchy == null)
            throw new RuntimeException("The Qualifier Hierarchy is not set for the root Qualifier : " + root.getName());

        walkTree(root, qualifierHierarchy, 0);

        return root;
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

    public Qualifier findQualifierByType(String qualifierType, String qualifierName) {
        Query query = entityManager.createQuery("select q from Qualifier q join q.qualifierType qt where qt.name = ?1 and q.name=?2");
        query.setParameter(1, qualifierType);
        query.setParameter(2, qualifierName);

        return (Qualifier) query.getSingleResult();
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

        @SuppressWarnings("unchecked")
    	List<Qualifier> resultList = query.getResultList();
		return resultList;
    }

    @Override
    public Qualifier fetchQualifier(String qualifierId) {
        return entityManager.find(Qualifier.class, qualifierId);
    }

    @Override
    public List<Qualifier> findQualifiersAtOrAboveQualifier(Qualifier qualifier) {
        Query query = entityManager.createQuery("select q from Qualifier q where q.leftVisit <= ?1 and q.rightVisit >= ?2 and q.qualifierHierarchy.id = ?3");
        query.setParameter(1, qualifier.getLeftVisit());
        query.setParameter(2, qualifier.getRightVisit());
        query.setParameter(3, qualifier.getQualifierHierarchy().getId());
        @SuppressWarnings("unchecked")
    	List<Qualifier> resultList = query.getResultList();
		return resultList;
    }

    @Override
    public List<Qualifier> findQualifiersAtOrBelowQualifier(Qualifier qualifier){
        Query query = entityManager.createQuery("select q from Qualifier q where q.leftVisit >= ?1 and q.rightVisit <= ?2 and q.qualifierHierarchy.id = ?3");
        query.setParameter(1, qualifier.getLeftVisit());
        query.setParameter(2, qualifier.getRightVisit());
        query.setParameter(3, qualifier.getQualifierHierarchy().getId());
        @SuppressWarnings("unchecked")
    	List<Qualifier> resultList = query.getResultList();
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
        @SuppressWarnings("unchecked")
    	List<Qualifier> resultList = query.getResultList();
		return resultList;
	}


	@Override
	public QualifierType fetchQualifierType(String id) {
		return entityManager.find(QualifierType.class, id);
	}

	@Override
	public List<QualifierType> findQualifierTypes() {
		Query query = entityManager.createQuery("SELECT qt FROM QualifierType qt");
        @SuppressWarnings("unchecked")
    	List<QualifierType> resultList = query.getResultList();
		return resultList;
	}


	@Override
	public List<QualifierHierarchy> findQualifierHierarchies() {
		Query query = entityManager.createQuery("SELECT qh FROM QualifierHierarchy qh");
        @SuppressWarnings("unchecked")
    	List<QualifierHierarchy> resultList = query.getResultList();
		return resultList;
	}

	@Override
	public QualifierHierarchy fetchQualifierHierarchyByName(String name) {
	    Query query = entityManager.createQuery("SELECT qh FROM QualifierHierarchy qh where qh.name = :qname");
	    query.setParameter("qname", name);

	    return (QualifierHierarchy) query.getSingleResult();
    }

	@Override
	public QualifierHierarchy fetchQualifierHierarchy(String id) {
        return entityManager.find(QualifierHierarchy.class, id);
	}


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

}
