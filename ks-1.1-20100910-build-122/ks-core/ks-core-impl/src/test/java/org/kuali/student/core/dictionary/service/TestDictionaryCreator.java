package org.kuali.student.core.dictionary.service;

import org.junit.Test;
import java.beans.IntrospectionException;
import java.io.IOException;
import org.kuali.student.core.statement.dto.ReqComponentInfo;


public class TestDictionaryCreator
{

 @Test
 public void testRunDictinoaryCreator ()
   throws IOException,
          IntrospectionException,
          SecurityException,
          NoSuchFieldException
 {
  new DictionaryCreator ().execute (ReqComponentInfo.class, "target/ks-ReqComponentInfo-dictinoary-context-generated.xml");
 }
}
