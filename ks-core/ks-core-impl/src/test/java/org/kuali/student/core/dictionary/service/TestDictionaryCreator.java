package org.kuali.student.core.dictionary.service;

import org.junit.Test;
import java.beans.IntrospectionException;
import java.io.IOException;
import org.kuali.student.core.dictionary.service.impl.DictionaryCreator;
import org.kuali.student.core.versionmanagement.dto.VersionInfo;


public class TestDictionaryCreator
{

 @Test
 public void testRunDictinoaryCreator ()
   throws IOException,
          IntrospectionException,
          SecurityException,
          NoSuchFieldException
 {
  new DictionaryCreator ().execute (VersionInfo.class, "target/ks-VersionInfo-dictinoary-context-generated.xml");
 }
}
