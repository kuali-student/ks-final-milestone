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
import org.kuali.student.common.ui.client.event.SaveHandler;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSModalDialogPanel;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.forms.KSFormField;
import org.kuali.student.common.ui.client.widgets.forms.KSFormLayoutPanel;
import org.kuali.student.core.dto.TimeAmountInfo;
import org.kuali.student.lum.lu.dto.CluCreditInfo;
import org.kuali.student.lum.lu.dto.CluIdentifierInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.dto.CluInstructorInfo;
import org.kuali.student.lum.lu.ui.course.client.service.LuRpcService;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author Kuali Student Team
 *
 */
public class CourseBeginProposal extends Composite{
    private KSFormLayoutPanel form = new KSFormLayoutPanel();
    VerticalPanel vPanel = new VerticalPanel();
    
    private final String PROPOSED_TITLE = "proposedTitle";
    private final String DEPARTMENT     = "department";
    private final String FACULTY        = "faculty";
    private final String DELEGATE       = "delegate";
    
    private CluInfo clu;
    
    private final KSButton saveButton = new KSButton("Save Draft", new ClickHandler(){
        public void onClick(ClickEvent event) {
            fireSaveEvent();
        }        
    });
    
    private final KSButton cancelButton = new KSButton("Cancel"); 
    
    public CourseBeginProposal(){
        form.addFormField(getFormField(PROPOSED_TITLE,  "Proposed Course Title"));
        form.addFormField(getFormField(FACULTY,         "Originating Faculty Member"));
        form.addFormField(getFormField(DELEGATE,        "Adminstrative Delegate"));
        
        KSFormField deptField = new KSFormField(DEPARTMENT, "Department");
        deptField.setWidget(getDeptDropDown());
        form.addFormField(deptField);
        
        vPanel.add(new KSLabel("Begin Proposal"));
        vPanel.add(form);
        
        vPanel.add(saveButton); //TODO: enable this button only if required fields filled in
        vPanel.add(cancelButton);
        
        super.initWidget(vPanel);
    }
        
    public KSFormField getFormField(String name, String label){
        KSFormField ff = new KSFormField();
        ff.setName(name);
        ff.setLabelText(label);
        ff.setWidget(new KSTextBox());
        return ff;
    }
    
    public CluInfo getCourseProposalClu(){
        return clu;
    }
    
    private CluInfo createCourseProposalClu(){
        CluInfo clu = new CluInfo();
        
        CluIdentifierInfo cluId = new CluIdentifierInfo();
        cluId.setLongName(form.getFieldValue(PROPOSED_TITLE));
        clu.setOfficialIdentifier(cluId);
        
        CluInstructorInfo cluInstructor = new CluInstructorInfo();
        clu.setPrimaryInstructor(cluInstructor);
                
        //FIXME: Is this supposed to be the adminOrg id or name
        clu.setAdminOrg(((KSDropDown)form.getFieldWidget(DEPARTMENT)).getSelectedItem());
        return clu;        
    }
    
    public KSDropDown getDeptDropDown(){
        KSDropDown deptDropDown = new KSDropDown();
        
        //TODO: Get list of departments from org service
        
        return deptDropDown;
    }
    
    public void addSaveHandler(SaveHandler handler){        
        addHandler(handler, SaveEvent.TYPE);
    }
    
    public void addCancelHandler(ClickHandler handler){        
        cancelButton.addClickHandler(handler);
    }
    
    public void fireSaveEvent(){
        //TODO: Validate data
        LuRpcService.Util.getInstance().createClu("luType.shell.course", createCourseProposalClu(), new AsyncCallback<CluInfo>(){

            public void onFailure(Throwable caught) {
                //TODO: Display error
                Window.alert(caught.toString());
            }

            @Override
            public void onSuccess(CluInfo result) {
                clu = result;
                fireEvent(new SaveEvent());
            }
        });
    }
}
