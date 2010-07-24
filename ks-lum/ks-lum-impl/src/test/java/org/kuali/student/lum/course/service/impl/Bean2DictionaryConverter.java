package org.kuali.student.lum.course.service.impl;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;
import org.kuali.student.core.dictionary.dto.DataType;

import org.kuali.student.core.dictionary.dto.FieldDefinition;
import org.kuali.student.core.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.core.dto.MetaInfo;

public class Bean2DictionaryConverter
{

 private Class<?> clazz;

 public Bean2DictionaryConverter (Class<?> clazz)
 {
  this.clazz = clazz;
 }

 public ObjectStructureDefinition convert ()
 {
  ObjectStructureDefinition os = new ObjectStructureDefinition ();
  os.setName (clazz.getName ());
  BeanInfo beanInfo;
  try
  {
   beanInfo = Introspector.getBeanInfo (clazz);
  }
  catch (IntrospectionException ex)
  {
   throw new RuntimeException (ex);
  }
  os.setHasMetaData (calcHasMetaData (beanInfo));
  for (PropertyDescriptor pd : beanInfo.getPropertyDescriptors ())
  {
   if ( ! MetaInfo.class.equals (pd.getPropertyType ())
       &&  ! Class.class.equals (pd.getPropertyType ())
       &&  ! DictionaryConstants.ATTRIBUTES.equals (pd.getName ()))
   {
    os.getAttributes ().add (calcField (clazz, pd));
   }
  }
  return os;
 }

 private boolean calcHasMetaData (BeanInfo beanInfo)
 {
  for (PropertyDescriptor pd : beanInfo.getPropertyDescriptors ())
  {
   // not sure why a simple
   // if (MetaInfo.class.equals (pd.getPropertyType ())
   // {
   //   return true;
   // }
   // wont work
   if ( ! MetaInfo.class.equals (pd.getPropertyType ())
       &&  ! Class.class.equals (pd.getPropertyType ())
       &&  ! DictionaryConstants.ATTRIBUTES.equals (pd.getName ()))
   {
    // continue
   }
   else
   {
    return true;
   }
  }
  return false;
 }

 private FieldDefinition calcField (Class<?> clazz, PropertyDescriptor pd)
 {
  FieldDefinition fd = new FieldDefinition ();
  fd.setName (pd.getName ());
  Class<?> pt = pd.getPropertyType ();
  if (List.class.equals (pt))
  {
   fd.setMaxOccurs (fd.UNBOUNDED);
   try
   {
    pt =
    (Class<?>) ((ParameterizedType) clazz.getDeclaredField (pd.getName ()).getGenericType ()).getActualTypeArguments ()[0];
   }
   catch (NoSuchFieldException ex)
   {
    throw new RuntimeException (ex);
   }
   catch (SecurityException ex)
   {
    throw new RuntimeException (ex);
   }
  }
  else
  {
   fd.setMaxOccurs (fd.SINGLE);
  }
  fd.setDataType (calcDataType (pt));
  return fd;
 }

 private DataType calcDataType (Class<?> pt)
 {
  if (int.class.equals (pt) || Integer.class.equals (pt))
  {
   return DataType.INTEGER;
  }
  else if (long.class.equals (pt) || Long.class.equals (pt))
  {
   return DataType.LONG;
  }
  else if (double.class.equals (pt) || Double.class.equals (pt))
  {
   return DataType.DOUBLE;
  }
  else if (float.class.equals (pt) || Float.class.equals (pt))
  {
   return DataType.FLOAT;
  }
  else if (boolean.class.equals (pt) || Boolean.class.equals (pt))
  {
   return DataType.BOOLEAN;
  }
  else if (Date.class.equals (pt))
  {
   return DataType.DATE;
  }
  else if (String.class.equals (pt))
  {
   return DataType.STRING;
  }
  else if (List.class.equals (pt))
  {
   throw new RuntimeException ("Can't have a list of lists, List<List<?>>");
  }
  else
  {
   return DataType.COMPLEX;
  }
 }
}
