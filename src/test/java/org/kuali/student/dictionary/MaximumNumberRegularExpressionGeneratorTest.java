/*
 * Copyright 2009 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.dictionary;

import java.util.regex.Pattern;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author nwright
 */
public class MaximumNumberRegularExpressionGeneratorTest
{

 public MaximumNumberRegularExpressionGeneratorTest ()
 {
 }

 @BeforeClass
 public static void setUpClass ()
  throws Exception
 {
 }

 @AfterClass
 public static void tearDownClass ()
  throws Exception
 {
 }

 @Before
 public void setUp ()
 {
 }

 @After
 public void tearDown ()
 {
 }

 /**
  * Test of generate method, of class MaximumNumberRegularExpressionGenerator.
  */
 @Test
 public void testGenerate ()
 {
  System.out.println ("generate");
  MaximumNumberRegularExpressionGenerator instance =
   new MaximumNumberRegularExpressionGenerator ();
  for (int maxNum = 0; maxNum < 1000; maxNum ++)
  {
   System.out.println ("Testing " + maxNum);
   String pattern = instance.generate (maxNum);
   Pattern p = Pattern.compile (pattern);
   for (int i = 0; i <= maxNum; i ++)
   {
    if ( ! p.matcher ("" + i).matches ())
    {
     fail (i + " does not match the pattern " + pattern + " for " + maxNum +
      "  but should");
    }
   }
   for (int i = maxNum + 1; i <= maxNum + 1000; i ++)
   {
    if (p.matcher ("" + i).matches ())
    {
     fail (i + " matchs the pattern " + pattern + " for " + maxNum +
      "  but should not");
    }
   }
  }
  assertTrue (true);
 }

}