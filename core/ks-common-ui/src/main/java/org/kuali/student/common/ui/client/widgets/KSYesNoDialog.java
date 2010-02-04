/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.common.ui.client.widgets;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Label;
/**
 * 
 * @deprecated
 * */
public class KSYesNoDialog{
    KSDialog dialog = new KSDialog();

    public int result = -1;
    
    public KSYesNoDialog(String title, String message){
        dialog.setTitle(title);
        Label messageLabel = new Label();
        messageLabel.setText(message);
        dialog.setContent(messageLabel);
        dialog.addButton(KSDialog.YesText, new ClickHandler (){
            @Override
            public void onClick(ClickEvent event) {
                result = KSDialog.Yes;
                dialog.hide();
            }
        });
        dialog.addButton(KSDialog.NoText, new ClickHandler (){
            @Override
            public void onClick(ClickEvent event) {
                result = KSDialog.No;
                dialog.hide();
            }
        });
    }
    public int getResult(){
        return result;
    }
    public void show(){
        dialog.show();
    }
}
