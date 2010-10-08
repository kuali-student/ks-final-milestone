package org.kuali.student.lum.course.service.impl;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.kuali.student.core.dto.MetaInfo;

public class ComplexSubstructuresHelper
{


 public Set<String> getComplexStructures (String className)
 {
  Set<String> complexStructures = new LinkedHashSet<String> ();
  loadComplexStructures (className, complexStructures);
  return complexStructures;
 }

 private void loadComplexStructures (String className,
                                     Set<String> complexStructures)
 {
  if ( ! complexStructures.add (className))
  {
   return;
  }
  BeanInfo beanInfo;
  Class<?> clazz;
  try
  {
   clazz = Class.forName (className);
  }
  catch (ClassNotFoundException ex)
  {
   System.out.println ("ComplexSubstructuresHelper: Could not process because the class must be a freestanding object: " + className);
   return;
  }
  try
  {
   beanInfo = Introspector.getBeanInfo (clazz);
  }
  catch (IntrospectionException ex)
  {
   throw new RuntimeException (ex);
  }
  for (PropertyDescriptor pd : beanInfo.getPropertyDescriptors ())
  {
   Class<?> subClass = pd.getPropertyType ();
   if (List.class.equals (subClass))
   {
    try
    {
     subClass =
     (Class<?>) ((ParameterizedType) clazz.getDeclaredField (
       pd.getName ()).getGenericType ()).getActualTypeArguments ()[0];
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
   if ( ! MetaInfo.class.equals (subClass)
       &&  ! Class.class.equals (subClass)
       &&  ! String.class.equals (subClass)
       &&  ! Integer.class.equals (subClass)
       &&  ! Long.class.equals (subClass)
       &&  ! Boolean.class.equals (subClass)
       &&  ! boolean.class.equals (subClass)
       &&  ! int.class.equals (subClass)
       &&  ! long.class.equals (subClass)
       &&  ! Double.class.equals (subClass)
       &&  ! Float.class.equals (subClass)
       &&  ! Date.class.equals (subClass)
       &&  ! DictionaryConstants.ATTRIBUTES.equals (pd.getName ())
       &&  ! Enum.class.isAssignableFrom (subClass)
       &&  ! Object.class.equals (subClass))
   {
    loadComplexStructures (subClass.getName (), complexStructures);
   }
  }
 }


}
