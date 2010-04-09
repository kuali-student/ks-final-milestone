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
import org.kuali.student.dictionary.writer.orch.FieldTypeCategoryCalculator;
import org.kuali.student.dictionary.writer.orch.ConstraintInterrogator;
import org.kuali.student.dictionary.model.validation.DictionaryValidationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.kuali.student.core.assembly.data.LookupMetadata;
import org.kuali.student.dictionary.DictionaryExecutionException;
import org.kuali.student.dictionary.model.Constraint;
import org.kuali.student.dictionary.model.Dictionary;
import org.kuali.student.dictionary.model.DictionaryModel;
import org.kuali.student.dictionary.model.Field;
import org.kuali.student.dictionary.model.MessageStructure;
import org.kuali.student.dictionary.model.OrchObj;
import org.kuali.student.dictionary.model.OrchestrationModel;
import org.kuali.student.dictionary.model.OrchestrationObject;
import org.kuali.student.dictionary.model.OrchestrationObjectField;
import org.kuali.student.dictionary.model.OrchestrationObjectField.FieldTypeCategory;
import org.kuali.student.dictionary.model.State;
import org.kuali.student.dictionary.model.Type;
import org.kuali.student.dictionary.model.TypeStateConstraint;
import org.kuali.student.dictionary.model.XmlType;

/**
 *
 * @author nwright
 */
public class OrchestrationObjectsLoader implements OrchestrationModel
{

 private DictionaryModel model;
 private ModelFinder finder;
 private String rootPackage;
 private List<LookupMetadata> lookupMetas;

 public OrchestrationObjectsLoader (DictionaryModel model,
                                    String rootPackage)
 {
  this.model = model;
  this.finder = new ModelFinder (model);
  this.lookupMetas = new SearchTypesToLookupMetadataBankConverter (model).getLookups ();
  this.rootPackage = rootPackage;
 }

 public List<LookupMetadata> getLookups ()
 {
  return this.lookupMetas;
 }



 /**
  * Load the Orchestration objects from the model
  * @param out
  */
 @Override
 public Map<String, OrchestrationObject> getOrchestrationObjects ()
 {

  // first do from message structures
  Map<String, OrchestrationObject> orchObjs =
   getOrchestrationObjectsFromMessageStructures ();
  orchObjs.putAll (this.getOrchestrationObjectsFromOrchObjs ());

  return orchObjs;
 }

 private Map<String, OrchestrationObject> getOrchestrationObjectsFromMessageStructures ()
 {
  Map<String, OrchestrationObject> map = new HashMap ();
  for (XmlType xmlType : model.getXmlTypes ())
  {
   if (xmlType.getService ().equalsIgnoreCase ("organization"))
   {
    continue;
   }
//   if (xmlType.getService ().equalsIgnoreCase ("atp"))
//   {
//    continue;
//   }
   if (xmlType.getPrimitive ().equalsIgnoreCase (XmlType.COMPLEX))
   {
    OrchestrationObject obj = new OrchestrationObject ();
    obj.setSource (OrchestrationObject.Source.MESSAGE_STRUCTURE);
    map.put (xmlType.getName ().toLowerCase (), obj);
    obj.setName (xmlType.getName ());
    obj.setInfoPackagePath (xmlType.getJavaPackage ());
    obj.setOrchestrationPackagePath (rootPackage + ".base");

    obj.setHasOwnCreateUpdate (xmlType.hasOwnCreateUpdate ());
    List<OrchestrationObjectField> fields = new ArrayList ();
    obj.setFields (fields);
    for (MessageStructure ms : model.getMessageStructures ())
    {
     if (ms.getXmlObject ().equalsIgnoreCase (xmlType.getName ()))
     {
      OrchestrationObjectField field = new OrchestrationObjectField ();
      fields.add (field);
      field.setParent (obj);
      field.setName (ms.getShortName ());
      field.setType (calcType (ms.getType ()));
      field.setFieldTypeCategory (
       calcFieldTypeCategory (field, calcIsList (ms.getType ()), false));
      field.setWriteAccess (calcMessageStructureWriteAccess (ms));
      loadMessageStructureFieldConstraints (field, field.getParent ().getName () +
       "." +
       field.getName ());
     }
    }
   }
  }
  return map;
 }

