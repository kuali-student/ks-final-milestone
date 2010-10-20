package org.kuali.student.lum.course.service.impl;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.fail;

import org.kuali.student.core.dictionary.service.impl.DictionaryTesterHelper;

public class TestFreestandingLuSearchDictionary
{

 @Test
 public void testLoadSearchBrowseDictionary ()
 {
  Set<String> startingClasses = new LinkedHashSet ();
  startingClasses.add ("search");
  startingClasses.add ("browse");
  String contextFile = "ks-lu-search-dictionary-context";
  String outFile = "target/" + contextFile + ".txt";
  DictionaryTesterHelper helper = new DictionaryTesterHelper (outFile,
                                                              startingClasses,
                                                              contextFile
                                                              + ".xml",
                                                              true);
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
