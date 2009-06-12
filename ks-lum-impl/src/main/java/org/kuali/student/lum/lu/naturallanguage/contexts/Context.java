package org.kuali.student.lum.lu.naturallanguage.contexts;

import java.util.Map;

import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.lum.lu.entity.ReqComponent;

public interface Context {
    /**
     * Creates the Velocity context map (template data) for the requirement component.
     * 
     * @param reqComponent Requirement component
     * @throws DoesNotExistException
     */
	public Map<String, Object> createContextMap(ReqComponent reqComponent) throws DoesNotExistException;
}
