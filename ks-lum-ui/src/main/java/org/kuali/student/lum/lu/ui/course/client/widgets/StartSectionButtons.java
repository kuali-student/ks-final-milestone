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

import org.kuali.student.common.ui.client.event.SaveEvent;
import org.kuali.student.common.ui.client.event.SaveHandler;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.lum.lu.ui.course.client.configuration.DefaultCreateUpdateLayout.SaveTypes;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * This panel adds a save and cancel button to section, with the ability
 * to add save and cancel handlers. 
 * 
 * @author Kuali Student Team
 *
 */
//TODO: Move this to common-ui
public class StartSectionButtons extends Composite{
    VerticalPanel panel = new VerticalPanel();
    
    private final KSButton saveButton = new KSButton("Save Draft", new ClickHandler(){
        public void onClick(ClickEvent event) {
            StartSectionButtons.this.fireEvent(new SaveEvent<SaveTypes>(SaveTypes.CREATE));
        }        
    });
    
//    private final KSButton saveWorkflowButton = new KSButton("Start Workflow Proposal", new ClickHandler(){
//        public void onClick(ClickEvent event) {
//            StartSectionButtons.this.fireEvent(new SaveEvent<SaveTypes>(SaveTypes.WF_CREATE));
//        }        
//    });

    private final KSButton cancelButton = new KSButton("Cancel");
    
    public StartSectionButtons(){
        super();
        HorizontalPanel row = new HorizontalPanel();
        row.add(saveButton);
        //row.add(saveWorkflowButton);
        row.setStyleName("KS-Course-Save-Button");
        panel.add(row); //TODO: enable this button only if required fields filled in
        panel.add(cancelButton);
        initWidget(panel);
    }
        
    public void addSaveHandler(SaveHandler handler){        
        addHandler(handler, SaveEvent.TYPE);
    }
    
    public void addCancelHandler(ClickHandler handler){        
        cancelButton.addClickHandler(handler);
    }

    
}
