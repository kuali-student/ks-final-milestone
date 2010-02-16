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

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.UIObject;

/**
 * Utility class for DOM element manipulations, UIObject effects, etc.
 * 
 * @author wilj
 *
 */
public class Elements {

	public interface FadeCallback {
		boolean isCanceled();

		void onFadeComplete();

		void onFadeStart();
	}

	/**
	 * Causes an element to "fade in" via opacity.  Make sure that element to fade is not visible before calling this method.
	 * When the callback is invoked with onFadeStart, the element can be set to visible as it is entirely transparent.
	 * When onFadeComplete is called, the element will be entirely opaque.
	 * @param target the UIObject to be faded in
	 * @param milliseconds how long the fade effect should take to process
	 * @param callback the callback to be invoked on fade progress
	 */
	public static void fadeIn(final UIObject target, final int milliseconds, final FadeCallback callback) {
		final Element e = target.getElement();
		final int interval = milliseconds / 50;
		setOpacity(e, 0);
		DeferredCommand.addCommand(new Command() {

			@Override
			public void execute() {
				callback.onFadeStart();

				final Timer t = new Timer() {
					int pct = 0;

					@Override
					public void run() {
						pct += 2;
						setOpacity(e, pct);
						if (pct == 100 || callback.isCanceled()) {
							this.cancel();
							callback.onFadeComplete();
						}
					}
				};
				t.scheduleRepeating(interval);

			}
		});
	}

	/**
	 * Causes an element to "fade out" via opacity.
	 * When onFadeComplete is called, the element will be entirely transparent.
	 * @param target the UIObject to be faded out
	 * @param milliseconds how long the fade effect should take to process
	 * @param callback the callback to be invoked on fade progress
	 */
	public static void fadeOut(final UIObject target, final int milliseconds, final FadeCallback callback) {
		final Element e = target.getElement();
		final int interval = milliseconds / 50;
		DeferredCommand.addCommand(new Command() {

			@Override
			public void execute() {
				callback.onFadeStart();

				final Timer t = new Timer() {
					int pct = 100;

					@Override
					public void run() {
						pct -= 2;
						setOpacity(e, pct);
						if (pct == 0 || callback.isCanceled()) {
							this.cancel();
							callback.onFadeComplete();
						}
					}
				};
				t.scheduleRepeating(interval);

			}
		});
	}

	/**
	 * Sets a DOM element's opacity
	 * @param e
	 * @param percent
	 */
	public static void setOpacity(final Element e, final int percent) {
		final Style s = e.getStyle();
		final double d = ((double) percent / (double) 100);
		s.setProperty("opacity", String.valueOf(d));
		s.setProperty("MozOpacity", String.valueOf(d));
		s.setProperty("KhtmlOpacity", String.valueOf(d));
		s.setProperty("filter", "alpha(opacity=" + percent + ")");
	}

	/**
	 * Enables/disables text selection for the specified element.
	 * Code borrowed/modified from:
	 * <a href="http://forgetmenotes.blogspot.com/2009/05/gwt-disable-text-selection-in-table.html">http://forgetmenotes.blogspot.com/2009/05/gwt-disable-text-selection-in-table.html</a>
	 * @param e
	 * @param selectable
	 */
	public static native void setTextSelectable(Element e, boolean selectable)/*-{
																				if (selectable) {
																				e.ondrag = null;
																				e.onselectstart = null;
																				e.style.MozUserSelect="text";
																				} else {
																				e.ondrag = function () { return false; };
																				e.onselectstart = function () { return false; };
																				e.style.MozUserSelect="none";
																				}
																				}-*/;
}
