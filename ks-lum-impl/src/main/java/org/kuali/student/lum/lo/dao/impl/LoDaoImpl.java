/*
 * Copyright 2009 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kuali.student.lum.lo.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.kuali.student.core.dao.impl.AbstractSearchableCrudDaoImpl;
import org.kuali.student.lum.lo.dao.LoDao;
import org.kuali.student.lum.lo.entity.Lo;
import org.kuali.student.lum.lo.entity.LoCategory;

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
	public boolean addChildLoToLo(String loId, String parentLoId) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.dao.LoDao#addEquivalentLoToLo(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean addEquivalentLoToLo(String loId, String equivalentLoId) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.dao.LoDao#addLoCategoryToLo(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean addLoCategoryToLo(String loCategoryId, String loId) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.dao.LoDao#getAllDescendantLoIds(java.lang.String)
	 */
	@Override
	public List<String> getAllDescendantLoIds(String loId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.dao.LoDao#getAncestors(java.lang.String)
	 */
	@Override
	public List<String> getAncestors(String loId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.dao.LoDao#getEquivalentLos(java.lang.String)
	 */
	@Override
	public List<Lo> getEquivalentLos(String loId) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.dao.LoDao#getLoEquivalents(java.lang.String)
	 */
	@Override
	public List<Lo> getLoEquivalents(String loId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.dao.LoDao#getLoParents(java.lang.String)
	 */
	@Override
	public List<Lo> getLoParents(String loId) {
		// TODO Auto-generated method stub
		return null;
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
	 * @see org.kuali.student.lum.lo.dao.LoDao#isEquivalent(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean isEquivalent(String loId, String equivalentLoId) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.dao.LoDao#removeChildLoFromLo(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean removeChildLoFromLo(String loId, String parentLoId) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.dao.LoDao#removeEquivalentLoFromLo(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean removeEquivalentLoFromLo(String loId, String equivalentLoId) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.dao.LoDao#removeLoCategoryFromLo(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean removeLoCategoryFromLo(String loCategoryId, String loId) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.dao.LoDao#getLoCategories(java.lang.String)
	 */
	@Override
	public List<LoCategory> getLoCategories(String loHierarchyKey) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.dao.LoDao#isDescendant(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean isDescendant(String loId, String descendantLoId) {
		// TODO Auto-generated method stub
		return false;
	}
}
