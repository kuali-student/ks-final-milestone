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
 * Represents the sorting of a column
 * @author wilj
 *
 */
public class SortInfo implements Serializable {

	/**
	 * Enumeration of sort directions
	 */
	public enum Direction {
		ASCENDING, DESCENDING, UNSORTED;
		public Direction toggle() {
			if (this == ASCENDING) {
				return DESCENDING;
			} else {
				return ASCENDING;
			}
		}
	}

	private static final long serialVersionUID = 1L;

	private String key = null;
	private Direction direction = Direction.UNSORTED;

	/**
	 * Creates a new SortInfo object.  This constructor should only be used by RPC serialization
	 */
	protected SortInfo() {
		// do nothing
	}

	/**
	 * Creates a new SortInfo object
	 * @param key the key name of the column
	 * @param direction the direction in which the column is sorted
	 */
	public SortInfo(final String key, final Direction direction) {
		super();
		this.key = key;
		this.direction = direction;
	}

	/**
	 * Returns the direction in which the column is sorted
	 * @return the direction in which the column is sorted
	 */
	public Direction getDirection() {
		return direction;
	}

	/**
	 * Returns the key name of the column
	 * @return the key name of the column
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Sets the direction in which the column is sorted
	 * @param direction the direction in which the column is sorted
	 */
	public void setDirection(final Direction direction) {
		this.direction = direction;
	}

	/**
	 * Sets the key name of the column
	 * @param key the key name of the column
	 */
	public void setKey(final String key) {
		this.key = key;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return key + " " + direction.toString();
	}

}