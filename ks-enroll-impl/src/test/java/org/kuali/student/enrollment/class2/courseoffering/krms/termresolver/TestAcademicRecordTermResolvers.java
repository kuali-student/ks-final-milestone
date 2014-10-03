package org.kuali.student.enrollment.class2.courseoffering.krms.termresolver;

import edu.emory.mathcs.backport.java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.enrollment.academicrecord.dto.GPAInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.academicrecord.service.AcademicRecordService;
import org.kuali.student.lum.lrc.service.util.MockLrcTestDataLoader;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.common.krms.data.KRMSEnrollmentEligibilityDataLoader;
import org.kuali.student.r2.common.util.constants.AcademicRecordServiceConstants;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.kuali.student.r2.lum.lu.service.impl.CluDataLoader;
import org.kuali.student.r2.lum.lu.service.impl.CluSetDataLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:krms-test-with-mocks-context.xml"})
public class TestAcademicRecordTermResolvers extends AbstractTermResolverTestHelper {

    public ContextInfo contextInfo = null;
    Map<String, Object> resolvedPrereqs = null;
    Map<String, String> parameters = null;
    private static boolean notSetup = true;

    @Resource(name = "cluService")
    private CluService cluService;

    private CluService cluServiceMock;

    private AcademicRecordService academicRecordServiceMock;

    @Resource
    private KRMSEnrollmentEligibilityDataLoader dataLoader;

    @Resource(name = "lrcService")
    private LRCService lrcService;

    @Resource(name="studentToCourseRecordsMap")
    @SuppressWarnings("all")
    private Map<String, List<StudentCourseRecordInfo>> studentToCourseRecordsMap;

    private List<StudentCourseRecordInfo> courseRecords;

    @Before
    public void setUp() throws Exception {
            contextInfo = new ContextInfo();
            contextInfo.setLocale(new LocaleInfo());
            contextInfo.setPrincipalId("admin");
            dataLoader.setContextInfo(contextInfo);
            if(notSetup){
                loadCluData();
                new MockLrcTestDataLoader(this.lrcService).loadData();

                notSetup = false;
            }

            resolvedPrereqs = getDefaultPrerequisites();
            parameters = getDefaultParameters();

            // mock services with Mockito
            cluServiceMock = mock(CluService.class);
            academicRecordServiceMock = mock(AcademicRecordService.class);

            courseRecords = new ArrayList<>();
    }

