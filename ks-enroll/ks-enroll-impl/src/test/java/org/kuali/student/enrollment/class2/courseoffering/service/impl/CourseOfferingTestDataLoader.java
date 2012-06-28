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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.dto.RichTextInfo;
import org.kuali.student.common.exceptions.AlreadyExistsException;
import org.kuali.student.common.exceptions.CircularRelationshipException;
import org.kuali.student.common.exceptions.DependentObjectsExistException;
import org.kuali.student.common.exceptions.UnsupportedActionException;
import org.kuali.student.common.exceptions.VersionMismatchException;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.lum.course.dto.ActivityInfo;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.course.dto.FormatInfo;
import org.kuali.student.lum.course.service.CourseService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LuServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.class1.atp.service.impl.AtpTestDataLoader;

/**
 * @author ocleirig
 *
 */
public class CourseOfferingTestDataLoader {

	private final AcademicCalendarService acalService;
	private final CourseOfferingService coService;
	
	private final AtpTestDataLoader atpDataLoader;
	private final AcalTestDataLoader acalDataLoader;
	
	private final CourseService courseService;

	/**
	 * @param coService 
	 * @param acalService 
	 * @param canonicalCourseService 
	 * 
	 */
	public CourseOfferingTestDataLoader(AtpService atpService, AcademicCalendarService acalService, CourseOfferingService coService, CourseService canonicalCourseService) {
		this.acalService = acalService;
		this.coService = coService;
		this.courseService = canonicalCourseService;
		
		this.atpDataLoader = new AtpTestDataLoader(atpService);
		this.acalDataLoader = new AcalTestDataLoader(acalService);
		
	 
 }

	public void loadData(ContextInfo context) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, AlreadyExistsException, org.kuali.student.common.exceptions.DataValidationErrorException, org.kuali.student.common.exceptions.InvalidParameterException, org.kuali.student.common.exceptions.MissingParameterException, org.kuali.student.common.exceptions.OperationFailedException, org.kuali.student.common.exceptions.PermissionDeniedException, VersionMismatchException, org.kuali.student.common.exceptions.DoesNotExistException, CircularRelationshipException, DependentObjectsExistException, UnsupportedActionException, org.kuali.student.r2.common.exceptions.VersionMismatchException, org.kuali.student.r2.common.exceptions.AlreadyExistsException {

		atpDataLoader.loadData();
		acalDataLoader.loadData();
		
		// load the canonical course data
		
		loadCanonicalCourseAndFormat("CLU-1", "2012FA", "CHEM", "CHEM123", "Chemistry 123", "description 1", "COURSE1-FORMAT1",
                LuServiceConstants.COURSE_ACTIVITY_LECTURE_TYPE_KEY, LuServiceConstants.COURSE_ACTIVITY_LAB_TYPE_KEY);
		
        loadCanonicalCourseAndFormat("CLU-2", "2012SP", "ENG", "ENG101", "Intro English", "description 2", "COURSE2-FORMAT1",
                LuServiceConstants.COURSE_ACTIVITY_LECTURE_TYPE_KEY, null);
        
		// test lui data
		
		CourseOfferingInfo co1 = CourseOfferingServiceDataUtils.createCourseOffering("CLU-1", "atpId1", "Lui Desc 101", "CHEM123");
		coService.createCourseOffering("CLU-1", "atpId1", LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, co1, new ArrayList<String>(), context);
		
		CourseOfferingInfo co2 = CourseOfferingServiceDataUtils.createCourseOffering("CLU-2", "atpId2", "Lui Desc 301", "BIO123");
		coService.createCourseOffering("CLU-2", "atpId2", LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, co2, new ArrayList<String>(), context);
		
		// format
		FormatOfferingInfo fo1 = CourseOfferingServiceDataUtils.createFormatOffering(co1.getId(), "COURSE1-FORMAT1", "atpId1", "Lecture", LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY);
		
		coService.createFormatOffering(co1.getId(), "COURSE1-FORMAT1", LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY, fo1, context);
		
		List<OfferingInstructorInfo> instructors = new ArrayList<OfferingInstructorInfo>();
		
		instructors.add(CourseOfferingServiceDataUtils.createInstructor("p1", "Instructor", 100.00F));
		
		ActivityOfferingInfo ao1 = CourseOfferingServiceDataUtils.createActivityOffering("atpId1", fo1.getId(), "SCHED-1", "COURSE1-FORMAT1", "Lecture", "A", LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, instructors);
		
		coService.createActivityOffering(fo1.getId(), "COURSE1-FORMAT1", LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, ao1, context);
		
		// activity
		
//				loadLui("Lui-1", "Lui one", "cluId1", "atpId1", "kuali.lui.type.course.offering", "kuali.lui.state.draft", "<p>Lui Desc 101</p>", "Lui Desc 101", "2011-01-01 00:00:00.0", "2011-12-31 00:00:00.0", 200, 50, "ref.url");
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
		
	
	// copied from CourseR1DataLoader
	
	        
	        
	    }

	    public void loadCanonicalCourseAndFormat(String id,
	            String startTermId,
	            String subjectArea,
	            String code,
	            String title,
	            String description,
	            String formatId,
	            String activityTypeKey1,
	            String activityTypeKey2) throws AlreadyExistsException, org.kuali.student.common.exceptions.DataValidationErrorException, org.kuali.student.common.exceptions.InvalidParameterException, org.kuali.student.common.exceptions.MissingParameterException, org.kuali.student.common.exceptions.OperationFailedException, org.kuali.student.common.exceptions.PermissionDeniedException, VersionMismatchException, org.kuali.student.common.exceptions.DoesNotExistException, CircularRelationshipException, DependentObjectsExistException, UnsupportedActionException {
	        List<String> activityTypeKeys = new ArrayList();
	        if (activityTypeKey1 != null) {
	            activityTypeKeys.add(activityTypeKey1);
	        }
	        if (activityTypeKey1 != null) {
	            activityTypeKeys.add(activityTypeKey2);
	        }
	        this.loadCourseInternal(id, startTermId, subjectArea, code, title, description, formatId, activityTypeKeys);
	    }

	    private void loadCourseInternal(String id,
	            String startTermId,
	            String subjectArea,
	            String code,
	            String title,
	            String description,
	            String formatId,
	            List<String> activityTypeKeys) throws AlreadyExistsException, org.kuali.student.common.exceptions.DataValidationErrorException, org.kuali.student.common.exceptions.InvalidParameterException, org.kuali.student.common.exceptions.MissingParameterException, org.kuali.student.common.exceptions.OperationFailedException, org.kuali.student.common.exceptions.PermissionDeniedException, VersionMismatchException, org.kuali.student.common.exceptions.DoesNotExistException, CircularRelationshipException, DependentObjectsExistException, UnsupportedActionException {
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

}
