package org.kuali.student.lum.course.service.impl;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.junit.Test;
import org.kuali.student.core.dictionary.service.impl.DictionaryTesterHelper;
import org.kuali.student.lum.lrc.dto.CredentialInfo;
import org.kuali.student.lum.lrc.dto.CreditInfo;
import org.kuali.student.lum.lrc.dto.GradeInfo;
import org.kuali.student.lum.lrc.dto.ResultComponentInfo;
import org.kuali.student.lum.lrc.dto.ScaleInfo;
import static org.junit.Assert.*;

public class TestLrcDictionary
{

 @Test
 public void testLoadLrcDictionary ()
 {
  Set<String> startingClasses = new LinkedHashSet ();
  startingClasses.add (ResultComponentInfo.class.getName ());
  startingClasses.add (CreditInfo.class.getName ());
  startingClasses.add (CredentialInfo.class.getName ());
  startingClasses.add (ScaleInfo.class.getName ());
  startingClasses.add (GradeInfo.class.getName ());
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
