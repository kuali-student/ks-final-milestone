package org.kuali.student.enrollment.class2.courseoffering.krms.termresolver;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.student.common.test.mock.data.AbstractMockServicesAwareDataLoader;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.core.constants.GesServiceConstants;
import org.kuali.student.core.ges.dto.ValueInfo;
import org.kuali.student.core.ges.service.GesService;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentProgramRecordInfo;
import org.kuali.student.enrollment.academicrecord.service.AcademicRecordService;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.infc.CourseOffering;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.enrollment.krms.termresolver.CluId2CluInfoTermResolver;
import org.kuali.student.enrollment.krms.termresolver.CluId2CluVersionIndIdTermResolver;
import org.kuali.student.lum.lrc.service.util.MockLrcTestDataLoader;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.common.krms.data.KRMSEnrollmentEligibilityDataLoader;
import org.kuali.student.r2.common.util.constants.AcademicRecordServiceConstants;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.class1.organization.service.impl.OrgTestDataLoader;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.kuali.student.r2.core.population.service.PopulationService;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.kuali.student.r2.lum.lu.service.impl.CluDataLoader;
import org.kuali.student.r2.lum.lu.service.impl.CluSetDataLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
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

    @Resource(name = "gesService")
    private GesService gesService;

    @Resource(name="gesServiceDataLoader")
    private AbstractMockServicesAwareDataLoader gesServiceDataLoader;

    @Resource(name="studentToCourseRecordsMap")
    private Map<String, List<StudentCourseRecordInfo>> studentToCourseRecordsMap;

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
                gesServiceDataLoader.beforeTest();
                notSetup = false;
            }

            resolvedPrereqs = getDefaultPrerequisites();
            parameters = getDefaultParameters();
    }

    private CompletedCourseTermResolver getCompletedCourseTermResolver(){
        CompletedCourseTermResolver termResolver = new CompletedCourseTermResolver();
        termResolver.setAcademicRecordService(academicRecordService);
        termResolver.setCluService(cluService);
        return termResolver;
    }

    @Test
    public void testCompletedCourseTermResolver() throws Exception {
        //Setup the term resolver
        CompletedCourseTermResolver termResolver = this.getCompletedCourseTermResolver();

        //Setup data
        resolvedPrereqs.put(RulesExecutionConstants.PERSON_ID_TERM.getName(), KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID);
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

    @Test
    public void testCompletedCoursesTermResolver() {
        //Setup the term resolver
        CompletedCoursesTermResolver termResolver = new CompletedCoursesTermResolver();
        termResolver.setCluService(cluService);
        termResolver.setAcademicRecordService(academicRecordService);

        //Setup data
        resolvedPrereqs.put(RulesExecutionConstants.PERSON_ID_TERM.getName(), KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID);
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_COURSE_CLUSET_KEY, "COURSE-SET2");

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSServiceConstants.TERM_RESOLVER_COMPLETEDCOURSES);

        //Evaluate term Resolver
        Boolean isAllCompleted = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(isAllCompleted);
        assertFalse(isAllCompleted);

        //Reset to student 2 and re-evaluate.
        resolvedPrereqs.put(RulesExecutionConstants.PERSON_ID_TERM.getName(), KRMSEnrollmentEligibilityDataLoader.STUDENT_TWO_ID);
        isAllCompleted = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(isAllCompleted);
        assertTrue(isAllCompleted);
    }

    @Test
    public void testNumberOfCompletedCoursesTermResolver() {
        //Setup the term resolver
        NumberOfCompletedCoursesTermResolver termResolver = new NumberOfCompletedCoursesTermResolver();
        termResolver.setCluService(cluService);
        termResolver.setAcademicRecordService(academicRecordService);

        //Setup data
        resolvedPrereqs.put(RulesExecutionConstants.PERSON_ID_TERM.getName(), KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID);
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_COURSE_CLUSET_KEY, "COURSE-SET3");

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSServiceConstants.TERM_RESOLVER_NUMBEROFCOMPLETEDCOURSES);

        //Evaluate term Resolver
        Integer numberOfCompletedCourses = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(numberOfCompletedCourses);
        assertEquals(new Integer(1), numberOfCompletedCourses);

        //Reset to student 2 and re-evaluate.
        resolvedPrereqs.put(RulesExecutionConstants.PERSON_ID_TERM.getName(), KRMSEnrollmentEligibilityDataLoader.STUDENT_TWO_ID);
        numberOfCompletedCourses = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(numberOfCompletedCourses);
        assertEquals(new Integer(2), numberOfCompletedCourses);
    }

    @Test
    public void testNumberOfCreditsFromCoursesTermResolver() {
        //Setup the term resolver
        CreditsEarnedFromCoursesTermResolver termResolver = new CreditsEarnedFromCoursesTermResolver();
        termResolver.setCluService(cluService);
        termResolver.setAcademicRecordService(academicRecordService);

        //Setup data
        resolvedPrereqs.put(RulesExecutionConstants.PERSON_ID_TERM.getName(), KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID);
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_COURSE_CLUSET_KEY, "COURSE-SET3");

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSServiceConstants.TERM_RESOLVER_NUMBEROFCREDITSFROMCOMPLETEDCOURSES);

        //Evaluate term Resolver
        Integer credits = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(credits);
        assertEquals(new Integer(3), credits);

        resolvedPrereqs.put(RulesExecutionConstants.PERSON_ID_TERM.getName(), KRMSEnrollmentEligibilityDataLoader.STUDENT_TWO_ID);
        credits = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(credits);
        assertEquals(new Integer(6), credits);
    }

    private EnrolledCourseTermResolver getEnrolledCourseTermResolver(){
        EnrolledCourseTermResolver termResolver = new EnrolledCourseTermResolver();
        termResolver.setCourseRegistrationService(courseRegistrationService);
        termResolver.setCourseOfferingService(courseOfferingService);
        termResolver.setCluService(cluService);
        return termResolver;
    }

    @Test
    public void testEnrolledCourseTermResolver() throws Exception {
        //Setup the term resolver
        EnrolledCourseTermResolver termResolver = this.getEnrolledCourseTermResolver();

        //Setup data for unsubmitted registration request.
        resolvedPrereqs.put(RulesExecutionConstants.PERSON_ID_TERM.getName(), KRMSEnrollmentEligibilityDataLoader.STUDENT_TWO_ID);
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
        resolvedPrereqs.put(RulesExecutionConstants.PERSON_ID_TERM.getName(), KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID);
        versionIndId = cluService.getClu("COURSE3", contextInfo).getVersion().getVersionIndId();
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_COURSE_CLU_KEY, versionIndId);
        isEnrolled = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(isEnrolled);
        assertTrue(isEnrolled);

        //Setup data for completed course, completed course should not be included, only concurrently enrolled courses.
        resolvedPrereqs.put(RulesExecutionConstants.PERSON_ID_TERM.getName(), KRMSEnrollmentEligibilityDataLoader.STUDENT_THREE_ID);
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
        termResolver.setCluService(cluService);
        termResolver.setCourseOfferingService(courseOfferingService);
        termResolver.setCourseRegistrationService(courseRegistrationService);
        termResolver.setAtpService(atpService);

        //Setup data
        resolvedPrereqs.put(RulesExecutionConstants.PERSON_ID_TERM.getName(), KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID);
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
        resolvedPrereqs.put(RulesExecutionConstants.PERSON_ID_TERM.getName(), KRMSEnrollmentEligibilityDataLoader.STUDENT_TWO_ID);
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
        termResolver.setCluService(cluService);
        termResolver.setCourseOfferingService(courseOfferingService);
        termResolver.setCourseRegistrationService(courseRegistrationService);
        termResolver.setAtpService(atpService);

        //Setup data
        resolvedPrereqs.put(RulesExecutionConstants.PERSON_ID_TERM.getName(), KRMSEnrollmentEligibilityDataLoader.STUDENT_TWO_ID);
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
        resolvedPrereqs.put(RulesExecutionConstants.PERSON_ID_TERM.getName(), KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID);
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
        resolvedPrereqs.put(RulesExecutionConstants.PERSON_ID_TERM.getName(), KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID);

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
        termResolver.setCluService(cluService);

        //Setup data
        resolvedPrereqs.put(RulesExecutionConstants.PERSON_ID_TERM.getName(), KRMSEnrollmentEligibilityDataLoader.STUDENT_TWO_ID);
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
        resolvedPrereqs.put(RulesExecutionConstants.PERSON_ID_TERM.getName(), KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID);
        //parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_CLU_KEY, "DTC101");

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSServiceConstants.TERM_RESOLVER_GPAFORDURATION);

        //Evaluate term Resolver
        termResolver.resolve(resolvedPrereqs, parameters);
        //Float gpa = termResolver.resolve(resolvedPrereqs, parameters);
        //assertNotNull(gpa);
        //assertEquals(new Float(3.9), gpa);
    }

    private CourseWithGradeTermResolver getCourseWithGradeTermResolver() {
        CourseWithGradeTermResolver termResolver = new CourseWithGradeTermResolver();
        termResolver.setCluService(cluService);
        termResolver.setAcademicRecordService(academicRecordService);
        termResolver.setLrcService(lrcService);
        return termResolver;
    }

    @Test
    public void testCourseWithGradeTermResolver() throws Exception {
        //Setup the term resolver
        CourseWithGradeTermResolver termResolver = getCourseWithGradeTermResolver();

        //Setup data
        resolvedPrereqs.put(RulesExecutionConstants.PERSON_ID_TERM.getName(), KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID);
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
        termResolver.setCluService(cluService);
        termResolver.setLrcService(lrcService);
        termResolver.setAcademicRecordService(academicRecordService);

        //Setup data
        resolvedPrereqs.put(RulesExecutionConstants.PERSON_ID_TERM.getName(), KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID);
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
        termResolver.setCluService(cluService);
        termResolver.setAcademicRecordService(academicRecordService);
        termResolver.setLrcService(lrcService);

        //Setup data
        resolvedPrereqs.put(RulesExecutionConstants.PERSON_ID_TERM.getName(), KRMSEnrollmentEligibilityDataLoader.STUDENT_TWO_ID);
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
        resolvedPrereqs.put(RulesExecutionConstants.PERSON_ID_TERM.getName(), KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID);

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSServiceConstants.TERM_RESOLVER_NUMBEROFCREDITSEARNED);

        //Evaluate term Resolver
        Integer credits = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(credits);
        assertEquals(new Integer(9), credits);

        resolvedPrereqs.put(RulesExecutionConstants.PERSON_ID_TERM.getName(), KRMSEnrollmentEligibilityDataLoader.STUDENT_TWO_ID);
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
        resolvedPrereqs.put(RulesExecutionConstants.PERSON_ID_TERM.getName(), KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID);
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
        resolvedPrereqs.put(RulesExecutionConstants.PERSON_ID_TERM.getName(), KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID);
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
        resolvedPrereqs.put(RulesExecutionConstants.PERSON_ID_TERM.getName(), KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID);
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
        resolvedPrereqs.put(RulesExecutionConstants.PERSON_ID_TERM.getName(), KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID);
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_ORGANIZATION_KEY, "1");
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_DURATION_KEY, "1");
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_DURATION_TYPE_KEY, "1");

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSServiceConstants.TERM_RESOLVER_ADMITTEDTOPROGRAMLIMITCOURSESINORGFORDURATION);

        //Evaluate term Resolver
        termResolver.resolve(resolvedPrereqs, parameters);
        //Integer result = termResolver.resolve(resolvedPrereqs, parameters);
        //assertNotNull(result);
        //assertEquals(new Integer(0), result);
    }

    @Test
    public void testAdmittedToProgramTermResolver() {
        //Setup the term resolver
        AdmittedProgramTermResolver termResolver = new AdmittedProgramTermResolver();
        termResolver.setAcademicRecordService(academicRecordService);

        //Setup data
        resolvedPrereqs.put(RulesExecutionConstants.PERSON_ID_TERM.getName(), KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID);
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
        resolvedPrereqs.put(RulesExecutionConstants.PERSON_ID_TERM.getName(), KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID);
        resolvedPrereqs.put(KSKRMSServiceConstants.TERM_PREREQUISITE_CLU_ID, "Program1");

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSServiceConstants.TERM_RESOLVER_ADMITTEDTOPROGRAMATCOURSECAMPUS);

        //Evaluate term Resolver
        termResolver.resolve(resolvedPrereqs, parameters);
        //Boolean isAdmitted = termResolver.resolve(resolvedPrereqs, parameters);
        //assertNotNull(isAdmitted);
        //assertTrue(isAdmitted);
    }

    @Test
    public void testAdmittedToProgramWithClassStandingTermResolver() {
        //Setup the term resolver
        AdmittedProgramWithClassStandingTermResolver termResolver = new AdmittedProgramWithClassStandingTermResolver();

        //Setup data
        resolvedPrereqs.put(RulesExecutionConstants.PERSON_ID_TERM.getName(), KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID);
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
        termResolver.setCluService(cluService);

        //Setup data
        resolvedPrereqs.put(RulesExecutionConstants.PERSON_ID_TERM.getName(), KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID);
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

    @Test
    public void testCompletedCourseAsOfTermTermResolver() throws Exception{
        //Setup the term resolver
        CompletedCourseAsOfTermTermResolver termResolver = new CompletedCourseAsOfTermTermResolver();
        termResolver.setAcademicRecordService(academicRecordService);
        termResolver.setAtpService(atpService);
        termResolver.setCluService(cluService);
        termResolver.setCourseOfferingService(courseOfferingService);

        //Setup data
        resolvedPrereqs.put(RulesExecutionConstants.PERSON_ID_TERM.getName(), KRMSEnrollmentEligibilityDataLoader.STUDENT_THREE_ID);
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
        termResolver.setCluService(cluService);
        termResolver.setCourseOfferingService(courseOfferingService);

        //Setup data
        resolvedPrereqs.put(RulesExecutionConstants.PERSON_ID_TERM.getName(), KRMSEnrollmentEligibilityDataLoader.STUDENT_THREE_ID);
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
        termResolver.setCluService(cluService);
        termResolver.setCourseOfferingService(courseOfferingService);

        //Setup data
        resolvedPrereqs.put(RulesExecutionConstants.PERSON_ID_TERM.getName(), KRMSEnrollmentEligibilityDataLoader.STUDENT_THREE_ID);
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
    public void testCluId2CluInfoTermResolver() throws Exception {
        //Setup the term resolver
        CluId2CluInfoTermResolver termResolver = new CluId2CluInfoTermResolver();
        termResolver.setCluService(cluService);

        String cluId = "COURSE1";

        //Setup data
        resolvedPrereqs.put(RulesExecutionConstants.CLU_ID_TERM.getName(), cluId);

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                RulesExecutionConstants.CLU_INFO_TERM.getName());

        //Evaluate term Resolver
        CluInfo resolvedCluInfo = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(resolvedCluInfo);
        assertEquals(resolvedCluInfo.getId(), cluId);
    }

    @Test
    public void testCluId2CluVersionIndIdTermResolver() throws Exception {
        //Setup the term resolver
        CluId2CluVersionIndIdTermResolver termResolver = new CluId2CluVersionIndIdTermResolver();
        termResolver.setCluService(cluService);

        String cluId = "COURSE1";

        //Setup data
        resolvedPrereqs.put(RulesExecutionConstants.CLU_ID_TERM.getName(), cluId);

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                RulesExecutionConstants.CLU_VERSION_IND_ID_TERM.getName());

        String versionIndId = cluService.getClu(cluId, contextInfo).getVersion().getVersionIndId();

        //Evaluate term Resolver
        String resolvedVersionIndId = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(versionIndId);
        assertEquals(versionIndId, resolvedVersionIndId);
    }

    @Test
    public void testCourseRecordForStudentTermResolver() throws Exception {
        //Setup the term resolver
        CourseRecordForStudentTermResolver termResolver = new CourseRecordForStudentTermResolver();
        termResolver.setAcademicRecordService(academicRecordService);

        String cluId = "COURSE1";
        String versionIndId = cluService.getClu(cluId, contextInfo).getVersion().getVersionIndId();

        //Setup data
        resolvedPrereqs.put(RulesExecutionConstants.ENTITY_ID_TERM.getName(), KRMSEnrollmentEligibilityDataLoader.STUDENT_THREE_ID);
        resolvedPrereqs.put(RulesExecutionConstants.CLU_VERSION_IND_ID_TERM.getName(), versionIndId);

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSServiceConstants.TERM_RESOLVER_COURSE_RECORD_FOR_STUDENT);

        //Evaluate term Resolver
        List<StudentCourseRecordInfo> records = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(records);
        //assertEquals(1, records.size());

        for (StudentCourseRecordInfo record : records) {
            assertEquals(record.getId(), cluId);
        }


        //Reevaluate under student 2 (no record for course)
        resolvedPrereqs.put(RulesExecutionConstants.PERSON_ID_TERM.getName(), KRMSEnrollmentEligibilityDataLoader.STUDENT_TWO_ID);
        records = termResolver.resolve(resolvedPrereqs, parameters);

        assertNotNull(records);
        assertTrue(records.isEmpty());
    }

    @Test
    public void testCourseCompletedAttemptsTermResolver() throws Exception {
        //Setup the term resolver
        CourseCompletedAttemptsTermResolver termResolver = new CourseCompletedAttemptsTermResolver();

        List<StudentCourseRecordInfo> list01=studentToCourseRecordsMap.get("KS-5213"); // R.JESSICAL
        List<StudentCourseRecordInfo> list02=studentToCourseRecordsMap.get("KS-2471"); // R.JOHANNAC

        //Setup data for first test (all courses complete)
        resolvedPrereqs.put(KSKRMSServiceConstants.TERM_RESOLVER_COURSE_RECORD_FOR_STUDENT, list01);

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSServiceConstants.TERM_RESOLVER_COURSE_COMPLETED_ATTEMPTS);

        //Evaluate Term Resolver for the first test
        Integer completedAttempts = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(completedAttempts);
        assertEquals(completedAttempts.intValue(), 2);

        //Setup data for second test (one course withdrawn)
        resolvedPrereqs.put(KSKRMSServiceConstants.TERM_RESOLVER_COURSE_RECORD_FOR_STUDENT, list02);

        //Evaluate term Resolver for the second test
        completedAttempts = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(completedAttempts);
        assertEquals(2, completedAttempts.intValue());
    }

    @Test
    public void testCourseRegisteredCountTermResolver() throws Exception {
        //Setup the term resolver
        CourseRegisteredCountTermResolver termResolver = new CourseRegisteredCountTermResolver();

        StudentCourseRecordInfo registeredCourse = new StudentCourseRecordInfo();
        registeredCourse.setStateKey(AcademicRecordServiceConstants.STUDENTCOURSERECORD_STATE_KEY_REGISTERED);

        List<StudentCourseRecordInfo> courseRecordList = new ArrayList<>();
        courseRecordList.addAll(studentToCourseRecordsMap.get("KS-5213")); // R.JESSICAL
        courseRecordList.add(registeredCourse);

        //Setup data for test
        resolvedPrereqs.put(KSKRMSServiceConstants.TERM_RESOLVER_COURSE_RECORD_FOR_STUDENT, courseRecordList);

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSServiceConstants.TERM_RESOLVER_COURSE_REGISTERED_COUNT);

        //Evaluate Term Resolver
        Integer registeredCount = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(registeredCount);
        assertEquals(registeredCount.intValue(), 1);
    }

    @Test
    public void testCourseTotalAttemptsTermResolver() throws Exception {
        //Setup the term resolver
        CourseTotalAttemptsTermResolver termResolver = new CourseTotalAttemptsTermResolver();

        StudentCourseRecordInfo registeredCourse = new StudentCourseRecordInfo();
        registeredCourse.setStateKey(AcademicRecordServiceConstants.STUDENTCOURSERECORD_STATE_KEY_REGISTERED);

        List<StudentCourseRecordInfo> courseRecordList = new ArrayList<>();
        courseRecordList.addAll(studentToCourseRecordsMap.get("KS-5213")); // R.JESSICAL
        courseRecordList.add(registeredCourse);

        //Setup data for test
        resolvedPrereqs.put(KSKRMSServiceConstants.TERM_RESOLVER_COURSE_RECORD_FOR_STUDENT, courseRecordList);

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                RulesExecutionConstants.TOTAL_COURSE_ATTEMPTS.getName());

        //Evaluate Term Resolver
        Integer registeredCount = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(registeredCount);
        assertEquals(registeredCount.intValue(), 3);
    }

    @Test
    public void testStudentWithGradeForCourseTermResolver() throws Exception {
        //Setup the term resolver
        StudentWithGradeForCourseTermResolver termResolver = new StudentWithGradeForCourseTermResolver();

        // Case 1: Student has Grade (True)
        List<StudentCourseRecordInfo> courseRecords = academicRecordService.getStudentCourseRecordsForCourse(
                KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID,
                "COURSE1",
                contextInfo);


        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_GRADE_KEY, "kuali.result.value.grade.letter.plus.minus.a+");
        resolvedPrereqs.put(KSKRMSServiceConstants.TERM_RESOLVER_COURSE_RECORD_FOR_STUDENT, courseRecords);

        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSServiceConstants.TERM_RESOLVER_STUDENT_WITH_GRADE_FOR_COURSE);

        Boolean hasGrade = termResolver.resolve(resolvedPrereqs, parameters);
        assertTrue(hasGrade);


        // Case 2: Student has taken class but doesn't have grade (False)
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_GRADE_KEY, "kuali.result.value.grade.letter.plus.minus.b");

        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSServiceConstants.TERM_RESOLVER_STUDENT_WITH_GRADE_FOR_COURSE);

        hasGrade = termResolver.resolve(resolvedPrereqs, parameters);
        assertFalse(hasGrade);


        // Case 3: Student has not taken class and thus doesn't have grade (False)
        courseRecords = academicRecordService.getStudentCourseRecordsForCourse(
                KRMSEnrollmentEligibilityDataLoader.STUDENT_TWO_ID,
                "COURSE1",
                contextInfo);

        resolvedPrereqs.put(KSKRMSServiceConstants.TERM_RESOLVER_COURSE_RECORD_FOR_STUDENT, courseRecords);

        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSServiceConstants.TERM_RESOLVER_STUDENT_WITH_GRADE_FOR_COURSE);

        hasGrade = termResolver.resolve(resolvedPrereqs, parameters);
        assertFalse(hasGrade);
    }

    @Test
    public void testGesValueTermResolver() throws Exception {
        //Setup the term resolver
        GesValueTermResolver termResolver = new GesValueTermResolver();
        termResolver.setGesService(gesService);

        //Setup prerequisites
        resolvedPrereqs.put(RulesExecutionConstants.CONTEXT_INFO_TERM.getName(), contextInfo);
        resolvedPrereqs.put(RulesExecutionConstants.PERSON_ID_TERM.getName(), "kuali.population.student.key.everyone100000077");
        resolvedPrereqs.put(RulesExecutionConstants.ATP_ID_TERM.getName(), dataLoader.getFallTermId());
        resolvedPrereqs.put(RulesExecutionConstants.AS_OF_DATE_TERM.getName(), KRMSEnrollmentEligibilityDataLoader.START_FALL_TERM_DATE);

        //Setup parameters
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_GES_PARAMETER_KEY, GesServiceConstants.PARAMETER_KEY_MAX_REPEATABLE);

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSServiceConstants.TERM_RESOLVER_GES_VALUE);

        //Evaluate term Resolver
        ValueInfo value = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(value);
        assertEquals(value.getDecimalValue().intValue(), 2);
    }

    @Test
    public void testGesIntegerValueTermResolver() throws Exception {
        //Setup the term resolver
        GesIntegerValueTermResolver termResolver = new GesIntegerValueTermResolver();
        termResolver.setGesService(gesService);

        //Setup prerequisites
        resolvedPrereqs.put(RulesExecutionConstants.CONTEXT_INFO_TERM.getName(), contextInfo);
        resolvedPrereqs.put(RulesExecutionConstants.PERSON_ID_TERM.getName(), "kuali.population.student.key.everyone100000077");
        resolvedPrereqs.put(RulesExecutionConstants.ATP_ID_TERM.getName(), dataLoader.getFallTermId());
        resolvedPrereqs.put(RulesExecutionConstants.AS_OF_DATE_TERM.getName(), KRMSEnrollmentEligibilityDataLoader.START_FALL_TERM_DATE);

        //Setup parameters
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_GES_PARAMETER_KEY, GesServiceConstants.PARAMETER_KEY_MAX_REPEATABLE);

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSServiceConstants.TERM_RESOLVER_GES_INTEGER_VALUE);

        //Evaluate term Resolver
        Integer value = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(value);
        assertEquals(value.intValue(), 2);
    }

    @Test
    public void testGesMaxRepeatabilityTermResolver() throws Exception {
        //Setup the term resolver
        GesMaxRepeatabilityTermResolver termResolver = new GesMaxRepeatabilityTermResolver();
        termResolver.setGesService(gesService);

        //Setup prerequisites
        resolvedPrereqs.put(RulesExecutionConstants.CONTEXT_INFO_TERM.getName(), contextInfo);
        resolvedPrereqs.put(RulesExecutionConstants.PERSON_ID_TERM.getName(), "kuali.population.student.key.everyone100000077");
        resolvedPrereqs.put(RulesExecutionConstants.ATP_ID_TERM.getName(), dataLoader.getFallTermId());
        resolvedPrereqs.put(RulesExecutionConstants.AS_OF_DATE_TERM.getName(), KRMSEnrollmentEligibilityDataLoader.START_FALL_TERM_DATE);
        resolvedPrereqs.put(RulesExecutionConstants.MAX_REPEATABILITY.getName(), 2);
        resolvedPrereqs.put(RulesExecutionConstants.TOTAL_COURSE_ATTEMPTS.getName(), 2);

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSServiceConstants.TERM_RESOLVER_GES_MAX_REPEATABILITY);

        String value;

        //Evaluate term Resolver for error
        value = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(value);
        assertEquals(value, GesMaxRepeatabilityTermResolver.MAX_REPEATABILITY_ERROR);

        //Evaluate term Resolver for warning
        resolvedPrereqs.put(RulesExecutionConstants.TOTAL_COURSE_ATTEMPTS.getName(), 1);
        value = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(value);
        assertEquals(value, GesMaxRepeatabilityTermResolver.MAX_REPEATABILITY_WARNING);

        //Evaluate term Resolver for success
        resolvedPrereqs.put(RulesExecutionConstants.TOTAL_COURSE_ATTEMPTS.getName(), 0);
        value = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(value);
        assertEquals(value, GesMaxRepeatabilityTermResolver.MAX_REPEATABILITY_SUCCESS);
    }

    @Test
    public void testPopulationTermResolver() {
        //Setup the term resolver
        PopulationTermResolver termResolver = new PopulationTermResolver();
        termResolver.setPopulationService(populationService);

        //Setup data
        resolvedPrereqs.put(RulesExecutionConstants.PERSON_ID_TERM.getName(), "SENIOR_ONLY_STUDENTS100000285");
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
        resolvedPrereqs.put(RulesExecutionConstants.PERSON_ID_TERM.getName(), KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID);
        resolvedPrereqs.put(KSKRMSServiceConstants.TERM_PREREQUISITE_CLU_ID, "ORG1");

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSServiceConstants.TERM_RESOLVER_INSTRUCTORPERMISSION);

        //Evaluate term Resolver
        termResolver.resolve(resolvedPrereqs, parameters);
        //Boolean hasPermission = termResolver.resolve(resolvedPrereqs, parameters);
        //assertNotNull(hasPermission);
        //assertTrue(hasPermission);
    }

    //@Test
    //public void testNumberOfEnrollmentsForCourseTermResolver() {
    //Setup the term resolver
    //    NumberOfEnrollmentsForCourseTermResolver termResolver = new NumberOfEnrollmentsForCourseTermResolver();
    //}
    @SuppressWarnings("unchecked")
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
        Map<String, Object> resolvedPrereqs = new HashMap<>();
        resolvedPrereqs.put(RulesExecutionConstants.CONTEXT_INFO_TERM.getName(), contextInfo);
        return resolvedPrereqs;
    }

    private Map<String, String> getDefaultParameters() {
        return new HashMap<>();
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
        courseRecord.setId(courseId);
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
        populationService.getMembersAsOfDate("kuali.population.student.key.everyone", new Date(), contextInfo);
    }

    private void loadOrgData(){
        OrgTestDataLoader orgDataLoader = new OrgTestDataLoader(organizationService);
        orgDataLoader.loadData();
    }

}

