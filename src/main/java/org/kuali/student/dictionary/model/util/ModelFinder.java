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
import org.kuali.student.dictionary.model.validation.DictionaryValidationException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

 public Type findType (String xmlObject, String typeName)
 {
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

 public Dictionary findRoot (Dictionary dict)
 {
  if (dict.getParentObject ().equalsIgnoreCase (Type.NA))
  {
   return dict;
  }
  Dictionary parent = findParent (dict);
  if (parent == null)
  {
   throw new DictionaryValidationException ("dictionary entry " + dict.getId () +
    " has no parent but parent object is not " + Type.NA);
  }
  return findRoot (parent);
 }

 public Dictionary findParent (Dictionary dict)
 {
  for (Dictionary d : model.getDictionary ())
  {
   if (d.getXmlObject ().equalsIgnoreCase (dict.getParentObject ()))
   {
    if (d.getShortName ().equalsIgnoreCase (dict.getParentShortName ()))
    {
     return d;
    }
   }
  }
  return null;
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
 public List<Dictionary> findDefaultDictionary ()
 {
  List<Dictionary> list = new ArrayList (model.getDictionary ().size ());
  for (Dictionary d : model.getDictionary ())
  {
   if (d.getMainState ().equalsIgnoreCase (State.DEFAULT))
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
  List<Dictionary> list = new ArrayList (model.getDictionary ().size ());
  for (Dictionary d : model.getDictionary ())
  {
   if ( ! d.getMainState ().equalsIgnoreCase (State.DEFAULT))
   {
    list.add (d);
   }
  }
  return list;
 }

 /**
  * Expands a type that has a status of Type.GROUPING.
  * A group can contain another group
  * @param type
  * @return
  */
 public Set<Type> findExpandedTypes (Type type)
 {
  Set<Type> types = new HashSet ();
  String pattern = type.getTypeKey ();
  GroupTypeStatePatternMatcher matcher =
   new GroupTypeStatePatternMatcher (pattern);
  for (Type t : model.getTypes ())
  {
   // can't match yourself
   if (t == type)
   {
    System.out.println ("skipping itself " + type.getName ());
    continue;
   }
   if (t.getInclude ().equalsIgnoreCase ("false"))
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
      types.addAll (findExpandedTypes (t));
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

}

