package org.kuali.student.lum.course.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.kuali.student.common.validator.DefaultValidatorImpl;
import org.kuali.student.common.validator.SampCustomValidator;
import org.kuali.student.common.validator.ServerDateParser;
import org.kuali.student.common.validator.ValidatorFactory;
import org.kuali.student.core.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.course.dto.LoDisplayInfo;
import org.kuali.student.lum.lo.dto.LoCategoryInfo;
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
		helper.doTest();
	}

	@Test
	public void testCourseInfoValidation() throws OperationFailedException {
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:ks-courseInfo-dictionary-context.xml");
		System.out.println("h1. Test Validation");
		DefaultValidatorImpl val = new DefaultValidatorImpl();
		val.setValidatorFactory(new ValidatorFactory(new SampCustomValidator()));
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
		assertEquals(1, validationResults.size());

		System.out.println("test validation on dynamic attributes");
		info.getAttributes().put("finalExamStatus", "123");
		validationResults = val.validateObject(info, os);
		for (ValidationResultInfo vr : validationResults) {
			System.out.println(vr.getElement() + " " + vr.getMessage());
		}
		assertEquals(2, validationResults.size());

		LoDisplayInfo loInfo = new LoDisplayInfo();
		LoCategoryInfo loCatInfo = new LoCategoryInfo();
		loInfo.setLoCategoryInfoList(Arrays.asList(loCatInfo));
		RichTextInfo rtInfo = new RichTextInfo();
		rtInfo.setPlain("The ability to use sensory cues to guide motor activity.  This ranges from sensory stimulation, through cue selection, to translation.");
		rtInfo.setFormatted(rtInfo.getPlain());
		loCatInfo.setDesc(rtInfo);
		info.setCourseSpecificLOs(Arrays.asList(loInfo));
		validationResults = val.validateObject(info, os);
		for (ValidationResultInfo vr : validationResults) {
			System.out.println(vr.getElement() + " " + vr.getMessage());
		}
		assertTrue(rtInfo.getPlain().matches("[A-Za-z0-9.\\\\\\-;:&#34;,'&amp;%$#@!\t\n\r ]*"));
		assertEquals(2, validationResults.size());

	}
}
