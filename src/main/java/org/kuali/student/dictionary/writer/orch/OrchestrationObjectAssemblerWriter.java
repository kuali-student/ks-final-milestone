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
package org.kuali.student.dictionary.writer.orch;

import org.kuali.student.dictionary.model.OrchestrationObject;
import org.kuali.student.dictionary.model.OrchestrationObjectField;
import org.kuali.student.dictionary.model.validation.DictionaryValidationException;
import org.kuali.student.dictionary.model.DictionaryModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
//import org.kuali.student.core.assembly.Assembler;
//import org.kuali.student.core.assembly.data.AssemblyException;
//import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.dictionary.DictionaryExecutionException;
import org.kuali.student.dictionary.writer.JavaClassWriter;
import org.kuali.student.dictionary.writer.JavaEnumConstantCalculator;

/**
 *
 * @author nwright
 */
public class OrchestrationObjectAssemblerWriter extends JavaClassWriter
{

 private DictionaryModel model;
 private String directory;
 private OrchestrationObject orchObj;
 private Map<String, OrchestrationObject> orchObjs;

 public OrchestrationObjectAssemblerWriter (DictionaryModel model,
                                            String directory,
                                            Map<String, OrchestrationObject> orchObjs,
                                            OrchestrationObject orchObj)
 {
  super (directory, orchObj.getOrchestrationPackagePath () + ".assembler", orchObj.
   getJavaClassAssemblerName ());
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
    OrchestrationObjectAssemblerWriter inlineWriter =
     new OrchestrationObjectAssemblerWriter (model,
                                             directory,
                                             orchObjs,
                                             field.getInlineObject ());
    inlineWriter.write ();
   }
  }


  indentPrintln ("public class " + orchObj.getJavaClassAssemblerName ());
  incrementIndent ();
