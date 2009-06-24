package org.kuali.student.common.ui.client.configurable.mvc;

import java.util.ArrayList;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValueBinder;
import org.kuali.student.common.ui.client.widgets.KSRichEditor;
import org.kuali.student.common.ui.client.widgets.KSTextArea;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.forms.KSFormField;
import org.kuali.student.common.ui.client.widgets.forms.KSFormLayoutPanel;
import org.kuali.student.common.ui.client.widgets.forms.EditModeChangeEvent.EditMode;
import org.kuali.student.core.validation.dto.ValidationResult.ErrorLevel;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SimpleConfigurableSection extends LayoutSectionView implements ConfigurableLayoutSection {
    
    protected final VerticalPanel panel = new VerticalPanel();
	private final Label sectionTitleLabel = new Label();
	private final Label instructionsLabel = new Label();
	private KSFormLayoutPanel form = null;
	private boolean loaded = false;
	private ArrayList<FieldEntry> fields = new ArrayList<FieldEntry>();	
	
	//FIXME: Could use KSFormField, but it's a bit clunky for config time, although
	// making use of another entry object that overlaps KSFormField isn't ideal either.
	private class FieldEntry{
        private String fieldKey;
	    private String fieldLabel;
	    private FieldType fieldType;

       public FieldEntry(String fieldKey, String fieldLabel, FieldType fieldType) {
            super();
            this.fieldKey = fieldKey;
            this.fieldLabel = fieldLabel;
            this.fieldType = fieldType;
        }

        public String getFieldKey() {
            return fieldKey;
        }
    
        public String getFieldLabel() {
            return fieldLabel;
        }
    
        public FieldType getFieldType() {
            return fieldType;
        }
	}
	
	public SimpleConfigurableSection(Enum<?> viewEnum, String name) {	    
		super(viewEnum, name);
	    super.initWidget(panel);
		panel.add(sectionTitleLabel);
		panel.add(instructionsLabel);			
	}
	
	@Override
	public void setInstructions(String instructions) {
		instructionsLabel.setText(instructions);
	}

	@Override
	public void setSectionTitle(String sectionTitle) {
		sectionTitleLabel.setText(sectionTitle);
	}

	@Override
	public void addField(String fieldKey, String fieldLabel) {
	    fields.add(new FieldEntry(fieldKey, fieldLabel, FieldType.TEXTBOX));
	}

	@Override
    public void addField(String fieldKey, String fieldLabel, FieldType fieldType) {
        fields.add(new FieldEntry(fieldKey, fieldLabel, fieldType));
    }

	
    /**
     * @see org.kuali.student.common.ui.client.configurable.mvc.ConfigurableLayoutSection#addSection(java.lang.String)
     */
    @Override
    public void addSection(String fieldKey, ConfigurableLayoutSection section) {
        
    }

	@Override
	public void validate(final Callback<ErrorLevel> callback) {
	    //TODO: Implement this
	}
	
	public void setEditMode(EditMode mode){
	    this.form.setEditMode(mode);
	}

	public void beforeShow(){
	    if (!loaded){
	        form = new KSFormLayoutPanel();
	        panel.add(form);
	        for (int i=0; i < fields.size(); i++){
	            FieldEntry field = fields.get(i);
	            KSFormField formField = new KSFormField(field.getFieldKey(), field.getFieldLabel());
	            switch (field.getFieldType()) {
                    case TEXTBOX: formField.setWidget(new KSTextBox()); break;
                    case TEXTAREA: formField.setWidget(new KSTextArea()); break;
                    case RICHTEXT: formField.setWidget(new KSRichEditor()); break;
                }
	            form.addFormField(formField);
	        }
	        loaded = true;
	    }
	}
	
	public void clear(){
	    //TODO: Reset the form...form will require clear/reset method();
	}
	
	public void updateModel(){
	    getController().requestModelDTO(modelUpdateCallback);
	}
	
	private ModelRequestCallback<ModelDTO> modelUpdateCallback = new ModelRequestCallback<ModelDTO>(){
        public void onModelReady(Model<ModelDTO> model) {
            ModelDTO modelDTO = model.get();
            for (int i=0; i < fields.size(); i++){
                String key = ((FieldEntry)fields.get(i)).getFieldKey();
                ModelDTOValue modelDTOValue = modelDTO.get(key);
                ModelDTOValueBinder.copyValue(form.getFieldValue(key), modelDTOValue);
            }        
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
            for (int i=0; i < form.getRowCount(); i++){
                KSFormField formField = form.getFormRow(i);
                ModelDTOValue modelDTOValue = modelDTO.get(formField.getName());
                ModelDTOValueBinder.copyValue(modelDTOValue, (HasValue)formField.getWidget());
            }                   
        }

        @Override
        public void onRequestFail(Throwable cause) {
            // TODO Will Gomes - THIS METHOD NEEDS JAVADOCS
            
        }
	};

}
