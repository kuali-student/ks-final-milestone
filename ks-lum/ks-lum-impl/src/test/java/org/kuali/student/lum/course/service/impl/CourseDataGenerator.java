package org.kuali.student.lum.course.service.impl;

import org.apache.commons.beanutils.BeanUtils;
import org.kuali.student.r2.lum.course.service.assembler.CourseAssemblerConstants;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.DtoConstants;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.infc.RichText;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * Use this class to generate test data for a course (it may need improvements for creating real relationships for more
 * complex data elements)
 */
public class CourseDataGenerator {
    private static final String[] campusLocations = {CourseAssemblerConstants.COURSE_CAMPUS_LOCATION_CD_NORTH, CourseAssemblerConstants.COURSE_CAMPUS_LOCATION_CD_SOUTH};
    String activities[] = {CourseAssemblerConstants.COURSE_ACTIVITY_LAB_TYPE, CourseAssemblerConstants.COURSE_ACTIVITY_DISCUSSION_TYPE, CourseAssemblerConstants.COURSE_ACTIVITY_TUTORIAL_TYPE,
            CourseAssemblerConstants.COURSE_ACTIVITY_LECTURE_TYPE, CourseAssemblerConstants.COURSE_ACTIVITY_WEBLECTURE_TYPE,
            /*
              * \
              * *
              * CourseAssemblerConstants
              * .
              * COURSE_ACTIVITY_WEBDISCUSS_TYPE
              * ,
              */// not
                // in
            // DB
            CourseAssemblerConstants.COURSE_ACTIVITY_DIRECTED_TYPE};
    public static String subjectAreas[] = {"GEOG", "COMP", "BIOL", "ENGL", "SOCY"};
    public static String loCategories[] = {"category-1", "category-2"};
    public static String loDisplayInfo[] = {"displayInfo-1", "displayInfo-2"};
    public static String fees[] = {"fees-1", "fees-2"};
    public static String courseSpecificLOs[];
    public static String revenues[] = {"revenues-1", "revenues-2"};
    public static String affiliatedOrgs[] = {"affiliatedOrgs-1", "affiliatedOrgs-2"};
    
    Random generator = new Random();

    public CourseInfo getCourseTestData() throws IntrospectionException, InstantiationException, IllegalAccessException, IllegalArgumentException, SecurityException, InvocationTargetException, NoSuchFieldException {
        CourseInfo testData = generateTestData(CourseInfo.class, 0, 0, null);

        testData.getAttributes().add(new AttributeInfo("proposalTitle", "proposalTitle-1"));
        testData.getAttributes().add(new AttributeInfo("proposalRationale", "proposalRationale"));         
         
        testData.getCreditOptions().get(0).getResultValueKeys().set(0, "1");
        testData.getCreditOptions().get(0).getResultValueKeys().set(1, "2");
        testData.getCreditOptions().get(1).getResultValueKeys().set(0, "3");
        testData.getCreditOptions().get(1).getResultValueKeys().set(1, "4");
        for (ResultValuesGroupInfo resultComponent : testData.getCreditOptions()) {
            resultComponent.getResultValueRange().setMinValue("2");
            resultComponent.getResultValueRange().setMaxValue("5");
        }
        return testData;
    }

