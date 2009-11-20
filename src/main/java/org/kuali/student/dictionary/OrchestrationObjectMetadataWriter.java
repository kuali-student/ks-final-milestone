/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.dictionary;

import java.util.Date;
import java.util.Map;
import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.common.assembly.client.Metadata;
import org.kuali.student.lum.lu.assembly.data.client.ModifiableData;
import org.kuali.student.lum.lu.assembly.data.client.PropertyEnum;

/**
 *
 * @author nwright
 */
public class OrchestrationObjectMetadataWriter extends JavaClassWriter
{

 private DictionaryModel model;
 private String directory;
 public static final String ROOT_PACKAGE = "org.kuali.student.orchestration";
 private OrchestrationObject orchObj;
 private Map<String, OrchestrationObject> orchObjs;

 public OrchestrationObjectMetadataWriter (DictionaryModel model,
                                         String directory,
                                         Map<String, OrchestrationObject> orchObjs,
                                         OrchestrationObject orchObj)
 {
  super (directory, orchObj.getOrchestrationPackagePath (), orchObj.
   getJavaClassMetadataName ());
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
    OrchestrationObjectMetadataWriter inlineWriter =
     new OrchestrationObjectMetadataWriter (model,
                                          directory,
                                          orchObjs,
                                          field.getInlineObject ());
    inlineWriter.write ();
   }
  }


  indentPrintln ("public class " + orchObj.getJavaClassMetadataName ());
  openBrace ();

  indentPrintln ("// version 2");
  
  // get metadata
  imports.add (Metadata.class.getName ());
  indentPrintln ("public Metadata getMetadata (String type, String state)");
  openBrace ();
  indentPrintln ("Metadata mainMeta = new Metadata ();");
  indentPrintln ("Metadata childMeta;");
  for (OrchestrationObjectField field : orchObj.getFields ())
  {
   indentPrintln ("");
   indentPrintln ("// metadata for " + field.getName ());
   indentPrintln ("childMeta = new Metadata ();");
   String constant = new JavaEnumConstantCalculator (field.getName ()).calc ();
   imports.add (orchObj.getFullyQualifiedJavaClassHelperName () + ".Properties");
   indentPrintln ("mainMeta.getProperties ().put (Properties."
    + constant + ".getKey (), childMeta);");
   if (field.getInlineObject () != null)
   {
    // TODO: something
   }
   imports.add (Data.class.getName ());
   indentPrintln ("childMeta.setDataType (Data.DataType." + calcDataTypeToUse (field) + ");");
   indentPrintln ("childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);");
   }
   indentPrintln ("");
   indentPrintln ("mainMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);");
   indentPrintln ("return mainMeta;");
   closeBrace (); // end getMetadata method

   closeBrace (); // end class

   this.writeJavaClassAndImportsOutToFile ();
   this.getOut ().close ();
  }

 private Data.DataType calcDataTypeToUse (OrchestrationObjectField field)
 {
  //XmlType xmlType = new ModelFinder (model).findXmlType (field.getType ());
  switch (field.getFieldTypeCategory ())
  {
   case DYNAMIC_ATTRIBUTE:
   case LIST:
    imports.add (Data.class.getName ());
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
    imports.add (Data.class.getName ());
    return Data.DataType.DATA;

   case COMPLEX_INLINE:
    imports.add (Data.class.getName ());
    return Data.DataType.DATA;
  }
  throw new DictionaryValidationException ("Unknown/unhandled field type category" +
   field.getFieldTypeCategory () + " for field type " +
   field.getType () + " for field " + field.getName ());
 }

}
