package r1.org.kuali.student.core.dictionary.service;

import static org.junit.Assert.fail;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.kuali.student.r1.common.dictionary.service.impl.ComplexSubstructuresHelper;
import org.kuali.student.r2.core.atp.dto.AtpAtpRelationInfo;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.comment.dto.CommentInfo;
import org.kuali.student.r2.core.comment.dto.TagInfo;
import org.kuali.student.r2.core.document.dto.DocumentBinaryInfo;
import org.kuali.student.r2.core.document.dto.DocumentCategoryInfo;
import org.kuali.student.r2.core.document.dto.DocumentInfo;
import org.kuali.student.r2.core.document.dto.RefDocRelationInfo;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.proposal.dto.ProposalDocRelationInfo;
import org.kuali.student.r2.core.proposal.dto.ProposalInfo;
import org.kuali.student.r2.core.statement.dto.ReqCompFieldInfo;
import org.kuali.student.r2.core.statement.dto.ReqComponentInfo;
import org.kuali.student.r2.core.statement.dto.StatementInfo;
import org.kuali.student.r2.core.statement.dto.StatementTreeViewInfo;

//import org.kuali.student.r1.core.statement.dto.StatementInfo;

public class TestDTOEqualXMLBeanDefinition {

	
	
//	@Test
	public void testOrgDTOsAgainstDataDictionary() {
		System.out.println("testing statement dictionary");

		Set<String> startingClasses = new LinkedHashSet();
		startingClasses.add(OrgInfo.class.getName());
//		startingClasses.add(ReqComponentInfo.class.getName());
//		startingClasses.add(ReqCompFieldInfo.class.getName());
//		startingClasses.add(StatementTreeViewInfo.class.getName());
		String contextFile = "organization-dictionary-config";
		String outFile = "target/" + contextFile + ".txt";
		DictionaryDiscrepencyTesterHelper helper = new DictionaryDiscrepencyTesterHelper(
				outFile, startingClasses, contextFile + ".xml", false);
		helper.setPrintDescrepenciesOnly(true);
		List<String> errors = helper.doTest();
		if (errors.size() > 0) {
			fail("failed dictionary validation:\n" + formatAsString(errors));
		}

	}
	
	
	//@Test
	public void testATPDTOsAgainstDataDictionary() {
		System.out.println("testing statement dictionary");

		Set<String> startingClasses = new LinkedHashSet();
		startingClasses.add(MilestoneInfo.class.getName());	
		startingClasses.add(AtpAtpRelationInfo.class.getName());
		startingClasses.add(AtpInfo.class.getName());
		String contextFile = "ks-atp-dictionary-context";
		String outFile = "target/" + contextFile + ".txt";
		DictionaryDiscrepencyTesterHelper helper = new DictionaryDiscrepencyTesterHelper(
				outFile, startingClasses, contextFile + ".xml", false);
		helper.setPrintDescrepenciesOnly(true);
		List<String> errors = helper.doTest();
		if (errors.size() > 0) {
			fail("failed dictionary validation:\n" + formatAsString(errors));
		}

	}
	
