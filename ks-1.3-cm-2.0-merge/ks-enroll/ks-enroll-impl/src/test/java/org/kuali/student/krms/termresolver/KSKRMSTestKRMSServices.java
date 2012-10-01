package org.kuali.student.krms.termresolver;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.rice.krms.api.repository.context.ContextDefinition;
import org.kuali.rice.krms.api.repository.term.TermResolverDefinition;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.krms.KSKRMSTestCase;
import org.kuali.student.krms.util.KSKRMSExecutionConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Map;

@Ignore
public class KSKRMSTestKRMSServices extends KSKRMSTestCase {

	org.kuali.student.enrollment.class2.acal.service.assembler.AcademicCalendarAssembler acalAssembler;
	AcademicCalendarService acalService;
	KSTermResolverTypeService ksKRMSTermResolverTypeService;

    @Override
    protected String getAdditionalSpringFile(){
        return "ks-krms-test-context.xml";
    }

    protected ContextDefinition getKRMSContext(String context) {
        return null;
    }

	// @Test
	public void testThis() {
		ContextDefinition ctxDev = getKRMSContext(KSKRMSConstants.CONTEXT_STUD_ELIGIBILITY);
		System.out.println(ctxDev.getName());
	}

	@Before
	public void setupTypeService() {
		ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(
		        new String[] {"ckasspath:ks-krms-test-context.xml"});
		BeanFactory factory = (BeanFactory) appContext;
		ksKRMSTermResolverTypeService = (KSTermResolverTypeService) factory.getBean("ksKRMSTermResolverTypeService");
	}

//	@Test
	public void testBeans() {
//		ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(
//		        new String[] {"ks-krms-test-context.xml"});
		// of course, an ApplicationContext is just a BeanFactory
//		BeanFactory factory = (BeanFactory) appContext;
//		acalAssembler = (org.kuali.student.enrollment.class2.acal.service.assembler.AcademicCalendarAssembler) factory.getBean("acalAssembler");
//		Object bean = factory.getBean("acalService");
//		acalService = (org.kuali.student.enrollment.acal.service.AcademicCalendarService) bean;



		TermResolverDefinition termResolver = krmsTermResolverLookup(KSKRMSConstants.TERM_SPEC_RESOLVER_COMPLETED_COURSE);
		if (ksKRMSTermResolverTypeService != null) {
			TermResolver<CompletedCourseTermResolver> termResolverInstance = (TermResolver<CompletedCourseTermResolver>) ksKRMSTermResolverTypeService.loadTermResolver(termResolver);

			Map<String, String> parameters = new HashMap<String, String>();
		    parameters.put(KSKRMSExecutionConstants.PERSON_ID_TERM_PROPERTY, "1234101");
		    parameters.put(KSKRMSExecutionConstants.COURSE_CODE_TERM_PROPERTY, "100");

			termResolverInstance.resolve(setupTermResolverResolvedPrereqs(), parameters);
		}
	}

	@Test
	public void testCompletedCourseTermResolver() {
		TermResolverDefinition termResolver = krmsTermResolverLookup(KSKRMSConstants.TERM_SPEC_RESOLVER_COMPLETED_COURSES);
		if (ksKRMSTermResolverTypeService != null) {
			TermResolver<CompletedCoursesTermResolver> termResolverInstance = (TermResolver<CompletedCoursesTermResolver>) ksKRMSTermResolverTypeService.loadTermResolver(termResolver);

			Map<String, String> parameters = new HashMap<String, String>();
		    parameters.put(KSKRMSExecutionConstants.PERSON_ID_TERM_PROPERTY, "1234102");

			termResolverInstance.resolve(setupTermResolverResolvedPrereqs(), parameters);
		}
	}

	@Test
	public void testCompletedCoursesTermResolver() {
		TermResolverDefinition termResolver = krmsTermResolverLookup(KSKRMSConstants.TERM_SPEC_RESOLVER_COMPLETED_COURSES);
		if (ksKRMSTermResolverTypeService != null) {
			TermResolver<CompletedCoursesTermResolver> termResolverInstance = (TermResolver<CompletedCoursesTermResolver>) ksKRMSTermResolverTypeService.loadTermResolver(termResolver);

			Map<String, String> parameters = new HashMap<String, String>();
		    parameters.put(KSKRMSExecutionConstants.PERSON_ID_TERM_PROPERTY, "1234103");

			termResolverInstance.resolve(setupTermResolverResolvedPrereqs(), parameters);
		}
	}

