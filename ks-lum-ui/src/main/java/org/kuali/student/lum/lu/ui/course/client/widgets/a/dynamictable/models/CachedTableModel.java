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

package org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.models;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.lum.lu.ui.course.client.widgets.a.common.events.BusyStateChangeHandler;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.common.events.BusyStateChangeEvent.BusyState;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.Row;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.Selection;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.SortInfo;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.TableException;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.TableModel;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.events.ModelChangeEvent;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.events.ModelChangeHandler;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.filters.Filter;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.util.Callback;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.util.WorkQueue;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.util.WorkQueue.WorkItem;

import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * A caching TableModel that wraps another TableModel and buffers requests and results
 * @author wilj
 *
 * @param <T> the generic type with which the table structure is associated
 */
public class CachedTableModel<T> extends TableModel<T> {
	private class RequestRowsWorkItem extends WorkItem {

		private final int start;
		private final int count;
		private final AsyncCallback<List<Row<T>>> callback;

		public RequestRowsWorkItem(final int start, final int count, final AsyncCallback<List<Row<T>>> callback) {
			super();
			this.start = start;
			this.count = count;
			this.callback = callback;
		}

		/* (non-Javadoc)
		 * @see org.kuali.student.lum.lu.ui.course.client.widgets.a.util.WorkQueue.WorkItem#exec(org.kuali.student.lum.lu.ui.course.client.widgets.a.util.Callback)
		 */
		@Override
		public void exec(final Callback<Boolean> onCompleteCallback) {
			final List<Row<T>> cached = extractPopulatedPage(start, count);
			if (cached == null) {
				delegateRequest(start, count, callback, onCompleteCallback);
			} else {
				DeferredCommand.addCommand(new Command() {
					@Override
					public void execute() {
						try {
							callback.onSuccess(cached);
						} finally {
							onCompleteCallback.exec(true);
						}
					}
				});
			}
		}
	}

	private final WorkQueue queue = new WorkQueue();

	private final TableModel<T> impl;
	private final int fetchSize;

	private Row<T>[] cache;

	public CachedTableModel(final TableModel<T> impl, final int fetchSize) {
		super();
		this.impl = impl;
		this.fetchSize = fetchSize;
		initCache(0);
		this.impl.addModelChangeHandler(new ModelChangeHandler() {
			@Override
			public void onRowCountChange(final ModelChangeEvent event) {
				//				queue.clear();
				initCache(event.getRowCount());
			}
		});
	}

	@Override
	public HandlerRegistration addBusyStateChangeHandler(final BusyStateChangeHandler handler) {
		return impl.addBusyStateChangeHandler(handler);
	}

	@Override
	public HandlerRegistration addModelChangeHandler(final ModelChangeHandler handler) {
		return impl.addModelChangeHandler(handler);
	}

	@Override
	public HandlerRegistration addSelectionHandler(final SelectionHandler<Selection<T>> handler) {
		return impl.addSelectionHandler(handler);
	}

	private void delegateRequest(final int start, final int count, final AsyncCallback<List<Row<T>>> callback,
			final Callback<Boolean> onCompleteCallback) {
		int fetchMin = Math.max(0, start - (fetchSize / 2));
		// seek to first null row in range
		while (cache[fetchMin] != null && fetchMin < start) {
			fetchMin++;
		}
		int fetchMax = Math.min(cache.length - 1, fetchMin + fetchSize);
		// seek to last null row in range
		while (cache[fetchMax] != null && fetchMax > (start + count)) {
			fetchMax--;
		}
		final int fetchStart = fetchMin;
		final int fetchCount = fetchMax - fetchMin + 1;
		impl.requestRows(fetchStart, fetchCount, new AsyncCallback<List<Row<T>>>() {
			@Override
			public void onFailure(final Throwable caught) {
				try {
					fireBusyStateChange(BusyState.READY);
					callback.onFailure(caught);
				} finally {
					onCompleteCallback.exec(true);
				}
			}

			@Override
			public void onSuccess(final List<Row<T>> result) {
				try {
					for (final Row<T> r : result) {
						try {
							cache[r.getIndex()] = r;
						} catch (final ArrayIndexOutOfBoundsException e) {
							throw new TableException("Model row index mismatch. Model row index: " + r.getIndex()
									+ ", cache size: " + cache.length, e);
						}
					}
					callback.onSuccess(extractPopulatedPage(start, count));
				} finally {
					onCompleteCallback.exec(true);
				}
			}
		});

	}

	private List<Row<T>> extractPopulatedPage(final int start, final int count) {
		final List<Row<T>> result = new ArrayList<Row<T>>(count);
		for (int i = start; i < start + count; i++) {
			final Row<T> r = cache[i];
			if (r == null) {
				return null;
			}
			result.add(r);
		}
		return result;
	}

	@Override
	public void fireEvent(final GwtEvent<?> event) {
		impl.fireEvent(event);
	}

	@Override
	public Filter getFilter() {
		return impl.getFilter();
	}

	@Override
	public int getRowCount() {
		return impl.getRowCount();
	}

	@Override
	public Selection<T> getSelection() {
		return impl.getSelection();
	}

	@Override
	public List<SortInfo> getSortInfo() {
		return impl.getSortInfo();
	}

	@Override
	public String getUniqueIdentifier(final T value) {
		return impl.getUniqueIdentifier(value);
	}

	@SuppressWarnings("unchecked")
	private void initCache(final int newSize) {
		cache = new Row[newSize];
	}

	@Override
	public void refresh() {
		queue.submit(new WorkItem() {

			@Override
			public void exec(final Callback<Boolean> onCompleteCallback) {
				try {
					impl.refresh();
				} finally {
					onCompleteCallback.exec(true);
				}
			}
		});

	}

	@Override
	public void requestRows(final int start, final int count, final AsyncCallback<List<Row<T>>> callback) {
		queue.submit(new RequestRowsWorkItem(start, count, callback));
	}

	@Override
	public void setFilter(final Filter filter) {
		impl.setFilter(filter);
	}

	@Override
	public void setSortInfo(final List<SortInfo> sortInfo) {
		impl.setSortInfo(sortInfo);
	}

}
