/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/lic enses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.lum.statement.config.context;

import org.kuali.student.r1.core.statement.dto.ReqComponentInfo;
import org.kuali.student.r1.lum.statement.typekey.ReqComponentFieldTypes;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.lum.lrc.dto.ResultScaleInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;

import java.util.HashMap;
import java.util.Map;

/**
 * This class creates the template context for grade condition type.
 */
public class LrcContextImpl extends BasicContextImpl {

    private LRCService lrcService;
    private TypeService typeService;

    /**
     * Total credits template token
     */
    public final static String GRADE_TOKEN = "grade";
    public final static String GRADE_TYPE_TOKEN = "gradeType";

    public void setLrcService(LRCService lrcService) {
        this.lrcService = lrcService;
    }

    public TypeService getTypeService() {
        return typeService;
    }

    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
    }

    private ResultScaleInfo getResultScale(String resultScaleId, ContextInfo contextInfo) throws OperationFailedException {
        if (resultScaleId == null) {
            return null;
        }
        try {
            return lrcService.getResultScale(resultScaleId, contextInfo);
        } catch (Exception e) {
            throw new OperationFailedException(e.getMessage(), e);
        }
    }
    private ResultValuesGroupInfo getResultValuesGroupByResultValueGroupId(String resultValueGroupId, ContextInfo contextInfo ) throws OperationFailedException {
        if (resultValueGroupId == null) {
            return null;
        }
        try {
            return lrcService.getResultValuesGroup(resultValueGroupId,contextInfo);
        } catch (Exception e) {
            throw new OperationFailedException(e.getMessage(), e);
        }
    }

    private ResultValueInfo getResultValue(String resultValueId, ContextInfo contextInfo) throws OperationFailedException {
        try {
            return lrcService.getResultValue(resultValueId,contextInfo);
        } catch (Exception e) {
            throw new OperationFailedException(e.getMessage(), e);
        }

    }

    /**
     * Creates the context map (template data) for the requirement component.
     *
     * @param reqComponent Requirement component
     * @throws OperationFailedException Creating context map fails
     */
    public Map<String, Object> createContextMap(ReqComponentInfo reqComponent, ContextInfo contextInfo) throws OperationFailedException {
        Map<String, Object> contextMap = new HashMap<String, Object>();

        String gradeId = getReqComponentFieldValue(reqComponent, ReqComponentFieldTypes.GRADE_KEY.getId());
        if (gradeId == null) {
            gradeId = getReqComponentFieldValue(reqComponent, ReqComponentFieldTypes.GRADE_TYPE_KEY.getId());
        }

        if (gradeId != null){
        ResultValueInfo grade = getResultValue(gradeId, contextInfo);
            if (grade != null) {
                contextMap.put(GRADE_TOKEN, grade.getValue());
                contextMap.put(GRADE_TYPE_TOKEN, getResultScale(grade.getResultScaleKey(),contextInfo));
            }
        }
        return contextMap;
    }
}
