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
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.event.SaveActionEvent;
import org.kuali.student.common.ui.client.event.SaveActionHandler;
import org.kuali.student.common.ui.client.event.ValidateRequestEvent;
import org.kuali.student.common.ui.client.event.ValidateRequestHandler;
import org.kuali.student.common.ui.client.event.ValidateResultEvent;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.common.ui.client.mvc.ModelProvider;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.mvc.WorkQueue;
import org.kuali.student.common.ui.client.mvc.WorkQueue.WorkItem;
import org.kuali.student.common.ui.client.service.DataSaveResult;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.KSProgressIndicator;
import org.kuali.student.common.ui.client.widgets.buttongroups.OkGroup;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.OkEnum;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.validation.dto.ValidationResultContainer;
import org.kuali.student.core.validation.dto.ValidationResultInfo.ErrorLevel;
import org.kuali.student.lum.lu.ui.course.client.configuration.course.CourseConfigurer;
import org.kuali.student.lum.lu.ui.course.client.configuration.course.CourseProposalController;
import org.kuali.student.lum.lu.ui.tools.client.service.CluSetManagementRpcService;
import org.kuali.student.lum.lu.ui.tools.client.service.CluSetManagementRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
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
        super.setDefaultModelId(CluSetsConfigurer.CLUSET_MGT_MODEL);
        super.registerModel(CluSetsConfigurer.CLUSET_MGT_MODEL, new ModelProvider<DataModel>() {

            @Override
            public void requestModel(final ModelRequestCallback<DataModel> callback) {
                if (modelRequestQueue == null){
                    modelRequestQueue = new WorkQueue();
                }

                WorkItem workItem = new WorkItem(){
                    @Override
                    public void exec(Callback<Boolean> workCompleteCallback) {
                        if (cluSetModel.getRoot() == null || cluSetModel.getRoot().size() == 0){
                            cluSetModel.setRoot(new Data());
                        }
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
            addApplicationEventHandler(SaveActionEvent.TYPE, new SaveActionHandler(){
                @Override
                public void doSave(SaveActionEvent saveAction) {
                    GWT.log("CluSetManagementController received save action request.", null);
                    doSaveAction(saveAction);
                }
            });
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
        super.requestModel(modelType, callback);
    }
    
    public void doSaveAction(final SaveActionEvent saveActionEvent){
        getCurrentView().updateModel();
        requestModel(new ModelRequestCallback<DataModel>() {
            @Override
            public void onModelReady(DataModel model) {
                model.validate(new Callback<List<ValidationResultContainer>>() {
                    @Override
                    public void exec(List<ValidationResultContainer> result) {
                        
                        boolean save = true;
                        View v = getCurrentView();
                        if(v instanceof Section){
                            ((Section) v).setFieldHasHadFocusFlags(true);
                            ErrorLevel status = ((Section) v).processValidationResults(result);
                            if(status == ErrorLevel.ERROR){
                                save = false;
                            }
                        }
                        
                        if(save){
                            getCurrentView().updateModel();
                            CluSetsManagementController.this.updateModel();
                            saveCluSet(saveActionEvent);
                        }
                        else{
                            Window.alert("Save failed.  Please check fields for errors.");
                        }
                        
                    }
                });
            }

            @Override
            public void onRequestFail(Throwable cause) {
                GWT.log("Unable to retrieve model for validation and save", cause);
            }
            
        });
    }
    
    private void saveCluSet(final SaveActionEvent saveActionEvent) {
        final KSLightBox saveWindow = new KSLightBox();
        final KSLabel saveMessage = new KSLabel(saveActionEvent.getMessage() + "...");
        final OkGroup buttonGroup = new OkGroup(new Callback<OkEnum>(){
                
                @Override
                public void exec(OkEnum result) {
                    saveWindow.hide();
                    saveActionEvent.doActionComplete();                
                }
            });

        buttonGroup.setWidth("250px");
        buttonGroup.getButton(OkEnum.Ok).setEnabled(false);
        buttonGroup.setContent(saveMessage);

        
        if (saveActionEvent.isAcknowledgeRequired()){
            saveWindow.setWidget(buttonGroup);
        } else {
            saveWindow.setWidget(saveMessage);
        }
        saveWindow.show();
        
        final Callback<Throwable> saveFailedCallback = new Callback<Throwable>() {

            @Override
            public void exec(Throwable caught) {
                 GWT.log("Save Failed.", caught);
                 saveWindow.setWidget(buttonGroup);
                 saveMessage.setText("Save Failed!  Please Try Again.");
                 buttonGroup.getButton(OkEnum.Ok).setEnabled(true);   
            }
            
        };
        try {
            // TODO make an asynchronous call to save data
            cluSetManagementRpcServiceAsync.saveData(cluSetModel.getRoot(), new AsyncCallback<DataSaveResult>() {
                @Override
                public void onFailure(Throwable caught) {
                    saveFailedCallback.exec(caught); 
                }

                @Override
                public void onSuccess(DataSaveResult result) {
                  // FIXME needs to check validation results and display messages if validation failed
                  cluSetModel.setRoot(result.getValue());
                  View currentView = getCurrentView(); 
                  if (currentView instanceof VerticalSectionView){
                      ((VerticalSectionView) currentView).redraw();
                  }
                  if (saveActionEvent.isAcknowledgeRequired()){
                      saveMessage.setText("Save Successful");
                      buttonGroup.getButton(OkEnum.Ok).setEnabled(true);
                  } else {
                      saveWindow.hide();
                      saveActionEvent.doActionComplete();                        
                  } 
                }
            });
            
            // test code remove when done testing
            if (saveActionEvent.isAcknowledgeRequired()){
                saveMessage.setText("Save Successful");
                buttonGroup.getButton(OkEnum.Ok).setEnabled(true);
            } else {
                saveWindow.hide();
                saveActionEvent.doActionComplete();                        
            } 
//            cluProposalRpcServiceAsync.saveData(cluProposalModel.getRoot(), new AsyncCallback<DataSaveResult>(){
//                public void onFailure(Throwable caught) {
//                   saveFailedCallback.exec(caught);                 
//                }
//
//                public void onSuccess(DataSaveResult result) {
//                    // FIXME needs to check validation results and display messages if validation failed
//                    cluProposalModel.setRoot(result.getValue());
//                    View currentView = getCurrentView(); 
//                    if (currentView instanceof VerticalSectionView){
//                        ((VerticalSectionView) currentView).redraw();
//                    }
//                    if (saveActionEvent.isAcknowledgeRequired()){
//                        saveMessage.setText("Save Successful");
//                        buttonGroup.getButton(OkEnum.Ok).setEnabled(true);
//                    } else {
//                        saveWindow.hide();
//                        saveActionEvent.doActionComplete();                        
//                    } 
//                    workflowToolbar.refresh();
//                }
//            });
        } catch (Exception e) {
            saveFailedCallback.exec(e);
        }

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