	@Test
	public void testEnrolledCourseByTermTermResolver() {
		TermResolverDefinition termResolver = krmsTermResolverLookup(KSKRMSConstants.TERM_SPEC_RESOLVER_ENROLLED_COURSE_BY_TERM);
		if (ksKRMSTermResolverTypeService != null) {
			TermResolver<EnrolledCourseByTermTermResolver> termResolverInstance = (TermResolver<EnrolledCourseByTermTermResolver>) ksKRMSTermResolverTypeService.loadTermResolver(termResolver);

			Map<String, String> parameters = new HashMap<String, String>();
		    parameters.put(KSKRMSExecutionConstants.PERSON_ID_TERM_PROPERTY, "1234104");
		    parameters.put(KSKRMSExecutionConstants.TERM_ID_TERM_PROPERTY, "101");

			termResolverInstance.resolve(setupTermResolverResolvedPrereqs(), parameters);
		}
	}

	@Test
	public void testEnrolledCoursesByTermTermResolver() {
		TermResolverDefinition termResolver = krmsTermResolverLookup(KSKRMSConstants.TERM_SPEC_RESOLVER_ENROLLED_COURSES_BY_TERM);
		if (ksKRMSTermResolverTypeService != null) {
			TermResolver<EnrolledCoursesByTermTermResolver> termResolverInstance = (TermResolver<EnrolledCoursesByTermTermResolver>) ksKRMSTermResolverTypeService.loadTermResolver(termResolver);

			Map<String, String> parameters = new HashMap<String, String>();
		    parameters.put(KSKRMSExecutionConstants.PERSON_ID_TERM_PROPERTY, "1234105");
		    parameters.put(KSKRMSExecutionConstants.TERM_ID_TERM_PROPERTY, "102");

			termResolverInstance.resolve(setupTermResolverResolvedPrereqs(), parameters);
		}
	}

	@Test
	public void testCourseNumberRangeTermResolver() {
		TermResolverDefinition termResolver = krmsTermResolverLookup(KSKRMSConstants.TERM_SPEC_RESOLVER_COURSE_NUMBER_RANGE);
		if (ksKRMSTermResolverTypeService != null) {
			TermResolver<CourseNumberRangeTermResolver> termResolverInstance = (TermResolver<CourseNumberRangeTermResolver>) ksKRMSTermResolverTypeService.loadTermResolver(termResolver);

			Map<String, String> parameters = new HashMap<String, String>();

			termResolverInstance.resolve(setupTermResolverResolvedPrereqs(), parameters);
		}
	}

    @Test
	public void testSubjectCodeTermResolver() {
		TermResolverDefinition termResolver = krmsTermResolverLookup(KSKRMSConstants.TERM_SPEC_RESOLVER_SUBJECT_CODE);
		if (ksKRMSTermResolverTypeService != null) {
			TermResolver<SubjectCodeTermResolver> termResolverInstance = (TermResolver<SubjectCodeTermResolver>) ksKRMSTermResolverTypeService.loadTermResolver(termResolver);

			Map<String, String> parameters = new HashMap<String, String>();

			termResolverInstance.resolve(setupTermResolverResolvedPrereqs(), parameters);
		}
	}

	@Test
	public void testCourseSetTermResolver() {
		TermResolverDefinition termResolver = krmsTermResolverLookup(KSKRMSConstants.TERM_SPEC_RESOLVER_COURSE_SET);
		if (ksKRMSTermResolverTypeService != null) {
			TermResolver<CourseSetTermResolver> termResolverInstance = (TermResolver<CourseSetTermResolver>) ksKRMSTermResolverTypeService.loadTermResolver(termResolver);

			Map<String, String> parameters = new HashMap<String, String>();

			termResolverInstance.resolve(setupTermResolverResolvedPrereqs(), parameters);
		}
	}

