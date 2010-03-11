/*
 * Copyright 2009 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 * 
 * http://www.osedu.org/licenses/ECL-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.common.ui.client.widgets.pagetable;


import java.util.List;

import org.kuali.student.common.ui.client.widgets.KSLabel;

import com.google.gwt.gen2.table.client.AbstractColumnDefinition;
import com.google.gwt.gen2.table.client.MutableTableModel;
import com.google.gwt.gen2.table.client.PagingOptions;
import com.google.gwt.gen2.table.client.PagingScrollTable;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Contains a PagingScrollTable and a Label containing text of selected rows 
 * 
 * @author Kuali Student Team (gstruthers@berkeley.edu)
 *
 * @param <T>
 */
public abstract class AbstractTableSelectable<T> extends Composite {
    protected final VerticalPanel main;

    protected final KSLabel selection;
    protected PagingScrollTable<T> pagingScrollTable;


    /**
     * This constructs the widget shell
     * 
     */
    public AbstractTableSelectable() {
        super();
        main = new VerticalPanel();
        selection = new KSLabel("Selected");
        

    }

    /**
     * Insert empty row
     * 
     * @param beforeRow zero indexd row number
     */
    public void insertDataRow(int beforeRow) {
        ((MutableTableModel<T>) pagingScrollTable.getTableModel()).insertRow(beforeRow);
    }

    /**
     * Create column definitions in the concrete class for the table's DTO and column formats
     * 
     * @return column definitions
     */
    protected abstract List<AbstractColumnDefinition<T, ?>> createColumnDefinitions();

    /**
     * Use when displaying paging with paging options 
     * 
     * @param pagingOptions
     
    protected FlexTable createTableAndSelectionPanel(PagingOptions pagingOptions) {
        FlexTable tableAndSelection = new FlexTable();
        int row = 0;
        tableAndSelection.insertRow(row);
        tableAndSelection.insertCell(row, 0);
        tableAndSelection.setWidget(row, 0, pagingOptions);
        tableAndSelection.insertCell(row, 1);
        tableAndSelection.setWidget(row, 1, selection);
        row++;
        tableAndSelection.insertRow(row);
        tableAndSelection.insertCell(row, 0);
        tableAndSelection.setWidget(row, 0, pagingScrollTable);
        return tableAndSelection;
    }*/

    /**
     * Use when displaying table and selection without paging options
     * 
     * @return
     */
    protected HorizontalPanel createTableAndSelectionPanel(PagingOptions pagingOptions) {
        
        HorizontalPanel tableAndSelection = new HorizontalPanel();
        if(pagingOptions != null) {
            VerticalPanel verticalPanel = new VerticalPanel();
            verticalPanel.add(pagingOptions);
            verticalPanel.add(pagingScrollTable);
            tableAndSelection.add(verticalPanel);
        }else {
            tableAndSelection.add(pagingScrollTable);
        }
        tableAndSelection.add(selection);
        return tableAndSelection;
    }

}
