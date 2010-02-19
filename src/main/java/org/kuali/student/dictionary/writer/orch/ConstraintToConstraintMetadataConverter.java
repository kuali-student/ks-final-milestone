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

import org.kuali.student.dictionary.writer.dict.ConstraintWriter;
import org.kuali.student.dictionary.model.validation.DictionaryValidationException;
import org.kuali.student.dictionary.model.Constraint;
import org.kuali.student.core.assembly.data.ConstraintMetadata;

/**
 *
 * @author nwright
 */
public class ConstraintToConstraintMetadataConverter
{

 private Constraint cons;

 public ConstraintToConstraintMetadataConverter (Constraint cons)
 {
  this.cons = cons;
 }

 public ConstraintMetadata convert ()
 {
  ConstraintMetadata meta = new ConstraintMetadata ();
  meta.setId (asString (cons.getId ()));
  meta.setMessageId (asString (cons.getMessageId ()));
  meta.setDesc (asString (cons.getDesc ()));
  meta.setComments (asString (cons.getComments ()));
  meta.setMinLength (asInteger (cons.getMinLength ()));
  meta.setMaxLength (asInteger (cons.getMaxLength ()));
  meta.setMinOccurs (asInteger (cons.getMinOccurs ()));
  meta.setMaxOccurs (asInteger (cons.getMaxOccurs ()));
  meta.setMinValue (asString (cons.getMinValue ()));
  meta.setMaxValue (asString (cons.getMaxValue ()));
  meta.setServerSide (asBoolean (cons.getServerSide ()));
  meta.setSpecialValidator (asString (cons.getClassName ()));
  meta.setValidChars (asString (ConstraintWriter.calcValidChars (cons.getValidChars ())));
  return meta;
 }

 private String asString (String value)
 {
  if (value == null)
  {
   return null;
  }
  if (value.trim ().equals (""))
  {
   return null;
  }
  return value;
 }

 private Boolean asBoolean (String value)
 {
  return asBoolean (value, false);
 }

 private Boolean asBoolean (String value, Boolean defVal)
 {
  if (value == null)
  {
   return defVal;
  }
  if (value.trim ().equals (""))
  {
   return defVal;
  }
  if (value.equalsIgnoreCase ("true"))
  {
   return true;
  }
  if (value.equalsIgnoreCase ("false"))
  {
   return false;
  }
  throw new DictionaryValidationException (value + " in constraint " + cons.
   getId () + " could not be parsed as a boolean.");
 }

 private Integer asInteger (String value)
 {
  if (value == null)
  {
   return null;
  }
  if (value.trim ().equals (""))
  {
   return null;
  }
  if (value.trim ().equals (Constraint.UNBOUNDED))
  {
   return null;
  }
  try
  {
   return Integer.parseInt (value);
  }
  catch (NumberFormatException ex)
  {
   throw new DictionaryValidationException (value + " in constraint " + cons.
    getId () + " could not be parsed as an integer ");
  }
 }

}
