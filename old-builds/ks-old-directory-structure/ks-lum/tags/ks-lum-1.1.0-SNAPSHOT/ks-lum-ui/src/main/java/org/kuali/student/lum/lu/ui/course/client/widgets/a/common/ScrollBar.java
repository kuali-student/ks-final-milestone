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

package org.kuali.student.lum.lu.ui.course.client.widgets.a.common;

import org.kuali.student.lum.lu.ui.course.client.widgets.a.common.events.ScrollEvent;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.common.events.ScrollHandler;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.common.events.ScrollEvent.ScrollEventType;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.util.A11y;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.util.Elements;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.util.StyleEnum;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Standalone scrollbar widget.  Can be used when scrollbar-like capabilities are required and
 * a normal ScrollPanel will not suffice.  (e.g. if the resulting content area is too big for 
 * certain browsers to render, etc) 
 * 
 * @author wilj
 *
 */
public class ScrollBar extends Composite implements HasValue<Integer> {
	private final class ScrollRange {
		private static final int MIN = 0;
		private int max = 0;
		private int absoluteMin = 0;
		private int absoluteScrollPosition = 0;
		private int rangeHeight = 0;

		public int getAbsoluteMin() {
			return absoluteMin;
		}

		public int getAbsoluteScrollPosition() {
			return absoluteScrollPosition;
		}

		public int getMax() {
			return max;
		}

		public int getMin() {
			return MIN;
		}

		public int getRangeHeight() {
			return rangeHeight;
		}

		public void recalc() {
			final int rangeTop = panel.getAbsoluteTop();
			absoluteMin = MIN + rangeTop;

			rangeHeight = (panel.getElement().getClientHeight() - up.getOffsetHeight()) - down.getOffsetHeight();

			max = rangeHeight - tab.getOffsetHeight();
			absoluteScrollPosition = tab.getAbsoluteTop() - up.getOffsetHeight();
		}

	}

	/**
	 * Style names associated with the ScrollBar widget
	 * @author wilj
	 *
	 */
	public enum Styles implements StyleEnum {
		SCROLLBAR("ScrollBar"), SCROLLBAR_TAB("ScrollBar-Tab"), SCROLLBAR_IMAGE("ScrollBar-Image");
		private final String style;

		private Styles(final String style) {
			this.style = style;
		}

		public String getStyle() {
			return this.style;
		}
	}

	private static final int SCROLL_INTERVAL = 100;

	private final AbsolutePanel panel = new AbsolutePanel();

	private final Image up;
	private final Image down;
	private final SimplePanel tab = new SimplePanel();
	private int maxValue = 0;
	private int pageSize = 0;
	private int currentValue = 0;
	private int mouseY = -1;

	private int tabGrabOffset = -1;
	private int currentScrollAmount = -1;
	private final ScrollRange range = new ScrollRange();

	private boolean scrolling = false;

	private boolean dragging = false;

	private final Timer scrollDownTimer = new Timer() {
		@Override
		public void run() {
			if (scrolling) {
				scrollDown();
			} else {
				stopScrolling(this);
			}
		}
	};
	private final Timer scrollUpTimer = new Timer() {
		@Override
		public void run() {
			if (scrolling) {
				scrollUp();
			} else {
				stopScrolling(this);
			}
		}
	};

	private final Timer scrollDragTimer = new Timer() {
		@Override
		public void run() {
			if (scrolling) {
				setValue(getValueFromMouseY(tabGrabOffset));
			} else {
				this.cancel();
				stopScrolling(this);
			}

		}
	};
	private boolean redrawScheduled = false;

	private final Timer redrawTimer = new Timer() {
		@Override
		public void run() {
			final boolean scrollable = maxValue > 0;
			final int opacity = (scrollable) ? 100 : 0;
			tab.setVisible(scrollable);
			Elements.setOpacity(up.getElement(), opacity);
			Elements.setOpacity(down.getElement(), opacity);

			// this is a bit of a hack, but until GWT provides a way to "execute this after all styles have been adjusted" it is kind of necessary
			doRedraw();
			DeferredCommand.addCommand(new Command() {
				@Override
				public void execute() {
					doRedraw();
				}
			});

			redrawScheduled = false;
		}

	};

	private final Element documentBody = Document.get().getBody();

