package org.kuali.student.lum.course.service.impl;

import org.junit.Test;
import java.beans.IntrospectionException;
import java.io.IOException;
import org.kuali.student.core.dictionary.service.impl.DictionaryCreator;
import org.kuali.student.lum.lrc.dto.CredentialInfo;
import org.kuali.student.lum.lrc.dto.CreditInfo;
import org.kuali.student.lum.lrc.dto.GradeInfo;
import org.kuali.student.lum.lrc.dto.ResultComponentInfo;
import org.kuali.student.lum.lrc.dto.ScaleInfo;

public class TestDictionaryCreator
{

 @Test
 public void testRunDictinoaryCreator ()
   throws IOException,
          IntrospectionException,
          SecurityException,
          NoSuchFieldException
 {
  new DictionaryCreator ().execute (ResultComponentInfo.class,
                                    "target/ks-ResultComponentInfo-dictinoary-context-generated.xml");
  new DictionaryCreator ().execute (GradeInfo.class,
                                    "target/ks-GradeInfo-dictinoary-context-generated.xml");
  new DictionaryCreator ().execute (ScaleInfo.class,
                                    "target/ks-ScaleInfo-dictinoary-context-generated.xml");
  new DictionaryCreator ().execute (CreditInfo.class,
                                    "target/ks-CreditInfo-dictinoary-context-generated.xml");
  new DictionaryCreator ().execute (CredentialInfo.class,
                                    "target/ks-CredentialInfo-dictinoary-context-generated.xml");
 }
}
