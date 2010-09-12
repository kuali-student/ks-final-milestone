package org.kuali.student.lum.course.service.impl;

import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.Test;

public class TestSearchConfigs
{

 @Test
 public void testLuSearchConfig ()
 {
  Map<String, String> configs = new LinkedHashMap ();
  configs.put ("lu", "ks-lum/ks-lum-impl");
  configs.put ("lo", "ks-lum/ks-lum-impl");  
  configs.put ("lrc", "ks-lum/ks-lum-impl");
  configs.put ("em", "ks-core/ks-core-impl");
  configs.put ("proposal", "ks-core/ks-core-impl");
  configs.put ("atp", "ks-core/ks-core-impl");
  configs.put ("comment", "ks-core/ks-core-impl");
  configs.put ("organization", "ks-core/ks-core-impl");
  for (String key : configs.keySet ())
  {
   System.out.println ("processing " + key);
   String searchConfigFileName = key + "-search-config.xml";
   String projectLocation = configs.get (key);
   String outFile = "target/" + key + "-search-config.txt";
   SearchConfigTesterHelper helper =
                            new SearchConfigTesterHelper (outFile,
                                                          projectLocation,
                                                          searchConfigFileName);
   helper.doTest ();
  }
 }
}
