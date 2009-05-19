package org.kuali.student.common.ui.client.configurable;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.core.dto.Idable;
import org.kuali.student.core.validation.dto.ValidationResult.ErrorLevel;

public abstract class LayoutGroup<T extends Idable> {
    private String groupTitle = null;
    private String instructions = null;
    private ConfigurableLayoutSection<T> parentLayout = null;
    
    public String getGroupTitle() {
        return groupTitle;
    }

    public LayoutGroup<T> setSectionTitle(String groupTitle) {
        this.groupTitle = groupTitle;
        return this;
    }
    public String getInstructions() {
        return instructions;
    }
    public LayoutGroup<T> setInstructions(String instructions) {
        this.instructions = instructions;
        return this;
    }
    public LayoutGroup<T> setParentLayout(ConfigurableLayoutSection<T> parentLayout) {
        this.parentLayout = parentLayout;
        return this;
    }
    public ConfigurableLayoutSection<T> getParentLayout() {
        return parentLayout;
    }   
    
    public abstract void validate(Callback<ErrorLevel> callback);
    public abstract void populate();
    public abstract void updateObject();
}
