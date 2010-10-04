package org.kuali.student.core.enumerationmanagement.bo.options;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.kuali.rice.core.util.KeyLabelPair;
import org.kuali.rice.kns.lookup.keyvalues.KeyValuesBase;
import org.kuali.rice.kns.service.BusinessObjectService;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.student.core.enumerationmanagement.bo.EnumeratedValue;

/**
 * This base class creates a KeyValuesFinder for EnumeratedValues consisting of
 * key=EnumeratedValue.code and value=EnumeratedValue.value.  Simply override
 * getEnumerationKey() to return the enumerationKey of the Enumeration you want
 * a KeyValuesFinder for.
 */
public abstract class EnumerationCodeValuesFinderBase extends KeyValuesBase {
    
    @SuppressWarnings("unchecked")
    @Override
    public List<KeyLabelPair> getKeyValues() {
        List<KeyLabelPair> labels = new ArrayList<KeyLabelPair>();
        Map<String, Object> criteria = new HashMap<String,Object>();
        
        criteria.put(EnumeratedValue.ENUMERATION_KEY, getEnumerationKey());
        BusinessObjectService boService = KNSServiceLocator.getBusinessObjectService();
        Collection<EnumeratedValue> values = boService.findMatching(EnumeratedValue.class, criteria);
        
        Iterator<EnumeratedValue> iterator = values.iterator(); 
        while(iterator.hasNext()) {
            EnumeratedValue value = iterator.next();
            labels.add(new KeyLabelPair(value.getCode(), value.getValue()));
        }
        
        return labels;
    }
    
    public abstract String getEnumerationKey();

}
