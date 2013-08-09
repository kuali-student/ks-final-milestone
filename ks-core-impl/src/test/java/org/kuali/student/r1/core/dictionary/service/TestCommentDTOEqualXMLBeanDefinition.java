package org.kuali.student.r1.core.dictionary.service;

import static org.junit.Assert.fail;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.kuali.student.r1.common.dictionary.service.impl.DictionaryDiscrepencyTesterHelper;
import org.kuali.student.r2.core.comment.dto.CommentInfo;
import org.kuali.student.r2.core.comment.dto.TagInfo;

public class TestCommentDTOEqualXMLBeanDefinition {

	@Test
	public void testLoadCommentInfoDictionary() {
  System.out.println ("testing comment dictionary");
		Set<String> startingClasses = new LinkedHashSet();
		
		startingClasses.add(CommentInfo.class.getName ());//NullPointerException
		startingClasses.add(TagInfo.class.getName ());//NullPointerException
		String contextFile = "ks-comment-dictionary-context";
		String outFile = "target/" + contextFile + ".txt";
		DictionaryDiscrepencyTesterHelper helper = new DictionaryDiscrepencyTesterHelper(outFile,
				startingClasses, contextFile + ".xml", false);
		helper.setPrintDescrepenciesOnly(true);
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
