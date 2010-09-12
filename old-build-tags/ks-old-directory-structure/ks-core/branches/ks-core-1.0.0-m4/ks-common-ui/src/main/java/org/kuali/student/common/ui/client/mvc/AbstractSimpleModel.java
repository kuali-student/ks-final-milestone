package org.kuali.student.common.ui.client.mvc;

import org.kuali.student.common.ui.client.mvc.ModelChangeEvent.Action;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;

public class AbstractSimpleModel implements Model {
	private HandlerManager handlers = new HandlerManager(this);
	@Override
	public HandlerRegistration addModelChangeHandler(ModelChangeHandler handler) {
		return handlers.addHandler(ModelChangeEvent.TYPE, handler);
	}
	
	protected void fireChangeEvent(Action action) {
		handlers.fireEvent(new ModelChangeEvent(action, this));
	}

}
