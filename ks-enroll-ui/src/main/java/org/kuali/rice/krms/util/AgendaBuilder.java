package org.kuali.rice.krms.util;

import org.apache.commons.lang.BooleanUtils;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.uif.container.Group;
import org.kuali.rice.krad.uif.container.TreeGroup;
import org.kuali.rice.krad.uif.field.MessageField;
import org.kuali.rice.krad.uif.util.ComponentFactory;
import org.kuali.rice.krad.uif.util.ComponentUtils;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krms.dto.AgendaEditor;
import org.kuali.student.enrollment.class1.krms.dto.RuleEditor;

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
    private Map<String, List<String>> typeRelationsMap;

    private int agendaCounter;
    private int ruleCounter;

    public AgendaBuilder(View view) {
        this.view = view;
    }

    public void setTypeRelationsMap(Map<String, List<String>> typeRelationsMap) {
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

        Group group = (Group) ComponentFactory.getNewComponentInstance("KRMS-AgendaSection-Template");
        group.setHeaderText("Agenda " + agendaCounter);

        List<Component> components = new ArrayList<Component>();
        List<String> ruleTypes = typeRelationsMap.get(agenda.getTypeId());
        List<RuleEditor> ruleEditors = new ArrayList<RuleEditor>();
        for (String ruleType : ruleTypes) {

            // Add all existing rules of this type.
            boolean exist = false;
            for (RuleEditor rule : agenda.getRuleEditors()) {
                if (rule.getTypeId().equals(ruleType)) {
                    components.add(buildEditRule(rule));
                    exist = true;

                    ruleEditors.add(rule);
                }
            }

            // If the ruletype does not exist, add an empty rule section
            if (!exist) {
                components.add(buildAddRule(ruleType));
                ruleEditors.add(new RuleEditor());
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
    private Component buildEditRule(RuleEditor rule) {
        Group group = (Group) ComponentFactory.getNewComponentInstance("KRMS-RuleEdit-Template");
        group.setHeaderText(rule.getDescription() + ruleCounter);

        Group editSection = (Group) ComponentUtils.findComponentInList((List<Component>) group.getItems(), "KRMS-RuleEdit-Section");
        MessageField messageField = (MessageField) ComponentUtils.findComponentInList((List<Component>) editSection.getItems(), "KRMS-Instruction-EditMessage");
        messageField.setMessageText("Instructional text for rule:" + ruleCounter); // TODO: get test from type map.

        TreeGroup treeGroup = (TreeGroup) ComponentUtils.findComponentInList((List<Component>) editSection.getItems(), "KRMS-PreviewTree-Section");
        treeGroup.setPropertyName("agendas[" + agendaCounter + "].ruleEditors[" + ruleCounter + "].previewTree");  //TODO: create method to generate agenda.rule path.

        ruleCounter++;
        return group;
    }

    /**
     * This method dynamically builds a disclosure section to allow the user to add a new rule for this rule type.
     * @param description
     * @return
     */
    private Component buildAddRule(String description) {
        Group group = (Group) ComponentFactory.getNewComponentInstance("KRMS-RuleAdd-Template");
        group.setHeaderText(description + ruleCounter);

        Group editSection = (Group) ComponentUtils.findComponentInList((List<Component>) group.getItems(), "KRMS-RuleAdd-Section");
        MessageField messageField = (MessageField) ComponentUtils.findComponentInList((List<Component>) editSection.getItems(), "KRMS-Instruction-AddMessage");
        messageField.setMessageText("Instructional text for rule:" + ruleCounter);   // TODO: get test from type map.

        ruleCounter++;
        return group;
    }

    private Properties buildAgendaURLParameters(RuleEditor ruleEditor, String methodToCall){
        Properties props = new Properties();
        props.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, methodToCall);
        props.put(KRADConstants.OVERRIDE_KEYS, "cluId");
        props.put("viewName", "AgendaWithRuleView");
        props.put("viewTypeName", "MAINTENANCE");
        props.put("cluId", "39b47c39-451a-4aff-9c87-47092e8627f0");
        props.put(KRADConstants.DATA_OBJECT_CLASS_ATTRIBUTE, RuleEditor.class.getName());
        props.put(UifConstants.UrlParams.SHOW_HOME, BooleanUtils.toStringTrueFalse(false));
        return props;
    }
}
