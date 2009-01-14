package org.kuali.student.enumeration.web.client.view;

import org.kuali.student.enumeration.dto.EnumeratedValueField;
import org.kuali.student.enumeration.dto.EnumeratedValueFields;
import org.kuali.student.enumeration.dto.EnumerationMeta;
import org.kuali.student.enumeration.dto.FieldDescriptor;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class EnumerationMetaComposite extends Composite {
    FlowPanel content = new FlowPanel();
    TextBox metaNameBox = new TextBox();
    TextBox metaDescBox = new TextBox();
    TextBox metaKeyBox = new TextBox();
    
    FlexTable fieldTable = new FlexTable();
    public EnumerationMetaComposite() {
        // content.setStyleName("EnumerationMetaComposite");
        initWidget(content);
        
        content.add(new HTML("NameBox"));
        content.add(metaNameBox);

        content.add(new HTML("DescBox"));
        content.add(metaDescBox);

        content.add(new HTML("KeyBox"));
        content.add(metaKeyBox);

        fieldTable.setHTML(0, 0, "Key");
        fieldTable.setHTML(0, 1, "Data Type");
        fieldTable.setHTML(0, 2, "InvalidChars");
        fieldTable.setHTML(0, 3, "MaxLength");
        fieldTable.setHTML(0, 4, "MaxOccurs");
        fieldTable.setHTML(0, 5, "MaxValue");
        fieldTable.setHTML(0, 6, "MinLength");
        fieldTable.setHTML(0, 7, "getMinOccurs");
        fieldTable.setHTML(0, 8, "getMinValue");
        fieldTable.setHTML(0, 9, "getValidChars");
        
        content.add(fieldTable);
        
        Button addRowToFieldDescriptorTable = new Button("Add FieldDescriptor");
        content.add(addRowToFieldDescriptorTable);
        addRowToFieldDescriptorTable.addClickListener(new ClickListener() {
            public void onClick(Widget arg0) {
                int rowCount = fieldTable.getRowCount();
                fieldTable.setWidget(rowCount, 0, new TextBox());
                fieldTable.setWidget(rowCount, 1, new TextBox());
                fieldTable.setWidget(rowCount, 2, new TextBox());
                fieldTable.setWidget(rowCount, 3, new TextBox());
                fieldTable.setWidget(rowCount, 4, new TextBox());
                fieldTable.setWidget(rowCount, 5, new TextBox());
                fieldTable.setWidget(rowCount, 6, new TextBox());
                fieldTable.setWidget(rowCount, 7, new TextBox());
                fieldTable.setWidget(rowCount, 8, new TextBox());
                fieldTable.setWidget(rowCount, 9, new TextBox());
            }
        });
    }

    public EnumerationMeta getEnumerationMeta() {
        EnumerationMeta meta = new EnumerationMeta();
        meta.setDesc(metaDescBox.getText());
        meta.setKey(metaKeyBox.getText());
        meta.setName(metaNameBox.getText());

        for(int i=0;i< fieldTable.getRowCount();i++){
            EnumeratedValueField field = new EnumeratedValueField();
            TextBox fieldKeyBox = (TextBox)fieldTable.getWidget(i, 0);
            field.setKey(fieldKeyBox.getText());
            FieldDescriptor fieldDescriptor = new FieldDescriptor();
            
            TextBox fieldDataTypeBox = (TextBox)fieldTable.getWidget(i, 1);
            fieldDescriptor.setDataType(fieldDataTypeBox.getText());
            
            TextBox fieldInvalidCharsBox = (TextBox)fieldTable.getWidget(i, 2);
            fieldDescriptor.setInvalidChars(fieldInvalidCharsBox.getText());

            TextBox fieldMaxLengthBox = (TextBox)fieldTable.getWidget(i, 3);
            fieldDescriptor.setMaxLength(Integer.parseInt(fieldMaxLengthBox.getText()));

            TextBox fieldMaxOccursBox = (TextBox)fieldTable.getWidget(i, 4);
            fieldDescriptor.setMaxOccurs(Integer.parseInt(fieldMaxOccursBox.getText()));

            TextBox fieldMaxValueBox = (TextBox)fieldTable.getWidget(i, 5);
            fieldDescriptor.setMaxValue(fieldMaxValueBox.getText());

            TextBox fieldMinLengthBox = (TextBox)fieldTable.getWidget(i, 6);
            fieldDescriptor.setMinLength(Integer.parseInt(fieldMinLengthBox.getText()));
            
            TextBox fieldMinOccursBox = (TextBox)fieldTable.getWidget(i, 7);
            fieldDescriptor.setMinOccurs(Integer.parseInt(fieldMinOccursBox.getText()));
            
            TextBox fieldMinValueBox = (TextBox)fieldTable.getWidget(i, 8);
            fieldDescriptor.setMinValue(fieldMinValueBox.getText());
            
            TextBox fieldValidCharsBox = (TextBox)fieldTable.getWidget(i, 9);
            fieldDescriptor.setValidChars(fieldValidCharsBox.getText());

            field.setFieldDescriptor(fieldDescriptor);
            EnumeratedValueFields fields = new EnumeratedValueFields();

            meta.setEnumeratedValueFields(fields);
        }
        return meta;
    }

    public void setEnumerationMeta(EnumerationMeta meta) {
        FlowPanel namePanel = new FlowPanel();
        namePanel.add(new HTML("Name:"));
        metaNameBox.setText(meta.getName());

        namePanel.add(new HTML("Desc:"));
        metaDescBox.setText(meta.getDesc());

        namePanel.add(new HTML("Key:"));
        metaKeyBox.setText(meta.getKey());

        content.add(namePanel);
        int rowIndex = 1;
        for (EnumeratedValueField field : meta.getEnumeratedValueFields().getEnumeratedValueField()) {
            TextBox fieldKeyBox = new TextBox();
            TextBox fieldTypeBox = new TextBox();
            TextBox fieldInvalidCharsBox = new TextBox();
            TextBox fieldMaxLengthBox = new TextBox();
            TextBox fieldMaxOccursBox = new TextBox();
            TextBox fieldMaxValueBox = new TextBox();
            TextBox fieldMinLengthBox = new TextBox();
            TextBox fieldMinOccursBox = new TextBox();
            TextBox fieldMinValueBox = new TextBox();
            TextBox fieldValidCharsBox = new TextBox();

            fieldKeyBox.setText(field.getKey());
            fieldTable.setWidget(rowIndex, 0, fieldKeyBox);
            
            fieldTypeBox.setText(field.getFieldDescriptor().getDataType());
            fieldTable.setWidget(rowIndex, 1, fieldTypeBox);
            
            fieldInvalidCharsBox.setText(field.getFieldDescriptor().getInvalidChars());
            fieldTable.setWidget(rowIndex, 2, fieldInvalidCharsBox);
            
            fieldMaxLengthBox.setText(Integer.toString(field.getFieldDescriptor().getMaxLength()));
            fieldTable.setWidget(rowIndex, 3, fieldMaxLengthBox);
            
            fieldMaxOccursBox.setText(Integer.toString(field.getFieldDescriptor().getMaxOccurs()));
            fieldTable.setWidget(rowIndex, 4, fieldMaxOccursBox);
            
            fieldMaxValueBox.setText(field.getFieldDescriptor().getMaxValue());
            fieldTable.setWidget(rowIndex, 5, fieldMaxValueBox);
            
            fieldMinLengthBox.setText(Integer.toString(field.getFieldDescriptor().getMinLength()));
            fieldTable.setWidget(rowIndex, 6, fieldMinLengthBox);
            
            fieldMinOccursBox.setText(Integer.toString(field.getFieldDescriptor().getMinOccurs()));
            fieldTable.setWidget(rowIndex, 7, fieldMinOccursBox);
            
            fieldMinValueBox.setText(field.getFieldDescriptor().getMinValue());
            fieldTable.setWidget(rowIndex, 8, fieldMinValueBox);

            fieldValidCharsBox.setText(field.getFieldDescriptor().getValidChars());
            fieldTable.setWidget(rowIndex, 9, fieldValidCharsBox);
            rowIndex = rowIndex + 1;
        }
        content.add(fieldTable);
    }

}
