package org.kuali.student.enumeration.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.kuali.student.enumeration.dao.EnumerationManagementDAO;
import org.kuali.student.enumeration.entity.EnumeratedValueEntity;
import org.kuali.student.enumeration.entity.EnumerationMetaEntity;
import org.springframework.stereotype.Repository;

@Repository
public class EnumerationManagementDAOImpl implements EnumerationManagementDAO {
    private EntityManager entityManager;

    @PersistenceContext(name = "EnumerationManagement")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<EnumerationMetaEntity> findEnumerationMetas() {
        Query query = entityManager.createQuery("SELECT e FROM EnumerationMetaEntity e");
        return (List<EnumerationMetaEntity>) query.getResultList();
    }
    public EnumerationMetaEntity addEnumerationMeta(EnumerationMetaEntity entity){
        
        entityManager.persist(entity);
        entity = entityManager.find(EnumerationMetaEntity.class, entity.getId());
        return entity;
    }
    public void removeEnumerationMeta(EnumerationMetaEntity entity){
        entityManager.remove(entity);
    }

    public EnumerationMetaEntity fetchEnumerationMeta(String enumerationKey) {
        Query query = entityManager.createQuery("SELECT e FROM EnumerationMetaEntity e where e.enumerationKey = :key");
        query.setParameter("key", enumerationKey);
        
        EnumerationMetaEntity em = null;
        if(!query.getResultList().isEmpty()){
        	em = (EnumerationMetaEntity)query.getResultList().get(0);
        }

        return em;
    }

    public List<EnumeratedValueEntity> fetchEnumeration(String enumerationKey, String enumContextKey, String contextValue, Date contextDate) {
    	
    	List<EnumeratedValueEntity> list = new ArrayList<EnumeratedValueEntity>();
    	
    	if(enumerationKey != null){
    		if(enumContextKey == null || contextValue == null){
    			//check to see if there is a date, return all results under these conditions if one does not exist
    			if(contextDate != null){
    				Query query = entityManager.createQuery(
    			            "select e from EnumeratedValueEntity e, ContextEntity c " +
    			            "where e.id = c.enumerationId and " +
    			            "e.effectiveDate < :contextDate and " +
    			            "e.expirationDate > :contextDate and " +
    			            "e.enumerationKey = :enumerationKey ");
    				 query.setParameter("enumerationKey", enumerationKey);
    				 query.setParameter("contextDate", contextDate);
    				 list = (List<EnumeratedValueEntity>)query.getResultList();
    			}
    			else{
    				Query query = entityManager.createQuery(
    			            "select e from EnumeratedValueEntity e, ContextEntity c " +
    			            "where e.id = c.enumerationId and " +
    			            "e.enumerationKey = :enumerationKey ");
    				 query.setParameter("enumerationKey", enumerationKey);
    				 list = (List<EnumeratedValueEntity>)query.getResultList();
    			}
    		}
    		//enumContextKey and contextValue are both not null
    		else{
    			//check to see if there is a date, return all results under these conditions if one does not exist
    			if(contextDate != null){
    				Query query = entityManager.createQuery(
    			            "select e from EnumeratedValueEntity e, ContextEntity c " +
    			            "where e.id = c.enumerationId and " +
    			            "e.effectiveDate < :contextDate and " +
    			            "e.expirationDate > :contextDate and " +
    			            "c.value = :contextValue and " +
    			            "c.key = :enumContextKey and " +
    			            "e.enumerationKey = :enumerationKey ");
    				 query.setParameter("enumerationKey", enumerationKey);
    				 query.setParameter("enumContextKey", enumContextKey);        
    				 query.setParameter("contextValue", contextValue);
    				 query.setParameter("contextDate", contextDate);
    				 list = (List<EnumeratedValueEntity>)query.getResultList();
    			}
    			else{
    				Query query = entityManager.createQuery(
    			            "select e from EnumeratedValueEntity e, ContextEntity c " +
    			            "where e.id = c.enumerationId and " +
    			            "c.value = :contextValue and " +
    			            "c.key = :enumContextKey and " +
    			            "e.enumerationKey = :enumerationKey ");
    				 query.setParameter("enumerationKey", enumerationKey);
    				 query.setParameter("enumContextKey", enumContextKey);        
    				 query.setParameter("contextValue", contextValue);
    				 list = (List<EnumeratedValueEntity>)query.getResultList();
    			}
    		}
    		
		    
    	}
    	
        return list;

    }

    public EnumeratedValueEntity addEnumeratedValue(String enumerationKey, EnumeratedValueEntity value) {
        entityManager.persist(value);
        value.setEnumerationKey(enumerationKey);

        return value;
    }

    public EnumeratedValueEntity updateEnumeratedValue(String enumerationKey, String code, EnumeratedValueEntity value) {
        Query query = entityManager.createQuery("update EnumeratedValueEntity e set e.value = :value where e.enumerationKey = :key and e.code = :code");
        query.setParameter("key", enumerationKey);
        query.setParameter("code", code);
        query.setParameter("value", value);
        query.executeUpdate();

        query = entityManager.createQuery("SELECT e FROM EnumeratedValueEntity e where e.enumerationKey = :key and e.code = :code");
        query.setParameter("key", enumerationKey);
        query.setParameter("code", code);
        Object obj = query.getResultList().get(0);

        return (EnumeratedValueEntity) obj;
    }

    public void removeEnumeratedValue(String enumerationKey, String code) {
        Query query = entityManager.createQuery("delete from EnumeratedValueEntity e where e.enumerationKey = :key and e.code = :code");
        query.setParameter("key", enumerationKey);
        query.setParameter("code", code);

        query.executeUpdate();
    }
}
