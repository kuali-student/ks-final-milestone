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
package org.kuali.student.dictionary.writer;

import org.kuali.student.dictionary.model.TypeStateConstraint;
import org.kuali.student.dictionary.model.OrchestrationObject;
import org.kuali.student.dictionary.model.OrchestrationObjectField;
import org.kuali.student.dictionary.model.validation.DictionaryValidationException;
import org.kuali.student.dictionary.model.DictionaryModel;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.common.assembly.client.Metadata;
import org.kuali.student.common.assembly.client.QueryPath;
import org.kuali.student.dictionary.DictionaryExecutionException;

/**
 *
 * @author nwright
 */
public class OrchestrationObjectMetadataWriter extends JavaClassWriter
{

 private DictionaryModel model;
 private String directory;
 private String rootPackage;
 private OrchestrationObject orchObj;
 private Map<String, OrchestrationObject> orchObjs;

 public OrchestrationObjectMetadataWriter (DictionaryModel model,
                                           String directory,
                                           String rootPackage,
                                           Map<String, OrchestrationObject> orchObjs,
                                           OrchestrationObject orchObj)
 {
  super (directory, orchObj.getOrchestrationPackagePath (), orchObj.
   getJavaClassMetadataName ());
  this.model = model;
  this.directory = directory;
  this.rootPackage = rootPackage;
  this.orchObjs = orchObjs;
  this.orchObj = orchObj;
 }

 /**
  * Write out the entire file
  * @param out
  */
 public void write ()
 {
  // recursively call any in-line orchestration objects
  for (OrchestrationObjectField field : orchObj.getFields ())
  {
   if (field.getInlineObject () != null)
   {
    OrchestrationObjectMetadataWriter inlineWriter =
     new OrchestrationObjectMetadataWriter (model,
                                            directory,
                                            rootPackage,
                                            orchObjs,
                                            field.getInlineObject ());
    inlineWriter.write ();
   }
  }


  indentPrintln ("public class " + orchObj.getJavaClassMetadataName ());
  openBrace ();



  indentPrintln ("");
  indentPrintln ("public boolean matches (String inputType, String inputState, String dictType, String dictState)");
  openBrace ();
  indentPrintln ("// TODO: code more complex matches");
  indentPrintln ("return true;");
  closeBrace ();
  indentPrintln ("");

  // get metadata
  imports.add (Metadata.class.getName ());
  indentPrintln ("public Metadata getMetadata (String type, String state)");
  openBrace ();
  indentPrintln ("Metadata mainMeta = new Metadata ();");
  indentPrintln ("mainMeta.setDataType (Data.DataType.DATA);");
  indentPrintln ("mainMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);");
  indentPrintln ("Map <String, Integer> recursions = new HashMap ();");
  indentPrintln ("loadChildMetadata (mainMeta, type, state, recursions);");
  indentPrintln ("return mainMeta;");
  closeBrace (); // end getMetadata method
  indentPrintln ("");

  imports.add (Map.class.getName ());
  imports.add (HashMap.class.getName ());
  indentPrintln ("public void loadChildMetadata (Metadata mainMeta, String type, String state,  Map<String, Integer> recursions)");
  openBrace ();
  indentPrintln ("int recurseLevel = increment (recursions, " + quote (orchObj.
   getJavaClassMetadataName ()) + ");");
  indentPrintln ("");
  indentPrintln ("Metadata childMeta;");
  // TODO: only write this next line if we have at least one list
  indentPrintln ("Metadata listMeta;");
  for (OrchestrationObjectField field : orchObj.getFields ())
  {
   writeMetadataForField (field);
  }
  indentPrintln ("");
  closeBrace (); // end loadChildMetadata method

  indentPrintln ("");
  indentPrintln ("private int increment (Map<String, Integer> recursions, String key)");
  openBrace ();
  indentPrintln ("Integer recurseLevel = recursions.get (key);");
  indentPrintln ("if (recurseLevel == null)");
  openBrace ();
  indentPrintln ("recursions.put (key, 0);");
  indentPrintln ("return 0;");
  closeBrace ();
  indentPrintln ("recursions.put (key, recurseLevel.intValue () + 1);");
  indentPrintln ("return recurseLevel.intValue ();");
  closeBrace ();

  closeBrace (); // end class

  this.writeJavaClassAndImportsOutToFile ();
  this.getOut ().close ();
 }

