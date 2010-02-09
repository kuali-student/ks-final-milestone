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
package org.kuali.student.dictionary.writer;

import org.kuali.student.dictionary.model.util.ModelFinder;
import org.kuali.student.dictionary.model.TypeStateCaseConstraint;
import org.kuali.student.dictionary.model.validation.DictionaryValidationException;
import org.kuali.student.dictionary.model.XmlType;
import org.kuali.student.dictionary.model.CrossObjectConstraint;
import org.kuali.student.dictionary.model.Dictionary;
import org.kuali.student.dictionary.model.DictionaryModel;
import org.kuali.student.dictionary.model.Field;
import org.kuali.student.dictionary.model.State;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This writes out a single field entry and all of it's constraints into the XML output document
 * @author nwright
 */
public class DictionaryEntryWriter 
{

 private DictionaryModel model;
 private ModelFinder finder;
 private Dictionary dict;
 private Field field;
 private ObjectStructureWriter parent;
 private XmlWriter writer;

 public DictionaryEntryWriter (XmlWriter writer,
                               DictionaryModel model,
                               Dictionary dict,
                               ObjectStructureWriter parent)
 {
  super ();
  this.writer = writer;
  this.model = model;
  this.finder = new ModelFinder (model);
  this.dict = dict;
  this.parent = parent;
  field = getField (dict);
 }

 private Field getField (Dictionary d)
 {
  Field field = finder.findField (d.getXmlObject (), d.getShortName ());
  if (field == null)
  {
   throw new DictionaryValidationException ("No field entry found for dictionary entry, " + d.
    getId () + ", pointing to field " + d.getXmlObject () + "."
    + d.getShortName ());
  }
  return field;
 }

 private Dictionary getDictStateOverride (Dictionary d, State ms)
 {
  for (Dictionary override : finder.findStateOverrideDictionary ())
  {
   // if you find yourself in the list then you are the override
   // and there is no other
   if (override.getId ().equals (d.getId ()))
   {
    return null;
   }
   if ( ! override.getXmlObject ().equals (d.getXmlObject ()))
   {
    continue;
   }
   if ( ! override.getShortName ().equals (d.getShortName ()))
   {
    continue;
   }
   if ( ! override.getParentObject ().equals (d.getParentObject ()))
   {
    continue;
   }
   if ( ! override.getParentShortName ().equals (d.getParentShortName ()))
   {
    continue;
   }
   if ( ! override.getMainType ().equals (d.getMainType ()))
   {
    continue;
   }
   if ( ! override.getMainState ().equals (ms.getName ()))
   {
    continue;
   }
   return override;
  }
  return null;
 }

 private static Set<String> dictionaryEntriesWritten = new HashSet ();

 public static Set<String> getDictionaryEntriesWritten ()
 {
  return dictionaryEntriesWritten;
 }

 public void write ()
 {
  // subsequent use of the exact same dictionary field
 
  // write it out
  writeFieldHeader ();
  writeFieldDescriptor ();
  writeConstraintDescriptor ();
  writeSelector ();
  writeDynamic ();
  writeFieldFooter ();
 }


 private void writeFieldRef ()
 {
  //indent (System.out, ' ');
  //System.out.writer.println ("Writing out: " + dict.getId () + " as ref");
  writer.indentPrint ("<dict:fieldRef");
  writer.writeAttribute ("bean", dict.getId ());
  writer.println ("/>");
 }

 private void writeFieldHeader ()
 {
  writer.indentPrintln ("");
  writer.indentPrint ("<dict:field");
  if (dictionaryEntriesWritten.contains (dict.getId ()))
  {
   //indent (System.out, ' ');
   //System.out.writer.println ("Writing out: " + dict.getId () + " w/o id");
  }
  else
  {
   writer.writeAttribute ("id", dict.getId ());
   // remember that this field was written before
   dictionaryEntriesWritten.add (dict.getId ());
   //indent (System.out, ' ');
   //System.out.writer.println ("Writing out: " + dict.getId () + " with id");
  }
  writer.writeAttribute ("key", dict.getShortName ());
  writer.println (">");

  writer.incrementIndent ();
  writer.writeComment (field.getComments ());
  writer.writeComment (dict.getComments ());
 }

 private void writeFieldFooter ()
 {
  writer.decrementIndent ();
  writer.indentPrintln ("</dict:field>");
 }

 private void writeFieldDescriptor ()
 {
  writer.indentPrintln ("<dict:fieldDescriptor>");
  writer.incrementIndent ();
  writer.writeTag ("dict:name", dict.getName ());
  writer.writeTag ("dict:desc", dict.getDesc ());
  writer.writeTag ("dict:dataType", calcDataType ());
  // Inline-ObjectStructure to write out the structure of this fields sub-fields
  if (shouldInsertInLineObjectStructure ())
  {
   writer.indent (System.out, ' ');
   System.out.println ("Writing out sub-structure for: " + dict.getId ());
   ObjectStructureWriter osw =
    new ObjectStructureWriter (writer,
                               model,
                               getXmlType (),
                               null);
   osw.write ();
  }
  writer.writeTag ("dict:readOnly", calcReadOnly ());
  writer.decrementIndent ();
  writer.indentPrintln ("</dict:fieldDescriptor>");
 }

 private void writeSelector ()
 {
  writer.writeTag ("dict:selector", calcSelector ());
 }

 private void writeDynamic ()
 {
  writer.writeTag ("dict:dynamic", calcDynamic ());
 }

