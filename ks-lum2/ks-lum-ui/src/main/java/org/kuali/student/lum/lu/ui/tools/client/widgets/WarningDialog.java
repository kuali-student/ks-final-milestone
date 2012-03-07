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

package org.kuali.student.lum.lu.ui.tools.client.widgets;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.layout.VerticalFlowPanel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;

public class WarningDialog {
    
    private KSLightBox dialog = new KSLightBox();
    private KSLabel titleLabel = null;
    private KSLabel messageLabel = null;
    private KSLabel questionLabel = null;
    private VerticalFlowPanel layout = new VerticalFlowPanel();
    private List<Callback<Boolean>> confirmationCallbacks = new ArrayList<Callback<Boolean>>();
    private KSButton affirmativeButton;
    private KSButton negativeButton;

    public WarningDialog(String title, String message, String question,
            String affirmativeChoice, String negativeChoice) {
        titleLabel = new KSLabel(title);
        messageLabel = new KSLabel(message);
        questionLabel = new KSLabel(question);
        affirmativeButton = new KSButton(affirmativeChoice);
        negativeButton = new KSButton(negativeChoice);
        
        layout.add(titleLabel);
        layout.add(createSpacerPanel("0px","20px"));
        layout.add(messageLabel);
        layout.add(createSpacerPanel("0px","50px"));
        layout.add(questionLabel);
        
        HorizontalPanel buttonsPanel = new HorizontalPanel();
        buttonsPanel.add(affirmativeButton);
        buttonsPanel.add(createSpacerPanel("10px", "0px"));
        buttonsPanel.add(negativeButton);
        layout.add(createSpacerPanel("0px","5px"));
        layout.add(buttonsPanel);
        
        affirmativeButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (confirmationCallbacks != null) {
                    for (Callback<Boolean> confirmationCallback : confirmationCallbacks) {
                        confirmationCallback.exec(Boolean.TRUE);
                    }
                }
            }
        });
        negativeButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (confirmationCallbacks != null) {
                    for (Callback<Boolean> confirmationCallback : confirmationCallbacks) {
                        confirmationCallback.exec(Boolean.FALSE);
                    }
                }
            }
        });
        dialog.setWidget(layout);
    }
    
    private Panel createSpacerPanel(String width, String height) {
        Panel spacerPanel = new SimplePanel();
        spacerPanel.setWidth(width);
        spacerPanel.setHeight(height);
        return spacerPanel;
    }
    
    public void show() {
        dialog.show();
    }
    
    public void hide() {
        dialog.hide();
    }
    
    public void addConfirmationCallback(Callback<Boolean> confirmationCallback) {
        confirmationCallbacks.add(confirmationCallback);
    }
    
}
