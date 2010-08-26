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
package org.kuali.student.dictionary.model.util;

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
public class GroupTypeStatePatternMatcherTest
{

 public GroupTypeStatePatternMatcherTest ()
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
  * Test of matches method, of class GroupTypeStatePatternMatcher.
  */
 @Test
 public void testMatches ()
 {
  System.out.println ("matches");
  GroupTypeStatePatternMatcher matcher = null;  
  matcher = new GroupTypeStatePatternMatcher ("kuali.type.*");
  assertEquals (true, matcher.matches ("kuali.type.foo"));
  assertEquals (false, matcher.matches ("kuali.lu.type.foo"));

  matcher = new GroupTypeStatePatternMatcher ("kuali.type.a,kuali.type.b");
  assertEquals (true, matcher.matches ("kuali.type.a"));
  assertEquals (true, matcher.matches ("kuali.type.b"));
  assertEquals (false, matcher.matches ("kuali.type.foo"));

  matcher = new GroupTypeStatePatternMatcher ("kuali.type.a.*,kuali.type.b");
  assertEquals (true, matcher.matches ("kuali.type.a.foo"));
  assertEquals (true, matcher.matches ("kuali.type.b"));
  assertEquals (false, matcher.matches ("kuali.type.foo"));

  matcher = new GroupTypeStatePatternMatcher ("*");
  assertEquals (true, matcher.matches ("kuali.type.a.foo"));
  assertEquals (true, matcher.matches ("kuali.type.b"));
  assertEquals (true, matcher.matches ("kuali.type.foo"));

  matcher = new GroupTypeStatePatternMatcher ("kuali.type.b");
  assertEquals (false, matcher.matches ("kuali.type.a.foo"));
  assertEquals (true, matcher.matches ("kuali.type.b"));
  assertEquals (false, matcher.matches ("kuali.type.before"));
 }

}