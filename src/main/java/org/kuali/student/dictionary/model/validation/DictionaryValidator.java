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
import org.kuali.student.dictionary.model.Dictionary;
import org.kuali.student.dictionary.model.DictionaryModel;
import org.kuali.student.dictionary.model.Constraint;
import org.kuali.student.dictionary.model.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.kuali.student.dictionary.model.XmlType;

/**
 * This validates a single dictinoary entry
 * @author nwright
 */
public class DictionaryValidator implements ModelValidator
{

 private Dictionary dict;
 private DictionaryModel model;
 private ModelFinder finder;
 public DictionaryValidator (Dictionary dict, DictionaryModel model)
 {
  this.dict = dict;
  this.model = model;
  this.finder = new ModelFinder (model);
 }

 private Collection errors;

 @Override
 public Collection<String> validate ()
 {
  ConstraintValidator cv = new ConstraintValidator (dict.getInlineConstraint ());
  errors = cv.validate ();
  validateForDuplicates ();
  if (dict.getPrimitive ().equalsIgnoreCase (XmlType.COMPLEX))
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
  Constraint cons = new ModelFinder (this.model).findConstraint (id);
  if (cons != null)
  {
   return cons;
  }
  System.out.println ("id=["+ id + "]");
  if (id == null)
  {
   System.out.println ("id is null");
  }
  else if (id.equals (""))
   {
    System.out.println ("id is empty string");
   }
  else
  {
   int i = 0;
   for (byte b : id.getBytes ())
   {
    i++;
    System.out.println (i + ":" + b);
   }
  }
  addError ("Dictionary constraint id, " + id +
   " is not defined in the bank of constraints");
  return null;
 }

 private Field findField ()
 {
  Field field = finder.findField (dict);
  if (field != null)
  {
   return field;
  }
  addError ("Dictionary with id , " + dict.getId () +
   " does not have a corresponding field defined in the message structure.");
  return null;
 }

 private String getConstraintId (Constraint cons)
 {
  if (cons.getId ().equals (""))
  {
   return cons.getKey ();
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
