package org.kuali.student.enrollment.class2.courseoffering.krms.termresolver;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentProgramRecordInfo;
import org.kuali.student.enrollment.academicrecord.service.AcademicRecordService;
import org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.util.AtpForCourseOfferingIdTermResolver;
import org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.util.CluIdsFromVersionIndIdTermResolver;
import org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.util.CluIdsInCluSetTermResolver;
import org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.util.CourseRecordsForCourseIdTermResolver;
import org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.util.CourseRecordsForCourseSetTermResolver;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.infc.CourseOffering;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.lum.lrc.service.util.MockLrcTestDataLoader;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.common.krms.data.KRMSEnrollmentEligibilityDataLoader;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.class1.organization.service.impl.OrgTestDataLoader;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.kuali.student.r2.core.population.service.PopulationService;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.kuali.student.r2.lum.lu.service.impl.CluDataLoader;
import org.kuali.student.r2.lum.lu.service.impl.CluSetDataLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:krms-test-with-mocks-context.xml"})
public class TestTermResolvers {

    public ContextInfo contextInfo = null;
    Map<String, Object> resolvedPrereqs = null;
    Map<String, String> parameters = null;
    private static boolean notSetup = true;

    @Resource(name = "orgServiceImpl")
    private OrganizationService organizationService;

    @Resource
    private AtpService atpService;

    @Resource(name = "cluService")
    private CluService cluService;

    @Resource(name = "courseService")
    private CourseService courseService;

    @Resource(name = "acadRecordService")
    private AcademicRecordService academicRecordService;

    @Resource
    private KRMSEnrollmentEligibilityDataLoader dataLoader;

    @Resource(name = "courseRegistrationService")
    private CourseRegistrationService courseRegistrationService;

    @Resource(name = "courseOfferingService")
    private CourseOfferingService courseOfferingService;

    @Resource(name = "populationService")
    private PopulationService populationService;

    @Resource(name = "lrcService")
    private LRCService lrcService;

    @Before
    public void setUp() throws Exception {
            contextInfo = new ContextInfo();
            contextInfo.setLocale(new LocaleInfo());
            contextInfo.setPrincipalId("admin");
            dataLoader.setContextInfo(contextInfo);
            if(notSetup){
                loadCluData();
                loadAcadRecordData();
                loadRegistrationData();
                loadProgramRecordData();
                loadPopulationData();
                loadOrgData();
                new MockLrcTestDataLoader(this.lrcService).loadData();
                notSetup = false;
            }

            resolvedPrereqs = getDefaultPrerequisites();
            parameters = getDefaultParameters();
    }


    private TermResolver<List<String>> getCluIdsTermResolver() {
        CluIdsFromVersionIndIdTermResolver termResolver = new CluIdsFromVersionIndIdTermResolver();
        termResolver.setCluVersionService(cluService);
        return termResolver;
    }

    private CompletedCourseTermResolver getCompletedCourseTermResolver(){
        CompletedCourseTermResolver termResolver = new CompletedCourseTermResolver();
        termResolver.setAcademicRecordService(academicRecordService);
        termResolver.setCluIdsTermResolver(this.getCluIdsTermResolver());
        return termResolver;
    }

