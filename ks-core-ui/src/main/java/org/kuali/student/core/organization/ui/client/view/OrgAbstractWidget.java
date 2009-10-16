/*
 * Copyright 2009 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 * 
 * http://www.osedu.org/licenses/ECL-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.core.organization.ui.client.view;

import org.kuali.student.common.ui.client.dto.HelpInfo;
import org.kuali.student.common.ui.client.event.SaveEvent;
import org.kuali.student.common.ui.client.event.SaveHandler;
import org.kuali.student.common.ui.client.widgets.KSDisclosureSection;
import org.kuali.student.common.ui.client.widgets.forms.KSFormField;
import org.kuali.student.common.ui.client.widgets.forms.KSFormLayoutPanel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasName;
import com.google.gwt.user.client.ui.Widget;

abstract class OrgAbstractWidget extends Composite implements ClickHandler {
    protected KSDisclosureSection w;
    protected String orgId;

    public OrgAbstractWidget() {
        this(null);
    }

    public OrgAbstractWidget(KSDisclosureSection widget) {
        if(widget == null)
            widget = new KSDisclosureSection("Organization Information", null, false);
        super.initWidget(w = widget);
    }

    protected abstract void save();

    protected static void addFormField(Widget w, String label, String name, KSFormLayoutPanel formPanel) {
        KSFormField ff = new KSFormField();
        ff.setLabelText(label);
        ff.setWidget(w);
        if (w instanceof HasName)
            ((HasName) w).setName(name);
        ff.setHelpInfo(new HelpInfo());
        ff.setName(name);

        formPanel.addFormField(ff);
    }
    
    @Override
    public void onClick(ClickEvent event) {
        save();
    }

    public HandlerRegistration addSaveHandler(SaveHandler handler) {
        return addHandler(handler, SaveEvent.TYPE);
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
}