	//@Test
	public void testProposalDTOsAgainstDataDictionary() {
		System.out.println("testing statement dictionary");

		Set<String> startingClasses = new LinkedHashSet();
		startingClasses.add(ProposalDocRelationInfo.class.getName());
		startingClasses.add(ProposalInfo.class.getName());
		String contextFile = "ks-proposalInfo-dictionary-context";
		String outFile = "target/" + contextFile + ".txt";
		DictionaryDiscrepencyTesterHelper helper = new DictionaryDiscrepencyTesterHelper(
				outFile, startingClasses, contextFile + ".xml", false);
		helper.setPrintDescrepenciesOnly(true);
		List<String> errors = helper.doTest();
		if (errors.size() > 0) {
			fail("failed dictionary validation:\n" + formatAsString(errors));
		}

	}
	
	
	@Test
	public void testVersionManagementDTOsAgainstDataDictionary() {
		System.out.println("testing statement dictionary");

		Set<String> startingClasses = new LinkedHashSet();
		startingClasses.add(ProposalDocRelationInfo.class.getName());
		//startingClasses.add(ProposalInfo.class.getName());
		String contextFile = "ks-proposalInfo-dictionary-context";
		String outFile = "target/" + contextFile + ".txt";
		DictionaryDiscrepencyTesterHelper helper = new DictionaryDiscrepencyTesterHelper(
				outFile, startingClasses, contextFile + ".xml", false);
		helper.setPrintDescrepenciesOnly(true);
		List<String> errors = helper.doTest();
		if (errors.size() > 0) {
			fail("failed dictionary validation:\n" + formatAsString(errors));
		}

	}

	
	
	
	//@Test
	public void testDocumentDTOsAgainstDataDictionary() {
		System.out.println("testing statement dictionary");

		Set<String> startingClasses = new LinkedHashSet();
		startingClasses.add(DocumentBinaryInfo.class.getName());
		startingClasses.add(DocumentCategoryInfo.class.getName());
		startingClasses.add(DocumentInfo.class.getName());
		startingClasses.add(RefDocRelationInfo.class.getName());
		String contextFile = "ks-document-dictionary-context";
		String outFile = "target/" + contextFile + ".txt";
		DictionaryDiscrepencyTesterHelper helper = new DictionaryDiscrepencyTesterHelper(
				outFile, startingClasses, contextFile + ".xml", false);
		helper.setPrintDescrepenciesOnly(true);
		List<String> errors = helper.doTest();
		if (errors.size() > 0) {
			fail("failed dictionary validation:\n" + formatAsString(errors));
		}

	}
	
	
	//@Test
	public void testCommentDTOsAgainstDataDictionary() {
		System.out.println("testing statement dictionary");

		Set<String> startingClasses = new LinkedHashSet();
		startingClasses.add(TagInfo.class.getName());
		startingClasses.add(CommentInfo.class.getName());
		String contextFile = "ks-comment-dictionary-context";
		String outFile = "target/" + contextFile + ".txt";
		DictionaryDiscrepencyTesterHelper helper = new DictionaryDiscrepencyTesterHelper(
				outFile, startingClasses, contextFile + ".xml", false);
		helper.setPrintDescrepenciesOnly(true);
		List<String> errors = helper.doTest();
		if (errors.size() > 0) {
			fail("failed dictionary validation:\n" + formatAsString(errors));
		}

	}
	
	private void testDTOsAgainstDataDictionary(String contextFile,ArrayList<String> listOfDTOsToCheck) {
		System.out.println("testing statement dictionary");

		Set<String> startingClasses = new LinkedHashSet();
//		startingClasses.add(AtpInfo.class.getName());
		
		for (String className : startingClasses) {
			startingClasses.add(className);
		}
		
		
		
		String contextFilePath = contextFile;
		String outFile = "target/" + contextFile + ".txt";
		DictionaryDiscrepencyTesterHelper helper = new DictionaryDiscrepencyTesterHelper(
				outFile, startingClasses, contextFile + ".xml", false);
		helper.setPrintDescrepenciesOnly(true);
		List<String> errors = helper.doTest();
		if (errors.size() > 0) {
			fail("failed dictionary validation:\n" + formatAsString(errors));
		}

	}


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

//	@Test
	public void testStatementDTOsAgainstDataDictionary() {
		System.out.println("testing statement dictionary");

		Set<String> startingClasses = new LinkedHashSet();
		startingClasses.add(StatementInfo.class.getName());
		startingClasses.add(ReqComponentInfo.class.getName());
		startingClasses.add(ReqCompFieldInfo.class.getName());
		startingClasses.add(StatementTreeViewInfo.class.getName());
		String contextFile = "ks-statement-dictionary-context";
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