    private <T> T generateTestData(Class<T> clazz, Integer propertyIndex, int sameClassNestLevel, String parentPropertyName) throws IntrospectionException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SecurityException, NoSuchFieldException {
        if (sameClassNestLevel > 2) {
            return null;
        }

        T instance = clazz.newInstance();

        if (String.class.equals(clazz)) {
            propertyIndex++;
            instance = (T) (getStringValue(parentPropertyName, parentPropertyName, propertyIndex));
            return instance;
        }

        BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
        ArrayList<Field> fields = new ArrayList<Field>();
        fields = getAllFields(fields, clazz);
        ArrayList<Method> methods= new ArrayList<Method>();
        methods = getAllMethods(methods, clazz);
        
        for (PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
            if (ignoreProperty(pd)) {
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
            if (List.class.equals(pt)) {
                // If this is a list then make a new list and make x amount of test data of that list type
                // Get the list type:
                Class<?> nestedClass = (Class<?>) ((ParameterizedType) declaredField.getGenericType()).getActualTypeArguments()[0];
                List list = new ArrayList();
                for (int i = 0; i < 2; i++) {
                    propertyIndex++;
                    Object listItem = null;
                    if (nestedClass.equals(pt) || nestedClass.equals(clazz)) {
                        listItem = generateTestData(nestedClass, propertyIndex, sameClassNestLevel + 1, pd.getName());
                    } else {
                        listItem = generateTestData(nestedClass, propertyIndex, sameClassNestLevel, pd.getName());
                    }
                    if (listItem != null) {
                        list.add(listItem);
                    }
                }
                value = list;
            } else if (Map.class.equals(pt)) {
                Class<?> keyType = (Class<?>) ((ParameterizedType) declaredField.getGenericType()).getActualTypeArguments()[0];
                Class<?> valueType = (Class<?>) ((ParameterizedType) declaredField.getGenericType()).getActualTypeArguments()[1];
                Map map = new HashMap();
                for (int i = 0; i < 2; i++) {
                    propertyIndex++;
                    map.put(generateTestData(keyType, propertyIndex, sameClassNestLevel, pd.getName()), generateTestData(valueType, propertyIndex, sameClassNestLevel, pd.getName()));
                }
                value = map;
            } else if (int.class.equals(pt) || Integer.class.equals(pt)) {
                value = propertyIndex;
            } else if (long.class.equals(pt) || Long.class.equals(pt)) {
                value = Long.valueOf(propertyIndex).longValue();
            } else if (double.class.equals(pt) || Double.class.equals(pt)) {
                value = Double.valueOf(propertyIndex).doubleValue();
            } else if (float.class.equals(pt) || Float.class.equals(pt)) {
                value = Float.valueOf(propertyIndex).floatValue();
            } else if (boolean.class.equals(pt) || Boolean.class.equals(pt)) {
                value = Math.random() % 2 == 0;
            } else if (Date.class.equals(pt)) {
                value = new Date();
            } else if (String.class.equals(pt)) {
                value = getStringValue(pd.getName(), parentPropertyName, propertyIndex);
            } else {
                value = generateTestData(pt, propertyIndex, sameClassNestLevel, pd.getName());
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
        if ("class".equals(name)) {
            return true;
        }
        if ("metaInfo".equals(name)) {
            return true;
        }
        if ("joints".equals(name) || "crossListings".equals(name)) {
            return true;
        }
        return false;
    }

    /**
     * Used to return special strings based on the current field name and parent field name
     * 
     * @param name
     * @param parentPropertyName
     * @param propertyIndex
     * @return String value of the element
     */
    private String getStringValue(String name, String parentPropertyName, Integer propertyIndex) {
        if ("id".equals(name)) {
            if ("loCategoryInfoList".equals(parentPropertyName)) {
                return loCategories[propertyIndex % loCategories.length];
            }
            if ("loDisplayInfoList".equals(parentPropertyName)) {
                return loDisplayInfo[propertyIndex % loCategories.length];
            }
            if ("courseSpecificLOs".equals(parentPropertyName)) {
                return null;
            }
            if ("affiliatedOrgs".equals(parentPropertyName)) {
                return null;
            }
            if ("fees".equals(parentPropertyName)) {
                return fees[propertyIndex % fees.length];
            }
            if ("revenues".equals(parentPropertyName)) {
                return revenues[propertyIndex % revenues.length];
            }
            return null;
        }
        if ("typeKey".equals(name)) {
            if ("formats".equals(parentPropertyName)) {
                return CourseAssemblerConstants.COURSE_FORMAT_TYPE;
            }
            if ("activities".equals(parentPropertyName)) {
                return activities[generator.nextInt(activities.length)];
            }
            if (null == parentPropertyName) {
                return "kuali.lu.type.CreditCourse";
            }
            if ("loCategoryInfoList".equals(parentPropertyName)) {
                return "loCategoryType.skillarea";
            }
            if ("loInfo".equals(parentPropertyName)) {
                return "kuali.lo.type.singleUse";
            }
            if ("crossListings".equals(parentPropertyName)) {
                return "kuali.lu.type.CreditCourse.identifier.cross-listed";
            }
            if ("joints".equals(parentPropertyName)) {
                return "kuali.lu.relation.type.co-located";
            }
            if ("variations".equals(parentPropertyName)) {
                return "kuali.lu.type.CreditCourse.identifier.variation";
            }
            if ("unitsDeployment".equals(parentPropertyName)) {
                return "kuali.adminOrg.type.Administration";
            }
            if ("unitsContentOwner".equals(parentPropertyName)) {
                return "kuali.adminOrg.type.unitsContentOwner";
            }
            if ("creditOptions".equals(parentPropertyName)) {
                return CourseAssemblerConstants.COURSE_RESULT_COMP_TYPE_CREDIT_FIXED;
            }
            if ("loDisplayInfoList".equals(parentPropertyName)) {
                return null;
            }
            if ("courseSpecificLOs".equals(parentPropertyName)) {
                return null;
            }
            if ("affiliatedOrgs".equals(parentPropertyName)) {
                return  null;
            }
            if ("fees".equals(parentPropertyName)) {
                return  "loCategoryType.skillarea";
            }
            if ("revenues".equals(parentPropertyName)) {
                return null;
            }
            throw new RuntimeException("Code what to do with this type. Parent:" + parentPropertyName);
        }
        if ("resultValueIds".equals(name)) {
            return String.valueOf(propertyIndex);
        }
        if ("activityType".equals(name)) {
            return activities[generator.nextInt(activities.length)];
        }
        if ("stateKey".equals(name)) {
            return DtoConstants.STATE_DRAFT;
        }
        if ("contactHours".equals(parentPropertyName) && "unitTypeKey".equals(name)) {
            return "kuali.atp.duration.day";
        }
        if ("contactHours".equals(parentPropertyName) && "unitQuantity".equals(name)) {
            return propertyIndex.toString();
        }
        if ("duration".equals(parentPropertyName) && "atpDurationTypeKey".equals(name)) {
            return "kuali.atp.duration.Semester";
        }
        if ("duration".equals(parentPropertyName) && "timeQuantity".equals(name)) {
            return propertyIndex.toString();
        }
        if ("outOfClassHours".equals(parentPropertyName) && "unitTypeKey".equals(name)) {
            return "kuali.atp.duration.Week";
        }
        if ("outOfClassHours".equals(parentPropertyName) && "unitQuantity".equals(name)) {
            return propertyIndex.toString();
        }
        if ("courseNumberSuffix".equals(name)) {
            return "323";
        }
        if ("subjectArea".equals(name)) {
            return subjectAreas[generator.nextInt(subjectAreas.length)];
        }
        if ("campusLocations".equals(parentPropertyName)) {
            return campusLocations[propertyIndex % 2];
        }
        if ("code".equals(name)) {
            return "code-10";
        }
        // TODO: make it return A, B, C...
        if ("variationCode".equals(name)) {
            return "A";
        }
        if ("startTerm".equals(name)) {
            return "atp.2009FallSemester";
        }
        if ("endTerm".equals(name)) {
            return "atp.2009FallSemester";
        }
        // Default
        return name + "-" + propertyIndex;
    }

    public static void main(String[] args) throws IntrospectionException, InstantiationException, IllegalAccessException, IllegalArgumentException, SecurityException, InvocationTargetException, NoSuchFieldException {
        CourseDataGenerator generator = new CourseDataGenerator();
        generator.getCourseTestData();
    }
    public static ArrayList<Field> getAllFields(ArrayList<Field> fields, Class<?> type) {
        for (Field field: type.getDeclaredFields()) {
            fields.add(field);
        }

        if (type.getSuperclass() != null) {
            fields = getAllFields(fields, type.getSuperclass());
        }

        return fields;
    }
    
    public static ArrayList<Method> getAllMethods(ArrayList<Method> methods, Class<?> type) {
        for (Method method: type.getMethods()) {
            methods.add(method);
        }

        if (type.getSuperclass() != null) {
            methods = getAllMethods(methods, type.getSuperclass());
        }

        return methods;
    }
    public static Field findField(String fieldName, ArrayList<Field> fields) {
        for (Field field : fields) {
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }
        return null;
    }
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
