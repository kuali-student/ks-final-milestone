/*
 * Copyright 2009 Johnson Consulting Services
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.HasBlurHandlers;
import com.google.gwt.event.dom.client.HasFocusHandlers;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Accessibility;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.HTMLTable.CellFormatter;
import com.google.gwt.user.client.ui.HTMLTable.ColumnFormatter;
import com.google.gwt.user.client.ui.HTMLTable.RowFormatter;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.common.ScrollBar;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.common.events.BusyStateChangeEvent;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.common.events.BusyStateChangeHandler;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.common.events.ScrollEvent;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.common.events.ScrollHandler;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.common.events.BusyStateChangeEvent.BusyState;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.ActionDefinition;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.ActionsPopup;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.ColumnDefinition;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.DynamicTable;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.MultiItemSelectColumnDefinition;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.Row;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.Selection;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.SortInfo;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.SortInfoStack;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.TableCell;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.TableDefinition;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.TableException;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.TableModel;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.DynamicTable.Styles;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.SortInfo.Direction;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.TableDefinition.SelectionMode;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.actions.ActionHandler;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.events.ModelChangeEvent;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.events.ModelChangeHandler;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.events.RowFocusEvent;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.themes.Theme;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.util.A11y;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.util.Callback;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.util.Elements;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.util.WorkQueue;

/**
 * Default implementation class for DynamicTable
 * Other than the initialize method, definitions for all other public methods are defined in the documentation for DynamicTable
 * @author wilj
 *
 * @param <T> the generic type with which the table structure is associated
 */
// TODO figure out why in IE6 and Opera, the scrollbar's container div is too large on the first load,and corrects itself afterward
public class DynamicTableImplDefault<T> extends Composite implements Focusable, HasFocusHandlers, HasBlurHandlers {
	protected class BodyCellImpl implements TableCell {
		private final CellFormatter cf;
		private int row;
		private int col;

		private BodyCellImpl() {
			this.cf = grid.getCellFormatter();
		}

		/* (non-Javadoc)
		 * @see org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.TableCell#getWidget()
		 */
		@Override
		public Widget getWidget() {
			return grid.getWidget(row, col);
		}

		@Override
		public void setElement(final com.google.gwt.dom.client.Element element) {
			final com.google.gwt.dom.client.Element e = cf.getElement(row, col);
			final NodeList<Node> children = e.getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				e.removeChild(children.getItem(i));
			}
			if (element != null) {
				e.appendChild(element);
			}
		}

		@Override
		public void setInnerHtml(final String html) {
			if (html == null) {
				cf.getElement(row, col).setInnerHTML("");
			} else {
				cf.getElement(row, col).setInnerHTML(html);
			}

		}

		@Override
		public void setText(final String text) {
			if (text == null) {
				grid.clearCell(row, col);
			} else {
				grid.setText(row, col, text);
			}
		}

