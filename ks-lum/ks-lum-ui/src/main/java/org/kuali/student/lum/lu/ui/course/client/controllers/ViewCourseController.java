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

import org.kuali.student.common.assembly.data.Data;
import org.kuali.student.common.assembly.data.Metadata;
import org.kuali.student.common.dto.DtoConstants;
import org.kuali.student.common.rice.authorization.PermissionType;
import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.configurable.mvc.layouts.DocumentLayoutController;
import org.kuali.student.common.ui.client.configurable.mvc.layouts.TabMenuController;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.common.ui.client.mvc.ModelProvider;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.mvc.WorkQueue;
import org.kuali.student.common.ui.client.mvc.WorkQueue.WorkItem;
import org.kuali.student.common.ui.client.mvc.dto.ReferenceModel;
import org.kuali.student.common.ui.client.util.ExportElement;
import org.kuali.student.common.ui.client.util.ExportUtils;
import org.kuali.student.common.ui.client.util.WindowTitleUtils;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.notification.KSNotification;
import org.kuali.student.common.ui.client.widgets.notification.KSNotifier;
import org.kuali.student.common.ui.client.widgets.progress.BlockingTask;
import org.kuali.student.common.ui.client.widgets.progress.KSBlockingProgressIndicator;
import org.kuali.student.common.ui.client.widgets.table.summary.SummaryTableSection;
import org.kuali.student.common.ui.shared.IdAttributes.IdType;
import org.kuali.student.core.statement.dto.StatementTypeInfo;
import org.kuali.student.lum.common.client.helpers.RecentlyViewedHelper;
import org.kuali.student.lum.lu.ui.course.client.configuration.CourseConfigurer;
import org.kuali.student.lum.lu.ui.course.client.configuration.ViewCourseConfigurer;
import org.kuali.student.lum.lu.ui.course.client.configuration.CourseConfigurer.CourseSections;
import org.kuali.student.lum.lu.ui.course.client.configuration.ViewCourseConfigurer.ViewCourseSections;
import org.kuali.student.lum.lu.ui.course.client.requirements.CourseRequirementsDataModel;
import org.kuali.student.lum.lu.ui.course.client.requirements.HasRequirements;
import org.kuali.student.lum.lu.ui.course.client.service.CourseRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.CourseRpcServiceAsync;
import org.kuali.student.lum.lu.ui.course.client.widgets.CourseWorkflowActionList;

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
public class ViewCourseController extends TabMenuController implements DocumentLayoutController, HasRequirements{
    private final DataModel cluModel = new DataModel(); 
   
    private WorkQueue modelRequestQueue;

    private String cluType = "kuali.lu.type.CreditCourse";
    private String courseId = null;
    
    private static final String CLU_STATE = DtoConstants.STATE_ACTIVE;
    private static final String MSG_GROUP = "course";
    
    private final String REFERENCE_TYPE = "referenceType.clu";
    private boolean initialized = false;
    CourseRpcServiceAsync rpcServiceAsync = GWT.create(CourseRpcService.class);
    
	private final BlockingTask loadDataTask = new BlockingTask("Retrieving Data....");
	private final BlockingTask initTask = new BlockingTask("Initializing....");
	private final KSLabel statusLabel = new KSLabel("");
	
	private final List<CourseWorkflowActionList> actionDropDownWidgets = new ArrayList<CourseWorkflowActionList>();

    private final CourseRequirementsDataModel reqDataModel;
    
    final ViewCourseConfigurer cfg = GWT.create(ViewCourseConfigurer.class);
	            
