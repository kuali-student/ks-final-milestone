package org.kuali.student.lum.course.service.impl;

import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.Test;

public class TestClusetUiObjectDictionary
{

 @Test
 public void testLoadCluSetInfoDictionary ()
 {
  Set<String> startingClasses = new LinkedHashSet ();
  startingClasses.add ("cluset");
  startingClasses.add ("courseSet");
  startingClasses.add ("programSet");
  startingClasses.add ("testSet");
  String contextFile = "ks-cluset-ui-object-dictionary-context";
  String outFile = "target/" + contextFile + ".txt";
  DictionaryTesterHelper helper = new DictionaryTesterHelper (outFile,
                                                              startingClasses,
                                                              contextFile
                                                              + ".xml",
                                                              true);
  helper.doTest ();
 }


}
