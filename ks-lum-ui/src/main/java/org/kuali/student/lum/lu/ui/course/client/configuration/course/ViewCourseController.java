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
package org.kuali.student.lum.lu.ui.course.client.configuration.course;

import java.util.List;

import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.configurable.mvc.layouts.TabbedSectionLayout;
import org.kuali.student.common.ui.client.event.ChangeViewActionEvent;
import org.kuali.student.common.ui.client.event.ValidateRequestEvent;
import org.kuali.student.common.ui.client.event.ValidateRequestHandler;
import org.kuali.student.common.ui.client.event.ValidateResultEvent;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.common.ui.client.mvc.ModelProvider;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.WorkQueue;
import org.kuali.student.common.ui.client.mvc.WorkQueue.WorkItem;
import org.kuali.student.common.ui.client.mvc.dto.ReferenceModel;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.KSProgressIndicator;
import org.kuali.student.common.ui.client.widgets.containers.KSTitleContainerImpl;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.validation.dto.ValidationResultContainer;
import org.kuali.student.lum.lu.assembly.data.client.LuData;
import org.kuali.student.lum.lu.ui.course.client.service.CourseRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.CourseRpcServiceAsync;
import org.kuali.student.lum.lu.ui.course.client.widgets.ViewCourseActionList;
import org.kuali.student.lum.lu.ui.main.client.controller.LUMApplicationManager.LUMViews;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in. 
 * 
 * @author Kuali Student Team
 *
 */
public class ViewCourseController extends TabbedSectionLayout { 
    private final DataModel cluModel = new DataModel(); 
//    private RequirementsDisplayWidget.RequirementsModel requirementsModel;
    
    private WorkQueue modelRequestQueue;

    private String cluType = "kuali.lu.type.CreditCourse";
    private String courseId = null;
    
    private static final String CLU_STATE = "active";
    private static final String CLU_ID_PATH   = "id";
    
    private final String REFERENCE_TYPE = "referenceType.clu";
    private boolean initialized = false;
    CourseRpcServiceAsync rpcServiceAsync = GWT.create(CourseRpcService.class);
    
    final KSLightBox progressWindow = new KSLightBox();

    private static KSTitleContainerImpl layoutTitle = new KSTitleContainerImpl("View Course");
    
    private ViewCourseActionList actionToolbar;
    
            
    public ViewCourseController(){
        super(ViewCourseController.class.getName(), layoutTitle);
        initialize();
    }
    public ViewCourseController(ViewContext context){
        super(ViewCourseController.class.getName(), layoutTitle);
    	setViewContext(context);
        initialize();
    }
    public ViewCourseController(String cluType){
        super(ViewCourseController.class.getName(), layoutTitle);
        this.cluType = cluType;        
        initialize();
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
        super.addApplicationEventHandler(ValidateRequestEvent.TYPE, new ValidateRequestHandler() {

            @Override
            public void onValidateRequest(ValidateRequestEvent event) {
                requestModel(new ModelRequestCallback<DataModel>() {
                    @Override
                    public void onModelReady(DataModel model) {
                        model.validate(new Callback<List<ValidationResultContainer>>() {
                            @Override
                            public void exec(List<ValidationResultContainer> result) {
                                ValidateResultEvent e = new ValidateResultEvent();
                                e.setValidationResult(result);
                                fireApplicationEvent(e);
                            }
                        });
                    }

                    @Override
                    public void onRequestFail(Throwable cause) {
                        GWT.log("Unable to retrieve model for validation", cause);
                    }
                    
                });
            }
            
        });
        actionToolbar = new ViewCourseActionList(createActionSubmitSuccessHandler());
        actionToolbar.setIdPath(CLU_ID_PATH);
//        actionToolbar.setRequiredFieldPaths(new String[]{"course/department"});
        actionToolbar.setRpcService(rpcServiceAsync);
        this.addToolbar(actionToolbar);

    }
   
