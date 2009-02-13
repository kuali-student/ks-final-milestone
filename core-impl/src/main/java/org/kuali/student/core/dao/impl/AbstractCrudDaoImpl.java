package org.kuali.student.core.dao.impl;

import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.kuali.student.core.dao.CrudDao;
import org.kuali.student.core.entity.AttributeDef;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

public abstract class AbstractCrudDaoImpl implements CrudDao {

	protected EntityManager em;
	@Autowired
	private MessageSource messageSource;
	private Locale locale;

	public void setMessageSource (MessageSource messageSource) {
		this.messageSource = messageSource;
		locale = Locale.getDefault();
	}

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
			String msg = messageSource.getMessage("AbstractCrudDaoImpl.noEntityForKey", new Object[] {key, clazz}, locale);
			throw new DoesNotExistException(msg);
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

	@SuppressWarnings("unchecked")
	@Override
	public <T extends AttributeDef> T fetchAttributeDefByName(Class<T> clazz, String attributeName) {

		String className = clazz.getSimpleName();

		Query q = em.createQuery("SELECT attrDef FROM "+className+" attrDef WHERE attrDef.name = :attributeName");
		q.setParameter("attributeName", attributeName);

		return (T) q.getSingleResult();
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
			String msg = messageSource.getMessage("AbstractCrudDaoImpl.noEntityForKey", new Object[] {key, clazz}, locale);
			throw new DoesNotExistException(msg);
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
