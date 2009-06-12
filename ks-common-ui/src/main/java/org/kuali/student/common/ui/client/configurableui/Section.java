package org.kuali.student.common.ui.client.configurableui;

import java.util.ArrayList;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.core.dto.Idable;
import org.kuali.student.core.validation.dto.ValidationResult.ErrorLevel;

import com.google.gwt.user.client.ui.Composite;

public abstract class Section<T extends Idable> extends Composite {
    private String sectionTitle = null;
    private String instructions = null;
    private UILayout<T> parentLayout = null;
    private ArrayList<Section> innerSectionList= new ArrayList<Section>();
    
    
    public String getSectionTitle() {
        return sectionTitle;
    }

    public Section<T> setSectionTitle(String sectionTitle) {
        this.sectionTitle = sectionTitle;
        return this;
    }

    public String getInstructions() {
        return instructions;
    }

    public Section<T> setInstructions(String instructions) {
        this.instructions = instructions;
        return this;
    }

    public Section<T> setParentLayout(UILayout<T> parentLayout) {
        this.parentLayout = parentLayout;
        return this;
    }

    public UILayout<T> getParentLayout() {
        return parentLayout;
    }
    
    public abstract void validate(Callback<ErrorLevel> callback);
    
    public abstract void populate(T obj);
    public abstract void updateObject();
    public abstract Section<T> addField(PropertyField<T,Object> field);
    public Section addField(Section section){
        innerSectionList.add(section);
        return this;
    }
    public void validateChildrenSections(Callback<ErrorLevel> callback){
        for(Section section : innerSectionList){
            section.validate(callback);
        }
    }
}
