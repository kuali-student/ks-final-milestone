package org.kuali.student.r1.core.dictionary.service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.kuali.student.r1.common.dictionary.service.impl.DictionaryTesterHelper;
import org.kuali.student.r1.core.workflow.dto.CollaboratorInfo;

public class TestWorkflowDictionary {
	@Test
	public void testLoadWorkflowInfoDictionary() {
		Set<String> startingClasses = new LinkedHashSet();
		startingClasses.add(CollaboratorInfo.class.getName ());
		//startingClasses.add(WorkflowPersonInfo.class); FIXME: This is failing
		String contextFile = "ks-workflow-dictionary-context";
		String outFile = "target/" + contextFile + ".txt";
		DictionaryTesterHelper helper = new DictionaryTesterHelper(outFile,
				startingClasses, contextFile + ".xml", false);
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
}

