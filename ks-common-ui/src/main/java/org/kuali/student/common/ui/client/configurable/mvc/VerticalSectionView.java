package org.kuali.student.common.ui.client.configurable.mvc;

import java.util.List;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;
import org.kuali.student.core.validation.dto.ValidationResultContainer;
import org.kuali.student.core.validation.dto.ValidationResultInfo;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
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
	
	private Model<ModelDTO> model = null;
		
	public VerticalSectionView(Enum<?> viewEnum, String name, Class<? extends ModelDTO> modelDTOType) {	    
		super(viewEnum, name);
	    super.initWidget(panel);
	    this.modelDTOType = modelDTOType; 
	}
		
	public void beforeShow(){
	    super.beforeShow();
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
	    getController().requestModel(modelDTOType, new ModelRequestCallback<ModelDTO>(){
            public void onModelReady(Model<ModelDTO> m) {
                //if (model != m){
                    model = m;
                    redraw();
                //}                    
            }

            @Override
            public void onRequestFail(Throwable cause) {
                Window.alert("Failed to get model: " + getName());
            }
            
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
        ModelDTO modelDTO = model.get();
        super.updateModel(modelDTO);
    }
    
    public void updateView(){
	    getController().requestModel(modelDTOType, new ModelRequestCallback<ModelDTO>(){
            public void onModelReady(Model<ModelDTO> m) {
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
