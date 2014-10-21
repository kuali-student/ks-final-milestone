package org.kuali.student.common.util.security;

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
    @Deprecated
    public static ContextInfo getContextInfo(){
        return createDefaultContextInfo();
    }

    /**
     * Creates a default context with the current user and date and default locale
     * @return a default context
     */
    public static ContextInfo createDefaultContextInfo(){
        String currentUserId = SecurityUtils.getCurrentUserId();

        LocaleInfo localeInfo = new LocaleInfo();
        localeInfo.setLocaleLanguage(Locale.getDefault().getLanguage());
        localeInfo.setLocaleRegion(Locale.getDefault().getCountry());

        ContextInfo contextInfo = new ContextInfo();
        contextInfo.setAuthenticatedPrincipalId(currentUserId);
        contextInfo.setPrincipalId(currentUserId);
        contextInfo.setCurrentDate(new Date());
        contextInfo.setLocale(localeInfo);

        return contextInfo;
    }
}
