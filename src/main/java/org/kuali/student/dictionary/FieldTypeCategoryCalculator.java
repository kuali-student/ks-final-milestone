/*
 * Copyright 2009 The Kuali Foundation
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
package org.kuali.student.dictionary;

import org.kuali.student.dictionary.OrchestrationObjectField.FieldTypeCategory;

/**
 *
 * @author nwright
 */
public class FieldTypeCategoryCalculator
{

 public static FieldTypeCategory calculate (OrchestrationObjectField field,
                                            boolean isAList,
                                            boolean mustBeInXmlTypes,
                                            DictionaryModel model)
 {
  if (field.getType ().equalsIgnoreCase ("attributeInfo"))
  {
   return FieldTypeCategory.DYNAMIC_ATTRIBUTE;
  }
  if (isAList)
  {
   return FieldTypeCategory.LIST;
  }
  if (field.getType ().equalsIgnoreCase ("Complex-Inline"))
  {
   return FieldTypeCategory.COMPLEX_INLINE;
  }
  XmlType xmlType = new ModelFinder (model).findXmlType (field.getType ());
  if (xmlType == null)
  {
   if (mustBeInXmlTypes)
   {
    throw new DictionaryValidationException ("No XmlType found for field type " +
     field.getType () + " " + field.getName ());
   }
   // if not found it must be a complex orchestration object
   return FieldTypeCategory.COMPLEX;
  }

  if (xmlType.getPrimitive ().equalsIgnoreCase ("Primitive"))
  {
   return FieldTypeCategory.PRIMITIVE;
  }

  if (xmlType.getPrimitive ().equalsIgnoreCase ("Mapped String"))
  {
   return FieldTypeCategory.MAPPED_STRING;
  }

  if (xmlType.getPrimitive ().equalsIgnoreCase ("Complex"))
  {
   return FieldTypeCategory.COMPLEX;
  }

  throw new DictionaryValidationException ("Unknown/unhandled xmlType.primtive value, " +
   xmlType.getPrimitive () + ", for field type " +
   field.getType () + " for field " + field.getName ());
 }

}
