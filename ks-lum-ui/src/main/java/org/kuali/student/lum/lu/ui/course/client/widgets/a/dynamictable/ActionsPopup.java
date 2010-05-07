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

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.actions.ActionHandler;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.PopupPanel;

/**
 * PopupPanel used to display menu of ActionDefinitions associated with the table
 * @author wilj
 *
 * @param <T> the generic type with which the table structure is associated
 */
public class ActionsPopup<T> extends PopupPanel {
	private boolean popupIsEnabled = false;

	/**
	 * Creates a new ActionPopup
	 * @param table the DynamicTable that the popup is bound to
	 * @param actions an ordered map of action handlers and their names
	 */
	public ActionsPopup(final DynamicTable<T> table, final LinkedHashMap<String, ActionHandler<T>> actions) {
		super(true);
		final MenuBar menu = new MenuBar(true);
		for (final Entry<String, ActionHandler<T>> entry : actions.entrySet()) {
			final ActionHandler<T> action = entry.getValue();
			final boolean enabled = action.isEnabledFor(table);

			final MenuItem item = new MenuItem(entry.getKey(), new Command() {
				@Override
				public void execute() {
					if (enabled) {
						ActionsPopup.this.hide();
						action.onAction(table);
					}
				}
			});

			if (enabled) {
				popupIsEnabled = true;
			} else {
				item.addStyleDependentName("disabled");
			}
			menu.addItem(item);
		}
		super.setWidget(menu);
	}

	/**
	 * Returns true if any of the specified actions are currently enabled for the specified table
	 * @return true if any of the specified actions are currently enabled for the specified table
	 */
	public boolean hasEnabledActions() {
		return popupIsEnabled;
	}
}
