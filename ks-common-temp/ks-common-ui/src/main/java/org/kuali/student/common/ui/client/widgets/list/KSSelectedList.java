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

import org.kuali.student.common.assembly.data.Data;
import org.kuali.student.common.assembly.data.Data.DataValue;
import org.kuali.student.common.assembly.data.Data.Property;
import org.kuali.student.common.assembly.data.Data.StringKey;
import org.kuali.student.common.assembly.data.Data.Value;
import org.kuali.student.common.ui.client.configurable.mvc.WidgetConfigInfo;
import org.kuali.student.common.ui.client.mvc.*;
import org.kuali.student.common.ui.client.util.UtilConstants;
import org.kuali.student.common.ui.client.widgets.DataHelper;
import org.kuali.student.common.ui.client.widgets.HasInputWidget;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSItemLabel;
import org.kuali.student.common.ui.client.widgets.layout.VerticalFlowPanel;
import org.kuali.student.common.ui.client.widgets.menus.KSListPanel;
import org.kuali.student.common.ui.client.widgets.menus.KSListPanel.ListType;
import org.kuali.student.common.ui.client.widgets.search.KSPicker;
import org.kuali.student.common.ui.client.widgets.search.SelectedResults;
import org.kuali.student.common.ui.client.widgets.suggestbox.KSSuggestBox;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class KSSelectedList extends Composite implements HasDataValue, HasName, HasSelectionChangeHandlers, HasWidgetReadyCallback, TranslatableValueWidget, HasInputWidget, HasFocusLostCallbacks, HasCrossConstraints {
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

    private final List<SelectionChangeHandler> selectionChangeHandlers = new ArrayList<SelectionChangeHandler>();
    private final List<Callback<Widget>> widgetReadyCallbacks = new ArrayList<Callback<Widget>>();
    private boolean hasDetails = false;

    private WidgetConfigInfo config;

    public KSSelectedList(WidgetConfigInfo config, boolean hasDetails) {
        init(config, hasDetails);
    }

    public KSSelectedList(WidgetConfigInfo config) {
        init(config, false);
    }

    public WidgetConfigInfo getConfig() {
        return config;
    }

    private void init(WidgetConfigInfo config, final boolean hasDetails) {
        this.config = config;
        this.hasDetails = hasDetails;
        mainPanel = new VerticalFlowPanel();
        initWidget(mainPanel);
        if (config.canEdit) {
            pickerPanel = new HorizontalPanel();
            pickerPanel.addStyleName("ks-selected-list-picker");
            addItemButton = new KSButton("Add to list", ButtonStyle.SECONDARY_SMALL);
            addItemButton.setEnabled(false);
            picker = new KSPicker(config);
            picker.setAdvancedSearchCallback(createAdvancedSearchCallback());
            addSelectionChangeHandler();

            addItemButton.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                	Value v = picker.getValue();
                	if(v instanceof DataValue){
	                    Data d = ((DataValue) picker.getValue()).get();
	                    
	                    Iterator<Property> iter = d.realPropertyIterator();
	                    while (iter.hasNext()) {
	                        Property p = iter.next();
	                        String s = p.getValue();
	                        KSItemLabel selectedItem = createItem(s, picker.getDisplayValue(),
	                                hasDetails);
	                        addItem(selectedItem);
	                    }
                	}else{
                		String s = v.get();
                        KSItemLabel selectedItem = createItem(s, picker.getDisplayValue(),
	                                hasDetails);
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
        if (config.canEdit) {
            valuesPanel.setStyleName("ks-selected-list");
        } else {
            valuesPanel.setStyleName("ks-selected-list-readOnly");
        }
        mainPanel.add(valuesPanel);
        initialized = true;
        widgetReady();
    }

    public KSListPanel separateValuesPanel() {
        mainPanel.remove(valuesPanel);
        return valuesPanel;
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
                        KSItemLabel item = createItem(res.getReturnKey(), res.getDisplayKey(), hasDetails);
                        addItem(item, false);
                    }
                    picker.clear();
                    addItemButton.setEnabled(false);
                    selectionChanged();
                }
            }

        };
        return result;
    }

    /**
     * Adds handler when suggest box selection change event is fired and enables/disables the the
     * Add item button accordingly.
     */
    private void addSelectionChangeHandler() {
        if (picker.getInputWidget() instanceof KSSuggestBox) {
            KSSuggestBox suggestBox = (KSSuggestBox) picker.getInputWidget();
            suggestBox.addSelectionChangeHandler(new SelectionChangeHandler() {
                @Override
                public void onSelectionChange(SelectionChangeEvent event) {
                    //Compare the user entered value in picker's input textbox with the current display value
                    //from the picker suggest list, if they are the same (ie. user has selected item from list)
                    //then enable the add to list button. NOTE: This comparison should not be required if the picker
                    //ONLY fired selection change event when an actual selection change took place, but doesn't seem
                    //to be doing that.
                    String userValue = ((KSSuggestBox) picker.getInputWidget()).getText();
                    String displayValue = picker.getDisplayValue();
                    boolean enabled = displayValue != null && !displayValue.isEmpty() && displayValue.equals(userValue);
                    addItemButton.setEnabled(enabled);
                }
            });
        } else if(picker.getInputWidget() instanceof KSDropDown){
        	KSDropDown dropDown = (KSDropDown) picker.getInputWidget();
        	dropDown.addSelectionChangeHandler(new SelectionChangeHandler() {
                @Override
                public void onSelectionChange(SelectionChangeEvent event) {
                	//Enable if not null
                    String displayValue = picker.getDisplayValue();
                    boolean enabled = displayValue != null && !displayValue.isEmpty();
                    addItemButton.setEnabled(enabled);
                }
            });
        }
    }

    public void addItem(String value, String display) {
        addItem(createItem(value, display, hasDetails));
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
        if (picker.getDisplayValue().equals(UtilConstants.IMPOSSIBLE_CHARACTERS)) {
            data.add(UtilConstants.IMPOSSIBLE_CHARACTERS);
        }
        DataValue result = new DataValue(data);
        return result;
    }

    public List<KSItemLabel> getSelectedItems() {
        return selectedItems;
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
        if (picker.getDisplayValue().equals(UtilConstants.IMPOSSIBLE_CHARACTERS)) {
            data.add(UtilConstants.IMPOSSIBLE_CHARACTERS);
            Data displayData = new Data();
            displayData.set("id-translation", UtilConstants.IMPOSSIBLE_CHARACTERS);
            _runtimeData.add(displayData);
        }
        data.set(new StringKey("_runtimeData"), _runtimeData);
        DataValue result = new DataValue(data);
        return result;
    }

    private KSItemLabel createItem(String value, String display, boolean hasDetails) {
        Data itemData = toItemData(value, display);
        DataValue itemDataValue = new DataValue(itemData);
        KSItemLabel item = new KSItemLabel(KSSelectedList.this.config.canEdit, hasDetails,
                itemDataHelper);
        item.setValue(itemDataValue);
        item.addCloseHandler(new CloseHandler<KSItemLabel>() {
            @Override
            public void onClose(CloseEvent<KSItemLabel> event) {
                KSItemLabel itemToBeDeleted = event.getTarget();
                selectedItems.remove(itemToBeDeleted);
                removedItems.add(itemToBeDeleted);
                valuesPanel.remove(itemToBeDeleted);
                selectionChanged();
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
                KSItemLabel item = createItem(v, "Display: " + v, hasDetails);
                addItem(item);
            }
        }
    }

    @Override
    public void addFocusLostCallback(Callback<Boolean> callback) {
        if (picker != null)
            picker.addFocusLostCallback(callback);
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
            addItem(createItem(id, translation, hasDetails), false);
        }
    }

    @Override
    public void setValue(Map<String, String> translations) {
        clear();
        if (translations != null) {
            for (Entry<String, String> e : translations.entrySet()) {
                addItem(createItem(e.getKey(), e.getValue(), hasDetails), false);
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

	@Override
	public HashSet<String> getCrossConstraints() {
		if(picker!=null){
			return picker.getCrossConstraints();
		}
		return new HashSet<String>();
	}

	@Override
	public void reprocessWithUpdatedConstraints() {
		if(picker!=null){
			picker.reprocessWithUpdatedConstraints();
		}
	}

	public KSButton getAddItemButton() {
		return addItemButton;
	}

	public VerticalFlowPanel getMainPanel() {
		return mainPanel;
	}

	public KSPicker getPicker() {
		return picker;
	}

}