    @Test
    public void testCompletedCourseTermResolver() throws Exception {
        //Setup the term resolver
        CompletedCourseTermResolver termResolver = this.getCompletedCourseTermResolver();

        //Setup data
        resolvedPrereqs.put(KSKRMSServiceConstants.TERM_PREREQUISITE_PERSON_ID, KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID);
        String versionIndId = cluService.getClu("COURSE1", contextInfo).getVersion().getVersionIndId();
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_COURSE_CLU_KEY, versionIndId);

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSServiceConstants.TERM_RESOLVER_COMPLETEDCOURSE);

        //Evaluate term Resolver
        Boolean isCompleted = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(isCompleted);
        assertTrue(isCompleted);

        //Replace clu parameter with non existing course.
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_COURSE_CLU_KEY, "AAA999");

        //Evaluate term Resolver
        isCompleted = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(isCompleted);
        assertFalse(isCompleted);
    }

    private TermResolver<List<String>> getCluIdsInCluSetTermResolver() {
        CluIdsInCluSetTermResolver termResolver = new CluIdsInCluSetTermResolver();
        termResolver.setCluService(cluService);
        return termResolver;
    }

    @Test
    public void testCompletedCoursesTermResolver() {
        //Setup the term resolver
        CompletedCoursesTermResolver termResolver = new CompletedCoursesTermResolver();
        termResolver.setCluIdsInCluSetTermResolver(this.getCluIdsInCluSetTermResolver());
        termResolver.setCompletedCourseTermResolver(this.getCompletedCourseTermResolver());

        //Setup data
        resolvedPrereqs.put(KSKRMSServiceConstants.TERM_PREREQUISITE_PERSON_ID, KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID);
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_COURSE_CLUSET_KEY, "COURSE-SET2");

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSServiceConstants.TERM_RESOLVER_COMPLETEDCOURSES);

        //Evaluate term Resolver
        Boolean isAllCompleted = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(isAllCompleted);
        assertFalse(isAllCompleted);

        //Reset to student 2 and re-evaluate.
        resolvedPrereqs.put(KSKRMSServiceConstants.TERM_PREREQUISITE_PERSON_ID, KRMSEnrollmentEligibilityDataLoader.STUDENT_TWO_ID);
        isAllCompleted = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(isAllCompleted);
        assertTrue(isAllCompleted);
    }

    @Test
    public void testNumberOfCompletedCoursesTermResolver() {
        //Setup the term resolver
        NumberOfCompletedCoursesTermResolver termResolver = new NumberOfCompletedCoursesTermResolver();
        termResolver.setCluIdsInCluSetTermResolver(this.getCluIdsInCluSetTermResolver());
        termResolver.setCompletedCourseTermResolver(this.getCompletedCourseTermResolver());

        //Setup data
        resolvedPrereqs.put(KSKRMSServiceConstants.TERM_PREREQUISITE_PERSON_ID, KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID);
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_COURSE_CLUSET_KEY, "COURSE-SET3");

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSServiceConstants.TERM_RESOLVER_NUMBEROFCOMPLETEDCOURSES);

        //Evaluate term Resolver
        Integer numberOfCompletedCourses = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(numberOfCompletedCourses);
        assertEquals(new Integer(1), numberOfCompletedCourses);

        //Reset to student 2 and re-evaluate.
        resolvedPrereqs.put(KSKRMSServiceConstants.TERM_PREREQUISITE_PERSON_ID, KRMSEnrollmentEligibilityDataLoader.STUDENT_TWO_ID);
        numberOfCompletedCourses = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(numberOfCompletedCourses);
        assertEquals(new Integer(2), numberOfCompletedCourses);
    }

    private TermResolver<List<StudentCourseRecordInfo>> getCourseRecordsForCourseIdTermResolver() {
        CourseRecordsForCourseIdTermResolver termResolver = new CourseRecordsForCourseIdTermResolver();
        termResolver.setAcademicRecordService(academicRecordService);
        termResolver.setCluIdsTermResolver(this.getCluIdsTermResolver());
        return termResolver;
    }

    private TermResolver<List<StudentCourseRecordInfo>> getCourseRecordsForCourseSetTermResolver() {
        CourseRecordsForCourseSetTermResolver termResolver = new CourseRecordsForCourseSetTermResolver();
        termResolver.setCluIdsInCluSetTermResolver(this.getCluIdsInCluSetTermResolver());
        termResolver.setCourseRecordsForCourseIdTermResolver(this.getCourseRecordsForCourseIdTermResolver());
        return termResolver;
    }

    @Test
    public void testNumberOfCreditsFromCoursesTermResolver() {
        //Setup the term resolver
        CreditsEarnedFromCoursesTermResolver termResolver = new CreditsEarnedFromCoursesTermResolver();
        termResolver.setCourseRecordsForCourseSetTermResolver(this.getCourseRecordsForCourseSetTermResolver());

        //Setup data
        resolvedPrereqs.put(KSKRMSServiceConstants.TERM_PREREQUISITE_PERSON_ID, KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID);
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_COURSE_CLUSET_KEY, "COURSE-SET3");

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSServiceConstants.TERM_RESOLVER_NUMBEROFCREDITSFROMCOMPLETEDCOURSES);

        //Evaluate term Resolver
        Integer credits = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(credits);
        assertEquals(new Integer(3), credits);

        resolvedPrereqs.put(KSKRMSServiceConstants.TERM_PREREQUISITE_PERSON_ID, KRMSEnrollmentEligibilityDataLoader.STUDENT_TWO_ID);
        credits = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(credits);
        assertEquals(new Integer(6), credits);
    }

    private EnrolledCourseTermResolver getEnrolledCourseTermResolver(){
        EnrolledCourseTermResolver termResolver = new EnrolledCourseTermResolver();
        termResolver.setCourseRegistrationService(courseRegistrationService);
        termResolver.setCourseOfferingService(courseOfferingService);
        termResolver.setCluIdsTermResolver(this.getCluIdsTermResolver());
        return termResolver;
    }

    @Test
    public void testEnrolledCourseTermResolver() throws Exception {
        //Setup the term resolver
        EnrolledCourseTermResolver termResolver = this.getEnrolledCourseTermResolver();

        //Setup data for unsubmitted registration request.
        resolvedPrereqs.put(KSKRMSServiceConstants.TERM_PREREQUISITE_PERSON_ID, KRMSEnrollmentEligibilityDataLoader.STUDENT_TWO_ID);
        resolvedPrereqs.put(KSKRMSServiceConstants.TERM_PREREQUISITE_TERM_ID, dataLoader.getFallTermId());
        String versionIndId = cluService.getClu("COURSE8", contextInfo).getVersion().getVersionIndId();
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_COURSE_CLU_KEY, versionIndId);

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSServiceConstants.TERM_RESOLVER_ENROLLEDCOURSE);

        //Evaluate term Resolver
        Boolean isEnrolled = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(isEnrolled);
        assertTrue(isEnrolled);

        //Setup data for submitted enrollment.
        resolvedPrereqs.put(KSKRMSServiceConstants.TERM_PREREQUISITE_PERSON_ID, KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID);
        versionIndId = cluService.getClu("COURSE3", contextInfo).getVersion().getVersionIndId();
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_COURSE_CLU_KEY, versionIndId);
        isEnrolled = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(isEnrolled);
        assertTrue(isEnrolled);

        //Setup data for completed course, completed course should not be included, only concurrently enrolled courses.
        resolvedPrereqs.put(KSKRMSServiceConstants.TERM_PREREQUISITE_PERSON_ID, KRMSEnrollmentEligibilityDataLoader.STUDENT_THREE_ID);
        versionIndId = cluService.getClu("COURSE2", contextInfo).getVersion().getVersionIndId();
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_COURSE_CLU_KEY, versionIndId);
        isEnrolled = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(isEnrolled);
        assertFalse(isEnrolled);
    }

    @Test
    public void testEnrolledCoursesTermResolver() {
        //Setup the term resolver
        EnrolledCoursesTermResolver termResolver = new EnrolledCoursesTermResolver();
        termResolver.setCluIdsInCluSetTermResolver(this.getCluIdsInCluSetTermResolver());
        termResolver.setEnrolledCourseTermResolver(this.getEnrolledCourseTermResolver());

        //Setup data
        resolvedPrereqs.put(KSKRMSServiceConstants.TERM_PREREQUISITE_PERSON_ID, KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID);
        resolvedPrereqs.put(KSKRMSServiceConstants.TERM_PREREQUISITE_TERM_ID, dataLoader.getFallTermId());
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_COURSE_CLUSET_KEY, "COURSE-SET1");

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSServiceConstants.TERM_RESOLVER_ENROLLEDCOURSES);

        //Evaluate term Resolver
        Boolean isAllEnrolled = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(isAllEnrolled);
        assertFalse(isAllEnrolled);

        //Student two is enrolled for course1.
        resolvedPrereqs.put(KSKRMSServiceConstants.TERM_PREREQUISITE_PERSON_ID, KRMSEnrollmentEligibilityDataLoader.STUDENT_TWO_ID);
        isAllEnrolled = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(isAllEnrolled);
        assertTrue(isAllEnrolled);

        //Check for a different term
        resolvedPrereqs.put(KSKRMSServiceConstants.TERM_PREREQUISITE_TERM_ID, dataLoader.getSpringTermId());
        isAllEnrolled = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(isAllEnrolled);
        assertFalse(isAllEnrolled);
    }

    @Test
    public void testNumberOfEnrolledCoursesTermResolver() {
        //Setup the term resolver
        NumberOfEnrolledCoursesTermResolver termResolver = new NumberOfEnrolledCoursesTermResolver();
        termResolver.setCluIdsInCluSetTermResolver(this.getCluIdsInCluSetTermResolver());
        termResolver.setEnrolledCourseTermResolver(this.getEnrolledCourseTermResolver());

        //Setup data
        resolvedPrereqs.put(KSKRMSServiceConstants.TERM_PREREQUISITE_PERSON_ID, KRMSEnrollmentEligibilityDataLoader.STUDENT_TWO_ID);
        resolvedPrereqs.put(KSKRMSServiceConstants.TERM_PREREQUISITE_TERM_ID, dataLoader.getFallTermId());
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_COURSE_CLUSET_KEY, "COURSE-SET3");

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSServiceConstants.TERM_RESOLVER_NUMBEROFENROLLEDCOURSES);

        //Evaluate term Resolver
        Integer numberOfEnrolledCourses = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(numberOfEnrolledCourses);
        assertEquals(new Integer(2), numberOfEnrolledCourses);

        //Check student 2
        resolvedPrereqs.put(KSKRMSServiceConstants.TERM_PREREQUISITE_PERSON_ID, KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID);
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_COURSE_CLUSET_KEY, "COURSE-SET2");
        numberOfEnrolledCourses = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(numberOfEnrolledCourses);
        assertEquals(new Integer(1), numberOfEnrolledCourses);
    }

    @Test
    public void testGPATermResolver() {
        //Setup the term resolver
        GPATermResolver termResolver = new GPATermResolver();
        termResolver.setAcademicRecordService(academicRecordService);

        //Setup data
        resolvedPrereqs.put(KSKRMSServiceConstants.TERM_PREREQUISITE_PERSON_ID, KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID);

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSServiceConstants.TERM_RESOLVER_GPA);

        //Evaluate term Resolver
        Float gpa = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(gpa);
        assertEquals(new Float(3.9), gpa);
    }

    @Test
    public void testGPAForCoursesTermResolver() {
        //Setup the term resolver
        GPAForCoursesTermResolver termResolver = new GPAForCoursesTermResolver();
        termResolver.setAcademicRecordService(academicRecordService);
        termResolver.setCourseRecordsForCourseSetTermResolver(this.getCourseRecordsForCourseSetTermResolver());
        //Setup data
        resolvedPrereqs.put(KSKRMSServiceConstants.TERM_PREREQUISITE_PERSON_ID, KRMSEnrollmentEligibilityDataLoader.STUDENT_TWO_ID);
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_COURSE_CLUSET_KEY, "COURSE-SET3");

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSServiceConstants.TERM_RESOLVER_GPAFORCOURSES);

        //Evaluate term Resolver
        Float gpa = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(gpa);
        assertEquals(new Float(3.0), gpa);
    }

    @Test
    public void testGPAForDurationTermResolver() {
        //Setup the term resolver
        GPAForDurationTermResolver termResolver = new GPAForDurationTermResolver();
        termResolver.setAcademicRecordService(academicRecordService);

        //Setup data
        resolvedPrereqs.put(KSKRMSServiceConstants.TERM_PREREQUISITE_PERSON_ID, KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID);
        //parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_CLU_KEY, "DTC101");

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSServiceConstants.TERM_RESOLVER_GPAFORDURATION);

        //Evaluate term Resolver
        Float gpa = termResolver.resolve(resolvedPrereqs, parameters);
        //assertNotNull(gpa);
        //assertEquals(new Float(3.9), gpa);
    }

    private CourseWithGradeTermResolver getCourseWithGradeTermResolver() {
        CourseWithGradeTermResolver termResolver = new CourseWithGradeTermResolver();
        termResolver.setCourseRecordsForCourseIdTermResolver(this.getCourseRecordsForCourseIdTermResolver());
        termResolver.setLrcService(lrcService);
        return termResolver;
    }

    @Test
    public void testCourseWithGradeTermResolver() throws Exception {
        //Setup the term resolver
        CourseWithGradeTermResolver termResolver = getCourseWithGradeTermResolver();

        //Setup data
        resolvedPrereqs.put(KSKRMSServiceConstants.TERM_PREREQUISITE_PERSON_ID, KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID);
        String versionIndId = cluService.getClu("COURSE1", contextInfo).getVersion().getVersionIndId();
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_COURSE_CLU_KEY, versionIndId);
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_GRADE_KEY, "kuali.result.value.grade.letter.plus.minus.b");
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_GRADE_TYPE_KEY, "kuali.result.value.grade.letter.plus.minus");

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSServiceConstants.TERM_RESOLVER_COURSEWITHGRADE);

        //Evaluate term Resolver
        Boolean courseWithGrade = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(courseWithGrade);
        assertTrue(courseWithGrade);

        versionIndId = cluService.getClu("COURSE2", contextInfo).getVersion().getVersionIndId();
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_COURSE_CLU_KEY, versionIndId);
        courseWithGrade = termResolver.resolve(resolvedPrereqs, parameters);
        assertFalse(courseWithGrade);
    }

    @Test
    public void testCoursesWithGradeTermResolver() {
        //Setup the term resolver
        CoursesWithGradeTermResolver termResolver = new CoursesWithGradeTermResolver();
        termResolver.setCluIdsInCluSetTermResolver(this.getCluIdsInCluSetTermResolver());
        termResolver.setCourseWithGradeTermResolver(this.getCourseWithGradeTermResolver());

        //Setup data
        resolvedPrereqs.put(KSKRMSServiceConstants.TERM_PREREQUISITE_PERSON_ID, KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID);
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_COURSE_CLUSET_KEY, "COURSE-SET1");
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_GRADE_KEY, "kuali.result.value.grade.letter.plus.minus.b");
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_GRADE_TYPE_KEY, "kuali.result.value.grade.letter.plus.minus");

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSServiceConstants.TERM_RESOLVER_COURSESWITHGRADE);

        //Evaluate term Resolver
        Boolean courseWithGrade = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(courseWithGrade);
        assertTrue(courseWithGrade);

        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_COURSE_CLUSET_KEY, "COURSE-SET2");
        courseWithGrade = termResolver.resolve(resolvedPrereqs, parameters);
        assertFalse(courseWithGrade);
    }

    @Test
    public void testNumberOfCoursesWithGradeTermResolver() {
        //Setup the term resolver
        NumberOfCoursesWithGradeTermResolver termResolver = new NumberOfCoursesWithGradeTermResolver();
        termResolver.setCluIdsInCluSetTermResolver(this.getCluIdsInCluSetTermResolver());
        termResolver.setCourseWithGradeTermResolver(this.getCourseWithGradeTermResolver());

        //Setup data
        resolvedPrereqs.put(KSKRMSServiceConstants.TERM_PREREQUISITE_PERSON_ID, KRMSEnrollmentEligibilityDataLoader.STUDENT_TWO_ID);
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_COURSE_CLUSET_KEY, "COURSE-SET2");
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_GRADE_KEY, "kuali.result.value.grade.letter.plus.minus.b");
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_GRADE_TYPE_KEY, "kuali.result.value.grade.letter.plus.minus");

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSServiceConstants.TERM_RESOLVER_NUMBEROFCOURSESWITHGRADE);

        //Evaluate term Resolver
        Integer numberOfCoursesWithGrade = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(numberOfCoursesWithGrade);
        assertEquals(new Integer(2), numberOfCoursesWithGrade);

        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_COURSE_CLUSET_KEY, "COURSE-SET3");
        numberOfCoursesWithGrade = termResolver.resolve(resolvedPrereqs, parameters);
        assertEquals(new Integer(1), numberOfCoursesWithGrade);
    }

    @Test
    public void testCreditsEarnedTermResolver() {
        //Setup the term resolver
        CreditsEarnedTermResolver termResolver = new CreditsEarnedTermResolver();
        termResolver.setAcademicRecordService(academicRecordService);

        //Setup data
        resolvedPrereqs.put(KSKRMSServiceConstants.TERM_PREREQUISITE_PERSON_ID, KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID);

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSServiceConstants.TERM_RESOLVER_NUMBEROFCREDITSEARNED);

        //Evaluate term Resolver
        Integer credits = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(credits);
        assertEquals(new Integer(9), credits);

        resolvedPrereqs.put(KSKRMSServiceConstants.TERM_PREREQUISITE_PERSON_ID, KRMSEnrollmentEligibilityDataLoader.STUDENT_TWO_ID);
        credits = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(credits);
        assertEquals(new Integer(12), credits);
    }

    @Test
    public void testOrganizationCreditsTermResolver() {
        //Setup the term resolver
        CreditsEarnedFromOrganizationTermResolver termResolver = new CreditsEarnedFromOrganizationTermResolver();
        termResolver.setAcademicRecordService(academicRecordService);
        termResolver.setCourseOfferingService(courseOfferingService);

        //Setup data
        resolvedPrereqs.put(KSKRMSServiceConstants.TERM_PREREQUISITE_PERSON_ID, KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID);
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_ORGANIZATION_KEY, "ORG1");

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSServiceConstants.TERM_RESOLVER_NUMBEROFCREDITSFROMORGANIZATION);

        //Evaluate term Resolver
        Integer credits = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(credits);
        assertEquals(new Integer(9), credits);

        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_ORGANIZATION_KEY, "ORG2");
        credits = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(credits);
        assertEquals(new Integer(0), credits);
    }

    @Test
    public void testAdminOrgPermissionRequiredTermResolver() {


        //Setup the term resolver
        AdminOrgPermissionTermResolver termResolver = new AdminOrgPermissionTermResolver();
        termResolver.setOrganizationService(organizationService);

        //Setup data
        resolvedPrereqs.put(KSKRMSServiceConstants.TERM_PREREQUISITE_PERSON_ID, KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID);
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_ORGANIZATION_KEY, "1");

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSServiceConstants.TERM_RESOLVER_ADMINORGANIZATIONPERMISSIONREQUIRED);

        //Evaluate term Resolver
        Boolean hasPermission = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(hasPermission);
        assertTrue(hasPermission);
    }

    @Test
    public void testScoreTermResolver() {
        //Setup the term resolver
        ScoreTermResolver termResolver = new ScoreTermResolver();

        //Setup data
        resolvedPrereqs.put(KSKRMSServiceConstants.TERM_PREREQUISITE_PERSON_ID, KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID);
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TEST_CLU_KEY, "1");

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSServiceConstants.TERM_RESOLVER_SCOREONTEST);

        //Evaluate term Resolver
        //Integer score = termResolver.resolve(resolvedPrereqs, parameters);
        //assertNotNull(score);
        //assertEquals(new Integer(0), score);
    }

    @Test
    public void testFreeTextTermResolver() {
        //Setup the term resolver
        FreeFormTextTermResolver termResolver = new FreeFormTextTermResolver();

        //Setup data
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_FREE_TEXT_KEY, "1");

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSServiceConstants.TERM_RESOLVER_FREEFORMTEXT);

        //Evaluate term Resolver
        Boolean result = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(result);
        assertTrue(result);
    }

    @Test
    public void testProgramCoursesOrgDurationTermResolver() {
        //Setup the term resolver
        ProgramCoursesOrgDurationTermResolver termResolver = new ProgramCoursesOrgDurationTermResolver();

        //Setup data
        resolvedPrereqs.put(KSKRMSServiceConstants.TERM_PREREQUISITE_PERSON_ID, KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID);
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_ORGANIZATION_KEY, "1");
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_DURATION_KEY, "1");
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_DURATION_TYPE_KEY, "1");

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSServiceConstants.TERM_RESOLVER_ADMITTEDTOPROGRAMLIMITCOURSESINORGFORDURATION);

        //Evaluate term Resolver
        Integer result = termResolver.resolve(resolvedPrereqs, parameters);
        //assertNotNull(result);
        //assertEquals(new Integer(0), result);
    }

    @Test
    public void testAdmittedToProgramTermResolver() {
        //Setup the term resolver
        AdmittedProgramTermResolver termResolver = new AdmittedProgramTermResolver();
        termResolver.setAcademicRecordService(academicRecordService);

        //Setup data
        resolvedPrereqs.put(KSKRMSServiceConstants.TERM_PREREQUISITE_PERSON_ID, KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID);
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_COURSE_CLU_KEY, "PROGRAM1");

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSServiceConstants.TERM_RESOLVER_ADMITTEDTOPROGRAM);

        //Evaluate term Resolver
        Boolean isAdmitted = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(isAdmitted);
        assertTrue(isAdmitted);

        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_COURSE_CLU_KEY, "PROGRAM2");
        isAdmitted = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(isAdmitted);
        assertFalse(isAdmitted);
    }

    @Test
    public void testAdmittedToProgramAtCourseCampusTermResolver() {
        //Setup the term resolver
        AdmittedProgramAtCourseCampusTermResolver termResolver = new AdmittedProgramAtCourseCampusTermResolver();

        //Setup data
        resolvedPrereqs.put(KSKRMSServiceConstants.TERM_PREREQUISITE_PERSON_ID, KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID);
        resolvedPrereqs.put(KSKRMSServiceConstants.TERM_PREREQUISITE_CLU_ID, "Program1");

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSServiceConstants.TERM_RESOLVER_ADMITTEDTOPROGRAMATCOURSECAMPUS);

        //Evaluate term Resolver
        Boolean isAdmitted = termResolver.resolve(resolvedPrereqs, parameters);
        //assertNotNull(isAdmitted);
        //assertTrue(isAdmitted);
    }

    @Test
    public void testAdmittedToProgramWithClassStandingTermResolver() {
        //Setup the term resolver
        AdmittedProgramWithClassStandingTermResolver termResolver = new AdmittedProgramWithClassStandingTermResolver();

        //Setup data
        resolvedPrereqs.put(KSKRMSServiceConstants.TERM_PREREQUISITE_PERSON_ID, KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID);
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_CLASS_STANDING_KEY, "1");
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_COURSE_CLU_KEY, "1");

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSServiceConstants.TERM_RESOLVER_ADMITTEDTOPROGRAMWITHCLASSSTANDING);

        //Evaluate term Resolver
        //Boolean isAdmitted = termResolver.resolve(resolvedPrereqs, parameters);
        //assertNotNull(isAdmitted);
        //assertTrue(isAdmitted);
    }

    @Test
    public void testCompletedCourseForTermTermResolver() throws Exception{
        //Setup the term resolver
        CompletedCourseForTermTermResolver termResolver = new CompletedCourseForTermTermResolver();
        termResolver.setAcademicRecordService(academicRecordService);
        termResolver.setCourseOfferingService(courseOfferingService);
        termResolver.setCluIdsTermResolver(this.getCluIdsTermResolver());

        //Setup data
        resolvedPrereqs.put(KSKRMSServiceConstants.TERM_PREREQUISITE_PERSON_ID, KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID);
        String versionIndId = cluService.getClu("COURSE1", contextInfo).getVersion().getVersionIndId();
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_COURSE_CLU_KEY, versionIndId);
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TERM_KEY, dataLoader.getSpringTermId());

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSServiceConstants.TERM_RESOLVER_COMPLETEDCOURSEFORTERM);

        //Evaluate term Resolver
        Boolean completed = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(completed);
        assertFalse(completed);

        //Reset the term id.
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TERM_KEY, dataLoader.getFallTermId());
        completed = termResolver.resolve(resolvedPrereqs, parameters);
        assertTrue(completed);
    }

    private TermResolver<AtpInfo> getAtpForCOIdTermResolver() {
        AtpForCourseOfferingIdTermResolver termResolver = new AtpForCourseOfferingIdTermResolver();
        termResolver.setCourseOfferingService(courseOfferingService);
        termResolver.setAtpService(atpService);
        return termResolver;
    }

    @Test
    public void testCompletedCourseAsOfTermTermResolver() throws Exception{
        //Setup the term resolver
        CompletedCourseAsOfTermTermResolver termResolver = new CompletedCourseAsOfTermTermResolver();
        termResolver.setAcademicRecordService(academicRecordService);
        termResolver.setAtpService(atpService);
        termResolver.setCluIdsTermResolver(this.getCluIdsTermResolver());
        termResolver.setAtpForCOIdTermResolver(this.getAtpForCOIdTermResolver());

        //Setup data
        resolvedPrereqs.put(KSKRMSServiceConstants.TERM_PREREQUISITE_PERSON_ID, KRMSEnrollmentEligibilityDataLoader.STUDENT_THREE_ID);
        String versionIndId = cluService.getClu("COURSE1", contextInfo).getVersion().getVersionIndId();
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_COURSE_CLU_KEY, versionIndId);
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TERM_KEY, dataLoader.getSummerTermId());

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSServiceConstants.TERM_RESOLVER_COMPLETEDCOURSEFORTERM);

        //Evaluate term Resolver
        Boolean completed = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(completed);
        assertFalse(completed);

        //Reset the term id.
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TERM_KEY, dataLoader.getWinterTermId());
        completed = termResolver.resolve(resolvedPrereqs, parameters);
        assertTrue(completed);
    }

    @Test
    public void testCompletedCoursePriorToTermTermResolver() throws Exception{
        //Setup the term resolver
        CompletedCoursePriorToTermTermResolver termResolver = new CompletedCoursePriorToTermTermResolver();
        termResolver.setAcademicRecordService(academicRecordService);
        termResolver.setAtpService(atpService);
        termResolver.setCluIdsTermResolver(this.getCluIdsTermResolver());
        termResolver.setAtpForCOIdTermResolver(this.getAtpForCOIdTermResolver());

        //Setup data
        resolvedPrereqs.put(KSKRMSServiceConstants.TERM_PREREQUISITE_PERSON_ID, KRMSEnrollmentEligibilityDataLoader.STUDENT_THREE_ID);
        String versionIndId = cluService.getClu("COURSE1", contextInfo).getVersion().getVersionIndId();
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_COURSE_CLU_KEY, versionIndId);
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TERM_KEY, dataLoader.getWinterTermId());

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSServiceConstants.TERM_RESOLVER_COMPLETEDCOURSEPRIORTOTERM);

        //Evaluate term Resolver
        Boolean completed = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(completed);
        assertFalse(completed);

        //Reset the term id.
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TERM_KEY, dataLoader.getSummerTermId());
        completed = termResolver.resolve(resolvedPrereqs, parameters);
        assertTrue(completed);
    }

    @Test
    public void testCompletedCourseBetweenTermsTermResolver() throws Exception {
        //Setup the term resolver
        CompletedCourseBetweenTermsTermResolver termResolver = new CompletedCourseBetweenTermsTermResolver();
        termResolver.setAcademicRecordService(academicRecordService);
        termResolver.setAtpService(atpService);
        termResolver.setCluIdsTermResolver(this.getCluIdsTermResolver());
        termResolver.setAtpForCOIdTermResolver(this.getAtpForCOIdTermResolver());

        //Setup data
        resolvedPrereqs.put(KSKRMSServiceConstants.TERM_PREREQUISITE_PERSON_ID, KRMSEnrollmentEligibilityDataLoader.STUDENT_THREE_ID);
        String versionIndId = cluService.getClu("COURSE1", contextInfo).getVersion().getVersionIndId();
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_COURSE_CLU_KEY, versionIndId);
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TERM_KEY, dataLoader.getSummerTermId());
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TERM2_KEY, dataLoader.getFallTermId());

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSServiceConstants.TERM_RESOLVER_COMPLETEDCOURSEBETWEENTERMS);

        //Evaluate term Resolver
        Boolean completed = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(completed);
        assertFalse(completed);

        //Reset the term id.
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TERM_KEY, dataLoader.getWinterTermId());
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TERM2_KEY, dataLoader.getFallTermId());
        completed = termResolver.resolve(resolvedPrereqs, parameters);
        assertTrue(completed);
    }

    @Test
    public void testPopulationTermResolver() {
        //Setup the term resolver
        PopulationTermResolver termResolver = new PopulationTermResolver();
        termResolver.setPopulationService(populationService);

        //Setup data
        resolvedPrereqs.put(KSKRMSServiceConstants.TERM_PREREQUISITE_PERSON_ID, "SENIOR_ONLY_STUDENTS100000285");
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_POPULATION_KEY, "SENIOR_ONLY_STUDENTS");

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSServiceConstants.TERM_RESOLVER_POPULATION);

        //Evaluate term Resolver
        Boolean isInPopulation = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(isInPopulation);
        assertTrue(isInPopulation);

    }

    @Test
    public void testInstructorPermissionTermResolver() {
        //Setup the term resolver
        InstructorPermissionTermResolver termResolver = new InstructorPermissionTermResolver();

        //Setup data
        resolvedPrereqs.put(KSKRMSServiceConstants.TERM_PREREQUISITE_PERSON_ID, KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID);
        resolvedPrereqs.put(KSKRMSServiceConstants.TERM_PREREQUISITE_CLU_ID, "ORG1");

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSServiceConstants.TERM_RESOLVER_INSTRUCTORPERMISSION);

        //Evaluate term Resolver
        Boolean hasPermission = termResolver.resolve(resolvedPrereqs, parameters);
        //assertNotNull(hasPermission);
        //assertTrue(hasPermission);
    }

    //@Test
    //public void testNumberOfEnrollmentsForCourseTermResolver() {
    //Setup the term resolver
    //    NumberOfEnrollmentsForCourseTermResolver termResolver = new NumberOfEnrollmentsForCourseTermResolver();
    //}

    private void validateTermResolver(TermResolver termResolver, Map<String, Object> prereqs, Map<String, String> parameters, String output) {

        //Check the term name.
        assertEquals(output, termResolver.getOutput());

        //Check if prerequisites are listed
        validateAttributeSet("Prerequisites list does not contain ", prereqs.keySet(), termResolver.getPrerequisites());

        //Check if parameters are listed
        validateAttributeSet("Parameters list does not contain ", parameters.keySet(), termResolver.getParameterNames());
    }

    private void validateAttributeSet(String message, Set<String> names, Set<String> keys) {
        if (keys == null) {
            return;
        }

        for (String key : keys) {
            assertTrue(message + key, names.contains(key));
        }
    }

    private Map<String, Object> getDefaultPrerequisites() {
        Map<String, Object> resolvedPrereqs = new HashMap<String, Object>();
        resolvedPrereqs.put(KSKRMSServiceConstants.TERM_PREREQUISITE_CONTEXTINFO, contextInfo);
        return resolvedPrereqs;
    }

    private Map<String, String> getDefaultParameters() {
        Map<String, String> parameters = new HashMap<String, String>();
        return parameters;
    }

    private void loadCluData() {
        CluDataLoader cluDataLoader = new CluDataLoader();
        cluDataLoader.setCluService(cluService);
        cluDataLoader.setContextInfo(contextInfo);
        cluDataLoader.load();

        CluSetDataLoader cluSetDataLoader = new CluSetDataLoader();
        cluSetDataLoader.setCluService(cluService);
        cluSetDataLoader.setContextInfo(contextInfo);
        cluSetDataLoader.load();
    }

    private void loadAcadRecordData() throws Exception {

        dataLoader.beforeTest();

        // setup the test data for student 1
        createStudentCourseRecord(KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID, dataLoader.getFallTermId(),
                "COURSE1", "kuali.result.value.grade.letter.plus.minus.a+", "kuali.result.value.grade.letter.plus.minus");
        createStudentCourseRecord(KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID, dataLoader.getFallTermId(),
                "COURSE2", "kuali.result.value.grade.letter.plus.minus.c", "kuali.result.value.grade.letter.plus.minus");
        createStudentCourseRecord(KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID, dataLoader.getFallTermId(),
                "COURSE4", "kuali.result.value.grade.letter.plus.minus.c", "kuali.result.value.grade.letter.plus.minus");

        // setup the test data for student 2
        createStudentCourseRecord(KRMSEnrollmentEligibilityDataLoader.STUDENT_TWO_ID, dataLoader.getFallTermId(),
                "COURSE2", "kuali.result.value.grade.letter.plus.minus.a", "kuali.result.value.grade.letter.plus.minus");
        createStudentCourseRecord(KRMSEnrollmentEligibilityDataLoader.STUDENT_TWO_ID, dataLoader.getFallTermId(),
                "COURSE3", "kuali.result.value.grade.letter.plus.minus.b", "kuali.result.value.grade.letter.plus.minus");
        createStudentCourseRecord(KRMSEnrollmentEligibilityDataLoader.STUDENT_TWO_ID, dataLoader.getFallTermId(),
                "COURSE4", "kuali.result.value.grade.letter.plus.minus.a", "kuali.result.value.grade.letter.plus.minus");
        createStudentCourseRecord(KRMSEnrollmentEligibilityDataLoader.STUDENT_TWO_ID, dataLoader.getFallTermId(),
                "COURSE5", "kuali.result.value.grade.letter.plus.minus.c", "kuali.result.value.grade.letter.plus.minus");

        // setup the test data for student 3
        createStudentCourseRecord(KRMSEnrollmentEligibilityDataLoader.STUDENT_THREE_ID, dataLoader.getSpringTermId(),
                "COURSE1", "kuali.result.value.grade.letter.plus.minus.a+", "kuali.result.value.grade.letter.plus.minus");
        createStudentCourseRecord(KRMSEnrollmentEligibilityDataLoader.STUDENT_THREE_ID, dataLoader.getSpringTermId(),
                "COURSE2", "kuali.result.value.grade.letter.plus.minus.a+", "kuali.result.value.grade.letter.plus.minus");
        createStudentCourseRecord(KRMSEnrollmentEligibilityDataLoader.STUDENT_THREE_ID, dataLoader.getSpringTermId(),
                "COURSE3", "kuali.result.value.grade.letter.plus.minus.a+", "kuali.result.value.grade.letter.plus.minus");

    }

    private void createStudentCourseRecord(String personId, String termId, String courseId, String gradeValue, String gradeScaleKey) throws Exception {
        CourseOffering courseOffering = dataLoader.getCourseOffering(courseId, termId);
        StudentCourseRecordInfo courseRecord = dataLoader.createStudentCourseRecord(personId, termId,
                courseOffering.getCourseCode(), courseOffering.getCourseOfferingTitle(), gradeValue, gradeScaleKey);
        courseRecord.setCourseOfferingId(courseOffering.getId());
        dataLoader.storeStudentCourseRecord(personId, termId, courseId, courseRecord);
    }

    private void loadRegistrationData() throws Exception {
        // setup the test data for student 1
        RegistrationRequestInfo regReqInfo = createRegistrationRequest(KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID, dataLoader.getFallTermId(), "COURSE3", "COURSE5");
        dataLoader.submitRegistrationRequest(regReqInfo.getId());
        // setup the test data for student 2
        createRegistrationRequest(KRMSEnrollmentEligibilityDataLoader.STUDENT_TWO_ID, dataLoader.getFallTermId(), "COURSE1", "COURSE6", "COURSE7", "COURSE8");
    }

    private RegistrationRequestInfo createRegistrationRequest(String studentId, String termId, String... courseIds) throws Exception {
        RegistrationRequestInfo request = dataLoader.createRegistrationRequest(studentId, termId);
        for(String courseId : courseIds){
            RegistrationGroupInfo regGroup = dataLoader.getRegistrationGroup(courseId, termId);
            request.getRegistrationRequestItems().add(dataLoader.createRegistrationItem(studentId, regGroup.getId()));
        }
        return dataLoader.persistRegistrationRequest(request);
    }

    private void loadProgramRecordData() throws Exception {
        // setup the test data for student 1
        insertStudentProgramRecord(KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID, "PROGRAM1");
        insertStudentProgramRecord(KRMSEnrollmentEligibilityDataLoader.STUDENT_TWO_ID, "PROGRAM1");
        insertStudentProgramRecord(KRMSEnrollmentEligibilityDataLoader.STUDENT_THREE_ID, "PROGRAM2");
    }

    private void insertStudentProgramRecord(String personId, String programId) throws Exception {
        CluInfo program = dataLoader.getProgram(programId);
        StudentProgramRecordInfo programRecord = dataLoader.createStudentProgramRecord(personId, program);
        dataLoader.storeStudentProgramRecord(personId, programRecord.getProgramId(), programRecord);
    }

    private void loadPopulationData() throws Exception {
        populationService.getMembersAsOfDate("SENIOR_ONLY_STUDENTS", new Date(), contextInfo);
    }

    private void loadOrgData(){
        OrgTestDataLoader orgDataLoader = new OrgTestDataLoader(organizationService);
        orgDataLoader.loadData();
    }

}

