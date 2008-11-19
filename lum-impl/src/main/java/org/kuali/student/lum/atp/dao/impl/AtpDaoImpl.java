package org.kuali.student.lum.atp.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.kuali.student.core.entity.AttributeDef;
import org.kuali.student.lum.atp.dao.AtpDao;

public class AtpDaoImpl implements AtpDao {
	@PersistenceContext(unitName = "Atp")
	private EntityManager em;
	
	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}


	@Override
	public <T> T fetch(Class<T> clazz, String key) {
		return em.find(clazz, key);
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> find(Class<T> clazz){
		
		String className = clazz.getSimpleName();
		
		Query q = em.createQuery("SELECT x FROM "+className+" x");
		return (List<T>) q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends AttributeDef> T fetchAttributeDefByName(Class<T> clazz, String attributeName) {

		String className = clazz.getSimpleName();
		
		Query q = em.createQuery("SELECT attrDef FROM "+className+" attrDef WHERE attrDef.name=:attributeName");
		q.setParameter("attributeName", attributeName);

		return (T) q.getSingleResult();
	}

	@Override
	public <T> T create(T entity) {
		em.persist(entity);
		return entity;
	}
}
