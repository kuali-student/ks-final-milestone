/**
 * Copyright 2014 The Kuali Foundation Licensed under the
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
 * Created by prasannag on 1/9/14
 */
package org.kuali.student.cm.course.service.impl;

import org.apache.commons.lang.BooleanUtils;
import org.joda.time.DateTime;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.student.cm.common.util.CurriculumManagementConstants;
import org.kuali.student.cm.course.form.wrapper.CluInstructorInfoWrapper;
import org.kuali.student.cm.course.form.wrapper.RetireCourseWrapper;
import org.kuali.student.cm.course.service.RetireCourseMaintainable;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.r2.common.dto.DtoConstants;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.util.constants.CourseServiceConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class handles the operation related to course retire
 *
 * @author Kuali Student Team
 */
public class RetireCourseMaintainableImpl extends CourseMaintainableImpl implements RetireCourseMaintainable {

    @Override
    public void processAfterNew(MaintenanceDocument document, Map<String, String[]> requestParameters) {

        RetireCourseWrapper retireCourseWrapper = (RetireCourseWrapper) getDataObject();

        // We can actually get this from the workflow document initiator id. It doesn't need to be stored in the form.
        retireCourseWrapper.setUserId(ContextUtils.createDefaultContextInfo().getPrincipalId());

        // Initialize Course Requisites
        retireCourseWrapper.setNamespace(KSKRMSServiceConstants.NAMESPACE_CODE);
        retireCourseWrapper.setRefDiscriminatorType(CourseServiceConstants.REF_OBJECT_URI_COURSE);
        retireCourseWrapper.setRefObjectId(retireCourseWrapper.getCourseInfo().getId());
        retireCourseWrapper.setAgendas(getAgendasForRef(retireCourseWrapper.getRefDiscriminatorType(), retireCourseWrapper.getRefObjectId()));

        retireCourseWrapper.getCourseInfo().setStateKey(DtoConstants.STATE_DRAFT);
        retireCourseWrapper.setLastUpdated(CurriculumManagementConstants.CM_DATE_FORMATTER.format(new DateTime()));
        retireCourseWrapper.getCourseInfo().setEffectiveDate(new java.util.Date());

    }

    public void populateCourseData(String courseId, RetireCourseWrapper courseWrapper) throws Exception {

        CourseInfo course = getCourseService().getCourse(courseId, createContextInfo());
        courseWrapper.setCourseInfo(course);
    }



}
