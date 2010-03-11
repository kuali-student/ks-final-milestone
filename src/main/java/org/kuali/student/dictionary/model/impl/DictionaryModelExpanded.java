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
 * This provides expansion functionality to the spreadsheet model by expanding
 * the dictionary entries when it encounters types or states that are not
 * individual types or states but actually groups.
 * @author nwright
 */
public class DictionaryModelExpanded implements DictionaryModel
{

 private DictionaryModel model;
 private DictionaryExpander expander;

 public DictionaryModelExpanded (DictionaryModel spreadsheet,
                                 DictionaryExpander expander)
 {
  this.model = spreadsheet;
  this.expander = expander;
 }

 @Override
 public List<Constraint> getConstraints ()
 {
  return model.getConstraints ();
 }

 @Override
 public List<CrossObjectConstraint> getCrossObjectConstraints ()
 {
  return model.getCrossObjectConstraints ();
 }

 private List<Dictionary> dicts = null;

 @Override
 public List<Dictionary> getDictionary ()
 {
  if (dicts == null)
  {
   dicts = expander.expand ();
  }
  return dicts;
 }

 @Override
 public List<Field> getFields ()
 {
  return model.getFields ();
 }

 private List<State> states = null;

 @Override
 public List<State> getStates ()
 {
  return model.getStates ();
 }

 @Override
 public List<Type> getTypes ()
 {
  return model.getTypes ();
 }

 @Override
 public List<XmlType> getXmlTypes ()
 {
  return model.getXmlTypes ();
 }

 @Override
 public List<String> getSourceNames ()
 {
  return model.getSourceNames ();
 }

 @Override
 public List<OrchObj> getOrchObjs ()
 {
  return model.getOrchObjs ();
 }

 @Override
 public List<MessageStructure> getMessageStructures ()
 {
  return model.getMessageStructures ();
 }

 @Override
 public List<Service> getServices ()
 {
  return model.getServices ();
 }

 @Override
 public List<Project> getProjects ()
 {
  return model.getProjects ();
 }

 @Override
 public List<SearchType> getSearchTypes ()
 {
  return model.getSearchTypes ();
 }

 @Override
 public List<ServiceMethod> getServiceMethods ()
 {
  return model.getServiceMethods ();
 }

}
