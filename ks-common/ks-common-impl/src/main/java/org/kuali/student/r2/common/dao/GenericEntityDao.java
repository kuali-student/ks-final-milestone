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
import org.kuali.student.r2.common.entity.PersistableEntity;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
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

    protected Boolean enableMaxIdFetch = Boolean.TRUE;
    protected Integer maxInClauseElements = 1000;

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

        StringBuilder queryString = new StringBuilder();

        TypedQuery<T> query = buildQuery(queryString, primaryKeyMemberName, primaryKeySet);

        List<T> resultList = query.getResultList();

        verifyResults(resultList, primaryKeySet);

        return resultList;
    }

    /**
     * Returns a query of find by ids. Breaks up the query into multiple ORed IN() clauses if the set of ids is larger
     * than maxInClauseElements
     * @param queryStringRef a reference to the query string
     * @param primaryKeyMemberName name of the column that is the primary key
     * @param primaryKeySet set of primary key strings
     * @return a typed query that finds entities for the set of keys
     */
    protected TypedQuery<T> buildQuery(StringBuilder queryStringRef, String primaryKeyMemberName, Set<String> primaryKeySet) {

        TypedQuery<T> queryRef;

        if (!enableMaxIdFetch || primaryKeySet.size() <= maxInClauseElements) {

            queryStringRef.append("from ").append(entityClass.getSimpleName()).append(" where ").append(primaryKeyMemberName).append(" in (:ids)");
            queryRef = em.createQuery(queryStringRef.toString(), entityClass).setParameter("ids", primaryKeySet);

        } else {
            //Max fetchh is enabled so break uip the where clause into multiple IN() clauses
            List<List<String>> brokenLists = new ArrayList<List<String>>();
            List<String> lst = new ArrayList<String>();

            queryStringRef.append("from ").append(entityClass.getSimpleName());

            Iterator<String> itr = primaryKeySet.iterator();
            for (int index = 0; itr.hasNext(); index++) {

                if (index % maxInClauseElements == 0) {

                    brokenLists.add(lst);

                    if (brokenLists.size() == 1) {
                        queryStringRef.append(" where ").append(primaryKeyMemberName).append(" in (:ids1)");
                    } else {
                        queryStringRef.append(" or ").append(primaryKeyMemberName).append(" in (:ids").append(brokenLists.size()).append(")");
                    }

                }
                lst.add(itr.next());
            }

            queryRef = em.createQuery(queryStringRef.toString(), entityClass);

            for (int i = 1; i <= brokenLists.size(); i++) {
                queryRef.setParameter("ids" + i, brokenLists.get(i - 1));
            }

        }
        return queryRef;
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
    public void update(T entity) {
        em.merge(entity);
    }

    @Override
    public void remove(T entity) {
        em.remove(entity);
    }

    @Override
    public T merge(T entity) {
        
        if (em.contains(entity))
            em.detach(entity);
        
        return em.merge(entity);
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

    public void setEnableMaxIdFetch(Boolean enableMaxIdFetch) {
        this.enableMaxIdFetch = enableMaxIdFetch;
    }

    public void setMaxInClauseElements(Integer maxInClauseElements) {
        this.maxInClauseElements = maxInClauseElements;
    }

}