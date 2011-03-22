package org.kuali.student.common.ui.client.util;

import java.io.Serializable;
import java.util.List;

public class ExportElement implements Serializable {

    private String fieldLabel;
    private String fieldValue;
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
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public String getKey() {
        return fieldLabel;
    }

    public String getValue() {
        return fieldValue;
    }

    public boolean isSub() {
        return subset != null;
    }

    public List<ExportElement> getSubset() {
        return subset;
    }

    public void setSubset(List<ExportElement> subset) {
        this.subset = subset;
    }

}