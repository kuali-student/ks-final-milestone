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
package org.kuali.student.lum.lu.ui.course.client.configuration.viewclu;

import org.kuali.student.common.ui.client.configurable.mvc.PagedSectionLayout;
import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.dto.ReferenceModel;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.StringType;
import org.kuali.student.lum.lu.ui.course.client.configuration.LUConstants;
import org.kuali.student.lum.lu.ui.course.client.configuration.mvc.CluProposalModelDTO;
import org.kuali.student.lum.lu.ui.course.client.service.CluProposalRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.CluProposalRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in. 
 * 
 * @author Kuali Student Team
 *
 */
public class ViewCluController extends PagedSectionLayout{
    private Model<CluProposalModelDTO> cluProposalModel;
    
    private String id = null;
    
    private CluProposalRpcServiceAsync cluProposalRpcServiceAsync = GWT.create(CluProposalRpcService.class);  

    public ViewCluController(String id){
        super();
        //TODO:  Not sure what defaults we should use or if we should even allow a default
        init(id, LUConstants.LU_TYPE_CREDIT_COURSE, LUConstants.LU_STATE_PROPOSED);

    }
    
    public ViewCluController(String id, String type, String state){
        super();
        init(id, type, state);
    }

    private void init(String id, String type, String state) {
        ViewCluConfigurer.generateLayout(this);
        cluProposalModel = null;
        this.setModelDTOType(CluProposalModelDTO.class);
//        addApplicationEventHandler(SaveActionEvent.TYPE, new SaveActionHandler(){
//            public void doSave(SaveActionEvent saveAction) {
//                GWT.log("CluProposalController received save action request.", null);
//                doSaveAction(saveAction);
//            }            
//        });
        this.id = id;
        loadClu();

    }
    
    /**
     * @see org.kuali.student.common.ui.client.mvc.Controller#getViewsEnum()
     */
    @Override
    public Class<? extends Enum<?>> getViewsEnum() {
        return ViewCluConfigurer.LuSections.class;
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
        } else {
            super.requestModel(modelType, callback);
        }
    }


    
    private void setId(String id) {
        
        this.id = id;
        loadClu();
        
    }
        
    private void loadClu() {
        
        if (id != null) {
        	//FIXME This method is no longer available
//           cluProposalRpcServiceAsync.getProposalModelDTO(id,  new AsyncCallback<CluProposalModelDTO>(){
//            @Override
//            public void onFailure(Throwable caught) {
//                caught.printStackTrace();
//                
//            }
//
//            @Override
//            public void onSuccess(CluProposalModelDTO result) {
//                cluProposalModel.put(result);
//                getCurrentView().beforeShow();
////                cluProposalModel.update(result);
////                getCurrentView().updateModel();
//
//            }
//        }); 
           
        }
    }
    
}
