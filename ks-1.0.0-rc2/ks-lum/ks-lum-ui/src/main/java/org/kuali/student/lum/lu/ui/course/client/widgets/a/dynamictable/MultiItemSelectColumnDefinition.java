/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

package org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Widget;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.common.events.BusyStateChangeEvent;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.common.events.BusyStateChangeHandler;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.common.events.BusyStateChangeEvent.BusyState;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.Selection.SelectionType;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.actions.ExportAction;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.actions.ShowSettingsAction;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.events.ModelChangeEvent;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.events.ModelChangeHandler;

// TODO handle the ignoring of input when table is busy, in a better way than propagating the checks everywhere
/**
 * TableDecorator implementing multi-select capabilities for the table
 * @author wilj
 *
 * @param <T> the generic type with which the table structure is associated
 */
public class MultiItemSelectColumnDefinition<T> extends StaticColumnDefinition<T> {
	private static class CheckBoxSelector<T> extends CheckBox {
		private T currentRow;
		private final TableModel<T> model;

		public CheckBoxSelector(final TableModel<T> model) {
			super.setTabIndex(-1);
			this.model = model;
			this.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(final ClickEvent event) {
					final String id = model.getUniqueIdentifier(currentRow);
					final boolean selected = !model.getSelection().isSelected(id);
					model.getSelection().setSelected(id, selected);
				}
			});
		}

		public void setCurrentRow(final T currentRow) {
			this.currentRow = currentRow;
			this.setValue(model.getSelection().isSelected(model.getUniqueIdentifier(currentRow)));
		}
	}

	private int lastRowCount = 0;

	private boolean busy = true;

	public MultiItemSelectColumnDefinition() {
		super("selectionIndicator");
		super.addAttribute(DECORATOR_COLUMN);
		super.addAttribute(ExportAction.NON_EXPORTABLE);
		super.addAttribute(ShowSettingsAction.PINNED);
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.StaticColumnDefinition#getCellWidget(org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.DynamicTable)
	 */
	@Override
	public Widget getCellWidget(final DynamicTable<T> table) {
		return new CheckBoxSelector<T>(table.getModel());
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.StaticColumnDefinition#getHeaderFooterWidget(org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.DynamicTable)
	 */
	@Override
	public Widget getHeaderFooterWidget(final DynamicTable<T> table) {
		final CheckBox result = new CheckBox();
		final TableModel<T> model = table.getModel();
		result.setEnabled(false);
		result.setTabIndex(-1);
		result.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				if (!busy) {
					final Selection<T> sel = model.getSelection();
					final boolean checkAll = sel.getType() == SelectionType.INCLUSIVE
							|| (sel.getType() == SelectionType.EXCLUSIVE && sel.getIds().size() > 0);
					sel.bulkSelect(checkAll);
				}
			}
		});
		model.addBusyStateChangeHandler(new BusyStateChangeHandler() {
			@Override
			public void onStateChange(final BusyStateChangeEvent event) {
				busy = event.getState() == BusyState.BUSY;
				result.setEnabled(!busy);
			}
		});
		model.addModelChangeHandler(new ModelChangeHandler() {
			@Override
			public void onRowCountChange(final ModelChangeEvent event) {
				lastRowCount = event.getRowCount();
			}
		});
		model.addSelectionHandler(new SelectionHandler<Selection<T>>() {
			@Override
			public void onSelection(final SelectionEvent<Selection<T>> event) {
				DeferredCommand.addCommand(new Command() {
					@Override
					public void execute() {
						final Selection<T> sel = model.getSelection();
						final boolean allSelected = (sel.getType() == SelectionType.INCLUSIVE && sel.getIds().size() == lastRowCount)
								|| (sel.getType() == SelectionType.EXCLUSIVE && sel.getIds().isEmpty());
						result.setValue(allSelected);
					}
				});

			}
		});
		return result;

	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.StaticColumnDefinition#updateCellWidget(org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.DynamicTable, com.google.gwt.user.client.ui.Widget, java.lang.Object)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void updateCellWidget(final DynamicTable<T> table, final Widget widget, final T value) {
		final CheckBoxSelector<T> box = (CheckBoxSelector<T>) widget;
		box.setCurrentRow(value);
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.StaticColumnDefinition#updateHeaderFooterWidget(org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.DynamicTable, com.google.gwt.user.client.ui.Widget)
	 */
	@Override
	public void updateHeaderFooterWidget(final DynamicTable<T> table, final Widget widget) {
		final Selection<T> sel = table.getModel().getSelection();
		final boolean checked = sel.getType() == SelectionType.EXCLUSIVE && sel.getIds().isEmpty();
		((CheckBox) widget).setValue(checked);
	}

}