 private void writeConstraintDescriptor ()
 {
  writer.indentPrintln ("<dict:constraintDescriptor>");
  writer.incrementIndent ();
  // write out referenced constraints
  for (String id : field.getConstraintIds ())
  {
   ConstraintRefWriter crw = new ConstraintRefWriter (writer, id);
   crw.write ();
  }


  for (String id : dict.getAdditionalConstraintIds ())
  {
   writer.incrementIndent ();
   ConstraintRefWriter crw = new ConstraintRefWriter (writer, id);
   crw.write ();
   writer.decrementIndent ();
  }

// write out in-line constraint
  writer.incrementIndent ();
  ConstraintWriter cw =
   new ConstraintWriter (writer, field.getInlineConstraint ());
  cw.write ();
  cw = new ConstraintWriter (writer, dict.
   getInlineConstraint ());
  cw.write ();
  writer.decrementIndent ();

  // write out cross-object constraints tha are tied to this field
  TypeStateCaseConstraint tscc =
   new TypeStateCaseConstraint (getTypeStateWhens ());
  writer.incrementIndent ();
  TypeStateCaseConstraintWriter tsccw =
   new TypeStateCaseConstraintWriter (writer, tscc, model);
  tsccw.write ();
  writer.decrementIndent ();
  writer.decrementIndent ();
  writer.indentPrintln ("</dict:constraintDescriptor>");
 }

 private List<Dictionary> getSubFields (Dictionary d)
 {
  List<Dictionary> list = new ArrayList ();
  for (Dictionary sub : finder.findDefaultDictionary ())
  {
   if ( ! sub.getParentObject ().equals (d.getXmlObject ()))
   {
    continue;
   }

   if ( ! sub.getParentShortName ().equals (d.getShortName ()))
   {
    continue;
   }

   if ( ! sub.getMainType ().equals (d.getMainType ()))
   {
    continue;
   }
   list.add (sub);
  }

  if (list.size () == 0)
  {
   if (calcNotUsed (d, getField (d)))
   {
    return list;
   }
   throw new DictionaryValidationException ("No sub-fields found in default dictionary for dictionary entry " + d.
    getId ());
  }
  return list;
 }

 private String calcDataType ()
 {
  return calcDataType (field);
 }

 private String calcDataType (Dictionary d)
 {
  Field f = getField (d);
  return calcDataType (f);
 }

 private String calcDataType (Field f)
 {
  if (f.getPrimitive ().equalsIgnoreCase ("complex"))
  {
   return "complex";
  }

  if (f.getPrimitive ().equalsIgnoreCase ("mapped string"))
  {
   return "string";
  }

  if (f.getPrimitive ().equalsIgnoreCase ("primitive"))
  {
   return field.getXmlType ();
  }

  throw new DictionaryValidationException ("Unexpected data value for the primitive column in field.  Found " + f.
   getPrimitive () + " on field " + f.getId ());
 }

 private boolean calcNotUsed (Dictionary d, Field f)
 {
  if (f.getConstraintIds ().contains ("not.used"))
  {
   return true;
  }

  if (d != null)
  {
   if (d.getAdditionalConstraintIds ().contains ("not.used"))
   {
    return true;
   }

  }
  return false;
 }

 private boolean shouldInsertInLineObjectStructure ()
 {
  if ( ! calcDataType ().equals ("complex"))
  {
   return false;
  }

  if (calcNotUsed (dict, field))
  {
   return false;
  }

  return true;
 }

 private String calcReadOnly ()
 {
  if (field.getConstraintIds ().contains ("read.only"))
  {
   return "true";
  }

  if (dict != null)
  {
   if (dict.getAdditionalConstraintIds ().contains ("read.only"))
   {
    return "true";
   }

  }
  return "";
 }

 private String calcSelector ()
 {
  if (dict != null)
  {
   if (dict.getSelector ().equals ("true"))
   {
    return "true";
   }

  }
  return "";
 }

 private String calcDynamic ()
 {
  if (dict != null)
  {
   if (field.getDynamic ().equals ("true"))
   {
    return "true";
   }

  }
  return "";
 }

 private XmlType getXmlType ()
 {
  for (XmlType x : model.getXmlTypes ())
  {
   if (x.getName ().equals (field.getXmlType ()))
   {
    return x;
   }

  }
  throw new DictionaryValidationException ("Unexpected value for the xmlType column in field.  Found " + field.
   getXmlType () + " on field " + field.getId ());
 }

 private static Set<String> crossObjectConstraintsWritten = new HashSet ();

 public static Set<String> getCrossObjectConstraintsWritten ()
 {
  return crossObjectConstraintsWritten;
 }

 private List<CrossObjectConstraint> getTypeStateWhens ()
 {
  List<CrossObjectConstraint> list = new ArrayList ();
  for (CrossObjectConstraint coc : model.getCrossObjectConstraints ())
  {
   if (coc.getDictionaryId ().equals (dict.getId ()))
   {
    if (coc.getImplementation ().equals (CrossObjectConstraint.IMPLEMENTATION_TYPE_STATE_WHEN))
    {
     if (coc.getType1 ().equals (dict.getMainType ()))
     {
      if (coc.getState1 ().equals (dict.getMainState ()) || coc.getState1 ().
       equals (State.DEFAULT))
      {
       list.add (coc);
       crossObjectConstraintsWritten.add (coc.getId ());
      }
     }
    }
   }
  }
  return list;
 }

}
