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
package org.kuali.student.common.ui.client.configurable.mvc.views;

import org.kuali.student.common.ui.client.configurable.mvc.RowDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.Section;
import org.kuali.student.common.ui.client.configurable.mvc.SectionView;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * 
 * An vertical layout implementation of SectionView. Fields to this section view
 * will be added in a vertical layout. 
 * 
 * @author Kuali Student Team
 *
 */
public class VerticalSectionView extends SectionView {
    
    protected final VerticalPanel panel = new VerticalPanel();
    private boolean loaded = false;
    private String modelId;
    
    private DataModel model;
    
    public VerticalSectionView(Enum<?> viewEnum, String name, String modelId) {     
        super(viewEnum, name);
        super.initWidget(panel);
        this.modelId = modelId; 
    }
        
    @Override
    public void beforeShow(final Callback<Boolean> onReadyCallback){
        super.beforeShow(new Callback<Boolean>() {
			@Override
			public void exec(Boolean result) {
		        if (!loaded){
		            panel.add(generateTitlePanel());
		            panel.add(sectionTitle);
		            panel.add(instructionsLabel);
		            for(Section ns: sections){
		                ns.redraw();
		            }
		            for(RowDescriptor r: rows){
		                panel.add(r);
		            }
		            
		            loaded = true;
		        }

		        //Request model and redraw view
		        getController().requestModel(modelId, new ModelRequestCallback<DataModel>(){

		            @Override
		            public void onRequestFail(Throwable cause) {
		                Window.alert("Failed to get model: " + getName());
		                onReadyCallback.exec(false);
		            }

		            @Override
		            public void onModelReady(DataModel m) {
		                model = m;
		                redraw();
		                onReadyCallback.exec(true);
		            }
		            
		        });
			}
        });

    }
    
    @Override   
    public void redraw(){
        super.updateView(model);
    }
            
    @Override
    public void validate(Callback<org.kuali.student.core.validation.dto.ValidationResultInfo.ErrorLevel> callback) {
        // TODO bsmith - THIS METHOD NEEDS JAVADOCS        
    }

    /**
     * This updates the model
     * 
     * @see org.kuali.student.common.ui.client.mvc.View#updateModel()
     */
    @Override
    @SuppressWarnings("unchecked")
    public void updateModel() {
        if(model!=null){
            super.updateModel(model);
        }
    }
    
    public void updateView(){
        getController().requestModel(modelId, new ModelRequestCallback<DataModel>(){
            @Override
            public void onModelReady(DataModel m) {
            	// TODO review this, shouldn't it assign this.model = m?
                updateView(m);                
            }

        
            @Override
            public void onRequestFail(Throwable cause) {
                Window.alert("Failed to get model");
            }            
        });
    }
}
