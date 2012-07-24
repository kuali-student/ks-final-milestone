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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.kuali.student.common.dto.DtoConstants;
import org.kuali.student.common.dto.RichTextInfo;
import org.kuali.student.common.exceptions.AlreadyExistsException;
import org.kuali.student.common.exceptions.CircularRelationshipException;
import org.kuali.student.common.exceptions.DependentObjectsExistException;
import org.kuali.student.common.exceptions.UnsupportedActionException;
import org.kuali.student.common.exceptions.VersionMismatchException;
import org.kuali.student.common.mock.MockService;
import org.kuali.student.common.test.TestAwareDataLoader;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.acal.service.assembler.TermAssembler;
import org.kuali.student.enrollment.class2.courseoffering.service.CourseOfferingCodeGenerator;
import org.kuali.student.enrollment.class2.courseoffering.service.RegistrationGroupCodeGenerator;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.lum.course.dto.ActivityInfo;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.course.dto.FormatInfo;
import org.kuali.student.lum.course.service.CourseService;
import org.kuali.student.r2.common.assembler.AssemblyException;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;
import org.kuali.student.r2.common.util.constants.LuServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.class1.atp.service.impl.AtpTestDataLoader;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


/**
 * This class is expected to be configured as a spring bean in order to have the resources loaded.  
 * <p/>
 * The {@link TestAwareDataLoader}  api is used by the TestCourseOfferingServiceMockImpl to coordinate the data clear and load operations. 
 * 
 * The data modeled here probably should have come from this picture in the CourseOfferingService documentation : 
 * <p/>
 * {@link https://wiki.kuali.org/display/STUDENT/KS+ENR+HLDS+-+Seat+Pools+-+Reg+Groups#KSENRHLDS-SeatPools-RegGroups-DiagramofSeatPoolandRegGroupExamples}
 * 
 * We also define some methods that can be used to insert specific kinds of data into various spots for each example CourseOffering.
 * 
 * @author ocleirig
 *
 */
public class CourseOfferingServiceTestDataLoader implements TestAwareDataLoader, ApplicationContextAware {

	@Resource
	private AcademicCalendarService acalService;
	
	@Resource
	private CourseOfferingService coService;
	
	@Resource
	private CourseService courseService;

	@Resource
	private AtpService atpService;
	
	@Resource
	private CourseOfferingCodeGenerator courseCodeGenerator;
	
	@Resource
	private RegistrationGroupCodeGenerator registrationGroupCodeGenerator;
	
	private AtpTestDataLoader atpDataLoader;
	private AcalTestDataLoader acalDataLoader;

	private ApplicationContext applicationContext;

	private boolean initialized;
	

