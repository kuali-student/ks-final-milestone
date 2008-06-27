package org.kuali.student.commons.ui.mvc.client.widgets;

import org.kuali.student.commons.ui.mvc.client.model.ModelObject;

public abstract class ModelTableColumn<T extends ModelObject> {
	public abstract String getColumnHeader();
	public abstract String getColumnValue(T modelObject);
}
