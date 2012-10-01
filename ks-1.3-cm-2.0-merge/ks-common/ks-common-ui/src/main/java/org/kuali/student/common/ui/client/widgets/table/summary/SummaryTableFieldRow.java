package org.kuali.student.common.ui.client.widgets.table.summary;

import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.r1.common.assembly.data.MetadataInterrogator;

public class SummaryTableFieldRow extends SummaryTableRow{
    private FieldDescriptor fieldDescriptor1;
    private FieldDescriptor fieldDescriptor2;
    private boolean temporary = false;
    
    public SummaryTableFieldRow(){
        this.setContentCellCount(2);
    }
    
    public SummaryTableFieldRow(FieldDescriptor fieldDescriptor1,
            FieldDescriptor fieldDescriptor2){
        this.fieldDescriptor1 = fieldDescriptor1;
        this.setKey(fieldDescriptor1.getFieldKey());
        this.setCell1(fieldDescriptor1.getFieldWidget());
        this.setTitle(fieldDescriptor1.getFieldLabel());
        this.setRequired(MetadataInterrogator.isRequired(fieldDescriptor1.getMetadata()) || 
        		MetadataInterrogator.isRequiredForNextState(fieldDescriptor1.getMetadata()));
        
        this.fieldDescriptor2 = fieldDescriptor2;
        this.setCell2(fieldDescriptor2.getFieldWidget());
        this.setContentCellCount(2);
    }

    public FieldDescriptor getFieldDescriptor1(){
        return fieldDescriptor1;
    }
    public FieldDescriptor getFieldDescriptor2(){
        return fieldDescriptor2;
    }
    
    /**
     * This flag means the row should be removed before data is re-generated for the
     * rows
     * @param multiplicityRow
     */
    protected void setTemporaryRowFlag(boolean multiplicityRow) {
		this.temporary = multiplicityRow;
	}
    
    protected boolean isTemporaryRow() {
		return temporary;
	}

}
