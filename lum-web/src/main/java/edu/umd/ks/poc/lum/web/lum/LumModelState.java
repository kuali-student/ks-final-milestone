package edu.umd.ks.poc.lum.web.lum;

import org.kuali.student.commons.ui.mvc.client.Controller;
import org.kuali.student.commons.ui.propertychangesupport.BasePropertyChange;


public class LumModelState extends BasePropertyChange {
    private static LumModelState instance;
    
    Controller c = null;
    
    /**
     * 
     */
    protected LumModelState() {
        super();
    }
    
    public static LumModelState getInstance() {
        return instance == null ? instance = new LumModelState() : instance;
    }
}