 private void writeMetadataForField (OrchestrationObjectField field)
 {
  System.out.println ("Writing metadata for field " + field.
   getFullyQualifiedName ());
  indentPrintln ("");
  indentPrintln ("// metadata for " + field.getName ());
  indentPrintln ("childMeta = new Metadata ();");
  String constant = new JavaEnumConstantCalculator (field.getName ()).calc ();
  imports.add (orchObj.getFullyQualifiedJavaClassHelperName () + ".Properties");
  indentPrintln ("mainMeta.getProperties ().put (Properties." + constant +
   ".getKey (), childMeta);");

  imports.add (Data.class.getName ());
  indentPrintln ("childMeta.setDataType (Data.DataType." +
   calcDataTypeToUse (field) + ");");
  indentPrintln ("childMeta.setWriteAccess (Metadata.WriteAccess." + field.
   getWriteAccess () + ");");
  String defVal = calcDefaultValueDataType (field);
  if (defVal != null)
  {
   //		childMeta.setDefaultValue (new Data.StringValue ("Draft"));
   indentPrintln ("childMeta.setDefaultValue (" + defVal + ");");
  }
  if (field.getDefaultValuePath () != null &&  ! field.getDefaultValuePath ().
   equals (""))
  {
   indentPrintln ("childMeta.setDefaultValuePath (" +
    quote (field.getDefaultValuePath ()) + ");");
  }
  if (field.getLookup () != null &&  ! field.getLookup ().equals (""))
  {
   imports.add (rootPackage + ".LookupMetadataBank");
   indentPrintln ("childMeta.setLookupMetadata (LookupMetadataBank.LOOKUP_BANK.get (" +
    quote (field.getLookup ()) + ".toLowerCase ()));");
   indentPrintln ("childMeta.setLookupContextPath (" +
    quote (replaceEmptyWitNull (field.getLookupContextPath ())) + ");");
  }
  if (field.getAdditionalLookups () != null)
  {
   for (String additinalLookup : field.getAdditionalLookups ())
   {
    imports.add (rootPackage + ".LookupMetadataBank");
    indentPrintln ("childMeta.getAdditionalLookups ().add ((LookupMetadataBank.LOOKUP_BANK.get (" +
     quote (additinalLookup) + ".toLowerCase ())));");
   }
  }
  String lastType = null;
  String lastState = null;
  boolean closeIf = false;
  for (TypeStateConstraint tsCons : field.getConstraints ())
  {
   boolean writeIfTypeStateMatcher = true;
   if (lastType != null)
   {
    if (lastState != null)
    {
     if (lastType.equals (tsCons.getType ()))
     {
      if (lastState.equals (tsCons.getState ()))
      {
       writeIfTypeStateMatcher = false;
      }
     }
    }
   }
   lastType = tsCons.getType ();
   lastState = tsCons.getState ();
   closeIf = true;
   TypeStateConstraintMetadataWriter writer =
    new TypeStateConstraintMetadataWriter (this,
                                           tsCons,
                                           writeIfTypeStateMatcher,
                                           rootPackage);
   writer.write ();
  }
  if (closeIf)
  {
   closeBrace ();
  }
  switch (field.getFieldTypeCategory ())
  {
   case PRIMITIVE:
    break;
   case MAPPED_STRING:
    break;
   case DYNAMIC_ATTRIBUTE:
    break;
   case LIST_OF_PRIMITIVE:
   case LIST_OF_MAPPED_STRING:
   case LIST_OF_COMPLEX:
   case LIST_OF_COMPLEX_INLINE:
    imports.add (QueryPath.class.getName ());
    indentPrintln ("listMeta = new Metadata ();");
    indentPrintln ("listMeta.setDataType (Data.DataType." +
     calcDataTypeToUseForList (field) + ");");
    indentPrintln ("listMeta.setWriteAccess (Metadata.WriteAccess." + field.
     getWriteAccess () + ");");
    indentPrintln ("childMeta.getProperties ().put (QueryPath.getWildCard (), listMeta);");
    switch (field.getFieldTypeCategory ())
    {
     case LIST_OF_COMPLEX:
      OrchestrationObject fieldOO =
       orchObjs.get (field.getType ().toLowerCase ());
      if (fieldOO == null)
      {
       throw new DictionaryValidationException ("Could not find orchestration object for field " +
        field.getName () + " type " + field.getType ());
      }
      indentPrintln ("if (recurseLevel >= " + field.getMaxRecursions () + ")");
      openBrace ();
      indentPrintln ("mainMeta.setWriteAccess (Metadata.WriteAccess.NEVER);");
      closeBrace ();
      indentPrintln ("else");
      openBrace ();
      imports.add (fieldOO.getFullyQualifiedJavaClassMetadataName ());
      indentPrintln ("new " + fieldOO.getJavaClassMetadataName () +
       " ().loadChildMetadata (listMeta, type, state, recursions);");
      closeBrace ();
      break;
     case LIST_OF_COMPLEX_INLINE:
      indentPrintln ("if (recurseLevel >= " + field.getMaxRecursions () + ")");
      openBrace ();
      indentPrintln ("mainMeta.setWriteAccess (Metadata.WriteAccess.NEVER);");
      closeBrace ();
      indentPrintln ("else");
      openBrace ();
      imports.add (field.getInlineObject ().
       getFullyQualifiedJavaClassMetadataName ());
      indentPrintln ("new " +
       field.getInlineObject ().getJavaClassMetadataName () +
       " ().loadChildMetadata (listMeta, type, state, recursions);");
      closeBrace ();
      break;
     default:
      break;
    }
    break;
   case COMPLEX:
    OrchestrationObject fieldOO = orchObjs.get (field.getType ().toLowerCase ());
    if (fieldOO == null)
    {
     throw new DictionaryValidationException ("Could not find orchestration object for field " +
      field.getName () + " type " + field.getType ());
    }
    indentPrintln ("if (recurseLevel >= " + field.getMaxRecursions () + ")");
    openBrace ();
    indentPrintln ("mainMeta.setWriteAccess (Metadata.WriteAccess.NEVER);");
    closeBrace ();
    indentPrintln ("else");
    openBrace ();
    imports.add (fieldOO.getFullyQualifiedJavaClassMetadataName ());
    indentPrintln ("new " + fieldOO.getJavaClassMetadataName () +
     " ().loadChildMetadata (childMeta, type, state, recursions);");
    closeBrace ();
    break;
   case COMPLEX_INLINE:
    indentPrintln ("if (recurseLevel >= " + field.getMaxRecursions () + ")");
    openBrace ();
    indentPrintln ("mainMeta.setWriteAccess (Metadata.WriteAccess.NEVER);");
    closeBrace ();
    indentPrintln ("else");
    openBrace ();
    imports.add (field.getInlineObject ().getFullyQualifiedJavaClassHelperName ());
    indentPrintln ("new " + field.getInlineObject ().getJavaClassMetadataName () +
     " ().loadChildMetadata (childMeta, type, state, recursions);");
    closeBrace ();
    break;
   default:
    throw new DictionaryExecutionException ("unhandled type");
  }
 }