    public ViewCourseController(Enum<?> viewType){
    	super(CourseProposalController.class.getName());
        initialize();
        addStyleName("courseView");
        reqDataModel = new CourseRequirementsDataModel(this);
        this.tabPanel.addStyleName("standard-content-padding");
        this.setViewEnum(viewType);
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
    	CourseWorkflowActionList actionList = new CourseWorkflowActionList(this.getMessage("cluActionsLabel"), getViewContext(), "/HOME/CURRICULUM_HOME/COURSE_PROPOSAL", cluModel, new Callback<String>() {
    		@Override
    		public void exec(String newState) {
    			if (newState != null) {
                    KSNotifier.add(new KSNotification(getMessage("cluStateChangeNotification" + newState), false, 5000));
                    // FIXME: this is not updating the cluModel so state will not be updated in the model.  May not be a problem.
                    statusLabel.setText("Status: " + newState);
    			} else {
                    KSNotifier.add(new KSNotification(getMessage("cluStateChangeFailedNotification"), false, 5000));
    			}
    		}
    	});
        actionDropDownWidgets.add(actionList);
        
    	return actionList;
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

	        	@Override
                public void handleFailure(Throwable caught) {
	        		initialized = false;
                	onReadyCallback.exec(false);
                	KSBlockingProgressIndicator.removeTask(initTask);
                    throw new RuntimeException("Failed to get model definition.", caught);
                }

                public void onSuccess(Metadata result) {
                	DataModelDefinition def = new DataModelDefinition(result);
                    cluModel.setDefinition(def);
                    init(def, onReadyCallback);
                }
	          });
            
        }
    }

    private void updateCourseActionItems() {
    	
		for(CourseWorkflowActionList widget: actionDropDownWidgets){
			widget.updateCourseActionItems(cluModel);
			widget.setEnabled(true);
			if(widget.isEmpty()) {
				widget.setVisible(false);
			}
			else{
				widget.setVisible(true);
			}
		}
    }

    private void init(final DataModelDefinition modelDefinition, final Callback<Boolean> onReadyCallback){

        CourseRequirementsDataModel.getStatementTypes(new Callback<List<StatementTypeInfo>>() {

            @Override
            public void exec(List<StatementTypeInfo> stmtTypes) {
                List<StatementTypeInfo> stmtTypesOut = new ArrayList<StatementTypeInfo>();
                if (stmtTypes != null) {
                    for (StatementTypeInfo stmtType : stmtTypes) {
                        if (stmtType.getId().contains("kuali.statement.type.course.enrollmentEligibility") ||
                            stmtType.getId().contains("kuali.statement.type.course.creditConstraints")) {
                            continue;
                        }
                        stmtTypesOut.add(stmtType);
                    }
                }
                if(!initialized){
                	initialized = true;
	                cfg.setStatementTypes(stmtTypesOut);
	                cfg.setModelDefinition(modelDefinition);
	                cfg.generateLayout(ViewCourseController.this);
                }
                onReadyCallback.exec(true);
                KSBlockingProgressIndicator.removeTask(initTask);
            }
        });
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
        }else if (modelType == Data.class){
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
                updateCourseActionItems();
                callback.onModelReady(cluModel);
                workCompleteCallback.exec(true);
                reqDataModel.retrieveStatementTypes(cluModel.<String>get("id"), new Callback<Boolean>() {
                    @Override
                    public void exec(Boolean result) {
                        if (result) {
                            KSBlockingProgressIndicator.removeTask(loadDataTask);
                        }
                    }
                });

            }

        });
    }
    
    @SuppressWarnings("unchecked")
    private void getCurrentVersion(final ModelRequestCallback callback, final Callback<Boolean> workCompleteCallback) {
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
                updateCourseActionItems();
                callback.onModelReady(cluModel);
                workCompleteCallback.exec(true);
                KSBlockingProgressIndicator.removeTask(loadDataTask);
            }

        });
    }
    
    @SuppressWarnings("unchecked")
    private void createNewCluModel(final ModelRequestCallback callback, final Callback<Boolean> workCompleteCallback){
        cluModel.setRoot(new Data());
        callback.onModelReady(cluModel);
        workCompleteCallback.exec(true);
    }

    public String getCourseId() {
        return courseId;
    }
    
    public String getVersionIndId() {
        return (String)cluModel.get("versionInfo/versionIndId");
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
        this.cluModel.setRoot(new Data());        
    }
       
    public void clear(String cluType){
        super.clear();
        this.cluType = cluType;
        if (cluModel != null){
            this.cluModel.setRoot(new Data());            
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
    	
    	updateStatus();
    	
    	this.setContentTitle(title);
    	this.setName(title);
    	WindowTitleUtils.setContextTitle(title);
    }
    
    private void updateStatus() {
    	if(cluModel.get("state") != null){
    		statusLabel.setText("Status: " + cluModel.get("state"));
    	}
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

    public String getMessage(String courseMessageKey) {
    	String msg = Application.getApplicationContext().getMessage(MSG_GROUP, courseMessageKey);
    	if (msg == null) {
    		msg = courseMessageKey;
    	}
    	return msg;
    }
    
	public Widget getStatusLabel() {
		statusLabel.setStyleName("courseStatusLabel");
		return statusLabel;
	}
	
	public Widget getVersionHistoryWidget(){
		KSButton button = new KSButton("Version History", ButtonStyle.DEFAULT_ANCHOR, new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				parentController.showView(ViewCourseParentController.Views.VERSIONS);
			}
		});
		button.addStyleName("versionHistoryLink");
		return button;
		
	}
	
	@Override
	public void onHistoryEvent(String historyStack) {
		super.onHistoryEvent(historyStack);
		if (cluModel.get("courseTitle") != null){
			RecentlyViewedHelper.addCurrentDocument(getCourseTitle());
		}
	}
	
	public String getCourseTitle(){
		return cluModel.get("courseTitle");
	}

	// this is misleading given the current version concept.  This just gets the id of the course
	public String getCurrentId() {
		return cluModel.get("id");
	}

    @Override
    public CourseRequirementsDataModel getReqDataModel() {
        return reqDataModel;
    }
    
    @Override
    public DataModel getExportDataModel() {
        return cluModel;
    }
    
    @Override
    public boolean isExportButtonActive() {
        if (this.getCurrentViewEnum() != null && this.getCurrentViewEnum().equals(ViewCourseSections.DETAILED)) {
            return true;
        }
        return false;
    }
    
    @Override
    public ArrayList<ExportElement> getExportElementsFromView() {
        ArrayList<ExportElement> exportElements = new ArrayList<ExportElement>();
        if (this.getCurrentViewEnum().equals(ViewCourseSections.DETAILED)) {      
            SummaryTableSection tableSection = this.cfg.getSummaryConfigurer().getTableSection();
            ExportElement heading = new ExportElement();
            heading.setFieldLabel("");
            heading.setFieldValue(tableSection.getTitle());
            exportElements.add(heading);
            exportElements = ExportUtils.getDetailsForWidget(tableSection, exportElements);
        }
        return exportElements;
    }
}