 private OrchestrationObjectField.WriteAccess calcMessageStructureWriteAccess (
  MessageStructure ms)
 {
  if (ms.getShortName ().equalsIgnoreCase ("id"))
  {
   return OrchestrationObjectField.WriteAccess.NEVER;
  }
  if (ms.getShortName ().equalsIgnoreCase ("key"))
  {
   return OrchestrationObjectField.WriteAccess.NEVER;
  }
  if (ms.getShortName ().equalsIgnoreCase ("type"))
  {
   return OrchestrationObjectField.WriteAccess.ON_CREATE;
  }
  if (ms.getShortName ().equalsIgnoreCase ("metaInfo"))
  {
   return OrchestrationObjectField.WriteAccess.NEVER;
  }
  if (ms.getShortName ().equalsIgnoreCase ("versionInd"))
  {
   return OrchestrationObjectField.WriteAccess.NEVER;
  }
  if (ms.getShortName ().equalsIgnoreCase ("createTime"))
  {
   return OrchestrationObjectField.WriteAccess.NEVER;
  }
  if (ms.getShortName ().equalsIgnoreCase ("createId"))
  {
   return OrchestrationObjectField.WriteAccess.NEVER;
  }
  if (ms.getShortName ().equalsIgnoreCase ("updateTime"))
  {
   return OrchestrationObjectField.WriteAccess.NEVER;
  }
  if (ms.getShortName ().equalsIgnoreCase ("updateId"))
  {
   return OrchestrationObjectField.WriteAccess.NEVER;
  }
  return OrchestrationObjectField.WriteAccess.ALWAYS;
 }

 private boolean calcIsList (String type)
 {
  if (type.endsWith ("List"))
  {
   return true;
  }

  return false;
 }

 private String calcType (String type)
 {
  if (type.endsWith ("List"))
  {
   type = type.substring (0, type.length () - "List".length ());
  }

  return type;
 }

 private FieldTypeCategory calcFieldTypeCategory (OrchestrationObjectField field,
                                                  boolean isAList,
                                                  boolean mustBeInXmlTypes)
 {
  return FieldTypeCategoryCalculator.calculate (field, isAList, mustBeInXmlTypes, model);
 }

 private void loadMessageStructureFieldConstraints (
  OrchestrationObjectField ooField, String fieldId)
 {
  Field msField = finder.findField (fieldId);
  if (msField == null)
  {
   throw new DictionaryExecutionException
    ("could not find field with field id " + fieldId
    + " on ooField " + ooField.getFullyQualifiedName ());
  }
  loadMessageStructureFieldConstraints (ooField, msField);
 }

  private void loadMessageStructureFieldConstraints (
  OrchestrationObjectField ooField, Dictionary dict)
 {
  Field msField = finder.findField (dict);
  if (msField == null)
  {
   throw new DictionaryExecutionException
    ("could not find field with for dictionary with id " + dict.getId ()
    + " on ooField " + ooField.getFullyQualifiedName ());
  }
  loadMessageStructureFieldConstraints (ooField, msField);
 }

  private void loadMessageStructureFieldConstraints (OrchestrationObjectField ooField, Field msField)
 {
  for (String consId : msField.getConstraintIds ())
  {
   Constraint cons = finder.findConstraint (consId);
   if (cons == null)
   {
    throw new DictionaryValidationException ("Could not find constraint id [" +
     consId + "] for field [" + msField.getId () + "] in bank of constraints");
   }
   TypeStateConstraint tsCons =
    new TypeStateConstraint (Type.DEFAULT, State.DEFAULT, cons);
   ooField.getConstraints ().add (tsCons);
  }
  if (msField.getInlineConstraint () != null)
  {
   if (new ConstraintInterrogator (msField.getInlineConstraint ()).
    constrainsSomething ())
   {
    TypeStateConstraint tsCons =
     new TypeStateConstraint (Type.DEFAULT, State.DEFAULT, msField.
     getInlineConstraint ());
    ooField.getConstraints ().add (tsCons);
   }
  }
  // TODO: Add dictionary based constraints
 }

