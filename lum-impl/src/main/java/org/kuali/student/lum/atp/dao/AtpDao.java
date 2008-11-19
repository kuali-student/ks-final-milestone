package org.kuali.student.lum.atp.dao;

import org.kuali.student.core.entity.AttributeDef;

public interface AtpDao {

	public <T> T fetch(Class<T> clazz,
			String key);

	public <T extends AttributeDef> T fetchAttributeDefByName(Class<T> clazz, String attributeName);

	public <T> T create(T entity);

}
