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

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FocusPanel;
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
    
    VerticalPanel root;
    
    KSTextArea loText = new KSTextArea();
    KSButton upButton = new KSButton();
    KSButton downButton = new KSButton();
    KSButton deleteButton = new KSButton();
    KSButton indentButton = new KSButton();
//    private final FocusGroup focus = new FocusGroup(this);
    FlexTable layoutTable = new FlexTable();
    public LOPicker() {
        super();
        root = new VerticalPanel(){
       //     public void onBrowserEvent(Event event){
         //       switch (DOM.eventGetType(event)) {
           //         case Event.ONMOUSEOVER:
             //           layoutTable.setVisible(true);
               //         break;
                 //   case Event.ONMOUSEOUT:
                   //     layoutTable.setVisible(false);
                     // break;
               // }
                //super.onBrowserEvent(event);
           // }
        };
        
        layoutTable.setWidget(0, 0, upButton);
        layoutTable.setWidget(0, 1, deleteButton);
        layoutTable.setWidget(1, 0, downButton);
        layoutTable.setWidget(1, 1, indentButton);
        
        root.addStyleName("KS-LO-Picker-Main-Panel");
        upButton.addStyleName("KS-LOMoveUpButton");
        downButton.addStyleName("KS-LOMoveDownButton");
        deleteButton.addStyleName("KS-LODeleteButton");
        indentButton.addStyleName("KS-LOIndentButton");

        FocusPanel focusPanel = new FocusPanel();
        focusPanel.addFocusHandler(new FocusHandler(){
            @Override
            public void onFocus(FocusEvent event) {
                layoutTable.setVisible(true);
            }
        });
        HorizontalPanel mainPanel = new HorizontalPanel(){
            public void onBrowserEvent(Event event){
                Window.alert("from hori"+event.getString());
                switch (DOM.eventGetType(event)) {
                    case Event.ONMOUSEOVER:
                        layoutTable.setVisible(true);
                        break;
                    case Event.ONMOUSEOUT:
                        layoutTable.setVisible(false);
                      break;
                }
                super.onBrowserEvent(event);
            }
        };
        loText.addFocusHandler(new FocusHandler(){
            @Override
            public void onFocus(FocusEvent event) {
           //     layoutTable.setVisible(true);
            }
        });
        loText.addBlurHandler(new BlurHandler(){
            @Override
            public void onBlur(BlurEvent event) {
             //   layoutTable.setVisible(false);
            }
        });
        
//        loText.setVisibleLines(2);

        focusPanel.setWidget(layoutTable);
        mainPanel.add(loText);
        mainPanel.add(focusPanel);
        initWidget(root);


        root.add(mainPanel);
        layoutTable.setVisible(false);
       
        super.sinkEvents(Event.ONMOUSEMOVE);
        super.sinkEvents(Event.ONMOUSEOUT);
    }
    public void onBrowserEvent(Event event){
        switch (DOM.eventGetType(event)) {
            case Event.ONMOUSEMOVE:
                layoutTable.setVisible(true);
                break;
            case Event.ONMOUSEOUT:
                layoutTable.setVisible(false);
              break;
        }
        super.onBrowserEvent(event);
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
    public void enableMoveUpButton(boolean enable){
        upButton.setEnabled(enable);
        if(enable){
            upButton.addStyleName("KS-LOMoveUpButton");
        }else{
            upButton.addStyleName("KS-LOMoveUpButtonDisable");
        }
    }
    public void enableMoveDownButton(boolean enable){
        downButton.setEnabled(enable);
        if(enable){
            upButton.addStyleName("KS-LOMoveDownButton");
        }else{
            upButton.addStyleName("KS-LOMoveDownButtonDisable");
        }
    }
    public void enableDeleteButton(boolean enable){
        deleteButton.setEnabled(enable);
        if(enable){
            upButton.addStyleName("KS-LODeleteButton");
        }else{
            upButton.addStyleName("KS-LODeleteButtonDisable");
        }
    }
    public void indentDeleteButton(boolean enable){
        indentButton.setEnabled(enable);
        if(enable){
            indentButton.addStyleName("KS-LOIndentButton");
        }else{
            indentButton.addStyleName("KS-LOIndentButtonDisable");
        }
    }
}
