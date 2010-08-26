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

import org.kuali.student.wsdl.course.RichTextInfo;




/**
 *
 * @author nwright
 */
public class RichTextInfoHelper
{


 public RichTextInfo get (String formatted, String plain)
 {
  if (plain == null)
  {
   return null;
  }
  RichTextInfo rt = new RichTextInfo ();
  rt.setFormatted (formatted);
  rt.setPlain (plain);
  return rt;
 }

 public RichTextInfo getFromFormatted (String formatted)
 {
  return get (formatted, calcPlain (formatted));
 }

 public RichTextInfo getFromPlain (String plain)
 {
  return get (calcFormatted (plain), plain);
 }

 public String calcPlain (String formatted)
 {
  // TODO: Implement logic to strip out formatting info
  return formatted;
 }

 public String calcFormatted (String plain)
 {
  if (plain == null)
  {
   return null;
  }
  // TODO: Implement logic to convert to formatted info
  String formatted = plain.replaceAll ("\n", "<br>");
  return formatted;
 }

}
