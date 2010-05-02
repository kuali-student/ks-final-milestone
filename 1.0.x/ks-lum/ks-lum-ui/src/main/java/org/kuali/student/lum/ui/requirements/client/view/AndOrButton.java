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

package org.kuali.student.lum.ui.requirements.client.view;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

public class AndOrButton extends HorizontalPanel {
    Button andButton = new Button("");
    Button orButton = new Button("");
    Label andLabel = new Label("And");
    Label orLabel = new Label("Or");
    HandlerRegistration andHandlerRegi;
    HandlerRegistration orHandlerRegi;
    
    public final static int And = 1;
    public final static int Or = 2;
    private int value = And;
    public AndOrButton(){
        super.add(andButton);
        super.add(andLabel);
        super.add(orLabel);
        super.add(orButton);
        andLabel.setStyleName("KS-Rules-Toggle-Label");
        andButton.setStyleName("KS-Rules-Toggle-Button");
        orLabel.setStyleName("KS-Rules-Toggle-Label");
        orButton.setStyleName("KS-Rules-Toggle-Button");
        update(AndOrButton.And);
        andButton.addClickHandler(new ClickHandler(){
            @Override
            public void onClick(ClickEvent event) {
              update(AndOrButton.Or);
            }
        });
        orButton.addClickHandler(new ClickHandler(){
            @Override
            public void onClick(ClickEvent event) {
              update(AndOrButton.And);
            }
        });       
    }
    public int getValue(){
        return value;
    }
    public void addClickHandler(ClickHandler clickHandler) {
        andHandlerRegi = andButton.addClickHandler(clickHandler);
        orHandlerRegi = orButton.addClickHandler(clickHandler);
    }
    public void removeClickHandler() {
        andHandlerRegi.removeHandler();
        orHandlerRegi.removeHandler();
    }
    public void setValue(int v){
        this.value = v;
        update(this.value);
    }
    private void update(int value){
        this.value = value;
        if(value == And){
            andButton.setVisible(true);
            andLabel.setVisible(true);
            orButton.setVisible(false);
            orLabel.setVisible(false);
        }else if (value == Or){
            andButton.setVisible(false);
            andLabel.setVisible(false);
            orButton.setVisible(true);
            orLabel.setVisible(true);
        }
    }}
