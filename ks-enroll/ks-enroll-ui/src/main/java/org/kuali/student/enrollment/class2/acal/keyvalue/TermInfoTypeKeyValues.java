package org.kuali.student.enrollment.class2.acal.keyvalue;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.dto.TypeInfo;

import javax.xml.namespace.QName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//Core slice class.
@Deprecated
public class TermInfoTypeKeyValues extends KeyValuesBase implements Serializable{
	public static final String FALL_TERM_TYPE_KEY = "kuali.atp.type.Fall";
	public static final String WINTER_TERM_TYPE_KEY = "kuali.atp.type.Winter";
	public static final String SPRING_TERM_TYPE_KEY = "kuali.atp.type.Spring";
	public static final String SUMMER_TERM_TYPE_KEY = "kuali.atp.type.Summer";
	
	private static final long serialVersionUID = 1L;
	
	private transient AcademicCalendarService academicCalendarService;

    private static List<TypeInfo> termTypes;

    public List getKeyValues() {
        List <ConcreteKeyValue> keyValues = new ArrayList<ConcreteKeyValue>();

        //pull out data from KSEN_TYPETYPE_RELTN
        try {
        	List<TypeInfo> typeInfoList = getTermTypes();
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
            throw new RuntimeException(ipe);
        }catch (MissingParameterException mpe){
            throw new RuntimeException(mpe);
        }catch (OperationFailedException ofe){
            throw new RuntimeException(ofe);
        }catch (PermissionDeniedException pde){
            throw new RuntimeException(pde);
        }

        return keyValues;
    }

    private List<TypeInfo> getTermTypes() throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {
        if(termTypes == null) {
            termTypes = Collections.unmodifiableList(getAcademicCalendarService().getTermTypes(new ContextInfo()));
        }

        return termTypes;
    }

    protected AcademicCalendarService getAcademicCalendarService() {
        if(academicCalendarService == null) {
       	    academicCalendarService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/acal","AcademicCalendarService"));
        }

        return academicCalendarService;
    }
}
