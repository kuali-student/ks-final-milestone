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
package org.kuali.student.dictionary.model.util;

import org.kuali.student.dictionary.model.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.kuali.student.dictionary.DictionaryExecutionException;

/**
 * Untility that implements searches of the spreadsheet model that are needed
 * TODO: refactor all the *Writer to use this instead of their own finds.
 * @author nwright
 */
public class ModelFinder
{

 private SearchModel searchModel;
 private ServiceContractModel serviceMethodModel;
 private DictionaryModel model;

 public ModelFinder (DictionaryModel model)
 {
  this.model = model;
  this.searchModel = model;
  this.serviceMethodModel = model;
 }

 public ModelFinder (SearchModel model)
 {
  this.searchModel = model;
 }

 public ModelFinder (ServiceContractModel model)
 {
  this.serviceMethodModel = model;
 }

 public XmlType findXmlType (String xmlTypeName)
 {
  for (XmlType xmlType : model.getXmlTypes ())
  {
   if (xmlTypeName.equalsIgnoreCase (xmlType.getName ()))
   {
    return xmlType;
   }
  }
  return null;
 }

 public List<State> findStates (String xmlObject)
 {
  List<State> list = new ArrayList ();
  for (State state : model.getStates ())
  {
   if (state.getXmlObject ().equalsIgnoreCase (xmlObject))
   {
    list.add (state);
   }
  }
  return list;
 }

 public List<Type> findTypes (String xmlObject)
 {
  List<Type> list = new ArrayList ();
  for (Type type : model.getTypes ())
  {
   if (type.getXmlObject ().equalsIgnoreCase (xmlObject))
   {
    list.add (type);
   }
  }
  return list;
 }

 public Type findType (String xmlObject, String typeName)
 {
  if (typeName.equalsIgnoreCase (State.DEFAULT))
  {
   return this.getDefaultType ();
  }
  for (Type type : model.getTypes ())
  {
   if (type.getXmlObject ().equalsIgnoreCase (xmlObject))
   {
    if (type.getName ().equalsIgnoreCase (typeName))
    {
     return type;
    }
   }
  }
  return null;
 }

 public Type findType (String typeKey)
 {
  for (Type type : model.getTypes ())
  {
   if (type.getTypeKey ().equalsIgnoreCase (typeKey))
   {
    return type;
   }
  }
  return null;
 }

 public State findState (String xmlObject, String stateName)
 {
  if (stateName.equalsIgnoreCase (State.DEFAULT))
  {
   return this.getDefaultState ();
  }
  for (State state : model.getStates ())
  {
   if (state.getXmlObject ().equalsIgnoreCase (xmlObject))
   {
    if (state.getName ().equalsIgnoreCase (stateName))
    {
     return state;
    }
   }
  }
  return null;
 }

  public State findState (String stateKey)
 {
  for (State state : model.getStates ())
  {
   if (state.getStateKey ().equalsIgnoreCase (stateKey))
   {
     return state;
   }
  }
  return null;
 }


 public Dictionary findRoot (Dictionary dict)
 {
  if (dict.getParent () == null)
  {
   return dict;
  }
  return findRoot (dict.getParent ());
 }

 /**
  * get dictionary entry for the id
  * @return
  */
 public Dictionary findDictionaryEntry (String dictId)
 {
  for (Dictionary d : model.getDictionary ())
  {
   if (d.getId ().equalsIgnoreCase (dictId))
   {
    return d;
   }
  }
  return null;
 }

