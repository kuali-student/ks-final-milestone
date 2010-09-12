package org.kuali.student.common.ui.client.widgets.table.summary;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class SummaryTableDemo extends Composite {
    public SummaryTableDemo() {
        super.initWidget(createDemoPage());
        
    }
    private Widget createDemoPage(){
        VerticalPanel p = new VerticalPanel();
        p.setPixelSize(800, 800);
        SummaryTable summaryTable = new SummaryTable();
        SummaryTableModel model = new SummaryTableModel();
        model.setContentColumnHeader1("Title 1");
        model.setContentColumnHeader2("Title 2");
        
        SummaryTableSection section = new SummaryTableSection();
        section.setTitle("section 1");
        section.setEditable(true);
        
        section.add(createRow1());
        section.add(createRow2());
        model.addSection(section);
        
        section = new SummaryTableSection();
        section.setTitle("section 2");
        section.setEditable(true);
        
        section.add(createRow1());
        section.add(createRow2());
        
        model.addSection(section);
        summaryTable.setModel(model);
        //summaryTable.highLightRow("key1");
        summaryTable.highLightCell("key1",1);
        p.add(summaryTable);
        return p;
    }    
    private SectionRow createRow2(){
        SectionRow row = new SectionRow();
        row.setKey("key1");
        row.setTitle("Row 2");
        row.setCell1(new Label("content 2"));
        return row;
    }
    private SectionRow createRow1(){
          SectionRow row = new SectionRow();
          row.setTitle("Row 1");
          row.setContentCellCount(2);
          row.setCell1(new Label("content 1"));
          
          row.setCell2(new Label("content 1"));
          return row;
    }
}
