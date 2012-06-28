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
import java.util.List;

import org.kuali.student.enrollment.acal.constants.AcademicCalendarServiceConstants;
import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;

/**
 * @author ocleirig
 * 
 */
public class RegistrationGroupTestDataLoader {

	private AcademicCalendarInfo calendar;
	private TermInfo term;
	
	private CourseOfferingInfo courseOffering;
	private FormatOfferingInfo formatOffering;
	private ActivityOfferingInfo activityOffering;

	/**
	 * 
	 */
	public RegistrationGroupTestDataLoader() {
		// TODO Auto-generated constructor stub
	}

	public RegistrationGroupTestDataLoader(AcademicCalendarService acalService,
			CourseOfferingService coService, ContextInfo callContext) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, AlreadyExistsException {

		calendar = acalService.createAcademicCalendar(
				AcademicCalendarServiceConstants.ACADEMIC_CALENDAR_TYPE_KEY,
				CourseOfferingServiceDataUtils.createAcademicCalendar("2012",
						"2012-ACAL"), callContext);

		term = acalService.createTerm(
				AtpServiceConstants.ATP_FALL_TYPE_KEY,
				CourseOfferingServiceDataUtils.createTerm("FALL-2012",
						"FALL 2012"), callContext);

		acalService.addTermToAcademicCalendar(calendar.getId(), term.getId(),
				callContext);

		courseOffering = coService.createCourseOffering(
				"C1",
				term.getId(),
				LuiServiceConstants.COURSE_OFFERING_TYPE_KEY,
				CourseOfferingServiceDataUtils.createCourseOffering("C1",
						term.getId(), "Test Course C1", "TESTC1"), new ArrayList<String>(), callContext);

		String formatId = "FMT-1";
		formatOffering = coService.createFormatOffering(
				courseOffering.getId(), formatId,
				LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY,
				CourseOfferingServiceDataUtils.createFormatOffering(
						courseOffering.getId(), formatId, term.getId(), "FORMAT", LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY), callContext);

		String activityId = "AO-1";
		List<OfferingInstructorInfo> instructors = new ArrayList<OfferingInstructorInfo>();
		
		instructors.add(CourseOfferingServiceDataUtils.createInstructor("person12", "Instructor", 100.00F));
		activityOffering = coService
				.createActivityOffering(
						formatOffering.getId(),
						activityId,
						LuiServiceConstants.ACTIVITY_ACTIVITY_OFFERING_TYPE_KEY,
						CourseOfferingServiceDataUtils.createActivityOffering(term.getId(), courseOffering.getId(),
								formatOffering.getId(), "schedId", activityId, "Lecture", "A123", LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, instructors),
						callContext);
	}

	/**
	 * @return the calendar
	 */
	public AcademicCalendarInfo getCalendar() {
		return calendar;
	}

	/**
	 * @return the term
	 */
	public TermInfo getTerm() {
		return term;
	}

	/**
	 * @return the courseOffering
	 */
	public CourseOfferingInfo getCourseOffering() {
		return courseOffering;
	}

	/**
	 * @return the formatOffering
	 */
	public FormatOfferingInfo getFormatOffering() {
		return formatOffering;
	}

	/**
	 * @return the activityOffering
	 */
	public ActivityOfferingInfo getActivityOffering() {
		return activityOffering;
	}

	
}
