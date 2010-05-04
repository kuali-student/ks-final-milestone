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

import java.io.Serializable;

/**
 * Maps an object in the model to a row index.
 * @author wilj
 *
 * @param <T> the generic type with which the table structure is associated
 */
public class Row<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	private int index = 0;
	private T value = null;

	/**
	 * Creates a new Row object. This constructor is only used for serialization purposes 
	 */
	protected Row() {
		// do nothing
	}

	/**
	 * Creates a new Row object
	 * @param index the row's index within the logical model
	 * @param value the value of the row
	 */
	public Row(final int index, final T value) {
		this.index = index;
		this.value = value;
	}

	/**
	 * Returns the row's index within the logical model
	 * @return the row's index within the logical model
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Returns the row's value
	 * @return the row's value
	 */
	public T getValue() {
		return value;
	}

	/**
	 * Sets the row's index within the logical model
	 * @param index the row's index within the logical model
	 */
	public void setIndex(final int index) {
		this.index = index;
	}

	/**
	 * Sets the row's value
	 * @param value the row's value
	 */
	public void setValue(final T value) {
		this.value = value;
	}

}
