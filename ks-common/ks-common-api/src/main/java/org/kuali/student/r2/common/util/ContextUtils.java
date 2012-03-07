package org.kuali.student.r2.common.util;

import org.kuali.rice.krad.UserSession;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.r2.common.dto.ContextInfo;

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
        UserSession userSession = GlobalVariables.getUserSession();
        if (userSession != null) {
            contextInfo.setPrincipalId(userSession.getPrincipalId());
        }

        return contextInfo;
    }

}