 private Data.DataType calcDataTypeToUse (OrchestrationObjectField field)
 {
  //XmlType xmlType = new ModelFinder (model).findXmlType (field.getType ());
  switch (field.getFieldTypeCategory ())
  {
   case LIST_OF_PRIMITIVE:
   case LIST_OF_MAPPED_STRING:
   case LIST_OF_COMPLEX:
   case LIST_OF_COMPLEX_INLINE:
    return Data.DataType.LIST;

   case DYNAMIC_ATTRIBUTE:
    return Data.DataType.DATA;

   case PRIMITIVE:
    if (field.getType ().equalsIgnoreCase ("string"))
    {
     return Data.DataType.STRING;
    }
    if (field.getType ().equalsIgnoreCase ("date"))
    {
     imports.add (Date.class.getName ());
     return Data.DataType.TRUNCATED_DATE;
    }
    if (field.getType ().equalsIgnoreCase ("dateTime"))
    {
     imports.add (Date.class.getName ());
     return Data.DataType.DATE;
    }
    if (field.getType ().equalsIgnoreCase ("boolean"))
    {
     return Data.DataType.BOOLEAN;
    }
    if (field.getType ().equalsIgnoreCase ("integer"))
    {
     return Data.DataType.INTEGER;
    }
    if (field.getType ().equalsIgnoreCase ("long"))
    {
     return Data.DataType.LONG;
    }
    throw new DictionaryValidationException (
     "Unknown/handled field type " +
     field.getType () + " " + field.getName ());

   case MAPPED_STRING:
    return Data.DataType.STRING;

   case COMPLEX:
    return Data.DataType.DATA;

   case COMPLEX_INLINE:
    return Data.DataType.DATA;
  }
  throw new DictionaryValidationException ("Unknown/unhandled field type category" +
   field.getFieldTypeCategory () + " for field type " +
   field.getType () + " for field " + field.getName ());
 }

