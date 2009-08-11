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
public class SpreadsheetFinder
{

 private Spreadsheet spreadsheet;

 public SpreadsheetFinder (Spreadsheet spreadsheet)
 {
  this.spreadsheet = spreadsheet;
 }

 public XmlType findXmlType (String xmlTypeName)
 {
  for (XmlType xmlType : spreadsheet.getXmlTypes ())
  {
   if (xmlTypeName.equals (xmlType.getName ()))
   {
    return xmlType;
   }
  }
  return null;
 }

 public Type findType (String xmlObject, String typeName)
 {
  for (Type type : spreadsheet.getTypes ())
  {
   if (type.getXmlObject ().equals (xmlObject))
   {
    if (type.getName ().equals (typeName))
    {
     return type;
    }
   }
  }
  return null;
 }

 public Dictionary findRoot (Dictionary dict)
 {
  if (dict.getParentObject ().equals (Type.NA))
  {
   return dict;
  }
  Dictionary parent = findParent (dict);
  if (parent == null)
  {
   throw new RuntimeException ("dictionary entry " + dict.getId () +
    " has no parent but parent object is not " + Type.NA);
  }
  return findRoot (parent);
 }

 public Dictionary findParent (Dictionary dict)
 {
  for (Dictionary d : spreadsheet.getDictionary ())
  {
   if (d.getXmlObject ().equals (dict.getParentObject ()))
   {
    if (d.getShortName ().equals (dict.getParentShortName ()))
    {
     return d;
    }
   }
  }
  return null;
 }

 /**
  * get dictionary entries for the state overries
  * @return
  */
 public List<Dictionary> findDefaultDictionary ()
 {
  List<Dictionary> list = new ArrayList (spreadsheet.getDictionary ().size ());
  for (Dictionary d : spreadsheet.getDictionary ())
  {
   if (d.getMainState ().equals (State.DEFAULT))
   {
    list.add (d);
   }
  }
  return list;
 }

 /**
  * get dictionary entries for the state overries
  * @return
  */
 public List<Dictionary> findStateOverrideDictionary ()
 {
  List<Dictionary> list = new ArrayList (spreadsheet.getDictionary ().size ());
  for (Dictionary d : spreadsheet.getDictionary ())
  {
   if ( ! d.getMainState ().equals (State.DEFAULT))
   {
    list.add (d);
   }
  }
  return list;
 }

 public List<Type> findExpandedTypes (Type type)
 {
  List<Type> types = new ArrayList ();
  String matchPattern = type.getTypeKey ();
  for (Type t : spreadsheet.getTypes ())
  {
   // can't match yourself
   if (t == type)
   {
    System.out.println ("skipping itself " + type.getName ());
    continue;
   }
   if (t.getInclude ().equals ("false"))
   {
    continue;
   }

   // must match the same type of object
   if (type.getXmlObject ().equals (t.getXmlObject ()))
   {
    if (matches (matchPattern, t.getTypeKey ()))
    {
     types.add (t);
    }

   }
  }
  return types;
 }

 protected boolean matches (String pattern, String key)
 {
  // TODO: allow patterns other than * at the end
  if (pattern.endsWith ("*"))
  {
   if (key.startsWith (pattern.substring (0, pattern.length () - 1)))
   {
    return true;
   }

  }
  return false;
 }

}

