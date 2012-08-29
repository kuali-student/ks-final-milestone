package org.kuali.student.r2.common.util;

import org.kuali.rice.krad.UserSession;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;

import java.util.Date;
import java.util.Locale;

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

    /**
     * Creates a default context with the current user and date and default locale
     * @return a default context
     */
    public static ContextInfo createDefaultContextInfo(){
        ContextInfo contextInfo = new ContextInfo();
        UserSession userSession = GlobalVariables.getUserSession();
        if (userSession != null) {
            contextInfo.setAuthenticatedPrincipalId(userSession.getPrincipalId());
            contextInfo.setPrincipalId(userSession.getPrincipalId());
        }
        contextInfo.setCurrentDate(new Date());
        LocaleInfo localeInfo = new LocaleInfo();
        localeInfo.setLocaleLanguage(Locale.getDefault().getLanguage());
        localeInfo.setLocaleRegion(Locale.getDefault().getCountry());
        contextInfo.setLocale(localeInfo);
        return contextInfo;
    }
}
