package org.kuali.student.common.ui.client.widgets.table.scroll;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.util.BrowserUtils;
import org.kuali.student.common.ui.client.util.DebugIdUtils;
import org.kuali.student.common.ui.client.widgets.list.HasSelectionChangeHandlers;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeEvent;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;
import org.kuali.student.common.ui.client.widgets.notification.LoadingDiv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.dom.client.ScrollHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.PopupPanel.PositionCallback;

/**
 * A table with UiBinder.
 */
public class Table extends Composite implements HasRetrieveAdditionalDataHandlers, HasSelectionChangeHandlers {

    private int headerSelectedCellIndex = -1;

    private FocusType focusType = FocusType.NONE;

    private static TableUiBinder uiBinder = GWT.create(TableUiBinder.class);
    private final List<RetrieveAdditionalDataHandler> retrieveDataHandlers = new ArrayList<RetrieveAdditionalDataHandler>();

    interface TableUiBinder extends UiBinder<Widget, Table> {}

    interface SelectionStyle extends CssResource {
        String selectedRow();

        String columnAscending();

        String columnDescending();

        String selectedHeaderCell();
    }

    private static enum FocusType {
        HEADER, BODY, NONE
    }

    @UiField
    FlexTable header;
    @UiField
    MouseHoverFlexTable table;
    @UiField
    SelectionStyle selectionStyle;
    @UiField
    ScrollPanel scrollPanel;
    @UiField
    FocusPanel focusPanel;
    @UiField
    FocusPanel headerFocusPanel;
    @UiField
    HTMLPanel panel;

    private TableModel tableModel;
    private final LoadingDiv loading = new LoadingDiv();

    public Table() {
        initWidget(uiBinder.createAndBindUi(this));

        scrollPanel.addScrollHandler(new ScrollHandler() {
            @Override
            public void onScroll(ScrollEvent event) {
                int position = scrollPanel.getScrollPosition();
                // see 6th comment in KSLAB-1790; possibly not created yet?
                if (null != scrollPanel.getWidget()) {
                    int size = scrollPanel.getWidget().getOffsetHeight();
                    int diff = size - scrollPanel.getOffsetHeight();
                    if (position == diff) {
                        for (int i = 0; i < retrieveDataHandlers.size(); i++) {
                            retrieveDataHandlers.get(i).onAdditionalDataRequest();
                        }
                    }
                }
            }
        });
        addHandlers();
    }

    public void removeAllRows() {
        table.removeAllRows();
    }

    public void removeContent() {
        getScrollPanel().clear();
    }

    public void addContent() {
        getScrollPanel().setWidget(getContent());
    }

    private void addHandlers() {
        focusPanel.addKeyDownHandler(new KeyDownHandler() {

            @Override
            public void onKeyDown(KeyDownEvent event) {
                int code = event.getNativeKeyCode();
                if (code == KeyCodes.KEY_DOWN) {
                    processKeyUpAndDownEvent(TablePredicateFactory.DOWN_RIGHT_PREDICATE);
                } else if (code == KeyCodes.KEY_UP) {
                    processKeyUpAndDownEvent(TablePredicateFactory.UP_LEFT_PREDICATE);
                } else if (code == ' ') {
                    processSpaceClick();
                }
            }
        });
        headerFocusPanel.addKeyDownHandler(new KeyDownHandler() {
            @Override
            public void onKeyDown(KeyDownEvent event) {
                int code = event.getNativeKeyCode();
                if (code == KeyCodes.KEY_RIGHT) {
                    processKeyLeftRight(TablePredicateFactory.DOWN_RIGHT_PREDICATE);
                } else if (code == KeyCodes.KEY_LEFT) {
                    processKeyLeftRight(TablePredicateFactory.UP_LEFT_PREDICATE);
                } else if (code == KeyCodes.KEY_DOWN) {
                    processKeyDownOnHeader();
                } else if (code == ' ') {
                    onTableHeaderClicked(headerSelectedCellIndex, true);
                }
            }
        });
    }