	@Test
	public void testEffectioveDateFromTermResolver() {
		TermResolverDefinition termResolver = krmsTermResolverLookup(KSKRMSConstants.TERM_SPEC_RESOLVER_EFFECTIVE_DATE_FROM);
		if (ksKRMSTermResolverTypeService != null) {
			TermResolver<EffectiveDateFromTermResolver> termResolverInstance = (TermResolver<EffectiveDateFromTermResolver>) ksKRMSTermResolverTypeService.loadTermResolver(termResolver);

			Map<String, String> parameters = new HashMap<String, String>();

			termResolverInstance.resolve(setupTermResolverResolvedPrereqs(), parameters);
		}
	}

	@Test
	public void testEffectioveDateToTermResolver() {
		TermResolverDefinition termResolver = krmsTermResolverLookup(KSKRMSConstants.TERM_SPEC_RESOLVER_EFFECTIVE_DATE_TO);
		if (ksKRMSTermResolverTypeService != null) {
			TermResolver<EffectiveDateToTermResolver> termResolverInstance = (TermResolver<EffectiveDateToTermResolver>) ksKRMSTermResolverTypeService.loadTermResolver(termResolver);

			Map<String, String> parameters = new HashMap<String, String>();

			termResolverInstance.resolve(setupTermResolverResolvedPrereqs(), parameters);
		}
	}

	@Test
	public void testFreeTextTermResolver() {
		TermResolverDefinition termResolver = krmsTermResolverLookup(KSKRMSConstants.TERM_SPEC_RESOLVER_FREE_TEXT);
		if (ksKRMSTermResolverTypeService != null) {
			TermResolver<FreeTextTermResolver> termResolverInstance = (TermResolver<FreeTextTermResolver>) ksKRMSTermResolverTypeService.loadTermResolver(termResolver);

			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put(KSKRMSExecutionConstants.PERSON_ID_TERM_PROPERTY, "1234106");

			termResolverInstance.resolve(setupTermResolverResolvedPrereqs(), parameters);
		}
	}

	@Test
	public void testGPATermResolver() {
		TermResolverDefinition termResolver = krmsTermResolverLookup(KSKRMSConstants.TERM_SPEC_GPA);
		if (ksKRMSTermResolverTypeService != null) {
			TermResolver<GPATermResolver> termResolverInstance = (TermResolver<GPATermResolver>) ksKRMSTermResolverTypeService.loadTermResolver(termResolver);

			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put(KSKRMSExecutionConstants.PERSON_ID_TERM_PROPERTY, "1234107");
			parameters.put(KSKRMSExecutionConstants.CALC_TYPE_KEY_TERM_PROPERTY, "1000");

			termResolverInstance.resolve(setupTermResolverResolvedPrereqs(), parameters);
		}
	}

	@Test
	public void testGradeTermResolver() {
		TermResolverDefinition termResolver = krmsTermResolverLookup(KSKRMSConstants.TERM_SPEC_GRADE);
		if (ksKRMSTermResolverTypeService != null) {
			TermResolver<GradeTermResolver> termResolverInstance = (TermResolver<GradeTermResolver>) ksKRMSTermResolverTypeService.loadTermResolver(termResolver);

			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put(KSKRMSExecutionConstants.PERSON_ID_TERM_PROPERTY, "1234108");

			termResolverInstance.resolve(setupTermResolverResolvedPrereqs(), parameters);
		}
	}

	@Test
	public void testGradeTypeTermResolver() {
		TermResolverDefinition termResolver = krmsTermResolverLookup(KSKRMSConstants.TERM_SPEC_GRADE_TYPE);
		if (ksKRMSTermResolverTypeService != null) {
			TermResolver<GradeTypeTermResolver> termResolverInstance = (TermResolver<GradeTypeTermResolver>) ksKRMSTermResolverTypeService.loadTermResolver(termResolver);

			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put(KSKRMSExecutionConstants.PERSON_ID_TERM_PROPERTY, "1234109");

			termResolverInstance.resolve(setupTermResolverResolvedPrereqs(), parameters);
		}
	}

