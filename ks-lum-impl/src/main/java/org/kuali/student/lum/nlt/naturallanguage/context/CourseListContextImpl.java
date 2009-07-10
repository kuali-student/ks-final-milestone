package org.kuali.student.lum.nlt.naturallanguage.context;

import java.util.HashMap;
import java.util.Map;

import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.lum.lu.entity.ReqComponent;
import org.kuali.student.lum.nlt.naturallanguage.util.ReqComponentTypes;

/**
 * This class creates the template context for course list types.
 */
public class CourseListContextImpl extends AbstractContext<ReqComponent> {
    /**
     * Creates the context map (template data) for the requirement component.
     * 
     * @param reqComponent Requirement component
     * @throws DoesNotExistException If CLU, CluSet or relation does not exist
     */
    public Map<String, Object> createContextMap(ReqComponent reqComponent) throws DoesNotExistException {
        Map<String, Object> contextMap = new HashMap<String, Object>();
        
        contextMap.put(EXPECTED_VALUE_TOKEN, getReqCompFieldValue(reqComponent, ReqComponentTypes.ReqCompFieldTypes.REQUIRED_COUNT_KEY.getKey()));
        contextMap.put(OPERATOR_TOKEN, getReqCompFieldValue(reqComponent, ReqComponentTypes.ReqCompFieldTypes.OPERATOR_KEY.getKey()));
        contextMap.put(CLU_SET_TOKEN, getCluSet(reqComponent));

        return contextMap;
    }
}
