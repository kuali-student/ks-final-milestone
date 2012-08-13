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

package org.kuali.student.r2.lum.statement.config.context;

import java.util.HashMap;
import java.util.Map;
import java.util.List   ;
import org.kuali.student.r1.lum.lrc.dto.ResultComponentTypeInfo;

import org.kuali.student.r1.core.statement.dto.ReqComponentInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r1.lum.lrc.service.LrcService;
import org.kuali.student.r1.lum.statement.typekey.ReqComponentFieldTypes;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.dto.ContextInfo;

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

	private ResultValuesGroupInfo getResultComponentByResultComponentId(String resultComponentId, ContextInfo contextInfo) throws OperationFailedException {
		if (resultComponentId == null) {
			return null;
		}
		try {


			return  lrcService.getResultValuesGroup(resultComponentId, contextInfo);
		} catch (Exception e) {
			throw new OperationFailedException(e.getMessage(), e);
		}
	}
	
	private String getResultValue(ResultValuesGroupInfo resultComponent, String resultValue) throws OperationFailedException {
		if(resultComponent == null || resultValue == null) {
			return null;
		}
		for(String rv : resultComponent.getResultValueKeys()) {
			if(rv.equals(resultValue)) {
				return rv;
			}
		}
		throw new OperationFailedException("Result value not found: "+resultValue);
	}
	
	private ResultValuesGroupInfo getResultComponentByResultValueId(String resultValueId, ContextInfo contextInfo) throws OperationFailedException {
		if(resultValueId == null) {
			return null;
		}
		
		try {

			List<ResultComponentTypeInfo> typeList = lrcService.getResultComponentTypes(contextInfo);
			for(ResultComponentTypeInfo type : typeList) {
				List<String> resultComponentIdList = lrcService.getResultValuesGroupIdsByType(type.getId(), contextInfo);
				for(String resultComponentId : resultComponentIdList) {
				    ResultValuesGroupInfo resultComponent = lrcService.getResultValuesGroup(resultComponentId, contextInfo);
					if(resultComponent.getResultValueKeys().contains(resultValueId)) {
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
     *
     * @param reqComponent Requirement component
     * @throws OperationFailedException Creating context map fails
     */
    public Map<String, Object> createContextMap(ReqComponentInfo reqComponent, ContextInfo contextInfo) throws OperationFailedException {
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

    	
        ResultValuesGroupInfo gradeTypeResultComponent = null;
        String gradeId = getReqComponentFieldValue(reqComponent, ReqComponentFieldTypes.GRADE_KEY.getId());
        if (gradeId == null) {
			String gradeTypeId = getReqComponentFieldValue(reqComponent, ReqComponentFieldTypes.GRADE_TYPE_KEY.getId());
			gradeTypeResultComponent = getResultComponentByResultComponentId(gradeTypeId, contextInfo);
			if (gradeTypeResultComponent != null) {
				contextMap.put(GRADE_TYPE_TOKEN, gradeTypeResultComponent);
			}
        } else {
        	gradeTypeResultComponent = getResultComponentByResultValueId(gradeId, contextInfo);
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
