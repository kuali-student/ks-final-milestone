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
import org.kuali.rice.krms.api.repository.language.NaturalLanguageTemplate;
import org.kuali.rice.krms.api.repository.language.NaturalLanguageUsage;
import org.kuali.rice.krms.api.repository.type.KrmsTypeRepositoryService;
import org.kuali.rice.krms.api.repository.typerelation.TypeTypeRelation;
import org.kuali.rice.krms.impl.repository.KrmsRepositoryServiceLocator;
import org.kuali.rice.krms.impl.repository.NaturalLanguageTemplateBoService;
import org.kuali.rice.krms.impl.repository.NaturalLanguageUsageBoService;
import org.kuali.rice.krms.impl.repository.TypeTypeRelationBoService;
import org.kuali.student.enrollment.class1.krms.dto.RuleEditor;
import org.kuali.student.krms.naturallanguage.util.KsKrmsConstants;
import org.kuali.student.krms.naturallanguage.util.KsKrmsRepositoryServiceLocator;
import org.kuali.student.enrollment.class2.courseoffering.service.decorators.PermissionServiceConstants;

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

        String ruleTypeId = null;
        if (dataObject instanceof RuleEditor){
            ruleTypeId = ((RuleEditor) dataObject).getRule().getTypeId();
        }

        NaturalLanguageUsage usage = getNaturalLanguageUsageBoService().getNaturalLanguageUsageByName(PermissionServiceConstants.KS_SYS_NAMESPACE, KsKrmsConstants.KRMS_NL_TYPE_DESCRIPTION);

        // if we have an agenda w/ a selected context
        Collection<TypeTypeRelation> typeRelations = getTypeTypeRelationBoService().findTypeTypeRelationsByFromType(ruleTypeId);
        for (TypeTypeRelation typeRelation : typeRelations) {

            NaturalLanguageTemplate template = null;
            try{
                template = getNaturalLanguageTemplateBoService().findNaturalLanguageTemplateByLanguageCodeTypeIdAndNluId("en", typeRelation.getToTypeId(), usage.getId());
            }catch (IndexOutOfBoundsException e){
                //Ignore, rice error in NaturalLanguageTemplateBoServiceImpl line l
            }

            if (template != null){
                //Use the template in the dropdown
                keyValues.add(new ConcreteKeyValue(typeRelation.getToTypeId(), template.getTemplate()));
            } else {
                //If no template exist, display the type name
                keyValues.add(new ConcreteKeyValue(typeRelation.getToTypeId(), getKrmsTypeRepositoryService().getTypeById(typeRelation.getToTypeId()).getName()));
            }
        }

        return keyValues;
    }

    private KrmsTypeRepositoryService getKrmsTypeRepositoryService() {
        return KrmsRepositoryServiceLocator.getKrmsTypeRepositoryService();
    }

    private TypeTypeRelationBoService getTypeTypeRelationBoService() {
        return KrmsRepositoryServiceLocator.getTypeTypeRelationBoService();
    }

    private NaturalLanguageUsageBoService getNaturalLanguageUsageBoService() {
        return KsKrmsRepositoryServiceLocator.getNaturalLanguageUsageBoService();
    }

    private NaturalLanguageTemplateBoService getNaturalLanguageTemplateBoService() {
        return KsKrmsRepositoryServiceLocator.getNaturalLanguageTemplateBoService();
    }

}
