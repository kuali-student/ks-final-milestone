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
import java.util.List;

import org.joda.time.DateTime;
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FinalExam;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.lpr.dto.LprInfo;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.common.util.constants.LrcServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.lum.clu.dto.LuCodeInfo;

/**
 * 
 * Helper methods for creating the CourseOfferingService class2 objects.
 * 
 * This was originally created to assist with unit testing the CourseOfferingServiceMockImpl
 * 
 * @author ocleirig
 * 
 *
 */
public final class CourseOfferingServiceDataUtils {

	
	/**
	 * Create and initialize an ActivityOffering using some base data aswell as the parameters given.
	 * 
	 * @param formatOfferingId
	 * @param activityId
	 * @param scheduleId
	 * @param activityName
	 * @param instructors
	 * @return
	 */
		// Copied from TestCourseOfferingServiceWithMocks
		// and flushed out using the ActivityOfferingTransformer
		public static ActivityOfferingInfo createActivityOffering(
				String formatOfferingId, String scheduleId, String activityId, String activityName, String activityCode, List<OfferingInstructorInfo> instructors) {
			
			ActivityOfferingInfo orig = new ActivityOfferingInfo();
			
			 orig.setId(UUIDHelper.genStringUUID());
			 
			 orig.setTypeKey(LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY);
			orig.setStateKey(LuiServiceConstants.LUI_DRAFT_STATE_KEY);
				
			orig.setFormatOfferingId(formatOfferingId);
			orig.setActivityId(activityId);
			
			orig.setActivityCode(activityCode);
			
			orig.setScheduleId(scheduleId);
			orig.setActivityOfferingURL("http://activity.com");
		
			
			orig.setDescr(new RichTextInfo(activityName, "<b>"+ activityName +"</b>"));
			orig.setName(activityName);
			orig.setMinimumEnrollment(100);
			orig.setMaximumEnrollment(150);
			
			orig.setIsEvaluated(true);
			orig.setIsMaxEnrollmentEstimate(false);
			orig.setIsHonorsOffering(true);
			
			orig.setInstructors(instructors);

			return orig;
		}

		/**
		 * Create and initialize a FormatOffering using some base data aswell as the parameters given.
		 * 		
		 * @param courseOfferingId
		 * @param formatId
		 * @param termId
		 * @param formatName
		 * @return
		 */
		// Copied from TestCourseOfferingServiceWithMocks
		// fleshed out with details from FormatOfferingTransformer
		public static FormatOfferingInfo createFormatOffering(String courseOfferingId,
				String formatId, String termId, String formatName) {

			FormatOfferingInfo orig = new FormatOfferingInfo();

		    orig.setId(UUIDHelper.genStringUUID());
		    orig.setCourseOfferingId(courseOfferingId);
			orig.setFormatId(formatId);
			orig.setTermId(termId);
			
		    orig.setTypeKey(LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY);
		    orig.setStateKey(LuiServiceConstants.COURSE_OFFERING_PROCESS_STATE_KEYS[0]);
		    
		    orig.setName(formatName);
		    
		    orig.setDescr(new RichTextInfo(formatName, "<b>" + formatName + "</b>"));
		    
			orig.setGradeRosterLevelTypeKey(CourseOfferingServiceConstants.GRADE_ROSTER_LEVEL_TYPE_KEY_ATTR);
			orig.setFinalExamLevelTypeKey(CourseOfferingServiceConstants.FINAL_EXAM_LEVEL_TYPE_KEY_ATTR);
			
			orig.setActivityOfferingTypeKeys(Arrays
					.asList(LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY));
			orig.setTypeKey(LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY);
			orig.setStateKey(LuiServiceConstants.LUI_DRAFT_STATE_KEY);
			
			return orig;
		}
		
		/**
		 * Create a CourseOfferingInfo object with some defaults and the parameter values provided.
		 * 
		 * @param courseId The unique database id for the course
		 * @param termId The unique database id for the term
		 * @param courseTitle The title of the course
		 * @param courseCode The unique code that represents the course (ie ENGL101)
		 * @return The initialized and configured CourseOfferingObject
		 */
		// copied from TestCourseOfferingServiceWithMocks
		public static CourseOfferingInfo createCourseOffering(String courseId,
				String termId, String courseTitle, String courseCode) {
			CourseOfferingInfo orig = new CourseOfferingInfo();
			
			orig.setId(UUIDHelper.genStringUUID());
			orig.setCourseId(courseId);
			orig.setTermId(termId);
			
			orig.setTypeKey(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY);
			orig.setStateKey(LuiServiceConstants.COURSE_OFFERING_PROCESS_STATE_KEYS[0]);
			
			orig.setWaitlistLevelTypeKey("waitlist key");
			orig.setHasWaitlist(true);
			orig.setFinalExamType(FinalExam.STANDARD.toString());
			orig.setIsEvaluated(true);
			orig.setIsFeeAtActivityOffering(false);
			orig.setFundingSource("funding source");
			orig.setIsFinancialAidEligible(true);
			
			orig.setCourseOfferingCode(courseCode);
			orig.setCourseOfferingTitle(courseTitle);
			orig.setCourseOfferingURL("http://courseoffering.com");
			
			List<String> campusLocations = new ArrayList<String>();
			
			campusLocations.add("MAIN");
			
			orig.setCampusLocations(campusLocations);
			
			// TODO: add these methods and more parameters to the method
//			orig.setCourseNumberSuffix(courseNumberSuffix);
//			orig.setCreditOptionDisplay(creditOptionDisplay);
//			orig.setCreditOptionId(creditOptionId);
			
			
			orig.setDescr(new RichTextInfo(courseTitle, "<b>" + courseTitle + "<b>"));
			
			
			
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

	public static OfferingInstructorInfo createInstructor(String personId, String personName, Float percentageEffort) {

		OfferingInstructorInfo instructor = new OfferingInstructorInfo();
		
		instructor.setId(UUIDHelper.genStringUUID());
		
        instructor.setPersonId(personId);
        
        instructor.setPersonName(personName);
        
        instructor.setPercentageEffort(percentageEffort);
        
        instructor.setTypeKey(LprServiceConstants.INSTRUCTOR_MAIN_TYPE_KEY);
        instructor.setStateKey(LprServiceConstants.INSTRUCTOR_COURSE_ASSIGNMENT_STATE_KEYS[0]);

        return instructor;
	}
	

}
