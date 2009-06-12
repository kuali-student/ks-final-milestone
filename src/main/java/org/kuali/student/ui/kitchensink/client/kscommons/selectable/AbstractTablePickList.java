package org.kuali.student.ui.kitchensink.client.kscommons.selectable;


import java.util.List;
import java.util.Set;

import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.list.impl.KSPickListImpl;
import org.kuali.student.ui.kitchensink.client.kscommons.dto.Person;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.gen2.table.client.AbstractColumnDefinition;
import com.google.gwt.gen2.table.client.FixedWidthGrid;
import com.google.gwt.gen2.table.client.MutableTableModel;
import com.google.gwt.gen2.table.client.PagingOptions;
import com.google.gwt.gen2.table.client.PagingScrollTable;
import com.google.gwt.gen2.table.override.client.FlexTable;
import com.google.gwt.gen2.table.override.client.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public abstract class AbstractTablePickList<T> extends Composite {
    protected final VerticalPanel main;
    private final VerticalPanel buttonPanel;


    protected PagingScrollTable<T> pagingScrollTable;
    protected PagingScrollTable<T> pagingScrollTablePicked;


    public AbstractTablePickList() {
        super();
        main = new VerticalPanel();
        buttonPanel = new VerticalPanel();

    }

    public void insertDataRow(int beforeRow) {
        ((MutableTableModel<T>) pagingScrollTable.getTableModel()).insertRow(beforeRow);
    }
    public void insertPickedDataRow(int beforeRow) {
        ((MutableTableModel<T>) pagingScrollTablePicked.getTableModel()).insertRow(beforeRow);
    }

    protected abstract List<AbstractColumnDefinition<Person, ?>> createColumnDefinitions();


    protected FlexTable createTableAndSelectionPanel() {
        FlexTable tableAndSelection = new FlexTable();
        int row = 0;
        tableAndSelection.insertRow(row);
        tableAndSelection.insertCell(row, 0);
        tableAndSelection.setWidget(row, 0, pagingScrollTable);

        tableAndSelection.insertCell(row, 1);
        buttonPanel.add(addAll);
        buttonPanel.add(add);
        buttonPanel.add(remove);
        buttonPanel.add(removeAll);
        tableAndSelection.setWidget(row, 1, buttonPanel);
        tableAndSelection.insertCell(row, 2);
        tableAndSelection.setWidget(row, 2, pagingScrollTablePicked);
        return tableAndSelection;
    }

    private final Button add = new Button(">", new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
            FixedWidthGrid leftDataTable = pagingScrollTable.getDataTable();
            Integer[] leftRowIds = new Integer[0];//ConcurrentModificationException using Set
            leftRowIds = leftDataTable.getSelectedRows().toArray(leftRowIds);
            FixedWidthGrid rightDataTable = pagingScrollTablePicked.getDataTable();
            int rightRowCount = rightDataTable.getRowCount();

            rightDataTable.resizeRows(rightRowCount + leftRowIds.length);
            for(int i = 0; i < leftRowIds.length; i++) {
                 T value = pagingScrollTable.getRowValue(leftRowIds[i]);
                 pagingScrollTablePicked.setRowValue(rightRowCount + i, value);
            }
            for(int i = 0; i < leftRowIds.length; i++) {
                leftDataTable.removeRow(leftRowIds[i]);
           }
            pagingScrollTable.redraw();
            pagingScrollTablePicked.redraw();
        }
    }); 
    private final Button remove = new Button("&lt;", new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
        FixedWidthGrid rightDataTable = pagingScrollTablePicked.getDataTable();
        Integer[] rightRowIds = new Integer[0];//ConcurrentModificationException using Set
        rightRowIds = rightDataTable.getSelectedRows().toArray(rightRowIds);
        FixedWidthGrid leftDataTable = pagingScrollTable.getDataTable();
        int leftRowCount = leftDataTable.getRowCount();

        leftDataTable.resizeRows(leftRowCount + rightRowIds.length);
        for(int i = 0; i < rightRowIds.length; i++) {
             T value = pagingScrollTablePicked.getRowValue(rightRowIds[i]);
             pagingScrollTable.setRowValue(leftRowCount + i, value);
        }
        for(int i = 0; i < rightRowIds.length; i++) {
            rightDataTable.removeRow(rightRowIds[i]);
       }
        pagingScrollTable.redraw();
        pagingScrollTablePicked.redraw();
    }
    });
   
    private final Button addAll = new Button(">>", new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
            FixedWidthGrid leftDataTable = pagingScrollTable.getDataTable();
            int leftRowCount = leftDataTable.getRowCount();
            FixedWidthGrid rightDataTable = pagingScrollTablePicked.getDataTable();
            rightDataTable.resizeRows(leftRowCount);
/*            for(int i = 0; i < rightRowCount; i++) {
                rightDataTable.removeRow(i);
            }*/
            for(int i = 0; i < leftRowCount; i++) {
                T value = pagingScrollTable.getRowValue(i);
                boolean hasZeroZero = rightDataTable.isCellPresent(i, 0);
                boolean hasZeroOne = rightDataTable.isCellPresent(i, 1);

                pagingScrollTablePicked.setRowValue(i, value);
                //leftDataTable.removeRow(i);
            }
            leftDataTable.resizeRows(0);
            pagingScrollTable.redraw();
            pagingScrollTablePicked.redraw();
        }
    });
    private final Button removeAll = new Button("&lt;&lt;", new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
            FixedWidthGrid rightDataTable = pagingScrollTablePicked.getDataTable();
            int rightRowCount = rightDataTable.getRowCount();
            FixedWidthGrid leftDataTable = pagingScrollTable.getDataTable();
            int leftRowCount = leftDataTable.getRowCount();
            leftDataTable.resizeRows(rightRowCount);

/*            for(int i = 0; i < leftRowCount; i++) {
                leftDataTable.removeRow(i);
            }*/
            for(int i = 0; i < rightRowCount; i++) {
                T value = pagingScrollTablePicked.getRowValue(i);
                pagingScrollTable.setRowValue(i, value);
            }
            rightDataTable.resizeRows(0);
            pagingScrollTable.redraw();
            pagingScrollTablePicked.redraw();
        }
    });

}