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

import java.util.Date;
import java.util.Map;
import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.lum.lu.assembly.data.client.ModifiableData;
import org.kuali.student.lum.lu.assembly.data.client.PropertyEnum;

/**
 *
 * @author nwright
 */
public class OrchestrationObjectHelperWriter extends JavaClassWriter
{

 private DictionaryModel model;
 private String directory;
 public static final String ROOT_PACKAGE = "org.kuali.student.orchestration";
 private OrchestrationObject orchObj;
 private Map<String, OrchestrationObject> orchObjs;

 public OrchestrationObjectHelperWriter (DictionaryModel model,
                                         String directory,
                                         Map<String, OrchestrationObject> orchObjs,
                                         OrchestrationObject orchObj)
 {
  super (directory, orchObj.getOrchestrationPackagePath (), orchObj.
   getJavaClassHelperName ());
  this.model = model;
  this.directory = directory;
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
    OrchestrationObjectHelperWriter inlineWriter =
     new OrchestrationObjectHelperWriter (model,
                                          directory,
                                          orchObjs,
                                          field.getInlineObject ());
    inlineWriter.write ();
   }
  }

  indentPrintln ("public class " + orchObj.getJavaClassHelperName ());
  openBrace ();

  indentPrintln ("private static final long serialVersionUID = 1;");
  indentPrintln ("");

  indentPrintln ("public enum Properties implements PropertyEnum");
  imports.add (PropertyEnum.class.getName ());
  openBrace ();
  int i = 0;
  for (OrchestrationObjectField field : orchObj.getFields ())
  {
   i ++;
   String comma = ",";
   if (i == orchObj.getFields ().size ())
   {
    comma = ";";
   }
   String constant = new JavaEnumConstantCalculator (field.getName ()).calc ();
   indentPrintln (constant + " (\"" + field.getName () + "\")" + comma);
  }
  indentPrintln ("");
  indentPrintln ("private final String key;");
  indentPrintln ("");
  indentPrintln ("private Properties (final String key)");
  openBrace ();
  indentPrintln ("this.key = key;");
  closeBrace ();

  indentPrintln ("");
  indentPrintln ("@Override");
  indentPrintln ("public String getKey ()");
  openBrace ();
  indentPrintln ("return this.key;");
  closeBrace (); // getKey brace
  closeBrace (); // enum brace

  String modifiableOrData = calcModifiableOrData (orchObj);

  indentPrintln ("private " + modifiableOrData + " data;");
  indentPrintln ("");
  indentPrintln ("public " + orchObj.getJavaClassHelperName () + " (" +
   modifiableOrData + " data)");
  openBrace ();
  indentPrintln ("this.data = data;");
  closeBrace (); // constructor

  // add Data getData ()
  indentPrintln ("");
  indentPrintln ("public " + modifiableOrData + " getData ()");
  openBrace ();
  indentPrintln ("return data;");
  closeBrace (); // getter
  indentPrintln ("");

  for (OrchestrationObjectField field : orchObj.getFields ())
  {
   String setter = calcSetterMethodName (field.getName ());
   String getter = calcGetterMethodName (field.getName ());
   String fieldTypeToUse = calcFieldTypeToUse (field);
   String constant = new JavaEnumConstantCalculator (field.getName ()).calc ();

   indentPrintln ("");
   indentPrintln ("public void " + setter + " (" + fieldTypeToUse + " value)");
   openBrace ();
   String setterValue;
   switch (field.getFieldTypeCategory ())
   {
    case PRIMITIVE:
    case MAPPED_STRING:
    case DYNAMIC_ATTRIBUTE:
    case LIST:
     setterValue = "value";
     break;
    case COMPLEX:
    case COMPLEX_INLINE:
     setterValue = "value.getData ()";
     break;
    default:
     throw new DictionaryExecutionException ("unhandled type");
   }
   indentPrintln ("data.set (Properties." + constant + ".getKey (), " +
    setterValue + ");");
   closeBrace (); // setter
   indentPrintln ("");

   indentPrintln ("");
   indentPrintln ("public " + fieldTypeToUse + " " + getter + " ()");
   openBrace ();
   String getterValue = "data.get (Properties." + constant + ".getKey ())";
   switch (field.getFieldTypeCategory ())
   {
    case DYNAMIC_ATTRIBUTE:
    case PRIMITIVE:
    case MAPPED_STRING:
    case LIST:
     indentPrintln ("return (" + fieldTypeToUse + ") " + getterValue + ";");
     break;
    case COMPLEX:
     String castType = calcModifiableOrData (field.getType ());
     indentPrintln ("return new " + fieldTypeToUse + " ((" + castType + ") " +
      getterValue + ");");
     break;
    case COMPLEX_INLINE:
     castType = "Data";
     imports.add (Data.class.getName ());
     indentPrintln ("return new " + fieldTypeToUse + " ((" + castType + ") " +
      getterValue + ");");
     break;
    default:
     throw new DictionaryExecutionException ("unhandled type");
   }
   closeBrace (); // getter
   indentPrintln ("");
  }

  closeBrace (); // end class

  this.writeJavaClassAndImportsOutToFile ();
  this.getOut ().close ();
 }

 private String calcModifiableOrData (String orchObjName)
 {
  OrchestrationObject oo = orchObjs.get (orchObjName.toLowerCase ());
  if (oo == null)
  {
   throw new DictionaryExecutionException ("could not find orchestrration object " +
    orchObjName);
  }
  return calcModifiableOrData (oo);
 }

 private String calcModifiableOrData (OrchestrationObject orchObj)
 {
  if (orchObj.hasOwnCreateUpdate ())
  {
   imports.add (ModifiableData.class.getName ());
   return "ModifiableData";
  }
  imports.add (Data.class.getName ());
  return "Data";
 }

 public static String calcGetterMethodName (String name)
 {
  return "get" + name.substring (0, 1).toUpperCase () + name.substring (1);
 }

 public static String calcSetterMethodName (String name)
 {
  return "set" + name.substring (0, 1).toUpperCase () + name.substring (1);
 }

 private String calcFieldTypeToUse (OrchestrationObjectField field)
 {
  //XmlType xmlType = new ModelFinder (model).findXmlType (field.getType ());
  switch (field.getFieldTypeCategory ())
  {
   // TODO: translate map <String, String> to from Data
   case DYNAMIC_ATTRIBUTE:
   case LIST:
    imports.add (Data.class.getName ());
    return "Data";

   case PRIMITIVE:
    if (field.getType ().equalsIgnoreCase ("string"))
    {
     return "String";
    }

    if (field.getType ().equalsIgnoreCase ("date"))
    {
     // TODO: figure out what to use for Date
     imports.add (Date.class.getName ());
     return "Date";
    }

    if (field.getType ().equalsIgnoreCase ("dateTime"))
    {
     imports.add (Date.class.getName ());
     return "Date";
    }

    if (field.getType ().equalsIgnoreCase ("boolean"))
    {
     return "Boolean";
    }

    if (field.getType ().equalsIgnoreCase ("integer"))
    {
     return "Integer";
    }

    if (field.getType ().equalsIgnoreCase ("long"))
    {
     return "Long";
    }

    throw new DictionaryValidationException (
     "Unknown/handled field type " +
     field.getType () + " " + field.getName ());

   case MAPPED_STRING:
    return "String";

   case COMPLEX:
    OrchestrationObject fieldOO =
     orchObjs.get (field.getType ().toLowerCase ());
    imports.add (fieldOO.getFullyQualifiedJavaClassHelperName ());
    return fieldOO.getJavaClassHelperName ();

   case COMPLEX_INLINE:
    imports.add (field.getInlineObject ().getFullyQualifiedJavaClassHelperName ());
    return field.getInlineObject ().getJavaClassHelperName ();
  }
  throw new DictionaryValidationException ("Unknown/unhandled field type category" +
   field.getFieldTypeCategory () + " for field type " +
   field.getType () + " for field " + field.getName ());
 }


}
