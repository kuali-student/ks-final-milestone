package org.kuali.student.poc.client;


import org.kuali.student.commons.ui.mvc.client.EventTypeHierarchy;
import org.kuali.student.commons.ui.mvc.client.EventTypeRegistry;
import org.kuali.student.commons.ui.mvc.client.MVCEvent;

public class BaseEvents {
	public static class ShowView extends MVCEvent {
        static {
            EventTypeRegistry.register(ShowView.class, new ShowView().getHierarchy());
        }
        public EventTypeHierarchy getHierarchy() {
            return super.getHierarchy().add(ShowView.class);
        }
    }
	static {
	    new ShowView();
	}
}
