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
import java.util.List;

/**
 *
 * @author nwright
 */
public class DictionaryMainTypeExpander implements DictionaryExpander
{

 private List<Dictionary> oldDicts;
 private List<Dictionary> newDicts;
 private Spreadsheet spreadsheet;
 private SpreadsheetFinder finder;

 public DictionaryMainTypeExpander (Spreadsheet spreadsheet)
 {
  this.spreadsheet = spreadsheet;
  this.oldDicts = spreadsheet.getDictionary ();
  this.finder = new SpreadsheetFinder (this.spreadsheet);
 }

 public List<Dictionary> expand ()
 {
  newDicts = new ArrayList (oldDicts.size () * 3);
  expandMainType ();
  return newDicts;
 }

 private void expandMainType ()
 {
  for (Dictionary d : oldDicts)
  {
   Type type = getMainType (d);
   if (type.getStatus ().equals ("Grouping"))
   {
    expandMainType (d, type);
   }
   else
   {
    newDicts.add (d);
   }
  }
  return;
 }

 private Type getMainType (Dictionary dict)
 {
  Dictionary root = finder.findRoot (dict);
  Type type = finder.findType (root.getXmlObject (), dict.getMainType ());
  if (type == null)
  {
   throw new RuntimeException ("Could not find main type for dictionary entry " +
    dict.getId () + ": " + root.getXmlObject () + "." + dict.getMainType ());
  }
  return type;
 }

 private void expandMainType (Dictionary d, Type type)
 {
  for (Type t : finder.findExpandedTypes (type))
  {
   try
   {
    System.out.println ("Expanding dictionary entry " + d.getId () +
     " with type " + type.getName () + "  to " + t.getName ());
    Dictionary clone = (Dictionary) d.clone ();
    clone.setMainType (t.getName ());
    newDicts.add (clone);
   }
   catch (CloneNotSupportedException ex)
   {
    throw new RuntimeException (ex);
   }
  }
 }
}
 
 