/**
 * Copyright 2013 The Kuali Foundation Licensed under the
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
 * Created by David Yin on 3/4/13
 */
package org.kuali.student.enrollment.class2.autogen.service;

import org.kuali.student.enrollment.class2.courseoffering.service.CO_AO_RG_ViewHelperService;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingListSectionWrapper;
import org.kuali.student.enrollment.class2.autogen.form.ARGCourseOfferingManagementForm;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;

import java.util.List;
/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public interface ARGCourseOfferingManagementViewHelperService extends CO_AO_RG_ViewHelperService {
    public void populateTerm(ARGCourseOfferingManagementForm form) throws Exception;

    public void loadCourseOfferingsByTermAndCourseCode(String termId, String courseCode, ARGCourseOfferingManagementForm form) throws Exception;

    public void loadCourseOfferingsByTermAndSubjectCode(String termId, String subjectCode, ARGCourseOfferingManagementForm form) throws Exception;

    public void loadActivityOfferingsByCourseOffering (CourseOfferingInfo theCourseOfferingInfo, ARGCourseOfferingManagementForm form) throws Exception;

    public void createActivityOfferings(String formatOfferingId,String activityId,int noOfActivityOfferings, ARGCourseOfferingManagementForm form);

    public void changeActivityOfferingsState(List<ActivityOfferingWrapper> aoList, CourseOfferingInfo courseOfferingInfo, String selectedAction) throws Exception;

    public void markCourseOfferingsForScheduling(List<CourseOfferingListSectionWrapper> coWrappers) throws Exception;

    public void loadPreviousAndNextCourseOffering(ARGCourseOfferingManagementForm form);

    public List<ActivityOfferingWrapper> getActivityOfferingsByCourseOfferingId (String courseOfferingId, ARGCourseOfferingManagementForm form) throws Exception;

    public void approveCourseOfferings(ARGCourseOfferingManagementForm form) throws Exception;
    public void deleteCourseOfferings(ARGCourseOfferingManagementForm form) throws Exception;
    public void approveActivityOfferings(ARGCourseOfferingManagementForm form) throws Exception;
    public void draftActivityOfferings(ARGCourseOfferingManagementForm form) throws Exception;

}