    private void processKeyDownOnHeader() {
        changeFocus(FocusType.BODY);
        Row currentRow = tableModel.getRow(0);
        tableModel.setCurrentIndex(0);
        currentRow.setHighlighted(true);
        updateTableSelection();
        removeHeaderSelection();
    }

    private void removeHeaderSelection() {
        if (headerSelectedCellIndex >= 0) {
            header.getCellFormatter().removeStyleName(0, headerSelectedCellIndex, selectionStyle.selectedHeaderCell());
            headerSelectedCellIndex = -1;
        }
    }

    private void processKeyLeftRight(TablePredicateFactory.Predicate tablePredicate) {
        if (tablePredicate.indexCondition(headerSelectedCellIndex, tableModel.getColumnCount())) {
            header.getCellFormatter().removeStyleName(0, headerSelectedCellIndex, selectionStyle.selectedHeaderCell());
            headerSelectedCellIndex = tablePredicate.nextIndex(headerSelectedCellIndex);
            header.getCellFormatter().addStyleName(0, headerSelectedCellIndex, selectionStyle.selectedHeaderCell());

        }
    }

    private void processSpaceClick() {
        int index = tableModel.getCurrentIndex();
        if (index >= 0) {
            Row currentRow = tableModel.getRow(index);
            boolean selected = currentRow.isSelected();
            if (selected) {
                currentRow.setSelected(false);
            } else {
                tableModel.setSelectedRow(index);
            }
            updateTableSelection();
        }
    }

    private void processKeyUpAndDownEvent(TablePredicateFactory.Predicate tablePredicate) {
        int currentIndex = tableModel.getCurrentIndex();
        Row currentRow = tableModel.getRow(currentIndex);
        if (currentIndex == 0 && tablePredicate.moveType() == TablePredicateFactory.MoveType.UP_LEFT) {
            tableModel.getRow(currentIndex).setHighlighted(false);
            tableModel.setCurrentIndex(-1);
            headerSelectedCellIndex = 0;
            changeFocus(FocusType.HEADER);
            header.getCellFormatter().addStyleName(0, 0, selectionStyle.selectedHeaderCell());
        } else {
            if (currentRow.isHighlighted() && tablePredicate.indexCondition(currentIndex, tableModel.getRowCount())) {
                currentRow.setHighlighted(false);
                currentIndex = tablePredicate.nextIndex(currentIndex);
                tableModel.getRow(currentIndex).setHighlighted(true);
                tableModel.setCurrentIndex(currentIndex);
                scrollPanel.ensureVisible(table.getWidget(currentIndex, 0));
            }
        }
        updateTableSelection();
    }

    public FlexTable getHeader() {
        return header;
    }

    public FlexTable getContent() {
        return table;
    }

    public ScrollPanel getScrollPanel() {
        return scrollPanel;
    }

    public void setTableModel(TableModel m) {
        tableModel = m;
        table.setModel(tableModel);
        if (m instanceof AbstractTableModel) {
            ((AbstractTableModel) tableModel).addTableModelListener(new TableModelListener() {
                @Override
                public void tableChanged(TableModelEvent e) {
                    updateTable(e);
                }
            });
            ((AbstractTableModel) tableModel).fireTableStructureChanged();
        }
    }

    public TableModel getTableModel() {
        return tableModel;
    }

    @UiHandler("table")
    void onTableClicked(ClickEvent event) {
        removeHeaderSelection();
        // changeFocus(FocusType.BODY);
        Cell cell = table.getCellForEvent(event);

        if (cell == null) {
            return;
        }
        int cellIndex = cell.getCellIndex();
        int rowIndex = cell.getRowIndex();
        tableModel.setCurrentIndex(rowIndex);
        Row row = tableModel.getRow(rowIndex);

        if (tableModel.isMultipleSelectable() == false) {
            for (int r = 0; r < tableModel.getRowCount(); r++) {
                if (r != rowIndex) {
                    tableModel.getRow(r).setSelected(false);
                }
            }
            row.setSelected(!row.isSelected());
            updateTableSelection();
            for (int r = 0; r < tableModel.getRowCount(); r++) {
                updateTableCell(r, 0);
            }
        } else {
            if (cellIndex == 0) {
                return;
            }
            row.setSelected(!row.isSelected());
            updateTableSelection();
            updateTableCell(rowIndex, 0);
        }
    }

