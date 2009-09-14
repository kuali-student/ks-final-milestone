package org.kuali.student.common.ui.client.configurable.mvc;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue;
import org.kuali.student.common.ui.client.widgets.KSRequiredMarker;
import org.kuali.student.common.ui.client.widgets.layout.HorizontalBlockFlowPanel;
import org.kuali.student.core.validation.dto.ValidationResultInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public abstract class Section extends Composite implements ConfigurableLayoutSection{
    
    protected SectionTitle sectionTitle = SectionTitle.generateEmptyTitle();
    protected final Label instructionsLabel = new Label();
    
    public enum FieldLabelType{LABEL_TOP, LABEL_LEFT}
    public enum SectionTitleType{DECORATED_TITLE_BAR, TITLE_TOP, TITLE_LEFT, NONE}
   
    protected ArrayList<FieldDescriptor> fields = new ArrayList<FieldDescriptor>();
    protected ArrayList<Section> sections = new ArrayList<Section>();
    
    protected List<RowDescriptor> rows = new ArrayList<RowDescriptor>();
    
    private RequiredEnum requiredState = RequiredEnum.NOT_MARKED;

    protected HorizontalBlockFlowPanel generateTitlePanel() {
    	HorizontalBlockFlowPanel titlePanel = new HorizontalBlockFlowPanel();
    	titlePanel.add(sectionTitle);
    	titlePanel.add(new KSRequiredMarker(requiredState));
		return titlePanel;
	}

	public RequiredEnum getRequiredState() {
		return requiredState;
	}

	public void setRequiredState(RequiredEnum requiredState) {
		this.requiredState = requiredState;
		for(FieldDescriptor f: fields){
	        if(this.getRequiredState() == RequiredEnum.REQUIRED){
	        	//if top level is required remove required from sub fields, stop required * from being shown
	        	if(f.getRequiredState() == RequiredEnum.REQUIRED){
	        		f.setRequiredState(RequiredEnum.NOT_MARKED);
	        	}
	        }
	        else if(this.getRequiredState() == RequiredEnum.OPTIONAL){
	        	if(f.getRequiredState() == RequiredEnum.OPTIONAL){
	        		f.setRequiredState(RequiredEnum.NOT_MARKED);
	        	}
	        }
		}
		
		for(Section s: sections){
	        if(this.getRequiredState() == RequiredEnum.REQUIRED){
	        	//if top level is required remove required from sub fields, stop required * from being shown
	        	if(s.getRequiredState() == RequiredEnum.REQUIRED){
	        		s.setRequiredState(RequiredEnum.NOT_MARKED);
	        	}
	        }
	        else if(this.getRequiredState() == RequiredEnum.OPTIONAL){
	        	if(s.getRequiredState() == RequiredEnum.OPTIONAL){
	        		s.setRequiredState(RequiredEnum.NOT_MARKED);
	        	}
	        }
		}
	}

	@Override
    public void addSection(Section section) {
        //Required logic
        if(this.getRequiredState() == RequiredEnum.REQUIRED){
        	//if top level is required remove required from sub fields, stop required * from being shown
        	if(section.getRequiredState() == RequiredEnum.REQUIRED){
        		section.setRequiredState(RequiredEnum.NOT_MARKED);
        	}
        }
        else if(this.getRequiredState() == RequiredEnum.OPTIONAL){
        	if(section.getRequiredState() == RequiredEnum.OPTIONAL){
        		section.setRequiredState(RequiredEnum.NOT_MARKED);
        	}
        }
		
        sections.add(section);
        RowDescriptor row = new RowDescriptor();
        row.addSection(section);
        rows.add(row);        
    }
    
    @Override
    public void addField(FieldDescriptor fieldDescriptor) {
        //Required logic
        if(this.getRequiredState() == RequiredEnum.REQUIRED){
        	//if top level is required remove required from sub fields, stop required * from being shown
        	if(fieldDescriptor.getRequiredState() == RequiredEnum.REQUIRED){
        		fieldDescriptor.setRequiredState(RequiredEnum.NOT_MARKED);
        	}
        }
        else if(this.getRequiredState() == RequiredEnum.OPTIONAL){
        	if(fieldDescriptor.getRequiredState() == RequiredEnum.OPTIONAL){
        		fieldDescriptor.setRequiredState(RequiredEnum.NOT_MARKED);
        	}
        }
        
        fields.add(fieldDescriptor);

        RowDescriptor row = new RowDescriptor();
        row.addField(fieldDescriptor);
        rows.add(row);
        
        //If fieldDescriptor.getFieldWidget() is not implementing the 
        //HasBlurHandlers. binding.bind does not do the bind. 
        ValidationEventBinding binding = new LostFocusValidationEventBinding();
        if(fieldDescriptor.getValidationRequestCallback()!= null){
            binding.bind(fieldDescriptor.getFieldWidget(), fieldDescriptor.getValidationRequestCallback());
        }
        // how to deal with the special case
        

    }
    
    @Override    
    public void addWidget(Widget widget) {
        RowDescriptor row = new RowDescriptor();
        row.addWidget(widget);
        rows.add(row);
    }

    @Override
    public SectionTitle getSectionTitle() {
        return sectionTitle;
    }
    
    @Override
    public void setSectionTitle(SectionTitle sectionTitle) {
        this.sectionTitle = sectionTitle;
    }   
     
    @Override
    public String getInstructions() {
        return instructionsLabel.getText();
    }
    
    @Override
    public void setInstructions(String instructions) {
        instructionsLabel.setText(instructions);
    }
            
    @Override
    public List<FieldDescriptor> getFields() {
        List<FieldDescriptor> allFields = new ArrayList<FieldDescriptor>();
        allFields.addAll(fields);
        for(Section ns: sections){
            allFields.addAll(ns.getFields());
        }
        return allFields;
    }

    public abstract void validate(Callback<ValidationResultInfo.ErrorLevel> callback);
    
    public void updateModel(ModelDTO modelDTO){
        for (int i=0; i < fields.size(); i++){
            FieldDescriptor field = (FieldDescriptor)fields.get(i);
            if (field.getFieldWidget() instanceof MultiplicityComposite){
                ((MultiplicityComposite)field.getFieldWidget()).updateModelDTOValue();
            }

            PropertyBinding pBinding = field.getPropertyBinding();
            PropertyBinding wBinding = field.getWidgetBinding();
            if (wBinding != null){
                Widget w = field.getFieldWidget();            
                pBinding.setValue(modelDTO, wBinding.getValue(w));
            } else {
                GWT.log(field.getFieldKey() + " has no widget binding.", null);
            }
        }
        for(Section s: sections){
            s.updateModel(modelDTO);
        }
    }
    
    public void updateView(ModelDTO modelDTO) {
        for (int i=0; i < fields.size(); i++){
            FieldDescriptor field = (FieldDescriptor)fields.get(i);
            if (field.getFieldWidget() instanceof MultiplicityComposite){
                ModelDTOValue value = modelDTO.get(field.getFieldKey());
                ((MultiplicityComposite)field.getFieldWidget()).setValue(value);
            }
            PropertyBinding pBinding = field.getPropertyBinding();
            PropertyBinding wBinding = field.getWidgetBinding();
            if (wBinding != null){
                Widget w = field.getFieldWidget();            
                wBinding.setValue(w, pBinding.getValue(modelDTO));
            } else {
                GWT.log(field.getFieldKey() + " has no widget binding.", null);
            }
        }
        for(Section s: sections){
            s.updateView(modelDTO);
        }
    }

    public abstract void clear();
    public abstract void redraw();

}
