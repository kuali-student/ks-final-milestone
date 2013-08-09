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

package org.kuali.student.common.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A <code>Map<code> implementation with a fixed maximum size of a 
 * least recently used (LRU) entry list using a <code>LinkedHashMap</code>.
 * 
 * @param <K> Key
 * @param <V> Value
 */
public class LRUMap<K,V> extends LinkedHashMap<K,V> {
	/** Class serial version uid */
    private static final long serialVersionUID = 1L;

    /** Maximum size of map */
	private int maxSize = 50;

	/**
	 * Constructs a new LRU Map with a default maximum size of 50 entries.
	 */
	public LRUMap() {
	}

	/**
	 * Constructs a new LRU Map.
	 * 
	 * @param maxSize Maximum size of LRU map.
	 */
	public LRUMap(int maxSize) {
    	this.maxSize = maxSize;
    }

	/**
	 * Removed oldest entry in map.
	 * 
	 * @param eldest Oldest entry to remove
	 */
    public boolean removeEldestEntry(Map.Entry<K,V> eldest) {
		return size() > this.maxSize;
	}
}
