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
import org.kuali.student.krms.util.KSKRMSExecutionUtil;
import org.kuali.student.krms.util.KSKRMSExecutionConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CompletedCourseTermResolver implements TermResolver<StudentCourseRecordInfo> {	

	private AcademicRecordService acadRecordService;
	
    @Override
    public Set<String> getPrerequisites() {
        return Collections.emptySet();
    }

    @Override
    public String getOutput() {
        return this.getClass().getSimpleName();
    }

    @Override
    public Set<String> getParameterNames() {
    	Set<String> temp = new HashSet<String>(2);
        temp.add(KSKRMSExecutionConstants.PERSON_ID_TERM_PROPERTY);
        temp.add(KSKRMSExecutionConstants.COURSE_CODE_TERM_PROPERTY);

        return Collections.unmodifiableSet(temp);
    }

    @Override
    public int getCost() {
        // TODO Analyze, though probably not much to check here
        return 0;
    }



	public AcademicRecordService getAcadRecordService() {
		return acadRecordService;
	}

	public void setAcadRecordService(AcademicRecordService acadRecordService) {
		this.acadRecordService = acadRecordService;
	}

	@Override
	public StudentCourseRecordInfo resolve(Map<String, Object> resolvedPrereqs, Map<String, String> parameters)
			throws TermResolutionException {
		// Get the parameters for the Term
        String personId = parameters.get(KSKRMSExecutionConstants.PERSON_ID_TERM_PROPERTY);
        String courseCode = parameters.get(KSKRMSExecutionConstants.COURSE_CODE_TERM_PROPERTY);
        ContextInfo context = (ContextInfo) resolvedPrereqs.get(KSKRMSExecutionConstants.CONTEXT_INFO_TERM_NAME);
		//
		try {
			List<StudentCourseRecordInfo> completedCourseRecords = getAcadRecordService().getCompletedCourseRecords(personId, context);
			for (StudentCourseRecordInfo studentCourseRecordInfo : completedCourseRecords) {
				if (studentCourseRecordInfo.getCourseCode().equals(courseCode)) {
					return studentCourseRecordInfo;
				}
			}
			
		} catch (Exception e) {
			KSKRMSExecutionUtil.convertExceptionsToTermResolutionException(parameters, e, this);
		}
		return null;
	}

}
