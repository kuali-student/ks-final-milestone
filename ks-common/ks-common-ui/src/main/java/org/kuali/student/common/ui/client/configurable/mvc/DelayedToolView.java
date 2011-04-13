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

package org.kuali.student.common.ui.client.configurable.mvc;

import java.util.List;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.dto.ReferenceModel;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.LazyPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * A view that delays its generation until it requested to be shown.
 * 
 * @author Kuali Student Team
 *
 */
public abstract class DelayedToolView extends LazyPanel implements ToolView{
    private Controller controller;    
    private Enum<?> viewEnum;
    private String viewName;    //View name is being used as menu item label   

    private HasReferenceId reference;
    
    private ModelRequestCallback<ReferenceModel> modelRequestCallback = 
        new ModelRequestCallback<ReferenceModel>(){
            public void onModelReady(ReferenceModel model) {
                reference.setReferenceId(model.getReferenceId());
                reference.setReferenceTypeKey(model.getReferenceTypeKey());
                reference.setReferenceType(model.getReferenceType());
                reference.setReferenceState(model.getReferenceState());
                DelayedToolView.this.setVisible(true);       
            }
    
            public void onRequestFail(Throwable cause) {
                Window.alert(cause.toString());
            }
    };
    
    
    /**
     * @param controller
     * @param name
     */
    public DelayedToolView(Controller controller, Enum<?> viewEnum, String viewName) {
        this.controller = controller;
        this.viewName = viewName;
        this.viewEnum = viewEnum;
    }


    public DelayedToolView(Enum<?> viewEnum, String viewName) {
        this.controller = null;
        this.viewEnum = viewEnum;
        this.viewName = viewName;
    }
   
    @Override
    public void beforeShow(final Callback<Boolean> onReadyCallback){
        if (getWidget() instanceof HasReferenceId){
            reference = (HasReferenceId)getWidget();
            controller.requestModel(ReferenceModel.class, modelRequestCallback);
        } else if (this instanceof HasReferenceId){
            reference = (HasReferenceId)this;
            controller.requestModel(ReferenceModel.class, modelRequestCallback);            
        } else {
            this.setVisible(true);
        }
        // FIXME ? need to wire onReadyCallback into the model request, so that we aren't indicating that we're ready before the model is available?
        onReadyCallback.exec(true);
    }

    /**
     * @see org.kuali.student.common.ui.client.mvc.View#beforeHide()
     */
    @Override
    public boolean beforeHide() {
        return true;
    }


    /**
     * @see org.kuali.student.common.ui.client.mvc.View#getController()
     */
    @Override
    public Controller getController() {
        return this.controller;
    }


    /**
     * @see org.kuali.student.common.ui.client.mvc.View#getName()
     */
    @Override
    public String getName() {
        return this.viewName;
    }

    public Enum<?> getViewEnum() {
        return viewEnum;
    }

    /**
     * @see org.kuali.student.common.ui.client.mvc.View#updateModel()
     */
    @Override
    public void updateModel() {
        //There is no model to update here, reference model is read-only        
    }
    
    public void setController(Controller controller){
        this.controller = controller;
    }

    
    public Widget asWidget(){
    	return this;
    }
    
    @Override
    public String collectHistory(String historyStack) {
    	return null;
    }


	@Override
	protected Widget createWidget() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onHistoryEvent(String historyStack) {
		
	}

	@Override
	public void collectBreadcrumbNames(List<String> names) {
		names.add(this.getName());
		
	}
	
	   public boolean isExportButtonActive() {
	        return false;
	    }
}
