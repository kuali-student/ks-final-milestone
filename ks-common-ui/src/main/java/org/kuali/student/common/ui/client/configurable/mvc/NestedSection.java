package org.kuali.student.common.ui.client.configurable.mvc;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;

public abstract class NestedSection extends Composite implements ConfigurableLayoutSection{
    
    
    public abstract void updateModel(Model<ModelDTO> model);
    public abstract void clear();
    public abstract void redraw();
    
    protected final Label sectionTitleLabel = new Label();
    protected final Label instructionsLabel = new Label();
    
    protected ArrayList<FieldDescriptor> fields = new ArrayList<FieldDescriptor>();
    protected ArrayList<NestedSection> sections = new ArrayList<NestedSection>();
    protected List<Object> orderedLayoutList = new ArrayList<Object>();
   
    @Override
    public void addSection(NestedSection section) {
        sections.add(section);
        orderedLayoutList.add(section);
    }
    
    @Override
    public void addField(FieldDescriptor fieldDescriptor) {
        fields.add(fieldDescriptor);
        orderedLayoutList.add(fieldDescriptor);
    }
    
    @Override
    public String getSectionTitle() {
        return sectionTitleLabel.getText();
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
    public void setSectionTitle(String sectionTitle) {
        sectionTitleLabel.setText(sectionTitle);
    }
    

    @Override
    public List<FieldDescriptor> getFields() {
        List<FieldDescriptor> allFields = new ArrayList<FieldDescriptor>();
        allFields.addAll(fields);
        for(NestedSection ns: sections){
            allFields.addAll(ns.getFields());
        }
        return allFields;
    }

}
