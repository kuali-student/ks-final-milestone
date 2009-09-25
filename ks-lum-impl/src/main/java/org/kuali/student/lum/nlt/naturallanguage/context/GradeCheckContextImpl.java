package org.kuali.student.lum.nlt.naturallanguage.context;

import java.util.HashMap;
import java.util.Map;

import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.lum.nlt.naturallanguage.util.CustomReqComponentInfo;
import org.kuali.student.lum.nlt.naturallanguage.util.ReqComponentTypes;

/**
 * This class creates the template context for grade check type.
 */
public class GradeCheckContextImpl extends AbstractContext<CustomReqComponentInfo> {
	/** GPA template token */ 
	private final static String GPA_TOKEN = "gpa";
	
    /**
     * Creates the context map (template data) for the requirement component.
     * 
     * @param reqComponent Requirement component
     * @throws DoesNotExistException If CLU, CluSet or relation does not exist
     */
    public Map<String, Object> createContextMap(CustomReqComponentInfo reqComponent) throws OperationFailedException {
    	Map<String, Object> contextMap = new HashMap<String, Object>();
    	contextMap.put(GPA_TOKEN, getReqCompFieldValue(reqComponent, ReqComponentTypes.ReqCompFieldTypes.GPA_KEY.getKey()));

        return contextMap;
    }
}