	@Test
	public void testLearningObjectivesTermResolver() {
		TermResolverDefinition termResolver = krmsTermResolverLookup(KSKRMSConstants.TERM_SPEC_RESOLVER_LEARNING_OBJECTIVES);
		if (ksKRMSTermResolverTypeService != null) {
			TermResolver<LearningObjectivesTermResolver> termResolverInstance = (TermResolver<LearningObjectivesTermResolver>) ksKRMSTermResolverTypeService.loadTermResolver(termResolver);

			Map<String, String> parameters = new HashMap<String, String>();

			termResolverInstance.resolve(setupTermResolverResolvedPrereqs(), parameters);
		}
	}

	@Test
	public void testNumberOfCoursesTermResolver() {
		TermResolverDefinition termResolver = krmsTermResolverLookup(KSKRMSConstants.TERM_SPEC_RESOLVER_NUMBER_OF_COURSES);
		if (ksKRMSTermResolverTypeService != null) {
			TermResolver<NumberOfCoursesTermResolver> termResolverInstance = (TermResolver<NumberOfCoursesTermResolver>) ksKRMSTermResolverTypeService.loadTermResolver(termResolver);

			Map<String, String> parameters = new HashMap<String, String>();

			termResolverInstance.resolve(setupTermResolverResolvedPrereqs(), parameters);
		}
	}

	@Test
	public void testNumberOfCreditsTermResolver() {
		TermResolverDefinition termResolver = krmsTermResolverLookup(KSKRMSConstants.TERM_SPEC_RESOLVER_NUMBER_OF_CREDITS);
		if (ksKRMSTermResolverTypeService != null) {
			TermResolver<NumberOfCreditsTermResolver> termResolverInstance = (TermResolver<NumberOfCreditsTermResolver>) ksKRMSTermResolverTypeService.loadTermResolver(termResolver);

			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put(KSKRMSExecutionConstants.PERSON_ID_TERM_PROPERTY, "1234110");
		    parameters.put(KSKRMSExecutionConstants.TERM_ID_TERM_PROPERTY, "108");

			termResolverInstance.resolve(setupTermResolverResolvedPrereqs(), parameters);
		}
	}

	@Test
	public void testScoreTermResolver() {
		TermResolverDefinition termResolver = krmsTermResolverLookup(KSKRMSConstants.TERM_SPEC_RESOLVER_SCORE);
		if (ksKRMSTermResolverTypeService != null) {
			TermResolver<ScoreTermResolver> termResolverInstance = (TermResolver<ScoreTermResolver>) ksKRMSTermResolverTypeService.loadTermResolver(termResolver);

			Map<String, String> parameters = new HashMap<String, String>();

			termResolverInstance.resolve(setupTermResolverResolvedPrereqs(), parameters);
		}
	}

	@Test
	public void testTestTermResolver() {
		TermResolverDefinition termResolver = krmsTermResolverLookup(KSKRMSConstants.TERM_SPEC_RESOLVER_TEST);
		if (ksKRMSTermResolverTypeService != null) {
			TermResolver<TestTermResolver> termResolverInstance = (TermResolver<TestTermResolver>) ksKRMSTermResolverTypeService.loadTermResolver(termResolver);

			Map<String, String> parameters = new HashMap<String, String>();

			termResolverInstance.resolve(setupTermResolverResolvedPrereqs(), parameters);
		}
	}

	@Test
	public void testDeptNumberTermResolver() {
		TermResolverDefinition termResolver = krmsTermResolverLookup(KSKRMSConstants.TERM_SPEC_RESOLVER_DEPT_NUMBER);
		if (ksKRMSTermResolverTypeService != null) {
			TermResolver<DeptNumberTermResolver> termResolverInstance = (TermResolver<DeptNumberTermResolver>) ksKRMSTermResolverTypeService.loadTermResolver(termResolver);

			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put(KSKRMSExecutionConstants.PERSON_ID_TERM_PROPERTY, "1234111");

			termResolverInstance.resolve(setupTermResolverResolvedPrereqs(), parameters);
		}
	}

