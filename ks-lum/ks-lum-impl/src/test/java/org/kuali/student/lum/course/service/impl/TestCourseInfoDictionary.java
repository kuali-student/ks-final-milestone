package org.kuali.student.lum.course.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.kuali.student.common.test.mock.MockProxyFactoryBean;
import org.kuali.student.common.validator.DefaultValidatorImpl;
import org.kuali.student.common.validator.ServerDateParser;
import org.kuali.student.common.validator.Validator;
import org.kuali.student.common.validator.ValidatorFactory;
import org.kuali.student.core.atp.dto.AtpInfo;
import org.kuali.student.core.atp.service.AtpService;
import org.kuali.student.core.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.core.dictionary.service.impl.DictionaryTesterHelper;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.lum.course.dto.CourseExpenditureInfo;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.course.dto.CourseRevenueInfo;
import org.kuali.student.lum.course.dto.LoDisplayInfo;
import org.kuali.student.lum.course.service.utils.ActiveDatesValidator;
import org.kuali.student.lum.course.service.utils.ExpenditurePercentValidator;
import org.kuali.student.lum.course.service.utils.RevenuePercentValidator;
import org.kuali.student.lum.lo.dto.LoCategoryInfo;
import org.kuali.student.lum.lu.dto.AffiliatedOrgInfo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestCourseInfoDictionary {

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
		List<Validator> vList = new ArrayList<Validator>();
		
		vList.add(new RevenuePercentValidator() );
		vList.add(new ExpenditurePercentValidator());
		vList.add(getActiveDatesValidator());
		vf.setValidatorList(vList);
		
		val.setValidatorFactory(vf);
		val.setDateParser(new ServerDateParser());
		val.setSearchDispatcher(new MockSearchDispatcher());
		CourseInfo info = new CourseInfo();
		ObjectStructureDefinition os = (ObjectStructureDefinition) ac.getBean(info.getClass().getName());
		List<ValidationResultInfo> validationResults = val.validateObject(info,	os);
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
		
		validationResults = val.validateObject(info, os);
		System.out.println("h3. With generated data");
		for (ValidationResultInfo vr : validationResults) {
			System.out.println(vr.getElement() + " " + vr.getMessage());
		}
		assertEquals(0, validationResults.size());

		System.out.println("testCourseDescrRequiredBasedOnState");
		info.setState("DRAFT");
		info.setDescr(null);
		validationResults = val.validateObject(info, os);
		assertEquals(0, validationResults.size());

		info.setState("ACTIVE");
		info.setDescr(null);
		validationResults = val.validateObject(info, os);
		for (ValidationResultInfo vr : validationResults) {
			System.out.println(vr.getElement() + " " + vr.getMessage());
		}
		assertEquals(3, validationResults.size());

		System.out.println("test validation on dynamic attributes");
		info.getAttributes().put("finalExamStatus", "123");
		validationResults = val.validateObject(info, os);
		for (ValidationResultInfo vr : validationResults) {
			System.out.println(vr.getElement() + " " + vr.getMessage());
		}
		assertEquals(3, validationResults.size());

		LoDisplayInfo loInfo = new LoDisplayInfo();
		LoCategoryInfo loCatInfo = new LoCategoryInfo();
		loInfo.setLoCategoryInfoList(Arrays.asList(loCatInfo));
		RichTextInfo rtInfo = new RichTextInfo();
		rtInfo.setPlain("The ability to use sensory cues to guide motor activity.  This ranges from sensory stimulation, through cue selection, to translation.");
		rtInfo.setFormatted(rtInfo.getPlain());
		loCatInfo.setDesc(rtInfo);
		info.setCourseSpecificLOs(Arrays.asList(loInfo));
		info.setRevenues(new ArrayList<CourseRevenueInfo>());
		validationResults = val.validateObject(info, os);
		for (ValidationResultInfo vr : validationResults) {
			System.out.println(vr.getElement() + " " + vr.getMessage());
		}
		assertTrue(rtInfo.getPlain().matches("[A-Za-z0-9.\\\\\\-;:&#34;,'&amp;%$#@!\t\n\r ]*"));
		assertEquals(3, validationResults.size());

		
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
        
        List<ValidationResultInfo> validationResults1 = val.validateObject(info, os);
        System.out.println("h3. With just a custom validations");

        assertEquals(2, validationResults1.size());
        
        for(ValidationResultInfo vr : validationResults1) {
            System.out.println(vr.getElement());
            assertTrue("/revenues".equals(vr.getElement()) || "/expenditure/affiliatedOrgs".equals(vr.getElement()));
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
