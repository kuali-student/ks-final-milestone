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

import org.kuali.student.common.ui.client.event.SaveEvent;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSConfirmationDialog;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.forms.KSFormField;
import org.kuali.student.common.ui.client.widgets.forms.KSFormLayoutPanel;
import org.kuali.student.common.ui.client.widgets.forms.EditModeChangeEvent.EditMode;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.ui.course.client.service.LuRpcService;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in. 
 * 
 * @author Kuali Student Team
 *
 */
public abstract class CourseSection extends ViewComposite {
    private final KSConfirmationDialog confirmSaveDialog = new KSConfirmationDialog();
    
    private VerticalPanel vPanel = new VerticalPanel();
    private KSFormLayoutPanel form;
    private Model<CluInfo> clus = null;        
    private String title;
    
    private ClickHandler saveClickHandler = new ClickHandler(){
        public void onClick(ClickEvent event) {
            // TODO: Validate data && update model
            form.setEditMode(EditMode.VIEW_ONLY);
            saveButton.setVisible(false);
            editButton.setVisible(true);
            nextButton.setVisible(true);
            fireSaveEvent();
        }                
    };

    KSButton saveButton = new KSButton("Save Changes", saveClickHandler);
    
    KSButton editButton = new KSButton("Edit", new ClickHandler(){
        public void onClick(ClickEvent event) {
            form.setEditMode(EditMode.EDITABLE);
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
    public CourseSection(Controller controller, String name, String title) {
        super(controller, name);
        super.initWidget(vPanel);
        this.title = title;
        confirmSaveDialog.setWidget(new KSLabel("Section data not saved. Do you wish to save?"));
        confirmSaveDialog.addConfirmHandler(saveClickHandler);        
    }
    
    
    protected void onLoad() {
            
        if (clus == null) {
            getController().requestModel(CluInfo.class, new ModelRequestCallback<CluInfo>() {
                public void onModelReady(Model<CluInfo> model) {
                    clus = model;
                    initSection();
                }

                public void onRequestFail(Throwable cause) {
                    throw new RuntimeException("Unable to connect to model", cause);
                }

            });
        }
    }

    public void addTextField(String name, String label){
        KSFormField ff = new KSFormField();
        ff.setName(name);
        ff.setLabelText(label);
        ff.setWidget(new KSTextBox());
        form.addFormField(ff);
    }
    
    public void addFormField(String name, String label, Widget w){
        KSFormField ff = new KSFormField();
        ff.setName(name);
        ff.setLabelText(label);
        ff.setWidget(w);
        form.addFormField(ff);
    }
    
    public KSFormLayoutPanel getForm(){
        return form;
    }

    public void initSection(){
        /*
        clus.addModelChangeHandler(new ModelChangeHandler<CluInfo>() {
            public void onModelChange(ModelChangeEvent<CluInfo> event) {
                redraw();
            }
        });
        */

        if (form == null){
            form = new KSFormLayoutPanel();
            init();
            
            KSLabel titleLabel = new KSLabel(title);
            titleLabel.setStyleName("KS-Course-Section-Header");
            vPanel.add(titleLabel);
            vPanel.add(form);
            
            saveButton.setVisible(true);
            editButton.setVisible(false);
            nextButton.setVisible(false);
            
            VerticalPanel buttonPanel = new VerticalPanel();
            buttonPanel.add(saveButton);
            buttonPanel.add(editButton);
            buttonPanel.add(nextButton);
            buttonPanel.setStyleName("KS-Course-Save-Button");
            
            vPanel.add(buttonPanel);
        };
        redraw();
    }
    
    public CluInfo getCourseInformation(){
        for (CluInfo clu : clus.getValues()) {
            return clu;
        }
        return null;
    }
      
    /**
     * Implement this method to add fields to this sections form
     */
    public abstract void init();  

    /**
     * Implement this method to redraw the form, ie set the form fields
     * using latest data from cluInfo.
     */
    public abstract void redraw();
    
    /**
     *  Implement this method to transfer values from form to the cluInfo
     *  model.
     *   
     * @return
     */
    public abstract CluInfo convert();
    
    public void addNextSectionClickHandler(ClickHandler handler){
        this.nextButton.addClickHandler(handler);
    }
    
    public void fireSaveEvent(){
        //TODO: Validate data
        clus.update(convert());
        CluInfo clu = getCourseInformation();
        LuRpcService.Util.getInstance().updateClu(clu.getId(), clu, new AsyncCallback<CluInfo>(){

            public void onFailure(Throwable caught) {
                //TODO: Display error
                Window.alert(caught.toString());
            }

            @Override
            public void onSuccess(CluInfo result) {
                
                clus.update(result);
                fireEvent(new SaveEvent());
            }
        });        
    }
    
    public boolean beforeHide(){
        if (form.isDirty()){
            confirmSaveDialog.show();
            return false;
        } else {
            return true;
        }           
    }
}
