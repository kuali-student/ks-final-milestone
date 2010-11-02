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
import org.kuali.student.lum.program.service.assembler.ProgramAssemblerConstants;

public class ProgramDataGeneratorUtils {
	Random generator = new Random(); //TODO: maybe need it later
	private static final String[] campusLocations = {CourseAssemblerConstants.COURSE_CAMPUS_LOCATION_CD_NORTH,CourseAssemblerConstants.COURSE_CAMPUS_LOCATION_CD_SOUTH};
	public static <T> T generateTestData(Class<T> clazz, String programType, Integer propertyIndex, int sameClassNestLevel, String parentPropertyName, boolean isMap) throws IntrospectionException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SecurityException, NoSuchFieldException {
		
		if(sameClassNestLevel>2){
			return null;
		}
		
		T instance = clazz.newInstance();

		if(String.class.equals(clazz)){
			propertyIndex++;
			instance = (T) (getStringValue(parentPropertyName,programType, parentPropertyName,propertyIndex, isMap));
			return instance;
		}
		
		BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
		for(PropertyDescriptor pd:beanInfo.getPropertyDescriptors()){

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
						listItem=generateTestData(nestedClass,programType, propertyIndex,sameClassNestLevel+1,pd.getName(),false);
					}else{
						listItem=generateTestData(nestedClass,programType, propertyIndex,sameClassNestLevel,pd.getName(), false);
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
					map.put(generateTestData(keyType,programType, i,sameClassNestLevel,pd.getName(), true),generateTestData(valueType,programType, i,sameClassNestLevel,pd.getName(), true));
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
				value = getStringValue(pd.getName(), programType, parentPropertyName, propertyIndex, false);
			}else{
				value = generateTestData(pt,programType, propertyIndex,sameClassNestLevel,pd.getName(), false);
			}
			pd.getWriteMethod().invoke(instance, value);
		}
		return instance;
	}
	
	private static boolean ignoreProperty(PropertyDescriptor pd) {
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
	private static String getStringValue(String name, String programType, String parentPropertyName,
			Integer propertyIndex, boolean isMap) {
		
		if("id".equals(name)){
            if("loCategoryInfoList".equals(parentPropertyName)){
                return "162979a3-25b9-4921-bc8f-c861b2267a73";
            }
            else {
			   return null;
            }
		}

        if("programRequirements".equals(name)){
        	return "REQ-200";
        }

        if("catalogPublicationTargets".equals(name)){
			return ProgramAssemblerConstants.CATALOG;
		}
        
		if("type".equals(name)){
			
			if(null==parentPropertyName){
				return programType;
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

			if("divisionsContentOwner".equals(parentPropertyName)){
				return ProgramAssemblerConstants.CONTENT_OWNER_DIVISION;
			}
			
			if("divisionsStudentOversight".equals(parentPropertyName)){
				return ProgramAssemblerConstants.STUDENT_OVERSIGHT_DIVISION;
			}
			
			if("unitsContentOwner".equals(parentPropertyName)){
				return ProgramAssemblerConstants.CONTENT_OWNER_UNIT;
			}
			
			if("unitsStudentOversight".equals(parentPropertyName)){
				return ProgramAssemblerConstants.STUDENT_OVERSIGHT_UNIT;
			}
			
			else{
				return "default.temp.type";
			}
		}

		if("state".equals(name)){
			return ProgramAssemblerConstants.DRAFT;
		}

		if("credentialProgramType".equals(name)){
			return programType;
		}

		if("campusLocations".equals(parentPropertyName)){
			return campusLocations[propertyIndex%2];
		}
		
		//Default
		if(isMap)
			return name+"-"+propertyIndex;
		else
			return name+"-"+"test";
	}

}
