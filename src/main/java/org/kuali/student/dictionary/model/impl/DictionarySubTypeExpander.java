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
package org.kuali.student.dictionary.model.impl;

import org.kuali.student.dictionary.model.util.ModelFinder;
import org.kuali.student.dictionary.model.*;
import org.kuali.student.dictionary.model.validation.DictionaryValidationException;
import java.util.ArrayList;
import java.util.List;
import org.kuali.student.dictionary.DictionaryExecutionException;

/**
 * A dictionary expander that expands the sub-type
 * @author nwright
 */
public class DictionarySubTypeExpander implements DictionaryExpander
{

 private List<Dictionary> oldDicts;
 private List<Dictionary> newDicts;
 private DictionaryModel model;
 private ModelFinder finder;

 public DictionarySubTypeExpander (DictionaryModel model)
 {
  this.model = model;
  this.oldDicts = model.getDictionary ();
  this.finder = new ModelFinder (this.model);
 }

 public List<Dictionary> expand ()
 {
  newDicts = new ArrayList (oldDicts.size () * 2);
  expandSubType ();
  return newDicts;
 }

 
 private void expandSubType ()
 {
  for (Dictionary d : oldDicts)
  {
   Type type = getSubType (d);
   // null means NA was found
   if (type == null)
   {
    newDicts.add (d);
   }
   else if (type.getStatus ().equals (Type.GROUPING))
   {
    newDicts.addAll (expandSubType (d, type));
   }
   else
   {
    newDicts.add (d);
   }

  }
  return;
 }

 private Type getSubType (Dictionary dict)
 {
  if (dict.getSubType ().equalsIgnoreCase (Type.DEFAULT))
  {
   return null;
  }

  String xmlObject = dict.getXmlObject ();

  Type type = finder.findType (xmlObject, dict.getSubType ());
  if (type == null)
  {
   throw new DictionaryValidationException ("could not find sub type for dictionary entry " +
    dict.getId () + ": " + dict.getXmlObject () + "." + dict.getSubType ());
  }

  return type;
 }

 private List<Dictionary> expandSubType (Dictionary d, Type type)
 {
  List<Dictionary> list = new ArrayList ();
  for (Type t : finder.expandType (type))
  {
   try
   {
    Dictionary clone = (Dictionary) d.clone ();
    clone.setSubType (t.getName ());
    list.add (clone);
   }
   catch (CloneNotSupportedException ex)
   {
    throw new DictionaryExecutionException (ex);
   }

  }
  return list;
 }

}
 
 