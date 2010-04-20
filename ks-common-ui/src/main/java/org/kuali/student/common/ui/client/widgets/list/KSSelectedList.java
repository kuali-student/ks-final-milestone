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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.kuali.student.common.ui.client.configurable.mvc.WidgetConfigInfo;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.HasDataValue;
import org.kuali.student.common.ui.client.mvc.HasWidgetReadyCallback;
import org.kuali.student.common.ui.client.mvc.TranslatableValueWidget;
import org.kuali.student.common.ui.client.util.Elements;
import org.kuali.student.common.ui.client.widgets.KSButton;
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
import org.quartz.utils.StringKeyDirtyFlagMap;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.HasCloseHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasName;
import com.google.gwt.user.client.ui.Widget;

public class KSSelectedList extends Composite implements HasDataValue, HasName, HasSelectionChangeHandlers, HasWidgetReadyCallback, TranslatableValueWidget {
    private String name;
    private boolean initialized;
    private VerticalFlowPanel mainPanel;
    private FlowPanel pickerPanel;
    private KSPicker picker;
    private KSButton addItemButton;
    private KSListPanel valuesPanel;
    private List<SelectedValue> selectedValues = new ArrayList<SelectedValue>();
    private List<SelectedValue> removedValues = new ArrayList<SelectedValue>();
    
    private List<SelectionChangeHandler> selectionChangeHandlers = new ArrayList<SelectionChangeHandler>();
    private List<Callback<Widget>> widgetReadyCallbacks = new ArrayList<Callback<Widget>>();
    
