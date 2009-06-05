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

//TODO: Move this to common-ui
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSConfirmationDialog;
import org.kuali.student.common.ui.client.widgets.KSLabel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * This is a button panel to display a set of buttons for layout sections. 
 * 
 * @author Kuali Student Team
 *
 */
public class SectionButtons extends Composite{
    private final KSConfirmationDialog confirmSaveDialog = new KSConfirmationDialog();
    
    private VerticalPanel vPanel = new VerticalPanel();
        
    private ClickHandler saveClickHandler = new ClickHandler(){
        public void onClick(ClickEvent event) {
            saveButton.setVisible(false);
            editButton.setVisible(true);
            nextButton.setVisible(true);
        }                
    };

    KSButton saveButton = new KSButton("Save Changes", saveClickHandler);
    
    KSButton editButton = new KSButton("Edit", new ClickHandler(){
        public void onClick(ClickEvent event) {
            saveButton.setVisible(true);
            editButton.setVisible(false);
            nextButton.setVisible(false);
        }        
    });
    
    KSButton nextButton = new KSButton("Next Section");
    
    /**
     * @param controller
     * @param name
     */
    public SectionButtons() {
        super.initWidget(vPanel);
        confirmSaveDialog.setWidget(new KSLabel("Section data not saved. Do you wish to save?"));
        confirmSaveDialog.addConfirmHandler(saveClickHandler);        

        saveButton.setVisible(true);
        editButton.setVisible(false);
        nextButton.setVisible(false);
        
        VerticalPanel buttonPanel = new VerticalPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(editButton);
        buttonPanel.add(nextButton);
        buttonPanel.setStyleName("KS-Course-Save-Button");
        
        vPanel.add(buttonPanel);

    }
        
    protected void onLoad() {
    }
    
    public void addNextSectionClickHandler(ClickHandler handler){
        this.nextButton.addClickHandler(handler);
    }

    public void addSaveClickHandler(ClickHandler handler){
        this.saveButton.addClickHandler(handler);
        this.confirmSaveDialog.addConfirmHandler(handler);
    }

    public void addEditClickHandler(ClickHandler handler){
        this.editButton.addClickHandler(handler);
    }
    
    public void showSaveDialog(){
        confirmSaveDialog.show();
    }
}
