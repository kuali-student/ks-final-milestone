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

package org.kuali.student.common.ui.client.widgets.list;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasName;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
import org.kuali.student.common.ui.client.configurable.mvc.WidgetConfigInfo;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.HasDataValue;
import org.kuali.student.common.ui.client.mvc.HasWidgetReadyCallback;
import org.kuali.student.common.ui.client.mvc.TranslatableValueWidget;
import org.kuali.student.common.ui.client.widgets.DataHelper;
import org.kuali.student.common.ui.client.widgets.HasInputWidget;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSItemLabel;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.layout.VerticalFlowPanel;
import org.kuali.student.common.ui.client.widgets.menus.KSListPanel;
import org.kuali.student.common.ui.client.widgets.menus.KSListPanel.ListType;
import org.kuali.student.common.ui.client.widgets.search.KSPicker;
import org.kuali.student.common.ui.client.widgets.search.SelectedResults;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Data.DataValue;
import org.kuali.student.core.assembly.data.Data.Property;
import org.kuali.student.core.assembly.data.Data.StringKey;
import org.kuali.student.core.assembly.data.Data.Value;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class KSSelectedList extends Composite implements HasDataValue, HasName, HasSelectionChangeHandlers, HasWidgetReadyCallback, TranslatableValueWidget, HasInputWidget {
    private static final String VALUE = "value";
    private static final String DISPLAY = "display";

    private String name;
    private boolean initialized;
    private VerticalFlowPanel mainPanel;
    private HorizontalPanel pickerPanel;
    private KSPicker picker;
    private KSButton addItemButton;
    private KSListPanel valuesPanel;
//    private List<SelectedValue> selectedValues = new ArrayList<SelectedValue>();
//    private List<SelectedValue> removedValues = new ArrayList<SelectedValue>();

    private List<KSItemLabel> selectedItems = new ArrayList<KSItemLabel>();
    private List<KSItemLabel> removedItems = new ArrayList<KSItemLabel>();
    public static ItemDataHelper itemDataHelper = new ItemDataHelper();

    private List<SelectionChangeHandler> selectionChangeHandlers = new ArrayList<SelectionChangeHandler>();
    private List<Callback<Widget>> widgetReadyCallbacks = new ArrayList<Callback<Widget>>();

    private WidgetConfigInfo config;

    public KSSelectedList(WidgetConfigInfo config) {
        this.config = config;
        mainPanel = new VerticalFlowPanel();
        initWidget(mainPanel);
        if (config.canEdit) {
            pickerPanel = new HorizontalPanel();
            pickerPanel.addStyleName("ks-selected-list-picker");
            addItemButton = new KSButton("Add to list", ButtonStyle.PRIMARY_SMALL);
            addItemButton.setEnabled(false);
            picker = new KSPicker(config);
            picker.setAdvancedSearchCallback(createAdvancedSearchCallback());
            picker.addValueChangeCallback(createPickerValueChangedCallback());

            addItemButton.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    DataValue v = (DataValue) picker.getValue();
                    Data d = v.get();

                    Iterator<Property> iter = d.realPropertyIterator();
                    while (iter.hasNext()) {
                        Property p = iter.next();
                        String s = p.getValue();
                        KSItemLabel selectedItem = createItem(s, picker.getDisplayValue());
                        addItem(selectedItem);
                    }
                    picker.clear();
                    addItemButton.setEnabled(false);

                }
            });

            pickerPanel.add(picker);
            pickerPanel.add(addItemButton);


            mainPanel.add(pickerPanel);
        }


        valuesPanel = new KSListPanel(ListType.UNORDERED);
        if(config.canEdit){
        	valuesPanel.setStyleName("ks-selected-list");
        }
        else{
        	valuesPanel.setStyleName("ks-selected-list-readOnly");
        }
        mainPanel.add(valuesPanel);
        initialized = true;
        widgetReady();
    }

    private void widgetReady() {
        for (Callback<Widget> callback : widgetReadyCallbacks) {
            callback.exec(this);
        }
    }

    private Callback<List<SelectedResults>> createAdvancedSearchCallback() {
        Callback<List<SelectedResults>> result = new Callback<List<SelectedResults>>() {
            public void exec(List<SelectedResults> results) {
                if (results.size() > 0) {
                    for (SelectedResults res : results) {
                        KSItemLabel item = createItem(res.getReturnKey(), res.getDisplayKey());
                        addItem(item, true);
                    }
                    picker.clear();
                    addItemButton.setEnabled(false);
                    selectionChanged();
                }
            }

        };
        return result;
    }

    private Callback<Value> createPickerValueChangedCallback() {
        return new Callback<Value>() {

            @Override
            public void exec(Value result) {
                String v = result.get();
                if (v != null && !v.isEmpty()) {
                    addItemButton.setEnabled(true);
                } else {
                    addItemButton.setEnabled(false);
                }
            }

        };
    }

    public void addItem(final KSItemLabel item) {
        addItem(item, true);
    }

    public void addItem(final KSItemLabel item, boolean fireChangeListeners) {
        if (removedItems.contains(item)) {
            removedItems.remove(item);
        }
        if (!selectedItems.contains(item)) {
            selectedItems.add(item);
        }
        valuesPanel.add(item);
        if (fireChangeListeners) {
            selectionChanged();
        }
        if (config.canEdit && fireChangeListeners) {
            item.setHighlighted(true);
            new Timer() {
                @Override
                public void run() {
                    item.setHighlighted(false);
                }
            }.schedule(5000);
        } else {
            item.removeHighlight();
        }
    }

    private void selectionChanged() {
        for (SelectionChangeHandler handler : selectionChangeHandlers) {
            handler.onSelectionChange(new SelectionChangeEvent(this));
        }
    }

    public void clear() {
        selectedItems = new ArrayList<KSItemLabel>();
        removedItems = new ArrayList<KSItemLabel>();
        valuesPanel.clear();
    }

    @Override
    public String getName() {

        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }


    @Override
    public HandlerRegistration addSelectionChangeHandler(final SelectionChangeHandler handler) {
        selectionChangeHandlers.add(handler);
        HandlerRegistration result = new HandlerRegistration() {
            @Override
            public void removeHandler() {
                selectionChangeHandlers.remove(handler);
            }
        };
        return result;
    }

    @Override
    public void addWidgetReadyCallback(Callback<Widget> callback) {
        widgetReadyCallbacks.add(callback);
    }

    @Override
    public boolean isInitialized() {
        return initialized;
    }

    @Override
    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }


    @Override
    public void addValueChangeCallback(Callback<Value> callback) {
        // TODO ryan - THIS METHOD NEEDS JAVADOCS

    }

    @Override
    public Value getValue() {
        Data data = new Data();
        for (KSItemLabel item : selectedItems) {
            data.add(item.getKey());
        }

        DataValue result = new DataValue(data);
        return result;
    }

    /**
     * Returns all selected values along with their translations in the _runtime data.
     *
     * @return
     */
    public Value getValueWithTranslations() {
        Data data = new Data();
        Data _runtimeData = new Data();

        for (KSItemLabel item : selectedItems) {
            data.add(item.getKey());
            Data displayData = new Data();
            displayData.set("id-translation", item.getDisplayText());
            _runtimeData.add(displayData);
        }
        data.set(new StringKey("_runtimeData"), _runtimeData);
        DataValue result = new DataValue(data);
        return result;
    }

    private KSItemLabel createItem(String value, String display) {
        Data itemData = toItemData(value, display);
        DataValue itemDataValue = new DataValue(itemData);
        KSItemLabel item = new KSItemLabel(KSSelectedList.this.config.canEdit,
                itemDataHelper);
        item.setValue(itemDataValue);
        item.addCloseHandler(new CloseHandler<KSItemLabel>() {
            @Override
            public void onClose(CloseEvent<KSItemLabel> event) {
                KSItemLabel itemToBeDeleted = event.getTarget();
                selectedItems.remove(itemToBeDeleted);
                removedItems.add(itemToBeDeleted);
                valuesPanel.remove(itemToBeDeleted);
            }
        });
        return item;
    }

    private Data toItemData(String value, String display) {
        Data data = new Data();
        data.set(VALUE, value);
        data.set(DISPLAY, display);
        return data;
    }

    @Override
    public void setValue(Value value) {
        clear();
        if (value != null) {
            Data data = ((DataValue) value).get();
            Iterator<Property> iter = data.realPropertyIterator();
            while (iter.hasNext()) {
                Property p = iter.next();
                String v = (String) p.getValue();
                //FIXME: do we need to do a search? is this method ever going to be called?
                KSItemLabel item = createItem(v, "Display: " + v);
                addItem(item);
            }
        }
    }

    public static class ItemDataHelper implements DataHelper {

        @Override
        public String parse(Data data) {
            String result;
            if (data == null) {
                return null;
            }
            result = (String) data.get(DISPLAY);
            return result;
        }

        @Override
        public String getKey(Data data) {
            String result;
            if (data == null) {
                return null;
            }
            result = (String) data.get(VALUE);
            return result;
        }

    }

    @Override
    public void setValue(String id, String translation) {
        clear();
        if (id != null && translation != null) {
            addItem(createItem(id, translation), false);
        }
    }

    @Override
    public void setValue(Map<String, String> translations) {
        clear();
        if (translations != null) {
            for (Entry<String, String> e : translations.entrySet()) {
                addItem(createItem(e.getKey(), e.getValue()), false);
            }
        }
    }

    @Override
    public Widget getInputWidget() {
        if (picker == null) {
            return valuesPanel;
        }
        return picker.getInputWidget();
    }
}
