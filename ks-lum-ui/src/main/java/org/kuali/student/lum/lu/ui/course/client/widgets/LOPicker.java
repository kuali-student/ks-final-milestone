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

import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSTextArea;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * This is a description of what this class does - hjohnson don't forget to fill this in. 
 * 
 * @author Kuali Student Team (kuali-student@googlegroups.com)
 *
 */
public class LOPicker extends  Composite implements  HasValue<String>  { 
    
    VerticalPanel root = new VerticalPanel();
    
    KSTextArea loText = new KSTextArea();
    KSButton upButton = new KSButton();
    KSButton downButton = new KSButton();
    KSButton deleteButton = new KSButton();
    KSButton indentButton = new KSButton();
//    private final FocusGroup focus = new FocusGroup(this);
    
    public LOPicker() {
        super();
        FlexTable layoutTable = new FlexTable();
        
        layoutTable.setWidget(0, 0, upButton);
        layoutTable.setWidget(0, 1, deleteButton);
        layoutTable.setWidget(1, 0, downButton);
        layoutTable.setWidget(1, 1, indentButton);
        
        upButton.addStyleName("KS-LOMoveUpButton");
        downButton.addStyleName("KS-LOMoveDownButton");
        deleteButton.addStyleName("KS-LODeleteButton");
        indentButton.addStyleName("KS-LOIndentButton");

        
        HorizontalPanel mainPanel = new HorizontalPanel();
        mainPanel.addStyleName("KS-LO-Picker-Link-Panel");
               
        mainPanel.add(loText);
        mainPanel.add(layoutTable);
        initWidget(root);

        root.add(mainPanel);

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
    
    public void hideIndentButton(boolean vis){
        indentButton.setVisible(vis);
    }
    public void addMoveUpAction(ClickHandler ch){
        upButton.addClickHandler(ch);
    }
    public void addMoveDownAction(ClickHandler ch){
        downButton.addClickHandler(ch);
    }
    public void addDeleteAction(ClickHandler ch){
        deleteButton.addClickHandler(ch);
    }
    public void addIndentAction(ClickHandler ch){
        indentButton.addClickHandler(ch);
    }
}
