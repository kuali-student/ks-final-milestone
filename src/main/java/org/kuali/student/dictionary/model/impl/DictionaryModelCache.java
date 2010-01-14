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

import org.kuali.student.dictionary.model.*;
import java.util.List;

/**
 * This reads the supplied spreadsheet but then caches it so it doesn't have to
 * re-read it again.
 * @author nwright
 */
public class DictionaryModelCache implements DictionaryModel
{

 private DictionaryModel model;

 public DictionaryModelCache (DictionaryModel spreadsheet)
 {
  this.model = spreadsheet;
 }

 private List<Constraint> constraints = null;

 @Override
 public List<Constraint> getConstraints ()
 {
  if (constraints == null)
  {
   constraints = model.getConstraints ();
  }
  return constraints;
 }

 private List<CrossObjectConstraint> crossObjectConstraints = null;

 @Override
 public List<CrossObjectConstraint> getCrossObjectConstraints ()
 {
  if (crossObjectConstraints == null)
  {
   crossObjectConstraints = model.getCrossObjectConstraints ();
  }
  return crossObjectConstraints;
 }

 private List<Dictionary> dicts = null;

 @Override
 public List<Dictionary> getDictionary ()
 {
  if (dicts == null)
  {
   dicts = model.getDictionary ();
  }
  return dicts;
 }

 private List<Field> fields = null;

 @Override
 public List<Field> getFields ()
 {
  if (fields == null)
  {
   fields = model.getFields ();
  }
  return fields;
 }

 private List<State> states = null;

 @Override
 public List<State> getStates ()
 {
  if (states == null)
  {
   states = model.getStates ();
  }
  return states;
 }

 private List<Type> types;

 @Override
 public List<Type> getTypes ()
 {
  if (types == null)
  {
   types = model.getTypes ();
  }
  return types;
 }

 private List<XmlType> xmlTypes;

 @Override
 public List<XmlType> getXmlTypes ()
 {
  if (xmlTypes == null)
  {
   xmlTypes = model.getXmlTypes ();
  }
  return xmlTypes;
 }

 @Override
 public List<String> getSourceNames ()
 {
  return model.getSourceNames ();
 }

 private List<OrchObj> orchObjs = null;

 @Override
 public List<OrchObj> getOrchObjs ()
 {
  if (orchObjs == null)
  {
   orchObjs = model.getOrchObjs ();
  }
  return orchObjs;
 }

 private List<MessageStructure> messageStructures = null;

 @Override
 public List<MessageStructure> getMessageStructures ()
 {
  if (messageStructures == null)
  {
   messageStructures = model.getMessageStructures ();
  }
  return messageStructures;
 }

 private List<SearchType> searchTypes = null;

 @Override
 public List<SearchType> getSearchTypes ()
 {
  if (searchTypes == null)
  {
   searchTypes = model.getSearchTypes ();
  }
  return searchTypes;
 }

  private List<Service> services = null;

 @Override
 public List<Service> getServices ()
 {
  if (services == null)
  {
   services = model.getServices ();
  }
  return services;
 }

 private List<Project> projects = null;

 @Override
 public List<Project> getProjects ()
 {
  if (projects == null)
  {
   projects = model.getProjects ();
  }
  return projects;
 }

 private List<ServiceMethod> serviceMethods = null;

 public List<ServiceMethod> getServiceMethods ()
 {
  if (serviceMethods == null)
  {
   serviceMethods = model.getServiceMethods ();
  }
  return serviceMethods;
 }


}
