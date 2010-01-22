package org.kuali.student.commons.ui.mvc.client;

import java.util.ArrayList;
import java.util.List;

public class EventTypeHierarchy {
    private List<Class<? extends MVCEvent>> hierarchy = new ArrayList<Class<? extends MVCEvent>>();
    
    public List<Class<? extends MVCEvent>> getList() {
        return hierarchy;
    }
    public EventTypeHierarchy add(Class<? extends MVCEvent> clazz) {
        hierarchy.add(clazz);
        return this;
    }
}
