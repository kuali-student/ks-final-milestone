package org.kuali.student.krms.termresolver;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.rice.krms.impl.repository.ContextBoService;
import org.kuali.student.enrollment.academicrecord.service.AcademicRecordService;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.krms.service.impl.KSTermResolverTypeService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.common.util.constants.KSKRMSServiceConstants;
import org.kuali.student.r2.core.class1.organization.service.impl.OrgTestDataLoader;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.HashMap;
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
    private String studentID = "12020303";
    private String termID = "1001";
    private String calcTypeID = "mockTypeKey3";

    @Resource(name = "orgServiceImpl")
    private OrganizationService organizationService;

    @Resource(name = "cluService")
    private CluService cluService;

    @Resource(name = "acadRecordService")
    private AcademicRecordService academicRecordService;

    @Resource(name = "courseRegistrationService")
    private CourseRegistrationService courseRegistrationService;

    @Resource(name = "courseOfferingService")
    private CourseOfferingService courseOfferingService;

    @Before
    public void setUp() {
        contextInfo = new ContextInfo();
        contextInfo.setLocale(new LocaleInfo());
        contextInfo.setPrincipalId("admin");

        resolvedPrereqs = getDefaultPrerequisites();
        parameters = getDefaultParameters();
    }

    @Test
    public void testAdminOrgPermissionRequiredTermResolver() {
        OrgTestDataLoader orgDataLoader = new OrgTestDataLoader(organizationService);
        orgDataLoader.loadData();

        //Setup the term resolver
        AdminOrgPermRequiredTermResolver termResolver = new AdminOrgPermRequiredTermResolver();
        termResolver.setOrganizationService(organizationService);

        //Add prerequisite
        resolvedPrereqs.put(KSKRMSServiceConstants.TERM_PREREQUISITE_PERSON_ID, studentID);

        //Create parameters
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
    public void testClassStandingTermResolver() {
        //Setup the term resolver
        ClassStandingTermResolver termResolver = new ClassStandingTermResolver();

    }

    @Test
    public void testCompletedCoursesTermResolver() {
        //Setup the term resolver
        CompletedCoursesTermResolver termResolver = new CompletedCoursesTermResolver();

    }

    @Test
    public void testCompletedCourseTermResolver() {
        //Setup the term resolver
        CompletedCourseTermResolver termResolver = new CompletedCourseTermResolver();
        termResolver.setAcademicRecordService(this.academicRecordService);

        //Add prerequisite
        resolvedPrereqs.put(KSKRMSServiceConstants.TERM_PREREQUISITE_PERSON_ID, studentID);

        //Create parameters
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_CLU_KEY, "DTC101");

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSServiceConstants.TERM_RESOLVER_COMPLETEDCOURSES);

        //Evaluate term Resolver
        Boolean isCompleted = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(isCompleted);
        assertTrue(isCompleted);
    }

    @Test
    public void testEnrolledCoursesTermResolver() {
        //Setup the term resolver
        EnrolledCoursesTermResolver termResolver = new EnrolledCoursesTermResolver();

    }

    @Test
    public void testEnrolledCourseTermResolver() {
        //Setup the term resolver
        EnrolledCourseTermResolver termResolver = new EnrolledCourseTermResolver();
        termResolver.setCourseRegistrationService(courseRegistrationService);

        //Add prerequisites
        resolvedPrereqs.put(KSKRMSServiceConstants.TERM_PREREQUISITE_PERSON_ID, studentID);

        //Create parameters
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_CLU_KEY, "DTC102");

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSServiceConstants.TERM_RESOLVER_ENROLLEDCOURSE);

        //Evaluate term Resolver
        //boolean academicRecord = termResolver.resolve(resolvedPrereqs, parameters);

        //assertNotNull(academicRecord);
    }

    @Test
    public void testFreeTextTermResolver() {
        //Setup the term resolver
        FreeFormTextTermResolver termResolverForm = new FreeFormTextTermResolver();

        //Create parameters
        parameters.put(KSKRMSServiceConstants.TERM_PREREQUISITE_PERSON_ID, studentID);

        //Validate the term resolver
        validateTermResolver(termResolverForm, resolvedPrereqs, parameters,
                KSKRMSServiceConstants.TERM_RESOLVER_FREEFORMTEXT);

        //Evaluate term Resolver
        //Boolean result = termResolverForm.resolve(resolvedPrereqs, parameters);

        //assertNotNull(result);
        //assertTrue(result);
    }

    @Test
    public void testGPATermResolver() {
        //Setup the term resolver
        GPATermResolver termResolver = new GPATermResolver();
        termResolver.setAcademicRecordService(academicRecordService);

        //Add prerequisites
        resolvedPrereqs.put(KSKRMSServiceConstants.TERM_PREREQUISITE_PERSON_ID, studentID);

        //Create parameters
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_CLU_KEY, "DTC101");

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSServiceConstants.TERM_RESOLVER_GPAFORCOURSES);

        //Evaluate term Resolver
        //Integer result = termResolver.resolve(resolvedPrereqs, parameters);

        //assertNotNull(result);
    }

    @Test
    public void testGradeTypeTermResolver() {
        //Setup the term resolver
        GradeTypeTermResolver termResolver = new GradeTypeTermResolver();

    }

    @Test
    public void testNumberOfCompletedCoursesTermResolver() {
        //Setup the term resolver
        NumberOfCompletedCoursesTermResolver termResolver = new NumberOfCompletedCoursesTermResolver();
        termResolver.setAcademicRecordService(academicRecordService);
        termResolver.setCluService(cluService);
        termResolver.setCourseOfferingService(courseOfferingService);

        //Add prerequisite
        resolvedPrereqs.put(KSKRMSServiceConstants.TERM_PREREQUISITE_PERSON_ID, studentID);

        //Create parameters
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_CLUSET_KEY, "DTC101,DTC102");

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSServiceConstants.TERM_RESOLVER_NUMBEROFCOMPLETEDCOURSES);

        //Evaluate term Resolver
        //Integer acadRecords = termResolver.resolve(resolvedPrereqs, parameters);

        //assertNotNull(acadRecords);
        //assertEquals(2, acadRecords.intValue());
    }

    @Test
    public void testNumberOfCreditsTermResolver() {
        //Setup the term resolver
        NumberOfCreditsTermResolver termResolver = new NumberOfCreditsTermResolver();
        termResolver.setAcademicRecordService(academicRecordService);

        //Add prerequisites
        resolvedPrereqs.put(KSKRMSServiceConstants.TERM_PREREQUISITE_PERSON_ID, studentID);

        //Create parameters
        //parameters.put(KSKRMSServiceConstants.CALC_TYPE_KEY_TERM_PROPERTY, calcTypeID);

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSServiceConstants.TERM_RESOLVER_NUMBEROFCREDITS);

        //Evaluate term Resolver
        //Integer result = termResolver.resolve(resolvedPrereqs, parameters);

        //assertNotNull(result);
    }

    @Test
    public void testNumberOfEnrolledCoursesTermResolver() {
        //Setup the term resolver
        NumberOfEnrolledCoursesTermResolver termResolver = new NumberOfEnrolledCoursesTermResolver();

    }

    @Test
    public void testNumberOfEnrollmentsForCourseTermResolver() {
        //Setup the term resolver
        NumberOfEnrollmentsForCourseTermResolver termResolver = new NumberOfEnrollmentsForCourseTermResolver();

    }

    @Test
    public void testProgramCourseCampusTermResolver() {
        //Setup the term resolver
        ProgramCourseCampusTermResolver termResolver = new ProgramCourseCampusTermResolver();

    }

    @Test
    public void testScoreTermResolver() {
        //Setup the term resolver
        ScoreTermResolver termResolver = new ScoreTermResolver();

    }

    private void validateTermResolver(TermResolver termResolver, Map<String, Object> prereqs, Map<String, String> parameters, String output) {

        //Check the term name.
        assertEquals(output, termResolver.getOutput());

        //Check if prerequisites are listed
        validateAttributeSet("Prerequisites list does not contain ", prereqs.keySet(), termResolver.getPrerequisites());

        //Check if paramteters are listed
        validateAttributeSet("Parameters list does not contain ", parameters.keySet(), termResolver.getParameterNames());
    }

    private void validateAttributeSet(String message, Set<String> names, Set<String> keys) {

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

}

