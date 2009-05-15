package org.kuali.student.lum.lu.naturallanguage.contexts;

import java.util.HashMap;
import java.util.Map;

import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.lum.lu.entity.ReqComponent;
import org.kuali.student.lum.lu.naturallanguage.util.ReqCompTypes;

public class GradeCheckContext extends AbstractContext {

    /**
     * Creates the Velocity context map (template data) for the requirement component.
     * 
     * @param reqComponent
     *            Requirement component
     * @param velocityContextMap
     *            Context map
     * @throws DoesNotExistException
     */
    public Map<String, Object> createContextMap(ReqComponent reqComponent) throws DoesNotExistException {
        Map<String, String> map = super.getReqCompField(reqComponent);

    	Map<String, Object> contextMap = new HashMap<String, Object>();
    	contextMap.put(ReqCompTypes.VelocityToken.GPA.getKey(), map.get(ReqCompTypes.ReqCompFieldDefinitions.GPA_KEY.getKey()));

        return contextMap;
    }
}
