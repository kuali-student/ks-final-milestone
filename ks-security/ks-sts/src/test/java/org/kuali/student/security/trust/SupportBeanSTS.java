package org.kuali.student.security.trust;

public class SupportBeanSTS {
    
    public SupportBeanSTS(){
        System.setProperty("ks.test.securityTokenService.useCas", "false");
    }
}
