package org.kuali.student.enrollment.lpr.dao;

import org.springframework.orm.jpa.support.JpaDaoSupport;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Igor
 */
public class GenericEntityDao<T> extends JpaDaoSupport implements EntityDao<T> {

    protected EntityManager em;

    /**
     * Entity class.
     */
    private Class<T> entityClass;

    public GenericEntityDao() {
        em = getJpaTemplate().getEntityManagerFactory().createEntityManager();
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
    public List<T> findAll() {
        return (List<T>) em.createQuery("from " + entityClass.getSimpleName());
    }

    @Override
    public void persist(T entity) {
        em.persist(entity);
    }

    @Override
    public void update(T entity) {
        em.refresh(entity);
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
}
