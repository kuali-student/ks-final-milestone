package org.kuali.rice.krms.util;

import org.kuali.rice.krad.uif.component.BindingInfo;
import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.uif.container.Group;
import org.kuali.rice.krad.uif.container.TreeGroup;
import org.kuali.rice.krad.uif.field.DataField;
import org.kuali.rice.krad.uif.util.ComponentUtils;
import org.kuali.rice.krad.uif.util.ObjectPropertyUtils;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krms.dto.AgendaEditor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: SW Genis
 * Date: 2013/05/21
 * Time: 3:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class AgendaSection extends Group {

    private String propertyName;
    private BindingInfo bindingInfo;

    private Group agendaPrototype;
    private Group rulePrototype;

    private AgendaBuilder agendaBuilder;

    @Override
    public void performInitialization(View view, Object model) {
        setFieldBindingObjectPath(getBindingInfo().getBindingObjectPath());

        super.performInitialization(view, model);

        if (bindingInfo != null) {
            bindingInfo.setDefaults(view, getPropertyName());
        }

    }

    @Override
    public void performApplyModel(View view, Object model, Component parent) {
        // get the collection for this group from the model
        List<Object> modelCollection = ObjectPropertyUtils.getPropertyValue(model,
                this.getBindingInfo().getBindingPath());

        // create agenda sections for each agenda
        List<Component> items = new ArrayList<Component>();
        for (int index = 0; index < modelCollection.size(); index++) {
            AgendaEditor agenda = (AgendaEditor) modelCollection.get(index);
            items.add(this.getAgendaBuilder().buildAgenda(agenda, index, this));
        }
        this.setItems(items);

        super.performApplyModel(view, model, parent);
    }

    @Override
    public List<Component> getComponentsForLifecycle() {
        return super.getComponentsForLifecycle();
    }

    @Override
    public List<Component> getComponentPrototypes() {
        List<Component> components = super.getComponentPrototypes();
        components.add(this.getAgendaPrototype());
        components.add(this.getRulePrototype());

        return components;
    }

    public AgendaBuilder getAgendaBuilder() {
        if (this.agendaBuilder == null) {
            this.agendaBuilder = new AgendaBuilder();
        }
        return this.agendaBuilder;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public BindingInfo getBindingInfo() {
        return bindingInfo;
    }

    public void setBindingInfo(BindingInfo bindingInfo) {
        this.bindingInfo = bindingInfo;
    }

    public Group getAgendaPrototype() {
        return agendaPrototype;
    }

    public void setAgendaPrototype(Group agendaPrototype) {
        this.agendaPrototype = agendaPrototype;
    }

    public Group getRulePrototype() {
        return rulePrototype;
    }

    public void setRulePrototype(Group rulePrototype) {
        this.rulePrototype = rulePrototype;
    }
}