		@Override
		public void setWidget(final Widget widget) {
			if (widget == null) {
				grid.clearCell(row, col);
			} else {
				grid.setWidget(row, col, widget);
			}
		}
	}

	protected class ColumnHeaderCellImpl implements TableCell {
		private ColumnHeaderWidget header;

		private ColumnHeaderCellImpl() {

		}

		/* (non-Javadoc)
		 * @see org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.TableCell#getWidget()
		 */
		@Override
		public Widget getWidget() {
			// nothing to return here
			return null;
		}

		@Override
		public void setElement(final com.google.gwt.dom.client.Element element) {
			final SimplePanel sp = new SimplePanel();
			sp.getElement().appendChild(element);
			header = new ColumnHeaderWidget(sp);
		}

		@Override
		public void setInnerHtml(final String html) {
			header = new ColumnHeaderWidget(new HTML(html));
		}

		@Override
		public void setText(final String text) {
			header = new ColumnHeaderWidget(new Label(text));
		}

		@Override
		public void setWidget(final Widget widget) {
			header = new ColumnHeaderWidget(widget);
		}
	}

	private class ColumnHeaderWidget extends Composite {
		private final HorizontalPanel panel = new HorizontalPanel();
		private final FlowPanel iconPanel = new FlowPanel();
		private final Image arrowDown = new Image();//Theme.INSTANCE.getTableImages().getSortArrowDown();
		private final Image arrowUp = new Image();//Theme.INSTANCE.getTableImages().getSortArrowUp();
		private final Image arrowUnsorted = new Image();//Theme.INSTANCE.getTableImages().getSortArrowUnsorted();

		private final Widget widget;

		public ColumnHeaderWidget(final Widget header) {
			panel.add(header);
			panel.add(iconPanel);
			iconPanel.add(arrowDown);
			iconPanel.add(arrowUp);
			iconPanel.add(arrowUnsorted);
			arrowDown.setVisible(false);
			arrowUp.setVisible(false);
			arrowUnsorted.setVisible(false);
			super.initWidget(panel);
			this.widget = header;
		}

		@Override
		public Widget getWidget() {
			return widget;
		}

		public void onSort(final Direction direction) {
			arrowDown.setVisible(direction == Direction.DESCENDING);
			arrowUp.setVisible(direction == Direction.ASCENDING);
			arrowUnsorted.setVisible(direction == Direction.UNSORTED);
		}
	}

	private class RedrawWorkItem extends WorkQueue.WorkItem {
		private final Callback<T> redrawCompleteCallback;
		private final int start;
		private final int count;

		public RedrawWorkItem(final int start, final int count, final Callback<T> redrawCompleteCallback) {
			this.start = start;
			this.count = count;
			this.redrawCompleteCallback = redrawCompleteCallback;
		}

		/* (non-Javadoc)
		 * @see org.kuali.student.lum.lu.ui.course.client.widgets.a.util.WorkQueue.WorkItem#exec(org.kuali.student.lum.lu.ui.course.client.widgets.a.util.Callback)
		 */
		@Override
		public void exec(final Callback<Boolean> onCompleteCallback) {
			model.requestRows(start, count, new AsyncCallback<List<Row<T>>>() {
				@Override
				public void onFailure(final Throwable e) {
					onCompleteCallback.exec(true);
					if (e instanceof TableException) {
						throw (TableException) e;
					} else {
						throw new TableException(e);
					}
				}

				@Override
				public void onSuccess(final List<Row<T>> result) {
					try {
						if (!RedrawWorkItem.this.isCanceled()) {
							drawRows(result, redrawCompleteCallback);
						} else {
							redrawCompleteCallback.exec(null);
						}
					} finally {
						onCompleteCallback.exec(true);
					}
				}
			});

		}

	}

	public static final int REDRAW_INTERVAL = 100;
	public static final int SELECTION_TOGGLE_KEYCODE = 32;
	protected final HorizontalPanel container = new HorizontalPanel();
	protected final SimplePanel gridPanel = new SimplePanel();
	protected final FlowPanel scrollPanel = new FlowPanel();
	protected FlexTable grid;
	protected ScrollBar scroll;
	protected TableDefinition<T> definition;
	protected TableModel<T> model;
	protected int topRow = 0;
	protected int focusedRow = -1;
	protected int rowCount = 0;
	protected FlowPanel topImagePanel = new FlowPanel();
	protected FlowPanel bottomImagePanel = new FlowPanel();
	protected Image optionsTop;
	protected Image optionsBottom;
	protected Image spinnerTop;
	protected Image spinnerBottom;

	protected int pendingRequestCounter = 0;

	protected ColumnDefinition<T>[] columns;

	protected int columnCount = 0;

	protected ColumnHeaderCellImpl headerCell;
	protected BodyCellImpl bodyCell;
	// default busy to true, to prevent early clicks by the user, will be automatically set to false once initial load is complete
	protected boolean busy = true;
	protected SortInfoStack sortInfo = new SortInfoStack();
	protected DynamicTable<T> parentTable;

	protected int definedVisibleRows;
	protected final TableRange allTableRange = new TableRange();
	protected final TableRange headerRange = new TableRange();
	protected final TableRange bodyRange = new TableRange();
	protected final TableRange disabledBodyRange = new TableRange();
	protected final TableRange footerRange = new TableRange();
	protected final TableRange columnHeaderRange = new TableRange();
	protected final TableRange columnFooterRange = new TableRange();

	protected final TableRange noRecordsIndicatorRange = new TableRange();

	protected List<Row<T>> currentRows = null;

	protected final Callback<T> redrawAndFocusCallback = new Callback<T>() {
		@Override
		public void exec(final T value) {
			if (value != null) {
				fireRowFocusEvent(value);
			}
		}
	};

	private final WorkQueue modelWorkQueue = new WorkQueue();

	public DynamicTableImplDefault() {
		super.initWidget(container);

	}

	@Override
	public HandlerRegistration addBlurHandler(final BlurHandler handler) {
		return addDomHandler(handler, BlurEvent.getType());
	}

	@Override
	public HandlerRegistration addFocusHandler(final FocusHandler handler) {
		return addDomHandler(handler, FocusEvent.getType());
	}

	protected ActionsPopup<T> buildActionsPopup() {
		final LinkedHashMap<String, ActionHandler<T>> handlers = new LinkedHashMap<String, ActionHandler<T>>();
		for (final ActionDefinition<T> def : definition.getActions()) {
			handlers.put(def.getName(), def.getHandler());
		}
		if (handlers.isEmpty()) {
			return null;
		} else {
			return new ActionsPopup<T>(parentTable, handlers);
		}
	}

	protected void buildTable() {
		grid.setStyleName(Styles.TABLE.getStyle());
		drawHeaders();

		final CellFormatter cf = grid.getCellFormatter();
		for (int r = bodyRange.getStartRow(); r < bodyRange.getRowCount(); r++) {
			for (int c = bodyRange.getStartColumn(); c < bodyRange.getIteratorEndColumn(); c++) {
				cf.addStyleName(r, c, Styles.CELL.getStyle());
			}
		}
		initAccessibility();

		// add "no records found" cell
		setNoRecordsIndicatorText("", false);
		// TODO fix this, now that we don't have colspan to stretch it across (absolute position a div over it?)
		grid.getFlexCellFormatter().setColSpan(noRecordsIndicatorRange.getStartRow(),
				noRecordsIndicatorRange.getStartColumn(), noRecordsIndicatorRange
				.getColumnCount());
		cf.setHorizontalAlignment(noRecordsIndicatorRange.getStartRow(), noRecordsIndicatorRange.getStartColumn(),
				HasHorizontalAlignment.ALIGN_CENTER);
		cf.setStyleName(noRecordsIndicatorRange.getStartRow(), noRecordsIndicatorRange.getStartColumn(),
				Styles.NO_RECORDS_CELL.getStyle());

		grid.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				if (!busy) {
					final EventTarget target = event.getNativeEvent().getEventTarget();
					if (com.google.gwt.dom.client.Element.is(target)) {
						final Cell cell = grid.getCellForEvent(event);
						if (cell != null) {
							// TODO change this to cell.getElement once Google fixes issue #3757
							final Element cellElement = grid.getCellFormatter().getElement(cell.getRowIndex(),
									cell.getCellIndex());
							if ((definition.isShowHeader() && columnHeaderRange.contains(cell.getRowIndex(), cell
									.getCellIndex()))
									|| (definition.isShowFooter() && columnFooterRange.contains(cell.getRowIndex(),
									cell.getCellIndex()))) {
								final ColumnDefinition<T> col = columns[cell.getCellIndex()];
								if (col.isSortable()) {
									sortInfo.append(col.getName());
									model.setSortInfo(sortInfo);
									updateSortIndicators();
								}
							} else if (bodyRange.contains(cell.getRowIndex(), cell.getCellIndex())) {
								setFocusedRow(topRow + cell.getRowIndex() - 1);
							}

						}
					}
				}
			}
		});
	}

	private void calculateTableRanges(final int visibleRows) {
		int totalRows = definedVisibleRows;
		if (definition.isShowHeader()) {
			totalRows++;
		}
		if (definition.isShowFooter()) {
			totalRows++;
		}
		allTableRange.reset(0, 0, totalRows + 1, columnCount);

		if (definition.isShowHeader()) {
			headerRange.reset(0, 0, 1, allTableRange.getColumnCount());
			columnHeaderRange.reset(0, 0, 1, columnCount);

		} else {
			headerRange.reset();
			columnHeaderRange.reset();
		}
		bodyRange.reset(headerRange.getRowCount(), 0, visibleRows, columnCount);
		disabledBodyRange.reset(bodyRange.getIteratorEndRow(), 0, definedVisibleRows - visibleRows, columnCount);
		noRecordsIndicatorRange.reset(disabledBodyRange.getIteratorEndRow(), 0, 1, columnCount);

		if (definition.isShowFooter()) {
			footerRange.reset(noRecordsIndicatorRange.getIteratorEndRow(), 0, 1, allTableRange.getColumnCount());
			columnFooterRange.reset(footerRange.getStartRow(), 0, 1, columnCount);
		} else {
			footerRange.reset();
			columnFooterRange.reset();
		}

	}

	private void drawHeaders() {
		final CellFormatter f = grid.getCellFormatter();
		final ColumnFormatter cf = grid.getColumnFormatter();

		TableRange range = null;
		if (definition.isShowHeader()) {
			range = columnHeaderRange;
		} else if (definition.isShowFooter()) {
			range = columnFooterRange;
		}

		if (range != null) {
			for (int i = range.getStartColumn(); i < range.getIteratorEndColumn(); i++) {
				final ColumnDefinition<T> col = columns[i - range.getStartColumn()];
				if (definition.isShowHeader()) {
					final Widget w = getColumnHeaderWidget(col);
					if (w != null) {
						grid.setWidget(columnHeaderRange.getStartRow(), i, w);
					}
				}
				if (definition.isShowFooter()) {
					final Widget w = getColumnHeaderWidget(col);
					if (w != null) {
						grid.setWidget(columnFooterRange.getStartRow(), i, w);
					}
				}
				cf.addStyleName(i, col.getName());
			}
		}

		if (definition.isShowHeader()) {
			range = headerRange;
		} else if (definition.isShowFooter()) {
			range = footerRange;
		} else {
			range = null;
		}

		if (range != null) {
			for (int i = range.getStartColumn(); i < range.getIteratorEndColumn(); i++) {
				if (definition.isShowHeader()) {
					f.addStyleName(headerRange.getStartRow(), i, Styles.HEADER_FOOTER.getStyle());
				}
				if (definition.isShowFooter()) {
					f.addStyleName(footerRange.getStartRow(), i, Styles.HEADER_FOOTER.getStyle());
				}
			}
		}
		updateSortIndicators();
	}

	private void drawRows(final List<Row<T>> result, final Callback<T> redrawCompleteCallback) {
		if (!result.isEmpty() && result.get(0).getIndex() != topRow) {
			// older request, ignore the results
			return;
		} else if (rowCount == 0) {
			showNoRecordsIndicator();
			return;
		}
		currentRows = result;

		setNoRecordsIndicatorText("", false);

		calculateTableRanges(Math.min(result.size(), definedVisibleRows));

		T focusedValue = null;

		final Selection<T> sel = model.getSelection();
		final RowFormatter rf = grid.getRowFormatter();
		final CellFormatter cf = grid.getCellFormatter();
		for (int rowIndex = 0; rowIndex < bodyRange.getRowCount(); rowIndex++) {
			final int rowPosition = rowIndex + headerRange.getRowCount();
			T value = null;
			final int absoluteRow = rowIndex + topRow;
			if (absoluteRow < rowCount) {
				value = result.get(rowIndex).getValue();
				if (absoluteRow == focusedRow) {
					focusedValue = value;
				}
				rf.getElement(rowPosition).setAttribute("modelId", model.getUniqueIdentifier(value));
				for (int col = bodyRange.getStartColumn(); col < bodyRange.getIteratorEndColumn(); col++) {
					bodyCell.row = rowPosition;
					bodyCell.col = col;

					columns[col - bodyRange.getStartColumn()].getRenderer()
							.renderCell(parentTable, bodyCell, value);
				}
				if (absoluteRow == focusedRow) {
					rf.addStyleName(rowPosition, Styles.ROW_FOCUSED.getStyle());
				} else {
					rf.removeStyleName(rowPosition, Styles.ROW_FOCUSED.getStyle());
				}
				rf.setVisible(rowPosition, true);
			} else {
				throw new TableException("Attempted to render row in non-visible area. rowIndex: " + rowIndex
						+ ", absoluteRow: " + absoluteRow);
			}

			// TODO the intermingled A11Y code below is guaranteed to
			// not work quite correctly when single-row select is
			// implemented
			final Element rowHeader = cf.getElement(rowPosition, 0);
			A11y.removeContextualText(rowHeader);
			// TODO externalize a11y strings
			if (value != null && sel.isSelected(model.getUniqueIdentifier(value))) {
				rf.setStyleName(rowPosition, Styles.ROW_SELECTED.getStyle());
				A11y.addContextualText(rowHeader, "selected");
			} else {
				rf.setStyleName(rowPosition, absoluteRow % 2 == 0 ? Styles.ROW.getStyle() : Styles.ROW_ALT.getStyle());
			}
			if (absoluteRow == focusedRow) {
				rf.addStyleName(rowPosition, Styles.ROW_FOCUSED.getStyle());
				Accessibility.setState(getElement(), Accessibility.STATE_ACTIVEDESCENDANT, "");
				DeferredCommand.addCommand(new Command() {
					@Override
					public void execute() {
						Accessibility.setState(getElement(), Accessibility.STATE_ACTIVEDESCENDANT, rf.getElement(
								rowPosition).getId());
					}
				});

			}
			A11y.addContextualText(rowHeader, "Row " + (absoluteRow + 1) + " of " + rowCount, 2, 2);

		}
		for (int rowIndex = disabledBodyRange.getStartRow(); rowIndex < disabledBodyRange.getIteratorEndRow(); rowIndex++) {
			for (int i = bodyRange.getStartColumn(); i < bodyRange.getIteratorEndColumn(); i++) {
				grid.clearCell(rowIndex, i);
			}
			rf.setVisible(rowIndex, false);
		}
		for (int i = 0; i < columnCount; i++) {
			final ColumnDefinition<T> col = columns[i];
			if (definition.isShowHeader()) {
				final ColumnHeaderWidget w = (ColumnHeaderWidget) grid.getWidget(columnHeaderRange.getStartRow(),
						columnHeaderRange.getStartColumn() + i);
				if (w != null) {
					col.getRenderer().onRedraw(parentTable, w.getWidget());
				}
			}
			if (definition.isShowFooter()) {
				final ColumnHeaderWidget w = (ColumnHeaderWidget) grid.getWidget(columnFooterRange.getStartRow(),
						columnFooterRange.getStartColumn() + i);
				if (w != null) {
					col.getRenderer().onRedraw(parentTable, w.getWidget());
				}
			}
		}
		if (redrawCompleteCallback != null) {
			redrawCompleteCallback.exec(focusedValue);
		}
		this.initScrollBar();
	}

	private Element findParent(final Element current, final String tagName, final Element limit) {
		Element result = null;
		Element e = current;
		while (e != null && !e.equals(limit)) {
			if (e.getTagName().equalsIgnoreCase(tagName)) {
				result = e;
				break;
			} else {
				e = e.getParentElement().cast();
			}
		}
		return result;

	}

	private void fireRowFocusEvent(final T value) {
		parentTable.fireEvent(new RowFocusEvent<T>(value));
	}

	private final native void focus(Element e) /*-{
												e.focus();
												}-*/;

	private Widget getColumnHeaderWidget(final ColumnDefinition<T> col) {
		headerCell.header = null;
		col.getRenderer().renderHeader(parentTable, headerCell);
		return headerCell.header;
	}

	protected int getRelativeMouseWheelVelocity(final int velocity) {
		return velocity;
	}

	private int getRelativeRow(final int row) {
		return (row - topRow) + 1;
	}

	@Override
	public int getTabIndex() {
		String s = getElement().getAttribute("tabIndex");
		s = (s == null) ? "" : s.trim();
		if (!s.equals("")) {
			return Integer.valueOf(s);
		}
		return 0;
	}

	protected void handleDoubleClick(final NativePreviewEvent event) {
		// hack to work around getCellForEvent only accepting ClickEvent
		final Element e = event.getNativeEvent().getEventTarget().cast();
		if (scroll != null && !scroll.getElement().isOrHasChild(e)) {
			final Element tr = findParent(e, "tr", grid.getElement());
			if (tr != null) {
				final int r = DOM.getChildIndex((Element) tr.getParentElement(), tr);
				if (bodyRange.contains(r, bodyRange.getStartColumn())) {
					DeferredCommand.addCommand(new Command() {
						@Override
						public void execute() {
							toggleSelected(focusedRow);
						}
					});
				}
			}
		}
	}

	protected boolean handleKeyDown(final int keyCode) {
		boolean result = false;
		switch (keyCode) {
			case KeyCodes.KEY_DOWN:
				if (focusedRow < (rowCount - 1)) {
					setFocusedRow(focusedRow + 1);
				}
				result = true;
				break;
			case KeyCodes.KEY_UP:
				if (focusedRow > 0) {
					setFocusedRow(focusedRow - 1);
				}
				result = true;
				break;
			case KeyCodes.KEY_PAGEDOWN:
				if (focusedRow < ((rowCount - definedVisibleRows) - 1)) {
					setFocusedRow(focusedRow + definedVisibleRows);
				} else {
					setFocusedRow(rowCount - 1);
				}
				result = true;
				break;
			case KeyCodes.KEY_PAGEUP:
				if (focusedRow > definedVisibleRows) {
					setFocusedRow(focusedRow - definedVisibleRows);
				} else {
					setFocusedRow(0);
				}
				result = true;
				break;
			case KeyCodes.KEY_HOME:
				setFocusedRow(0);
				result = true;
				break;
			case KeyCodes.KEY_END:
				setFocusedRow(rowCount - 1);
				result = true;
				break;
			case SELECTION_TOGGLE_KEYCODE:
				if (focusedRow != -1) {
					toggleSelected(focusedRow);
				}
				result = true;
				break;
		}
		return result;
	}

	protected boolean handleKeyUp(final int keyCode) {
		// nothing to do here by default, but Opera impl needs to override it
		return false;
	}

	protected void handleMouseWheel(final NativePreviewEvent event) {
		// TODO fix mouse wheel scroll bug where the scrolling doesnt fire in webkit if the mouse is over text
		if (scroll != null) {
			int row = topRow
					+ getRelativeMouseWheelVelocity(event.getNativeEvent().getMouseWheelVelocityY());
			row = Math.min(rowCount - definedVisibleRows, row);
			row = Math.max(0, row);
			scroll.setValue(row, true);
		}
	}

	private void initAccessibility() {
		setTabIndex(0);
		Accessibility.setRole(getElement(), "grid");
		final CellFormatter fcf = grid.getCellFormatter();
		final RowFormatter rf = grid.getRowFormatter();

		for (int i = 0; i < grid.getRowCount(); i++) {
			Accessibility.setRole(rf.getElement(i), "row");
			rf.getElement(i).setId(HTMLPanel.createUniqueId());
		}

		// TODO maybe uncomment this, if removing it causes problems
		// ensure that grid cells exist
		//		for (int row = bodyRange.getStartRow(); row < bodyRange.getIteratorEndRow(); row++) {
		//			for (int col = bodyRange.getStartColumn(); col < bodyRange.getIteratorEndColumn(); col++) {
		//				grid.setText(row, col, "");
		//				final Element cellElement = fcf.getElement(row, col);
		//				cellElement.setId(HTMLPanel.createUniqueId());
		//				cellElement.setAttribute("headers", fcf.getElement(0, col).getId());
		//				Accessibility.setRole(fcf.getElement(row, col), "gridcell");
		//
		//			}
		//		}

		TableRange range = null;
		if (definition.isShowHeader()) {
			range = columnHeaderRange;
		} else if (definition.isShowFooter()) {
			range = columnFooterRange;
		}
		if (range != null) {
			for (int i = range.getStartColumn(); i < range.getIteratorEndColumn(); i++) {
				if (definition.isShowHeader()) {
					Accessibility.setRole(fcf.getElement(columnHeaderRange.getStartRow(), i), "columnheader");
				}
				if (definition.isShowFooter()) {
					Accessibility.setRole(fcf.getElement(columnFooterRange.getStartRow(), i), "columnheader");
				}
			}
		}
		this.addFocusHandler(new FocusHandler() {
			@Override
			public void onFocus(final FocusEvent event) {
				if (focusedRow == -1) {
					setFocusedRow(topRow);
				}
			}
		});

	}

	private void initEventPreview() {
		Event.addNativePreviewHandler(new Event.NativePreviewHandler() {
			@Override
			public void onPreviewNativeEvent(final NativePreviewEvent event) {
				final int type = event.getTypeInt();
				if (type != Event.ONMOUSEWHEEL && type != Event.ONDBLCLICK && type != Event.ONKEYDOWN
						&& type != Event.ONKEYUP) {
					return;
				}
				if (!busy
						&& com.google.gwt.dom.client.Element.is(event.getNativeEvent().getEventTarget())
						&& DynamicTableImplDefault.this.getElement().isOrHasChild(
						com.google.gwt.dom.client.Element.as(event.getNativeEvent().getEventTarget()))) {
					if (type == Event.ONMOUSEWHEEL) {
						handleMouseWheel(event);
						event.getNativeEvent().preventDefault();
					} else if (type == Event.ONDBLCLICK) {
						handleDoubleClick(event);
						event.getNativeEvent().preventDefault();
					} else if (type == Event.ONKEYDOWN) {
						if (handleKeyDown(event.getNativeEvent().getKeyCode())) {
							event.getNativeEvent().preventDefault();
						}
					} else if (type == Event.ONKEYUP) {
						if (handleKeyUp(event.getNativeEvent().getKeyCode())) {
							event.getNativeEvent().preventDefault();
						}
					}

				}
			}
		});
	}

	/**
	 * Initializes the table
	 * @param visibleRows the maximum number of visible data rows
	 * @param parentTable the DynamicTable that is wrapping this implementation
	 */
	public void initialize(final int visibleRows, final DynamicTable<T> parentTable) {
		this.definedVisibleRows = visibleRows;
		this.parentTable = parentTable;
		this.definition = parentTable.getDefinition();
		this.model = parentTable.getModel();

		container.setStyleName(definition.getTableName());
		container.addStyleName(Styles.TABLE_CONTAINER.getStyle());
		model.addModelChangeHandler(new ModelChangeHandler() {
			@Override
			public void onRowCountChange(final ModelChangeEvent event) {
				DeferredCommand.addCommand(new Command() {
					@Override
					public void execute() {
						modelWorkQueue.clear();
						rowCount = event.getRowCount();
						topRow = 0;
						focusedRow = -1;
						if (scroll != null) {
							scroll.setValue(0, false);
						}
						requestDrawRows();
						if (scroll != null) {
							scroll.redraw(rowCount - definedVisibleRows, definedVisibleRows);
						}
					}
				});

			}
		});
		model.addBusyStateChangeHandler(new BusyStateChangeHandler() {

			@Override
			public void onStateChange(final BusyStateChangeEvent event) {
				busy = event.getState() == BusyState.BUSY;
				if (definition.isShowHeader()) {
					spinnerTop.setVisible(busy);
					optionsTop.setVisible(!busy);
				}
				if (definition.isShowFooter()) {
					spinnerBottom.setVisible(busy);
					optionsBottom.setVisible(!busy);
				}
			}

		});
		model.addSelectionHandler(new SelectionHandler<Selection<T>>() {
			@Override
			public void onSelection(final SelectionEvent<Selection<T>> event) {
				if (!modelWorkQueue.isRunning()) {
					requestDrawRows();
				}
			}
		});
		initEventPreview();
		redrawAll();
	}

	protected void initScrollBar() {
		boolean firstInit = false;
		if (scroll == null) {
			firstInit = true;
			if (definition.isShowHeader()) {
				topImagePanel.setStyleName(Styles.INDICATOR_IMAGE_PANEL.getStyle());
				scrollPanel.add(topImagePanel);
			}

			scroll = new ScrollBar(rowCount - definedVisibleRows, definedVisibleRows);
			scrollPanel.setStyleName(Styles.SCROLLBAR_CELL.getStyle());
			scroll.addValueChangeHandler(new ValueChangeHandler<Integer>() {
				@Override
				public void onValueChange(final ValueChangeEvent<Integer> event) {
					final int row = event.getValue();
					if (row != topRow) {
						topRow = row;
						requestDrawRows();
					}
				}
			});
			scroll.addScrollHandler(new ScrollHandler() {

				@Override
				public void onScrollStart(final ScrollEvent event) {
					Elements.setTextSelectable(container.getElement(), false);
				}

				@Override
				public void onScrollStop(final ScrollEvent event) {
					Elements.setTextSelectable(container.getElement(), true);
				}

			});
			scroll.setVisible(rowCount > 0);
			scrollPanel.add(scroll);

			if (definition.isShowFooter()) {
				bottomImagePanel.setStyleName(Styles.INDICATOR_IMAGE_PANEL.getStyle());
				scrollPanel.add(bottomImagePanel);
			}

		}

		scroll.setVisible(rowCount > 0);

		DeferredCommand.addCommand(new Command() {
			@Override
			public void execute() {

				final int totalHeight = grid.getOffsetHeight();
				scrollPanel.setHeight(totalHeight + "px");
				int scrollHeight = totalHeight;
				if (definition.isShowHeader()) {
					scrollHeight -= topImagePanel.getOffsetHeight();
				}
				if (definition.isShowFooter()) {
					scrollHeight -= bottomImagePanel.getOffsetHeight();
				}
				scrollHeight = Math.max(scrollHeight, 0);
				scroll.setHeight(scrollHeight + "px");
				scroll.redraw(rowCount - definedVisibleRows, definedVisibleRows);
				DeferredCommand.addCommand(new Command() {
					@Override
					public void execute() {
						scroll.setValue(topRow, false);
					}
				});

			}
		});
		if (firstInit) {
			// poke it one more time, in case image load lag throws off the scrollbar size
			DeferredCommand.addCommand(new Command() {
				@Override
				public void execute() {
					initScrollBar();
				}
			});
		}

	}

	@Override
	protected void onLoad() {
		initScrollBar();
		model.refresh();
		// TODO redo a11y stuff
		A11y.disableTts(getElement(), true);
	}

	@SuppressWarnings("unchecked")
	public void redrawAll() {
		container.clear();
		scrollPanel.clear();

		// filter out columns that are not visible
		final List<ColumnDefinition<T>> cols = new ArrayList<ColumnDefinition<T>>();
		for (final ColumnDefinition<T> col : parentTable.getDefinition().getColumns()) {
			if (col.isVisible()) {
				cols.add(col);
			}
		}

		// since the columns collection is iterated frequently, copying to an array provides a big performance boost
		columnCount = cols.size();
		if (definition.getSelectionMode() == SelectionMode.MULTI_ITEM) {
			columnCount++;
		}
		columns = new ColumnDefinition[columnCount];
		int columnOffset = 0;
		if (definition.getSelectionMode() == SelectionMode.MULTI_ITEM) {
			columnOffset = 1;
			columns[0] = new MultiItemSelectColumnDefinition<T>();
		}
		for (int i = 0; i < cols.size(); i++) {
			columns[i + columnOffset] = cols.get(i);
		}

		calculateTableRanges(definedVisibleRows);
		grid = new FlexTable();
		for (int i = 0; i < allTableRange.getRowCount(); i++) {
			for (int j = 0; j < allTableRange.getColumnCount(); j++) {
				grid.setText(i, j, "");
			}
		}
		//		grid = new Grid(allTableRange.getRowCount(), allTableRange.getColumnCount());
		container.add(gridPanel);
		gridPanel.setWidget(grid);
		container.add(scrollPanel);

		headerCell = new ColumnHeaderCellImpl();
		bodyCell = new BodyCellImpl();

		scroll = null;

		if (definition.isShowHeader()) {
			//optionsTop = Theme.INSTANCE.getTableImages().getOptionsButton();

			optionsTop.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(final ClickEvent event) {
					if (!busy) {
						final ActionsPopup<T> pop = buildActionsPopup();
						if (pop != null && pop.hasEnabledActions()) {
							pop.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
								@Override
								public void setPosition(final int offsetWidth, final int offsetHeight) {
									final Element e = event.getNativeEvent().getEventTarget().cast();
									pop.setPopupPosition(e.getAbsoluteLeft() - offsetWidth + e.getOffsetWidth(), e
											.getAbsoluteTop());
								}
							});
						}
					}
				}
			});
