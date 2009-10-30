/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.lum.lo.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.kuali.student.core.dao.impl.AbstractSearchableCrudDaoImpl;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DependentObjectsExistException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.lum.lo.dao.LoDao;
import org.kuali.student.lum.lo.entity.Lo;
import org.kuali.student.lum.lo.entity.LoCategory;
import org.kuali.student.lum.lo.entity.LoHierarchy;

/**
 * @author Kuali Student Team
 *
 */
public class LoDaoImpl extends AbstractSearchableCrudDaoImpl implements LoDao {
	@PersistenceContext(unitName = "Lo")
	@Override
	public void setEm(EntityManager em) {
		super.setEm(em);
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.dao.LoDao#addChildLoToLo(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean addChildLoToLo(String loId, String parentLoId) throws DoesNotExistException, AlreadyExistsException {
		Lo parentLo;
		Lo childLo;
		
		if (isDescendant(parentLoId, loId)) {
			throw new AlreadyExistsException();
		}
		parentLo = fetch(Lo.class, parentLoId);
		childLo = fetch(Lo.class, loId);
		
		// TODO remove it as a child of any current parents
		
		// TODO or remove it as the root of its current hierearchy 
		
		// set its hierarchy to its new parent's
		childLo.setLoHierarchy(parentLo.getLoHierarchy());
		// and add it 
		parentLo.getChildLos().add(childLo);
		
		return true;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.dao.LoDao#addEquivalentLoToLo(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean addEquivalentLoToLo(String loId, String equivalentLoId) throws AlreadyExistsException, DoesNotExistException {
		Lo lo;
		Lo equivLo;
		
		lo = fetch(Lo.class, loId);
		equivLo = fetch(Lo.class, equivalentLoId);
		if (lo.getEquivalentLos().contains(equivLo)) {
			throw new AlreadyExistsException("Lo(" + equivalentLoId + ") is already equivalent to Lo(" + loId + ")");
		}
		lo.getEquivalentLos().add(equivLo);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.dao.LoDao#addLoCategoryToLo(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean addLoCategoryToLo(String loCategoryId, String loId) {
		// This actually entails adding it to the associated hierarchy
		Lo lo;
		LoCategory loCategory;
		try {
			lo = fetch(Lo.class, loId);
			loCategory = fetch(LoCategory.class, loCategoryId);
		} catch (DoesNotExistException e) {
			return false;
		}
		LoHierarchy hierarchy = lo.getLoHierarchy();
		hierarchy.getCategories().add(loCategory);
		loCategory.setLoHierarchy(hierarchy);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.dao.LoDao#removeLoCategoryFromLo(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean removeLoCategoryFromLo(String loCategoryId, String loId) {
		// This actually entails removing it from the associated hierarchy
		Lo lo;
		LoCategory loCategory;
		try {
			lo = fetch(Lo.class, loId);
			loCategory = fetch(LoCategory.class, loCategoryId);
		} catch (DoesNotExistException e) {
			return false;
		}
		LoHierarchy hierarchy = lo.getLoHierarchy();
		hierarchy.getCategories().remove(loCategory);
		loCategory.setLoHierarchy(null);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.dao.LoDao#getLoByIdList(java.util.List)
	 */
	@Override
	public List<Lo> getLoByIdList(List<String> loIds) {
		Query query = em.createNamedQuery("Lo.findLosByIdList");
		query.setParameter("idList", loIds);
		@SuppressWarnings("unchecked")
		List<Lo> resultList = query.getResultList();
		return resultList;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.dao.LoDao#deleteLo(java.lang.String)
	 */
	@Override
	public boolean deleteLo(String loId) throws DoesNotExistException, DependentObjectsExistException {
		Lo child = fetch(Lo.class, loId);
		if ( ! getLoChildren(loId).isEmpty() ) {
			throw new DependentObjectsExistException("Lo(" +
													 loId+
													 ") cannot be deleted without orphaning child Lo(s).");
		}
		List<Lo> parents = getLoParents(loId);
		for (Lo parent : parents) {
			parent.getChildLos().remove(child);
		}
		delete(Lo.class, loId);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.dao.LoDao#getLoCategoriesForLo(java.lang.String)
	 */
	@Override
	public List<LoCategory> getLoCategoriesForLo(String loId) {
		Query query = em.createNamedQuery("Lo.getLoCategoriesForLo");
		query.setParameter("loId", loId);
		@SuppressWarnings("unchecked")
		List<LoCategory> resultList = query.getResultList();
		return resultList;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.dao.LoDao#getLoChildren(java.lang.String)
	 */
	@Override
	public List<Lo> getLoChildren(String loId) {
		Query query = em.createNamedQuery("Lo.getLoChildren");
		query.setParameter("parentId", loId);
		@SuppressWarnings("unchecked")
		List<Lo> los = query.getResultList();
		return los;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.dao.LoDao#getAllDescendantLoIds(java.lang.String)
	 */
	@Override
	public List<String> getAllDescendantLoIds(String loId) {
		Query query = em.createNamedQuery("Lo.getLoChildrenIds");
		query.setParameter("parentId", loId);
		return getAllLevels(query, "parentId", loId);
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.dao.LoDao#getAncestors(java.lang.String)
	 * 
	 * Get the id's of _all_ ancestors of the specified Lo
	 */
	@Override
	public List<String> getAncestors(String loId) {
		Query query = em.createNamedQuery("Lo.getAncestors");
		query.setParameter("childId", loId);
		return getAllLevels(query, "childId", loId);
	}

	/* Recurse a query */
	private List<String> getAllLevels(Query query, String paramName, String paramValue) {
		// Eliminate dup's by using a set
		Set<String> valSet = new TreeSet<String>();
		query.setParameter(paramName, paramValue);
		@SuppressWarnings("unchecked")
		List<String> nextLevelList = query.getResultList();
		valSet.addAll(nextLevelList);
		for (String resultStr : nextLevelList) {
			valSet.addAll(getAllLevels(query, paramName, resultStr));
		}
		return new ArrayList<String>(valSet);
	}


	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.dao.LoDao#getLoEquivalents(java.lang.String)
     * Retrieves all learning objectives that have an equivalence reference to the specified LO.
     * Note: Equivalency of learning objectives is uni-directional, and we're navigating to those
     * LO's pointing to loId's
	 */
	@Override
	public List<Lo> getLoEquivalents(String loId) {
		Query query = em.createNamedQuery("Lo.getLoEquivalents");
		query.setParameter("loId", loId);
		@SuppressWarnings("unchecked")
		List<Lo> los = query.getResultList();
		return los;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.dao.LoDao#getEquivalentLos(java.lang.String)
	 *     /** 
     * Retrieves all equivalent learning objectives of a learning objective.
     * Note: Equivalency of learning objectives is uni-directional, and we're navigating to those
     * LO's that loId's LO points to as equivalent
	 */
	@Override
	public List<Lo> getEquivalentLos(String loId) {
		Query query = em.createNamedQuery("Lo.getEquivalentLos");
		query.setParameter("loId", loId);
		@SuppressWarnings("unchecked")
		List<Lo> los = query.getResultList();
		return los;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.dao.LoDao#isEquivalent(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean isEquivalent(String equivLoId, String loId) {
		Query query = em.createNamedQuery("Lo.getEquivalentLosIds");
		query.setParameter("loId", loId);
		@SuppressWarnings("unchecked")
		List<String> losIds = query.getResultList();
		return losIds.contains(equivLoId);
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.dao.LoDao#getLoParents(java.lang.String)
	 */
	@Override
	public List<Lo> getLoParents(String loId) {
		Query query = em.createNamedQuery("Lo.getLoParents");
		query.setParameter("loChildId", loId);
		@SuppressWarnings("unchecked")
		List<Lo> resultList = query.getResultList();
		return resultList;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.dao.LoDao#getLosByLoCategory(java.lang.String)
	 */
	@Override
	public List<Lo> getLosByLoCategory(String loCategoryId) {
		Query query = em.createNamedQuery("Lo.getLosByLoCategory");
		query.setParameter("loCategoryId", loCategoryId);
		@SuppressWarnings("unchecked")
		List<Lo> resultList = query.getResultList();
		return resultList;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.dao.LoDao#removeChildLoFromLo(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean removeChildLoFromLo(String loId, String parentLoId) throws DependentObjectsExistException, DoesNotExistException {
		Lo parentLo = null;
		Lo lo = null;
		if (getLoParents(loId).size() <= 1) {
			throw new DependentObjectsExistException();
		}
		try {
			parentLo = fetch(Lo.class, parentLoId);
			lo = fetch(Lo.class, loId);
		} catch (DoesNotExistException e) {
			return false;
		}
		List<Lo> children = parentLo.getChildLos();
		int index = children.indexOf(lo);
		if (-1 == index) {
			throw new DoesNotExistException("Lo(" + loId + ") is not a child of Lo(" + parentLoId + ").");
		}
		children.remove(index);
		// TODO - null out hierarchy 
		return true;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.dao.LoDao#removeEquivalentLoFromLo(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean removeEquivalentLoFromLo(String loId, String equivalentLoId) {
		Lo lo = null;
		Lo equivLo = null;
		try {
			lo = fetch(Lo.class, loId);
			equivLo = fetch(Lo.class, equivalentLoId);
		} catch (DoesNotExistException e) {
			return false;
		}
		List<Lo> equivs = lo.getEquivalentLos();
		int index = equivs.indexOf(equivLo);
		equivs.remove(index);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.dao.LoDao#getLoCategories(java.lang.String)
	 */
	@Override
	public List<LoCategory> getLoCategories(String loHierarchyKey) {
		Query query = em.createNamedQuery("Lo.getLoCategories");
		query.setParameter("hierarchyId", loHierarchyKey);
		@SuppressWarnings("unchecked")
		List<LoCategory> resultList = query.getResultList();
		return resultList;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.dao.LoDao#isDescendant(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean isDescendant(String loId, String descendantLoId) {
		List<Lo> los = getLoChildren(loId);
		Lo child = null;
		try {
			child = fetch(Lo.class, descendantLoId);
		} catch (DoesNotExistException e) {
			return false;
		}
		return los.contains(child);
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.dao.LoDao#deleteLoCategory(java.lang.String)
	 */
	@Override
	public boolean deleteLoCategory(String loCategoryId) throws DoesNotExistException, DependentObjectsExistException {
		List<Lo> los = getLosByLoCategory(loCategoryId);
		if (null != los & ! los.isEmpty()) {
			throw new DependentObjectsExistException("LoCategory(" + loCategoryId + ") still has " + los.size() + " Learning Objective(s) associated with it.");
		}
		delete(LoCategory.class, loCategoryId);
		return true;
	}
}
