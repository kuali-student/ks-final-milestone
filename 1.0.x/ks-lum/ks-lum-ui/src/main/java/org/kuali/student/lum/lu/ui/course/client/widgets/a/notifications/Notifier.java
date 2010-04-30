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
package org.kuali.student.lum.lu.ui.course.client.widgets.a.notifications;

import java.util.LinkedList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ScrollEvent;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.themes.Theme;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.util.Elements;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.util.StyleEnum;

/**
 * TODO javadoc this class
 * @author wilj
 *
 */
public class Notifier {
	public interface InitialPositionCallback {
		public int getBorderX();

		public int getBorderY();
	}

	private class NotificationPanel extends Composite {
		private final Image close = new Image();//Theme.INSTANCE.getNotificationImages().getClose();
		private final FocusPanel focus = new FocusPanel();
		private final AbsolutePanel panel = new AbsolutePanel();

		public NotificationPanel(final Widget widget, final PopupPanel popup) {
			super.initWidget(focus);
			focus.setWidget(panel);
			panel.add(widget);

			close.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(final ClickEvent event) {
					popup.hide();
				}
			});
			focus.addMouseOverHandler(new MouseOverHandler() {

				@Override
				public void onMouseOver(final MouseOverEvent event) {
					panel.add(close, panel.getOffsetWidth() - 20, 0);
				}
			});
			focus.addMouseOutHandler(new MouseOutHandler() {
				@Override
				public void onMouseOut(final MouseOutEvent event) {
					panel.remove(close);
				}
			});
		}

	}

	public enum Styles implements StyleEnum {
		NOTIFICATION("Notification");

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

	private static final int SPACING = 5;

	private static final int FADE_DURATION = 1000;

	private final InitialPositionCallback position;

	private final LinkedList<Notification> notifications = new LinkedList<Notification>();
	private final LinkedList<PopupPanel> popups = new LinkedList<PopupPanel>();

	public Notifier(final InitialPositionCallback position) {
		this.position = position;
		Window.addResizeHandler(new ResizeHandler() {
			@Override
			public void onResize(final ResizeEvent event) {
				reflow();
			}
		});
		Window.addWindowScrollHandler(new Window.ScrollHandler() {
			@Override
			public void onWindowScroll(final ScrollEvent event) {
				reflow();
			}
		});
	}

	public void addNotification(final Notification notification) {
		final PopupPanel pop = new PopupPanel(false, false);
		final NotificationPanel panel = new NotificationPanel(notification, pop);
		pop.setStyleName(Styles.NOTIFICATION.getStyle());
		pop.setWidget(panel);
		pop.setAnimationEnabled(false);
		pop.addCloseHandler(new CloseHandler<PopupPanel>() {
			@Override
			public void onClose(final CloseEvent<PopupPanel> event) {
				final int idx = popups.indexOf(pop);
				notifications.remove(idx);
				popups.remove(idx);
				reflow();
			}
		});

		positionAndShow(pop, notification);

		notifications.add(notification);
		popups.add(pop);
	}

	public void expireNotification(final Notification notification) {
		final int idx = notifications.indexOf(notification);
		if (idx != -1) {
			popups.get(idx).hide();
		}
	};

	private void positionAndShow(final PopupPanel pop, final Notification notification) {
		pop.setVisible(false);
		PopupPanel last = null;
		if (!notifications.isEmpty()) {
			last = popups.getLast();
		}

		final int y = (last == null) ? position.getBorderY() : last.getAbsoluteTop() + last.getOffsetHeight()
				+ SPACING;

		pop.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
			@Override
			public void setPosition(final int offsetWidth, final int offsetHeight) {
				pop.setPopupPosition(position.getBorderX() - offsetWidth, y);
			}
		});

		Elements.fadeIn(pop, FADE_DURATION, new Elements.FadeCallback() {
			@Override
			public boolean isCanceled() {
				return false;
			}

			@Override
			public void onFadeComplete() {
				new Timer() {
					@Override
					public void run() {
						if (pop.isShowing()) {
							Elements.fadeOut(pop, FADE_DURATION, new Elements.FadeCallback() {
								@Override
								public boolean isCanceled() {
									return false;
								}

								@Override
								public void onFadeComplete() {
									pop.hide();
								}

								@Override
								public void onFadeStart() {
									// do nothing
								}
							});
						}
					}
				}.schedule(notification.getDuration());
			}

			@Override
			public void onFadeStart() {
				pop.setVisible(true);
			}
		});
	}

	private void reflow() {
		PopupPanel last = null;
		for (int i = 0; i < popups.size(); i++) {
			final PopupPanel pop = popups.get(i);
			final int y = (last == null) ? position.getBorderY() : last.getAbsoluteTop()
					+ last.getOffsetHeight() + SPACING;
			pop.setPopupPosition(position.getBorderX() - pop.getOffsetWidth(), y);
			last = pop;
		}
	}

}
