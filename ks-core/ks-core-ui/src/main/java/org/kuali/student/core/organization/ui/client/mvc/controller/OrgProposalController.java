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

package org.kuali.student.core.organization.ui.client.mvc.controller;


import static org.kuali.student.core.organization.ui.client.mvc.view.CommonConfigurer.getLabel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.layouts.TabbedSectionLayout;
import org.kuali.student.common.ui.client.configurable.mvc.sections.BaseSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.event.ModifyActionEvent;
import org.kuali.student.common.ui.client.event.ModifyActionHandler;
import org.kuali.student.common.ui.client.event.SaveActionEvent;
import org.kuali.student.common.ui.client.event.SaveActionHandler;
import org.kuali.student.common.ui.client.mvc.Callback;
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
import org.kuali.student.common.ui.client.widgets.buttongroups.OkGroup;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.OkEnum;
import org.kuali.student.common.ui.client.widgets.containers.KSTitleContainerImpl;
import org.kuali.student.common.ui.client.widgets.progress.BlockingTask;
import org.kuali.student.common.ui.client.widgets.progress.KSBlockingProgressIndicator;
import org.kuali.student.common.ui.client.widgets.search.KSPicker;
import org.kuali.student.common.ui.shared.IdAttributes;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.core.assembly.data.Data.StringValue;
import org.kuali.student.core.assembly.data.Data.Value;
import org.kuali.student.core.organization.ui.client.mvc.model.SectionConfigInfo;
import org.kuali.student.core.organization.ui.client.mvc.view.CommonConfigurer;
import org.kuali.student.core.organization.ui.client.mvc.view.CommonConfigurer.SectionsEnum;
import org.kuali.student.core.organization.ui.client.service.OrgRpcService;
import org.kuali.student.core.organization.ui.client.service.OrgRpcServiceAsync;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.core.validation.dto.ValidationResultInfo.ErrorLevel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

public class OrgProposalController extends TabbedSectionLayout{

    private final DataModel orgProposalModel = new DataModel();
    private WorkQueue modelRequestQueue;
    private static KSTitleContainerImpl container = new KSTitleContainerImpl(getLabel("orgTitleOrg"), getLabel("orgCrud"),"");
    private boolean initialized = false;
    private CommonConfigurer commonConfigurer = new CommonConfigurer();
    private boolean isMetadataRefreshed = false;

    public static final String ORG_INFO_PATH		= "orgInfo";
    public static final String POSITION_PATH		= "OrgPositionRestrictionInfo";
    public static final String PERSON_PATH			= "orgPersonRelationInfo";
    public static final String ORGORG_PATH			= "orgOrgRelationInfo";
    public static final String ORG_TAB_NAME			= getLabel("orgOrgTab");
    public static final String POSITION_TAB_NAME	= getLabel("orgPositionTab");
    public static final String SEARCH_TAB_NAME		= getLabel("orgSearchTab");
    public static final String QUALIFICATION_ORG_ID	= "orgId";
    public static final String ORG_PROPOSAL_MODEL	= "orgProposalModel";

    private BlockingTask initializingTask = new BlockingTask(getLabel("orgLoading"));

    OrgRpcServiceAsync orgProposalRpcServiceAsync = GWT.create(OrgRpcService.class);

