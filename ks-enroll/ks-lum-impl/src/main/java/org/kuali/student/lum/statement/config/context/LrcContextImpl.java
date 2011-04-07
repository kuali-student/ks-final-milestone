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
import java.util.List;
import java.util.Map;

import org.kuali.student.common.exceptions.OperationFailedException;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.lum.lrc.dto.ResultComponentInfo;
import org.kuali.student.lum.lrc.dto.ResultComponentTypeInfo;
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

	private ResultComponentInfo getResultComponentByResultComponentId(String resultComponentId) throws OperationFailedException {
		if (resultComponentId == null) {
			return null;
		}
		try {
			return lrcService.getResultComponent(resultComponentId);
		} catch (Exception e) {
			throw new OperationFailedException(e.getMessage(), e);
		}
	}
	
	private String getResultValue(ResultComponentInfo resultComponent, String resultValue) throws OperationFailedException {
		if(resultComponent == null || resultValue == null) {
			return null;
		}
		for(String rv : resultComponent.getResultValues()) {
			if(rv.equals(resultValue)) {
				return rv;
			}
		}
		throw new OperationFailedException("Result value not found: "+resultValue);
	}
	
	private ResultComponentInfo getResultComponentByResultValueId(String resultValueId) throws OperationFailedException {
		if(resultValueId == null) {
			return null;
		}
		
		try {
			List<ResultComponentTypeInfo> typeList = lrcService.getResultComponentTypes();
			for(ResultComponentTypeInfo type : typeList) {
				List<String> resultComponentIdList = lrcService.getResultComponentIdsByResultComponentType(type.getId());
				for(String resultComponentId : resultComponentIdList) {
					ResultComponentInfo resultComponent = lrcService.getResultComponent(resultComponentId);
					if(resultComponent.getResultValues().contains(resultValueId)) {
						return resultComponent;
					}
				}
			}
		} catch (Exception e) {
			throw new OperationFailedException(e.getMessage(), e);
		}
		throw new OperationFailedException("Result value id not found: "+resultValueId);
	}
	
    /**
     * Creates the context map (template data) for the requirement component.
     * 
     * @param reqComponent Requirement component
     * @throws OperationFailedException Creating context map fails
     */
    public Map<String, Object> createContextMap(ReqComponentInfo reqComponent) throws OperationFailedException {
        Map<String, Object> contextMap = new HashMap<String, Object>();

//        String gradeTypeId = getReqComponentFieldValue(reqComponent, ReqComponentFieldTypes.GRADE_TYPE_KEY.getId());
//        ResultComponentInfo gradeTypeResultComponent = getResultComponentByResultComponentId(gradeTypeId);
//        if(gradeTypeResultComponent != null) {
//	        contextMap.put(GRADE_TYPE_TOKEN, gradeTypeResultComponent);
//        }
//	
//        String gradeId = getReqComponentFieldValue(reqComponent, ReqComponentFieldTypes.GRADE_KEY.getId());
//        String grade = getResultValue(gradeTypeResultComponent, gradeId);
//        if(grade != null) {
//        	contextMap.put(GRADE_TOKEN, grade);
//        }

    	
        ResultComponentInfo gradeTypeResultComponent = null;
        String gradeId = getReqComponentFieldValue(reqComponent, ReqComponentFieldTypes.GRADE_KEY.getId());
        if (gradeId == null) {
			String gradeTypeId = getReqComponentFieldValue(reqComponent, ReqComponentFieldTypes.GRADE_TYPE_KEY.getId());
			gradeTypeResultComponent = getResultComponentByResultComponentId(gradeTypeId);
			if (gradeTypeResultComponent != null) {
				contextMap.put(GRADE_TYPE_TOKEN, gradeTypeResultComponent);
			}
        } else {
        	gradeTypeResultComponent = getResultComponentByResultValueId(gradeId);
        }
		if (gradeTypeResultComponent != null) {
			contextMap.put(GRADE_TYPE_TOKEN, gradeTypeResultComponent);
		}

        String grade = getResultValue(gradeTypeResultComponent, gradeId);
        if(grade != null) {
        	contextMap.put(GRADE_TOKEN, grade);
        }

        return contextMap;
    }
}
