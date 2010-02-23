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

import com.google.gwt.event.shared.GwtEvent;

/**
 * Represents scroll events fired by the ScrollBar widget.  The event is only used to indicate scroll state change (start/stop)
 * @author wilj
 *
 */
public class ScrollEvent extends GwtEvent<ScrollHandler> {

	/**
	 * Enumeration of state changes
	 * @author wilj
	 *
	 */
	public enum ScrollEventType {
		SCROLL_START, SCROLL_STOP
	}

	public static final Type<ScrollHandler> TYPE = new Type<ScrollHandler>();

	private final ScrollEventType eventType;

	/**
	 * Creates a new ScrollEvent
	 * @param eventType the state of the scroll event (start/stop)
	 */
	public ScrollEvent(final ScrollEventType eventType) {
		this.eventType = eventType;
	}

	/**
	 * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
	 */
	@Override
	protected void dispatch(final ScrollHandler handler) {
		if (eventType == ScrollEventType.SCROLL_START) {
			handler.onScrollStart(this);
		} else if (eventType == ScrollEventType.SCROLL_STOP) {
			handler.onScrollStop(this);
		}
	}

	/**
	 * @see com.google.gwt.event.shared.GwtEvent#getAssociatedType()
	 */
	@Override
	public Type<ScrollHandler> getAssociatedType() {
		return TYPE;
	}
}
