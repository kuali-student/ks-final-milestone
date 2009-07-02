package org.kuali.student.common.ui.client.configurable.mvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public abstract class NestedSection extends Composite implements ConfigurableLayoutSection{
    
    
    public abstract void updateModel(Model<ModelDTO> model);
    public abstract void clear();
    public abstract void redraw();
    
    protected final Label sectionTitleLabel = new Label();
    protected final Label instructionsLabel = new Label();
    
    protected ArrayList<FieldDescriptor> fields = new ArrayList<FieldDescriptor>();
    protected Map<Enum<?>, NestedSection> sections = new HashMap<Enum<?>, NestedSection>();
    protected List<Object> orderedLayoutList = new ArrayList<Object>();
    
    public NestedSection(Enum<?> subsectionEnum, String name){
        
    }
   
    @Override
    public void addSection(Enum<?> subSectionName, NestedSection section) {
        sections.put(subSectionName, section);
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
        for(NestedSection ns: sections.values()){
            allFields.addAll(ns.getFields());
        }
        return allFields;
    }

}
