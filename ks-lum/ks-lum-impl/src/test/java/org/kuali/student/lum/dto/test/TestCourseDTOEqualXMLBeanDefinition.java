package org.kuali.student.lum.dto.test;

import org.junit.Test;
import org.kuali.student.r1.common.dictionary.service.impl.ComplexSubstructuresHelper;
import org.kuali.student.r2.core.statement.dto.StatementInfo;
import org.kuali.student.r2.lum.course.dto.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.fail;


public class TestCourseDTOEqualXMLBeanDefinition {




	private String formatAsString(List<String> errors) {
		int i = 0;
		StringBuilder builder = new StringBuilder();
		for (String error : errors) {
			i++;
			builder.append(i + ". " + error + "\n");
		}
		return builder.toString();
	}

	@Test
	public void getLinkedListFields() {
		ComplexSubstructuresHelper helpme = new ComplexSubstructuresHelper();
		ArrayList<Field> fields = new ArrayList<Field>();
		fields = helpme.getAllFields(fields, StatementInfo.class);
		for (Field field : fields) {
			System.out.println(field.getName());
		}
	}
	
	@Test
	public void testCourseDTOsAgainstDataDictionary() {
		System.out.println("testing course dictionary");
		Set<String> startingClasses = new LinkedHashSet();
        startingClasses.add(ActivityInfo.class.getName());
        startingClasses.add(CourseCrossListingInfo.class.getName());
        startingClasses.add(CourseExpenditureInfo.class.getName());
        startingClasses.add(CourseFeeInfo.class.getName());
		startingClasses.add(CourseJointInfo.class.getName());
		startingClasses.add(CourseRevenueInfo.class.getName());
		startingClasses.add(CourseVariationInfo.class.getName());
		startingClasses.add(FormatInfo.class.getName());
		startingClasses.add(LoDisplayInfo.class.getName());
		
		String contextFile = "ks-courseInfo-dictionary-context";
		String outFile = "target/" + contextFile + ".txt";
		DictionaryDiscrepencyTesterHelper helper = new DictionaryDiscrepencyTesterHelper(
				outFile, startingClasses, contextFile + ".xml", true);
		helper.setPrintDescrepenciesOnly(true);
		List<String> errors = helper.doTest();
		if (errors.size() > 0) {
			fail("failed dictionary validation:\n" + formatAsString(errors));
		}

	}
	
	//@Test
	public void testCourseDTOsAgainstDataDictionary_courseInfo() {
		System.out.println("testing courseInfo dictionary");
		Set<String> startingClasses = new LinkedHashSet();
		
		startingClasses.add(CourseInfo.class.getName());
		
		String contextFile = "ks-courseInfo-dictionary-context";
		String outFile = "target/" + contextFile + ".txt";
		DictionaryDiscrepencyTesterHelper helper = new DictionaryDiscrepencyTesterHelper(
				outFile, startingClasses, contextFile + ".xml", true);
		helper.setPrintDescrepenciesOnly(true);
		List<String> errors = helper.doTest();
		if (errors.size() > 0) {
			fail("failed dictionary validation:\n" + formatAsString(errors));
		}
	}
}
