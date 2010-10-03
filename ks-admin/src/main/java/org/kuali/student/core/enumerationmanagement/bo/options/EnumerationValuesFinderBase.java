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

public abstract class EnumerationValuesFinderBase extends KeyValuesBase {

    private static final String ENUMERATION_KEY = "enumerationKey";
    
    @SuppressWarnings("unchecked")
    @Override
    public List<KeyLabelPair> getKeyValues() {
        List<KeyLabelPair> labels = new ArrayList<KeyLabelPair>();
        Map<String, Object> criteria = new HashMap<String,Object>();
        
        criteria.put(ENUMERATION_KEY, getEnumerationKey());
        BusinessObjectService boService = KNSServiceLocator.getBusinessObjectService();
        Collection<EnumeratedValue> values = boService.findMatching(org.kuali.student.core.enumerationmanagement.bo.EnumeratedValue.class, criteria);
        
        Iterator<EnumeratedValue> iterator = values.iterator(); 
        while(iterator.hasNext()) {
            EnumeratedValue value = iterator.next();
            labels.add(new KeyLabelPair(value.getId(), value.getValue()));
        }
        
        return labels;
    }
    
    public abstract String getEnumerationKey();

}
