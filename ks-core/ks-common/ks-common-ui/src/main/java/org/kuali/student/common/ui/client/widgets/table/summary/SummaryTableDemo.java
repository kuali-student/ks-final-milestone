package org.kuali.student.common.ui.client.widgets.table.summary;

import java.util.HashMap;
import java.util.Map;

import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.common.ui.client.util.PrintUtils;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r1.common.assembly.data.Metadata;
import org.kuali.student.r1.common.assembly.data.QueryPath;
import org.kuali.student.r1.common.assembly.data.Data.DataType;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class SummaryTableDemo extends Composite {
    public SummaryTableDemo() {
        super.initWidget(createDemoPage());
       // super.initWidget(createSummaryTableFromDataModel());
    }
    private Widget createSummaryTableFromDataModel(){
        VerticalPanel p = new VerticalPanel();
        p.setPixelSize(800, 800);
        

        Metadata meta = new Metadata();
        meta.setCanView(true);
        meta.setDataType(DataType.DATA);
        
        Map<String, Metadata> metadatas = new HashMap<String,Metadata>();
        Metadata fieldMeta = new Metadata();
        fieldMeta.setCanView(true);
        fieldMeta.setDataType(DataType.STRING);
        metadatas.put("field1", fieldMeta);
        
        meta.setProperties(metadatas);
        
        final DataModel datamodel = new DataModel();
        DataModelDefinition definition = new DataModelDefinition();
        definition.setMetadata(meta);

        datamodel.setDefinition(definition);
        datamodel.setRoot(new Data());
        datamodel.set(QueryPath.parse("/field1"), "value");
        
        final SummaryTableSection tableSection = GWT.create(SummaryTableSection.class);
        tableSection.init(null);
        tableSection.setContentColumnHeader1("Title 1");
        //tableSection.setContentColumnHeader2("Title 2");
        String fieldKey = "/field1";
        MessageKeyInfo messageKey = new MessageKeyInfo("/field1");
        

        
        FieldDescriptor fd = new FieldDescriptor(fieldKey, messageKey,fieldMeta);
        FieldDescriptor fd2 = new FieldDescriptor(fieldKey, messageKey,fieldMeta);
        
        SummaryTableFieldRow fieldRow = new SummaryTableFieldRow(fd,fd2);
        SummaryTableFieldBlock block = new SummaryTableFieldBlock();
        block.addSummaryTableFieldRow(fieldRow);
        block.setTitle("sectionTitle");
        tableSection.addSummaryTableFieldBlock(block);
        tableSection.updateWidgetData(datamodel);
        p.add(tableSection);
        
        
        //datamodel.set(QueryPath.parse("/field1"), "value");
        //tableSection.updateWidgetData(datamodel);
        Button button = new Button("Change Data");
        button.addClickHandler(new ClickHandler(){

            @Override
            public void onClick(ClickEvent event) {
                datamodel.set(QueryPath.parse("/field1"), "new value");
                tableSection.updateWidgetData(datamodel);
            }
            
        });
        p.add(button);
        return p;
    }
    
    private Widget createDemoPage(){
        VerticalPanel p = new VerticalPanel();
        p.setPixelSize(800, 800);
        final SummaryTable summaryTable = new SummaryTable();
        SummaryTableModel model = new SummaryTableModel();
        model.setEditable(true);
        model.setContentColumnHeader1("Title 1");
        model.setContentColumnHeader2("Title 2");
        
        SummaryTableBlock section = new SummaryTableBlock();
        section.setTitle("section 1");
        //section.setEditable(true);
        
        section.add(createRow1());
        section.add(createRow2());
        model.addSection(section);
        
        section = new SummaryTableBlock();
        section.setTitle("section 2");
        //
        
        section.add(createRow1());
        section.add(createRow2());
        
        model.addSection(section);
        summaryTable.setModel(model);
        //summaryTable.highLightRow("key1");
        summaryTable.highlightCell("key1",1, "cellHighlight");
        p.add(summaryTable);
        p.add(new KSButton("Print", new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				PrintUtils.print(summaryTable);
				
			}
		}));
        return p;
    }    
    private SummaryTableRow createRow2(){
        SummaryTableRow row = new SummaryTableRow();
        row.setKey("key1");
        row.setTitle("Row 2");
        row.setCell1(new Label("content 2"));
        return row;
    }
    private SummaryTableRow createRow1(){
          SummaryTableRow row = new SummaryTableRow();
          row.setTitle("Row 1");
          row.setContentCellCount(2);
          row.setCell1(new Label("content 1"));
          
          row.setCell2(new Label("content 1"));
          return row;
    }
}
