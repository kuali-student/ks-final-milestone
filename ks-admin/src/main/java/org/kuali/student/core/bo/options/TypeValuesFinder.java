package org.kuali.student.core.bo.options;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.student.core.bo.KsTypeBusinessObject;

public abstract class TypeValuesFinder extends KeyValuesBase {

    @SuppressWarnings("unchecked")
    @Override
    public List<KeyValue> getKeyValues() {

        List<KeyValue> labels = new ArrayList<KeyValue>();

        BusinessObjectService boService = KRADServiceLocator.getBusinessObjectService();
        Collection<? extends KsTypeBusinessObject> values = boService.findAll(this.getBusinessObjectClass());

        Iterator<? extends KsTypeBusinessObject> iterator = values.iterator();
        while(iterator.hasNext()) {
            KsTypeBusinessObject value = iterator.next();
            labels.add(new ConcreteKeyValue(value.getId(), value.getName()));
        }

        return labels;
    }
    
    public abstract Class<? extends KsTypeBusinessObject> getBusinessObjectClass();

}
