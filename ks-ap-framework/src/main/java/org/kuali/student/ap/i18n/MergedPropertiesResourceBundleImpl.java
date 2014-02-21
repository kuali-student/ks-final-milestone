package org.kuali.student.ap.i18n;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
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

    public MergedPropertiesResourceBundleImpl(List<ResourceBundle> bundles) {
        this.bundles = bundles;
    }

    @Override
    protected Object handleGetObject(String key) {
        Object retVal = null;
        for (ResourceBundle bundle : bundles) {
            retVal = bundle.getObject(key);
            if (retVal != null) {
                break;
            }
        }
        return retVal;
    }

    @Override
    public Enumeration<String> getKeys() {
        int totalCount = 0;
        Set<String> keys = new HashSet<String>();
        for (ResourceBundle bundle : bundles) {
            List<String> bundleKeys = Collections.list(bundle.getKeys());
            keys.addAll(bundleKeys);
            totalCount+=bundleKeys.size();
        }
        if (totalCount != keys.size())
            throw new RuntimeException("Duplicate keys found");
        return Collections.enumeration(keys);
    }
}
