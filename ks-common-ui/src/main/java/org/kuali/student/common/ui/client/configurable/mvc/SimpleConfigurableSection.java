package org.kuali.student.common.ui.client.configurable.mvc;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValueBinder;
import org.kuali.student.common.ui.client.widgets.forms.KSFormField;
import org.kuali.student.common.ui.client.widgets.forms.KSFormLayoutPanel;
import org.kuali.student.common.ui.client.widgets.forms.EditModeChangeEvent.EditMode;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SimpleConfigurableSection extends LayoutSectionView {
    
    protected final VerticalPanel panel = new VerticalPanel();
	private final Label sectionTitleLabel = new Label();
	private final Label instructionsLabel = new Label();
	private KSFormLayoutPanel form = null;
	private boolean loaded = false;
		
	public SimpleConfigurableSection(Enum<?> viewEnum, String name) {	    
		super(viewEnum, name);
	    super.initWidget(panel);			
	}
	
	@Override
	public void setInstructions(String instructions) {
	    super.setInstructions(instructions);
		instructionsLabel.setText(instructions);
	}

	@Override
	public void setSectionTitle(String sectionTitle) {
	    super.setSectionTitle(sectionTitle);
		sectionTitleLabel.setText(sectionTitle);
	}
	
    @Override
    public void addField(FieldDescriptor fieldDescriptor) {
        super.addField(fieldDescriptor);
        RowDescriptor row = new RowDescriptor();
        row.addField(fieldDescriptor);
        rows.add(row);
        
    }

    @Override
    public void addSection(NestedSection section) {
        super.addSection(section);
        RowDescriptor row = new RowDescriptor();
        row.addSection(section);
        rows.add(row);
    }

	public void beforeShow(){
	    
	    if (!loaded){
	        panel.add(sectionTitleLabel);
	        panel.add(instructionsLabel);
	        for(NestedSection ns: sections){
	            ns.redraw();
	        }
	        for(RowDescriptor r: rows){
	            panel.add(r);
	        }
	        loaded = true;
	    }
	    updateView();
	}
	
	public void clear(){
	    //TODO: Reset the form...form will require clear/reset method();
	}
	
	public void updateModel(){
	    Controller.findController(this).requestModelDTO(modelUpdateCallback);
	}
	
	private ModelRequestCallback<ModelDTO> modelUpdateCallback = new ModelRequestCallback<ModelDTO>(){
        public void onModelReady(Model<ModelDTO> model) {
            ModelDTO modelDTO = model.get();
            for (int i=0; i < fields.size(); i++){
                FieldDescriptor field = (FieldDescriptor)fields.get(i);
                if (!(field.getFieldWidget() instanceof MultiplicityComposite)){
                    String fieldKey = field.getFieldKey();
                    ModelDTOValue modelDTOValue = modelDTO.get(fieldKey);
                    if (modelDTOValue  != null){
                        ModelDTOValueBinder.copyValueToModelDTO(form.getFieldValue(fieldKey), modelDTOValue);
                    } else {
                        modelDTOValue = ModelDTOValueBinder.createModelDTOInstance(form.getFieldValue(fieldKey), field.getFieldType());
                        modelDTO.put(fieldKey, modelDTOValue);
                    }
                }
            } 
            for(NestedSection s: sections){
                s.updateModel(model);
            }
            GWT.log(modelDTO.toString(), null);
        }

        public void onRequestFail(Throwable cause) {
            Window.alert("Model could not be updated");
        }	    
	};
	
	public void updateView(){
	    if(Controller.findController(this) != null){
	        Controller.findController(this).requestModelDTO(viewUpdateCallback);
	    }
	}
	
	private ModelRequestCallback<ModelDTO> viewUpdateCallback = new ModelRequestCallback<ModelDTO>(){

        public void onModelReady(Model<ModelDTO> model) {
            ModelDTO modelDTO = model.get();
            for (int i=0; i < fields.size(); i++){
                FieldDescriptor field = fields.get(i);
                ModelDTOValue modelDTOValue = modelDTO.get(field.getFieldKey());
                ModelDTOValueBinder.copyValueFromModelDTO(modelDTOValue, field.getFieldWidget());
            }
            for(NestedSection s: sections){
                s.updateView(model);
            }
        }

        @Override
        public void onRequestFail(Throwable cause) {
            // TODO Will Gomes - THIS METHOD NEEDS JAVADOCS
            
        }
	};

    @Override
    public void validate(Callback<org.kuali.student.core.validation.dto.ValidationResultInfo.ErrorLevel> callback) {
        // TODO bsmith - THIS METHOD NEEDS JAVADOCS
        
    }
}
