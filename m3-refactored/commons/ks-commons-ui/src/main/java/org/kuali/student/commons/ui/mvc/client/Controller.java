package org.kuali.student.commons.ui.mvc.client;

import java.util.HashMap;
import java.util.Map;

import org.kuali.student.commons.ui.mvc.client.model.Model;
import org.kuali.student.commons.ui.mvc.client.model.ModelObject;

import com.google.gwt.user.client.ui.Composite;

/**
 * Default implementation of the Controller interface.
 */
public abstract class Controller extends Composite  {
    private final EventDispatcher dispatcher = new EventDispatcher();
    private final Map<Class<?>, Model<? extends ModelObject>> models = new HashMap<Class<?>, Model<? extends ModelObject>>();

    /**
     * Returns the event dispatcher associated with this controller.
     * 
     * @return the event dispatcher associated with this controller.
     */
    public EventDispatcher getEventDispatcher() {
        return dispatcher;
    }

    /**
     * Returns the Model associated with the specified class. If the Model is not found locally, parent controllers are
     * searched recursively to find an appropriate Model.
     * 
     * @param modelObjectType
     *            the Class of the ModelObject type for which to retrieve a Model
     * @return Model for the specified ModelObject type, or null if not initialized at any level
     */
    public Model<? extends ModelObject> getModel(final Class<?> modelObjectType) {

        Model<? extends ModelObject> result = models.get(modelObjectType);

        if (result == null) {
            // not found locally, so ask parent
            Controller c = MVC.findParentController(this);
            if (c != null) {
                result = c.getModel(modelObjectType);
            }
        }
        return result;
    }

    /**
     * Associates a Model with this controller
     * 
     * @param modelObjectType
     *            the type of ModelObject with which this Model is associated
     * @param model
     *            the Model to add
     */
    protected void initializeModel(final Class<?> modelObjectType, final Model<? extends ModelObject> model) {
        if (!models.containsKey(modelObjectType)) {
            models.put(modelObjectType, model);
        }
    }

}
