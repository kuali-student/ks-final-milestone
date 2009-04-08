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
package org.kuali.student.lum.lu.ui.course.client.view;

import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSTextArea;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.forms.KSFormField;
import org.kuali.student.common.ui.client.widgets.forms.KSFormLayoutPanel;
import org.kuali.student.common.ui.client.widgets.forms.EditModeChangeEvent.EditMode;
import org.kuali.student.lum.lu.dto.CluInfo;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in. 
 * 
 * @author Kuali Student Team
 *
 */
public class CourseInformation extends ViewComposite{
    VerticalPanel vPanel = new VerticalPanel();
    KSFormLayoutPanel form;
    private Model<CluInfo> clus = null;
    
    KSButton saveButton = new KSButton("Save Changes", new ClickHandler(){
        public void onClick(ClickEvent event) {
            // TODO: Validate data && update model
            form.setEditMode(EditMode.VIEW_ONLY);
            saveButton.setVisible(false);
            editButton.setVisible(true);
        }                
    });
    
    KSButton editButton = new KSButton("Edit", new ClickHandler(){
        public void onClick(ClickEvent event) {
            form.setEditMode(EditMode.EDITABLE);
            saveButton.setVisible(true);
            editButton.setVisible(false);
        }        
    });
    
    /**
     * @param controller
     * @param name
     */
    public CourseInformation(Controller controller) {
        super(controller, "CourseInformation");
        super.initWidget(vPanel);
    }    
    
    @Override
    protected void onLoad() {
        if (clus == null) {
            getController().requestModel(CluInfo.class, new ModelRequestCallback<CluInfo>() {
                public void onModelReady(Model<CluInfo> model) {
                    clus = model;
                    setup();
                }

                public void onRequestFail(Throwable cause) {
                    throw new RuntimeException("Unable to connect to model", cause);
                }

            });
        }
    }

    protected void setup() {
        /*
        clus.addModelChangeHandler(new ModelChangeHandler<CluInfo>() {
            public void onModelChange(ModelChangeEvent<CluInfo> event) {
                redraw();
            }
        });
        */

        if (form == null){
            form = new KSFormLayoutPanel();
            form.addFormField(getFormField("courseNumber", "Course Number"));
            form.addFormField(getFormField("proposedTitle", "Proposed Course Title"));
            form.addFormField(getFormField("transcriptTitle", "Transcript Title"));
            
            KSFormField ff = new KSFormField("description","Decription");
            ff.setWidget(new KSTextArea());
            form.addFormField(ff);
            
            ff = new KSFormField("rationale","Rationale");
            ff.setWidget(new KSTextArea());
            form.addFormField(ff);
    
            vPanel.add(new KSLabel("Course Information"));
            vPanel.add(form);
            
            saveButton.setVisible(true);
            editButton.setVisible(false);
            vPanel.add(saveButton);
            vPanel.add(editButton);
        }
        redraw();
    }

    public KSFormField getFormField(String name, String label){
        KSFormField ff = new KSFormField();
        ff.setName(name);
        ff.setLabelText(label);
        ff.setWidget(new KSTextBox());
        return ff;
    }

    protected void redraw() {
        for (CluInfo clu : clus.getValues()) {
            form.setFieldValue("proposedTitle", clu.getOfficialIdentifier().getLongName());
        }
    }

}
