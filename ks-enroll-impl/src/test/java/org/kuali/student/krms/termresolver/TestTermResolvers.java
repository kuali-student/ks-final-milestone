package org.kuali.student.krms.termresolver;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.rice.krms.impl.repository.ContextBoService;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.enrollment.academicrecord.dto.GPAInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.academicrecord.service.AcademicRecordService;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.krms.service.impl.KSTermResolverTypeService;
import org.kuali.student.krms.util.KSKRMSExecutionConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.core.class1.organization.service.impl.OrgTestDataLoader;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:krms-test-with-mocks-context.xml"})
//TODO KSENROLL-3833
@Ignore
public class TestTermResolvers {

    public ContextInfo contextInfo = null;
    Map<String, Object> resolvedPrereqs = null;
    Map<String, String> parameters = null;
    private String studentID = "12020303";
    private String termID = "1001";
    private String calcTypeID = "mockTypeKey3";

    @Resource(name = "orgServiceImpl")
    private OrganizationService organizationService;

    @Resource(name = "acadRecordService")
    private AcademicRecordService academicRecordService;

    @Resource(name = "courseRegistrationService")
    private CourseRegistrationService courseRegistrationService;

    @Resource(name = "contextBoService")
    public ContextBoService contextRepository;

    @Resource(name = "ksKRMSTermResolverTypeService")
    public KSTermResolverTypeService ksKRMSTermResolverTypeService;

    @Before
    public void setUp() {
        contextInfo = new ContextInfo();
        contextInfo.setLocale(new LocaleInfo());
        contextInfo.setPrincipalId("admin");

        resolvedPrereqs = getDefaultPrerequisites();
        parameters = getDefaultParameters();
    }

    @Test
    public void testAdminOrgNumberTermResolver() {
        OrgTestDataLoader orgDataLoader = new OrgTestDataLoader(organizationService);
        orgDataLoader.loadData();

        //Setup the term resolver
        AdminOrgNumberTermResolver termResolver = new AdminOrgNumberTermResolver();
        termResolver.setOrganizationService(organizationService);

        //Create prerequisites
        //Map<String, Object> resolvedPrereqs = getDefaultPrerequisites();

        //Create parameters
        //Map<String, String> parameters = new HashMap<String, String>();
        parameters.put(KSKRMSExecutionConstants.ORG_TYPE_KEY_TERM_PROPERTY, "kuali.org.Office");

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSExecutionConstants.ADMIN_ORG_NUMBER_TERM_NAME);

        //Evaluate term Resolver
        List<String> orgRecords = termResolver.resolve(resolvedPrereqs, parameters);

