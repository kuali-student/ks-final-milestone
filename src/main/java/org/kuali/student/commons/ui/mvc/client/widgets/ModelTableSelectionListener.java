package org.kuali.student.commons.ui.mvc.client.widgets;

import org.kuali.student.commons.ui.mvc.client.model.ModelObject;

public interface ModelTableSelectionListener<T extends ModelObject> {
	public void onSelect(T modelObject);
}
