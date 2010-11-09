package org.kuali.student.lum.course.service.impl;

import org.junit.Test;
import java.beans.IntrospectionException;
import java.io.IOException;
import org.kuali.student.core.dictionary.service.impl.DictionaryCreator;
import org.kuali.student.lum.program.dto.ProgramRequirementInfo;


public class TestDictionaryCreator
{

 @Test
 public void testRunDictinoaryCreator ()
   throws IOException,
          IntrospectionException,
          SecurityException,
          NoSuchFieldException
 {
  new DictionaryCreator ().execute (ProgramRequirementInfo.class, "target/ks-ProgramRequirementInfo-dictinoary-context-generated.xml");
 }
}
