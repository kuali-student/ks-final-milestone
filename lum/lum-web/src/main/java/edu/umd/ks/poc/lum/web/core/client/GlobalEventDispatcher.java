package edu.umd.ks.poc.lum.web.core.client;

import org.kuali.student.commons.ui.mvc.client.EventDispatcher;

public class GlobalEventDispatcher extends EventDispatcher{

    private static GlobalEventDispatcher instance;
    
    protected GlobalEventDispatcher() {
        super();
    }
    
    public static GlobalEventDispatcher getInstance(){
        return instance == null ? instance = new GlobalEventDispatcher() : instance;
    }

}
