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

import java.io.PrintStream;

/**
 * Writes out a constraint in XML format.
 * @author nwright
 */
public class ConstraintWriter extends XmlWriter
{

 private Constraint constraint;

 public ConstraintWriter (PrintStream out, int indent, Constraint constraint)
 {
  super (out, indent);
  this.constraint = constraint;
 }

 public void write ()
 {
  if (isEmptyConstraint ())
  {
   return;
  }
  // only add spacing on stand alone ones
  // inline constraints shouldn't be separated
  if ( ! constraint.getId ().equals (""))
  {
   println ("");
  }

  indentPrint ("<dict:constraint");
  //TODO: not sure what to put in the key attribute
  incrementIndent ();
  writeAttribute ("key", constraint.getKey ());
  writeAttribute ("id", constraint.getId ());
  writeAttribute ("className", constraint.getClassName ());
  writeAttribute ("serverSide", constraint.getServerSide ());
  println (">");

  // write out description as comments as there is no field for this
  writeComment (constraint.getDesc ());
  writeComment (constraint.getComments ());


  //TODO: deal with locale
  //writeAttribute ("locale", constraint.getLocale ());
  writeTag ("dict:minValue", constraint.getMinValue ());
  writeTag ("dict:maxValue", constraint.getMaxValue ());
  writeTag ("dict:minOccurs", constraint.getMinOccurs ());
  writeTag ("dict:maxOccurs", constraint.getMaxOccurs ());
  writeTag ("dict:minLength", constraint.getMinLength ());
  writeTag ("dict:maxLength", constraint.getMaxLength ());
  if ( ! calcValidChars ().equals (""))
  {
   indentPrintln ("<dict:validChars>");
   writeTag ("dict:value", calcValidChars ());
   indentPrintln ("</dict:validChars>");
  }

  if ( ! constraint.getLookup ().equals (""))
  {
   indentPrint ("<dict:lookup");
   writeAttribute ("search", constraint.getLookup ());
   println (">");
   // TODO: Deal with search field and lookupKey
   //<dict:lookupKey field="addressCountry" mapsTo="country.code" />
   indentPrintln ("<dict:lookupKey field=\"(none)\" mapsTo=\"(none)\"/>");
   indentPrintln ("</dict:lookup>");
  }

  // end the constraint
  indentPrintln ("</dict:constraint>");
  decrementIndent ();
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
  String validChars = constraint.getValidChars ();
  if (validChars.equals (""))
  {
   return "";
  }
  return "regex:" + validChars;
 }

}
