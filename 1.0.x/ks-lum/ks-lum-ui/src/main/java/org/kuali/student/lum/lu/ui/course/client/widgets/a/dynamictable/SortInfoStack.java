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

import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.SortInfo.Direction;

/**
 * Convenience class used by the DynamicTable for tracking/manipulating column sort orders
 * @author wilj
 *
 */
public class SortInfoStack extends ArrayList<SortInfo> {
	private static final long serialVersionUID = 1L;

	/**
	 * Pushes a column onto the sort stack.
	 * If the column is not already in the sort stack, then it is added with an order of ASCENDING
	 * If the column is already sorted, then it is pulled from the stack, and pushed back to the top with the sort order inverted
	 * @param key the key name of the column to sort
	 */
	public void append(final String key) {
		SortInfo s = findInfo(key);
		if (s == null) {
			s = new SortInfo(key, Direction.ASCENDING);
		} else {
			this.remove(s);
			if (s.getDirection() == Direction.ASCENDING) {
				s.setDirection(Direction.DESCENDING);
			} else {
				s.setDirection(Direction.ASCENDING);
			}
		}
		this.add(0, s);
	}

	/**
	 * Finds the SortInfo object for the specified column
	 * @param key the key name of the column
	 * @return the SortInfo object, or null if the column is not in the stack
	 */
	public SortInfo findInfo(final String key) {
		SortInfo result = null;
		for (final SortInfo s : this) {
			if (s.getKey().equals(key)) {
				result = s;
				break;
			}
		}
		return result;
	}

	/**
	 * Returns the most recently sorted column
	 * @return the most recently sorted column
	 */
	public SortInfo getMostRecent() {
		if (this.isEmpty()) {
			return null;
		} else {
			return this.get(0);
		}
	}
}
