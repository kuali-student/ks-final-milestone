package org.kuali.student.lum.course.service.impl;

import org.junit.Test;
import org.kuali.student.common.test.mock.MockProxyFactoryBean;
import org.kuali.student.common.test.util.ContextInfoTestUtility;
import org.kuali.student.r2.lum.course.service.assembler.CourseAssemblerConstants;
import org.kuali.student.r1.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.r1.common.dictionary.service.impl.DictionaryTesterHelper;
import org.kuali.student.r1.common.validator.ServerDateParser;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.DtoConstants;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.validator.DefaultValidatorImpl;
import org.kuali.student.r2.common.validator.Validator;
import org.kuali.student.r2.common.validator.ValidatorFactory;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.lum.clu.dto.AffiliatedOrgInfo;
import org.kuali.student.r2.lum.course.dto.CourseExpenditureInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.dto.CourseRevenueInfo;
import org.kuali.student.r2.lum.course.dto.LoDisplayInfo;
import org.kuali.student.r2.lum.course.service.utils.*;
import org.kuali.student.r2.lum.lo.dto.LoCategoryInfo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;

import static org.junit.Assert.*;

public class TestCourseInfoDictionary {
	
	ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();

	@Test
	public void testLoadCourseInfoDictionary() {
		Set<String> startingClasses = new LinkedHashSet<String>();
		startingClasses.add(CourseInfo.class.getName ());
		// startingClasses.add (StatementTreeViewInfo.class);
		String contextFile = "ks-courseInfo-dictionary-context";
		String outFile = "target/" + contextFile + ".txt";
		DictionaryTesterHelper helper = new DictionaryTesterHelper(outFile,
				startingClasses, contextFile + ".xml", true);
	 List<String> errors = helper.doTest ();
  if (errors.size () > 0)
  {
   fail ("failed dictionary validation:\n" + formatAsString (errors));
  }
 }

 private String formatAsString (List<String> errors)
 {
  int i = 0;
  StringBuilder builder = new StringBuilder ();
  for (String error : errors)
  {
   i ++;
   builder.append (i + ". " + error + "\n");
  }
  return builder.toString ();
 }

