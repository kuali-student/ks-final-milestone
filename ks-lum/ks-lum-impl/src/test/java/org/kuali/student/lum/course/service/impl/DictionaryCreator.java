package org.kuali.student.lum.course.service.impl;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.kuali.student.core.dictionary.dto.FieldDefinition;
import org.kuali.student.core.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.core.dto.Idable;
import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.lum.lu.dto.CluInfo;

public class DictionaryCreator {

	private static final String OBJECT_STRUCTURE_CLASS = ObjectStructureDefinition.class.getSimpleName();
	private static final String FIELD_DEFINITION_CLASS = FieldDefinition.class.getSimpleName();
	
	private static final String ATTRIBUTES = "attributes";
	private static final String NAME = "name";
	private static final String HAS_META_DATA = "hasMetaData";
	private static final String DATA_OBJECT_STRUCTURE = "dataObjectStructure";
	
	private static final String BASE_REPEATING_INTEGER_FIELD = "baseRepeatingInteger";
	private static final String BASE_REPEATING_LONG_FIELD = "baseRepeatingLong";
	private static final String BASE_REPEATING_DOUBLE_FIELD = "baseRepeatingDouble";
	private static final String BASE_REPEATING_FLOAT_FIELD = "baseRepeatingFloat";
	private static final String BASE_REPEATING_BOOLEAN_FIELD = "baseRepeatingBoolean";
	private static final String BASE_REPEATING_DATE_FIELD = "baseRepeatingDate";
	private static final String BASE_REPEATING_STRING_FIELD = "baseRepeatingString";
	private static final String BASE_REPEATING_COMPLEX_FIELD = "baseRepeatingComplex";
	private static final String BASE_SINGLE_INTEGER_FIELD = "baseSingleInteger";
	private static final String BASE_SINGLE_LONG_FIELD = "baseRepeatingLong";
	private static final String BASE_SINGLE_DOUBLE_FIELD = "baseSingleDouble";
	private static final String BASE_SINGLE_FLOAT_FIELD = "baseSingleFloat";
	private static final String BASE_SINGLE_BOOLEAN_FIELD = "baseSingleBoolean";
	private static final String BASE_SINGLE_DATE_FIELD = "baseSingleDate";
	private static final String BASE_SINGLE_STRING_FIELD = "baseSingleString";
	private static final String BASE_SINGLE_COMPLEX_FIELD = "baseSingleComplex";
	private static final String BASE_ID_FIELD = "baseId";

	

	/**
	 * @param args
	 * @throws IOException
	 * @throws IntrospectionException 
	 * @throws NoSuchFieldException 
	 * @throws SecurityException 
	 */
	public static void main(String[] args) throws IOException, IntrospectionException, SecurityException, NoSuchFieldException {

		Class<?> clazz = CluInfo.class;
		
		// Create base dictionary object structure for DTOs that map to entities
		File file = new File("out.xml");
		OutputStream os = new FileOutputStream(file);
		StringBuffer s = new StringBuffer();
		addSpringHeaderOpen(s);
		
		System.out.println(clazz.getName());
		addObjectStructure(clazz, s, new HashSet<Class<?>>());
		
		addSpringHeaderClose(s);
		
		os.write(s.toString().getBytes());
	}
	
	private static void addObjectStructure(Class<?> clazz, StringBuffer s, Set<Class<?>> processed) throws IntrospectionException, SecurityException, NoSuchFieldException {
		//Don't process if processed
		if(processed.contains(clazz)){
			return;
		}
		processed.add(clazz);
		
		//Step 1, create the abstract structure
		s.append("\n\n<!-- "+clazz.getSimpleName()+"-->");
		s.append("\n<bean id=\""+clazz.getName()+"-parent\" abstract=\"true\" parent=\""+OBJECT_STRUCTURE_CLASS+"\">");
		addProperty(NAME,clazz.getName(),s);
		s.append("\n<property name=\""+ATTRIBUTES+"\">");
		s.append("\n<list>");
		BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
		boolean hasMetaData = false;
		for(PropertyDescriptor pd:beanInfo.getPropertyDescriptors()){
			if(!MetaInfo.class.equals(pd.getPropertyType())&&!Class.class.equals(pd.getPropertyType())&&!ATTRIBUTES.equals(pd.getName())){
				String fieldName = clazz.getSimpleName().substring(0, 1).toLowerCase()+clazz.getSimpleName().substring(1)+"."+pd.getName();
				s.append("\n<ref bean=\""+fieldName+"\"/>");
			}else{
				hasMetaData = true;
			}
		}
		s.append("\n</list>");
		s.append("\n</property>");
		
		addProperty(HAS_META_DATA,String.valueOf(hasMetaData),s);
		s.append("\n</bean>");
		
		//Create the instance
		s.append("\n<bean id=\""+clazz.getName()+"\" parent=\""+clazz.getName()+"-parent\"/>");
		//Step 2, loop through attributes
		Set<Class<?>> dependantStructures = new HashSet<Class<?>>();
		for(PropertyDescriptor pd:beanInfo.getPropertyDescriptors()){
			if(!MetaInfo.class.equals(pd.getPropertyType())&&!Class.class.equals(pd.getPropertyType())&&!ATTRIBUTES.equals(pd.getName())){
				dependantStructures.addAll(addField(clazz,pd,s,processed));
			}
		}
		//Step 3, process all dependant object structures
		for(Class<?> dependantClass:dependantStructures){
			addObjectStructure(dependantClass,s,processed);
		}
		
	}