    private void init(final Callback<Boolean> onReadyCallback) {
        KSProgressIndicator progressInd = new KSProgressIndicator();
        progressInd.setText("Loading");
        progressInd.show();
        progressWindow.setWidget(progressInd);

        if (initialized) {
            onReadyCallback.exec(true);
        } else {
            progressWindow.show();
            rpcServiceAsync.getMetadata("", "", 
                    new AsyncCallback<Metadata>(){
    
                        @Override
                        public void onFailure(Throwable caught) {
                            onReadyCallback.exec(false);
                            progressWindow.hide();
                            throw new RuntimeException("Failed to get model definition.", caught);                        
                        }
    
                        @Override
                        public void onSuccess(Metadata result) {
                            DataModelDefinition def = new DataModelDefinition(result);
                            cluModel.setDefinition(def);
                            init(def);
                            initialized = true;
                            onReadyCallback.exec(true);
                            progressWindow.hide();
                        }                
                });
            
        }
    }
    private void init(DataModelDefinition modelDefinition){
        ViewCourseConfigurer cfg = GWT.create(ViewCourseConfigurer.class);
        super.setUpdateableSection(false);

        cfg.setModelDefinition(modelDefinition);
        cfg.generateLayout(this);
             
        if (!initialized) {
            addButton(cfg.getTabKey(), getQuitButton());
        }
        initialized = true;
    }
        
    /**
     * @see org.kuali.student.common.ui.client.mvc.Controller#getViewsEnum()
     */
    @Override
    public Class<? extends Enum<?>> getViewsEnum() {
        return ViewCourseConfigurer.Sections.class;
    }

   

    
    @SuppressWarnings("unchecked")
    @Override
    public void requestModel(Class modelType, final ModelRequestCallback callback) {
        if(modelType == ReferenceModel.class){
            if (cluModel != null){
                ReferenceModel ref = new ReferenceModel();

                //FIXME: test code
                if(cluModel.get("course/id") != null){
                    ref.setReferenceId((String)cluModel.get("course/id"));
                }
                else{
                    ref.setReferenceId(null);
                }
                
                ref.setReferenceTypeKey(REFERENCE_TYPE);
                ref.setReferenceType(cluType);
                ref.setReferenceState(CLU_STATE);
                
                callback.onModelReady(ref);
            }
        }else if (modelType == LuData.class){
            requestModel(CourseConfigurer.CLU_PROPOSAL_MODEL, callback);
        } else /*
        */ {
            super.requestModel(modelType, callback);
        }
    }
       
    @SuppressWarnings("unchecked")    
    private void getCourseFromCluId(final ModelRequestCallback callback, final Callback<Boolean> workCompleteCallback){
        progressWindow.show();
        rpcServiceAsync.getData(courseId, new AsyncCallback<Data>(){

            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Error loading Course: "+caught.getMessage());
                createNewCluModel(callback, workCompleteCallback);
                progressWindow.hide();
            }

            @Override
            public void onSuccess(Data result) {
                cluModel.setRoot(result);
                getContainer().setTitle(getSectionTitle());
                callback.onModelReady(cluModel);
                workCompleteCallback.exec(true);
                progressWindow.hide();
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
                    doShowDefaultView(onReadyCallback);
                } else {
                    onReadyCallback.exec(false);
                }
            }
        });
    }
    
    private void doShowDefaultView(final Callback<Boolean> onReadyCallback) {
        super.showDefaultView(onReadyCallback);
    }
    
    private KSButton getQuitButton(){
        return new KSButton("Quit", new ClickHandler(){
                    public void onClick(ClickEvent event) {
                        Controller parentController = ViewCourseController.this.getParentController(); 
                        parentController.fireApplicationEvent(new ChangeViewActionEvent<LUMViews>(LUMViews.HOME_MENU));
                    }
                });       
    }
    
    
    protected  String getSectionTitle() {
               
    	StringBuffer sb = new StringBuffer();
    	sb.append(cluModel.get("courseCode"));
    	sb.append(" - ");
    	sb.append(cluModel.get("transcriptTitle"));

    	return sb.toString();

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
}