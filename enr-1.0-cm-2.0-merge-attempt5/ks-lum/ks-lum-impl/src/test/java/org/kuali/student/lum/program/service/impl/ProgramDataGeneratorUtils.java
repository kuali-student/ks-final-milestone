package org.kuali.student.lum.program.service.impl;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.kuali.student.r2.common.dto.DtoConstants;
import org.kuali.student.r2.lum.course.service.assembler.CourseAssemblerConstants;
import org.kuali.student.r2.lum.program.service.assembler.ProgramAssemblerConstants;

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
		
		// KSCM-621 Get all the fields including inherited fields...
		ArrayList<Field> fields = new ArrayList<Field>();
	    fields = getAllFields(fields, clazz);
	    ArrayList<Method> methods= new ArrayList<Method>();
	    methods = getAllMethods(methods, clazz);
	    
	    
		for(PropertyDescriptor pd:beanInfo.getPropertyDescriptors()){

			if(ignoreProperty(pd)){
				continue;
			}
			propertyIndex++;

			Object value = null;
			Class<?> pt = pd.getPropertyType();	// KSCM-621
			Field declaredField = findField(pd.getName(), fields);	 // KSCM-621
			
			// We're not interested in the Interface, List, Map but in the actual class
			if (pt.isInterface() && !List.class.equals(pt) && !Map.class.equals(pt)) {
				pt = declaredField.getType();
			} 
			
			if(List.class.equals(pt)){
				//If this is a list then make a new list and make x amount of test data of that list type
				//Get the list type:
				Class<?> nestedClass = (Class<?>) ((ParameterizedType) declaredField.getGenericType()).getActualTypeArguments()[0];			
				
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
				Class<?> keyType = (Class<?>) ((ParameterizedType) declaredField.getGenericType()).getActualTypeArguments()[0];
				Class<?> valueType = (Class<?>) ((ParameterizedType) declaredField.getGenericType()).getActualTypeArguments()[1];
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
			Method writeMethod = pd.getWriteMethod();
			
			if (writeMethod == null) {
				writeMethod = findSetMethod(pd.getName(), methods);
			}
			writeMethod.invoke(instance, value);
		}
		return instance;
	}
	
	private static boolean ignoreProperty(PropertyDescriptor pd) {
		String name = pd.getName();
		if("class".equals(name)){
			return true;
		}
		if("meta".equals(name)){
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
        
		if("typeKey".equals(name)){
			
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
				return ProgramAssemblerConstants.CURRICULUM_OVERSIGHT_DIVISION;
			}
			
			if("divisionsStudentOversight".equals(parentPropertyName)){
				return ProgramAssemblerConstants.STUDENT_OVERSIGHT_DIVISION;
			}
			
			if("unitsContentOwner".equals(parentPropertyName)){
				return ProgramAssemblerConstants.CURRICULUM_OVERSIGHT_UNIT;
			}
			
			if("unitsStudentOversight".equals(parentPropertyName)){
				return ProgramAssemblerConstants.STUDENT_OVERSIGHT_UNIT;
			}
			
			else{
				return "default.temp.type";
			}
		}

		if("stateKey".equals(name)){
			return DtoConstants.STATE_DRAFT;
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

	// KSCM-621
	public static ArrayList<Field> getAllFields(ArrayList<Field> fields, Class<?> type) {
	    for (Field field: type.getDeclaredFields()) {
	        fields.add(field);
	    }

	    if (type.getSuperclass() != null) {
	        fields = getAllFields(fields, type.getSuperclass());
	    }

	    return fields;
	}
	
	// KSCM-621
	public static ArrayList<Method> getAllMethods(ArrayList<Method> methods, Class<?> type) {
	    for (Method method: type.getMethods()) {
	        methods.add(method);
	    }

	    if (type.getSuperclass() != null) {
	        methods = getAllMethods(methods, type.getSuperclass());
	    }

	    return methods;
	}
	// KSCM-621
	public static Field findField(String fieldName, ArrayList<Field> fields) {
		for (Field field : fields) {
			if (field.getName().equals(fieldName)) {
				return field;
			}
		}
		return null;
	}
	// KSCM-621
	public static Method findSetMethod(String fieldName, ArrayList<Method> methods) {
		fieldName = ("set" + fieldName);
		for (Method method : methods) {
			if (method.getName().compareToIgnoreCase(fieldName) == 0) {
				return method;
			}
		}
		return null;
	}
}