	private static Set<Class<?>> addField(Class<?> clazz, PropertyDescriptor pd,
			StringBuffer s, Set<Class<?>> processed) throws SecurityException, NoSuchFieldException {
		Set<Class<?>> dependantStructures = new HashSet<Class<?>>();
		
		//Create the abstract field
		String fieldName = clazz.getSimpleName().substring(0, 1).toLowerCase()+clazz.getSimpleName().substring(1)+"."+pd.getName();
		Class<?> pt = pd.getPropertyType();
		String parentField=FIELD_DEFINITION_CLASS;
		boolean isComplex = false;
		if(clazz.isAssignableFrom(Idable.class)&&"id".equals(pd.getName())){
			parentField=BASE_ID_FIELD;
		}else if(List.class.equals(pt)){
			pt = (Class<?>) ((ParameterizedType) clazz.getDeclaredField(pd.getName()).getGenericType()).getActualTypeArguments()[0];
			if(int.class.equals(pt) || Integer.class.equals(pt)){
				parentField=BASE_REPEATING_INTEGER_FIELD;
			}else if(long.class.equals(pt) || Long.class.equals(pt)){
				parentField=BASE_REPEATING_LONG_FIELD;
			}else if(double.class.equals(pt) || Double.class.equals(pt)){
				parentField=BASE_REPEATING_DOUBLE_FIELD;
			}else if(float.class.equals(pt) || Float.class.equals(pt)){
				parentField=BASE_REPEATING_FLOAT_FIELD;
			}else if(boolean.class.equals(pt) || Boolean.class.equals(pt)){
				parentField=BASE_REPEATING_BOOLEAN_FIELD;
			}else if(Date.class.equals(pt)){
				parentField=BASE_REPEATING_DATE_FIELD;
			}else if(String.class.equals(pt)){
				parentField=BASE_REPEATING_STRING_FIELD;
			}else if(List.class.equals(pt)){
				throw new RuntimeException("Can't have a list of lists, List<List<?>> for property: "+fieldName);
			}else{
				parentField=BASE_REPEATING_COMPLEX_FIELD;
				isComplex=true;
				dependantStructures.add(pt);
			}
		}else{
			if(int.class.equals(pt) || Integer.class.equals(pt)){
				parentField=BASE_SINGLE_INTEGER_FIELD;
			}else if(long.class.equals(pt) || Long.class.equals(pt)){
				parentField=BASE_SINGLE_LONG_FIELD;
			}else if(double.class.equals(pt) || Double.class.equals(pt)){
				parentField=BASE_SINGLE_DOUBLE_FIELD;
			}else if(float.class.equals(pt) || Float.class.equals(pt)){
				parentField=BASE_SINGLE_FLOAT_FIELD;
			}else if(boolean.class.equals(pt) || Boolean.class.equals(pt)){
				parentField=BASE_SINGLE_BOOLEAN_FIELD;
			}else if(Date.class.equals(pt)){
				parentField=BASE_SINGLE_DATE_FIELD;
			}else if(String.class.equals(pt)){
				parentField=BASE_SINGLE_STRING_FIELD;
			}else{
				parentField=BASE_SINGLE_COMPLEX_FIELD;
				isComplex=true;
				dependantStructures.add(pt);
			}
		}
		
		
		s.append("\n\n<bean id=\""+fieldName+"-parent\" abstract=\"true\" parent=\""+parentField+"\">");
		addProperty(NAME,pd.getName(),s);
		if(isComplex){
			addPropertyRef(DATA_OBJECT_STRUCTURE,pt.getName(),s);
		}
		
		s.append("\n</bean>");
		
		//Create the instance
		s.append("\n<bean id=\""+fieldName+"\" parent=\""+fieldName+"-parent\"/>");
		
		return dependantStructures;
	}

	private static void addProperty(String propertyName, String propertyValue, StringBuffer s){
		s.append("\n<property name=\""+propertyName+"\" value=\""+propertyValue+"\"/>");
	}
	private static void addPropertyRef(String propertyName, String propertyValue, StringBuffer s){
		s.append("\n<property name=\""+propertyName+"\" ref=\""+propertyValue+"\"/>");
	}

	private static void addSpringHeaderClose(StringBuffer s) {
		s.append("\n</beans>");
	}

	public static void addSpringHeaderOpen(StringBuffer s){
		s.append("<beans xmlns=\"http://www.springframework.org/schema/beans\""
				+ "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
				+ "xsi:schemaLocation=\""
				+ "http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.5.xsd"
				+ "\">");
		s.append("\n<import resource=\"classpath:ks-base-dictionary-context.xml\"/>");
	}

}
