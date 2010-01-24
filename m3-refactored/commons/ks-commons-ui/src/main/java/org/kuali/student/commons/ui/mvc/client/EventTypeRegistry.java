package org.kuali.student.commons.ui.mvc.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventTypeRegistry {
    private static Map<Class<? extends MVCEvent>, List<Class<? extends MVCEvent>>> registry = new HashMap<Class<? extends MVCEvent>, List<Class<? extends MVCEvent>>>();
    
    public static Class<? extends MVCEvent> register(Class<? extends MVCEvent> clazz, EventTypeHierarchy hierarchy) {
        registry.put(clazz, hierarchy.getList());
        return clazz;
    }
    
    public static List<Class<? extends MVCEvent>> getHierarchy(Class<? extends MVCEvent> clazz) {
        return registry.get(clazz);
    }
    
    public static boolean isSubClass(Class<? extends MVCEvent> superClass, Class<? extends MVCEvent> subClass) {
        boolean result = false;
        List<Class<? extends MVCEvent>> list = registry.get(subClass);
        if (list != null) {
            result = list.contains(superClass);
        }
        return result;
    }
}
