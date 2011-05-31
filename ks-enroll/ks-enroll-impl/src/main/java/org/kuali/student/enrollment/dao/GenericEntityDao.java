package org.kuali.student.enrollment.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Igor
 */
public class GenericEntityDao<T> implements EntityDao<T> {

    /**
     * Entity class.
     */
    protected Class<T> entityClass;

    @PersistenceContext
    protected EntityManager em;

    public GenericEntityDao() {
        entityClass = getEntityClass();
    }

    @Override
    public T find(Serializable primaryKey) {
        return em.find(entityClass, primaryKey);
    }

    @Override
    public List<T> findByIds(List<? extends Serializable> primaryKeys) {
        List<T> resultList = new ArrayList<T>();
        for (Serializable primaryKey : primaryKeys) {
            resultList.add(find(primaryKey));
        }
        return resultList;
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
    public <T> T merge(T entity) {
        return em.merge(entity);
    }

    @SuppressWarnings("unchecked")
    protected <K extends T> Class<K> getEntityClass() {
        if (entityClass == null) {
            entityClass = (Class<T>) getEntityType(this);
        }
        return (Class<K>) entityClass;
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

    public void setEm(EntityManager em) {
        this.em = em;
    }
}
