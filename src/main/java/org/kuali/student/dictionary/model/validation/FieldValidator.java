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
package org.kuali.student.dictionary.model.validation;

import org.kuali.student.dictionary.model.util.ModelFinder;
import org.kuali.student.dictionary.model.DictionaryModel;
import org.kuali.student.dictionary.model.Constraint;
import org.kuali.student.dictionary.model.Field;
import java.util.Collection;
import org.kuali.student.dictionary.model.XmlType;

/**
 * This validates a single field definition
 * @author nwright
 */
public class FieldValidator implements ModelValidator
{

 private Field field;
 private DictionaryModel model;

 public FieldValidator (Field field, DictionaryModel sheet)
 {
  this.field = field;
  this.model = sheet;
 }

 private Collection errors;

 @Override
 public Collection<String> validate ()
 {
  ConstraintValidator cv =
   new ConstraintValidator (field.getInlineConstraint ());
  errors = cv.validate ();
  if (field.getPrimitive ().equalsIgnoreCase (XmlType.COMPLEX))
  {
   validateComplexConstraint (field.getInlineConstraint ());
   for (String id : field.getConstraintIds ())
   {
    Constraint cons = findConstraint (id);
    if (cons != null)
    {
     validateComplexConstraint (cons);
    }
   }
  }
  return errors;
 }

 private Constraint findConstraint (String id)
 {
  Constraint cons = new ModelFinder (model).findConstraint (id);
  if (cons != null)
  {
   return cons;
  }
  addError ("Field constraint id, " + id +
   " is not defined in the bank of constraints");
  return null;
 }

 private void validateComplexConstraint (Constraint cons)
 {
  if ( ! cons.getMinLength ().equals (""))
  {
   addError ("A minLength is not allowed on a complex field");
  }
  if ( ! cons.getMaxLength ().equals (""))
  {
   addError ("A maxLength is not allowed on a complex field");
  }
  if ( ! cons.getMinValue ().equals (""))
  {
   addError ("A minValue is not allowed on a complex field");
  }
  if ( ! cons.getMaxValue ().equals (""))
  {
   addError ("A maxValue is not allowed on a complex field");
  }
  if ( ! cons.getValidChars ().equals (""))
  {
   addError ("A validChars is not allowed on a complex field");
  }
  if ( ! cons.getLookup ().equals (""))
  {
   addError ("A lookup value is not allowed on a complex field");
  }
 }

 private void addError (String msg)
 {
  String error = "Error in field " + field.getId () + ": " + msg;
  if ( ! errors.contains (error))
  {
   errors.add (error);
  }
 }

}
