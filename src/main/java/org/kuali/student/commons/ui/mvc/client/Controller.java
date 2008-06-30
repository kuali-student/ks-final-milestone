package org.kuali.student.commons.ui.mvc.client;

import org.kuali.student.commons.ui.mvc.client.model.Model;
import org.kuali.student.commons.ui.mvc.client.model.ModelObject;
/**
 * Interface defining basic operations required to implement controller layer of MVC framework.
 * Most implementations will extend ControllerComposite, which is the default implementation.
 */
public interface Controller {
	/**
	 * Returns the event dispatcher associated with this controller.
	 * @return the event dispatcher associated with this controller.
	 */
	public EventDispatcher getEventDispatcher();
	/**
	 * Returns the Model associated with the specified class.
	 * @param modelObjectType the Class of the ModelObject type for which to retrieve a Model
	 * @return Model for the specified ModelObject type, or null if not initialized.
	 */
	public Model<? extends ModelObject> getModel(final Class<? extends ModelObject> modelObjectType);
	/**
	 * Associates a Model with this controller
	 * @param modelObjectType the type of ModelObject with which this Model is associated
	 * @param model the Model to add
	 */
	public void initializeModel(final Class<? extends ModelObject> modelObjectType, final Model<? extends ModelObject> model);
}
