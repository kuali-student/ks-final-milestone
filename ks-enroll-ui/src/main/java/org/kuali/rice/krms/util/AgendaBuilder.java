package org.kuali.rice.krms.util;

import org.apache.commons.lang.BooleanUtils;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.uif.container.Group;
import org.kuali.rice.krad.uif.container.LinkGroup;
import org.kuali.rice.krad.uif.container.TreeGroup;
import org.kuali.rice.krad.uif.element.Action;
import org.kuali.rice.krad.uif.element.Link;
import org.kuali.rice.krad.uif.field.MessageField;
import org.kuali.rice.krad.uif.util.ComponentFactory;
import org.kuali.rice.krad.uif.util.ComponentUtils;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krms.dto.AgendaEditor;
import org.kuali.rice.krms.dto.AgendaTypeInfo;
import org.kuali.rice.krms.dto.RuleTypeInfo;
import org.kuali.rice.krms.dto.RuleEditor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: danie
 * Date: 3/7/13
 * Time: 12:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class AgendaBuilder {

    private View view;
    private Map<String, AgendaTypeInfo> typeRelationsMap;

    private int agendaCounter;
    private int ruleCounter;

    public AgendaBuilder(View view) {
        this.view = view;
    }

    public void setTypeRelationsMap(Map<String, AgendaTypeInfo> typeRelationsMap) {
        this.typeRelationsMap = typeRelationsMap;
    }

    /**
     * This method dynamically build the components on the screen to render an angenda.
     * @param agenda
     * @return
     */
    public Component buildAgenda(AgendaEditor agenda) {
        // Reset the rule counter.
        ruleCounter = 0;

        AgendaTypeInfo agendaType = typeRelationsMap.get(agenda.getTypeId());
        Group group = (Group) ComponentFactory.getNewComponentInstance("KRMS-AgendaSection-Template");
        group.setHeaderText(agendaType.getDescription());

        List<Component> components = new ArrayList<Component>();
        List<RuleEditor> ruleEditors = new ArrayList<RuleEditor>();
        for (RuleTypeInfo ruleType : agendaType.getRuleTypes()) {

            // Add all existing rules of this type.
            boolean exist = false;
            if(agenda.getRuleEditors() != null) {
                for (RuleEditor rule : agenda.getRuleEditors()) {
                    if (rule.getTypeId().equals(ruleType.getId()) && rule.getId() != null) {
                        components.add(buildEditRule(rule, ruleType));
                        exist = true;

                        ruleEditors.add(rule);
                    }
                }

                // If the ruletype does not exist, add an empty rule section
                if (!exist) {
                    components.add(buildAddRule(ruleType));
                    RuleEditor ruleEditor = new RuleEditor();
                    ruleEditor.setTypeId(ruleType.getId());
                    ruleEditors.add(ruleEditor);
                }
            } else {
                components.add(buildAddRule(ruleType));
                RuleEditor ruleEditor = new RuleEditor();
                ruleEditor.setTypeId(ruleType.getId());
                ruleEditors.add(ruleEditor);
            }
        }

        group.setItems(components);
        view.assignComponentIds(group);

        agendaCounter++;
        agenda.setRuleEditors(ruleEditors);
        return group;
    }

    /**
     * This method dynamically builds a disclosure section for each rule that already exists.
     * @param rule
     * @return
     */
    protected Component buildEditRule(RuleEditor rule, RuleTypeInfo ruleTypeInfo) {
        Group group = (Group) ComponentFactory.getNewComponentInstance("KRMS-RuleEdit-Template");
        group.setHeaderText(ruleTypeInfo.getDescription());

        Group editSection = (Group) ComponentUtils.findComponentInList((List<Component>) group.getItems(), "KRMS-RuleEdit-Section");
        LinkGroup links = (LinkGroup) ComponentUtils.findComponentInList((List<Component>) editSection.getItems(), "KRSM-RuleEdit-ActionLinks");
        List<Action> actionLinks = (List<Action>) links.getItems();
        for(Action actionLink : actionLinks) {
            actionLink.getActionParameters().put("ruleId", rule.getId());
        }
        MessageField messageField = (MessageField) ComponentUtils.findComponentInList((List<Component>) editSection.getItems(), "KRMS-Instruction-EditMessage");
        messageField.setMessageText(ruleTypeInfo.getInstruction());

        Group sectionGroup = (Group) ComponentUtils.findComponentInList((List<Component>) editSection.getItems(), "KRMS-PreviewTree-Group");
        List<TreeGroup> treeGroups = ComponentUtils.getComponentsOfType((List<Component>) sectionGroup.getItems(), TreeGroup.class);
        if ((treeGroups != null) && (treeGroups.size() > 0)){
            treeGroups.get(0).setPropertyName("agendas[" + agendaCounter + "].ruleEditors[" + ruleCounter + "].previewTree");
        }

        ruleCounter++;
        return group;
    }

    /**
     * This method dynamically builds a disclosure section to allow the user to add a new rule for this rule type.
     * @param ruleTypeInfo
     * @return
     */
    protected Component buildAddRule(RuleTypeInfo ruleTypeInfo) {
        Group group = (Group) ComponentFactory.getNewComponentInstance("KRMS-RuleAdd-Template");
        group.setHeaderText(ruleTypeInfo.getDescription());

        Group editSection = (Group) ComponentUtils.findComponentInList((List<Component>) group.getItems(), "KRMS-RuleAdd-Section");
        LinkGroup links = (LinkGroup) ComponentUtils.findComponentInList((List<Component>) editSection.getItems(), "KRMS-RuleAdd-ActionLink");
        List<Action> actionLinks = (List<Action>) links.getItems();
        for(Action actionLink : actionLinks) {
            actionLink.getActionParameters().put("ruleType", ruleTypeInfo.getId());
        }
        MessageField messageField = (MessageField) ComponentUtils.findComponentInList((List<Component>) editSection.getItems(), "KRMS-Instruction-AddMessage");
        messageField.setMessageText(ruleTypeInfo.getInstruction());

        ruleCounter++;
        return group;
    }

    public Properties buildAgendaURLParameters(RuleEditor ruleEditor, String methodToCall){
        Properties props = new Properties();
        props.put("viewTypeName", "MAINTENANCE");
        props.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, methodToCall);
        props.put(KRADConstants.DATA_OBJECT_CLASS_ATTRIBUTE,"org.kuali.student.enrollment.class1.krms.dto.EnrolRuleEditor");
        props.put("viewName", "EnrolRuleEditView");
        props.put("id", ruleEditor.getId());
        props.put(UifConstants.UrlParams.SHOW_HOME, BooleanUtils.toStringTrueFalse(false));
        return props;
    }
}
