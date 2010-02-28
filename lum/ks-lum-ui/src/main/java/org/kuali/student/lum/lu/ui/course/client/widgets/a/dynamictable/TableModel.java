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

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.user.client.rpc.AsyncCallback;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.common.events.BusyStateChangeEvent;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.common.events.BusyStateChangeHandler;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.common.events.HasBusyState;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.common.events.BusyStateChangeEvent.BusyState;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.events.ModelChangeEvent;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.events.ModelChangeHandler;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.filters.Filter;

/**
 * Abstract class that defines a table model
 * @author wilj
 *
 * @param <T> the generic type with which the table structure is associated
 */
public abstract class TableModel<T> implements HasHandlers, HasSelectionHandlers<Selection<T>>, HasBusyState {
	private final HandlerManager handlers = new HandlerManager(this);
	private final Selection<T> selection = new Selection<T>(this);
	private Filter filter = null;
	private List<SortInfo> sortInfo = new ArrayList<SortInfo>();
	private int rowCount = 0;

	/**
	 * @see org.kuali.student.lum.lu.ui.course.client.widgets.a.common.events.HasBusyState#addBusyStateChangeHandler(org.kuali.student.lum.lu.ui.course.client.widgets.a.common.events.BusyStateChangeHandler)
	 */
	@Override
	public HandlerRegistration addBusyStateChangeHandler(final BusyStateChangeHandler handler) {
		return handlers.addHandler(BusyStateChangeEvent.TYPE, handler);
	}

	/**
	 * Adds a ModelChangeHandler
	 * @param handler the handler to add
	 * @return HandlerRegistration for removing the handler
	 */
	public HandlerRegistration addModelChangeHandler(final ModelChangeHandler handler) {
		return handlers.addHandler(ModelChangeEvent.TYPE, handler);
	}

	/**
	 * @see com.google.gwt.event.logical.shared.HasSelectionHandlers#addSelectionHandler(com.google.gwt.event.logical.shared.SelectionHandler)
	 */
	@Override
	public HandlerRegistration addSelectionHandler(final SelectionHandler<Selection<T>> handler) {
		return handlers.addHandler(SelectionEvent.getType(), handler);
	}

	/**
	 * To be called by subclasses when their busy state changes
	 * @param state the new busy state
	 */
	protected void fireBusyStateChange(final BusyState state) {
		handlers.fireEvent(new BusyStateChangeEvent(state));
	}

	/**
	 * @see com.google.gwt.event.shared.HasHandlers#fireEvent(com.google.gwt.event.shared.GwtEvent)
	 */
	@Override
	public void fireEvent(final GwtEvent<?> event) {
		handlers.fireEvent(event);
	}

	/**
	 * Fires a new ModelChangeEvent with the model's current row count
	 * Called by subclasses
	 * @param rowCount the current row count
	 */
	protected void fireModelChangeEvent(final int rowCount) {
		this.rowCount = rowCount;
		fireEvent(new ModelChangeEvent(rowCount));
	}

	/**
	 * Fires a new SelectionEvent with the table's current selection
	 * Called by subclasses 
	 */
	protected void fireSelectionEvent() {
		SelectionEvent.fire(this, selection);
	}

	/**
	 * Returns the model's current filter
	 * @return the model's current filter
	 */
	public Filter getFilter() {
		return filter;
	}

	/**
	 * Returns the model's current row count
	 * @return the model's current row count
	 */
	public int getRowCount() {
		return rowCount;
	}

	/**
	 * Returns the model's current selection
	 * @return the model's current selection
	 */
	public Selection<T> getSelection() {
		return selection;
	}

	/**
	 * Returns a list of the SortInfo objects currently used to sort the model
	 * @return a list of the SortInfo objects currently used to sort the model
	 */
	public List<SortInfo> getSortInfo() {
		return sortInfo;
	}

	/**
	 * Returns a unique (within the scope of the model) identifier for the specified value
	 * @param value the value from which to generate a unique identifier
	 * @return the unique identifier for the value
	 */
	public abstract String getUniqueIdentifier(T value);

	/**
	 * Requests that the model refresh itself 
	 */
	public abstract void refresh();

	/**
	 * Requests rows from the model
	 * @param start the 0 based starting row to fetch
	 * @param count the number of rows from the start index to fetch
	 * @param callback the AsyncCallback to invoke when the request has completed
	 */
	public abstract void requestRows(int start, int count, AsyncCallback<List<Row<T>>> callback);

	/**
	 * Sets the model's filter
	 * @param filter the filter to set
	 */
	public void setFilter(final Filter filter) {
		this.filter = filter;
		this.selection.clear();
	}

	/**
	 * Sets the model's sorting sort configuration, and refreshes the model
	 * @param sortInfo List<SortInfo>
	 */
	public void setSortInfo(final List<SortInfo> sortInfo) {
		setSortInfo(sortInfo, true);
	}

	/**
	 * Sets the model's sorting sort configuration, and refreshes the model
	 * @param sortInfo List<SortInfo>
	 * @param refresh true if the model should fire a refresh after applying the sorting
	 */
	public void setSortInfo(final List<SortInfo> sortInfo, final boolean refresh) {
		this.sortInfo = sortInfo;
		if (refresh) {
			refresh();
		}
	}

}