	@Test
	public void testAdminOrgNumberTermResolver() {
		TermResolverDefinition termResolver = krmsTermResolverLookup(KSKRMSConstants.TERM_SPEC_RESOLVER_ADMIN_ORG_NUMBER);
		if (ksKRMSTermResolverTypeService != null) {
			TermResolver<AdminOrgNumberTermResolver> termResolverInstance = (TermResolver<AdminOrgNumberTermResolver>) ksKRMSTermResolverTypeService.loadTermResolver(termResolver);

			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put(KSKRMSExecutionConstants.PERSON_ID_TERM_PROPERTY, "1234112");

			termResolverInstance.resolve(setupTermResolverResolvedPrereqs(), parameters);
		}
	}

	@Test
	public void testCompletedCourseNumberTermResolver() {
		TermResolverDefinition termResolver = krmsTermResolverLookup(KSKRMSConstants.TERM_SPEC_RESOLVER_COMPLETED_COURSE_NUMBER);
		if (ksKRMSTermResolverTypeService != null) {
			TermResolver<CompletedCourseNumberTermResolver> termResolverInstance = (TermResolver<CompletedCourseNumberTermResolver>) ksKRMSTermResolverTypeService.loadTermResolver(termResolver);

			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put(KSKRMSExecutionConstants.PERSON_ID_TERM_PROPERTY, "1234113");

			termResolverInstance.resolve(setupTermResolverResolvedPrereqs(), parameters);
		}
	}

	@Test
	public void testCompletedCourseCodeTermResolver() {
		TermResolverDefinition termResolver = krmsTermResolverLookup(KSKRMSConstants.TERM_SPEC_RESOLVER_COMPLETED_COURSE_CODE);
		if (ksKRMSTermResolverTypeService != null) {
			TermResolver<CompletedCourseCodeTermResolver> termResolverInstance = (TermResolver<CompletedCourseCodeTermResolver>) ksKRMSTermResolverTypeService.loadTermResolver(termResolver);

			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put(KSKRMSExecutionConstants.PERSON_ID_TERM_PROPERTY, "1234114");

			termResolverInstance.resolve(setupTermResolverResolvedPrereqs(), parameters);
		}
	}

	@Test
	public void testCompletedCourseSetTermResolver() {
		TermResolverDefinition termResolver = krmsTermResolverLookup(KSKRMSConstants.TERM_SPEC_RESOLVER_COMPLETED_COURSE_SET);
		if (ksKRMSTermResolverTypeService != null) {
			TermResolver<CompletedCourseSetTermResolver> termResolverInstance = (TermResolver<CompletedCourseSetTermResolver>) ksKRMSTermResolverTypeService.loadTermResolver(termResolver);

			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put(KSKRMSExecutionConstants.PERSON_ID_TERM_PROPERTY, "1234115");

			termResolverInstance.resolve(setupTermResolverResolvedPrereqs(), parameters);
		}
	}

	@Test
	public void testCompletedEffectiveDateFromTermResolver() {
		TermResolverDefinition termResolver = krmsTermResolverLookup(KSKRMSConstants.TERM_SPEC_RESOLVER_COMPLETED_EFFECTIVE_DATE_FROM);
		if (ksKRMSTermResolverTypeService != null) {
			TermResolver<CompletedEffectiveDateFromTermResolver> termResolverInstance = (TermResolver<CompletedEffectiveDateFromTermResolver>) ksKRMSTermResolverTypeService.loadTermResolver(termResolver);

			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put(KSKRMSExecutionConstants.PERSON_ID_TERM_PROPERTY, "1234116");

			termResolverInstance.resolve(setupTermResolverResolvedPrereqs(), parameters);
		}
	}

	@Test
	public void testCompletedEffectiveDateToTermResolver() {
		TermResolverDefinition termResolver = krmsTermResolverLookup(KSKRMSConstants.TERM_SPEC_RESOLVER_COMPLETED_EFFECTIVE_DATE_TO);
		if (ksKRMSTermResolverTypeService != null) {
			TermResolver<CompletedEffectiveDateToTermResolver> termResolverInstance = (TermResolver<CompletedEffectiveDateToTermResolver>) ksKRMSTermResolverTypeService.loadTermResolver(termResolver);

			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put(KSKRMSExecutionConstants.PERSON_ID_TERM_PROPERTY, "1234117");

			termResolverInstance.resolve(setupTermResolverResolvedPrereqs(), parameters);
		}
	}

