package org.kuali.student.ap.coursesearch.controller;

import org.kuali.rice.core.api.util.KeyValue;

import java.io.Serializable;

/**
 * Simple object for tracking facet click/count state.
 *
 * @see org.kuali.student.ap.coursesearch.controller.CourseSearchController.SessionSearchInfo
 */
public class FacetState implements Serializable {
    private static final long serialVersionUID = 1719950239861974273L;

    private final KeyValue value;
    private boolean checked = true;
    private int count;
    private String description;

    public FacetState(KeyValue value) {
        this.value = value;
    }

    public KeyValue getValue() {
        return value;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    /**
     * Increment the count
     */
    public void incrementCount() {
        count++;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