        assertNotNull(orgRecords);
        assertEquals(2, orgRecords.size());
        assertTrue(orgRecords.contains("3"));
        assertTrue(orgRecords.contains("6"));

    }

    @Test
    public void testCompletedCourseCodeTermResolver(){
        //Setup the term resolver
        CompletedCourseCodeTermResolver termResolver = new CompletedCourseCodeTermResolver();
        termResolver.setAcademicRecordService(academicRecordService);

        //Create parameters
        parameters.put(KSKRMSExecutionConstants.PERSON_ID_TERM_PROPERTY, studentID);

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSExecutionConstants.COMPLETED_COURSE_CODE_TERM_NAME);

        //Evaluate term Resolver
        List<StudentCourseRecordInfo> acadRecords = termResolver.resolve(resolvedPrereqs, parameters);

        assertNotNull(acadRecords);
        assertEquals(2,acadRecords.size());
        assertTrue(acadRecords.get(0).getCourseCode().contains("DTC101"));
        assertTrue(acadRecords.get(1).getCourseCode().contains("DTC102"));
    }

    @Test
    public void testCompletedCourseNumberTermResolver(){
        //Setup the term resolver
        CompletedCourseNumberTermResolver termResolver = new CompletedCourseNumberTermResolver();
        termResolver.setAcademicRecordService(academicRecordService);

        //Create parameters
        parameters.put(KSKRMSExecutionConstants.PERSON_ID_TERM_PROPERTY, studentID);

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSExecutionConstants.COMPLETED_COURSE_NUMBER_TERM_NAME);

        //Evaluate term Resolver
        List<StudentCourseRecordInfo> acadRecords = termResolver.resolve(resolvedPrereqs, parameters);

        assertNotNull(acadRecords);
        assertEquals(2,acadRecords.size());
        assertTrue(acadRecords.get(0).getCourseCode().contains("DTC101"));
        assertTrue(acadRecords.get(1).getCourseCode().contains("DTC102"));
    }

    @Test
    public void testCompletedCourseSetTermResolver(){
        //Setup the term resolver
        CompletedCourseSetTermResolver termResolver = new CompletedCourseSetTermResolver();
        termResolver.setAcademicRecordService(academicRecordService);

        //Create parameters
        parameters.put(KSKRMSExecutionConstants.PERSON_ID_TERM_PROPERTY, studentID);

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSExecutionConstants.COMPLETED_COURSE_SET_TERM_NAME);

        //Evaluate term Resolver
        List<StudentCourseRecordInfo> acadRecords = termResolver.resolve(resolvedPrereqs, parameters);

        assertNotNull(acadRecords);
        assertEquals(2,acadRecords.size());
        assertTrue(acadRecords.get(0).getCourseCode().contains("DTC101"));
        assertTrue(acadRecords.get(1).getCourseCode().contains("DTC102"));
    }

    @Test
    public void testCompletedCoursesTermResolver(){
        //Setup the term resolver
        CompletedCoursesTermResolver termResolver = new CompletedCoursesTermResolver();
        termResolver.setAcademicRecordService(academicRecordService);

        //Create parameters
        parameters.put(KSKRMSExecutionConstants.PERSON_ID_TERM_PROPERTY, studentID);

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSExecutionConstants.COMPLETED_COURSES_TERM_NAME);

        //Evaluate term Resolver
        List<StudentCourseRecordInfo> acadRecords = termResolver.resolve(resolvedPrereqs, parameters);

        assertNotNull(acadRecords);
        assertEquals(2,acadRecords.size());
        assertTrue(acadRecords.get(0).getCourseCode().contains("DTC101"));
        assertTrue(acadRecords.get(1).getCourseCode().contains("DTC102"));
    }

    @Test
    public void testCompletedCourseTermResolver(){
        //Setup the term resolver
        CompletedCourseTermResolver termResolver = new CompletedCourseTermResolver();
        termResolver.setAcademicRecordService(academicRecordService);

        //Create parameters
        parameters.put(KSKRMSExecutionConstants.PERSON_ID_TERM_PROPERTY, studentID);
        parameters.put(KSKRMSExecutionConstants.COURSE_CODE_TERM_PROPERTY, "DTC101");

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSExecutionConstants.COMPLETED_COURSE_TERM_NAME);

        //Evaluate term Resolver
        List<StudentCourseRecordInfo> acadRecords = termResolver.resolve(resolvedPrereqs, parameters);

        assertNotNull(acadRecords);
        assertEquals(1, acadRecords.size());
        assertTrue(acadRecords.get(0).getCourseCode().contains("DTC101"));
    }

    @Test
    public void testCompletedEffectiveDateFromTermResolver(){
        //Setup the term resolver
        CompletedEffectiveDateFromTermResolver termResolver = new CompletedEffectiveDateFromTermResolver();
        termResolver.setAcademicRecordService(academicRecordService);

        //Create parameters
        parameters.put(KSKRMSExecutionConstants.PERSON_ID_TERM_PROPERTY, studentID);

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSExecutionConstants.COMPLETED_EFFECTIVE_DATE_FROM_TERM_NAME);

        //Evaluate term Resolver
        List<StudentCourseRecordInfo> acadRecords = termResolver.resolve(resolvedPrereqs, parameters);

        assertNotNull(acadRecords);
        assertEquals(2,acadRecords.size());
        assertTrue(acadRecords.get(0).getCourseCode().contains("DTC101"));
        assertTrue(acadRecords.get(1).getCourseCode().contains("DTC102"));
    }

    @Test
    public void testCompletedEffectiveDateToTermResolver(){
        //Setup the term resolver
        CompletedEffectiveDateToTermResolver termResolver = new CompletedEffectiveDateToTermResolver();
        termResolver.setAcademicRecordService(academicRecordService);

        //Create parameters
        parameters.put(KSKRMSExecutionConstants.PERSON_ID_TERM_PROPERTY, studentID);

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSExecutionConstants.COMPLETED_EFFECTIVE_DATE_TO_TERM_NAME);

        //Evaluate term Resolver
        List<StudentCourseRecordInfo> acadRecords = termResolver.resolve(resolvedPrereqs, parameters);

        assertNotNull(acadRecords);
        assertEquals(2,acadRecords.size());
        assertTrue(acadRecords.get(0).getCourseCode().contains("DTC101"));
        assertTrue(acadRecords.get(1).getCourseCode().contains("DTC102"));
    }

    @Test
    public void testCompletedLearningObjectivesTermResolver(){
        //Setup the term resolver
        CompletedLearningObjectivesTermResolver termResolver = new CompletedLearningObjectivesTermResolver();
        termResolver.setAcademicRecordService(academicRecordService);

        //Create parameters
        parameters.put(KSKRMSExecutionConstants.PERSON_ID_TERM_PROPERTY, studentID);

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSExecutionConstants.COMPLETED_LEARNING_OBJECTIVES_TERM_NAME);

        //Evaluate term Resolver
        List<StudentCourseRecordInfo> acadRecords = termResolver.resolve(resolvedPrereqs, parameters);

        assertNotNull(acadRecords);
        assertEquals(2,acadRecords.size());
        assertTrue(acadRecords.get(0).getCourseCode().contains("DTC101"));
        assertTrue(acadRecords.get(1).getCourseCode().contains("DTC102"));
    }

    @Test
    public void testCompletedCourseNumberRangeTermResolver(){
        //Setup the term resolver
        CourseNumberRangeTermResolver termResolver = new CourseNumberRangeTermResolver();
        termResolver.setAcademicRecordService(academicRecordService);

        //Create parameters
        parameters.put(KSKRMSExecutionConstants.PERSON_ID_TERM_PROPERTY, studentID);

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSExecutionConstants.COURSE_NUMBER_RANGE_TERM_NAME);

        //Evaluate term Resolver
        List<StudentCourseRecordInfo> acadRecords = termResolver.resolve(resolvedPrereqs, parameters);

        assertNotNull(acadRecords);
        assertEquals(2,acadRecords.size());
        assertTrue(acadRecords.get(0).getCourseCode().contains("DTC101"));
        assertTrue(acadRecords.get(1).getCourseCode().contains("DTC102"));
    }

    @Ignore
    public void testCourseSetTermResolver(){
        //Setup the term resolver
        CourseSetTermResolver termResolver = new CourseSetTermResolver();
        termResolver.setAcademicRecordService(academicRecordService);

        //Create parameters
        //TODO change values being sent
        parameters.put(RulesExecutionConstants.STUDENT_COMPLETED_COURSE_IDS_TERM_NAME, "kuali.org.Office");

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSExecutionConstants.COURSE_SET_TERM_NAME);

        //Evaluate term Resolver
        List<StudentCourseRecordInfo> acadRecords = termResolver.resolve(resolvedPrereqs, parameters);

        assertNotNull(acadRecords);
    }

    @Test
    public void testDeptNumberTermResolver(){
        OrgTestDataLoader orgDataLoader = new OrgTestDataLoader(organizationService);
        orgDataLoader.loadData();
        //Setup the term resolver
        DeptNumberTermResolver termResolver = new DeptNumberTermResolver();
        termResolver.setOrganizationService(organizationService);

        //Create parameters
        parameters.put(KSKRMSExecutionConstants.ORG_ID_TERM_PROPERTY, "1");

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSExecutionConstants.DEPT_NUMBER_TERM_NAME);

        //Evaluate term Resolver
        OrgInfo orgRecords = termResolver.resolve(resolvedPrereqs, parameters);

        assertNotNull(orgRecords);
        assertTrue(orgRecords.getShortName().equals("KUSystem"));
        assertTrue(orgRecords.getTypeKey().equals("kuali.org.CorporateEntity"));
    }

    @Ignore
    public void testEffectiveDateFromTermResolver(){
        //Setup the term resolver
        EffectiveDateFromTermResolver termResolver = new EffectiveDateFromTermResolver();
        termResolver.setAcademicRecordService(academicRecordService);

        //Create parameters
        //TODO change values being sent
        parameters.put(RulesExecutionConstants.STUDENT_COMPLETED_COURSE_IDS_TERM_NAME, "kuali.org.Office");

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSExecutionConstants.EFFECTIVE_DATE_FROM_TERM_NAME);

        //Evaluate term Resolver
        List<StudentCourseRecordInfo> acadRecords = termResolver.resolve(resolvedPrereqs, parameters);

        assertNotNull(acadRecords);
    }

    @Ignore
    public void testEffectiveDateToTermResolver(){
        //Setup the term resolver
        EffectiveDateToTermResolver termResolver = new EffectiveDateToTermResolver();
        termResolver.setAcademicRecordService(academicRecordService);

        //Create parameters
        //TODO change values being sent
        parameters.put(RulesExecutionConstants.STUDENT_COMPLETED_COURSE_IDS_TERM_NAME, "kuali.org.Office");

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSExecutionConstants.EFFECTIVE_DATE_TO_TERM_NAME);

        //Evaluate term Resolver
        List<StudentCourseRecordInfo> acadRecords = termResolver.resolve(resolvedPrereqs, parameters);

        assertNotNull(acadRecords);
    }
    //TODO KSENROLL-3833
    @Ignore
     public void testEnrolledCourseByTermTermResolver(){
        //Setup the term resolver
        EnrolledCourseByTermTermResolver termResolver = new EnrolledCourseByTermTermResolver();
        termResolver.setCourseRegistrationService(courseRegistrationService);

        //Add prerequisites
        resolvedPrereqs.put(KSKRMSExecutionConstants.STUDENT_ID_TERM_NAME, studentID);

        //Create parameters
        parameters.put(KSKRMSExecutionConstants.TERM_ID_TERM_PROPERTY, termID);

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSExecutionConstants.ENROLLED_COURSE_BY_TERM_TERM_NAME);

        //Evaluate term Resolver
        List<CourseRegistrationInfo> courseRegistrationRecords = termResolver.resolve(resolvedPrereqs, parameters);

        assertNotNull(courseRegistrationRecords);
    }
    //TODO KSENROLL-3833
    @Ignore
    public void testEnrolledCourseCodeTermResolver(){
        //Setup the term resolver
        EnrolledCourseCodeTermResolver termResolver = new EnrolledCourseCodeTermResolver();
        termResolver.setCourseRegistrationService(courseRegistrationService);

        //Add prerequisites
        resolvedPrereqs.put(KSKRMSExecutionConstants.STUDENT_ID_TERM_NAME, studentID);

        //Create parameters
        parameters.put(KSKRMSExecutionConstants.TERM_ID_TERM_PROPERTY, termID);

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSExecutionConstants.ENROLLED_COURSE_CODE_TERM_NAME);

        //Evaluate term Resolver
        List<CourseRegistrationInfo> courseRegistrationRecords = termResolver.resolve(resolvedPrereqs, parameters);

        assertNotNull(courseRegistrationRecords);
    }
    //TODO KSENROLL-3833
    @Ignore
    public void testEnrolledCourseNumberTermResolver(){
        //Setup the term resolver
        EnrolledCourseNumberTermResolver termResolver = new EnrolledCourseNumberTermResolver();
        termResolver.setCourseRegistrationService(courseRegistrationService);

        //Add prerequisites
        resolvedPrereqs.put(KSKRMSExecutionConstants.STUDENT_ID_TERM_NAME, studentID);

        //Create parameters
        parameters.put(KSKRMSExecutionConstants.TERM_ID_TERM_PROPERTY, termID);

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSExecutionConstants.ENROLLED_COURSE_NUMBER_TERM_NAME);

        //Evaluate term Resolver
        List<CourseRegistrationInfo> courseRegistrationRecords = termResolver.resolve(resolvedPrereqs, parameters);

        assertNotNull(courseRegistrationRecords);
    }
    //TODO KSENROLL-3833
    @Ignore
    public void testEnrolledCoursesByTermTermResolver(){
        //Setup the term resolver
        EnrolledCoursesByTermTermResolver termResolver = new EnrolledCoursesByTermTermResolver();
        termResolver.setCourseRegistrationService(courseRegistrationService);

        //Add prerequisites
        resolvedPrereqs.put(KSKRMSExecutionConstants.STUDENT_ID_TERM_NAME, studentID);

        //Create parameters
        parameters.put(KSKRMSExecutionConstants.TERM_ID_TERM_PROPERTY, termID);

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSExecutionConstants.ENROLLED_COURSES_BY_TERM_TERM_NAME);

        //Evaluate term Resolver
        List<CourseRegistrationInfo> courseRegistrationRecords = termResolver.resolve(resolvedPrereqs, parameters);

        assertNotNull(courseRegistrationRecords);
    }
    //TODO KSENROLL-3833
    @Ignore
    public void testEnrolledCourseSetTermResolver(){
        //Setup the term resolver
        EnrolledCourseSetTermResolver termResolver = new EnrolledCourseSetTermResolver();
        termResolver.setCourseRegistrationService(courseRegistrationService);

        //Add prerequisites
        resolvedPrereqs.put(KSKRMSExecutionConstants.STUDENT_ID_TERM_NAME, studentID);

        //Create parameters
        parameters.put(KSKRMSExecutionConstants.TERM_ID_TERM_PROPERTY, termID);

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSExecutionConstants.ENROLLED_COURSE_SET_TERM_NAME);

        //Evaluate term Resolver
        List<CourseRegistrationInfo> courseRegistrationRecords = termResolver.resolve(resolvedPrereqs, parameters);

        assertNotNull(courseRegistrationRecords);
    }
    //TODO KSENROLL-3833
    @Ignore
    public void testEnrolledCoursesTermResolver(){
        //Setup the term resolver
        EnrolledCoursesTermResolver termResolver = new EnrolledCoursesTermResolver();
        termResolver.setCourseRegistrationService(courseRegistrationService);

        //Add prerequisites
        resolvedPrereqs.put(KSKRMSExecutionConstants.STUDENT_ID_TERM_NAME, studentID);

        //Create parameters
        parameters.put(KSKRMSExecutionConstants.TERM_ID_TERM_PROPERTY, termID);

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSExecutionConstants.ENROLLED_COURSES_TERM_NAME);

        //Evaluate term Resolver
        List<CourseRegistrationInfo> courseRegistrationRecords = termResolver.resolve(resolvedPrereqs, parameters);

        assertNotNull(courseRegistrationRecords);
    }
    //TODO KSENROLL-3833
    @Ignore
    public void testEnrolledCourseTermResolver(){
        //Setup the term resolver
        EnrolledCourseTermResolver termResolver = new EnrolledCourseTermResolver();
        termResolver.setCourseRegistrationService(courseRegistrationService);

        //Add prerequisites
        resolvedPrereqs.put(KSKRMSExecutionConstants.STUDENT_ID_TERM_NAME, studentID);

        //Create parameters
        parameters.put(KSKRMSExecutionConstants.TERM_ID_TERM_PROPERTY, termID);

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSExecutionConstants.ENROLLED_COURSE_TERM_NAME);

        //Evaluate term Resolver
        List<CourseRegistrationInfo> courseRegistrationRecords = termResolver.resolve(resolvedPrereqs, parameters);

        assertNotNull(courseRegistrationRecords);
    }
    //TODO KSENROLL-3833
    @Ignore
    public void testEnrolledEffectiveDateFromTermResolver(){
        //Setup the term resolver
        EnrolledEffectiveDateFromTermResolver termResolver = new EnrolledEffectiveDateFromTermResolver();
        termResolver.setCourseRegistrationService(courseRegistrationService);

        //Add prerequisites
        resolvedPrereqs.put(KSKRMSExecutionConstants.STUDENT_ID_TERM_NAME, studentID);

        //Create parameters
        parameters.put(KSKRMSExecutionConstants.TERM_ID_TERM_PROPERTY, termID);

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSExecutionConstants.ENROLLED_EFFECTIVE_DATE_FROM_TERM_NAME);

        //Evaluate term Resolver
        List<CourseRegistrationInfo> courseRegistrationRecords = termResolver.resolve(resolvedPrereqs, parameters);

        assertNotNull(courseRegistrationRecords);
    }
    //TODO KSENROLL-3833
    @Ignore
    public void testEnrolledEffectiveDateToTermResolver(){
        //Setup the term resolver
        EnrolledEffectiveDateToTermResolver termResolver = new EnrolledEffectiveDateToTermResolver();
        termResolver.setCourseRegistrationService(courseRegistrationService);

        //Add prerequisites
        resolvedPrereqs.put(KSKRMSExecutionConstants.STUDENT_ID_TERM_NAME, studentID);

        //Create parameters
        parameters.put(KSKRMSExecutionConstants.TERM_ID_TERM_PROPERTY, termID);

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSExecutionConstants.ENROLLED_EFFECTIVE_DATE_TO_TERM_NAME);

        //Evaluate term Resolver
        List<CourseRegistrationInfo> courseRegistrationRecords = termResolver.resolve(resolvedPrereqs, parameters);

        assertNotNull(courseRegistrationRecords);
    }
    //TODO KSENROLL-3833
    @Ignore
    public void testEnrolledLearningObjectivesTermResolver(){
        //Setup the term resolver
        EnrolledLearningObjectivesTermResolver termResolver = new EnrolledLearningObjectivesTermResolver();
        termResolver.setCourseRegistrationService(courseRegistrationService);

        //ADd prerequisite
        resolvedPrereqs.put(KSKRMSExecutionConstants.STUDENT_ID_TERM_NAME, studentID);

        //Add prerequisites
        resolvedPrereqs.put(KSKRMSExecutionConstants.STUDENT_ID_TERM_NAME, studentID);

        //Create parameters
        parameters.put(KSKRMSExecutionConstants.TERM_ID_TERM_PROPERTY, termID);

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSExecutionConstants.ENROLLED_LEARNING_OBJECTIVES_TERM_NAME);

        //Evaluate term Resolver
        List<CourseRegistrationInfo> courseRegistrationRecords = termResolver.resolve(resolvedPrereqs, parameters);

        assertNotNull(courseRegistrationRecords);
    }

    @Ignore
    public void testFreeTextTermResolver(){
        //Setup the term resolver
        FreeTextTermResolver termResolver = new FreeTextTermResolver();

        //Create parameters
        //TODO change values being sent
        parameters.put(RulesExecutionConstants.STUDENT_COMPLETED_COURSE_IDS_TERM_NAME, "kuali.org.Office");
        parameters.put(KSKRMSExecutionConstants.CALC_TYPE_KEY_TERM_PROPERTY, "222");

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSExecutionConstants.FREE_TEXT_TERM_NAME);

        //Evaluate term Resolver
        String result =  termResolver.resolve(resolvedPrereqs,parameters );

        assertNull(result);
    }

    @Test
    public void testGPATermResolver(){
        //Setup the term resolver
        GPATermResolver termResolver = new GPATermResolver();
        termResolver.setAcademicRecordService(academicRecordService);

        //Create parameters
        parameters.put(KSKRMSExecutionConstants.PERSON_ID_TERM_PROPERTY, studentID);
        parameters.put(KSKRMSExecutionConstants.CALC_TYPE_KEY_TERM_PROPERTY, calcTypeID) ;

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSExecutionConstants.GPA_TERM_NAME);

        //Evaluate term Resolver
        GPAInfo result = termResolver.resolve(resolvedPrereqs, parameters) ;

        assertNotNull(result);
        assertEquals(result.getCalculationTypeKey(), calcTypeID );
        assertEquals(result.getScaleKey(), "1" );
        assertEquals(result.getValue(), "3.9");
    }

    @Test
    public void testGradeTermResolver(){
        //Setup the term resolver
        GradeTermResolver termResolver = new GradeTermResolver();
        termResolver.setAcademicRecordService(academicRecordService);

        //Create parameters
        parameters.put(KSKRMSExecutionConstants.PERSON_ID_TERM_PROPERTY, studentID);

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSExecutionConstants.GRADE_TERM_NAME);

        //Evaluate term Resolver
        List<StudentCourseRecordInfo> gradeRecords = termResolver.resolve(resolvedPrereqs, parameters);

        assertNotNull(gradeRecords);
    }

    @Test
    public void testGradeTypeTermResolver(){
        //Setup the term resolver
        GradeTypeTermResolver termResolver = new GradeTypeTermResolver();
        termResolver.setAcademicRecordService(academicRecordService);

        //Create parameters
        parameters.put(KSKRMSExecutionConstants.PERSON_ID_TERM_PROPERTY, studentID);

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSExecutionConstants.GRADE_TYPE_TERM_NAME);

        //Evaluate term Resolver
        List<StudentCourseRecordInfo> gradeTypeRecords = termResolver.resolve(resolvedPrereqs, parameters);

        assertNotNull(gradeTypeRecords);
    }

    @Test
    public void testKSKRMSTermResolver(){
        //Evaluate term Resolver
        assertNotNull(ksKRMSTermResolverTypeService);
    }

    @Ignore
    public void testLearningObjectivesTermResolver(){
        //Setup the term resolver
        LearningObjectivesTermResolver termResolver = new LearningObjectivesTermResolver();
        termResolver.setAcademicRecordService(academicRecordService);

        //Create parameters
        //TODO change values being sent
        parameters.put(RulesExecutionConstants.STUDENT_COMPLETED_COURSE_IDS_TERM_NAME, "kuali.org.Office");

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSExecutionConstants.LEARNING_OBJECTIVES_TERM_NAME);

        //Evaluate term Resolver
        List<StudentCourseRecordInfo> result = termResolver.resolve(resolvedPrereqs, parameters);

        assertNull(result);
    }

    @Ignore
    public void testNumberOfCoursesTermResolver(){
        //Setup the term resolver
        LearningObjectivesTermResolver termResolver = new LearningObjectivesTermResolver();
        termResolver.setAcademicRecordService(academicRecordService);

        //Create parameters
        //TODO change values being sent
        parameters.put(RulesExecutionConstants.STUDENT_COMPLETED_COURSE_IDS_TERM_NAME, "kuali.org.Office");

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSExecutionConstants.LEARNING_OBJECTIVES_TERM_NAME);

        //Evaluate term Resolver
        List<StudentCourseRecordInfo> result = termResolver.resolve(resolvedPrereqs, parameters);

        assertNull(result);
    }

    @Test
    public void testNumberOfCreditsTermResolver(){
        //Setup the term resolver
        NumberOfCreditsTermResolver termResolver = new NumberOfCreditsTermResolver();
        termResolver.setAcademicRecordService(academicRecordService);

        //Create parameters
        parameters.put(KSKRMSExecutionConstants.PERSON_ID_TERM_PROPERTY, studentID);
        parameters.put(KSKRMSExecutionConstants.CALC_TYPE_KEY_TERM_PROPERTY, calcTypeID);

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSExecutionConstants.NUMBER_OF_CREDITS_TERM_NAME);

        //Evaluate term Resolver
        String result = termResolver.resolve(resolvedPrereqs, parameters);

        assertNotNull(result);
    }

    @Ignore
    public void testScoreTermResolver(){
        //Setup the term resolver
        ScoreTermResolver termResolver = new ScoreTermResolver();
        termResolver.setAcademicRecordService(academicRecordService);

        //Create parameters
        //TODO change values being sent
        parameters.put(RulesExecutionConstants.STUDENT_COMPLETED_COURSE_IDS_TERM_NAME, "kuali.org.Office");

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSExecutionConstants.SCORE_TERM_NAME);

        //Evaluate term Resolver
        List<StudentCourseRecordInfo> records = termResolver.resolve(resolvedPrereqs, parameters);

        assertNull(records);
    }

    @Ignore
    public void testSubjectCodeTermResolver(){
        //Setup the term resolver
        SubjectCodeTermResolver termResolver = new SubjectCodeTermResolver();
        termResolver.setAcademicRecordService(academicRecordService);

        //Create parameters
        //TODO change values being sent
        parameters.put(RulesExecutionConstants.STUDENT_COMPLETED_COURSE_IDS_TERM_NAME, "kuali.org.Office");

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSExecutionConstants.SUBJECT_CODE_TERM_NAME);

        //Evaluate term Resolver
        List<StudentCourseRecordInfo> records = termResolver.resolve(resolvedPrereqs, parameters);

        assertNull(records);
    }

    private void validateTermResolver(TermResolver termResolver, Map<String, Object> prereqs, Map<String, String> parameters, String output){

        //Check the term name.
        assertEquals(output, termResolver.getOutput());

        //Check if prerequisites are listed
        validateAttributeSet("Prerequisites list does not contain ", prereqs.keySet(), termResolver.getPrerequisites());

        //Check if paramteters are listed
        validateAttributeSet("Parameters list does not contain", parameters.keySet(), termResolver.getParameterNames());
    }

    private void validateAttributeSet(String message, Set<String> names, Set<String> keys){

        for(String key : keys){
            assertTrue(message + key, names.contains(key));
        }
    }

    private Map<String, Object> getDefaultPrerequisites(){
        Map<String, Object> resolvedPrereqs = new HashMap<String, Object>();
        resolvedPrereqs.put(KSKRMSExecutionConstants.CONTEXT_INFO_TERM_NAME, contextInfo);
        return resolvedPrereqs;
    }

    private Map<String, String> getDefaultParameters(){
        Map<String, String> parameters = new HashMap<String, String>();
        return parameters;
    }

}