	/**
	 * Creates a new ScrollBar widget.
	 * While the maximum value can be specified, the minimum value is always 0.
	 * 
	 * @param maxValue the maximum value
	 * @param pageSize the increment to be used for page up/down scrolls
	 */
	public ScrollBar(final int maxValue, final int pageSize) {
		super.initWidget(panel);
		this.maxValue = Math.max(0, maxValue);
		this.pageSize = pageSize;
		this.up = new Image();//Theme.INSTANCE.getScrollImages().getScrollUp();
		this.down =new Image();// Theme.INSTANCE.getScrollImages().getScrollDown();
		panel.add(up);
		panel.add(tab);
		panel.add(down);

		up.addMouseDownHandler(new MouseDownHandler() {
			@Override
			public void onMouseDown(final MouseDownEvent event) {
				currentScrollAmount = 1;
				scrollUp();
				startScrolling(scrollUpTimer);
			}
		});
		up.addMouseUpHandler(new MouseUpHandler() {
			@Override
			public void onMouseUp(final MouseUpEvent event) {
				scrolling = false;
			}
		});
		up.addMouseOutHandler(new MouseOutHandler() {
			@Override
			public void onMouseOut(final MouseOutEvent event) {
				if (!dragging) {
					scrolling = false;
				}
			}
		});

		down.addMouseDownHandler(new MouseDownHandler() {
			@Override
			public void onMouseDown(final MouseDownEvent event) {
				currentScrollAmount = 1;
				scrollDown();
				startScrolling(scrollDownTimer);
			}
		});
		down.addMouseUpHandler(new MouseUpHandler() {
			@Override
			public void onMouseUp(final MouseUpEvent event) {
				scrolling = false;
			}
		});
		down.addMouseOutHandler(new MouseOutHandler() {
			@Override
			public void onMouseOut(final MouseOutEvent event) {
				if (!dragging) {
					scrolling = false;
				}
			}
		});

		Event.addNativePreviewHandler(new Event.NativePreviewHandler() {
			@Override
			public void onPreviewNativeEvent(final NativePreviewEvent event) {
				final int type = event.getTypeInt();
				if ((type == Event.ONMOUSEDOWN || type == Event.ONMOUSEUP || type == Event.ONMOUSEMOVE || type == Event.ONMOUSEOUT)
						&& Element.is(event.getNativeEvent().getEventTarget())) {
					final Element e = Element.as(event.getNativeEvent().getEventTarget());
					if (type == Event.ONMOUSEDOWN && panel.getElement().equals(e)) {
						range.recalc();
						currentScrollAmount = ScrollBar.this.pageSize;
						if (event.getNativeEvent().getClientY() < range.getAbsoluteScrollPosition()) {
							scrollUp();
							startScrolling(scrollUpTimer);
						} else if (event.getNativeEvent().getClientY() > range.getAbsoluteScrollPosition()
								+ tab.getElement().getOffsetHeight()) {
							scrollDown();
							startScrolling(scrollDownTimer);
						}
						event.getNativeEvent().preventDefault();
					} else if (type == Event.ONMOUSEDOWN && tab.getElement().isOrHasChild(e)) {
						range.recalc();
						tabGrabOffset = event.getNativeEvent().getClientY() - range.getAbsoluteScrollPosition();
						dragging = true;
						startScrolling(scrollDragTimer);
						event.getNativeEvent().preventDefault();
					} else if (type == Event.ONMOUSEUP) {
						scrolling = false;
						dragging = false;
					} else if (type == Event.ONMOUSEMOVE) {
						mouseY = event.getNativeEvent().getClientY();
					} else if (type == Event.ONMOUSEOUT && e.equals(documentBody)) {
						//Log.debug("ScrollBar - document mouse out");
						scrolling = false;
						dragging = false;
					}
				}
			}
		});

		initStyles();

		// hacks for FireVox
		A11y.disableTts(panel.getElement(), true);
		getElement().setAttribute("tabIndex", "-1");
	}

	/**
	 * Adds a ScrollHandler to the scrollbar.
	 * It is important to note that this ScrollHandler is just used for detecting the state of the scrollbar.
	 * To catch value change events, use a ValueChangeHandler
	 * @param handler the handler to add
	 * @return HandlerRegistration for removing the handler
	 */
	public HandlerRegistration addScrollHandler(final ScrollHandler handler) {
		return this.addHandler(handler, ScrollEvent.TYPE);
	}

	/**
	 * @see com.google.gwt.event.logical.shared.HasValueChangeHandlers#addValueChangeHandler(com.google.gwt.event.logical.shared.ValueChangeHandler)
	 */
	@Override
	public HandlerRegistration addValueChangeHandler(final ValueChangeHandler<Integer> handler) {
		return this.addHandler(handler, ValueChangeEvent.getType());
	}

