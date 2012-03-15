package org.kuali.student.lum.dto.test;

import static org.junit.Assert.fail;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.kuali.student.r1.common.dictionary.service.impl.ComplexSubstructuresHelper;
import org.kuali.student.r2.core.statement.dto.ReqCompFieldInfo;
import org.kuali.student.r2.core.statement.dto.ReqComponentInfo;
import org.kuali.student.r2.core.statement.dto.StatementInfo;
import org.kuali.student.r2.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.r2.lum.clu.dto.AcademicSubjectOrgInfo;
import org.kuali.student.r2.lum.clu.dto.AccreditationInfo;
import org.kuali.student.r2.lum.clu.dto.AdminOrgInfo;
import org.kuali.student.r2.lum.clu.dto.AffiliatedOrgInfo;
import org.kuali.student.r2.lum.clu.dto.CluAccountingInfo;
import org.kuali.student.r2.lum.clu.dto.CluCluRelationInfo;
import org.kuali.student.r2.lum.clu.dto.CluCreditInfo;
import org.kuali.student.r2.lum.clu.dto.CluFeeInfo;

public class TestDTOEqualXMLBeanDefinition {




	private String formatAsString(List<String> errors) {
		int i = 0;
		StringBuilder builder = new StringBuilder();
		for (String error : errors) {
			i++;
			builder.append(i + ". " + error + "\n");
		}
		return builder.toString();
	}

	// @Test
	public void getLinkedListFields() {
		ComplexSubstructuresHelper helpme = new ComplexSubstructuresHelper();
		ArrayList<Field> fields = new ArrayList<Field>();
		fields = helpme.getAllFields(fields, StatementInfo.class);
		for (Field field : fields) {
			System.out.println(field.getName());
		}
	}

	@Test
	public void testCluDTOsAgainstDataDictionary() {
		System.out.println("testing statement dictionary");

		Set<String> startingClasses = new LinkedHashSet();
		//startingClasses.add(AcademicSubjectOrgInfo.class.getName());
		//startingClasses.add(AccreditationInfo.class.getName());
		//startingClasses.add(AdminOrgInfo.class.getName());
		//startingClasses.add(AffiliatedOrgInfo.class.getName());
		//startingClasses.add(CluAccountingInfo.class.getName());
		//startingClasses.add(CluCluRelationInfo.class.getName());
		//startingClasses.add(CluCreditInfo.class.getName());
		startingClasses.add(CluFeeInfo.class.getName());
		String contextFile = "ks-cluInfo-dictionary-context";
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
