/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.dictionary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.kuali.student.dictionary.OrchestrationObjectField.FieldTypeCategory;

/**
 *
 * @author nwright
 */
public class OrchestrationObjectsWriter
{

 private DictionaryModel model;
 private String directory;
 public static final String ROOT_PACKAGE = "org.kuali.student.orchestration";

 public OrchestrationObjectsWriter (DictionaryModel model, String directory)
 {
  this.model = model;
  this.directory = directory;
 }

 /**
  * Write out the entire file
  * @param out
  */
 public void write ()
 {
  this.validate ();

  // first do from message structures
  Map<String, OrchestrationObject> orchObjs =
   getOrchestrationObjectsFromMessageStructures ();
  orchObjs.putAll (this.getOrchestrationObjectsFromOrchObjs ());
  for (OrchestrationObject oo : orchObjs.values ())
  {
   System.out.println ("Writing out " + oo.getFullyQualifiedJavaClassDataName ());
   OrchestrationObjectDataHelperWriter writer =
    new OrchestrationObjectDataHelperWriter (model, directory, orchObjs, oo);
   writer.write ();
  }
 }

 private void validate ()
 {
  Collection<String> errors =
   new OrchestrationModelValidator (model).validate ();
  if (errors.size () > 0)
  {
   StringBuffer buf = new StringBuffer ();
   buf.append (errors.size () +
    " errors found while validating the spreadsheet.");
   int cnt = 0;
   for (String msg : errors)
   {
    cnt ++;
    buf.append ("\n");
    buf.append ("*error*" + cnt + ":" + msg);
   }

   throw new DictionaryValidationException (buf.toString ());
  }

 }

 private Map<String, OrchestrationObject> getOrchestrationObjectsFromMessageStructures ()
 {
  Map<String, OrchestrationObject> map = new HashMap ();
  for (XmlType xmlType : model.getXmlTypes ())
  {
   // TODO: remove this hack once all java packages exist so we are not trying to copy them.
   //       ALSO remove the one below
   //if (xmlType.getJavaPackage ().equals (""))
   //{
   // continue;
   // }
   if (xmlType.getPrimitive ().equals ("Complex"))
   {
    OrchestrationObject obj = new OrchestrationObject ();
    map.put (xmlType.getName ().toLowerCase (), obj);
    obj.setName (xmlType.getName ());
    obj.setInfoPackagePath (xmlType.getJavaPackage ());
    obj.setDataPackagePath (ROOT_PACKAGE + ".base");

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
      // TODO: remove this hack once all java packages exist so we are not trying to copy them.
      //       ALSO remove the one above
//      Field dictField = new ModelFinder (model).findField (ms.getId ());
//      if (dictField == null)
//      {
//       throw new DictionaryValidationException ("could not find corresponding field entry for message structure entry " +
//        ms.getId ());
//      }
//      XmlType fieldXmlType = new ModelFinder (model).findXmlType (dictField.
//       getXmlType ());
//      if (fieldXmlType.getPrimitive ().equals ("Complex"))
//      {
//       if (fieldXmlType.getJavaPackage ().equals (""))
//       {
//        continue;
//       }
//      }
      OrchestrationObjectField field = new OrchestrationObjectField ();
      fields.add (field);
      field.setParent (obj);
      field.setName (ms.getShortName ());
      field.setType (calcType (ms.getType ()));
      field.setFieldTypeCategory (
       calcFieldTypeCategory (field, calcIsList (ms.getType ()), true));
     }

    }
   }
  }
  return map;
 }

 public boolean calcIsList (String type)
 {
  if (type.endsWith ("List"))
  {
   return true;
  }

  return false;
 }

 public String calcType (String type)
 {
  if (type.endsWith ("List"))
  {
   type = type.substring (0, type.length () - "List".length ());
  }

  return type;
 }

 private FieldTypeCategory calcFieldTypeCategory (OrchestrationObjectField field,
                                                  boolean isAList, boolean mustBeInXmlTypes)
 {
  if (field.getType ().equalsIgnoreCase ("attributeInfo"))
  {
   return FieldTypeCategory.DYNAMIC_ATTRIBUTE;
  }
  if (isAList)
  {
   return FieldTypeCategory.LIST;
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

 private Map<String, OrchestrationObject> getOrchestrationObjectsFromOrchObjs ()
 {
  Map<String, OrchestrationObject> map = new HashMap ();
  OrchestrationObject obj = null;
  List<OrchestrationObjectField> fields = null;
  for (OrchObj orch : model.getOrchObjs ())
  {
   // reset and add the object
   if ( ! orch.getParent ().equals (""))
   {
    obj = new OrchestrationObject ();
    // TODO: worry about qualifying the name somehow so we can support different orchestrations
    map.put (orch.getParent ().toLowerCase (), obj);
    obj.setName (orch.getParent ());
    // TODO: add this to spreadsheet
    obj.setAssembleFromClass ("TODO: add this to spreadsheet");
    obj.setDataPackagePath (ROOT_PACKAGE + ".orch");
    obj.setHasOwnCreateUpdate (true);
    fields =
     new ArrayList ();
    obj.setFields (fields);
    continue;

   }

   if ( ! orch.getChild ().equals (""))
   {
    OrchestrationObjectField field = new OrchestrationObjectField ();
    fields.add (field);
    field.setParent (obj);
    field.setName (orch.getChild ());
    field.setType (calcType (orch.getXmlType ()));
    field.setFieldTypeCategory (
     calcFieldTypeCategory (field, calcIsCardList (orch.getCard1 ()), false));
    continue;

   }
   //TODO: worry about grand children

  }
  return map;
 }

 public boolean calcIsCardList (String type)
 {
  // TODO: make this logic more robust to handle cases like 1-5 etc
  if (type.endsWith ("-N"))
  {
   return true;
  }

  return false;
 }

}
