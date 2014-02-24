package org.kuali.student.ap.i18n;

import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: chmaurer
 * Date: 2/12/14
 * Time: 10:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class MergedPropertiesResourceBundleImpl extends ResourceBundle {

    List<ResourceBundle> bundles;
    private Locale locale;

    private static final Logger LOG = Logger.getLogger(MergedPropertiesResourceBundleImpl.class);

    public MergedPropertiesResourceBundleImpl(List<ResourceBundle> bundles, Locale locale) {
        this.bundles = bundles;
        this.locale = locale;
        validateKeys();
        validateLocales();
        LOG.debug("MergedPropertiesResourceBundleImpl(" + bundles.size() + ", " + locale.toString() + ")");
    }

    @Override
    public Locale getLocale() {
        return locale;
    }

    @Override
    protected Object handleGetObject(String key) {
        Object retVal = null;
        for (ResourceBundle bundle : bundles) {
            validateLocale(this.locale.toString(), bundle);
            try {
                retVal = bundle.getObject(key);
            }
            catch (MissingResourceException mre) {
                //Swallowing this for now since we are looping through multiple bundles...
            }
            if (retVal != null) {
                LOG.debug("Key(" + key + ") found in bundle with locale:'" + bundle.getLocale().toString() + "'");
                break;
            }
        }
        if (retVal == null) {
            throw new MissingResourceException("Can't find resource for bundle "
                    +this.getClass().getName()
                    +", key "+key,
                    this.getClass().getName(),
                    key);
        }
        return retVal;
    }

    @Override
    public Enumeration<String> getKeys() {
        Set<String> keys = new HashSet<String>();
        for (ResourceBundle bundle : bundles) {
            List<String> bundleKeys = Collections.list(bundle.getKeys());
            keys.addAll(bundleKeys);
        }
        return Collections.enumeration(keys);
    }

    /**
     * Make sure that the number of keys match up after combining the properties files
     */
    private void validateKeys() {
        int totalCount = 0;

        Set<String> keys = new HashSet<String>();
        for (ResourceBundle bundle : bundles) {
            boolean doValidate = true;
            List<String> bundleKeys = Collections.list(bundle.getKeys());

            if (bundle instanceof NonKeyValidatingResourceBundle) {
                //Don't include a bundle
                if (!((NonKeyValidatingResourceBundle) bundle).validateKeys()) {
                    doValidate = false;
                }
            }
            if (doValidate) {
                keys.addAll(bundleKeys);
                totalCount+=bundleKeys.size();
            }
        }
        if (totalCount != keys.size())
            throw new RuntimeException("Duplicate keys found.  Expected: " + totalCount + "; Found: " + keys.size());
    }

    /**
     * Make sure that the locales match up after combining the properties files
     */
    private void validateLocales() {
        String the_locale = this.getLocale().toString();
        for (ResourceBundle bundle : bundles) {
            validateLocale(the_locale, bundle);
        }
    }

    private void validateLocale(String expected, ResourceBundle bundle) {
        String bundle_locale = bundle.getLocale().toString();
        if (!expected.equals(bundle_locale) && !bundle_locale.equals("")) {
            //throw new RuntimeException("Mismatched locales.  Expected '" + the_locale + "' but got '" + bundle_locale + "' from " + bundle);
            LOG.warn("Mismatched locales.  Expected '" + expected + "' but got '" + bundle_locale + "' from " + bundle);
        }
        else {
            LOG.debug("Expected '" + expected + "' and got '" + bundle_locale + "' from " + bundle);
        }
    }
}
