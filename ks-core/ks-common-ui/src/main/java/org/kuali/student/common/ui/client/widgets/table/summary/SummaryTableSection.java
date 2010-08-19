package org.kuali.student.common.ui.client.widgets.table.summary;

import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBinding;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.core.assembly.data.QueryPath;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;

public class SummaryTableSection extends VerticalSection {
    SummaryTable summaryTable = new SummaryTable();
    SummaryTableModel summaryTableModel = new SummaryTableModel();
    private boolean needToRebuldTable = true;
    Controller controller;
    DataModel comparisonModel = null;

    public SummaryTableSection(Controller controller) {
        super();
        this.controller = controller;
        this.addWidget(summaryTable);
        summaryTable.setModel(summaryTableModel);
    }

    public SummaryTableSection(Controller controller, SectionTitle title) {
        super(title);
        this.controller = controller;
        this.addWidget(summaryTable);
        summaryTable.setModel(summaryTableModel);
    }

    public void setContentColumnHeader1(String title) {
        summaryTableModel.setContentColumnHeader1(title);
    }

    public void setContentColumnHeader2(String title) {
        summaryTableModel.setContentColumnHeader2(title);
    }

    public String getContentColumnHeader1() {
        return summaryTableModel.getContentColumnHeader1();
    }

    public String getContentColumnHeader2() {
        return summaryTableModel.getContentColumnHeader2();
    }

    public void setEditable(boolean bool) {
        summaryTableModel.setEditable(bool);
    }

    public void addSummaryTableFieldBlock(SummaryTableFieldBlock section) {
        summaryTableModel.addSection(section);
    }
    
    @Override
    public void updateWidgetData(DataModel model) {
        if (needToRebuldTable) {
            summaryTable.doLayout();
            needToRebuldTable = false;
        }
        List<SummaryTableBlock> sectionList = summaryTableModel.getSectionList();
        
        controller.requestModel("ComparisonModel", new ModelRequestCallback<DataModel>() {
            @Override
            public void onModelReady(DataModel model) {
                comparisonModel = model;
                // hard code a model to test the second column
            }

            @Override
            public void onRequestFail(Throwable cause) {
                GWT.log("ComparisonModel cannot be found. " + cause.getLocalizedMessage(), null);
            }
        });

        for (int i = 0; i < sectionList.size(); i++) {
            SummaryTableFieldBlock fieldBlock = (SummaryTableFieldBlock) sectionList.get(i);
            List<SummaryTableRow> rowList = fieldBlock.getSectionRowList();
            for (int j = 0; j < rowList.size(); j++) {
                SummaryTableFieldRow fieldRow = (SummaryTableFieldRow) rowList.get(i);
                FieldDescriptor field = fieldRow.getFieldDescriptor1();
                final FieldDescriptor field2 = fieldRow.getFieldDescriptor2();
                // for the first column
                ModelWidgetBinding binding = field.getModelWidgetBinding();
                String fieldPath = QueryPath.getPathSeparator() + field.getFieldKey();
                if (binding != null) {
                    Widget w = field.getFieldWidget();
                    binding.setWidgetValue(w, model, fieldPath);
                } else {
                    GWT.log(field.getFieldKey() + " has no widget binding.", null);
                }
                // the second column
                if (comparisonModel == null) {
                    fieldRow.setContentCellCount(1);// hide the second column
                }else{
                    ModelWidgetBinding binding2 = field2.getModelWidgetBinding();
                    String fieldPath2 = QueryPath.getPathSeparator() + field2.getFieldKey();
                    if (binding2 != null) {
                        Widget w = field2.getFieldWidget();
                        binding2.setWidgetValue(w, model, fieldPath2);
                    } else {
                        GWT.log(field2.getFieldKey() + " has no widget binding for the ComparisonModel.", null);
                    }
                }

                
            }
        }
    }

    @Override
    public String addField(FieldDescriptor fieldDescriptor) {
        GWT.log("addField(FieldDescriptor fieldDescriptor) method not supported");
        throw new UnsupportedOperationException("SummaryTableSection.addField(FieldDescriptor fieldDescriptor) method not supported");
    }

    @Override
    public String addSection(Section section) {
        GWT.log("addSection(Section section) method not supported");
        throw new UnsupportedOperationException("SummaryTableSection.addSection(Section section) method not supported");
    }

    @Override
    public String addSection(String key, Section section) {
        GWT.log("addSection(String key, Section section) method not supported");
        throw new UnsupportedOperationException("SummaryTableSection.addSection(String key, Section section) method not supported");
    }

}
