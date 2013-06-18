package org.kuali.rice.krms.util;

import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.uif.component.DataBinding;
import org.kuali.rice.krad.uif.container.Group;
import org.kuali.rice.krad.uif.container.TreeGroup;
import org.kuali.rice.krad.uif.element.Action;
import org.kuali.rice.krad.uif.field.DataField;
import org.kuali.rice.krad.uif.field.MessageField;
import org.kuali.rice.krad.uif.service.ViewHelperService;
import org.kuali.rice.krad.uif.util.ComponentFactory;
import org.kuali.rice.krad.uif.util.ComponentUtils;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krms.dto.*;
import org.kuali.rice.krms.service.RuleViewHelperService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: danie
 * Date: 3/7/13
 * Time: 12:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class AgendaBuilder {

    public static final String AGENDA = "_agenda";
    public static final String RULE = "_rule";

    private RuleViewHelperService viewHelperService;

    /**
     * This method dynamically build the components on the screen to render an angenda.
     *
     * @param agenda
     * @return
     */
    public Component buildAgenda(AgendaEditor agenda, int index, AgendaSection agendaSection) {
        String agendaSuffix = AGENDA + Integer.toString(index);
        Group group = ComponentUtils.copy(agendaSection.getAgendaPrototype(), agendaSuffix);
        group.setHeaderText(agenda.getAgendaTypeInfo().getDescription());

        String bindingPrefix = "agendas[" + index + "].";
        List<Component> components = new ArrayList<Component>();
        for (RuleEditor rule : agenda.getRuleEditors().values()) {
            components.add(buildRule(rule, bindingPrefix, agendaSection));
        }

        group.setItems(components);

        return group;
    }

    /**
     * This method dynamically builds a disclosure section for each rule that already exists.
     *
     * @param rule
     * @return
     */
    protected Component buildRule(RuleEditor rule, String bindingPrefix, AgendaSection agendaSection) {
        String ruleSuffix = RULE + rule.getKey();
        Group group = ComponentUtils.copy(agendaSection.getRulePrototype(), ruleSuffix);
        group.setHeaderText(rule.getRuleTypeInfo().getDescription());

        //Set the rule key on the action links.
        List<Action> actionLinks = ComponentUtils.getComponentsOfTypeDeep(group, Action.class);
        for (Action actionLink : actionLinks) {
            actionLink.getActionParameters().put("ruleKey", rule.getKey());
        }

        ComponentUtils.updateContextForLine(group, rule, 0, ruleSuffix);

        String bindingPath = bindingPrefix + "ruleEditors[" + rule.getKey() + "].";
        this.setPropertyBindingPaths(group, bindingPath);

        if (rule.isDummy() && rule.getParent() != null) {
            GlobalVariables.getMessageMap().putInfoForSectionId(group.getId(), "info.krms.agenda.rule.hasparent");
        } else if (!this.getViewHelperService().compareRules(rule)) {
            GlobalVariables.getMessageMap().putInfoForSectionId(group.getId(), "info.krms.agenda.rule.changed");
        }

        return group;
    }

    protected void setPropertyBindingPaths(Group group, String bindingPath) {

        List<DataField> dataFields = ComponentUtils.getComponentsOfTypeDeep(group.getItems(), DataField.class);
        for (DataField collectionField : dataFields) {
            collectionField.getBindingInfo().setBindingName(bindingPath + collectionField.getBindingInfo().getBindingName());
        }

        List<TreeGroup> treeFields = ComponentUtils.getComponentsOfTypeDeep(group.getItems(), TreeGroup.class);
        for (TreeGroup collectionField : treeFields) {
            collectionField.getBindingInfo().setBindingName(bindingPath + collectionField.getBindingInfo().getBindingName());
        }
    }

    public RuleViewHelperService getViewHelperService() {
        return viewHelperService;
    }

    public void setViewHelperService(RuleViewHelperService viewHelperService) {
        this.viewHelperService = viewHelperService;
    }
}
