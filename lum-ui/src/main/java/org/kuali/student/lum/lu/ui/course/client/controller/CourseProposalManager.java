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
package org.kuali.student.lum.lu.ui.course.client.controller;

import org.kuali.student.common.ui.client.event.SaveEvent;
import org.kuali.student.common.ui.client.event.SaveHandler;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.mvc.test.Person;
import org.kuali.student.core.dto.Idable;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.ui.course.client.view.BeginCourseProposal;
import org.kuali.student.lum.lu.ui.course.client.view.CourseInformation;
import org.kuali.student.lum.lu.ui.main.client.controller.LUMApplicationManager.LUMViews;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in. 
 * 
 * @author Kuali Student Team
 *
 */
public class CourseProposalManager extends Controller implements View {
    private BeginCourseProposal beginProposal = new BeginCourseProposal();
    private View courseInformation = new CourseInformation(this);
    Model<CluInfo> cluInfo = new Model<CluInfo>();
    SimplePanel simplePanel = new SimplePanel();
    
    private CourseProposalType type;
    
    public enum CourseProposalType {
        NEW_COURSE, TEMPLATE, COPY_COURSE, COPY_TEMPLATE
    }
    
    public enum CourseViews{
        COURSE_INFORMATION
    }
    
    public CourseProposalManager(Controller parentController){
        this.setParentController(parentController);
        beginProposal.addCancelHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
                beginProposal.hide();
                getParentController().showView(LUMViews.HOME_MENU);
            }            
        });
        beginProposal.addSaveHandler(new SaveHandler(){
            public void onSave(SaveEvent saveEvent) {                
                cluInfo.add(beginProposal.getCourseProposalClu());
                showView(CourseViews.COURSE_INFORMATION);
            }            
        });
        super.initWidget(simplePanel);
    }

    
    /**
     * @see org.kuali.student.common.ui.client.mvc.Controller#getView(java.lang.Enum)
     */   
    protected <V extends Enum<?>> View getView(V viewType) {
        switch ((CourseViews)viewType){
            case COURSE_INFORMATION:
                return courseInformation;
            default:
                return null;
        }
    }

    /**
     * @see org.kuali.student.common.ui.client.mvc.Controller#hideView(org.kuali.student.common.ui.client.mvc.View)
     */
    protected void hideView(View view) {
    }

    /**
     * @see org.kuali.student.common.ui.client.mvc.Controller#renderView(org.kuali.student.common.ui.client.mvc.View)
     */
    protected void renderView(View view) {
        simplePanel.setWidget((ViewComposite)view);
    }

    /**
     * @see org.kuali.student.common.ui.client.mvc.Controller#showDefaultView()
     */
    @Override
    public void showDefaultView() {       
        this.showView(CourseViews.COURSE_INFORMATION);
    }

    /**
     * @see org.kuali.student.common.ui.client.mvc.View#beforeHide()
     */
    @Override
    public boolean beforeHide() {
        return true;
    }

    /**
     * @see org.kuali.student.common.ui.client.mvc.View#beforeShow()
     */
    @Override
    public void beforeShow() {
        if (type == CourseProposalType.NEW_COURSE){
            beginProposal.show();
        }
    }

    /**
     * @see org.kuali.student.common.ui.client.mvc.View#getController()
     */
    @Override
    public Controller getController() {
        return this;
    }

    /**
     * @see org.kuali.student.common.ui.client.mvc.View#getName()
     */
    @Override
    public String getName() {
        // TODO Will Gomes - THIS METHOD NEEDS JAVADOCS
        return null;
    }
    
    public void setCourseProposalType(CourseProposalType type){
        this.type = type;
    }


    @Override
    public void requestModel(Class<? extends Idable> modelType, ModelRequestCallback callback) {
        if (modelType.equals(CluInfo.class)) {
            if (cluInfo == null) {
                cluInfo = new Model<CluInfo>();
            }
            callback.onModelReady(cluInfo);
        } else {
            super.requestModel(modelType, callback);
        }
    }
    

    

}
