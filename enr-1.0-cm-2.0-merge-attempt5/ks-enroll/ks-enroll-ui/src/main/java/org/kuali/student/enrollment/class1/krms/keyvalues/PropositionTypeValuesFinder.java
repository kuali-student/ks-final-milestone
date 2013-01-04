/**
 * Copyright 2005-2012 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.class1.krms.keyvalues;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.web.form.InquiryForm;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.rice.krms.api.repository.type.KrmsTypeDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeRepositoryService;
//import org.kuali.rice.krms.api.repository.typerelation.TypeTypeRelation;
import org.kuali.rice.krms.impl.repository.KrmsRepositoryServiceLocator;
import org.kuali.student.enrollment.class1.krms.dto.StudentAgendaEditor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Helper class that returns all agenda types that are valid for a given context.
 */
public class PropositionTypeValuesFinder extends UifKeyValuesFinderBase {

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {
        List<KeyValue> keyValues = new ArrayList<KeyValue>();

        Object dataObject;
        if (model instanceof InquiryForm) {
            InquiryForm inquiryForm = (InquiryForm) model;
            dataObject = inquiryForm.getDataObject();

        } else {
            MaintenanceDocumentForm MaintenanceDocumentForm = (MaintenanceDocumentForm) model;
            dataObject = MaintenanceDocumentForm.getDocument().getNewMaintainableObject().getDataObject();
        }

        String ruleTypeId;
        if (dataObject instanceof StudentAgendaEditor){
            ruleTypeId = ((StudentAgendaEditor)dataObject).getAgendaItemLine().getRule().getTypeId();
        }



       // if we have an agenda w/ a selected context
        //Collection<TypeTypeRelation> typeRelations = getTypeTypeRelationBoService().findTypeTypeRelationsByFromType(ruleTypeId);
        //for (TypeTypeRelation typeRelation : typeRelations) {
        //    keyValues.add(new ConcreteKeyValue(typeRelation.getToTypeId(), getKrmsTypeRepositoryService().getTypeById(typeRelation.getToTypeId()).getName()));
        //}

        return this.getMockKeyValues();
    }

    public KrmsTypeRepositoryService getKrmsTypeRepositoryService() {
        return KrmsRepositoryServiceLocator.getKrmsTypeRepositoryService();
    }

    //public TypeTypeRelationBoService getTypeTypeRelationBoService() {
    //    return KrmsRepositoryServiceLocator.getTypeTypeRelationBoService();
    //}


    private List<KeyValue> getMockKeyValues(){

        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        Collection<KrmsTypeDefinition> types = KrmsRepositoryServiceLocator.getKrmsTypeRepositoryService().findAllTypesByNamespace("KS-SYS");
        for (KrmsTypeDefinition type : types) {
            if ("simplePropositionTypeService".equals(type.getServiceName())){
                keyValues.add(new ConcreteKeyValue(type.getId(), type.getName()));
            }
        }
        return keyValues;
    }

}