    private CompletedCourseTermResolver getCompletedCourseTermResolver(){
        CompletedCourseTermResolver termResolver = new CompletedCourseTermResolver();
        termResolver.setAcademicRecordService(academicRecordServiceMock);
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
        courseRecords.add(new StudentCourseRecordInfo());

        //mock the academic record service method
        when(academicRecordServiceMock.getCompletedCourseRecordsForCourse(
                KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID, versionIndId, contextInfo)).
                thenReturn(courseRecords);

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
    public void testCompletedCoursesTermResolver() throws Exception {
        //Setup the term resolver
        CompletedCoursesTermResolver termResolver = new CompletedCoursesTermResolver();
        termResolver.setCluService(cluServiceMock);
        termResolver.setAcademicRecordService(academicRecordServiceMock);

        //Setup data
        resolvedPrereqs.put(RulesExecutionConstants.PERSON_ID_TERM.getName(), KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID);
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_COURSE_CLUSET_KEY, "COURSE-SET2");
        courseRecords.add(new StudentCourseRecordInfo());

        //Setup clu service mock
        @SuppressWarnings("unchecked")
        List<String> cluIds = Arrays.<String>asList(new String[]{"mock-clu-1", "mock-clu-2", "mock-clu-3"});
        when(cluServiceMock.getAllCluIdsInCluSet("COURSE-SET2", contextInfo)).thenReturn(cluIds);

        //Setup academic record service mock
        for (String cluId:cluIds) {
            when(academicRecordServiceMock.getCompletedCourseRecordsForCourse(
                    KRMSEnrollmentEligibilityDataLoader.STUDENT_TWO_ID, cluId, contextInfo)).
                    thenReturn(courseRecords);
        }

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
    public void testNumberOfCompletedCoursesTermResolver() throws  Exception {
        //Setup the term resolver
        NumberOfCompletedCoursesTermResolver termResolver = new NumberOfCompletedCoursesTermResolver();
        termResolver.setCluService(cluServiceMock);
        termResolver.setAcademicRecordService(academicRecordServiceMock);

        //Setup data
        resolvedPrereqs.put(RulesExecutionConstants.PERSON_ID_TERM.getName(), KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID);
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_COURSE_CLUSET_KEY, "COURSE-SET3");
        courseRecords.add(new StudentCourseRecordInfo());

        //Setup clu service mock
        @SuppressWarnings("unchecked")
        List<String> cluIds = Arrays.<String>asList(new String[]{"mock-clu-1", "mock-clu-2"});
        when(cluServiceMock.getAllCluIdsInCluSet("COURSE-SET3", contextInfo)).thenReturn(cluIds);

        //Setup academic record service mock
        when(academicRecordServiceMock.getCompletedCourseRecordsForCourse(
                KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID, "mock-clu-1", contextInfo)).
                thenReturn(courseRecords);
        for (String cluId:cluIds) {
            when(academicRecordServiceMock.getCompletedCourseRecordsForCourse(
                    KRMSEnrollmentEligibilityDataLoader.STUDENT_TWO_ID, cluId, contextInfo)).
                    thenReturn(courseRecords);
        }

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
    public void testNumberOfCreditsFromCoursesTermResolver() throws Exception {
        //Setup the term resolver
        CreditsEarnedFromCoursesTermResolver termResolver = new CreditsEarnedFromCoursesTermResolver();
        termResolver.setCluService(cluServiceMock);
        termResolver.setAcademicRecordService(academicRecordServiceMock);

        //Setup data
        resolvedPrereqs.put(RulesExecutionConstants.PERSON_ID_TERM.getName(), KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID);
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_COURSE_CLUSET_KEY, "COURSE-SET3");
        StudentCourseRecordInfo courseRecord = new StudentCourseRecordInfo();
        courseRecord.setCreditsEarned("3");
        courseRecords.add(courseRecord);

        //Setup clu service mock
        @SuppressWarnings("unchecked")
        List<String> cluIds = Arrays.<String>asList(new String[]{"mock-clu-1", "mock-clu-2"});
        when(cluServiceMock.getAllCluIdsInCluSet("COURSE-SET3", contextInfo)).thenReturn(cluIds);

        //Setup academic record service mock
        when(academicRecordServiceMock.getCompletedCourseRecordsForCourse(
                KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID, "mock-clu-1", contextInfo)).
                thenReturn(courseRecords);
        for (String cluId:cluIds) {
            when(academicRecordServiceMock.getCompletedCourseRecordsForCourse(
                    KRMSEnrollmentEligibilityDataLoader.STUDENT_TWO_ID, cluId, contextInfo)).
                    thenReturn(courseRecords);
        }

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

    @Test
    public void testGPAForCoursesTermResolver() throws Exception {
        //Setup the term resolver
        GPAForCoursesTermResolver termResolver = new GPAForCoursesTermResolver();
        termResolver.setAcademicRecordService(academicRecordServiceMock);
        termResolver.setCluService(cluServiceMock);

        //Setup data
        resolvedPrereqs.put(RulesExecutionConstants.PERSON_ID_TERM.getName(), KRMSEnrollmentEligibilityDataLoader.STUDENT_TWO_ID);
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_COURSE_CLUSET_KEY, "COURSE-SET3");
        StudentCourseRecordInfo courseRecord = new StudentCourseRecordInfo();
        courseRecord.setCreditsEarned("3");
        courseRecords.add(courseRecord);
        GPAInfo gpaInfo = new GPAInfo();
        gpaInfo.setValue("3.0");

        //Setup clu service mock
        @SuppressWarnings("unchecked")
        List<String> cluIds = Arrays.<String>asList(new String[]{"mock-clu-1"});
        when(cluServiceMock.getAllCluIdsInCluSet("COURSE-SET3", contextInfo)).thenReturn(cluIds);

        //Setup academic record service mock
        for (String cluId:cluIds) {
            when(academicRecordServiceMock.getCompletedCourseRecordsForCourse(
                    KRMSEnrollmentEligibilityDataLoader.STUDENT_TWO_ID, cluId, contextInfo)).
                    thenReturn(courseRecords);
        }
        when(academicRecordServiceMock.calculateGPA(courseRecords,
                AcademicRecordServiceConstants.ACADEMIC_RECORD_CALCULATION_GPA_TYPE_KEY, contextInfo)).thenReturn(gpaInfo);

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSServiceConstants.TERM_RESOLVER_GPAFORCOURSES);

