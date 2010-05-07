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

package org.kuali.student.common.ui.client.mvc.history;

import org.kuali.student.common.ui.client.mvc.Controller;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;

public class HistoryManager {
    private static final NavigationEventMonitor monitor = new NavigationEventMonitor();
    private static Controller root;
    
    public static void bind(Controller controller) {
        root = controller;
        root.addApplicationEventHandler(NavigationEvent.TYPE, monitor);
        History.addValueChangeHandler(new ValueChangeHandler<String>() {

            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                HistoryStackFrame frame = null;
                try {
                    frame = HistoryStackFrame.fromSerializedForm(event.getValue());
                } catch (Exception e) {
                    GWT.log("invalid history descriptor", e);
                }
                if (frame != null) {
                    root.onHistoryEvent(frame);
                }
            }
            
        });
    }
    
    public static boolean processHistoryQuerystring() {
        return processHistoryQuerystring(Window.Location.getHash()) || processHistoryQuerystring(Window.Location.getQueryString());
    }
    
    private static boolean processHistoryQuerystring(String queryString) {
        boolean result = false;
        
        try {
            HistoryStackFrame frame = HistoryStackFrame.fromSerializedForm(queryString);
            if (frame != null) {
                root.onHistoryEvent(frame);
                result = true;
            }
        } catch (Exception e) {
            GWT.log("error processing history tokens: " + queryString, e);
        }
        
        return result;
    }
    
    public static HistoryStackFrame collectHistoryFrame() {
        HistoryStackFrame result = new HistoryStackFrame();
        root.collectHistory(result);
        return result;
    }
    
    public static void logHistoryChange() {
        HistoryStackFrame frame = collectHistoryFrame();
        History.newItem(HistoryStackFrame.toSerializedForm(frame), false);
    }
    
    private static class NavigationEventMonitor implements NavigationEventHandler{
        private static final int EVENT_DELAY = 100;
        private long lastEvent = -1;
        
        private final Timer timer = new Timer() {

            @Override
            public void run() {
                if (lastEvent < (System.currentTimeMillis()-EVENT_DELAY)) {
                    this.cancel();
                    lastEvent = -1;
                    logHistoryChange();
                }
            }
            
        };
        
        @Override
        public void onNavigationEvent(NavigationEvent event) {
            boolean start = (lastEvent == -1);
            lastEvent = System.currentTimeMillis();
            if (start) {
                timer.scheduleRepeating(EVENT_DELAY);
            }
        }
    }
}
