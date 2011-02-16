package org.kuali.student.core.dictionary.service;

import java.util.Map;
import org.junit.Test;
import org.kuali.student.common.dictionary.dto.ValidCharsConstraint;
import org.kuali.student.common.validator.DefaultValidatorImpl;
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
  assertNull (v.processValidCharConstraint ("test", vc, null, "1"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "A"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "."));
  assertNull (v.processValidCharConstraint ("test", vc, null, "-"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, " "));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, ","));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\n"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\r"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\t"));

  id = "digits";
  vc = vcs.get (id);
  assertNotNull (vc);
  assertNull (v.processValidCharConstraint ("test", vc, null, "1"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "A"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "."));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "-"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, " "));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, ","));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\n"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\r"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\t"));

  id = "numeric";
  vc = vcs.get (id);
  assertNotNull (vc);
  assertNull (v.processValidCharConstraint ("test", vc, null, "0"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "1"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "20"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "-20"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "-12345620"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "20.1"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "20.0"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "20.01"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "20.00"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "120.00"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "1120.00"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "11120.00"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "111120.00"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "1111120.00"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "11111120.00"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "020"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "-020"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "-020.1"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "20.010"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "20.011"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "A"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "."));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "-"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, " "));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, ","));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\n"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\r"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\t"));

  id = "numeric.range";
  vc = vcs.get (id);
  assertNotNull (vc);
  assertNull (v.processValidCharConstraint ("test", vc, null, "0"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "1"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "20"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "-20"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "-12345620"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "20.1"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "20.0"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "20.01"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "20.00"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "120.00"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "1120.00"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "11120.00"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "111120.00"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "1111120.00"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "11111120.00"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "1-2"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "1.1-2.2"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "1 - 2"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "1--2"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "1 - -2"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "-1 - -2"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "1 -2"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "1 - 2 - 3"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "1- 2"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "020"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "-020"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "-020.1"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "20.010"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "20.011"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "A"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "."));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "-"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, " "));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, ","));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\n"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\r"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\t"));

  id = "positive.numeric.range";
  vc = vcs.get (id);
  assertNotNull (vc);
  assertNull (v.processValidCharConstraint ("test", vc, null, "0"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "1"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "20"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "-20"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "-12345620"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "20.1"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "20.0"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "20.01"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "20.00"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "120.00"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "1120.00"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "11120.00"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "111120.00"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "1111120.00"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "11111120.00"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "1-2"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "1.1-2.2"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "1 - 2"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "1--2"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "1 - -2"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "-1 - -2"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "1 -2"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "1 - 2 - 3"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "1- 2"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "020"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "-020"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "-020.1"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "20.010"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "20.011"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "A"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "."));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "-"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, " "));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, ","));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\n"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\r"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\t"));

  id = "gpa4.0";
  vc = vcs.get (id);
  assertNotNull (vc);
  assertNull (v.processValidCharConstraint ("test", vc, null, "0.0"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "4.0"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "1.0"));
  // TODO: fix reg ex so it allows 4.0 but excludes 4.1, 4.2 etc...
