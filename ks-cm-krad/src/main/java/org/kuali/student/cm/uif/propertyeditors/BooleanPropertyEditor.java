package org.kuali.student.cm.uif.propertyeditors;

import org.apache.commons.lang.BooleanUtils;
import java.beans.PropertyEditorSupport;
import java.io.Serializable;

/**
 * Renders booleans and Yes or No.
 */
public class BooleanPropertyEditor extends PropertyEditorSupport implements Serializable {
    /**
     * Converts boolean value to Yes or No.
     */
    @Override
    public String getAsText() {
        Object obj = this.getValue();

        if (obj == null) {
            return null;
        }

        return BooleanUtils.toStringYesNo((Boolean) obj);
    }

    @Override
    public void setAsText(String text) {
        this.setValue(BooleanUtils.toBoolean(text));
    }
}