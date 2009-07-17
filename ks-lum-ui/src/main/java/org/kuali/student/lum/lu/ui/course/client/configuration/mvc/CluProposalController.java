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
import org.kuali.student.common.ui.client.widgets.KSButton;
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
            
            //This should call RPC.
            GWT.log(cluProposalModel.get().toString(), null);
        }       
    });

    public CluProposalController(){
        super();
        LuConfigurer.configureCluProposal(this);
        addButton(saveButton);
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