//  importsAdd (Assembler.class.getName ());
//  importsAdd (orchObj.getFullyQualifiedJavaClassInfoName ());
//  indentPrintln ("implements Assembler <Data, " +
//   orchObj.getJavaClassInfoName () + ">");
//  decrementIndent ();
//  openBrace ();
//
//
//  indentPrintln ("");
//  writeAssembleMethod ();
//  writeDisassembleMethod ();
//
//  closeBrace (); // end class
//
//  this.writeJavaClassAndImportsOutToFile ();
//  this.getOut ().close ();
// }
//
// private void writeAssembleMethod ()
// {
//
//  indentPrintln ("");
//  indentPrintln ("@Override");
//  importsAdd (Data.class.getName ());
//  indentPrintln ("public Data assemble (" + orchObj.getJavaClassInfoName () +
//   " input)");
//  incrementIndent ();
//  importsAdd (AssemblyException.class.getName ());
//  indentPrintln ("throws AssemblyException");
//  decrementIndent ();
//  openBrace ();
//
//  indentPrintln ("if (input == null)");
//  openBrace ();
//  indentPrintln ("return null;");
//  closeBrace ();
//  indentPrintln ("Data result = new Data ();");
//  indentPrintln ("Data data;");
//  for (OrchestrationObjectField field : orchObj.getFields ())
//  {
//   writeAssembleTransferDataForField (field);
//  }
//  indentPrintln ("return result;");
//  closeBrace (); // end assemble method
// }
//
// private void writeAssembleTransferDataForField (OrchestrationObjectField field)
// {
//  indentPrintln ("");
//  indentPrintln ("// loading data for " + field.getProperName ());
//  String constant = new JavaEnumConstantCalculator (field.getName ()).calc ();
//  importsAdd (orchObj.getFullyQualifiedJavaClassHelperName () + ".Properties");
//  String getter = "input.get" + field.getProperName () + " ()";
//  switch (field.getFieldTypeCategory ())
//  {
//   case PRIMITIVE:
//    indentPrintln ("result.set (Properties." + constant + ".getKey ()," + getter +
//     ");");
//    break;
//   case MAPPED_STRING:
//    indentPrintln ("result.set (Properties." + constant + ".getKey ()," + getter +
//     ");");
//    break;
//   case DYNAMIC_ATTRIBUTE:
//    importsAdd (Map.class.getName ());
//    importsAdd (Data.class.getName ());
//    indentPrintln ("data = new Data ();");
//    indentPrintln ("result.set (Properties." + constant + ".getKey (), data);");
//    indentPrintln ("Map <String, String> attributes = " + getter + ";");
//    indentPrintln ("for (String key : attributes.keySet ())");
//    openBrace ();
//    indentPrintln ("data.set (key, attributes.get (key));");
//    closeBrace ();
//    break;
//   case LIST_OF_PRIMITIVE:
//   case LIST_OF_MAPPED_STRING:
//   case LIST_OF_COMPLEX:
//   case LIST_OF_COMPLEX_INLINE:
//    importsAdd (List.class.getName ());
//    indentPrintln ("data = new Data ();");
//    indentPrintln ("result.set (Properties." + constant + ".getKey (), data);");
//    switch (field.getFieldTypeCategory ())
//    {
//     case LIST_OF_PRIMITIVE:
//      indentPrintln ("for (" +
//       DictionaryTypeToJavaTypeConverter.convert (field, this) + " value : " +
//       getter + ")");
//      openBrace ();
//      indentPrintln ("data.add (value);");
//      closeBrace ();
//      break;
//     case LIST_OF_MAPPED_STRING:
//      indentPrintln ("for (String value : " + getter + ")");
//      openBrace ();
//      indentPrintln ("data.add (value);");
//      closeBrace ();
//      break;
//     case LIST_OF_COMPLEX:
//      OrchestrationObject fieldOO =
//       orchObjs.get (field.getType ().toLowerCase ());
//      if (fieldOO == null)
//      {
//       throw new DictionaryValidationException ("Could not find orchestration object for field " +
//        field.getName () + " type " + field.getType ());
//      }
//      importsAdd (fieldOO.getFullyQualifiedJavaClassAssemblerName ());
//      importsAdd (fieldOO.getFullyQualifiedJavaClassInfoName ());
//      indentPrintln ("for (" + fieldOO.getJavaClassInfoName () + " value : " +
//       getter + ")");
//      openBrace ();
//      indentPrintln ("Data dataValue = new " +
//       fieldOO.getJavaClassAssemblerName () +
//       " ().assemble (value);");
//      indentPrintln ("data.add (dataValue);");
//      closeBrace ();
//      break;
//     case LIST_OF_COMPLEX_INLINE:
//      importsAdd (field.getInlineObject ().
//       getFullyQualifiedJavaClassAssemblerName ());
//      // TODO: figure out what the the JavaClassInfoName is of an Orchestration Objecxt?
//      indentPrintln ("for (" + field.getInlineObject ().getJavaClassInfoName () +
//       " value : " +
//       getter + ")");
//      openBrace ();
//      indentPrintln ("Data dataValue = new " +
//       field.getInlineObject ().getJavaClassAssemblerName () +
//       " ().assemble (value);");
//      indentPrintln ("data.add (dataValue);");
//      closeBrace ();
//
//      break;
//     default:
//      throw new DictionaryExecutionException ("unhandled type");
//    }
//    break;
//   case COMPLEX:
//    OrchestrationObject fieldOO = orchObjs.get (field.getType ().toLowerCase ());
//    if (fieldOO == null)
//    {
//     throw new DictionaryValidationException ("Could not find orchestration object for field " +
//      field.getName () + " type " + field.getType ());
//    }
//    importsAdd (fieldOO.getFullyQualifiedJavaClassAssemblerName ());
//    indentPrintln ("data = new " + fieldOO.getJavaClassAssemblerName () +
//     " ().assemble (" + getter + ");");
//    indentPrintln ("result.set (Properties." + constant + ".getKey (), data);");
//    break;
//   case COMPLEX_INLINE:
//    importsAdd (field.getInlineObject ().
//     getFullyQualifiedJavaClassAssemblerName ());
//    indentPrintln ("data = new " + field.getInlineObject ().
//     getJavaClassAssemblerName () +
//     " ().assemble (" + getter + ");");
//    indentPrintln ("result.set (Properties." + constant + ".getKey (), data);");
//    break;
//   default:
//    throw new DictionaryExecutionException ("unhandled type");
//  }
// }
//
// private void writeDisassembleMethod ()
// {
//  indentPrintln ("");
//  indentPrintln ("@Override");
//  indentPrintln ("public " + orchObj.getJavaClassInfoName () +
//   " disassemble (Data input)");
//  incrementIndent ();
//  importsAdd (AssemblyException.class.getName ());
//  indentPrintln ("throws AssemblyException");
//  decrementIndent ();
//  openBrace ();
//
//  indentPrintln ("if (input == null)");
//  openBrace ();
//  indentPrintln ("return null;");
//  closeBrace ();
//  indentPrintln (orchObj.getJavaClassInfoName () + " result = new " + orchObj.
//   getJavaClassInfoName () + "();");
//  indentPrintln ("Data data;");
//  for (OrchestrationObjectField field : orchObj.getFields ())
//  {
//   writeDisassembleTransferDataForField (field);
//  }
//  indentPrintln ("return result;");
//  closeBrace (); // end assemble method
// }
//
// private void writeDisassembleTransferDataForField (
//  OrchestrationObjectField field)
// {
//  indentPrintln ("");
//  indentPrintln ("// loading info for " + field.getName ());
//  String constant = new JavaEnumConstantCalculator (field.getName ()).calc ();
//  importsAdd (orchObj.getFullyQualifiedJavaClassHelperName () + ".Properties");
//  String setter = "result.set" + field.getProperName ();
//  switch (field.getFieldTypeCategory ())
//  {
//   case PRIMITIVE:
//    String cast = "(" +
//     DictionaryTypeToJavaTypeConverter.convert (field, this) + ")";
//    indentPrintln (setter + "(" + cast + " input.get (Properties." + constant +
//     ".getKey ()));");
//    break;
//   case MAPPED_STRING:
//    indentPrintln (setter + "(input.get ((String) Properties." + constant +
//     ".getKey ()));");
//    break;
//   case DYNAMIC_ATTRIBUTE:
//    importsAdd (Map.class.getName ());
//    importsAdd (HashMap.class.getName ());
//    importsAdd (Data.class.getName ());
//    indentPrintln ("Map attributes = new HashMap ();");
//    indentPrintln (setter + "(attributes);");
//    indentPrintln ("data = input.get (Properties." + constant + ".getKey ());");
//      importsAdd (Iterator.class.getName ());
//    indentPrintln ("for (Iterator <Data.Property> it = data.iterator (); it.hasNext (); )");
//    openBrace ();
//    indentPrintln ("Data.Property prop = it.next ();");
//    indentPrintln ("attributes.set (prop.getKey (), (String) data.get (prop.getKey ()));");
//    closeBrace ();
//    break;
//   case LIST_OF_PRIMITIVE:
//   case LIST_OF_MAPPED_STRING:
//   case LIST_OF_COMPLEX:
//   case LIST_OF_COMPLEX_INLINE:
//    importsAdd (List.class.getName ());
//    importsAdd (ArrayList.class.getName ());
//    importsAdd (Data.class.getName ());
//    openBrace ();
//    switch (field.getFieldTypeCategory ())
//    {
//     case LIST_OF_PRIMITIVE:
//      cast = DictionaryTypeToJavaTypeConverter.convert (field, this);
//      indentPrintln ("List<" + cast + "> list = new ArrayList ();");
//      indentPrintln (setter + "(list);");
//      indentPrintln ("data = input.get (Properties." + constant + ".getKey ());");
//      importsAdd (Iterator.class.getName ());
//      indentPrintln ("for (Iterator <Data.Property> it = data.iterator (); it.hasNext (); )");
//      openBrace ();
//      indentPrintln ("Data.Property prop = it.next ();");
//      indentPrintln ("list.add ((" + cast + ") data.get (prop.getKey ());");
//      closeBrace ();
//      break;
//     case LIST_OF_MAPPED_STRING:
//      cast = "String";
//      indentPrintln ("List<" + cast + "> list = new ArrayList ();");
//      indentPrintln (setter + "(list);");
//      indentPrintln ("data = input.get (Properties." + constant + ".getKey ());");
//      importsAdd (Iterator.class.getName ());
//      indentPrintln ("for (Iterator <Data.Property> it = data.iterator (); it.hasNext (); )");
//      openBrace ();
//      indentPrintln ("Data.Property prop = it.next ();");
//      indentPrintln ("list.add ((" + cast + ") data.get (prop.getKey ());");
//      closeBrace ();
//      break;
//     case LIST_OF_COMPLEX:
//      OrchestrationObject fieldOO =
//       orchObjs.get (field.getType ().toLowerCase ());
//      if (fieldOO == null)
//      {
//       throw new DictionaryValidationException ("Could not find orchestration object for field " +
//        field.getName () + " type " + field.getType ());
//      }
//      importsAdd (fieldOO.getFullyQualifiedJavaClassAssemblerName ());
//      importsAdd (fieldOO.getFullyQualifiedJavaClassInfoName ());
//      cast = fieldOO.getJavaClassInfoName ();
//      indentPrintln ("List<" + cast + "> list = new ArrayList ();");
//      indentPrintln (setter + "(list);");
//      indentPrintln ("data = input.get (Properties." + constant + ".getKey ());");
//      importsAdd (Iterator.class.getName ());
//      indentPrintln ("for (Iterator <Data.Property> it = data.iterator (); it.hasNext (); )");
//      openBrace ();
//      indentPrintln ("Data.Property prop = it.next ();");
//      indentPrintln ("list.add (" + " new " +
//       fieldOO.getJavaClassAssemblerName () +
//       " ().disassemble (data));");
//      closeBrace ();
//      break;
//     case LIST_OF_COMPLEX_INLINE:
//      importsAdd (field.getInlineObject ().
//       getFullyQualifiedJavaClassAssemblerName ());
//      cast = field.getInlineObject ().getJavaClassInfoName ();
//      indentPrintln ("List<" + cast + "> list = new ArrayList ();");
//      indentPrintln (setter + "(list);");
//      indentPrintln ("data = input.get (Properties." + constant + ".getKey ());");
//      importsAdd (Iterator.class.getName ());
//      indentPrintln ("for (Iterator <Data.Property> it = data.iterator (); it.hasNext (); )");
//      openBrace ();
//      indentPrintln ("Data.Property prop = it.next ();");
//      indentPrintln ("list.add (" + " new " +
//       field.getInlineObject ().getJavaClassAssemblerName () +
//       " ().disassemble (data));");
//      closeBrace ();
//      break;
//     default:
//      throw new DictionaryExecutionException ("unhandled type");
//    }
//    closeBrace ();
//    break;
//   case COMPLEX:
//    OrchestrationObject fieldOO = orchObjs.get (field.getType ().toLowerCase ());
//    if (fieldOO == null)
//    {
//     throw new DictionaryValidationException ("Could not find orchestration object for field " +
//      field.getName () + " type " + field.getType ());
//    }
//    importsAdd (fieldOO.getFullyQualifiedJavaClassAssemblerName ());
//    indentPrintln ("data = input.get (Properties." + constant + ".getKey ());");
//    indentPrintln (setter + "(" + " new " +
//     fieldOO.getJavaClassAssemblerName () +
//     " ().disassemble (data));");
//    break;
//   case COMPLEX_INLINE:
//    importsAdd (field.getInlineObject ().
//     getFullyQualifiedJavaClassAssemblerName ());
//    indentPrintln ("data = input.get (Properties." + constant + ".getKey ());");
//    indentPrintln (setter + "(" + " new " +
//     field.getInlineObject ().getJavaClassAssemblerName () +
//     " ().disassemble (data));");
//    break;
//   default:
//    throw new DictionaryExecutionException ("unhandled type");
//  }
// }
 }
}
