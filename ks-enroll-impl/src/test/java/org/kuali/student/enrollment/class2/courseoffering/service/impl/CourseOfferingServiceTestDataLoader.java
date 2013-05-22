/*
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

import org.joda.time.DateTime;
import org.kuali.student.common.test.mock.data.AbstractMockServicesAwareDataLoader;
import org.kuali.student.common.test.spring.log4j.KSLog4JConfigurer;
import org.kuali.student.enrollment.class2.acal.util.AcalTestDataLoader;
import org.kuali.student.enrollment.class2.courseoffering.service.CourseOfferingCodeGenerator;
import org.kuali.student.enrollment.class2.courseoffering.service.transformer.RegistrationGroupCodeGeneratorFactory;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingClusterInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingSetInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.assembler.AssemblyException;
import org.kuali.student.r2.common.dto.BulkStatusInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.DtoConstants;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.CircularRelationshipException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.UnsupportedActionException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LuServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.acal.service.TermCodeGenerator;
import org.kuali.student.r2.core.acal.service.assembler.TermAssembler;
import org.kuali.student.r2.core.acal.service.impl.TermCodeGeneratorImpl;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.lum.course.dto.ActivityInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.dto.FormatInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;
import org.slf4j.Logger;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * This class is expected to be configured as a spring bean in order to have the resources loaded.
 * <p/>
 * The {@link org.kuali.student.common.test.TestAwareDataLoader}  api is used by the TestCourseOfferingServiceMockImpl to coordinate the data clear and load operations.
 * <p/>
 * The data modeled here probably should have come from this picture in the CourseOfferingService documentation :
 * <p/>
 * <a href="https://wiki.kuali.org/display/STUDENT/KS+ENR+HLDS+-+Seat+Pools+-+Reg+Groups#KSENRHLDS-SeatPools-RegGroups-DiagramofSeatPoolandRegGroupExamples">Documentation</a>
 * <p/>
 * We also define some methods that can be used to insert specific kinds of data into various spots for each example CourseOffering.
 *
 * @author Kuali Student Team
 */
public class CourseOfferingServiceTestDataLoader extends AbstractMockServicesAwareDataLoader {

    private static final Logger log = KSLog4JConfigurer.getLogger(CourseOfferingServiceTestDataLoader.class);
    
    public static final String CHEM123_COURSE_ID = "CLU-1";
    
    public static final String CHEM123_COURSE_OFFERING_ID = "CO-1";
    
    public static final String CHEM123_LEC_AND_LAB_FORMAT_OFFERING_ID = "CO-1:LEC-AND-LAB";
    
    public static final String ENG101_COURSE_ID = "CLU-2";
    
    public static final String ENG101_COURSE_OFFERING_ID = "CO-2";
    
    public static final String ENG101_LEC_ONLY_FORMAT_OFFERING_ID = "CO-2:LEC-ONLY";
    
    public static final String FALL_2012_TERM_ID = "2012FA";
    
    @Resource
    protected AcademicCalendarService acalService;

    @Resource
    protected CourseOfferingService coService;

    @Resource
    protected CourseService courseService;

    @Resource
    protected AtpService atpService;

    @Resource
    protected CourseOfferingCodeGenerator courseCodeGenerator;

    @Resource
    protected RegistrationGroupCodeGeneratorFactory registrationGroupCodeGeneratorFactory;

    

    protected AcalTestDataLoader acalDataLoader;

    TermInfo fall2012 = null;
    TermInfo spring2012 = null;

    private boolean loadBaseData = true;


    /**
     */
    public CourseOfferingServiceTestDataLoader() {
        super();
    }
    
    


    /* (non-Javadoc)
    * @see org.kuali.student.common.test.mock.data.AbstractMockServicesAwareDataLoader#initializeData()
    */
    @Override
    protected void initializeData() throws Exception {

        this.acalDataLoader = new AcalTestDataLoader(atpService);

        acalDataLoader.loadData();

        // this provides a way for users of the data loader to not to load the base data.
        // we may want to externalize the base data into the TestCourseOfferingServiceWithClass2Mocks
        if (!loadBaseData)
            return;
        
        // load in custom dates for use in the courses
        fall2012 = createTerm(FALL_2012_TERM_ID, "Fall 2012", AtpServiceConstants.ATP_FALL_TYPE_KEY, new DateTime().withDate(2012, 9, 1).toDate(), new DateTime().withDate(2012, 12, 31).toDate(), context);

        spring2012 = createTerm("2012SP", "Spring 2012", AtpServiceConstants.ATP_SPRING_TYPE_KEY, new DateTime().withDate(2012, 1, 1).toDate(), new DateTime().withDate(2012, 4, 30).toDate(), context);

        // load the canonical course data
        
        createCourse(fall2012, "CHEM", "123", context);
        createCourseCHEM123(fall2012, context);

        createCourseENG101(spring2012, context);

    }

   


