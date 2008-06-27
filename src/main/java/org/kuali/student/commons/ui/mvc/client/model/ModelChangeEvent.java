package org.kuali.student.commons.ui.mvc.client.model;

import org.kuali.student.commons.ui.mvc.client.MVCEvent;

import com.google.gwt.core.client.GWT;

public abstract class ModelChangeEvent extends MVCEvent {
	public static abstract class AddEvent extends ModelChangeEvent {}
	public static abstract class RemoveEvent extends ModelChangeEvent {}
	public static abstract class UpdateEvent extends ModelChangeEvent {}
	
	public static final ModelChangeEvent MODEL_CHANGE_EVENT = GWT.create(ModelChangeEvent.class);
	public static final ModelChangeEvent ADD = GWT.create(AddEvent.class);
	public static final ModelChangeEvent REMOVE = GWT.create(RemoveEvent.class);
	public static final ModelChangeEvent UPDATE = GWT.create(UpdateEvent.class);
	
}
