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
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue;
import org.kuali.student.common.ui.client.mvc.dto.ReferenceModel;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTO.Updater;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.StringType;
import org.kuali.student.lum.lu.ui.course.client.configuration.LUConstants;
import org.kuali.student.lum.lu.ui.course.client.configuration.mvc.CluProposalModelDTO;
import org.kuali.student.lum.lu.ui.course.client.configuration.mvc.LuConfigurer;
import org.kuali.student.lum.lu.ui.course.client.service.CluProposalRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.CluProposalRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
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

        this();
        this.id = id;

    }

    public ViewCluController(){
        super();
        generateLayout();
        cluProposalModel = null;
        this.setModelDTOType(CluProposalModelDTO.class);  
    }

    public void onLoad() {
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
                Updater updater = cluProposalModel.get().beginUpdate(true);
                
                StringType type = new StringType();
                type.set("kuali.lu.type.CreditCourse");
                updater.put("type", type);

                StringType state = new StringType();
                state.set("draft");
                updater.put("state", state);
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



    public void setId(String id) {

        this.id = id;
        loadClu();

    }

    private void loadClu() {

        if (id != null) {
            cluProposalRpcServiceAsync.getProposal(id,  new AsyncCallback<CluProposalModelDTO>(){
                @Override
                public void onFailure(Throwable caught) {
                	Window.alert("Error loading Proposal: "+caught.getMessage());
                	caught.printStackTrace();

                }

                @Override
                public void onSuccess(CluProposalModelDTO result) {
                    cluProposalModel.put(result);
                    ModelDTOValue typeModel = cluProposalModel.get().get("cluInfo/type");
                    ModelDTOValue stateModel = cluProposalModel.get().get("cluInfo/state");
                    ViewCluConfigurer.setType(((StringType) typeModel).get());
                    ViewCluConfigurer.setState(((StringType) stateModel).get());
                    generateLayout();

                    getCurrentView().beforeShow();


                }
            }); 

        }
        else {
            generateLayout();
        }
    }

    private void generateLayout() {
        ViewCluConfigurer.generateLayout(this);
    }

}
