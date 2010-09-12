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

import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.mvc.Callback;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ItemList<V extends ItemValue<V>> extends Composite implements HasValue<List<V>> {
    
    private List<V> value;
    protected SimplePanel mainPanel = new SimplePanel();
    private List<ValueChangeHandler<List<V>>> valueChangeHandlers =
        new ArrayList<ValueChangeHandler<List<V>>>(3);
    private boolean editable = true; // default to editable
    
    public ItemList() {
        super.initWidget(mainPanel);
    }
    
    @Override
    public List<V> getValue() {
        return value;
    }

    @Override
    public void setValue(List<V> value, boolean fireEvents) {
        this.value = value;
        redraw();
        if (fireEvents) {
            callHandlers();
        }
    }

    @Override
    public void setValue(List<V> value) {
        this.value = value;
        redraw();
        callHandlers();
    }
    
    private void callHandlers() {
        if (valueChangeHandlers != null) {
            MyGwtEvent myEvent = new MyGwtEvent(value);
            for (ValueChangeHandler<List<V>> handler : valueChangeHandlers) {
                handler.onValueChange(myEvent);
            }
        }
    }
    
    private void redraw() {
        VerticalPanel itemsPanel = new VerticalPanel();
        FlexTable itemTable = new FlexTable();
        int row = 0;
        mainPanel.clear();
        if (value != null) {
            for (V val : value) {
                val.setDeleteCallback(new Callback<V>() {
                    @Override
                    public void exec(V itemDeleted) {
                        value.remove(itemDeleted);
                        redraw();
                        callHandlers();
                    }
                });
                val.setEditable(isEditable());
                List<Widget> displayWidgets = val.generateDisplayWidgets();
                if (displayWidgets != null) {
                    int column = 0;
                    for (Widget displayWidget : displayWidgets) {
                        itemTable.setWidget(row, column, displayWidget);
                        column++;
                    }
                }
                itemsPanel.add(itemTable);
                row++;
            }
            mainPanel.setWidget(itemsPanel);
        }
    }
    
    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<List<V>> handler) {
        valueChangeHandlers.add(handler);
        return null;
    }
    
    public class MyGwtEvent extends ValueChangeEvent<List<V>> {
        public MyGwtEvent(List<V> value) {
            super(value);
        }
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }
    
}
