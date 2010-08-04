package org.kuali.student.lum.course.service.impl;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.kuali.student.core.dictionary.dto.FieldDefinition;
import org.kuali.student.core.dto.Idable;
import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.lum.lu.dto.CluInfo;

public class DictionaryCreator
{

 private static final String OBJECT_STRUCTURE_CLASS =
                             "objectStructureDefinition";
 private static final String FIELD_DEFINITION_CLASS =
                             FieldDefinition.class.getSimpleName ();
 private static final String ATTRIBUTES = "attributes";
 private static final String NAME = "name";
 private static final String HAS_META_DATA = "hasMetaData";
 private static final String DATA_OBJECT_STRUCTURE = "dataObjectStructure";
 private static final String BASE_INTEGER_REPEATING = "baseIntegerRepeating";
 private static final String BASE_LONG_REPEATING = "baseLongRepeating";
 private static final String BASE_DOUBLE_REPEATING = "baseDoubleRepeating";
 private static final String BASE_FLOAT_REPEATING = "baseFloatRepeating";
 private static final String BASE_BOOLEAN_REPEATING = "baseBooleanRepeating";
 private static final String BASE_DATE_REPEATING = "baseDateRepeating";
 private static final String BASE_STRING_REPEATING = "baseStringRepeating";
 private static final String BASE_COMPLEX_REPEATING = "baseComplexRepeating";
 private static final String BASE_INTEGER = "baseInteger";
 private static final String BASE_LONG = "baseLong";
 private static final String BASE_DOUBLE = "baseDouble";
 private static final String BASE_FLOAT = "baseFloat";
 private static final String BASE_BOOLEAN = "baseBoolean";
 private static final String BASE_DATE = "baseDate";
 private static final String BASE_STRING = "baseString";
 private static final String BASE_COMPLEX = "baseComplex";
 private static final String BASE_KUALI_ID = "baseKualiId";
 private static final String BASE_KUALI_RELATED_ORG_ID = "baseKualiRelatedOrgId";
 private static final String BASE_KUALI_RELATED_CLU_ID = "baseKualiRelatedCluId";
 private static final String BASE_KUALI_RELATED__PERSON_ID = "baseKualiRelatedPersonId";
 private static final String BASE_KUALI_TYPE = "baseKualiType";
 private static final String BASE_KUALI_STATE = "baseKualiState";
 private static final String BASE_KUALI_EFFECTIVE_DATE =
                             "baseKualiEffectiveDate";
 private static final String BASE_KUALI_EXPIRATION_DATE =
                             "baseKualiExpirationDate";

 /**
  * @param args
  */
 public static void main (String[] args)
 {
  Class<?> clazz = CluInfo.class;
  new DictionaryCreator ().execute (clazz, "out.xml");
 }

 public void execute (Class<?> clazz, String outputFileName)
 {
  // Create base dictionary object structure for DTOs that map to entities
  File file = new File (outputFileName);
  OutputStream os;
  try
  {
   os = new FileOutputStream (file);
  }
  catch (FileNotFoundException ex)
  {
   throw new IllegalArgumentException (ex);
  }
  StringBuffer s = new StringBuffer ();
  addSpringHeaderOpen (s);

  System.out.println (clazz.getName ());
  addObjectStructure (clazz, s, new HashSet<Class<?>> ());

  addSpringHeaderClose (s);
  try
  {
   os.write (s.toString ().getBytes ());
  }
  catch (IOException ex)
  {
   throw new IllegalArgumentException (ex);
  }
 }

 private void addObjectStructure (Class<?> clazz, StringBuffer s,
                                  Set<Class<?>> processed)
 {
  //Don't process if processed
  if (processed.contains (clazz))
  {
   return;
  }
  processed.add (clazz);

  //Step 1, create the abstract structure
  s.append ("\n\n<!-- " + clazz.getSimpleName () + "-->");
  s.append ("\n<bean id=\"" + clazz.getName ()
            + "-parent\" abstract=\"true\" parent=\"" + OBJECT_STRUCTURE_CLASS
            + "\">");
  addProperty (NAME, clazz.getName (), s);
  s.append ("\n<property name=\"" + ATTRIBUTES + "\">");
  s.append ("\n<list>");
  BeanInfo beanInfo;
  try
  {
   beanInfo = Introspector.getBeanInfo (clazz);
  }
  catch (IntrospectionException ex)
  {
   throw new IllegalArgumentException (ex);
  }
  boolean hasMetaData = false;
  for (PropertyDescriptor pd : beanInfo.getPropertyDescriptors ())
  {
   if ( ! MetaInfo.class.equals (pd.getPropertyType ()) &&  ! Class.class.equals (
     pd.getPropertyType ()) &&  ! ATTRIBUTES.equals (pd.getName ()))
   {
    String fieldName = clazz.getSimpleName ().substring (0, 1).toLowerCase () + clazz.getSimpleName ().substring (
      1) + "." + pd.getName ();
    s.append ("\n<ref bean=\"" + fieldName + "\"/>");
   }
   else
   {
    hasMetaData = true;
   }
  }
  s.append ("\n</list>");
  s.append ("\n</property>");

  addProperty (HAS_META_DATA, String.valueOf (hasMetaData), s);
  s.append ("\n</bean>");

  //Create the instance
  s.append ("\n<bean id=\"" + clazz.getName () + "\" parent=\""
            + clazz.getName () + "-parent\"/>");
  //Step 2, loop through attributes
  Set<Class<?>> dependantStructures = new HashSet<Class<?>> ();
  for (PropertyDescriptor pd : beanInfo.getPropertyDescriptors ())
  {
   if ( ! MetaInfo.class.equals (pd.getPropertyType ()) &&  ! Class.class.equals (
     pd.getPropertyType ()) &&  ! ATTRIBUTES.equals (pd.getName ()))
   {
    dependantStructures.addAll (addField (clazz, pd, s, processed));
   }
  }
  //Step 3, process all dependant object structures
  for (Class<?> dependantClass : dependantStructures)
  {
   addObjectStructure (dependantClass, s, processed);
  }

 }

