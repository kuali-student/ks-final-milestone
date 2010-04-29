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
package org.kuali.student.lum.lu.ui.course.client.widgets.a.common;

import com.google.gwt.user.client.ui.Image;

/**
 * Images to use when rendering the ScrollBar widget
 * @author wilj
 *
 */
public interface ScrollImages {
	/**
	 * The image to use when rendering the "scroll down" button
	 * @return the image to use when rendering the "scroll down" button
	 */
	public Image getScrollDown();

	/**
	 * The image to use when rendering the "scroll up" button
	 * @return the image to use when rendering the "scroll up" button
	 */
	public Image getScrollUp();
}
