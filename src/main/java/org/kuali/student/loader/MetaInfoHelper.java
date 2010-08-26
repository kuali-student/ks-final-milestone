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

import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.kuali.student.wsdl.course.MetaInfo;



/**
 *
 * @author nwright
 */
public class MetaInfoHelper
{
 public MetaInfo get ()
 {
  MetaInfo info = new MetaInfo ();
  info.setCreateId ("LOADER");
  info.setUpdateId ("LOADER");
  XMLGregorianCalendar now;
  try
  {
   now =
   DatatypeFactory.newInstance ().newXMLGregorianCalendar (new GregorianCalendar ());
  }
  catch (DatatypeConfigurationException ex)
  {
   throw new IllegalArgumentException (ex);
  }
  info.setCreateTime (now);
  info.setUpdateTime (now);
  info.setVersionInd ("1");
  return info;
 }

}
