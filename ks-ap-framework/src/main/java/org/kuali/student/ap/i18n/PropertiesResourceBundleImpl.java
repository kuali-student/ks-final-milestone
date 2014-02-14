package org.kuali.student.ap.i18n;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;

import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This class provides a ResourceBundle implementation that uses properties files and the Locale from a {@link ContextInfo}
 * @author Chris Maurer <chmaurer@iu.edu>
 */
public class PropertiesResourceBundleImpl extends ResourceBundle {

    // name of ResourceBundle
    protected String baseName = null;
    private ContextInfo contextInfo = null;

    public PropertiesResourceBundleImpl(String baseName, ContextInfo contextInfo, PropertiesResourceBundleImpl parent) {
        this.baseName = baseName;
        this.contextInfo = contextInfo;
        this.parent = parent;
    }

    @Override
    /**
     * Return user's preferred locale
     *	 Return locale from context if available
     *	 Otherwise, return system default locale
     *
     * @return user's Locale object
     */
    public Locale getLocale()
    {
        if(contextInfo == null){
            contextInfo = new ContextInfo();
        }
        LocaleInfo localeInfo = contextInfo.getLocale();
        Locale loc = null;
        if (localeInfo != null) {
            loc = LocaleHelper.localeInfo2Locale(localeInfo);
        }
        else {
            loc = Locale.getDefault();
        }
        //locale = loc;
        return loc;
    }

    @Override
    protected Object handleGetObject(String key) {
        return getBundle(baseName, getLocale()).getObject(key);
    }

    @Override
    public Enumeration<String> getKeys() {
        return getBundle(baseName, getLocale()).getKeys();
    }
}
