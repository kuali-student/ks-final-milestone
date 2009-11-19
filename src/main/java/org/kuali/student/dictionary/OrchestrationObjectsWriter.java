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
      field.setIsList (calcIsList (ms.getType ()));
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

}
