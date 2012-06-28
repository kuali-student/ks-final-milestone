/**
 * Copyright 2012 The Kuali Foundation
 *
 * Licensed under the the Educational Community License, Version 1.0
 * (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.joda.time.DateTime;
import org.kuali.student.enrollment.acal.constants.AcademicCalendarServiceConstants;
import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FinalExam;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;
import org.kuali.student.r2.common.util.constants.LrcServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;

/**
 * @author ocleirig
 *
 */
public final class CourseOfferingServiceDataUtils {

	
		// Copied from TestCourseOfferingServiceWithMocks
		public static ActivityOfferingInfo createActivityOffering(
				String formatOfferingId, String activityId) {
			ActivityOfferingInfo orig = new ActivityOfferingInfo();
			orig.setFormatOfferingId(formatOfferingId);
			orig.setActivityId(activityId);
			orig.setTypeKey(LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY);
			orig.setStateKey(LuiServiceConstants.LUI_DRAFT_STATE_KEY);
			orig.setMinimumEnrollment(100);
			orig.setMaximumEnrollment(150);
			orig.setIsEvaluated(true);
			orig.setIsMaxEnrollmentEstimate(false);
			orig.setIsHonorsOffering(true);

			return orig;
		}

		// Copied from TestCourseOfferingServiceWithMocks
		public static FormatOfferingInfo createFormatOffering(String courseOfferingId,
				String formatId) {

			FormatOfferingInfo orig = new FormatOfferingInfo();
			orig.setCourseOfferingId(courseOfferingId);
			orig.setFormatId(formatId);
			orig.setActivityOfferingTypeKeys(Arrays
					.asList(LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY));
			orig.setTypeKey(LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY);
			orig.setStateKey(LuiServiceConstants.LUI_DRAFT_STATE_KEY);

			return orig;
		}
		
		// copied from TestCourseOfferingServiceWithMocks
		public static CourseOfferingInfo createCourseOffering(String courseId,
				String termId, String courseTitle, ) {
			CourseOfferingInfo orig = new CourseOfferingInfo();
			orig.setCourseId(courseId);
			orig.setTermId(termId);
			orig.setTypeKey(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY);
			orig.setStateKey(LuiServiceConstants.COURSE_OFFERING_PROCESS_STATE_KEYS[0]);
			orig.setCourseOfferingTitle("my name");
			orig.setWaitlistLevelTypeKey("waitlist key");
			orig.setHasWaitlist(true);
			orig.setFinalExamType(FinalExam.STANDARD.toString());
			orig.setEvaluated(true);
			orig.setFeeAtActivityOffering(false);
			orig.setFundingSource("funding source");
			orig.setCourseOfferingCode("CODE");
			orig.setCourseOfferingTitle("Title");
			orig.getStudentRegistrationOptionIds().add(
					LrcServiceConstants.RESULT_GROUP_KEY_GRADE_AUDIT);
			orig.getStudentRegistrationOptionIds().add(
					LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PASSFAIL);
			orig.getRegistrationGradingOptionIds().add(
					LrcServiceConstants.RESULT_GROUP_KEY_GRADE_LETTER);
			orig.getRegistrationGradingOptionIds().add(
					LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PERCENTAGE);

			return orig;
		}

		

		// copied from TestAcademicCalendarServiceWithMocks
		public static TermInfo createTerm(String termName, String plainName)
				throws DataValidationErrorException, DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException, PermissionDeniedException,
				ReadOnlyException {
			TermInfo orig = new TermInfo();
			orig.setName(termName);
			orig.setDescr(new RichTextHelper().toRichTextInfo(plainName,
					"formatted " + plainName));
			orig.setTypeKey(AtpServiceConstants.ATP_FALL_TYPE_KEY);
			orig.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
			orig.setStartDate(new Date());
			orig.setEndDate(new DateTime().plusDays(121).toDate());

			return orig;
		}

	// Copied from TestAcademicCalendarSerciceImplWithMocks
	public static AcademicCalendarInfo createAcademicCalendar(String calendarName, String plainName) {
		 AcademicCalendarInfo orig = new AcademicCalendarInfo();
	        orig.setName(calendarName);
	        orig.setDescr(new RichTextHelper().toRichTextInfo(plainName, "formatted " + plainName));
	        orig.setTypeKey(AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY);
	        orig.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
	        DateTime start = new DateTime();
	        orig.setStartDate(start.toDate());
	        orig.setEndDate(start.plusMonths(4).toDate());
	        orig.setAdminOrgId("testOrgId1");
	        
	        return orig;
	        
	}
	

}