 private Set<Class<?>> addField (Class<?> clazz, PropertyDescriptor pd,
                                 StringBuffer s, Set<Class<?>> processed)
 {
  Set<Class<?>> dependantStructures = new HashSet<Class<?>> ();

  //Create the abstract field
  String fieldName = clazz.getSimpleName ().substring (0, 1).toLowerCase () + clazz.getSimpleName ().substring (
    1) + "." + pd.getName ();
  Class<?> pt = pd.getPropertyType ();
  String parentField = FIELD_DEFINITION_CLASS;
  boolean isComplex = false;
  if (clazz.isAssignableFrom (Idable.class) && "id".equals (pd.getName ()))
  {
   parentField = BASE_KUALI_ID;
  }
  if ("orgId".equals (pd.getName ()))
  {
   parentField = BASE_KUALI_RELATED_ORG_ID;
  }
  else if (List.class.equals (pt))
  {
   try
   {
    pt =
    (Class<?>) ((ParameterizedType) clazz.getDeclaredField (pd.getName ()).getGenericType ()).getActualTypeArguments ()[0];
   }
   catch (NoSuchFieldException ex)
   {
    throw new IllegalArgumentException (ex);
   }
   catch (SecurityException ex)
   {
    throw new IllegalArgumentException (ex);
   }
   if (int.class.equals (pt) || Integer.class.equals (pt))
   {
    parentField = BASE_INTEGER_REPEATING;
   }
   else if (long.class.equals (pt) || Long.class.equals (pt))
   {
    parentField = BASE_LONG_REPEATING;
   }
   else if (double.class.equals (pt) || Double.class.equals (pt))
   {
    parentField = BASE_DOUBLE_REPEATING;
   }
   else if (float.class.equals (pt) || Float.class.equals (pt))
   {
    parentField = BASE_FLOAT_REPEATING;
   }
   else if (boolean.class.equals (pt) || Boolean.class.equals (pt))
   {
    parentField = BASE_BOOLEAN_REPEATING;
   }
   else if (Date.class.equals (pt))
   {
    parentField = BASE_DATE_REPEATING;
   }
   else if (String.class.equals (pt))
   {
    parentField = BASE_STRING_REPEATING;
   }
   else if (List.class.equals (pt))
   {
    throw new RuntimeException ("Can't have a list of lists, List<List<?>> for property: "
                                + fieldName);
   }
   else
   {
    parentField = BASE_COMPLEX_REPEATING;
    isComplex = true;
    dependantStructures.add (pt);
   }
  }
  else
  {
   if (int.class.equals (pt) || Integer.class.equals (pt))
   {
    parentField = BASE_INTEGER;
   }
   else if (long.class.equals (pt) || Long.class.equals (pt))
   {
    parentField = BASE_LONG;
   }
   else if (double.class.equals (pt) || Double.class.equals (pt))
   {
    parentField = BASE_DOUBLE;
   }
   else if (float.class.equals (pt) || Float.class.equals (pt))
   {
    parentField = BASE_FLOAT;
   }
   else if (boolean.class.equals (pt) || Boolean.class.equals (pt))
   {
    parentField = BASE_BOOLEAN;
   }
   else if (Date.class.equals (pt))
   {
    parentField = BASE_DATE;
   }
   else if (String.class.equals (pt))
   {
    parentField = BASE_STRING;
   }
   else
   {
    parentField = BASE_COMPLEX;
    isComplex = true;
    dependantStructures.add (pt);
   }
  }


  s.append ("\n\n<bean id=\"" + fieldName
            + "-parent\" abstract=\"true\" parent=\"" + parentField + "\">");
  addProperty (NAME, pd.getName (), s);
  if (isComplex)
  {
   addPropertyRef (DATA_OBJECT_STRUCTURE, pt.getName (), s);
  }

  s.append ("\n</bean>");

  //Create the instance
  s.append ("\n<bean id=\"" + fieldName + "\" parent=\"" + fieldName
            + "-parent\"/>");

  return dependantStructures;
 }

 private void addProperty (String propertyName, String propertyValue,
                           StringBuffer s)
 {
  s.append ("\n<property name=\"" + propertyName + "\" value=\"" + propertyValue
            + "\"/>");
 }

 private static void addPropertyRef (String propertyName, String propertyValue,
                                     StringBuffer s)
 {
  s.append ("\n<property name=\"" + propertyName + "\" ref=\"" + propertyValue
            + "\"/>");
 }

 private void addSpringHeaderClose (StringBuffer s)
 {
  s.append ("\n</beans>");
 }

 public void addSpringHeaderOpen (StringBuffer s)
 {
  s.append ("<beans xmlns=\"http://www.springframework.org/schema/beans\""
            + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
            + "xsi:schemaLocation=\""
            + "http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.5.xsd"
            + "\">");
  s.append ("\n<import resource=\"classpath:ks-base-dictionary-context.xml\"/>");
 }
}