	/**
	 * @param coService 
	 * @param acalService 
	 * @param canonicalCourseService 
	 * 
	 */
	public CourseOfferingServiceTestDataLoader() {
	}

	
	
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
				this.applicationContext = applicationContext;
		
	}




	@Override
	public void beforeTest() throws Exception {
		this.atpDataLoader = new AtpTestDataLoader(atpService);
		this.acalDataLoader = new AcalTestDataLoader(acalService);		
		
		ContextInfo context = new ContextInfo();
		
		context.setPrincipalId("123");
		context.setCurrentDate(new Date());

		atpDataLoader.loadData();
		acalDataLoader.loadData();
		
		// load in custom dates for use in the courses
		 TermInfo fall2012 = createTerm("2012FA", "Fall 2012", AtpServiceConstants.ATP_FALL_TYPE_KEY, new DateTime().withDate(2012, 9, 1).toDate(), new DateTime().withDate(2012, 12, 31).toDate(), context);
		
		 TermInfo spring2012 = createTerm("2012SP", "Spring 2012", AtpServiceConstants.ATP_SPRING_TYPE_KEY, new DateTime().withDate(2012, 1, 1).toDate(), new DateTime().withDate(2012, 4, 30).toDate(), context);
		
		// load the canonical course data
		
		createCourseCHEM123(fall2012, context);
		
		createCourseENG101(spring2012, context);
		
		this.initialized = true;
		
		
		// activity
		
//		 loadLui("Lui-1", "Lui one", "cluId1", "atpId1", "kuali.lui.type.course.offering", "kuali.lui.state.draft", "<p>Lui Desc 101</p>", "Lui Desc 101", "2011-01-01 00:00:00.0", "2011-12-31 00:00:00.0", 200, 50, "ref.url");
//	     loadLui("Lui-2", "Lui rwo", "cluId2", "atpId2", "kuali.lui.type.activity.offering.lecture", "kuali.lui.state.draft", "<p>Lui Desc 201</p>", "Lui Desc 201", "2011-01-01 00:00:00.0", "2011-12-31 00:00:00.0", 200, 50, "ref.url");
//	     loadLui("Lui-3", "Lui three", "cluId3", "atpId3", "kuali.lui.type.course.offering", "kuali.lui.state.draft", "<p>Lui Desc 301</p>", "Lui Desc 301 for deletion", "2011-01-01 00:00:00.0", "2011-12-31 00:00:00.0", 200, 50, "ref.url");
//	     loadLui("Lui-4", "Lui four", "cluId4", "atpId4", "kuali.lui.type.activity.offering.lecture", "kuali.lui.state.draft", "<p>Lui Desc 401</p>", "Lui Desc 401 for deletion", "2011-01-01 00:00:00.0", "2011-12-31 00:00:00.0", 200, 50, "ref.url");
//	     loadLui("Lui-5", "Lui five", "cluId5", "atpId5", "kuali.lui.type.activity.offering.lab", "kuali.lui.state.draft", "<p>Lui Desc 501</p>", "Lui Desc 501", "2011-01-01 00:00:00.0", "2011-12-31 00:00:00.0", 200, 50, "ref.url");
//
//	     loadLuiLuiRel("LUILUIREL-1", "2011-01-01 00:00:00.0", "2100-01-01 00:00:00.0", "kuali.lui.lui.relation.state.active", "<p>LUILUIREL-1 Formatted</p>", "LUILUIREL-1 Plain", "Lui-1", "kuali.lui.lui.relation.associated", "Lui-2");
//	     loadLuiLuiRel("LUILUIREL-2", "2011-01-01 00:00:00.0", "2100-01-01 00:00:00.0", "kuali.lui.lui.relation.state.active", "<p>LUILUIREL-2 Formatted</p>", "LUILUIREL-2 Plain", "Lui-3", "kuali.lui.lui.relation.associated", "Lui-4");
//	     loadLuiLuiRel("LUILUIREL-3", "2011-01-01 00:00:00.0", "2100-01-01 00:00:00.0", "kuali.lui.lui.relation.state.active", "<p>LUILUIREL-3 Formatted</p>", "LUILUIREL-3 Plain", "Lui-5", "kuali.lui.lui.relation.associated", "Lui-2");
//
//	     addIdentifier("LUI-IDENT-1", "CHEM123", "CHEM", "123", "Chemistry 123", "Chem 123", "kuali.lui.identifier.type.official", null, "Lui-1");
//	     addIdentifier("LUI-IDENT-2", "CHEM456", "CHEM", "456", "Chemistry 456", "Chem 456", "kuali.lui.identifier.type.cross-listed", null, "Lui-1");
//	     addIdentifier("LUI-IDENT-3", "BIO123", "BIO", "123", "Biology 123", "Bio 123", "kuali.lui.identifier.type.official", null, "Lui-2");
//	     addIdentifier("LUI-IDENT-4", "BIO456", "BIO", "456", "Biology 456", "Bio 456", "kuali.lui.identifier.type.cross-listed", null, "Lui-2");
//	     addIdentifier("LUI-IDENT-5", "GEOG123", "GEOG", "123", "Geography 123", "Geog 123", "kuali.lui.identifier.type.official", null, "Lui-3");
//	     addIdentifier("LUI-IDENT-6", "MATH123", "MATH", "123", "Mathematics 123", "Math 123", "kuali.lui.identifier.type.official", null, "Lui-4");
//	     addIdentifier("LUI-IDENT-7", "MATH123", "MATH", "456", "Mathematics 456", "Math 456", "kuali.lui.identifier.type.cross-listed", null, "Lui-4");
	     
		// for registration groups
		
	}



	@Override
	public void afterTest() {
		// clear the state
		
		Map<String, MockService> map = applicationContext.getBeansOfType(MockService.class);
		
		for (MockService mock : map.values()) {
			
			mock.clear();
		}
		
	}




	    private TermInfo createTerm(String id, String name, String atpTypeKey, Date startDate, Date endDate, ContextInfo context) throws OperationFailedException, DataValidationErrorException, InvalidParameterException, MissingParameterException, PermissionDeniedException, ReadOnlyException {
	    	
	    	AtpInfo atpInfo = new AtpInfo();
	        atpInfo.setId(id);
	        atpInfo.setName(name);
	        atpInfo.setTypeKey(atpTypeKey);
	        atpInfo.setStateKey(AtpServiceConstants.ATP_OFFICIAL_STATE_KEY);
	        atpInfo.setStartDate(startDate);
	        atpInfo.setEndDate(endDate);
	        atpInfo.setDescr(new RichTextHelper().fromPlain(name));

	        try {
	        TermInfo term = new TermAssembler().assemble(atpInfo, context);
	        atpInfo.setCode(TermAssembler.buildAtpCodeForTerm(term));
	        }
	        catch (AssemblyException e) {
	            throw new OperationFailedException("Assembly of TermInfo failed", e);
	        }

	        AtpInfo saved = atpService.createAtp(atpInfo.getTypeKey(), atpInfo, context);
		
	        TermInfo term = new TermInfo();
	        
	        term.setId(saved.getId());
	        term.setAttributes(saved.getAttributes());
	        term.setCode(saved.getCode());
	        term.setStartDate(saved.getStartDate());
	        term.setEndDate(saved.getEndDate());
	        term.setMeta(saved.getMeta());
	        term.setDescr(saved.getDescr());
	        term.setStateKey(saved.getStateKey());
	        term.setTypeKey(saved.getTypeKey());
	        
	        return term;
	}


		


		private void createCourseCHEM123(TermInfo term, ContextInfo context) throws AlreadyExistsException, org.kuali.student.common.exceptions.DataValidationErrorException, org.kuali.student.common.exceptions.InvalidParameterException, org.kuali.student.common.exceptions.MissingParameterException, org.kuali.student.common.exceptions.OperationFailedException, org.kuali.student.common.exceptions.PermissionDeniedException, VersionMismatchException, org.kuali.student.common.exceptions.DoesNotExistException, CircularRelationshipException, DependentObjectsExistException, UnsupportedActionException, DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
	    	
	    	CourseInfo canonicalCourse = buildCanonicalCourse("CLU-1", term.getId(), "CHEM", "CHEM123", "Chemistry 123", "description 1");
			
			FormatInfo canonicalLectureOnlyFormat = buildCanonicalFormat("CHEM123:LEC-ONLY", canonicalCourse);
			
			ActivityInfo canonicalLectureOnlyLectureActivity = buildCanonicalActivity(LuServiceConstants.COURSE_ACTIVITY_LECTURE_TYPE_KEY, canonicalLectureOnlyFormat);
			
			FormatInfo canonicalLectureAndLabFormat = buildCanonicalFormat("CHEM123:LEC-AND-LAB", canonicalCourse);
			
			ActivityInfo canonicalLectureAndLabFormatLectureActivity = buildCanonicalActivity(LuServiceConstants.COURSE_ACTIVITY_LECTURE_TYPE_KEY, canonicalLectureAndLabFormat);
			ActivityInfo canonicalLectureAndLabFormatLabActivity = buildCanonicalActivity(LuServiceConstants.COURSE_ACTIVITY_LAB_TYPE_KEY, canonicalLectureAndLabFormat);
			
			courseService.createCourse(canonicalCourse);
		

			// course offering
			CourseOfferingInfo courseOffering = CourseOfferingServiceDataUtils.createCourseOffering(canonicalCourse, term.getId());
			
			courseOffering.setId("CO-1");
			
			coService.createCourseOffering(canonicalCourse.getId(), term.getId(), LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, courseOffering, new ArrayList<String>(), context);
			
			// FO-1: lecture only format
			FormatOfferingInfo lectureOnlyFormatOffering = CourseOfferingServiceDataUtils.createFormatOffering(courseOffering.getId(), canonicalLectureOnlyFormat.getId(), term.getId(), "Lecture", LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY);
			
			lectureOnlyFormatOffering.setId("CO-1:LEC-ONLY");
			
			coService.createFormatOffering(courseOffering.getId(), canonicalLectureOnlyFormat.getId(), LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY, lectureOnlyFormatOffering, context);
			
			// FO-2: lab and lecture format
			FormatOfferingInfo lectureAndLabFormatOffering = CourseOfferingServiceDataUtils.createFormatOffering(courseOffering.getId(), canonicalLectureAndLabFormat.getId(), term.getId(), "Lab & Lecture", new String[] {LuiServiceConstants.LAB_ACTIVITY_OFFERING_TYPE_KEY, LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY});
			
			lectureAndLabFormatOffering.setId("CO-1:LEC-AND-LAB");
			
			coService.createFormatOffering(courseOffering.getId(), canonicalLectureAndLabFormat.getId(), LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY, lectureAndLabFormatOffering, context);
			
			List<OfferingInstructorInfo> instructors = new ArrayList<OfferingInstructorInfo>();
			
			instructors.add(CourseOfferingServiceDataUtils.createInstructor("p1", "Instructor", 100.00F));
			
			
			// Format 1 Lecture offering A
			ActivityOfferingInfo lectureOnlyFormatLectureA = CourseOfferingServiceDataUtils.createActivityOffering(term.getId(), courseOffering, lectureOnlyFormatOffering.getId(), "SCHED-1", canonicalLectureOnlyLectureActivity.getId(), "Lecture A", "A", LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, instructors);
			
			lectureOnlyFormatLectureA.setId("CO-1:LEC-ONLY:LEC-A");
			
			coService.createActivityOffering(lectureOnlyFormatOffering.getId(), canonicalLectureOnlyLectureActivity.getId(), LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, lectureOnlyFormatLectureA, context);
			
			// Format 1 Lecture Offering B
			ActivityOfferingInfo lectureOnlyFormatLectureB = CourseOfferingServiceDataUtils.createActivityOffering(term.getId(), courseOffering, lectureOnlyFormatOffering.getId(), "SCHED-2", canonicalLectureOnlyLectureActivity.getId(), "Lecture B", "A", LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, instructors);
			
			lectureOnlyFormatLectureB.setId("CO-1:LEC-ONLY:LEC-B");
			
			coService.createActivityOffering(lectureOnlyFormatOffering.getId(), canonicalLectureOnlyLectureActivity.getId(), LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, lectureOnlyFormatLectureB, context);
			
			// Format 2:
			
			// Lecture A
			ActivityOfferingInfo lectureAndLabFormatLectureA = CourseOfferingServiceDataUtils.createActivityOffering(term.getId(), courseOffering, lectureAndLabFormatOffering.getId(), "SCHED-3", canonicalLectureAndLabFormatLectureActivity.getId(), "Lecture A", "A", LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, instructors);
			
			lectureAndLabFormatLectureA.setId("CO-1:LEC-AND-LAB:LEC-A");
			
			coService.createActivityOffering(lectureAndLabFormatOffering.getId(), canonicalLectureAndLabFormatLectureActivity.getId(), LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, lectureAndLabFormatLectureA, context);
			
			// Lecture B
			ActivityOfferingInfo lectureAndLabFormatLectureB = CourseOfferingServiceDataUtils.createActivityOffering(term.getId(), courseOffering, lectureAndLabFormatOffering.getId(), "SCHED-4", canonicalLectureAndLabFormatLectureActivity.getId(), "Lecture B", "A", LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, instructors);
			
			lectureAndLabFormatLectureB.setId("CO-1:LEC-AND-LAB:LEC-B");
			
			coService.createActivityOffering(lectureAndLabFormatOffering.getId(), canonicalLectureAndLabFormatLectureActivity.getId(), LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, lectureAndLabFormatLectureB, context);
		
			
			
			
			// Lab A
			ActivityOfferingInfo lectureAndLabFormatLabA = CourseOfferingServiceDataUtils.createActivityOffering(term.getId(), courseOffering, lectureAndLabFormatOffering.getId(), "LAB-A", canonicalLectureAndLabFormatLabActivity.getId(), "Lab A", "A", LuiServiceConstants.LAB_ACTIVITY_OFFERING_TYPE_KEY, instructors);
			
			lectureAndLabFormatLabA.setId("CO-1:LEC-AND-LAB:LAB-A");
			
			coService.createActivityOffering(lectureAndLabFormatOffering.getId(), canonicalLectureAndLabFormatLabActivity.getId(), LuiServiceConstants.LAB_ACTIVITY_OFFERING_TYPE_KEY, lectureAndLabFormatLabA, context);
			
			
			// Lab B
			ActivityOfferingInfo lectureAndLabFormatLabB = CourseOfferingServiceDataUtils.createActivityOffering(term.getId(), courseOffering, lectureAndLabFormatOffering.getId(), "LAB-B", canonicalLectureAndLabFormatLabActivity.getId(), "Lab B", "A", LuiServiceConstants.LAB_ACTIVITY_OFFERING_TYPE_KEY, instructors);
			
			lectureAndLabFormatLabB.setId("CO-1:LEC-AND-LAB:LAB-B");
			
			coService.createActivityOffering(lectureAndLabFormatOffering.getId(), canonicalLectureAndLabFormatLabActivity.getId(), LuiServiceConstants.LAB_ACTIVITY_OFFERING_TYPE_KEY, lectureAndLabFormatLabB, context);
			
			// Lab C
			
			
			ActivityOfferingInfo lectureAndLabFormatLabC = CourseOfferingServiceDataUtils.createActivityOffering(term.getId(), courseOffering, lectureAndLabFormatOffering.getId(), "LAB-C", canonicalLectureAndLabFormatLabActivity.getId(), "Lab C", "A", LuiServiceConstants.LAB_ACTIVITY_OFFERING_TYPE_KEY, instructors);
			
			lectureAndLabFormatLabC.setId("CO-1:LEC-AND-LAB:LAB-C");
			
			coService.createActivityOffering(lectureAndLabFormatOffering.getId(), canonicalLectureAndLabFormatLabActivity.getId(), LuiServiceConstants.LAB_ACTIVITY_OFFERING_TYPE_KEY, lectureAndLabFormatLabC, context);
			
			
	}
		

		public void createLabActivityOfferingForCHEM123(String labCode, ContextInfo context) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, org.kuali.student.common.exceptions.DoesNotExistException, org.kuali.student.common.exceptions.InvalidParameterException, org.kuali.student.common.exceptions.MissingParameterException, org.kuali.student.common.exceptions.OperationFailedException, org.kuali.student.common.exceptions.PermissionDeniedException {
			
			
			CourseOfferingInfo co = coService.getCourseOffering("CO-1", context);
			
			CourseInfo c = courseService.getCourse(co.getCourseId());
			
			// create the new activity offering
			
			FormatInfo targetFormat = null;
			
			for (FormatInfo format : c.getFormats()) {
				
				if (format.getId().equals("CHEM123:LEC-AND-LAB")) {
					targetFormat = format;
					break;
				}
			} 
			
			List<OfferingInstructorInfo> instructors = new ArrayList<OfferingInstructorInfo>();
			
			instructors.add(CourseOfferingServiceDataUtils.createInstructor("p1", "Instructor", 100.00F));
	
			String canonicalActivityId = CourseOfferingServiceDataUtils.createCanonicalActivityId(targetFormat.getId(), LuiServiceConstants.LAB_ACTIVITY_OFFERING_TYPE_KEY);
			
			ActivityOfferingInfo lectureAndLabFormatLabC = CourseOfferingServiceDataUtils.createActivityOffering(co.getTermId(), co, "CO-1:LEC-AND-LAB", labCode, canonicalActivityId, labCode, "A", LuiServiceConstants.LAB_ACTIVITY_OFFERING_TYPE_KEY, instructors);
			
			lectureAndLabFormatLabC.setId("CO-1:LEC-AND-LAB:" + labCode);
		
	
			
			coService.createActivityOffering("CO-1:LEC-AND-LAB", canonicalActivityId, LuiServiceConstants.LAB_ACTIVITY_OFFERING_TYPE_KEY, lectureAndLabFormatLabC, context);
			
			
		}

		
		private void createCourseENG101(TermInfo term, ContextInfo context) throws AlreadyExistsException, org.kuali.student.common.exceptions.DataValidationErrorException, org.kuali.student.common.exceptions.InvalidParameterException, org.kuali.student.common.exceptions.MissingParameterException, org.kuali.student.common.exceptions.OperationFailedException, org.kuali.student.common.exceptions.PermissionDeniedException, VersionMismatchException, org.kuali.student.common.exceptions.DoesNotExistException, CircularRelationshipException, DependentObjectsExistException, UnsupportedActionException, DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
			
			CourseInfo canonicalCourse = buildCanonicalCourse("CLU-2",term.getId() , "ENG", "ENG101", "Intro English", "Description of Intoroductory English");
			
			FormatInfo canonicalLectureOnlyFormat = buildCanonicalFormat("ENG101:LEC-ONLY", canonicalCourse);
			
			ActivityInfo canonicalLectureOnlyFormatLectureActivity = buildCanonicalActivity(LuServiceConstants.COURSE_ACTIVITY_LECTURE_TYPE_KEY, canonicalLectureOnlyFormat);
			
			courseService.createCourse(canonicalCourse);
			
			// create offerings
			CourseOfferingInfo co = CourseOfferingServiceDataUtils.createCourseOffering(canonicalCourse, term.getId());
			
			co.setId("CO-2");
			
			coService.createCourseOffering(canonicalCourse.getId(), term.getId(), LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, co, new ArrayList<String>(), context);
			
			// create format offering
			// FO-1: lecture only format
			FormatOfferingInfo fo1 = CourseOfferingServiceDataUtils.createFormatOffering(co.getId(), canonicalLectureOnlyFormat.getId(), term.getId(), "Lecture", LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY);
						
			fo1.setId("CO-2:LEC-ONLY");
					
			coService.createFormatOffering(co.getId(), canonicalLectureOnlyFormat.getId(), LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY, fo1, context);
			
			// create lecture activity
			List<OfferingInstructorInfo> instructors = new ArrayList<OfferingInstructorInfo>();
			
			instructors.add(CourseOfferingServiceDataUtils.createInstructor("p2", "Instructor", 100.00F));
			
			// Lecture A
			ActivityOfferingInfo lectureOnlyFormatLectureA = CourseOfferingServiceDataUtils.createActivityOffering(term.getId(), co, fo1.getId(), "SCHED-1", canonicalLectureOnlyFormatLectureActivity.getId(), "Lecture", "A", LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, instructors);
			
			lectureOnlyFormatLectureA.setId("CO-2:LEC-ONLY:LEC-A");
			
			coService.createActivityOffering(fo1.getId(), canonicalLectureOnlyFormatLectureActivity.getId(), LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, lectureOnlyFormatLectureA, context);
			
			// Lecture B
			ActivityOfferingInfo lectureOnlyFormatLectureB = CourseOfferingServiceDataUtils.createActivityOffering(term.getId(), co, fo1.getId(), "SCHED-1", canonicalLectureOnlyFormatLectureActivity.getId(), "Lecture", "A", LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, instructors);
			
			lectureOnlyFormatLectureB.setId("CO-2:LEC-ONLY:LEC-B");
			
			coService.createActivityOffering(fo1.getId(), canonicalLectureOnlyFormatLectureActivity.getId(), LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, lectureOnlyFormatLectureB, context);
			
			// Create a Registration Group
			List activities;
			RegistrationGroupInfo regGroupA = CourseOfferingServiceDataUtils.createRegistrationGroup(co.getId(), fo1.getId(), "2012SP", activities = Arrays.asList(new String[] {lectureOnlyFormatLectureA.getId()}), "Reg Group for Lecture A", "REG:LEC-A", false, false,50, LuiServiceConstants.REGISTRATION_GROUP_OPEN_STATE_KEY);
			
			regGroupA.setId("CO-2:LEC-ONLY:REG-GROUP-LEC-A");
			
			regGroupA.setRegistrationCode(registrationGroupCodeGenerator.generateRegistrationGroupCode(fo1, activities));
			
			
			coService.createRegistrationGroup(fo1.getId(), LuiServiceConstants.REGISTRATION_GROUP_TYPE_KEY, regGroupA, context);
			
			
	}


		public void loadCanonicalCourseAndFormat(String id,
	            String startTermId,
	            String subjectArea,
	            String code,
	            String title,
	            String description, 
	            String formatId,
	            String activityTypeKey) throws AlreadyExistsException, org.kuali.student.common.exceptions.DataValidationErrorException, org.kuali.student.common.exceptions.InvalidParameterException, org.kuali.student.common.exceptions.MissingParameterException, org.kuali.student.common.exceptions.OperationFailedException, org.kuali.student.common.exceptions.PermissionDeniedException, VersionMismatchException, org.kuali.student.common.exceptions.DoesNotExistException, CircularRelationshipException, DependentObjectsExistException, UnsupportedActionException {
	        this.loadCourseInternal(id, startTermId, subjectArea, code, title, description, formatId, new String[] {activityTypeKey});
	    }
	    
	    public void loadCanonicalCourseAndFormat(String id,
	            String startTermId,
	            String subjectArea,
	            String code,
	            String title,
	            String description, 
	            String formatId,
	            String[] activityTypeKeys) throws AlreadyExistsException, org.kuali.student.common.exceptions.DataValidationErrorException, org.kuali.student.common.exceptions.InvalidParameterException, org.kuali.student.common.exceptions.MissingParameterException, org.kuali.student.common.exceptions.OperationFailedException, org.kuali.student.common.exceptions.PermissionDeniedException, VersionMismatchException, org.kuali.student.common.exceptions.DoesNotExistException, CircularRelationshipException, DependentObjectsExistException, UnsupportedActionException {
	        this.loadCourseInternal(id, startTermId, subjectArea, code, title, description, formatId, activityTypeKeys);
	    }


	    private void loadCourseInternal(String id,
	            String startTermId,
	            String subjectArea,
	            String code,
	            String title,
	            String description,
	            String formatId,
	            String[] activityTypeKeys) throws AlreadyExistsException, org.kuali.student.common.exceptions.DataValidationErrorException, org.kuali.student.common.exceptions.InvalidParameterException, org.kuali.student.common.exceptions.MissingParameterException, org.kuali.student.common.exceptions.OperationFailedException, org.kuali.student.common.exceptions.PermissionDeniedException, VersionMismatchException, org.kuali.student.common.exceptions.DoesNotExistException, CircularRelationshipException, DependentObjectsExistException, UnsupportedActionException {
	        CourseInfo info = new CourseInfo();
	        info.setStartTerm(startTermId);
	        info.setEffectiveDate(calcEffectiveDateForTerm(startTermId, id));
	        info.setId(id);
	        info.setSubjectArea(subjectArea);
	        info.setCode(code);
	        info.setCourseNumberSuffix(code.substring(subjectArea.length()));
	        info.setCourseTitle(title);
	        RichTextInfo rt = new RichTextInfo();
	        rt.setPlain(description);
	        info.setDescr(rt);
	        info.setType(LuServiceConstants.CREDIT_COURSE_LU_TYPE_KEY);
	        info.setState("Active");
	        info.setFormats(new ArrayList<FormatInfo>());
	        FormatInfo format = new FormatInfo();
	        info.getFormats().add(format);
	        format.setId(formatId);
	        format.setType(LuServiceConstants.COURSE_FORMAT_TYPE_KEY);
	        format.setState("Active");
	        format.setActivities(new ArrayList<ActivityInfo>());
	        for (String activityTypeKey : activityTypeKeys) {
	            ActivityInfo activity = new ActivityInfo();
	            format.getActivities().add(activity);
	            activity.setId(format.getId() + "-" + activityTypeKey);
	            activity.setActivityType(activityTypeKey);
	            activity.setState("Active");
	        }
	        
	        courseService.createCourse(info);
	    }
	    
	    private CourseInfo buildCanonicalCourse(String id, String startTermId, String subjectArea, String code, String title, String description) {
	    	CourseInfo info = new CourseInfo();
	    	info.setStartTerm(startTermId);
	        info.setEffectiveDate(calcEffectiveDateForTerm(startTermId, id));
	        info.setId(id);
	        info.setSubjectArea(subjectArea);
	        info.setCode(code);
	        info.setCourseNumberSuffix(code.substring(subjectArea.length()));
	        info.setCourseTitle(title);
	        RichTextInfo rt = new RichTextInfo();
	        rt.setPlain(description);
	        info.setDescr(rt);
	        info.setType(LuServiceConstants.CREDIT_COURSE_LU_TYPE_KEY);
	        info.setState(DtoConstants.STATE_ACTIVE);
	        info.setFormats(new ArrayList<FormatInfo>());
			return info;
	    }
	    
	    
	    private ActivityInfo buildCanonicalActivity(String activityTypeKey, FormatInfo format) {
	    	
	    	ActivityInfo info = new ActivityInfo();
	    	info.setId(CourseOfferingServiceDataUtils.createCanonicalActivityId(format.getId(), activityTypeKey));
	    	info.setActivityType(activityTypeKey);
	    	info.setState(DtoConstants.STATE_ACTIVE);
	    	
	    	format.getActivities().add(info);
	    	
			return info;
	    	
	    }
	    
	    private FormatInfo buildCanonicalFormat (String formatId, CourseInfo course) {
	    	
	    	FormatInfo info = new FormatInfo();
	    	info.setId(formatId);
	    	info.setType(LuServiceConstants.COURSE_FORMAT_TYPE_KEY);
	    	info.setState(DtoConstants.STATE_ACTIVE);
	    	info.setActivities(new ArrayList<ActivityInfo>());
	    	
	    	course.getFormats().add(info);
	    	
			return info;
	    }

	    private Date calcEffectiveDateForTerm(String termId, String context) {
	        String year = termId.substring(0, 4);
	        String mmdd = "09-01";
	        if (termId.endsWith("FA")) {
	            mmdd = "09-01";
	        } else if (termId.endsWith("WI")) {
	            mmdd = "01-01";
	        } else if (termId.endsWith("SP")) {
	            mmdd = "03-01";
	        } else if (termId.endsWith("SU")) {
	            mmdd = "06-01";
	        }
	        return str2Date(year + "-" + mmdd + " 00:00:00.0", context);
	    }

	    private Date str2Date(String str, String context) {
	        if (str == null) {
	            return null;
	        }
	        DateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss.S");
	        try {
	            Date date = df.parse(str);
	            return date;
	        } catch (ParseException ex) {
	            throw new IllegalArgumentException("Bad date " + str + " in " + context);
	        }
	    }




		public boolean isInitialized() {
			return initialized;
		}

}