	private void doRedraw() {
		DeferredCommand.addCommand(new Command() {
			@Override
			public void execute() {
				panel.setWidth(up.getOffsetWidth() + "px");

				DeferredCommand.addCommand(new Command() {
					@Override
					public void execute() {
						range.recalc();
						final float maxTabHeight = range.getRangeHeight();
						int tabHeight = (int) (((float) pageSize / (float) maxValue) * maxTabHeight) / 3;
						tabHeight = Math.min((int) (maxTabHeight * .90), tabHeight);
						tabHeight = Math.max(15, tabHeight);

						tab.setSize(up.getOffsetWidth() + "px", tabHeight + "px");
						final int height = range.getMax() - range.getMin();
						final double d = (double) height / (double) maxValue;
						final int i = (int) (currentValue * d) + up.getOffsetHeight();
						panel.setWidgetPosition(tab, 0, i);
					}
				});
			}

		});
	}

	/**
	 * @see com.google.gwt.user.client.ui.HasValue#getValue()
	 */
	@Override
	public Integer getValue() {
		return currentValue;
	}

	private int getValueFromMouseY(final int offset) {
		range.recalc();
		final int relative = (mouseY - offset) - range.getAbsoluteMin();

		final int height = range.getMax() - range.getMin();
		final double pixelsPerValue = (double) height / (double) maxValue;
		int result = (int) (relative / pixelsPerValue);

		result = Math.min(result, maxValue);
		result = Math.max(result, 0);
		return result;
	}

	private void initStyles() {
		up.addStyleName(Styles.SCROLLBAR_IMAGE.getStyle());
		down.addStyleName(Styles.SCROLLBAR_IMAGE.getStyle());
		tab.addStyleName(Styles.SCROLLBAR_TAB.getStyle());
		panel.addStyleName(Styles.SCROLLBAR.getStyle());

		setStyle(panel, "position", "relative");

		setStyle(up, "position", "absolute");
		setStyle(up, "top", "0");
		setStyle(up, "left", "0");

		setStyle(down, "position", "absolute");
		setStyle(down, "bottom", "0");
		setStyle(down, "left", "0");

	}

	/**
	 * @see com.google.gwt.user.client.ui.Widget#onLoad()
	 */
	@Override
	public void onLoad() {
		DeferredCommand.addCommand(new Command() {
			@Override
			public void execute() {
				redraw();
			}
		});
	}

	/**
	 * Redraws the scrollbar.
	 * Note that this schedules a request for a redraw, the redraw is not immediate.
	 */
	public void redraw() {
		final int value = currentValue;
		currentValue = -1;
		setValue(value, false);
	}

	/**
	 * Redraws the scrollbar.
	 * Note that this schedules a request for a redraw, the redraw is not immediate.
	 * @param maxValue the new maxValue parameter for the scrollbar
	 * @param pageSize the new pageSize value for the scrollbar
	 */
	public void redraw(final int maxValue, final int pageSize) {
		this.maxValue = Math.max(0, maxValue);
		this.pageSize = pageSize;
		redraw();
	}

	private void scrollDown() {
		if (currentValue < maxValue) {
			final int i = currentValue + currentScrollAmount;
			setValue(Math.min(i, maxValue));
			if (i >= getValueFromMouseY(tab.getOffsetHeight())) {
				scrollDownTimer.cancel();
			}
		}
	}

	private void scrollUp() {
		if (currentValue > 0) {
			final int i = currentValue - currentScrollAmount;
			setValue(Math.max(i, 0));
			if (i <= getValueFromMouseY(0)) {
				scrollUpTimer.cancel();
			}
		}
	}

	private void setStyle(final Widget w, final String key, final String value) {
		w.getElement().getStyle().setProperty(key, value);
	}

	/**
	 * @see com.google.gwt.user.client.ui.HasValue#setValue(java.lang.Object)
	 */
	@Override
	public void setValue(final Integer value) {
		setValue(value, true);
	}

	/**
	 * @see com.google.gwt.user.client.ui.HasValue#setValue(java.lang.Object, boolean)
	 */
	@Override
	public void setValue(final Integer value, final boolean fireEvents) {
		if (value != currentValue) {
			currentValue = value;
			if (!redrawScheduled) {
				redrawScheduled = true;
				redrawTimer.schedule(250);
			}
			if (fireEvents) {
				ValueChangeEvent.fire(this, currentValue);
			}
		}
	}

	private void startScrolling(final Timer timer) {
	//	Log.debug("ScrollBar - startScrolling");
		scrolling = true;
		timer.scheduleRepeating(SCROLL_INTERVAL);
		fireEvent(new ScrollEvent(ScrollEventType.SCROLL_START));
	}

	private void stopScrolling(final Timer timer) {
		//Log.debug("ScrollBar - stopScrolling");
		timer.cancel();
		fireEvent(new ScrollEvent(ScrollEventType.SCROLL_STOP));
	}

}
