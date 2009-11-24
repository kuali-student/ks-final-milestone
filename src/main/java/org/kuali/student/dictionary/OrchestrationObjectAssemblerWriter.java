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
import java.util.List;
import java.util.Map;
import org.kuali.student.common.assembly.Assembler;
import org.kuali.student.common.assembly.client.AssemblyException;
import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.lum.lu.assembly.data.client.ModifiableData;

/**
 *
 * @author nwright
 */
public class OrchestrationObjectAssemblerWriter extends JavaClassWriter
{

 private DictionaryModel model;
 private String directory;
 public static final String ROOT_PACKAGE = "org.kuali.student.orchestration";
 private OrchestrationObject orchObj;
 private Map<String, OrchestrationObject> orchObjs;

 public OrchestrationObjectAssemblerWriter (DictionaryModel model,
                                            String directory,
                                            Map<String, OrchestrationObject> orchObjs,
                                            OrchestrationObject orchObj)
 {
  super (directory, orchObj.getOrchestrationPackagePath (), orchObj.
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
  imports.add (Assembler.class.getName ());
  imports.add (orchObj.getFullyQualifiedJavaClassInfoName ());
  String modifiableData = calcModifiableOrData (orchObj);
  indentPrintln ("implements Assembler <" + modifiableData + ", " + orchObj.
   getJavaClassInfoName () + ">");
  decrementIndent ();
  openBrace ();

  indentPrintln ("");
  indentPrintln ("@Override");
  indentPrintln ("public " + modifiableData + " assemble (" + orchObj.
   getJavaClassInfoName () + " input)");
  incrementIndent ();
  imports.add (AssemblyException.class.getName ());
  indentPrintln ("throws AssemblyException");
  decrementIndent ();
  openBrace ();

  indentPrintln ("if (input == null)");
  openBrace ();
  indentPrintln ("return null;");
  closeBrace ();
  indentPrintln (modifiableData + " result = new " + modifiableData + "();");
   indentPrintln ("Data data;");
  for (OrchestrationObjectField field : orchObj.getFields ())
  {
   writeLoadDataForField (field);
  }
  indentPrintln ("return result;");
  closeBrace (); // end assemble method
  indentPrintln ("");

  closeBrace (); // end class

  this.writeJavaClassAndImportsOutToFile ();
  this.getOut ().close ();
 }

 private void writeLoadDataForField (OrchestrationObjectField field)
 {
  indentPrintln ("");
  indentPrintln ("// loading data for " + field.getName ());
  String constant = new JavaEnumConstantCalculator (field.getName ()).calc ();
  imports.add (orchObj.getFullyQualifiedJavaClassHelperName () + ".Properties");
  String getter = "input." + calcGetterMethodName (field.getName ()) + " ()";
  switch (field.getFieldTypeCategory ())
  {
   case PRIMITIVE:
    indentPrintln ("result.set (Properties." + constant + ".getKey ()," + getter + ");");
    break;
   case MAPPED_STRING:
    indentPrintln ("result.set (Properties." + constant + ".getKey ()," + getter + ");");
    break;
   case DYNAMIC_ATTRIBUTE:
    imports.add (Map.class.getName ());
    // TODO: worry about having more than one dynamic attribute and getting a name clash
    imports.add (Data.class.getName ());
    indentPrintln ("data = new Data ();");
    indentPrintln ("result.set (Properties." + constant + ".getKey (), data);");
    indentPrintln ("Map <String, String> attributes = " + getter + ";");
    indentPrintln ("for (String key : attributes.keySet ())");
    openBrace ();
    indentPrintln ("data.set (key, attributes.get (key));");
    closeBrace ();
    break;
   case LIST:
    imports.add (List.class.getName ());
    indentPrintln ("data = new Data ();");
    indentPrintln ("result.set (Properties." + constant + ".getKey (), data);");
    // TODO: handle lists of non-strings including lists of complex objects
 
    indentPrintln ("for (String value : " + getter + ")");
    openBrace ();
    indentPrintln ("data.add (value);");
    closeBrace ();
    break;
   case COMPLEX:
    OrchestrationObject fieldOO = orchObjs.get (field.getType ().toLowerCase ());
    if (fieldOO == null)
    {
     throw new DictionaryValidationException ("Could not find orchestration object for field " +
      field.getName () + " type " + field.getType ());
    }
    imports.add (fieldOO.getFullyQualifiedJavaClassAssemblerName ());
    indentPrintln ("data = new " + fieldOO.getJavaClassAssemblerName () +
     " ().assemble (" + getter + ");");
    indentPrintln ("result.set (Properties." + constant + ".getKey (), data);");
    break;
   case COMPLEX_INLINE:
    imports.add (field.getInlineObject ().getFullyQualifiedJavaClassAssemblerName ());
    indentPrintln ("data = new " + field.getInlineObject ().getJavaClassAssemblerName () +
     " ().assemble (" + getter + ");");
    indentPrintln ("result.set (Properties." + constant + ".getKey (), data);");
    break;
   default:
    throw new DictionaryExecutionException ("unhandled type");
  }



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
}
