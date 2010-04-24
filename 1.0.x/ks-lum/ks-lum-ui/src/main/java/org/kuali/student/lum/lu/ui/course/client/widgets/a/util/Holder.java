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
package org.kuali.student.lum.lu.ui.course.client.widgets.a.util;

/**
 * Convenience class used to get around some of Java's limitations when emulating closures
 * @author wilj
 *
 * @param <T>
 */
public class Holder<T> {
	private T value = null;

	public Holder() {
		super();
		// do nothing
	}

	/**
	 * Creates a new Holder with the specified value
	 * @param value
	 */
	public Holder(final T value) {
		this.value = value;
	}

	/**
	 * Returns the Holder's current value
	 * @return the Holder's current value
	 */
	public T get() {
		return this.value;
	}

	/**
	 * Sets the Holder's value
	 * @param value the value to set
	 */
	public void set(final T value) {
		this.value = value;
	}
}
