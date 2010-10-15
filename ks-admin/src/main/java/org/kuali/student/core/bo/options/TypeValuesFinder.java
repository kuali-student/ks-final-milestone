package org.kuali.student.core.bo.options;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.kuali.rice.core.util.KeyLabelPair;
import org.kuali.rice.kns.lookup.keyvalues.KeyValuesBase;
import org.kuali.rice.kns.service.BusinessObjectService;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.student.core.bo.TypeBusinessObject;

public abstract class TypeValuesFinder extends KeyValuesBase {

    @SuppressWarnings("unchecked")
    @Override
    public List<KeyLabelPair> getKeyValues() {
        List<KeyLabelPair> labels = new ArrayList<KeyLabelPair>();
        
        BusinessObjectService boService = KNSServiceLocator.getBusinessObjectService();
        Collection<TypeBusinessObject> values = boService.findAll(this.getBusinessObjectClass());
        
        Iterator<TypeBusinessObject> iterator = values.iterator(); 
        while(iterator.hasNext()) {
            TypeBusinessObject value = iterator.next();
            labels.add(new KeyLabelPair(value.getId(), value.getName()));
        }
        
        return labels;
    }
    
    public abstract Class<? extends TypeBusinessObject> getBusinessObjectClass();

}
