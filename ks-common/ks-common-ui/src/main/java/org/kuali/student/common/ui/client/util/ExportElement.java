package org.kuali.student.common.ui.client.util;

import java.io.Serializable;
import java.util.List;

public class ExportElement implements Serializable {

    private static final long serialVersionUID = 1L;
    private String fieldLabel;
    private boolean isMandatory;
    private int printType = -1;

    private String fieldValue;
    private String fieldValue2;
    private String viewName;
    
    //Print type constants.
    public static int DEFAULT = 0;
    public static int BOLD = 1;
    public static int LIST_SUBREPORT = 2;
    public static int SUBREPORT = 3;
    public static int LIST = 4;
    public static int ITALIC = 5;
    public static int PROPOSAL = 6;
    public static int PARAGRAPH = 7;

    public ExportElement() {
        super();
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
    }

	public boolean isSub() {
        return subset != null;
    }
    
    public void setPrintType(int printType) {
        this.printType = printType;
    }

    public int getPrintType() {
        if (this.printType != -1) {
            if (this.printType == LIST && this.getSubset() != null){
                return LIST_SUBREPORT;
            }
            return this.printType;
        } else if (this.getSubset() != null && this.getValue().equals( "" )) {
            return SUBREPORT;
        }
        return DEFAULT;
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

    public boolean isDataEmpty() {
        if ((this.fieldLabel == null) && (this.fieldValue == null) && (this.fieldValue2 == null)) {
            return true;
        }
        return false;
    }

    public boolean isEmpty() {
        if ((this.fieldLabel == null) && (this.fieldValue == null) && (this.fieldValue2 == null) && (this.subset == null)) {
            return true;
        }
        return false;
    }

    public String toString() {
        return this.sectionName + " - " + this.viewName + " - " + this.getFieldLabel() + " - " + this.getFieldValue() + " - " + this.getFieldValue2();
    }

}