        //Evaluate term Resolver
        Float gpa = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(gpa);
        assertEquals(new Float(3.0), gpa);
    }

    private CourseWithGradeTermResolver getCourseWithGradeTermResolver() {
        CourseWithGradeTermResolver termResolver = new CourseWithGradeTermResolver();
        termResolver.setCluService(cluService);
        termResolver.setAcademicRecordService(academicRecordServiceMock);
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
        StudentCourseRecordInfo courseRecord = new StudentCourseRecordInfo();
        courseRecord.setAssignedGradeValue("kuali.result.value.grade.letter.plus.minus.b");
        courseRecord.setAssignedGradeScaleKey("kuali.result.value.grade.letter.plus.minus");
        courseRecords.add(courseRecord);

        //Setup academic record service mock
        when(academicRecordServiceMock.getCompletedCourseRecordsForCourse(
                KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID, versionIndId, contextInfo)).
                thenReturn(courseRecords);

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
    public void testCoursesWithGradeTermResolver() throws Exception {
        //Setup the term resolver
        CoursesWithGradeTermResolver termResolver = new CoursesWithGradeTermResolver();
        termResolver.setCluService(cluServiceMock);
        termResolver.setLrcService(lrcService);
        termResolver.setAcademicRecordService(academicRecordServiceMock);

        //Setup data
        resolvedPrereqs.put(RulesExecutionConstants.PERSON_ID_TERM.getName(), KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID);
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_COURSE_CLUSET_KEY, "COURSE-SET1");
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_GRADE_KEY, "kuali.result.value.grade.letter.plus.minus.b");
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_GRADE_TYPE_KEY, "kuali.result.value.grade.letter.plus.minus");
        StudentCourseRecordInfo courseRecord = new StudentCourseRecordInfo();
        courseRecord.setAssignedGradeValue("kuali.result.value.grade.letter.plus.minus.b");
        courseRecord.setAssignedGradeScaleKey("kuali.result.value.grade.letter.plus.minus");
        courseRecords.add(courseRecord);

        //Setup clu service mock
        @SuppressWarnings("unchecked")
        List<String> cluIds = Arrays.<String>asList(new String[]{"mock-clu-1", "mock-clu-2", "mock-clu-3"});
        when(cluServiceMock.getAllCluIdsInCluSet("COURSE-SET1", contextInfo)).thenReturn(cluIds);

        //Setup academic record service mock
        for (String cluId:cluIds) {
            when(academicRecordServiceMock.getCompletedCourseRecordsForCourse(
                    KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID, cluId, contextInfo)).
                    thenReturn(courseRecords);
        }

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
    public void testNumberOfCoursesWithGradeTermResolver() throws Exception {
        //Setup the term resolver
        NumberOfCoursesWithGradeTermResolver termResolver = new NumberOfCoursesWithGradeTermResolver();
        termResolver.setCluService(cluServiceMock);
        termResolver.setAcademicRecordService(academicRecordServiceMock);
        termResolver.setLrcService(lrcService);

        //Setup data
        resolvedPrereqs.put(RulesExecutionConstants.PERSON_ID_TERM.getName(), KRMSEnrollmentEligibilityDataLoader.STUDENT_TWO_ID);
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_COURSE_CLUSET_KEY, "COURSE-SET2");
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_GRADE_KEY, "kuali.result.value.grade.letter.plus.minus.b");
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_GRADE_TYPE_KEY, "kuali.result.value.grade.letter.plus.minus");
        StudentCourseRecordInfo courseRecord = new StudentCourseRecordInfo();
        courseRecord.setAssignedGradeValue("kuali.result.value.grade.letter.plus.minus.b");
        courseRecord.setAssignedGradeScaleKey("kuali.result.value.grade.letter.plus.minus");
        courseRecords.add(courseRecord);

        //Setup clu service mock
        @SuppressWarnings("unchecked")
        List<String> courseSet2 = Arrays.<String>asList(new String[]{"mock-clu-1", "mock-clu-2"});
        @SuppressWarnings("unchecked")
        List<String> courseSet3 = Arrays.<String>asList(new String[]{"mock-clu-1"});
        when(cluServiceMock.getAllCluIdsInCluSet("COURSE-SET2", contextInfo)).thenReturn(courseSet2);
        when(cluServiceMock.getAllCluIdsInCluSet("COURSE-SET3", contextInfo)).thenReturn(courseSet3);

        //Setup academic record service mock
        for (String cluId:courseSet2) {
            when(academicRecordServiceMock.getCompletedCourseRecordsForCourse(
                    KRMSEnrollmentEligibilityDataLoader.STUDENT_TWO_ID, cluId, contextInfo)).
                    thenReturn(courseRecords);
        }

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

}

