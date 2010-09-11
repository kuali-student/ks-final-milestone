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
package org.kuali.student.loader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import org.kuali.student.wsdl.course.CourseInfo;
import org.kuali.student.wsdl.course.DataValidationErrorException;

/**
 *
 * @author nwright
 */
public class CreditCourseLoader
{

 private CrsService crsService;

 public CrsService getCrsService ()
 {
  return crsService;
 }

 public void setCrsService (CrsService crsService)
 {
  this.crsService = crsService;
 }

 public CreditCourseLoader ()
 {
 }
 private Iterator<CreditCourse> inputDataSource;

 public Iterator<CreditCourse> getInputDataSource ()
 {
  return inputDataSource;
 }

 public void setInputDataSource (Iterator<CreditCourse> inputDataSource)
 {
  this.inputDataSource = inputDataSource;
 }

 public List<CreditCourseLoadResult> update ()
 {
  List<CreditCourseLoadResult> results = new ArrayList (500);
  int row = 0;
  while (inputDataSource.hasNext ())
  {
   CreditCourseLoadResult result = new CreditCourseLoadResult ();
   results.add (result);
   CreditCourse cc = inputDataSource.next ();
   row ++;
   CourseInfo info = new CreditCourseToCourseInfoConverter (cc).convert ();
   result.setRow (row);
   result.setCreditCourse (cc);
   result.setCourseInfo (info);
   try
   {
    CourseInfo createdInfo = crsService.createCourse (info);
    result.setCourseInfo (createdInfo);
    result.setSuccess (true);
   }
   catch (DataValidationErrorException ex)
   {
    result.setSuccess (false);
    result.setDataValidationErrorException (ex.getFaultInfo ());
   }
   catch (Exception ex)
   {
    result.setSuccess (false);
    result.setException (ex);
   }
  }
  return results;
 }

 public static CreditCourseLoaderModelFactory getInstance (String excelFile)
 {
  Properties props = new Properties ();
  props.putAll (CreditCourseLoaderModelFactory.getDefaultConfig ());
  props.put (CreditCourseLoaderModelFactory.EXCEL_FILES_DEFAULT_DIRECTORY_KEY, "src/main/"
   + CreditCourseLoaderModelFactory.RESOURCES_DIRECTORY);
  props.put (CreditCourseLoaderModelFactory.SERVICE_HOST_URL, "src/main/"
   + CreditCourseLoaderModelFactory.RESOURCES_DIRECTORY);
  System.out.println ("Current Directory=" + System.getProperty ("user.dir"));
  CreditCourseLoaderModelFactory factory = new CreditCourseLoaderModelFactory ();
  factory.setConfig (props);
  return factory;
 }

 


}
