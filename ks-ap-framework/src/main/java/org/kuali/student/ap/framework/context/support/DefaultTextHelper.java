package org.kuali.student.ap.framework.context.support;

import org.apache.log4j.Logger;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.KsapContext;
import org.kuali.student.ap.framework.context.TextHelper;
import org.kuali.student.ap.i18n.DBResourceBundleControlImpl;
import org.kuali.student.ap.i18n.DBResourceBundleImpl;
import org.kuali.student.ap.i18n.LocaleUtil;
import org.kuali.student.ap.i18n.MergedPropertiesResourceBundleControlImpl;
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
            ResourceBundle bundle = getBundle();
            validateLocaleMatch(getLocale(), bundle.getLocale(), "getText(" + messageCode + ")");
            LOG.debug("getText(" + messageCode + ") - bundle with locale: '" + bundle.getLocale().toString() + "' when looking for '" + getLocale().toString() + "'");
            value = bundle.getString(messageCode);
        } catch (MissingResourceException mre) {
            LOG.error("Error getting text value: " + mre.getLocalizedMessage());
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
     * @return The java.util.Locale from the context
     */
    private Locale getLocale() {
        KsapContext ksapCtx = KsapFrameworkServiceLocator.getContext();
        ContextInfo contextInfo = ksapCtx.getContextInfo();
        Locale locale = LocaleUtil.localeInfo2Locale(contextInfo.getLocale());
        return locale;
    }

    /**
     * Get the appropriate ResourceBundle to use for text lookups.
     * @return
     */
    private ResourceBundle getBundle() {
        KsapContext ksapCtx = KsapFrameworkServiceLocator.getContext();
        ContextInfo contextInfo = ksapCtx.getContextInfo();
        Locale locale = LocaleUtil.localeInfo2Locale(contextInfo.getLocale());
        List<ResourceBundle> bundles = processBundles(locale);

        DBResourceBundleImpl drb = (DBResourceBundleImpl) ResourceBundle.getBundle(DBResourceBundleControlImpl.class.getName(), locale,
                new DBResourceBundleControlImpl(getMessageGroup(), contextInfo));

        //Add to the beginning so that it gets searched first
        bundles.add(0, drb);

        MergedPropertiesResourceBundleImpl.Control control = new MergedPropertiesResourceBundleControlImpl(bundles);
        ResourceBundle mprb = ResourceBundle.getBundle(MergedPropertiesResourceBundleImpl.class.getName(), locale, control);

        return mprb;
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
            LOG.debug("Creating new ResourceBundle(baseName:" + baseName + ", locale: '" + locale.toString() + "')");
            ResourceBundle krb = ResourceBundle.getBundle(baseName, locale);
            LOG.debug("Found bundle with locale: '" + krb.getLocale().toString() + "' when looking for '" + locale.toString() + "'");
            validateLocaleMatch(locale, krb.getLocale(), "processBundles()");
            bundles.add(krb);
        }
        return bundles;
    }

    private void validateLocaleMatch(Locale expected, Locale actual, String logContext) {
        if (!"".equals(actual.toString()) && !expected.toString().equals(actual.toString())) {
            throw new RuntimeException(logContext + "- Locale mismatch.  Wanted '" + expected.toString() + "' but got '" + actual.toString() + "'");
        }
    }
}
