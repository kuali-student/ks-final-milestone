package org.kuali.student.enumeration.web.server.impl;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.enumeration.service.EnumerationService;
import org.kuali.student.enumeration.web.client.service.EnumerationGWTClientService;


public class EnumerationGWTClientServiceImpl implements EnumerationGWTClientService{
    EnumerationService enumerationService; 
    public List<String> fetchEnumertionMeta() {
        //just to test the config is corrent, will remove
        
        List<String> l = new ArrayList<String>();
        l.add("Joe");
        l.add("Joe");
        return l;
    }
    public EnumerationService getEnumerationService() {
        return enumerationService;
    }
    public void setEnumerationService(EnumerationService enumerationService) {
        this.enumerationService = enumerationService;
    }
    
}