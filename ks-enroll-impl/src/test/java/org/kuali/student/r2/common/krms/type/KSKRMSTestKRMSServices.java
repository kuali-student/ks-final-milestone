package org.kuali.student.r2.common.krms.type;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.rice.krms.api.repository.context.ContextDefinition;
import org.kuali.rice.krms.api.repository.term.TermResolverDefinition;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Map;

@Ignore
public class KSKRMSTestKRMSServices {

	AcademicCalendarService acalService;
	KSTermResolverTypeService ksKRMSTermResolverTypeService;

    //@Override
    //protected String getAdditionalSpringFile(){
    //    return "ks-krms-test-context.xml";
    //}

    protected ContextDefinition getKRMSContext(String context) {
        return null;
    }

    protected TermResolverDefinition krmsTermResolverLookup(String termResolverName) {
        // this may be called more than once, we only want to create one though
        //Map<String, String> queryArgs = new HashMap<String, String>();
        //queryArgs.put("nm", termResolverName);
        //TermResolverBo termBo = getBoService().findByPrimaryKey(TermResolverBo.class, queryArgs);
        //if (termBo != null) {
        //    return TermResolverBo.to(termBo);
        //}
        return null;
    }

	// @Test
	//public void testThis() {
	//	ContextDefinition ctxDev = getKRMSContext(KSKRMSServiceConstants.CONTEXT_STUD_ELIGIBILITY);
	//	System.out.println(ctxDev.getName());
	//}

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



