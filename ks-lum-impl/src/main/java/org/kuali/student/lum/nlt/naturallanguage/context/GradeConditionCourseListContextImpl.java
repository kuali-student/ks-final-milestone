package org.kuali.student.lum.nlt.naturallanguage.context;

import java.util.HashMap;
import java.util.Map;

import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.lum.lu.entity.ReqComponent;
import org.kuali.student.lum.nlt.naturallanguage.util.ReqComponentTypes;

/**
 * This class creates the template context for grade condition type.
 */
public class GradeConditionCourseListContextImpl extends AbstractContext<ReqComponent> {
	/** Total credits template token */ 
	private final static String TOTAL_CREDITS_TOKEN = "totalCredits";

    /**
     * Creates the context map (template data) for the requirement component.
     * 
     * @param reqComponent Requirement component
     * @throws DoesNotExistException
     * @throws DoesNotExistException If CLU, CluSet or relation does not exist
     */
    public Map<String, Object> createContextMap(ReqComponent reqComponent) throws DoesNotExistException {
        Map<String, Object> contextMap = new HashMap<String, Object>();
        
        contextMap.put(TOTAL_CREDITS_TOKEN, getReqCompFieldValue(reqComponent, ReqComponentTypes.ReqCompFieldTypes.TOTAL_CREDIT_KEY.getKey()));
        contextMap.put(CLU_SET_TOKEN, getCluSet(reqComponent));

        return contextMap;
    }
}
