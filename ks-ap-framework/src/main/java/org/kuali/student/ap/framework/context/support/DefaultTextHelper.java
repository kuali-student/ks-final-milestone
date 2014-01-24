package org.kuali.student.ap.framework.context.support;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.kuali.student.ap.i18n.DBResourceBundleControlImpl;
import org.kuali.student.ap.i18n.DBResourceBundleImpl;
import org.kuali.student.ap.i18n.PropertiesResourceBundleImpl;
import org.kuali.student.ap.i18n.LocaleHelper;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.KsapContext;
import org.kuali.student.ap.framework.context.TextHelper;
import org.kuali.student.r2.common.dto.ContextInfo;

public class DefaultTextHelper implements TextHelper, Serializable {

	private static final long serialVersionUID = -616654137052936870L;

    private static final Logger LOG = Logger.getLogger(DefaultTextHelper.class);

	private String messageGroup;
    private String baseName;
    private Map<String, PropertiesResourceBundleImpl> resourceBundles = new HashMap<String, PropertiesResourceBundleImpl>();

    public String getMessageGroup() {
        if (messageGroup == null)
            throw new IllegalArgumentException("messageGroup must be set");
        return messageGroup;
	}

	public void setMessageGroup(String messageGroup) {
		this.messageGroup = messageGroup;
	}

    public String getBaseName() {
        if (baseName == null)
            throw new IllegalArgumentException("baseName must be set");
        return baseName;
    }

    public void setBaseName(String baseName) {
        this.baseName = baseName;
    }

    @Override
    public String getText(String messageCode) {
        String value;
        try {
            value = getBundle().getString(messageCode);
        } catch (MissingResourceException mre) {
            value = "\\[missing key (mre): " + baseName + " " + messageCode + "\\]";
        }
        return value;
    }

    @Override
    public String getText(String messageCode, String defaultValue) {
        String value;
        try {
            value = getBundle().getString(messageCode);
        } catch (Exception e) {
            value = defaultValue;
        }
        return value;
    }

    @Override
    public String getFormattedMessage(String key, Object... args) {
        String pattern = getBundle().getString(key);
        return (new MessageFormat(pattern, getLocale())).format(args, new StringBuffer(), null).toString();
    }

    private Locale getLocale() {
        KsapContext ksapCtx = KsapFrameworkServiceLocator.getContext();
        ContextInfo contextInfo = ksapCtx.getContextInfo();
        Locale locale = LocaleHelper.localeInfo2Locale(contextInfo.getLocale());
        return locale;
    }

    /**
     * Get the appropriate DBResourceBundleImpl to use for text lookups.
     * @return
     */
    private DBResourceBundleImpl getBundle() {
        KsapContext ksapCtx = KsapFrameworkServiceLocator.getContext();
        ContextInfo contextInfo = ksapCtx.getContextInfo();
        Locale locale = LocaleHelper.localeInfo2Locale(contextInfo.getLocale());
        PropertiesResourceBundleImpl krb = getRBFromMap(locale, contextInfo);

        DBResourceBundleImpl drb = (DBResourceBundleImpl) ResourceBundle.getBundle(DBResourceBundleControlImpl.class.getName(), locale,
                new DBResourceBundleControlImpl(getMessageGroup(), contextInfo, krb));

        return drb;
    }

    /**
     * Look up (or store if not found) a PropertiesResourceBundleImpl using a locale string
     * @param locale The locale to use as the key in the map
     * @param contextInfo Used to create the PropertiesResourceBundleImpl
     * @return The found (or newly created) PropertiesResourceBundleImpl from the map
     */
    private PropertiesResourceBundleImpl getRBFromMap(Locale locale, ContextInfo contextInfo) {
        PropertiesResourceBundleImpl krb = resourceBundles.get(locale.toString());
        if (krb == null) {
            LOG.debug("new PropertiesResourceBundleImpl(" + getBaseName() + ", " + locale.toString() + ")");
            krb =  new PropertiesResourceBundleImpl(getBaseName(), contextInfo);
            resourceBundles.put(locale.toString(), krb);
        }
        return krb;
    }

}
