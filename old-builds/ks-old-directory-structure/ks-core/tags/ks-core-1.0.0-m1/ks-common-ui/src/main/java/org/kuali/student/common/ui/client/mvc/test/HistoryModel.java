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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.mvc.events.ViewChangeEvent;
import org.kuali.student.common.ui.client.mvc.events.ViewChangeHandler;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.History;

public class HistoryModel implements ValueChangeHandler<String> {
    Map<String, Controller> controllers = new LinkedHashMap<String, Controller>();
    
    public HistoryModel() {
        History.addValueChangeHandler(this);
    }
    
    public void addController(final String key, Controller controller) {
        controllers.put(key, controller);
        controller.addApplicationEventHandler(ViewChangeEvent.TYPE, new ViewChangeHandler() {
            @Override
            public void onViewChange(ViewChangeEvent event) {
                Map<String, List<String>> params = buildListParamMap(History.getToken());
                if(params.get(key) == null) {
                    History.newItem((params.isEmpty()? "" : History.getToken()+"&")+key+"="+event.getNewView().getName());
                } else {
                    String temp = "";
                    for(String name : params.keySet()) {
                        if(name.equals(key)) {
                            temp += "&" + name + "=" + event.getNewView().getName();
                        } else {
                            String t = "&"+name+"=";
                            List<String> values = params.get(name);
                            for(String value : values) {
                                temp += t + value;
                            }
                        }
                    }
                    History.newItem(temp.substring(1));
                }
            }
        });
    }
    
    public void addSubController(final String key, final Controller controller, final Map<String,List<String>> requiredParams) {
        controllers.put(key, controller);
        controller.addApplicationEventHandler(ViewChangeEvent.TYPE, new ViewChangeHandler() {
            @Override
            public void onViewChange(ViewChangeEvent event) {
                Map<String, List<String>> params = new LinkedHashMap<String, List<String>>(buildListParamMap(History.getToken()));
                if(requiredParams != null)
                    params.putAll(requiredParams);
                if(params.get(key) == null) {
                    History.newItem((params.isEmpty()? "" : History.getToken()+"&")+key+"="+event.getNewView().getName());
                } else {
                    String temp = "";
                    for(String name : params.keySet()) {
                        if(name.equals(key)) {
                            temp += "&" + name + "=" + event.getNewView().getName();
                        } else {
                            String t = "&"+name+"=";
                            List<String> values = params.get(name);
                            for(String value : values) {
                                temp += t + value;
                            }
                        }
                    }
                    if(params.get(key) == null)
                        temp += "&" + key + "=" + event.getNewView().getName();
                    History.newItem(temp.substring(1));
                }
            }
        });
    }
    
    @Override
    public void onValueChange(ValueChangeEvent<String> event) {
        Map<String, List<String>> params = buildListParamMap(event.getValue());
        for(String key : controllers.keySet()) {
            List<String> view = params.get(key);
            if(view != null && !view.isEmpty()) {
                Controller controller = controllers.get(key);
                View currentView = controller.getCurrentView();
                if(currentView == null || !view.get(0).equals(currentView.getName())) {
                    try {
                        controller.showView(valueOf(controller.getViewsEnum(),view.get(0)));
                    } catch(IllegalArgumentException e) {
                    }
                }
            }
        }
    }
    
    Enum<?> valueOf(Class<? extends Enum<?>> enumclass, String value) {
        Enum<?> consts[] = enumclass.getEnumConstants();
        for(Enum<?> e : consts) { 
            if(e.name().equals(value))
                return e;
        }
        return null;
    }
    
    /* stolen from Window.Location */
    Map<String, List<String>> buildListParamMap(String queryString) {
        Map<String, List<String>> out = new LinkedHashMap<String, List<String>>();

        if (queryString != null && queryString.length() > 0) {
          String qs = queryString;

          for (String kvPair : qs.split("&")) {
            String[] kv = kvPair.split("=", 2);
            if (kv[0].length() == 0) {
              continue;
            }

            List<String> values = out.get(kv[0]);
            if (values == null) {
              values = new ArrayList<String>();
              out.put(kv[0], values);
            }
            values.add(kv.length > 1 ? URL.decode(kv[1]) : "");
          }
        }

        for (Map.Entry<String, List<String>> entry : out.entrySet()) {
          entry.setValue(Collections.unmodifiableList(entry.getValue()));
        }

        out = Collections.unmodifiableMap(out);

        return out;
      }

}
