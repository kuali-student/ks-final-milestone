package org.kuali.student.enrollment.class2.acal.service;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.KRADUtils;
import org.kuali.rice.krad.util.UrlFactory;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.acal.dto.TermWrapper;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;


/**
 * Created by IntelliJ IDEA.
 * User: huangb
 */
//Core slice class.
@Deprecated
public class TermLookupableImpl extends LookupableImpl  {
    public final static String TERM_KEY = "key";
 	private transient AcademicCalendarService academicCalendarService;


    @Override
    protected List<?> getSearchResults(LookupForm lookupForm, Map<String, String> fieldValues, boolean unbounded) {
    	TermInfo termInfo = null;
    	List<TermInfo> termInfoList = new ArrayList<TermInfo>();

    	String termKey = fieldValues.get(TERM_KEY);
    	ContextInfo context = new ContextInfo();
    	try{
    		termInfo = getAcademicCalendarService().getTerm(termKey, context);
    		termInfoList.add(termInfo);
    		return termInfoList;
    	}catch (DoesNotExistException dnee){
            System.out.println("call getAcademicCalendarService().getKeyDatesForTerm(termKey, context), and get DoesNotExistException:  "+dnee.toString());
    	}catch (InvalidParameterException ipe){
            System.out.println("call getAcademicCalendarService().getKeyDatesForTerm(termKey, context), and get InvalidParameterException:  "+ipe.toString());
    	}catch (MissingParameterException mpe){
            System.out.println("call getAcademicCalendarService().getKeyDatesForTerm(termKey, context), and get MissingParameterException:  "+mpe.toString());
    	}catch (OperationFailedException ofe){
            System.out.println("call getAcademicCalendarService().getKeyDatesForTerm(termKey, context), and get OperationFailedException:  "+ofe.toString());
    	}catch (PermissionDeniedException pde){
            System.out.println("call getAcademicCalendarService().getKeyDatesForTerm(termKey, context), and get PermissionDeniedException:  "+pde.toString());
    	}
    	return null;

    }

    @Override
    protected String getActionUrlHref(LookupForm lookupForm, Object dataObject, String methodToCall, List<String> pkNames) {

       Properties props = new Properties();
       props.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, methodToCall);

       Map<String, String> primaryKeyValues = KRADUtils.getPropertyKeyValuesFromDataObject(pkNames, dataObject);
       for (String primaryKey : primaryKeyValues.keySet()) {
           String primaryKeyValue = primaryKeyValues.get(primaryKey);
           props.put(primaryKey, primaryKeyValue);
           props.put(KRADConstants.OVERRIDE_KEYS, primaryKey);
       }

       if (StringUtils.isNotBlank(lookupForm.getReturnLocation())) {
           props.put(KRADConstants.RETURN_LOCATION_PARAMETER, lookupForm.getReturnLocation());
       }

       props.put(UifParameters.DATA_OBJECT_CLASS_NAME, TermWrapper.class.getName());
       props.put(UifParameters.VIEW_TYPE_NAME, UifConstants.ViewType.MAINTENANCE);

       return UrlFactory.parameterizeUrl(KRADConstants.Maintenance.REQUEST_MAPPING_MAINTENANCE, props);

    }

    protected AcademicCalendarService getAcademicCalendarService() {
        if(academicCalendarService == null) {
        	 academicCalendarService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/acal", "AcademicCalendarService"));
        }
        return academicCalendarService;
    }




}
