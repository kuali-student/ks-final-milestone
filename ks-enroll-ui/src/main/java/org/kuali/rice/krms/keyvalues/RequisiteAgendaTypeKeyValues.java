package org.kuali.rice.krms.keyvalues;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krms.api.KrmsConstants;
import org.kuali.rice.krms.api.repository.RuleManagementService;
import org.kuali.rice.krms.api.repository.language.NaturalLanguageTemplate;
import org.kuali.rice.krms.api.repository.language.NaturalLanguageUsage;
import org.kuali.rice.krms.api.repository.type.KrmsTypeRepositoryService;
import org.kuali.rice.krms.api.repository.typerelation.TypeTypeRelation;
import org.kuali.rice.krms.impl.repository.KrmsRepositoryServiceLocator;
import org.kuali.student.krms.naturallanguage.util.KsKrmsConstants;
import org.kuali.student.enrollment.class2.courseoffering.service.decorators.PermissionServiceConstants;

import javax.xml.namespace.QName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RequisiteAgendaTypeKeyValues implements Serializable {

    private static final long serialVersionUID = 1L;

    private transient RuleManagementService ruleManagementService;

    public List<KeyValue> getKeyValues(String agendaId) {
        List<KeyValue> keyValues = new ArrayList<KeyValue>();

        NaturalLanguageUsage usage = this.getRuleManagementService().getNaturalLanguageUsageByNameAndNamespace(KsKrmsConstants.KRMS_NL_TYPE_DESCRIPTION, PermissionServiceConstants.KS_SYS_NAMESPACE);

        //Use KRMS Type Repository Service to get Agenda Types
        try {
            List<TypeTypeRelation> typeTypeRelationList = this.getKrmsTypeRepositoryService().findTypeTypeRelationsByFromType(agendaId);
            for (TypeTypeRelation typeTypeRelation : typeTypeRelationList) {
                NaturalLanguageTemplate template = null;
                try{
                    template = this.getRuleManagementService().findNaturalLanguageTemplateByLanguageCodeTypeIdAndNluId("en", typeTypeRelation.getToTypeId(), usage.getId());
                }catch (IndexOutOfBoundsException e){
                    //Ignore, rice error in NaturalLanguageTemplateBoServiceImpl line l
                }

                if (template != null){
                    //Use the template in the dropdown
                    keyValues.add(new ConcreteKeyValue(typeTypeRelation.getToTypeId(), template.getTemplate()));
                } else {
                    //If no template exist, display the type name
                    keyValues.add(new ConcreteKeyValue(typeTypeRelation.getToTypeId(), getKrmsTypeRepositoryService().getTypeById(typeTypeRelation.getToTypeId()).getName()));
                }
            }
        } catch (Exception ex) {}
        return keyValues;
    }

    private KrmsTypeRepositoryService getKrmsTypeRepositoryService() {
        return KrmsRepositoryServiceLocator.getKrmsTypeRepositoryService();
    }

    public RuleManagementService getRuleManagementService() {
        if (ruleManagementService == null) {
            ruleManagementService = (RuleManagementService) GlobalResourceLoader.getService(new QName(KrmsConstants.Namespaces.KRMS_NAMESPACE_2_0, "ruleManagementService"));
        }
        return ruleManagementService;
    }
}