	@Test
	public void testCompletedLearningObjectivesTermResolver() {
		TermResolverDefinition termResolver = krmsTermResolverLookup(KSKRMSConstants.TERM_SPEC_RESOLVER_COMPLETED_LEARNING_OBJ_DESCR);
		if (ksKRMSTermResolverTypeService != null) {
			TermResolver<CompletedLearningObjectivesTermResolver> termResolverInstance = (TermResolver<CompletedLearningObjectivesTermResolver>) ksKRMSTermResolverTypeService.loadTermResolver(termResolver);

			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put(KSKRMSExecutionConstants.PERSON_ID_TERM_PROPERTY, "1234118");

			termResolverInstance.resolve(setupTermResolverResolvedPrereqs(), parameters);
		}
	}

	@Test
	public void testEnrolledCourseTermResolver() {
		TermResolverDefinition termResolver = krmsTermResolverLookup(KSKRMSConstants.TERM_SPEC_RESOLVER_ENROLLED_COURSE);
		if (ksKRMSTermResolverTypeService != null) {
			TermResolver<EnrolledCourseTermResolver> termResolverInstance = (TermResolver<EnrolledCourseTermResolver>) ksKRMSTermResolverTypeService.loadTermResolver(termResolver);

			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put(KSKRMSExecutionConstants.PERSON_ID_TERM_PROPERTY, "1234119");
			parameters.put(KSKRMSExecutionConstants.TERM_ID_TERM_PROPERTY, "109");

			termResolverInstance.resolve(setupTermResolverResolvedPrereqs(), parameters);
		}
	}

	@Test
	public void testEnrolledCourseNumberTermResolver() {
		TermResolverDefinition termResolver = krmsTermResolverLookup(KSKRMSConstants.TERM_SPEC_RESOLVER_ENROLLED_COURSE_NUMBER_RANGE);
		if (ksKRMSTermResolverTypeService != null) {
			TermResolver<EnrolledCourseNumberTermResolver> termResolverInstance = (TermResolver<EnrolledCourseNumberTermResolver>) ksKRMSTermResolverTypeService.loadTermResolver(termResolver);

			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put(KSKRMSExecutionConstants.PERSON_ID_TERM_PROPERTY, "1234119");
			parameters.put(KSKRMSExecutionConstants.TERM_ID_TERM_PROPERTY, "110");

			termResolverInstance.resolve(setupTermResolverResolvedPrereqs(), parameters);
		}
	}

	@Test
	public void testEnrolledCourseCodeTermResolver() {
		TermResolverDefinition termResolver = krmsTermResolverLookup(KSKRMSConstants.TERM_SPEC_RESOLVER_ENROLLED_COURSE_NUMBER_SUBJECT_CODE);
		if (ksKRMSTermResolverTypeService != null) {
			TermResolver<EnrolledCourseCodeTermResolver> termResolverInstance = (TermResolver<EnrolledCourseCodeTermResolver>) ksKRMSTermResolverTypeService.loadTermResolver(termResolver);

			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put(KSKRMSExecutionConstants.PERSON_ID_TERM_PROPERTY, "1234120");
			parameters.put(KSKRMSExecutionConstants.TERM_ID_TERM_PROPERTY, "111");

			termResolverInstance.resolve(setupTermResolverResolvedPrereqs(), parameters);
		}
	}

	@Test
	public void testEnrolledCourseSetTermResolver() {
		TermResolverDefinition termResolver = krmsTermResolverLookup(KSKRMSConstants.TERM_SPEC_RESOLVER_ENROLLED_SET);
		if (ksKRMSTermResolverTypeService != null) {
			TermResolver<EnrolledCourseSetTermResolver> termResolverInstance = (TermResolver<EnrolledCourseSetTermResolver>) ksKRMSTermResolverTypeService.loadTermResolver(termResolver);
		   			
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put(KSKRMSExecutionConstants.PERSON_ID_TERM_PROPERTY, "1234121");
			parameters.put(KSKRMSExecutionConstants.TERM_ID_TERM_PROPERTY, "112");
			
			termResolverInstance.resolve(setupTermResolverResolvedPrereqs(), parameters);
		}
	}
	
