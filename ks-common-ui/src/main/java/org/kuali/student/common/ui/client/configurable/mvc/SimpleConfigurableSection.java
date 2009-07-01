package org.kuali.student.common.ui.client.configurable.mvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.kuali.student.core.validation.dto.ValidationResult.ErrorLevel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class SimpleConfigurableSection extends Section {
    
    protected final VerticalPanel panel = new VerticalPanel();
	private final Label sectionTitleLabel = new Label();
	private final Label instructionsLabel = new Label();
	private KSFormLayoutPanel form = null;
	private boolean loaded = false;
	private ArrayList<FieldDescriptor> fields = new ArrayList<FieldDescriptor>();
	private Map<Enum<?>, NestedSection> sections = new HashMap<Enum<?>, NestedSection>();

		
	public SimpleConfigurableSection(Enum<?> viewEnum, String name) {	    
		super(viewEnum, name);
	    super.initWidget(panel);
		panel.add(sectionTitleLabel);
		panel.add(instructionsLabel);			
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
	public void validate(final Callback<ErrorLevel> callback) {
	    //TODO: Implement this
	    //For every field on this section do validation
	    //For every section call validate
	}
	
	public void setEditMode(EditMode mode){
	    this.form.setEditMode(mode);
	}

	public void beforeShow(){
	    if (!loaded){
	        form = new KSFormLayoutPanel();
	        panel.add(form);
/*	        for (int i=0; i < orderedWidgetList.size(); i++){
	            //FieldDescriptor field = fields.get(i);
	            Widget widget 
	            if()
	            KSFormField formField = new KSFormField(field.getFieldKey(), field.getFieldLabel());
	            formField.setWidget(field.getFieldWidget());
	            form.addFormField(formField);
	        }*/
	        for(Object o: orderedLayoutList){
	            if(o instanceof NestedSection){
	                NestedSection ns = ((NestedSection) o);
	                ns.redraw();
	                panel.add(ns);
	                
	            }
	            else if(o instanceof FieldDescriptor){
	                FieldDescriptor field = (FieldDescriptor)o;
	                KSFormField formField = new KSFormField(field.getFieldKey(), field.getFieldLabel());
	                formField.setWidget(field.getFieldWidget());
	                form.addFormField(formField);
	            }
	        }
	        loaded = true;
	    }
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
            for(NestedSection s: sections.values()){
                s.updateModel(model);
            }
            GWT.log(modelDTO.toString(), null);
        }

        public void onRequestFail(Throwable cause) {
            Window.alert("Model could not be updated");
        }	    
	};
	
	public void updateView(){
	}
	
	private ModelRequestCallback<ModelDTO> viewUpdateCallback = new ModelRequestCallback<ModelDTO>(){

        public void onModelReady(Model<ModelDTO> model) {
            ModelDTO modelDTO = model.get();
            for (int i=0; i < fields.size(); i++){
                FieldDescriptor field = fields.get(i);
                ModelDTOValue modelDTOValue = modelDTO.get(field.getFieldKey());
                ModelDTOValueBinder.copyValueFromModelDTO(modelDTOValue, field.getFieldWidget());
            }                                   
        }

        @Override
        public void onRequestFail(Throwable cause) {
            // TODO Will Gomes - THIS METHOD NEEDS JAVADOCS
            
        }
	};

    @Override
    public List<FieldDescriptor> getFields() {
        // TODO bsmith - THIS METHOD NEEDS JAVADOCS
        return null;
    }
}
