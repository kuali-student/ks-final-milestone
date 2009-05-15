package org.kuali.student.lum.lu.naturallanguage.contexts;

import java.util.HashMap;
import java.util.Map;

import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.lum.lu.entity.ReqComponent;
import org.kuali.student.lum.lu.naturallanguage.util.CustomCluSet;
import org.kuali.student.lum.lu.naturallanguage.util.ReqCompTypes;

public class CourseListContext extends AbstractContext {

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
        
        contextMap.put(ReqCompTypes.VelocityToken.EXPECTED_VALUE_KEY.getKey(), map.get(ReqCompTypes.ReqCompFieldDefinitions.REQUIRED_COUNT_KEY.getKey()));
        contextMap.put(ReqCompTypes.VelocityToken.OPERATOR_KEY.getKey(), map.get(ReqCompTypes.ReqCompFieldDefinitions.OPERATOR_KEY.getKey()));

        if(map.containsKey(ReqCompTypes.ReqCompFieldDefinitions.CLU_KEY.getKey())) {
        	String cluIds = map.get(ReqCompTypes.ReqCompFieldDefinitions.CLU_KEY.getKey());
        	CustomCluSet cluSet = super.getClusAsCluSet(cluIds);
        	contextMap.put(ReqCompTypes.VelocityToken.CLU_SET_KEY.getKey(), cluSet);
        } else if(map.containsKey(ReqCompTypes.ReqCompFieldDefinitions.CLUSET_KEY.getKey())) {
        	String cluSetId = map.get(ReqCompTypes.ReqCompFieldDefinitions.CLUSET_KEY.getKey());
            CustomCluSet cluSet = super.getCluSet(cluSetId);
            contextMap.put(ReqCompTypes.VelocityToken.CLU_SET_KEY.getKey(), cluSet);
        } /*else {
        	throw new DoesNotExistException("Invalid ReqComponent field. " +
        			"ReqComponent field must be " + 
        			ReqCompTypes.ReqCompFieldDefinitions.CLU_KEY + " or " + 
        			ReqCompTypes.ReqCompFieldDefinitions.CLUSET_KEY);
        }*/

        return contextMap;
    }
}
