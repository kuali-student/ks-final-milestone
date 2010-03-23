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
package org.kuali.student.lum.lu.ui.course.client.widgets.a.common.events;
//package org.kuali.student.lum.lu.ui.course.client.widgets.a.common.events;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Represents the "busy state" change of a widget or other component
 * @author wilj
 *
 */
public class BusyStateChangeEvent extends GwtEvent<BusyStateChangeHandler> {
	/**
	 * Busy/ready states
	 */
	public enum BusyState {
		BUSY, READY
	}

	public static final Type<BusyStateChangeHandler> TYPE = new Type<BusyStateChangeHandler>();

	private final BusyState state;

	/**
	 * Constructs a new BusyStateChangeEvent with the specified state
	 * @param state the new state
	 */
	public BusyStateChangeEvent(final BusyState state) {
		this.state = state;
	}

	/**
	 * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
	 */
	@Override
	protected void dispatch(final BusyStateChangeHandler handler) {
		handler.onStateChange(this);
	}

	/**
	 * @see com.google.gwt.event.shared.GwtEvent#getAssociatedType()
	 */
	@Override
	public Type<BusyStateChangeHandler> getAssociatedType() {
		return TYPE;
	}

	/**
	 * Returns the new state
	 * @return the new state
	 */
	public BusyState getState() {
		return state;
	}

}
