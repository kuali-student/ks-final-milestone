package org.kuali.student.ap.coursesearch;

import org.kuali.rice.core.api.util.KeyValue;

/**
 * Simple object for tracking facet click/count state.
 */
public interface FacetState {

    /**
     * Get the value
     * @return The KeyValue value
     */
    KeyValue getValue();

    /**
     * Get the value of the checked flag
     * @return True if checked, false if not checked
     */
    boolean isChecked();

    /**
     * Get the count of how many results this facet includes
     * @return The count
     */
    int getCount();

    /**
     * Increment the facet count by 1
     */
    void incrementCount();

    /**
     * Get the description
     * @return The description
     */
    String getDescription();

    /**
     * Set the checked flag
     * @param checked
     */
    void setChecked(boolean checked);

    /**
     * Reset the facet count back to 0
     */
    void resetCount();
}
