package edu.umd.ks.poc.lum.web.lu.client.service;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface LuRpcService  extends RemoteService, LuService{
    public static final String SERVICE_URI = "LuRpcService";

    public static class Util {
        
        private static LuRpcServiceAsync instance = null;

        public static LuRpcServiceAsync getInstance() {

            if(instance == null){
                instance = (LuRpcServiceAsync) GWT
                        .create(LuRpcService.class);               
                ((ServiceDefTarget) instance).setServiceEntryPoint(GWT.getModuleBaseURL() + SERVICE_URI);
            }
            return instance;
        }
    }
}
