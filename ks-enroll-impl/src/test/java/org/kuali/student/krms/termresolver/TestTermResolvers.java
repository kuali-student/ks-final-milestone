package org.kuali.student.krms.termresolver;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.rice.krms.impl.repository.ContextBoService;
import org.kuali.student.enrollment.academicrecord.service.AcademicRecordService;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.krms.service.impl.KSTermResolverTypeService;
import org.kuali.student.krms.util.KSKRMSExecutionConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.core.class1.organization.service.impl.OrgTestDataLoader;
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
    public void testNumberOfCompletedCoursesTermResolver() {
        //Setup the term resolver
        NumberOfCompletedCoursesTermResolver termResolver = new NumberOfCompletedCoursesTermResolver();
        termResolver.setAcademicRecordService(academicRecordService);

        //Add prerequisite
        resolvedPrereqs.put(KSKRMSExecutionConstants.PERSON_ID_TERM_PROPERTY, studentID);

        //Create parameters
        parameters.put(KSKRMSExecutionConstants.COURSE_CODES_TERM_PROPERTY, "DTC101,DTC102");

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                "NumberOfCompletedCourses");

        //Evaluate term Resolver
        Integer acadRecords = termResolver.resolve(resolvedPrereqs, parameters);

        assertNotNull(acadRecords);
        assertEquals(2, acadRecords.intValue());
    }

    @Test
    public void testCompletedCourseTermResolver() {
        //Setup the term resolver
        CompletedCourseTermResolver termResolver = new CompletedCourseTermResolver();

        //Add prerequisite
        resolvedPrereqs.put(KSKRMSExecutionConstants.PERSON_ID_TERM_PROPERTY, studentID);

        //Create parameters
        parameters.put(KSKRMSExecutionConstants.COURSE_CODE_TERM_PROPERTY, "DTC101,DTC102");


        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSExecutionConstants.COMPLETED_COURSES_TERM_NAME);

        //Evaluate term Resolver
        Boolean acadRecords = termResolver.resolve(resolvedPrereqs, parameters);

        assertNotNull(acadRecords);
        assertTrue(acadRecords);
    }

    //TODO KSENROLL-3833
    @Test
    public void testEnrolledCourseNumberTermResolver() {
        //Setup the term resolver
        EnrolledCourseNumberTermResolver termResolver = new EnrolledCourseNumberTermResolver();
        termResolver.setCourseRegistrationService(courseRegistrationService);

        //Add prerequisites
        resolvedPrereqs.put(KSKRMSExecutionConstants.PERSON_ID_TERM_PROPERTY, studentID);

        //Create parameters
        parameters.put(KSKRMSExecutionConstants.COURSE_ID_TERM_PROPERTY, "DTC101");

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSExecutionConstants.ENROLLED_COURSE_NUMBER_TERM_NAME);

        //Evaluate term Resolver
        Integer academicRecord = termResolver.resolve(resolvedPrereqs, parameters);

        assertNotNull(academicRecord);
    }

    //TODO KSENROLL-3833
    @Test
    public void testEnrolledCourseTermResolver() {
        //Setup the term resolver
        EnrolledCourseTermResolver termResolver = new EnrolledCourseTermResolver();
        termResolver.setCourseRegistrationService(courseRegistrationService);

        //Add prerequisites
        resolvedPrereqs.put(KSKRMSExecutionConstants.PERSON_ID_TERM_PROPERTY, studentID);

        //Create parameters
        parameters.put(KSKRMSExecutionConstants.COURSE_ID_TERM_PROPERTY, "DTC102");

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSExecutionConstants.ENROLLED_COURSE_TERM_NAME);

        //Evaluate term Resolver
        boolean academicRecord = termResolver.resolve(resolvedPrereqs, parameters);

        assertNotNull(academicRecord);
    }

    @Test
    public void testFreeTextTermResolver() {
        //Setup the term resolver
        FreeFormTextTermResolver termResolverForm = new FreeFormTextTermResolver();

        //Create parameters
        parameters.put(KSKRMSExecutionConstants.PERSON_ID_TERM_PROPERTY, studentID);

        //Validate the term resolver
        validateTermResolver(termResolverForm, resolvedPrereqs, parameters,
                KSKRMSExecutionConstants.FREE_TEXT_TERM_NAME);

        //Evaluate term Resolver
        Boolean result = termResolverForm.resolve(resolvedPrereqs, parameters);

        assertNotNull(result);
        assertTrue(result);
    }

    @Test
    public void testGPATermResolver() {
        //Setup the term resolver
        GPATermResolver termResolver = new GPATermResolver();
        termResolver.setAcademicRecordService(academicRecordService);

        //Add prerequisites
        resolvedPrereqs.put(KSKRMSExecutionConstants.PERSON_ID_TERM_PROPERTY, studentID);

        //Create parameters
        parameters.put(KSKRMSExecutionConstants.COURSE_CODE_TERM_PROPERTY, "DTC101");

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSExecutionConstants.GPA_TERM_NAME);

        //Evaluate term Resolver
        Integer result = termResolver.resolve(resolvedPrereqs, parameters);

        assertNotNull(result);
    }

    @Test
    public void testNumberOfCreditsTermResolver() {
        //Setup the term resolver
        NumberOfCreditsTermResolver termResolver = new NumberOfCreditsTermResolver();
        termResolver.setAcademicRecordService(academicRecordService);

        //Add prerequisites
        resolvedPrereqs.put(KSKRMSExecutionConstants.PERSON_ID_TERM_PROPERTY, studentID);

        //Create parameters
        parameters.put(KSKRMSExecutionConstants.CALC_TYPE_KEY_TERM_PROPERTY, calcTypeID);

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSExecutionConstants.NUMBER_OF_CREDITS_TERM_NAME);

        //Evaluate term Resolver
        Integer result = termResolver.resolve(resolvedPrereqs, parameters);

        assertNotNull(result);
    }

    @Test
    public void testKSKRMSTermResolver() {
        //Evaluate term Resolver
        assertNotNull(ksKRMSTermResolverTypeService);
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
        resolvedPrereqs.put(KSKRMSExecutionConstants.CONTEXT_INFO_TERM_NAME, contextInfo);
        return resolvedPrereqs;
    }

    private Map<String, String> getDefaultParameters() {
        Map<String, String> parameters = new HashMap<String, String>();
        return parameters;
    }

}

