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

package org.kuali.student.common.ui.client.widgets.notification;

import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import org.kuali.student.common.ui.client.mvc.Holder;
import org.kuali.student.common.ui.client.util.Elements;

public class KSNotifier {
    private static final FlowPanel notifier = new FlowPanel();

    public static final int FADE_DURATION = 1000;

    private static final int DEFAULT_FADE_DURATION = 4000;

    static {
        notifier.setStyleName("ks-notification-container");
    }

    private KSNotifier() {

    }

    public static void show(String message) {
        add(new KSNotification(message, false, DEFAULT_FADE_DURATION));
    }

    public static void add(final KSNotification notification) {
        if (notifier.getWidgetCount() == 0) {
            RootPanel.get().add(notifier);
        }

        final Holder<HandlerRegistration> reg = new Holder<HandlerRegistration>();
        reg.set(notification.addCloseHandler(new CloseHandler<KSNotification>() {
            @Override
            public void onClose(CloseEvent<KSNotification> event) {
                reg.get().removeHandler();
                remove(notification, false);
            }
        }));

        Elements.setOpacity(notification.getElement(), 0);
        notifier.add(notification);
        Elements.fadeIn(notification, FADE_DURATION, 85, new Elements.FadeCallback() {

            @Override
            public void onFadeStart() {
                // do nothing
            }

            @Override
            public void onFadeComplete() {
                new Timer() {
                    @Override
                    public void run() {
                        reg.get().removeHandler();
                        remove(notification, true);
                    }
                }.schedule(notification.getDuration());
            }
        });

    }

    public static void remove(final KSNotification notification, final boolean fireEvents) {
        if (notifier.getWidgetIndex(notification) != -1) {
            Elements.fadeOut(notification, FADE_DURATION, 0, new Elements.FadeCallback() {
                @Override
                public void onFadeStart() {
                    // do nothing
                }

                @Override
                public void onFadeComplete() {
                    notifier.remove(notification);
                    if (fireEvents) {
                        CloseEvent.fire(notification, notification);
                    }
                    if (notifier.getWidgetCount() == 0) {
                        RootPanel.get().remove(notifier);
                    }
                }
            });
        }
    }
}
