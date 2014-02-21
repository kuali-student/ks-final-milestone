package org.kuali.i18n;

import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.KsapContext;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;

import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: chmaurer
 * Date: 1/13/14
 * Time: 11:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class KualiResourceBundleImpl extends ResourceBundle implements KualiResourceBundle {

    // name of ResourceBundle
    protected String baseName = null;

    public KualiResourceBundleImpl() {
    }

    public KualiResourceBundleImpl(String baseName) {
        this.baseName = baseName;
    }

    @Override
    /**
     * Return user's preferred locale
     *	 Return locale from context if available
     *	 Otherwise, return system default locale
     *
     * @return user's Locale object
     * @author Chris Maurer
     */
    public Locale getLocale()
    {
        KsapContext ksapCtx = KsapFrameworkServiceLocator.getContext();
        ContextInfo ctx = ksapCtx.getContextInfo();
        if(ctx == null){
            ctx = new ContextInfo();
        }
        LocaleInfo locale = ctx.getLocale();
        Locale loc = null;
        if (locale != null) {
            loc = LocaleHelper.localeInfo2Locale(locale);
        }
        else {
            loc = Locale.getDefault();
        }
        return loc;
    }



    /**
     * Return string value for specified property in current locale specific ResourceBundle
     *
     * @param key
     *        property key to look up in current ResourceBundle
     * @param dflt
     *        the default value to be returned in case the property is missing
     * @return String value for specified property key
     */
    public String getString(String key, String dflt)
    {
        try
        {
            return getBundle(baseName, getLocale()).getString(key);
        }
        catch (Exception e)
        {
            return dflt;
        }
    }

    /**
     * Return formatted message based on locale-specific pattern
     * @param key maps to locale-specific pattern in properties file
     * @param args parameters to format and insert according to above pattern
     * @return formatted message
     */
    public String getFormattedMessage(String key, Object... args)
    {
        String pattern = (String) getObject(key);
        return (new MessageFormat(pattern, getLocale())).format(args, new StringBuffer(), null).toString();
    }

    @Override
    protected Object handleGetObject(String key) throws MissingResourceException {
        try {
            return getBundle(baseName, getLocale()).getObject(key);
        }
        catch (MissingResourceException e) {
//            if (M_log.isWarnEnabled()) {
//                M_log.warn("bundle \'"+baseName +"\'  missing key: \'" + key
//                        + "\'  from: " + e.getStackTrace()[3] ); // 3-deep gets us out of ResourceLoader
//            }
            throw new MissingResourceException(e.getMessage(), e.getClassName(), e.getKey());
            //return "[missing key (mre): " + baseName + " " + key + "]";
        }
    }

    @Override
    public Enumeration<String> getKeys() {
        return getBundle(baseName, getLocale()).getKeys();
    }

    public void setBaseName(String name)
    {
        this.baseName = name;
    }
}
