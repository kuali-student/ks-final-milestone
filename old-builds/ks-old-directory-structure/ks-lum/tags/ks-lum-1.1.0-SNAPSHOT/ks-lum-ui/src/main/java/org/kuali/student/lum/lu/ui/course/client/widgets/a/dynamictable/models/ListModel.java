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
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.Row;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.SortInfo;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.TableModel;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.SortInfo.Direction;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Base class for creating simple client-side list models
 * @author wilj
 *
 * @param <T> the generic type with which the table structure is associated
 */
public abstract class ListModel<T> extends TableModel<T> {
	private final List<T> values = new ArrayList<T>();
	private final Map<String, Comparator<T>> comparators = new HashMap<String, Comparator<T>>();

	/**
	 * Adds a new value to the model
	 * @param value the value to add
	 */
	public void add(final T value) {
		this.values.add(value);
		refresh();
	}

	/**
	 * Adds a collection of values to the model
	 * @param values the values to add
	 */
	public void addAll(final Collection<T> values) {
		this.values.addAll(values);
		refresh();
	}

	/**
	 * Adds a comparator to use when sorting the model
	 * @param key the key name of the column for which this comparator will be used
	 * @param comparator the comparator to use when sorting on this column
	 */
	protected void addComparator(final String key, final Comparator<T> comparator) {
		comparators.put(key, comparator);
	}

	/**
	 * Clears the model and fires a model change event by refreshing the model 
	 */
	public void clear() {
		this.values.clear();
		refresh();
	}

	@SuppressWarnings("unchecked")
	private int defaultCompare(final Object o1, final Object o2) {
		if (o1 == null && o2 == null) {
			return 0;
		} else if (o1 == null && o2 != null) {
			return 1;
		} else if (o1 != null && o2 == null) {
			return -1;
		} else if (o1 instanceof Comparable && o2 instanceof Comparable) {
			return ((Comparable) o1).compareTo(o2);
		} else {
			return 0;
		}
	}

	/**
	 * @see org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.TableModel#refresh()
	 */
	@Override
	public void refresh() {
		fireModelChangeEvent(values.size());
	}

	/**
	 * Removes a value from the model
	 * @param value the value to remove
	 */
	public void remove(final T value) {
		this.values.remove(value);
		refresh();
	}

	/**
	 * Removes a collection of values from the model
	 * @param values the values to remove
	 */
	public void removeAll(final Collection<T> values) {
		this.values.removeAll(values);
		refresh();
	}

	/**
	 * @see org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.TableModel#requestRows(int, int, com.google.gwt.user.client.rpc.AsyncCallback)
	 */
	@Override
	public void requestRows(final int start, final int count, final AsyncCallback<List<Row<T>>> callback) {
		DeferredCommand.addCommand(new Command() {
			@Override
			public void execute() {
				try {
					final List<Row<T>> result = new ArrayList<Row<T>>();
					for (int i = start; i < start + count; i++) {
						result.add(new Row<T>(i, values.get(i)));
					}
					callback.onSuccess(result);
				} catch (final RuntimeException e) {
					callback.onFailure(e);
				}
			}
		});
	}

	/**
	 * @see org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.TableModel#setSortInfo(java.util.List)
	 */
	@Override
	public void setSortInfo(final List<SortInfo> sortInfo) {
		for (final SortInfo s : sortInfo) {
			final Comparator<T> c = new Comparator<T>() {
				final int direction = (s.getDirection() == Direction.ASCENDING) ? 1 : -1;
				final Comparator<T> wrapped = comparators.get(s.getKey());

				@Override
				public int compare(final T o1, final T o2) {
					if (wrapped == null) {
						return defaultCompare(o1, o2) * direction;
					} else {
						return wrapped.compare(o1, o2) * direction;
					}
				}
			};
			Collections.sort(values, c);
		}
		super.setSortInfo(sortInfo);
	}
}
