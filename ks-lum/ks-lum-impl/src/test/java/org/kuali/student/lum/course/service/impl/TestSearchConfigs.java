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
  for (String key : configs.keySet ())
  {
   String searchConfigFileName = key + "-search-config.xml";
   String projectLocation = configs.get (key);
   String outFile = "target/" + key + "-search-config.txt";
   System.out.println ("processing " + key + ": see " + outFile + " for configuration summary");
   SearchConfigTesterHelper helper =
                            new SearchConfigTesterHelper (outFile,
                                                          projectLocation,
                                                          searchConfigFileName);
   helper.doTest ();
  }
 }
}
