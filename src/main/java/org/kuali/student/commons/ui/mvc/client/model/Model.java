package org.kuali.student.commons.ui.mvc.client.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kuali.student.commons.ui.mvc.client.MVCEventListener;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;

public class Model<T extends ModelObject> {
	private final Map<String, T> data = new HashMap<String, T>();
	
	private final Set<ListenerMapping> listeners = new HashSet<ListenerMapping>();
	
	
	
	public void addListener(ModelChangeEvent eventType, final MVCEventListener listener) {
		ListenerMapping lm = new ListenerMapping();
		lm.eventType = eventType;
		lm.listener = listener;
		listeners.add(lm);
	}
	
	public void removeListener(ModelChangeEvent eventType, final MVCEventListener listener) {
		List<ListenerMapping> toRemove = new ArrayList<ListenerMapping>();
		for (ListenerMapping lm : listeners) {
			if (eventType.equals(lm.eventType) && listener.equals(lm.listener)) {
				toRemove.add(lm);
			}
		}
		listeners.removeAll(toRemove);
	}
	

	public void fireChangeEvent(final ModelChangeEvent event, final T data) {
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
		public ModelChangeEvent eventType;
		public MVCEventListener listener;
	}
	

	public void add(T object) {
		data.put(object.getUniqueId(), object);
		fireChangeEvent(ModelChangeEvent.ADD, object);
	}
	public void update(T object) {
		// add if not already in data map
		data.put(object.getUniqueId(), object);
		fireChangeEvent(ModelChangeEvent.UPDATE, object);
	}
	public void remove(T object) {
		data.remove(object.getUniqueId());
		fireChangeEvent(ModelChangeEvent.REMOVE, object);
	}
	
	public T get(String uniqueId) {
		return data.get(uniqueId);
	}
	
	/**
	 * Clones values collection into a new arraylist to prevent direct manipulation of model
	 * @return
	 */
	public List<T> items() {
		return new ArrayList<T>(data.values());
	}
	
	

	

	
	
}