    public OrgProposalController(){
        super(OrgProposalController.class.getName(), container);
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
  	                    if (orgProposalModel.getRoot() == null || orgProposalModel.getRoot().size() == 0){
  	                        createNewOrgModel(callback, workCompleteCallback);

  	                    } else {
  	                        callback.onModelReady(orgProposalModel);
  	                        workCompleteCallback.exec(true);
  	                    }
  	                }
  	            };
  	            modelRequestQueue.submit(workItem);
            }
        });
    }

    private KSButton getSaveButton(){
        return new KSButton(getLabel("orgSave"), new ClickHandler(){
                    public void onClick(ClickEvent event) {
                        fireApplicationEvent(new SaveActionEvent());
                    }
                });
    }

    private KSButton getModifyButton(){
        return new KSButton(getLabel("orgModify"), new ClickHandler(){
                    public void onClick(ClickEvent event) {
                        List<Section> sections = null;
                        String fieldKey = null;
                        Value val = null;
                        View view = getView(SectionsEnum.SEARCH);


                        if(view instanceof SectionView){
                            List<FieldDescriptor> lfd = ((SectionView)view).getFields();
                            for(FieldDescriptor fd : lfd){
                                Widget widget = fd.getFieldWidget();
                                if(widget instanceof KSPicker){
                                    val = ((KSPicker)widget).getValue();
                                    fieldKey = fd.getFieldKey();
                                }
                            }
                            sections = ((SectionView)view).getSections();
                        }

                        String strval = ((StringValue)val).get();
                        if(strval != null && !strval.equals("")){
                            getCurrentView().updateModel();
                            fireApplicationEvent(new ModifyActionEvent((String)orgProposalModel.get("orgSearchInfo/searchOrgs")));
                        } else{
                            // display error message
                            for(Section section : sections){
                                if(section instanceof BaseSection){

                                    ValidationResultInfo vr = new ValidationResultInfo();
                                    vr.setError(getLabel("orgFieldCantBeEmpty"));
                                    section.getField(fieldKey).getFieldElement().processValidationResult(vr);
                                }
                            }
                        }
                    }
                });
    }

    private void init(final Callback<Boolean> onReadyCallback){

        if (initialized) {
            onReadyCallback.exec(true);
        } else {
    		KSBlockingProgressIndicator.addTask(initializingTask);

            String viewContextId = null;
            if(getViewContext().getIdType() != null){
                viewContextId = getViewContext().getId();
            }

            Map<String,String> idAttributes = new HashMap<String,String>();
            idAttributes.put(IdAttributes.ID_TYPE, QUALIFICATION_ORG_ID);
            orgProposalRpcServiceAsync.getMetadata(viewContextId, idAttributes, 
                    new KSAsyncCallback<Metadata>(){

                        @Override
                        public void handleFailure(Throwable caught) {
                            GWT.log("Failure",null);
                            KSBlockingProgressIndicator.removeTask(initializingTask);
                            throw new RuntimeException("Failed to get model definition.", caught);
                        }

                        @Override
                        public void onSuccess(Metadata result) {

                            DataModelDefinition def = new DataModelDefinition(result);
                            GWT.log("Loaded OrgMetaData",null);
                            orgProposalModel.setDefinition(def);
                            commonConfigurer.setModelDefinition(def);
                            setSectionConfig(onReadyCallback);
                            KSBlockingProgressIndicator.removeTask(initializingTask);
                            isMetadataRefreshed = true;
                        }
                });

        }
    }

    private void setSectionConfig(final Callback<Boolean> onReadyCallback){
        orgProposalRpcServiceAsync.getSectionConfig(
                new KSAsyncCallback<SectionConfigInfo>(){

                    @Override
                    public void handleFailure(Throwable caught) {
                        GWT.log("Failure", null);
                        throw new RuntimeException("Failed to get section config.", caught);
                    }

                    @Override
                    public void onSuccess(SectionConfigInfo result) {
                        GWT.log("Loaded SectionConfig", null);
                        commonConfigurer.setSectionConfigInfo(result);
                        init();
                        onReadyCallback.exec(true);
                    }
            });
    }

    private void init(){

        if(isMetadataRefreshed){
            commonConfigurer.configureOrgProposal(this);
            isMetadataRefreshed = false;
        }

        if(!initialized){
	        addButton(ORG_TAB_NAME, getSaveButton());
	        addButton(POSITION_TAB_NAME, getSaveButton());
	        addButton(SEARCH_TAB_NAME, getModifyButton());

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
	        setButtonPermission();
        }
        initialized = true;
    }


	@Override
	public Class<? extends Enum<?>> getViewsEnum() {
		return null;
	}


	@SuppressWarnings("unchecked")
	private void createNewOrgModel(final ModelRequestCallback callback, final Callback<Boolean> workCompleteCallback){
	    orgProposalModel.setRoot(new Data());
	    callback.onModelReady(orgProposalModel);
	    workCompleteCallback.exec(true);


	}

    public void doSaveAction(final SaveActionEvent saveActionEvent){
        GWT.log("Reached save action",null);

        View tempView2 = getView(CommonConfigurer.SectionsEnum.ORG_INFO);
        tempView2.updateModel();
        getCurrentView().updateModel();



        requestModel(new ModelRequestCallback<DataModel>() {
            @Override
            public void onModelReady(DataModel model) {
                model.validate(new Callback<List<ValidationResultInfo>>() {

                    @Override
                    public void exec(List<ValidationResultInfo> result) {
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
                            	GWT.log(" model updated ", null);
                                saveProposalOrg(saveActionEvent);
                                GWT.log("Reached summit 1 ", null);
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

    private void setButtonPermission(){
        HorizontalPanel buttonPanel = getButtonPanel(ORG_TAB_NAME);
        QueryPath orgInfoPath = QueryPath.concat(null, ORG_INFO_PATH);
        QueryPath orgOrgPath = QueryPath.concat(null, ORGORG_PATH);
        QueryPath orgPosPath = QueryPath.concat(null, POSITION_PATH);
        QueryPath orgPersonPath = QueryPath.concat(null, PERSON_PATH);
        Metadata orgInfometa = orgProposalModel.getMetadata(orgInfoPath);
        Metadata orgOrgmeta = orgProposalModel.getMetadata(orgOrgPath);
        Metadata orgPosmeta = orgProposalModel.getMetadata(orgPosPath);
        Metadata orgPersonmeta = orgProposalModel.getMetadata(orgPersonPath);

        if(orgInfometa.isCanEdit()||orgOrgmeta.isCanEdit()||orgPosmeta.isCanEdit()){
            buttonPanel.setVisible(true);
        }
        else{
            buttonPanel.setVisible(false);
        }
        buttonPanel = getButtonPanel(POSITION_TAB_NAME);
        orgInfoPath = QueryPath.concat(null, ORG_INFO_PATH);
        if(!orgPersonmeta.isCanEdit()){
            buttonPanel.setVisible(false);
        }
        else{
            buttonPanel.setVisible(true);
        }
    }

   public void doModifyAction(final ModifyActionEvent modifyActionEvent){
        GWT.log("Reached modify action", null);

        Map<String,String> idAttributes = new HashMap<String,String>();
        idAttributes.put(IdAttributes.ID_TYPE, QUALIFICATION_ORG_ID);
        orgProposalRpcServiceAsync.getMetadata(modifyActionEvent.getId(),idAttributes,
                new KSAsyncCallback<Metadata>(){

                    @Override
                    public void handleFailure(Throwable caught) {
                        GWT.log("Failure",null);
                        throw new RuntimeException("Failed to get model definition.", caught);
                    }

                    @Override
                    public void onSuccess(Metadata result) {

                        DataModelDefinition def = new DataModelDefinition(result);
                        GWT.log("Loaded OrgMetaData",null);
                        orgProposalModel.setDefinition(def);
                        commonConfigurer.setModelDefinition(def);
                        isMetadataRefreshed = true;
                        init();
                        GWT.log(" model updated ",null);
                        fetchProposalOrg(modifyActionEvent);
                        GWT.log("Reached summit 1 ",null);

                    }
            });


    }

   public void fetchProposalOrg(final ModifyActionEvent modifyActionEvent) {

       orgProposalRpcServiceAsync.fetchOrg(modifyActionEvent.getId(), new KSAsyncCallback<Data>() {
           public void handleFailure(Throwable caught) {
               GWT.log("Fetch Failed.", caught);
           }

           public void onSuccess(Data result) {
               orgProposalModel.setRoot(result);
               commonConfigurer.positionTable.setOrgId((String)orgProposalModel.get("orgInfo/id"));
               commonConfigurer.positionTable.fetchPosition();
               commonConfigurer.setOrgId((String)orgProposalModel.get("orgInfo/id"));
               getContainer().setTitle((String)orgProposalModel.get("orgInfo/longName"));
               final View orgView = getView(CommonConfigurer.SectionsEnum.ORG_INFO);
               setButtonPermission();

               if (orgView instanceof VerticalSectionView) {
                   ((VerticalSectionView) orgView).beforeShow(new Callback<Boolean>(){

                    @Override
                    public void exec(Boolean result) {
                        renderView(orgView);
                    }

                   });

               }

           }
       });
   }

    public void saveProposalOrg(final SaveActionEvent saveActionEvent){
        final KSLightBox saveWindow = new KSLightBox();
        saveWindow.removeCloseLink();
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

        orgProposalRpcServiceAsync.saveOrgProposal(orgProposalModel.getRoot(),  new KSAsyncCallback<DataSaveResult>(){
            public void handleFailure(Throwable caught) {
                GWT.log("Save Failed.", caught);
                saveWindow.setWidget(buttonGroup);
                saveMessage.setText(getLabel("orgSaveFailed"));
                buttonGroup.getButton(OkEnum.Ok).setEnabled(true);
             }

             public void onSuccess(DataSaveResult result) {
                 // FIXME needs to check validation results and display messages if validation failed
            	 // This was reviewd as part of KSCOR-220, deferred to KSCOR-225, can't be fixed until server side validation completed
                 orgProposalModel.setRoot(result.getValue());

                 View currentView = getCurrentView();
                 commonConfigurer.positionTable.setOrgId((String)orgProposalModel.get("orgInfo/id"));
                 commonConfigurer.positionTable.fetchPosition();
                 commonConfigurer.setOrgId((String)orgProposalModel.get("orgInfo/id"));
                 getContainer().setTitle((String)orgProposalModel.get("orgInfo/longName"));
                 if(currentView.getName().equals(getLabel("orgPositionRelationSection"))){
                     commonConfigurer.positionTable.fetchPosition();
                 }
                 if (saveActionEvent.isAcknowledgeRequired()){
                     saveMessage.setText(getLabel("orgSaveOk"));
                     buttonGroup.getButton(OkEnum.Ok).setEnabled(true);

                 } else {
                     saveWindow.hide();
                     saveActionEvent.doActionComplete();
                 }

                GWT.log("OrgSaved",null);
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