//  assertNotNull (v.processValidCharConstraint ("test", vc, null, "4.1"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "1"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "1.0"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\n"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\r"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\t"));

  id = "positive.integer";
  vc = vcs.get (id);
  assertNotNull (vc);
  assertNull (v.processValidCharConstraint ("test", vc, null, "1"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "22"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "33"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "0"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "1.0"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "-1"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\n"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\r"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\t"));

  id = "relationalOperator";
  vc = vcs.get (id);
  assertNotNull (vc);
  assertNull (v.processValidCharConstraint ("test", vc, null, "="));
  assertNull (v.processValidCharConstraint ("test", vc, null, ">"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "<"));
  assertNull (v.processValidCharConstraint ("test", vc, null, ">="));
  assertNull (v.processValidCharConstraint ("test", vc, null, "<="));
  assertNull (v.processValidCharConstraint ("test", vc, null, "<>"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "!="));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "=>"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "=<"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "0"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "1.0"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "-1"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\n"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\r"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\t"));

  id = "alpha";
  vc = vcs.get (id);
  assertNotNull (vc);
  assertNull (v.processValidCharConstraint ("test", vc, null, "a"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "A"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "Z"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "zzzzz"));
  assertNull (v.processValidCharConstraint ("test", vc, null,
                                            "abcdefghijklmnopqrstuvwxyz"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "1"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "0"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\n"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\r"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\t"));

  id = "upperCase";
  vc = vcs.get (id);
  assertNotNull (vc);
  assertNull (v.processValidCharConstraint ("test", vc, null, "AB"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "A"));
  assertNull (v.processValidCharConstraint ("test", vc, null,
                                            "ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "-"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "."));
  assertNull (v.processValidCharConstraint ("test", vc, null, "1"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "0"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "a"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\n"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\r"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\t"));

  id = "upperAlpha";
  vc = vcs.get (id);
  assertNotNull (vc);
  assertNull (v.processValidCharConstraint ("test", vc, null, "AB"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "A"));
  assertNull (v.processValidCharConstraint ("test", vc, null,
                                            "ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "a"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "1"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "0"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\n"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\r"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\t"));

  id = "lineText";
  vc = vcs.get (id);
  assertNotNull (vc);
  assertNull (v.processValidCharConstraint ("test", vc, null, "a"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "A"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "Z"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "Z"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "!"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "@"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "#"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "$"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "%"));
  //TODO: maybe allow these special characters
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "^"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "&"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "*"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "("));
  assertNull (v.processValidCharConstraint ("test", vc, null, ")"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "_"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "+"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "-"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "="));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "{"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "}"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "["));
  assertNull (v.processValidCharConstraint ("test", vc, null, "]"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "|"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "\\"));
  assertNull (v.processValidCharConstraint ("test", vc, null, ","));
  assertNull (v.processValidCharConstraint ("test", vc, null, "."));
  assertNull (v.processValidCharConstraint ("test", vc, null, "/"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "<"));
  assertNull (v.processValidCharConstraint ("test", vc, null, ">"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "?"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "~"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "`"));

  assertNull (v.processValidCharConstraint ("test", vc, null, "zzzzz"));
  assertNull (v.processValidCharConstraint ("test", vc, null,
                                            "abcdefghijklmnopqrstuvwxyz"));
  assertNull (v.processValidCharConstraint ("test", vc, null,
                                            "ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "1"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "0"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\n"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "\r"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "\t"));

  id = "multiLineText";
  vc = vcs.get (id);
  assertNotNull (vc);
  assertNull (v.processValidCharConstraint ("test", vc, null, "a"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "A"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "Z"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "Z"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "!"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "@"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "#"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "$"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "%"));
  //TODO: maybe allow these special characters
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "^"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "&"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "*"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "("));
  assertNull (v.processValidCharConstraint ("test", vc, null, ")"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "_"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "+"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "-"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "="));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "{"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "}"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "["));
  assertNull (v.processValidCharConstraint ("test", vc, null, "]"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "|"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "\\"));
  assertNull (v.processValidCharConstraint ("test", vc, null, ","));
  assertNull (v.processValidCharConstraint ("test", vc, null, "."));
  assertNull (v.processValidCharConstraint ("test", vc, null, "/"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "<"));
  assertNull (v.processValidCharConstraint ("test", vc, null, ">"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "?"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "~"));
  assertNotNull (v.processValidCharConstraint ("test", vc, null, "`"));

  assertNull (v.processValidCharConstraint ("test", vc, null, "zzzzz"));
  assertNull (v.processValidCharConstraint ("test", vc, null,
                                            "abcdefghijklmnopqrstuvwxyz"));
  assertNull (v.processValidCharConstraint ("test", vc, null,
                                            "ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "1"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "0"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "\n"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "\r"));
  assertNull (v.processValidCharConstraint ("test", vc, null, "\t"));

  // If you get this error -- don't just change the number
  // also add a unit test for new valid chars definition that you must have added into the base dictionary!
  assertEquals (13, vcs.size ());
 }
}
