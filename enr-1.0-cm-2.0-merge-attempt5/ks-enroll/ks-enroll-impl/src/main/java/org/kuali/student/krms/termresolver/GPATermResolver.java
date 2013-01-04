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
import org.kuali.student.enrollment.academicrecord.dto.GPAInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.academicrecord.service.AcademicRecordService;
import org.kuali.student.krms.util.KSKRMSExecutionConstants;
import org.kuali.student.krms.util.KSKRMSExecutionUtil;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
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

public class GPATermResolver implements TermResolver<Integer> {

    private AcademicRecordService academicRecordService;

    private final static Set<String> prerequisites = new HashSet<String>(1);

    static {
        prerequisites.add(KSKRMSExecutionConstants.PERSON_ID_TERM_PROPERTY);
        prerequisites.add(KSKRMSExecutionConstants.CONTEXT_INFO_TERM_NAME);
    }
    
    public AcademicRecordService getAcademicRecordService() {
        return academicRecordService;
    }

    public void setAcademicRecordService(AcademicRecordService academicRecordService) {
        this.academicRecordService = academicRecordService;
    }

    @Override
    public Set<String> getPrerequisites() {
        return prerequisites;
    }

    @Override
    public String getOutput() {
        return KSKRMSExecutionConstants.GPA_TERM_NAME;
    }

    @Override
    public Set<String> getParameterNames() {
        return Collections.singleton(KSKRMSExecutionConstants.COURSE_CODE_TERM_PROPERTY);
    }

    @Override
    public int getCost() {
        // TODO Analyze, though probably not much to check here
        return 5;
    }

    @Override
    public Integer resolve(Map<String, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {
        ContextInfo context = (ContextInfo) resolvedPrereqs.get(KSKRMSExecutionConstants.CONTEXT_INFO_TERM_NAME);
        String personId = (String) resolvedPrereqs.get(KSKRMSExecutionConstants.PERSON_ID_TERM_PROPERTY);
        String courseCodes = parameters.get(KSKRMSExecutionConstants.COURSE_CODE_TERM_PROPERTY);
        
        List<StudentCourseRecordInfo> studentCourseRecordInfoList = null;
        Integer result = null;
        try {
            studentCourseRecordInfoList = academicRecordService.getCompletedCourseRecords(personId, context);
        } catch (InvalidParameterException e) {
            throw new TermResolutionException(e.getMessage(), this, parameters);
        } catch (MissingParameterException e) {
            throw new TermResolutionException(e.getMessage(), this, parameters);
        } catch (OperationFailedException e) {
            throw new TermResolutionException(e.getMessage(), this, parameters);
        } catch (PermissionDeniedException e) {
            throw new TermResolutionException(e.getMessage(), this, parameters);
        } catch (DoesNotExistException e) {
            throw new TermResolutionException(e.getMessage(), this, parameters);
        }

        courseCodes.trim();
        String[] courseCode = courseCodes.split(",");

        if(courseCodes.contains(",")) {
            for(StudentCourseRecordInfo si : studentCourseRecordInfoList) {
                for(String cc : courseCode) {
                    if(cc.equals(si.getCourseCode())){
                        result += Integer.parseInt(si.getCreditsForGPA());
                    }
                }
            }
        } else {
            for(StudentCourseRecordInfo temp : studentCourseRecordInfoList) {
                if(temp.getCourseCode().equals(courseCodes)){
                    result = Integer.parseInt(temp.getCreditsForGPA());
                }
            }
        }

        return result;
    }
}
