package org.kuali.student.dictionary.writer;

import org.junit.Test;
import org.kuali.student.dictionary.TestConstants;

public class TestNewDictionary implements TestConstants
{

 @Test
 public void testDictionaryLoad ()
 {
  DictionaryServiceSpringImpl dict =
   new DictionaryServiceSpringImpl (RESOURCES_DIRECTORY
   + "/" + "lu-cluInfo-dictionary-override-config.xml");
  org.kuali.student.core.dictionary.dto.Type type = null;
 }

}
