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

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import org.kuali.student.wsdl.course.CourseInfo;
import org.kuali.student.wsdl.course.CourseService;
import org.kuali.student.wsdl.course.CourseService_Service;
import org.kuali.student.wsdl.course.DoesNotExistException;
import org.kuali.student.wsdl.course.StatusInfo;

/**
 *
 * @author nwright
 */
public class CrsService
{

 private static final String COURSE_SERVICE_NAME = "CourseService";
 private static final QName COURSE_SERVICE_QNAME =
                            CourseService_Service.SERVICE;
 private String hostUrl;

 public String getHostUrl ()
 {
  return hostUrl;
 }

 public void setHostUrl (String hostUrl)
 {
  this.hostUrl = hostUrl;
 }

 public String calcWsdlUrl (String serviceName)
 {
  String url = getHostUrl () + "/services/" + serviceName + "?wsdl";
//  System.out.println ("url is " + url);
  return url;
 }

 private CourseService getCourseService ()
 {
  URL wsdlURL;
  try
  {
   wsdlURL = new URL (calcWsdlUrl (COURSE_SERVICE_NAME));
  }
  catch (MalformedURLException ex)
  {
   throw new IllegalArgumentException (ex);
  }

//  System.out.println (wsdlURL);
  CourseService_Service oss =
                        new CourseService_Service (wsdlURL,
                                                   COURSE_SERVICE_QNAME);
  CourseService port = oss.getCourseServicePort ();
  return port;
 }

 public CourseInfo getCourse (String id)
   throws DoesNotExistException
 {
  CourseService port = getCourseService ();
//  System.out.println ("Invoking get course request...");
  CourseInfo result = null;
  try
  {
   result = port.getCourse (id);
  }
  catch (DoesNotExistException ex)
  {
   throw ex;
  }
  catch (Exception ex)
  {
   throw new RuntimeException (ex);
  }
  return result;
 }

 public CourseInfo updateCourse (CourseInfo info)
   throws org.kuali.student.wsdl.course.DataValidationErrorException
 {
  CourseService port = getCourseService ();
//  System.out.println ("Invoking create course request...");
  CourseInfo result = null;
  try
  {
   result = port.updateCourse (info);
  }
  catch (org.kuali.student.wsdl.course.DataValidationErrorException ex)
  {
   throw ex;
  }
  catch (Exception ex)
  {
   throw new RuntimeException (ex);
  }
  return result;
 }

 public CourseInfo createCourse (CourseInfo info)
   throws org.kuali.student.wsdl.course.DataValidationErrorException
 {
  CourseService port = getCourseService ();
//  System.out.println ("Invoking create course request...");
  CourseInfo result = null;
  try
  {
   result = port.createCourse (info);
  }
  catch (org.kuali.student.wsdl.course.DataValidationErrorException ex)
  {
   throw ex;
  }
  catch (Exception ex)
  {
   throw new RuntimeException (ex);
  }
  return result;
 }

 public boolean deleteCourse (String id)
   throws org.kuali.student.wsdl.course.DataValidationErrorException
 {
  CourseService port = getCourseService ();
//  System.out.println ("Invoking delete course request...");
  StatusInfo result = null;
  try
  {
   result = port.deleteCourse (id);
  }
  catch (org.kuali.student.wsdl.course.DataValidationErrorException ex)
  {
   throw ex;
  }
  catch (Exception ex)
  {
   throw new RuntimeException (ex);
  }
  return result.isSuccess ();
 }
}
