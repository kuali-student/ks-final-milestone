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
package org.kuali.student.lum.lu.ui.home.client.view;

import java.util.HashMap;
import java.util.List;

import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.application.ViewContext.IdType;
import org.kuali.student.common.ui.client.event.ChangeViewActionEvent;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.service.AuthorizationRpcService.PermissionType;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.search.AdvancedSearchWindow;
import org.kuali.student.common.ui.client.widgets.search.SearchPanel;
import org.kuali.student.common.ui.client.widgets.search.SelectedResults;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.proposal.ui.client.service.ProposalRpcService;
import org.kuali.student.core.proposal.ui.client.service.ProposalRpcServiceAsync;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.FindCourseMetadata;
import org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcServiceAsync;
import org.kuali.student.lum.lu.ui.course.client.service.LuRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.LuRpcServiceAsync;
import org.kuali.student.lum.lu.ui.home.client.view.CreateCreditCoursePanel.ButtonRow;
import org.kuali.student.lum.lu.ui.main.client.controller.LUMApplicationManager.LUMViews;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ModifyFindPanel extends ViewComposite{
    public static final String SEARCH_TYPE_PROPOSALS = "Proposals";
    public static final String SEARCH_TYPE_COURSES = "Courses";
    
    LuRpcServiceAsync luServiceAsync = GWT.create(LuRpcService.class);
    ProposalRpcServiceAsync proposalServiceAsync = GWT.create(ProposalRpcService.class);
    CreditCourseProposalRpcServiceAsync cluProposalRpcServiceAsync = GWT.create(CreditCourseProposalRpcService.class);
    
    // TODO please leave on until the atp search has found a home.
//    AtpRpcServiceAsync atpRpcServiceAsync = GWT.create(AtpRpcService.class);
    
    
    AdvancedSearchWindow courseSearchWindow;
    //KSAdvancedSearchWindow proposalSearchWindow;
    // TODO please leave on until the atp search has found a home.
    AdvancedSearchWindow atpSearchWindow;
    
    private VerticalPanel mainPanel = new VerticalPanel();
        
    private boolean loaded = false;    
       
    public ModifyFindPanel(Controller controller) {

        super(controller, "Find Course or Proposal");                     
        this.initWidget(mainPanel);
    }

    @Override
    public void beforeShow(final Callback<Boolean> onReadyCallback) {
        if (!loaded){                        
            //FIXME: This is a quick fix
            KSButton findCourseButton = new KSButton("Find Course", new ClickHandler(){
                public void onClick(ClickEvent event) {
                    if (courseSearchWindow == null){
                        initCourseSearchWindow();
                    }
                    courseSearchWindow.show();
                }            
            });
            
            /*KSButton findProposalButton = new KSButton("Find Proposal", new ClickHandler(){
                public void onClick(ClickEvent event) {
                    if (proposalSearchWindow == null){
                        initProposalSearchWindow();
                    }
                    proposalSearchWindow.show();
                }            
            });*/
            
            
            // TODO please leave on until the atp search has found a home.
//            KSButton findAtpButton = new KSButton("Find Session", new ClickHandler(){
//                public void onClick(ClickEvent event) {
//                    if (atpSearchWindow == null){
//                        initAtpSearchWindow();
//                    }
//                    atpSearchWindow.show();
//                }            
//            });
            
            ButtonRow findCourseRow = new ButtonRow(findCourseButton, "Find an existing course to modify.");
            addIfPermitted(mainPanel, findCourseRow, PermissionType.SEARCH);
            
            //ButtonRow findProposalRow = new ButtonRow(findProposalButton, "Find an existing proposal."); 
            //addIfPermitted(mainPanel, findProposalRow, "Lookup Proposal");
            
            // TODO Please leave on 
//            mainPanel.add(new ButtonRow(findAtpButton, "Find a Session."));

            loaded = true;
        }
        onReadyCallback.exec(true);
    }
    private void addIfPermitted(final Panel container, final Widget element, PermissionType permType) {
        cluProposalRpcServiceAsync.isAuthorized(permType, new HashMap<String,String>(), new AsyncCallback<Boolean>() {
            @Override
            public void onFailure(Throwable caught) {
                throw new RuntimeException("Could not verify authorization: " + caught.getMessage(), caught);
            }
            @Override
            public void onSuccess(Boolean result) {
                if(result) {
                    container.add(element);
                }
            }
            
        });
    }
    private void initCourseSearchWindow(){  
    	Metadata searchMetadata = new FindCourseMetadata().getMetadata("", "");  //no type or state at this point
        SearchPanel searchPicker = new SearchPanel(searchMetadata.getProperties().get("courseId").getInitialLookup());                
        courseSearchWindow = new AdvancedSearchWindow("Find Course", searchPicker);   	    	
        courseSearchWindow.addSelectionCompleteCallback(new Callback<List<SelectedResults>>(){
            public void exec(List<SelectedResults> results) {
                if (results.size() > 0){
                	ViewContext viewContext = new ViewContext();
                	viewContext.setId(results.get(0).getReturnKey());
                	viewContext.setIdType(IdType.OBJECT_ID);
                	ModifyFindPanel.this.getController().fireApplicationEvent(new ChangeViewActionEvent<LUMViews>(LUMViews.MODIFY_COURSE, viewContext));
                    courseSearchWindow.hide();
                }                
            }            
        });               
    } 
    
/*    private void initProposalSearchWindow(){
        proposalSearchWindow = new KSAdvancedSearchWindow(proposalServiceAsync, "proposal.search.courses", "proposal.resultColumn.proposalId", "Find Proposal");            
        proposalSearchWindow.addSelectionHandler(new SelectionHandler<List<String>>(){
            //FIXME: This should take user to the course view screens
            public void onSelection(SelectionEvent<List<String>> event) {
                final List<String> selected = event.getSelectedItem();
                if (selected.size() > 0){
                	ViewContext viewContext = new ViewContext();
                	viewContext.setId(selected.get(0));
                	viewContext.setIdType(IdType.PROPOSAL_ID);
                    FindPanel.this.getController().fireApplicationEvent(new ChangeViewActionEvent<LUMViews>(LUMViews.EDIT_COURSE_PROPOSAL, viewContext));
                    proposalSearchWindow.hide();
                }                
            }            
        });        
    }*/
    
    // TODO please leave on until the atp search has found a home.
//    private void initAtpSearchWindow(){
//        
//        Metadata searchMetadata = new CreditCourseMetadata().getMetadata("", "");  //no type or state at this point
//        SearchPanel searchPicker = new SearchPanel(atpRpcServiceAsync, searchMetadata.getProperties().get("firstExpectedOffering").getLookupMetadata());
//        atpSearchWindow = new AdvancedSearchWindow("Find Session", searchPicker);
//            
//        atpSearchWindow.addSelectionCompleteCallback(new Callback<List<String>>(){
//            public void exec(List<String> event) {
//                final String selected = event.get(0);
//                if (selected.length() > 0){
////                    List<String> selectedItems = event;
////                    ChangeViewStateEvent tempEvent = new ChangeViewStateEvent(LUMViews.VIEW_COURSE, selectedItems);
////                    FindPanel.this.getController().fireApplicationEvent(new ChangeViewStateEvent<LUMViews>(LUMViews.VIEW_COURSE, event));
//                    courseSearchWindow.hide();
//                }                
//            }            
//        });        
//    }
}
