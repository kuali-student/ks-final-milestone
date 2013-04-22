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

    private AlphaIterator alphaIterator = new AlphaIterator();

    public AgendaBuilder(View view) {
        this.view = view;
    }

    public void setTypeRelationsMap(Map<String, AgendaTypeInfo> typeRelationsMap) {
        this.typeRelationsMap = typeRelationsMap;
    }

    public List<Component> build(List<AgendaEditor> agendas){
        List<Component> components = new ArrayList<Component>();

        List<AgendaTypeInfo> agendaTypeInfos = new ArrayList<AgendaTypeInfo>(typeRelationsMap.values());
        for (AgendaTypeInfo agendaTypeInfo : agendaTypeInfos) {
            boolean exist = false;
            for (AgendaEditor agenda : agendas) {
                if (agenda.getTypeId().equals(agendaTypeInfo.getId())) {
                    components.add(this.buildAgenda(agenda));
                    exist = true;
                }
            }
            if (!exist) {
                AgendaEditor emptyAgenda = new AgendaEditor();
                emptyAgenda.setTypeId(agendaTypeInfo.getId());
                components.add(this.buildAgenda(emptyAgenda));
            }
        }

        return components;
    }

    /**
     * This method dynamically build the components on the screen to render an angenda.
     *
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
            if (agenda.getRuleEditors() != null) {
                for (RuleEditor rule : agenda.getRuleEditors()) {
                    if (rule.getTypeId().equals(ruleType.getId()) && (!rule.isDummy())) {
                        rule.setKey((String)alphaIterator.next());
                        rule.setDescription(ruleType.getDescription());
                        rule.setRuleInstruction(ruleType.getInstruction());
                        components.add(buildRule(rule, ruleType, this.buildEditRuleSection(rule, ruleType)));
                        exist = true;

                        ruleEditors.add(rule);
                    }
                }
            }

            // If the ruletype does not exist, add an empty rule section
            if (!exist) {
                RuleEditor ruleEditor = new RuleEditor();
                components.add(buildRule(ruleEditor, ruleType, this.buildAddRuleSection(ruleEditor, ruleType)));

                ruleEditor.setKey((String)alphaIterator.next());
                ruleEditor.setDummy(true);
                ruleEditor.setTypeId(ruleType.getId());
                ruleEditor.setDescription(ruleType.getDescription());
                ruleEditor.setRuleInstruction(ruleType.getInstruction());
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
     *
     * @param rule
     * @return
     */
    protected Component buildRule(RuleEditor rule, RuleTypeInfo ruleTypeInfo, Group ruleSection) {
        Group group = (Group) ComponentFactory.getNewComponentInstance("KRMS-Rule-Template");
        group.setHeaderText(ruleTypeInfo.getDescription());

        //Add edit container to disclosure section
        List<Component> items = new ArrayList<Component>();
        items.add(ruleSection);
        group.setItems(items);

        ruleCounter++;
        return group;
    }

    protected Group buildEditRuleSection(RuleEditor rule, RuleTypeInfo ruleTypeInfo){
        Group editSection = (Group) ComponentFactory.getNewComponentInstance("KRMS-RuleEdit-Section");
        LinkGroup links = (LinkGroup) ComponentUtils.findComponentInList((List<Component>) editSection.getItems(), "KRSM-RuleEdit-ActionLinks");
        List<Action> actionLinks = (List<Action>) links.getItems();
        for (Action actionLink : actionLinks) {
            actionLink.getActionParameters().put("ruleKey", rule.getKey());
        }
        MessageField messageField = (MessageField) ComponentUtils.findComponentInList((List<Component>) editSection.getItems(), "KRMS-Instruction-EditMessage");
        messageField.setMessageText(ruleTypeInfo.getInstruction());

        Group sectionGroup = (Group) ComponentUtils.findComponentInList((List<Component>) editSection.getItems(), "KRMS-PreviewTree-Group");
        List<TreeGroup> treeGroups = ComponentUtils.getComponentsOfType((List<Component>) sectionGroup.getItems(), TreeGroup.class);
        if ((treeGroups != null) && (treeGroups.size() > 0)) {
            treeGroups.get(0).setPropertyName("agendas[" + agendaCounter + "].ruleEditors[" + ruleCounter + "].viewTree");
        }

        return editSection;
    }

    /**
     * This method dynamically builds a disclosure section to allow the user to add a new rule for this rule type.
     *
     * @param ruleTypeInfo
     * @return
     */
    protected Group buildAddRuleSection(RuleEditor ruleEditor, RuleTypeInfo ruleTypeInfo) {
        Group addSection = (Group) ComponentFactory.getNewComponentInstance("KRMS-RuleAdd-Section");
        LinkGroup links = (LinkGroup) ComponentUtils.findComponentInList((List<Component>) addSection.getItems(), "KRMS-RuleAdd-ActionLink");
        List<Action> actionLinks = (List<Action>) links.getItems();
        for (Action actionLink : actionLinks) {
            actionLink.getActionParameters().put("ruleType", ruleTypeInfo.getId());
        }
        MessageField messageField = (MessageField) ComponentUtils.findComponentInList((List<Component>) addSection.getItems(), "KRMS-Instruction-AddMessage");
        messageField.setMessageText(ruleTypeInfo.getInstruction());

        return addSection;
    }

}
