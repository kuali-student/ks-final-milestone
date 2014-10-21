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
package org.kuali.student.enrollment.class2.courseoffering.service;

import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingListSectionWrapper;
import org.kuali.student.enrollment.class2.courseoffering.form.CourseOfferingManagementForm;
import org.kuali.student.enrollment.class2.scheduleofclasses.form.ActivityOfferingDisplayUI;
import org.kuali.student.enrollment.class2.scheduleofclasses.form.CourseOfferingDisplayUI;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;

import java.util.List;
/**
 * This class defines an interface for helper methods related to the Course Offering Management ui
 *
 * @author Kuali Student Team
 */
public interface CourseOfferingManagementViewHelperService extends CO_AO_RG_ViewHelperService {
    public void populateTerm(CourseOfferingManagementForm form) throws Exception;

    public void loadCourseOfferingsByTermAndCourseCode(TermInfo termInfo, String courseCode, CourseOfferingManagementForm form) throws Exception;

    public void loadCourseOfferingsByTermAndSubjectCode(String termId, String subjectCode, CourseOfferingDisplayUI form) throws Exception;

    public void loadActivityOfferingsByCourseOffering (CourseOfferingInfo theCourseOfferingInfo, CourseOfferingManagementForm form) throws Exception;

    public void createActivityOfferings(String formatOfferingId,String activityId,int noOfActivityOfferings, CourseOfferingManagementForm form);

    public void markCourseOfferingsForScheduling(List<CourseOfferingListSectionWrapper> coWrappers, String viewId, String socStateKey, boolean checkedOnly) throws Exception;

    public void loadPreviousAndNextCourseOffering(CourseOfferingManagementForm form);

    public List<ActivityOfferingWrapper> getActivityOfferingsByCourseOfferingId (String courseOfferingId, CourseOfferingManagementForm form) throws Exception;

    public void approveCourseOfferings(CourseOfferingManagementForm form) throws Exception;
    public void deleteCourseOfferings(CourseOfferingManagementForm form) throws Exception;
    public void approveActivityOfferings(CourseOfferingManagementForm form) throws Exception;
    public void draftActivityOfferings(CourseOfferingManagementForm form) throws Exception;

    public void build_AOs_RGs_AOCs_Lists_For_TheCourseOffering (ActivityOfferingDisplayUI form) throws Exception;
    public void build_AOs_RGs_AOCs_Lists_For_TheCourseOffering (ActivityOfferingDisplayUI form, SearchRequestInfo searchRequestInfo, boolean socView) throws Exception;

    public void setupRuleIndicator(List<ActivityOfferingWrapper> wrappers);
    public void loadExamOfferingRelations(CourseOfferingManagementForm theForm) throws Exception;
    public void loadExamOfferings(CourseOfferingManagementForm theForm) throws Exception;
}
