package org.kuali.student.common.ui.client.widgets.list;

import com.google.gwt.event.shared.HandlerRegistration;

public interface HasSelectionChangeHandlers{

	HandlerRegistration addSelectionChangeHandler(SelectionChangeHandler handler);
}
