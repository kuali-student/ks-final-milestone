package org.kuali.student.enumeration.web.server.gwt;

import java.util.Date;

import org.kuali.student.enumeration.dto.EnumeratedValue;
import org.kuali.student.enumeration.dto.EnumeratedValueList;
import org.kuali.student.enumeration.dto.EnumerationMetaList;
import org.kuali.student.enumeration.service.EnumerationService;
import org.kuali.student.enumeration.web.client.service.EnumerationGWTService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class EnumerationGWTServiceImpl extends RemoteServiceServlet implements EnumerationGWTService {

	 EnumerationService enumerationService; 
	    public EnumerationMetaList fetchEnumerationMetas() {
	        
	        return enumerationService.findEnumerationMetas();
	    }
	    public EnumeratedValueList fetchEnumeration(String enumerationKey,String enumContextKey,String contextValue,Date   contextDate ){
	        return enumerationService.fetchEnumeration(enumerationKey, enumContextKey, contextValue, contextDate);
	    }
	    
	    public EnumeratedValue addEnumeratedValue(String enumerationKey,EnumeratedValue value){
	     return enumerationService.addEnumeratedValue(enumerationKey, value);

	    }

	    public EnumeratedValue updateEnumeratedValue(String enumerationKey,String code,EnumeratedValue value){
	        return enumerationService.updateEnumeratedValue(enumerationKey, code, value);
	    }

	    public boolean removeEnumeratedValue(String enumerationKey,String code){
	        return enumerationService.removeEnumeratedValue(enumerationKey, code);
	    }

	    
	    
	    public EnumerationService getEnumerationService() {
	        return enumerationService;
	    }
	    public void setEnumerationService(EnumerationService enumerationService) {
	        this.enumerationService = enumerationService;
	    }
}