	@Test
	public void testEnrolledCoursesTermResolver() {
		TermResolverDefinition termResolver = krmsTermResolverLookup(KSKRMSConstants.TERM_SPEC_RESOLVER_ENROLLED_COURSES);
		if (ksKRMSTermResolverTypeService != null) {
			TermResolver<EnrolledCoursesTermResolver> termResolverInstance = (TermResolver<EnrolledCoursesTermResolver>) ksKRMSTermResolverTypeService.loadTermResolver(termResolver);
		   			
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put(KSKRMSExecutionConstants.PERSON_ID_TERM_PROPERTY, "1234122");
			parameters.put(KSKRMSExecutionConstants.TERM_ID_TERM_PROPERTY, "113");
			
			termResolverInstance.resolve(setupTermResolverResolvedPrereqs(), parameters);
		}
	}
	
	@Test
	public void testEnrolledEffectiveDateFromTermResolver() {
		TermResolverDefinition termResolver = krmsTermResolverLookup(KSKRMSConstants.TERM_SPEC_RESOLVER_ENROLLED_EFFECTIVE_DATE_FROM);
		if (ksKRMSTermResolverTypeService != null) {
			TermResolver<EnrolledEffectiveDateFromTermResolver> termResolverInstance = (TermResolver<EnrolledEffectiveDateFromTermResolver>) ksKRMSTermResolverTypeService.loadTermResolver(termResolver);
		   			
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put(KSKRMSExecutionConstants.PERSON_ID_TERM_PROPERTY, "1234123");
			parameters.put(KSKRMSExecutionConstants.TERM_ID_TERM_PROPERTY, "114");
			
			termResolverInstance.resolve(setupTermResolverResolvedPrereqs(), parameters);
		}
	}
	
	@Test
	public void testEnrolledEffectiveDateToTermResolver() {
		TermResolverDefinition termResolver = krmsTermResolverLookup(KSKRMSConstants.TERM_SPEC_RESOLVER_ENROLLED_EFFECTIVE_DATE_TO);
		if (ksKRMSTermResolverTypeService != null) {
			TermResolver<EnrolledEffectiveDateToTermResolver> termResolverInstance = (TermResolver<EnrolledEffectiveDateToTermResolver>) ksKRMSTermResolverTypeService.loadTermResolver(termResolver);
		   			
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put(KSKRMSExecutionConstants.PERSON_ID_TERM_PROPERTY, "1234124");
			parameters.put(KSKRMSExecutionConstants.TERM_ID_TERM_PROPERTY, "115");
			
			termResolverInstance.resolve(setupTermResolverResolvedPrereqs(), parameters);
		}
	}
	
	@Test
	public void EnrolledLearningObjectivesTermResolver() {
		TermResolverDefinition termResolver = krmsTermResolverLookup(KSKRMSConstants.TERM_SPEC_RESOLVER_ENROLLED_LEARNING_OBJ_DESCR);
		if (ksKRMSTermResolverTypeService != null) {
			TermResolver<EnrolledLearningObjectivesTermResolver> termResolverInstance = (TermResolver<EnrolledLearningObjectivesTermResolver>) ksKRMSTermResolverTypeService.loadTermResolver(termResolver);
		   			
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put(KSKRMSExecutionConstants.PERSON_ID_TERM_PROPERTY, "1234125");
			parameters.put(KSKRMSExecutionConstants.TERM_ID_TERM_PROPERTY, "116");
			
			termResolverInstance.resolve(setupTermResolverResolvedPrereqs(), parameters);
		}
	}
	
	private Map<String, Object> setupTermResolverResolvedPrereqs() {
		
		Map<String, Object> resolvedPrereqs = new HashMap<String, Object>();
		resolvedPrereqs.put(KSKRMSExecutionConstants.CONTEXT_INFO_TERM_NAME, getEnglishContextInfo() );
		return resolvedPrereqs;
	}
	
    private ContextInfo getEnglishContextInfo() {
        ContextInfo contextInfo = ContextUtils.getContextInfo();
        LocaleInfo localeInfo = contextInfo.getLocale();
        localeInfo.setLocaleLanguage("en");
        return contextInfo;
    }
	
}
