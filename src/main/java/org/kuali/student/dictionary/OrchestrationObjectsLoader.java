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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.kuali.student.dictionary.OrchestrationObjectField.FieldTypeCategory;

/**
 *
 * @author nwright
 */
public class OrchestrationObjectsLoader
{

 private DictionaryModel model;
 private String directory;
 public static final String ROOT_PACKAGE = "org.kuali.student.orchestration";

 public OrchestrationObjectsLoader (DictionaryModel model, String directory)
 {
  this.model = model;
  this.directory = directory;
 }

 /**
  * Load the Orchestration objects from the model
  * @param out
  */
 public Map<String, OrchestrationObject> load ()
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
   if (xmlType.getPrimitive ().equals ("Complex"))
   {
    OrchestrationObject obj = new OrchestrationObject ();
    map.put (xmlType.getName ().toLowerCase (), obj);
    obj.setName (xmlType.getName ());
    obj.setInfoPackagePath (xmlType.getJavaPackage ());
    obj.setOrchestrationPackagePath (ROOT_PACKAGE + ".base");

    // these orchestratration data objects get assembled from versions of themself
    // i.e CluInfoData from CluInfo
    obj.setAssembleFromClass (xmlType.getName ());
    obj.setHasOwnCreateUpdate (xmlType.getHasOwnCreateUpdate ().equals ("true"));
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
       calcFieldTypeCategory (field, calcIsList (ms.getType ()), true));
      loadMessageStructureFieldConstraints (field);
     }
    }
   }
  }
  return map;
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
  if (field.getType ().equalsIgnoreCase ("attributeInfo"))
  {
   return FieldTypeCategory.DYNAMIC_ATTRIBUTE;
  }
  if (isAList)
  {
   return FieldTypeCategory.LIST;
  }
  if (field.getType ().equalsIgnoreCase ("Complex-Inline"))
  {
   return FieldTypeCategory.COMPLEX_INLINE;
  }
  XmlType xmlType = new ModelFinder (model).findXmlType (field.getType ());
  if (xmlType == null)
  {
   if (mustBeInXmlTypes)
   {
    throw new DictionaryValidationException ("No XmlType found for field type " +
     field.getType () + " " + field.getName ());
   }
   // if not found it must be a complex orchestration object
   return FieldTypeCategory.COMPLEX;
  }

  if (xmlType.getPrimitive ().equalsIgnoreCase ("Primitive"))
  {
   return FieldTypeCategory.PRIMITIVE;
  }

  if (xmlType.getPrimitive ().equalsIgnoreCase ("Mapped String"))
  {
   return FieldTypeCategory.MAPPED_STRING;
  }

  if (xmlType.getPrimitive ().equalsIgnoreCase ("Complex"))
  {
   return FieldTypeCategory.COMPLEX;
  }

  throw new DictionaryValidationException ("Unknown/unhandled xmlType.primtive value, " +
   xmlType.getPrimitive () + ", for field type " +
   field.getType () + " for field " + field.getName ());
 }

 private void loadMessageStructureFieldConstraints (
  OrchestrationObjectField ooField)
 {
  ModelFinder finder = new ModelFinder (model);
  Field field = finder.findField (ooField.getParent ().getName () + "." +
   ooField.getName ());
  for (String consId : field.getConstraintIds ())
  {
   Constraint cons = finder.findConstraint (consId);
   if (cons == null)
   {
    throw new DictionaryValidationException ("Could not find constraint id [" +
     consId + "] for field [" + field.getId () + "] in bank of constraints");
   }
   TypeStateConstraint tsCons =
    new TypeStateConstraint (Type.DEFAULT, State.DEFAULT, cons);
   ooField.getConstraints ().add (tsCons);
  }
  if (field.getInlineConstraint () != null)
  {
   if (new ConstraintInterrogator (field.getInlineConstraint ()).
    constrainsSomething ())
   {
    TypeStateConstraint tsCons =
     new TypeStateConstraint (Type.DEFAULT, State.DEFAULT, field.
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
   // reset and add the object
   if ( ! orch.getParent ().equals (""))
   {
    parentObj = new OrchestrationObject ();
    // TODO: worry about qualifying the name somehow so we can support different orchestrations
    map.put (orch.getParent ().toLowerCase (), parentObj);
    parentObj.setName (orch.getParent ());
    // TODO: add this to spreadsheet
    parentObj.setAssembleFromClass ("TODO: add this to spreadsheet");
    parentObj.setOrchestrationPackagePath (ROOT_PACKAGE + ".orch");
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
    childField.setFieldTypeCategory (
     calcFieldTypeCategory (childField, calcIsCardList (orch.getCard1 ()), false));
    continue;
   }
   if ( ! orch.getGrandChild ().equals (""))
   {
    OrchestrationObjectField grandChildField = new OrchestrationObjectField ();
    OrchestrationObject inlineObj = childField.getInlineObject ();
    if (inlineObj == null)
    {
     inlineObj = new OrchestrationObject ();
     childField.setInlineObject (inlineObj);
     inlineObj.setInlineField (childField);
     inlineObj.setName (childField.getName ());
     inlineObj.setAssembleFromClass ("TODO: add this to spreadsheet");
     inlineObj.setOrchestrationPackagePath (ROOT_PACKAGE + ".orch");
     inlineObj.setHasOwnCreateUpdate (false);
     inlineObj.setFields (new ArrayList ());
    }
    inlineObj.getFields ().add (grandChildField);
    grandChildField.setParent (inlineObj);
    grandChildField.setName (orch.getGrandChild ());
    grandChildField.setType (calcType (orch.getXmlType ()));
    grandChildField.setFieldTypeCategory (
     calcFieldTypeCategory (grandChildField, calcIsCardList (orch.getCard2 ()), false));
    continue;
   }

  }
  return map;
 }

 private boolean calcIsCardList (String type)
 {
  // TODO: make this logic more robust to handle cases like 1-5 etc
  if (type.endsWith ("-N"))
  {
   return true;
  }

  return false;
 }

}
