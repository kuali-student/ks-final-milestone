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
package org.kuali.student.dictionary.writer;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.kuali.student.dictionary.model.util.ModelFinder;
import org.kuali.student.dictionary.model.validation.DictionaryValidationException;
import org.kuali.student.dictionary.model.XmlType;
import org.kuali.student.dictionary.model.DictionaryModel;

/**
 *
 * @author nwright
 */
public class ServiceMessageStructureTypeCalculator
{

 public static String calculate (JavaClassWriter writer, DictionaryModel model,
                                 String type)
 {
  if (type.equalsIgnoreCase ("attributeInfoList"))
  {
   writer.imports.add (Map.class.getName ());
   return "Map<String,String>";
  }

  if (type.endsWith ("List"))
  {
   writer.imports.add (List.class.getName ());
   type = type.substring (0, type.length () - "List".length ());
   return "List<" + calculate (writer, model, type) + ">";
  }
  XmlType xmlType = new ModelFinder (model).findXmlType (type);
  if (xmlType == null)
  {
   throw new DictionaryValidationException ("No XmlType found for type " +
    type);
  }

  if (xmlType.getPrimitive ().equalsIgnoreCase ("Primitive"))
  {
   if (type.equalsIgnoreCase ("string"))
   {
    return "String";
   }
   if (type.equalsIgnoreCase ("date"))
   {
    writer.imports.add (Date.class.getName ());
    return "Date";
   }
   if (type.equalsIgnoreCase ("datetime"))
   {
    writer.imports.add (Date.class.getName ());
    return "Date";
   }
   if (type.equalsIgnoreCase ("boolean"))
   {
    return "Boolean";
   }
   if (type.equalsIgnoreCase ("integer"))
   {
    return "Integer";
   }
   if (type.equalsIgnoreCase ("long"))
   {
    return "Long";
   }
  }

  if (xmlType.getPrimitive ().equalsIgnoreCase ("Mapped String"))
  {
   return "String";
  }

  if (xmlType.getPrimitive ().equalsIgnoreCase (XmlType.COMPLEX))
  {
   return type.substring (0, 1).toUpperCase () + type.substring (1);
  }

  throw new DictionaryValidationException ("Unknown/unhandled xmlType.primtive value, " +
   xmlType.getPrimitive () + ", for type " + type);
 }
}
