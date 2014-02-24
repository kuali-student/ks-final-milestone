package org.kuali.student.ap.i18n;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MergedPropertiesResourceBundleControlImpl extends ResourceBundle.Control {
    private static final Logger LOG = Logger.getLogger(MergedPropertiesResourceBundleControlImpl.class);
    public static final String FORMAT_PROPS_MERGED = "kuali.properties.merged";
    List<ResourceBundle> bundles;

    public MergedPropertiesResourceBundleControlImpl(List<ResourceBundle> bundles) {
        LOG.debug("MergedPropertiesResourceBundleControlImpl()");
        this.bundles = bundles;
    }

    @Override
    public List<String> getFormats(String baseName) {
        return Collections.singletonList(FORMAT_PROPS_MERGED);
    }

    @Override
    public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload) throws IllegalAccessException, InstantiationException, IOException {
        LOG.debug("MergedPropertiesResourceBundleControlImpl.newBundle with format: " + format);
        if ((baseName == null) || (locale == null) || (format == null) || (loader == null)) {
            throw new NullPointerException();
        }
        if (!format.equals(FORMAT_PROPS_MERGED)) {
            return null;
        }

        LOG.debug("Creating a new MergedPropertiesResourceBundleImpl with locale: " + locale.toString());
        return new MergedPropertiesResourceBundleImpl(bundles, locale);
    }
}