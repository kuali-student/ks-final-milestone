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
package org.kuali.student.lum.lu.ui.tools.client.configuration;

import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.layouts.TabbedSectionLayout;
import org.kuali.student.common.ui.client.event.SaveActionEvent;
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
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.KSProgressIndicator;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.validation.dto.ValidationResultContainer;
import org.kuali.student.lum.lu.ui.course.client.configuration.course.CourseConfigurer;
import org.kuali.student.lum.lu.ui.tools.client.service.CluSetManagementRpcService;
import org.kuali.student.lum.lu.ui.tools.client.service.CluSetManagementRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class CluSetsManagementController extends TabbedSectionLayout { //PagedSectionLayout {  FIXME should be paged layout? 
	
    private final DataModel cluSetModel = new DataModel();    
    private WorkQueue modelRequestQueue;

    private boolean initialized = false;
	CluSetManagementRpcServiceAsync cluSetManagementRpcServiceAsync = GWT.create(CluSetManagementRpcService.class);
	
	final KSLightBox progressWindow = new KSLightBox();

           
    public CluSetsManagementController(){
        super(CluSetsManagementController.class.getName());
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
                        cluSetModel.setRoot(new Data());
                        callback.onModelReady(cluSetModel);
                        workCompleteCallback.exec(true);
                        
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
    }
        
    private KSButton getQuitButton(){
        return new KSButton("Quit", new ClickHandler(){
                    public void onClick(ClickEvent event) {
                        Controller parentController = CluSetsManagementController.this.getParentController(); 
                   //     parentController.fireApplicationEvent(new ApplicationEvent<LUMViews>(LUMViews.HOME_MENU, event));
                    }
                });       
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
    		
	        cluSetManagementRpcServiceAsync.getMetadata("", "", new AsyncCallback<Metadata>(){
	
	                    @Override
	                    public void onFailure(Throwable caught) {
	                    	onReadyCallback.exec(false);
	                    	progressWindow.hide();
	                        throw new RuntimeException("Failed to get model definition.", caught);                        
	                    }
	
	                    @Override
	                    public void onSuccess(Metadata result) {
	                    	DataModelDefinition def = new DataModelDefinition(result);
	                        cluSetModel.setDefinition(def);
	                        init(def);
	                        initialized = true;
	                        onReadyCallback.exec(true);
	                        progressWindow.hide();
	                    }                
	            });	        
    	}
    }
    
    private void init(DataModelDefinition modelDefinition){
        
    	CluSetsConfigurer cfg = new CluSetsConfigurer();
        cfg.setModelDefinition(modelDefinition);
        cfg.configureCluSetManager(this);           
        
        if (!initialized){
	               
        }
        
        initialized = true;
    } 
        
    /**
     * @see org.kuali.student.common.ui.client.mvc.Controller#getViewsEnum()
     */
    @Override
    public Class<? extends Enum<?>> getViewsEnum() {
        return CourseConfigurer.CourseSections.class;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void requestModel(Class modelType, final ModelRequestCallback callback) {
    }
    
    public void doSaveAction(final SaveActionEvent saveActionEvent){       
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

	@Override
	public Enum<?> getViewEnumValue(String enumValue) {
		// TODO Auto-generated method stub
		return null;
	}
	
    @Override
    public void setParentController(Controller controller) {
        // TODO Auto-generated method stub
        super.setParentController(controller);    
    }
}