package org.kuali.student.enumeration.web.server.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.kuali.student.enumeration.dto.EnumeratedValue;
import org.kuali.student.enumeration.dto.EnumeratedValueList;
import org.kuali.student.enumeration.service.EnumerationService;
import org.kuali.student.enumeration.web.client.service.EnumerationGWTClientService;


public class EnumerationGWTClientServiceImpl implements EnumerationGWTClientService{
    EnumerationService enumerationService; 
    public List<String> fetchEnumertionMeta() {
        //just to test the config is corrent, will remove
        
        List<String> l = new ArrayList<String>();
        l.add("Joe");
        l.add("Joe");
        //enumerationService.findEnumerationMetas();
        return l; 
    }
//    public EnumeratedValueList fetchEnumeration(String enumerationKey,String enumContextKey,String contextValue,Date   contextDate ){
  //      return null;
    //}
    
//    public EnumeratedValue addEnumeratedValue(String enumerationKey,EnumeratedValue value){
  //      return null;
    //}

//    public EnumeratedValue updateEnumeratedValue(String enumerationKey,String code,EnumeratedValue value){
  //      return null;
    //}

//    public boolean removeEnumeratedValue(String enumerationKey,String code){
  //      return true;
    //}

    
    
    public EnumerationService getEnumerationService() {
        return enumerationService;
    }
    public void setEnumerationService(EnumerationService enumerationService) {
        this.enumerationService = enumerationService;
    }
    
}