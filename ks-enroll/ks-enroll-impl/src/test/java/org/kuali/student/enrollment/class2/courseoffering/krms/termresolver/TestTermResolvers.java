package org.kuali.student.enrollment.class2.courseoffering.krms.termresolver;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.krms.api.repository.RuleManagementService;
import org.kuali.rice.krms.api.repository.agenda.AgendaDefinition;
import org.kuali.rice.krms.api.repository.reference.ReferenceObjectBinding;
import org.kuali.rice.krms.impl.repository.mock.RuleManagementServiceMockImpl;
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
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestItemInfo;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.enrollment.krms.termresolver.BestEffortCreditLoadTermResolver;
import org.kuali.student.enrollment.krms.termresolver.CluId2CluInfoTermResolver;
import org.kuali.student.enrollment.krms.termresolver.CluId2CluVersionIndIdTermResolver;
import org.kuali.student.enrollment.rules.credit.limit.CourseRegistrationServiceTypeStateConstants;
import org.kuali.student.lum.lrc.service.util.MockLrcTestDataLoader;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.common.krms.data.KRMSEnrollmentEligibilityDataLoader;
import org.kuali.student.r2.common.util.constants.AcademicRecordServiceConstants;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.class1.organization.service.impl.OrgTestDataLoader;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.kuali.student.r2.core.population.dao.PopulationRuleDao;
import org.kuali.student.r2.core.population.model.PopulationRuleEntity;
import org.kuali.student.r2.core.population.service.impl.PopulationServiceImpl;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:krms-test-with-mocks-context.xml"})
public class TestTermResolvers extends AbstractTermResolverTestHelper {

    public ContextInfo contextInfo = null;
    Map<String, Object> resolvedPrereqs = null;
    Map<String, String> parameters = null;
    private static boolean notSetup = true;
    private static boolean initialized = false;

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
    private PopulationServiceImpl populationService;

    @Resource(name = "lrcService")
    private LRCService lrcService;

    @Resource(name = "gesService")
    private GesService gesService;

    @Resource(name="gesServiceDataLoader")
    private AbstractMockServicesAwareDataLoader gesServiceDataLoader;

    @Resource(name="studentToCourseRecordsMap")
    @SuppressWarnings("all")
    private Map<String, List<StudentCourseRecordInfo>> studentToCourseRecordsMap;

    @Resource(name = "ruleManagementService")
    private RuleManagementService ruleManagementService;

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

                //Add a rule for a course with id "COCustomRule"
                try{
                    if(!initialized){
                        RuleManagementServiceMockImpl ruleManagementServiceMock = (RuleManagementServiceMockImpl)ruleManagementService;
                        AgendaDefinition agendaDefinition = ruleManagementServiceMock.createAgenda(AgendaDefinition.Builder.create(null,"Agenda1","10003","10000").build());
                        ReferenceObjectBinding referenceObjectBinding =  ReferenceObjectBinding.Builder.create("Agenda",agendaDefinition.getId(),"KS","http://student.kuali.org/wsdl/courseOffering/CourseOfferingInfo","COCustomRule").build();
                        ruleManagementServiceMock.createReferenceObjectBinding(referenceObjectBinding);
                        initialized = true;
                    }
                }catch(Exception e){
                    throw new RuntimeException("Errors!",e);
                }
                gesServiceDataLoader.beforeTest();

