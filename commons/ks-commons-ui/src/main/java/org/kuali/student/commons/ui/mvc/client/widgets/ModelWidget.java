package org.kuali.student.commons.ui.mvc.client.widgets;

import java.util.Collection;
import java.util.List;

import org.kuali.student.commons.ui.mvc.client.model.ModelObject;

/**
 * Interface defining the operations required for a widget to be bound to a model via a ModelBinding
 * 
 * @param <T>
 *            the type of ModelObject to bind to
 */
public interface ModelWidget<T extends ModelObject> {
    /**
     * Called by the ModelBinding when an object is added to the Model
     * 
     * @param modelObject
     *            the ModelObject that was added
     */
    public void add(T modelObject);

    /**
     * Called by the ModelBinding when an object is removed from the Model
     * 
     * @param modelObject
     *            the ModelObject that was removed
     */
    public void remove(T modelObject);

    /**
     * Called by the ModelBinding when an object is updated in the Model
     * 
     * @param modelObject
     *            the ModelObject that was updated
     */
    public void update(T modelObject);

    /**
     * "Selects" the model object. Implementation depends on the widget being bound.
     * 
     * @param modelObject
     *            the ModelObject to select
     */
    public void select(T modelObject);

    /**
     * Returns a List of the ModelObjects contained in the widget
     * 
     * @return
     */
    public List<T> getItems();

    /**
     * Called by the ModelBinding to clear the widget
     */
    public void clear();

    /**
     * Returns the currently selected ModelObject
     * 
     * @return the currently selected ModelObject
     */
    public T getSelection();
    
    /**
     * Called by the ModelBinding when the widget is first linked to the model.
     * If the model is already populated, the contents will be added to the widget
     * via a bulk operation.
     *  
     * @param collection the items to add to the widget
     */
    public void addBulk(Collection<T> collection);
}
