package org.kuali.student.common.ui.client.widgets.table.summary;

import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.core.assembly.data.MetadataInterrogator;

public class SummaryTableFieldRow extends SummaryTableRow{
    FieldDescriptor fieldDescriptor1;
    FieldDescriptor fieldDescriptor2;
    
    public SummaryTableFieldRow(FieldDescriptor fieldDescriptor){
        this.fieldDescriptor1 = fieldDescriptor;
        this.setKey(fieldDescriptor.getFieldKey());
        this.setCell1(fieldDescriptor.getFieldWidget());
        this.setTitle(fieldDescriptor.getFieldLabel());
        this.setRequired(MetadataInterrogator.isRequired(fieldDescriptor.getMetadata()));
        
    }
    public SummaryTableFieldRow(FieldDescriptor fieldDescriptor1,
            FieldDescriptor fieldDescriptor2){
        this.fieldDescriptor1 = fieldDescriptor1;
        this.setKey(fieldDescriptor1.getFieldKey());
        this.setCell1(fieldDescriptor1.getFieldWidget());
        this.setTitle(fieldDescriptor1.getFieldLabel());
        this.setRequired(MetadataInterrogator.isRequired(fieldDescriptor1.getMetadata()));
        
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

}
