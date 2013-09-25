package org.kuali.student.r1.common.dictionary.service.impl;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.kuali.student.common.spring.AnnotationUtils;
import org.kuali.student.r1.common.dictionary.dto.DataType;
import org.kuali.student.r1.common.dictionary.dto.FieldDefinition;
import org.kuali.student.r1.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.infc.Meta;


@Deprecated
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
      boolean skipOver = false;
      // short term fix for KSENROLL-5710
      // essentially don't require deprecated method dictionary definitions.
      if (pd.getReadMethod() != null && AnnotationUtils.doesMethodHierarchyContainAnnotation(pd.getReadMethod(), Deprecated.class))
          skipOver = true;
      
      if (MetaInfo.class.equals (pd.getPropertyType ()) || Class.class.equals (pd.getPropertyType ()) ||  DictionaryConstants.ATTRIBUTES.equals (pd.getName ()))
              skipOver = true;
      
   if (!skipOver)
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
	  // Debuggin System.out.println(pd.getName());
//    if (MetaInfo.class.equals (pd.getPropertyType ()))
    if (Meta.class.isAssignableFrom(pd.getPropertyType()))
    {
      return true;
    }
//       &&  ! DictionaryConstants.ATTRIBUTES.equals (pd.getName ()))
  }
  return false;
 }

 private FieldDefinition calcField (Class<?> clazz, PropertyDescriptor pd)
 {
  FieldDefinition fd = new FieldDefinition ();
  String propDescriptorName = pd.getName ();
fd.setName (propDescriptorName);
  Class<?> pt = pd.getPropertyType ();
  if (List.class.equals (pt))
  {
   fd.setMaxOccurs (fd.UNBOUNDED);
   try
   {
	   ArrayList<Field> allFieldsOnClass = new ArrayList<Field>();
//	   Field declaredFields = clazz.getDeclaredField (propDescriptorName);
	   allFieldsOnClass = getAllFields(allFieldsOnClass, clazz);	   
       Field declaredFields = findField(propDescriptorName, allFieldsOnClass);
	ParameterizedType propGenericType = (ParameterizedType) declaredFields.getGenericType ();
	pt =
    (Class<?>) propGenericType.getActualTypeArguments ()[0];
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
  else if (Enum.class.isAssignableFrom (pt))
  {
   return DataType.STRING;
  }
  else if (Object.class.equals (pt))
  {
   return DataType.STRING;
  }
  else if (pt.getName ().startsWith ("org.kuali.student."))
  {
   return DataType.COMPLEX;
  }
  else
  {
   throw new RuntimeException ("unknown/unhandled type of object in bean " + pt.getName ());
  }
 }
 
	public ArrayList<Field> getAllFields(ArrayList<Field> fields, Class<?> type) {
	    for (Field field: type.getDeclaredFields()) {
	        fields.add(field);
	    }

	    if (type.getSuperclass() != null) {
	        fields = getAllFields(fields, type.getSuperclass());
	    }

	    return fields;
	}
	
	public Field findField(String fieldName, ArrayList<Field> fields) {
		for (Field field : fields) {
			if (field.getName().equals(fieldName)) {
				return field;
			}
		}
		return null;
	}
}

