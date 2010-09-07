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
import java.util.Map;
//import org.kuali.student.core.assembly.data.Data;
//import org.kuali.student.core.assembly.helper.PropertyEnum;
import org.kuali.student.dictionary.DictionaryExecutionException;
import org.kuali.student.dictionary.writer.JavaClassWriter;
import org.kuali.student.dictionary.writer.JavaEnumConstantCalculator;

/**
 *
 * @author nwright
 */
public class OrchestrationObjectHelperWriter extends JavaClassWriter
{

 public OrchestrationObjectHelperWriter (String rootDirectory,
                                         String packageName, String className)
 {
  super (rootDirectory, packageName, className);
 }
 
//
// private DictionaryModel model;
// private String directory;
// public static final String ROOT_PACKAGE = "org.kuali.student.orchestration";
// private OrchestrationObject orchObj;
// private Map<String, OrchestrationObject> orchObjs;
//
// public OrchestrationObjectHelperWriter (DictionaryModel model,
//                                         String directory,
//                                         Map<String, OrchestrationObject> orchObjs,
//                                         OrchestrationObject orchObj)
// {
//  super (directory, orchObj.getOrchestrationPackagePath (), orchObj.
//   getJavaClassHelperName ());
//  this.model = model;
//  this.directory = directory;
//  this.orchObjs = orchObjs;
//  this.orchObj = orchObj;
// }
//
// /**
//  * Write out the entire file
//  * @param out
//  */
// public void write ()
// {
//  // recursively call any in-line orchestration objects
//  for (OrchestrationObjectField field : orchObj.getFields ())
//  {
//   if (field.getInlineObject () != null)
//   {
//    OrchestrationObjectHelperWriter inlineWriter =
//     new OrchestrationObjectHelperWriter (model,
//                                          directory,
//                                          orchObjs,
//                                          field.getInlineObject ());
//    inlineWriter.write ();
//   }
//  }
//
//  indentPrintln ("public class " + orchObj.getJavaClassHelperName ());
//  openBrace ();
//
//  indentPrintln ("private static final long serialVersionUID = 1;");
//  indentPrintln ("");
//
//  indentPrintln ("public enum Properties implements PropertyEnum");
//  importsAdd (PropertyEnum.class.getName ());
//  openBrace ();
//  int i = 0;
//  for (OrchestrationObjectField field : orchObj.getFields ())
//  {
//   i ++;
//   String comma = ",";
//   if (i == orchObj.getFields ().size ())
//   {
//    comma = ";";
//   }
//   String constant = new JavaEnumConstantCalculator (field.getName ()).calc ();
//   indentPrintln (constant + " (\"" + field.getPropertyName () + "\")" + comma);
//  }
//  indentPrintln ("");
//  indentPrintln ("private final String key;");
//  indentPrintln ("");
//  indentPrintln ("private Properties (final String key)");
//  openBrace ();
//  indentPrintln ("this.key = key;");
//  closeBrace ();
//
//  indentPrintln ("");
//  indentPrintln ("@Override");
//  indentPrintln ("public String getKey ()");
//  openBrace ();
//  indentPrintln ("return this.key;");
//  closeBrace (); // getKey brace
//  closeBrace (); // enum brace
//
//  importsAdd (Data.class.getName ());
//  indentPrintln ("private Data data;");
//  indentPrintln ("");
//  indentPrintln ("private " + orchObj.getJavaClassHelperName () + " (Data data)");
//  openBrace ();
//  indentPrintln ("this.data = data;");
//  closeBrace (); // constructor
//
//  indentPrintln ("");
//  indentPrintln ("public static " + orchObj.getJavaClassHelperName () +
//   " wrap (Data data)");
//  openBrace ();
//  indentPrintln ("if (data == null)");
//  openBrace ();
//  indentPrintln (" return null;");
//  closeBrace ();
//
//  indentPrintln ("return new " + orchObj.getJavaClassHelperName () + " (data);");
//  closeBrace (); // constructor
//
//  // add Data getData ()
//  indentPrintln ("");
//  indentPrintln ("public Data getData ()");
//  openBrace ();
//  indentPrintln ("return data;");
//  closeBrace ();
//  indentPrintln ("");
//
//  for (OrchestrationObjectField field : orchObj.getFields ())
//  {
//   String setter = calcSetter (field);
//   String getter = calcGetter (field);
//   String fieldTypeToUse = calcFieldTypeToUse (field);
//   String constant = new JavaEnumConstantCalculator (field.getName ()).calc ();
//
//   indentPrintln ("");
//   indentPrintln ("public void " + setter + " (" + fieldTypeToUse + " value)");
//   openBrace ();
//   String setterValue;
//   switch (field.getFieldTypeCategory ())
//   {
//    case PRIMITIVE:
//    case MAPPED_STRING:
//    case DYNAMIC_ATTRIBUTE:
//    case LIST_OF_PRIMITIVE:
//    case LIST_OF_MAPPED_STRING:
//    case LIST_OF_COMPLEX:
//    case LIST_OF_COMPLEX_INLINE:
//     setterValue = "value";
//     break;
//    case COMPLEX:
//    case COMPLEX_INLINE:
//     setterValue = "(value == null) ? null : value.getData ()";
//     break;
//    default:
//     throw new DictionaryExecutionException ("unhandled type");
//   }
//   indentPrintln ("data.set (Properties." + constant + ".getKey (), " +
//    setterValue + ");");
//   closeBrace (); // setter
//   indentPrintln ("");
//
//   indentPrintln ("");
//   indentPrintln ("public " + fieldTypeToUse + " " + getter + " ()");
//   openBrace ();
//   String getterValue = "data.get (Properties." + constant + ".getKey ())";
//   switch (field.getFieldTypeCategory ())
//   {
//    case DYNAMIC_ATTRIBUTE:
//    case PRIMITIVE:
//    case MAPPED_STRING:
//    case LIST_OF_PRIMITIVE:
//    case LIST_OF_MAPPED_STRING:
//    case LIST_OF_COMPLEX:
//    case LIST_OF_COMPLEX_INLINE:
//     indentPrintln ("return (" + fieldTypeToUse + ") " + getterValue + ";");
//     break;
//    case COMPLEX:
//     importsAdd (Data.class.getName ());
//     indentPrintln ("return " + fieldTypeToUse + ".wrap ((Data) " +
//      getterValue + ");");
//     break;
//    case COMPLEX_INLINE:
//     importsAdd (Data.class.getName ());
//     indentPrintln ("return " + fieldTypeToUse + ".wrap ((Data) " +
//      getterValue + ");");
//     break;
//    default:
//     throw new DictionaryExecutionException ("unhandled type");
//   }
//   closeBrace (); // getter
//   indentPrintln ("");
//  }
//
//  closeBrace (); // end class
//
//  this.writeJavaClassAndImportsOutToFile ();
//  this.getOut ().close ();
// }
//
// private String calcGetter (OrchestrationObjectField field)
// {
//  if (calcFieldTypeToUse (field).equals ("Boolean"))
//  {
//   return "is" + field.getProperName ();
//  }
//  return "get" + field.getProperName ();
// }
//
// private String calcSetter (OrchestrationObjectField field)
// {
//  return "set" + field.getProperName ();
// }
//
// private String calcFieldTypeToUse (OrchestrationObjectField field)
// {
//  //XmlType xmlType = new ModelFinder (model).findXmlType (field.getType ());
//  switch (field.getFieldTypeCategory ())
//  {
//   // TODO: translate map <String, String> to from Data
//   case DYNAMIC_ATTRIBUTE:
//    importsAdd (Data.class.getName ());
//    return "Data";
//
//   case LIST_OF_PRIMITIVE:
//   case LIST_OF_MAPPED_STRING:
//   case LIST_OF_COMPLEX:
//   case LIST_OF_COMPLEX_INLINE:
//    importsAdd (Data.class.getName ());
//    return "Data";
//
//
//   case PRIMITIVE:
//    return DictionaryTypeToJavaTypeConverter.convert (field, this);
//
//
//   case MAPPED_STRING:
//    return "String";
//
//   case COMPLEX:
//    OrchestrationObject fieldOO =
//     orchObjs.get (field.getType ().toLowerCase ());
//    if (fieldOO == null)
//    {
//     throw new DictionaryValidationException ("Field [" + field.getName () +
//      "] has a complex type of, [" + field.getType () +
//      ", that does not exist later in the list of orchestration objects. ");
//    }
//    importsAdd (fieldOO.getFullyQualifiedJavaClassHelperName ());
//    return fieldOO.getJavaClassHelperName ();
//
//   case COMPLEX_INLINE:
//    importsAdd (field.getInlineObject ().getFullyQualifiedJavaClassHelperName ());
//    return field.getInlineObject ().getJavaClassHelperName ();
//  }
//  throw new DictionaryValidationException ("Unknown/unhandled field type category" +
//   field.getFieldTypeCategory () + " for field type " +
//   field.getType () + " for field " + field.getName ());
// }

}