    public TermInfo createTerm(String id, String name, String atpTypeKey, Date startDate, Date endDate, ContextInfo context) throws OperationFailedException, DataValidationErrorException, InvalidParameterException, MissingParameterException, PermissionDeniedException, ReadOnlyException {

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
            TermCodeGenerator tcg = new TermCodeGeneratorImpl();
            atpInfo.setCode(tcg.generateTermCode(term));
        } catch (AssemblyException e) {
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


    public void createCourseByTemplate(TermInfo term, String subjectCode, String courseNumberCode, ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, DoesNotExistException, CircularRelationshipException, DependentObjectsExistException, UnsupportedActionException, DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {

        String courseCode = subjectCode + courseNumberCode;
        String courseId = courseCode + "-ID";

        CourseInfo canonicalCourse = buildCanonicalCourse(courseId, term.getId(), subjectCode, courseCode, subjectCode + " " + courseCode, courseCode + " description 1");

        FormatInfo canonicalLectureOnlyFormat = buildCanonicalFormat(courseCode + ":LEC-ONLY", canonicalCourse);

        ActivityInfo canonicalLectureOnlyLectureActivity = buildCanonicalActivity(LuServiceConstants.COURSE_ACTIVITY_LECTURE_TYPE_KEY, canonicalLectureOnlyFormat);

        FormatInfo canonicalLectureAndLabFormat = buildCanonicalFormat(courseCode + ":LEC-AND-LAB", canonicalCourse);

        ActivityInfo canonicalLectureAndLabFormatLectureActivity = buildCanonicalActivity(LuServiceConstants.COURSE_ACTIVITY_LECTURE_TYPE_KEY, canonicalLectureAndLabFormat);
        ActivityInfo canonicalLectureAndLabFormatLabActivity = buildCanonicalActivity(LuServiceConstants.COURSE_ACTIVITY_LAB_TYPE_KEY, canonicalLectureAndLabFormat);

        courseService.createCourse(canonicalCourse, context);


        // course offering
        CourseOfferingInfo courseOffering = CourseOfferingServiceTestDataUtils.createCourseOffering(canonicalCourse, term.getId());

        courseOffering.setId(courseId);

        coService.createCourseOffering(canonicalCourse.getId(), term.getId(), LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, courseOffering, new ArrayList<String>(), context);

        // FO-1: lecture only format
        FormatOfferingInfo lectureOnlyFormatOffering = CourseOfferingServiceTestDataUtils.createFormatOffering(courseOffering.getId(), canonicalLectureOnlyFormat.getId(), term.getId(), "Lecture", LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY);

        lectureOnlyFormatOffering.setId(courseId + ":LEC-ONLY");

        coService.createFormatOffering(courseOffering.getId(), canonicalLectureOnlyFormat.getId(), LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY, lectureOnlyFormatOffering, context);

        // FO-2: lab and lecture format
        FormatOfferingInfo lectureAndLabFormatOffering = CourseOfferingServiceTestDataUtils.createFormatOffering(courseOffering.getId(), canonicalLectureAndLabFormat.getId(), term.getId(), "Lab & Lecture", new String[]{LuiServiceConstants.LAB_ACTIVITY_OFFERING_TYPE_KEY, LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY});

        lectureAndLabFormatOffering.setId(courseId + ":LEC-AND-LAB");

        coService.createFormatOffering(courseOffering.getId(), canonicalLectureAndLabFormat.getId(), LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY, lectureAndLabFormatOffering, context);

        List<OfferingInstructorInfo> instructors = new ArrayList<OfferingInstructorInfo>();

        instructors.add(CourseOfferingServiceTestDataUtils.createInstructor("p1", "Instructor", 100.00F));


        // Format 1 Lecture offering A
        ActivityOfferingInfo lectureOnlyFormatLectureA = CourseOfferingServiceTestDataUtils.createActivityOffering(term.getId(), courseOffering, lectureOnlyFormatOffering.getId(), null, canonicalLectureOnlyLectureActivity.getId(), "Lecture A", "A", LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, instructors);

        lectureOnlyFormatLectureA.setId(courseId + ":LEC-ONLY:LEC-A");

        coService.createActivityOffering(lectureOnlyFormatOffering.getId(), canonicalLectureOnlyLectureActivity.getId(), LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, lectureOnlyFormatLectureA, context);

        // Format 1 Lecture Offering B
        ActivityOfferingInfo lectureOnlyFormatLectureB = CourseOfferingServiceTestDataUtils.createActivityOffering(term.getId(), courseOffering, lectureOnlyFormatOffering.getId(), null, canonicalLectureOnlyLectureActivity.getId(), "Lecture B", "B", LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, instructors);

        lectureOnlyFormatLectureB.setId(courseId + ":LEC-ONLY:LEC-B");

        coService.createActivityOffering(lectureOnlyFormatOffering.getId(), canonicalLectureOnlyLectureActivity.getId(), LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, lectureOnlyFormatLectureB, context);

        // Format 2:

        // Lecture A
        ActivityOfferingInfo lectureAndLabFormatLectureA = CourseOfferingServiceTestDataUtils.createActivityOffering(term.getId(), courseOffering, lectureAndLabFormatOffering.getId(), null, canonicalLectureAndLabFormatLectureActivity.getId(), "Lecture A", "C", LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, instructors);

        lectureAndLabFormatLectureA.setId(courseId + ":LEC-AND-LAB:LEC-A");

        coService.createActivityOffering(lectureAndLabFormatOffering.getId(), canonicalLectureAndLabFormatLectureActivity.getId(), LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, lectureAndLabFormatLectureA, context);

        // Lecture B
        ActivityOfferingInfo lectureAndLabFormatLectureB = CourseOfferingServiceTestDataUtils.createActivityOffering(term.getId(), courseOffering, lectureAndLabFormatOffering.getId(), null, canonicalLectureAndLabFormatLectureActivity.getId(), "Lecture B", "D", LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, instructors);

        lectureAndLabFormatLectureB.setId(courseId + ":LEC-AND-LAB:LEC-B");

        coService.createActivityOffering(lectureAndLabFormatOffering.getId(), canonicalLectureAndLabFormatLectureActivity.getId(), LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, lectureAndLabFormatLectureB, context);


        // Lab A
        ActivityOfferingInfo lectureAndLabFormatLabA = CourseOfferingServiceTestDataUtils.createActivityOffering(term.getId(), courseOffering, lectureAndLabFormatOffering.getId(), null, canonicalLectureAndLabFormatLabActivity.getId(), "Lab A", "E", LuiServiceConstants.LAB_ACTIVITY_OFFERING_TYPE_KEY, instructors);

        lectureAndLabFormatLabA.setId(courseId + ":LEC-AND-LAB:LAB-A");

        coService.createActivityOffering(lectureAndLabFormatOffering.getId(), canonicalLectureAndLabFormatLabActivity.getId(), LuiServiceConstants.LAB_ACTIVITY_OFFERING_TYPE_KEY, lectureAndLabFormatLabA, context);


        // Lab B
        ActivityOfferingInfo lectureAndLabFormatLabB = CourseOfferingServiceTestDataUtils.createActivityOffering(term.getId(), courseOffering, lectureAndLabFormatOffering.getId(), null, canonicalLectureAndLabFormatLabActivity.getId(), "Lab B", "F", LuiServiceConstants.LAB_ACTIVITY_OFFERING_TYPE_KEY, instructors);

        lectureAndLabFormatLabB.setId(courseId + ":LEC-AND-LAB:LAB-B");

        coService.createActivityOffering(lectureAndLabFormatOffering.getId(), canonicalLectureAndLabFormatLabActivity.getId(), LuiServiceConstants.LAB_ACTIVITY_OFFERING_TYPE_KEY, lectureAndLabFormatLabB, context);

        // Lab C


        ActivityOfferingInfo lectureAndLabFormatLabC = CourseOfferingServiceTestDataUtils.createActivityOffering(term.getId(), courseOffering, lectureAndLabFormatOffering.getId(), null, canonicalLectureAndLabFormatLabActivity.getId(), "Lab C", "G", LuiServiceConstants.LAB_ACTIVITY_OFFERING_TYPE_KEY, instructors);

        lectureAndLabFormatLabC.setId(courseId + ":LEC-AND-LAB:LAB-C");

        coService.createActivityOffering(lectureAndLabFormatOffering.getId(), canonicalLectureAndLabFormatLabActivity.getId(), LuiServiceConstants.LAB_ACTIVITY_OFFERING_TYPE_KEY, lectureAndLabFormatLabC, context);


    }

    public String createCourse(TermInfo term, String subjectCode, String courseNumberCode, ContextInfo context) throws AlreadyExistsException, VersionMismatchException, CircularRelationshipException, DependentObjectsExistException, UnsupportedActionException, DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {

        String courseCode = subjectCode + courseNumberCode;
        String courseId = courseCode + "-ID";

        CourseInfo canonicalCourse = buildCanonicalCourse(courseId, term.getId(), subjectCode, courseCode, subjectCode + " " + courseCode, courseCode + " description 1");
        courseService.createCourse(canonicalCourse, context);

        return courseId;
    }

    public void createCourseOffering(TermInfo term, CourseInfo canonicalCourse, ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, DoesNotExistException, CircularRelationshipException, DependentObjectsExistException, UnsupportedActionException, DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        // course offering
        CourseOfferingInfo courseOffering = CourseOfferingServiceTestDataUtils.createCourseOffering(canonicalCourse, term.getId());

        courseOffering.setId("CO:" + canonicalCourse.getId());

        coService.createCourseOffering(canonicalCourse.getId(), term.getId(), LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, courseOffering, new ArrayList<String>(), context);


    }

    protected void createCourseCHEM123(TermInfo term, ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, DoesNotExistException, CircularRelationshipException, DependentObjectsExistException, UnsupportedActionException, DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {

        CourseInfo canonicalCourse = buildCanonicalCourse("CLU-1", term.getId(), "CHEM", "CHEM123", "Chemistry 123", "description 1");

        FormatInfo canonicalLectureOnlyFormat = buildCanonicalFormat("CHEM123:LEC-ONLY", canonicalCourse);

        ActivityInfo canonicalLectureOnlyLectureActivity = buildCanonicalActivity(LuServiceConstants.COURSE_ACTIVITY_LECTURE_TYPE_KEY, canonicalLectureOnlyFormat);

        FormatInfo canonicalLectureAndLabFormat = buildCanonicalFormat("CHEM123:LEC-AND-LAB", canonicalCourse);

        ActivityInfo canonicalLectureAndLabFormatLectureActivity = buildCanonicalActivity(LuServiceConstants.COURSE_ACTIVITY_LECTURE_TYPE_KEY, canonicalLectureAndLabFormat);
        ActivityInfo canonicalLectureAndLabFormatLabActivity = buildCanonicalActivity(LuServiceConstants.COURSE_ACTIVITY_LAB_TYPE_KEY, canonicalLectureAndLabFormat);

        courseService.createCourse(canonicalCourse, context);


        // course offering
        CourseOfferingInfo courseOffering = CourseOfferingServiceTestDataUtils.createCourseOffering(canonicalCourse, term.getId());

        courseOffering.setId("CO-1");

        coService.createCourseOffering(canonicalCourse.getId(), term.getId(), LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, courseOffering, new ArrayList<String>(), context);

        // FO-1: lecture only format
        FormatOfferingInfo lectureOnlyFormatOffering = CourseOfferingServiceTestDataUtils.createFormatOffering(courseOffering.getId(), canonicalLectureOnlyFormat.getId(), term.getId(), "Lecture", LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY);

        lectureOnlyFormatOffering.setId("CO-1:LEC-ONLY");

        coService.createFormatOffering(courseOffering.getId(), canonicalLectureOnlyFormat.getId(), LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY, lectureOnlyFormatOffering, context);

        // FO-2: lab and lecture format
        FormatOfferingInfo lectureAndLabFormatOffering = CourseOfferingServiceTestDataUtils.createFormatOffering(courseOffering.getId(), canonicalLectureAndLabFormat.getId(), term.getId(), "Lab & Lecture", new String[]{LuiServiceConstants.LAB_ACTIVITY_OFFERING_TYPE_KEY, LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY});

        lectureAndLabFormatOffering.setId("CO-1:LEC-AND-LAB");

        coService.createFormatOffering(courseOffering.getId(), canonicalLectureAndLabFormat.getId(), LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY, lectureAndLabFormatOffering, context);

        List<OfferingInstructorInfo> instructors = new ArrayList<OfferingInstructorInfo>();

        instructors.add(CourseOfferingServiceTestDataUtils.createInstructor("p1", "Instructor", 100.00F));


        // Format 1 Lecture offering A
        ActivityOfferingInfo lectureOnlyFormatLectureA = CourseOfferingServiceTestDataUtils.createActivityOffering(term.getId(), courseOffering, lectureOnlyFormatOffering.getId(), null, canonicalLectureOnlyLectureActivity.getId(), "Lecture A", "A", LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, instructors);

        lectureOnlyFormatLectureA.setId("CO-1:LEC-ONLY:LEC-A");

        coService.createActivityOffering(lectureOnlyFormatOffering.getId(), canonicalLectureOnlyLectureActivity.getId(), LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, lectureOnlyFormatLectureA, context);

        // Format 1 Lecture Offering B
        ActivityOfferingInfo lectureOnlyFormatLectureB = CourseOfferingServiceTestDataUtils.createActivityOffering(term.getId(), courseOffering, lectureOnlyFormatOffering.getId(), null, canonicalLectureOnlyLectureActivity.getId(), "Lecture B", "B", LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, instructors);

        lectureOnlyFormatLectureB.setId("CO-1:LEC-ONLY:LEC-B");

        coService.createActivityOffering(lectureOnlyFormatOffering.getId(), canonicalLectureOnlyLectureActivity.getId(), LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, lectureOnlyFormatLectureB, context);

        // Format 2:

        // Lecture A
        ActivityOfferingInfo lectureAndLabFormatLectureA = CourseOfferingServiceTestDataUtils.createActivityOffering(term.getId(), courseOffering, lectureAndLabFormatOffering.getId(), null, canonicalLectureAndLabFormatLectureActivity.getId(), "Lecture A", "C", LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, instructors);

        lectureAndLabFormatLectureA.setId("CO-1:LEC-AND-LAB:LEC-A");

        coService.createActivityOffering(lectureAndLabFormatOffering.getId(), canonicalLectureAndLabFormatLectureActivity.getId(), LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, lectureAndLabFormatLectureA, context);

        // Lecture B
        ActivityOfferingInfo lectureAndLabFormatLectureB = CourseOfferingServiceTestDataUtils.createActivityOffering(term.getId(), courseOffering, lectureAndLabFormatOffering.getId(), null, canonicalLectureAndLabFormatLectureActivity.getId(), "Lecture B", "D", LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, instructors);

        lectureAndLabFormatLectureB.setId("CO-1:LEC-AND-LAB:LEC-B");

        coService.createActivityOffering(lectureAndLabFormatOffering.getId(), canonicalLectureAndLabFormatLectureActivity.getId(), LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, lectureAndLabFormatLectureB, context);


        // Lab A
        ActivityOfferingInfo lectureAndLabFormatLabA = CourseOfferingServiceTestDataUtils.createActivityOffering(term.getId(), courseOffering, lectureAndLabFormatOffering.getId(), null, canonicalLectureAndLabFormatLabActivity.getId(), "Lab A", "E", LuiServiceConstants.LAB_ACTIVITY_OFFERING_TYPE_KEY, instructors);

        lectureAndLabFormatLabA.setId("CO-1:LEC-AND-LAB:LAB-A");

        coService.createActivityOffering(lectureAndLabFormatOffering.getId(), canonicalLectureAndLabFormatLabActivity.getId(), LuiServiceConstants.LAB_ACTIVITY_OFFERING_TYPE_KEY, lectureAndLabFormatLabA, context);


        // Lab B
        ActivityOfferingInfo lectureAndLabFormatLabB = CourseOfferingServiceTestDataUtils.createActivityOffering(term.getId(), courseOffering, lectureAndLabFormatOffering.getId(), null, canonicalLectureAndLabFormatLabActivity.getId(), "Lab B", "F", LuiServiceConstants.LAB_ACTIVITY_OFFERING_TYPE_KEY, instructors);

        lectureAndLabFormatLabB.setId("CO-1:LEC-AND-LAB:LAB-B");

        coService.createActivityOffering(lectureAndLabFormatOffering.getId(), canonicalLectureAndLabFormatLabActivity.getId(), LuiServiceConstants.LAB_ACTIVITY_OFFERING_TYPE_KEY, lectureAndLabFormatLabB, context);

        // Lab C


        ActivityOfferingInfo lectureAndLabFormatLabC = CourseOfferingServiceTestDataUtils.createActivityOffering(term.getId(), courseOffering, lectureAndLabFormatOffering.getId(), null, canonicalLectureAndLabFormatLabActivity.getId(), "Lab C", "G", LuiServiceConstants.LAB_ACTIVITY_OFFERING_TYPE_KEY, instructors);

        lectureAndLabFormatLabC.setId("CO-1:LEC-AND-LAB:LAB-C");

        coService.createActivityOffering(lectureAndLabFormatOffering.getId(), canonicalLectureAndLabFormatLabActivity.getId(), LuiServiceConstants.LAB_ACTIVITY_OFFERING_TYPE_KEY, lectureAndLabFormatLabC, context);


    }

    public static interface CourseOfferingCreationDetails {

        public void storeCourseId(String id);
        
        public void storeCourseOfferingId(String id);
        
        String getSubjectArea();

        String getCourseCode();

        String getCourseTitle();

        String getCourseDescription();

        /**
         * Get the type keys for the format given.
         * 
         * @param format
         * @return
         */
        List<String> getCanonicalActivityTypeKeys(int format);

        String getFormatOfferingName(int format);

        String[] getFormatOfferingActivityTypeKeys(int format);

        String getActivityOfferingTypeKey(int format, String activityTypeKey);

        String getActivityOfferingCode(int format, String activityTypeKey, int activity);

        String getActivityOfferingName(int format, String activityTypeKey, int activity);

        int getNumberOfFormats();

        int getNumberOfActivityOfferings(int format, String activityType);

        public int getActivityOfferingMaxEnrollment(int i, String typeKey, int j);

    }
    
    public String createCourseOffering(TermInfo term, CourseOfferingCreationDetails details, ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, DoesNotExistException, CircularRelationshipException, DependentObjectsExistException, UnsupportedActionException, DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {

        String courseOfferingId = null;
        
        CourseInfo canonicalCourse = buildCanonicalCourse(null, term.getId(), details.getSubjectArea(), details.getCourseCode(), details.getCourseTitle(), details.getCourseDescription());
        
        int formats = details.getNumberOfFormats();
        
       for (int i = 0; i < formats; i++) {
        
            FormatInfo canonicalFormat = buildCanonicalFormat(null, canonicalCourse);

            for (String canonicalActivityTypeKey : details.getCanonicalActivityTypeKeys(i)) {
                
               buildCanonicalActivity(canonicalActivityTypeKey, canonicalFormat);
            
            }
        }
        
       canonicalCourse = courseService.createCourse(canonicalCourse, context);
       
       details.storeCourseId(canonicalCourse.getId());


        // course offering
        CourseOfferingInfo courseOffering = CourseOfferingServiceTestDataUtils.createCourseOffering(canonicalCourse, term.getId());

        CourseOfferingInfo co = coService.createCourseOffering(canonicalCourse.getId(), term.getId(), LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, courseOffering, new ArrayList<String>(), context);

        courseOfferingId = co.getId();
        
        details.storeCourseOfferingId(co.getId());
        
        // create format offerings
        for (int i = 0; i < formats; i++) {
            
            FormatInfo canonicalFormat = canonicalCourse.getFormats().get(i);
            
            String canonicalFormatId = canonicalCourse.getFormats().get(i).getId();
            
            FormatOfferingInfo formatOffering = CourseOfferingServiceTestDataUtils.createFormatOffering(co.getId(), canonicalFormatId, term.getId(), details.getFormatOfferingName (i), details.getFormatOfferingActivityTypeKeys(i));

            FormatOfferingInfo fo = coService.createFormatOffering(co.getId(), canonicalFormatId, LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY, formatOffering, context);
            
            
            // create activity offerings
            for (ActivityInfo activity : canonicalFormat.getActivities()) {
                
                int numberOfEachActivityType = details.getNumberOfActivityOfferings(i, activity.getTypeKey());
                
                for (int j = 0; j < numberOfEachActivityType; j++) {
                    
                    List<OfferingInstructorInfo> instructors = new ArrayList<OfferingInstructorInfo>();

                    instructors.add(CourseOfferingServiceTestDataUtils
                            .createInstructor("p1", "Instructor", 100.00F));

                    ActivityOfferingInfo ao = CourseOfferingServiceTestDataUtils
                            .createActivityOffering(term.getId(),
                                    co, fo.getId(),
                                    null, activity.getId(), details
                                            .getActivityOfferingName(i,
                                                    activity.getTypeKey(), j), details
                                            .getActivityOfferingCode(i,
                                                    activity.getTypeKey(), j), details
                                            .getActivityOfferingTypeKey(i,
                                                    activity.getTypeKey()), instructors);
                    
                    int maxEnrollment = details.getActivityOfferingMaxEnrollment(i, activity.getTypeKey(), j);

                    ao.setMaximumEnrollment(maxEnrollment);
                    
                    coService.createActivityOffering(fo.getId(), activity.getId(), details.getActivityOfferingTypeKey(i, activity.getTypeKey()), ao, context);

                }
                
               
            }
            
            // create a default aoc
            ActivityOfferingClusterInfo aoc = CourseOfferingServiceTestDataUtils.createActivityOfferingCluster(fo.getId(), "Default", coService.getActivityOfferingsByFormatOffering(fo.getId(), context));

            // add in all AO's
            List<ActivityOfferingInfo> aos = coService.getActivityOfferingsWithoutClusterByFormatOffering(fo.getId(), context);
            
            Map<String, ActivityOfferingSetInfo>aoSetMap = new HashMap<String, ActivityOfferingSetInfo>();
            
            for (ActivityOfferingInfo activityOfferingInfo : aos) {
            
                ActivityOfferingSetInfo aoset = aoSetMap.get(activityOfferingInfo.getTypeKey());
            
                if (aoset == null) {
                    aoset = new ActivityOfferingSetInfo();
                    aoSetMap.put(activityOfferingInfo.getTypeKey(), aoset);
                }
                
                aoset.getActivityOfferingIds().add(activityOfferingInfo.getId());
                
                
            }
            
            aoc = coService.createActivityOfferingCluster(fo.getId(), CourseOfferingServiceConstants.AOC_ROOT_TYPE_KEY, aoc, context);
            
            
            // generate reg groups
            
            List<BulkStatusInfo> results = coService.generateRegistrationGroupsForFormatOffering(fo.getId(), context);
            
            log.debug("generated " + results + " reg groups");
            
        }
        
        return courseOfferingId;
    }

    public void createLabActivityOfferingForCHEM123(String labCode, ContextInfo context) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {


        CourseOfferingInfo co = coService.getCourseOffering("CO-1", context);

        CourseInfo c = courseService.getCourse(co.getCourseId(), context);

        // create the new activity offering

        FormatInfo targetFormat = null;

        for (FormatInfo format : c.getFormats()) {

            if (format.getId().equals("CHEM123:LEC-AND-LAB")) {
                targetFormat = format;
                break;
            }
        }

        List<OfferingInstructorInfo> instructors = new ArrayList<OfferingInstructorInfo>();

        instructors.add(CourseOfferingServiceTestDataUtils.createInstructor("p1", "Instructor", 100.00F));

        String canonicalActivityId = CourseOfferingServiceTestDataUtils.createCanonicalActivityId(targetFormat.getId(), LuiServiceConstants.LAB_ACTIVITY_OFFERING_TYPE_KEY);

        ActivityOfferingInfo lectureAndLabFormatLabC = CourseOfferingServiceTestDataUtils.createActivityOffering(co.getTermId(), co, "CO-1:LEC-AND-LAB", null, canonicalActivityId, labCode, "A", LuiServiceConstants.LAB_ACTIVITY_OFFERING_TYPE_KEY, instructors);

        lectureAndLabFormatLabC.setId("CO-1:LEC-AND-LAB:" + labCode);


        coService.createActivityOffering("CO-1:LEC-AND-LAB", canonicalActivityId, LuiServiceConstants.LAB_ACTIVITY_OFFERING_TYPE_KEY, lectureAndLabFormatLabC, context);


    }


    protected void createCourseENG101(TermInfo term, ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, DoesNotExistException, CircularRelationshipException, DependentObjectsExistException, UnsupportedActionException, DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {

        CourseInfo canonicalCourse = buildCanonicalCourse("CLU-2", term.getId(), "ENG", "ENG101", "Intro English", "Description of Intoroductory English");

        FormatInfo canonicalLectureOnlyFormat = buildCanonicalFormat("ENG101:LEC-ONLY", canonicalCourse);

        ActivityInfo canonicalLectureOnlyFormatLectureActivity = buildCanonicalActivity(LuServiceConstants.COURSE_ACTIVITY_LECTURE_TYPE_KEY, canonicalLectureOnlyFormat);

        courseService.createCourse(canonicalCourse, context);

        // create offerings
        CourseOfferingInfo co = CourseOfferingServiceTestDataUtils.createCourseOffering(canonicalCourse, term.getId());

        co.setId("CO-2");

        coService.createCourseOffering(canonicalCourse.getId(), term.getId(), LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, co, new ArrayList<String>(), context);

        // create format offering
        // FO-1: lecture only format
        FormatOfferingInfo fo1 = CourseOfferingServiceTestDataUtils.createFormatOffering(co.getId(), canonicalLectureOnlyFormat.getId(), term.getId(), "Lecture", LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY);

        fo1.setId("CO-2:LEC-ONLY");

        coService.createFormatOffering(co.getId(), canonicalLectureOnlyFormat.getId(), LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY, fo1, context);

        // create lecture activity
        List<OfferingInstructorInfo> instructors = new ArrayList<OfferingInstructorInfo>();

        instructors.add(CourseOfferingServiceTestDataUtils.createInstructor("p2", "Instructor", 100.00F));

        // Lecture A
        ActivityOfferingInfo lectureOnlyFormatLectureA = CourseOfferingServiceTestDataUtils.createActivityOffering(term.getId(), co, fo1.getId(), null, canonicalLectureOnlyFormatLectureActivity.getId(), "Lecture", "A", LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, instructors);

        lectureOnlyFormatLectureA.setId("CO-2:LEC-ONLY:LEC-A");

        coService.createActivityOffering(fo1.getId(), canonicalLectureOnlyFormatLectureActivity.getId(), LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, lectureOnlyFormatLectureA, context);

        // Lecture B
        ActivityOfferingInfo lectureOnlyFormatLectureB = CourseOfferingServiceTestDataUtils.createActivityOffering(term.getId(), co, fo1.getId(), null, canonicalLectureOnlyFormatLectureActivity.getId(), "Lecture", "B", LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, instructors);

        lectureOnlyFormatLectureB.setId("CO-2:LEC-ONLY:LEC-B");

        coService.createActivityOffering(fo1.getId(), canonicalLectureOnlyFormatLectureActivity.getId(), LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, lectureOnlyFormatLectureB, context);

        // create a default cluster
        List<ActivityOfferingInfo> activities = coService.getActivityOfferingsByFormatOffering("CO-2:LEC-ONLY", context);

        ActivityOfferingClusterInfo defaultAoc = CourseOfferingServiceTestDataUtils.createActivityOfferingCluster("CO-2:LEC-ONLY", "Default Cluster", activities);

        defaultAoc = coService.createActivityOfferingCluster("CO-2:LEC-ONLY", CourseOfferingServiceConstants.AOC_ROOT_TYPE_KEY, defaultAoc, context);

        // Create a Registration Group
        coService.generateRegistrationGroupsForFormatOffering("CO-2:LEC-ONLY", context);


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
        info.setTypeKey(LuServiceConstants.CREDIT_COURSE_LU_TYPE_KEY);
        info.setStateKey(DtoConstants.STATE_ACTIVE);
        info.setFormats(new ArrayList<FormatInfo>());
        ResultValuesGroupInfo rvg = new ResultValuesGroupInfo();
        rvg.setKey(LrcServiceConstants.RESULT_GROUP_KEY_KUALI_CREDITTYPE_CREDIT_1_0);
        info.getCreditOptions().add(rvg);
        info.getGradingOptions().add(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_LETTER);
        return info;
    }


    private ActivityInfo buildCanonicalActivity(String activityTypeKey, FormatInfo format) {

        ActivityInfo info = new ActivityInfo();
        info.setId(CourseOfferingServiceTestDataUtils.createCanonicalActivityId(format.getId(), activityTypeKey));
        info.setTypeKey(activityTypeKey);
        info.setStateKey(DtoConstants.STATE_ACTIVE);

        format.getActivities().add(info);

        return info;

    }

    private FormatInfo buildCanonicalFormat(String formatId, CourseInfo course) {

        FormatInfo info = new FormatInfo();
        info.setId(formatId);
        info.setTypeKey(LuServiceConstants.COURSE_FORMAT_TYPE_KEY);
        info.setStateKey(DtoConstants.STATE_ACTIVE);
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
        try {
            Date date = DateFormatters.DEFAULT_YEAR_MONTH_24HOUR_MILLISECONDS_FORMATTER.parse(str);
            return date;
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Bad date " + str + " in " + context);
        }
    }

    public TermInfo getFall2012() {
        return fall2012;
    }

    public void setFall2012(TermInfo fall2012) {
        this.fall2012 = fall2012;
    }

    public TermInfo getSpring2012() {
        return spring2012;
    }

    public void setSpring2012(TermInfo spring2012) {
        this.spring2012 = spring2012;
    }



    /**
     * @param acalService the acalService to set
     */
    public void setAcalService(AcademicCalendarService acalService) {
        this.acalService = acalService;
    }


    /**
     * @param coService the coService to set
     */
    public void setCoService(CourseOfferingService coService) {
        this.coService = coService;
    }


    /**
     * @param courseService the courseService to set
     */
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }


    /**
     * @param atpService the atpService to set
     */
    public void setAtpService(AtpService atpService) {
        this.atpService = atpService;
    }


    /**
     * @param courseCodeGenerator the courseCodeGenerator to set
     */
    public void setCourseCodeGenerator(
            CourseOfferingCodeGenerator courseCodeGenerator) {
        this.courseCodeGenerator = courseCodeGenerator;
    }


    /**
     * @param registrationGroupCodeGeneratorFactory the registrationGroupCodeGeneratorFactory to set
     */
    public void setRegistrationGroupCodeGeneratorFactory(
            RegistrationGroupCodeGeneratorFactory registrationGroupCodeGeneratorFactory) {
        this.registrationGroupCodeGeneratorFactory = registrationGroupCodeGeneratorFactory;
    }


    /**
     * @param acalDataLoader the acalDataLoader to set
     */
    public void setAcalDataLoader(AcalTestDataLoader acalDataLoader) {
        this.acalDataLoader = acalDataLoader;
    }


    /**
     * If set to false then the base data will not be loaded.
     * 
     * @param loadBaseData the loadBaseData to set
     */
    public void setLoadBaseData(boolean loadBaseData) {
        this.loadBaseData = loadBaseData;
    }
    
    

    
}