		//TermResolverDefinition termResolver = krmsTermResolverLookup(KSKRMSServiceConstants.TERM_SPEC_RESOLVER_COMPLETED_COURSE);
		if (ksKRMSTermResolverTypeService != null) {
			//TermResolver<CompletedCourseTermResolver> termResolverInstance = (TermResolver<CompletedCourseTermResolver>) ksKRMSTermResolverTypeService.loadTermResolver(termResolver);

			Map<String, String> parameters = new HashMap<String, String>();
		    //parameters.put(KSKRMSServiceConstants.PERSON_ID_TERM_PROPERTY, "1234101");
		    //parameters.put(KSKRMSServiceConstants.COURSE_CODE_TERM_PROPERTY, "100");

			//termResolverInstance.resolve(setupTermResolverResolvedPrereqs(), parameters);
		}
	}

	@Test
	public void testCompletedCourseTermResolver() {
		//TermResolverDefinition termResolver = krmsTermResolverLookup(KSKRMSServiceConstants.TERM_SPEC_RESOLVER_COMPLETED_COURSES);
		if (ksKRMSTermResolverTypeService != null) {
			//TermResolver<CompletedCourseTermResolver> termResolverInstance = (TermResolver<CompletedCourseTermResolver>) ksKRMSTermResolverTypeService.loadTermResolver(termResolver);

			Map<String, String> parameters = new HashMap<String, String>();
		    //parameters.put(KSKRMSServiceConstants.PERSON_ID_TERM_PROPERTY, "1234102");

			//termResolverInstance.resolve(setupTermResolverResolvedPrereqs(), parameters);
		}
	}

	@Test
	public void testFreeTextTermResolver() {
		//TermResolverDefinition termResolver = krmsTermResolverLookup(KSKRMSServiceConstants.TERM_SPEC_RESOLVER_FREE_TEXT);
		if (ksKRMSTermResolverTypeService != null) {
			//TermResolver<FreeFormTextTermResolver> termResolverInstance = (TermResolver<FreeFormTextTermResolver>) ksKRMSTermResolverTypeService.loadTermResolver(termResolver);

			Map<String, String> parameters = new HashMap<String, String>();
			//parameters.put(KSKRMSServiceConstants.PERSON_ID_TERM_PROPERTY, "1234106");

			//termResolverInstance.resolve(setupTermResolverResolvedPrereqs(), parameters);
		}
	}

	@Test
	public void testGPATermResolver() {
		//TermResolverDefinition termResolver = krmsTermResolverLookup(KSKRMSServiceConstants.TERM_SPEC_GPA);
		if (ksKRMSTermResolverTypeService != null) {
			//TermResolver<GPATermResolver> termResolverInstance = (TermResolver<GPATermResolver>) ksKRMSTermResolverTypeService.loadTermResolver(termResolver);

			Map<String, String> parameters = new HashMap<String, String>();
			//parameters.put(KSKRMSServiceConstants.PERSON_ID_TERM_PROPERTY, "1234107");
			//parameters.put(KSKRMSServiceConstants.CALC_TYPE_KEY_TERM_PROPERTY, "1000");

			//termResolverInstance.resolve(setupTermResolverResolvedPrereqs(), parameters);
		}
	}

	@Test
	public void testNumberOfCreditsTermResolver() {
		//TermResolverDefinition termResolver = krmsTermResolverLookup(KSKRMSServiceConstants.TERM_SPEC_RESOLVER_NUMBER_OF_CREDITS);
		if (ksKRMSTermResolverTypeService != null) {
			//TermResolver<CompletedCourseCreditsTermResolver> termResolverInstance = (TermResolver<CompletedCourseCreditsTermResolver>) ksKRMSTermResolverTypeService.loadTermResolver(termResolver);

			Map<String, String> parameters = new HashMap<String, String>();
			//parameters.put(KSKRMSServiceConstants.PERSON_ID_TERM_PROPERTY, "1234110");
		    //parameters.put(KSKRMSServiceConstants.TERM_ID_TERM_PROPERTY, "108");

			//termResolverInstance.resolve(setupTermResolverResolvedPrereqs(), parameters);
		}
	}

	@Test
	public void testScoreTermResolver() {
		//TermResolverDefinition termResolver = krmsTermResolverLookup(KSKRMSServiceConstants.TERM_SPEC_RESOLVER_SCORE);
		if (ksKRMSTermResolverTypeService != null) {
			//TermResolver<ScoreTermResolver> termResolverInstance = (TermResolver<ScoreTermResolver>) ksKRMSTermResolverTypeService.loadTermResolver(termResolver);

			Map<String, String> parameters = new HashMap<String, String>();

			//termResolverInstance.resolve(setupTermResolverResolvedPrereqs(), parameters);
		}
	}

	@Test
	public void testAdminOrgNumberTermResolver() {
		//TermResolverDefinition termResolver = krmsTermResolverLookup(KSKRMSServiceConstants.TERM_SPEC_RESOLVER_ADMIN_ORG_NUMBER);
		if (ksKRMSTermResolverTypeService != null) {
			//TermResolver<AdminOrgPermissionTermResolver> termResolverInstance = (TermResolver<AdminOrgPermissionTermResolver>) ksKRMSTermResolverTypeService.loadTermResolver(termResolver);

			Map<String, String> parameters = new HashMap<String, String>();
			//parameters.put(KSKRMSServiceConstants.PERSON_ID_TERM_PROPERTY, "1234112");

			//termResolverInstance.resolve(setupTermResolverResolvedPrereqs(), parameters);
		}
	}

	@Test
	public void testCompletedCourseNumberTermResolver() {
		//TermResolverDefinition termResolver = krmsTermResolverLookup(KSKRMSServiceConstants.TERM_SPEC_RESOLVER_COMPLETED_COURSE_NUMBER);
		if (ksKRMSTermResolverTypeService != null) {
			//TermResolver<NumberOfCompletedCoursesTermResolver> termResolverInstance = (TermResolver<NumberOfCompletedCoursesTermResolver>) ksKRMSTermResolverTypeService.loadTermResolver(termResolver);

			Map<String, String> parameters = new HashMap<String, String>();
			//parameters.put(KSKRMSServiceConstants.PERSON_ID_TERM_PROPERTY, "1234113");

			//termResolverInstance.resolve(setupTermResolverResolvedPrereqs(), parameters);
		}
	}

	@Test
	public void testEnrolledCourseTermResolver() {
		//TermResolverDefinition termResolver = krmsTermResolverLookup(KSKRMSServiceConstants.TERM_SPEC_RESOLVER_ENROLLED_COURSE);
		if (ksKRMSTermResolverTypeService != null) {
			//TermResolver<EnrolledCourseTermResolver> termResolverInstance = (TermResolver<EnrolledCourseTermResolver>) ksKRMSTermResolverTypeService.loadTermResolver(termResolver);

			Map<String, String> parameters = new HashMap<String, String>();
			//parameters.put(KSKRMSServiceConstants.PERSON_ID_TERM_PROPERTY, "1234119");
			//parameters.put(KSKRMSServiceConstants.TERM_ID_TERM_PROPERTY, "109");

			//termResolverInstance.resolve(setupTermResolverResolvedPrereqs(), parameters);
		}
	}
	
	private Map<String, Object> setupTermResolverResolvedPrereqs() {
		
		Map<String, Object> resolvedPrereqs = new HashMap<String, Object>();
		resolvedPrereqs.put(KSKRMSServiceConstants.TERM_PREREQUISITE_CONTEXTINFO, getEnglishContextInfo() );
		return resolvedPrereqs;
	}
	
    private ContextInfo getEnglishContextInfo() {
        ContextInfo contextInfo = ContextUtils.getContextInfo();
        LocaleInfo localeInfo = contextInfo.getLocale();
        localeInfo.setLocaleLanguage("en");
        return contextInfo;
    }
	
}
