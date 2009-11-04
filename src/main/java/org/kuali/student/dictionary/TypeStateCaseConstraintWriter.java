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
import java.util.HashSet;
import java.util.Set;

/**
 * Writes a single constraint for the type-state when case statement
 * @author nwright
 */
public class TypeStateCaseConstraintWriter extends XmlWriter
{

 private TypeStateCaseConstraint constraint;
 private DictionaryModel spreadsheet;

 public TypeStateCaseConstraintWriter (PrintStream out, int indent,
                                       TypeStateCaseConstraint constraint,
                                       DictionaryModel spreadsheet)
 {
  super (out, indent);
  this.constraint = constraint;
  this.spreadsheet = spreadsheet;
 }

 private static Set<String> typeStateCaseConstraintsWritten = new HashSet ();

 public void write ()
 {
  if (isEmptyConstraint ())
  {
   return;
  }

  // write a ref it it has already been written
  if (typeStateCaseConstraintsWritten.contains (constraint.getId ()))
  {
   ConstraintRefWriter crw =
    new ConstraintRefWriter (getOut (), getIndent (), constraint.getId ());
   crw.write ();
   return;
  }
  typeStateCaseConstraintsWritten.add (constraint.getId ());

  indentPrint ("<dict:constraint");
  writeAttribute ("key", constraint.getKey ());
  writeAttribute ("id", constraint.getId ());
  println (">");

  indentPrintln ("<dict:typeStateCase>");

  incrementIndent ();
  for (CrossObjectConstraint when : constraint.getTypeStateWhens ())
  {
   indentPrint ("<dict:typeStateWhen");
   Type type = getType (when.getType2 (), when.getObject2 ());
   if (type == null)
   {
    throw new DictionaryValidationException ("CrossObjectConstraint " + when.getId () +
     " has a type, " + when.getType2 () +
     ", that does not exist for the object, " + when.getObject2 ());

   }
   writeAttribute ("type", type.getTypeKey ());
   if ( ! when.getState2 ().equals (State.DEFAULT))
   {
    writeAttribute ("state", when.getState2 ());
   }
   println (">");
   // write out description as comments as there is no field for this
   writeComment (when.getDesc ());
   writeComment (when.getComments ());
   incrementIndent ();
   writeTag ("dict:minOccurs", when.getMinOccurs ());
   writeTag ("dict:maxOccurs", when.getMaxOccurs ());
   decrementIndent ();
   indentPrintln ("</dict:typeStateWhen>");
  }
  decrementIndent ();
  indentPrintln ("</dict:typeStateCase>");

  // end the constraint
  indentPrintln ("</dict:constraint>");
 }

 private Type getType (String name, String xmlObject)
 {
  for (Type type : spreadsheet.getTypes ())
  {
   if (type.getName ().equals (name))
   {
    if (type.getXmlObject ().equals (xmlObject))
    {
     return type;
    }
   }
  }
  return null;
 }

 private boolean isEmptyConstraint ()
 {
  for (CrossObjectConstraint when : constraint.getTypeStateWhens ())
  {
//   System.out.println ("Checking if empty: " + when.getId () + ", min=" + when.
//    getMinOccurs () + ", max=" + when.getMaxOccurs ());
   if ( ! when.getMinOccurs ().equals (""))
   {
    return false;
   }
   if ( ! when.getMaxOccurs ().equals (""))
   {
    return false;
   }
  }
  return true;
 }

}
