package org.kuali.student.ap.i18n;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * This class provides a ResourceBundle implementation that uses the {@link org.kuali.student.r2.common.messages.service.MessageService} and the Locale from a {@link org.kuali.student.r2.common.dto.ContextInfo}
 * @see org.kuali.student.ap.i18n.DBResourceBundleControlImpl for the details on how the properties are actually retrieved from the DB.
 * @author Chris Maurer <chmaurer@iu.edu>
 */
public class DBResourceBundleImpl extends ResourceBundle implements NonKeyValidatingResourceBundle {
    private Locale locale;
    private Properties properties;

    public DBResourceBundleImpl(Properties properties, Locale locale, ResourceBundle parent) {
        this.properties = properties;
        this.locale = locale;
        this.parent = parent;
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
    public boolean validateKeys() {
        return false;
    }
}
