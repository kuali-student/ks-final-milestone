package org.kuali.student.core.dictionary.service;

import org.junit.Test;
import java.beans.IntrospectionException;
import java.io.IOException;
import org.kuali.student.core.atp.dto.AtpInfo;
import org.kuali.student.core.atp.dto.DateRangeInfo;
import org.kuali.student.core.atp.dto.MilestoneInfo;
import org.kuali.student.core.dictionary.service.impl.DictionaryCreator;


public class TestDictionaryCreator
{

 @Test
 public void testRunDictinoaryCreator ()
   throws IOException,
          IntrospectionException,
          SecurityException,
          NoSuchFieldException
 {
  new DictionaryCreator ().execute (AtpInfo.class, "target/ks-AtpInfo-dictinoary-context-generated.xml");
  new DictionaryCreator ().execute (MilestoneInfo.class, "target/ks-MilestoneInfo-dictinoary-context-generated.xml");
  new DictionaryCreator ().execute (DateRangeInfo.class, "target/ks-DateRangeInfo-dictinoary-context-generated.xml");
 }
}