    private WidgetConfigInfo config;
    public KSSelectedList(WidgetConfigInfo config) {
        this.config = config;
        mainPanel = new VerticalFlowPanel();
        initWidget(mainPanel);
        if(config.canEdit) {
            pickerPanel = new FlowPanel();
            pickerPanel.addStyleName("ks-selected-list-picker");
            addItemButton = new KSButton("add to list");
            addItemButton.setEnabled(false);
            picker = new KSPicker(config);
            picker.setAdvancedSearchCallback(createAdvancedSearchCallback());
            picker.addValueChangeCallback(createPickerValueChangedCallback());
            
            addItemButton.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    DataValue v = (DataValue)picker.getValue();
                    Data d = v.get();
                    
                    Iterator<Property> iter = d.realPropertyIterator();
                    while(iter.hasNext()) {
                        Property p = iter.next();
                        String s = p.getValue();
                        SelectedValue selectedValue = new SelectedValue(s, picker.getDisplayValue());
                        addValue(selectedValue);
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
        valuesPanel.setStyleName("ks-selected-list");
        mainPanel.add(valuesPanel);
        initialized = true;
        widgetReady();
    }
    
    private void widgetReady() {
        for(Callback<Widget> callback : widgetReadyCallbacks) {
            callback.exec(this);
        }
    }
    private Callback<List<SelectedResults>> createAdvancedSearchCallback() {
        Callback<List<SelectedResults>> result = new Callback<List<SelectedResults>>() {
            public void exec(List<SelectedResults> results) {
                if (results.size() > 0) {
                    for(SelectedResults res : results) {
                        SelectedValue sv = new SelectedValue(res.getReturnKey(), res.getDisplayKey());
                        addValue(sv);
                        picker.clear();
                        addItemButton.setEnabled(false);
                    }
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
                if(v != null && !v.isEmpty()) {
                    addItemButton.setEnabled(true);
                } else {
                    addItemButton.setEnabled(false);
                }
            }
            
        };
    }
    public void addValue(final SelectedValue value) {
        if(removedValues.contains(value)) {
            removedValues.remove(value);
        }
        if(!selectedValues.contains(value)) {
            selectedValues.add(value);
        }
        valuesPanel.add(value);
        selectionChanged();
        if(config.canEdit) {
            value.setHighlighted(true);
            new Timer() {
                @Override
                public void run() {
                    value.setHighlighted(false);
                }
            }.schedule(5000);
        }
    }
    
    private void selectionChanged() {
        for(SelectionChangeHandler handler : selectionChangeHandlers) {
            handler.onSelectionChange(new SelectionChangeEvent(this));
        }
    }
    
    public void clear() {
        selectedValues = new ArrayList<SelectedValue>();
        removedValues = new ArrayList<SelectedValue>();
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
        for(SelectedValue v : selectedValues) {
            data.add(v.getValue());
        }
        DataValue result = new DataValue(data);
        return result;
    }
    
    /**
     * Returns all selected values along with their translations in the _runtime data.
     * @return
     */
    public Value getValueWithTranslations(){
        Data data = new Data();
        Data _runtimeData = new Data();
        for(SelectedValue v : selectedValues) {
            data.add(v.getValue());
            Data displayData = new Data();
            displayData.set("id-translation", v.getDisplay());
            _runtimeData.add(displayData);
        }
        data.set(new StringKey("_runtimeData"), _runtimeData);
        DataValue result = new DataValue(data);
        return result;    	
    }

    @Override
    public void setValue(Value value) {
        clear();
        if(value != null) {
            Data data = ((DataValue)value).get();
            Iterator<Property> iter = data.realPropertyIterator();
            while(iter.hasNext()) {
                Property p = iter.next();
                String v = (String)p.getValue();
                //FIXME: do we need to do a search? is this method ever going to be called?
                SelectedValue sv = new SelectedValue(v, "Display: " + v);
                addValue(sv);
            }
        }
    }
    
    public class SelectedValue extends Composite implements HasCloseHandlers<SelectedValue> {
        private final String id = HTMLPanel.createUniqueId();
        private final String contentId = HTMLPanel.createUniqueId();
        private final String removeLinkId = HTMLPanel.createUniqueId();
        private final String backgroundId = HTMLPanel.createUniqueId();
        private final String PANEL_CONTENT_OPEN = "<span id='" + contentId + "'></span>";
        private final String PANEL_CONTENT_REMOVE_LINK = "<a href='javascript:return false;' title='Remove' class='ks-selected-list-value-remove' id='" + removeLinkId + "'></a>"; 
        private final String PANEL_CONTENT_BACKGROUND = "<div id='" + backgroundId + "' class='ks-selected-list-value-container'></div>"; 
        private final HTMLPanel panel = new HTMLPanel(PANEL_CONTENT_OPEN + (config.canEdit ? PANEL_CONTENT_REMOVE_LINK : "") + PANEL_CONTENT_BACKGROUND);

        private String value;
        private String display;

        public SelectedValue(String value, String display) {
            this.value = value;
            this.display = display;
            
            panel.setStyleName("ks-selected-list-value");
            panel.getElement().setId(id);
            
            
            panel.getElementById(contentId).setInnerText(display);
            if(config.canEdit) {
                initHandlers();
            }
            super.initWidget(panel);
        }
        
        public void setHighlighted(boolean highlighted) {
            if (highlighted) {
                Elements.fadeIn(panel.getElementById(backgroundId), 250, 100, new Elements.EmptyFadeCallback());
            } else {
                Elements.fadeOut(panel.getElementById(backgroundId), 500, 0, new Elements.EmptyFadeCallback());   
            }
        }

        private void initHandlers() {
            DOM.sinkEvents(panel.getElementById(removeLinkId), Event.ONCLICK);
            addDomHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    Element e = Element.as(event.getNativeEvent().getEventTarget());
                    if (e.equals(panel.getElementById(removeLinkId))) {
                        doRemove();
                    }
                }
            }, ClickEvent.getType());
        }
        
        private void doRemove() {
            selectedValues.remove(this);
            removedValues.add(this);
            valuesPanel.remove(this);
            CloseEvent.fire(this, this);
        }
        
        public String getId() {
            return id;
        }

        public String getContentId() {
            return contentId;
        }

        public String getCloseLinkId() {
            return removeLinkId;
        }

        
        @Override
        public HandlerRegistration addCloseHandler(
                CloseHandler<SelectedValue> handler) {
            return addHandler(handler, CloseEvent.getType());
        }


        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getDisplay() {
            return display;
        }

        public void setDisplay(String display) {
            this.display = display;
        }

    }

    @Override
    public void setValue(String id, String translation) {
        clear();
        if(id != null && translation != null) {
            addValue(new SelectedValue(id, translation));
        }
    }

    @Override
    public void setValue(Map<String, String> translations) {
        clear();
        if(translations != null) {
            for(Entry<String, String> e : translations.entrySet()) {
                addValue(new SelectedValue(e.getKey(), e.getValue()));
            }
        }
    }
}