//			if (topImagePanel.getWidgetCount() == 0) {
				//spinnerTop = Theme.INSTANCE.getTableImages().getBusySpinner();
				//spinnerTop.setVisible(false);
	//			topImagePanel.add(optionsTop);
		//		topImagePanel.add(spinnerTop);
			//}
		}
/*
		if (definition.isShowFooter()) {
			optionsBottom = Theme.INSTANCE.getTableImages().getOptionsButton();

			optionsBottom.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(final ClickEvent event) {
					if (!busy) {
						final ActionsPopup<T> pop = buildActionsPopup();
						if (pop != null && pop.hasEnabledActions()) {
							pop.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
								@Override
								public void setPosition(final int offsetWidth, final int offsetHeight) {
									final Element e = event.getNativeEvent().getEventTarget().cast();
									pop.setPopupPosition(e.getAbsoluteLeft() - offsetWidth + e.getOffsetWidth(), e
											.getAbsoluteTop()
											+ e.getOffsetHeight() - offsetHeight);
								}
							});
						}
					}
				}
			});
			if (bottomImagePanel.getWidgetCount() == 0) {
				spinnerBottom = Theme.INSTANCE.getTableImages().getBusySpinner();
				spinnerBottom.setVisible(false);
				bottomImagePanel.add(optionsBottom);
				bottomImagePanel.add(spinnerBottom);
			}
		}
*/
		buildTable();
		if (isAttached()) {
			model.refresh();
			//			requestDrawRows();
		}
	}

	private void requestDrawRows() {
		requestDrawRows(null);
	}

	private void requestDrawRows(final Callback<T> redrawCompleteCallback) {
		final int count = Math.min(rowCount - topRow, definedVisibleRows);
		modelWorkQueue.submit(new RedrawWorkItem(topRow, count, redrawCompleteCallback));
	}

	@Override
	public void setAccessKey(final char key) {
		// TODO handle setAccessKey
	}

	@Override
	public void setFocus(final boolean focused) {
		if (focused) {
			focus(getElement());
		}
	}

	private void setFocusedRow(final int row) {
		if (row < topRow) {
			focusedRow = row;
			topRow = row;
			if (scroll != null) {
				scroll.setValue(topRow, false);
			}
			requestDrawRows(redrawAndFocusCallback);
		} else if (row >= topRow + bodyRange.getRowCount()) {
			focusedRow = row;
			topRow = row - bodyRange.getRowCount() + 1;
			if (scroll != null) {
				scroll.setValue(topRow, false);
			}
			requestDrawRows(redrawAndFocusCallback);
		} else if (currentRows != null) {
			final RowFormatter rf = grid.getRowFormatter();
			if (focusedRow != -1 && focusedRow >= topRow && focusedRow <= topRow + bodyRange.getRowCount()) {
				rf.removeStyleName(getRelativeRow(focusedRow), Styles.ROW_FOCUSED.getStyle());
			}
			rf.addStyleName(getRelativeRow(row), Styles.ROW_FOCUSED.getStyle());
			focusedRow = row;
			final int relative = getRelativeRow(focusedRow);
			final Element rowElement = rf.getElement(relative);
			fireRowFocusEvent(currentRows.get(relative - bodyRange.getStartRow()).getValue());
			Accessibility.setState(getElement(), Accessibility.STATE_ACTIVEDESCENDANT, rowElement.getId());
		}
	}

	private void setNoRecordsIndicatorText(final String text, final boolean visible) {
		grid.setText(noRecordsIndicatorRange.getStartRow(), noRecordsIndicatorRange.getStartColumn(), text);
		grid.getRowFormatter().setVisible(noRecordsIndicatorRange.getStartRow(), visible);
	}

	@Override
	public void setTabIndex(final int index) {
		getElement().setAttribute("tabIndex", String.valueOf(index));
	}

	private void showNoRecordsIndicator() {
		for (int row = bodyRange.getStartRow(); row < bodyRange.getIteratorEndRow(); row++) {
			grid.getRowFormatter().setVisible(row, false);
		}
//		setNoRecordsIndicatorText(Theme.INSTANCE.getTableMessages().getNoRecordsFound(), true);
	}

	protected void toggleSelected(final int row) {
		final int relative = getRelativeRow(row);
		final String id = grid.getRowFormatter().getElement(relative).getAttribute("modelId");
		switch (definition.getSelectionMode()) {
			case MULTI_ITEM:
				model.getSelection().setSelected(id, !model.getSelection().isSelected(id));
				break;

			case SINGLE_ITEM:
				final boolean selected = model.getSelection().isSelected(id);
				model.getSelection().clear();
				if (!selected) {
					model.getSelection().setSelected(id, true);
				}
			case DISABLED:
				// do nothing
				break;
		}
	}

	private void updateSortIndicators() {
		TableRange range = null;
		if (definition.isShowHeader()) {
			range = columnHeaderRange;
		} else if (definition.isShowFooter()) {
			range = columnFooterRange;
		}
		if (range != null) {
			for (int i = range.getStartColumn(); i < range.getIteratorEndColumn(); i++) {
				if (columns[i - range.getStartColumn()].isSortable()) {
					final String lastKey = (sortInfo.isEmpty()) ? "" : sortInfo.getMostRecent().getKey();
					final String key = columns[i - range.getStartColumn()].getName();
					Direction d = Direction.UNSORTED;
					if (key.equals(lastKey)) {
						final SortInfo s = sortInfo.findInfo(key);
						d = (s == null) ? Direction.UNSORTED : s.getDirection();
					}
					if (definition.isShowHeader()) {
						((ColumnHeaderWidget) grid.getWidget(columnHeaderRange.getStartRow(), i)).onSort(d);
					}
					if (definition.isShowFooter()) {
						((ColumnHeaderWidget) grid.getWidget(columnFooterRange.getStartRow(), i)).onSort(d);
					}
				}
			}
		}
	}

}
