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

/**
 * 
 */
package org.kuali.student.lum.lu.ui.course.client.widgets.a.common;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.ui.Image;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.util.StyleEnum;

/**
 * @author wilj
 *
 */
public class DeferredImage extends Image {
	public enum Styles implements StyleEnum {
		DEFERRED_IMAGE("DeferredImage");

		private final String style;

		private Styles(final String style) {
			this.style = style;
		}

		/* (non-Javadoc)
		 * @see org.kuali.student.lum.lu.ui.course.client.widgets.a.util.StyleEnum#getStyle()
		 */
		@Override
		public String getStyle() {
			return this.style;
		}

	}

	private static final Set<String> loadHistory = new HashSet<String>();

	private final LoadHandler loadHandler = new LoadHandler() {
		@Override
		public void onLoad(final LoadEvent arg0) {
			DeferredImage.this.removeStyleName(Styles.DEFERRED_IMAGE.getStyle());
			loadHistory.add(DeferredImage.this.getUrl());
		}
	};

	/**
	 * @param url
	 */
	public DeferredImage(final String url) {
		super(url);
		checkLoadHistory();
	}

	/**
	 * @param url
	 * @param left
	 * @param top
	 * @param width
	 * @param height
	 */
	public DeferredImage(final String url, final int left, final int top, final int width, final int height) {
		super(url, left, top, width, height);
		checkLoadHistory();
	}

	private void checkLoadHistory() {
		if (!loadHistory.contains(super.getUrl())) {
			this.addStyleName(Styles.DEFERRED_IMAGE.getStyle());
			super.addLoadHandler(loadHandler);
		}
	}

}
