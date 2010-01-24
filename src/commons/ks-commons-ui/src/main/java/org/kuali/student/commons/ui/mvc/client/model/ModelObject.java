package org.kuali.student.commons.ui.mvc.client.model;

import java.io.Serializable;

/**
 * Interface required for an object to be a ModelObject
 */
public interface ModelObject extends Serializable {
    /**
     * Returns a String that uniquely identifies this ModelObject. Result must be unique for this type of ModelObject
     * 
     * @return a String that uniquely identifies this ModelObject
     */
    public String getUniqueId();
}
