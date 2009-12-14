package org.kuali.student.core.organization.ui.client.mvc.controller;


import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.TabbedSectionLayout;
import org.kuali.student.common.ui.client.event.SaveActionEvent;
import org.kuali.student.common.ui.client.event.SaveActionHandler;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.CollectionModel;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.mvc.WorkQueue;
import org.kuali.student.common.ui.client.mvc.WorkQueue.WorkItem;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.containers.KSTitleContainerImpl;
import org.kuali.student.common.ui.client.widgets.suggestbox.KSAdvancedSearchWindow;
import org.kuali.student.common.ui.client.widgets.tabs.KSTabPanel;
import org.kuali.student.core.organization.dto.OrgInfo;
import org.kuali.student.core.organization.ui.client.mvc.view.OrgConfigurerFactory;
import org.kuali.student.core.organization.ui.client.mvc.view.OrgConfigurerFactory.OrgSections;
import org.kuali.student.core.organization.ui.client.service.DataSaveResult;
import org.kuali.student.core.organization.ui.client.service.OrgRpcService;
import org.kuali.student.core.organization.ui.client.service.OrgRpcServiceAsync;



import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class OrgProposalController extends TabbedSectionLayout{

    private CollectionModel<org.kuali.student.common.ui.client.mvc.DataModel> orgProposalModel; 
    private WorkQueue modelRequestQueue;
    private static KSTitleContainerImpl container = new KSTitleContainerImpl("Organization Management", "Create, Modify and Browse","");
    private KSTabPanel tabPanel = new KSTabPanel();
    
    OrgRpcServiceAsync orgProposalRpcServiceAsync = GWT.create(OrgRpcService.class);
    public OrgProposalController(){

        super(container);
        
//        super.initWidget(container);
        init();
    }
    
    private KSButton getSaveButton(){
        return new KSButton("Save", new ClickHandler(){
                    public void onClick(ClickEvent event) {
                        fireApplicationEvent(new SaveActionEvent());
                    }
                });
    }
    
    private void init(){
        OrgConfigurerFactory.configureOrgProposal(this);
        addButton("Create", getSaveButton());
        addApplicationEventHandler(SaveActionEvent.TYPE, new SaveActionHandler(){
            public void doSave(SaveActionEvent saveAction) {
                GWT.log("OrgController received save action request.", null);
                doSaveAction(saveAction);
            }            
        });
    }
	@Override
	public Class<? extends Enum<?>> getViewsEnum() {
		return null;
	}
	
	
	  @SuppressWarnings("unchecked")
	    @Override
	    public void requestModel(String modelId, final ModelRequestCallback modelRequestCallback) {
	        if (modelRequestQueue == null){
	            modelRequestQueue = new WorkQueue();
	        }
	        if (modelId.equals(OrgConfigurerFactory.ORG_PROPOSAL_MODEL)){
	            WorkItem workItem = new WorkItem(){
	                @Override
	                public void exec(Callback<Boolean> workCompleteCallback) {
	                    if (orgProposalModel == null){
	                        
	                        createNewOrgModel(modelRequestCallback, workCompleteCallback);
	                                     
	                    } else {
	                        modelRequestCallback.onModelReady(orgProposalModel);
	                        workCompleteCallback.exec(true);
	                    }
	                }               
	            };
	            modelRequestQueue.submit(workItem);
	        } else{
	            super.requestModel(modelId, modelRequestCallback);
	        }
	    }
	  
	  
	  @SuppressWarnings("unchecked")
	    private void createNewOrgModel(final ModelRequestCallback callback, final Callback<Boolean> workCompleteCallback){
	      
	        if (orgProposalModel == null){
	            orgProposalModel = new CollectionModel<org.kuali.student.common.ui.client.mvc.DataModel>();

	            // TODO revise how the org proposal model's metadata is retrieved
//	            orgProposalRpcServiceAsync.getOrgProposalModelDefinition(OrgConfigurerFactory.ORG_PROPOSAL_MODEL, 
//	                new AsyncCallback<org.kuali.student.common.ui.client.mvc.DataModel>(){
//
//	                    @Override
//	                    public void onFailure(Throwable caught) {
//	                        Window.alert("Failed to get model definition.");                        
//	                    }
//
//	                    @Override
//	                    public void onSuccess(org.kuali.student.common.ui.client.mvc.DataModel result) {
//	                        orgProposalModel.put(result);
//	                        callback.onModelReady(orgProposalModel);
//	                        workCompleteCallback.exec(true);
//	                    }                
//	            });
	                        
	        } 

	    }
	  
	    public void doSaveAction(SaveActionEvent saveActionEvent){     
	        System.out.println("Reached save action");
	        

	        View tempView2 = getCurrentView();
	        
            getCurrentView().updateModel();
            
            System.out.println(" model updated ");
            saveProposalOrg(saveActionEvent);
            System.out.println("Reached summit 1 ");
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
	    
	    public void saveProposalOrg(final SaveActionEvent saveActionEvent){

	        orgProposalRpcServiceAsync.saveOrgProposal(orgProposalModel.getValue().getRoot(),  new AsyncCallback<DataSaveResult>(){
                public void onFailure(Throwable caught) {
                    System.out.println("Org Save service Failed");
//                    saveFailedCallback.exec(caught);         
                    try{
                        throw caught;
                    }
                    catch(Throwable e){
                        e.printStackTrace();
                    }
                 }
 
                 public void onSuccess(DataSaveResult result) {
                     // FIXME needs to check validation results and display messages if validation failed
                     
                     
//                     orgProposalModel.getValue().setRoot(result.getValue());
//                     if (saveActionEvent.isAcknowledgeRequired()){
//                         saveMessage.setText("Save Successful");
//                         buttonGroup.getButton(OkEnum.Ok).setEnabled(true);
//                     } else {
//                         saveWindow.hide();
//                         saveActionEvent.doActionComplete();                        
//                     }        
                     
                    System.out.println("OrgSaved");
                 }
                 });
	    }
}
