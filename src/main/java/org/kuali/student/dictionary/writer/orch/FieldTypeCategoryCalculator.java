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
package org.kuali.student.dictionary.writer.orch;

import java.lang.reflect.Field;
import org.kuali.student.dictionary.model.util.ModelFinder;
import org.kuali.student.dictionary.model.validation.DictionaryValidationException;
import org.kuali.student.dictionary.model.XmlType;
import org.kuali.student.dictionary.model.DictionaryModel;
import org.kuali.student.dictionary.model.OrchestrationObjectField;
import org.kuali.student.dictionary.model.OrchestrationObjectField.FieldTypeCategory;

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
   FieldTypeCategory cat = calculate (field, false, mustBeInXmlTypes, model);
   switch (cat)
   {
    case PRIMITIVE:
     return FieldTypeCategory.LIST_OF_PRIMITIVE;
    case MAPPED_STRING:
     return FieldTypeCategory.LIST_OF_MAPPED_STRING;
    case COMPLEX:
     return FieldTypeCategory.LIST_OF_COMPLEX;
    case COMPLEX_INLINE:
     return FieldTypeCategory.LIST_OF_COMPLEX_INLINE;
    default:
    throw new DictionaryValidationException ("Lists of " + cat + "'s are not supported, field = "
     + " " + field.getName ());
   }
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

  if (xmlType.getPrimitive ().equalsIgnoreCase (XmlType.COMPLEX))
  {
   return FieldTypeCategory.COMPLEX;
  }

  throw new DictionaryValidationException ("Unknown/unhandled xmlType.primtive value, " +
   xmlType.getPrimitive () + ", for field type " +
   field.getType () + " for field " + field.getName ());
 }

}
