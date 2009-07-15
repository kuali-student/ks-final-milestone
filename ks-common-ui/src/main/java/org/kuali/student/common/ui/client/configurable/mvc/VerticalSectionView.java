package org.kuali.student.common.ui.client.configurable.mvc;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;

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
	
	private Model<ModelDTO> model = null;
		
	public VerticalSectionView(Enum<?> viewEnum, String name) {	    
		super(viewEnum, name);
	    super.initWidget(panel);			
	}
		
	public void beforeShow(){
	    
	    if (!loaded){
	        panel.add(sectionTitleLabel);
	        panel.add(instructionsLabel);
	        for(Section ns: sections){
	            ns.redraw();
	        }
	        for(RowDescriptor r: rows){
	            panel.add(r);
	        }
	        
	        loaded = true;
	    }

        //Request model and redraw view if model changed
	    getController().requestModel(ModelDTO.class, new ModelRequestCallback<ModelDTO>(){
            public void onModelReady(Model<ModelDTO> m) {
                if (model != m){
                    model = m;
                    redraw();
                }                    
            }

            @Override
            public void onRequestFail(Throwable cause) {
                Window.alert("Failed to get model");
            }
            
        });
	}
	
	public void clear(){
	    //TODO: Reset the form...form will require clear/reset method();
	}
		
	public void redraw(){
        ModelDTO modelDTO = model.get();
        for (int i=0; i < fields.size(); i++){
            FieldDescriptor field = fields.get(i);
            field.getWidgetBinding().setValue(field.getFieldWidget(), field.getPropertyBinding().getValue(modelDTO));
        }
        for(Section s: sections){
            s.updateView(model.get());
        }
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

    /**
     * @see org.kuali.student.common.ui.client.configurable.mvc.Section#updateView(org.kuali.student.common.ui.client.mvc.dto.ModelDTO)
     */
    @Override
    public void updateView(ModelDTO modelDTO) {
        // TODO Will Gomes - THIS METHOD NEEDS JAVADOCS
        
    }
}
