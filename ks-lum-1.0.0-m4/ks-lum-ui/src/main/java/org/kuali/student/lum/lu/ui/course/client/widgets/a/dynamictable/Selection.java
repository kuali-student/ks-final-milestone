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
package org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents the selected rows within the model.
 * 
 * <b>IMPORTANT NOTE:</b> Models used with the DynamicTable widget can be larger than the browser can easily handle.  In current uses they
 * frequently exceed 100,000 records. (In testing, models of over 100,000,000 records have been used.)
 * 
 * Because of the potential for large models, and the table's ability to "select all", the Selection object cannot always contain all of the record identifiers.
 * 
 * The solution to this is to use a flag (SelectionType) that indicates whether the section mode is inclusive or exclusive of the identifiers in the list.
 * This relies on the fact that it is unlikely for a user to manually select more record identifiers than can be easily transmitted via RPC.
 * 
 * 
 * @author wilj
 *
 * @param <T> the generic type with which the table structure is associated
 */
public class Selection<T> {
	public enum SelectionType {
		INCLUSIVE, EXCLUSIVE
	}

	private final Set<String> ids = new HashSet<String>();
	private SelectionType type = SelectionType.INCLUSIVE;
	private final TableModel<T> model;

	/**
	 * Creates a new Selection object
	 * @param model the TableModel to which the selection is bound
	 */
	public Selection(final TableModel<T> model) {
		this.model = model;
	}

	/**
	 * Performs a bulk select or de-select
	 * @param selected true if all of the records in the model are to be selected
	 */
	public void bulkSelect(final boolean selected) {
		this.type = selected ? SelectionType.EXCLUSIVE : SelectionType.INCLUSIVE;
		this.ids.clear();
		model.fireSelectionEvent();
	}

	/**
	 * Clears the current selection
	 */
	public void clear() {
		bulkSelect(false);
	}

	/**
	 * Returns the String identifiers of the records tracked in this Selection. 
	 * See the "IMPORTANT NOTE" above about selection modes
	 * @return the String identifiers of the records tracked in this Selection
	 */
	public Set<String> getIds() {
		return ids;
	}

	/**
	 * Returns the number of rows that are selected
	 * @return the number of rows that are selected
	 */
	public int getSelectedRowCount() {
		int result;
		if (type == SelectionType.EXCLUSIVE) {
			result = model.getRowCount() - ids.size();
		} else {
			result = ids.size();
		}
		return result;
	}

	/**
	 * Returns the type of the selection (e.g. INCLUSIVE or EXCLUSIVE)
	 * @return the type of the selection
	 */
	public SelectionType getType() {
		return type;
	}

	/**
	 * Returns true if the specified record ID is selected
	 * @param id the ID of the record
	 * @return true if the record is selected
	 */
	public boolean isSelected(final String id) {
		final boolean contains = ids.contains(id);
		return (type == SelectionType.INCLUSIVE && contains) || (type == SelectionType.EXCLUSIVE && !contains);
	}

	/**
	 * Sets the selection state of the specified record
	 * @param id the record identifier
	 * @param selected true if the record is to be selected
	 */
	public void setSelected(final String id, final boolean selected) {
		if (type == SelectionType.INCLUSIVE) {
			if (selected) {
				ids.add(id);
			} else {
				ids.remove(id);
			}
		} else {
			if (selected) {
				ids.remove(id);
			} else {
				ids.add(id);
			}
		}
		model.fireSelectionEvent();
	}

	/**
	 * Sets the selection type/mode.  If individual records are currently selected, this results in an inversion of the selection
	 * @param type the new selection mode
	 */
	public void setType(final SelectionType type) {
		this.type = type;
		model.fireSelectionEvent();
	}

}
