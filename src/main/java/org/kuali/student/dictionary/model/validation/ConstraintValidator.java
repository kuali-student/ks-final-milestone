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

import org.kuali.student.dictionary.model.Constraint;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This validates a constraint.
 * @author nwright
 */
public class ConstraintValidator implements ModelValidator
{

 private Constraint cons;

 public ConstraintValidator (Constraint cons)
 {
  this.cons = cons;
 }

 private Collection errors;

 @Override
 public Collection<String> validate ()
 {
  this.errors = new ArrayList ();
  validateMinMaxLength ();
  validateMinMaxOccurs ();
  validateMinMaxValue ();
  return this.errors;
 }

 private void validateMinMaxLength ()
 {
  if ( ! cons.getMinLength ().equals (""))
  {
   int min = parseIntError (cons.getMinLength (), "minLength");
   if (min < 0)
   {
    this.addError ("minLength is less than zero");
   }
   if ( ! cons.getMaxLength ().equals (""))
   {
    int max = parseIntError (cons.getMaxLength (), "maxLength");
    if (min < 0)
    {
     this.addError ("maxLength is less than zero");
    }

    if (min > max)
    {
     addError ("minLength exceeds the maxLength");
    }
   }
  }
 }

 private void validateMinMaxOccurs ()
 {
  if ( ! cons.getMinOccurs ().equals (""))
  {
   int min = parseIntError (cons.getMinOccurs (), "minOccurs");
   if (min < 0)
   {
    this.addError ("minOccurs is less than zero");
   }
   if ( ! cons.getMaxOccurs ().equals (""))
   {
    int max = parseIntError (cons.getMaxOccurs (), "maxOccurs");
    if (max < 0)
    {
     this.addError ("maxOccurs is less than zero");
    }
    if (min > max)
    {
     addError ("minOccurs exceeds the maxOccurs");
    }
   }
  }
 }

 private void validateMinMaxValue ()
 {
  if ( ! cons.getMinValue ().equals (""))
  {
   int min = parseIntError (cons.getMinValue (), "minValue");
   if (min < 0)
   {
    this.addError ("minValue is less than zero");
   }
   if ( ! cons.getMaxValue ().equals (""))
   {
    int max = parseIntError (cons.getMaxValue (), "maxValue");
    if (max < 0)
    {
     this.addError ("maxOccurs is less than zero");
    }
    if (min > max)
    {
     addError ("minOccurs exceeds the maxOccurs");
    }
   }
  }
 }

 private int parseIntError (String value, String field)
 {
  if (value.equals (""))
  {
   return 0;
  }
  try
  {
   int val = Integer.parseInt (value);
   return val;
  }
  catch (NumberFormatException ex)
  {
   addError (field + " is [" + value + "] not an integer");
   return 0;
  }
 }

 private void addError (String msg)
 {
  String id = cons.getId ();
  if (id.equals (""))
  {
   id = cons.getKey ();
  }
  String error = "Error in constraint " + id + ": " + msg;
  if ( ! errors.contains (error))
  {
   errors.add (error);
  }
 }

}