 private Data.DataType calcDataTypeToUseForList (OrchestrationObjectField field)
 {
  //XmlType xmlType = new ModelFinder (model).findXmlType (field.getType ());
  switch (field.getFieldTypeCategory ())
  {
   case LIST_OF_PRIMITIVE:
    if (field.getType ().equalsIgnoreCase ("string"))
    {
     return Data.DataType.STRING;
    }
    if (field.getType ().equalsIgnoreCase ("date"))
    {
     imports.add (Date.class.getName ());
     return Data.DataType.TRUNCATED_DATE;
    }
    if (field.getType ().equalsIgnoreCase ("dateTime"))
    {
     imports.add (Date.class.getName ());
     return Data.DataType.DATE;
    }
    if (field.getType ().equalsIgnoreCase ("boolean"))
    {
     return Data.DataType.BOOLEAN;
    }
    if (field.getType ().equalsIgnoreCase ("integer"))
    {
     return Data.DataType.INTEGER;
    }
    if (field.getType ().equalsIgnoreCase ("long"))
    {
     return Data.DataType.LONG;
    }
    throw new DictionaryValidationException (
     "Unknown/handled field type " +
     field.getType () + " " + field.getName ());

   case LIST_OF_MAPPED_STRING:
    return Data.DataType.STRING;

   case LIST_OF_COMPLEX:
    return Data.DataType.DATA;

   case LIST_OF_COMPLEX_INLINE:
    return Data.DataType.DATA;
  }
  throw new DictionaryValidationException ("Expected a LIST but Unknown/unhandled field type category" +
   field.getFieldTypeCategory () + " for field type " +
   field.getType () + " for field " + field.getName ());
 }

 private String calcDefaultValueDataType (OrchestrationObjectField field)
 {
  if (field.getDefaultValue () == null)
  {
   return null;
  }
  if (field.getDefaultValue ().equals (""))
  {
   return null;
  }
  //XmlType xmlType = new ModelFinder (model).findXmlType (field.getType ());
  switch (field.getFieldTypeCategory ())
  {


   case PRIMITIVE:
    if (field.getType ().equalsIgnoreCase ("string"))
    {
     return "new Data.StringValue (" + quote (field.getDefaultValue ()) + ")";
    }

    if (field.getType ().equalsIgnoreCase ("date"))
    {
     throw new DictionaryValidationException ("Default values not supported for field type " +
      field.getFieldTypeCategory () + " in field " +
      field.getFullyQualifiedName ());
    }

    if (field.getType ().equalsIgnoreCase ("dateTime"))
    {
     throw new DictionaryValidationException ("Default values not supported for field type " +
      field.getFieldTypeCategory () + " in field " +
      field.getFullyQualifiedName ());
    }

    if (field.getType ().equalsIgnoreCase ("boolean"))
    {
     return "new Data.BooleanValue (Boolean." + field.getDefaultValue ().
      toUpperCase () + ")";
    }

    if (field.getType ().equalsIgnoreCase ("integer"))
    {
     return "new Data.IntegerValue (" + field.getDefaultValue () + ")";
    }

    if (field.getType ().equalsIgnoreCase ("long"))
    {
     return "new Data.LongValue (" + field.getDefaultValue () + ")";
    }

    throw new DictionaryValidationException (
     "Unknown/handled field type " +
     field.getType () + " " + field.getName ());

   case MAPPED_STRING:
    return "new Data.StringValue (" + quote (field.getDefaultValue ()) + ")";

   default:
    throw new DictionaryValidationException ("Default values not supported for field type " +
     field.getFieldTypeCategory () + " in field " +
     field.getFullyQualifiedName ());
  }
 }

 private String replaceEmptyWitNull (String str)
 {
  if (str == null)
  {
   return null;
  }
  if (str.equals (""))
  {
   return null;
  }
  return str;
 }

 private String quote (String str)
 {
  return StringQuoter.quote (str);
 }

}
