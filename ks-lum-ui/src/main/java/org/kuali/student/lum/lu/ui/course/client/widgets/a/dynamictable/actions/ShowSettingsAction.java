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

package org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.actions;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.ColumnDefinition;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.DynamicTable;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.ColumnDefinition.ColumnAttribute;

/**
 * Defines an action that displays a table settings popup.  Currently the only settings that can be changed are which columns are visible.
 * @author wilj
 *
 * @param <T> the generic type with which the table structure is associated
 */
public class ShowSettingsAction<T> implements ActionHandler<T> {
	public static final ColumnAttribute PINNED = new ColumnAttribute() {
	};

	/**
	 * @see org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.actions.ActionHandler#isEnabledFor(org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.DynamicTable)
	 */
	@Override
	public boolean isEnabledFor(final DynamicTable<T> table) {
		return true;
	}

	/**
	 * @see org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.actions.ActionHandler#onAction(org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.DynamicTable)
	 */
	@Override
	public void onAction(final DynamicTable<T> table) {
		final List<ColumnDefinition<T>> cols = new ArrayList<ColumnDefinition<T>>();
		for (final ColumnDefinition<T> col : table.getDefinition().getColumns()) {
			if (!col.hasAttribute(PINNED.getClass())) {
				cols.add(col);
			}
		}
/*		final ColumnPicker<T> picker = new ColumnPicker<T>(cols, Theme.INSTANCE
				.getTableMessages().getSelectColumns(),
				new ColumnPicker.IsSelectedAdapter<T>() {
			@Override
			public boolean isSelected(final ColumnDefinition<T> column) {
				return column.isVisible();
			}
		});

		picker.show(new Callback<Boolean>() {
			@Override
			public void exec(final Boolean value) {
				if (value && picker.isSelectionChanged()) {
					for (final Entry<ColumnDefinition<T>, Boolean> e : picker.getSelection().entrySet()) {
						e.getKey().setVisible(e.getValue());
					}
					table.redraw();
				}
			}
		});
*/
	}

}
