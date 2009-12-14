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
package org.kuali.student.lum.lu.ui.main.client.controller;

import java.util.List;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.DelegatingViewComposite;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.mvc.events.LogoutEvent;
import org.kuali.student.common.ui.client.mvc.events.LogoutHandler;
import org.kuali.student.lum.lu.ui.course.client.configuration.LUConstants;
import org.kuali.student.lum.lu.ui.course.client.configuration.course.CourseProposalController;
import org.kuali.student.lum.lu.ui.course.client.configuration.history.KSHistory;
import org.kuali.student.lum.lu.ui.course.client.configuration.viewclu.ViewCluController;
import org.kuali.student.lum.lu.ui.course.client.service.CluProposalRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.CluProposalRpcServiceAsync;
import org.kuali.student.lum.lu.ui.home.client.view.HomeMenuController;
import org.kuali.student.lum.lu.ui.main.client.events.ChangeViewStateEvent;
import org.kuali.student.lum.lu.ui.main.client.events.ChangeViewStateHandler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;

public class LUMApplicationManager extends Controller{

	   private final SimplePanel viewPanel = new SimplePanel();

	    KSHistory history;

	    private final View homeMenuView = new DelegatingViewComposite(this, new HomeMenuController());

	    private Controller cluProposalController = null;
	    private Controller viewCluController = null;
	    private DelegatingViewComposite createCluView;
	    private DelegatingViewComposite viewCluView;

	    private CluProposalRpcServiceAsync cluProposalRpcServiceAsync = GWT.create(CluProposalRpcService.class);

	    public LUMApplicationManager(){
	        super();
	        history = new KSHistory(this);
	        super.initWidget(viewPanel);
	    }

	    protected void onLoad() {
	        addApplicationEventHandler(ChangeViewStateEvent.TYPE, new ChangeViewStateHandler() {
	            public void onViewStateChange(ChangeViewStateEvent event) {
	                //FIXME: This is very hacky
	                if (event.getEventSource() != null && event.getEventSource() instanceof SelectionEvent){                    
	                    List<String> selectedIds = (List<String>)((SelectionEvent)event.getEventSource()).getSelectedItem();
	                    String selectedId = selectedIds.get(0);

	                    if (event.getViewType().equals(LUMViews.EDIT_COURSE_PROPOSAL)){
	                        initCluProposalViewFromProposalId(LUConstants.PROPOSAL_TYPE_COURSE_CREATE, LUConstants.CLU_TYPE_CREDIT_COURSE, selectedId);                        
	                    }

	                    if (event.getViewType().equals(LUMViews.VIEW_COURSE)) {
	                        initViewCluViewFromCluId(selectedId);                        
	                    }

	                }
	                showView(event.getViewType(), NO_OP_CALLBACK);  
	            }
	        });

	        addApplicationEventHandler(LogoutEvent.TYPE, new LogoutHandler() {
	            public void onLogout(LogoutEvent event) {
	                Window.Location.assign("/j_spring_security_logout");
	            }
	        });
	    }

	    public enum LUMViews {
	        HOME_MENU, CREATE_COURSE, EDIT_COURSE_PROPOSAL, VIEW_COURSE, CREATE_PROGRAM
	    }

	    @Override
	    protected <V extends Enum<?>> View getView(V viewType) {
	    	// FIXME the showDefaultView calls should probably be handled elsewhere, not in this factory method
	        switch ((LUMViews) viewType) {
	            case HOME_MENU:
	                return homeMenuView;
	            case CREATE_COURSE:
	                //initBlankCluProposalView(LUConstants.PROPOSAL_TYPE_COURSE_CREATE, LUConstants.CLU_TYPE_CREDIT_COURSE);
	                initBlankCourseView();
	                
	                //FIXME: This is a quick fix, need better way to reset view
	                cluProposalController.showDefaultView(NO_OP_CALLBACK);  

	                return createCluView;
	            case EDIT_COURSE_PROPOSAL:
	                //View setup should already be handled.

	                //FIXME: This is quick fix, need better way via config to set and show summary view.
	                cluProposalController.showDefaultView(NO_OP_CALLBACK); 
//	                cluProposalController.showView(LuConfigurer.LuSections.SUMMARY);//FIXME this was causing the nav bar not to show up
	                return createCluView;
	            case VIEW_COURSE:
	                if (viewCluView == null){
	                    viewCluView = new DelegatingViewComposite(this, new ViewCluController());
	                }
	                viewCluController.showDefaultView(NO_OP_CALLBACK);
	                return viewCluView;
	            case CREATE_PROGRAM:
	                initBlankCluProposalView(LUConstants.PROPOSAL_TYPE_PROGRAM_CREATE, LUConstants.CLU_TYPE_CREDIT_PROGRAM);  //FIXME replace with program specific constants

	                //FIXME: This is a quick fix, need better way to reset view
	                cluProposalController.showDefaultView(NO_OP_CALLBACK);  

	                return createCluView; //createProgramView;                
	            default:
	                return null;
	        }
	    }

