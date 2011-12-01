package org.kuali.student.enrollment.class2.acal.keyvalue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;

import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;

public class TermInfoTypeKeyValues extends KeyValuesBase implements Serializable{
	public static final String FALL_TERM_TYPE_KEY = "kuali.atp.type.Fall";
	public static final String WINTER_TERM_TYPE_KEY = "kuali.atp.type.Winter";
	public static final String SPRING_TERM_TYPE_KEY = "kuali.atp.type.Spring";
	public static final String SUMMER_TERM_TYPE_KEY = "kuali.atp.type.Summer";
	
	private static final long serialVersionUID = 1L;
	
	private transient AcademicCalendarService academicCalendarService;
	
    public List getKeyValues() {
        List <ConcreteKeyValue> keyValues = new ArrayList<ConcreteKeyValue>();

//        keyValues.add(new ConcreteKeyValue("kuali.atp.type.Fall", "Fall" ));
//        keyValues.add(new ConcreteKeyValue("kuali.atp.type.Winter", "Winter"));
//        keyValues.add(new ConcreteKeyValue("kuali.atp.type.Spring", "Spring"));
//        keyValues.add(new ConcreteKeyValue("kuali.atp.type.Summer", "Summer"));
/*
        try {
        	List<TypeInfo> typeInfoList = getAcademicCalendarService().getTermTypesForAcademicCalendarType(AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY, ContextInfo.newInstance());
        	for (TypeInfo typeInfo : typeInfoList){
        		String typeKey = typeInfo.getKey();
        		if (typeKey.equalsIgnoreCase(FALL_TERM_TYPE_KEY)){
        			keyValues.add(new ConcreteKeyValue(typeKey, typeInfo.getName()));
        		}
        		else if(typeKey.equalsIgnoreCase(WINTER_TERM_TYPE_KEY)){
        			keyValues.add(new ConcreteKeyValue(typeKey, typeInfo.getName()));
        		}
        		else if (typeKey.equalsIgnoreCase(SPRING_TERM_TYPE_KEY)){
        			keyValues.add(new ConcreteKeyValue(typeKey, typeInfo.getName()));
        		}
        		else if (typeKey.equalsIgnoreCase(SUMMER_TERM_TYPE_KEY)){
        			keyValues.add(new ConcreteKeyValue(typeKey, typeInfo.getName()));
        		}
        	}
            
        }catch (InvalidParameterException ipe){
            
        }catch (MissingParameterException mpe){
            
        }catch (OperationFailedException ofe){
            
        }catch (DoesNotExistException dee){
        	
        }  
*/      
        //pull out data from KSEN_TYPETYPE_RELTN 
        try {
        	List<TypeInfo> typeInfoList = getAcademicCalendarService().getTermTypes(ContextInfo.newInstance());
        	for (TypeInfo typeInfo : typeInfoList){
        		String typeKey = typeInfo.getKey();
        		if (typeKey.equalsIgnoreCase(FALL_TERM_TYPE_KEY)){
        			keyValues.add(new ConcreteKeyValue(typeKey, typeInfo.getName()));
        		}
        		else if(typeKey.equalsIgnoreCase(WINTER_TERM_TYPE_KEY)){
        			keyValues.add(new ConcreteKeyValue(typeKey, typeInfo.getName()));
        		}
        		else if (typeKey.equalsIgnoreCase(SPRING_TERM_TYPE_KEY)){
        			keyValues.add(new ConcreteKeyValue(typeKey, typeInfo.getName()));
        		}
        		else if (typeKey.equalsIgnoreCase(SUMMER_TERM_TYPE_KEY)){
         			keyValues.add(new ConcreteKeyValue(typeKey, typeInfo.getName()));
        		}
        	}
            
        }catch (InvalidParameterException ipe){
            
        }catch (MissingParameterException mpe){
            
        }catch (OperationFailedException ofe){
            
        }
        return keyValues;
    }
    
    protected AcademicCalendarService getAcademicCalendarService() {
        if(academicCalendarService == null) {
       	 academicCalendarService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/acal","AcademicCalendarService"));
       }

       return academicCalendarService;
   }
}
