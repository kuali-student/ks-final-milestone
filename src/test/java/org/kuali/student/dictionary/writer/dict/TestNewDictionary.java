package org.kuali.student.dictionary.writer.dict;

import org.junit.Test;
import org.kuali.student.dictionary.TestConstants;

public class TestNewDictionary implements TestConstants
{

 @Test
 public void testDictionaryLoad ()
 {
  DictionaryServiceSpringImpl lu =
   new DictionaryServiceSpringImpl (RESOURCES_DIRECTORY
   + "/" + "lu-dictionary-config.xml");
    DictionaryServiceSpringImpl lo =
   new DictionaryServiceSpringImpl (RESOURCES_DIRECTORY
   + "/" + "lo-dictionary-config.xml");
  org.kuali.student.core.dictionary.dto.Type type = null;
 }

}
