package org.kuali.student.r2.core.dictionary.service;

import org.junit.Test;
import java.beans.IntrospectionException;
import java.io.IOException;

import org.kuali.student.r1.common.dictionary.service.impl.DictionaryCreator;
import org.kuali.student.r2.core.document.dto.DocumentInfo;
import org.kuali.student.r2.core.document.dto.RefDocRelationInfo;


public class TestDictionaryCreator
{

 @Test
 public void testRunDictinoaryCreator ()
   throws IOException,
          IntrospectionException,
          SecurityException,
          NoSuchFieldException
 {
  new DictionaryCreator ().execute (RefDocRelationInfo.class, "target/ks-RefDocRelationInfo-dictinoary-context-generated.xml");
  new DictionaryCreator ().execute (DocumentInfo.class, "target/ks-DocumentInfo-dictinoary-context-generated.xml");
 }
}
