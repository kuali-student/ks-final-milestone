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
public class OrchestrationObjectDataWriter extends JavaClassWriter
{

 private DictionaryModel model;
 private String directory;
 public static final String ROOT_PACKAGE = "org.kuali.student.orchestration";
 private OrchestrationObject orchObj;
 private Map<String, OrchestrationObject> orchObjs;

 public OrchestrationObjectDataWriter (DictionaryModel model,
                                       String directory,
                                       Map<String, OrchestrationObject> orchObjs,
                                       OrchestrationObject orchObj)
 {
  super (directory, orchObj.getDataPackagePath (), orchObj.getJavaClassDataName ());
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

  indentPrintln ("public class " + orchObj.getJavaClassDataName ());
  incrementIndent ();
  if (orchObj.hasOwnCreateUpdate ())
  {
   indentPrintln ("extends ModifiableData");
   imports.add (ModifiableData.class.getName ());
  }
  else
  {
   indentPrintln ("extends Data");
   imports.add (Data.class.getName ());
  }
  decrementIndent ();
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

  indentPrintln ("");
  indentPrintln ("public " + orchObj.getJavaClassDataName () + " ()");
  openBrace ();
  indentPrintln ("// TODO: ask Wil if we want to really use the class name as the key");

  imports.add (orchObj.getFullyQualifiedInfoJavaClassName ());
  indentPrintln ("super (" + orchObj.getInfoJavaClassName () +
   ".class.getName ());");
  closeBrace (); // constructor

  for (OrchestrationObjectField field : orchObj.getFields ())
  {
   String setter = calcSetterMethodName (field.getName ());
   String getter = calcGetterMethodName (field.getName ());
   String type = calcFieldTypeToUse (field);
   String constant = new JavaEnumConstantCalculator (field.getName ()).calc ();

   indentPrintln ("");
   indentPrintln ("public void " + setter + " (" + type + " value)");
   openBrace ();
   indentPrintln ("super.set (Properties." + constant + ".getKey (), value);");
   closeBrace (); // setter
   indentPrintln ("");

   indentPrintln ("");
   indentPrintln ("public " + type + " " + getter + " ()");
   openBrace ();
   indentPrintln ("return super.get (Properties." + constant + ".getKey ());");
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
  if (field.isIsList ())
  {
   imports.add (Data.class.getName ());
   return "Data";
  }

  XmlType xmlType = new ModelFinder (model).findXmlType (field.getType ());
  if (xmlType == null)
  {
   throw new DictionaryValidationException ("No XmlType found for field type " +
    field.getType () + " " + field.getName ());
  }

  if (xmlType.getPrimitive ().equalsIgnoreCase ("Primitive"))
  {
   if (field.getType ().equalsIgnoreCase ("string"))
   {
    return "String";
   }
   if (field.getType ().equalsIgnoreCase ("date"))
   {
    imports.add (Date.class.getName ());
    return "Date";
   }
   if (field.getType ().equalsIgnoreCase ("dateTime"))
   {
    // TODO: figure out what the right class is for this
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
  }

  if (xmlType.getPrimitive ().equalsIgnoreCase ("Mapped String"))
  {
   return "String";
  }
  if (xmlType.getPrimitive ().equalsIgnoreCase ("Map <String, String>"))
  {
   return "Map <String, String>";
  }
  if (xmlType.getPrimitive ().equalsIgnoreCase ("Complex"))
  {
   OrchestrationObject fieldOO =
    orchObjs.get (xmlType.getName ().toLowerCase ());
   imports.add (fieldOO.getFullyQualifiedJavaClassDataName ());
   return fieldOO.getJavaClassDataName ();
  }
  throw new DictionaryValidationException ("Unknown/unhandled primitive " +
   xmlType.getPrimitive () + " for field type " +
   field.getType () + " for field " + field.getName ());
 }

}
