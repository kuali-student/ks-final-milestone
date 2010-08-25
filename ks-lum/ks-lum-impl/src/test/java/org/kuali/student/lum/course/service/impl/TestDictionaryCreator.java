package org.kuali.student.lum.course.service.impl;

import org.junit.Test;
import java.beans.IntrospectionException;
import java.io.IOException;
import org.kuali.student.lum.program.dto.MinorDisciplineInfo;


public class TestDictionaryCreator
{

 @Test
 public void testRunDictinoaryCreator ()
   throws IOException,
          IntrospectionException,
          SecurityException,
          NoSuchFieldException
 {
  new DictionaryCreator ().execute (MinorDisciplineInfo.class, "target/ks-MinorDisciplineInfo-dictinoary-context-generated.xml");
 }
}
