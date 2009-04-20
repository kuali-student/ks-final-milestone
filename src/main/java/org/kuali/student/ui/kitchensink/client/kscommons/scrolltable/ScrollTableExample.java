package org.kuali.student.ui.kitchensink.client.kscommons.scrolltable;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_EXAMPLE;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.pagetable.KSScrollTable;
import org.kuali.student.common.ui.client.widgets.pagetable.ScrollTableHelper;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.gen2.table.client.FixedWidthFlexTable;
import com.google.gwt.gen2.table.client.FixedWidthGrid;
import com.google.gwt.gen2.table.client.SelectionGrid.SelectionPolicy;

import com.google.gwt.gen2.table.override.client.FlexTable.FlexCellFormatter;

public class ScrollTableExample extends Composite {

    final VerticalPanel main = new VerticalPanel();
    
    KSScrollTable scrollTable;
    ScrollTableHelper helper = new ScrollTableHelper();
    final KSLabel label = new KSLabel("Scrollable table with sortable columns: ", false);

    public ScrollTableExample() {

        List<String> columnHeaders = populateMockColumnHeaders();
        FixedWidthFlexTable headerTable = helper.createStringListHeader("<b>Team 1</b>",columnHeaders);
        
        FixedWidthGrid dataTable = helper.createDataTable(SelectionPolicy.MULTI_ROW, columnHeaders.size());
        scrollTable = new KSScrollTable(dataTable, headerTable);
        populateMockData(dataTable);

        scrollTable.setPixelSize(440,200);//FIXME workaround for incubator bug
        main.addStyleName(STYLE_EXAMPLE);

        main.add(label);
        main.add(scrollTable);
        super.initWidget(main);
    }
    
    private List<String>  populateMockColumnHeaders() {
        List<String> columnHeaders = new ArrayList<String>();
        columnHeaders.add("<b>First Name<b/>");
        columnHeaders.add("<b>Last Name<b/>");
        columnHeaders.add("<b>School Name<b/>");
        return columnHeaders;
    }
    
    private void populateMockData(FixedWidthGrid dataTable) {
        int beforeRow = 0;
        List<String> rowText = new ArrayList<String>();
        rowText.add("Gary");
        rowText.add("Struthers");
        rowText.add("UC Berkeley");
        helper.insertStringListRow(beforeRow, rowText, dataTable);
        beforeRow++;
        rowText.set(0, "Brian");
        rowText.set(1, "Smith");
        rowText.set(2,"Delta College");
        helper.insertStringListRow(beforeRow, rowText, dataTable);
        beforeRow++;
        rowText.set(0, "Joe");
        rowText.set(1, "Yin");
        rowText.set(2,"University of British Columbia");
        helper.insertStringListRow(beforeRow, rowText, dataTable);
        beforeRow++;
        rowText.set(0, "Will");
        rowText.set(1, "Gnomes");
        rowText.set(2,"University of Maryland");
        helper.insertStringListRow(beforeRow, rowText, dataTable);
        beforeRow++;
        rowText.set(0, "Wil");
        rowText.set(1, "Johnson");
        rowText.set(2,"Florida State University");
        helper.insertStringListRow(beforeRow, rowText, dataTable);
        beforeRow++;
        rowText.set(0, "Heather");
        rowText.set(1, "Johnson");
        rowText.set(2,"University of British Columbia");
        helper.insertStringListRow(beforeRow, rowText, dataTable);
    }
}