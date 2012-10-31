package org.kuali.student.lum.course.service.impl;

import org.junit.Test;
import org.kuali.student.r1.common.dictionary.service.impl.DictionaryTesterHelper;
import org.kuali.student.r2.lum.lrc.dto.ResultScaleInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValueRangeInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.fail;

public class TestLrcDictionary
{

 @Test
 public void testLoadLrcDictionary ()
 {
  Set<String> startingClasses = new LinkedHashSet ();
  startingClasses.add (ResultScaleInfo.class.getName ());
  startingClasses.add (ResultValueInfo.class.getName ());
  startingClasses.add (ResultValueRangeInfo.class.getName ());
  startingClasses.add (ResultValuesGroupInfo.class.getName ());
  String contextFile = "ks-lrc-dictionary-context";
  String outFile = "target/" + contextFile + ".txt";
  DictionaryTesterHelper helper = new DictionaryTesterHelper (outFile,
                                                              startingClasses,
                                                              contextFile
                                                             + ".xml",
                                                              false);
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
