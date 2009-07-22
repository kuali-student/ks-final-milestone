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

import org.kuali.student.common.ui.client.configurable.mvc.PagedSectionLayout;
import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.StringType;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.ui.course.client.service.CluProposal;
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
    
    CluProposalRpcServiceAsync cluProposalRpcServiceAsync = GWT.create(CluProposalRpcService.class);
       
    private KSButton createButton = new KSButton("Begin", new ClickHandler(){
        public void onClick(ClickEvent event) {
            // TODO Will Gomes - THIS METHOD NEEDS JAVADOCS
            
        }        
    });
    
    private KSButton saveButton = new KSButton("Save", new ClickHandler(){
        public void onClick(ClickEvent event) {
            getCurrentView().updateModel();
            
            StringType type = new StringType();
            type.set("kuali.lu.type.CreditCourse");
            cluProposalModel.get().put("type", type);
            
            StringType state = new StringType();
            state.set("draft");
            cluProposalModel.get().put("state", state);
            
            cluProposalRpcServiceAsync.createProposal(cluProposalModel.get(), new AsyncCallback<CluProposalModelDTO>(){
                @Override
                public void onFailure(Throwable caught) {
                    caught.printStackTrace();
                    
                }

                @Override
                public void onSuccess(CluProposalModelDTO result) {
                    System.out.println("It worked maybe");
                    cluProposalModel.put(result);
                }
            }); 
        }
    });
    
    private KSButton clear = new KSButton("Clear", new ClickHandler(){
        public void onClick(ClickEvent event) {
            
        }       
    });
    
    private KSButton testButton = new KSButton("Get Proposal Info", new ClickHandler(){
        public void onClick(ClickEvent event) {
/*            if(cluProposalModel != null){
                for(String k : cluProposalModel.get().keySet()){
                    ModelDTOValue v = cluProposalModel.get().get(k);
                    if(v instanceof StringType){
                        ((StringType) v).set(((StringType) v).get().toUpperCase());
                    }
                }
            }*/
            //((VerticalSectionView) getCurrentView()).updateView();
            cluProposalRpcServiceAsync.getProposal(((StringType) cluProposalModel.get().get("id")).get(), 
                    new AsyncCallback<CluProposal>(){

                @Override
                public void onFailure(Throwable caught) {
                    // TODO bsmith - THIS METHOD NEEDS JAVADOCS
                    
                }

                @Override
                public void onSuccess(CluProposal result) {
                    CluInfo info = result.getCluInfo();
                    Window.alert(
                       "Id: " + info.getId() + "\n" +
                       "Type: " + info.getType() + "\n" +
                       "State: " + info.getState() + "\n" +
                       "Next Review Period: " + info.getNextReviewPeriod() + "\n" +
                       "Study Subject Area: " + info.getStudySubjectArea() + "\n" +
                       "Reference URL: " + info.getReferenceURL());
                    
                    
                }
                
            });
        }       
    });

    public CluProposalController(){
        super();
        LuConfigurer.configureCluProposal(this);
        addButton(saveButton);
        addButton(testButton);
        cluProposalModel = null;
        this.setModelDTOType(CluProposalModelDTO.class);
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
                callback.onModelReady(cluProposalModel);
            } else {
                callback.onModelReady(cluProposalModel); 
            }
        } else {
            super.requestModel(modelType, callback);
        }
    }

    
}
