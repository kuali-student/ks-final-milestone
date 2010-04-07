package org.kuali.student.core.organization.ui.client.mvc.controller;


import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.layouts.TabbedSectionLayout;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.event.ModifyActionEvent;
import org.kuali.student.common.ui.client.event.ModifyActionHandler;
import org.kuali.student.common.ui.client.event.SaveActionEvent;
import org.kuali.student.common.ui.client.event.SaveActionHandler;
import org.kuali.student.common.ui.client.event.ValidateRequestEvent;
import org.kuali.student.common.ui.client.event.ValidateRequestHandler;
import org.kuali.student.common.ui.client.event.ValidateResultEvent;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.CollectionModel;
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
import org.kuali.student.common.ui.client.widgets.containers.KSTitleContainerImpl;
import org.kuali.student.common.ui.client.widgets.tabs.KSTabPanel;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.organization.dto.OrgInfo;
import org.kuali.student.core.organization.ui.client.mvc.view.CommonConfigurer;
import org.kuali.student.core.organization.ui.client.mvc.view.OrgConfigurerFactory;
import org.kuali.student.core.organization.ui.client.mvc.view.OrgConfigurerFactory.OrgSections;
import org.kuali.student.core.organization.ui.client.service.OrgRpcService;
import org.kuali.student.core.organization.ui.client.service.OrgRpcServiceAsync;
import org.kuali.student.core.organization.ui.client.mvc.model.SectionConfigInfo;
import org.kuali.student.core.validation.dto.ValidationResultContainer;
import org.kuali.student.core.validation.dto.ValidationResultInfo.ErrorLevel;





