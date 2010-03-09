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

import com.google.gwt.event.shared.EventHandler;

/**
 * Handler interface for ScrollEvent events
 * @author wilj
 *
 */
public interface ScrollHandler extends EventHandler {

	/**
	 * Called when the referenced scrollbar starts scrolling
	 * @param event
	 */
	public void onScrollStart(ScrollEvent event);

	/**
	 * Called when the referenced scrollbar stops scrolling
	 * @param event
	 */
	public void onScrollStop(ScrollEvent event);
}
