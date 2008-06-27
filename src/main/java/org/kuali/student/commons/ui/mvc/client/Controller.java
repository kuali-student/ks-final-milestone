package org.kuali.student.commons.ui.mvc.client;

import org.kuali.student.commons.ui.mvc.client.model.Model;
import org.kuali.student.commons.ui.mvc.client.model.ModelObject;

public interface Controller {
	public EventDispatcher getEventDispatcher();
	public Model<? extends ModelObject> getModel(final Class<? extends ModelObject> modelObjectType);
	public void initializeModel(final Class<? extends ModelObject> modelObjectType, final Model<? extends ModelObject> model);
}
