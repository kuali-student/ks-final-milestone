package org.kuali.student.core.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.kuali.student.core.dao.CrudDao;
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
