package org.kuali.student.lum.course.service.impl;

import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.Test;

public class TestLuSearchDictionary
{

 @Test
 public void testLoadCluSetInfoDictionary ()
 {
  Set<String> startingClasses = new LinkedHashSet ();
  startingClasses.add ("search");
  String contextFile = "ks-lu-search-dictionary-context";
  String outFile = "target/" + contextFile + ".txt";
  DictionaryTesterHelper helper = new DictionaryTesterHelper (outFile,
                                                              startingClasses,
                                                              contextFile
                                                              + ".xml",
                                                              true);
  helper.doTest ();
 }


}
