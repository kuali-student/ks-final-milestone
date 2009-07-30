package org.kuali.student.common.ui.client.configurable.mvc;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;
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
    
    @Override
    public void addSection(Section section) {
        sections.add(section);
        RowDescriptor row = new RowDescriptor();
        row.addSection(section);
        rows.add(row);        
    }
    
    @Override
    public void addField(FieldDescriptor fieldDescriptor) {
        fields.add(fieldDescriptor);

        RowDescriptor row = new RowDescriptor();
        row.addField(fieldDescriptor);
        rows.add(row);
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
