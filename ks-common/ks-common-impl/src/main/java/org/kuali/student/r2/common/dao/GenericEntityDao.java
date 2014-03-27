/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the the Educational Community License, Version 1.0
 * (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.common.dao;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.common.util.query.QueryUtil;
import org.kuali.student.r2.common.entity.PersistableEntity;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Kuali Student Team
 */
public class GenericEntityDao<T extends PersistableEntity<String>> implements EntityDao<String,T> {

    /**
     * Entity class.
     */
    protected Class<T> entityClass;

    @PersistenceContext
    protected EntityManager em;

    protected Integer maxInClauseElements = 100;

    public GenericEntityDao() {
        entityClass = getEntityClass();
    }

    @Override
    public T find(String primaryKey) {
        return em.find(entityClass, primaryKey);
    }

    @Override
    public List<T> findByIds(String primaryKeyMemberName, List<String> primaryKeys) throws DoesNotExistException {

        // fix for jira KSENROLL-2949
        if (primaryKeys == null || primaryKeys.isEmpty())
            return new ArrayList<T>();

        Set<String>primaryKeySet = new HashSet<String>(primaryKeys.size());
        // remove duplicates from the key list
        primaryKeySet.addAll(primaryKeys);

        StringBuilder queryStringRef = new StringBuilder();
        queryStringRef.append("from ").append(entityClass.getSimpleName()).append(" where ");

        TypedQuery<T> query = QueryUtil.buildQuery(em, maxInClauseElements, queryStringRef, null, primaryKeyMemberName, primaryKeys, entityClass);

        List<T> resultList = query.getResultList();

        verifyResults(resultList, primaryKeySet);

        return resultList;
    }

    /**
     * Check if an entity exists with the primary key given.
     * 
     * @param primaryKey the primary key identifier for the entity that will be checked.
     * @return true if there is a row/entity with the primary key given in the database; false otherwise.
     * 
     */
    public boolean entityExists (String primaryKey) {
        
        // TODO: see if this can be externalized as a named query.
        Query q = em.createQuery("select id from " + entityClass.getSimpleName() + " where id = :key").setParameter("key", primaryKey);
    
        try {
            q.getSingleResult();
        }
        catch (NonUniqueResultException e) {
            // more than 1 match (should never happen...)
            return false;
        } 
        catch (NoResultException e) {
            // zero matches
            return false;
        }

        // all other cases
        return true;
        
    }

    protected void verifyResults(List<T> resultList, Set<String> primaryKeys) throws DoesNotExistException {

    	 if (resultList.size() == 0){

             throw new DoesNotExistException("No data was found for : " + StringUtils.join(primaryKeys, ", "));

         } else if (resultList.size() != primaryKeys.size()) {
        	 // only found some of the keys given.
         	Set<String> unmatchedKeySet = new HashSet<String> ();
         
         	unmatchedKeySet.addAll(primaryKeys);

         	for (T t : resultList) {
 				
         		unmatchedKeySet.remove(t.getId());
 			}
         	
         	throw new DoesNotExistException("Missing data for : " + StringUtils.join(unmatchedKeySet.iterator(), ", "));
         	
         }
	}

	@Override
    public List<T> findByIds(List<String> primaryKeys) throws DoesNotExistException {

        return this.findByIds("id", primaryKeys);

    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        return (List<T>) em.createQuery("from " + entityClass.getSimpleName()).getResultList();
    }

    @Override
    public void persist(T entity) {
        em.persist(entity);
    }

    @Override
    public void remove(T entity) {
        em.remove(entity);
    }

    @Override
    public T merge(T entity) throws VersionMismatchException {
        
        if (em.contains(entity))
            em.detach(entity);
        
        T mergedEntity = null;
        
        try {
            mergedEntity = em.merge(entity);
        } catch (OptimisticLockException e) {
            throw new VersionMismatchException("Failed for entity.id = " + entity.getId());
        }
        
        return mergedEntity;
        
    }

    @SuppressWarnings("unchecked")
    protected <C extends T> Class<C> getEntityClass() {
        if (entityClass == null) {
            entityClass = (Class<T>) getEntityType(this);
        }
        return (Class<C>) entityClass;
    }

    private Type getEntityType(Object object) {
        Type type = object.getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            ParameterizedType paramType = (ParameterizedType) type;
            Type result = paramType.getActualTypeArguments()[0];
            return result;
        } else {
            throw new IllegalArgumentException("Could not guess entity type by reflection.");
        }
    }

    @Override
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public EntityManager getEm() {
        return em;
    }

    public void setMaxInClauseElements(Integer maxInClauseElements) {
        this.maxInClauseElements = maxInClauseElements;
    }
}