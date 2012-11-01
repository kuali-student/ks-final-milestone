/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 *
 * Created by Charles on 9/10/12
 */
package org.kuali.student.enrollment.class2.courseoffering.service.transformer;

import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingDisplayInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.KeyNameInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.class1.state.dto.StateInfo;
import org.kuali.student.r2.core.class1.state.service.StateService;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.lum.lrc.service.LRCService;

import java.util.ArrayList;
import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class CourseOfferingDisplayTransformer {
    public static CourseOfferingDisplayInfo co2coDisplay(CourseOfferingInfo coInfo,
                                                         AtpService atpService,
                                                         StateService stateService,
                                                         TypeService typeService,
                                                         LRCService lrcService,
                                                         ContextInfo context)
            throws InvalidParameterException, MissingParameterException, DoesNotExistException,
            PermissionDeniedException, OperationFailedException {
        CourseOfferingDisplayInfo displayInfo = new CourseOfferingDisplayInfo();
        displayInfo.setId(coInfo.getId());
        // Fields use in course offering display info
        // descr, courseId,
        displayInfo.setDescr(coInfo.getDescr());
        displayInfo.setCourseId(coInfo.getCourseId());
        //  termId, courseOfferingTitle,  courseOfferingCode,
        displayInfo.setTermId(coInfo.getTermId());
        displayInfo.setCourseOfferingTitle(coInfo.getCourseOfferingTitle());
        displayInfo.setCourseOfferingCode(coInfo.getCourseOfferingCode());
        // subjectArea,
        displayInfo.setSubjectArea(coInfo.getSubjectArea());
        // termName, termCode
        AtpInfo atpInfo = atpService.getAtp(coInfo.getTermId(), context);
        displayInfo.setTermName(atpInfo.getName());
        displayInfo.setTermCode(atpInfo.getCode());

        // gradingOptionName, creditOptionName,
        displayInfo.setGradingOption(new KeyNameInfo(coInfo.getGradingOptionId(), coInfo.getGradingOptionName()));
        displayInfo.setCreditOption(new KeyNameInfo(coInfo.getCreditOptionId(), coInfo.getCreditCnt()));

        // studentRegistrationGradingOptions: I use key for name as well, because we don't use name really otherwise
        // we have to call rvg = getLrcService().getResultValuesGroup(studentGradingOption, contextInfo); to get rvg.getName() as a name
        if (!coInfo.getStudentRegistrationGradingOptions().isEmpty()) {
            List<KeyNameInfo> studentGradingOptionList = new ArrayList<KeyNameInfo>();
            for (String studentGradingOption : coInfo.getStudentRegistrationGradingOptions()) {
                studentGradingOptionList.add(new KeyNameInfo(studentGradingOption, studentGradingOption));
            }
            displayInfo.setStudentRegistrationGradingOptions(studentGradingOptionList);
        }

        displayInfo.setHonorsOffering(coInfo.getIsHonorsOffering());

        // typeName, stateName
        TypeInfo typeInfo = typeService.getType(coInfo.getTypeKey(), context);
        displayInfo.setTypeName(typeInfo.getName());
        StateInfo stateInfo = stateService.getState(coInfo.getStateKey(), context);
        displayInfo.setStateName(stateInfo.getName());

        return displayInfo;
    }
}
