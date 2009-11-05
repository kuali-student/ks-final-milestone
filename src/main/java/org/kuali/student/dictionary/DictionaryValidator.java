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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This validates a single dictinoary entry
 * @author nwright
 */
public class DictionaryValidator implements ModelValidator
{

 private Dictionary dict;
 private DictionaryModel sheet;

 public DictionaryValidator (Dictionary dict, DictionaryModel sheet)
 {
  this.dict = dict;
  this.sheet = sheet;
 }

 private Collection errors;

 @Override
 public Collection<String> validate ()
 {
  ConstraintValidator cv = new ConstraintValidator (dict.getInlineConstraint ());
  errors = cv.validate ();
  validateForDuplicates ();
  if (dict.getPrimitive ().equals ("Complex"))
  {
   for (Constraint cons : getAllConstraints ())
   {
    validateComplexConstraint (cons);
   }
  }
  return errors;
 }

 private List<Constraint> getAllConstraints ()
 {
  List<Constraint> list = getFieldNamedConstraints ();
  Field field = findField ();
  if (field != null)
  {
   list.add (field.getInlineConstraint ());
  }
  list.addAll (getDictionaryAdditionalConstraints ());
  list.add (dict.getInlineConstraint ());
  return list;
 }

  private List<Constraint> getAllNamedConstraints ()
 {
  List<Constraint> list = getFieldNamedConstraints ();
  list.addAll (getDictionaryAdditionalConstraints ());
  return list;
 }


 private List<Constraint> getFieldNamedConstraints ()
 {
  List<Constraint> list = new ArrayList ();
  Field field = findField ();
  if (field != null)
  {
   for (String id : field.getConstraintIds ())
   {
    Constraint cons = findConstraint (id);
    if (cons != null)
    {
     list.add (cons);
    }
   }
  }
  return list;
 }

 private List<Constraint> getDictionaryAdditionalConstraints ()
 {
  List<Constraint> list = new ArrayList ();
  for (String id : dict.getAdditionalConstraintIds ())
  {
   Constraint cons = findConstraint (id);
   if (cons != null)
   {
    list.add (cons);
   }
  }
  list.add (dict.getInlineConstraint ());
  return list;
 }

 private Constraint findConstraint (String id)
 {
  for (Constraint cons : this.sheet.getConstraints ())
  {
   if (cons.getId ().equals (id))
   {
    return cons;
   }
  }
  addError ("Constraint id, " + id +
   " is not defined in the list of constraints");
  return null;
 }

 private Field findField ()
 {
  String id = dict.getXmlObject () + "." + dict.getShortName ();
  for (Field field : this.sheet.getFields ())
  {
   if (field.getId ().equals (id))
   {
    return field;
   }
  }
  addError ("Field id, " + id +
   " is not defined in the list of fields");
  return null;
 }

 private String getConstraintId (Constraint cons)
 {
  if (cons.getId ().equals (""))
  {
   return "in-line constraint";
  }
  return cons.getId ();
 }

 private void validateComplexConstraint (Constraint cons)
 {
  if ( ! cons.getMinLength ().equals (""))
  {
   addError (getConstraintId (cons) +
    " has a minLength which does not make sense on a complex field");
  }
  if ( ! cons.getMaxLength ().equals (""))
  {
   addError (getConstraintId (cons) +
    " has a maxLength which does not make sense on a complex field");
  }
  if ( ! cons.getMinValue ().equals (""))
  {
   addError (getConstraintId (cons) +
    " has a minValue which does not make sense on a complex field");
  }
  if ( ! cons.getMaxValue ().equals (""))
  {
   addError (getConstraintId (cons) +
    " has a maxValue which does not make sense on a complex field");
  }
  if ( ! cons.getValidChars ().equals (""))
  {
   addError (getConstraintId (cons) +
    " has validChars which does not make sense on a complex field");
  }
  if ( ! cons.getLookup ().equals (""))
  {
   addError (getConstraintId (cons) +
    " has a lookup which does not make sense on a complex field");
  }
 }

 private void validateForDuplicates ()
 {
  Set<String> ids = new HashSet ();
  for (Constraint cons : getAllNamedConstraints ())
  {
   if ( ! ids.add (cons.getId ()))
   {
    // optional is OK to be duplicated because it is is used to override
    // the 'required' setting
    if ( ! cons.getId ().equals ("optional"))
    {
     addError ("Constraint with id of [" + cons.getId () + "] is duplicated");
    }
   }
  }
 }

 private void addError (String msg)
 {
  String error = "Error in dictionary entry: " + dict.getId () + ": " + msg;
  if ( ! errors.contains (error))
  {
   errors.add (error);
  }
 }

}
