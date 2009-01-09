package org.kuali.student.enumeration.web.client.view;

import org.kuali.student.enumeration.dto.EnumeratedValueField;
import org.kuali.student.enumeration.dto.EnumerationMeta;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;

public class EnumerationMetaComposite extends Composite {
    FlowPanel content = new FlowPanel();
    
    public EnumerationMetaComposite(){
        //content.setStyleName("EnumerationMetaComposite");
        initWidget(content);
    }

    public void setEnumerationMeta(EnumerationMeta meta ){
        FlowPanel namePanel = new FlowPanel();
        namePanel.add(new HTML("Name:"));
        namePanel.add(new HTML(meta.getName()));

        namePanel.add(new HTML("Desc:"));
        namePanel.add(new HTML(meta.getDesc()));

        namePanel.add(new HTML("Key:"));
        namePanel.add(new HTML(meta.getKey()));

        content.add(namePanel);        
        
        FlexTable fieldTable = new FlexTable();
        fieldTable.setHTML(0, 0, "Key");
        fieldTable.setHTML(0, 1, "Data Type");
        fieldTable.setHTML(0, 2, "InvalidChars");
        fieldTable.setHTML(0, 3, "MaxLength");
        fieldTable.setHTML(0, 4, "MaxOccurs");
        fieldTable.setHTML(0, 5, "MaxValue");
        fieldTable.setHTML(0, 6, "MinLength");
        fieldTable.setHTML(0, 7, "getMinOccurs");
        fieldTable.setHTML(0, 8, "getMinValue");
        fieldTable.setHTML(0, 9, "MinLength");
        fieldTable.setHTML(0, 10, "getValidChars");
        
        int rowIndex = 1;
        for(EnumeratedValueField field : meta.getEnumeratedValueFields().getEnumeratedValueField()){
            fieldTable.setHTML(rowIndex, 0, field.getKey());
            fieldTable.setHTML(rowIndex, 1, field.getFieldDescriptor().getDataType());
            fieldTable.setHTML(rowIndex, 2, field.getFieldDescriptor().getInvalidChars());
            fieldTable.setHTML(rowIndex, 3, Integer.toString(field.getFieldDescriptor().getMaxLength()));
            fieldTable.setHTML(rowIndex, 4, Integer.toString(field.getFieldDescriptor().getMaxOccurs()));
            fieldTable.setHTML(rowIndex, 5, field.getFieldDescriptor().getMaxValue());
            fieldTable.setHTML(rowIndex, 6, Integer.toString(field.getFieldDescriptor().getMinLength()));
            fieldTable.setHTML(rowIndex, 7, Integer.toString(field.getFieldDescriptor().getMinOccurs()));
            fieldTable.setHTML(rowIndex, 8, field.getFieldDescriptor().getMinValue());
            fieldTable.setHTML(rowIndex, 9, Integer.toString(field.getFieldDescriptor().getMinLength()));
            fieldTable.setHTML(rowIndex, 10, field.getFieldDescriptor().getValidChars());
            rowIndex = rowIndex+1;
        }
        content.add(fieldTable);
    }
   
}
