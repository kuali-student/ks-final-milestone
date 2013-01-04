/**
 * Copyright 2011 The Kuali Foundation Licensed under the
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

package org.kuali.student.krms.termresolver;

import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.academicrecord.service.AcademicRecordService;
import org.kuali.student.krms.util.KSKRMSExecutionConstants;
import org.kuali.student.krms.util.KSKRMSExecutionUtil;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.atp.service.AtpService;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EffectiveDateToTermResolver implements TermResolver<List<StudentCourseRecordInfo>> {	

    private AcademicRecordService academicRecordService;
    
    
    public AcademicRecordService getAcademicRecordService() {
        return academicRecordService;
    }

    public void setAcademicRecordService(AcademicRecordService academicRecordService) {
        this.academicRecordService = academicRecordService;
    }

    @Override
    public Set<String> getPrerequisites() {
        return Collections.singleton(RulesExecutionConstants.CONTEXT_INFO_TERM_NAME);
    }

    @Override
    public String getOutput() {
        return "EffectiveDateToTermResolver.getOutput()";
    }

    @Override
    public Set<String> getParameterNames() {
        /*Set<String> temp = new HashSet<String>(1);
        temp.add(KSKRMSExecutionConstants.PERSON_ID_TERM_PROPERTY);
        return Collections.unmodifiableSet(temp);*/
    	return null;
    }

    @Override
    public int getCost() {
        // TODO Analyze, though probably not much to check here
        return 5;
    }

    @Override
    public List<StudentCourseRecordInfo> resolve(Map<String, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {
        /*ContextInfo context = (ContextInfo) resolvedPrereqs.get(RulesExecutionConstants.CONTEXT_INFO_TERM_NAME);
        String personId = parameters.get(KSKRMSExecutionConstants.PERSON_ID_TERM_PROPERTY);
        
        List<StudentCourseRecordInfo> result = null;
        try {
            result = academicRecordService.???(personId, context);
        } catch (Exception e) {
            KSKRMSExecutionUtil.convertExceptionsToTermResolutionException(parameters, e, this);
        }
        return result;*/
    	return null;
    }
}
