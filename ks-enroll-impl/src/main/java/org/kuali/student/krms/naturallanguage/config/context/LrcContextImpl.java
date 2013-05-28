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

package org.kuali.student.krms.naturallanguage.config.context;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.r2.core.constants.TypeServiceConstants;
import org.kuali.student.r2.core.krms.naturallanguage.TermParameterTypes;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.lum.lrc.dto.ResultScaleInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;

import javax.xml.namespace.QName;
import java.util.Map;
import org.kuali.rice.core.api.exception.RiceIllegalStateException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.ContextUtils;

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
        if(typeService == null){
            typeService = (TypeService) GlobalResourceLoader.getService(new QName(TypeServiceConstants.NAMESPACE, TypeServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return typeService;
    }
    public LRCService getLrcService() {
        if(lrcService == null){
            lrcService = GlobalResourceLoader.getService(new QName(LrcServiceConstants.NAMESPACE, LrcServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return lrcService;
    }
    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
    }

    private ResultScaleInfo getResultScale(String resultScaleId, ContextInfo contextInfo)  {
        if (resultScaleId == null) {
            return null;
        }
        try {
            return this.getLrcService().getResultScale(resultScaleId, contextInfo);
        } catch (Exception e) {
            throw new RiceIllegalStateException (e);
        }
    }
    private ResultValuesGroupInfo getResultValuesGroupByResultValueGroupId(String resultValueGroupId, ContextInfo contextInfo ) throws OperationFailedException {
        if (resultValueGroupId == null) {
            return null;
        }
        try {
            return this.getLrcService().getResultValuesGroup(resultValueGroupId,contextInfo);
        } catch (Exception e) {
            throw new RiceIllegalStateException (e);
        }
    }

    private ResultValueInfo getResultValue(String resultValueId, ContextInfo contextInfo)  {
        try {
            return this.getLrcService().getResultValue(resultValueId,contextInfo);
        } catch (Exception e) {
            throw new RiceIllegalStateException (e);
        }

    }

    /**
     * Creates the context map (template data) for the requirement component.
     *
     * @param parameters
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException Creating context map fails
     */
    @Override
    public Map<String, Object> createContextMap(Map<String, Object> parameters) {
        ContextInfo contextInfo = ContextUtils.getContextInfo();
        Map<String, Object> contextMap = super.createContextMap(parameters);

        String gradeId = (String) parameters.get(TermParameterTypes.GRADE_KEY.getId());
        if (gradeId == null) {
            gradeId = (String) parameters.get(TermParameterTypes.GRADE_TYPE_KEY.getId());
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
