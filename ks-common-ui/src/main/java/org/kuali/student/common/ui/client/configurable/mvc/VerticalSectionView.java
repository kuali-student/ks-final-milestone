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
package org.kuali.student.common.ui.client.configurable.mvc;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.CollectionModel;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;

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
	
	private Class<? extends ModelDTO> modelDTOType;
	
	private CollectionModel<ModelDTO> model = null;
		
	public VerticalSectionView(Enum<?> viewEnum, String name, Class<? extends ModelDTO> modelDTOType) {	    
		super(viewEnum, name);
	    super.initWidget(panel);
	    this.modelDTOType = modelDTOType; 
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
			    getController().requestModel(modelDTOType, new ModelRequestCallback<CollectionModel<ModelDTO>>(){
		            public void onModelReady(CollectionModel<ModelDTO> m) {
		                //if (model != m){
		                    model = m;
		                    redraw();
		                    onReadyCallback.exec(true);
		                //}                    
		            }

		            @Override
		            public void onRequestFail(Throwable cause) {
		                Window.alert("Failed to get model: " + getName());
		                onReadyCallback.exec(false);
		            }
		            
		        });	}
	    	
	    });
	}
	
    @Override	
	public void redraw(){
		super.updateView(model.get());
/*        ModelDTO modelDTO = model.get();
        for (int i=0; i < fields.size(); i++){
            FieldDescriptor field = fields.get(i);
            field.getWidgetBinding().setValue(field.getFieldWidget(), field.getPropertyBinding().getValue(modelDTO));
        }
        for(Section s: sections){
            s.updateView(model.get());
        }*/
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
    		ModelDTO modelDTO = model.get();
    		super.updateModel(modelDTO);
    	}
    }
    
    public void updateView(){
	    getController().requestModel(modelDTOType, new ModelRequestCallback<CollectionModel<ModelDTO>>(){
            public void onModelReady(CollectionModel<ModelDTO> m) {
                    updateView(m.get());
            }

            @Override
            public void onRequestFail(Throwable cause) {
                Window.alert("Failed to get model");
            }
            
        });
    }

/*	@Override
	public void processValidationResults(List<ValidationResultContainer> results) {
		for(RowDescriptor r: rows){
			r.clearValidationMessages();
			for(FieldDescriptor f: r.getFields()){
				if(f.hasHadFocus()){
					for(ValidationResultContainer vc: results){
						if(vc.getElement().equals(f.getFieldKey())){
							r.setValidationMessages(vc.getValidationResults());
						}
					}
				}
			}
		}
		
		for(ValidationResultContainer vc: results){
			System.out.println(vc.getElement() + " : " +  vc.getDataType());
		}
		
		for(FieldDescriptor f: fields){
			for(ValidationResultContainer vc: results){
				if(vc.getElement().equals(f.getFieldKey())){
					
				}
			}
		}
		
		for(Section s: sections){
			s.processValidationResults(results);
		}
	}*/
}
