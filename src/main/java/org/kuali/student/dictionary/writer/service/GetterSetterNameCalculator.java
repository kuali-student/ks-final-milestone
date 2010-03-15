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
package org.kuali.student.dictionary.writer.service;

import org.kuali.student.dictionary.model.DictionaryModel;
import org.kuali.student.dictionary.model.MessageStructure;
import org.kuali.student.dictionary.writer.JavaClassWriter;

/**
 *
 * @author nwright
 */
public class GetterSetterNameCalculator
{

 private MessageStructure ms;
 private JavaClassWriter writer;
 private DictionaryModel model;

 public GetterSetterNameCalculator (MessageStructure ms,
                                    JavaClassWriter writer,
                                    DictionaryModel model)
 {
  this.ms = ms;
  this.writer = writer;
  this.model = model;
 }

 public String calcGetter ()
 {
  if (calcFieldTypeToUse (ms.getType ()).equals ("Boolean"))
  {
   if (ms.getShortName ().toLowerCase ().startsWith ("is"))
   {
    return calcInitLower (ms.getShortName ());
   }
   return "is" + calcInitUpper (ms.getShortName ());
  }
  return "get" + calcInitUpper (ms.getShortName ());
 }

 public String calcSetter ()
 {
   if (calcFieldTypeToUse (ms.getType ()).equals ("Boolean"))
  {
   if (ms.getShortName ().toLowerCase ().startsWith ("is"))
   {
    return "set" + calcInitUpper (ms.getShortName ().substring (2));
    }
   }
  return "set" + calcInitUpper (ms.getShortName ());
 }

 public static String calcInitUpper (String name)
 {
  return name.substring (0, 1).toUpperCase () + name.substring (1);
 }

 public static String calcInitLower (String name)
 {
  return name.substring (0, 1).toLowerCase () + name.substring (1);
 }


 public String calcFieldTypeToUse (String type)
 {
  return ServiceMessageStructureTypeCalculator.calculate (writer, model, type, null);
 }

}
