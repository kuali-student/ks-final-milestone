package org.kuali.student.common.ui.client.configurable.mvc;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.core.validation.dto.ValidationResultInfo;

import com.google.gwt.user.client.ui.Composite;

public abstract class Section extends Composite implements ConfigurableLayoutSection{
    private String sectionTitle = null;
    private String instructions = null;
   
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
    
    public String getSectionTitle() {
        return sectionTitle;
    }

    public void setSectionTitle(String sectionTitle) {
        this.sectionTitle = sectionTitle;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
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

    public abstract void validate(Callback<ValidationResultInfo.ErrorLevel> callback);
}
