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

package org.kuali.student.lum.lo.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.kuali.student.core.dao.impl.AbstractSearchableCrudDaoImpl;
import org.kuali.student.core.exceptions.DependentObjectsExistException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.UnsupportedActionException;
import org.kuali.student.lum.lo.dao.LoDao;
import org.kuali.student.lum.lo.entity.Lo;
import org.kuali.student.lum.lo.entity.LoCategory;
import org.kuali.student.lum.lo.entity.LoLoRelation;
import org.kuali.student.lum.lu.assembly.SingleUseLoInfoAssembler;

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
	 * @see org.kuali.student.lum.lo.dao.LoDao#addLoCategoryToLo(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean addLoCategoryToLo(String loCategoryId, String loId) throws UnsupportedActionException, DoesNotExistException {
		Lo lo = fetch(Lo.class, loId);
		LoCategory loCategory = fetch(LoCategory.class, loCategoryId);
		String loRepoId = lo.getLoRepository().getId();
		String loCategoryRepoId = loCategory.getLoRepository().getId();
		
		if ( ! loRepoId.equals(loCategoryRepoId) ) {
			throw new UnsupportedActionException("The learning objective category is not associated with the learning objective's repository");
		}
		lo.getCategories().add(loCategory);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.dao.LoDao#removeLoCategoryFromLo(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean removeLoCategoryFromLo(String loCategoryId, String loId) throws DoesNotExistException {
		Lo lo = fetch(Lo.class, loId);
		LoCategory loCategory = fetch(LoCategory.class, loCategoryId);
		lo.getCategories().remove(loCategory);
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
		if ( ! getIncludedLos(loId).isEmpty() ) {
			throw new DependentObjectsExistException("Lo(" +
													 loId+
													 ") cannot be deleted without orphaning child Lo(s).");
		}
		// TODO - will need more general logic here when we have relationships other than "includes"
		// hopefully dictionary-driven
		List<Lo> parents = getIncludingLos(loId);
		for (Lo parent : parents) {
			parent.getRelatedLos().remove(child);
			update(parent);
		}
		delete(Lo.class, loId);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.dao.LoDao#getLoChildren(java.lang.String)
	 */
	private List<Lo> getIncludedLos(String loId) throws DoesNotExistException {
		return getRelatedLosByLoId(loId, SingleUseLoInfoAssembler.INCLUDES_RELATION_TYPE); 
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.dao.LoDao#getLoParents(java.lang.String)
	 */
	private List<Lo> getIncludingLos(String loId) throws DoesNotExistException {
		return getLosByRelatedLoId(loId, SingleUseLoInfoAssembler.INCLUDES_RELATION_TYPE); 
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
	 * @see org.kuali.student.lum.lo.dao.LoDao#getLoCategories(java.lang.String)
	 */
	@Override
	public List<LoCategory> getLoCategories(String loRepositoryKey) {
		Query query = em.createNamedQuery("Lo.getLoCategories");
		query.setParameter("repositoryId", loRepositoryKey);
		@SuppressWarnings("unchecked")
		List<LoCategory> resultList = query.getResultList();
		return resultList;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.dao.LoDao#deleteLoCategory(java.lang.String)
	 */
	@Override
	public boolean deleteLoCategory(String loCategoryId) throws DoesNotExistException, DependentObjectsExistException {
		List<Lo> los = getLosByLoCategory(loCategoryId);
		if (null != los && !los.isEmpty()) {
			throw new DependentObjectsExistException("LoCategory(" + loCategoryId + ") still has " + los.size() + " Learning Objective(s) associated with it.");
		}
		delete(LoCategory.class, loCategoryId);
		return true;
	}

	@Override
	public List<Lo> getRelatedLosByLoId(String loId, String loLoRelationTypeId)
			throws DoesNotExistException {
		Query query = em.createNamedQuery("Lo.getRelatedLosByLoId");
		query.setParameter("loId", loId);
		query.setParameter("loLoRelationTypeId", loLoRelationTypeId);
		@SuppressWarnings("unchecked")
		List<Lo> resultList = query.getResultList();
		return resultList;
	}

	@Override
	public List<Lo> getLosByRelatedLoId(String relatedLoId,
			String loLoRelationTypeId) throws DoesNotExistException {
		Query query = em.createNamedQuery("Lo.getLosByRelatedLoId");
		query.setParameter("relatedLoId", relatedLoId);
		query.setParameter("loLoRelationTypeId", loLoRelationTypeId);
		@SuppressWarnings("unchecked")
		List<Lo> resultList = query.getResultList();
		return resultList;
	}

	@Override
	public void deleteLoLoRelation(String loLoRelationId) throws DoesNotExistException, DependentObjectsExistException {
		// make sure we don't orphan an LO (and potentially its children)
		LoLoRelation llRelation = fetch(LoLoRelation.class, loLoRelationId);
		if (getIncludingLos(llRelation.getRelatedLo().getId()).size() == 1) {
			// TODO - "&& [not a top-level LO for another CLU]" when LO's are reused
			throw new DependentObjectsExistException("LoLoRelation(" +
													 loLoRelationId +
													 ") cannot be deleted without orphaning Lo(s).");
		}
		delete(LoLoRelation.class, loLoRelationId);
	}

	@Override
	public List<LoCategory> getLoCategoriesForLo(String loId) {
		Query query = em.createNamedQuery("Lo.getLoCategoriesForLo");
		query.setParameter("loId", loId);
		// @SuppressWarnings("unchecked")
		List<LoCategory> resultList = query.getResultList();
		return resultList;
	}

	@Override
	public List<String> getAllowedLoLoRelationTypesForLoType(String loTypeKey, String relatedLoTypeKey) {
		Query query = em.createNamedQuery("Lo.getAllowedLoLoRelationTypes");
		query.setParameter("loTypeKey", loTypeKey);
		query.setParameter("relatedLoTypeKey", relatedLoTypeKey);
		@SuppressWarnings("unchecked")
		List<String> resultList = query.getResultList();
		return resultList;
	}

	@Override
	public List<Lo> getLosByRepository(String loRepositoryId) {
		Query query = em.createNamedQuery("Lo.getLosByRepository");
		query.setParameter("loRepositoryId", loRepositoryId);
		@SuppressWarnings("unchecked")
		List<Lo> resultList = query.getResultList();
		return resultList;
	}

	@Override
	public List<LoLoRelation> getLoLoRelationsByLoId(String loId) {
		Query query = em.createNamedQuery("Lo.getLoLoRelationsByLoId");
		query.setParameter("loId", loId);
		@SuppressWarnings("unchecked")
		List<LoLoRelation> resultList = query.getResultList();
		return resultList;
	}

}
