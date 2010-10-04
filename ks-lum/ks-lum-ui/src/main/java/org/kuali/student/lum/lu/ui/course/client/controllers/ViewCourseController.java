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

package org.kuali.student.lum.lu.ui.course.client.controllers;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.configurable.mvc.layouts.DocumentLayoutController;
import org.kuali.student.common.ui.client.configurable.mvc.layouts.TabMenuController;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.common.ui.client.mvc.ModelProvider;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.WorkQueue;
import org.kuali.student.common.ui.client.mvc.WorkQueue.WorkItem;
import org.kuali.student.common.ui.client.mvc.dto.ReferenceModel;
import org.kuali.student.common.ui.client.mvc.history.HistoryManager;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.StylishDropDown;
import org.kuali.student.common.ui.client.widgets.menus.KSMenuItemData;
import org.kuali.student.common.ui.client.widgets.progress.BlockingTask;
import org.kuali.student.common.ui.client.widgets.progress.KSBlockingProgressIndicator;
import org.kuali.student.common.ui.shared.IdAttributes.IdType;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.rice.StudentIdentityConstants;
import org.kuali.student.core.rice.authorization.PermissionType;
import org.kuali.student.lum.lu.assembly.data.client.LuData;
import org.kuali.student.lum.lu.ui.course.client.configuration.CourseConfigurer;
import org.kuali.student.lum.lu.ui.course.client.configuration.ViewCourseConfigurer;
import org.kuali.student.lum.lu.ui.course.client.helpers.RecentlyViewedHelper;
import org.kuali.student.lum.lu.ui.course.client.service.CourseRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.CourseRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in. 
 * 
 * @author Kuali Student Team
 *
 */
public class ViewCourseController extends TabMenuController implements DocumentLayoutController { 
    private final DataModel cluModel = new DataModel(); 
   
    private WorkQueue modelRequestQueue;

    private String cluType = "kuali.lu.type.CreditCourse";
    private String courseId = null;
    
    private static final String CLU_STATE = "Active";
    
    private final String REFERENCE_TYPE = "referenceType.clu";
    private boolean initialized = false;
    CourseRpcServiceAsync rpcServiceAsync = GWT.create(CourseRpcService.class);
    
	private BlockingTask loadDataTask = new BlockingTask("Retrieving Data....");
	private BlockingTask initTask = new BlockingTask("Initializing....");
	private KSLabel statusLabel = new KSLabel("");
            
    public ViewCourseController(){
    	super(CourseProposalController.class.getName());
        initialize();
        addStyleName("courseView");
    }
    
    @Override
    public void setViewContext(ViewContext viewContext) {
    	super.setViewContext(viewContext);
    	if(viewContext.getId() != null && !viewContext.getId().isEmpty()){
    		viewContext.setPermissionType(PermissionType.OPEN);
    		this.setCourseId(viewContext.getId());
    	}
    }
    
    private void initialize() {
        super.setDefaultModelId(CourseConfigurer.CLU_PROPOSAL_MODEL);
        super.registerModel(CourseConfigurer.CLU_PROPOSAL_MODEL, new ModelProvider<DataModel>() {

            @Override
            public void requestModel(final ModelRequestCallback<DataModel> callback) {
                if (modelRequestQueue == null){
                    modelRequestQueue = new WorkQueue();
                }

                WorkItem workItem = new WorkItem(){
                    @Override
                    public void exec(Callback<Boolean> workCompleteCallback) {
                        if (cluModel.getRoot() == null || cluModel.getRoot().size() == 0){
                            if (courseId != null){
                                getCourseFromCluId(callback, workCompleteCallback);
                            } else{
                                createNewCluModel(callback, workCompleteCallback);
                            }                
                        } else {
                            callback.onModelReady(cluModel);
                            workCompleteCallback.exec(true);
                        }
                    }               
                };
                modelRequestQueue.submit(workItem);
                
            }
            
        });

    }
    
