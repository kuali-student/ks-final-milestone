package org.kuali.student.commons.ui.mvc.client;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;

public class EventDispatcher {
	private final Set<ListenerMapping> listeners = new HashSet<ListenerMapping>();
	
	
	
	public void addListener(final MVCEvent eventType, final MVCEventListener listener) {
		ListenerMapping lm = new ListenerMapping();
		lm.eventType = eventType;
		lm.listener = listener;
		listeners.add(lm);
	}
	
	public void removeListener(final MVCEvent eventType, final MVCEventListener listener) {
		List<ListenerMapping> toRemove = new ArrayList<ListenerMapping>();
		for (ListenerMapping lm : listeners) {
			if (eventType.equals(lm.eventType) && listener.equals(lm.listener)) {
				toRemove.add(lm);
			}
		}
		listeners.removeAll(toRemove);
	}
	
	public void fireEvent(final MVCEvent event) {
		fireEvent(event, null);
	}
	public void fireEvent(final MVCEvent event, final Object data) {
		DeferredCommand.addCommand(new Command() {
			public void execute() {
				for (ListenerMapping lm : listeners) {
					if (event.isAssignableTo(lm.eventType)) {
						lm.listener.onEvent(event, data);
					}
				}
			}
		});
	}
	
	private static class ListenerMapping {
		public MVCEvent eventType;
		public MVCEventListener listener;
	}
	
}