 private Map<String, OrchestrationObject> getOrchestrationObjectsFromOrchObjs ()
 {
  Map<String, OrchestrationObject> map = new HashMap ();
  OrchestrationObject parentObj = null;
  OrchestrationObjectField childField = null;
  for (OrchObj orch : model.getOrchObjs ())
  {
   System.out.println ("Loading orchObj " + orch.getId ());
   // reset and add the object
   if ( ! orch.getParent ().equals (""))
   {
    parentObj = new OrchestrationObject ();
    parentObj.setSource (OrchestrationObject.Source.ORCH_OBJS);
    // TODO: worry about qualifying the name somehow so we can support different orchestrations
    map.put (orch.getParent ().toLowerCase (), parentObj);
    parentObj.setName (orch.getParent ());
    parentObj.setOrchestrationPackagePath (rootPackage + ".orch");
    parentObj.setHasOwnCreateUpdate (true);
    parentObj.setFields (new ArrayList ());
    continue;

   }

   if ( ! orch.getChild ().equals (""))
   {
    childField = new OrchestrationObjectField ();
    parentObj.getFields ().add (childField);
    childField.setParent (parentObj);
    childField.setName (orch.getChild ());
    childField.setType (calcType (orch.getXmlType ()));
    childField.setMaxRecursions (calcMaxRecursions (orch));
    childField.setDefaultValue (orch.getDefaultValue ());
    childField.setDefaultValuePath (orch.getDefaultValuePath ());
    childField.setFieldTypeCategory (
     calcFieldTypeCategory (childField, calcIsCardList (orch.getCard1 ()), false));
    childField.setWriteAccess (calcWriteAccess (orch));
    loadOrchObjsFieldConstraints (childField, orch);
    childField.setLookup (orch.getLookup ());
    childField.setLookupContextPath (orch.getLookupContextPath ());
    loadAdditionalLookups (childField);
    continue;
   }
   if ( ! orch.getGrandChild ().equals (""))
   {
    OrchestrationObjectField grandChildField = new OrchestrationObjectField ();
    OrchestrationObject inlineObj = childField.getInlineObject ();
    if (inlineObj == null)
    {
     inlineObj = new OrchestrationObject ();
     inlineObj.setSource (OrchestrationObject.Source.ORCH_OBJS);
     childField.setInlineObject (inlineObj);
     inlineObj.setInlineField (childField);
     inlineObj.setName (childField.getName ());
     inlineObj.setOrchestrationPackagePath (rootPackage + ".orch");
     inlineObj.setHasOwnCreateUpdate (false);
     inlineObj.setFields (new ArrayList ());
    }
    inlineObj.getFields ().add (grandChildField);
    grandChildField.setParent (inlineObj);
    grandChildField.setName (orch.getGrandChild ());
    grandChildField.setType (calcType (orch.getXmlType ()));
    grandChildField.setMaxRecursions (calcMaxRecursions (orch));
    grandChildField.setDefaultValue (orch.getDefaultValue ());
    grandChildField.setDefaultValuePath (orch.getDefaultValuePath ());
    grandChildField.setFieldTypeCategory (
     calcFieldTypeCategory (grandChildField, calcIsCardList (orch.getCard2 ()), false));
    grandChildField.setWriteAccess (calcWriteAccess (orch));
    loadOrchObjsFieldConstraints (grandChildField, orch);
    grandChildField.setLookup (orch.getLookup ());
    grandChildField.setLookupContextPath (orch.getLookupContextPath ());
    loadAdditionalLookups (childField);
    continue;
   }

  }
  return map;
 }

 private void loadAdditionalLookups (OrchestrationObjectField field)
 {
  if (field.getLookup () == null)
  {
   return;
  }
  if (field.getLookup ().equals (""))
  {
   return;
  }
  List<String> list = new ArrayList ();
  for (LookupMetadata lookup : findAdditional (field.getLookup ()))
  {
   list.add (lookup.getId ());
  }
  field.setAdditionalLookups (list);
 }

