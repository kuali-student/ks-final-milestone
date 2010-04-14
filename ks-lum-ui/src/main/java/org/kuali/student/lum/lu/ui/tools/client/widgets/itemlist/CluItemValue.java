/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

package org.kuali.student.lum.lu.ui.tools.client.widgets.itemlist;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.buttons.KSLinkButton;
import org.kuali.student.common.ui.client.widgets.buttons.KSLinkButton.ButtonStyle;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;

public class CluItemValue implements ItemValue<CluItemValue> {
    
    private String id;
    private String name;
    private Callback<CluItemValue> deleteCallback;
    private boolean editable;

    @Override
    public List<Widget> generateDisplayWidgets() {
        List<Widget> displayWidgets = new ArrayList<Widget>(3);
        KSLinkButton deleteButton = new KSLinkButton("X", ButtonStyle.DELETE);
        KSLabel itemLabel = new KSLabel(getName());
        // TODO set style color here
//      itemLabel.getElement().getStyle().setProperty("background", "#E0E0E0");
        displayWidgets.add(itemLabel);
        if (isEditable()) {
            displayWidgets.add(deleteButton);

            if (deleteCallback != null) {
                deleteButton.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        deleteCallback.exec(CluItemValue.this);
                    }
                });
            }
        }
        return displayWidgets;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Callback<CluItemValue> getDeleteCallback() {
        return deleteCallback;
    }

    @Override
    public void setDeleteCallback(Callback<CluItemValue> deleteCallback) {
        this.deleteCallback = deleteCallback;
    }

    @Override
    public boolean equals(Object obj) {
        boolean result = false;
        CluItemValue item2 = null;
        
        if (obj == null) return false;
        item2 = (CluItemValue) obj;
        if (id == item2.getId() && 
                name != null && item2.getName() != null &&
                name.equals(item2.getName())) {
            result = true;
        }
        return result;
    }

    @Override
    public int hashCode() {
        int hashCode = -1;
        hashCode = id.hashCode() + name.hashCode();
        return hashCode;
    }

    @Override
    public boolean isEditable() {
        return editable;
    }

    @Override
    public void setEditable(boolean editable) {
        this.editable = editable;
    }
    
}
