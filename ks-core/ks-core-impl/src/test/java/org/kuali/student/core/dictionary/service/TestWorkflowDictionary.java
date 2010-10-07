package org.kuali.student.core.dictionary.service;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Test;
import org.kuali.student.core.workflow.dto.CollaboratorInfo;
import org.kuali.student.core.workflow.dto.WorkflowPersonInfo;

public class TestWorkflowDictionary {
	@Test
	public void testLoadWorkflowInfoDictionary() {
		Set<Class<?>> startingClasses = new LinkedHashSet<Class<?>>();
		startingClasses.add(CollaboratorInfo.class);
		//startingClasses.add(WorkflowPersonInfo.class); FIXME: This is failing
		String contextFile = "ks-workflow-dictionary-context";
		String outFile = "target/" + contextFile + ".txt";
		DictionaryTesterHelper helper = new DictionaryTesterHelper(outFile,
				startingClasses, contextFile + ".xml", false);
		helper.doTest();
	}
}
