/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
public class OrchestrationObjectDataHelperWriter extends JavaClassWriter
{

 private DictionaryModel model;
 private String directory;
 public static final String ROOT_PACKAGE = "org.kuali.student.orchestration";
 private OrchestrationObject orchObj;
 private Map<String, OrchestrationObject> orchObjs;

 public OrchestrationObjectDataHelperWriter (DictionaryModel model,
                                             String directory,
                                             Map<String, OrchestrationObject> orchObjs,
                                             OrchestrationObject orchObj)
 {
  super (directory, orchObj.getDataPackagePath (), orchObj.
   getJavaClassDataHelperName ());
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

  indentPrintln ("public class " + orchObj.getJavaClassDataHelperName ());
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

  String modifiableOrData = null;
  if (orchObj.hasOwnCreateUpdate ())
  {
   modifiableOrData = "ModifiableData";

   imports.add (ModifiableData.class.getName ());
  }
  else
  {
   modifiableOrData = "Data";
   imports.add (Data.class.getName ());
  }

  indentPrintln ("private " + modifiableOrData + " data;");
  indentPrintln ("");
  indentPrintln ("public " + orchObj.getJavaClassDataHelperName () + " (" +
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
   String type = calcFieldTypeToUse (field);
   FieldTypeCategory fieldTypeCategory = calcFieldTypeCategory (field);
   String constant = new JavaEnumConstantCalculator (field.getName ()).calc ();

   indentPrintln ("");
   indentPrintln ("public void " + setter + " (" + type + " value)");
   openBrace ();
   String setterValue;
   switch (fieldTypeCategory)
   {
    case PRIMITIVE:
    case MAPPED_STRING:
    case DYNAMIC_ATTRIBUTE:
    case LIST:
     setterValue = "value";
     break;
    case COMPLEX:
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
   indentPrintln ("public " + type + " " + getter + " ()");
   openBrace ();
   String getterValue = "data.get (Properties." + constant + ".getKey ())";
   switch (fieldTypeCategory)
   {
    case DYNAMIC_ATTRIBUTE:
    case PRIMITIVE:
    case MAPPED_STRING:
    case LIST:
     indentPrintln ("return (" + type + ") " + getterValue + ";");
     break;
    case COMPLEX:
     imports.add (Data.class.getName ());
     indentPrintln ("return new " + type + " ((Data) " + getterValue + ");");
     break;
    default:
     throw new DictionaryExecutionException ("unhandled type");
   }
   closeBrace (); // getter
   indentPrintln ("");
  }

  // final brace
  closeBrace ();

  this.writeJavaClassAndImportsOutToFile ();
  this.getOut ().close ();
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
  XmlType xmlType = new ModelFinder (model).findXmlType (field.getType ());
  switch (calcFieldTypeCategory (field))
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

    throw new DictionaryValidationException ("Unknown/handled field type " +
     field.getType () + " " + field.getName ());
   case MAPPED_STRING:
    return "String";
   case COMPLEX:
    OrchestrationObject fieldOO =
     orchObjs.get (xmlType.getName ().toLowerCase ());
    imports.add (fieldOO.getFullyQualifiedJavaClassDataName ());
    return fieldOO.getJavaClassDataHelperName ();
  }
  throw new DictionaryValidationException ("Unknown/unhandled primitive " +
   xmlType.getPrimitive () + " for field type " +
   field.getType () + " for field " + field.getName ());
 }

 public enum FieldTypeCategory
 {

  LIST, PRIMITIVE, MAPPED_STRING, DYNAMIC_ATTRIBUTE, COMPLEX;
 }

 private FieldTypeCategory calcFieldTypeCategory (OrchestrationObjectField field)
 {
  if (field.getType ().equalsIgnoreCase ("attributeInfo"))
  {
   return FieldTypeCategory.DYNAMIC_ATTRIBUTE;
  }
  if (field.isIsList ())
  {
   return FieldTypeCategory.LIST;
  }

  XmlType xmlType = new ModelFinder (model).findXmlType (field.getType ());
  if (xmlType == null)
  {
   throw new DictionaryValidationException ("No XmlType found for field type " +
    field.getType () + " " + field.getName ());
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

}
