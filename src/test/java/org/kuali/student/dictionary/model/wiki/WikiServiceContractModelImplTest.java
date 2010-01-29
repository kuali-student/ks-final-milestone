/*
 * Copyright 2010 The Kuali Foundation
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
package org.kuali.student.dictionary.model.wiki;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.dictionary.TestConstants;
import org.kuali.student.dictionary.model.MessageStructure;
import org.kuali.student.dictionary.model.Service;
import org.kuali.student.dictionary.model.ServiceMethod;
import org.kuali.student.dictionary.model.XmlType;
import org.kuali.student.dictionary.model.util.MessageStructureDumper;
import org.kuali.student.dictionary.model.util.ServiceDumper;
import org.kuali.student.dictionary.model.util.ServiceMethodDumper;
import org.kuali.student.dictionary.model.util.XmlTypeDumper;
import static org.junit.Assert.*;

/**
 *
 * @author nwright
 */
public class WikiServiceContractModelImplTest implements TestConstants
{

 //           ***** NOTE *******
 // In firefox to get this you have to do the following:
 // Menu Toools/Options
 // Privacy Tab
 // Change "History/FireFox will" from "remmber history to "use custom settings for history"
 // Show Cookies button
 // Scoll down or search for to "test.kuali.org"
 // Select the JSESSIONID cookie
 // cut and paste the content here.
 // ==> the JSessionID changes everytime you drop out of the browser.
 public static final String JSESSIONID =
  "69164E6A77E6246078B6C92DB3504E1A.Kuali3_1Engine";

 public WikiServiceContractModelImplTest ()
 {
 }

 @BeforeClass
 public static void setUpClass ()
  throws Exception
 {
  getInstance ();
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

 private static WikiServiceContractModelImpl instance = null;

 private static WikiServiceContractModelImpl getInstance ()
 {
  if (instance != null)
  {
   return instance;
  }
  System.out.println ("getting instance of WikiServiceContractModelImpl");
  List<String> serviceKeys = new ArrayList ();
  serviceKeys.add ("atp");
  serviceKeys.add ("comment");
//  serviceKeys.add ("dictionary");
//  serviceKeys.add ("enumerationmanagement");
//  serviceKeys.add ("lo");
//  serviceKeys.add ("lrc");
//  serviceKeys.add ("lu");
//  serviceKeys.add ("organization");
//  serviceKeys.add ("person");
//  serviceKeys.add ("proposal");
//  serviceKeys.add ("refdocrelation");
//  serviceKeys.add ("resource");
//  serviceKeys.add ("search");
  instance =
   new WikiServiceContractModelImpl (serviceKeys, SERVICE_REPOSITORY_PATH_ON_WIKI, JSESSIONID);
  return instance;
 }



 @Test
 public void testGetServices ()
 {
  System.out.println ("getServiceMethods");

  List<Service> services = instance.getServices ();
  for (Service service : services)
  {
   new ServiceDumper (service, System.out).dump ();
  }
  //assertEquals (13, services.size ());
 }


 @Test
 public void testGetServiceMethods ()
 {
  System.out.println ("getServiceMethods");

  List<ServiceMethod> methods = instance.getServiceMethods ();
  for (ServiceMethod method : methods)
  {
   new ServiceMethodDumper (method, System.out).dump ();
  }
 }

 @Test
 public void testGetMessageStructures ()
 {
  System.out.println ("getMessageStructures");
  List<MessageStructure> msgs = instance.getMessageStructures ();
  for (MessageStructure messageStructure : msgs)
  {
   new MessageStructureDumper (messageStructure, System.out).dump ();
  }
 }


 @Test
 public void testGetXmlTypes ()
 {
  System.out.println ("getXmlTypes");
  List<XmlType> xmlTypes = instance.getXmlTypes ();
  for (XmlType xmlType : xmlTypes)
  {
   new XmlTypeDumper (xmlType, System.out).dump ();
  }
 }
}
