package org.kuali.student.security.cxf.interceptors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.cxf.interceptor.Interceptor;

public class IntercpetorContainer extends HashMap<String,List<Interceptor>> {
    
    private List<Interceptor> inInterceptors = new ArrayList<Interceptor>();
    private List<Interceptor> outInterceptors = new ArrayList<Interceptor>();
    
    
    public List<Interceptor> getInInterceptors() {
        return inInterceptors;
    }
    public void setInInterceptors(List<Interceptor> inInterceptors) {
        this.inInterceptors = inInterceptors;
        this.put("inInterceptor", inInterceptors);
    }
    public List<Interceptor> getOutInterceptors() {
        return outInterceptors;
    }
    public void setOutInterceptors(List<Interceptor> outInterceptors) {
        this.outInterceptors = outInterceptors;
        this.put("outInterceptor", outInterceptors);
    }
    
    
}
