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
package org.kuali.student.lum.lu.ui.course.client.configuration.viewclu;

import org.kuali.student.common.ui.client.configurable.mvc.PagedSectionLayout;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue;
import org.kuali.student.common.ui.client.mvc.dto.ReferenceModel;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.StringType;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.SimpleModelDefinition;
import org.kuali.student.lum.lu.ui.course.client.configuration.mvc.CluProposalModelDTO;
//import org.kuali.student.lum.lu.ui.course.client.service.CluProposalRpcService;
//import org.kuali.student.lum.lu.ui.course.client.service.CluProposalRpcServiceAsync;
import org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcServiceAsync;
import org.kuali.student.lum.lu.ui.main.client.controller.LUMApplicationManager.LUMViews;
import org.kuali.student.lum.lu.ui.main.client.events.ChangeViewStateEvent;

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
/**
 * @deprecated 
 */
public class ViewCluController extends PagedSectionLayout{
    private DataModel cluProposalModel;

    private String cluId = null;

    private CreditCourseProposalRpcServiceAsync cluProposalRpcServiceAsync = GWT.create(CreditCourseProposalRpcService.class);  

    private KSButton quitButton = new KSButton("Quit", new ClickHandler(){
        public void onClick(ClickEvent event) {
            Controller parentController =ViewCluController.this.getParentController(); 
            parentController.fireApplicationEvent(new ChangeViewStateEvent<LUMViews>(LUMViews.HOME_MENU, event));
        }
    });
    
    public ViewCluController(){
        super(ViewCluController.class.getName());
        generateLayout();
        cluProposalModel = null;
        this.setModelDTOType(CluProposalModelDTO.class);  
        addButton(quitButton);
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
    
    @Override
    public Enum<?> getViewEnumValue(String enumValue) {
        return ViewCluConfigurer.LuSections.valueOf(enumValue);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void requestModel(Class modelType, ModelRequestCallback callback) {
        if (modelType == CluProposalModelDTO.class){
            if (cluProposalModel == null){
            	// TODO retrieve metadata (if not already cached) from the assembler
                cluProposalModel = new DataModel(new SimpleModelDefinition(), new Data());
//                cluProposalModel.put(new CluProposalModelDTO());
//                
//                StringType type = new StringType();
//                type.set("kuali.lu.type.CreditCourse");
//                cluProposalModel.get().put("type", type);
//
//                StringType state = new StringType();
//                state.set("draft");
//                cluProposalModel.get().put("state", state);
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
                callback.onModelReady(ref);
            }
        } else {
            super.requestModel(modelType, callback);
        }
    }

    public void setId(String id) {
        this.cluId = id;
        this.cluProposalModel = null;
//        loadClu();

    }

    private void loadClu() {
    	// TODO need to doublecheck, make sure this is really a proposal ID and not a CLU ID
        if (cluId != null) {
        	cluProposalRpcServiceAsync.getData(cluId, new AsyncCallback<Data>() {

                @Override
                public void onFailure(Throwable caught) {
                	Window.alert("Error loading Clu: "+caught.getMessage());
                	caught.printStackTrace();

                }

                @Override
                public void onSuccess(Data result) {
                    cluProposalModel.setRoot(result);
                    // TODO will need to update these property paths once finished integrating Norm's work
                    ViewCluConfigurer.setType((String) cluProposalModel.get("proposal/type"));
                    ViewCluConfigurer.setState((String) cluProposalModel.get("proposal/state"));
                    generateLayout();

                    
                    getCurrentView().beforeShow(NO_OP_CALLBACK);
                }
        		
        	});

        }
        else {
            generateLayout();
        }
    }

    @Deprecated
    private void generateLayout() {
        super.removeSections();
        //FIXME commented out for commit, class is believe to be unused
        //ViewCluConfigurer.generateLayout(this);
    }
    
    public void clear(){
        super.clear();
        this.cluProposalModel = null;
        this.cluId = null;
    }

}