	    private View initBlankCourseView(){
	        cluProposalController = new CourseProposalController();
	        createCluView = new DelegatingViewComposite(LUMApplicationManager.this, cluProposalController);
	        
	        return createCluView;
	    }
	    
	    private View initBlankCluProposalView(String proposalType, String cluType){
	       // if (cluProposalController == null){
	            cluProposalController = new CourseProposalController(proposalType, cluType);           
	       // }
	       // ((CluProposalController)cluProposalController).clear(proposalType, cluType);
	        createCluView = new DelegatingViewComposite(LUMApplicationManager.this, cluProposalController);         

	        return createCluView;
	    }

	    private View initCluProposalViewFromProposalId(String proposalType, String cluType, String proposalId){
	        initBlankCluProposalView(proposalType, cluType);
	        ((CourseProposalController)cluProposalController).setProposalId(proposalId);
	        return createCluView;
	    }

	    private View initCluProposalViewFromDocId(String proposalType, String cluType, String docId){
	    //    if (cluProposalController == null){
	    	cluProposalController = new CourseProposalController(proposalType, cluType, docId);           
	    //    }
	   //     ((CluProposalController)cluProposalController).clear(proposalType, cluType);
	        ((CourseProposalController)cluProposalController).setDocId(docId);
	        createCluView = new DelegatingViewComposite(LUMApplicationManager.this, cluProposalController); 
	        
	        return createCluView;        
	    }

	    private View initViewCluViewFromCluId(String cluId){
	        initBlankViewCluView();
	        ((ViewCluController)viewCluController).setId(cluId);
	        
	        return viewCluView;
	    }    
	    
	    private View initBlankViewCluView(){
	        if (viewCluController == null){
	            viewCluController = new ViewCluController(); 
	            viewCluView = new DelegatingViewComposite(LUMApplicationManager.this, viewCluController);            
	        }
	        ((ViewCluController)viewCluController).clear();

	        return viewCluView;
	    }
	    
	    //Accessor for get view
	    public <V extends Enum<?>> View getControllerView(V viewType){
	        return this.getView(viewType);
	    }

	    @Override
	    protected void hideView(View view) {
	        viewPanel.clear();

	    }

	    @Override
	    protected void renderView(View view) {
	        // TODO Bsmith - THIS METHOD NEEDS JAVADOCS
	        viewPanel.setWidget((Composite)view);
	    }

	    @Override
	    public void showDefaultView(final Callback<Boolean> onReadyCallback) {
	        final String docId=Window.Location.getParameter("docId");
	        String backdoorId=Window.Location.getParameter("backdoorId");
	        if(docId!=null){
	            if(backdoorId!=null){
	                cluProposalRpcServiceAsync.loginBackdoor(backdoorId, new AsyncCallback<Boolean>(){
	                    public void onFailure(Throwable caught) {
	                        Window.alert(caught.getMessage());
	                        onReadyCallback.exec(false);
	                    }

	                    public void onSuccess(Boolean result) {
	                        if(!result){
	                            Window.alert("Error with backdoor login");
	                            onReadyCallback.exec(false);
	                        }
	                        initCluProposalViewFromDocId(LUConstants.PROPOSAL_TYPE_COURSE_CREATE, LUConstants.CLU_TYPE_CREDIT_COURSE, docId);  //FIXME replace with program specific constants
	                        showView(LUMViews.EDIT_COURSE_PROPOSAL, onReadyCallback);
	                    }

	                });
	            }else{
	                initCluProposalViewFromDocId(LUConstants.PROPOSAL_TYPE_COURSE_CREATE, LUConstants.CLU_TYPE_CREDIT_COURSE, docId);  //FIXME replace with program specific constants
	                this.showView(LUMViews.EDIT_COURSE_PROPOSAL, onReadyCallback);
	            }
	        }
	        else{
	            this.showView(LUMViews.HOME_MENU, onReadyCallback);
	        }
	    }

	    public Class<? extends Enum<?>> getViewsEnum() {
	        return LUMViews.class;
	    }        
}
