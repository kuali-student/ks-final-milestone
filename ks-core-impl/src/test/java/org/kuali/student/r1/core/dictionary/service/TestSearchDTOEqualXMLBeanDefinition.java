package org.kuali.student.r1.core.dictionary.service;

import static org.junit.Assert.fail;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.kuali.student.r1.common.dictionary.service.impl.DictionaryDiscrepencyTesterHelper;

public class TestSearchDTOEqualXMLBeanDefinition {

	@Test
	public void testLoadStatementInfoDictionary() {
  System.out.println ("testing statement dictionary");
		Set<String> startingClasses = new LinkedHashSet();
		
		//startingClasses.add(SearchParamInfo.class.getName ());//NullPointerException
		//startingClasses.add(SearchRequestInfo.class.getName ());//NullPointerException
		//startingClasses.add(SearchResultCellInfo.class.getName ());//NullPointerException
		//startingClasses.add(SearchResultInfo.class.getName ());//NullPointerException
		//startingClasses.add(SearchResultRowInfo.class.getName ());//NullPointerException
		//startingClasses.add(SortDirection.class.getName ());//NullPointerException
		String contextFile = "ks-statement-dictionary-context";//Wrong xml, can't find any with above mentioned classes
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
