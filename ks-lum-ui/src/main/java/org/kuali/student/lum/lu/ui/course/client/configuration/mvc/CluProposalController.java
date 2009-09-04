/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.lum.lu.ui.course.client.configuration.mvc;

import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.PagedSectionLayout;
import org.kuali.student.common.ui.client.event.SaveActionEvent;
import org.kuali.student.common.ui.client.event.SaveActionHandler;
import org.kuali.student.common.ui.client.event.ValidateResultEvent;
import org.kuali.student.common.ui.client.event.ValidateResultHandler;
import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.mvc.dto.ReferenceModel;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.StringType;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.core.validation.dto.ValidationResultContainer;
import org.kuali.student.lum.lu.ui.course.client.service.CluProposalRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.CluProposalRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in. 
 * 
 * @author Kuali Student Team
 *
 */
public class CluProposalController extends PagedSectionLayout{
    private Model<CluProposalModelDTO> cluProposalModel;
    private Model<ProposalInfoModelDTO> proposalInfoModel;
    
    CluProposalRpcServiceAsync cluProposalRpcServiceAsync = GWT.create(CluProposalRpcService.class);
    private boolean savedOnce=false;
    
    private KSButton saveButton = new KSButton("Save", new ClickHandler(){
        public void onClick(ClickEvent event) {
            fireApplicationEvent(new SaveActionEvent());
        }
    });
    
    private KSButton clear = new KSButton("Clear", new ClickHandler(){
        public void onClick(ClickEvent event) {
            
        }       
    });
    
    private KSButton testButton = new KSButton("Get Proposal Info", new ClickHandler(){
        public void onClick(ClickEvent event) {
            cluProposalRpcServiceAsync.getProposalModelDTO(((StringType) cluProposalModel.get().get("id")).get(), 
                    new AsyncCallback<CluProposalModelDTO>(){

                @Override
                public void onFailure(Throwable caught) {
                    // TODO bsmith - THIS METHOD NEEDS JAVADOCS
                    
                }

                @Override
                public void onSuccess(CluProposalModelDTO result) {
                    
                    Window.alert(result.toString());
                }
                
            });
        }       
    });
    
    private KSButton testButton2 = new KSButton("Get & Set", new ClickHandler(){
        public void onClick(ClickEvent event) {
            cluProposalRpcServiceAsync.getProposalModelDTO(((StringType) cluProposalModel.get().get("id")).get(), 
                    new AsyncCallback<CluProposalModelDTO>(){

                @Override
                public void onFailure(Throwable caught) {
                    // TODO bsmith - THIS METHOD NEEDS JAVADOCS
                    
                }

                @Override
                public void onSuccess(CluProposalModelDTO result) {
                    cluProposalModel.put(result);
                    getCurrentView().beforeShow();
                }
                
            });
        }       
    });

    public CluProposalController(){
        super();
        LuConfigurer.configureCluProposal(this);
        addButton(saveButton);
        addButton(testButton);
        addButton(testButton2);
        cluProposalModel = null;
        this.setModelDTOType(CluProposalModelDTO.class);
        addApplicationEventHandler(SaveActionEvent.TYPE, new SaveActionHandler(){
            public void doSave(SaveActionEvent saveAction) {
                GWT.log("CluProposalController received save action request.", null);
                doSaveAction(saveAction);
            }            
        });
        super.addApplicationEventHandler(ValidateResultEvent.TYPE, new ValidateResultHandler() {
            @Override
            public void onValidateResult(ValidateResultEvent event) {
               List<ValidationResultContainer> list = event.getValidationResult();
               if(list == null || list.size() == 0 ){
                   return;
               }
               String ele = list.get(0).getElement();
               Window.alert("Error:"+ele);
            }
        });

    }
    
        
    /**
     * @see org.kuali.student.common.ui.client.mvc.Controller#getViewsEnum()
     */
    @Override
    public Class<? extends Enum<?>> getViewsEnum() {
        return LuConfigurer.LuSections.class;
    }


    @SuppressWarnings("unchecked")
    @Override
    public void requestModel(Class modelType, ModelRequestCallback callback) {
        if (modelType == CluProposalModelDTO.class){
            if (cluProposalModel == null){
                cluProposalModel = new Model<CluProposalModelDTO>();
                cluProposalModel.put(new CluProposalModelDTO());
                StringType type = new StringType();
                type.set("kuali.lu.type.CreditCourse");
                cluProposalModel.get().put("type", type);
                
                StringType state = new StringType();
                state.set("draft");
                cluProposalModel.get().put("state", state);
                callback.onModelReady(cluProposalModel);
            } else {
                callback.onModelReady(cluProposalModel); 
            }
        } else if(modelType == ReferenceModel.class){
        	if (cluProposalModel != null){
        		ReferenceModel ref = new ReferenceModel();
        		//ref.setReferenceId(((StringType) cluProposalModel.get().get("id")).get());
        		//ref.setReferenceKey(((StringType) cluProposalModel.get().get("type")).get());
        		//FIXME: test code
        		ref.setReferenceId("testId");
        		ref.setReferenceTypeKey("referenceType.clu");
        		ref.setReferenceType("kuali.lu.type.CreditCourse");
        		ref.setReferenceState("draft");
        		Model<ReferenceModel> model = new Model<ReferenceModel>();
        		model.put(ref);
        		callback.onModelReady(model);
        	}
        } else if (modelType == ProposalInfoModelDTO.class){            
            proposalInfoModel = new Model<ProposalInfoModelDTO>();
            ProposalInfoModelDTO proposalInfoModelDTO = new ProposalInfoModelDTO();
            proposalInfoModel.put(proposalInfoModelDTO);
            callback.onModelReady(proposalInfoModel);                        
        } else {
            super.requestModel(modelType, callback);
        }
    }
    
    public void doSaveAction(SaveActionEvent saveActionEvent){
        if (proposalInfoModel == null){
            showStartSection();
        } else {
            saveProposalClu(saveActionEvent);
        }
    }
    
    public void saveProposalClu(final SaveActionEvent saveActionEvent){
        View v = getCurrentView();
        getCurrentView().updateModel();
        
        if(!savedOnce){
            cluProposalRpcServiceAsync.createProposal(cluProposalModel.get(), new AsyncCallback<CluProposalModelDTO>(){
                public void onFailure(Throwable caught) {
                    caught.printStackTrace();                        
                }

                public void onSuccess(CluProposalModelDTO result) {
                    cluProposalModel.put(result);
                    saveActionEvent.doActionComplete();                    
                }
            });
            savedOnce = true;
        }
        else{
            
            cluProposalRpcServiceAsync.saveProposal(cluProposalModel.get(), new AsyncCallback<CluProposalModelDTO>(){
                public void onFailure(Throwable caught) {
                    caught.printStackTrace();                    
                }

                public void onSuccess(CluProposalModelDTO result) {
                    cluProposalModel.put(result);
                    saveActionEvent.doActionComplete();                    
                }
            });
        }        
    }
}
