/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.lum.statement.config.context;

import java.util.HashMap;
import java.util.Map;

import org.kuali.student.core.statement.entity.ReqComponent;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.lum.lrc.dto.ResultComponentInfo;
import org.kuali.student.lum.lrc.service.LrcService;
import org.kuali.student.lum.statement.typekey.ReqComponentFieldTypes;

/**
 * This class creates the template context for grade condition type.
 */
public class LrcContextImpl extends BasicContextImpl {
	
	private LrcService lrcService;
	
	/** Total credits template token */ 
	public final static String GRADE_TOKEN = "grade";
    public final static String GRADE_TYPE_TOKEN = "gradeType";	

	public void setLrcService(LrcService lrcService) {
		this.lrcService = lrcService;
	}

	private ResultComponentInfo getResultComponent(String resultComponentId) throws OperationFailedException {
		try {
			return lrcService.getResultComponent(resultComponentId);
		} catch (Exception e) {
			throw new OperationFailedException(e.getMessage(), e);
		}
	}
	
	private String getResultValue(ResultComponentInfo resultComponent, String resultValue) throws OperationFailedException {
		for(String rv : resultComponent.getResultValues()) {
			if(rv.equals(resultValue)) {
				return rv;
			}
		}
		throw new OperationFailedException("Result value not found: "+resultValue);
	}
	
    /**
     * Creates the context map (template data) for the requirement component.
     * 
     * @param reqComponent Requirement component
     * @throws OperationFailedException Creating context map fails
     */
    public Map<String, Object> createContextMap(ReqComponent reqComponent) throws OperationFailedException {
        Map<String, Object> contextMap = new HashMap<String, Object>();

        String gradeTypeId = getReqComponentFieldValue(reqComponent, ReqComponentFieldTypes.GRADE_TYPE_KEY.getId());
        ResultComponentInfo gradeTypeResultComponent = getResultComponent(gradeTypeId);
        contextMap.put(GRADE_TYPE_TOKEN, gradeTypeResultComponent);

        String gradeId = getReqComponentFieldValue(reqComponent, ReqComponentFieldTypes.GRADE_KEY.getId());
        String grade = getResultValue(gradeTypeResultComponent, gradeId);
        contextMap.put(GRADE_TOKEN, grade);
        return contextMap;
    }
}
