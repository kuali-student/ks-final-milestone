package org.kuali.student.common.ui.client.util;

import java.io.Serializable;
import java.util.List;

public class ExportElement implements Serializable {

    private static final long serialVersionUID = 1L;
    private String fieldLabel;
    private boolean isMandatory;

    private String fieldValue;
    private String fieldValue2;
    private String viewName;

    public ExportElement() {
        super();
    }

    public ExportElement(String viewName, String sectionName) {
        super();
        this.viewName = viewName;
        this.sectionName = sectionName;
    }

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
        return this.fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
//        if (this.fieldValue != null && fieldValue.startsWith("CORE")) {
//			System.out.println("Stop gou hier");
//		}
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
        return fieldValue2;
    }

    public void setFieldValue2(String fieldValue2) {
        this.fieldValue2 = fieldValue2;
    }

    public String getKey() {
        if (this.getFieldLabel() != null) {
            return this.getFieldLabel();
        }
        return "";
    }

    public String getValue() {
        if (this.getFieldValue() != null) {
            return getFieldValue();
        }
        return "";
    }

    public String getProposalValue() {
        if (this.getFieldValue() != null) {
            return getFieldValue();
        }
        return "";
    }

    public String getOriginalValue() {
        if (this.getFieldValue2() != null) {
            return this.getFieldValue2();
        }
        return "";
    }

    public boolean isEmpty() {
        if ((this.fieldLabel == null) && (this.fieldValue == null) && (this.fieldValue2 == null) && (this.subset == null)) {
            return true;
        }
        return false;
    }

    public String printLine() {
        String output = new String();
//        if (this.getFieldLabel() == null && this.fieldValue != null) {
//			this.setFieldLabel(this.getFieldValue());
//		}

        output = this.sectionName + " - " + this.viewName + " - " + this.getFieldLabel() + " - " + this.getFieldValue() + " - " + this.getFieldValue2();
        return output;
    }

}