    public Widget generateActionDropDown(){
    	List<KSMenuItemData> items = new ArrayList<KSMenuItemData>();
    	StylishDropDown actions = new StylishDropDown("Course Actions");
    	items.add(new KSMenuItemData("Propose Course Modification", new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				if(getViewContext() != null && getViewContext().getId() != null && !getViewContext().getId().isEmpty()){
					ViewContext viewContext = new ViewContext();
					viewContext.setId((String)cluModel.get("versionInfo/versionIndId"));
                    viewContext.setIdType(IdType.COPY_OF_OBJECT_ID);
                    viewContext.setAttribute(StudentIdentityConstants.DOCUMENT_TYPE_NAME, "kuali.proposal.type.course.modify");
					HistoryManager.navigate("/HOME/CURRICULUM_HOME/COURSE_PROPOSAL", viewContext);
				}
			}
		}));
        actions.setItems(items);
        return actions;
    }
   
    private void init(final Callback<Boolean> onReadyCallback) {

        if (initialized) {
            onReadyCallback.exec(true);
        } else {
        	KSBlockingProgressIndicator.addTask(initTask);
        	this.setContentTitle("View Course");
    		this.setName("Course");
    		String idType = null;
    		String viewContextId = null;
    		// The switch was added due to the way permissions currently work.
    		// For a new Create Course Proposal or Modify Course we send nulls so that permissions are not checked.
    		if(getViewContext().getIdType() != null){
                idType = getViewContext().getIdType().toString();
                viewContextId = getViewContext().getId();
                if(getViewContext().getIdType()==IdType.COPY_OF_OBJECT_ID){
                	viewContextId = null;
                }

    		}
    		
        	rpcServiceAsync.getMetadata("", null, new KSAsyncCallback<Metadata>(){

	        	public void handleFailure(Throwable caught) {
	        		initialized = false;
                	onReadyCallback.exec(false);
                	KSBlockingProgressIndicator.removeTask(initTask);
                    throw new RuntimeException("Failed to get model definition.", caught);
                }

                public void onSuccess(Metadata result) {
                	DataModelDefinition def = new DataModelDefinition(result);
                    cluModel.setDefinition(def);
                    init(def);
                    onReadyCallback.exec(true);
                    KSBlockingProgressIndicator.removeTask(initTask);
                }
	          });
            
        }
    }
    
    private void init(DataModelDefinition modelDefinition){
        ViewCourseConfigurer cfg = GWT.create(ViewCourseConfigurer.class);
        
        cfg.setModelDefinition(modelDefinition);
        cfg.generateLayout(this);
        
        initialized = true;
    }
        
    /**
     * @see org.kuali.student.common.ui.client.mvc.Controller#getViewsEnum()
     */
    @Override
    public Class<? extends Enum<?>> getViewsEnum() {
        return ViewCourseConfigurer.ViewCourseSections.class;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void requestModel(Class modelType, final ModelRequestCallback callback) {
        if(modelType == ReferenceModel.class){
            if (cluModel != null){
                ReferenceModel ref = new ReferenceModel();

                if(cluModel.get("course/id") != null){
                    ref.setReferenceId((String)cluModel.get("course/id"));
                } else{
                    ref.setReferenceId(null);
                }
                
                ref.setReferenceTypeKey(REFERENCE_TYPE);
                ref.setReferenceType(cluType);
                ref.setReferenceState(CLU_STATE);
                
                callback.onModelReady(ref);
            }
        }else if (modelType == LuData.class){
            requestModel(CourseConfigurer.CLU_PROPOSAL_MODEL, callback);
        } else {
            super.requestModel(modelType, callback);
        }
    }
       
    @SuppressWarnings("unchecked")    
    private void getCourseFromCluId(final ModelRequestCallback callback, final Callback<Boolean> workCompleteCallback){
    	KSBlockingProgressIndicator.addTask(loadDataTask);

        rpcServiceAsync.getData(courseId, new KSAsyncCallback<Data>(){

            @Override
            public void handleFailure(Throwable caught) {
                Window.alert("Error loading Course: "+caught.getMessage());
                createNewCluModel(callback, workCompleteCallback);
                KSBlockingProgressIndicator.removeTask(loadDataTask);
            }

            @Override
            public void onSuccess(Data result) {
                cluModel.setRoot(result);
                //getContainer().setTitle(getSectionTitle());
                setHeaderTitle();
                callback.onModelReady(cluModel);
                workCompleteCallback.exec(true);
                KSBlockingProgressIndicator.removeTask(loadDataTask);
            }

        });
    }
    
    @SuppressWarnings("unchecked")
    private void createNewCluModel(final ModelRequestCallback callback, final Callback<Boolean> workCompleteCallback){
        cluModel.setRoot(new LuData());
        callback.onModelReady(cluModel);
        workCompleteCallback.exec(true);            
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
        this.cluModel.setRoot(new LuData());        
    }
       
    public void clear(String cluType){
        super.clear();
        this.cluType = cluType;
        if (cluModel != null){
            this.cluModel.setRoot(new LuData());            
        }
        this.courseId = null;
    }
    
    
    @Override
    public void showDefaultView(final Callback<Boolean> onReadyCallback) {
        init(new Callback<Boolean>() {

            @Override
            public void exec(Boolean result) {
                if (result) {
                	ViewCourseController.super.showDefaultView(onReadyCallback);
                } else {
                    onReadyCallback.exec(false);
                }
            }
        });
    }
    
    @Override
    public void beforeShow(Callback<Boolean> onReadyCallback) {
    	showDefaultView(onReadyCallback);
    }
    
    private KSButton getQuitButton(){
        return new KSButton("Quit", new ClickHandler(){
                    public void onClick(ClickEvent event) {
                    	Application.navigate("/HOME/CURRICULUM_HOME");
                    }
                });       
    }
    
    
    protected void setHeaderTitle() {
               
    	String title; 
    	if (cluModel.get("transcriptTitle") != null){
    		title = getCourseTitle();
    	}
    	else{
    		title = "Course";
    	}

    	if(cluModel.get("state") != null){
    		statusLabel.setText("Status: " + cluModel.get("state"));
    	}
    	
    	this.setContentTitle(title);
    	this.setName(title);

    }
    
    private CloseHandler<KSLightBox> createActionSubmitSuccessHandler() {
    	CloseHandler<KSLightBox> handler = new CloseHandler<KSLightBox>(){
			@Override
			public void onClose(CloseEvent<KSLightBox> event) {
				//Reload the lum main entrypoint
				Window.Location.reload();
			}
    	};
		return handler;
	}

	public Widget getStatusLabel() {
		statusLabel.setStyleName("courseStatusLabel");
		return statusLabel;
	}
	
	@Override
	public void onHistoryEvent(String historyStack) {
		super.onHistoryEvent(historyStack);
		if (cluModel.get("transcriptTitle") != null){
			RecentlyViewedHelper.addCurrentDocument(getCourseTitle());
		}
	}
	
	private String getCourseTitle(){
		StringBuffer sb = new StringBuffer();
		sb.append(cluModel.get("code"));
		sb.append(" - ");
		sb.append(cluModel.get("transcriptTitle"));
		return sb.toString();
	}

}