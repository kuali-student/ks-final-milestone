/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.ui.kitchensink.client.kscommons.selectable;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_EXAMPLE;

import java.util.ArrayList;
import java.util.List;


import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.pagetable.TableSelectionToLabelHandler;


import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.gen2.table.client.AbstractColumnDefinition;
import com.google.gwt.gen2.table.client.CachedTableModel;
import com.google.gwt.gen2.table.client.ColumnDefinition;
import com.google.gwt.gen2.table.client.DefaultTableDefinition;
import com.google.gwt.gen2.table.client.FixedWidthGrid;
import com.google.gwt.gen2.table.client.PagingOptions;
import com.google.gwt.gen2.table.client.PagingScrollTable;
import com.google.gwt.gen2.table.client.TableDefinition;
import com.google.gwt.gen2.table.client.SelectionGrid.SelectionPolicy;
import com.google.gwt.gen2.table.override.client.FlexTable;
import com.google.gwt.gen2.table.override.client.FlexTable.FlexCellFormatter;
import com.google.gwt.gen2.table.override.client.HTMLTable.ColumnFormatter;

/**
 * An example of a scroll table that extends 
 * @see class com.google.gwt.gen2.table.client.AbstractScrollTable
 * Rows are mapped to an example dto that implements 
 * @see org.kuali.student.core.dto.Idable
 * Where all columns contain text, the text of every selected row is copied
 * to a Label
 * Multi-row selection is the selection policy 
 * 
 * @author Kuali Student Team (gstruthers@berkeley.edu)
 *
 */
public class ScrollSelectableExample extends Composite {
    final FlexTable tableAndSelection = new FlexTable();
    final VerticalPanel main = new VerticalPanel();
    final HorizontalPanel pagingAndSelection = new HorizontalPanel();
    
    PagingScrollTable<Person> pagingScrollTable;
    
    final KSLabel label = new KSLabel("Scrollable table with sortable columns" +
            "\nthat displays multi row selections: ", false);
    private People tableModel;

    
    /**
     * The {@link DefaultTableDefinition}.
     */
    private DefaultTableDefinition<Person> tableDefinition = null;
    private TableSelectionToLabelHandler handler = null;
    final KSLabel selection = new KSLabel("Selected");

    public ScrollSelectableExample() {

        createTableModel();
        TableDefinition<Person> tableDef = createTableDefinition();
        pagingScrollTable = new PagingScrollTable<Person>(tableModel,tableDef);
        pagingScrollTable.setEmptyTableWidget(new HTML(
            "There is no data to display"));
        
        FixedWidthGrid dataTable = pagingScrollTable.getDataTable();
        //dataTable.setSelectionPolicy(SelectionPolicy.ONE_ROW);
        pagingScrollTable.setPageSize(tableModel.getRowCount());
        handler = new TableSelectionToLabelHandler(dataTable,selection);
        dataTable.addRowSelectionHandler(handler);
        int tableWidth = 200;
        pagingScrollTable.setPixelSize(tableWidth,200);//FIXME workaround for incubator bug
        //Incubator Issue 266 workaround to set column width
        int column = 0;
        for(Integer width :colPxWidths) {
            pagingScrollTable.setColumnWidth(column, width);
            column++;
        }
        main.addStyleName(STYLE_EXAMPLE);

        main.add(label);
        PagingOptions pagingOptions = new PagingOptions(pagingScrollTable); 
        pagingOptions.setPixelSize(tableWidth, pagingOptions.getOffsetHeight());
        int row = 0;
        tableAndSelection.insertRow(row);
        tableAndSelection.insertCell(row, 1);
        tableAndSelection.setWidget(row, 1, selection);
        tableAndSelection.insertCell(row, 0);
        tableAndSelection.setWidget(row, 0, pagingScrollTable);

        main.add(tableAndSelection);
        super.initWidget(main);
    }

  
    private void createTableModel() {
        tableModel = new People();

    }
    private List<Integer> colPxWidths = new ArrayList<Integer>();
    private TableDefinition<Person> createTableDefinition() {
        tableDefinition = new DefaultTableDefinition<Person>();
        int guessCharWidth = 9;//Guess how many pixels wide a header string is, to set min column width
        
        String firstNameHdr = "First Name";
        PeopleColumnDefinition<String> columnDefFirstName = new PeopleColumnDefinition<String>(firstNameHdr ){

                    @Override
                    public String getCellValue(Person rowValue) {        
                        return rowValue.getFirstName();
                    }

                    @Override
                    public void setCellValue(Person rowValue, String cellValue) {
                        rowValue.setFirstName(cellValue);   
                    }
        };
        columnDefFirstName.setMinimumColumnWidth(firstNameHdr.length());
        columnDefFirstName.setPreferredColumnWidth(firstNameHdr.length() * guessCharWidth);//Incubator Issue 266 no effect
        colPxWidths.add(firstNameHdr.length() * guessCharWidth);//Incubator Issue 266 workaround to set column width
        columnDefFirstName.setColumnSortable(true);
        tableDefinition.addColumnDefinition(columnDefFirstName);
        
        String lastNameHdr = "Last Name";
        PeopleColumnDefinition<String> columnDefLastName = new PeopleColumnDefinition<String>(lastNameHdr){

                 @Override
                 public String getCellValue(Person rowValue) {        
                     return rowValue.getLastName();
                 }

                 @Override
                 public void setCellValue(Person rowValue, String cellValue) {
                     rowValue.setLastName(cellValue);   
                 }
        };
        columnDefLastName.setMinimumColumnWidth(lastNameHdr.length());
        columnDefLastName.setPreferredColumnWidth(lastNameHdr.length() * guessCharWidth);//Incubator Issue 266 no effect
        colPxWidths.add(lastNameHdr.length() * guessCharWidth);//Incubator Issue 266 workaround to set column width
        columnDefLastName.setColumnSortable(true);
        tableDefinition.addColumnDefinition(columnDefLastName);
        return tableDefinition; 
    }
    
    /**
     * @return the tableDefinition
     */
    public DefaultTableDefinition<Person> getTableDefinition() {
        return tableDefinition;
    }
    /**
     * @return the tableModel
     */
    public People getTableModel() {
        return tableModel;
    }

    public void insertDataRow(int beforeRow) {
        tableModel.insertRow(beforeRow);
    }

}