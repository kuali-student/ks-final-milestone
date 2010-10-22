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

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.kuali.student.core.dao.impl.AbstractSearchableCrudDaoImpl;
import org.kuali.student.core.enumerationmanagement.EnumerationException;
import org.kuali.student.core.enumerationmanagement.dao.EnumerationManagementDAO;
import org.kuali.student.core.enumerationmanagement.entity.EnumeratedValue;
import org.kuali.student.core.enumerationmanagement.entity.Enumeration;
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

	public List<Enumeration> findEnumerations() {
    	List<Enumeration> metas;
    	try{
	        Query query = em.createQuery("SELECT e FROM Enumeration e");
	        @SuppressWarnings("unchecked")
	        List<Enumeration> resultList = (List<Enumeration>) query.getResultList();
			metas = resultList;
    	}
        catch(Exception e){
	    	logger.error("findEnumerations query failed.", e);
			throw new EnumerationException("findEnumerations query failed.", e);
	    }
        return metas;
    }

    public Enumeration addEnumeration(Enumeration entity){
    	try{
	        em.persist(entity);
	        entity = em.find(Enumeration.class, entity.getId());
		}
	    catch(Exception e){
	    	throw new EnumerationException("addEnumerationMeta query failed.", e);
	    }
        return entity;
    }

    public boolean removeEnumeration(String enumerationKey){
        boolean removed = false;
        try{
	    	Enumeration meta = this.fetch(Enumeration.class, enumerationKey);
	        if(meta != null){
	        	em.remove(meta);
	        	removed = true;
	        }
		}
	    catch(Exception e){
	    	logger.error("removeEnumeration query failed.", e);
			throw new EnumerationException("removeEnumeration query failed.", e);
	    }

	    return removed;
	}

    public EnumeratedValue addEnumeratedValue(String enumerationKey, EnumeratedValue value) {
    	try{    
	        em.persist(value);
    	}
        catch(Exception e){
        	logger.error("addEnumeratedValue query failed.", e);
			throw new EnumerationException("addEnumeratedValue query failed.", e);
        }

        return value;
    }


    public EnumeratedValue updateEnumeratedValue(Enumeration enumeration, String code, EnumeratedValue enumeratedValue) {

        EnumeratedValue returnValue = null;
    	try{
    	    
    	    List<EnumeratedValue> list = this.fetchEnumeratedValues(enumeration.getId());
	        for(EnumeratedValue e: list){
	        	if(e.getCode().equals(code)){
	        		e.setCode(enumeratedValue.getCode());
	        		e.setEffectiveDate(enumeratedValue.getEffectiveDate());
	        		e.setExpirationDate(enumeratedValue.getExpirationDate());
	        		e.setEnumeration(enumeration);
	        		e.setSortKey(enumeratedValue.getSortKey());
	        		e.setValue(enumeratedValue.getValue());
	        		e.setAbbrevValue(enumeratedValue.getAbbrevValue());
	        		e.setContextEntityList(enumeratedValue.getContextEntityList());
	        		em.merge(e);
	        		returnValue = e;
	        		break;
	        	}
	        }
    	}
        catch(Exception e){
			throw new EnumerationException("updateEnumeratedValue query failed.", e);
        }

        return returnValue;
    }

    public boolean removeEnumeratedValue(String enumerationKey, String code) {
        boolean removed = false;
        try{
	    	List<EnumeratedValue> list = this.fetchEnumeratedValues(enumerationKey);
	        for(EnumeratedValue e: list){
	        	if(e.getCode().equals(code)){
	        		em.remove(e);
	        		removed = true;
	        		break;
	        	}
	        }
        }
		catch(Exception e){
			logger.error("removeEnumeratedValue query failed.", e);
			throw new EnumerationException("removeEnumeratedValue query failed.", e);
		}
        return removed;
    }


	public List<EnumeratedValue> fetchEnumeratedValues(String enumerationKey) {

		List<EnumeratedValue> list ;
		try{
			Query query = em.createQuery(
		            "select e from EnumeratedValue e " +
		            "where e.enumeration.id = :enumerationKey ");
			query.setParameter("enumerationKey", enumerationKey);
			@SuppressWarnings("unchecked")
			List<EnumeratedValue> resultList = (List<EnumeratedValue>)query.getResultList();
			list = resultList;
		}
		catch(Exception e){
			logger.error("fetchEnumeration query failed.", e);
			throw new EnumerationException("fetchEnumeration query failed.", e);
		}

		return list;
	}
	
	public List<EnumeratedValue> fetchEnumeratedValuesWithDate(String enumerationKey, Date contextDate) {

		List<EnumeratedValue> list;
		try{
			Query query = em.createQuery(
		            "select e from EnumeratedValue e " +
		            "where e.effectiveDate <= :contextDate and " +
		            "(e.expirationDate is null or e.expirationDate >= :contextDate) and " +
		            "e.enumeration.id = :enumerationKey ");
			query.setParameter("enumerationKey", enumerationKey);
			query.setParameter("contextDate", contextDate);
		    @SuppressWarnings("unchecked")
			List<EnumeratedValue> resultList = (List<EnumeratedValue>)query.getResultList();
			list = resultList;
		}
		catch(Exception e){
			logger.error("fetchEnumerationWithDate query failed.", e);
			throw new EnumerationException("fetchEnumerationWithDate query failed.", e);
		}
		return list;
	}
	public List<EnumeratedValue> fetchEnumeratedValuesWithContext(String enumerationKey, String enumContextKey, String contextValue) {

		List<EnumeratedValue> list;
		try{
			Query query = em.createQuery(
		            "select e from EnumeratedValue e JOIN e.contextEntityList c " +
		            "where c.contextValue = :contextValue and " +
		            "c.contextKey = :enumContextKey and " +
		            "e.enumeration.id = :enumerationKey ");
			 query.setParameter("enumerationKey", enumerationKey);
			 query.setParameter("enumContextKey", enumContextKey);
			 query.setParameter("contextValue", contextValue);

			@SuppressWarnings("unchecked")
 		 	List<EnumeratedValue> resultList = (List<EnumeratedValue>)query.getResultList();
			list = resultList;
		}
		catch(Exception e){
			logger.error("fetchEnumerationWithContext query failed.", e);
			throw new EnumerationException("fetchEnumerationWithContext query failed.", e);
		}
		return list;
	}

	public List<EnumeratedValue> fetchEnumeratedValuesWithContextAndDate(String enumerationKey, String enumContextKey, String contextValue,
			Date contextDate) {

		List<EnumeratedValue> list;

		try{
	        Query query = em.createQuery(
	                "select e from EnumeratedValue e, IN(e.contextEntityList) c "
	                + "where e.effectiveDate <= :contextDate and " +
	                "(e.expirationDate is null or e.expirationDate >= :contextDate) and " +
	                "c.contextValue = :contextValue and " +
	                "c.contextKey = :enumContextKey and " +
	                "e.enumeration.id = :enumKey ");
	        query.setParameter("contextDate", contextDate);
	        query.setParameter("contextValue", contextValue);
	        query.setParameter("enumContextKey", enumContextKey);
	        query.setParameter("enumKey", enumerationKey);
	        @SuppressWarnings("unchecked")
	        List<EnumeratedValue> resultList = (List<EnumeratedValue>)query.getResultList();
			list = resultList;
		}
		catch(Exception e){
			logger.error("fetchEnumerationWithContextAndDate query failed.", e);
			throw new EnumerationException("fetchEnumerationWithContextAndDate query failed.", e);
		}

        return list;
	}
  }
