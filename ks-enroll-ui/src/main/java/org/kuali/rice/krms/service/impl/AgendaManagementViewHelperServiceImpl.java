package org.kuali.rice.krms.service.impl;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krms.api.KrmsConstants;
import org.kuali.rice.krms.api.repository.RuleManagementService;
import org.kuali.rice.krms.api.repository.agenda.AgendaItemDefinition;
import org.kuali.rice.krms.api.repository.rule.RuleDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeRepositoryService;
import org.kuali.rice.krms.impl.repository.AgendaBoService;
import org.kuali.rice.krms.impl.repository.KrmsRepositoryServiceLocator;
import org.kuali.rice.krms.builder.ComponentBuilder;
import org.kuali.student.enrollment.class1.krms.dto.RuleEditor;
import org.kuali.rice.krms.dto.TemplateInfo;
import org.kuali.student.enrollment.class1.krms.form.AgendaManagementForm;
import org.kuali.rice.krms.keyvalues.RequisiteAgendaTypeKeyValues;
import org.kuali.rice.krms.keyvalues.RuleTypeKeyValues;
import org.kuali.rice.krms.service.AgendaManagementViewHelperService;
import org.kuali.rice.krms.service.TemplateRegistry;
import org.kuali.rice.krms.tree.RulePreviewTreeBuilder;
import org.kuali.student.enrollment.uif.service.impl.KSViewHelperServiceImpl;
import org.kuali.student.krms.util.KSKRMSConstants;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AgendaManagementViewHelperServiceImpl extends KSViewHelperServiceImpl implements AgendaManagementViewHelperService {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(AgendaManagementViewHelperServiceImpl.class);

    private RulePreviewTreeBuilder previewTreeBuilder;
    private RuleManagementService ruleManagementService;
    private static TemplateRegistry templateRegistry;

    @Override
    public void performInitialization(View view, Object model) {
        AgendaManagementForm form = (AgendaManagementForm) model;
        String agendaId = "10008";
//        agendaId = form.getAgenda().getId();
        List<AgendaItemDefinition> agendaItems = getAgendaBoService().getAgendaItemsByAgendaId(agendaId);
        List<String> ruleIds = new ArrayList<String>();
        for(AgendaItemDefinition agendaItem : agendaItems) {
            ruleIds.add(agendaItem.getRuleId());
        }
        setupRulePreview(form, ruleIds);
        String agendaCourseTypeId = getAgendaCourseTypeId();

        List<KeyValue> agendaTypeIds = setAgendaTypes(form, agendaCourseTypeId);
        setRuleTypes(form, agendaTypeIds);

        super.performInitialization(view, model);

    }

    private void setupRulePreview(AgendaManagementForm form, List<String> ruleIds) {
        for(String ruleId : ruleIds) {
            RuleDefinition ruleDefinition = this.getRuleManagementService().getRule(ruleId);
            RuleEditor ruleEditor = new RuleEditor(ruleDefinition);
            KrmsTypeDefinition ruleType = getKrmsTypeRepositoryService().getTypeById(ruleDefinition.getTypeId());
            if(ruleType.getName().equals(KSKRMSConstants.STUD_ELIGIBILITY_RYLE_TYPE)) {
                form.setStudentEligAndPrereqPreviewTree(this.getPreviewTreeBuilder().buildTree(ruleEditor));
            } else if(ruleType.getName().equals(KSKRMSConstants.CORE_REQUISITE_RYLE_TYPE)) {
                form.setCorequisitePreviewTree(this.getPreviewTreeBuilder().buildTree(ruleEditor));
            } else if(ruleType.getName().equals(KSKRMSConstants.ANTI_REQUISITE_RYLE_TYPE)) {
                form.setAntirequisitePreviewTree(this.getPreviewTreeBuilder().buildTree(ruleEditor));
            } else if(ruleType.getName().equals(KSKRMSConstants.RECOMMENDED_PREPERATION_RYLE_TYPE)) {
                form.setRecPrepPreviewTree(this.getPreviewTreeBuilder().buildTree(ruleEditor));
            } else if(ruleType.getName().equals(KSKRMSConstants.REPEATED_CREDITS_RYLE_TYPE)) {
                form.setRepeatableCreditPreviewTree(this.getPreviewTreeBuilder().buildTree(ruleEditor));
            } else if(ruleType.getName().equals(KSKRMSConstants.COURSE_RESTRICTS_RYLE_TYPE)) {
                form.setRestrictCreditPreviewTree(this.getPreviewTreeBuilder().buildTree(ruleEditor));
            }
        }
    }

    private String getAgendaCourseTypeId() {
        KrmsTypeDefinition krmsTypeDefinition = getKrmsTypeRepositoryService().getTypeByName(KSKRMSConstants.KS_SYS_NAMESPACE, KSKRMSConstants.COURSE_AGENDA_TYPE_ID);
        String agendaId = krmsTypeDefinition.getId();
        return agendaId;
    }

    private List<KeyValue> setAgendaTypes(AgendaManagementForm form, String agendaId) {
//        agendaId = form.getAgenda().getId();
        RequisiteAgendaTypeKeyValues requisiteAgendaTypeKeyValues = new RequisiteAgendaTypeKeyValues();
        List<KeyValue> agendaTypes = requisiteAgendaTypeKeyValues.getKeyValues(agendaId);
        for(KeyValue agendaType : agendaTypes) {
            if(agendaType.getValue().equals(KSKRMSConstants.CONTEXT_ENROLLMENT_ELIGIBILITY)) {
                form.setEnrollmentEligibility(agendaType.getValue());
            } else if(agendaType.getValue().equals(KSKRMSConstants.CONTEXT_CREDIT_CONTRAINTS)) {
                form.setCreditConstraints(agendaType.getValue());
            }
        }
        return agendaTypes;
    }

    private void setRuleTypes(AgendaManagementForm form, List<KeyValue> agendaTypeIds) {
        RuleTypeKeyValues ruleTypeKeyValues = new RuleTypeKeyValues();
        Map<String, Map<String, String>> keyValues = ruleTypeKeyValues.getKeyValues(agendaTypeIds);
        Map<String, String> ruleTypes = keyValues.get("ruleTypes");
        Map<String, String> ruleInstructions = keyValues.get("ruleInstructions");
        List<String> ruleTypeList = new ArrayList<String>(ruleTypes.values());

        for(String ruleType: ruleTypeList) {
            if(ruleType.equals(KSKRMSConstants.CONTEXT_STUD_ELIGIBILITY)) {
                form.setStudentEligAndPrereq(ruleType);
                form.setStudentEligAndPrereqInstruction(ruleInstructions.get(ruleType));
            } else if(ruleType.equals(KSKRMSConstants.CONTEXT_CORE_REQUISITE)) {
                form.setCorequisites(ruleType);
                form.setCorequisitesInstruction(ruleInstructions.get(ruleType));
            } else if(ruleType.equals(KSKRMSConstants.CONTEXT_RECOMMENDED_PREPARATION)) {
                form.setRecommendedPreparation(ruleType);
                form.setRecommendedPreparationInstruction(ruleInstructions.get(ruleType));
            } else if(ruleType.equals(KSKRMSConstants.CONTEXT_ANTI_REQUISITE)) {
                form.setAntirequisite(ruleType);
                form.setAntirequisiteInstruction(ruleInstructions.get(ruleType));
            } else if(ruleType.equals(KSKRMSConstants.CONTEXT_COURSE_RESTRICTS)) {
                form.setRestrictCredits(ruleType);
                form.setRestrictCreditsInstruction(ruleInstructions.get(ruleType));
            } else if(ruleType.equals(KSKRMSConstants.CONTEXT_REPEATED_CREDITS)) {
                form.setRepeatableCredit(ruleType);
                form.setRepeatableCreditInstruction(ruleInstructions.get(ruleType));
            }
        }
    }

    @Override
    public TemplateInfo getTemplateForType(String type) {
        return this.getTemplateRegistry().getTemplateForType(type);
    }

    @Override
    public String getTermSpecNameForType(String type) {
        return this.getTemplateRegistry().getTermSpecNameForType(type);
    }

    @Override
    public String getOperationForType(String type) {
        return this.getTemplateRegistry().getOperationForType(type);
    }

    @Override
    public String getValueForType(String type) {
        return this.getTemplateRegistry().getValueForType(type);
    }

    @Override
    public ComponentBuilder getComponentBuilderForType(String type) {
        return this.getTemplateRegistry().getComponentBuilderForType(type);
    }

    private AgendaBoService getAgendaBoService() {
        return KrmsRepositoryServiceLocator.getAgendaBoService();
    }

    private KrmsTypeRepositoryService getKrmsTypeRepositoryService() {
        return KrmsRepositoryServiceLocator.getKrmsTypeRepositoryService();
    }

    private RulePreviewTreeBuilder getPreviewTreeBuilder() {
        if (previewTreeBuilder == null) {
            previewTreeBuilder = new RulePreviewTreeBuilder();
            previewTreeBuilder.setRuleManagementService(this.getRuleManagementService());
        }
        return previewTreeBuilder;
    }

    public RuleManagementService getRuleManagementService() {
        if (ruleManagementService == null) {
            ruleManagementService = (RuleManagementService) GlobalResourceLoader.getService(new QName(KrmsConstants.Namespaces.KRMS_NAMESPACE_2_0, "ruleManagementService"));
        }
        return ruleManagementService;
    }

    private TemplateRegistry getTemplateRegistry() {
        if (templateRegistry == null) {
            templateRegistry = (TemplateRegistry) GlobalResourceLoader.getService(QName.valueOf("templateResolverMockService"));
        }
        return templateRegistry;
    }
}
