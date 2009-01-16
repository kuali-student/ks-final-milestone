package org.kuali.student.enumeration.web.client.view;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.enumeration.dto.EnumeratedValueField;
import org.kuali.student.enumeration.dto.EnumeratedValueFields;
import org.kuali.student.enumeration.dto.EnumerationMeta;
import org.kuali.student.enumeration.dto.EnumerationMetaList;
import org.kuali.student.enumeration.dto.FieldDescriptor;
import org.kuali.student.enumeration.web.client.service.EnumerationGWTService;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class EnumerationMetaPanel extends FlowPanel {
    HTML messageHTML = new HTML();
    Button addButton = new Button("Add Enumeration Meta");
    Button addRowToFieldDescriptorTable = new Button("Add FieldDescriptor");
    Button fetchEnumerationMetasButton = new Button("Fetch Enumeration Metas");
    TextBox metaNameBox = new TextBox();
    TextBox metaDescBox = new TextBox();
    TextBox metaKeyBox = new TextBox();

    FlexTable fieldTable = new FlexTable();
    FlowPanel metaResultPanel = new FlowPanel();

    public EnumerationMetaPanel() {
        add(new HTML("Enum Meta"));
        add(messageHTML);



        addButton.addClickListener(new ClickListener() {
            public void onClick(Widget arg0) {
                EnumerationGWTService.Util.getInstance().addEnumerationMeta(getEnumerationMeta(), new AsyncCallback<EnumerationMeta>() {
                    public void onFailure(Throwable caught) {
                        messageHTML.setHTML("Exception");
                        throw new RuntimeException("Exception", caught);
                    }

                    public void onSuccess(EnumerationMeta meta) {
                        messageHTML.setHTML("Success:");
                    }
                });
            }
        });
        fetchEnumerationMetasButton.addClickListener(new ClickListener() {

            public void onClick(Widget arg0) {

                EnumerationGWTService.Util.getInstance().fetchEnumerationMetas(new AsyncCallback<EnumerationMetaList>() {
                    public void onFailure(Throwable caught) {
                        messageHTML.setHTML("Unable to load enum meta objects");
                        throw new RuntimeException("Unable to load enum meta objects", caught);
                    }

                    public void onSuccess(EnumerationMetaList metaList) {
                        messageHTML.setHTML("metaList.getEnumerationMeta():" + metaList.getEnumerationMeta().size());
                        listEnumerationMeta(metaList);
                    }
                });
            }
        });

        addRowToFieldDescriptorTable.addClickListener(new ClickListener() {
            public void onClick(Widget arg0) {
                final int rowCount = fieldTable.getRowCount();
                fieldTable.setWidget(rowCount, 0, createTextBox());
                fieldTable.setWidget(rowCount, 1, createTextBox());
                fieldTable.setWidget(rowCount, 2, createTextBox());
                fieldTable.setWidget(rowCount, 3, createTextBox());
                fieldTable.setWidget(rowCount, 4, createTextBox());
                fieldTable.setWidget(rowCount, 5, createTextBox());
                fieldTable.setWidget(rowCount, 6, createTextBox());
                fieldTable.setWidget(rowCount, 7, createTextBox());
                fieldTable.setWidget(rowCount, 8, createTextBox());
                fieldTable.setWidget(rowCount, 9, createTextBox());

                Button buttonDelete = new Button("Delete");
                buttonDelete.addClickListener(new ClickListener() {
                    public void onClick(Widget arg0) {
                        for (int i = 1; i < fieldTable.getRowCount(); i++) {
                            if (fieldTable.getWidget(i, 10) == arg0) {
                                fieldTable.removeRow(i);
                            }
                        }
                    }
                });
                fieldTable.setWidget(rowCount, 10, buttonDelete);
            }
        });

        FlexTable metaLayoutTable = new FlexTable();
        metaLayoutTable.setWidget(0, 0, new HTML("Name:"));
        metaLayoutTable.setWidget(0, 1, metaNameBox);
        metaLayoutTable.setWidget(0, 2, new HTML("Desc:"));
        metaLayoutTable.setWidget(0, 3, metaDescBox);
        metaLayoutTable.setWidget(0, 4, new HTML("Key:"));
        metaLayoutTable.setWidget(0, 5, metaKeyBox);

        add(metaLayoutTable);
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

        add(fieldTable);
        FlexTable buttonLayoutTable = new FlexTable();
        buttonLayoutTable.setWidget(0, 0, addButton);
        buttonLayoutTable.setWidget(0, 1, addRowToFieldDescriptorTable);
        buttonLayoutTable.setWidget(0, 2, fetchEnumerationMetasButton);

        add(buttonLayoutTable);
        
        add(metaResultPanel);
    }

    public EnumerationMeta getEnumerationMeta() {
        EnumerationMeta meta = new EnumerationMeta();
        meta.setDesc(metaDescBox.getText());
        meta.setKey(metaKeyBox.getText());
        meta.setName(metaNameBox.getText());

        List<EnumeratedValueField> fieldList = new ArrayList<EnumeratedValueField>();

        for (int i = 1; i < fieldTable.getRowCount(); i++) {
            EnumeratedValueField field = new EnumeratedValueField();
            TextBox fieldKeyBox = (TextBox) fieldTable.getWidget(i, 0);
            field.setKey(fieldKeyBox.getText());
            FieldDescriptor fieldDescriptor = new FieldDescriptor();

            TextBox fieldDataTypeBox = (TextBox) fieldTable.getWidget(i, 1);
            fieldDescriptor.setDataType(fieldDataTypeBox.getText());

            TextBox fieldInvalidCharsBox = (TextBox) fieldTable.getWidget(i, 2);
            fieldDescriptor.setInvalidChars(fieldInvalidCharsBox.getText());

            TextBox fieldMaxLengthBox = (TextBox) fieldTable.getWidget(i, 3);
            fieldDescriptor.setMaxLength(Integer.parseInt(fieldMaxLengthBox.getText()));

            TextBox fieldMaxOccursBox = (TextBox) fieldTable.getWidget(i, 4);
            fieldDescriptor.setMaxOccurs(Integer.parseInt(fieldMaxOccursBox.getText()));

            TextBox fieldMaxValueBox = (TextBox) fieldTable.getWidget(i, 5);
            fieldDescriptor.setMaxValue(fieldMaxValueBox.getText());

            TextBox fieldMinLengthBox = (TextBox) fieldTable.getWidget(i, 6);
            fieldDescriptor.setMinLength(Integer.parseInt(fieldMinLengthBox.getText()));

            TextBox fieldMinOccursBox = (TextBox) fieldTable.getWidget(i, 7);
            fieldDescriptor.setMinOccurs(Integer.parseInt(fieldMinOccursBox.getText()));

            TextBox fieldMinValueBox = (TextBox) fieldTable.getWidget(i, 8);
            fieldDescriptor.setMinValue(fieldMinValueBox.getText());

            TextBox fieldValidCharsBox = (TextBox) fieldTable.getWidget(i, 9);
            fieldDescriptor.setValidChars(fieldValidCharsBox.getText());

            field.setFieldDescriptor(fieldDescriptor);

            fieldList.add(field);
        }
        EnumeratedValueFields fields = new EnumeratedValueFields();
        fields.setEnumeratedValueField(fieldList);
        meta.setEnumeratedValueFields(fields);
        return meta;
    }

    public void listEnumerationMeta(EnumerationMetaList metaList) {
        metaResultPanel.clear();

        for (EnumerationMeta meta : metaList.getEnumerationMeta()) {
            DisclosurePanel d = new DisclosurePanel();

            d.setHeader(new HTML("Name:" + meta.getName() + " Desc:" + meta.getDesc() + " Key:" + meta.getKey()));

            FlexTable staticTable = new FlexTable();
            staticTable.setHTML(0, 0, "Key");
            staticTable.setHTML(0, 1, "Data Type");
            staticTable.setHTML(0, 2, "InvalidChars");
            staticTable.setHTML(0, 3, "MaxLength");
            staticTable.setHTML(0, 4, "MaxOccurs");
            staticTable.setHTML(0, 5, "MaxValue");
            staticTable.setHTML(0, 6, "MinLength");
            staticTable.setHTML(0, 7, "getMinOccurs");
            staticTable.setHTML(0, 8, "getMinValue");
            staticTable.setHTML(0, 9, "getValidChars");
            int rowIndex = 1;
            for (EnumeratedValueField field : meta.getEnumeratedValueFields().getEnumeratedValueField()) {
                staticTable.setWidget(rowIndex, 0, new HTML(field.getKey()));
                staticTable.setWidget(rowIndex, 1, new HTML(field.getFieldDescriptor().getDataType()));
                staticTable.setWidget(rowIndex, 2, new HTML(field.getFieldDescriptor().getInvalidChars()));
                staticTable.setWidget(rowIndex, 3, new HTML(Integer.toString(field.getFieldDescriptor().getMaxLength())));
                staticTable.setWidget(rowIndex, 4, new HTML(Integer.toString(field.getFieldDescriptor().getMaxOccurs())));
                staticTable.setWidget(rowIndex, 5, new HTML(field.getFieldDescriptor().getMaxValue()));
                staticTable.setWidget(rowIndex, 6, new HTML(Integer.toString(field.getFieldDescriptor().getMinLength())));
                staticTable.setWidget(rowIndex, 7, new HTML(Integer.toString(field.getFieldDescriptor().getMinOccurs())));
                staticTable.setWidget(rowIndex, 8, new HTML(field.getFieldDescriptor().getMinValue()));
                staticTable.setWidget(rowIndex, 9, new HTML(field.getFieldDescriptor().getValidChars()));
                rowIndex = rowIndex + 1;
            }
            d.setContent(staticTable);
            metaResultPanel.add(d);
        }

    }

    private TextBox createTextBox() {
        TextBox box = new TextBox();
        box.setWidth("100px");
        return box;
    }
}
