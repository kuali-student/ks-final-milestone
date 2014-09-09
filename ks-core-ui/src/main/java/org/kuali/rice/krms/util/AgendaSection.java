/**
 * Copyright 2005-2013 The Kuali Foundation
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
package org.kuali.rice.krms.util;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.datadictionary.parse.BeanTagAttribute;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.component.BindingInfo;
import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.uif.container.CollectionGroup;
import org.kuali.rice.krad.uif.container.Group;
import org.kuali.rice.krad.uif.container.GroupBase;
import org.kuali.rice.krad.uif.field.DataField;
import org.kuali.rice.krad.uif.lifecycle.ViewLifecycle;
import org.kuali.rice.krad.uif.lifecycle.ViewLifecycleRestriction;
import org.kuali.rice.krad.uif.lifecycle.ViewLifecycleUtils;
import org.kuali.rice.krad.uif.util.LifecycleElement;
import org.kuali.rice.krad.uif.util.ObjectPropertyUtils;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krms.dto.AgendaEditor;
import org.kuali.rice.krms.service.RuleViewHelperService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kuali Student Team
 */
public class AgendaSection extends GroupBase {

    private String propertyName;
    private BindingInfo bindingInfo;

    private Group agendaPrototype;
    private Group rulePrototype;

    private AgendaBuilder agendaBuilder;

    public AgendaSection(){
        super();
    }

    @Override
    public void performInitialization(Object model) {
        setFieldBindingObjectPath(getBindingInfo().getBindingObjectPath());

        super.performInitialization(model);

        View view = ViewLifecycle.getView();

        if (bindingInfo != null) {
            bindingInfo.setDefaults(view, getPropertyName());
        }

        setCollectionPath();

    }

    protected void setCollectionPath() {
        // set static collection path on items
        String collectionPath = "";
        if (StringUtils.isNotBlank(getBindingInfo().getCollectionPath())) {
            collectionPath += getBindingInfo().getCollectionPath() + ".";
        }
        if (StringUtils.isNotBlank(getBindingInfo().getBindByNamePrefix())) {
            collectionPath += getBindingInfo().getBindByNamePrefix() + ".";
        }
        collectionPath += getBindingInfo().getBindingName();

        List<DataField> agendafields = ViewLifecycleUtils.getElementsOfTypeDeep(agendaPrototype, DataField.class);
        for (DataField collectionField : agendafields) {
            collectionField.getBindingInfo().setCollectionPath(collectionPath);
        }

        List<DataField> rulefields = ViewLifecycleUtils.getElementsOfTypeDeep(rulePrototype, DataField.class);
        for (DataField collectionField : rulefields) {
            collectionField.getBindingInfo().setCollectionPath(collectionPath);
        }
    }

    @Override
    public void performApplyModel(Object model, LifecycleElement parent) {
        // get the collection for this group from the model
        List<Object> modelCollection = ObjectPropertyUtils.getPropertyValue(model,
                this.getBindingInfo().getBindingPath());
        if (null == modelCollection)
        {
            modelCollection = new ArrayList<Object>();
        }
        View view = ViewLifecycle.getView();

        //Set the ruleviewhelperservice on the agendabuilder.
        this.getAgendaBuilder().setViewHelperService((RuleViewHelperService) view.getViewHelperService());

        // create agenda sections for each agenda
        List<Component> items = new ArrayList<Component>();
        for (int index = 0; index < modelCollection.size(); index++) {
            AgendaEditor agenda = (AgendaEditor) modelCollection.get(index);
            items.add(this.getAgendaBuilder().buildAgenda(agenda, index, this));
        }
        this.setItems(items);

        super.performApplyModel(model, parent);
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

    @ViewLifecycleRestriction(UifConstants.ViewPhases.INITIALIZE)
    @BeanTagAttribute(name = "agendaPrototype", type = BeanTagAttribute.AttributeType.SINGLEBEAN)
    public Group getAgendaPrototype() {
        return agendaPrototype;
    }

    public void setAgendaPrototype(Group agendaPrototype) {
        this.agendaPrototype = agendaPrototype;
    }

    @ViewLifecycleRestriction(UifConstants.ViewPhases.INITIALIZE)
    @BeanTagAttribute(name = "rulePrototype", type = BeanTagAttribute.AttributeType.SINGLEBEAN)
    public Group getRulePrototype() {
        return rulePrototype;
    }

    public void setRulePrototype(Group rulePrototype) {
        this.rulePrototype = rulePrototype;
    }
}
