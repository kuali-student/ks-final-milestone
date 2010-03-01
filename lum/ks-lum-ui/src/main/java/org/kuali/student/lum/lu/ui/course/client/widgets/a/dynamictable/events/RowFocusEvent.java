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
package org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.events;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Fired by DynamicTable widgets when a row is "focused"
 * Note that this is not an actual DOM focus event, but a pseudo-focus event
 * @author wilj
 *
 * @param <T> the generic type with which the table structure is associated
 */
@SuppressWarnings("unchecked")
public class RowFocusEvent<T> extends GwtEvent<RowFocusHandler<T>> {
	private static Type TYPE;

	public static <T> Type<T> getType() {
		if (TYPE == null) {
			TYPE = new Type<RowFocusHandler<T>>();
		}
		return TYPE;
	}

	private final T focusedValue;

	/**
	 * Creates a new RowFocusEvent
	 * @param focusedValue the value of the row that is focused
	 */
	public RowFocusEvent(final T focusedValue) {
		this.focusedValue = focusedValue;
	}

	@Override
	protected void dispatch(final RowFocusHandler<T> handler) {
		handler.onRowFocus(this);
	}

	@Override
	public Type<RowFocusHandler<T>> getAssociatedType() {
		return getType();
	}

	/**
	 * Returns the value of the focused row
	 * @return the value of the focused row
	 */
	public T getFocusedValue() {
		return focusedValue;
	}

}
