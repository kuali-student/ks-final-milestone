package org.kuali.student.enumeration.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.*;

import org.kuali.student.enumeration.EnumerationException;
import org.kuali.student.enumeration.dao.EnumerationManagementDAO;
import org.kuali.student.enumeration.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class EnumerationManagementDAOImpl implements EnumerationManagementDAO {
    private EntityManager entityManager;
    
    final static Logger logger = LoggerFactory.getLogger(EnumerationManagementDAOImpl.class);

    @PersistenceContext(name = "EnumerationManagement")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    public EntityManager getEntityManager(){
        return this.entityManager; 
    }
    
    public List<EnumerationMetaEntity> findEnumerationMetas() {
    	List<EnumerationMetaEntity> metas = new ArrayList<EnumerationMetaEntity>();
    	try{
	        Query query = entityManager.createQuery("SELECT e FROM EnumerationMetaEntity e");
	        metas = (List<EnumerationMetaEntity>) query.getResultList();
    	}
        catch(Exception e){
	    	logger.error("findEnumerationMetas query failed.", e);
			throw new EnumerationException("findEnumerationMetas query failed.", e);
	    }
        return metas;
    }
    
    public EnumerationMetaEntity addEnumerationMeta(EnumerationMetaEntity entity){
    	try{
	        entityManager.persist(entity);
	        entity = entityManager.find(EnumerationMetaEntity.class, entity.getId());
		}
	    catch(Exception e){
	    	logger.error("addEnumerationMeta query failed.", e);
			throw new EnumerationException("addEnumerationMeta query failed.", e);
	    }
        return entity;
    }
    
    public boolean removeEnumerationMeta(String enumerationKey){
        boolean removed = false;
        try{
	    	EnumerationMetaEntity meta = this.fetchEnumerationMeta(enumerationKey);
	        if(meta != null){
	        	entityManager.remove(meta);
	        	removed = true;
	        }
		}
	    catch(Exception e){
	    	logger.error("removeEnumerationMeta query failed.", e);
			throw new EnumerationException("removeEnumerationMeta query failed.", e);
	    }
	    	
	    return removed;
	}

    public EnumerationMetaEntity fetchEnumerationMeta(String enumerationKey) {
    	EnumerationMetaEntity em = null;
    	try{
	    	Query query = entityManager.createQuery("SELECT e FROM EnumerationMetaEntity e where e.enumerationKey = :key");
	        query.setParameter("key", enumerationKey);
	        
	        if(!query.getResultList().isEmpty()){
	        	em = (EnumerationMetaEntity)query.getResultList().get(0);
	        }else{
	            return null;
	        }
    	}
        catch(Exception e){
        	logger.error("fetchEnumerationMeta query failed.", e);
			throw new EnumerationException("fetchEnumerationMeta query failed.", e);
        }

        return em;
    }

    public EnumeratedValueEntity addEnumeratedValue(String enumerationKey, EnumeratedValueEntity value) {
    	try{
	    	//EnumerationMetaEntity meta = this.fetchEnumerationMeta(enumerationKey);
	        //boolean addOK = true;
	        /*
	        List<EnumeratedValueFieldEntity> fields = meta.getEnumeratedValueFieldList();
	        for(EnumeratedValueFieldEntity field: fields){
	        	if(field.getFieldKey().equalsIgnoreCase("code")){
	        		addOK = addOK && validateValue(value.getCode(), field);
	        	}
	        	else if(field.getFieldKey().equalsIgnoreCase("abbrevValue")){
	        		addOK = addOK && validateValue(value.getAbbrevValue(), field);
	        	}
	        	else if(field.getFieldKey().equalsIgnoreCase("value")){
	        		addOK = addOK && validateValue(value.getValue(), field);
	        	}
	        	else if(field.getFieldKey().equalsIgnoreCase("effectiveDate")){
	        		addOK = addOK && validateValue(value.getEffectiveDate(), field);
	        	}
	        	else if(field.getFieldKey().equalsIgnoreCase("expirationDate")){
	        		addOK = addOK && validateValue(value.getExpirationDate(), field);
	        	}
	        	else if(field.getFieldKey().equalsIgnoreCase("sortKey")){
	        		addOK = addOK && validateValue(value.getSortKey(), field);
	        	}
	        	
	        	//it is not ok to add, a validate failed.
	        	if(!addOK){
	        		break;
	        	}
	        }
	        */
	        
	        entityManager.persist(value);
	        value.setEnumerationKey(enumerationKey);
    	}
        catch(Exception e){
        	logger.error("addEnumeratedValue query failed.", e);
			throw new EnumerationException("addEnumeratedValue query failed.", e);
        }

        return value;
    }
    
    //not used, use in future for validation?
    private boolean validateValue(String value, EnumeratedValueFieldEntity field){
    	//boolean validated = true;
    	
    	//datatype
    	if(!field.getDataType().equalsIgnoreCase("string")){
    		return false;
    	}
    	
    	//maxLength, non-set max length is -1
    	if(field.getMaxLength() != -1 && field.getMaxLength() < value.length()){
    		return false;
    	}
    	
    	//minLength, non-set min length is -1
    	if(field.getMinLength() != -1 && field.getMinLength() > value.length()){
    		return false;
    	}
    	
    	//??????
    	if(field.getMaxOccurs() != -1){
    		return false;
    	}
    	
    	//??????
    	if(field.getMinOccurs() != -1){
    		return false;
    	}
    	
    	//validChars, a non-set valid chars field means all characters valid
    	if(field.getValidChars() != null && !field.getValidChars().equals("")){
    		char[] valueChars = value.toCharArray();
    		for(char c: valueChars){
    			if(field.getValidChars().indexOf(c) == -1){
    				return false;
    			}
    		}
    	}
    	
    	//invalidChars, a non-set invalid chars field means no chars are specified as invalid
    	if(field.getInvalidChars() != null && !field.getInvalidChars().equals("")){
    		char[] valueChars = value.toCharArray();
    		for(char c: valueChars){
    			if(field.getInvalidChars().indexOf(c) >= 0){
    				return false;
    			}
    		}
    	}
    	
    	return true;
    }
    
    public EnumeratedValueEntity updateEnumeratedValue(String enumerationKey, String code, EnumeratedValueEntity enumeratedValueEntity) {
        //this.removeEnumeratedValue(enumerationKey, code);
        //this.addEnumeratedValue(enumerationKey, enumeratedValueEntity);
    	
    	//Query query = entityManager.createQuery("update EnumeratedValueEntity e set e.value = :value where e.enumerationKey = :key and e.code = :code");
        //query.setParameter("key", enumerationKey);
        //query.setParameter("code", code);
        //query.setParameter("value", enumeratedValueEntity.getValue());
        
        //query.executeUpdate();
    	EnumeratedValueEntity returnValue = null;
    	try{
	    	List<EnumeratedValueEntity> list = this.fetchEnumeration(enumerationKey);
	        for(EnumeratedValueEntity e: list){
	        	if(e.getCode().equals(code)){
	        		e.setCode(enumeratedValueEntity.getCode());
	        		e.setEffectiveDate(enumeratedValueEntity.getEffectiveDate());
	        		e.setExpirationDate(enumeratedValueEntity.getExpirationDate());
	        		e.setEnumerationKey(enumerationKey);
	        		e.setSortKey(enumeratedValueEntity.getSortKey());
	        		e.setValue(enumeratedValueEntity.getValue());
	        		e.setAbbrevValue(enumeratedValueEntity.getAbbrevValue());
	        		e.setContextEntityList(enumeratedValueEntity.getContextEntityList());
	        		entityManager.merge(e);
	        		returnValue = e;
	        	}
	        }
    	}
        catch(Exception e){
        	logger.error("updateEnumeratedValue query failed.", e);
			throw new EnumerationException("updateEnumeratedValue query failed.", e);
        }
        
    	//entityManager.merge(enumeratedValueEntity);
    	//for(ContextEntity c: enumeratedValueEntity.getContextEntityList()){
    		//entityManager.merge(c);
    	//}
/*
    	Query query = entityManager.createQuery(
	            "select e from EnumeratedValueEntity e JOIN e.contextEntityList c " +
	            "where e.enumerationKey = :enumerationKey "+
	            "and e.code = :code");
		query.setParameter("enumerationKey", enumerationKey);
        query.setParameter("code", code);
        Object obj = query.getResultList().get(0);
*/
        
        //return (EnumeratedValueEntity) obj;
        return returnValue;
    }
    
    public boolean removeEnumeratedValue(String enumerationKey, String code) {
        //Query query = entityManager.createQuery("delete from EnumeratedValueEntity e where e.enumerationKey = :key and e.code = :code");
        //query.setParameter("key", enumerationKey);
       // query.setParameter("code", code);
        boolean removed = false;
        try{
	    	List<EnumeratedValueEntity> list = this.fetchEnumeration(enumerationKey);
	        for(EnumeratedValueEntity e: list){
	        	if(e.getCode().equals(code)){
	        		entityManager.remove(e);
	        		removed = true;
	        	}
	        }
        }
		catch(Exception e){
			logger.error("removeEnumeratedValue query failed.", e);
			throw new EnumerationException("removeEnumeratedValue query failed.", e);
		}
        //query.executeUpdate();
        return removed;
    }
    
    
	public List<EnumeratedValueEntity> fetchEnumeration(String enumerationKey) {
		
		List<EnumeratedValueEntity> list = new ArrayList<EnumeratedValueEntity>();
		try{
			Query query = entityManager.createQuery(
		            "select e from EnumeratedValueEntity e " +
		            "where e.enumerationKey = :enumerationKey ");
			 query.setParameter("enumerationKey", enumerationKey);
			 list = (List<EnumeratedValueEntity>)query.getResultList();
		}
		catch(Exception e){
			logger.error("fetchEnumeration query failed.", e);
			throw new EnumerationException("fetchEnumeration query failed.", e);
		}
		 
		return list;
	}
	public List<EnumeratedValueEntity> fetchEnumerationWithDate(String enumerationKey, Date contextDate) {
		
		List<EnumeratedValueEntity> list = new ArrayList<EnumeratedValueEntity>();
		try{
			Query query = entityManager.createQuery(
		            "select e from EnumeratedValueEntity e " +
		            "where e.effectiveDate <= :contextDate and " +
		            "(e.expirationDate is null or e.expirationDate >= :contextDate) and " +
		            "e.enumerationKey = :enumerationKey ");
			 query.setParameter("enumerationKey", enumerationKey);
			 query.setParameter("contextDate", contextDate);
			 list = (List<EnumeratedValueEntity>)query.getResultList();
		}
		catch(Exception e){
			logger.error("fetchEnumerationWithDate query failed.", e);
			throw new EnumerationException("fetchEnumerationWithDate query failed.", e);
		}
		return list;
	}
	public List<EnumeratedValueEntity> fetchEnumerationWithContext(String enumerationKey, String enumContextKey, String contextValue) {
		
		List<EnumeratedValueEntity> list = new ArrayList<EnumeratedValueEntity>();
		try{
			Query query = entityManager.createQuery(
		            "select e from EnumeratedValueEntity e JOIN e.contextEntityList c " +
		            "where c.contextValue = :contextValue and " +
		            "c.contextKey = :enumContextKey and " +
		            "e.enumerationKey = :enumerationKey ");
			 query.setParameter("enumerationKey", enumerationKey);
			 query.setParameter("enumContextKey", enumContextKey);        
			 query.setParameter("contextValue", contextValue);
			 
			list = (List<EnumeratedValueEntity>)query.getResultList();
		}
		catch(Exception e){
			logger.error("fetchEnumerationWithContext query failed.", e);
			throw new EnumerationException("fetchEnumerationWithContext query failed.", e);
		}
		return list;
	}
	
	public List<EnumeratedValueEntity> fetchEnumerationWithContextAndDate(String enumerationKey, String enumContextKey, String contextValue,
			Date contextDate) {
    	
		List<EnumeratedValueEntity> list = new ArrayList<EnumeratedValueEntity>();
    	
		try{
	        Query query = entityManager.createQuery(
	                "select e from EnumeratedValueEntity e JOIN e.contextEntityList c " 
	                + "where e.effectiveDate <= :contextDate and " +
	                "(e.expirationDate is null or e.expirationDate >= :contextDate) and " + 
	                "c.contextValue = :contextValue and " + 
	                "c.contextKey = :enumContextKey and " +
	                "e.enumerationKey = :enumKey ");
	        query.setParameter("contextDate", contextDate);
	        query.setParameter("contextValue", contextValue);
	        query.setParameter("enumContextKey", enumContextKey);
	        query.setParameter("enumKey", enumerationKey);
	        list = (List<EnumeratedValueEntity>)query.getResultList();
		}
		catch(Exception e){
			logger.error("fetchEnumerationWithContextAndDate query failed.", e);
			throw new EnumerationException("fetchEnumerationWithContextAndDate query failed.", e);
		}
         
        return list;
	}
	
    /*
    public List<EnumeratedValueEntity> fetchEnumeration(String enumerationKey, String enumContextKey, String contextValue, Date contextDate) {
    	

    	
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
 	


    }
      */ 
}
