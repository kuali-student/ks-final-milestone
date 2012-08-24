package org.kuali.student.r1.core.dictionary.service;

import java.util.Map;
import org.junit.Test;
import org.kuali.student.r1.common.dictionary.dto.ValidCharsConstraint;
import org.kuali.student.r2.common.validator.DefaultValidatorImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import static org.junit.Assert.*;

public class TestBaseDictionary
{

 @Test
 public void testValidCharsConstraints ()
 {
  System.out.println ("testing base dictionary");
  String contextFile = "ks-base-dictionary-context.xml";
  ApplicationContext ac = new ClassPathXmlApplicationContext ("classpath:"
                                                              + contextFile);
  Map<String, ValidCharsConstraint> vcs = (Map<String, ValidCharsConstraint>) ac.getBeansOfType (
    ValidCharsConstraint.class);
  for (String id : vcs.keySet ())
  {
   ValidCharsConstraint vc = vcs.get (id);
   System.out.println ("valid chars constraint: " + id + " "
                       + vc.getLabelKey () + " " + vc.getValue ());
  }

  String id = null;
  ValidCharsConstraint vc = null;
  DefaultValidatorImpl v = new DefaultValidatorImpl ();

  id = "alphanumericHyphenPeriod";
  vc = vcs.get (id);
  assertNotNull (vc);
  assertNull (v.processValidCharConstraint ("test", vc, null, "1", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "A", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, ".", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "-", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, " ", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, ",", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\n", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\r", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\t", null));

  id = "digits";
  vc = vcs.get (id);
  assertNotNull (vc);
  assertNull (v.processValidCharConstraint ("test", vc, null, "1", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "A", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, ".", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "-", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, " ", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, ",", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\n", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\r", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\t", null));

  id = "numeric";
  vc = vcs.get (id);
  assertNotNull (vc);
  assertNull (v.processValidCharConstraint ("test", vc, null, "0", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "1", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "20", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "-20", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "-12345620", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "20.1", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "20.0", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "20.01", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "20.00", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "120.00", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "1120.00", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "11120.00", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "111120.00", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "1111120.00", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "11111120.00", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "020", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "-020", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "-020.1", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "20.010", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "20.011", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "A", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, ".", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "-", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, " ", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, ",", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\n", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\r", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\t", null));

  id = "numeric.range";
  vc = vcs.get (id);
  assertNotNull (vc);
  assertNull (v.processValidCharConstraint ("test", vc, null, "0", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "1", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "20", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "-20", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "-12345620", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "20.1", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "20.0", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "20.01", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "20.00", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "120.00", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "1120.00", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "11120.00", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "111120.00", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "1111120.00", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "11111120.00", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "1-2", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "1.1-2.2", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "1 - 2", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "1--2", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "1 - -2", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "-1 - -2", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "1 -2", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "1 - 2 - 3", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "1- 2", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "020", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "-020", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "-020.1", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "20.010", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "20.011", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "A", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, ".", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "-", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, " ", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, ",", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\n", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\r", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\t", null));

  id = "positive.numeric.range";
  vc = vcs.get (id);
  assertNotNull (vc);
  assertNull (v.processValidCharConstraint ("test", vc, null, "0", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "1", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "20", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "-20", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "-12345620", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "20.1", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "20.0", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "20.01", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "20.00", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "120.00", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "1120.00", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "11120.00", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "111120.00", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "1111120.00", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "11111120.00", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "1-2", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "1.1-2.2", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "1 - 2", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "1--2", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "1 - -2", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "-1 - -2", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "1 -2", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "1 - 2 - 3", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "1- 2", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "020", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "-020", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "-020.1", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "20.010", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "20.011", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "A", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, ".", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "-", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, " ", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, ",", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\n", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\r", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\t", null));

  id = "gpa4.0";
  vc = vcs.get (id);
  assertNotNull (vc);
  assertNull (v.processValidCharConstraint ("test", vc, null, "0.0", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "4.0", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "1.0", null));
  // TODO: fix reg ex so it allows 4.0 but excludes 4.1, 4.2 etc...
//  assertNotNull (v.processValidCharConstraint ("test", vc, null, "4.1"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "1", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "1.0", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\n", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\r", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\t", null));

  id = "positive.integer";
  vc = vcs.get (id);
  assertNotNull (vc);
  assertNull (v.processValidCharConstraint ("test", vc, null, "1", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "22", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "33", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "0", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "1.0", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "-1", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\n", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\r", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\t", null));

  id = "relationalOperator";
  vc = vcs.get (id);
  assertNotNull (vc);
  assertNull (v.processValidCharConstraint ("test", vc, null, "=", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, ">", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "<", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, ">=", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "<=", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "<>", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "!=", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "=>", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "=<", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "0", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "1.0", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "-1", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\n", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\r", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\t", null));

  id = "alpha";
  vc = vcs.get (id);
  assertNotNull (vc);
  assertNull (v.processValidCharConstraint ("test", vc, null, "a", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "A", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "Z", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "zzzzz", null));
  assertNull (v.processValidCharConstraint ("test", vc, null,
                                            "abcdefghijklmnopqrstuvwxyz", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "1", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "0", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\n", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\r", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\t", null));

  id = "upperCase";
  vc = vcs.get (id);
  assertNotNull (vc);
  assertNull (v.processValidCharConstraint ("test", vc, null, "AB", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "A", null));
  assertNull (v.processValidCharConstraint ("test", vc, null,
                                            "ABCDEFGHIJKLMNOPQRSTUVWXYZ", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "-", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, ".", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "1", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "0", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "a", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\n", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\r", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\t", null));

  id = "upperAlpha";
  vc = vcs.get (id);
  assertNotNull (vc);
  assertNull (v.processValidCharConstraint ("test", vc, null, "AB", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "A", null));
  assertNull (v.processValidCharConstraint ("test", vc, null,
                                            "ABCDEFGHIJKLMNOPQRSTUVWXYZ", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "a", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "1", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "0", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\n", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\r", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\t", null));

  id = "lineText";
  vc = vcs.get (id);
  assertNotNull (vc);
  assertNull (v.processValidCharConstraint ("test", vc, null, "a", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "A", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "Z", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "Z", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "!", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "@", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "#", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "$", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "%", null));
  //TODO: maybe allow these special characters
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "^", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "&", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "*", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "(", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, ")", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "_", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "+", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "-", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "=", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "{", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "}", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "[", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "]", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "|", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "\\", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, ",", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, ".", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "/", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "<", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, ">", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "?", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "~", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "`", null));

  assertNull (v.processValidCharConstraint ("test", vc, null, "zzzzz", null));
  assertNull (v.processValidCharConstraint ("test", vc, null,
                                            "abcdefghijklmnopqrstuvwxyz", null));
  assertNull (v.processValidCharConstraint ("test", vc, null,
                                            "ABCDEFGHIJKLMNOPQRSTUVWXYZ", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "1", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "0", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\n", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\r", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "\t", null));

  id = "multiLineText";
  vc = vcs.get (id);
  assertNotNull (vc);
  assertNull (v.processValidCharConstraint ("test", vc, null, "a", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "A", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "Z", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "Z", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "!", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "@", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "#", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "$", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "%", null));
  //TODO: maybe allow these special characters
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "^", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "&", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "*", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "(", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, ")", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "_", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "+", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "-", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "=", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "{", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "}", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "[", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "]", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "|", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "\\", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, ",", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, ".", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "/", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "<", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, ">", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "?", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "~", null));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "`", null));

  assertNull (v.processValidCharConstraint ("test", vc, null, "zzzzz", null));
  assertNull (v.processValidCharConstraint ("test", vc, null,
                                            "abcdefghijklmnopqrstuvwxyz", null));
  assertNull (v.processValidCharConstraint ("test", vc, null,
                                            "ABCDEFGHIJKLMNOPQRSTUVWXYZ", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "1", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "0", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "\n", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "\r", null));
  assertNull (v.processValidCharConstraint ("test", vc, null, "\t", null));

  // If you get this error -- don't just change the number
  // also add a unit test for new valid chars definition that you must have added into the base dictionary!
  assertEquals (13, vcs.size ());
 }
}
