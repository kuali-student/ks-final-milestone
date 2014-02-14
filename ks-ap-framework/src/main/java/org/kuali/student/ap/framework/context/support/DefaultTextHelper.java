package org.kuali.student.ap.framework.context.support;

import org.apache.log4j.Logger;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.KsapContext;
import org.kuali.student.ap.framework.context.TextHelper;
import org.kuali.student.ap.i18n.DBResourceBundleControlImpl;
import org.kuali.student.ap.i18n.DBResourceBundleImpl;
import org.kuali.student.ap.i18n.LocaleHelper;
import org.kuali.student.ap.i18n.MergedPropertiesResourceBundleImpl;
import org.kuali.student.r2.common.dto.ContextInfo;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class DefaultTextHelper implements TextHelper, Serializable {

	private static final long serialVersionUID = -616654137052936870L;

    private static final Logger LOG = Logger.getLogger(DefaultTextHelper.class);

	private String messageGroup;
    private List<String> baseNames;
    private String resourceBundleNames;

    public String getMessageGroup() {
        if (messageGroup == null)
            throw new IllegalArgumentException("messageGroup must be set");
        return messageGroup;
	}

	public void setMessageGroup(String messageGroup) {
		this.messageGroup = messageGroup;
	}

    private List<String> getBaseNames() {
        if (baseNames == null) {
            baseNames = new ArrayList<String>();
            String baseNameArray[] = getResourceBundleNamesFromConfig().split(",");
            for(String baseName : baseNameArray){
                baseNames.add(baseName);
            }
        }
        return baseNames;
    }

    private String getResourceBundleNamesFromConfig() {
       if (resourceBundleNames == null) {
           resourceBundleNames = ConfigContext.getCurrentContextConfig().getProperty(TextHelper.CONFIG_RESOURCE_BUNDLE_NAMES);
       }
        return resourceBundleNames;
    }

    @Override
    public String getText(String messageCode) {
        String value;
        try {
            value = getBundle().getString(messageCode);
        } catch (MissingResourceException mre) {
            LOG.error("Error getting text value", mre);
            value = "\\[missing key (mre): " + getResourceBundleNamesFromConfig() + " " + messageCode + "\\]";
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

    /**
     * Get the locale from the Context
     * @return
     */
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
        List<ResourceBundle> bundles = processBundles(locale);
        MergedPropertiesResourceBundleImpl mprb = new MergedPropertiesResourceBundleImpl(bundles);

        DBResourceBundleImpl drb = (DBResourceBundleImpl) ResourceBundle.getBundle(DBResourceBundleControlImpl.class.getName(), locale,
                new DBResourceBundleControlImpl(getMessageGroup(), contextInfo, mprb));

        return drb;
    }

    /**
     * Go through all of the configured bundles and make a list of ResourceBundle objects
     * @param locale
     * @return
     */
    private List<ResourceBundle> processBundles(Locale locale) {
        List<ResourceBundle> bundles = new ArrayList<ResourceBundle>();
        LOG.debug("Processing " + getBaseNames().size() + " bundles...");
        for (String baseName : getBaseNames()) {
            LOG.debug("Creating new PropertiesResourceBundleImpl(baseName:" + baseName + ", locale: " + locale.toString() + ")");
            ResourceBundle krb = ResourceBundle.getBundle(baseName, locale);
            bundles.add(krb);
        }
        return bundles;
    }
}
