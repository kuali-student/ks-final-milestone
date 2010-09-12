/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.common.ui.client.mvc.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.mvc.events.ViewChangeEvent;
import org.kuali.student.common.ui.client.mvc.events.ViewChangeHandler;
import org.kuali.student.common.ui.client.mvc.test.PersonApplication.PersonViews;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class HistoryView extends ViewComposite {
    private final VerticalPanel panel = new VerticalPanel();
    private final KSLabel label = new KSLabel("Notice that the history only contains view change events that are local to the controller, no child or parent view change events are captured.  This is because the ViewChangeEvent is a checked event.");
    private final ListBox history = new ListBox();
    private final KSButton reset = new KSButton("Reset History", new ClickHandler() {
        public void onClick(ClickEvent event) {
            history.clear();
        }
    });
    Map<String, Controller> map;
    HistoryModel model = null;

    public HistoryView(final Controller controller) {
        this(controller, new HistoryModel());
//        History.addValueChangeHandler(new ValueChangeHandler<String>() {
//            @Override
//            public void onValueChange(ValueChangeEvent<String> event) {
//                Map<String, List<String>> params = buildListParamMap(event.getValue());
//                List<String> view = params.get("view");
//                if(view != null && !view.isEmpty()) {
//                    try {
//                        controller.showView(PersonViews.valueOf(view.get(0)));
//                    } catch(IllegalArgumentException e) {
//                        try {
//                            controller.showView(AddressManager.AddressViews.valueOf(view.get(0)));
//                        } catch(IllegalArgumentException e1){
//                        }
//                    }
//                }
//            }
//            /* stolen from Window.Location */
//            Map<String, List<String>> buildListParamMap(String queryString) {
//                Map<String, List<String>> out = new HashMap<String, List<String>>();
//
//                if (queryString != null && queryString.length() > 0) {
//                  String qs = queryString;
//
//                  for (String kvPair : qs.split("&")) {
//                    String[] kv = kvPair.split("=", 2);
//                    if (kv[0].length() == 0) {
//                      continue;
//                    }
//
//                    List<String> values = out.get(kv[0]);
//                    if (values == null) {
//                      values = new ArrayList<String>();
//                      out.put(kv[0], values);
//                    }
//                    values.add(kv.length > 1 ? URL.decode(kv[1]) : "");
//                  }
//                }
//
//                for (Map.Entry<String, List<String>> entry : out.entrySet()) {
//                  entry.setValue(Collections.unmodifiableList(entry.getValue()));
//                }
//
//                out = Collections.unmodifiableMap(out);
//
//                return out;
//              }
//        });
    }
    
    public HistoryView(Controller controller, HistoryModel hist) {
        super(controller, PersonApplication.PersonViews.HISTORY.toString());
        super.initWidget(panel);
        model = hist;
        panel.add(label);
        panel.add(history);
        panel.add(reset);
        model.addController("view", controller);
        controller.addApplicationEventHandler(ViewChangeEvent.TYPE, new ViewChangeHandler() {
            public void onViewChange(ViewChangeEvent event) {
                history.addItem(event.getNewView().getName());
            }
        });
    }

}
