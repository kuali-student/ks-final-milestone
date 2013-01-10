package org.kuali.student.enrollment.class1.krms.keyvalues;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krms.api.repository.type.KrmsTypeDefinition;
import org.kuali.rice.krms.impl.repository.KrmsRepositoryServiceLocator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RequisiteAgendaTypeKeyValues extends UifKeyValuesFinderBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {
        List<KeyValue> keyValues = new ArrayList<KeyValue>();

        //Use KRMS Type Repository Service to get Agenda Types
        try {
            List<KrmsTypeDefinition> krmsTypeDefinitionList = KrmsRepositoryServiceLocator.getKrmsTypeRepositoryService().findAllTypes();

            for (KrmsTypeDefinition krmsTypeDefinition : krmsTypeDefinitionList) {
                if(krmsTypeDefinition.getServiceName().equals("agendaTypeService")) {
                    if(!krmsTypeDefinition.getId().equals("10000")) {
                        ConcreteKeyValue keyValue = new ConcreteKeyValue();
                        keyValue.setKey(krmsTypeDefinition.getId());
                        keyValue.setValue(krmsTypeDefinition.getName());
                        keyValues.add(keyValue);
                    }
                }
            }
        } catch (Exception ex) {}
        return keyValues;
    }
}
