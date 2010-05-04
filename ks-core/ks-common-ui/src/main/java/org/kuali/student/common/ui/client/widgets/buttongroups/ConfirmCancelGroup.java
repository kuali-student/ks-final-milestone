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

package org.kuali.student.common.ui.client.widgets.buttongroups;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.ConfirmCancelEnum;
import org.kuali.student.common.ui.client.widgets.buttonlayout.ButtonRow;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

public class ConfirmCancelGroup extends ButtonGroup<ConfirmCancelEnum>{
    
    public ConfirmCancelGroup(Callback<ConfirmCancelEnum> callback){
        layout = new ButtonRow();
        this.addCallback(callback);
        
        addButton(ConfirmCancelEnum.CANCEL);
        addButtonToSecondaryGroup(ConfirmCancelEnum.CONFIRM);
        
        this.initWidget(layout);
    }
    
    private void addButton(final ConfirmCancelEnum type){
        KSButton button = new KSButton(type.getText(), new ClickHandler(){
            
            @Override
            public void onClick(ClickEvent event) {
                sendCallbacks(type);
            }
        });
        layout.addButton(button);
        buttonMap.put(type, button);
    }
    
    private void addButtonToSecondaryGroup(final ConfirmCancelEnum type){
        KSButton button = new KSButton(type.getText(), new ClickHandler(){
            
            @Override
            public void onClick(ClickEvent event) {
                sendCallbacks(type);
            }
        });
        ((ButtonRow)layout).addButtonToSecondaryGroup(button);
        buttonMap.put(type, button);
    }
}
