package org.kuali.student.lum.dto.test;

import org.junit.Test;
import org.kuali.student.r1.common.dictionary.service.impl.ComplexSubstructuresHelper;
import org.kuali.student.r2.core.statement.dto.StatementInfo;
import org.kuali.student.r2.lum.program.dto.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.fail;


public class TestProgramDTOEqualXMLBeanDefinition {




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
	public void testProgramDTOsAgainstDataDictionary() {
		System.out.println("testing statement dictionary");

    	Set<String> startingClasses = new LinkedHashSet();
		startingClasses.add(CommonWithCoreProgramInfo.class.getName());
		startingClasses.add(CommonWithCredentialProgramInfo.class.getName());
		startingClasses.add(CommonWithProgramVariationInfo.class.getName());
		startingClasses.add(CoreProgramInfo.class.getName());
		startingClasses.add(CredentialProgramInfo.class.getName());
		startingClasses.add(HonorsProgramInfo.class.getName());
		startingClasses.add(MajorDisciplineInfo.class.getName());
		startingClasses.add(MinorDisciplineInfo.class.getName());
		startingClasses.add(ProgramRequirementInfo.class.getName());
		startingClasses.add(ProgramVariationInfo.class.getName());
		String contextFile = "ks-programInfo-dictionary-context";
		String outFile = "target/" + contextFile + ".txt";
		DictionaryDiscrepencyTesterHelper helper = new DictionaryDiscrepencyTesterHelper(
				outFile, startingClasses, contextFile + ".xml", false);
		helper.setPrintDescrepenciesOnly(true);
		List<String> errors = helper.doTest();
		if (errors.size() > 0) {
			fail("failed dictionary validation:\n" + formatAsString(errors));
		}

	}
}
