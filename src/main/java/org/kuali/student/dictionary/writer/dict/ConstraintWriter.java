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
package org.kuali.student.dictionary.writer.dict;

import org.kuali.student.dictionary.model.Constraint;
import org.kuali.student.dictionary.writer.XmlWriter;

/**
 * Writes out a constraint in XML format.
 * @author nwright
 */
public class ConstraintWriter
{

 private Constraint constraint;
 private XmlWriter writer;

 public ConstraintWriter (XmlWriter writer, Constraint constraint)
 {
  this.writer = writer;
  this.constraint = constraint;
 }

 public void write ()
 {
  if (constraint.getId ().equals (""))
  {
   if (isEmptyConstraint ())
   {
    return;
   }
  }
  else
  {
   // only add spacing on stand alone ones
   // inline constraints shouldn't be separated
   writer.println ("");
  }

  writer.indentPrint ("<dict:constraint");
  //TODO: not sure what to put in the key attribute
  writer.incrementIndent ();
  writer.writeAttribute ("key", constraint.getKey ());
  if ( ! constraint.getId ().equals (""))
  {
   writer.writeAttribute ("id", "constraint." + constraint.getId ());
  }
  writer.writeAttribute ("className", constraint.getClassName ());
  writer.writeAttribute ("serverSide", constraint.getServerSide ());
  writer.println (">");

  // write out description as comments as there is no field for this
  writer.writeComment (constraint.getDesc ());
  writer.writeComment (constraint.getComments ());


  //TODO: deal with locale
  //writeAttribute ("locale", constraint.getLocale ());
  writer.writeTag ("dict:minValue", constraint.getMinValue ());
  writer.writeTag ("dict:maxValue", constraint.getMaxValue ());
  writer.writeTag ("dict:minOccurs", constraint.getMinOccurs ());
  writer.writeTag ("dict:maxOccurs", constraint.getMaxOccurs ());
  writer.writeTag ("dict:minLength", constraint.getMinLength ());
  writer.writeTag ("dict:maxLength", constraint.getMaxLength ());
  if ( ! calcValidChars ().equals (""))
  {
   writer.indentPrintln ("<dict:validChars>");
   writer.writeTag ("dict:value", calcValidChars ());
   writer.indentPrintln ("</dict:validChars>");
  }

  if ( ! constraint.getLookup ().equals (""))
  {
   writer.indentPrint ("<dict:lookup");
   writer.writeAttribute ("search", constraint.getLookup ());
   writer.println (">");
   // TODO: Deal with search field and lookupKey
   //<dict:lookupKey field="addressCountry" mapsTo="country.code" />
   writer.indentPrintln ("<dict:lookupKey field=\"(none)\" mapsTo=\"(none)\"/>");
   writer.indentPrintln ("</dict:lookup>");
  }

  // end the constraint
  writer.indentPrintln ("</dict:constraint>");
  writer.decrementIndent ();
 }

 private boolean isEmptyConstraint ()
 {
  if ( ! constraint.getMinLength ().equals (""))
  {
   return false;
  }
  if ( ! constraint.getMaxLength ().equals (""))
  {
   return false;
  }
  if ( ! constraint.getMinValue ().equals (""))
  {
   return false;
  }
  if ( ! constraint.getMaxValue ().equals (""))
  {
   return false;
  }
  if ( ! constraint.getMinOccurs ().equals (""))
  {
   return false;
  }
  if ( ! constraint.getMaxOccurs ().equals (""))
  {
   return false;
  }
  if ( ! constraint.getLookup ().equals (""))
  {
   return false;
  }
  if ( ! constraint.getValidChars ().equals (""))
  {
   return false;
  }
  return true;
 }

 private String calcValidChars ()
 {
  return calcValidChars (constraint.getValidChars ());
 }

 public static String calcValidChars (String validChars)
 {
  if (validChars.equals (""))
  {
   return "";
  }
  return "regex:" + validChars;
 }

}