    @UiHandler("header")
    void onTableHeaderClicked(ClickEvent event) {
        Cell cell = header.getCellForEvent(event);
        if (cell != null) {
            onTableHeaderClicked(cell.getCellIndex(), false);
        }
    }

    private void onTableHeaderClicked(int cellIndex, boolean propagateEventIfNotSortingColumn) {
        if (cellIndex == 0 && tableModel.isMultipleSelectable() && propagateEventIfNotSortingColumn) {
            Widget widget = header.getWidget(0, 0);
            if (widget instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) widget;
                boolean correctValue = !checkBox.getValue();
                checkBox.setValue(correctValue);
                for (int row = 0; row < tableModel.getRowCount(); row++) {
                    tableModel.getRow(row).setSelected(correctValue);
                    updateTableSelection();
                }
            }
        } else {
            removeHeaderSelection();
        }
        headerSelectedCellIndex = cellIndex;
        Column col = tableModel.getColumn(cellIndex);
        tableModel.sort(col);
    }

    private void onTableClicked(int row, String columnId, TableCellWidget cellWidget) {
        onTableCellChanged(row, columnId, cellWidget);
    }

    private void onTableCellChanged(int rowIndex, String columnId, TableCellWidget cellWidget) {
        Row row = tableModel.getRow(rowIndex);
        if ("RowHeader".equals(columnId)) {
            row.setSelected(!row.isSelected());
            updateTableSelection();
        }
        row.setCellData(columnId, cellWidget.getCellEditorValue());
    }

    private void updateTableSelection() {
        int count = tableModel.getRowCount();
        String attrName = BrowserUtils.getClassAttr();
        for (int i = 0; i < count; i++) {
            Element tr = table.getRowFormatter().getElement(i);
            if (tableModel.getRow(i).isSelected()) {
                tr.setAttribute(attrName, "table-row-selected");
            } else {
                tr.setAttribute(attrName, "table-row");
            }
            if (tableModel.getRow(i).isHighlighted()) {
                tr.setAttribute(attrName, "table-row-hover");
            }
            if (tableModel.isMultipleSelectable()) {
                updateTableCell(i, 0);
            }
        }
        SelectionChangeEvent.fire(this);
    }

    private void changeFocus(FocusType focusType) {
        this.focusType = focusType;
        if (focusType == FocusType.HEADER) {
            headerFocusPanel.setFocus(true);
        } else if (focusType == FocusType.BODY) {
            focusPanel.setFocus(true);
        }
    }

    public void updateTable(TableModelEvent event) {
        if (event.getType() == TableModelEvent.TableStructure) {
            updateTableStructure();
            updateTableData();
        } else if (event.getType() == TableModelEvent.TableData) {
            updateTableData();
        } else if (event.getType() == TableModelEvent.CellUpdate) {
            updateTableCell(event.getFirstRow(), event.getColumn());
        }
    }

    private void updateTableData() {
        for (int r = 0; r < tableModel.getRowCount(); r++) {
            int columnCount = tableModel.getColumnCount();
            for (int c = 0; c < columnCount; c++) {
                updateTableCell(r, c);
            }
        }
        updateTableSelection();
    }

    private void updateTableCell(final int r, final int c) {
        int columnCount = tableModel.getColumnCount();
        for (int i = 0; i < columnCount - 1; i++) {
            Column col = tableModel.getColumn(i);
            table.getColumnFormatter().setWidth(i, col.getWidth());
        }
        final String columnId = tableModel.getColumn(c).getId();
        Row row = tableModel.getRow(r);
        Object v = null;
        if ("RowHeader".equals(columnId)) {
            v = row.isSelected();
        } else {
            v = row.getCellData(columnId);
        }
        if ("RowHeader".equals(columnId) == false) {
            if (v != null) {
                table.setWidget(r, c, new Label(v.toString()));
            } else {
                table.setHTML(r, c, "&nbsp;");
            }
            return;
        }
        final TableCellWidget widget = new TableCellWidget(v);
        final StringBuilder debugId = new StringBuilder();
        if ("RowHeader".equals(columnId)) {
            //Setup debug id
            //Skip the first 'row selection' column
            for (int i = 1; i < columnCount; i++) {
                Column column = tableModel.getColumn(i);
                Object value = row.getCellData(column.getId());
                debugId.append(value);
                if (i != columnCount - 1) {
                    debugId.append("-");
                }
            }
            widget.getDefaultTableEditor().ensureDebugId(DebugIdUtils.createWebDriverSafeDebugId(debugId.toString()));
        }
        widget.setCellEditorValue(v);
        if (widget instanceof HasClickHandlers) {
            ((HasClickHandlers) widget).addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    onTableClicked(r, columnId, widget);
                }
            });
        }
        if (widget instanceof HasChangeHandlers) {
            ((HasChangeHandlers) widget).addChangeHandler(new ChangeHandler() {
                @Override
                public void onChange(ChangeEvent event) {
                    onTableCellChanged(r, columnId, widget);

                }
            });
        }
        table.setWidget(r, c, widget);
    }

    private void updateTableStructure() {
        int columnCount = tableModel.getColumnCount();
        for (int i = 0; i < columnCount; i++) {
            Column col = tableModel.getColumn(i);
            header.setWidget(0, i, col.getColumnTitleWidget());
        }
        for (int i = 0; i < columnCount - 1; i++) {
            Column col = tableModel.getColumn(i);
            header.getColumnFormatter().setWidth(i, col.getWidth());
        }
        for (int i = 0; i < columnCount; i++) {
            Column col = tableModel.getColumn(i);

            header.getCellFormatter().removeStyleName(0, i, selectionStyle.columnAscending());
            header.getCellFormatter().removeStyleName(0, i, selectionStyle.columnDescending());

            if (col.getSortDirection() == Column.Ascending) {
                header.getCellFormatter().addStyleName(0, i, selectionStyle.columnAscending());
            } else if (col.getSortDirection() == Column.Descending) {
                header.getCellFormatter().addStyleName(0, i, selectionStyle.columnDescending());
            }
        }
    }

    @Override
    public HandlerRegistration addRetrieveAdditionalDataHandler(final RetrieveAdditionalDataHandler handler) {
        retrieveDataHandlers.add(handler);
        HandlerRegistration result = new HandlerRegistration() {
            @Override
            public void removeHandler() {
                retrieveDataHandlers.remove(handler);
            }
        };
        return result;
    }

    public void displayLoading(boolean isLoading) {
        changeFocus(FocusType.BODY);
        final int x = scrollPanel.getAbsoluteLeft() + scrollPanel.getOffsetWidth();
        final int y = scrollPanel.getAbsoluteTop() + scrollPanel.getOffsetHeight();
        if (isLoading) {
            loading.setPopupPositionAndShow(new PositionCallback() {

                @Override
                public void setPosition(int offsetWidth, int offsetHeight) {
                    loading.setPopupPosition(x - offsetWidth, y + 1);
                }
            });
        } else {
            loading.hide();
        }
    }

    /**
     * @see org.kuali.student.common.ui.client.widgets.list.HasSelectionChangeHandlers#addSelectionChangeHandler(org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler)
     */
    @Override
    public HandlerRegistration addSelectionChangeHandler(SelectionChangeHandler handler) {
        return addHandler(handler, SelectionChangeEvent.getType());
    }
}