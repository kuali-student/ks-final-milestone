package org.kuali.student.commons.ui.mvc.client;

import java.util.HashMap;
import java.util.Map;

import org.kuali.student.commons.ui.mvc.client.model.Model;
import org.kuali.student.commons.ui.mvc.client.model.ModelObject;

import com.google.gwt.user.client.ui.Composite;

public class ControllerComposite extends Composite implements Controller {
	private final EventDispatcher dispatcher = new EventDispatcher();
	private final Map<Class<? extends ModelObject>, Model<? extends ModelObject>> models = new HashMap<Class<? extends ModelObject>, Model<? extends ModelObject>>();
	
	public EventDispatcher getEventDispatcher() {
		return dispatcher;
	}

	public Model<? extends ModelObject> getModel(
			final Class<? extends ModelObject> modelObjectType) {
		
		Model<? extends ModelObject> result = models.get(modelObjectType);
		
		if (result == null) {
			// not found locally, so ask parent
			ControllerComposite c = MVC.findParentController(this);
			if (c != null) {
				result = c.getModel(modelObjectType);
			}
		}
		return result;
	}

	public void initializeModel(final Class<? extends ModelObject> modelObjectType, final Model<? extends ModelObject> model) {
		if (!models.containsKey(modelObjectType)) {
			models.put(modelObjectType, model);
		}
	}

}
