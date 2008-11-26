package org.kuali.student.enumeration.web.server.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.kuali.student.enumeration.dto.EnumeratedValue;
import org.kuali.student.enumeration.dto.EnumeratedValueList;
import org.kuali.student.enumeration.dto.EnumerationMetaList;
import org.kuali.student.enumeration.service.EnumerationService;
import org.kuali.student.enumeration.web.client.service.EnumerationGWTClientService;


public class EnumerationGWTClientServiceImpl implements EnumerationGWTClientService{
    EnumerationService enumerationService; 
    @Override
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