 private List<LookupMetadata> findAdditional (String lookupKey)
 {
  List<LookupMetadata> list = new ArrayList ();
  for (LookupMetadata lookup : lookupMetas)
  {
   if (lookup.getId ().toLowerCase ().startsWith (lookupKey + ".additional."))
   {
    list.add (lookup);
   }
  }
  return list;
 }
 private Integer calcMaxRecursions (OrchObj orch)
 {
  String recursions = orch.getRecursions ();
  if (recursions == null)
  {
   return null;
  }
  if (recursions.equals (""))
  {
   return null;
  }
  try
  {
   int maxRecursions = Integer.parseInt (recursions);
   if (maxRecursions < 1)
   {
    throw new DictionaryValidationException ("OrchObj " + orch.getId () +
     " has a value for recursions that is not a positive integer [" + recursions +
     "]");
   }
   return maxRecursions;
  }
  catch (NumberFormatException ex)
  {
   throw new DictionaryValidationException ("OrchObj " + orch.getId () +
    " has a value for recursions that is not an integer [" + recursions + "]");
  }
 }

 private OrchestrationObjectField.WriteAccess calcWriteAccess (OrchObj orch)
 {
  String writeAccess = orch.getWriteAccess ();
  if (writeAccess.equalsIgnoreCase ("Always"))
  {
   return OrchestrationObjectField.WriteAccess.ALWAYS;
  }
  if (writeAccess.equalsIgnoreCase ("Never"))
  {
   return OrchestrationObjectField.WriteAccess.NEVER;
  }
  if (writeAccess.equalsIgnoreCase ("OnCreate"))
  {
   return OrchestrationObjectField.WriteAccess.ON_CREATE;
  }
  throw new DictionaryValidationException ("OrchObj " + orch.getId () +
   " has an unknown/unhandled value for WriteAccess [" + writeAccess + "]");
 }

 private void loadOrchObjsFieldConstraints (
  OrchestrationObjectField ooField, OrchObj orchObj)
 {
  for (String consId : orchObj.getConstraintIds ())
  {
   Constraint cons = finder.findConstraint (consId);
   if (cons == null)
   {
    throw new DictionaryValidationException ("Could not find constraint id [" +
     consId + "] for orchestration object [" + orchObj.getId () +
     "] in bank of constraints");
   }
   TypeStateConstraint tsCons =
    new TypeStateConstraint (Type.DEFAULT, State.DEFAULT, cons);
   ooField.getConstraints ().add (tsCons);
  }
  if (orchObj.getInlineConstraint () != null)
  {
   if (new ConstraintInterrogator (orchObj.getInlineConstraint ()).
    constrainsSomething ())
   {
    TypeStateConstraint tsCons =
     new TypeStateConstraint (Type.DEFAULT, State.DEFAULT, orchObj.
     getInlineConstraint ());
    ooField.getConstraints ().add (tsCons);
   }
  }
  if ( ! orchObj.getDictionaryId ().equals (""))
  {
   Dictionary dict = finder.findDictionaryEntry (orchObj.getDictionaryId ());
   if (dict == null)
   {
    throw new DictionaryValidationException ("Could not find dictionary Entry [" +
     orchObj.getDictionaryId () + "] for orchestration object field " + orchObj.
     getId ());
   }
   loadConstraintsForDictionaryEntry (ooField, dict);
   loadMessageStructureFieldConstraints (ooField, dict);
  }
 }

 private void loadConstraintsForDictionaryEntry (
  OrchestrationObjectField ooField, Dictionary dict)
 {
  for (String consId : dict.getAdditionalConstraintIds ())
  {
   Constraint cons = finder.findConstraint (consId);
   if (cons == null)
   {
    throw new DictionaryValidationException ("Could not find constraint id [" +
     consId + "] for dictionary [" + dict.getId () + "] in bank of constraints");
   }
   TypeStateConstraint tsCons =
    new TypeStateConstraint (Type.DEFAULT, State.DEFAULT, cons);
   ooField.getConstraints ().add (tsCons);
  }
  if (dict.getInlineConstraint () != null)
  {
   if (new ConstraintInterrogator (dict.getInlineConstraint ()).
    constrainsSomething ())
   {
    TypeStateConstraint tsCons =
     new TypeStateConstraint (Type.DEFAULT, State.DEFAULT, dict.
     getInlineConstraint ());
    ooField.getConstraints ().add (tsCons);
   }
  }
 }

 private boolean calcIsCardList (String type)
 {
  // TODO: make this logic more robust to handle cases like 1-5 etc
  if (type.equalsIgnoreCase ("N"))
  {
   return true;
  }

  return false;
 }

}