	@Test
	public void testCourseInfoValidation() throws OperationFailedException {
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:ks-courseInfo-dictionary-context.xml");
		System.out.println("h1. Test Validation");
		DefaultValidatorImpl val = new DefaultValidatorImpl();
		ValidatorFactory vf = new ValidatorFactory();
		SubjectAreaUnitOwnerValidator saVal = new SubjectAreaUnitOwnerValidator();
		
		List<Validator> vList = new ArrayList<Validator>();
		
		saVal.setSearchDispatcher(new MockSearchDispatcher());
		
		vList.add(new RevenuePercentValidator() );
		vList.add(new ExpenditurePercentValidator());
		vList.add(saVal);
		vList.add(getActiveDatesValidator());
		vList.add(new ActivityTypeValidator());
		vf.setValidatorList(vList);
		
		val.setValidatorFactory(vf);
		val.setDateParser(new ServerDateParser());
		val.setSearchDispatcher(new MockSearchDispatcher());
		// TODO KSCM Do we need a mock for COurseInfo?
		CourseInfo info = new CourseInfo();
		ObjectStructureDefinition os =(ObjectStructureDefinition) ac.getBean(info.getClass().getName());
		List<ValidationResultInfo> validationResults =  val.validateObject(info,	os, contextInfo);
		System.out.println("h3. With just a blank Course");
		for (ValidationResultInfo vr : validationResults) {
			System.out.println(vr.getElement() + " " + vr.getMessage());
		}
		assertEquals(3, validationResults.size());

		try {
			info = new CourseDataGenerator().getCourseTestData();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

		// Clean up revenues because of custom validation  
		info.setRevenues(new ArrayList<CourseRevenueInfo>());
		info.setExpenditure(null);
		
		validationResults = val.validateObject(info, os, contextInfo);
		System.out.println("h3. With generated data");
		for (ValidationResultInfo vr : validationResults) {
			System.out.println(vr.getElement() + " " + vr.getMessage());
		}
		assertEquals(0, validationResults.size());

		System.out.println("testCourseDescrRequiredBasedOnState");
		info.setStateKey("DRAFT");
		info.setDescr(null);
		validationResults = val.validateObject(info, os, contextInfo);
		assertEquals(0, validationResults.size());

		info.setStateKey("ACTIVE");
		info.setDescr(null);
		validationResults = val.validateObject(info, os, contextInfo);
		for (ValidationResultInfo vr : validationResults) {
			System.out.println(vr.getElement() + " " + vr.getMessage());
		}
		assertEquals(3, validationResults.size());

		System.out.println("test validation on dynamic attributes");
		
		info.getAttributes().add(new AttributeInfo("finalExamStatus", "123"));
		validationResults = val.validateObject(info, os, contextInfo);
		for (ValidationResultInfo vr : validationResults) {
			System.out.println(vr.getElement() + " " + vr.getMessage());
		}
		assertEquals(5, validationResults.size());

		LoDisplayInfo loInfo = new LoDisplayInfo();
		LoCategoryInfo loCatInfo = new LoCategoryInfo();
		loInfo.setLoCategoryInfoList(Arrays.asList(loCatInfo));
		RichTextInfo rtInfo = new RichTextInfo();
		rtInfo.setPlain("The ability to use sensory cues to guide motor activity.  This ranges from sensory stimulation, through cue selection, to translation.");
		rtInfo.setFormatted(rtInfo.getPlain());
		loCatInfo.setDescr(rtInfo);
		info.setCourseSpecificLOs(Arrays.asList(loInfo));
		info.setRevenues(new ArrayList<CourseRevenueInfo>());
		validationResults = val.validateObject(info, os, contextInfo);
		for (ValidationResultInfo vr : validationResults) {
			System.out.println(vr.getElement() + " " + vr.getMessage());
		}
		assertTrue(rtInfo.getPlain().matches("[A-Za-z0-9.\\\\\\-;:&#34;,'&amp;%$#@!\t\n\r ]*"));
//courseSpecificLOs/0/loCategoryInfoList/0/name validation.required
//courseSpecificLOs/0/loInfo validation.required
///descr validation.required
///finalExamStatus validation.validCharsFailed
///finalExamRationale validation.required
		assertEquals(7, validationResults.size());

		
		// Test custom validation 
        try {
            info = new CourseDataGenerator().getCourseTestData();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        
        AffiliatedOrgInfo af1 = new AffiliatedOrgInfo();
        af1.setOrgId("orgId");
        af1.setPercentage(15l);

        AffiliatedOrgInfo af2 = new AffiliatedOrgInfo();
        af2.setOrgId("orgId");
        af2.setPercentage(15l);
        
        List<AffiliatedOrgInfo> afList = new ArrayList<AffiliatedOrgInfo>();
        afList.add(af1);
        afList.add(af2);
        
        CourseRevenueInfo cr = new CourseRevenueInfo();
        cr.setFeeType("REVENUE");
        cr.setAffiliatedOrgs(afList);
                

        AffiliatedOrgInfo af3 = new AffiliatedOrgInfo();
        af3.setOrgId("orgId");
        af3.setPercentage(55l);

        AffiliatedOrgInfo af4 = new AffiliatedOrgInfo();
        af4.setOrgId("orgId");
        af4.setPercentage(45l);
        
        List<AffiliatedOrgInfo> afList2 = new ArrayList<AffiliatedOrgInfo>();
        afList2.add(af3);
        afList2.add(af4);
        
        CourseRevenueInfo cr2 = new CourseRevenueInfo();
        cr2.setFeeType("REVENUE");
        cr2.setAffiliatedOrgs(afList2);
        
        List<CourseRevenueInfo> revenues = new ArrayList<CourseRevenueInfo>();
        revenues.add(cr);
        revenues.add(cr2);
        
        info.setRevenues(revenues);
        
        
        CourseExpenditureInfo cei = new CourseExpenditureInfo();
        cei.setAffiliatedOrgs(afList);
        
        List<ValidationResultInfo> validationResults1 = val.validateObject(info, os, contextInfo);
        System.out.println("h3. With just a custom validations");

        assertEquals(6, validationResults1.size());
        
        for(ValidationResultInfo vr : validationResults1) {
            System.out.println(vr.getElement());
           assertTrue("/revenues".equals(vr.getElement()) || "/expenditure/affiliatedOrgs".equals(vr.getElement())
                    ||"revenues/0/stateKey".equals(vr.getElement()) ||"revenues/0/typeKey".equals(vr.getElement())||"revenues/1/stateKey".equals(vr.getElement()) ||"revenues/1/typeKey".equals(vr.getElement()) ||"/stateKey".equals(vr.getElement()) ||"/typeKey".equals(vr.getElement()));
        }

	}

	private Validator getActiveDatesValidator() {
		ActiveDatesValidator adv = new ActiveDatesValidator();
		MockProxyFactoryBean b = new MockProxyFactoryBean();
		Map<String,Object> methodReturnMap = new HashMap<String,Object>(); 
		AtpInfo atpInfo = new AtpInfo();
		atpInfo.setStartDate(new Date());
		atpInfo.setEndDate(new Date());
		methodReturnMap.put("getAtp", atpInfo);
		b.setMethodReturnMap(methodReturnMap);
		
		b.setInterfaceClass(AtpService.class);
		try {
			adv.setAtpService((AtpService) b.getObject());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return adv;
	}
	
}
