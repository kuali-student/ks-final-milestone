package org.kuali.student.core.organization.ui.client.view;

import com.google.gwt.event.shared.HandlerRegistration;


public interface HasStateChanges {

    /**
     * This method expects a widget to be able to save its state as a map of strings
     * 
     * @return a string map that will be merged into the history token
     */
    public String saveState();
    /**
     * This method expects a widget to be able to restore its state from a map of strings
     * 
     * @param state the tokenized history string
     */
    public void loadState(String state);
}
