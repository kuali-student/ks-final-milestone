package org.kuali.student.lum.lu.naturallanguage.contexts;

import java.util.HashMap;
import java.util.Map;

import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.lum.lu.entity.ReqComponent;
import org.kuali.student.lum.lu.naturallanguage.util.CustomCluSet;
import org.kuali.student.lum.lu.naturallanguage.util.ReqCompTypes;

public class GradeConditionCourseListContext extends AbstractContext {

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
        
        contextMap.put(ReqCompTypes.VelocityToken.TOTAL_CREDITS.getKey(), map.get(ReqCompTypes.ReqCompFieldDefinitions.TOTAL_CREDIT_KEY.getKey()));

        if(map.containsKey(ReqCompTypes.ReqCompFieldDefinitions.CLU_KEY.getKey())) {
        	String cluIds = map.get(ReqCompTypes.ReqCompFieldDefinitions.CLU_KEY.getKey());
        	CustomCluSet cluSet = getClusAsCluSet(cluIds);
        	contextMap.put(ReqCompTypes.VelocityToken.CLU_SET_KEY.getKey(), cluSet);
        } else if(map.containsKey(ReqCompTypes.ReqCompFieldDefinitions.CLUSET_KEY.getKey())) {
        	String cluSetId = map.get(ReqCompTypes.ReqCompFieldDefinitions.CLUSET_KEY.getKey());
            CustomCluSet cluSet = getCluSet(cluSetId);
            contextMap.put(ReqCompTypes.VelocityToken.CLU_SET_KEY.getKey(), cluSet);
        }

        return contextMap;
    }
}
