package org.kuali.student.core.dao;

import java.util.List;

import org.kuali.student.core.entity.AttributeDef;
import org.kuali.student.core.exceptions.DoesNotExistException;

public interface CrudDao {
	public <T> T fetch(Class<T> clazz, String key) throws DoesNotExistException;

	public <T extends AttributeDef> T fetchAttributeDefByName(Class<T> clazz,
			String attributeName);

	public <T> T create(T entity);

	public <T> void delete(Class<T> clazz, String key) throws DoesNotExistException;

	public void delete(Object entity);

	public <T> List<T> find(Class<T> clazz);

	public <T> T update(T entity);
}
