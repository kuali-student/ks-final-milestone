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

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.application.ApplicationComposite;
import org.kuali.student.common.ui.client.event.SaveEvent;
import org.kuali.student.common.ui.client.event.SaveHandler;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSModalDialogPanel;
import org.kuali.student.common.ui.client.widgets.menus.KSAccordionMenu;
import org.kuali.student.common.ui.client.widgets.menus.KSMenu;
import org.kuali.student.common.ui.client.widgets.menus.KSMenuItemData;
import org.kuali.student.core.dto.Idable;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.ui.course.client.view.CourseBeginProposal;
import org.kuali.student.lum.lu.ui.course.client.view.CourseAuthor;
import org.kuali.student.lum.lu.ui.course.client.view.CourseInformation;
import org.kuali.student.lum.lu.ui.main.client.controller.LUMApplicationManager.LUMViews;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in. 
 * 
 * @author Kuali Student Team
 *
 */
public class CourseProposalManager extends Controller implements View {
    private final KSModalDialogPanel beginProposalDialog = new KSModalDialogPanel();
    
    private View courseInformation = new CourseInformation(this);
    private View courseAuthor = new CourseAuthor(this);
    Model<CluInfo> cluInfo = new Model<CluInfo>();
    ApplicationComposite app = new ApplicationComposite();
    SimplePanel contentPanel = new SimplePanel();
    
    private CourseProposalType type;
    
    private View dummyView = new DummyView(this); 
        
    private class DummyView extends ViewComposite{
        /**
         * @param controller
         * @param name
         */
        public DummyView(Controller controller) {
            super(controller, "Dummy");
            super.initWidget(new KSLabel("Not implemented"));
        }
        
    }
    
    public enum CourseProposalType {
        NEW_COURSE, TEMPLATE, COPY_COURSE, COPY_TEMPLATE
    }
    
    public enum CourseViews{
        COURSE_INFORMATION, COURSE_AUTHOR, COURSE_DUMMY
    }
    
    public CourseProposalManager(Controller parentController){
        this.setParentController(parentController);
        final CourseBeginProposal beginProposal = new CourseBeginProposal();
        beginProposal.addCancelHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
                beginProposalDialog.hide();
                getParentController().showView(LUMViews.HOME_MENU);
            }            
        });
        beginProposal.addSaveHandler(new SaveHandler(){
            public void onSave(SaveEvent saveEvent) {                
                cluInfo.add(beginProposal.getCourseProposalClu());
                showView(CourseViews.COURSE_AUTHOR);
                beginProposalDialog.hide();
            }            
        });
        beginProposalDialog.setWidget(beginProposal);
        setup();
        super.initWidget(app);
    }

    /**
     */
    @Override
    protected void onLoad() {
        if (type == CourseProposalType.NEW_COURSE){
            beginProposalDialog.show();
        }
    }
    
    public void setup(){
        HorizontalPanel hPanel = new HorizontalPanel();
        KSMenu menuPanel = new KSAccordionMenu();
        
        List<KSMenuItemData> menuItems = new ArrayList<KSMenuItemData>();
        
        KSMenuItemData proposalInfo = new KSMenuItemData("1. Proposal Information"); 
        addSubItem(proposalInfo, "Author + Collaborators", CourseViews.COURSE_AUTHOR);
        addSubItem(proposalInfo, "Governance", CourseViews.COURSE_DUMMY);
        addSubItem(proposalInfo, "Course Format", CourseViews.COURSE_DUMMY);
        
        KSMenuItemData academicContent = new KSMenuItemData("2. Academic Content");
        addSubItem(academicContent, "Course Information", CourseViews.COURSE_INFORMATION);
        addSubItem(academicContent, "Learning Objectives", CourseViews.COURSE_DUMMY);
        addSubItem(academicContent, "Syllabus", CourseViews.COURSE_DUMMY);
        addSubItem(academicContent, "Learning Results", CourseViews.COURSE_DUMMY);
        
        KSMenuItemData studentElig = new KSMenuItemData("3. Student Eligibility");
        addSubItem(studentElig, "Course Restrictions", CourseViews.COURSE_DUMMY);
        addSubItem(studentElig, "Pre + Co Requisites", CourseViews.COURSE_DUMMY);
       
        KSMenuItemData admin = new KSMenuItemData("4. Administrative");
        addSubItem(admin, "Credits", CourseViews.COURSE_DUMMY);
        addSubItem(admin, "Active Dates", CourseViews.COURSE_DUMMY);
        addSubItem(admin, "Financials", CourseViews.COURSE_DUMMY);
        addSubItem(admin, "Program Requirements", CourseViews.COURSE_DUMMY);

        KSMenuItemData attachments = new KSMenuItemData("5. Attachments");
        addSubItem(attachments, "Supporting Documents", CourseViews.COURSE_DUMMY);
        
        menuItems.add(proposalInfo);
        menuItems.add(academicContent);
        menuItems.add(studentElig);
        menuItems.add(admin);
        menuItems.add(attachments);
        
        menuPanel.setItems(menuItems);
        
        hPanel.add(menuPanel);
        hPanel.add(contentPanel);
        app.setContent(hPanel);
    }
    
    private void addSubItem(final KSMenuItemData group, String title, final CourseViews view) {

        KSMenuItemData item = new KSMenuItemData(title);
    
        item.setClickHandler(getClickHandler(view));              
        group.addSubItem(item);
    }
    
    private ClickHandler getClickHandler(final CourseViews v){
        return new ClickHandler() {
            public void onClick(ClickEvent arg0) {
                showView(v);
            }
        };
    }                    
    
    /**
     * @see org.kuali.student.common.ui.client.mvc.Controller#getView(java.lang.Enum)
     */   
    protected <V extends Enum<?>> View getView(V viewType) {
        switch ((CourseViews)viewType){
            case COURSE_AUTHOR:
                return courseAuthor;
            case COURSE_INFORMATION:
                return courseInformation; 
            default:
                return dummyView;
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
        if (view != null){
            contentPanel.setWidget((ViewComposite)view);
        }
    }

    /**
     * @see org.kuali.student.common.ui.client.mvc.Controller#showDefaultView()
     */
    @Override
    public void showDefaultView() {       
        this.showView(CourseViews.COURSE_AUTHOR);
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
