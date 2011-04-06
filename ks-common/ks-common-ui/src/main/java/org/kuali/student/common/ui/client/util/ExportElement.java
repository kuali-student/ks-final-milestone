package org.kuali.student.common.ui.client.util;

import java.io.Serializable;
import java.util.List;

public class ExportElement implements Serializable {

    private static final long serialVersionUID = 1L;
    private String fieldLabel = new String("label");
    private boolean isMandatory;

    private String fieldValue;
    private String fieldValue2;
    private String viewName;
    private String sectionName;
    private List<ExportElement> subset;

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getFieldLabel() {
        return fieldLabel;
    }

    public void setFieldLabel(String fieldLabel) {
        this.fieldLabel = fieldLabel;
    }

    public String getFieldValue() {
        if (fieldValue != null) {
            return fieldValue;
        }
        return "";
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public String getKey() {
        return fieldLabel;
    }

    public boolean isSub() {
        return subset != null;
    }

    public List<ExportElement> getSubset() {
        return subset;
    }

    public void setSubset(List<ExportElement> subset) {
        if (subset != null && subset.size() > 0) {
            this.subset = subset;            
        }
    }

    public boolean isMandatory() {
        return isMandatory;
    }

    public void setMandatory(boolean isMandatory) {
        this.isMandatory = isMandatory;
    }

    public String getFieldValue2() {
        if (fieldValue2 != null) {
            return fieldValue2;
        }
        return "";
    }

    public void setFieldValue2(String fieldValue2) {
        this.fieldValue2 = fieldValue2;
    }

    public String getValue() {
        return this.getFieldValue();
    }

    public String getProposalValue() {
        return this.getFieldValue();
    }

    public String getOriginalValue() {
        return this.getFieldValue2();
    }
    
    public String printLine() {
        String output = new String();
        output = this.sectionName + " - " + this.viewName + " - " + this.getFieldLabel() + " - " + this.getFieldValue() + " - " + this.getFieldValue2();
        return output;
    }

}