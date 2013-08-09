package org.kuali.student.r1.common.dictionary.service.impl;

import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.infc.Meta;
import org.kuali.student.r2.common.infc.RichText;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;

@Deprecated
public class ComplexSubstructuresHelper {

	public Set<String> getComplexStructures(String className) {
		Set<String> complexStructures = new LinkedHashSet<String>();
		loadComplexStructures(className, complexStructures);
		return complexStructures;
	}

	private void loadComplexStructures(String className,
			Set<String> complexStructures) {
		if (!complexStructures.add(className)) {
			return;
		}
		BeanInfo beanInfo;
		Class<?> clazz;
		try {
			clazz = Class.forName(className);
			// Debuggin  System.out.println("The Class : " + className);
		} catch (ClassNotFoundException ex) {
			System.out
					.println("ComplexSubstructuresHelper: Could not process because the class must be a freestanding object: "
							+ className);
			return;
		}
		try {
			beanInfo = Introspector.getBeanInfo(clazz);
		} catch (IntrospectionException ex) {
			throw new RuntimeException(ex);
		}
		
		// Get all the fields including inherited fields...
		ArrayList<Field> fields = new ArrayList<Field>();
	    fields = this.getAllFields(fields, clazz);
	    
		for (PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
			String propertyName = pd.getName();
//			System.out.println(propertyName);
			Class<?> subClass = pd.getPropertyType();
			// find the actual class and not the interface for the field
			Field propertyField1 = findField(propertyName, fields);
            // The following was added because of this behaviour, explained in the urls under this comment
            // https://forums.oracle.com/forums/thread.jspa?threadID=1158697
            // http://bugs.sun.com/view_bug.do?bug_id=6794807
			if (propertyField1 != null) {
				Class<?> subClassField =  propertyField1.getType();
				subClass = subClassField;
			}
			//
			if (List.class.equals(subClass)) {
				try {
//					Field propertyField = clazz
//							.getDeclaredField(propertyName);
					
					Field propertyField = findField(propertyName, fields);
					ParameterizedType propertyGenericDataType = (ParameterizedType) propertyField.getGenericType();
					subClass = (Class<?>) propertyGenericDataType
							.getActualTypeArguments()[0];
//				} catch (NoSuchFieldException ex) {
//					throw new RuntimeException(ex);
				} catch (SecurityException ex) {
					throw new RuntimeException(ex);
				}
			}
			//
			if (!MetaInfo.class.equals(subClass)
					&& !Meta.class.equals(subClass)		// added coz it's a interface object
					&& !RichText.class.equals(subClass)		// added coz it's a interface object					
					&& !Class.class.equals(subClass)
					
					&& !String.class.equals(subClass)
					&& !Integer.class.equals(subClass)
					&& !Long.class.equals(subClass)
					&& !Boolean.class.equals(subClass)
					&& !boolean.class.equals(subClass)
					&& !int.class.equals(subClass)
					&& !long.class.equals(subClass)
					&& !Double.class.equals(subClass)
					&& !Float.class.equals(subClass)
					&& !Date.class.equals(subClass)
					&& !DictionaryConstants.ATTRIBUTES.equals(propertyName)
					&& !Enum.class.isAssignableFrom(subClass)
					&& !Object.class.equals(subClass)) {
				loadComplexStructures(subClass.getName(), complexStructures);
			}
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
