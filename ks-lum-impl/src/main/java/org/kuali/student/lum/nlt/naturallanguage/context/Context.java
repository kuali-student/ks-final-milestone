package org.kuali.student.lum.nlt.naturallanguage.context;

import java.util.Map;

import org.kuali.student.core.exceptions.OperationFailedException;

public interface Context<T> {
    /**
     * Creates the Velocity context map (template data) for a specific context.
     * 
     * @param context Context to create the map from
     * @throws OperationFailedException If creating context data map fails
     */
	public Map<String, Object> createContextMap(T context) throws OperationFailedException;
}