 /**
  * get dictionary entries for the state overries
  * @return
  */
 public List<Dictionary> findChildDictionaryEntries (Dictionary parent)
 {
  List<Dictionary> list = new ArrayList ();
  for (Dictionary d : model.getDictionary ())
  {
   if (d.getParent () == null)
   {
    continue;
   }
   if (d.getParent ().equals (parent))
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
 public List<Dictionary> findDefaultDictionary ()
 {
  List<Dictionary> list = new ArrayList (model.getDictionary ().size ());
  for (Dictionary d : model.getDictionary ())
  {
   if (d.getState ().equalsIgnoreCase (State.DEFAULT))
   {
    list.add (d);
   }
  }
  return list;
 }

 public List<Dictionary> findDictionaryEntriees (String xmlTypeName)
 {
  List<Dictionary> list = new ArrayList ();
  for (Dictionary dict : model.getDictionary ())
  {
   if (dict.getXmlObject ().equalsIgnoreCase (xmlTypeName))
   {
    list.add (dict);
   }
  }
  return list;
 }

 public List<Dictionary> findDefaultDictionaryEntriees (String xmlTypeName)
 {
  List<Dictionary> list = new ArrayList ();
  for (Dictionary dict : this.findDefaultDictionary ())
  {
   if (dict.getXmlObject ().equalsIgnoreCase (xmlTypeName))
   {
    list.add (dict);
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
  List<Dictionary> list = new ArrayList (model.getDictionary ().size ());
  for (Dictionary d : model.getDictionary ())
  {
   if ( ! d.getState ().equalsIgnoreCase (State.DEFAULT))
   {
    list.add (d);
   }
  }
  return list;
 }

 /**
  * Expands a type that has a status of Type.GROUPING.
  * A group can contain another group
  * @param state
  * @return
  */
 public Set<Type> expandType (Type type)
 {
  Set<Type> types = new LinkedHashSet ();
  if ( ! type.getStatus ().equalsIgnoreCase (Type.GROUPING))
  {
   types.add (type);
   return types;
  }
  String pattern = type.getTypeKey ();
  GroupTypeStatePatternMatcher matcher =
   new GroupTypeStatePatternMatcher (pattern);
  for (Type t : model.getTypes ())
  {
   // can't match yourself
   if (t == type)
   {
    //System.out.println ("skipping itself " + type.getName ());
    continue;
   }
   if ( ! t.getInclude ())
   {
    continue;
   }

   // must match the same type of object
   if (type.getXmlObject ().equalsIgnoreCase (t.getXmlObject ()))
   {
    if (matcher.matches (t.getTypeKey ()))
    {
     if (t.getStatus ().equalsIgnoreCase (Type.GROUPING))
     {
      //TODO: Worry about self-recursion
      types.addAll (expandType (t));
     }
     else
     {
      types.add (t);
     }
    }
   }
  }
  return types;
 }

 /**
  * Expands a type that has a status of Type.GROUPING.
  * A group can contain another group
  * @param state
  * @return
  */
 public Set<State> expandState (State state)
 {
  Set<State> states = new LinkedHashSet ();
  if ( ! state.getStatus ().equalsIgnoreCase (State.GROUPING))
  {
   states.add (state);
   return states;
  }
  String pattern = state.getStateKey ();
  GroupTypeStatePatternMatcher matcher =
   new GroupTypeStatePatternMatcher (pattern);
  for (State s : model.getStates ())
  {
   // can't match yourself
   if (s == state)
   {
    //System.out.println ("skipping itself " + state.getName ());
    continue;
   }
   if ( ! s.getInclude ())
   {
    continue;
   }

   // must match the same type of object
   if (state.getXmlObject ().equalsIgnoreCase (s.getXmlObject ()))
   {
    if (matcher.matches (s.getStateKey ()))
    {
     if (s.getStatus ().equalsIgnoreCase (Type.GROUPING))
     {
      //TODO: Worry about self-recursion
      states.addAll (expandState (s));
     }
     else
     {
      states.add (s);
     }
    }
   }
  }
  return states;
 }

 public Constraint findConstraint (String id)
 {
  for (Constraint cons : model.getConstraints ())
  {
   if (cons.getId ().equalsIgnoreCase (id))
   {
    return cons;
   }
  }
  return null;
 }

 public Field findField (String id)
 {
  for (Field field : model.getFields ())
  {
   if (field.getId ().equalsIgnoreCase (id))
   {
    return field;
   }
  }
  return null;
 }

 public Field findField (String xmlTypeName, String shortName)
 {
  return findField (xmlTypeName + "." + shortName);
 }

 public Field findField (Dictionary dict)
 {
  if (dict.isDynamic ())
  {
   return findField (dict.getXmlObject (), "attributes");
  }
  return findField (dict.getXmlObject (), dict.getShortName ());
 }

 public List<Field> findFields (String xmlTypeName)
 {
  List<Field> list = new ArrayList ();
  for (Field field : model.getFields ())
  {
   if (field.getXmlObject ().equalsIgnoreCase (xmlTypeName))
   {
    list.add (field);
   }
  }
  return list;
 }

 public Service findService (String key)
 {
  for (Service serv : model.getServices ())
  {
   if (serv.getKey ().equalsIgnoreCase (key))
   {
    return serv;
   }
  }
  return null;
 }

 public Project findProject (String key)
 {
  for (Project proj : model.getProjects ())
  {
   if (proj.getKey ().equalsIgnoreCase (key))
   {
    return proj;
   }
  }
  return null;
 }

 public SearchType findSearchType (String key)
 {
  for (SearchType st : searchModel.getSearchTypes ())
  {
   if (st.getKey ().equalsIgnoreCase (key))
   {
    return st;
   }
  }
  return null;
 }

 public ServiceMethod findServiceMethod (String service, String name)
 {
  for (ServiceMethod method : serviceMethodModel.getServiceMethods ())
  {
   if (method.getService ().equalsIgnoreCase (service))
   {
    if (method.getName ().equalsIgnoreCase (name))
    {
     return method;
    }
   }
  }
  return null;
 }

 public List<ServiceMethod> getServiceMethodsInService (String service)
 {
  List<ServiceMethod> list = new ArrayList ();
  for (ServiceMethod method : serviceMethodModel.getServiceMethods ())
  {
   if (method.getService ().equalsIgnoreCase (service))
   {
    list.add (method);
   }
  }
  return list;
 }

 public List<MessageStructure> findMessageStructures (String xmlType)
 {
  List<MessageStructure> list = new ArrayList ();
  for (MessageStructure ms : model.getMessageStructures ())
  {
   if (ms.getXmlObject ().equalsIgnoreCase (xmlType))
   {
    list.add (ms);
   }
  }
  return list;
 }

 private Type defaultType = null;

 public Type getDefaultType ()
 {
  if (defaultType != null)
  {
   return defaultType;
  }
  List<Type> list = findTypes (Type.DEFAULT);
  if (list.size () == 0)
  {
   throw new DictionaryExecutionException ("No default Type found");
  }
  if (list.size () > 1)
  {
   throw new DictionaryExecutionException ("More than one default Type found");
  }
  defaultType = list.get (0);
  return defaultType;
 }

 private State defaultState = null;

 public State getDefaultState ()
 {
  if (defaultState != null)
  {
   return defaultState;
  }
  List<State> list = findStates (State.DEFAULT);
  if (list.size () == 0)
  {
   throw new DictionaryExecutionException ("No default State found");
  }
  if (list.size () > 1)
  {
   throw new DictionaryExecutionException ("More than one default State found");
  }
  defaultState = list.get (0);
  return defaultState;
 }

}

