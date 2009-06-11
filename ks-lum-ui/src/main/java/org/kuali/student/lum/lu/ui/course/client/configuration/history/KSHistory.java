package org.kuali.student.lum.lu.ui.course.client.configuration.history;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.configurable.ConfigurableLayout;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.mvc.events.ViewChangeEvent;
import org.kuali.student.common.ui.client.mvc.events.ViewChangeHandler;
import org.kuali.student.lum.lu.ui.course.client.configuration.DefaultCreateUpdateLayout;
import org.kuali.student.lum.lu.ui.course.client.configuration.LUCreateUpdateView;
import org.kuali.student.lum.lu.ui.main.client.controller.LUMApplicationManager;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;

public class KSHistory implements ValueChangeHandler<String> {
    
    public static final String CONTROLLER_KEY = "view";
    public static final String LAYOUT_KEY = "section";
    public static final String IDABLE_KEY = "id";
    public static final String DOCIDABLE_KEY = "docId";
    
    private Controller controller;
    private Map<Enum<?>, ConfigurableLayout<?>> layoutMap;
    
    public KSHistory(final Controller controller) {
        this.controller = controller;
        layoutMap = new HashMap<Enum<?>, ConfigurableLayout<?>>();
        DeferredCommand.addCommand(new Command() {
            @Override
            public void execute() {
                History.addValueChangeHandler(KSHistory.this);
                History.fireCurrentHistoryState();
                controller.addApplicationEventHandler(ViewChangeEvent.TYPE, new ViewChangeHandler() {
                    String key = CONTROLLER_KEY;
                    @Override
                    public void onViewChange(ViewChangeEvent event) {
                        Map<String, List<String>> params = buildListParamMap(History.getToken());
                        if(params.get(key) == null) {
                            if(controller.getCurrentViewEnum() != null)
                                History.newItem((params.isEmpty()? "" : History.getToken()+"&")+key+"="+controller.getCurrentViewEnum().name(), false);
                        } else {
                            String temp = "";
                            for(String name : params.keySet()) {
                                if(name.equals(key)) {
                                    if(controller.getCurrentViewEnum() != null)
                                        temp += "&" + name + "=" + controller.getCurrentViewEnum().name();
                                } else {
                                    String t = "&"+name+"=";
                                    List<String> values = params.get(name);
                                    for(String value : values) {
                                        temp += t + value;
                                    }
                                }
                            }
                            History.newItem(temp.substring(1), false);
                        }
                    }
                });
            }});
    }
    
    public void addLayoutToView(Enum<?> view, ConfigurableLayout<?> layout) {
        if(view == null)
            throw new NullPointerException("view cannot be null (not as worried about this one)");
        if(layout == null) {
            throw new NullPointerException("layout cannot be null");
        }
        layoutMap.put(view, layout);
        layout.addApplicationEventHandler(ViewChangeEvent.TYPE, new ViewChangeHandler() {
            String key = LAYOUT_KEY;
            @Override
            public void onViewChange(ViewChangeEvent event) {
                Map<String, List<String>> params = buildListParamMap(History.getToken());
                if(params.get(key) == null) {
                    History.newItem((params.isEmpty()? "" : History.getToken()+"&")+key+"="+event.getNewView().getName(), false);
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
                    History.newItem(temp.substring(1), false);
                }
            }});
    }
    
    @Override
    public void onValueChange(ValueChangeEvent<String> event) {
        final Map<String, List<String>> params = buildListParamMap(event.getValue());
        if(params.get(CONTROLLER_KEY) != null && !params.get(CONTROLLER_KEY).isEmpty()) {
            DeferredCommand.addCommand(new Command() {
                @Override
                public void execute() {
                    Enum<?> view = null;
                    try {
                        view = valueOf(controller.getViewsEnum(), params.get(CONTROLLER_KEY).get(0)); //only assuming one value, hope this doesn't bite me later
                    } catch(IllegalArgumentException e) {
                        return; // i'm stopping if there isn't a valid view, even if other stuff is valid
                    }
                    if(params.get(IDABLE_KEY) != null && !params.get(IDABLE_KEY).isEmpty()) {
                        String id = params.get(IDABLE_KEY).get(0);
                        View lumView = ((LUMApplicationManager)controller).getControllerView(view);
                        ((LUCreateUpdateView)lumView).setId(id);
                    }
                    if(params.get(DOCIDABLE_KEY) != null && !params.get(DOCIDABLE_KEY).isEmpty()) {
                        String id = params.get(DOCIDABLE_KEY).get(0);
                        View lumView = ((LUMApplicationManager)controller).getControllerView(view);
                        ((LUCreateUpdateView)lumView).setId(id);
                    }
                    if(controller.getCurrentViewEnum() == null || !view.equals(controller.getCurrentViewEnum())) {
                        controller.showView(view);
                    }
                    if(params.get(LAYOUT_KEY) != null && !params.get(LAYOUT_KEY).isEmpty()) {
                        String path = params.get(LAYOUT_KEY).get(0);
                        ConfigurableLayout<?> configurableLayout = layoutMap.get(view);
                        if(configurableLayout instanceof DefaultCreateUpdateLayout)
                            ((DefaultCreateUpdateLayout<?>)configurableLayout).selectSection(path); //probably should just be moved up to superclass
                    }
                }});
        }
    }
    
    protected Enum<?> valueOf(Class<? extends Enum<?>> enumclass, String value) {
        Enum<?> consts[] = enumclass.getEnumConstants();
        for(Enum<?> e : consts) { 
            if(e.name().equals(value))
                return e;
        }
        throw new IllegalArgumentException("Enum not a valid type");
    }
    
    /* stolen from Window.Location */
    protected Map<String, List<String>> buildListParamMap(String queryString) {
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
        
        //Put in all the query params too
        out.putAll(Window.Location.getParameterMap());
        
        out = Collections.unmodifiableMap(out);
        
        return out;
      }

}
