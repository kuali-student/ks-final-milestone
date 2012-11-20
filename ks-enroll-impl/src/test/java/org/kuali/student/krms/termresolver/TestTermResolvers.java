package org.kuali.student.krms.termresolver;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.rice.krms.impl.repository.ContextBoService;
import org.kuali.rice.krms.impl.type.KrmsTypeResolver;
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
public class TestTermResolvers {

    public ContextInfo contextInfo = null;
    Map<String, Object> resolvedPrereqs = null;
    Map<String, String> parameters = null;

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

        OrgTestDataLoader orgDataLoader = new OrgTestDataLoader(organizationService);
        orgDataLoader.loadData();

    }

    @Test
    public void testAdminOrgNumberTermResolver() {
        //Setup the term resolver
        AdminOrgNumberTermResolver termResolver = new AdminOrgNumberTermResolver();
        termResolver.setOrganizationService(organizationService);

        //Create prerequisites
        //Map<String, Object> resolvedPrereqs = getDefaultPrerequisites();

        //Create parameters
        //Map<String, String> parameters = new HashMap<String, String>();
        parameters.put(KSKRMSExecutionConstants.ORG_TYPE_KEY_TERM_PROPERTY, "kuali.org.Office");

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters, RulesExecutionConstants.ADMIN_ORG_NUMBER_TERM_NAME);

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
        //TODO change values being sent
        parameters.put(RulesExecutionConstants.STUDENT_COMPLETED_COURSE_IDS_TERM_NAME, "kuali.org.Office");

        //Validate the term resolver
        //TODO change values being sent
        validateTermResolver(termResolver, resolvedPrereqs, parameters, RulesExecutionConstants.COMPLETED_COURSE_TERM_NAME);

        //Evaluate term Resolver
        List<StudentCourseRecordInfo> acadRecords = termResolver.resolve(resolvedPrereqs, parameters);

        assertNotNull(acadRecords);
    }

    @Test
    private void testCompletedCourseNumberTermResolver(){
        //Setup the term resolver
        CompletedCourseNumberTermResolver termResolver = new CompletedCourseNumberTermResolver();
        termResolver.setAcademicRecordService(academicRecordService);

        //Create parameters
        //TODO change values being sent
        parameters.put(RulesExecutionConstants.STUDENT_COMPLETED_COURSE_IDS_TERM_NAME, "kuali.org.Office");

        //Validate the term resolver
        //TODO change values being sent
        validateTermResolver(termResolver, resolvedPrereqs, parameters, RulesExecutionConstants.NR_OF_COMPLETED_COURSES_TERM_NAME);

        //Evaluate term Resolver
        List<StudentCourseRecordInfo> acadRecords = termResolver.resolve(resolvedPrereqs, parameters);

        assertNotNull(acadRecords);
    }

    @Test
    public void testCompletedCourseSetTermResolver(){
        //Setup the term resolver
        CompletedCourseSetTermResolver termResolver = new CompletedCourseSetTermResolver();
        termResolver.setAcademicRecordService(academicRecordService);

        //Create parameters
        //TODO change values being sent
        parameters.put(RulesExecutionConstants.STUDENT_COMPLETED_COURSE_IDS_TERM_NAME, "kuali.org.Office");

        //Validate the term resolver
        //TODO change values being sent
        validateTermResolver(termResolver, resolvedPrereqs, parameters, RulesExecutionConstants.NR_OF_COMPLETED_COURSES_TERM_NAME);

        //Evaluate term Resolver
        List<StudentCourseRecordInfo> acadRecords = termResolver.resolve(resolvedPrereqs, parameters);

        assertNotNull(acadRecords);
    }

    @Test
    public void testCompletedCoursesTermResolver(){
        //Setup the term resolver
        CompletedCoursesTermResolver termResolver = new CompletedCoursesTermResolver();
        termResolver.setAcademicRecordService(academicRecordService);

        //Create parameters
        //TODO change values being sent
        parameters.put(RulesExecutionConstants.STUDENT_COMPLETED_COURSE_IDS_TERM_NAME, "kuali.org.Office");

        //Validate the term resolver
        //TODO change values being sent
        validateTermResolver(termResolver, resolvedPrereqs, parameters, RulesExecutionConstants.NR_OF_COMPLETED_COURSES_TERM_NAME);

        //Evaluate term Resolver
        List<StudentCourseRecordInfo> acadRecords = termResolver.resolve(resolvedPrereqs, parameters);

        assertNotNull(acadRecords);
    }

    @Test
    public void testCompletedCourseTermResolver(){
        //Setup the term resolver
        CompletedCourseTermResolver termResolver = new CompletedCourseTermResolver();
        termResolver.setAcademicRecordService(academicRecordService);

        //Create parameters
        //TODO change values being sent
        parameters.put(RulesExecutionConstants.STUDENT_COMPLETED_COURSE_IDS_TERM_NAME, "kuali.org.Office");
        parameters.put(KSKRMSExecutionConstants.COURSE_CODE_TERM_PROPERTY, "DTC101");

        //Validate the term resolver
        //TODO change values being sent
        validateTermResolver(termResolver, resolvedPrereqs, parameters, RulesExecutionConstants.NR_OF_COMPLETED_COURSES_TERM_NAME);

        //Evaluate term Resolver
        List<StudentCourseRecordInfo> acadRecords = termResolver.resolve(resolvedPrereqs, parameters);

        assertNotNull(acadRecords);
    }

    @Test
    public void testCompletedEffectiveDateFromTermResolver(){
        //Setup the term resolver
        CompletedEffectiveDateFromTermResolver termResolver = new CompletedEffectiveDateFromTermResolver();
        termResolver.setAcademicRecordService(academicRecordService);

        //Create parameters
        //TODO change values being sent
        parameters.put(RulesExecutionConstants.STUDENT_COMPLETED_COURSE_IDS_TERM_NAME, "kuali.org.Office");

        //Validate the term resolver
        //TODO change values being sent
        validateTermResolver(termResolver, resolvedPrereqs, parameters, RulesExecutionConstants.NR_OF_COMPLETED_COURSES_TERM_NAME);

        //Evaluate term Resolver
        List<StudentCourseRecordInfo> acadRecords = termResolver.resolve(resolvedPrereqs, parameters);

        assertNotNull(acadRecords);
    }

    @Test
    public void testCompletedEffectiveDateToTermResolver(){
        //Setup the term resolver
        CompletedEffectiveDateToTermResolver termResolver = new CompletedEffectiveDateToTermResolver();
        termResolver.setAcademicRecordService(academicRecordService);

        //Create parameters
        //TODO change values being sent
        parameters.put(RulesExecutionConstants.STUDENT_COMPLETED_COURSE_IDS_TERM_NAME, "kuali.org.Office");

        //Validate the term resolver
        //TODO change values being sent
        validateTermResolver(termResolver, resolvedPrereqs, parameters, RulesExecutionConstants.NR_OF_COMPLETED_COURSES_TERM_NAME);

        //Evaluate term Resolver
        List<StudentCourseRecordInfo> acadRecords = termResolver.resolve(resolvedPrereqs, parameters);

        assertNotNull(acadRecords);
    }

    @Test
    public void testCompletedLearningObjectivesTermResolver(){
        //Setup the term resolver
        CompletedLearningObjectivesTermResolver termResolver = new CompletedLearningObjectivesTermResolver();
        termResolver.setAcademicRecordService(academicRecordService);

        //Create parameters
        //TODO change values being sent
        parameters.put(RulesExecutionConstants.STUDENT_COMPLETED_COURSE_IDS_TERM_NAME, "kuali.org.Office");

        //Validate the term resolver
        //TODO change values being sent
        validateTermResolver(termResolver, resolvedPrereqs, parameters, RulesExecutionConstants.NR_OF_COMPLETED_COURSES_TERM_NAME);

        //Evaluate term Resolver
        List<StudentCourseRecordInfo> acadRecords = termResolver.resolve(resolvedPrereqs, parameters);

        assertNotNull(acadRecords);
    }

    @Test
    public void testCompletedCourseNumberRangeTermResolver(){
        //Setup the term resolver
        CourseNumberRangeTermResolver termResolver = new CourseNumberRangeTermResolver();
        termResolver.setAcademicRecordService(academicRecordService);

        //Create parameters
        //TODO change values being sent
        parameters.put(RulesExecutionConstants.STUDENT_COMPLETED_COURSE_IDS_TERM_NAME, "kuali.org.Office");

        //Validate the term resolver
        //TODO change values being sent
        validateTermResolver(termResolver, resolvedPrereqs, parameters, RulesExecutionConstants.NR_OF_COMPLETED_COURSES_TERM_NAME);

        //Evaluate term Resolver
        List<StudentCourseRecordInfo> acadRecords = termResolver.resolve(resolvedPrereqs, parameters);

        assertNotNull(acadRecords);
    }

    @Test
    public void testCourseSetTermResolver(){
        //Setup the term resolver
        CourseSetTermResolver termResolver = new CourseSetTermResolver();
        termResolver.setAcademicRecordService(academicRecordService);

        //Create parameters
        //TODO change values being sent
        parameters.put(RulesExecutionConstants.STUDENT_COMPLETED_COURSE_IDS_TERM_NAME, "kuali.org.Office");

        //Validate the term resolver
        //TODO change values being sent
        validateTermResolver(termResolver, resolvedPrereqs, parameters, RulesExecutionConstants.NR_OF_COMPLETED_COURSES_TERM_NAME);

        //Evaluate term Resolver
        List<StudentCourseRecordInfo> acadRecords = termResolver.resolve(resolvedPrereqs, parameters);

        assertNotNull(acadRecords);
    }

    @Test
    public void testDeptNumberTermResolver(){
        //Setup the term resolver
        DeptNumberTermResolver termResolver = new DeptNumberTermResolver();
        termResolver.setOrganizationService(organizationService);

        //Create parameters
        //TODO change values being sent
        parameters.put(RulesExecutionConstants.STUDENT_COMPLETED_COURSE_IDS_TERM_NAME, "kuali.org.Office");

        //Validate the term resolver
        //TODO change values being sent
        validateTermResolver(termResolver, resolvedPrereqs, parameters, RulesExecutionConstants.NR_OF_COMPLETED_COURSES_TERM_NAME);

        //Evaluate term Resolver
        OrgInfo orgRecords = termResolver.resolve(resolvedPrereqs, parameters);

        assertNotNull(orgRecords);
    }

    @Test
    public void testEffectiveDateFromTermResolver(){
        //Setup the term resolver
        EffectiveDateFromTermResolver termResolver = new EffectiveDateFromTermResolver();
        termResolver.setAcademicRecordService(academicRecordService);

        //Create parameters
        //TODO change values being sent
        parameters.put(RulesExecutionConstants.STUDENT_COMPLETED_COURSE_IDS_TERM_NAME, "kuali.org.Office");

        //Validate the term resolver
        //TODO change values being sent
        validateTermResolver(termResolver, resolvedPrereqs, parameters, RulesExecutionConstants.NR_OF_COMPLETED_COURSES_TERM_NAME);

        //Evaluate term Resolver
        List<StudentCourseRecordInfo> acadRecords = termResolver.resolve(resolvedPrereqs, parameters);

        assertNotNull(acadRecords);
    }

    @Test
    public void testEffectiveDateToTermResolver(){
        //Setup the term resolver
        EffectiveDateToTermResolver termResolver = new EffectiveDateToTermResolver();
        termResolver.setAcademicRecordService(academicRecordService);

        //Create parameters
        //TODO change values being sent
        parameters.put(RulesExecutionConstants.STUDENT_COMPLETED_COURSE_IDS_TERM_NAME, "kuali.org.Office");

        //Validate the term resolver
        //TODO change values being sent
        validateTermResolver(termResolver, resolvedPrereqs, parameters, RulesExecutionConstants.NR_OF_COMPLETED_COURSES_TERM_NAME);

        //Evaluate term Resolver
        List<StudentCourseRecordInfo> acadRecords = termResolver.resolve(resolvedPrereqs, parameters);

        assertNotNull(acadRecords);
    }

    @Test
     public void testEnrolledCourseByTermTermResolver(){
        //Setup the term resolver
        EnrolledCourseByTermTermResolver termResolver = new EnrolledCourseByTermTermResolver();
        termResolver.setCourseRegistrationService(courseRegistrationService);

        //Create parameters
        //TODO change values being sent
        parameters.put(RulesExecutionConstants.STUDENT_COMPLETED_COURSE_IDS_TERM_NAME, "kuali.org.Office");

        //Validate the term resolver
        //TODO change values being sent
        validateTermResolver(termResolver, resolvedPrereqs, parameters, RulesExecutionConstants.NR_OF_COMPLETED_COURSES_TERM_NAME);

        //Evaluate term Resolver
        List<CourseRegistrationInfo> courseRegistrationRecords = termResolver.resolve(resolvedPrereqs, parameters);

        assertNotNull(courseRegistrationRecords);
    }

    @Test
    public void testEnrolledCourseCodeTermResolver(){
        //Setup the term resolver
        EnrolledCourseCodeTermResolver termResolver = new EnrolledCourseCodeTermResolver();
        termResolver.setCourseRegistrationService(courseRegistrationService);

        //Create parameters
        //TODO change values being sent
        parameters.put(RulesExecutionConstants.STUDENT_COMPLETED_COURSE_IDS_TERM_NAME, "kuali.org.Office");

        //Validate the term resolver
        //TODO change values being sent
        validateTermResolver(termResolver, resolvedPrereqs, parameters, RulesExecutionConstants.NR_OF_COMPLETED_COURSES_TERM_NAME);

        //Evaluate term Resolver
        List<CourseRegistrationInfo> courseRegistrationRecords = termResolver.resolve(resolvedPrereqs, parameters);

        assertNotNull(courseRegistrationRecords);
    }

    @Test
    public void testEnrolledCourseNumberTermResolver(){
        //Setup the term resolver
        EnrolledCourseNumberTermResolver termResolver = new EnrolledCourseNumberTermResolver();
        termResolver.setCourseRegistrationService(courseRegistrationService);

        //Create parameters
        //TODO change values being sent
        parameters.put(RulesExecutionConstants.STUDENT_COMPLETED_COURSE_IDS_TERM_NAME, "kuali.org.Office");

        //Validate the term resolver
        //TODO change values being sent
        validateTermResolver(termResolver, resolvedPrereqs, parameters, RulesExecutionConstants.NR_OF_COMPLETED_COURSES_TERM_NAME);

        //Evaluate term Resolver
        List<CourseRegistrationInfo> courseRegistrationRecords = termResolver.resolve(resolvedPrereqs, parameters);

        assertNotNull(courseRegistrationRecords);
    }

    @Test
    public void testEnrolledCoursesByTermTermResolver(){
        //Setup the term resolver
        EnrolledCoursesByTermTermResolver termResolver = new EnrolledCoursesByTermTermResolver();
        termResolver.setCourseRegistrationService(courseRegistrationService);

        //Create parameters
        //TODO change values being sent
        parameters.put(RulesExecutionConstants.STUDENT_COMPLETED_COURSE_IDS_TERM_NAME, "kuali.org.Office");
        parameters.put(KSKRMSExecutionConstants.TERM_ID_TERM_PROPERTY, "1001");

        //Validate the term resolver
        //TODO change values being sent
        validateTermResolver(termResolver, resolvedPrereqs, parameters, RulesExecutionConstants.NR_OF_COMPLETED_COURSES_TERM_NAME);

        //Evaluate term Resolver
        List<CourseRegistrationInfo> courseRegistrationRecords = termResolver.resolve(resolvedPrereqs, parameters);

        assertNotNull(courseRegistrationRecords);
    }

    @Test
    public void testEnrolledCourseSetTermResolver(){
        //Setup the term resolver
        EnrolledCourseSetTermResolver termResolver = new EnrolledCourseSetTermResolver();
        termResolver.setCourseRegistrationService(courseRegistrationService);

        //Create parameters
        //TODO change values being sent
        parameters.put(RulesExecutionConstants.STUDENT_COMPLETED_COURSE_IDS_TERM_NAME, "kuali.org.Office");
        parameters.put(KSKRMSExecutionConstants.TERM_ID_TERM_PROPERTY, "1001");

        //Validate the term resolver
        //TODO change values being sent
        validateTermResolver(termResolver, resolvedPrereqs, parameters, RulesExecutionConstants.NR_OF_COMPLETED_COURSES_TERM_NAME);

        //Evaluate term Resolver
        List<CourseRegistrationInfo> courseRegistrationRecords = termResolver.resolve(resolvedPrereqs, parameters);

        assertNotNull(courseRegistrationRecords);
    }

    @Test
    public void testEnrolledCoursesTermResolver(){
        //Setup the term resolver
        EnrolledCoursesTermResolver termResolver = new EnrolledCoursesTermResolver();
        termResolver.setCourseRegistrationService(courseRegistrationService);

        //Create parameters
        //TODO change values being sent
        parameters.put(RulesExecutionConstants.STUDENT_COMPLETED_COURSE_IDS_TERM_NAME, "kuali.org.Office");
        parameters.put(KSKRMSExecutionConstants.TERM_ID_TERM_PROPERTY, "1001");

        //Validate the term resolver
        //TODO change values being sent
        validateTermResolver(termResolver, resolvedPrereqs, parameters, RulesExecutionConstants.NR_OF_COMPLETED_COURSES_TERM_NAME);

        //Evaluate term Resolver
        List<CourseRegistrationInfo> courseRegistrationRecords = termResolver.resolve(resolvedPrereqs, parameters);

        assertNotNull(courseRegistrationRecords);
    }

    @Test
    public void testEnrolledCourseTermResolver(){
        //Setup the term resolver
        EnrolledCourseTermResolver termResolver = new EnrolledCourseTermResolver();
        termResolver.setCourseRegistrationService(courseRegistrationService);

        //Create parameters
        //TODO change values being sent
        parameters.put(RulesExecutionConstants.STUDENT_COMPLETED_COURSE_IDS_TERM_NAME, "kuali.org.Office");
        parameters.put(KSKRMSExecutionConstants.TERM_ID_TERM_PROPERTY, "1001");

        //Validate the term resolver
        //TODO change values being sent
        validateTermResolver(termResolver, resolvedPrereqs, parameters, RulesExecutionConstants.NR_OF_COMPLETED_COURSES_TERM_NAME);

        //Evaluate term Resolver
        List<CourseRegistrationInfo> courseRegistrationRecords = termResolver.resolve(resolvedPrereqs, parameters);

        assertNotNull(courseRegistrationRecords);
    }

    @Test
    public void testEnrolledEffectiveDateFromTermResolver(){
        //Setup the term resolver
        EnrolledEffectiveDateFromTermResolver termResolver = new EnrolledEffectiveDateFromTermResolver();
        termResolver.setCourseRegistrationService(courseRegistrationService);

        //Create parameters
        //TODO change values being sent
        parameters.put(RulesExecutionConstants.STUDENT_COMPLETED_COURSE_IDS_TERM_NAME, "kuali.org.Office");
        parameters.put(KSKRMSExecutionConstants.TERM_ID_TERM_PROPERTY, "1001");

        //Validate the term resolver
        //TODO change values being sent
        validateTermResolver(termResolver, resolvedPrereqs, parameters, RulesExecutionConstants.NR_OF_COMPLETED_COURSES_TERM_NAME);

        //Evaluate term Resolver
        List<CourseRegistrationInfo> courseRegistrationRecords = termResolver.resolve(resolvedPrereqs, parameters);

        assertNotNull(courseRegistrationRecords);
    }

    @Test
    public void testEnrolledEffectiveDateToTermResolver(){
        //Setup the term resolver
        EnrolledEffectiveDateToTermResolver termResolver = new EnrolledEffectiveDateToTermResolver();
        termResolver.setCourseRegistrationService(courseRegistrationService);

        //Create parameters
        //TODO change values being sent
        parameters.put(RulesExecutionConstants.STUDENT_COMPLETED_COURSE_IDS_TERM_NAME, "kuali.org.Office");
        parameters.put(KSKRMSExecutionConstants.TERM_ID_TERM_PROPERTY, "1001");

        //Validate the term resolver
        //TODO change values being sent
        validateTermResolver(termResolver, resolvedPrereqs, parameters, RulesExecutionConstants.NR_OF_COMPLETED_COURSES_TERM_NAME);

        //Evaluate term Resolver
        List<CourseRegistrationInfo> courseRegistrationRecords = termResolver.resolve(resolvedPrereqs, parameters);

        assertNotNull(courseRegistrationRecords);
    }

    @Test
    public void testEnrolledLearningObjectivesTermResolver(){
        //Setup the term resolver
        EnrolledLearningObjectivesTermResolver termResolver = new EnrolledLearningObjectivesTermResolver();
        termResolver.setCourseRegistrationService(courseRegistrationService);

        //Create parameters
        //TODO change values being sent
        parameters.put(RulesExecutionConstants.STUDENT_COMPLETED_COURSE_IDS_TERM_NAME, "kuali.org.Office");
        parameters.put(KSKRMSExecutionConstants.TERM_ID_TERM_PROPERTY, "1001");

        //Validate the term resolver
        //TODO change values being sent
        validateTermResolver(termResolver, resolvedPrereqs, parameters, RulesExecutionConstants.NR_OF_COMPLETED_COURSES_TERM_NAME);

        //Evaluate term Resolver
        List<CourseRegistrationInfo> courseRegistrationRecords = termResolver.resolve(resolvedPrereqs, parameters);

        assertNotNull(courseRegistrationRecords);
    }

    @Test
    public void testFreeTextTermResolver(){
        //Setup the term resolver
        FreeTextTermResolver termResolver = new FreeTextTermResolver();

        //Create parameters
        //TODO change values being sent
        parameters.put(RulesExecutionConstants.STUDENT_COMPLETED_COURSE_IDS_TERM_NAME, "kuali.org.Office");
        parameters.put(KSKRMSExecutionConstants.CALC_TYPE_KEY_TERM_PROPERTY, "222");

        //Validate the term resolver
        //TODO change values being sent
        validateTermResolver(termResolver, resolvedPrereqs, parameters, RulesExecutionConstants.NR_OF_COMPLETED_COURSES_TERM_NAME);

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
        //TODO change values being sent
        parameters.put(RulesExecutionConstants.STUDENT_COMPLETED_COURSE_IDS_TERM_NAME, "kuali.org.Office");
        parameters.put(KSKRMSExecutionConstants.CALC_TYPE_KEY_TERM_PROPERTY, "mockTypeKey3") ;

        //Validate the term resolver
        //TODO change values being sent
        validateTermResolver(termResolver, resolvedPrereqs, parameters, RulesExecutionConstants.NR_OF_COMPLETED_COURSES_TERM_NAME);

        //Evaluate term Resolver
        GPAInfo result = termResolver.resolve(resolvedPrereqs, parameters) ;

        assertNotNull(result);
        assertEquals(result.getCalculationTypeKey(), "mockTypeKey3" );
        assertEquals(result.getScaleKey(), "1" );
        assertEquals(result.getValue(), "3.9");
    }

    @Test
    public void testGradeTermResolver(){
        //Setup the term resolver
        GradeTermResolver termResolver = new GradeTermResolver();
        termResolver.setAcademicRecordService(academicRecordService);

        //Create parameters
        //TODO change values being sent
        parameters.put(RulesExecutionConstants.STUDENT_COMPLETED_COURSE_IDS_TERM_NAME, "kuali.org.Office");

        //Validate the term resolver
        //TODO change values being sent
        validateTermResolver(termResolver, resolvedPrereqs, parameters, RulesExecutionConstants.NR_OF_COMPLETED_COURSES_TERM_NAME);

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
        //TODO change values being sent
        parameters.put(RulesExecutionConstants.STUDENT_COMPLETED_COURSE_IDS_TERM_NAME, "kuali.org.Office");

        //Validate the term resolver
        //TODO change values being sent
        validateTermResolver(termResolver, resolvedPrereqs, parameters, RulesExecutionConstants.NR_OF_COMPLETED_COURSES_TERM_NAME);

        //Evaluate term Resolver
        List<StudentCourseRecordInfo> gradeTypeRecords = termResolver.resolve(resolvedPrereqs, parameters);

        assertNotNull(gradeTypeRecords);
    }

    @Test
    public void testKSKRMSTermResolver(){
        //Evaluate term Resolver
        assertNotNull(ksKRMSTermResolverTypeService);
    }

    @Test
    public void testLearningObjectivesTermResolver(){
        //Setup the term resolver
        LearningObjectivesTermResolver termResolver = new LearningObjectivesTermResolver();
        termResolver.setAcademicRecordService(academicRecordService);

        //Create parameters
        //TODO change values being sent
        parameters.put(RulesExecutionConstants.STUDENT_COMPLETED_COURSE_IDS_TERM_NAME, "kuali.org.Office");

        //Validate the term resolver
        //TODO change values being sent
        validateTermResolver(termResolver, resolvedPrereqs, parameters, RulesExecutionConstants.NR_OF_COMPLETED_COURSES_TERM_NAME);

        //Evaluate term Resolver
        List<StudentCourseRecordInfo> result = termResolver.resolve(resolvedPrereqs, parameters);

        assertNull(result);
    }

    @Test
    public void testNumberOfCoursesTermResolver(){
        //Setup the term resolver
        LearningObjectivesTermResolver termResolver = new LearningObjectivesTermResolver();
        termResolver.setAcademicRecordService(academicRecordService);

        //Create parameters
        //TODO change values being sent
        parameters.put(RulesExecutionConstants.STUDENT_COMPLETED_COURSE_IDS_TERM_NAME, "kuali.org.Office");

        //Validate the term resolver
        //TODO change values being sent
        validateTermResolver(termResolver, resolvedPrereqs, parameters, RulesExecutionConstants.NR_OF_COMPLETED_COURSES_TERM_NAME);

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
        //TODO change values being sent
        parameters.put(RulesExecutionConstants.STUDENT_COMPLETED_COURSE_IDS_TERM_NAME, "kuali.org.Office");
        parameters.put(KSKRMSExecutionConstants.CALC_TYPE_KEY_TERM_PROPERTY, "mockTypeKey1");

        //Validate the term resolver
        //TODO change values being sent
        validateTermResolver(termResolver, resolvedPrereqs, parameters, RulesExecutionConstants.NR_OF_COMPLETED_COURSES_TERM_NAME);

        //Evaluate term Resolver
        String result = termResolver.resolve(resolvedPrereqs, parameters);

        assertNotNull(result);
    }

    @Test
    public void testScoreTermResolver(){
        //Setup the term resolver
        ScoreTermResolver termResolver = new ScoreTermResolver();
        termResolver.setAcademicRecordService(academicRecordService);

        //Create parameters
        //TODO change values being sent
        parameters.put(RulesExecutionConstants.STUDENT_COMPLETED_COURSE_IDS_TERM_NAME, "kuali.org.Office");

        //Validate the term resolver
        //TODO change values being sent
        validateTermResolver(termResolver, resolvedPrereqs, parameters, RulesExecutionConstants.NR_OF_COMPLETED_COURSES_TERM_NAME);

        //Evaluate term Resolver
        List<StudentCourseRecordInfo> records = termResolver.resolve(resolvedPrereqs, parameters);

        assertNull(records);
    }

    @Test
    public void testSubjectCodeTermResolver(){
        //Setup the term resolver
        SubjectCodeTermResolver termResolver = new SubjectCodeTermResolver();
        termResolver.setAcademicRecordService(academicRecordService);

        //Create parameters
        //TODO change values being sent
        parameters.put(RulesExecutionConstants.STUDENT_COMPLETED_COURSE_IDS_TERM_NAME, "kuali.org.Office");

        //Validate the term resolver
        //TODO change values being sent
        validateTermResolver(termResolver, resolvedPrereqs, parameters, RulesExecutionConstants.NR_OF_COMPLETED_COURSES_TERM_NAME);

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
        resolvedPrereqs.put(RulesExecutionConstants.CONTEXT_INFO_TERM_NAME, contextInfo);
        return resolvedPrereqs;
    }

    private Map<String, String> getDefaultParameters(){
        Map<String, String> parameters = new HashMap<String, String>();
        return parameters;
    }

}

