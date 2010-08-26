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
package org.kuali.student.dictionary.command.run;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import org.kuali.student.dictionary.DictionaryExecutionException;
import org.kuali.student.dictionary.model.MessageStructure;
import org.kuali.student.dictionary.model.Service;
import org.kuali.student.dictionary.model.ServiceMethod;
import org.kuali.student.dictionary.model.XmlType;
import org.kuali.student.dictionary.model.util.MessageStructureDumper;
import org.kuali.student.dictionary.model.util.ServiceDumper;
import org.kuali.student.dictionary.model.util.ServiceMethodDumper;
import org.kuali.student.dictionary.model.util.ServicesFilter;
import org.kuali.student.dictionary.model.util.ServicesFilterArrangeByKeys;
import org.kuali.student.dictionary.model.util.ServicesFilterByKeys;
import org.kuali.student.dictionary.model.util.ServicesFilterChained;
import org.kuali.student.dictionary.model.util.ServicesFilterExcludeDev;
import org.kuali.student.dictionary.model.util.ServicesFilterLatestVersionOnly;
import org.kuali.student.dictionary.model.util.XmlTypeDumper;
import org.kuali.student.dictionary.model.wiki.WikiServiceContractModelImpl;

/**
 *
 * @author nwright
 */
public class WikiServiceContractModelImplRun implements RunConstants
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
  "452588DE694E226763B940A1CC94C149.Kuali3_1Engine";

 public WikiServiceContractModelImplRun ()
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
//  serviceKeys.add ("atp");
//  serviceKeys.add ("comment");
//  serviceKeys.add ("dictionary");
//  serviceKeys.add ("enumerationmanagement");
//  serviceKeys.add ("lo");
//  serviceKeys.add ("lrc");
  serviceKeys.add ("organization");
//  serviceKeys.add ("person");
//  serviceKeys.add ("proposal");
//  serviceKeys.add ("refdocrelation");
//  serviceKeys.add ("resource");
//  serviceKeys.add ("search");
//  serviceKeys.add ("lu");
  List<ServicesFilter> filters = new ArrayList ();
  //filters.add (new ServicesFilterExcludeDev ());
  filters.add (new ServicesFilterLatestVersionOnly ());
  filters.add (new ServicesFilterByKeys (serviceKeys));
  filters.add (new ServicesFilterArrangeByKeys (serviceKeys));
  ServicesFilterChained filter = new ServicesFilterChained (filters);
  instance =
   new WikiServiceContractModelImpl (filter, SERVICE_REPOSITORY_PATH_ON_WIKI, JSESSIONID);
  return instance;
 }

 private PrintStream getOut (String fileName)
 {
  File file = new File (RESOURCES_DIRECTORY + "/" + fileName);
  OutputStream out;
  try
  {
   out = new FileOutputStream (file);
  }
  catch (FileNotFoundException ex)
  {
   throw new DictionaryExecutionException (ex);
  }
  PrintStream ps = new PrintStream (out);
  return ps;
 }

 public void testGetServices ()
 {
  System.out.println ("getServiceMethods");
  PrintStream out = getOut ("service.txt");
  new ServiceDumper (null, out).writeTabbedHeader ();
  List<Service> services = getInstance ().getServices ();
  for (Service service : services)
  {
   new ServiceDumper (service, out).writeTabbedData ();
  }
  //assertEquals (13, services.size ());
 }

 public void testGetServiceMethods ()
 {
  System.out.println ("getServiceMethods");
  PrintStream out = getOut ("service methods.txt");
  List<ServiceMethod> methods = instance.getServiceMethods ();
  new ServiceMethodDumper (null, out).writeTabbedHeader ();
  for (ServiceMethod method : methods)
  {
   new ServiceMethodDumper (method, out).writeTabbedData ();
  }
 }

 public void testGetMessageStructures ()
 {
  System.out.println ("getMessageStructures");
  PrintStream out = getOut ("message structures.txt");

  new MessageStructureDumper (null, out).writeTabbedHeader ();
  List<MessageStructure> msgs = getInstance ().getMessageStructures ();
  for (MessageStructure messageStructure : msgs)
  {
   new MessageStructureDumper (messageStructure, out).writeTabbedData ();
  }
 }

 public void testGetXmlTypes ()
 {
  System.out.println ("getXmlTypes");
  PrintStream out = getOut ("xml types.txt");

  new XmlTypeDumper (null, out).writeTabbedHeader ();
  List<XmlType> xmlTypes = getInstance ().getXmlTypes ();
  for (XmlType xmlType : xmlTypes)
  {
   new XmlTypeDumper (xmlType, out).writeTabbedData ();
  }
 }

 public static void main (String[] args)
 {
  WikiServiceContractModelImplRun tester =
   new WikiServiceContractModelImplRun ();
  tester.testGetServices ();
  tester.testGetMessageStructures ();
  tester.testGetServiceMethods ();
  tester.testGetXmlTypes ();
 }

}
