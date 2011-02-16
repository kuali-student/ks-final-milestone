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

package org.kuali.student.common.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.kuali.student.common.dao.CrudDao;
import org.kuali.student.core.exceptions.DoesNotExistException;

public abstract class AbstractCrudDaoImpl implements CrudDao {

	protected EntityManager em;

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}


	@Override
	public <T> T fetch(Class<T> clazz, String key) throws DoesNotExistException {
		T entity = em.find(clazz, key);
		if (entity == null) {
			throw new DoesNotExistException("No entity for key '" + key + "' found for " + clazz);
		}
		return entity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> find(Class<T> clazz){

		String className = clazz.getSimpleName();

		Query q = em.createQuery("SELECT x FROM "+className+" x");
		return (List<T>) q.getResultList();
	}

	@Override
	public <T> T create(T entity) {
		em.persist(entity);
		return entity;
	}

	@Override
	public <T> void delete(Class<T> clazz, String key) throws DoesNotExistException {
		T entity = em.find(clazz, key);
		if (entity == null) {
			throw new DoesNotExistException("No such key '" + key + "' for " + clazz);
		}
		em.remove(entity);
	}

	@Override
	public void delete(Object entity) {
		em.remove(entity);
	}

	@Override
	public <T> T update(T entity) {
		T updated =  em.merge(entity);
		//We need to flush here to make sure the version number is updated before we populate any messages that return the version
		em.flush();
		return updated;
	}
}
