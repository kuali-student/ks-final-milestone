/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.lum.lu.ui.course.client.widgets;

import java.util.List;

import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSStyles;
import org.kuali.student.common.ui.client.widgets.KSTextArea;
import org.kuali.student.common.ui.client.widgets.suggestbox.KSAdvancedSearchWindow;
import org.kuali.student.lum.lu.ui.course.client.service.LoRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.LoRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * This is a description of what this class does - hjohnson don't forget to fill this in. 
 * 
 * @author Kuali Student Team (kuali-student@googlegroups.com)
 *
 */
public class LOPicker extends  Composite implements  HasValue<String>  { 

    private LoRpcServiceAsync loRpcServiceAsync = GWT.create(LoRpcService.class);
    
    KSAdvancedSearchWindow loSearchWindow;
    
    VerticalPanel root = new VerticalPanel();
    
    KSTextArea loText = new KSTextArea();
    ButtonPanel buttonPanel = new ButtonPanel();
//    private final FocusGroup focus = new FocusGroup(this);
    
    public LOPicker() {
        super();

        HorizontalPanel searchPanel = new HorizontalPanel();
        searchPanel.addStyleName("KS-LO-Picker-Link-Panel");
        
        
        KSLabel searchLink = new KSLabel("Search for LO");
        searchLink.addStyleName("KS-LO-Picker-Link");
        searchLink.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (loSearchWindow == null) {
                    initSearchWindow();
                }
                loSearchWindow.show();
                
            }
            
        });
               
        searchPanel.add(loText);
        searchPanel.add(searchLink);
        searchPanel.add(buttonPanel);
        initWidget(root);

        root.add(searchPanel);

    }

    @Override
    public String getValue() {
        return loText.getValue();
    }

    @Override
    public void setValue(String value) {
        loText.setValue(value);
    }

    @Override
    public void setValue(String value, boolean fireEvents) {
        setValue(value);        
    }

    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) {
        return loText.addValueChangeHandler(handler);
    }

    @Override
    public void fireEvent(GwtEvent<?> event) {
        super.fireEvent(event);
    }
    
    private void initSearchWindow() {
        loSearchWindow = new KSAdvancedSearchWindow(loRpcServiceAsync, "lo.search.loByDesc","lo.resultColumn.loDescId", "Find Learning Objectives");   
        //FIXME: This text should be from message service
        loSearchWindow.setInstructions("Search for these words in Learning Objectives");
        loSearchWindow.setIgnoreCase(true);
        loSearchWindow.setPartialMatch(true);
        loSearchWindow.addSelectionHandler(new SelectionHandler<List<String>>(){
            public void onSelection(SelectionEvent<List<String>> event) {
                final List<String> selected = event.getSelectedItem();
                if (selected.size() > 0){
                    loText.setValue(selected.get(0));                    
                    loSearchWindow.hide();
                }                
            }            
        });

        
    }
       
    class ButtonPanel extends  Composite {
        FlexTable layoutTable = new FlexTable();
        KSButton upButton = new KSButton();
        KSButton downButton = new KSButton();
        KSButton deleteButton = new KSButton();
        KSButton indentButton = new KSButton();
        
        public ButtonPanel(){
            layoutTable.setWidget(0, 0, upButton);
            layoutTable.setWidget(0, 1, deleteButton);
            layoutTable.setWidget(1, 0, downButton);
            layoutTable.setWidget(1, 1, indentButton);
            super.initWidget(layoutTable);
        }
        public void hideIndentButton(boolean vis){
            indentButton.setVisible(vis);
        }
        public void addMoveUpAction(ClickHandler ch){
            upButton.addClickHandler(ch);
        }
        public void addMoveDownAction(ClickHandler ch){
            downButton.addClickHandler(ch);
        }
        public void addMoveDeleteAction(ClickHandler ch){
            deleteButton.addClickHandler(ch);
        }
        public void addMoveIndentAction(ClickHandler ch){
            indentButton.addClickHandler(ch);
        }
    }
}