import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class OrgProposalController extends TabbedSectionLayout{

    private final DataModel orgProposalModel = new DataModel(); 
    private WorkQueue modelRequestQueue;
    private static KSTitleContainerImpl container = new KSTitleContainerImpl("Organization Management", "Create, Modify and Browse","");
    private KSTabPanel tabPanel = new KSTabPanel();
    private boolean initialized = false;
    private boolean modified = false;
    private CommonConfigurer commonConfigurer = new CommonConfigurer();
    final KSLightBox progressWindow = new KSLightBox();
    boolean flag = false;
    
    OrgRpcServiceAsync orgProposalRpcServiceAsync = GWT.create(OrgRpcService.class);
    public OrgProposalController(){

        super(OrgProposalController.class.getName(), container);
        
//        super.initWidget(container);
        initialize();
    }
    
    private void initialize() {
        super.setDefaultModelId(CommonConfigurer.ORG_PROPOSAL_MODEL);
        super.registerModel(CommonConfigurer.ORG_PROPOSAL_MODEL, new ModelProvider<DataModel>() {

            @Override
            public void requestModel(final ModelRequestCallback<DataModel> callback) {
                if (modelRequestQueue == null){
                    modelRequestQueue = new WorkQueue();
                }

                WorkItem workItem = new WorkItem(){
                    @Override
                    public void exec(Callback<Boolean> workCompleteCallback) {
//                        if (cluProposalModel.getRoot() == null || cluProposalModel.getRoot().size() == 0){
//                            if(docId!=null){
//                                getCluProposalFromWorkflowId(callback, workCompleteCallback);
//                            } else if (proposalId != null){
//                                getCluProposalFromProposalId(callback, workCompleteCallback);
//                            } else{
//                                createNewCluProposalModel(callback, workCompleteCallback);
//                            }                
//                        } else {
//                            callback.onModelReady(cluProposalModel);
//                            workCompleteCallback.exec(true);
//                        }
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
    
    private KSButton getSaveButton(){
        return new KSButton("Save", new ClickHandler(){
                    public void onClick(ClickEvent event) {
                        fireApplicationEvent(new SaveActionEvent());
                    }
                });
    }
    
    private KSButton getModifyButton(){
        return new KSButton("Modify", new ClickHandler(){
                    public void onClick(ClickEvent event) {
                        fireApplicationEvent(new ModifyActionEvent((String)orgProposalModel.get("orgSearchInfo/searchOrgs")));
                    }
                });
    }
    
    
    
    private void init(final Callback<Boolean> onReadyCallback){
        KSProgressIndicator progressInd = new KSProgressIndicator();
        progressInd.setText("Loading");
        progressInd.show();
        progressWindow.setWidget(progressInd);

        if (initialized) {
            onReadyCallback.exec(true);
        } 
        else {
            progressWindow.show();
            orgProposalRpcServiceAsync.getOrgMetaData( 
                    new AsyncCallback<Metadata>(){

                        @Override
                        public void onFailure(Throwable caught) {
                            System.out.println("Failure");
                            progressWindow.hide();
                            throw new RuntimeException("Failed to get model definition.", caught);                        
                        }

                        @Override
                        public void onSuccess(Metadata result) {
                            
                            DataModelDefinition def = new DataModelDefinition(result);
                            System.out.println("Loaded OrgMetaData");
                            orgProposalModel.setDefinition(def);
                            commonConfigurer.setModelDefinition(def);
                            setSectionConfig(onReadyCallback);
                            progressWindow.hide();
                            
                        }
                });
            
        }
        
        

    }
    
    private void setSectionConfig(final Callback<Boolean> onReadyCallback){

        
        orgProposalRpcServiceAsync.getSectionConfig( 
                new AsyncCallback<SectionConfigInfo>(){

                    @Override
                    public void onFailure(Throwable caught) {
                        System.out.println("Failure");
                        throw new RuntimeException("Failed to get section config.", caught);                        
                    }

                    @Override
                    public void onSuccess(SectionConfigInfo result) {
                        System.out.println("Loaded SectionConfig");
                        commonConfigurer.setSectionConfigInfo(result);
                        init();
                        onReadyCallback.exec(true);
                        progressWindow.hide();
                        
                    }                
            });
    }
    
    private void init(){
        
        if(!flag){
            commonConfigurer.configureOrgProposal(this);
            flag= true;
        }
        
        if(!initialized){
        addButton("Organization", getSaveButton());
        addButton("Positions/Members", getSaveButton());
        addButton("Search/Modify", getModifyButton());
        
        addApplicationEventHandler(ModifyActionEvent.TYPE, new ModifyActionHandler(){
            @Override
            public void onModify(ModifyActionEvent modifyEvent) {
                GWT.log("OrgController received save action request.", null);
                doModifyAction(modifyEvent);
                
            }            
        });
        addApplicationEventHandler(SaveActionEvent.TYPE, new SaveActionHandler(){
            public void doSave(SaveActionEvent saveAction) {
                GWT.log("OrgController received save action request.", null);
                doSaveAction(saveAction);
            }            
        });
        }
        initialized = true;
    }
    
//    private void init(DataModelDefinition modelDefinition,SectionConfigInfo sectionConfigInfo) {
//        CommonConfigurer commonConfigurer = new CommonConfigurer();
//        commonConfigurer.setModelDefinition(modelDefinition);
//        System.out.println("Success");
//        commonConfigurer.setSectionConfigInfo(sectionConfigInfo);
//        commonConfigurer.configureOrgProposal(this);
//    }
    
	@Override
	public Class<? extends Enum<?>> getViewsEnum() {
		return null;
	}
	
	
	  @SuppressWarnings("unchecked")
	    @Override
	    public void requestModel(String modelId, final ModelRequestCallback modelRequestCallback) {
	      String id = (modelId == null) ? super.getDefaultModelId() : modelId;
	        if (modelRequestQueue == null){
	            modelRequestQueue = new WorkQueue();
	        }
	        if (id.equals(OrgConfigurerFactory.ORG_PROPOSAL_MODEL)){
	            WorkItem workItem = new WorkItem(){
	                @Override
	                public void exec(Callback<Boolean> workCompleteCallback) {
	                    if (orgProposalModel.getRoot() == null || orgProposalModel.getRoot().size() == 0){
	                        createNewOrgModel(modelRequestCallback, workCompleteCallback);
	                                     
	                    } else {
	                        modelRequestCallback.onModelReady(orgProposalModel);
	                        workCompleteCallback.exec(true);
	                    }
	                }               
	            };
	            modelRequestQueue.submit(workItem);
	        } else{
	            super.requestModel(id, modelRequestCallback);
	        }
	    }
	  
	  
	  @SuppressWarnings("unchecked")
	    private void createNewOrgModel(final ModelRequestCallback callback, final Callback<Boolean> workCompleteCallback){
	        orgProposalModel.setRoot(new Data());
	        callback.onModelReady(orgProposalModel);
	        workCompleteCallback.exec(true);            


	    }
	  
	    public void doSaveAction(final SaveActionEvent saveActionEvent){     
	        System.out.println("Reached save action");
	        
	        View tempView2 = getView(CommonConfigurer.SectionsEnum.ORG_INFO);
	        tempView2.updateModel();
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
                                    System.out.println(" model updated ");
                                    saveProposalOrg(saveActionEvent);
                                    System.out.println("Reached summit 1 ");
                                }
                        }
                    
                    });
                }

                @Override
                public void onRequestFail(Throwable cause) {
                    GWT.log("Unable to retrieve model for validation and save", cause);
                    
                }
            });
            
            
            
            
            
            

//            this.updateModel();
//	        String proposalName = cluProposalModel.get().get(CLU_PROPOSAL_NAME_KEY);
//	        currentSaveEvent = saveActionEvent;
//	        if (proposalName == null){
//	            showStartSection();
//	        } else {
//	            getStartSection().updateModel();
//	            
//	            getCurrentView().updateModel();
//	            
//	            this.updateModel();
//	            
//	            saveProposalClu(saveActionEvent);
//	            processingSave=true;
//	            View v = getCurrentView();
//	          if(v instanceof SectionView){
//	              ((SectionView) v).setFieldHasHadFocusFlags(true);
//	              this.validate((SectionView)v);
//	          }
//	        }       
	    }
	    
	       public void doModifyAction(ModifyActionEvent modifyActionEvent){     
	            System.out.println("Reached modify action");
	            

	            View tempView2 = getView(CommonConfigurer.SectionsEnum.ORG_INFO);
	            
	            //getCurrentView().updateModel();
	            
	            System.out.println(" model updated ");
	            fetchProposalOrg(modifyActionEvent);
	            System.out.println("Reached summit 1 ");
      
	        }
	       public void fetchProposalOrg(final ModifyActionEvent modifyActionEvent) {

	           orgProposalRpcServiceAsync.fetchOrg(modifyActionEvent.getId(), new AsyncCallback<Data>() {
	               public void onFailure(Throwable caught) {
	                   GWT.log("Fetch Failed.", caught);
	                   // saveWindow.setWidget(buttonGroup);
	                   // saveMessage.setText("Save Failed!  Please Try Again.");
	                   // buttonGroup.getButton(OkEnum.Ok).setEnabled(true);
	               }

	               public void onSuccess(Data result) {
	                   // FIXME needs to check validation results and display messages if validation failed
	                   orgProposalModel.setRoot(result);
	                   commonConfigurer.positionTable.setOrgId((String)orgProposalModel.get("orgInfo/id"));
	                   commonConfigurer.positionTable.fetchPosition();
	                   commonConfigurer.setOrgId((String)orgProposalModel.get("orgInfo/id"));
	                   getContainer().setTitle((String)orgProposalModel.get("orgInfo/longName"));
	                   View currentView = getCurrentView();
	                   View orgView = getView(CommonConfigurer.SectionsEnum.ORG_INFO);
	                   renderView(orgView);
	                   if (orgView instanceof VerticalSectionView) {
	                       ((VerticalSectionView) orgView).redraw();
	                   }
	                   modified = true;
	               }
	           });
	       }
	    public void saveProposalOrg(final SaveActionEvent saveActionEvent){
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
	        
	        orgProposalRpcServiceAsync.saveOrgProposal(orgProposalModel.getRoot(),  new AsyncCallback<DataSaveResult>(){
                public void onFailure(Throwable caught) {
                    GWT.log("Save Failed.", caught);
                    saveWindow.setWidget(buttonGroup);
                    saveMessage.setText("Save Failed!  Please Try Again.");
                    buttonGroup.getButton(OkEnum.Ok).setEnabled(true);   
                 }
 
                 public void onSuccess(DataSaveResult result) {
                     // FIXME needs to check validation results and display messages if validation failed
                     orgProposalModel.setRoot(result.getValue());

                     View currentView = getCurrentView(); 
                     commonConfigurer.positionTable.setOrgId((String)orgProposalModel.get("orgInfo/id"));
                     commonConfigurer.positionTable.fetchPosition();
                     commonConfigurer.setOrgId((String)orgProposalModel.get("orgInfo/id"));
                     getContainer().setTitle((String)orgProposalModel.get("orgInfo/longName"));
                     if(currentView.getName().equals("Positions")){
                         commonConfigurer.positionTable.fetchPosition();
                     }
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
                     
                    System.out.println("OrgSaved");
                 }
                 });
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
}
