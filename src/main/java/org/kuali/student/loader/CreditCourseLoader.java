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

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.lum.lu.dto.AdminOrgInfo;
import org.kuali.student.lum.lu.dto.CluIdentifierInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.wsdl.course.CourseInfo;

/**
 *
 * @author nwright
 */
public class CreditCourseLoader
{

 public CreditCourseLoader ()
 {
 }

 private Iterator<CreditCourse> source;

 public Iterator<CreditCourse> getSource ()
 {
  return source;
 }

 public void setSource (Iterator<CreditCourse> source)
 {
  this.source = source;
 }

 public int update ()
 {
 
  int row = 0;
  int written = 0;
  while (source.hasNext ())
  {
   CreditCourse cc = source.next ();
   row ++;
   written ++;
   CourseInfo info = new CreditCourseToCourseInfoConverter (cc).convert ();
   create (info);
  }
  return written;
 }

 private void create (CourseInfo info)
 {
  System.out.println ("creating course");
 }

 
  
 private String asString (String value)
 {
  if (value == null)
  {
   return "null";
  }
  return escape (value.toString ());
 }

 private String escape (String value)
 {
  if (value == null)
  {
   return null;
  }
  if (value.indexOf ("'") == -1)
  {
   return "'" + value + "'";
  }
  StringBuilder builder = new StringBuilder (value.length () + 2);
  for (int i = 0; i < value.length (); i ++)
  {
   char c = value.charAt (i);
   if (c == '\'')
   {
    builder.append ('\'');
   }
   builder.append (c);
  }
  return "'" + builder.toString () + "'";
 }

 private String asDate (Date value)
 {
  if (value == null)
  {
   return "null";
  }
  SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd");
  return "to_date ('" + sdf.format (value) + "', 'YYYY-MM-DD')";
 }

 private String asNumber (String value)
 {
  if (value == null)
  {
   return "null";
  }
  int numb = Integer.parseInt (value);
  return numb + "";
 }

 private String asNumber (Number value)
 {
  if (value == null)
  {
   return "null";
  }
  return value.toString ();
 }

 private String asBoolean (Boolean value)
 {
  if (value == null)
  {
   return "null";
  }
  if (value)
  {
   return "1";
  }
  return "0";
 }

}
