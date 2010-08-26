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
package org.kuali.student.dictionary.writer.dict;

import org.kuali.student.dictionary.writer.dict.ClassNameDecorator;
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
public class ClassNameDecoratorTest
{

 public ClassNameDecoratorTest ()
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
  * Test of decorate method, of class ClassNameDecorator.
  */
 @Test
 public void testDecorate ()
 {
  System.out.println ("decorate");

  List<String> input = new ArrayList ();
  List<String> output = new ArrayList ();

  input.add ("core.auth.calculation.AuthenticatedPrincipal");
  output.add ("org.kuali.student.core.auth.calculation.AuthenticatedPrincipalCalculator");
  input.add ("core.auth.validation.PrincipaldExists");
  output.add ("org.kuali.student.core.auth.validation.PrincipaldExistsValidator");
  input.add ("core.calculation.HardCodedValue");
  output.add ("org.kuali.student.core.calculation.HardCodedValueCalculator");
  input.add ("core.calculation.Now");
  output.add ("org.kuali.student.core.calculation.NowCalculator");
  input.add ("core.calculation.PlainText");
  output.add ("org.kuali.student.core.calculation.PlainTextCalculator");
  input.add ("core.calculation.Today");
  output.add ("org.kuali.student.core.calculation.TodayCalculator");
  input.add ("core.calculationVersionInd");
  output.add ("org.kuali.student.core.calculationVersionIndCalculator");
  input.add ("core.document.validation.DocumentIdExists");
  output.add ("org.kuali.student.core.document.validation.DocumentIdExistsValidator");
  input.add ("core.org.validation.OrgIdExists");
  output.add ("org.kuali.student.core.org.validation.OrgIdExistsValidator");
  input.add ("core.person.validation.PersonIddExists");
  output.add ("org.kuali.student.core.person.validation.PersonIddExistsValidator");
  input.add ("core.proposal.validation.ProposalIdExists");
  output.add ("org.kuali.student.core.proposal.validation.ProposalIdExistsValidator");
  input.add ("core.validation.KualiId");
  output.add ("org.kuali.student.core.validation.KualiIdValidator");
  input.add ("core.validation.KualiMetaData");
  output.add ("org.kuali.student.core.validation.KualiMetaDataValidator");
  input.add ("core.validation.KualiType");
  output.add ("org.kuali.student.core.validation.KualiTypeValidator");
  input.add ("lum.lrc.validation.credentialExists");
  output.add ("org.kuali.student.lum.lrc.validation.credentialExistsValidator");
  input.add ("lum.lrc.validation.CreditExists");
  output.add ("org.kuali.student.lum.lrc.validation.CreditExistsValidator");
  input.add ("lum.lrc.validation.GradeExists");
  output.add ("org.kuali.student.lum.lrc.validation.GradeExistsValidator");
  input.add ("lum.lrc.validation.ResultComponentExists");
  output.add ("org.kuali.student.lum.lrc.validation.ResultComponentExistsValidator");
  input.add ("lum.lrc.validation.ResultValueObjectExists");
  output.add ("org.kuali.student.lum.lrc.validation.ResultValueObjectExistsValidator");
  input.add ("lum.lrd.validation.LearningResultDefinitionIdExists");
  output.add ("org.kuali.student.lum.lrd.validation.LearningResultDefinitionIdExistsValidator");
  input.add ("lum.lu.calculation.CopyOfficialCourseNumberPart");
  output.add ("org.kuali.student.lum.lu.calculation.CopyOfficialCourseNumberPartCalculator");
  input.add ("lum.lu.calculation.CopyOfficialCourseSubjectArea");
  output.add ("org.kuali.student.lum.lu.calculation.CopyOfficialCourseSubjectAreaCalculator");
  input.add ("lum.lu.calculation.CourseNo");
  output.add ("org.kuali.student.lum.lu.calculation.CourseNoCalculator");
  input.add ("lum.lu.validation.CluIdExists");
  output.add ("org.kuali.student.lum.lu.validation.CluIdExistsValidator");
  input.add ("lum.lu.validation.CluSetIdExists");
  output.add ("org.kuali.student.lum.lu.validation.CluSetIdExistsValidator");
  input.add ("lum.lu.validation.LuStatementIdExists");
  output.add ("org.kuali.student.lum.lu.validation.LuStatementIdExistsValidator");
  input.add ("lum.lu.validation.RequirementComponentIdExists");
  output.add ("org.kuali.student.lum.lu.validation.RequirementComponentIdExistsValidator");
  input.add ("edu.mit.student.SpecialValidation");
  output.add ("edu.mit.student.SpecialValidation");

  for (int i = 0; i < input.size (); i ++)
  {
   String className = input.get (i);
   ClassNameDecorator instance = new ClassNameDecorator (className);
   String result = instance.decorate ();
   assertEquals (className + "=>" + output.get (i), className + "=>" + result);
  }
 }

}