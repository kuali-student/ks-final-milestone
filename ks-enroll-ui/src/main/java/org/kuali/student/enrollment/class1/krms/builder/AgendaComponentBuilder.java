package org.kuali.student.enrollment.class1.krms.builder;

import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.uif.component.ComponentBase;
import org.kuali.rice.krad.uif.container.Group;
import org.kuali.rice.krad.uif.container.LinkGroup;
import org.kuali.rice.krad.uif.container.PageGroup;
import org.kuali.rice.krad.uif.field.Field;
import org.kuali.rice.krad.uif.field.FieldBase;
import org.kuali.rice.krad.uif.field.MessageField;
import org.kuali.rice.krad.uif.util.ComponentUtils;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krms.api.repository.RuleManagementService;
import org.kuali.rice.krms.api.repository.agenda.AgendaItemDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeRepositoryService;
import org.kuali.rice.krms.impl.repository.AgendaBoService;
import org.kuali.rice.krms.impl.repository.KrmsRepositoryServiceLocator;
import org.kuali.rice.krms.impl.repository.RuleBoService;
import org.kuali.rice.krms.keyvalues.RequisiteAgendaTypeKeyValues;
import org.kuali.rice.krms.keyvalues.RuleInstructionKeyValues;
import org.kuali.rice.krms.keyvalues.RuleTypeKeyValues;
import org.kuali.rice.krms.tree.RulePreviewTreeBuilder;
import org.kuali.student.enrollment.class1.krms.form.AgendaManagementForm;
import org.kuali.student.krms.util.KSKRMSConstants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: danie
 * Date: 3/7/13
 * Time: 12:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class AgendaComponentBuilder {

    private RulePreviewTreeBuilder previewTreeBuilder;
    private RuleManagementService ruleManagementService;

    private ComponentUtils componentUtils = new ComponentUtils();

    private PageGroup pageTemplate;
    private Group agendaSectionTemplate;
    private Group ruleSectionTemplate;
    private LinkGroup functionLinksTemplate;
    private LinkGroup addLinkTemplate;
    private MessageField ruleInstruction;

    public AgendaComponentBuilder(AgendaManagementForm form) {
        pageTemplate = (PageGroup) form.getView().getPage();
        agendaSectionTemplate = (Group) pageTemplate.getItems().get(0);
        ruleSectionTemplate = (Group) pageTemplate.getItems().get(1);
        functionLinksTemplate = (LinkGroup) pageTemplate.getItems().get(2);
        addLinkTemplate = (LinkGroup) pageTemplate.getItems().get(3);
        ruleInstruction = (MessageField) pageTemplate.getItems().get(4);
    }

    public List<Group> buildSections(List<KeyValue> agendaTypes, Map<String, Map<String, String>> ruleTypes, Map<String, String> ruleTypeInstruction, List<String> ruleIds) {
        List<Group> agendaSections = new ArrayList<Group>();
        for(KeyValue agendaType : agendaTypes) {
            Group agendaSection = ComponentUtils.copy(agendaSectionTemplate);
            agendaSection.setId(agendaType.getKey());
            agendaSection.getHeader().setHeaderText(agendaType.getValue());

//            List<Group> ruleSections = addRuleSections(agendaType.getKey(), ruleTypes.get(agendaType), ruleTypeInstruction, ruleIds);

            agendaSections.add(agendaSection);
        }
        return agendaSections;
    }

    private void addRuleSections(String agendaType, Map<String, String> ruleTypes, Map<String, String> ruleTypeInstructions, List<String> ruleIds) {
        List<Group> ruleSections = new ArrayList<Group>();
        for(String ruleType : ruleTypes.keySet()) {
            Group ruleSection = ComponentUtils.copy(ruleSectionTemplate);
            ruleSection.setId(ruleType);
            ruleSection.getHeader().setHeaderText(ruleTypes.get(ruleType));
            addInstruction(ruleSection, ruleTypeInstructions);
        }
    }

    private void addInstruction(Group ruleSection, Map<String, String> ruleTypeInstructions) {
        MessageField instruction = ComponentUtils.copy(ruleInstruction);
        instruction.setMessageText(ruleTypeInstructions.get(ruleSection.getId()));
    }
}
