package org.kuali.student.common.util;

import org.kuali.student.common.dto.ContextInfo;

/**
 * 
 *  This is a library of utility methods that can be used when working with the context info. 
 * 
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
public class ContextUtils {
    
    /**
     * 
     * Create a new instance of ContextInfo.
     * 
     * @return
     */
    public static ContextInfo getContextInfo(){
        ContextInfo contextInfo = new ContextInfo();
        //TODO KSCM-gwt-compile: UserSession contains references that can't be emulated in GWT.
//        UserSession userSession = GlobalVariables.getUserSession();
//        if (userSession != null) {
//            contextInfo.setPrincipalId(userSession.getPrincipalId());
//        }
        return contextInfo;
    }

}
