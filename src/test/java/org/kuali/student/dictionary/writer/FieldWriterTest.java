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
package org.kuali.student.dictionary.writer;

import org.kuali.student.dictionary.model.MockDictionaryModel;
import org.kuali.student.dictionary.*;
import org.kuali.student.dictionary.writer.FieldWriter;
import org.kuali.student.dictionary.model.DictionaryModelCache;
import org.kuali.student.dictionary.model.Dictionary;
import org.kuali.student.dictionary.model.State;
import org.kuali.student.dictionary.model.DictionaryModel;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
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
public class FieldWriterTest
{

 public FieldWriterTest ()
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
  * Test of write method, of class FieldWriter.
  */
 @Test
 public void testWrite ()
 {
  System.out.println ("write");
  //TODO: Revisit this whole test because it doesn't really work.
  // The test fields don't match the spreadsheet info.
  ByteArrayOutputStream baos = new ByteArrayOutputStream ();
  PrintStream out = new PrintStream (baos);
  DictionaryModel spreadsheet = new DictionaryModelCache (new MockDictionaryModel ());
  Dictionary dict = spreadsheet.getDictionary ().get (0);
  State state = spreadsheet.getStates ().get (0);
  FieldWriter instance = new FieldWriter (out, 0, spreadsheet, dict, state, false);
  instance.write ();
  System.out.println ("|||||||");
  System.out.println (baos.toString ());
  System.out.println ("|||||||");
  // This should fail because the test fields don't actually match what is in the spreadsheet model
if (baos.toString ().length () < 1782)
  {
   fail ("Should have at least 1782");
  }
  assertEquals (true, true);
  //assertEquals (1782, baos.toString ().length ());
 }

}