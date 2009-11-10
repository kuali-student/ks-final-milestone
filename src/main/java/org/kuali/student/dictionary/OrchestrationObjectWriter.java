/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.dictionary;


import com.sun.codemodel.ClassType;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JConditional;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JEnumConstant;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JVar;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.kuali.student.common.assembly.Assembler;
import org.kuali.student.common.assembly.client.AssemblyException;
import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.lum.lu.assembly.data.client.ModifiableData;
import org.kuali.student.lum.lu.assembly.data.client.PropertyEnum;


/**
 *
 * @author nwright
 */
public class OrchestrationObjectWriter
{

 private DictionaryModel model;
 private String directory;
 public static final String ROOT_PACKAGE = "org.kuali.student.orchestration";

 public OrchestrationObjectWriter (DictionaryModel model, String directory)
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

  JCodeModel jcm = new JCodeModel ();
  Map<String, JDefinedClass> map = new HashMap ();
  // first do from message structures
  List<OrchestrationObject> orchObjs =
   getOrchestrationObjectsFromMessageStructures ();
  addClassesToModel (jcm, map, orchObjs);
  addBeanMethodsToModelForFields (jcm, map, orchObjs);
  addBaseAssemblersToModel (jcm, map, orchObjs);

  // now do from orchObjs
  orchObjs =
   getOrchestrationObjectsFromOrchObjs ();
  addClassesToModel (jcm, map, orchObjs);
  addBeanMethodsToModelForFields (jcm, map, orchObjs);
  try
  {
   jcm.build (new File (directory));
  }
  catch (IOException ex)
  {
   throw new DictionaryValidationException (ex);
  }
 }

 private void addClassesToModel (JCodeModel jcm, Map<String, JDefinedClass> map,
                                 List<OrchestrationObject> orchObjs)
 {
  for (OrchestrationObject orchObj : orchObjs)
  {

   try
   {
    addClassToModel (jcm, map, orchObj);
   }
   catch (JClassAlreadyExistsException ex)
   {
    throw new DictionaryValidationException (ex);
   }
  }
 }

 private void addClassToModel (JCodeModel jcm, Map<String, JDefinedClass> map,
                               OrchestrationObject orchObj)
  throws JClassAlreadyExistsException
 {
  String className = orchObj.getFullyQualifiedName () + "Data";
  System.out.println ("Creating " + className);
  JDefinedClass mainClass = jcm._class (className, ClassType.CLASS);
  map.put (orchObj.getName ().toLowerCase (), mainClass);
  if (orchObj.hasOwnCreateUpdate ())
  {
   mainClass._extends (ModifiableData.class);
  }
  else
  {
   mainClass._extends (Data.class);
  }
  mainClass.field (JMod.PRIVATE + JMod.STATIC + JMod.FINAL, long.class, "serialVersionUID", JExpr.
   lit (1l));

  JDefinedClass enumClass = mainClass._enum ("Properties");
  enumClass._implements (PropertyEnum.class);
  for (OrchestrationObjectField field : orchObj.getFields ())
  {
   JEnumConstant ec =
    enumClass.enumConstant (calcCONSTANT (field.getName ()));
   ec.arg (JExpr.lit (field.getName ()));

  }
  JFieldVar keyVar =
   enumClass.field (JMod.PRIVATE + JMod.FINAL, String.class, "key");

  JMethod enumConstructor = enumClass.constructor (JMod.PRIVATE);
  JVar keyParam = enumConstructor.param (JMod.FINAL, String.class, "ky");
  enumConstructor.body ().assign (keyVar, keyParam);

  JMethod getKeyMethod =
   enumClass.method (JMod.PUBLIC, String.class, "getKey");
  getKeyMethod.annotate (Override.class);
  getKeyMethod.body ()._return (keyVar);


  JMethod mainConstructor = mainClass.constructor (JMod.PUBLIC);
  // TODO: ask Wil if we want to really use the class name as the key
  // TODO: figure out how to insert a comment into the JBlock
  mainConstructor.body ().directStatement ("super (" + orchObj.getName () +
   ".class.getName ());");

 }

 private void addBeanMethodsToModelForFields (JCodeModel jcm,
                                              Map<String, JDefinedClass> map,
                                              List<OrchestrationObject> orchObjs)
 {
  for (OrchestrationObject oo : orchObjs)
  {
   System.out.println ("Adding Bean Methods to Model " + oo.getName ());
   for (OrchestrationObjectField field : oo.getFields ())
   {
    System.out.println ("Adding Bean Method to Model " + oo.getName () + "." +
     field.getName () + "\t" + field.getType ());
    addBeanMethodsToModelForField (map.get (oo.getName ().toLowerCase ()), field, map);
   }
  }
 }

 private void addBaseAssemblersToModel (JCodeModel jcm,
                                        Map<String, JDefinedClass> map,
                                        List<OrchestrationObject> orchObjs)
 {
  for (OrchestrationObject orchObj : orchObjs)
  {
   try
   {
    addBaseAssemblerToModel (jcm, map, orchObjs, orchObj);
   }
   catch (JClassAlreadyExistsException ex)
   {
    throw new DictionaryValidationException (ex);
   }
  }
 }

 private void addBaseAssemblerToModel (JCodeModel jcm,
                                       Map<String, JDefinedClass> map,
                                       List<OrchestrationObject> orchObjs,
                                       OrchestrationObject orchObj)
  throws JClassAlreadyExistsException
 {
  String className = orchObj.getFullyQualifiedName () + "Assembler";
  System.out.println ("Creating assembler for " + className);
  JDefinedClass mainClass = jcm._class (className, ClassType.CLASS);
  map.put (orchObj.getName ().toLowerCase () + "Assembler", mainClass);


  JClass genericAssembler = jcm.ref (Assembler.class.getName ());
  List <JClass> narroweredClasses = new ArrayList ();
  narroweredClasses.add (jcm._getClass (orchObj.getFullyQualifiedName () + "Data"));
  narroweredClasses.add (jcm.ref (orchObj.getFullyQualifiedName ()));
  JClass narrowedAssembler = genericAssembler.narrow (narroweredClasses);
  mainClass._implements (narrowedAssembler);

  JDefinedClass dataClass = map.get (orchObj.getName ().toLowerCase ());
  JMethod assembleMethod = mainClass.method (JMod.PUBLIC, dataClass, "assemble");
  assembleMethod.annotate (Override.class);
  assembleMethod._throws (AssemblyException.class);
  JVar inputParam = assembleMethod.param (String.class, "input");
  JExpression inputIsNullExpr = inputParam.eq (JExpr._null ());
  JConditional ifStmnt = assembleMethod.body ()._if (inputIsNullExpr);
  ifStmnt._then ()._return (JExpr._null ());
  JVar resultVar =
   assembleMethod.body ().decl (dataClass, "result", JExpr._new (dataClass));
  for (OrchestrationObjectField field : orchObj.getFields ())
  {
   String setMethodCall = "result." + calcSetterMethodName (field.getName ()) +
    "(";
   String getMethodCall = "input." + calcGetterMethodName (field.getName ()) +
    "()";

   Object fieldType = calcFieldTypeToUse (dataClass, field, map);
   if (fieldType instanceof Class)
   {
    assembleMethod.body ().directStatement (setMethodCall + getMethodCall + ");");
   }
   else if (fieldType instanceof JDefinedClass)
   {
    OrchestrationObject child = findOrchestrationObject (orchObjs, field.getType ());
    String assemblerName = child.getFullyQualifiedName () + "Assembler";
    String assemblerCall =
     "new " + assemblerName + "().get (" + getMethodCall + ")";
    assembleMethod.body ().directStatement (setMethodCall + assemblerCall + ");");
   }
   else
   {
    assert false;
   }
  }
 }

 private OrchestrationObject findOrchestrationObject (List<OrchestrationObject> orchObjs,
                                   String name)
 {
  for (OrchestrationObject orch : orchObjs)
  {
   if (orch.getName ().equalsIgnoreCase (name))
   {
    return orch;
   }
  }
  return null;
 }

 private Object calcFieldTypeToUse (JDefinedClass mainClass,
                                    OrchestrationObjectField field,
                                    Map<String, JDefinedClass> map)
 {
  if (field.isIsList ())
  {
   return Data.class;
  }

  XmlType xmlType = new ModelFinder (model).findXmlType (field.getType ());
  if (xmlType == null)
  {
   //throw new DictionaryValidationException ("No XmlType found for field type " +
   // field.getType () + " " + field.getName ());
   //
   // THIS IS A HACK because Orchestration Objects are not formally defined
   // in the XmlTypes spreadsheeet
   Object fieldType = map.get (field.getType ().toLowerCase ());
   if (fieldType == null)
   {
    throw new DictionaryValidationException ("Complex field type " +
     field.getType () + " for field " + field.getName () + " not found in map");
   }
   return fieldType;
  }

  if (xmlType.getPrimitive ().equalsIgnoreCase ("Primitive"))
  {
   if (field.getType ().equalsIgnoreCase ("string"))
   {
    return String.class;
   }
   if (field.getType ().equalsIgnoreCase ("date"))
   {
    return Date.class;
   }
   if (field.getType ().equalsIgnoreCase ("dateTime"))
   {
    // TODO: figure out what the right class is for this
    return Date.class;
   }
   if (field.getType ().equalsIgnoreCase ("boolean"))
   {
    return Boolean.class;
   }
   if (field.getType ().equalsIgnoreCase ("integer"))
   {
    return Integer.class;
   }
   if (field.getType ().equalsIgnoreCase ("long"))
   {
    return Long.class;
   }

   throw new DictionaryValidationException ("Unknown/handled field type " +
    field.getType () + " " + field.getName ());
  }

  if (xmlType.getPrimitive ().equalsIgnoreCase ("Mapped String"))
  {
   return String.class;
  }
  if (xmlType.getPrimitive ().equalsIgnoreCase ("Complex"))
  {
   Object fieldType = map.get (field.getType ().toLowerCase ());
   if (fieldType == null)
   {
    throw new DictionaryValidationException ("Complex field type " +
     field.getType () + " for field " + field.getName () + " not found in map");
   }
   return fieldType;
  }
  throw new DictionaryValidationException ("Unknown/unhandled primitive " +
   xmlType.getPrimitive () + " for field type " +
   field.getType () + " for field " + field.getName ());
 }

 private void addBeanMethodsToModelForField (JDefinedClass mainClass,
                                             OrchestrationObjectField field,
                                             Map<String, JDefinedClass> map)
 {

  Object fieldType = calcFieldTypeToUse (mainClass, field, map);
  // getter
  JMethod getMethod = null;
  if (fieldType instanceof Class)
  {
   Class clazz = (Class) fieldType;
   getMethod =
    mainClass.method (JMod.PUBLIC, clazz, calcGetterMethodName (field.getName ()));
  }
  else
  {
   JDefinedClass jdc = (JDefinedClass) fieldType;
   getMethod =
    mainClass.method (JMod.PUBLIC, jdc, calcGetterMethodName (field.getName ()));
  }

  getMethod.body ().directStatement (
   "return super.get(Properties." +
   calcCONSTANT (field.getName ()) + ".getKey());");

  // setter
  JMethod setMethod =
   mainClass.method (JMod.PUBLIC, void.class, calcSetterMethodName (field.
   getName ()));
  JVar valueParam = null;
  if (fieldType instanceof Class)
  {
   Class clazz = (Class) fieldType;
   valueParam = setMethod.param (clazz, "value");
  }
  else
  {
   JDefinedClass jdc = (JDefinedClass) fieldType;
   valueParam = setMethod.param (jdc, "value");
  }

  setMethod.body ().directStatement ("super.set(Properties." +
   calcCONSTANT (field.getName ()) + ".getKey(), value);");
 }

 public static String calcGetterMethodName (String name)
 {
  return "get" + name.substring (0, 1).toUpperCase () + name.substring (1);
 }

 public static String calcSetterMethodName (String name)
 {
  return "set" + name.substring (0, 1).toUpperCase () + name.substring (1);
 }

 public static String calcCONSTANT (String name)
 {
  StringBuffer buf = new StringBuffer (name.length () + 3);
  // do the first character so we don't prepend the first with a _ if it is upper
  buf.append (Character.toUpperCase (name.charAt (0)));
  for (int i = 1; i <
   name.length (); i ++)
  {
   char c = name.charAt (i);
   if (Character.isUpperCase (c))
   {
    buf.append ('_');
   }

   buf.append (Character.toUpperCase (c));
  }

  return buf.toString ();
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

 private List<OrchestrationObject> getOrchestrationObjectsFromMessageStructures ()
 {
  List<OrchestrationObject> list = new ArrayList ();
  for (XmlType xmlType : model.getXmlTypes ())
  {
   if (xmlType.getPrimitive ().equals ("Complex"))
   {
    OrchestrationObject obj = new OrchestrationObject ();
    list.add (obj);
    obj.setPackagePath (ROOT_PACKAGE + ".base");
    obj.setName (xmlType.getName ());
    // these orchestratration data objects get assembled from versions of themself
    // i.e CluInfoData from CluInfo
    obj.setAssembleFromClass (xmlType.getName ());
    obj.setHasOwnCreateUpdate (xmlType.getHasOwnCreateUpdate ().equals ("true"));
    List<OrchestrationObjectField> fields = new ArrayList ();
    obj.setFields (fields);
    for (MessageStructure ms : model.getMessageStructures ())
    {
     if (ms.getXmlObject ().equals (xmlType.getName ()))
     {
      OrchestrationObjectField field = new OrchestrationObjectField ();
      fields.add (field);
      field.setName (ms.getShortName ());
      field.setType (calcType (ms.getType ()));
      field.setIsList (calcIsList (ms.getType ()));
     }

    }
   }
  }
  return list;
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

 private List<OrchestrationObject> getOrchestrationObjectsFromOrchObjs ()
 {
  List<OrchestrationObject> list = new ArrayList ();
  OrchestrationObject obj = null;
  List<OrchestrationObjectField> fields = null;
  for (OrchObj orch : model.getOrchObjs ())
  {
   // reset and add the object
   if ( ! orch.getParent ().equals (""))
   {
    obj = new OrchestrationObject ();
    list.add (obj);
    obj.setName (orch.getParent ());
    // TODO: add this to spreadsheet
    obj.setAssembleFromClass ("TODO: add this to spreadsheet");
    obj.setPackagePath (ROOT_PACKAGE + ".orch");
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
    field.setName (orch.getChild ());
    field.setType (calcType (orch.getXmlType ()));
    field.setIsList (calcIsCardList (orch.getCard1 ()));
    continue;

   }
   //TODO: worry about grand children

  }
  return list;
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
