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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This writes out a single field entry and all of it's constraints into the XML output document
 * @author nwright
 */
public class FieldWriter extends XmlWriter
{

 private Spreadsheet spreadsheet;
 private SpreadsheetFinder finder;
 private State mainState;
 private Dictionary origDict;
 private Dictionary dict;
 private Field field;
 private boolean inline;

 public FieldWriter (PrintStream out, int indent, Spreadsheet spreadsheet,
                     Dictionary origDict, State mainState, boolean inline)
 {
  super (out, indent);
  this.spreadsheet = spreadsheet;
  this.finder = new SpreadsheetFinder (spreadsheet);
  this.origDict = origDict;
  this.mainState = mainState;
  this.inline = inline;
  field = getField (origDict);
  loadDictStateOverride ();
 }

 private Field getField (Dictionary d)
 {
  for (Field f : spreadsheet.getFields ())
  {
   if (f.getXmlObject ().equals (d.getXmlObject ()))
   {
    if (f.getShortName ().equals (d.getShortName ()))
    {
     return f;
    }
   }
  }
  throw new DictionaryValidationException ("No field entry found for dictionary entry, " +
   d.getId () + ", pointing to field " +
   d.getXmlObject () + "." + d.getShortName ());
 }

 private void loadDictStateOverride ()
 {
  this.dict = origDict;
  Dictionary override = getDictStateOverride (dict, mainState);
  if (override != null)
  {
   //indent (System.out, ' ');
   //System.out.println ("Overriding " + dict.getId () + " in state " + mainState.
   // getName () + " with " + override.getId ());
   dict = override;
  }
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
  if (calcCanUseRef ())
  {
   writeFieldRef ();
   return;
  }

  // write it out
  writeFieldHeader ();
  writeFieldDescriptor ();
  writeConstraintDescriptor ();
  writeSelector ();
  writeDynamic ();
  writeFieldFooter ();
 }

 private boolean calcCanUseRef ()
 {
  // if not already written then cannot use ref because there is
  // no previous field to refer to!
  if ( ! dictionaryEntriesWritten.contains (dict.getId ()))
  {
   return false;
  }
  // for complex fields.. can simply use ref if no sub-field is overridden
  // otherwise have re-do the whole thing so the sub-field can be properly overridden
  if (isOverriddenOrAnySubFieldOverriden (dict))
  {
   return false;
  }
  return true;
 }

 private boolean isOverriddenOrAnySubFieldOverriden (Dictionary d)
 {
  Dictionary override = getDictStateOverride (d, mainState);
  if (override != null)
  {
   return true;
  }
  // if not complex a complex type and it was not overriden itself then there are no sub-fields to be overridden
  if ( ! calcDataType (d).equals ("complex"))
  {
   return false;
  }
  // Recursively check sub-fields to see if  any of  those fields are overridden
  for (Dictionary sub : getSubFields (d))
  {
   if (isOverriddenOrAnySubFieldOverriden (sub))
   {
    return true;
   }
  }
  return false;
 }

 private void writeFieldRef ()
 {
  //indent (System.out, ' ');
  //System.out.println ("Writing out: " + dict.getId () + " as ref");
  indentPrint ("<dict:fieldRef");
  writeAttribute ("bean", dict.getId ());
  println ("/>");
 }

 private void writeFieldHeader ()
 {
  indentPrintln ("");
  indentPrint ("<dict:field");
  if (dictionaryEntriesWritten.contains (dict.getId ()))
  {
   //indent (System.out, ' ');
   //System.out.println ("Writing out: " + dict.getId () + " w/o id");
  }
  else
  {
   writeAttribute ("id", dict.getId ());
   // remember that this field was written before
   dictionaryEntriesWritten.add (dict.getId ());
  //indent (System.out, ' ');
  //System.out.println ("Writing out: " + dict.getId () + " with id");
  }
  writeAttribute ("key", dict.getShortName ());
  println (">");

  writeComment (field.getComments ());
  writeComment (dict.getComments ());
 }

 private void writeFieldFooter ()
 {
  indentPrintln ("</dict:field>");
 }

 private void writeFieldDescriptor ()
 {
  indentPrintln ("<dict:fieldDescriptor>");
  writeTag ("dict:name", dict.getName ());
  writeTag ("dict:desc", dict.getDesc ());
  writeTag ("dict:dataType", calcDataType ());
  // Inline-ObjectStructure to write out the structure of this fields sub-fields
  if (shouldInsertInLineObjectStructure ())
  {
   indent (System.out, ' ');
   System.out.println ("Writing out sub-structure for: " + dict.getId ());
   ObjectStructureWriter osw =
    new ObjectStructureWriter (getOut (), getIndent () + 1, spreadsheet, getXmlType (), getSubFields (dict), true, mainState);
   osw.write ();
  }
  writeTag ("dict:readOnly", calcReadOnly ());
  indentPrintln ("</dict:fieldDescriptor>");
 }

 private void writeSelector ()
 {
  writeTag ("dict:selector", calcSelector ());
 }

 private void writeDynamic ()
 {
  writeTag ("dict:dynamic", calcDynamic ());
 }

 private void writeConstraintDescriptor ()
 {
  indentPrintln ("<dict:constraintDescriptor>");
  // write out referenced constraints
  for (String id : field.getConstraintIds ())
  {
   ConstraintRefWriter crw = new ConstraintRefWriter (getOut (), getIndent () +
    1, id);
   crw.write ();
  }


  for (String id : dict.getAdditionalConstraintIds ())
  {
   ConstraintRefWriter crw = new ConstraintRefWriter (getOut (), getIndent () +
    1, id);
   crw.write ();
  }

// write out in-line constraint
  ConstraintWriter cw =
   new ConstraintWriter (getOut (), getIndent () + 1, field.getInlineConstraint ());
  cw.write ();
  cw = new ConstraintWriter (getOut (), getIndent () + 1, dict.
   getInlineConstraint ());
  cw.write ();

  // write out cross-object constraints tha are tied to this field
  TypeStateCaseConstraint tscc =
   new TypeStateCaseConstraint (getTypeStateWhens ());
  TypeStateCaseConstraintWriter tsccw =
   new TypeStateCaseConstraintWriter (getOut (), getIndent () + 1, tscc, spreadsheet);
  tsccw.write ();
  indentPrintln ("</dict:constraintDescriptor>");
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
   throw new DictionaryValidationException ("No sub-fields found in default dictionary for dictionary entry " +
    d.getId ());
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

  throw new DictionaryValidationException ("Unexpected data value for the primitive column in field.  Found " +
   f.getPrimitive () + " on field " + f.getId ());
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
  for (XmlType x : spreadsheet.getXmlTypes ())
  {
   if (x.getName ().equals (field.getXmlType ()))
   {
    return x;
   }

  }
  throw new DictionaryValidationException ("Unexpected value for the xmlType column in field.  Found " +
   field.getXmlType () + " on field " + field.getId ());
 }

 private static Set<String> crossObjectConstraintsWritten = new HashSet ();

 public static Set<String> getCrossObjectConstraintsWritten ()
 {
  return crossObjectConstraintsWritten;
 }

 private List<CrossObjectConstraint> getTypeStateWhens ()
 {
  List<CrossObjectConstraint> list = new ArrayList ();
  for (CrossObjectConstraint coc : spreadsheet.getCrossObjectConstraints ())
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
