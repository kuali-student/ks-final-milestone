package org.kuali.student.lum.program.service.impl;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.kuali.student.lum.course.service.assembler.CourseAssemblerConstants;
import org.kuali.student.lum.program.dto.MajorDisciplineInfo;
import org.kuali.student.lum.program.service.assembler.ProgramAssemblerConstants;

public class MajorDisciplineDataGenerator {
	private static final String[] campusLocations = {CourseAssemblerConstants.COURSE_CAMPUS_LOCATION_CD_NORTH,CourseAssemblerConstants.COURSE_CAMPUS_LOCATION_CD_SOUTH};
	Random generator = new Random();

	public MajorDisciplineInfo getMajorDisciplineInfoTestData() throws IntrospectionException, InstantiationException, IllegalAccessException, IllegalArgumentException, SecurityException, InvocationTargetException, NoSuchFieldException{
		MajorDisciplineInfo testData = generateTestData(MajorDisciplineInfo.class, 0, 0,null);
		return testData;
	}

	private <T> T generateTestData(Class<T> clazz, Integer propertyIndex, int sameClassNestLevel, String parentPropertyName) throws IntrospectionException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SecurityException, NoSuchFieldException {
		if(sameClassNestLevel>2){
			return null;
		}
		
		T instance = clazz.newInstance();
		System.out.println("Class:" + clazz.getName() + " preant: " + parentPropertyName);
		if(clazz.getName().equals("org.kuali.student.lum.program.dto.LoInfo"))
			System.out.println("debug now...");
		
		if(String.class.equals(clazz)){
			propertyIndex++;
			instance = (T) (getStringValue(parentPropertyName,parentPropertyName,propertyIndex));
			return instance;
		}
		
		BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
		for(PropertyDescriptor pd:beanInfo.getPropertyDescriptors()){
			System.out.println(pd.getName());
			if(ignoreProperty(pd)){
				continue;
			}
			propertyIndex++;
			Object value = null;
			Class<?> pt = pd.getPropertyType();
			if(List.class.equals(pt)){
				//If this is a list then make a new list and make x amount of test data of that list type
				//Get the list type:
				Class<?> nestedClass = (Class<?>) ((ParameterizedType) clazz.getDeclaredField(pd.getName()).getGenericType()).getActualTypeArguments()[0];
				List list = new ArrayList();
				for(int i=0;i<2;i++){
					propertyIndex++;
					Object listItem=null;
					if(nestedClass.equals(pt)||nestedClass.equals(clazz)){
						listItem=generateTestData(nestedClass,propertyIndex,sameClassNestLevel+1,pd.getName());
					}else{
						listItem=generateTestData(nestedClass,propertyIndex,sameClassNestLevel,pd.getName());
					}
					if(listItem!=null){
						list.add(listItem);
					}
				}
				value = list;
			}else if(Map.class.equals(pt)){
				Class<?> keyType = (Class<?>) ((ParameterizedType) clazz.getDeclaredField(pd.getName()).getGenericType()).getActualTypeArguments()[0];
				Class<?> valueType = (Class<?>) ((ParameterizedType) clazz.getDeclaredField(pd.getName()).getGenericType()).getActualTypeArguments()[1];
				Map map = new HashMap();
				for(int i=0;i<2;i++){
					propertyIndex++;
					map.put(generateTestData(keyType,propertyIndex,sameClassNestLevel,pd.getName()),generateTestData(valueType,propertyIndex,sameClassNestLevel,pd.getName()));
				}
				value = map;
			}else if(int.class.equals(pt) || Integer.class.equals(pt)){
				value = propertyIndex;
			}else if(long.class.equals(pt) || Long.class.equals(pt)){
				value = Long.valueOf(propertyIndex).longValue();
			}else if(double.class.equals(pt) || Double.class.equals(pt)){
				value = Double.valueOf(propertyIndex).doubleValue();
			}else if(float.class.equals(pt) || Float.class.equals(pt)){
				value = Float.valueOf(propertyIndex).floatValue();
			}else if(boolean.class.equals(pt) || Boolean.class.equals(pt)){
				value = Math.random()%2==0; 
			}else if(Date.class.equals(pt)){
				value = new Date();
			}else if(String.class.equals(pt)){
				value = getStringValue(pd.getName(),parentPropertyName, propertyIndex);
			}else{
				value = generateTestData(pt,propertyIndex,sameClassNestLevel,pd.getName());
			}
			pd.getWriteMethod().invoke(instance, value);
		}
		return instance;
	}
	
	private boolean ignoreProperty(PropertyDescriptor pd) {
		String name = pd.getName();
		if("class".equals(name)){
			return true;
		}
		if("metaInfo".equals(name)){
			return true;
		}
		
		return false;
	}

	/**
	 * Used to return special strings based on the current field name and parent field name
	 * @param name
	 * @param parentPropertyName
	 * @param propertyIndex
	 * @return String value of the element
	 */
	private String getStringValue(String name, String parentPropertyName,
			Integer propertyIndex) {
		if("id".equals(name)){
			return null;
		}
		if("type".equals(name)){
			
			if(null==parentPropertyName){
				return ProgramAssemblerConstants.MAJOR_DISCIPLINE;
			}
			
			if("loInfo".equals(parentPropertyName)){
				return "kuali.lo.type.singleUse";
			}
			
			if("loCategoryInfoList".equals(parentPropertyName)){
				return "loCategoryType.skillarea";
			}
			
			if("variations".equals(parentPropertyName)){
				return ProgramAssemblerConstants.PROGRAM_VARIATION;
			}
			
			throw new RuntimeException("Code what to do with this type");
		}

		if("state".equals(name)){
			return ProgramAssemblerConstants.DRAFT;
		}

		if("campusLocations".equals(parentPropertyName)){
			return campusLocations[propertyIndex%2];
		}
		//Default
		return name+"-"+propertyIndex;
	}

	public static void main(String[] args) throws IntrospectionException, InstantiationException, IllegalAccessException, IllegalArgumentException, SecurityException, InvocationTargetException, NoSuchFieldException{
		MajorDisciplineDataGenerator generator = new MajorDisciplineDataGenerator();
		generator.getMajorDisciplineInfoTestData();
	}
}
