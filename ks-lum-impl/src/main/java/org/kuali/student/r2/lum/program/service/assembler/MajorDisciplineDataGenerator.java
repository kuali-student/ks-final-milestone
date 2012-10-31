package org.kuali.student.r2.lum.program.service.assembler;

import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.DtoConstants;
import org.kuali.student.r2.lum.course.service.assembler.CourseAssemblerConstants;
import org.kuali.student.r2.lum.course.service.assembler.CourseAssemblerConstants;
import org.kuali.student.r2.lum.program.dto.MajorDisciplineInfo;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MajorDisciplineDataGenerator {
	private static final String[] campusLocations = {CourseAssemblerConstants.COURSE_CAMPUS_LOCATION_CD_NORTH,CourseAssemblerConstants.COURSE_CAMPUS_LOCATION_CD_SOUTH};
	Random generator = new Random();
    
	public MajorDisciplineInfo getMajorDisciplineInfoTestData() throws IntrospectionException, InstantiationException, IllegalAccessException, IllegalArgumentException, SecurityException, InvocationTargetException, NoSuchFieldException{
		MajorDisciplineInfo testData = generateTestData(MajorDisciplineInfo.class, 0, 0,null, false);
		return testData;
	}

	private <T> T generateTestData(Class<T> clazz, Integer propertyIndex, int sameClassNestLevel, String parentPropertyName, boolean isMap) throws IntrospectionException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SecurityException, NoSuchFieldException {
		if(sameClassNestLevel>2){
			return null;
		}
		
		T instance = clazz.newInstance();

		if(String.class.equals(clazz)){
			propertyIndex++;
			instance = (T) (getStringValue(parentPropertyName,parentPropertyName,propertyIndex, isMap, false));
			return instance;
		}
		
		BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
		
		List<Field> fields = new ArrayList<Field>();
        fields = getAllFields(fields, clazz);
        List<Method> methods= new ArrayList<Method>();
        methods = getAllMethods(methods, clazz);
		
		for(PropertyDescriptor pd:beanInfo.getPropertyDescriptors()){

			if(ignoreProperty(pd)){
				continue;
			}
			propertyIndex++;
			Object value = null;
			Class<?> pt = pd.getPropertyType();
			
			Field declaredField = findField(pd.getName(), fields);
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
						listItem=generateTestData(nestedClass,propertyIndex,sameClassNestLevel+1,pd.getName(),false);
					}else{
						listItem=generateTestData(nestedClass,propertyIndex,sameClassNestLevel,pd.getName(), false);
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
					map.put(generateTestData(keyType,i,sameClassNestLevel,pd.getName(), true),generateTestData(valueType,i,sameClassNestLevel,pd.getName(), true));
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
				value = getStringValue(pd.getName(),parentPropertyName, propertyIndex, false, AttributeInfo.class.equals(clazz));
			}else{
//                System.out.println("Property:" + pd.getDisplayName() + " :" + clazz.getName());
			    value = generateTestData(pt,propertyIndex,sameClassNestLevel,pd.getName(), false);

			}
			Method writeMethod = pd.getWriteMethod();
            
            if (writeMethod == null) {
                writeMethod = findSetMethod(pd.getName(), methods);
            }
            writeMethod.invoke(instance, value);
		}
		return instance;
	}
	
	private boolean ignoreProperty(PropertyDescriptor pd) {
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
	 * @param isMap
	 * @param isAttribute
	 * @return String value of the element
	 */
	private String getStringValue(String name, String parentPropertyName,
			Integer propertyIndex, boolean isMap, boolean isAttribute) {
		if("id".equals(name)){
            if("loCategoryInfoList".equals(parentPropertyName)){
                return "162979a3-25b9-4921-bc8f-c861b2267a73";
            }
            else {
			   return null;
            }
		}
        if("loRepository".equals(name)){
            return "kuali.loRepository.key.singleUse";
        }
        if("credentialProgramId".equals(name)){
            return "00f5f8c5-fff1-4c8b-92fc-789b891e0849";
        }
        if("programRequirements".equals(name)){
        	return "REQ-200";
        }
        if("catalogPublicationTargets".equals(name)){
			return ProgramAssemblerConstants.CATALOG;
		}
		if("typeKey".equals(name)){
			
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

			if("orgCoreProgram".equals(parentPropertyName)){
				return ProgramAssemblerConstants.CORE_PROGRAM;
			}

			if("divisionsContentOwner".equals(parentPropertyName)){
				return ProgramAssemblerConstants.CURRICULUM_OVERSIGHT_DIVISION;
			}
			if("divisionsStudentOversight".equals(parentPropertyName)){
				return ProgramAssemblerConstants.STUDENT_OVERSIGHT_DIVISION;
			}
            if("divisionsDeployment".equals(parentPropertyName)){
				return ProgramAssemblerConstants.DEPLOYMENT_DIVISION;
			}
			if("divisionsFinancialResources".equals(parentPropertyName)){
				return ProgramAssemblerConstants.FINANCIAL_RESOURCES_DIVISION;
			}
			if("divisionsFinancialControl".equals(parentPropertyName)){
				return ProgramAssemblerConstants.FINANCIAL_CONTROL_DIVISION;
			}
			if("unitsContentOwner".equals(parentPropertyName)){
				return ProgramAssemblerConstants.CURRICULUM_OVERSIGHT_UNIT;
			}
			if("unitsStudentOversight".equals(parentPropertyName)){
				return ProgramAssemblerConstants.STUDENT_OVERSIGHT_UNIT;
			}
			if("unitsDeployment".equals(parentPropertyName)){
				return ProgramAssemblerConstants.DEPLOYMENT_UNIT;
			}
			if("unitsFinancialResources".equals(parentPropertyName)){
				return ProgramAssemblerConstants.FINANCIAL_RESOURCES_UNIT;
			}
            if("unitsFinancialControl".equals(parentPropertyName)){
				return ProgramAssemblerConstants.FINANCIAL_CONTROL_UNIT;
            }

			//Temporary change added because of addition of type field to AdminOrgInfo.
			// Please add the type values for MajorDisciplineInfo admin orgs
			else{
				return "default.temp.type";
			}
			
//			throw new RuntimeException("Code what to do with this type");
		}

		if("stateKey".equals(name)){
			return DtoConstants.STATE_DRAFT;
		}

		if("campusLocations".equals(parentPropertyName)){
			return campusLocations[propertyIndex%2];
		}
		//Default
		if(isMap || isAttribute)
			return name+"-"+propertyIndex;
		else
			return name+"-"+"test";
	}
	
	// KSCM-621
    public static List<Field> getAllFields(List<Field> fields, Class<?> type) {
        for (Field field: type.getDeclaredFields()) {
            fields.add(field);
        }

        if (type.getSuperclass() != null) {
            fields = getAllFields(fields, type.getSuperclass());
        }

        return fields;
    }
    
    // KSCM-621
    public static List<Method> getAllMethods(List<Method> methods, Class<?> type) {
        for (Method method: type.getMethods()) {
            methods.add(method);
        }

        if (type.getSuperclass() != null) {
            methods = getAllMethods(methods, type.getSuperclass());
        }

        return methods;
    }
    
    // KSCM-621
    public static Field findField(String fieldName, List<Field> fields) {
        for (Field field : fields) {
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }
        return null;
    }
    
    // KSCM-621
    public static Method findSetMethod(String fieldName, List<Method> methods) {
        fieldName = ("set" + fieldName);
        for (Method method : methods) {
            if (method.getName().compareToIgnoreCase(fieldName) == 0) {
                return method;
            }
        }
        return null;
    }

	public static void main(String[] args) throws IntrospectionException, InstantiationException, IllegalAccessException, IllegalArgumentException, SecurityException, InvocationTargetException, NoSuchFieldException{
		MajorDisciplineDataGenerator generator = new MajorDisciplineDataGenerator();
		generator.getMajorDisciplineInfoTestData();
	}
}
