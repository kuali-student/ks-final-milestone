/*
 * Copyright 2009 Johnson Consulting Services
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * 
 */
package org.kuali.student.lum.lu.ui.course.client.widgets.a.focus;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.HasBlurHandlers;
import com.google.gwt.event.dom.client.HasFocusHandlers;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.Widget;

public class FocusGroup implements HasBlurHandlers, HasFocusHandlers, HasHandlers {
	private class SyntheticBlurEvent extends BlurEvent {
		public SyntheticBlurEvent() {
			// do nothing
		}
	}

	private class SyntheticFocusEvent extends FocusEvent {
		public SyntheticFocusEvent() {
			// do nothing
		}
	}

	private final HandlerManager handlers;
	private final Map<Widget, Boolean> focusTrackers = new HashMap<Widget, Boolean>();
	private boolean focusEventPending = false;

	private boolean focused = false;

	private final Command checkFocusState = new Command() {
		@Override
		public void execute() {
			focusEventPending = false;
			boolean nowFocused = false;
			for (final Boolean b : focusTrackers.values()) {
				if (b) {
					nowFocused = true;
					break;
				}
			}

			if (focused ^ nowFocused) {
				if (nowFocused) {
					// we weren't focused, but now we are
					//					FocusEvent.fireNativeEvent(lastFocusEvent, FocusGroup.this);
					handlers.fireEvent(new SyntheticFocusEvent());
				} else {
					// we were focused, now we aren't
					//					BlurEvent.fireNativeEvent(lastBlurEvent, FocusGroup.this);
					handlers.fireEvent(new SyntheticBlurEvent());
				}
			}

			focused = nowFocused;
		}
	};

	public FocusGroup(final Widget parentWidget) {
		this.handlers = new HandlerManager(parentWidget);
	}

	@Override
	public HandlerRegistration addBlurHandler(final BlurHandler handler) {
		return handlers.addHandler(BlurEvent.getType(), handler);
	}

	@Override
	public HandlerRegistration addFocusHandler(final FocusHandler handler) {
		return handlers.addHandler(FocusEvent.getType(), handler);
	}

	public void addWidget(final Widget w) {
		if (w instanceof HasBlurHandlers) {
			((HasBlurHandlers) w).addBlurHandler(new BlurHandler() {
				@Override
				public void onBlur(final BlurEvent event) {
					focusTrackers.put(w, false);
					queueCheckFocusState();
				}
			});
		}
		if (w instanceof HasFocusHandlers) {
			((HasFocusHandlers) w).addFocusHandler(new FocusHandler() {
				@Override
				public void onFocus(final FocusEvent event) {
					focusTrackers.put(w, true);
					queueCheckFocusState();
				}
			});
		}
	}

	@Override
	public void fireEvent(final GwtEvent<?> event) {
		handlers.fireEvent(event);
	}

	private void queueCheckFocusState() {
		if (!focusEventPending) {
			focusEventPending = true;
			DeferredCommand.addCommand(checkFocusState);
		}
	}
}
