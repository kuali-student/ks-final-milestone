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

import java.util.List;

/**
 * This reads the supplied spreadsheet but then caches it so it doesn't have to
 * re-read it again.
 * @author nwright
 */
public class DictionaryModelCache implements DictionaryModel
{

 private DictionaryModel spreadsheet;

 public DictionaryModelCache (DictionaryModel spreadsheet)
 {
  this.spreadsheet = spreadsheet;
 }

 private List<Constraint> constraints = null;

 @Override
 public List<Constraint> getConstraints ()
 {
  if (constraints == null)
  {
   constraints = spreadsheet.getConstraints ();
  }
  return constraints;
 }

  private List<CrossObjectConstraint> crossObjectConstraints = null;

 @Override
 public List<CrossObjectConstraint> getCrossObjectConstraints ()
 {
  if (crossObjectConstraints == null)
  {
   crossObjectConstraints = spreadsheet.getCrossObjectConstraints ();
  }
  return crossObjectConstraints;
 }


 private List<Dictionary> dicts = null;

 @Override
 public List<Dictionary> getDictionary ()
 {
  if (dicts == null)
  {
   dicts = spreadsheet.getDictionary ();
  }
  return dicts;
 }

 private List<Field> fields = null;

 @Override
 public List<Field> getFields ()
 {
  if (fields == null)
  {
   fields = spreadsheet.getFields ();
  }
  return fields;
 }

 private List<State> states = null;

 @Override
 public List<State> getStates ()
 {
  if (states == null)
  {
   states = spreadsheet.getStates ();
  }
  return states;
 }

 private List<Type> types;

 @Override
 public List<Type> getTypes ()
 {
  if (types == null)
  {
   types = spreadsheet.getTypes ();
  }
  return types;
 }

 private List<XmlType> xmlTypes;

 @Override
 public List<XmlType> getXmlTypes ()
 {
  if (xmlTypes == null)
  {
   xmlTypes = spreadsheet.getXmlTypes ();
  }
  return xmlTypes;
 }

 @Override
 public List<String> getSourceNames ()
 {
  return spreadsheet.getSourceNames ();
 }

 private List<OrchObj> orchObjs = null;

 public List<OrchObj> getOrchObjs ()
 {
  if (orchObjs == null)
  {
   orchObjs = spreadsheet.getOrchObjs ();
  }
  return orchObjs;
 }


 
}
