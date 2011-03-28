package org.kuali.student.enrollment.lpr.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Interface, that describes default methods of Dao objects for entity.
 *
 * @author ihar
 */
public interface EntityDao<T> {

	/**
	 * Find object by primary key.
	 *
	 * @param primaryKey Primary key
	 * @return Entity for given key
	 */
	T find(Serializable primaryKey);

	/**
	 * Find objects of specified class by primary keys.
	 *
	 * @param primaryKeys - list of Primary keys
	 * @return Entity for given key
	 */
	public List<T> findByIds(List<? extends Serializable> primaryKeys);

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
	 * Update detached object.
	 *
	 * @param entity Entity to update
	 */
	void update(T entity);


	/**
	 * Merge detached object.
	 *
	 * @param entity Entity to save
	 * @param <T>    the Entity type
	 * @return Merged entity.
	 */
	<T> T merge(T entity);

	/**
	 * Remove entity from the persistent store.
	 *
	 * @param entity Entity to remove
	 */
	void remove(T entity);
}
