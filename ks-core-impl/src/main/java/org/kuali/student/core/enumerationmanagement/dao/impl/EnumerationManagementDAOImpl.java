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

package org.kuali.student.core.enumerationmanagement.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.kuali.student.core.dao.impl.AbstractSearchableCrudDaoImpl;
import org.kuali.student.core.enumerationmanagement.EnumerationException;
import org.kuali.student.core.enumerationmanagement.dao.EnumerationManagementDAO;
import org.kuali.student.core.enumerationmanagement.entity.EnumeratedValueEntity;
import org.kuali.student.core.enumerationmanagement.entity.EnumerationMetaEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class EnumerationManagementDAOImpl extends AbstractSearchableCrudDaoImpl implements EnumerationManagementDAO {
    
    final static Logger logger = LoggerFactory.getLogger(EnumerationManagementDAOImpl.class);

	@PersistenceContext(unitName = "EnumerationManagement")
	@Override
	public void setEm(EntityManager em) {
		super.setEm(em);
	}

	public List<EnumerationMetaEntity> findEnumerationMetas() {
    	List<EnumerationMetaEntity> metas;
    	try{
	        Query query = em.createQuery("SELECT e FROM EnumerationMetaEntity e");
	        @SuppressWarnings("unchecked")	        
	        List<EnumerationMetaEntity> resultList = (List<EnumerationMetaEntity>) query.getResultList();
			metas = resultList;
    	}
        catch(Exception e){
	    	logger.error("findEnumerationMetas query failed.", e);
			throw new EnumerationException("findEnumerationMetas query failed.", e);
	    }
        return metas;
    }
    
    public EnumerationMetaEntity addEnumerationMeta(EnumerationMetaEntity entity){
    	try{
	        em.persist(entity);
	        entity = em.find(EnumerationMetaEntity.class, entity.getId());
		}
	    catch(Exception e){
	    	throw new EnumerationException("addEnumerationMeta query failed.", e);
	    }
        return entity;
    }
    
    public boolean removeEnumerationMeta(String enumerationKey){
        boolean removed = false;
        try{
	    	EnumerationMetaEntity meta = this.fetchEnumerationMeta(enumerationKey);
	        if(meta != null){
	        	em.remove(meta);
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
    	EnumerationMetaEntity enumerationMetaEntity = null;
    	try{
	    	Query query = em.createQuery("SELECT e FROM EnumerationMetaEntity e where e.enumerationKey = :key");
	        query.setParameter("key", enumerationKey);
	        
	        if(!query.getResultList().isEmpty()){
	        	enumerationMetaEntity = (EnumerationMetaEntity)query.getResultList().get(0);
	        }else{
	            return null;
	        }
    	}
        catch(Exception e){
        	logger.error("fetchEnumerationMeta query failed.", e);
			throw new EnumerationException("fetchEnumerationMeta query failed.", e);
        }

        return enumerationMetaEntity;
    }

    public EnumeratedValueEntity addEnumeratedValue(String enumerationKey, EnumeratedValueEntity value) {
    	try{
	        em.persist(value);
	        value.setEnumerationKey(enumerationKey);
    	}
        catch(Exception e){
        	logger.error("addEnumeratedValue query failed.", e);
			throw new EnumerationException("addEnumeratedValue query failed.", e);
        }

        return value;
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
	        		em.merge(e);
	        		returnValue = e;
	        	}
	        }

    	}
        catch(Exception e){
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
	        		em.remove(e);
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
		
		List<EnumeratedValueEntity> list ;
		try{
			Query query = em.createQuery(
		            "select e from EnumeratedValueEntity e " +
		            "where e.enumerationKey = :enumerationKey ");
			query.setParameter("enumerationKey", enumerationKey);
			@SuppressWarnings("unchecked")
			List<EnumeratedValueEntity> resultList = (List<EnumeratedValueEntity>)query.getResultList();
			list = resultList;
		}
		catch(Exception e){
			logger.error("fetchEnumeration query failed.", e);
			throw new EnumerationException("fetchEnumeration query failed.", e);
		}
		 
		return list;
	}
	public List<EnumeratedValueEntity> fetchEnumerationWithDate(String enumerationKey, Date contextDate) {
		
		List<EnumeratedValueEntity> list;
		try{
			Query query = em.createQuery(
		            "select e from EnumeratedValueEntity e " +
		            "where e.effectiveDate <= :contextDate and " +
		            "(e.expirationDate is null or e.expirationDate >= :contextDate) and " +
		            "e.enumerationKey = :enumerationKey ");
			query.setParameter("enumerationKey", enumerationKey);
			query.setParameter("contextDate", contextDate);
		    @SuppressWarnings("unchecked")
			List<EnumeratedValueEntity> resultList = (List<EnumeratedValueEntity>)query.getResultList();
			list = resultList;
		}
		catch(Exception e){
			logger.error("fetchEnumerationWithDate query failed.", e);
			throw new EnumerationException("fetchEnumerationWithDate query failed.", e);
		}
		return list;
	}
	public List<EnumeratedValueEntity> fetchEnumerationWithContext(String enumerationKey, String enumContextKey, String contextValue) {
		
		List<EnumeratedValueEntity> list;
		try{
			Query query = em.createQuery(
		            "select e from EnumeratedValueEntity e JOIN e.contextEntityList c " +
		            "where c.contextValue = :contextValue and " +
		            "c.contextKey = :enumContextKey and " +
		            "e.enumerationKey = :enumerationKey ");
			 query.setParameter("enumerationKey", enumerationKey);
			 query.setParameter("enumContextKey", enumContextKey);        
			 query.setParameter("contextValue", contextValue);
			 
			@SuppressWarnings("unchecked")
 		 	List<EnumeratedValueEntity> resultList = (List<EnumeratedValueEntity>)query.getResultList();
			list = resultList;
		}
		catch(Exception e){
			logger.error("fetchEnumerationWithContext query failed.", e);
			throw new EnumerationException("fetchEnumerationWithContext query failed.", e);
		}
		return list;
	}
	
	public List<EnumeratedValueEntity> fetchEnumerationWithContextAndDate(String enumerationKey, String enumContextKey, String contextValue,
			Date contextDate) {
    	
		List<EnumeratedValueEntity> list;
    	
		try{
	        Query query = em.createQuery(
	                "select e from EnumeratedValueEntity e, IN(e.contextEntityList) c " 
	                + "where e.effectiveDate <= :contextDate and " +
	                "(e.expirationDate is null or e.expirationDate >= :contextDate) and " + 
	                "c.contextValue = :contextValue and " + 
	                "c.contextKey = :enumContextKey and " +
	                "e.enumerationKey = :enumKey ");
	        query.setParameter("contextDate", contextDate);
	        query.setParameter("contextValue", contextValue);
	        query.setParameter("enumContextKey", enumContextKey);
	        query.setParameter("enumKey", enumerationKey);
	        @SuppressWarnings("unchecked")
	        List<EnumeratedValueEntity> resultList = (List<EnumeratedValueEntity>)query.getResultList();
			list = resultList;
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
