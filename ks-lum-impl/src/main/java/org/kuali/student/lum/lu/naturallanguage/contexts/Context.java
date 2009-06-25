package org.kuali.student.lum.lu.naturallanguage.contexts;

import java.util.Map;

import org.kuali.student.core.exceptions.DoesNotExistException;

public interface Context<T> {
    /**
     * Creates the Velocity context map (template data) for a specific context.
     * 
     * @param context Context to create the map from
     * @throws DoesNotExistException If object/data does not exists
     */
	public Map<String, Object> createContextMap(T context) throws DoesNotExistException;
}
