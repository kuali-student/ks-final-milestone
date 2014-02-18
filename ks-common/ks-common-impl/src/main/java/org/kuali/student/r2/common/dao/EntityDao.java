package org.kuali.student.r2.common.dao;

import java.io.Serializable;
import java.util.List;

import org.kuali.student.r2.common.entity.PersistableEntity;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;

import javax.persistence.EntityManager;

/**
 * Interface, that describes default methods of Dao objects for entity.
 *
 * @author Kuali Student Team
 */
public interface EntityDao<K extends Serializable, T extends PersistableEntity<K>> {

    /**
     * Find object by primary key.
     *
     * @param primaryKey Primary key
     * @return Entity for given key
     */
    T find(K primaryKey);

    /**
     * Find objects of specified class by primary keys. Note: this method assumes that your primary key is "id"
     *
     * @param primaryKeys - list of Primary keys
     * @return Entity for given key
     */
    public List<T> findByIds(List<K> primaryKeys) throws Exception;


    /**
     * Find objects of specified class by primary keys.
     *
     * @param primaryKeys - list of Primary keys
     * @return Entity for given key
     */
    public List<T> findByIds(String primaryKeyMemberName, List<K> primaryKeys) throws Exception;

    /**
     * Load all entities of this type.
     *
     * @return List of entities
     */
    List<T> findAll();

    /**
     * Persist unsaved object.
     *
     * @param entity Entity to save
     */
    void persist(T entity);

    /**
     * Merge detached object.
     * 
     * Note: If optimistic locking is used the entity returned will be different from the parameter value and should be used in any future operations.
     *
     * @param entity Entity to save
     * @return Merged entity.
     * @throws VersionMismatchException if there is an optimistic locking failure detected during the merge.
     */
    T merge(T entity) throws VersionMismatchException;

    /**
     * Remove entity from the persistent store.
     *
     * @param entity Entity to remove
     */
    void remove(T entity);

    /**
     * @return An entity manager, if applicable
     * @throws UnsupportedOperationException if there is no EM associated with this
     */
    EntityManager getEm() throws UnsupportedOperationException;

    /**
     * @param em An entity manager, if applicable
     * @throws UnsupportedOperationException if there is no EM associated with this
     */
    void setEm(EntityManager em) throws UnsupportedOperationException;
}
