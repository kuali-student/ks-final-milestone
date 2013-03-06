package org.kuali.rice.krms.keyvalues;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.rice.krms.api.repository.language.NaturalLanguageTemplate;
import org.kuali.rice.krms.api.repository.language.NaturalLanguageUsage;
import org.kuali.rice.krms.api.repository.type.KrmsTypeRepositoryService;
import org.kuali.rice.krms.api.repository.typerelation.TypeTypeRelation;
import org.kuali.rice.krms.impl.repository.KrmsRepositoryServiceLocator;
import org.kuali.rice.krms.impl.repository.NaturalLanguageTemplateBoService;
import org.kuali.rice.krms.impl.repository.NaturalLanguageUsageBoService;
import org.kuali.rice.krms.impl.repository.TypeTypeRelationBoService;
import org.kuali.student.krms.naturallanguage.util.KsKrmsConstants;
import org.kuali.student.krms.naturallanguage.util.KsKrmsRepositoryServiceLocator;
import org.kuali.student.enrollment.class2.courseoffering.service.decorators.PermissionServiceConstants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RuleTypeKeyValues implements Serializable {

    private static final long serialVersionUID = 1L;

    public Map<String,Map<String, String>> getKeyValues(List<KeyValue> agendaTypeIds) {

        Map<String, Map<String, String>> keyValueList = new HashMap<String, Map<String, String>>();
        Map<String, String> keyValuesRule = new HashMap<String, String>();
        Map<String, String> keyValuesInstruction = new HashMap<String, String>();
        NaturalLanguageUsage usageType = getNaturalLanguageUsageBoService().getNaturalLanguageUsageByName(PermissionServiceConstants.KS_SYS_NAMESPACE, KsKrmsConstants.KRMS_NL_TYPE_DESCRIPTION);
        NaturalLanguageUsage usageInstruction = getNaturalLanguageUsageBoService().getNaturalLanguageUsageByName(PermissionServiceConstants.KS_SYS_NAMESPACE, KsKrmsConstants.KRMS_NL_TYPE_INSTRUCTION);
         //Use Type Type Relation to get Rule Types
        try {
            for(KeyValue agendaTypeId : agendaTypeIds) {
                List<TypeTypeRelation> typeTypeRelationList = getTypeTypeRelationBoService().findTypeTypeRelationsByFromType(agendaTypeId.getKey());
                for (TypeTypeRelation typeTypeRelation : typeTypeRelationList) {
                    NaturalLanguageTemplate templateRule = null;
                    NaturalLanguageTemplate templateInstruction = null;
                    try{
                        templateRule = getNaturalLanguageTemplateBoService().findNaturalLanguageTemplateByLanguageCodeTypeIdAndNluId("en", typeTypeRelation.getToTypeId(), usageType.getId());
                        templateInstruction = getNaturalLanguageTemplateBoService().findNaturalLanguageTemplateByLanguageCodeTypeIdAndNluId("en", typeTypeRelation.getToTypeId(), usageInstruction.getId());
                    }catch (IndexOutOfBoundsException e){
                        //Ignore, rice error in NaturalLanguageTemplateBoServiceImpl line l
                    }

                    if (templateRule != null){
                        //Use the template in the dropdown
                        keyValuesRule.put(typeTypeRelation.getToTypeId(), templateRule.getTemplate());
                    } else {
                        //If no template exist, display the type name
                        keyValuesRule.put(typeTypeRelation.getToTypeId(), getKrmsTypeRepositoryService().getTypeById(typeTypeRelation.getToTypeId()).getName());
                    }

                    if (templateInstruction != null && templateRule != null) {
                        keyValuesInstruction.put(templateRule.getTemplate(), templateInstruction.getTemplate());
                    } else {
                        keyValuesInstruction.put(typeTypeRelation.getToTypeId(), getKrmsTypeRepositoryService().getTypeById(typeTypeRelation.getToTypeId()).getName());
                    }
                }
            }
        } catch (Exception ex) {}
        keyValueList.put("ruleTypes", keyValuesRule);
        keyValueList.put("ruleInstructions", keyValuesInstruction);
        return keyValueList;
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
