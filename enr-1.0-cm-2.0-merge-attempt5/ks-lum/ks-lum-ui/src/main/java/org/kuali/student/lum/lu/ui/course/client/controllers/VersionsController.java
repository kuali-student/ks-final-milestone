package org.kuali.student.lum.lu.ui.course.client.controllers;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.configurable.mvc.layouts.BasicLayoutWithContentHeader;
import org.kuali.student.common.ui.client.configurable.mvc.sections.HorizontalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.common.ui.client.mvc.ModelProvider;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.notification.KSNotification;
import org.kuali.student.common.ui.client.widgets.notification.KSNotifier;
import org.kuali.student.common.ui.client.widgets.progress.BlockingTask;
import org.kuali.student.common.ui.client.widgets.progress.KSBlockingProgressIndicator;
import org.kuali.student.lum.common.client.lu.LUUIConstants;
import org.kuali.student.lum.lu.ui.course.client.configuration.CourseSummaryConfigurer;
import org.kuali.student.lum.lu.ui.course.client.requirements.CourseRequirementsDataModel;
import org.kuali.student.lum.lu.ui.course.client.requirements.HasRequirements;
import org.kuali.student.lum.lu.ui.course.client.service.CourseRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.CourseRpcServiceAsync;
import org.kuali.student.lum.lu.ui.course.client.views.SelectVersionsView;
import org.kuali.student.lum.lu.ui.course.client.views.ShowVersionView;
import org.kuali.student.lum.lu.ui.course.client.widgets.CourseWorkflowActionList;
import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r1.common.assembly.data.Metadata;
import org.kuali.student.r2.common.dto.DtoConstants;
import org.kuali.student.r1.core.statement.dto.StatementTypeInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class VersionsController extends BasicLayoutWithContentHeader implements HasRequirements{
	
	public static enum Views{VERSION_SELECT, VERSION_VIEW, VERSION_COMPARE}
	
	private SelectVersionsView select = new SelectVersionsView(this, "", Views.VERSION_SELECT);
	private ShowVersionView view;
	private VerticalSectionView compare;
    private static final String MSG_GROUP = "course";
    private String type = "course";
    private String state = DtoConstants.STATE_DRAFT;
    private String groupName = LUUIConstants.COURSE_GROUP_NAME;
	CourseSummaryConfigurer summaryConfigurer;
	CourseRpcServiceAsync rpcServiceAsync = GWT.create(CourseRpcService.class);
	DataModelDefinition definition;
	
	private DataModel cluModel1;
	private DataModel cluModel2;
	
	private String lastId1 = "";
	private String lastId2 = "";
	private HorizontalSection workflowVersionInfoSection = new HorizontalSection();
	
    private final CourseRequirementsDataModel reqDataModel1;
    private final CourseRequirementsDataModel reqDataModel2;
	
	private boolean initialized = false;
	private String versionIndId = "";
	private String currentVersionId = "";
	private final BlockingTask loadDataTask = new BlockingTask("Retrieving Data....");
	
	private List<CourseWorkflowActionList> actionDropDownWidgets = new ArrayList<CourseWorkflowActionList>();
	private KSLabel statusLabel = new KSLabel("");
	
	public VersionsController(Enum<?> viewType) {
		super(VersionsController.class.toString());
		this.addView(select);
        this.setDefaultView(Views.VERSION_SELECT);
        this.setName("Versions");
        this.setViewEnum(viewType);
        KSButton versionHistoryButton = new KSButton("Version History", ButtonStyle.DEFAULT_ANCHOR, new ClickHandler(){

    		@Override
    		public void onClick(ClickEvent event) {
    			VersionsController.this.showDefaultView(Controller.NO_OP_CALLBACK);
    		}
    	});
        versionHistoryButton.addStyleName("versionHistoryLink");

        reqDataModel1 = new CourseRequirementsDataModel(this);
        reqDataModel2 = new CourseRequirementsDataModel(this);

        workflowVersionInfoSection.addWidget(this.getStatusLabel());
        workflowVersionInfoSection.addWidget(this.generateActionDropDown());
        workflowVersionInfoSection.addWidget(versionHistoryButton);
		
        this.getHeader().addWidget(workflowVersionInfoSection);
        this.viewContainer.addStyleName("standard-content-padding");
        initialize();
    }	

	public void setVersionIndId(String versionIndId) {
		this.versionIndId = versionIndId;
	}
	
	private void initialize() {
        super.setDefaultModelId("Model");
        super.registerModel("Model", new ModelProvider<DataModel>() {

            @Override
            public void requestModel(final ModelRequestCallback<DataModel> callback) {
                if(getViewContext().getId() != null && !getViewContext().getId().isEmpty()){
            		getCourseFromCluId(getViewContext().getId(), 1, callback, true);
                }
                else{
                	callback.onModelReady(null);
                }
            }
        });
        
        super.registerModel("ComparisonModel", new ModelProvider<DataModel>() {
            @Override
            public void requestModel(final ModelRequestCallback<DataModel> callback) {
            	if(getViewContext().getAttribute("docId2") != null && !getViewContext().getAttribute("docId2").isEmpty()){
            		getCourseFromCluId(getViewContext().getAttribute("docId2"), 2, callback, false);	
            	}
            	else{
            		callback.onModelReady(null);
            	}
            }
        });
	}
	 
	@SuppressWarnings("unchecked")    
	private void getCourseFromCluId(final String courseId, final int modelNum, final ModelRequestCallback callback, final boolean id1Model){
		KSBlockingProgressIndicator.addTask(loadDataTask);
	
	    rpcServiceAsync.getData(courseId, new KSAsyncCallback<Data>(){
	
	        @Override
	        public void handleFailure(Throwable caught) {
	            Window.alert("Error loading Course: "+caught.getMessage());
	            callback.onRequestFail(caught);
	            KSBlockingProgressIndicator.removeTask(loadDataTask);
	        }
	
	        @Override
	        public void onSuccess(Data result) {
	        	if(modelNum == 1){
	        		cluModel1 = new DataModel();
	        		cluModel1.setDefinition(definition);
	        		cluModel1.setRoot(result);
	        		if(courseId.equals(currentVersionId)){
	        			String name = "Version " + cluModel1.get("version/sequenceNumber") + " (current version)";
	        			cluModel1.setModelName(name);
	        			view.setName(name);
	        			view.showWarningMessage(false);
	        		}
	        		else{
	        			String name = "Version " + cluModel1.get("version/sequenceNumber");
	        			cluModel1.setModelName(name);
	        			view.setName(name);
	        			view.showWarningMessage(true);
	        		}
	        		updateState(cluModel1);

	 	            reqDataModel1.retrieveStatementTypes(cluModel1.<String>get("id"), new Callback<Boolean>() {
	                    @Override
	                    public void exec(Boolean result) {
	                        if (result) {
	                            KSBlockingProgressIndicator.removeTask(loadDataTask);
	        	 	            callback.onModelReady(cluModel1);
	                        }
	                    }
	                }); 
	        	}
	        	else{
	        		cluModel2 = new DataModel();
	        		cluModel2.setDefinition(definition);
	        		cluModel2.setRoot(result);
	        		if(courseId.equals(currentVersionId)){
	        			cluModel2.setModelName("Version " + cluModel2.get("version/sequenceNumber") + " (current version)");
	        		}
	        		else{
	        			cluModel2.setModelName("Version " + cluModel2.get("version/sequenceNumber"));
	        		}

	 	            reqDataModel2.retrieveStatementTypes(cluModel2.<String>get("id"), new Callback<Boolean>() {
	                    @Override
	                    public void exec(Boolean result) {
	                        if (result) {
	                            KSBlockingProgressIndicator.removeTask(loadDataTask);
	        	 	            callback.onModelReady(cluModel2);
	                        }
	                    }
	                });
	        	}	            
	        }	
	    });
	}
	
    @Override
    public void showDefaultView(final Callback<Boolean> onReadyCallback) {
    	KSBlockingProgressIndicator.addTask(loadDataTask);
    	
        init(new Callback<Boolean>() {

            @Override
            public void exec(Boolean result) {
                if (result) {
                	VersionsController.super.showDefaultView(new Callback<Boolean>() {
                		
                		@Override
                		public void exec(Boolean result) {
                			onReadyCallback.exec(result);
                			KSBlockingProgressIndicator.removeTask(loadDataTask);
                		}
                	});
                } else {
                    onReadyCallback.exec(false);
                    KSBlockingProgressIndicator.removeTask(loadDataTask);
                }
                
            }
        });
    }
    
    @Override
    public void beforeShow(Callback<Boolean> onReadyCallback) {
    	workflowVersionInfoSection.setVisible(false);
    	this.getHeader().showPrint(false);
    	this.getHeader().showExport(false);
    	showDefaultView(onReadyCallback);
    }
    
    private void init(final Callback<Boolean> onReadyCallback) {

        KSBlockingProgressIndicator.addTask(loadDataTask);

        rpcServiceAsync.getMetadata("", null, new KSAsyncCallback<Metadata>() {

            public void handleFailure(Throwable caught) {
                initialized = false;
                onReadyCallback.exec(false);
                KSBlockingProgressIndicator.removeTask(loadDataTask);
                throw new RuntimeException("Failed to get model definition.", caught);
            }

            public void onSuccess(Metadata result) {
                definition = new DataModelDefinition(result);
                KSBlockingProgressIndicator.removeTask(loadDataTask);
                configureScreens(onReadyCallback);
            }
        });

    }

    private void configureScreens(final Callback<Boolean> onReadyCallback) {
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

                summaryConfigurer = GWT.create(CourseSummaryConfigurer.class);
                summaryConfigurer.init(type, state, groupName, definition, stmtTypesOut, VersionsController.this, "Model");
                view = new ShowVersionView(Views.VERSION_VIEW, "Version", "Model", VersionsController.this, stmtTypesOut);
                compare = summaryConfigurer.generateCourseSummarySection();
                compare.setLayoutController(VersionsController.this);
                compare.setSectionTitle("Compare Versions");
                compare.setName("Compare Versions");
                compare.setViewEnum(Views.VERSION_COMPARE);
                VersionsController.this.addView(view);
                VersionsController.this.addView(compare);
                initialized = true;
                onReadyCallback.exec(true);
            }
        });
    }
    
    public Widget generateActionDropDown(){
    	CourseWorkflowActionList actionList = new CourseWorkflowActionList(this.getMessage("cluActionsLabel"));

    	actionDropDownWidgets.add(actionList);
        
    	return actionList;
    }
    
    private void updateState(final DataModel cluModel) {
    	if(cluModel.get("stateKey") != null){
            statusLabel.setText(getMessage("courseStatusLabel") + ": " + cluModel.get("stateKey"));
	    	
	    	for(CourseWorkflowActionList widget: actionDropDownWidgets){
				widget.init(getViewContext(), "/HOME/CURRICULUM_HOME/COURSE_PROPOSAL", cluModel, new Callback<String>() {
					@Override
			        public void exec(String newState) {
			            if (newState != null) {
			                KSNotifier.add(new KSNotification(getMessage("cluStateChangeNotification" + newState), false, 5000));
			                // FIXME: this is not updating the cluModel so state will not be updated in the model.  May not be a problem.
                                    statusLabel.setText(getMessage("courseStatusLabel") + ": " + newState);
			            } else {
			            	KSNotifier.add(new KSNotification(getMessage("cluStateChangeFailedNotification"), false, 5000));
			            }
			        }
				});
				widget.updateCourseActionItems(cluModel);

			}
    	}
    }
    
	public Widget getStatusLabel() {
		statusLabel.setStyleName("courseStatusLabel");
		return statusLabel;
	}
    
    public DataModelDefinition getDefinition(){
    	return definition;
    }

	public void setCurrentVersionId(String id) {
		this.currentVersionId = id;
	}

	public String getCurrentVersionId() {
		return currentVersionId;
	}
	
	public String getVersionIndId() {
		return versionIndId;
	}
	
    public String getMessage(String courseMessageKey) {
    	String msg = Application.getApplicationContext().getMessage(MSG_GROUP, courseMessageKey);
    	if (msg == null) {
    		msg = courseMessageKey;
    	}
    	return msg;
    }
	
	
	@Override
	public <V extends Enum<?>> void showView(V viewType, Callback<Boolean> onReadyCallback) {
		if(viewType != Views.VERSION_SELECT){
			workflowVersionInfoSection.setVisible(true);
			this.getHeader().showPrint(true);
			this.getHeader().showExport(true);
		}
		else{
			workflowVersionInfoSection.setVisible(false);
			this.getHeader().showPrint(false);
			this.getHeader().showExport(false);
		}
		super.showView(viewType, onReadyCallback);
	}



	public void setCurrentTitle(String currentTitle) {
    	this.getHeader().setTitle(currentTitle);
	}
	
	
	@Override
	public CourseRequirementsDataModel getReqDataModel() {
		return reqDataModel1;
	}

	public CourseRequirementsDataModel getReqDataModelComp() {
		return reqDataModel2;
	} 
}
