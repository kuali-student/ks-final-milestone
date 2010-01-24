package org.kuali.student.commons.ui.mvc.client;

/**
 * Interface defining an MVC event listener
 */
public interface MVCEventListener {
    /**
     * Called when the event is fired.
     * 
     * @param event
     *            the event that was fired
     * @param data
     *            the Object associated with the event, or null if no data associated with the event
     */
    public void onEvent(Class<? extends MVCEvent> event, final Object data);
}
