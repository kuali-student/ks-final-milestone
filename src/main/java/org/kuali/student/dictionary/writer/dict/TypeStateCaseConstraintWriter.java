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

import org.kuali.student.dictionary.writer.dict.ConstraintRefWriter;
import org.kuali.student.dictionary.model.TypeStateCaseConstraint;
import org.kuali.student.dictionary.model.validation.DictionaryValidationException;
import org.kuali.student.dictionary.model.CrossObjectConstraint;
import org.kuali.student.dictionary.model.DictionaryModel;
import org.kuali.student.dictionary.model.Type;
import org.kuali.student.dictionary.model.State;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;
import org.kuali.student.dictionary.writer.XmlWriter;

/**
 * Writes a single constraint for the type-state when case statement
 * @author nwright
 */
public class TypeStateCaseConstraintWriter 
{

 private TypeStateCaseConstraint constraint;
 private DictionaryModel spreadsheet;
 private XmlWriter writer;

 public TypeStateCaseConstraintWriter (XmlWriter writer,
                                       TypeStateCaseConstraint constraint,
                                       DictionaryModel spreadsheet)
 {
  super ();
  this.writer = writer;
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
    new ConstraintRefWriter (writer, constraint.getId ());
   crw.write ();
   return;
  }
  typeStateCaseConstraintsWritten.add (constraint.getId ());

  writer.indentPrint ("<dict:constraint");
  writer.writeAttribute ("key", constraint.getKey ());
  writer.writeAttribute ("id", constraint.getId ());
  writer.println (">");

  writer.indentPrintln ("<dict:typeStateCase>");

  writer.incrementIndent ();
  for (CrossObjectConstraint when : constraint.getTypeStateWhens ())
  {
   writer.indentPrint ("<dict:typeStateWhen");
   Type type = getType (when.getType2 (), when.getObject2 ());
   if (type == null)
   {
    throw new DictionaryValidationException ("CrossObjectConstraint " + when.getId () +
     " has a type, " + when.getType2 () +
     ", that does not exist for the object, " + when.getObject2 ());

   }
   writer.writeAttribute ("type", type.getTypeKey ());
   if ( ! when.getState2 ().equals (State.DEFAULT))
   {
    writer.writeAttribute ("state", when.getState2 ());
   }
   writer.println (">");
   // write out description as comments as there is no field for this
   writer.writeComment (when.getDesc ());
   writer.writeComment (when.getComments ());
   writer.incrementIndent ();
   writer.writeTag ("dict:minOccurs", when.getMinOccurs ());
   writer.writeTag ("dict:maxOccurs", when.getMaxOccurs ());
   writer.decrementIndent ();
   writer.indentPrintln ("</dict:typeStateWhen>");
  }
  writer.decrementIndent ();
  writer.indentPrintln ("</dict:typeStateCase>");

  // end the constraint
  writer.indentPrintln ("</dict:constraint>");
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
