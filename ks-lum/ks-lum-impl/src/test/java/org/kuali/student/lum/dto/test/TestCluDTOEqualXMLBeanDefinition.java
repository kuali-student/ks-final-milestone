package org.kuali.student.lum.dto.test;

import org.junit.Test;
import org.kuali.student.r1.common.dictionary.service.impl.ComplexSubstructuresHelper;
import org.kuali.student.r2.core.statement.dto.StatementInfo;
import org.kuali.student.r2.lum.clu.dto.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.fail;

public class TestCluDTOEqualXMLBeanDefinition {




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
		  startingClasses.add(AcademicSubjectOrgInfo.class.getName());
		  startingClasses.add(AccreditationInfo.class.getName());
		  startingClasses.add(AdminOrgInfo.class.getName());
		  startingClasses.add(AffiliatedOrgInfo.class.getName());
		  startingClasses.add(CluAccountingInfo.class.getName());
		  startingClasses.add(CluCluRelationInfo.class.getName());
		  startingClasses.add(CluCreditInfo.class.getName());
		  startingClasses.add(CluFeeInfo.class.getName());
		  startingClasses.add(CluFeeRecordInfo.class.getName()); 
		  startingClasses.add(CluIdentifierInfo.class.getName());
		  startingClasses.add(CluInfo.class.getName());
		  startingClasses.add(CluInstructorInfo.class.getName());
		  startingClasses.add(CluLoRelationInfo.class.getName());
		  startingClasses.add(CluPublicationInfo.class.getName());
		  startingClasses.add(CluResultInfo.class.getName());
		  startingClasses.add(CluSetInfo.class.getName());
		  startingClasses.add(CluSetTreeViewInfo.class.getName());
		  startingClasses.add(ExpenditureInfo.class.getName());
		  startingClasses.add(FeeInfo.class.getName());
		  startingClasses.add(FieldInfo.class.getName());
		  startingClasses.add(LuCodeInfo.class.getName());
		  startingClasses.add(LuDocRelationInfo.class.getName());
		  startingClasses.add(MembershipQueryInfo.class.getName());
		  startingClasses.add(ResultOptionInfo.class.getName());
		  startingClasses.add(RevenueInfo.class.getName());
		  
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