                notSetup = false;
            }

            resolvedPrereqs = getDefaultPrerequisites();
            parameters = getDefaultParameters();

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
        resolvedPrereqs.put(RulesExecutionConstants.PERSON_ID_TERM.getName(), KRMSEnrollmentEligibilityDataLoader.STUDENT_THREE_ID);
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
        resolvedPrereqs.put(RulesExecutionConstants.WAITLISTED_ATTEMPTS_TERM.getName(), 0);
        resolvedPrereqs.put(RulesExecutionConstants.SIMULATED_ATTEMPTS_TERM.getName(), 0);

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                RulesExecutionConstants.TOTAL_COURSE_ATTEMPTS_TERM.getName());

        //Evaluate Term Resolver
        Integer registeredCount = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(registeredCount);
        assertEquals(3, registeredCount.intValue());

        //Add in waitlisted courses
        resolvedPrereqs.put(RulesExecutionConstants.WAITLISTED_ATTEMPTS_TERM.getName(), 2);
        registeredCount = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(registeredCount);
        assertEquals(5, registeredCount.intValue());

        //Add in simulated courses
        //Add in waitlisted courses
        resolvedPrereqs.put(RulesExecutionConstants.SIMULATED_ATTEMPTS_TERM.getName(), 1);
        registeredCount = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(registeredCount);
        assertEquals(6, registeredCount.intValue());
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
        assertEquals(2, value.getDecimalValue().intValue());
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
        assertEquals(2, value.intValue());
    }

    @Test
    public void testMaxRepeatabilityTermResolver() throws Exception {
        //Setup the term resolver
        MaxRepeatabilityTermResolver termResolver = new MaxRepeatabilityTermResolver();
        termResolver.setGesService(gesService);

        //Setup prerequisites
        resolvedPrereqs.put(RulesExecutionConstants.CONTEXT_INFO_TERM.getName(), contextInfo);
        resolvedPrereqs.put(RulesExecutionConstants.PERSON_ID_TERM.getName(), "kuali.population.student.key.everyone100000077");
        resolvedPrereqs.put(RulesExecutionConstants.ATP_ID_TERM.getName(), dataLoader.getFallTermId());
        resolvedPrereqs.put(RulesExecutionConstants.AS_OF_DATE_TERM.getName(), KRMSEnrollmentEligibilityDataLoader.START_FALL_TERM_DATE);

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                RulesExecutionConstants.MAX_REPEATABILITY_TERM.getName());

        //Evaluate term Resolver
        Integer value = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(value);
        assertEquals(2, value.intValue());
    }

    @Test
    public void testCourseRepeatabilityTermResolver() throws Exception {
        //Setup the term resolver
        CourseRepeatabilityTermResolver termResolver = new CourseRepeatabilityTermResolver();
        termResolver.setRuleManagementService(ruleManagementService);

        //Setup prerequisites
        resolvedPrereqs.put(RulesExecutionConstants.CONTEXT_INFO_TERM.getName(), contextInfo);
        resolvedPrereqs.put(RulesExecutionConstants.PERSON_ID_TERM.getName(), "kuali.population.student.key.everyone100000077");
        resolvedPrereqs.put(RulesExecutionConstants.ATP_ID_TERM.getName(), dataLoader.getFallTermId());
        resolvedPrereqs.put(RulesExecutionConstants.AS_OF_DATE_TERM.getName(), KRMSEnrollmentEligibilityDataLoader.START_FALL_TERM_DATE);
        resolvedPrereqs.put(RulesExecutionConstants.MAX_REPEATABILITY_TERM.getName(), 2);
        resolvedPrereqs.put(RulesExecutionConstants.TOTAL_COURSE_ATTEMPTS_TERM.getName(), 2);
        resolvedPrereqs.put(RulesExecutionConstants.REGISTRATION_GROUP_TERM.getName(), new RegistrationGroupInfo());

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSServiceConstants.TERM_RESOLVER_COURSE_REPEATABILITY);

        String value;

        //Evaluate term Resolver for error
        value = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(value);
        assertEquals(CourseRepeatabilityTermResolver.MAX_REPEATABILITY_ERROR, value);

        //Evaluate term Resolver for warning
        resolvedPrereqs.put(RulesExecutionConstants.TOTAL_COURSE_ATTEMPTS_TERM.getName(), 1);
        value = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(value);
        assertEquals(CourseRepeatabilityTermResolver.MAX_REPEATABILITY_WARNING, value);

        //Evaluate term Resolver for success
        resolvedPrereqs.put(RulesExecutionConstants.TOTAL_COURSE_ATTEMPTS_TERM.getName(), 0);
        value = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(value);
        assertEquals(CourseRepeatabilityTermResolver.MAX_REPEATABILITY_SUCCESS, value);

        //Evaluate term Resolver for error, but the CO in the RG should have a custom rule
        //Set the CO to an ID that has a custom Repeatability rule on it
        RegistrationGroupInfo regGroup = new RegistrationGroupInfo();
        regGroup.setCourseOfferingId("COCustomRule");
        resolvedPrereqs.put(RulesExecutionConstants.REGISTRATION_GROUP_TERM.getName(), regGroup);
        resolvedPrereqs.put(RulesExecutionConstants.MAX_REPEATABILITY_TERM.getName(), 1);
        resolvedPrereqs.put(RulesExecutionConstants.TOTAL_COURSE_ATTEMPTS_TERM.getName(), 2);

        value = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(value);
        assertEquals(CourseRepeatabilityTermResolver.MAX_REPEATABILITY_SUCCESS, value);
    }

    @Test
    public void testMaxCreditsTermResolver() throws Exception {
        //Setup the term resolver
        MaxCreditsTermResolver termResolver = new MaxCreditsTermResolver();
        termResolver.setGesService(gesService);

        //Setup prerequisites
        resolvedPrereqs.put(RulesExecutionConstants.CONTEXT_INFO_TERM.getName(), contextInfo);
        resolvedPrereqs.put(RulesExecutionConstants.PERSON_ID_TERM.getName(), "kuali.population.student.key.everyone100000077");
        resolvedPrereqs.put(RulesExecutionConstants.ATP_ID_TERM.getName(), dataLoader.getFallTermId());
        resolvedPrereqs.put(RulesExecutionConstants.AS_OF_DATE_TERM.getName(), KRMSEnrollmentEligibilityDataLoader.START_FALL_TERM_DATE);

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                RulesExecutionConstants.MAX_CREDITS_TERM.getName());

        //Evaluate term Resolver
        Float maxCredits = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(maxCredits);
        assertEquals(new Float(20), maxCredits);

        //Evaluate for a term that is not set up
        resolvedPrereqs.put(RulesExecutionConstants.ATP_ID_TERM.getName(), dataLoader.getWinterTermId());
        maxCredits = termResolver.resolve(resolvedPrereqs, parameters);
        assertNotNull(maxCredits);
        assertEquals(new Float(-1), maxCredits);
    }

    @Test
    public void testBestEffortCreditLoadTermResolver() throws Exception {
        //Setup the term resolver
        BestEffortCreditLoadTermResolver termResolver = new BestEffortCreditLoadTermResolver();

        //Mock a reg group
        String mockRegGroup01 = "mock-reg-group-01";
        RegistrationGroupInfo registrationGroupInfo = mock(RegistrationGroupInfo.class);
        when(registrationGroupInfo.getId()).thenReturn(mockRegGroup01);

        //Mock a registration request item
        RegistrationRequestItemInfo requestItemInfo = mock(RegistrationRequestItemInfo.class);
        when(requestItemInfo.getTypeKey()).thenReturn(CourseRegistrationServiceTypeStateConstants.REQ_ITEM_ADD_TYPE_KEY);
        when(requestItemInfo.getCredits()).thenReturn(new KualiDecimal(3));
        when(requestItemInfo.getRegistrationGroupId()).thenReturn(mockRegGroup01);

        List<CourseRegistrationInfo> existingRegistrations = new ArrayList<>();
        List<CourseRegistrationInfo> waitlistedRegistrations = new ArrayList<>();
        List<CourseRegistrationInfo> simulatedRegistrations = new ArrayList<>();

        //Mock some course registrations
        String mockCRID01 = "mock-cr-01";
        CourseRegistrationInfo cr01 = new CourseRegistrationInfo();
        CourseRegistrationInfo cr02 = new CourseRegistrationInfo();
        CourseRegistrationInfo cr03 = new CourseRegistrationInfo();
        cr01.setId(mockCRID01);
        cr01.setCredits(new KualiDecimal(6));
        cr02.setCredits(new KualiDecimal(3));
        cr03.setCredits(new KualiDecimal(2));

        //Setup prerequisites
        resolvedPrereqs.put(RulesExecutionConstants.CONTEXT_INFO_TERM.getName(), contextInfo);
        resolvedPrereqs.put(RulesExecutionConstants.REGISTRATION_REQUEST_ITEM_TERM.getName(), requestItemInfo);
        resolvedPrereqs.put(RulesExecutionConstants.EXISTING_REGISTRATIONS_TERM.getName(), existingRegistrations);
        resolvedPrereqs.put(RulesExecutionConstants.EXISTING_WAITLISTED_REGISTRATIONS_TERM.getName(), waitlistedRegistrations);
        resolvedPrereqs.put(RulesExecutionConstants.SIMULATED_REGISTRATIONS_TERM.getName(), simulatedRegistrations);
        resolvedPrereqs.put(RulesExecutionConstants.MAX_CREDITS_TERM.getName(), 8f);

        //Validate the term resolver
        validateTermResolver(termResolver, resolvedPrereqs, parameters,
                KSKRMSServiceConstants.TERM_RESOLVER_BEST_EFFORT_CREDIT_LOAD);

        //Evaluate term Resolver
        Boolean creditLoadOkay = termResolver.resolve(resolvedPrereqs, parameters);
        assertTrue(creditLoadOkay);

        //Increase the credits on the added item
        when(requestItemInfo.getCredits()).thenReturn(new KualiDecimal(9));
        creditLoadOkay = termResolver.resolve(resolvedPrereqs, parameters);
        assertFalse(creditLoadOkay);

        /*
        Adding 3 credit course
        One 6 credit course is registered
        Max credits allowed: 8
        */
        when(requestItemInfo.getCredits()).thenReturn(new KualiDecimal(3));
        existingRegistrations.add(cr01);
        creditLoadOkay = termResolver.resolve(resolvedPrereqs, parameters);
        assertFalse(creditLoadOkay);

        /*
        Adding 3 credit course
        One 3 credit course is registered
        One 3 credit course is waitlisted
        Max credits allowed: 8
        */
        cr01.setCredits(new KualiDecimal(3));
        waitlistedRegistrations.add(cr02);
        creditLoadOkay = termResolver.resolve(resolvedPrereqs, parameters);
        assertFalse(creditLoadOkay);

        /*
        Adding 3 credit course
        One 3 credit course is registered
        One 3 credit course is waitlisted
        Max credits allowed: 8
        countWaitlistedCoursesTowardsCreditLimit is set to false
        */
        termResolver.setCountWaitlistedCoursesTowardsCreditLimit(false);
        creditLoadOkay = termResolver.resolve(resolvedPrereqs, parameters);
        assertTrue(creditLoadOkay);

        /*
        Adding 3 credit course
        One 2 credit course is registered
        One 2 credit course is waitlisted
        One 2 credit course is simulated
        Max credits allowed: 8
        */
        cr01.setCredits(new KualiDecimal(2));
        cr02.setCredits(new KualiDecimal(2));
        simulatedRegistrations.add(cr03);
        termResolver.setCountWaitlistedCoursesTowardsCreditLimit(true);
        creditLoadOkay = termResolver.resolve(resolvedPrereqs, parameters);
        assertFalse(creditLoadOkay);

        /*
        One 2 credit course is registered
        One 3 credit course is waitlisted
        One 3 credit course is simulated
        Max credits allowed: 8
        Updating the registered course to 3 credits
        */
        cr02.setCredits(new KualiDecimal(3));
        cr03.setCredits(new KualiDecimal(3));
        when(requestItemInfo.getExistingCourseRegistrationId()).thenReturn(mockCRID01);
        when(requestItemInfo.getTypeKey()).thenReturn(CourseRegistrationServiceTypeStateConstants.REQ_ITEM_UPDATE_TYPE_KEY);
        creditLoadOkay = termResolver.resolve(resolvedPrereqs, parameters);
        assertFalse(creditLoadOkay);

        /*
        One 2 credit course is registered
        One 3 credit course is waitlisted
        One 3 credit course is simulated
        Max credits allowed: 8
        Updating the registered course to 1 credits
        */
        when(requestItemInfo.getCredits()).thenReturn(new KualiDecimal(1));
        creditLoadOkay = termResolver.resolve(resolvedPrereqs, parameters);
        assertTrue(creditLoadOkay);

        /*
        Validate "no credit limit" functionality by registering for a ridiculous
        amount of credits
         */
        resolvedPrereqs.put(RulesExecutionConstants.MAX_CREDITS_TERM.getName(), BestEffortCreditLoadTermResolver.NO_CREDIT_LIMIT);
        when(requestItemInfo.getTypeKey()).thenReturn(CourseRegistrationServiceTypeStateConstants.REQ_ITEM_ADD_TYPE_KEY);
        when(requestItemInfo.getCredits()).thenReturn(new KualiDecimal(100));
        cr01.setCredits(new KualiDecimal(100));
        cr02.setCredits(new KualiDecimal(100));
        cr03.setCredits(new KualiDecimal(100));
        creditLoadOkay = termResolver.resolve(resolvedPrereqs, parameters);
        assertTrue(creditLoadOkay);

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
        PopulationRuleDao populationRuleDaoMock = mock(PopulationRuleDao.class);
        when(populationRuleDaoMock.getPopulationRuleByPopulationId(anyString())).thenReturn(new PopulationRuleEntity());
        populationService.setPopulationRuleDao(populationRuleDaoMock);
        populationService.getMembersAsOfDate("SENIOR_ONLY_STUDENTS", new Date(), contextInfo);
        populationService.getMembersAsOfDate("kuali.population.student.key.everyone", new Date(), contextInfo);
    }

    private void loadOrgData(){
        OrgTestDataLoader orgDataLoader = new OrgTestDataLoader(organizationService);
        orgDataLoader.loadData();
    }

}

