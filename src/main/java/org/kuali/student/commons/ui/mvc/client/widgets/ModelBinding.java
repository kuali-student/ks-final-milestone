package org.kuali.student.commons.ui.mvc.client.widgets;

import org.kuali.student.commons.ui.mvc.client.MVCEvent;
import org.kuali.student.commons.ui.mvc.client.MVCEventListener;
import org.kuali.student.commons.ui.mvc.client.model.Model;
import org.kuali.student.commons.ui.mvc.client.model.ModelChangeEvent;
import org.kuali.student.commons.ui.mvc.client.model.ModelObject;

/**
 * Used to create a linkage between a Model and a widget implement ModelWidget
 * 
 * @param <T>
 *            the ModelObject type of the Model being bound to
 */
public class ModelBinding<T extends ModelObject> {
    private Model<T> model = null;
    private MVCEventListener listener = null;

    /**
     * Creates a ModelBinding linking the specified model to the specified widget
     * 
     * @param model
     *            the model to which to bind the widget
     * @param widget
     *            the widget to which to bind the model
     */
    public ModelBinding(final Model<T> model, final ModelWidget<T> widget) {
        this.model = model;
        listener = new MVCEventListener() {
            public void onEvent(final Class<? extends MVCEvent> event, final Object data) {
                @SuppressWarnings("unchecked")
                T object = (T) data;

                if (event.equals(ModelChangeEvent.AddEvent.class)) {
                    widget.add(object);
                } else if (event.equals(ModelChangeEvent.UpdateEvent.class)) {
                    widget.update(object);
                } else if (event.equals(ModelChangeEvent.RemoveEvent.class)) {
                    widget.remove(object);
                }
            }

            
        };
        model.addListener(ModelChangeEvent.class, listener);
        widget.addBulk(model.items());
    }

    /**
     * Destroys the link between the model and the widget. Should be called in the widget's onUnload event
     */
    public void unlink() {
        model.removeListener(ModelChangeEvent.class, listener);
    }
}
