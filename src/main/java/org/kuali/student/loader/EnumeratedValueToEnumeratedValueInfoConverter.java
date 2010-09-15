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

import java.util.Arrays;
import java.util.List;
import org.kuali.student.wsdl.enumerationmanagement.EnumeratedValueInfo;

/**
 *
 * @author nwright
 */
public class EnumeratedValueToEnumeratedValueInfoConverter
{


 private EnumeratedValue ev;

 public EnumeratedValueToEnumeratedValueInfoConverter (EnumeratedValue ev)
 {
  this.ev = ev;
 }

 public static final String ADMINISTRATION_ADMIN_ORG_TYPE = "kuali.adminOrg.type.Administration";
 public EnumeratedValueInfo convert ()
 {
  EnumeratedValueInfo info = new EnumeratedValueInfo ();
  info.setCode (ev.getCode ());
  info.setAbbrevValue (ev.getAbbrevValue ());
  info.setValue (ev.getValue ());
  info.setEffectiveDate (new DateHelper ().asXmlDate (ev.getEffectiveDate ()));
  info.setEffectiveDate (new DateHelper ().asXmlDate (ev.getExpirationDate ()));
  if (ev.getSortKey () != null)
  {
   info.setSortKey ("" + ev.getSortKey ());
  }
  return info;
 }


 private List<String> convertContexts (String contexts)
 {
  if (contexts == null)
  {
   return null;
  }
  return Arrays.asList (contexts.split (" "));
 }

}
