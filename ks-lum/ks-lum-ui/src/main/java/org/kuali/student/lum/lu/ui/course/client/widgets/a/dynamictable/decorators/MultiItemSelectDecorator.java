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
package org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.decorators;

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
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.Selection;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.TableDecorator;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.TableDefinition;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.TableModel;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.Selection.SelectionType;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.events.ModelChangeEvent;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.events.ModelChangeHandler;

// TODO handle the ignoring of input when table is busy, in a better way than propagating the checks everywhere
/**
 * TableDecorator implementing multi-select capabilities for the table
 * @author wilj
 *
 * @param <T> the generic type with which the table structure is associated
 */
public class MultiItemSelectDecorator<T> implements TableDecorator<T> {
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

	/**
	 * @see org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.TableDecorator#getRowDecorator(org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.TableDefinition, org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.TableModel)
	 */
	@Override
	public Widget getRowDecorator(final TableDefinition<T> definition, final TableModel<T> model) {
		return new CheckBoxSelector<T>(model);
	}

	/**
	 * @see org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.TableDecorator#getTableDecorator(org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.TableDefinition, org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.TableModel)
	 */
	@Override
	public Widget getTableDecorator(final TableDefinition<T> definition, final TableModel<T> model) {
		final CheckBox result = new CheckBox();
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

	/**
	 * @see org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.TableDecorator#updateRowDecorator(org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.TableDefinition, org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.TableModel, com.google.gwt.user.client.ui.Widget, java.lang.Object)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void updateRowDecorator(final TableDefinition<T> definition, final TableModel<T> model, final Widget w,
			final T rowValue) {
		final CheckBoxSelector<T> box = (CheckBoxSelector<T>) w;
		box.setCurrentRow(rowValue);
	}

	/**
	 * @see org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.TableDecorator#updateTableDecorator(org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.TableDefinition, org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.TableModel, com.google.gwt.user.client.ui.Widget)
	 */
	@Override
	public void updateTableDecorator(final TableDefinition<T> definition, final TableModel<T> model, final Widget w) {
		final Selection<T> sel = model.getSelection();
		final boolean checked = sel.getType() == SelectionType.EXCLUSIVE && sel.getIds().isEmpty();
		((CheckBox) w).setValue(checked);
	}

}
