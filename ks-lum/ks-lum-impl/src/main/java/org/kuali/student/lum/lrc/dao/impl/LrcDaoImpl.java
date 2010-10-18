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

package org.kuali.student.lum.lrc.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.kuali.student.core.dao.impl.AbstractSearchableCrudDaoImpl;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.lum.lrc.dao.LrcDao;
import org.kuali.student.lum.lrc.entity.ResultComponentType;

public class LrcDaoImpl extends AbstractSearchableCrudDaoImpl implements LrcDao {
	@PersistenceContext(unitName = "Lrc")
	@Override
	public void setEm(EntityManager em) {
		super.setEm(em);
	}

	@Override
    public List<String> getResultComponentIdsByResult(String resultValueId, String resultComponentTypeKey) {
        Query query = em.createNamedQuery("ResultComponent.getResultComponentIdsByResult");
        query.setParameter("resultValueId", resultValueId);
        query.setParameter("resultComponentTypeKey", resultComponentTypeKey);
        @SuppressWarnings("unchecked")
        List<String> resultList = query.getResultList();
        return resultList;
    }

	@Override
    public List<String> getResultComponentIdsByResultComponentType(String resultComponentTypeKey) {
        Query query = em.createNamedQuery("ResultComponent.getResultComponentIdsByResultComponentType");
        query.setParameter("resultComponentTypeKey", resultComponentTypeKey);
        @SuppressWarnings("unchecked")
        List<String> resultList = query.getResultList();
        return resultList;
    }

	@Override
    public ResultComponentType getResultComponentType(String resultComponentTypeKey) throws DoesNotExistException {
        Query query = em.createNamedQuery("ResultComponent.getResultComponentType");
        query.setParameter("resultComponentTypeKey", resultComponentTypeKey);
        try {
            return (ResultComponentType)query.getSingleResult();
        } catch (NoResultException e) {
            throw new DoesNotExistException();
        } catch (EntityNotFoundException e){
        	throw new DoesNotExistException();
        }
    }
}
