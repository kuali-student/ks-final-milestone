package org.kuali.i18n;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: chmaurer
 * Date: 1/15/14
 * Time: 4:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class DBResourceBundleImpl extends ResourceBundle implements KualiResourceBundle {
// Got some tips for this class from here:
// http://www.webreference.com/programming/Globalize-Web-Applications16_Java_ResourceBundles/index.html
    private Locale locale;
    private Properties properties;

    public DBResourceBundleImpl(Properties properties, Locale locale, KualiResourceBundle parent) {
        this.properties = properties;
        this.locale = locale;
        this.parent = (ResourceBundle)parent;
    }

    @Override
    public Locale getLocale() {
        return locale;
    }

    @Override
    protected Object handleGetObject(String key) {
        return properties.getProperty(key);
    }

    @Override
    public Enumeration<String> getKeys() {
        Set<String> handleKeys = properties.stringPropertyNames();
        return Collections.enumeration(handleKeys);
    }

    @Override
    public String getString(String key, String dflt)
    {
        try
        {
            return getString(key);
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
    @Override
    public String getFormattedMessage(String key, Object... args)
    {
        String pattern = (String) getObject(key);
        return (new MessageFormat(pattern, getLocale())).format(args, new StringBuffer(), null).toString();
    }

}
