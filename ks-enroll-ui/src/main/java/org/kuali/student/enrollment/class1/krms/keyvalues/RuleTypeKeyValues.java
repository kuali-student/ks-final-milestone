package org.kuali.student.enrollment.class1.krms.keyvalues;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krms.api.repository.type.KrmsTypeDefinition;
import org.kuali.rice.krms.api.repository.typerelation.TypeTypeRelation;
import org.kuali.rice.krms.impl.repository.KrmsRepositoryServiceLocator;
import org.kuali.rice.krms.impl.repository.TypeTypeRelationBoService;
import org.kuali.student.enrollment.class1.krms.form.RequisitesForm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RuleTypeKeyValues extends UifKeyValuesFinderBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {
        RequisitesForm requisiteForm = (RequisitesForm)model;
        List<KeyValue> keyValues = new ArrayList<KeyValue>();

         //Use Type Type Relation to get Rule Types
        try {
            List<TypeTypeRelation> typeTypeRelationList = getTypeTypeRelationBoService().findTypeTypeRelationsByFromType(requisiteForm.getAgendaType());

            for (TypeTypeRelation typeTypeRelation : typeTypeRelationList) {
                KrmsTypeDefinition krmsTypeDefinition = KrmsRepositoryServiceLocator.getKrmsTypeRepositoryService().getTypeById(typeTypeRelation.getToTypeId());
                ConcreteKeyValue keyValue = new ConcreteKeyValue();
                keyValue.setKey(krmsTypeDefinition.getId());
                keyValue.setValue(krmsTypeDefinition.getName());
                keyValues.add(keyValue);
            }
        } catch (Exception ex) {}
        return keyValues;
    }

    public TypeTypeRelationBoService getTypeTypeRelationBoService() {
        return KrmsRepositoryServiceLocator.getTypeTypeRelationBoService();
    }
}
