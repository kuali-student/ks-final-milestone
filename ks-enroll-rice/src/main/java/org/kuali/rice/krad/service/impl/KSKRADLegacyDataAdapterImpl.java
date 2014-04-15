package org.kuali.rice.krad.service.impl;

import org.kuali.rice.krad.data.metadata.DataObjectMetadata;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.util.KRADUtils;
import org.springframework.beans.PropertyAccessorUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An extension of {@link KRADLegacyDataAdapterImpl} to work around rice bug
 */
public class KSKRADLegacyDataAdapterImpl extends KRADLegacyDataAdapterImpl {
    @Override
    public Map<String, String> getInquiryParameters(Object dataObject, List<String> keys, String propertyName) {
        Map<String, String> inquiryParameters = new HashMap<String, String>();
        DataObjectMetadata objectMetadata =
                KRADServiceLocator.getDataObjectService().getMetadataRepository().getMetadata(dataObject.getClass());
        org.kuali.rice.krad.data.metadata.DataObjectRelationship dataObjectRelationship = null;
        // KS customization: added null check for objectMetadata
        if (objectMetadata != null) {
            objectMetadata.getRelationshipByLastAttributeInRelationship(propertyName);
        }
        for (String keyName : keys) {
            String keyConversion = keyName;
            if (dataObjectRelationship != null) {
                keyConversion = dataObjectRelationship.getParentAttributeNameRelatedToChildAttributeName(keyName);
            } else if (PropertyAccessorUtils.isNestedOrIndexedProperty(propertyName)) {
                String nestedAttributePrefix = KRADUtils.getNestedAttributePrefix(propertyName);
                keyConversion = nestedAttributePrefix + "." + keyName;
            }
            inquiryParameters.put(keyConversion, keyName);
        }
        return inquiryParameters;
    }
}
